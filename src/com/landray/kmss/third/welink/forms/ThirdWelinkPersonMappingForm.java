package com.landray.kmss.third.welink.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 人员映射
  */
public class ThirdWelinkPersonMappingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docAlterTime;

    private String fdWelinkId;

    private String fdLoginName;

    private String fdMobileNo;

	private String fdEkpPersonId;

	private String fdEkpPersonName;

	private String fdWelinkUserId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docAlterTime = null;
        fdWelinkId = null;
        fdLoginName = null;
        fdMobileNo = null;
		setFdEkpPersonId(null);
		setFdEkpPersonName(null);
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkPersonMapping> getModelClass() {
        return ThirdWelinkPersonMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			// toModelPropertyMap.addNoConvertProperty("docAlterTime");
			// toModelPropertyMap.addNoConvertProperty("fdWelinkId");
			// toModelPropertyMap.addNoConvertProperty("fdLoginName");
			// toModelPropertyMap.addNoConvertProperty("fdMobileNo");
			toModelPropertyMap.put("fdEkpPersonId",
					new FormConvertor_IDToModel("fdEkpPerson",
							SysOrgElement.class));
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
     * welink人员ID
     */
    public String getFdWelinkId() {
        return this.fdWelinkId;
    }

    /**
     * welink人员ID
     */
    public void setFdWelinkId(String fdWelinkId) {
        this.fdWelinkId = fdWelinkId;
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

	public String getFdEkpPersonId() {
		return fdEkpPersonId;
	}

	public void setFdEkpPersonId(String fdEkpPersonId) {
		this.fdEkpPersonId = fdEkpPersonId;
	}

	public String getFdEkpPersonName() {
		return fdEkpPersonName;
	}

	public void setFdEkpPersonName(String fdEkpPersonName) {
		this.fdEkpPersonName = fdEkpPersonName;
	}

	public String getFdWelinkUserId() {
		return fdWelinkUserId;
	}

	public void setFdWelinkUserId(String fdWelinkUserId) {
		this.fdWelinkUserId = fdWelinkUserId;
	}

}
