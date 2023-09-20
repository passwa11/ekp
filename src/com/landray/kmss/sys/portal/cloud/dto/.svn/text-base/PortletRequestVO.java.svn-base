package com.landray.kmss.sys.portal.cloud.dto;

import java.util.Map;

/**
 * 参见cloud中的com.landray.sys.portal.dto.extension.PortletRequestVO
 * 
 * @author chao
 *
 */
public class PortletRequestVO {
	/**
	 * 请求url，去除后面参数的，如：!{cateid}
	 */
	private String url;
	/**
	 * 请求方法 get\post，ekp一般是get
	 */
	private String method = "get";
	/**
	 * 数据格式 json、text、xml、static、jsonp
	 */
	private String dataType;
	/**
	 * 数据转换器
	 */
	private String transform;
	/**
	 * post 请求参数列表
	 */
	private Map<String, Object> data;
	/**
	 * get 请求参数列表
	 */
	private Map<String, Object> params;
	/**
	 * 静态数据源，type=static 时 有效
	 */
	private Map<String, Object> staticData;
	/**
	 * 样例数据
	 */
	private String example;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Map<String, Object> getStaticData() {
		return staticData;
	}

	public void setStaticData(Map<String, Object> staticData) {
		this.staticData = staticData;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}
}
