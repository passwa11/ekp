package com.landray.kmss.third.ding.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.forms.ThirdDingOrmTempForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉流程模板映射
  */
public class ThirdDingOrmTemp extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdOrder;

    private String fdTemplateId;

    private String fdProcessCode;

	private String fdProcessName;

    private String fdDingTemplateType;

    private String fdStartFlow;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<ThirdDingOrmDe> fdDetail;

    @Override
    public Class<ThirdDingOrmTempForm> getFormClass() {
        return ThirdDingOrmTempForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * EKP流程模板Id
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * EKP流程模板Id
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 钉钉流程模板Code
     */
    public String getFdProcessCode() {
        return this.fdProcessCode;
    }

    /**
     * 钉钉流程模板Code
     */
    public void setFdProcessCode(String fdProcessCode) {
        this.fdProcessCode = fdProcessCode;
    }

    /**
     * 钉钉模板类型
     */
    public String getFdDingTemplateType() {
        return this.fdDingTemplateType;
    }

    /**
     * 钉钉模板类型
     */
    public void setFdDingTemplateType(String fdDingTemplateType) {
        this.fdDingTemplateType = fdDingTemplateType;
    }

    /**
     * 流程启动方
     */
    public String getFdStartFlow() {
        return this.fdStartFlow;
    }

    /**
     * 流程启动方
     */
    public void setFdStartFlow(String fdStartFlow) {
        this.fdStartFlow = fdStartFlow;
    }

    /**
     * 是否有效
     */
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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

	public String getFdProcessName() {
		return fdProcessName;
	}

	public void setFdProcessName(String fdProcessName) {
		this.fdProcessName = fdProcessName;
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

    /**
     * 流程模板字段映射
     */
    public List<ThirdDingOrmDe> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 流程模板字段映射
     */
    public void setFdDetail(List<ThirdDingOrmDe> fdDetail) {
        this.fdDetail = fdDetail;
    }
}
