package com.landray.kmss.sys.attend.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
  * 考勤组变更记录表
 * @author wj
  */
public class SysAttendHisCategoryForm  extends ExtendForm {

    /**
     * 考勤组名称冗余存储
     */
    private String fdName;
    /**
     * 原考勤组ID
     */
    private String fdCategoryId;

    private String fdCategoryContent;
    /**
     * 历史版本的开始时间
     */
    private Date fdBeginTime;
    /**
     * 历史版本的结束时间
     */
    private Date fdEndTime;

    private Date docAlterTime;
    /**
     * 签到类型【1、考勤组，2是签到组】冗余存储
     */
    private Integer fdType;

    /**
     * 外勤是否需审批。0不审批，1需审批 冗余存储，因为有业务直接关联
     */
    private Integer fdOsdReviewType;

    private SysAttendCategoryForm sysAttendCategoryForm;

    public SysAttendCategoryForm getSysAttendCategoryForm() {
        return sysAttendCategoryForm;
    }

    public void setSysAttendCategoryForm(SysAttendCategoryForm sysAttendCategoryForm) {
        this.sysAttendCategoryForm = sysAttendCategoryForm;
    }

    @Override
    public Class<SysAttendHisCategory> getModelClass() {
        return SysAttendHisCategory.class;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdCategoryId() {
        return fdCategoryId;
    }

    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    public String getFdCategoryContent() {
        return fdCategoryContent;
    }

    public void setFdCategoryContent(String fdCategoryContent) {
        this.fdCategoryContent = fdCategoryContent;
    }

    public Date getFdBeginTime() {
        return fdBeginTime;
    }

    public void setFdBeginTime(Date fdBeginTime) {
        this.fdBeginTime = fdBeginTime;
    }

    public Date getFdEndTime() {
        return fdEndTime;
    }

    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    public Date getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    public Integer getFdType() {
        return fdType;
    }

    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    public Integer getFdOsdReviewType() {
        return fdOsdReviewType;
    }

    public void setFdOsdReviewType(Integer fdOsdReviewType) {
        this.fdOsdReviewType = fdOsdReviewType;
    }

    private static FormToModelPropertyMap toModelPropertyMap;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.sysAttendCategoryForm =new SysAttendCategoryForm();
        this.fdOsdReviewType =null;
    }
    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }
}
