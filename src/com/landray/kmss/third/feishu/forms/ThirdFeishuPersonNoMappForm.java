package com.landray.kmss.third.feishu.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonNoMapp;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 人员未匹配数据
  */
public class ThirdFeishuPersonNoMappForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docAlterTime;

    private String fdFeishuName;

    private String fdFeishuMobileNo;

    private String fdEmail;

    private String fdFeishuNo;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docAlterTime = null;
        fdFeishuName = null;
        fdFeishuMobileNo = null;
        fdEmail = null;
        fdFeishuNo = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuPersonNoMapp> getModelClass() {
        return ThirdFeishuPersonNoMapp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.addNoConvertProperty("fdFeishuName");
            toModelPropertyMap.addNoConvertProperty("fdFeishuId");
            toModelPropertyMap.addNoConvertProperty("fdFeishuMobileNo");
            toModelPropertyMap.addNoConvertProperty("fdEmail");
        }
        return toModelPropertyMap;
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
