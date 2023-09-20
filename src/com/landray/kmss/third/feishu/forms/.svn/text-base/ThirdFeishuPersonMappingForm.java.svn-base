package com.landray.kmss.third.feishu.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 人员映射
  */
public class ThirdFeishuPersonMappingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdLoginName;

    private String fdMobileNo;

    private String fdEkpId;

    private String fdEkpName;

	private String fdOpenId;

	private String fdEmployeeId;

	private String docAlterTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
		setFdOpenId(null);
		setFdEmployeeId(null);
        fdLoginName = null;
        fdMobileNo = null;
        fdEkpId = null;
        fdEkpName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuPersonMapping> getModelClass() {
        return ThirdFeishuPersonMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.addNoConvertProperty("fdFeishuId");
			toModelPropertyMap.put("fdEkpId",
					new FormConvertor_IDToModel("fdEkp",
							SysOrgElement.class));
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
     * 登录名
     */
    public String getFdLoginName() {
        return this.fdLoginName;
    }

    /**
     * 登录名
     */
    public void setFdLoginName(String fdLoginName) {
        this.fdLoginName = fdLoginName;
    }

    /**
     * 手机号
     */
    public String getFdMobileNo() {
        return this.fdMobileNo;
    }

    /**
     * 手机号
     */
    public void setFdMobileNo(String fdMobileNo) {
        this.fdMobileNo = fdMobileNo;
    }

    /**
     * ekp人员
     */
    public String getFdEkpId() {
        return this.fdEkpId;
    }

    /**
     * ekp人员
     */
    public void setFdEkpId(String fdEkpId) {
        this.fdEkpId = fdEkpId;
    }

    /**
     * ekp人员
     */
    public String getFdEkpName() {
        return this.fdEkpName;
    }

    /**
     * ekp人员
     */
    public void setFdEkpName(String fdEkpName) {
        this.fdEkpName = fdEkpName;
    }

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

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
}
