package com.landray.kmss.third.welink.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.welink.forms.ThirdWelinkPersonMappingForm;
import com.landray.kmss.util.DateUtil;

/**
  * 人员映射
  */
public class ThirdWelinkPersonMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docAlterTime;

    private String fdWelinkId;

	private String fdWelinkUserId;

    private String fdLoginName;

    private String fdMobileNo;

	private SysOrgElement fdEkpPerson;

    @Override
    public Class<ThirdWelinkPersonMappingForm> getFormClass() {
        return ThirdWelinkPersonMappingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdEkpPerson.fdName", "fdEkpPersonName");
			toFormPropertyMap.put("fdEkpPerson.fdId", "fdEkpPersonId");
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

	public SysOrgElement getFdEkpPerson() {
		return fdEkpPerson;
	}

	public void setFdEkpPerson(SysOrgElement fdEkpPerson) {
		this.fdEkpPerson = fdEkpPerson;
	}

	public String getFdWelinkUserId() {
		return fdWelinkUserId;
	}

	public void setFdWelinkUserId(String fdWelinkUserId) {
		this.fdWelinkUserId = fdWelinkUserId;
	}

}
