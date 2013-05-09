package server.domain;


import java.io.Serializable;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import server.configuration.Configuration;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class DomainObject<Id> implements Serializable {
	@Transient
	private boolean initialized = true;

	public void initialize() {
	}

	public abstract Id getId();

	public abstract void setId(Id id);

	@XmlElement
	public String getUri() {
		return Configuration.baseUri + getUriPath();
	}

	public abstract String getUriPath();

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}
