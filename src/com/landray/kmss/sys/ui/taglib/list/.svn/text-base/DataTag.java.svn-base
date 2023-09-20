package com.landray.kmss.sys.ui.taglib.list;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.StringUtil;

public class DataTag extends BaseTag {

	public JSONObject datas;

	public JSONObject page;

	public JSONObject getPage() {
		return page;
	}

	public void setPage(JSONObject page) {
		this.page = page;
	}

	public JSONObject getDatas() {
		return datas;
	}

	public void setDatas(JSONObject datas) {
		this.datas = datas;
	}

	@Override
	public void release() {
		datas = null;
		page = null;
		super.release();
	}

	public JSONObject bulidJsonDatas() {
		if (page != null && datas != null) {
			datas.element("page", page);
		}
		return datas;
	}

	@Override
    public int doEndTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			String jsonpcallback = request.getParameter("jsonpcallback");
			String body = bulidJsonDatas().toString();
			if(StringUtil.isNotNull(jsonpcallback)){
				pageContext.getOut().append(jsonpcallback+"("+body+")");
			}else{
				pageContext.getOut().append(body);
			}
		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

}
