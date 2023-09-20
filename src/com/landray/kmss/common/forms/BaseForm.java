package com.landray.kmss.common.forms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.landray.kmss.web.action.ActionForm;

/**
 * Form基类，不建议直接继承，仅当ExtendForm完全无法满足实际业务需求时才继承该类。
 * 
 * @author 叶中奇
 * @version 1.0 2006-4-3
 */
public class BaseForm extends ActionForm implements Serializable {
	private static final long serialVersionUID = -182612138979882578L;

	@JsonIgnore
	protected String method;

	@JsonIgnore
	protected String method_GET;

	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
    public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @return 最后一次往服务器提交GET的Method
	 */
	public String getMethod_GET() {
		return method_GET;
	}

	public void setMethod_GET(String method_GET) {
		this.method_GET = method_GET;
	}

	/**
	 * @return 最后一次发往服务器请求的Method
	 */
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 动态属性
	 */
	private Map<String, String> customPropMap;

	public Map<String, String> getCustomPropMap() {
		if (customPropMap == null) {
			customPropMap = new HashMap<String, String>();
		}
		return customPropMap;
	}

	public void setCustomPropMap(Map<String, String> customPropMap) {
		this.customPropMap = customPropMap;
	}

	public void addCustomPropMap(String key, String value) {
		getCustomPropMap().put(key, value);
	}
	
	/**
	 * 获取字段-字段描述的映射关系,主要用于记录操作日志时，翻译字段名
	 * @return
	 */
	public Map<String,String> getFieldMessageKeyMap(){
        Map<String,String> fieldMessageMap = new HashMap<String,String>();
        return fieldMessageMap;
    }
}
