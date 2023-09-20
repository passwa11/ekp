package com.landray.kmss.km.imeeting.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingOutsignForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;

/**
  * 签订服务记录
  */
public class KmImeetingOutsign extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdMainid;

    private String fdStatus;

    private String fdUrl;

    private String fdExtmsg;

    private Date docCreateTime;

	private SysOrgElement docCreator;// 签署人

	private String fdType;

	@Override
    public Class<KmImeetingOutsignForm> getFormClass() {
		return KmImeetingOutsignForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 主文档id
     */
    public String getFdMainid() {
        return this.fdMainid;
    }

    /**
     * 主文档id
     */
    public void setFdMainid(String fdMainid) {
        this.fdMainid = fdMainid;
    }

    /**
     * 状态码
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态码
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 回调地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 回调地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 扩展信息
     */
    public String getFdExtmsg() {
        return this.fdExtmsg;
    }

    /**
     * 扩展信息
     */
    public void setFdExtmsg(String fdExtmsg) {
        this.fdExtmsg = fdExtmsg;
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
	 * 签署人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * 签署人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 签订类型
	 */
	public String getFdType() {
		return this.fdType;
	}

	/**
	 * 签订类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
}
