package com.landray.kmss.sys.time.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.time.forms.SysTimeBusinessExForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 换班流程信息
  */
public class SysTimeBusinessEx extends BaseModel implements IAttachment, ISysNotifyModel, ISysQuartzModel {
	private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private Date fdReturnDate;

    private Date fdExchangeDate;

    private Integer fdStatus;

    private String fdMsg;

    private String fdFlowId;

    private String fdFlowName;

    private Integer fdChangeType;

    private SysOrgPerson docCreator;

    private SysOrgElement fdApplyPerson;

    private SysOrgElement fdExchangePerson;
    
    private String fdDocUrl;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<SysTimeBusinessExForm> getFormClass() {
        return SysTimeBusinessExForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdReturnDate", new ModelConvertor_Common("fdReturnDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdExchangeDate", new ModelConvertor_Common("fdExchangeDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdApplyPerson.fdName", "fdApplyPersonName");
            toFormPropertyMap.put("fdApplyPerson.fdId", "fdApplyPersonId");
            toFormPropertyMap.put("fdExchangePerson.fdName", "fdExchangePersonName");
            toFormPropertyMap.put("fdExchangePerson.fdId", "fdExchangePersonId");
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
     * 还班时间
     */
    public Date getFdReturnDate() {
        return this.fdReturnDate;
    }

    /**
     * 还班时间
     */
    public void setFdReturnDate(Date fdReturnDate) {
        this.fdReturnDate = fdReturnDate;
    }

    /**
     * 换班日期
     */
    public Date getFdExchangeDate() {
        return this.fdExchangeDate;
    }

    /**
     * 换班日期
     */
    public void setFdExchangeDate(Date fdExchangeDate) {
        this.fdExchangeDate = fdExchangeDate;
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 备注
     */
    public String getFdMsg() {
        return this.fdMsg;
    }

    /**
     * 备注
     */
    public void setFdMsg(String fdMsg) {
        this.fdMsg = fdMsg;
    }

    /**
     * 流程id
     */
    public String getFdFlowId() {
        return this.fdFlowId;
    }

    /**
     * 流程id
     */
    public void setFdFlowId(String fdFlowId) {
        this.fdFlowId = fdFlowId;
    }

    /**
     * 流程名称
     */
    public String getFdFlowName() {
        return this.fdFlowName;
    }

    /**
     * 流程名称
     */
    public void setFdFlowName(String fdFlowName) {
        this.fdFlowName = fdFlowName;
    }

    /**
     * 类型
     */
    public Integer getFdChangeType() {
        return this.fdChangeType;
    }

    /**
     * 类型 1:换班。2：换班
     */
    public void setFdChangeType(Integer fdChangeType) {
        this.fdChangeType = fdChangeType;
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
     * 申请人
     */
    public SysOrgElement getFdApplyPerson() {
        return this.fdApplyPerson;
    }

    /**
     * 申请人
     */
    public void setFdApplyPerson(SysOrgElement sysOrgElement) {
        this.fdApplyPerson = sysOrgElement;
    }

    /**
     * 替班人
     */
    public SysOrgElement getFdExchangePerson() {
        return this.fdExchangePerson;
    }

    /**
     * 替班人
     */
    public void setFdExchangePerson(SysOrgElement fdExchangePerson) {
        this.fdExchangePerson = fdExchangePerson;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

	public String getFdDocUrl() {
		return fdDocUrl;
	}

	public void setFdDocUrl(String fdDocUrl) {
		this.fdDocUrl = fdDocUrl;
	}
}
