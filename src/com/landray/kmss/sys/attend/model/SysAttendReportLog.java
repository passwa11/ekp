package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attend.forms.SysAttendReportLogForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
 * 考勤记录导出记录表
 *@author 王京
 *@date 2021-10-13
 */
public class SysAttendReportLog extends BaseModel implements IAttachment, ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Date docCreateTime;

    private String fdDesc;

    private String fdFileName;

    private Integer fdStatus;

    private String fdType;

    private String fdNo;

    private SysOrgPerson docCreator;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<SysAttendReportLogForm> getFormClass() {
        return SysAttendReportLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
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
     * 错误信息
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 错误信息
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 文件名称
     */
    public String getFdFileName() {
        return this.fdFileName;
    }

    /**
     * 文件名称
     */
    public void setFdFileName(String fdFileName) {
        this.fdFileName = fdFileName;
    }

    /**
     * 状态标识
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态标识
     * 0是未执行
     * 1是执行成功
     * 2是执行失败
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 1是每日汇总
     * 2是每月汇总
     * 其他fdId为考勤报表的主键
     * 类型 --因为月报表有具体的ID。所以这里存储类型或者ID
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
     * 编号
     */
    public String getFdNo() {
        return this.fdNo;
    }

    /**
     * 编号
     */
    public void setFdNo(String fdNo) {
        this.fdNo = fdNo;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
