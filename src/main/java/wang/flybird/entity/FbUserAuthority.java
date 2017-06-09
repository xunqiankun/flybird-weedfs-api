package wang.flybird.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the fb_user_authority database table.
 * 
 */
@Entity
@Table(name="fb_user_authority")
@NamedQuery(name="FbUserAuthority.findAll", query="SELECT f FROM FbUserAuthority f")
public class FbUserAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="id")
	@GenericGenerator(name = "id", strategy = "assigned")
	private String id;

	@Column(name="authority_id")
	private String authorityId;

	@Column(name="user_id")
	private String userId;

	public FbUserAuthority() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}