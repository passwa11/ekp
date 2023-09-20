package com.landray.kmss.hr.organization.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.organization.forms.HrOrganizationRankForm;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;

/**
  * 职级
  */
public class HrOrganizationRank extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

    private Date docCreateTime;

    private Date docAlterTime;

    private HrOrganizationGrade fdGrade;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

	private Integer fdWeight;

	public Integer getFdWeight() {
		return fdWeight;
	}

	public void setFdWeight(Integer fdWeight) {
		this.fdWeight = fdWeight;
	}

	@Override
    public Class<HrOrganizationRankForm> getFormClass() {
        return HrOrganizationRankForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdGrade.fdName", "fdGradeName");
            toFormPropertyMap.put("fdGrade.fdId", "fdGradeId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 岗位职级
     */
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

	/**
	 * 创建时间
	 */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 职等
     */
    public HrOrganizationGrade getFdGrade() {
        return this.fdGrade;
    }

    /**
     * 职等
     */
    public void setFdGrade(HrOrganizationGrade fdGrade) {
        this.fdGrade = fdGrade;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

	//数据是否关联
	private boolean fdIsUse;

	public boolean isFdIsUse() throws Exception {
		IHrOrganizationPostService hrOrganizationPostService = (IHrOrganizationPostService) SpringBeanUtil
				.getBean("hrOrganizationPostService");
		return hrOrganizationPostService.getPostAndPersonByRankId(this.fdId);
	}

	public void setFdIsUse(boolean fdIsUse) {
		this.fdIsUse = fdIsUse;
	}
}
