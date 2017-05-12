package wang.flybird.entity.enums;

/**
 * 布尔类型
 * 
 * @author Kwok
 */
public enum TrueOrFalse {
	/**
	 * 假
	 */
	FALSE("0"),
	/**
	 * 真
	 */
	TRUE("1");

	private String value;

	private TrueOrFalse(String value) {
		this.value = value;
	}

	/**
	 * 根据数据返回审批状态
	 * 
	 * @param value
	 *            字符
	 * @return 审批状态
	 * 
	 */
	public static TrueOrFalse parse(Boolean value) {
		if (value) {
			return TRUE;
		} else {
			return FALSE;
		}
	}

	/**
	 * 根据数据返回审批状态
	 * 
	 * @param value
	 *            字符
	 * @return 审批状态
	 * 
	 */
	public static Boolean parse(String value) {
		if (TRUE.getValue().equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取得压面验证返回的数值
	 * 
	 * @return 字符串
	 */
	public String getValue() {
		return this.value;
	}

}
