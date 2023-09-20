package com.landray.kmss.hr.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.organization.forms.HrOrganizationPostForm;
import com.landray.kmss.hr.organization.interfaces.IHrOrgPost;

/**
  * 人员岗位信息
  */
public class HrOrganizationPost extends HrOrganizationElement implements IHrOrgPost {

	public HrOrganizationPost() {
		super();
		setFdOrgType(new Integer(HR_TYPE_POST));
	}

	@Override
	public Class getFormClass() {
		return HrOrganizationPostForm.class;
	}

	/**
	 * 岗位序列
	 */
	private HrOrganizationPostSeq fdPostSeq;
	private HrOrganizationGrade fdGradeMix;
	private HrOrganizationGrade fdGradeMax;
	/**
	 * 职级范围-下限
	 */
	private HrOrganizationRank fdRankMix;

	/**
	 * 职级范围-上限
	 */
	private HrOrganizationRank fdRankMax;

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

	public HrOrganizationGrade getFdGradeMix() {
		return fdGradeMix;
	}

	public void setFdGradeMix(HrOrganizationGrade fdGradeMix) {
		this.fdGradeMix = fdGradeMix;
	}

	public HrOrganizationGrade getFdGradeMax() {
		return fdGradeMax;
	}

	public void setFdGradeMax(HrOrganizationGrade fdGradeMax) {
		this.fdGradeMax = fdGradeMax;
	}

	public String getFdIsFullCompile() {
		return fdIsFullCompile;
	}

	public void setFdIsFullCompile(String fdIsFullCompile) {
		this.fdIsFullCompile = fdIsFullCompile;
	}

	public HrOrganizationPostSeq getFdPostSeq() {
		return fdPostSeq;
	}

	public HrOrganizationRank getFdRankMix() {
		return fdRankMix;
	}

	public HrOrganizationRank getFdRankMax() {
		return fdRankMax;
	}


	public void setFdPostSeq(HrOrganizationPostSeq fdPostSeq) {
		this.fdPostSeq = fdPostSeq;
	}

	public void setFdRankMix(HrOrganizationRank fdRankMix) {
		this.fdRankMix = fdRankMix;
	}

	public void setFdRankMax(HrOrganizationRank fdRankMax) {
		this.fdRankMax = fdRankMax;
	}

	public String getFdIsKey() {
		return fdIsKey;
	}

	public String getFdIsSecret() {
		return fdIsSecret;
	}

	public void setFdIsKey(String fdIsKey) {
		this.fdIsKey = fdIsKey;
	}

	public void setFdIsSecret(String fdIsSecret) {
		this.fdIsSecret = fdIsSecret;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPersons",
					new ModelConvertor_ModelListToString("fdPersonIds:fdPersonNames", "fdId:fdName"));
			toFormPropertyMap.put("fdPostSeq.fdId", "fdPostSeqId");
			toFormPropertyMap.put("fdPostSeq.fdName", "fdPostSeqName");
			toFormPropertyMap.put("fdRankMix.fdId", "fdRankMixId");
			toFormPropertyMap.put("fdRankMix.fdName", "fdRankMixName");
			toFormPropertyMap.put("fdRankMax.fdId", "fdRankMaxId");
			toFormPropertyMap.put("fdRankMax.fdName", "fdRankMaxName");
			toFormPropertyMap.put("fdGradeMax.fdId", "fdGradeMaxId");
			toFormPropertyMap.put("fdGradeMax.fdName", "fdGradeMaxName");
			toFormPropertyMap.put("fdGradeMix.fdId", "fdGradeMixId");
			toFormPropertyMap.put("fdGradeMix.fdName", "fdGradeMixName");
			toFormPropertyMap.put("fdRankMix.fdWeight", "fdRankMixWeight");
			toFormPropertyMap.put("fdRankMax.fdWeight", "fdRankMaxWeight");
		}
		return toFormPropertyMap;
	}
	
}
