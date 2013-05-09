package server.exception;

public class MissingPartException extends ServerException {
	public MissingPartException(String field) {
		super("missing_body_part", "\"" + field + "\" body part is required");
	}
}
