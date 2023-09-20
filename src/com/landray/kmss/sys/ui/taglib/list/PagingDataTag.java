package com.landray.kmss.sys.ui.taglib.list;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.sunbor.web.tag.Page;

@SuppressWarnings("serial")
public class PagingDataTag extends BaseTag {

	protected String currentPage;
	protected String pageSize;
	protected String totalSize;
	protected Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public void release() {
		page = null;
		currentPage = null;
		pageSize = null;
		totalSize = null;
		super.release();
	}

	private void pageToJson(JSONObject json) {
		if (page != null) {
			json.element("currentPage", page.getPageno());
			json.element("pageSize", page.getRowsize());
			json.element("totalSize", page.getTotalrows());
		}
	}

	public JSONObject bulidJsonDatas() {
		JSONObject json = new JSONObject();
		this.pageToJson(json);
		if (currentPage != null) {
            json.element("currentPage", currentPage);
        }
		if (pageSize != null) {
            json.element("pageSize", pageSize);
        }
		if (totalSize != null) {
            json.element("totalSize", totalSize);
        }
		return json;
	}

	@Override
	public int doEndTag() throws JspException {

		try {
			Tag parent = getParent();
			if (parent instanceof DataTag) {
                ((DataTag) parent).setPage(bulidJsonDatas());
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
