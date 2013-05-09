package server.exception;

import javax.ws.rs.core.Response.Status;

public class ServerException extends RuntimeException {
	private String code;

	public ServerException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Status getStatus() {
		return Status.BAD_REQUEST;
	}
}
