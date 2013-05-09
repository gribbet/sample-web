package server.util;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement
public class Error {
	private String code;
	private String message;

	public Error() {
	}

	public Error(String code) {
		this.code = code;
	}

	public Error(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("code", code).add("message", message).toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}