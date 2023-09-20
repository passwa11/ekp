package com.landray.kmss.third.feishu.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.feishu.forms.ThirdFeishuPersonMappingForm;
import com.landray.kmss.util.DateUtil;

/**
  * 人员映射
  */
public class ThirdFeishuPersonMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

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

    private String fdLoginName;

    private String fdMobileNo;

    private SysOrgElement fdEkp;

    @Override
    public Class<ThirdFeishuPersonMappingForm> getFormClass() {
        return ThirdFeishuPersonMappingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("docAlterTime",
					new ModelConvertor_Common("docAlterTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdEkp.fdName", "fdEkpName");
            toFormPropertyMap.put("fdEkp.fdId", "fdEkpId");
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
    public SysOrgElement getFdEkp() {
        return this.fdEkp;
    }

    /**
     * ekp人员
     */
    public void setFdEkp(SysOrgElement fdEkp) {
        this.fdEkp = fdEkp;
    }

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	private Date docAlterTime;
}
