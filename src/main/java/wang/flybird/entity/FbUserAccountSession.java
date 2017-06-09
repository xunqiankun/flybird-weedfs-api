package wang.flybird.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the fb_user_account_session database table.
 * 
 */
@Entity
@Table(name="fb_user_account_session")
@NamedQuery(name="FbUserAccountSession.findAll", query="SELECT f FROM FbUserAccountSession f")
public class FbUserAccountSession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="id")
	@GenericGenerator(name = "id", strategy = "assigned")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expire_time")
	private Date expireTime;

	@Column(name="fb_user_account_id")
	private String fbUserAccountId;

	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public FbUserAccountSession() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getFbUserAccountId() {
		return this.fbUserAccountId;
	}

	public void setFbUserAccountId(String fbUserAccountId) {
		this.fbUserAccountId = fbUserAccountId;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}