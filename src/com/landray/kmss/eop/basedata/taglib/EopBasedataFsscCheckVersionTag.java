package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;


public class EopBasedataFsscCheckVersionTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1126345L;
	private String version;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
    public int doStartTag()throws JspException{
		try {
			if(EopBasedataFsscUtil.checkVersion(version)){
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

}
