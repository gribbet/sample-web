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

import server.domain.Message;
import server.logger.InjectLogger;
import server.service.MessageService;
import server.service.MessageService.SortType;
import server.util.Count;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

@Path(MessageResource.PATH)
public class MessageResource extends BaseResource {
	public static final String PATH = "messages";
	@InjectLogger
	private Logger logger;
	@Inject
	private MessageService messageService;

	@GET
	public List<Message> list(@QueryParam("query") String query, @QueryParam("sort") String sort, @QueryParam("reverse") boolean reverse,
			@QueryParam("start") Integer start, @QueryParam("count") Integer count) {
		logger.info("List Messages");
		return messageService.list(query, SortType.fromString(sort), reverse, start, count);
	}

	@GET
	@Path("count")
	public Count count(@QueryParam("query") String query) {
		logger.info("Count Messages");
		return new Count(messageService.count(query));
	}

	@POST
	public Response create(Message message) {
		requireUser(message.getUser());
		messageService.create(message);
		logger.info("Created " + message);
		return Response.status(Status.CREATED).entity(message).build();
	}

	@GET
	@Path("/{id}")
	public Message find(@PathParam("id") Integer id) {
		Message message = load(id);
		logger.info("Find Message " + id);
		return message;
	}

	@PUT
	@Path("/{id}")
	public Message modify(@PathParam("id") Integer id, Message message) {
		Message original = load(id);
		requireUser(original.getUser());
		message.setId(id);
		message = messageService.modify(message);
		logger.info("Modified " + message);
		return message;
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Integer id) {
		Message message = load(id);
		requireUser(message.getUser());
		messageService.delete(message);
		logger.info("Deleted " + message);
	}

	private Message load(Integer id) {
		Message message = messageService.find(id);
		if (message == null)
			throw new NotFoundException();
		return message;
	}
}
