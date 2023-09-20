package com.landray.kmss.hr.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 人事组织架构
  */
public class HrOrganizationElementForm extends ExtendForm {

	@Override
    public Class<HrOrganizationElement> getModelClass() {
		return HrOrganizationElement.class;
	}

	/*
	 * 类型
	 */
	private Integer fdOrgType;

	public Integer getFdOrgType() {
		return fdOrgType;
	}

	public void setFdOrgType(Integer fdOrgType) {
		this.fdOrgType = fdOrgType;
	}

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

	/**
	 * 简称
	 */
	private String fdNameAbbr;

	public String getFdNameAbbr() {
		return fdNameAbbr;
	}

	public void setFdNameAbbr(String fdNameAbbr) {
		this.fdNameAbbr = fdNameAbbr;
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

	private String fdParentOrgId;
	private String fdParentOrgName;

	public String getFdParentOrgId() {
		return fdParentOrgId;
	}

	public void setFdParentOrgId(String fdParentOrgId) {
		this.fdParentOrgId = fdParentOrgId;
	}

	public String getFdParentOrgName() {
		return fdParentOrgName;
	}

	public void setFdParentOrgName(String fdParentOrgName) {
		this.fdParentOrgName = fdParentOrgName;
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
	 * 组织分管领导
	 */
	private String fdBranLeaderId;

	private String fdBranLeaderName;

	public String getFdBranLeaderId() {
		return fdBranLeaderId;
	}

	public String getFdBranLeaderName() {
		return fdBranLeaderName;
	}

	public void setFdBranLeaderId(String fdBranLeaderId) {
		this.fdBranLeaderId = fdBranLeaderId;
	}

	public void setFdBranLeaderName(String fdBranLeaderName) {
		this.fdBranLeaderName = fdBranLeaderName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdNo = null;
		fdOrder = null;
		fdKeyword = null;
		fdIsAvailable = "true";
		fdIsBusiness = "true";
		fdMemo = null;
		fdParentId = null;
		fdParentName = null;
		fdThisLeaderId = null;
		fdThisLeaderName = null;
		fdOrgEmail = null;
		fdIsCompileOpen = null;
		fdIsLimitNum = null;
		fdCompileNum = null;
		fdBranLeaderId = null;
		fdBranLeaderName = null;
		fdIsVirOrg = null;
		fdIsCorp = null;
		fdCreateTime = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.addNoConvertProperty("fdCreateTime");
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", HrOrganizationElement.class));
			toModelPropertyMap.put("fdThisLeaderId",
					new FormConvertor_IDToModel("hbmThisLeader",
							HrOrganizationElement.class));
			toModelPropertyMap.put("fdBranLeaderId",
					new FormConvertor_IDToModel("fdBranLeader", HrOrganizationElement.class));
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

	/*****************************编制begin******************************/
	/**
	 * 是否开启编制
	 */
	private String fdIsCompileOpen;

	/**
	 * 是否限定人数
	 */
	private String fdIsLimitNum;

	/**
	 * 编制人数
	 */
	private Integer fdCompileNum;

	public String getFdIsCompileOpen() {
		return fdIsCompileOpen;
	}

	public String getFdIsLimitNum() {
		return fdIsLimitNum;
	}

	public Integer getFdCompileNum() {
		return fdCompileNum;
	}

	public void setFdIsCompileOpen(String fdIsCompileOpen) {
		this.fdIsCompileOpen = fdIsCompileOpen;
	}

	public void setFdIsLimitNum(String fdIsLimitNum) {
		this.fdIsLimitNum = fdIsLimitNum;
	}

	public void setFdCompileNum(Integer fdCompileNum) {
		this.fdCompileNum = fdCompileNum;
	}

	/*****************************编制end******************************/

	/**
	 * 是否虚拟组织
	 */
	private String fdIsVirOrg;

	/**
	 * 是否法人公司
	 */
	private String fdIsCorp;

	public String getFdIsVirOrg() {
		return fdIsVirOrg;
	}

	public String getFdIsCorp() {
		return fdIsCorp;
	}

	public void setFdIsVirOrg(String fdIsVirOrg) {
		this.fdIsVirOrg = fdIsVirOrg;
	}

	public void setFdIsCorp(String fdIsCorp) {
		this.fdIsCorp = fdIsCorp;
	}


	/* 文件导入 */
	protected FormFile file = null;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	/* 文件导入 */

	private String fdCreateTime;

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

}
