package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingSeatTemplate;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

public class KmImeetingSeatTemplateForm extends ExtendForm {

	private String fdName;// 模版名字

	private String fdSeatDetail;// 坐席明细

	private String fdSeatCount;// 座位数

	private String fdCols;// 列数

	private String fdRows;// 行数

	/**
	 * 模版名字
	 * 
	 * @return
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * 模版名字
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 坐席明细
	 * 
	 * @return
	 */
	public String getFdSeatDetail() {
		return fdSeatDetail;
	}

	/**
	 * 坐席明细
	 */
	public void setFdSeatDetail(String fdSeatDetail) {
		this.fdSeatDetail = fdSeatDetail;
	}

	/**
	 * 座位数
	 * 
	 * @return
	 */
	public String getFdSeatCount() {
		return fdSeatCount;
	}

	/**
	 * 座位数
	 */
	public void setFdSeatCount(String fdSeatCount) {
		this.fdSeatCount = fdSeatCount;
	}

	public String getFdCols() {
		if (StringUtil.isNull(fdCols)) {
			setFdCols("22");
		}
		return fdCols;
	}

	public void setFdCols(String fdCols) {
		this.fdCols = fdCols;
	}

	public String getFdRows() {
		if (StringUtil.isNull(fdRows)) {
			setFdRows("15");
		}
		return fdRows;
	}

	public void setFdRows(String fdRows) {
		this.fdRows = fdRows;
	}

	@Override
	public Class<KmImeetingSeatTemplate> getModelClass() {
		return KmImeetingSeatTemplate.class;
	}

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return formToModelPropertyMap;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdSeatDetail = null;
		fdSeatCount = null;
		fdCols = null;
		fdRows = null;
		super.reset(mapping, request);
	}


}
