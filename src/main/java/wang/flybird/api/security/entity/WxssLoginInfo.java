package wang.flybird.api.security.entity;

import wang.flybird.api.demo.entity.Person;

public class WxssLoginInfo {
    private String clientType;
    private String accountType;
    private String wxAppId;
    private String wxCode;
    private String wxEncryptedData;
    private String wxIv;
    
    private Person person;

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWxCode() {
		return wxCode;
	}

	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}

	public String getWxEncryptedData() {
		return wxEncryptedData;
	}

	public void setWxEncryptedData(String wxEncryptedData) {
		this.wxEncryptedData = wxEncryptedData;
	}

	public String getWxIv() {
		return wxIv;
	}

	public void setWxIv(String wxIv) {
		this.wxIv = wxIv;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
     
    
}
