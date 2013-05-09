package server.server.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import server.logger.InjectLogger;
import server.service.AuthenticationService;
import server.util.Error;

import com.google.inject.Inject;

@Provider
public class GenericExceptionMapper extends BaseExceptionMapper<Exception> {
	@InjectLogger
	private Logger logger;
	@SuppressWarnings("unused")
	@Inject
	// Need @Inject to @InjectLogger
	private AuthenticationService authenticationService;

	@Override
	public Response toResponse(Exception exception) {
		logger.error("Exception", exception);
		Throwable throwable = exception;
		while (throwable.getCause() != null)
			throwable = throwable.getCause();
		return Response.status(Status.INTERNAL_SERVER_ERROR).type(getMediaType()).entity(new Error("unexpected", throwable.getMessage()))
				.build();
	}
}
