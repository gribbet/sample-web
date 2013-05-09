package server.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import server.resource.TokenResource;

import com.google.common.base.Objects;

@Entity
@Table(name = "token")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Token extends DomainObject<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "key1")
	private String key;
	@Column(nullable = false)
	private String secret;
	@Column(nullable = false)
	private Date expiration;
	@ManyToOne(optional = false)
	private User user;

	@Override
	public String getId() {
		return getKey();
	}

	@Override
	public void setId(String id) {
		setKey(id);
	}

	public String getUriPath() {
		return TokenResource.PATH + "/" + getKey();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("key", key).add("secret", secret).add("expiration", expiration).add("user", user)
				.toString();
	}

	public boolean isValid() {
		return expiration.before(new Date());
	}

	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlElement
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@XmlElement
	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	@XmlElement
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
