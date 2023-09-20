package com.landray.kmss.common.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.module.core.register.loader.ModuleDictUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 基于BaseForm扩展的类，建议大部分的Form继承。<br>
 * 该类强行定义了fdId这个域。
 * 
 * @author 叶中奇
 * @version 1.0 2006-4-3
 */
public abstract class ExtendForm extends BaseForm implements IExtendForm {
	private static final long serialVersionUID = 1127382361269562728L;

	protected String fdId;

	@Override
    public String getFdId() {
		if (fdId == null) {
			fdId = IDGenerator.generateID();
		}
		return fdId;
	}

	@Override
    public void setFdId(String id) {
		this.fdId = id;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		return new FormToModelPropertyMap();
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdId = IDGenerator.generateID();
		dynamicMap.clear();
		ModuleDictUtil.formReset(this, mechanismMap);
		getCustomPropMap().clear();
		super.reset(mapping, request);
	}
	
	private Map<String, String> dynamicMap = new HashMap<String, String>();

	@Override
    public Map<String, String> getDynamicMap() {
		return dynamicMap;
	}

	public void setDynamicMap(Map<String, String> dynamicMap) {
		this.dynamicMap = dynamicMap;
	}

	/**
	 * 机制类
	 */
	private Map<String, Object> mechanismMap = new HashMap<>();

	@Override
    public Map<String, Object> getMechanismMap() {
		return mechanismMap;
	}

	@Override
    public void setMechanismMap(Map<String, Object> mechanismMap) {
		this.mechanismMap = mechanismMap;
	}

}
