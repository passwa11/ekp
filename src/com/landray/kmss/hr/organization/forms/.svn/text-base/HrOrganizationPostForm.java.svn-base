package com.landray.kmss.hr.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationPostSeq;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 人员岗位信息
  */
public class HrOrganizationPostForm extends HrOrganizationElementForm {

	/*
	 * 个人列表
	 */
	private String fdPersonIds;

	private String fdOldPersonIds;

	private String fdPersonNames;

	/**
	 * 岗位序列
	 */
	private String fdPostSeqId;

	private String fdPostSeqName;

	/**
	 * 职级范围-下限
	 */
	private String fdRankMixId;

	private String fdRankMixName;

	/**
	 * 职级范围-上限
	 */
	private String fdRankMaxId;

	private String fdRankMaxName;

	/**
	 * 是否关键岗位
	 */
	private String fdIsKey;

	/**
	 * 是否涉密岗位
	 */
	private String fdIsSecret;

	/**
	 * 岗位满编设置
	 */
	private String fdIsFullCompile;

	private String fdRankMixWeight;

	private String fdRankMaxWeight;

	public String getFdRankMixWeight() {
		return fdRankMixWeight;
	}

	public void setFdRankMixWeight(String fdRankMixWeight) {
		this.fdRankMixWeight = fdRankMixWeight;
	}

	public String getFdRankMaxWeight() {
		return fdRankMaxWeight;
	}

	public void setFdRankMaxWeight(String fdRankMaxWeight) {
		this.fdRankMaxWeight = fdRankMaxWeight;
	}

	public String getFdIsFullCompile() {
		return fdIsFullCompile;
	}

	public void setFdIsFullCompile(String fdIsFullCompile) {
		this.fdIsFullCompile = fdIsFullCompile;
	}

	public String getFdPersonIds() {
		return fdPersonIds;
	}

	public void setFdPersonIds(String fdPersonIds) {
		this.fdPersonIds = fdPersonIds;
	}

	public String getFdPersonNames() {
		return fdPersonNames;
	}

	public void setFdPersonNames(String fdPersonNames) {
		this.fdPersonNames = fdPersonNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPersonIds = null;
		fdPersonNames = null;
		fdRankMaxId = null;
		fdRankMixId = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return HrOrganizationPost.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonIds",
					new FormConvertor_IDsToModelList("fdPersons", HrStaffPersonInfo.class));
			toModelPropertyMap.put("fdPostSeqId",
					new FormConvertor_IDToModel("fdPostSeq", HrOrganizationPostSeq.class));
			toModelPropertyMap.put("fdRankMixId", new FormConvertor_IDToModel("fdRankMix", HrOrganizationRank.class));
			toModelPropertyMap.put("fdRankMaxId", new FormConvertor_IDToModel("fdRankMax", HrOrganizationRank.class));
		}
		return toModelPropertyMap;
	}

	public String getFdOldPersonIds() {
		return fdOldPersonIds;
	}

	public String getFdPostSeqId() {
		return fdPostSeqId;
	}

	public String getFdPostSeqName() {
		return fdPostSeqName;
	}

	public String getFdRankMixId() {
		return fdRankMixId;
	}

	public String getFdRankMixName() {
		return fdRankMixName;
	}

	public String getFdRankMaxId() {
		return fdRankMaxId;
	}

	public String getFdRankMaxName() {
		return fdRankMaxName;
	}

	public String getFdIsKey() {
		return fdIsKey;
	}

	public String getFdIsSecret() {
		return fdIsSecret;
	}

	public void setFdOldPersonIds(String fdOldPersonIds) {
		this.fdOldPersonIds = fdOldPersonIds;
	}

	public void setFdPostSeqId(String fdPostSeqId) {
		this.fdPostSeqId = fdPostSeqId;
	}

	public void setFdPostSeqName(String fdPostSeqName) {
		this.fdPostSeqName = fdPostSeqName;
	}

	public void setFdRankMixId(String fdRankMixId) {
		this.fdRankMixId = fdRankMixId;
	}

	public void setFdRankMixName(String fdRankMixName) {
		this.fdRankMixName = fdRankMixName;
	}

	public void setFdRankMaxId(String fdRankMaxId) {
		this.fdRankMaxId = fdRankMaxId;
	}

	public void setFdRankMaxName(String fdRankMaxName) {
		this.fdRankMaxName = fdRankMaxName;
	}

	public void setFdIsKey(String fdIsKey) {
		this.fdIsKey = fdIsKey;
	}

	public void setFdIsSecret(String fdIsSecret) {
		this.fdIsSecret = fdIsSecret;
	}

}
