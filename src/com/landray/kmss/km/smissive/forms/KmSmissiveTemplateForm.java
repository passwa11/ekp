package com.landray.kmss.km.smissive.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.smissive.model.KmSmissiveTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.news.forms.SysNewsPublishCategoryForm;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.rule.forms.ISysRuleTemplateForm;
import com.landray.kmss.sys.rule.forms.SysRuleTemplateForm;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.sys.tag.forms.SysTagTemplateForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagTemplateForm;
import com.landray.kmss.sys.workflow.base.forms.SysWfTemplateForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn
 */
@SuppressWarnings("serial")
public class KmSmissiveTemplateForm extends SysSimpleCategoryAuthTmpForm
		implements IAttachmentForm, ISysWfTemplateForm, ISysRelationMainForm,
		ISysTagTemplateForm, ISysNewsPublishCategoryForm, ISysNumberForm,
		ISysRuleTemplateForm {

	private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();
	@Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
				return sysNumberMainMappForm;
			}
	@Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm frm) {
				sysNumberMainMappForm=frm;
			}

	/*
	 * 是否需要正文
	 */
	private String fdNeedContent;

	public String getFdNeedContent() {
		return fdNeedContent;
	}

	public void setFdNeedContent(String fdNeedContent) {
		this.fdNeedContent = fdNeedContent;
	}

	/*
	 * 编号前缀
	 */
	private String fdCodePre = null;
	/*
	 * 当前流水号
	 */
	private String fdCurNo = "0";
	private String fdYear = null;
	/*
	 * 默认表头
	 */
	private String fdTmpTitle = null;
	/*
	 * 默认紧急程度
	 */
	private String fdTmpUrgency = null;
	/*
	 * 默认密级等级
	 */
	private String fdTmpSecret = null;
	/*
	 * 流程信息保密
	 */
	private String fdTmpFlowFlag = "false";

	/*
	 * 辅类别
	 */
	private String docPropertyIds = null;
	private String docPropertyNames = null;

	/*
	 * 发文单位
	 */
	private String fdTmpMainDeptId = null;
	private String fdTmpMainDeptName = null;
	/*
	 * 主送单位
	 */
	private String fdTmpSendDeptIds = null;
	private String fdTmpSendDeptNames = null;
	/*
	 * 抄送单位
	 */
	private String fdTmpCopyDeptIds = null;
	private String fdTmpCopyDeptNames = null;
	/*
	 * 签发人
	 */
	private String fdTmpIssuerId = null;
	private String fdTmpIssuerName = null;

	public String getFdTmpMainDeptId() {
		return fdTmpMainDeptId;
	}

	public void setFdTmpMainDeptId(String fdTmpMainDeptId) {
		this.fdTmpMainDeptId = fdTmpMainDeptId;
	}

	public String getFdTmpMainDeptName() {
		return fdTmpMainDeptName;
	}

	public void setFdTmpMainDeptName(String fdTmpMainDeptName) {
		this.fdTmpMainDeptName = fdTmpMainDeptName;
	}

	public String getFdTmpSendDeptIds() {
		return fdTmpSendDeptIds;
	}

	public void setFdTmpSendDeptIds(String fdTmpSendDeptIds) {
		this.fdTmpSendDeptIds = fdTmpSendDeptIds;
	}

	public String getFdTmpSendDeptNames() {
		return fdTmpSendDeptNames;
	}

	public void setFdTmpSendDeptNames(String fdTmpSendDeptNames) {
		this.fdTmpSendDeptNames = fdTmpSendDeptNames;
	}

	public String getFdTmpCopyDeptIds() {
		return fdTmpCopyDeptIds;
	}

	public void setFdTmpCopyDeptIds(String fdTmpCopyDeptIds) {
		this.fdTmpCopyDeptIds = fdTmpCopyDeptIds;
	}

	public String getFdTmpCopyDeptNames() {
		return fdTmpCopyDeptNames;
	}

	public void setFdTmpCopyDeptNames(String fdTmpCopyDeptNames) {
		this.fdTmpCopyDeptNames = fdTmpCopyDeptNames;
	}

	public String getFdTmpIssuerId() {
		return fdTmpIssuerId;
	}

	public void setFdTmpIssuerId(String fdTmpIssuerId) {
		this.fdTmpIssuerId = fdTmpIssuerId;
	}

	public String getFdTmpIssuerName() {
		return fdTmpIssuerName;
	}

	public void setFdTmpIssuerName(String fdTmpIssuerName) {
		this.fdTmpIssuerName = fdTmpIssuerName;
	}

	public String getFdYear() {
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}

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
	public String getFdCurNo() {
		return fdCurNo;
	}

	/**
	 * @param fdCurNo
	 *            要设置的 当前流水号
	 */
	public void setFdCurNo(String fdCurNo) {
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
	public String getFdTmpFlowFlag() {
		return fdTmpFlowFlag;
	}

	/**
	 * @param fdTmpFlowFlag
	 *            要设置的 流程信息保密
	 */
	public void setFdTmpFlowFlag(String fdTmpFlowFlag) {
		this.fdTmpFlowFlag = fdTmpFlowFlag;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdNeedContent = null;
		fdCodePre = null;
		fdCurNo = "0";
		fdTmpTitle = null;
		fdTmpUrgency = null;
		fdTmpSecret = null;
		fdTmpFlowFlag = "false";
		Calendar calendar = Calendar.getInstance();
		fdYear = String.valueOf(calendar.get(Calendar.YEAR));

		fdTmpMainDeptId = null;
		fdTmpMainDeptName = null;

		fdTmpSendDeptIds = null;
		fdTmpSendDeptNames = null;

		fdTmpCopyDeptIds = null;
		fdTmpCopyDeptNames = null;

		fdTmpIssuerId = null;
		fdTmpIssuerName = null;

		// 初始化关联表单
		sysRelationMainForm = new SysRelationMainForm();
		// 流程模板的初始化动作
		sysWfTemplateForms.clear();
		sysRuleTemplateForms.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class<KmSmissiveTemplate> getModelClass() {
		return KmSmissiveTemplate.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());

			toModelPropertyMap.put("fdTmpMainDeptId",
					new FormConvertor_IDToModel("fdTmpMainDept",
							SysOrgElement.class));
			toModelPropertyMap.put("fdTmpSendDeptIds",
					new FormConvertor_IDsToModelList("fdTmpSendDepts",
							SysOrgElement.class));
			toModelPropertyMap.put("fdTmpCopyDeptIds",
					new FormConvertor_IDsToModelList("fdTmpCopyDepts",
							SysOrgElement.class));
			toModelPropertyMap.put("fdTmpIssuerId",
					new FormConvertor_IDToModel("fdTmpIssuer",
							SysOrgPerson.class));

			toModelPropertyMap.put("docPropertyIds",
					new FormConvertor_IDsToModelList("docProperties",
							SysCategoryProperty.class));
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", KmSmissiveTemplate.class));
		}
		return toModelPropertyMap;
	}

	// ******************* 附件机制（开始）**********************//
	AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	// ******************* 附件机制（结束）**********************//

	// ********** 以下的代码为流程模板需要的代码，请直接拷贝 **********
	protected AutoHashMap sysWfTemplateForms = new AutoHashMap(
			SysWfTemplateForm.class);

	@Override
    public AutoHashMap getSysWfTemplateForms() {
		return sysWfTemplateForms;
	}

	// ********** 以上的代码为流程模板需要的代码，请直接拷贝 **********

	// ********** 关联机制(开始)，请直接拷贝 **********

	protected SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

	@Override
    public SysRelationMainForm getSysRelationMainForm() {
		return sysRelationMainForm;
	}

	// ********** 关联机制(结束)，请直接拷贝 **********

	// =============标签机制开始===================
	private AutoHashMap sysTagTemplateForms = new AutoHashMap(
			SysTagTemplateForm.class);

	@Override
    public AutoHashMap getSysTagTemplateForms() {
		return sysTagTemplateForms;
	}

	// =============标签机制结束===================

	// =================发布机制开始===============
	private AutoHashMap sysNewsPublishCategoryForms = new AutoHashMap(
			SysNewsPublishCategoryForm.class);

	@Override
    public AutoHashMap getSysNewsPublishCategoryForms() {
		return sysNewsPublishCategoryForms;
	}

	// =================发布机制结束===============
	/* 规则机制start */
	private AutoHashMap sysRuleTemplateForms = new AutoHashMap(
			SysRuleTemplateForm.class);

	@Override
	public AutoHashMap getSysRuleTemplateForms() {
		return sysRuleTemplateForms;
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
