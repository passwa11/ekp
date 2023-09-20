package com.landray.kmss.sys.ui.taglib;

import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.sys.ui.model.SysUiConfig;
import com.landray.kmss.util.ResourceUtil;

/**
 * 引入开启合并压缩参数
 * @author lr-linyuchao
 *
 */
public class SysUiCompressTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	@Override
    public int doStartTag() {
		try {
			SysUiConfig settingDefault = new SysUiConfig();
			pageContext.setAttribute("compressSwitch", "true".equals(settingDefault.getCompressSwitch()) ? "true" : "false");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	@Override
    public void release() {
		super.release();
	}

}
