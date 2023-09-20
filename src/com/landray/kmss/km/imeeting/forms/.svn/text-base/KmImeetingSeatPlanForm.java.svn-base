package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingSeatPlan;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 坐席安排form
 * 
 * @author ADai
 *
 */
public class KmImeetingSeatPlanForm extends ExtendForm {

	private String docSubject;

	private String fdImeetingMainId;// 会议主文档id

	private String fdIsTopicPlan;// 是否开启议题排位

	private String fdSeatDetail;// 排位明细

	private String fdSeatCount;// 座位数

	private String fdTemplateSeatDetail;// 模版座位明细

	private String fdCols;// 列数

	private String fdRows;// 行数

	private String fdHasTemplateDetail;// 是否进行座席设置

	private String fdIsShowTopic;// 是否显示按议题排位

	private Integer fdTopicSize;// 议题数量

	public Integer getFdTopicSize() {
		return fdTopicSize;
	}

	public void setFdTopicSize(Integer fdTopicSize) {
		this.fdTopicSize = fdTopicSize;
	}

	/**
	 * 是否显示按议题排位按钮
	 * 
	 */
	public String getFdIsShowTopic() {
		return fdIsShowTopic;
	}

	/**
	 * 是否显示按议题排位按钮
	 * 
	 */
	public void setFdIsShowTopic(String fdIsShowTopic) {
		this.fdIsShowTopic = fdIsShowTopic;
	}

	/**
	 * 会议室是否进行座席设置
	 * 
	 */
	public String getFdHasTemplateDetail() {
		return fdHasTemplateDetail;
	}

	/**
	 * 会议室是否进行座席设置
	 * 
	 */
	public void setFdHasTemplateDetail(String fdHasTemplateDetail) {
		this.fdHasTemplateDetail = fdHasTemplateDetail;
	}

	@Override
	public Class<KmImeetingSeatPlan> getModelClass() {
		return KmImeetingSeatPlan.class;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 会议主文档id
	 */
	public String getFdImeetingMainId() {
		return fdImeetingMainId;
	}

	/**
	 * 会议主文档id
	 */
	public void setFdImeetingMainId(String fdImeetingMainId) {
		this.fdImeetingMainId = fdImeetingMainId;
	}

	/**
	 * 是否开启议题排位
	 */
	public String getFdIsTopicPlan() {
		if (fdIsTopicPlan == null) {
			setFdIsTopicPlan("false");
		}
		return fdIsTopicPlan;
	}

	/**
	 * 是否开启议题排位
	 */
	public void setFdIsTopicPlan(String fdIsTopicPlan) {
		this.fdIsTopicPlan = fdIsTopicPlan;
	}

	/**
	 * 排位明细
	 */
	public String getFdSeatDetail() {
		return fdSeatDetail;
	}
	/**
	 * 排位明细
	 */
	public void setFdSeatDetail(String fdSeatDetail) {
		this.fdSeatDetail = fdSeatDetail;
	}

	public String getFdTemplateSeatDetail() {
		return fdTemplateSeatDetail;
	}

	public void setFdTemplateSeatDetail(String fdTemplateSeatDetail) {
		this.fdTemplateSeatDetail = fdTemplateSeatDetail;
	}

	public String getFdSeatCount() {
		return fdSeatCount;
	}

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

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			formToModelPropertyMap.put("fdImeetingMainId",
					new FormConvertor_IDToModel(
							"fdImeetingMain", KmImeetingMain.class));
		}

		return formToModelPropertyMap;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdImeetingMainId = null;
		fdIsTopicPlan = null;
		fdSeatDetail = null;
		fdCols = null;
		fdRows = null;
		docSubject = null;
		super.reset(mapping, request);
	}

}
