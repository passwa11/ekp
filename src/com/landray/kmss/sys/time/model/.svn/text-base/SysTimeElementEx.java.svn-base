package com.landray.kmss.sys.time.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.time.forms.SysTimeElementExForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 换班信息表
  */
public class SysTimeElementEx extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private Date fdSchduleDate;

    private Integer fdType;

    private Integer fdWorkType;

    private SysOrgPerson docCreator;

    private SysTimeWork fdWork;

    private SysTimePatchwork fdPatchwork;

    private SysTimeVacation fdVacation;

    private SysTimeOrgElementTime fdOrgElementTime;

    private SysTimeBusinessEx fdTimeBusiness;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<SysTimeElementExForm> getFormClass() {
        return SysTimeElementExForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdSchduleDate", new ModelConvertor_Common("fdSchduleDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdWork.fdName", "fdWorkName");
            toFormPropertyMap.put("fdWork.fdId", "fdWorkId");
            toFormPropertyMap.put("fdPatchwork.fdName", "fdPatchworkName");
            toFormPropertyMap.put("fdPatchwork.fdId", "fdPatchworkId");
            toFormPropertyMap.put("fdVacation.fdType", "fdVacationName");
            toFormPropertyMap.put("fdVacation.fdId", "fdVacationId");
            toFormPropertyMap.put("fdOrgElementTime.fdName", "fdOrgElementTimeName");
            toFormPropertyMap.put("fdOrgElementTime.fdId", "fdOrgElementTimeId");
            toFormPropertyMap.put("fdTimeBusiness.docSubject", "fdTimeBusinessName");
            toFormPropertyMap.put("fdTimeBusiness.fdId", "fdTimeBusinessId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 换班日期
     */
    public Date getFdSchduleDate() {
        return this.fdSchduleDate;
    }

    /**
     * 换班日期
     */
    public void setFdSchduleDate(Date fdSchduleDate) {
        this.fdSchduleDate = fdSchduleDate;
    }

    /**
     * 类型
     */
    public Integer getFdType() {
        return this.fdType;
    }

    /**
     * 类型 1:换班，2：还班
     */
    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    /**
     * 日期类型
     */
    public Integer getFdWorkType() {
        return this.fdWorkType;
    }

    /**
     * 日期类型  1：工作日:2：假期:3：补班）
     */
    public void setFdWorkType(Integer fdWorkType) {
        this.fdWorkType = fdWorkType;
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
     * 班次
     */
    public SysTimeWork getFdWork() {
        return this.fdWork;
    }

    /**
     * 班次
     */
    public void setFdWork(SysTimeWork fdWork) {
        this.fdWork = fdWork;
    }

    /**
     * 补班
     */
    public SysTimePatchwork getFdPatchwork() {
        return this.fdPatchwork;
    }

    /**
     * 补班
     */
    public void setFdPatchwork(SysTimePatchwork fdPatchwork) {
        this.fdPatchwork = fdPatchwork;
    }

    /**
     * 节假日
     */
    public SysTimeVacation getFdVacation() {
        return this.fdVacation;
    }

    /**
     * 节假日
     */
    public void setFdVacation(SysTimeVacation fdVacation) {
        this.fdVacation = fdVacation;
    }

    /**
     * 人员排班
     */
    public SysTimeOrgElementTime getFdOrgElementTime() {
        return this.fdOrgElementTime;
    }

    /**
     * 人员排班
     */
    public void setFdOrgElementTime(SysTimeOrgElementTime fdOrgElementTime) {
        this.fdOrgElementTime = fdOrgElementTime;
    }

    /**
     * 换班流程信息
     */
    public SysTimeBusinessEx getFdTimeBusiness() {
        return this.fdTimeBusiness;
    }

    /**
     * 换班流程信息
     */
    public void setFdTimeBusiness(SysTimeBusinessEx fdTimeBusiness) {
        this.fdTimeBusiness = fdTimeBusiness;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
