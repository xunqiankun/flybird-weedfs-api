package wang.flybird.entity.custom;

import java.util.List;

/**
 * 微信公众账号(微信小程序)用户信息
 * 
 * @author LiQi
 *
 */
public class WXAppUserInfo {

	/**
	 * 用户的唯一标识
	 */
	private String openid;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户的性别，1：男性，2：女性，0:未知
	 */
	private String gender;

	/**
	 * 用户个人资料填写的省份
	 */
	private String province;

	/**
	 * 普通用户个人资料填写的城市
	 */
	private String city;

	/**
	 * 国家，如中国为CN
	 */
	private String country;

	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640x640正方形头像），用户没有头像时该项为空。
	 * 若用户更换头像，原有头像URL将失效
	 */
	private String avatarUrl;

	/**
	 * 用户特权信息，json数组，如微信沃卡用户为(chinaunicom)
	 */
	private List<String> privilege;

	/**
	 * 只有在用户将公众账号绑定到微信开放平台账号后，才会出现该字段。
	 */
	private String unionId;

	private String errcode;

	private String errmsg;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
