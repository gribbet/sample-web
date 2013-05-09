package server.filter;


import java.util.Date;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;

import server.configuration.Configuration;
import server.domain.Authentication;
import server.domain.Token;
import server.exception.UnauthorizedException;
import server.service.AuthenticationService;
import server.service.TokenService;
import server.util.DateUtil;

import com.google.inject.Inject;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.oauth.server.OAuthServerRequest;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.signature.OAuthSignature;
import com.sun.jersey.oauth.signature.OAuthSignatureException;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class OAuthAuthenticationFilter implements ContainerRequestFilter {
	@Inject
	private AuthenticationService authenticationService;
	@Inject
	private TokenService tokenService;
	@Context
	private HttpContext context;

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {
		if (!Configuration.secure) {
			context.getProperties().put("admin", true);
			return containerRequest;
		}
		String method = containerRequest.getMethod();
		if (!method.equals("POST") && !method.equals("GET") && !method.equals("PUT") && !method.equals("DELETE"))
			return containerRequest;
		OAuthServerRequest request = new OAuthServerRequest(containerRequest);
		OAuthParameters parameters = new OAuthParameters();
		parameters.readRequest(request);

		String key = parameters.getConsumerKey();
		if (key == null)
			throw new UnauthorizedException("no_consumer_key", "OAuth consumer key required");
		Authentication authentication = authenticationService.find(key);
		if (authentication == null)
			throw new UnauthorizedException("bad_consumer_key", "Invalid OAuth consumer key");
		OAuthSecrets secrets = new OAuthSecrets();
		secrets.setConsumerSecret(authentication.getSecret());
		context.getProperties().put("authentication", authentication);

		String tokenKey = parameters.getToken();
		if (!StringUtils.isEmpty(tokenKey)) {
			Token token = tokenService.find(tokenKey);
			if (token == null || !token.isValid())
				throw new UnauthorizedException("invalid_token_key", "Invalid OAuth token key");
			secrets.setTokenSecret(token.getSecret());
			context.getProperties().put("token", token);
			context.getProperties().put("user", token.getUser());
		}

		String timestampString = parameters.getTimestamp();
		if (timestampString == null)
			timestampString = "0";
		try {
			Date timestamp = new Date(Long.parseLong(timestampString) * 1000);
			if (timestamp.before(DateUtil.previousHour(new Date())))
				throw new UnauthorizedException("bad_timestamp", "Invalid OAuth timestamp");
		} catch (NumberFormatException e) {
			throw new UnauthorizedException("bad_timestamp", "Invalid OAuth timestamp");
		}

		try {
			if (!OAuthSignature.verify(request, parameters, secrets))
				throw new UnauthorizedException("bad_signature", "Invalid OAuth signature");
		} catch (OAuthSignatureException e) {
			throw new UnauthorizedException("bad_signature", "Invalid OAuth signature");
		}

		return containerRequest;
	}
}
