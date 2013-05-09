package server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import server.resource.MessageResource;
import server.util.Modifiable;

import com.google.common.base.Objects;

@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Indexed
public class Message extends DomainObject<Integer> {
	@Id
	@GeneratedValue
	private Integer id;
	@Version
	private int version;
	@ManyToOne
	@Modifiable
	private User user;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	@Column(nullable = false)
	@Field
	@Modifiable
	private String subject;
	@Column(nullable = false)
	@Field
	@Modifiable
	private String message;

	public String getUriPath() {
		return MessageResource.PATH + "/" + getId();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("subject", subject).toString();
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
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@XmlElement
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@XmlElement
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@XmlElement
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
