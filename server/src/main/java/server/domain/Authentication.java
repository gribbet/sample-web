package server.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import server.resource.AuthenticationResource;

import com.google.common.base.Objects;

@Entity
@Table(name = "authentication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Authentication extends DomainObject<String> {
	@Id
	@Column(name = "key1")
	private String key;
	@Version
	private int version;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false)
	private String secret;
	@Column(nullable = false)
	private boolean admin = false;

	public String getUriPath() {
		return AuthenticationResource.PATH + "/" + getKey();
	}

	@Override
	public String getId() {
		return getKey();
	}

	@Override
	public void setId(String id) {
		setKey(id);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("key", key).add("name", name).add("secret", secret).toString();
	}

	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@XmlElement
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
