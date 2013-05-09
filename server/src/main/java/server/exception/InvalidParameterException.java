package server.exception;

public class InvalidParameterException extends ServerException {
	public InvalidParameterException(String field, String message) {
		super("invalid_parameter", "\"" + field + "\" is invalid: " + message);
	}
}
