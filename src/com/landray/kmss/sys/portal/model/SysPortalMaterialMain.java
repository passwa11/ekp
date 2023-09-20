package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.forms.SysPortalMaterialMainForm;
import com.landray.kmss.util.DateUtil;

/**
  * 素材库
  */
public class SysPortalMaterialMain extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Date docCreateTime;

    private Date docAlterTime;

    private Long fdSize;

    private String fdType;

    private Double fdWidth;

    private Double fdLength;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

	private String fdAttId;

    public String getFdImgUrl() {
        return fdImgUrl;
    }

    public void setFdImgUrl(String fdImgUrl) {
        this.fdImgUrl = fdImgUrl;
    }

    private String fdImgUrl;//素材库内置图标路径



	// tags
	private List<SysPortalMaterialTag> fdTags = new ArrayList<SysPortalMaterialTag>();

    @Override
    public Class<SysPortalMaterialMainForm> getFormClass() {
        return SysPortalMaterialMainForm.class;
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
			toFormPropertyMap.put("fdTags",
					new ModelConvertor_ModelListToString("fdTagIds:fdTagNames",
							"fdId:fdName"));
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
     * 大小
     */
    public Long getFdSize() {
        return this.fdSize;
    }

    /**
     * 大小
     */
    public void setFdSize(Long fdSize) {
        this.fdSize = fdSize;
    }

    /**
     * 类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 宽
     */
    public Double getFdWidth() {
        return this.fdWidth;
    }

    /**
     * 宽
     */
    public void setFdWidth(Double fdWidth) {
        this.fdWidth = fdWidth;
    }

    /**
     * 长
     */
    public Double getFdLength() {
        return this.fdLength;
    }

    /**
     * 长
     */
    public void setFdLength(Double fdLength) {
        this.fdLength = fdLength;
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
	 * 附件Id
	 */
	public String getFdAttId() {
		return fdAttId;
	}

	/**
	 * 附件Id
	 */
	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}

	/**
	 * 素材标签引用
	 */
	public List<SysPortalMaterialTag> getFdTags() {
		return this.fdTags;
    }

	/**
	 * 素材标签引用
	 */
	public void setFdTags(List<SysPortalMaterialTag> fdTags) {
		this.fdTags = fdTags;
    }
}
