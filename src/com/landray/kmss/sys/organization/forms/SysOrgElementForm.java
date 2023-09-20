package com.landray.kmss.sys.organization.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.extend.forms.PluginExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean for a Struts application.
 * 
 * @author 叶中奇
 */
public abstract class SysOrgElementForm extends PluginExtendForm {
	/*
	 * 显示名
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 编号
	 */
	private String fdNo;

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	/*
	 * 排序号
	 */
	private String fdOrder;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * 关键字
	 */
	private String fdKeyword;

	public String getFdKeyword() {
		return fdKeyword;
	}

	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
	}

	/*
	 * 是否有效
	 */
	private String fdIsAvailable = "true";

	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/*
	 * 是否业务相关
	 */
	private String fdIsBusiness = "true";

	public String getFdIsBusiness() {
		return fdIsBusiness;
	}

	public void setFdIsBusiness(String fdIsBusiness) {
		this.fdIsBusiness = fdIsBusiness;
	}

	/*
	 * 描述
	 */
	private String fdMemo;

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	/*
	 * 父部门
	 */
	private String fdParentId;

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	private String fdParentName;

	public String getFdParentName() {
		return fdParentName;
	}

	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	/*
	 * 本级领导
	 */
	private String fdThisLeaderId;

	public String getFdThisLeaderId() {
		return fdThisLeaderId;
	}

	public void setFdThisLeaderId(String fdThisLeaderId) {
		this.fdThisLeaderId = fdThisLeaderId;
	}

	private String fdThisLeaderName;

	public String getFdThisLeaderName() {
		return fdThisLeaderName;
	}

	public void setFdThisLeaderName(String fdThisLeaderName) {
		this.fdThisLeaderName = fdThisLeaderName;
	}

	/**
	 * 场所管理员的ID列表
	 */
	protected String authElementAdminIds = null;

	/**
	 * @return 场所管理员的ID列表
	 */
	public String getAuthElementAdminIds() {
		return authElementAdminIds;
	}

	/**
	 * @param authElementAdminIds
	 *            场所管理员的ID列表
	 */
	public void setAuthElementAdminIds(String authElementAdminIds) {
		this.authElementAdminIds = authElementAdminIds;
	}

	/**
	 * 场所管理员的名称列表
	 */
	protected String authElementAdminNames = null;

	/**
	 * @return 场所管理员的名称列表
	 */
	public String getAuthElementAdminNames() {
		return authElementAdminNames;
	}

	/**
	 * @param authElementAdminNames
	 *            场所管理员的名称列表
	 */
	public void setAuthElementAdminNames(String authElementAdminNames) {
		this.authElementAdminNames = authElementAdminNames;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdNo = null;
		fdOrder = null;
		fdKeyword = null;
		fdIsAvailable = "true";
		fdIsBusiness = "true";
		fdIsExternal = "false";
		fdMemo = null;
		fdParentId = null;
		fdParentName = null;
		fdThisLeaderId = null;
		fdThisLeaderName = null;
		fdOrgEmail = null;
		fdRange = new SysOrgElementRangeForm();
		fdRange.setFdViewType("1");
		fdHideRange = new SysOrgElementHideRangeForm();
		fdHideRange.setFdViewType("0");
		docCreatorId = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysOrgElement.class));
			toModelPropertyMap.put("fdThisLeaderId",
					new FormConvertor_IDToModel("hbmThisLeader",
							SysOrgElement.class));
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgPerson.class));
			
			toModelPropertyMap.put("fdRange", new FormConvertor_FormToModel("fdRange"));
			toModelPropertyMap.put("fdHideRange", new FormConvertor_FormToModel("fdHideRange"));
		}
		return toModelPropertyMap;
	}

	public void setFdOrgEmail(String fdOrgEmail) {
		this.fdOrgEmail = fdOrgEmail;
	}

	public String getFdOrgEmail() {
		return fdOrgEmail;
	}

	private String fdOrgEmail;

	private String deptLevelNames;

	public String getDeptLevelNames() {
		return deptLevelNames;
	}

	public void setDeptLevelNames(String deptLevelNames) {
		this.deptLevelNames = deptLevelNames;
	}

	private String fdNameOri;

	public String getFdNameOri() {
		return fdNameOri;
	}

	public void setFdNameOri(String fdNameOri) {
		this.fdNameOri = fdNameOri;
	}
	
	/**
	 * 是否外部组织
	 */
	private String fdIsExternal;

	public String getFdIsExternal() {
		// 默认是内部组织
		if (fdIsExternal == null) {
			fdIsExternal = "false";
		}
		return fdIsExternal;
	}

	public void setFdIsExternal(String fdIsExternal) {
		this.fdIsExternal = fdIsExternal;
	}
	
	/**
	 * 外部组织扩展
	 */
	private SysOrgElementExternalForm fdExternal;

	public SysOrgElementExternalForm getFdExternal() {
		return fdExternal;
	}

	public void setFdExternal(SysOrgElementExternalForm fdExternal) {
		this.fdExternal = fdExternal;
	}

	/**
	 * 是否开启组织成员查看组织范围
	 */
	private SysOrgElementRangeForm fdRange;

	/**
	 * 是否隐藏本部门
	 */
	private SysOrgElementHideRangeForm fdHideRange;

	public SysOrgElementHideRangeForm getFdHideRange() {
		return fdHideRange;
	}

	public void setFdHideRange(SysOrgElementHideRangeForm fdHideRange) {
		this.fdHideRange = fdHideRange;
	}

	public SysOrgElementRangeForm getFdRange() {
		return fdRange;
	}

	public void setFdRange(SysOrgElementRangeForm fdRange) {
		this.fdRange = fdRange;
	}
	
	/**
	 * 创建者的ID
	 */
	private String docCreatorId;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	private String fdPreDeptId;

	public String getFdPreDeptId() {
		return fdPreDeptId;
	}

	public void setFdPreDeptId(String fdPreDeptId) {
		this.fdPreDeptId = fdPreDeptId;
	}
}
