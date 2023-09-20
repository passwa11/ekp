package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingTemplateForm;
import com.landray.kmss.kms.multidoc.interfaces.IKmsMultidocSubsideModel;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaCategoryModel;
import com.landray.kmss.sys.agenda.model.SysAgendaCategory;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindCategoryContextModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.sys.rule.model.ISysRuleTemplateModel;
import com.landray.kmss.sys.rule.model.SysRuleTemplate;
import com.landray.kmss.sys.vote.model.IVoteCategoryModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateModel;

import java.util.*;

/**
 * 会议模板
 */
public class KmImeetingTemplate extends ExtendAuthTmpModel implements
		IBaseTemplateModel, InterceptFieldEnabled, ISysWfTemplateModel,
		ISysNotifyModel, ISysNewsPublishCategoryModel, ISysNumberModel,
		ISysAgendaCategoryModel, IVoteCategoryModel, IKmsMultidocSubsideModel,
		ISysRuleTemplateModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 模板名称
	 */
	protected String fdName;

	/**
	 * @return 模板名称
	 */
	@Override
	public String getFdName() {
		// return fdName;
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            模板名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/**
	 * 模版是否有效
	 */
	private Boolean fdIsAvailable;

	/**
	 * 
	 * @return 模版状态
	 */
	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
            fdIsAvailable = Boolean.TRUE;
        }
		return fdIsAvailable;
	}

	/**
	 * 
	 * @param fdIsAvailable
	 */
	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 会议主题
	 */
	protected String docSubject;

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
	protected Integer fdPeriodType;

	/**
	 * @return 召开周期
	 */
	public Integer getFdPeriodType() {
		return fdPeriodType;
	}

	/**
	 * @param fdPeriodType
	 *            召开周期
	 */
	public void setFdPeriodType(Integer fdPeriodType) {
		this.fdPeriodType = fdPeriodType;
	}

	/**
	 * 召开时间
	 */
	protected String fdHoldTime;

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
	protected Integer fdNotifyDay;

	/**
	 * @return 提前几天催办召开会议
	 */
	public Integer getFdNotifyDay() {
		return fdNotifyDay;
	}

	/**
	 * @param fdNotifyDay
	 *            提前几天催办召开会议
	 */
	public void setFdNotifyDay(Integer fdNotifyDay) {
		this.fdNotifyDay = fdNotifyDay;
	}

	/**
	 * 备注
	 */
	protected String fdRemark;

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
	protected String fdSummaryContent = null;

	/**
	 * @return 返回 纪要内容
	 */
	public java.lang.String getFdSummaryContent() {
		return (String) readLazyField("fdSummaryContent", fdSummaryContent);
	}

	/**
	 * @param fdSummaryContent
	 *            要设置的 纪要内容
	 */
	public void setFdSummaryContent(String fdSummaryContent) {
		this.fdSummaryContent = (String) writeLazyField("fdSummaryContent",
				this.fdSummaryContent, fdSummaryContent);
	}

	/**
	 * 外部与会人员
	 */
	protected String fdOtherAttendPerson;

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
	protected SysCategoryMain docCategory;

	/**
	 * @return 所属分类
	 */
	@Override
	public SysCategoryMain getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(SysCategoryMain docCategory) {
		this.docCategory = docCategory;
	}

	/**
	 * 会议组织人
	 */
	protected SysOrgElement fdEmcee = null;

	public SysOrgElement getFdEmcee() {
		return fdEmcee;
	}

	public void setFdEmcee(SysOrgElement fdEmcee) {
		this.fdEmcee = fdEmcee;
	}

	/**
	 * 会议组织部门
	 */
	protected SysOrgElement docDept;

	/**
	 * @return 会议组织部门
	 */
	public SysOrgElement getDocDept() {
		return docDept;
	}

	/**
	 * @param docDept
	 *            会议组织部门
	 */
	public void setDocDept(SysOrgElement docDept) {
		this.docDept = docDept;
	}

	/**
	 * 纪要录入人
	 */
	protected SysOrgElement fdSummaryInputPerson;

	public SysOrgElement getFdSummaryInputPerson() {
		return fdSummaryInputPerson;
	}

	public void setFdSummaryInputPerson(SysOrgElement fdSummaryInputPerson) {
		this.fdSummaryInputPerson = fdSummaryInputPerson;
	}

	/**
	 * 主持人
	 */
	protected SysOrgElement fdHost;

	/**
	 * @return 主持人
	 */
	public SysOrgElement getFdHost() {
		return fdHost;
	}

	/**
	 * @param fdHost
	 *            主持人
	 */
	public void setFdHost(SysOrgElement fdHost) {
		this.fdHost = fdHost;
	}

	/**
	 * 与会人员
	 */
	protected List<SysOrgElement> fdAttendPersons;

	/**
	 * @return 与会人员
	 */
	public List<SysOrgElement> getFdAttendPersons() {
		return fdAttendPersons;
	}

	/**
	 * @param fdAttendPersons
	 *            与会人员
	 */
	public void setFdAttendPersons(List<SysOrgElement> fdAttendPersons) {
		this.fdAttendPersons = fdAttendPersons;
	}

	private SysOrgPerson docAlteror;

	/**
	 * 修改者
	 * 
	 * @return docAlteror
	 */
	public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param docAlteror
	 *            要设置的 docAlteror
	 */
	public void setDocAlteror(SysOrgPerson docAlteror) {
		this.docAlteror = docAlteror;
	}

	private Date docAlterTime;

	/**
	 * 修改时间
	 * 
	 * @return docAlterTime
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 docAlterTime
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 多对多关联 相关属性
	 */
	protected List docProperties = new ArrayList();

	public List getDocProperties() {
		return docProperties;
	}

	public void setDocProperties(List docProperties) {
		this.docProperties = docProperties;
	}

	@Override
	public Class getFormClass() {
		return KmImeetingTemplateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
			toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
			toFormPropertyMap.put("fdEmcee.fdId", "fdEmceeId");
			toFormPropertyMap.put("fdEmcee.fdName", "fdEmceeName");
			toFormPropertyMap.put("docDept.fdId", "docDeptId");
			toFormPropertyMap.put("docDept.deptLevelNames", "docDeptName");
			toFormPropertyMap.put("fdSummaryInputPerson.fdId",
					"fdSummaryInputPersonId");
			toFormPropertyMap.put("fdSummaryInputPerson.fdName",
					"fdSummaryInputPersonName");
			toFormPropertyMap.put("fdHost.fdId", "fdHostId");
			toFormPropertyMap.put("fdHost.fdName", "fdHostName");
			toFormPropertyMap.put("fdAttendPersons",
					new ModelConvertor_ModelListToString(
							"fdAttendPersonIds:fdAttendPersonNames",
							"fdId:fdName"));
			toFormPropertyMap.put("docProperties",
					new ModelConvertor_ModelListToString(
							"docPropertyIds:docPropertyNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	/**
	 * 流程模板
	 */
	private List sysWfTemplateModels;

	@Override
	public List getSysWfTemplateModels() {
		return sysWfTemplateModels;
	}

	@Override
	public void setSysWfTemplateModels(List sysWfTemplateModels) {
		this.sysWfTemplateModels = sysWfTemplateModels;
	}

	/**
	 * 发布机制
	 */
	private List sysNewsPublishCategorys;

	@Override
	public List getSysNewsPublishCategorys() {
		return sysNewsPublishCategorys;
	}

	@Override
	public void setSysNewsPublishCategorys(List sysNewsPublishCategorys) {
		this.sysNewsPublishCategorys = sysNewsPublishCategorys;
	}

	/**
	 * 编号机制
	 */
	private SysNumberMainMapp sysNumberMainMapp = new SysNumberMainMapp();

	@Override
	public SysNumberMainMapp getSysNumberMainMappModel() {
		return sysNumberMainMapp;
	}

	@Override
	public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMapp) {
		this.sysNumberMainMapp = sysNumberMainMapp;
	}

	/**
	 * 日程机制
	 */

	protected String syncDataToCalendarTime = "noSync";

	public String getSyncDataToCalendarTime() {
		return syncDataToCalendarTime;
	}

	public void setSyncDataToCalendarTime(String syncDataToCalendarTime) {
		this.syncDataToCalendarTime = syncDataToCalendarTime;
	}

	private SysAgendaCategory sysAgendaCategory = new SysAgendaCategory();

	@Override
	public SysAgendaCategory getSysAgendaCategory() {
		return sysAgendaCategory;
	}

	@Override
	public void setSysAgendaCategory(SysAgendaCategory sysAgendaCategory) {
		this.sysAgendaCategory = sysAgendaCategory;
	}

	private SysNotifyRemindCategoryContextModel sysNotifyRemindCategoryContextModel = new SysNotifyRemindCategoryContextModel();

	@Override
	public SysNotifyRemindCategoryContextModel getSysNotifyRemindCategoryContextModel() {
		return sysNotifyRemindCategoryContextModel;
	}

	@Override
	public void setSysNotifyRemindCategoryContextModel(
			SysNotifyRemindCategoryContextModel sysNotifyRemindCategoryContextModel) {
		this.sysNotifyRemindCategoryContextModel = sysNotifyRemindCategoryContextModel;
	}

	private String fdVoteCategoryId;

	@Override
	public String getFdVoteCategoryId() {
		return fdVoteCategoryId;
	}

	@Override
	public void setFdVoteCategoryId(String fdVoteCategoryId) {
		this.fdVoteCategoryId = fdVoteCategoryId;
	}

	private Boolean fdNeedMultiRes = new Boolean(false);

	public Boolean getFdNeedMultiRes() {
		return fdNeedMultiRes;
	}

	public void setFdNeedMultiRes(Boolean fdNeedMultiRes) {
		this.fdNeedMultiRes = fdNeedMultiRes;
	}

	/***** 知识沉淀 *****/
	private KmsMultidocSubside kmsMultidocSubside = null;

	@Override
	public KmsMultidocSubside getKmsMultidocSubSide() {
		return kmsMultidocSubside;
	}

	@Override
	public void
			setKmsMultidocSubside(KmsMultidocSubside kmsMultidocSubside) {
		this.kmsMultidocSubside = kmsMultidocSubside;
	}
	/***** 知识沉淀 *****/
	/* 规则引擎start */
	private List<SysRuleTemplate> sysRuleTemplates = null;

	@Override
	public List<SysRuleTemplate> getSysRuleTemplates() {
		return sysRuleTemplates;
	}

	@Override
	public void setSysRuleTemplates(List<SysRuleTemplate> sysRuleTemplates) {
		this.sysRuleTemplates = sysRuleTemplates;
	}
	/* 规则引擎end */

	/**
	 * 是否启用电子签章
	 */

	private Boolean fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public Boolean getFdSignEnable() {
		if (fdSignEnable == null) {
			return false;
		}
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(Boolean fdSignEnable) {
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
