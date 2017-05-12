package wang.flybird.entity.enums;

/**
 * 删除状态位
 * 
 * @author LiXueYi
 *
 */
public enum DelFlag {
	/**
	 * 已删除
	 */
	YES("1", true),
	/**
	 * 未删除
	 */
	NO("0", false);

	private String value;
	private boolean boolValue;

	private DelFlag(String value, boolean boolValue) {
		this.value = value;
		this.boolValue = boolValue;
	}

	public String getValue() {
		return this.value;
	}

	public boolean getBoolValue() {
		return boolValue;
	}

	public static DelFlag parse(String value) {
		DelFlag result = null;
		if (YES.value.equals(value)) {
			result = DelFlag.YES;
		} else if (NO.value.equals(value)) {
			result = DelFlag.NO;
		}
		return result;
	}

	public static DelFlag parse(boolean value) {
		DelFlag result = null;
		if (value) {
			result = DelFlag.YES;
		} else {
			result = DelFlag.NO;
		}
		return result;
	}
}
