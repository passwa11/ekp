package com.landray.kmss.sys.attend.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendRestatLog;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 重新统计日志表
 * @author 王京
 * @date 2021-01-14
 */
public class SysAttendRestatLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String docCreateTime;

    private String docAlterTime;

    private String fdBeginDate;

    private String fdEndDate;

    private String fdCategoryName;

    private String fdStatUserNames;

    private String fdStatus;

    private String fdCreateMiss;

    private String docCreatorId;

    private String docCreatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        docCreateTime = null;
        docAlterTime = null;
        fdBeginDate = null;
        fdEndDate = null;
        fdCategoryName = null;
        fdStatUserNames = null;
        fdStatus = null;
        fdCreateMiss = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysAttendRestatLog> getModelClass() {
        return SysAttendRestatLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdBeginDate", new FormConvertor_Common("fdBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdEndDate", new FormConvertor_Common("fdEndDate").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 统计开始时间
     */
    public String getFdBeginDate() {
        return this.fdBeginDate;
    }

    /**
     * 统计开始时间
     */
    public void setFdBeginDate(String fdBeginDate) {
        this.fdBeginDate = fdBeginDate;
    }

    /**
     * 统计结束时间
     */
    public String getFdEndDate() {
        return this.fdEndDate;
    }

    /**
     * 统计结束时间
     */
    public void setFdEndDate(String fdEndDate) {
        this.fdEndDate = fdEndDate;
    }

    /**
     * 考勤组
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 考勤组
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }

    /**
     * 考勤组人员
     */
    public String getFdStatUserNames() {
        return this.fdStatUserNames;
    }

    /**
     * 考勤组人员
     */
    public void setFdStatUserNames(String fdStatUserNames) {
        this.fdStatUserNames = fdStatUserNames;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 重新生成缺卡记录
     */
    public String getFdCreateMiss() {
        return this.fdCreateMiss;
    }

    /**
     * 重新生成缺卡记录
     */
    public void setFdCreateMiss(String fdCreateMiss) {
        this.fdCreateMiss = fdCreateMiss;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }


}
