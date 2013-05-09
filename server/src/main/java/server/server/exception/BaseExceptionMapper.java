package server.server.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class BaseExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {
	@Context
	private HttpHeaders headers;

	protected MediaType getMediaType() {
		MediaType mediaType = headers.getMediaType();
		if (mediaType == null && headers.getAcceptableMediaTypes().size() > 0)
			mediaType = headers.getAcceptableMediaTypes().get(0);
		if (!MediaType.APPLICATION_XML_TYPE.equals(mediaType) && !MediaType.APPLICATION_JSON_TYPE.equals(mediaType))
			mediaType = MediaType.APPLICATION_JSON_TYPE;
		return mediaType;
	}
}
