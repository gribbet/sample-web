package server.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import server.resource.FileResource;

import com.google.common.base.Objects;

@Entity
@Table(name = "file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class File extends DomainObject<Integer> {
	@Id
	@GeneratedValue
	private Integer id;
	@Version
	private int version;
	@Column(name = "file_name", nullable = false)
	private String fileName;
	@Column(name = "size", nullable = false)
	private Long size = 0L;

	public String getUriPath() {
		return FileResource.PATH + "/" + getId();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("fileName", fileName).toString();
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
}
