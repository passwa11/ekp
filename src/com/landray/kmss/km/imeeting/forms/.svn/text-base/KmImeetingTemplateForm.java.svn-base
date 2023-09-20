package com.landray.kmss.km.imeeting.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.kms.multidoc.forms.KmsMultidocSubsideForm;
import com.landray.kmss.kms.multidoc.interfaces.IKmsMultidocSubsideForm;
import com.landray.kmss.sys.agenda.forms.SysAgendaCategoryForm;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaCategoryForm;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.news.forms.SysNewsPublishCategoryForm;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryForm;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindCategoryContextForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.sys.rule.forms.ISysRuleTemplateForm;
import com.landray.kmss.sys.rule.forms.SysRuleTemplateForm;
import com.landray.kmss.sys.vote.forms.IVoteCategoryForm;
import com.landray.kmss.sys.workflow.base.forms.SysWfTemplateForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 会议模板 Form
 */
public class KmImeetingTemplateForm extends ExtendAuthTmpForm implements
		ISysWfTemplateForm, ISysNewsPublishCategoryForm, ISysNumberForm,
		ISysAgendaCategoryForm, IVoteCategoryForm, IKmsMultidocSubsideForm,
		ISysRuleTemplateForm {

	private static final long serialVersionUID = -6618853280716455411L;
	/**
	 * 模板名称
	 */
	protected String fdName = null;

	/**
	 * @return 模板名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            模板名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 模板是否有效
	 */
	private String fdIsAvailable = "true";

	/**
	 * 
	 * @return 模板状态
	 */
	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	/**
	 * 
	 * @param fdIsAvailable
	 */
	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 会议主题
	 */
	protected String docSubject = null;

	/**
	 * @return 会议主题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            会议主题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 召开周期
	 */
	protected String fdPeriodType = null;

	/**
	 * @return 召开周期
	 */
	public String getFdPeriodType() {
		return fdPeriodType;
	}

	/**
	 * @param fdPeriodType
	 *            召开周期
	 */
	public void setFdPeriodType(String fdPeriodType) {
		this.fdPeriodType = fdPeriodType;
	}

	/**
	 * 召开时间
	 */
	protected String fdHoldTime = null;

	/**
	 * @return 召开时间
	 */
	public String getFdHoldTime() {
		return fdHoldTime;
	}

	/**
	 * @param fdHoldTime
	 *            召开时间
	 */
	public void setFdHoldTime(String fdHoldTime) {
		this.fdHoldTime = fdHoldTime;
	}

	/**
	 * 提前几天催办召开会议
	 */
	protected String fdNotifyDay = null;

	/**
	 * @return 提前几天催办召开会议
	 */
	public String getFdNotifyDay() {
		return fdNotifyDay;
	}

	/**
	 * @param fdNotifyDay
	 *            提前几天催办召开会议
	 */
	public void setFdNotifyDay(String fdNotifyDay) {
		this.fdNotifyDay = fdNotifyDay;
	}

	/**
	 * 备注
	 */
	protected String fdRemark = null;

	/**
	 * @return 备注
	 */
	public String getFdRemark() {
		return fdRemark;
	}

	/**
	 * @param fdRemark
	 *            备注
	 */
	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	/**
	 * 纪要内容
	 */
	private String fdSummaryContent = null;

	public String getFdSummaryContent() {
		return fdSummaryContent;
	}

	public void setFdSummaryContent(String fdSummaryContent) {
		this.fdSummaryContent = fdSummaryContent;
	}

	/*
	 * 创建人
	 */
	private String docCreatorId = null;

	private String docCreatorName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

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

	/**
	 * 同步时机
	 */
	protected String syncDataToCalendarTime = null;

	/**
	 * @return 同步时机
	 */
	public String getSyncDataToCalendarTime() {
		return syncDataToCalendarTime;
	}

	/**
	 * @param syncDataToCalendarTime
	 *            同步时机
	 */
	public void setSyncDataToCalendarTime(String syncDataToCalendarTime) {
		this.syncDataToCalendarTime = syncDataToCalendarTime;
	}

	/**
	 * 外部与会人员
	 */
	protected String fdOtherAttendPerson = null;

	/**
	 * @return 外部与会人员
	 */
	public String getFdOtherAttendPerson() {
		return fdOtherAttendPerson;
	}

	/**
	 * @param fdOtherAttendPerson
	 *            外部与会人员
	 */
	public void setFdOtherAttendPerson(String fdOtherAttendPerson) {
		this.fdOtherAttendPerson = fdOtherAttendPerson;
	}

	/**
	 * 所属分类
	 */
	protected String docCategoryId = null;

	/**
	 * @return 所属分类
	 */
	public String getDocCategoryId() {
		return docCategoryId;
	}

	/**
	 * @param docCategoryId
	 *            所属分类
	 */
	public void setDocCategoryId(String docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	protected String docCategoryName = null;

	public String getDocCategoryName() {
		return docCategoryName;
	}

	public void setDocCategoryName(String docCategoryName) {
		this.docCategoryName = docCategoryName;
	}

	/**
	 * 所有人可阅读标记
	 */
	protected String authReaderFlag = null;

	/**
	 * @return 所有人可阅读标记
	 */
	public String getAuthReaderFlag() {
		return authReaderFlag;
	}

	/**
	 * @param authReaderFlag
	 *            所有人可阅读标记
	 */
	public void setAuthReaderFlag(String authReaderFlag) {
		this.authReaderFlag = authReaderFlag;
	}

	/**
	 * 会议组织人
	 */
	protected String fdEmceeId;

	protected String fdEmceeName;

	public String getFdEmceeId() {
		return fdEmceeId;
	}

	public void setFdEmceeId(String fdEmceeId) {
		this.fdEmceeId = fdEmceeId;
	}

	public String getFdEmceeName() {
		return fdEmceeName;
	}

	public void setFdEmceeName(String fdEmceeName) {
		this.fdEmceeName = fdEmceeName;
	}

	/**
	 * 所属部门的ID
	 */
	protected String docDeptId = null;

	/**
	 * @return 所属部门的ID
	 */
	public String getDocDeptId() {
		return docDeptId;
	}

	/**
	 * @param docDeptId
	 *            所属部门的ID
	 */
	public void setDocDeptId(String docDeptId) {
		this.docDeptId = docDeptId;
	}

	/**
	 * 所属部门的名称
	 */
	protected String docDeptName = null;

	/**
	 * @return 所属部门的名称
	 */
	public String getDocDeptName() {
		return docDeptName;
	}

	/**
	 * @param docDeptName
	 *            所属部门的名称
	 */
	public void setDocDeptName(String docDeptName) {
		this.docDeptName = docDeptName;
	}

	/**
	 * 录入人的ID
	 */
	protected String fdSummaryInputPersonId = null;

	public String getFdSummaryInputPersonId() {
		return fdSummaryInputPersonId;
	}

	public void setFdSummaryInputPersonId(String fdSummaryInputPersonId) {
		this.fdSummaryInputPersonId = fdSummaryInputPersonId;
	}

	/**
	 * 录入人的名称
	 */
	protected String fdSummaryInputPersonName = null;

	public String getFdSummaryInputPersonName() {
		return fdSummaryInputPersonName;
	}

	public void setFdSummaryInputPersonName(String fdSummaryInputPersonName) {
		this.fdSummaryInputPersonName = fdSummaryInputPersonName;
	}

	/**
	 * 主持人的ID
	 */
	protected String fdHostId = null;

	/**
	 * @return 主持人的ID
	 */
	public String getFdHostId() {
		return fdHostId;
	}

	/**
	 * @param fdHostId
	 *            主持人的ID
	 */
	public void setFdHostId(String fdHostId) {
		this.fdHostId = fdHostId;
	}

	/**
	 * 主持人的名称
	 */
	protected String fdHostName = null;

	/**
	 * @return 主持人的名称
	 */
	public String getFdHostName() {
		return fdHostName;
	}

	/**
	 * @param fdHostName
	 *            主持人的名称
	 */
	public void setFdHostName(String fdHostName) {
		this.fdHostName = fdHostName;
	}

	/**
	 * 与会人员的ID列表
	 */
	protected String fdAttendPersonIds = null;

	/**
	 * @return 与会人员的ID列表
	 */
	public String getFdAttendPersonIds() {
		return fdAttendPersonIds;
	}

	/**
	 * @param fdAttendPersonIds
	 *            与会人员的ID列表
	 */
	public void setFdAttendPersonIds(String fdAttendPersonIds) {
		this.fdAttendPersonIds = fdAttendPersonIds;
	}

	/**
	 * 与会人员的名称列表
	 */
	protected String fdAttendPersonNames = null;

	/**
	 * @return 与会人员的名称列表
	 */
	public String getFdAttendPersonNames() {
		return fdAttendPersonNames;
	}

	/**
	 * @param fdAttendPersonNames
	 *            与会人员的名称列表
	 */
	public void setFdAttendPersonNames(String fdAttendPersonNames) {
		this.fdAttendPersonNames = fdAttendPersonNames;
	}

	/**
	 * 模板修改者ID
	 */
	private String docAlterorId;

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	/**
	 * 模板修改者Name
	 */
	private String docAlterorName;

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	/**
	 * 模板修改时间
	 */
	private String docAlterTime;

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/*
	 * % 相关属性
	 */
	private String docPropertyIds = null;

	private String docPropertyNames = null;

	public String getDocPropertyIds() {
		return docPropertyIds;
	}

	public void setDocPropertyIds(String docPropertyIds) {
		this.docPropertyIds = docPropertyIds;
	}

	public String getDocPropertyNames() {
		return docPropertyNames;
	}

	public void setDocPropertyNames(String docPropertyNames) {
		this.docPropertyNames = docPropertyNames;
	}

	/**
	 * 关联投票模板
	 */
	private String fdVoteCategoryId = null;

	@Override
    public String getFdVoteCategoryId() {
		return fdVoteCategoryId;
	}

	@Override
    public void setFdVoteCategoryId(String fdVoteCategoryId) {
		this.fdVoteCategoryId = fdVoteCategoryId;
	}

	private String fdVoteCategoryName = null;

	@Override
    public String getFdVoteCategoryName() {
		return fdVoteCategoryName;
	}

	@Override
    public void setFdVoteCategoryName(String fdVoteCategoryName) {
		this.fdVoteCategoryName = fdVoteCategoryName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		docSubject = null;
		fdPeriodType = "5"; // 默认为不定期
		fdHoldTime = null;
		fdNotifyDay = "1";
		fdRemark = null;
		syncDataToCalendarTime = null;
		fdOtherAttendPerson = null;
		docCategoryId = null;
		docCategoryName = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		authReaderFlag = null;
		authAttNocopy = null;
		authAttNodownload = null;
		authAttNoprint = null;
		fdEmceeId = null;
		fdEmceeName = null;
		docDeptId = null;
		docDeptName = null;
		fdSummaryInputPersonId = null;
		fdSummaryInputPersonName = null;
		fdHostId = null;
		fdHostName = null;
		fdAttendPersonIds = null;
		fdAttendPersonNames = null;
		fdSummaryContent = null;
		docPropertyIds = null;
		docPropertyNames = null;
		fdVoteCategoryId = null;
		fdVoteCategoryName = null;
		fdIsAvailable = "true";
		sysWfTemplateForms.clear();
		sysRuleTemplateForms.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingTemplate.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 所属类别
			toModelPropertyMap.put("docCategoryId",
					new FormConvertor_IDToModel("docCategory",
							SysCategoryMain.class));
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("fdEmceeId", new FormConvertor_IDToModel(
					"fdEmcee", SysOrgPerson.class));
			toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel(
					"docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdSummaryInputPersonId",
					new FormConvertor_IDToModel("fdSummaryInputPerson",
							SysOrgElement.class));
			toModelPropertyMap.put("fdHostId", new FormConvertor_IDToModel(
					"fdHost", SysOrgElement.class));
			toModelPropertyMap.put("fdAttendPersonIds",
					new FormConvertor_IDsToModelList("fdAttendPersons",
							SysOrgElement.class));
			// 相关属性
			toModelPropertyMap.put("docPropertyIds",
					new FormConvertor_IDsToModelList("docProperties",
							SysCategoryProperty.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 流程模板
	 */
	private AutoHashMap sysWfTemplateForms = new AutoHashMap(
			SysWfTemplateForm.class);

	@Override
	public AutoHashMap getSysWfTemplateForms() {
		return sysWfTemplateForms;
	}

	/**
	 * 发布机制开始
	 */
	private AutoHashMap sysNewsPublishCategoryForms = new AutoHashMap(
			SysNewsPublishCategoryForm.class);

	@Override
	public AutoHashMap getSysNewsPublishCategoryForms() {
		return sysNewsPublishCategoryForms;
	}

	/**
	 * 编号机制
	 */
	private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

	@Override
	public SysNumberMainMappForm getSysNumberMainMappForm() {
		return sysNumberMainMappForm;
	}

	@Override
	public void setSysNumberMainMappForm(
			SysNumberMainMappForm sysNumberMainMappForm) {
		this.sysNumberMainMappForm = sysNumberMainMappForm;
	}

	/**
	 * 日程机制
	 */
	private SysAgendaCategoryForm sysAgendaCategoryForm = new SysAgendaCategoryForm();

	@Override
    public SysAgendaCategoryForm getSysAgendaCategoryForm() {
		return sysAgendaCategoryForm;
	}

	public void setSysAgendaCategoryForm(
			SysAgendaCategoryForm sysAgendaCategoryForm) {
		this.sysAgendaCategoryForm = sysAgendaCategoryForm;
	}

	private SysNotifyRemindCategoryContextForm sysNotifyRemindCategoryContextForm = new SysNotifyRemindCategoryContextForm();

	@Override
    public SysNotifyRemindCategoryContextForm getSysNotifyRemindCategoryContextForm() {
		return sysNotifyRemindCategoryContextForm;
	}

	public void setSysNotifyRemindCategoryContextForm(
			SysNotifyRemindCategoryContextForm sysNotifyRemindCategoryContextForm) {
		this.sysNotifyRemindCategoryContextForm = sysNotifyRemindCategoryContextForm;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	private String fdNeedMultiRes = "false";

	public String getFdNeedMultiRes() {
		return fdNeedMultiRes;
	}

	public void setFdNeedMultiRes(String fdNeedMultiRes) {
		this.fdNeedMultiRes = fdNeedMultiRes;
	}

	/******* 知识沉淀 *******/
	private KmsMultidocSubsideForm kmsMultidocSubsideForm = new KmsMultidocSubsideForm();

	@Override
	public KmsMultidocSubsideForm getKmsMultidocSubsideForm() {
		return kmsMultidocSubsideForm;
	}

	@Override
	public void setKmsMultidocSubsideForm(
			KmsMultidocSubsideForm kmsMultidocSubsideForm) {
		this.kmsMultidocSubsideForm = kmsMultidocSubsideForm;
	}
	/******* 知识沉淀 *******/
	/* 规则引擎start */
	private AutoHashMap sysRuleTemplateForms = new AutoHashMap(
			SysRuleTemplateForm.class);

	@Override
	public AutoHashMap getSysRuleTemplateForms() {
		return sysRuleTemplateForms;
	}

	/* 规则引擎end */

	/**
	 * 是否启用电子签章
	 */
	private String fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public String getFdSignEnable() {
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(String fdSignEnable) {
		this.fdSignEnable = fdSignEnable;
	}
	/**
	 * 提醒中心
	 */
	private Map<String, Object> sysRemind = new HashMap<String, Object>();

	public Map<String, Object> getSysRemind() {
		return sysRemind;
	}

	public void setSysRemind(Map<String, Object> sysRemind) {
		this.sysRemind = sysRemind;
	}
}
