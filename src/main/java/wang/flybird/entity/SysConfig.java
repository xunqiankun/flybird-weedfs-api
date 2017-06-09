package wang.flybird.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_config database table.
 * 
 */
@Entity
@Table(name="sys_config")
@NamedQuery(name="SysConfig.findAll", query="SELECT s FROM SysConfig s")
public class SysConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SYS_CONFIG_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SYS_CONFIG_ID_GENERATOR")
	private String id;

	private String key;

	private String remark;

	private byte status;

	private String value;

	public SysConfig() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}