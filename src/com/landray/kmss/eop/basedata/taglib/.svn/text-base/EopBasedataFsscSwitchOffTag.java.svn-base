package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataFsscSwitchOffTag extends TagSupport{
	private static final long serialVersionUID = 1L;
	private String property;

	public void setProperty(String property) {
		this.property = property;
	}
	@Override
    public int doStartTag()throws JspException{
		if (StringUtil.isNotNull(property)) {
			try {
				Integer on = Integer.valueOf(EopBasedataFsscUtil.getSwitchValue(property));
				return on.equals(1)?0:1;
			} catch (NumberFormatException e) {
				return 1;
			} catch (Exception e) {
				return 1;
			}
		}else {
			return 1;
		}
	}
}
