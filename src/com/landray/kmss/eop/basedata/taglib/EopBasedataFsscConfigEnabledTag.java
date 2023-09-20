package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;

public class EopBasedataFsscConfigEnabledTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1126345L;
	private String property;
	private Object value;
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
    public int doStartTag()throws JspException{
		try {
			String items = EopBasedataFsscUtil.getSwitchValue(property);
			if(items==null){
				items = "";
			}
			items = ";"+items+";";
			String __value = (String) (value==null?"":value);
			__value = ";"+__value+";";
			if(items.indexOf(__value)!=-1){
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

}
