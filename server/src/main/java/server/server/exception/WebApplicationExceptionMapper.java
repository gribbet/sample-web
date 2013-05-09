package server.server.exception;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.UnmarshalException;

import org.slf4j.Logger;

import server.logger.InjectLogger;
import server.service.AuthenticationService;
import server.util.Error;

import com.google.inject.Inject;
import com.sun.jersey.json.impl.reader.JsonFormatException;

@Provider
public class WebApplicationExceptionMapper extends BaseExceptionMapper<WebApplicationException> {
	@InjectLogger
	private Logger logger;
	@SuppressWarnings("unused")
	@Inject
	// Need @Inject to @InjectLogger
	private AuthenticationService authenticationService;

	@Override
	public Response toResponse(WebApplicationException exception) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST).type(getMediaType());
		if (exception.getCause() instanceof UnmarshalException) {
			UnmarshalException unmarshalException = (UnmarshalException) exception.getCause();
			return builder.entity(new Error("parse_error", unmarshalException.getLinkedException().getMessage())).build();
		}
		if (exception.getCause() instanceof JsonFormatException) {
			JsonFormatException jsonFormatException = (JsonFormatException) exception.getCause();
			return builder.entity(
					new Error("parse_error", jsonFormatException.getMessage() + ". Line: " + jsonFormatException.getErrorLine()
							+ ", Column: " + jsonFormatException.getErrorColumn())).build();
		}
		if (exception.getResponse() != null) {
			builder = builder.status(exception.getResponse().getStatus());
			if (exception.getCause() != null) {
				Throwable throwable = exception;
				while (throwable.getCause() != null)
					throwable = throwable.getCause();
				String message = throwable.getMessage();
				builder = builder.entity(new Error("unexpected", message));
				logger.error("Unexpected", exception.getCause());
			}

			return builder.build();
		}
		logger.error("Unexpected exception occurred", exception);
		return builder.entity(new Error("unexpected", exception.getMessage())).build();
	}
}
