package server.server.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import server.exception.ServerException;
import server.util.Error;

@Provider
public class ServerExceptionMapper extends BaseExceptionMapper<ServerException> {
	@Override
	public Response toResponse(ServerException exception) {
		ServerException serverException = (ServerException) exception;
		return respond(serverException.getStatus(), new Error(serverException.getCode(), serverException.getMessage()));
	}

	private Response respond(Status status, Error error) {
		return Response.status(status).type(getMediaType()).entity(error).build();
	}
}
