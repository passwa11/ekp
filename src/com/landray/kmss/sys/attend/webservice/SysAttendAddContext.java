package com.landray.kmss.sys.attend.webservice;


/**
 * @author linxiuxian
 *
 */
public class SysAttendAddContext {

	/**
	 * 来源 说明: 不允许为空
	 */
	private String appName;

	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 考勤记录数组
	 */
	private String datas;



	/**
	 * 扩展参数，用于以后参数的扩展。 json数据,格式如：{key1:value1,key2:value2}
	 */
	private String others;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}
}
