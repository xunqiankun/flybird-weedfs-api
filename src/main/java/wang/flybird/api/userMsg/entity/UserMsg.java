package wang.flybird.api.userMsg.entity;

public class UserMsg {

	private String msgid;
	private String senderid;
	private String sendericon;
	private String sendertype;
	private String sendtime;
	private String msgtitle;
	private String isread;
	private String navurl;
	private String msg;
	
	
	public UserMsg(String msgid) {
		super();
		this.msgid = msgid;
	}
	public UserMsg(String msgid, String senderid, String sendericon, String sendertype, String sendtime,
			String msgtitle, String isread, String msg) {
		super();
		this.msgid = msgid;
		this.senderid = senderid;
		this.sendericon = sendericon;
		this.sendertype = sendertype;
		this.sendtime = sendtime;
		this.msgtitle = msgtitle;
		this.isread = isread;
		this.msg = msg;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public String getSendericon() {
		return sendericon;
	}
	public void setSendericon(String sendericon) {
		this.sendericon = sendericon;
	}
	public String getSendertype() {
		return sendertype;
	}
	public void setSendertype(String sendertype) {
		this.sendertype = sendertype;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getMsgtitle() {
		return msgtitle;
	}
	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}
	public String getIsread() {
		return isread;
	}
	public void setIsread(String isread) {
		this.isread = isread;
	}
	public String getNavurl() {
		return navurl;
	}
	public void setNavurl(String navurl) {
		this.navurl = navurl;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
