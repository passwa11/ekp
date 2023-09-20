package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingSeatPlanForm;

/**
 * 坐席安排
 * 
 * @author ADai
 *
 */
public class KmImeetingSeatPlan extends BaseModel {

	private KmImeetingMain fdImeetingMain;// 会议主文档

	private Boolean fdIsTopicPlan;// 是否开启议题排位

	private String fdSeatDetail;// 排位明细

	private String fdCols;// 列

	private String fdRows;// 行

	/**
	 * 会议主文档
	 */
	public KmImeetingMain getFdImeetingMain() {
		return fdImeetingMain;
	}
	/**
	 * 会议主文档
	 */
	public void setFdImeetingMain(KmImeetingMain fdImeetingMain) {
		this.fdImeetingMain = fdImeetingMain;
	}

	/**
	 * 是否开启议题排位
	 */
	public Boolean getFdIsTopicPlan() {
		return fdIsTopicPlan;
	}

	/**
	 * 是否开启议题排位
	 */
	public void setFdIsTopicPlan(Boolean fdIsTopicPlan) {
		this.fdIsTopicPlan = fdIsTopicPlan;
	}

	/**
	 * 排位明细
	 */
	public String getFdSeatDetail() {
		return (String) readLazyField("fdSeatDetail", fdSeatDetail);
	}

	/**
	 * 排位明细
	 */
	public void setFdSeatDetail(String fdSeatDetail) {
		this.fdSeatDetail = (String) writeLazyField("fdSeatDetail",
				this.fdSeatDetail, fdSeatDetail);
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
	public Class<KmImeetingSeatPlanForm> getFormClass() {
		return KmImeetingSeatPlanForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdImeetingMain.fdId", "fdImeetingMainId");
		}
		return toFormPropertyMap;
	}

}
