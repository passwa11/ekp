package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataFsscSwitchOnTag extends TagSupport{
	private static final long serialVersionUID = 1L;
	private String property;
	private String defaultValue;

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	@Override
    public int doStartTag()throws JspException{
		if (StringUtil.isNotNull(property)) {
			try {
				String value=EopBasedataFsscUtil.getSwitchValue(property);
				if(StringUtil.isNotNull(defaultValue)&&StringUtil.isNull(value)) {
					return Integer.valueOf(defaultValue);
				}
				return Boolean.valueOf(EopBasedataFsscUtil.getSwitchValue(property))?1:0;
			} catch (NumberFormatException e) {
				return 0;
			} catch (Exception e) {
				return 0;
			}
		}else {
			return 0;
		}
	}
}
