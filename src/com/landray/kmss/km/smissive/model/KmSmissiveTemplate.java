package com.landray.kmss.km.smissive.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.smissive.forms.KmSmissiveTemplateForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.rule.model.ISysRuleTemplateModel;
import com.landray.kmss.sys.rule.model.SysRuleTemplate;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagTemplateModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn 类别设置
 */
@SuppressWarnings("serial")
public class KmSmissiveTemplate extends SysSimpleCategoryAuthTmpModel implements
		IAttachment,// 附件
		ISysWfTemplateModel,// 流程
		ISysRelationMainModel,// 关联
		ISysTagTemplateModel,// 标签
		ISysNewsPublishCategoryModel,// 发布
		ISysNumberModel,
		ISysRuleTemplateModel
{
	
	private SysNumberMainMapp sysNumberMainMapp=new SysNumberMainMapp();
	@Override
    public SysNumberMainMapp getSysNumberMainMappModel() {
		return sysNumberMainMapp;
	}
	@Override
    public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMapp1) {
		this.sysNumberMainMapp=sysNumberMainMapp1;
	}

	/*
	 * 是否需要正文
	 */
	private String fdNeedContent;

	public String getFdNeedContent() {
		if (StringUtil.isNull(fdNeedContent)) {
			return "1";
		}
		return fdNeedContent;
	}

	public void setFdNeedContent(String fdNeedContent) {
		this.fdNeedContent = fdNeedContent;
	}

	/*
	 * 编号前缀
	 */
	protected String fdCodePre;

	/*
	 * 当前流水号
	 */
	protected Integer fdCurNo;

	protected String fdYear;

	/*
	 * 默认表头
	 */
	protected String fdTmpTitle;

	/*
	 * 默认紧急程度
	 */
	protected String fdTmpUrgency;

	/*
	 * 默认密级等级
	 */
	protected String fdTmpSecret;

	/*
	 * 流程信息保密
	 */
	protected Boolean fdTmpFlowFlag;

	/*
	 * 发文单位
	 */
	protected SysOrgElement fdTmpMainDept;

	/*
	 * 主送单位
	 */
	protected SysOrgElement fdTmpSendDept;

	/*
	 * 主送单位(变成多值，为兼容老数据保留原单值字段)
	 */
	protected List fdTmpSendDepts = new ArrayList();

	/*
	 * 抄送单位
	 */
	protected SysOrgElement fdTmpCopyDept;

	/*
	 * 抄送单位(变成多值，为兼容老数据保留原单值字段)
	 */
	protected List fdTmpCopyDepts = new ArrayList();

	/*
	 * 签发人
	 */
	protected SysOrgPerson fdTmpIssuer;

	public KmSmissiveTemplate() {
		super();
	}

	public SysOrgElement getFdTmpMainDept() {
		return fdTmpMainDept;
	}

	public void setFdTmpMainDept(SysOrgElement fdTmpMainDept) {
		this.fdTmpMainDept = fdTmpMainDept;
	}

	public SysOrgElement getFdTmpSendDept() {
		return fdTmpSendDept;
	}

	public void setFdTmpSendDept(SysOrgElement fdTmpSendDept) {
		this.fdTmpSendDept = fdTmpSendDept;
	}

	public List getFdTmpSendDepts() {
		if (fdTmpSendDept != null) {
			fdTmpSendDepts.add(fdTmpSendDept);
			fdTmpSendDept = null;
		}
		return fdTmpSendDepts;
	}

	public void setFdTmpSendDepts(List fdTmpSendDepts) {
		this.fdTmpSendDepts = fdTmpSendDepts;
	}

	public SysOrgElement getFdTmpCopyDept() {
		return fdTmpCopyDept;
	}

	public void setFdTmpCopyDept(SysOrgElement fdTmpCopyDept) {
		this.fdTmpCopyDept = fdTmpCopyDept;
	}

	public List getFdTmpCopyDepts() {
		if (fdTmpCopyDept != null) {
			fdTmpCopyDepts.add(fdTmpCopyDept);
			fdTmpCopyDept = null;
		}
		return fdTmpCopyDepts;
	}

	public void setFdTmpCopyDepts(List fdTmpCopyDepts) {
		this.fdTmpCopyDepts = fdTmpCopyDepts;
	}

	public SysOrgPerson getFdTmpIssuer() {
		return fdTmpIssuer;
	}

	public void setFdTmpIssuer(SysOrgPerson fdTmpIssuer) {
		this.fdTmpIssuer = fdTmpIssuer;
	}

	public String getFdYear() {
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}

	/**
	 * @return 返回 编号前缀
	 */
	public String getFdCodePre() {
		return fdCodePre;
	}

	/**
	 * @param fdCodePre
	 *            要设置的 编号前缀
	 */
	public void setFdCodePre(String fdCodePre) {
		this.fdCodePre = fdCodePre;
	}

	/**
	 * @return 返回 当前流水号
	 */
	public Integer getFdCurNo() {
		return fdCurNo;
	}

	/**
	 * @param fdCurNo
	 *            要设置的 当前流水号
	 */
	public void setFdCurNo(Integer fdCurNo) {
		this.fdCurNo = fdCurNo;
	}

	/**
	 * @return 返回 默认表头
	 */
	public String getFdTmpTitle() {
		return fdTmpTitle;
	}

	/**
	 * @param fdTmpTitle
	 *            要设置的 默认表头
	 */
	public void setFdTmpTitle(String fdTmpTitle) {
		this.fdTmpTitle = fdTmpTitle;
	}

	/**
	 * @return 返回 默认紧急程度
	 */
	public String getFdTmpUrgency() {
		return fdTmpUrgency;
	}

	/**
	 * @param fdTmpUrgency
	 *            要设置的 默认紧急程度
	 */
	public void setFdTmpUrgency(String fdTmpUrgency) {
		this.fdTmpUrgency = fdTmpUrgency;
	}

	/**
	 * @return 返回 默认密级等级
	 */
	public String getFdTmpSecret() {
		return fdTmpSecret;
	}

	/**
	 * @param fdTmpSecret
	 *            要设置的 默认密级等级
	 */
	public void setFdTmpSecret(String fdTmpSecret) {
		this.fdTmpSecret = fdTmpSecret;
	}

	/**
	 * @return 返回 流程信息保密
	 */
	public Boolean getFdTmpFlowFlag() {
		return fdTmpFlowFlag;
	}

	/**
	 * @param fdTmpFlowFlag
	 *            要设置的 流程信息保密
	 */
	public void setFdTmpFlowFlag(Boolean fdTmpFlowFlag) {
		this.fdTmpFlowFlag = fdTmpFlowFlag;
	}

	/*
	 * 多对多关联 辅类别
	 */
	protected List<SysCategoryProperty> docProperties = new ArrayList<SysCategoryProperty>();

	public List<SysCategoryProperty> getDocProperties() {
		return docProperties;
	}

	public void setDocProperties(List<SysCategoryProperty> fdProperties) {
		this.docProperties = fdProperties;
	}

	@Override
    public Class<KmSmissiveTemplateForm> getFormClass() {
		return KmSmissiveTemplateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			// 签发人fdIssuer
			toFormPropertyMap.put("fdTmpIssuer.fdId", "fdTmpIssuerId");
			toFormPropertyMap.put("fdTmpIssuer.fdName", "fdTmpIssuerName");
			// 发文单位
			toFormPropertyMap.put("fdTmpMainDept.fdId", "fdTmpMainDeptId");
			toFormPropertyMap.put("fdTmpMainDept.fdName", "fdTmpMainDeptName");
			// 主送单位
			// toFormPropertyMap.put("fdTmpSendDept.fdId", "fdTmpSendDeptId");
			// toFormPropertyMap.put("fdTmpSendDept.fdName",
			// "fdTmpSendDeptName");
			toFormPropertyMap.put("fdTmpSendDepts",
					new ModelConvertor_ModelListToString(
							"fdTmpSendDeptIds:fdTmpSendDeptNames",
							"fdId:fdName"));
			// 抄送单位
			// toFormPropertyMap.put("fdTmpCopyDept.fdId", "fdTmpCopyDeptId");
			// toFormPropertyMap.put("fdTmpCopyDept.fdName",
			// "fdTmpCopyDeptName");
			toFormPropertyMap.put("fdTmpCopyDepts",
					new ModelConvertor_ModelListToString(
							"fdTmpCopyDeptIds:fdTmpCopyDeptNames",
							"fdId:fdName"));

			toFormPropertyMap.put("docProperties",
					new ModelConvertor_ModelListToString(
							"docPropertyIds:docPropertyNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	// =====附件机制(开始)=====
	protected AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	// =====附件机制(结束)=====

	// ********** 以下的代码为流程模板需要的代码，请直接拷贝 **********
	@SuppressWarnings("unchecked")
	protected List sysWfTemplateModels;

	@Override
    @SuppressWarnings("unchecked")
	public List getSysWfTemplateModels() {
		return sysWfTemplateModels;
	}

	@Override
    @SuppressWarnings("unchecked")
	public void setSysWfTemplateModels(List sysWfTemplateModels) {
		this.sysWfTemplateModels = sysWfTemplateModels;
	}

	// ********** 以上的代码为流程模板需要的代码，请直接拷贝 **********

	// ********** 关联机制(开始) **********

	/*
	 * 关联域模型信息
	 */
	protected SysRelationMain sysRelationMain = null;

	@Override
    public SysRelationMain getSysRelationMain() {
		return sysRelationMain;
	}

	@Override
    public void setSysRelationMain(SysRelationMain sysRelationMain) {
		this.sysRelationMain = sysRelationMain;
	}

	String relationSeparate = null;

	/**
	 * 获取关联分表字段
	 * 
	 * @return
	 */
	public String getRelationSeparate() {
		return relationSeparate;
	}

	/**
	 * 设置关联分表字段
	 */
	public void setRelationSeparate(String relationSeparate) {
		this.relationSeparate = relationSeparate;
	}

	// ********** 关联机制(结束) **********

	// ======标签机制开始 =========
	@SuppressWarnings("unchecked")
	private List sysTagTemplates;

	@Override
    @SuppressWarnings("unchecked")
	public List getSysTagTemplates() {
		return sysTagTemplates;
	}

	@Override
    @SuppressWarnings("unchecked")
	public void setSysTagTemplates(List sysTagTemplates) {
		this.sysTagTemplates = sysTagTemplates;
	}

	// =======标签机制开始==========

	// ======发布机制(开始)=====
	@SuppressWarnings("unchecked")
	private List sysNewsPublishCategorys;

	@Override
    @SuppressWarnings("unchecked")
	public List getSysNewsPublishCategorys() {
		return sysNewsPublishCategorys;
	}

	@Override
    @SuppressWarnings("unchecked")
	public void setSysNewsPublishCategorys(List sysNewsPublishCategorys) {
		this.sysNewsPublishCategorys = sysNewsPublishCategorys;
	}

	// ======发布机制(结束)=====
	/* 规则机制start */
	private List<SysRuleTemplate> sysRuleTemplates = null;

	@Override
    public List<SysRuleTemplate> getSysRuleTemplates() {
		return sysRuleTemplates;
	}

	@Override
    public void setSysRuleTemplates(List<SysRuleTemplate> sysRuleTemplates) {
		this.sysRuleTemplates = sysRuleTemplates;
	}

	/* 规则机制end */
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
