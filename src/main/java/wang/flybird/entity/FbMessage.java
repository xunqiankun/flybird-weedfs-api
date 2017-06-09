package wang.flybird.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fb_message database table.
 * 
 */
@Entity
@Table(name="fb_message")
@NamedQuery(name="FbMessage.findAll", query="SELECT f FROM FbMessage f")
public class FbMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FB_MESSAGE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FB_MESSAGE_ID_GENERATOR")
	private String id;

	private String msg;

	private String msgtitle;

	private String msgtype;

	private String receiverid;

	private String receivertype;

	private String senderid;

	private String sendertype;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendtime;

	public FbMessage() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgtitle() {
		return this.msgtitle;
	}

	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}

	public String getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public String getReceivertype() {
		return this.receivertype;
	}

	public void setReceivertype(String receivertype) {
		this.receivertype = receivertype;
	}

	public String getSenderid() {
		return this.senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getSendertype() {
		return this.sendertype;
	}

	public void setSendertype(String sendertype) {
		this.sendertype = sendertype;
	}

	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

}