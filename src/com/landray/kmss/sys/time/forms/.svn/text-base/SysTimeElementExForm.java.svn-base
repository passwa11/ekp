package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.sys.time.model.SysTimeElementEx;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.util.AutoHashMap;

/**
  * 换班信息表
  */
public class SysTimeElementExForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdSchduleDate;

    private String fdType;

    private String fdWorkType;

    private String docCreatorId;

    private String docCreatorName;

    private String fdWorkId;

    private String fdWorkName;

    private String fdPatchworkId;

    private String fdPatchworkName;

    private String fdVacationId;

    private String fdVacationName;

    private String fdOrgElementTimeId;

    private String fdOrgElementTimeName;

    private String fdTimeBusinessId;

    private String fdTimeBusinessName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdSchduleDate = null;
        fdType = null;
        fdWorkType = null;
        docCreatorId = null;
        docCreatorName = null;
        fdWorkId = null;
        fdWorkName = null;
        fdPatchworkId = null;
        fdPatchworkName = null;
        fdVacationId = null;
        fdVacationName = null;
        fdOrgElementTimeId = null;
        fdOrgElementTimeName = null;
        fdTimeBusinessId = null;
        fdTimeBusinessName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysTimeElementEx> getModelClass() {
        return SysTimeElementEx.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdSchduleDate", new FormConvertor_Common("fdSchduleDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdWorkId", new FormConvertor_IDToModel("fdWork", SysTimeWork.class));
            toModelPropertyMap.put("fdPatchworkId", new FormConvertor_IDToModel("fdPatchwork", SysTimePatchwork.class));
            toModelPropertyMap.put("fdVacationId", new FormConvertor_IDToModel("fdVacation", SysTimeVacation.class));
            toModelPropertyMap.put("fdOrgElementTimeId", new FormConvertor_IDToModel("fdOrgElementTime", SysTimeOrgElementTime.class));
            toModelPropertyMap.put("fdTimeBusinessId", new FormConvertor_IDToModel("fdTimeBusiness", SysTimeBusinessEx.class));
        }
        return toModelPropertyMap;
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
     * 换班日期
     */
    public String getFdSchduleDate() {
        return this.fdSchduleDate;
    }

    /**
     * 换班日期
     */
    public void setFdSchduleDate(String fdSchduleDate) {
        this.fdSchduleDate = fdSchduleDate;
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
     * 日期类型
     */
    public String getFdWorkType() {
        return this.fdWorkType;
    }

    /**
     * 日期类型
     */
    public void setFdWorkType(String fdWorkType) {
        this.fdWorkType = fdWorkType;
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

    /**
     * 班次
     */
    public String getFdWorkId() {
        return this.fdWorkId;
    }

    /**
     * 班次
     */
    public void setFdWorkId(String fdWorkId) {
        this.fdWorkId = fdWorkId;
    }

    /**
     * 班次
     */
    public String getFdWorkName() {
        return this.fdWorkName;
    }

    /**
     * 班次
     */
    public void setFdWorkName(String fdWorkName) {
        this.fdWorkName = fdWorkName;
    }

    /**
     * 补班
     */
    public String getFdPatchworkId() {
        return this.fdPatchworkId;
    }

    /**
     * 补班
     */
    public void setFdPatchworkId(String fdPatchworkId) {
        this.fdPatchworkId = fdPatchworkId;
    }

    /**
     * 补班
     */
    public String getFdPatchworkName() {
        return this.fdPatchworkName;
    }

    /**
     * 补班
     */
    public void setFdPatchworkName(String fdPatchworkName) {
        this.fdPatchworkName = fdPatchworkName;
    }

    /**
     * 节假日
     */
    public String getFdVacationId() {
        return this.fdVacationId;
    }

    /**
     * 节假日
     */
    public void setFdVacationId(String fdVacationId) {
        this.fdVacationId = fdVacationId;
    }

    /**
     * 节假日
     */
    public String getFdVacationName() {
        return this.fdVacationName;
    }

    /**
     * 节假日
     */
    public void setFdVacationName(String fdVacationName) {
        this.fdVacationName = fdVacationName;
    }

    /**
     * 人员排班
     */
    public String getFdOrgElementTimeId() {
        return this.fdOrgElementTimeId;
    }

    /**
     * 人员排班
     */
    public void setFdOrgElementTimeId(String fdOrgElementTimeId) {
        this.fdOrgElementTimeId = fdOrgElementTimeId;
    }

    /**
     * 人员排班
     */
    public String getFdOrgElementTimeName() {
        return this.fdOrgElementTimeName;
    }

    /**
     * 人员排班
     */
    public void setFdOrgElementTimeName(String fdOrgElementTimeName) {
        this.fdOrgElementTimeName = fdOrgElementTimeName;
    }

    /**
     * 换班流程信息
     */
    public String getFdTimeBusinessId() {
        return this.fdTimeBusinessId;
    }

    /**
     * 换班流程信息
     */
    public void setFdTimeBusinessId(String fdTimeBusinessId) {
        this.fdTimeBusinessId = fdTimeBusinessId;
    }

    /**
     * 换班流程信息
     */
    public String getFdTimeBusinessName() {
        return this.fdTimeBusinessName;
    }

    /**
     * 换班流程信息
     */
    public void setFdTimeBusinessName(String fdTimeBusinessName) {
        this.fdTimeBusinessName = fdTimeBusinessName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
