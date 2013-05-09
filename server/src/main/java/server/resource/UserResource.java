package server.resource;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import server.domain.Message;
import server.domain.User;
import server.exception.MissingPartException;
import server.logger.InjectLogger;
import server.service.ImageService;
import server.service.MessageService;
import server.service.UserService;
import server.service.UserService.SortType;
import server.util.Count;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.multipart.FormDataParam;

@Path(UserResource.PATH)
public class UserResource extends BaseResource {
	public static final String PATH = "users";
	@InjectLogger
	private Logger logger;
	@Inject
	private UserService userService;
	@Inject
	private ImageService imageService;
	@Inject
	private MessageService messageService;

	@GET
	public List<User> list(@QueryParam("query") String query, @QueryParam("sort") String sort, @QueryParam("reverse") boolean reverse,
			@QueryParam("start") Integer start, @QueryParam("count") Integer count) {
		logger.info("List Users");
		return userService.list(query, SortType.fromString(sort), reverse, start, count);
	}

	@GET
	@Path("count")
	public Count count(@QueryParam("query") String query) {
		logger.info("Count Users");
		return new Count(userService.count(query));
	}

	@POST
	public Response create(User user) {
		userService.create(user);
		logger.info("Created " + user);
		return Response.status(Status.CREATED).entity(user).build();
	}

	@GET
	@Path("/{id}")
	public User find(@PathParam("id") Integer id) {
		User user = load(id);
		logger.info("Find User " + id);
		return user;
	}

	@PUT
	@Path("/{id}")
	public User modify(@PathParam("id") Integer id, User user) {
		requireUser(user);
		user.setId(id);
		user = userService.modify(user);
		logger.info("Modified " + user);
		return user;
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Integer id) {
		requireAdmin();
		User user = load(id);
		userService.delete(user);
		logger.info("Deleted " + user);
	}

	@GET
	@Path("/{id}/messages")
	public List<Message> message(@PathParam("id") Integer id, @QueryParam("sort") String sort, @QueryParam("reverse") boolean reverse,
			@QueryParam("start") Integer start, @QueryParam("count") Integer count) {
		User user = load(id);
		logger.info("List Messages for " + user);
		return messageService.list(user, MessageService.SortType.fromString(sort), reverse, start, count);
	}

	@GET
	@Path("/{id}/messages/count")
	public Integer songs(@PathParam("id") Integer id) {
		User user = load(id);
		logger.info("Count Songs for " + user);
		return messageService.count(user);
	}

	@GET
	@Path("{id}/image")
	public Response imageDownload(@PathParam("id") Integer id, @QueryParam("width") Integer width, @QueryParam("height") Integer height) {
		User user = load(id);
		InputStream stream = imageService.getStream(user.getImage(), width, height);
		logger.info("Downloading image for " + user);
		return Response.ok().type(MediaType.valueOf("image/png")).entity(stream).build();
	}

	@POST
	@Path("{id}/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response imageUpload(@PathParam("id") Integer id, @FormDataParam("image") InputStream imageStream) {
		if (imageStream == null)
			throw new MissingPartException("image");
		User user = load(id);
		requireUser(user);
		logger.info("Uploading image for " + user);
		user.setImage(imageService.create(imageStream));
		user = userService.update(user);
		return Response.status(Status.OK).entity(user).build();
	}

	private User load(Integer id) {
		User user = userService.find(id);
		if (user == null)
			throw new NotFoundException();
		return user;
	}
}
