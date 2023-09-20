package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingTopicForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainModel;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessModel;
import com.landray.kmss.util.AutoHashMap;


public class KmImeetingTopic extends ExtendAuthModel implements IAttachment, ISysWfMainModel, ISysNotifyModel {

	/*
	 * 议题名称
	 */
	protected String docSubject;

	/*
	 * 议题编号
	 */
	protected String fdNo;
	
	/*
	 * 议题类别
	 */
	protected KmImeetingTopicCategory fdTopicCategory;

	/*
	 * 议题来源
	 */
	protected String fdSourceSubject = null;
	/*
	 * 外模块名称
	 */
	protected String fdModelName = null;
	protected String fdModelId = null;

	/*
	 * 承办单位
	 */
	protected SysOrgElement fdChargeUnit = null;

	/*
	 * 汇报人
	 */
	protected SysOrgPerson fdReporter = null;
	/*
	 * 材料负责人
	 */
	protected SysOrgPerson fdMaterialStaff = null;

	/*
	 * 建议列席单位
	 */
	protected List fdAttendUnit = new ArrayList();
	/*
	 * 建议旁听单位
	 */
	protected List fdListenUnit = new ArrayList();
	
	/*
	 * 上报理由
	 */
	protected String fdContent;
	/*
	 * 最后修改者
	 */
	protected SysOrgElement docAlteror = null;
	/*
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	protected Date docPublishTime;
	/*
	 * 是否上会
	 */
	protected Boolean fdIsAccept = false;

	@Override
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

	public KmImeetingTopicCategory getFdTopicCategory() {
		return fdTopicCategory;
	}

	public void setFdTopicCategory(KmImeetingTopicCategory fdTopicCategory) {
		this.fdTopicCategory = fdTopicCategory;
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

	public SysOrgElement getFdChargeUnit() {
		return fdChargeUnit;
	}

	public void setFdChargeUnit(SysOrgElement fdChargeUnit) {
		this.fdChargeUnit = fdChargeUnit;
	}

	public SysOrgPerson getFdReporter() {
		return fdReporter;
	}

	public void setFdReporter(SysOrgPerson fdReporter) {
		this.fdReporter = fdReporter;
	}

	public SysOrgPerson getFdMaterialStaff() {
		return fdMaterialStaff;
	}

	public void setFdMaterialStaff(SysOrgPerson fdMaterialStaff) {
		this.fdMaterialStaff = fdMaterialStaff;
	}

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

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	public Date getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(Date docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	public Boolean getFdIsAccept() {
		return fdIsAccept;
	}

	public void setFdIsAccept(Boolean fdIsAccept) {
		this.fdIsAccept = fdIsAccept;
	}

	public KmImeetingTopic() {
		super();
	}



	@Override
    public Class getFormClass() {
		return KmImeetingTopicForm.class;
	}
	
	

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdReporter.fdId", "fdReporterId");
			toFormPropertyMap.put("fdReporter.fdName", "fdReporterName");
			toFormPropertyMap.put("fdChargeUnit.fdId", "fdChargeUnitId");
			toFormPropertyMap.put("fdChargeUnit.fdName", "fdChargeUnitName");
			toFormPropertyMap.put("fdMaterialStaff.fdName", "fdMaterialStaffName");
			toFormPropertyMap.put("fdMaterialStaff.fdId", "fdMaterialStaffId");
			toFormPropertyMap.put("fdTopicCategory.fdName", "fdTopicCategoryName");
			toFormPropertyMap.put("fdTopicCategory.fdId", "fdTopicCategoryId");
			toFormPropertyMap.put("fdAttendUnit",
					new ModelConvertor_ModelListToString("fdAttendUnitIds:fdAttendUnitNames", "fdId:fdName"));
			toFormPropertyMap.put("fdListenUnit",
					new ModelConvertor_ModelListToString("fdListenUnitIds:fdListenUnitNames", "fdId:fdName"));

			// -------------- 归档（结束）---------------
		}
		return toFormPropertyMap;
	}

	// =====附件机制(开始)=====
	protected AutoHashMap autoHashMap = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	// =====附件机制(结束)=====

	// ********** 以下的代码为流程需要的代码，请直接拷贝 **********
	private SysWfBusinessModel sysWfBusinessModel = new SysWfBusinessModel();

	@Override
    public SysWfBusinessModel getSysWfBusinessModel() {
		return sysWfBusinessModel;
	}
	// ********** 以上的代码为流程需要的代码，请直接拷贝 **********


}
