package com.landray.kmss.km.review.restservice.dto;

public class SysDictExtendPropertyDTO {
	/*
	 * 显示名
	 */
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * 属性名，如：docSubject
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * 数据库列名，hibernate继承过来的属性
	 */
	private String column;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	/*
	 * 类型 若为简单类型，则型如：String、Integer、RTF、Date、Time、DateTime等 若为对象类型，则为对象的类名
	 * 若为列表类型，则为列表元素对象的类名
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * 是否允许为空
	 */
	private boolean notNull = false;

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	/*
	 * 是否一个可以显示的字段
	 */
	private boolean canDisplay = true;

	public boolean isCanDisplay() {
		return canDisplay;
	}

	public void setCanDisplay(boolean canDisplay) {
		this.canDisplay = canDisplay;
	}

	/*
	 * 是否只读
	 */
	private boolean readOnly = false;

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/*
	 * 枚举类型，为空则该属性不是枚举值
	 */
	private String enumType;

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	/*
	 * 枚举值列表，用于表单自定义的扩充，跟enumType的区别在于enumValues为用户自定义，enumType为程序定义
	 */
	private String enumValues;

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	// 业务类型 ： 自定义表单 的控件类型
	private String businessType;

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
}
