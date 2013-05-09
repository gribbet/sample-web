package server.filter;


import java.util.Date;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import server.domain.Authentication;
import server.logger.InjectLogger;
import server.service.AuthenticationService;

import com.google.inject.Inject;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	@InjectLogger
	private Logger logger;
	@Inject
	private AuthenticationService authenticationService;
	@Context
	private HttpContext context;

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		Authentication authentication = (Authentication) context.getProperties().get("authentication");
		String authenticationName = "none";
		if (authentication != null) {
			authentication = authenticationService.find(authentication);
			authenticationName = authentication.getName();
		}
		Date startTime = (Date) context.getProperties().get("requestStartTime");
		Long time = null;
		if (startTime != null)
			time = new Date().getTime() - startTime.getTime();
		logger.debug(response.getStatus() + " " + request.getMethod() + " " + request.getPath() + " (" + authenticationName + ") "
				+ (time != null ? time + "ms" : ""));
		return response;
	}

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		context.getProperties().put("requestStartTime", new Date());
		return request;
	}
}
