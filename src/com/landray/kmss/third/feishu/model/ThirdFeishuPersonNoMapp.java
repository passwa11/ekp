package com.landray.kmss.third.feishu.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.feishu.forms.ThirdFeishuPersonNoMappForm;
import com.landray.kmss.util.DateUtil;

/**
  * 人员未匹配数据
  */
public class ThirdFeishuPersonNoMapp extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docAlterTime;

    private String fdFeishuName;

    private String fdFeishuMobileNo;

    private String fdEmail;

    private String fdFeishuNo;

    @Override
    public Class<ThirdFeishuPersonNoMappForm> getFormClass() {
        return ThirdFeishuPersonNoMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 飞书人员名称
     */
    public String getFdFeishuName() {
        return this.fdFeishuName;
    }

    /**
     * 飞书人员名称
     */
    public void setFdFeishuName(String fdFeishuName) {
        this.fdFeishuName = fdFeishuName;
    }

	private String fdOpenId;

	public String getFdOpenId() {
		return fdOpenId;
	}

	public void setFdOpenId(String fdOpenId) {
		this.fdOpenId = fdOpenId;
	}

	public String getFdEmployeeId() {
		return fdEmployeeId;
	}

	public void setFdEmployeeId(String fdEmployeeId) {
		this.fdEmployeeId = fdEmployeeId;
	}

	private String fdEmployeeId;

    /**
     * 飞书手机号
     */
    public String getFdFeishuMobileNo() {
        return this.fdFeishuMobileNo;
    }

    /**
     * 飞书手机号
     */
    public void setFdFeishuMobileNo(String fdFeishuMobileNo) {
        this.fdFeishuMobileNo = fdFeishuMobileNo;
    }

    /**
     * 飞书邮箱
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 飞书邮箱
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 飞书工号
     */
    public String getFdFeishuNo() {
        return this.fdFeishuNo;
    }

    /**
     * 飞书工号
     */
    public void setFdFeishuNo(String fdFeishuNo) {
        this.fdFeishuNo = fdFeishuNo;
    }
}
