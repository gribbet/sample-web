package server.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import server.resource.UserResource;
import server.util.Modifiable;

import com.google.common.base.Objects;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Indexed
public class User extends DomainObject<Integer> {
	@Id
	@GeneratedValue
	private Integer id;
	@Version
	private int version;
	@Column(nullable = false)
	@Field
	@Modifiable
	private String name;
	@Column(unique = true, nullable = false)
	@Index(name = "idx_username")
	@Field
	@Modifiable
	private String username;
	@Column(unique = true, nullable = false)
	@Field
	@Modifiable
	private String email;
	@Transient
	private String password;
	@Column(name = "password_hash", nullable = false)
	@Index(name = "idx_password_hash")
	@Modifiable
	private String passwordHash;
	@OneToOne
	private Image image;
	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<Message> messages = new ArrayList<Message>();

	public String getUriPath() {
		return UserResource.PATH + "/" + getId();
	}

	@XmlElement
	public String getImageUri() {
		if (image == null)
			return null;
		return getUri() + "/image";
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("name", name).add("username", username).toString();
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
