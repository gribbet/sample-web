package server.resource;


import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import server.domain.Authentication;
import server.logger.InjectLogger;
import server.service.AuthenticationService;
import server.util.Count;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

@Path(AuthenticationResource.PATH)
public class AuthenticationResource extends BaseResource {
	public static final String PATH = "authentications";
	@InjectLogger
	private Logger logger;
	@Inject
	private AuthenticationService authenticationService;

	@GET
	public List<Authentication> list(@QueryParam("start") Integer start, @QueryParam("count") Integer count) {
		requireAdmin();
		logger.info("List Authentications");
		return authenticationService.list(start, count);
	}

	@GET
	@Path("count")
	public Count count() {
		requireAdmin();
		logger.info("Count Authentications");
		return new Count(authenticationService.count());
	}

	@POST
	public Response create(Authentication authentication) {
		requireAdmin();
		authenticationService.create(authentication);
		logger.info("Created " + authentication);
		return Response.status(Status.CREATED).entity(authentication).build();
	}

	@GET
	@Path("/{key}")
	public Authentication find(@PathParam("key") String key) {
		Authentication authentication = load(key);
		logger.info("Find Authentication " + key);
		return authentication;
	}

	@PUT
	@Path("/{key}")
	public Authentication modify(@PathParam("key") String key, Authentication authentication) {
		requireAdmin();
		authentication.setKey(key);
		authentication = authenticationService.modify(authentication);
		logger.info("Modified " + authentication);
		return authentication;
	}

	@DELETE
	@Path("/{key}")
	public void delete(@PathParam("key") String key) {
		requireAdmin();
		Authentication authentication = load(key);
		logger.info("Deleted " + authentication);
		authenticationService.delete(authentication);
	}

	private Authentication load(String key) {
		Authentication authentication = authenticationService.find(key);
		if (authentication == null)
			throw new NotFoundException();
		return authentication;
	}
}
