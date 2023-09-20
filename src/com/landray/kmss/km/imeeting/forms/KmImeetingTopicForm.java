package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.model.KmImeetingTopic;
import com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;


public class KmImeetingTopicForm extends ExtendAuthForm implements
		IAttachmentForm, ISysWfMainForm {

	/*
	 * 议题名称
	 */
	private String docSubject = null;

	/*
	 * 议题编号
	 */
	private String fdNo = null;

	/*
	 * 议题类别
	 */
	private String fdTopicCategoryId = null;
	private String fdTopicCategoryName = null;

	/*
	 * 议题来源
	 */
	private String fdSourceSubject = null;
	/*
	 * 外模块名称
	 */
	private String fdModelName = null;
	private String fdModelId = null;

	/*
	 * 承办单位
	 */
	private String fdChargeUnitId = null;
	private String fdChargeUnitName = null;

	/*
	 * 汇报人
	 */
	private String fdReporterId = null;
	private String fdReporterName = null;
	/*
	 * 材料负责人
	 */
	private String fdMaterialStaffId = null;
	private String fdMaterialStaffName = null;

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

	/*
	 * 上报理由
	 */
	private String fdContent;
	/*
	 * 最后修改者
	 */
	private String docAlterorId = null;
	private String docAlterorName = null;
	/*
	 * 最后修改时间
	 */
	private String docAlterTime = null;
	/*
	 * 最后修改者
	 */
	private String docCreatorId = null;
	private String docCreatorName = null;
	private String docCreateTime = null;


	private String docPublishTime = null;

	/*
	 * 是否允许继续传阅
	 */
	protected String fdIsAccept = null;

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	public String getFdTopicCategoryId() {
		return fdTopicCategoryId;
	}

	public void setFdTopicCategoryId(String fdTopicCategoryId) {
		this.fdTopicCategoryId = fdTopicCategoryId;
	}

	public String getFdTopicCategoryName() {
		return fdTopicCategoryName;
	}

	public void setFdTopicCategoryName(String fdTopicCategoryName) {
		this.fdTopicCategoryName = fdTopicCategoryName;
	}

	public String getFdSourceSubject() {
		return fdSourceSubject;
	}

	public void setFdSourceSubject(String fdSourceSubject) {
		this.fdSourceSubject = fdSourceSubject;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
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

	public String getFdReporterId() {
		return fdReporterId;
	}

	public void setFdReporterId(String fdReporterId) {
		this.fdReporterId = fdReporterId;
	}

	public String getFdReporterName() {
		return fdReporterName;
	}

	public void setFdReporterName(String fdReporterName) {
		this.fdReporterName = fdReporterName;
	}

	public String getFdMaterialStaffId() {
		return fdMaterialStaffId;
	}

	public void setFdMaterialStaffId(String fdMaterialStaffId) {
		this.fdMaterialStaffId = fdMaterialStaffId;
	}

	public String getFdMaterialStaffName() {
		return fdMaterialStaffName;
	}

	public void setFdMaterialStaffName(String fdMaterialStaffName) {
		this.fdMaterialStaffName = fdMaterialStaffName;
	}

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

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	

	public String getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}


	public String getFdIsAccept() {
		return fdIsAccept;
	}

	public void setFdIsAccept(String fdIsAccept) {
		this.fdIsAccept = fdIsAccept;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		fdNo = null;
		fdTopicCategoryId = null;
		fdTopicCategoryName = null;
		fdSourceSubject = null;
		fdModelName = null;
		fdModelId = null;
		fdChargeUnitId = null;
		fdChargeUnitName = null;
		fdReporterId = null;
		fdReporterName = null;
		fdMaterialStaffId = null;
		fdMaterialStaffName = null;
		fdAttendUnitIds = null;
		fdAttendUnitNames = null;
		fdListenUnitIds = null;
		fdListenUnitNames = null;
		fdContent = null;
		docAlterorId = null;
		docAlterorName = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterTime = null;
		docCreateTime = null;
		docPublishTime = null;
		fdIsAccept = null;

		autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
		sysWfBusinessForm = new SysWfBusinessForm();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingTopic.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel("docAlteror", SysOrgPerson.class));
			toModelPropertyMap.put("fdReporterId", new FormConvertor_IDToModel("fdReporter", SysOrgPerson.class));
			toModelPropertyMap.put("fdChargeUnitId", new FormConvertor_IDToModel("fdChargeUnit", SysOrgElement.class));
			toModelPropertyMap.put("fdMaterialStaffId",
					new FormConvertor_IDToModel("fdMaterialStaff", SysOrgPerson.class));
			toModelPropertyMap.put("fdTopicCategoryId",
					new FormConvertor_IDToModel("fdTopicCategory", KmImeetingTopicCategory.class));
			toModelPropertyMap.put("fdAttendUnitIds",
					new FormConvertor_IDsToModelList("fdAttendUnit", KmImissiveUnit.class));
			toModelPropertyMap.put("fdListenUnitIds",
					new FormConvertor_IDsToModelList("fdListenUnit", KmImissiveUnit.class));
		}
		return toModelPropertyMap;
	}


	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	// ********** 以下的代码为流程需要的代码，请直接拷贝 **********
	private SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

	@Override
    public SysWfBusinessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	// ********** 以上的代码为流程需要的代码，请直接拷贝 **********

}
