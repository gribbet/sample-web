package server.util;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.google.common.base.Objects;

@XmlRootElement
public class Count {
	@XmlValue
	private Integer count;

	public Count() {
	}

	public Count(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("count", count).toString();
	}
}