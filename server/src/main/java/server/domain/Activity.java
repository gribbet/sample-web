package server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import server.domain.Activity.ActivityId;

import com.google.common.base.Objects;

@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Activity extends DomainObject<ActivityId> implements Comparable<Activity> {
	@EmbeddedId
	private ActivityId id = new ActivityId();
	private long total = 1L;

	@Embeddable
	public static class ActivityId implements Serializable {
		@Column(name = "key1")
		private String key;
		private long time;
		private boolean hourly = false;

		public ActivityId() {
		}

		public ActivityId(String key, long time, boolean hourly) {
			this.key = key;
			this.time = time;
			this.hourly = hourly;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public boolean isHourly() {
			return hourly;
		}

		public void setHourly(boolean hourly) {
			this.hourly = hourly;
		}

		@Override
		public boolean equals(Object object) {
			if (!(object instanceof ActivityId))
				return false;
			ActivityId other = (ActivityId) object;
			return Objects.equal(key, other.key) && Objects.equal(time, other.time) && Objects.equal(hourly, other.hourly);
		}

		public int hashCode() {
			return Objects.hashCode(key, time, hourly);
		}
	}

	@StaticMetamodel(ActivityId.class)
	public static abstract class ActivityId_ {
		public static volatile SingularAttribute<ActivityId, String> key;
		public static volatile SingularAttribute<ActivityId, Long> time;
		public static volatile SingularAttribute<ActivityId, Boolean> hourly;
	}

	@Override
	public String getUriPath() {
		return "activities";
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("key", id.key).add("time", id.time).add("hourly", id.hourly).add("total", total).toString();
	}

	@Override
	public int compareTo(Activity other) {
		return new Long(id.time).compareTo(other.id.time);
	}

	public ActivityId getId() {
		return id;
	}

	public void setId(ActivityId id) {
		this.id = id;
	}

	@XmlElement
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@XmlElement
	public String getKey() {
		return id.key;
	}

	public void setKey(String key) {
		id.key = key;
	}

	@XmlElement
	public long getTime() {
		return id.time;
	}

	public void setTime(long time) {
		id.time = time;
	}

	public boolean isHourly() {
		return id.hourly;
	}

	public void setHourly(boolean hourly) {
		id.hourly = hourly;
	}
}