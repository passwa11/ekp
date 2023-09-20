package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingSeatTemplateForm;

/**
 * 坐席模版
 * 
 * @author ADai
 *
 */
public class KmImeetingSeatTemplate extends BaseModel {

	private String fdName;// 模版名字

	private String fdSeatDetail;// 坐席明细

	private String fdSeatCount;// 座位数

	private String fdCols;// 列

	private String fdRows;// 行

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
		return (String) readLazyField("fdSeatDetail", fdSeatDetail);
	}
	/**
	 * 坐席明细
	 */
	public void setFdSeatDetail(String fdSeatDetail) {
		this.fdSeatDetail = (String) writeLazyField("fdSeatDetail",
				this.fdSeatDetail, fdSeatDetail);
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
		return fdCols;
	}

	public void setFdCols(String fdCols) {
		this.fdCols = fdCols;
	}

	public String getFdRows() {
		return fdRows;
	}

	public void setFdRows(String fdRows) {
		this.fdRows = fdRows;
	}

	@Override
	public Class<KmImeetingSeatTemplateForm> getFormClass() {
		return KmImeetingSeatTemplateForm.class;
	}

}
