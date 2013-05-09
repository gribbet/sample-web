package server.exception;

import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends ServerException {

	public UnauthorizedException(String code, String message) {
		super(code, message);
	}

	public Status getStatus() {
		return Status.UNAUTHORIZED;
	}
}
