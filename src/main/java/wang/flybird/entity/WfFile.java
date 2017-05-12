package wang.flybird.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the wf_file database table.
 * 
 */
@Entity
@Table(name="wf_file")
@NamedQuery(name="WfFile.findAll", query="SELECT w FROM WfFile w")
public class WfFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length=1)
	private String delflag;

	@Column(length=15)
	private String fid;

	@Column(length=255)
	private String fname;

	private long fsize;

	@Id
	@GeneratedValue(generator="id")
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(length=36)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uptime;

	@Column(length=36)
	private String username;

	public WfFile() {
	}

	public String getDelflag() {
		return this.delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public long getFsize() {
		return this.fsize;
	}

	public void setFsize(long fsize) {
		this.fsize = fsize;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUptime() {
		return this.uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}