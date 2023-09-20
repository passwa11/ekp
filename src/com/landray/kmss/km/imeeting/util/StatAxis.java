package com.landray.kmss.km.imeeting.util;


/**
 * 统计坐标信息
 */
public class StatAxis {

	// 坐标轴名称
	private String name;

	// 坐标轴类型，横轴默认为类目型'category'，纵轴默认为数值型'value'
	private String type;

	// 类目列表，同时也是label内容
	private String data;

	public StatAxis() {

	}

	public StatAxis(String name, String type, String data) {
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
