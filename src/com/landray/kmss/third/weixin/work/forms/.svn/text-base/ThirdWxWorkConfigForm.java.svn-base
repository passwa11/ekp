package com.landray.kmss.third.weixin.work.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.weixin.work.model.ThirdWxWorkConfig;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 企业微信配置
  */
public class ThirdWxWorkConfigForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String fdKey;

	private String fdField;

	private String fdValue;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdKey = null;
		fdField = null;
		fdValue = null;
		dataMap.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class<ThirdWxWorkConfig> getModelClass() {
		return ThirdWxWorkConfig.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	/**
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 微信企业标识
	 */
	public String getFdKey() {
		return this.fdKey;
	}

	/**
	 * 微信企业标识
	 */
	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	/**
	 * 字段名
	 */
	public String getFdField() {
		return this.fdField;
	}

	/**
	 * 字段名
	 */
	public void setFdField(String fdField) {
		this.fdField = fdField;
	}

	/**
	 * 字段值
	 */
	public String getFdFdValue() {
		return this.fdValue;
	}

	/**
	 * 字段值
	 */
	public void setFdValue(String fdValue) {
		this.fdValue = fdValue;
	}

	private Map dataMap = new HashMap();

	public Map getMap() {
		return dataMap;
	}

	public void setMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	public Object getValue(String key) {
		return dataMap.get(key);
	}

	public void setValue(String key, Object value) {
		dataMap.put(key, value);
	}
}
