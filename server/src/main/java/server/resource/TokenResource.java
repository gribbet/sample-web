package server.resource;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import server.domain.Token;
import server.exception.UnauthorizedException;
import server.logger.InjectLogger;
import server.service.TokenService;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

@Path(TokenResource.PATH)
public class TokenResource extends BaseResource {
	public static final String PATH = "tokens";
	@InjectLogger
	private Logger logger;
	@Inject
	private TokenService tokenService;

	@POST
	public Response create(@QueryParam("username") String username, @QueryParam("password") String password) {
		Token token = tokenService.getToken(username, password);
		if (token == null) {
			logger.info("Login failed for \"" + username + "\"");
			throw new UnauthorizedException("login_failed", "Invalid username/password combination");
		}
		logger.info("Login successful for \"" + username + "\"");
		return Response.status(Status.CREATED).entity(token).build();
	}

	@GET
	@Path("/{key}")
	public Token find(@PathParam("key") String key) {
		Token token = tokenService.find(key);
		if (token == null)
			throw new NotFoundException();
		logger.info("Find Token " + key);
		return token;
	}
}
