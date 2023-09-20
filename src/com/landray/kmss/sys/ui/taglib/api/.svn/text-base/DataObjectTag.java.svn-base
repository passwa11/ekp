package com.landray.kmss.sys.ui.taglib.api;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表一个数据对象子项如基本数据子项: base
 *
 */
@SuppressWarnings("serial")
public class DataObjectTag extends BaseTag {

	protected String name;

	protected JSONObject dataObject;
	
	@Override
	public void release() {
		name = null;
		dataObject = null;
		
		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		if (name != null) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}


	@Override
	public int doEndTag() throws JspException {

		try {
			Tag parent = getParent();
			if (parent == null) {
				parent = (Tag) pageContext.getRequest().getAttribute(
						ResponseConstant.PAGE_ATTRIBUTE_KEY_RESPONSE_TAG);
			}
			if (parent instanceof ResponseTag && name != null
					&& dataObject != null) {
				((ResponseTag) parent).addDataObject(name, dataObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getDataObject() {
		return dataObject;
	}

	public void putDataObject(JSONObject putObject) {
		if (putObject != null) {
			if (dataObject == null) {
				dataObject = new JSONObject();
			}
			dataObject.putAll(putObject);
		}
	}

	public void addOrUpdateDataObjectProperty(String propertyName,
			Map<String, Object> valuesMap) {

		if (propertyName == null || valuesMap == null || valuesMap.isEmpty()) {
			return;
		}
		if (dataObject == null) {
			dataObject = new JSONObject();
		}

		Object obj = dataObject.get(propertyName);
		if(obj!=null){
			
			if(obj instanceof String){ //解决最后一级为string的问题
				JSONObject property = new JSONObject();
				dataObject.put(propertyName, property);
				
				property.putAll(valuesMap);
			}else if(obj instanceof JSONObject){
				JSONObject property = (JSONObject)obj;
			}
			
		}else{

			JSONObject property = new JSONObject();
			dataObject.put(propertyName, property);
			
			property.putAll(valuesMap);
		}
			
	}
}
