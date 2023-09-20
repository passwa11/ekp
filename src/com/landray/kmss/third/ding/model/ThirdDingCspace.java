package com.landray.kmss.third.ding.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.forms.ThirdDingCspaceForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉盘文件日志
  */
public class ThirdDingCspace extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdMediaId;

    private String fdAttId;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdName;

    private String fdStatus;

    private SysOrgElement fdCreater;

	private String fdFileId;

    private String fdDingFileId;

    private String fdErrorMsg;

    public String getFdErrorMsg() {
        return fdErrorMsg;
    }

    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    /**
     *  0:表示上传到钉盘，1表示从钉盘下载
     */
    private String fdType;

    public String getFdDingFileId() {
        return fdDingFileId;
    }

    public void setFdDingFileId(String fdDingFileId) {
        this.fdDingFileId = fdDingFileId;
    }

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    public String getFdFileId() {
		return fdFileId;
	}

	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId;
	}

	@Override
    public Class<ThirdDingCspaceForm> getFormClass() {
        return ThirdDingCspaceForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCreater.fdName", "fdCreaterName");
            toFormPropertyMap.put("fdCreater.fdId", "fdCreaterId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * mediaId
     */
    public String getFdMediaId() {
        return this.fdMediaId;
    }

    /**
     * mediaId
     */
    public void setFdMediaId(String fdMediaId) {
        this.fdMediaId = fdMediaId;
    }

    /**
     * 附件
     */
    public String getFdAttId() {
        return this.fdAttId;
    }

    /**
     * 附件
     */
    public void setFdAttId(String fdAttId) {
        this.fdAttId = fdAttId;
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
     * 附件状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 附件状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 操作者
     */
    public SysOrgElement getFdCreater() {
        return this.fdCreater;
    }

    /**
     * 操作者
     */
    public void setFdCreater(SysOrgElement fdCreater) {
        this.fdCreater = fdCreater;
    }
}
