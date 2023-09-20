package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataFsscShowModelNameTag extends TagSupport{
	private static final long serialVersionUID = 10228289321L;
	private String modelName;

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Override
    public int doStartTag()throws JspException{
		if (StringUtil.isNotNull(modelName)) {
			try {
				SysDictModel model = SysDataDict.getInstance().getModel(modelName);
				String[] message = model.getMessageKey().split(":");
				pageContext.getOut().write(ResourceUtil.getString(message[1],message[0]));
				return 0;
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
