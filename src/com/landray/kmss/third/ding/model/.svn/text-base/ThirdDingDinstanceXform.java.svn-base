package com.landray.kmss.third.ding.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.forms.ThirdDingDinstanceXformForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉流程实例
  */
public class ThirdDingDinstanceXform extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdInstanceId;

    private String fdDingUserId;

    private String fdEkpInstanceId;

    private String fdUrl;

    private Date docCreateTime;

    private String fdConfig;

	private String fdStatus;

	private ThirdDingDtemplateXform fdTemplate;

    private SysOrgElement fdEkpUser;

    private List<ThirdDingIndanceXDetail> fdDetail;

    @Override
    public Class<ThirdDingDinstanceXformForm> getFormClass() {
        return ThirdDingDinstanceXformForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
            toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
            toFormPropertyMap.put("fdEkpUser.fdName", "fdEkpUserName");
            toFormPropertyMap.put("fdEkpUser.fdId", "fdEkpUserId");
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

	/**
	 * 状态 已拒绝(00) 待审 (20) 已通过(30)
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
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
     * 实例Id
     */
    public String getFdInstanceId() {
        return this.fdInstanceId;
    }

    /**
     * 实例Id
     */
    public void setFdInstanceId(String fdInstanceId) {
        this.fdInstanceId = fdInstanceId;
    }

    /**
     * 发起人dingId
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 发起人dingId
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }

    /**
     * 文档Id
     */
    public String getFdEkpInstanceId() {
        return this.fdEkpInstanceId;
    }

    /**
     * 文档Id
     */
    public void setFdEkpInstanceId(String fdEkpInstanceId) {
        this.fdEkpInstanceId = fdEkpInstanceId;
    }

    /**
     * 文档地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 文档地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
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
     * 配置信息
     */
    public String getFdConfig() {
        return this.fdConfig;
    }

    /**
     * 配置信息
     */
    public void setFdConfig(String fdConfig) {
        this.fdConfig = fdConfig;
    }

    /**
     * 所属模板
     */
    public ThirdDingDtemplateXform getFdTemplate() {
        return this.fdTemplate;
    }

    /**
     * 所属模板
     */
    public void setFdTemplate(ThirdDingDtemplateXform fdTemplate) {
        this.fdTemplate = fdTemplate;
    }

    /**
     * 发起人
     */
    public SysOrgElement getFdEkpUser() {
        return this.fdEkpUser;
    }

    /**
     * 发起人
     */
    public void setFdEkpUser(SysOrgElement fdEkpUser) {
        this.fdEkpUser = fdEkpUser;
    }

    /**
     * 流程实例明细表
     */
    public List<ThirdDingIndanceXDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 流程实例明细表
     */
    public void setFdDetail(List<ThirdDingIndanceXDetail> fdDetail) {
        this.fdDetail = fdDetail;
    }
}
