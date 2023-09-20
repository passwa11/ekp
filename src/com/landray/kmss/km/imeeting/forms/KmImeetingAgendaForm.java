package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议议程 Form
 */
public class KmImeetingAgendaForm extends ExtendForm {

	/*
	 * 议题编号
	 */
	private String fdNo = null;
	/*
	 * 承办单位
	 */
	private String fdChargeUnitId = null;
	private String fdChargeUnitName = null;

	private String fdExpectStartTime = null;

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	public String getFdChargeUnitId() {
		return fdChargeUnitId;
	}

	public void setFdChargeUnitId(String fdChargeUnitId) {
		this.fdChargeUnitId = fdChargeUnitId;
	}

	public String getFdChargeUnitName() {
		return fdChargeUnitName;
	}

	public void setFdChargeUnitName(String fdChargeUnitName) {
		this.fdChargeUnitName = fdChargeUnitName;
	}

	public String getFdExpectStartTime() {
		return fdExpectStartTime;
	}

	public void setFdExpectStartTime(String fdExpectStartTime) {
		this.fdExpectStartTime = fdExpectStartTime;
	}

	/*
	 * 建议列席单位
	 */
	private String fdAttendUnitIds = null;
	private String fdAttendUnitNames = null;
	/*
	 * 建议旁听单位
	 */
	private String fdListenUnitIds = null;
	private String fdListenUnitNames = null;

	public String getFdAttendUnitIds() {
		return fdAttendUnitIds;
	}

	public void setFdAttendUnitIds(String fdAttendUnitIds) {
		this.fdAttendUnitIds = fdAttendUnitIds;
	}

	public String getFdAttendUnitNames() {
		return fdAttendUnitNames;
	}

	public void setFdAttendUnitNames(String fdAttendUnitNames) {
		this.fdAttendUnitNames = fdAttendUnitNames;
	}

	public String getFdListenUnitIds() {
		return fdListenUnitIds;
	}

	public void setFdListenUnitIds(String fdListenUnitIds) {
		this.fdListenUnitIds = fdListenUnitIds;
	}

	public String getFdListenUnitNames() {
		return fdListenUnitNames;
	}

	public void setFdListenUnitNames(String fdListenUnitNames) {
		this.fdListenUnitNames = fdListenUnitNames;
	}

	/**
	 * 议题库来源id
	 */
	protected String fdFromTopicId = null;

	public String getFdFromTopicId() {
		return fdFromTopicId;
	}

	public void setFdFromTopicId(String fdFromTopicId) {
		this.fdFromTopicId = fdFromTopicId;
	}
	/**
	 * 议程名称
	 */
	protected String docSubject = null;

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
	protected String docReporterTime = null;

	/**
	 * @return 汇报时间(分钟)
	 */
	public String getDocReporterTime() {
		return docReporterTime;
	}

	/**
	 * @param docReporterTime
	 *            汇报时间(分钟)
	 */
	public void setDocReporterTime(String docReporterTime) {
		this.docReporterTime = docReporterTime;
	}

	/**
	 * 上会材料
	 */
	protected String attachmentName = null;

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
	protected String attachmentId = null;

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
	 * 材料提交时间
	 */
	protected String attachmentSubmitTime = null;

	/**
	 * @return 材料提交时间
	 */
	public String getAttachmentSubmitTime() {
		return attachmentSubmitTime;
	}

	/**
	 * @param attachmentSubmitTime
	 *            材料提交时间
	 */
	public void setAttachmentSubmitTime(String attachmentSubmitTime) {
		this.attachmentSubmitTime = attachmentSubmitTime;
	}

	/**
	 * 是否公开
	 */
	protected String fdIsPublic = null;

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
	 * 所属会议安排的ID
	 */
	protected String fdMainId = null;

	/**
	 * @return 所属会议安排的ID
	 */
	public String getFdMainId() {
		return fdMainId;
	}

	/**
	 * @param fdMainId
	 *            所属会议安排的ID
	 */
	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
	}

	/**
	 * 所属会议安排的名称
	 */
	protected String fdMainName = null;

	/**
	 * @return 所属会议安排的名称
	 */
	public String getFdMainName() {
		return fdMainName;
	}

	/**
	 * @param fdMainName
	 *            所属会议安排的名称
	 */
	public void setFdMainName(String fdMainName) {
		this.fdMainName = fdMainName;
	}

	/**
	 * 汇报人的ID
	 */
	protected String docReporterId = null;

	/**
	 * @return 汇报人的ID
	 */
	public String getDocReporterId() {
		return docReporterId;
	}

	/**
	 * @param docReporterId
	 *            汇报人的ID
	 */
	public void setDocReporterId(String docReporterId) {
		this.docReporterId = docReporterId;
	}

	/**
	 * 汇报人的名称
	 */
	protected String docReporterName = null;

	/**
	 * @return 汇报人的名称
	 */
	public String getDocReporterName() {
		return docReporterName;
	}

	/**
	 * @param docReporterName
	 *            汇报人的名称
	 */
	public void setDocReporterName(String docReporterName) {
		this.docReporterName = docReporterName;
	}

	/**
	 * 材料责任人的ID
	 */
	protected String docResponsId = null;

	/**
	 * @return 材料责任人的ID
	 */
	public String getDocResponsId() {
		return docResponsId;
	}

	/**
	 * @param docResponsId
	 *            材料责任人的ID
	 */
	public void setDocResponsId(String docResponsId) {
		this.docResponsId = docResponsId;
	}

	/**
	 * 材料责任人的名称
	 */
	protected String docResponsName = null;

	/**
	 * @return 材料责任人的名称
	 */
	public String getDocResponsName() {
		return docResponsName;
	}

	/**
	 * @param docResponsName
	 *            材料责任人的名称
	 */
	public void setDocResponsName(String docResponsName) {
		this.docResponsName = docResponsName;
	}

	private String fdBallotResult;// 表决结果

	public String getFdBallotResult() {
		return fdBallotResult;
	}

	public void setFdBallotResult(String fdBallotResult) {
		this.fdBallotResult = fdBallotResult;
	}

	/*
	 * 附件相关
	 */
	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		docReporterTime = null;
		attachmentName = null;
		attachmentId = null;
		attachmentSubmitTime = null;
		fdIsPublic = null;
		fdMainId = null;
		fdMainName = null;
		fdChargeUnitId = null;
		fdChargeUnitName = null;
		docReporterId = null;
		docReporterName = null;
		docResponsId = null;
		docResponsName = null;
		fdExpectStartTime = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingAgenda.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdMainId", new FormConvertor_IDToModel(
					"fdMain", KmImeetingMain.class));
			toModelPropertyMap.put("fdChargeUnitId", new FormConvertor_IDToModel("fdChargeUnit", SysOrgElement.class));
			toModelPropertyMap.put("docReporterId",
					new FormConvertor_IDToModel("docReporter",
							SysOrgElement.class));
			toModelPropertyMap.put("docResponsId", new FormConvertor_IDToModel(
					"docRespons", SysOrgElement.class));
			toModelPropertyMap.put("fdAttendUnitIds",
					new FormConvertor_IDsToModelList("fdAttendUnit", KmImissiveUnit.class));
			toModelPropertyMap.put("fdListenUnitIds",
					new FormConvertor_IDsToModelList("fdListenUnit", KmImissiveUnit.class));
		}
		return toModelPropertyMap;
	}
}
