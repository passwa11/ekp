package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingAgendaForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;

/**
 * 会议议程
 */
public class KmImeetingAgenda extends BaseModel implements ISysQuartzModel,
		ISysNotifyModel {

	/*
	 * 议题编码
	 */
	protected String fdNo;
	/*
	 * 承办单位
	 */
	protected SysOrgElement fdChargeUnit;
	/*
	 * 预计开始时间
	 */
	protected Date fdExpectStartTime;

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	public SysOrgElement getFdChargeUnit() {
		return fdChargeUnit;
	}

	public void setFdChargeUnit(SysOrgElement fdChargeUnit) {
		this.fdChargeUnit = fdChargeUnit;
	}

	public Date getFdExpectStartTime() {
		return fdExpectStartTime;
	}

	public void setFdExpectStartTime(Date fdExpectStartTime) {
		this.fdExpectStartTime = fdExpectStartTime;
	}

	/*
	 * 建议列席单位
	 */
	protected List fdAttendUnit = new ArrayList();
	/*
	 * 建议旁听单位
	 */
	protected List fdListenUnit = new ArrayList();

	public List getFdAttendUnit() {
		return fdAttendUnit;
	}

	public void setFdAttendUnit(List fdAttendUnit) {
		this.fdAttendUnit = fdAttendUnit;
	}

	public List getFdListenUnit() {
		return fdListenUnit;
	}

	public void setFdListenUnit(List fdListenUnit) {
		this.fdListenUnit = fdListenUnit;
	}

	/**
	 * 议题库来源
	 */
	protected String fdFromTopicId;

	public String getFdFromTopicId() {
		return fdFromTopicId;
	}

	public void setFdFromTopicId(String fdFromTopicId) {
		this.fdFromTopicId = fdFromTopicId;
	}

	/**
	 * 议程名称
	 */
	protected String docSubject;

	/**
	 * @return 议程名称
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            议程名称
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 汇报时间(分钟)
	 */
	protected Integer docReporterTime;

	/**
	 * @return 汇报时间(分钟)
	 */
	public Integer getDocReporterTime() {
		return docReporterTime;
	}

	/**
	 * @param docReporterTime
	 *            汇报时间(分钟)
	 */
	public void setDocReporterTime(Integer docReporterTime) {
		this.docReporterTime = docReporterTime;
	}

	/**
	 * 上会材料
	 */
	protected String attachmentName;

	/**
	 * @return 上会材料
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName
	 *            上会材料
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * 上会材料Ids
	 */
	protected String attachmentId;

	/**
	 * @return 上会材料Ids
	 */
	public String getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId
	 *            上会材料Ids
	 */
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * 材料提交时间(提前X天)
	 */
	protected Integer attachmentSubmitTime;

	/**
	 * @return 材料提交时间(提前X天)
	 */
	public Integer getAttachmentSubmitTime() {
		return attachmentSubmitTime;
	}

	/**
	 * @param attachmentSubmitTime
	 *            材料提交时间(提前X天)
	 */
	public void setAttachmentSubmitTime(Integer attachmentSubmitTime) {
		this.attachmentSubmitTime = attachmentSubmitTime;
	}

	/**
	 * 是否公开
	 */
	protected String fdIsPublic;

	/**
	 * @return 是否公开
	 */
	public String getFdIsPublic() {
		return fdIsPublic;
	}

	/**
	 * @param fdIsPublic
	 *            是否公开
	 */
	public void setFdIsPublic(String fdIsPublic) {
		this.fdIsPublic = fdIsPublic;
	}

	/**
	 * 所属会议安排
	 */
	protected KmImeetingMain fdMain;

	/**
	 * @return 所属会议安排
	 */
	public KmImeetingMain getFdMain() {
		return fdMain;
	}

	/**
	 * @param fdMain
	 *            所属会议安排
	 */
	public void setFdMain(KmImeetingMain fdMain) {
		this.fdMain = fdMain;
	}

	/**
	 * 汇报人
	 */
	protected SysOrgElement docReporter;

	/**
	 * @return 汇报人
	 */
	public SysOrgElement getDocReporter() {
		return docReporter;
	}

	/**
	 * @param docReporter
	 *            汇报人
	 */
	public void setDocReporter(SysOrgElement docReporter) {
		this.docReporter = docReporter;
	}

	/**
	 * 材料责任人
	 */
	protected SysOrgElement docRespons;

	/**
	 * @return 材料责任人
	 */
	public SysOrgElement getDocRespons() {
		return docRespons;
	}

	/**
	 * @param docRespons
	 *            材料责任人
	 */
	public void setDocRespons(SysOrgElement docRespons) {
		this.docRespons = docRespons;
	}

	private String fdBallotResult;// 表决结果

	public String getFdBallotResult() {
		return (String) readLazyField("fdBallotResult",
				fdBallotResult);
	}

	public void setFdBallotResult(String fdBallotResult) {
		this.fdBallotResult = (String) writeLazyField(
				"fdBallotResult", this.fdBallotResult,
				fdBallotResult);
	}

	@Override
    public Class getFormClass() {
		return KmImeetingAgendaForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMain.fdId", "fdMainId");
			toFormPropertyMap.put("fdMain.fdName", "fdMainName");
			toFormPropertyMap.put("fdChargeUnit.fdId", "fdChargeUnitId");
			toFormPropertyMap.put("fdChargeUnit.fdName", "fdChargeUnitName");
			toFormPropertyMap.put("docReporter.fdId", "docReporterId");
			toFormPropertyMap.put("docReporter.fdName", "docReporterName");
			toFormPropertyMap.put("docRespons.fdId", "docResponsId");
			toFormPropertyMap.put("docRespons.fdName", "docResponsName");
			toFormPropertyMap.put("fdAttendUnit",
					new ModelConvertor_ModelListToString("fdAttendUnitIds:fdAttendUnitNames", "fdId:fdName"));
			toFormPropertyMap.put("fdListenUnit",
					new ModelConvertor_ModelListToString("fdListenUnitIds:fdListenUnitNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
