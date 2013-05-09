package server.exception;

public class MissingParameterException extends ServerException {
	public MissingParameterException(String field) {
		super("missing_parameter", "\"" + field + "\" is required");
	}
}
