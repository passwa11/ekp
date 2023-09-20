package com.landray.kmss.third.ding.notify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.notify.forms.ThirdDingNotifyWorkrecordForm;
import com.landray.kmss.util.DateUtil;

/**
  * 钉钉待办表
  */
public class ThirdDingNotifyWorkrecord extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;


	private String fdNotifyId;

    private String fdRecordId;

    private String fdDingUserId;
    
    private Date docCreateTime;
    
	private Boolean fdEKPDel;

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}



    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }


    /**
     * 钉钉业务ID
     */
    public String getFdRecordId() {
        return this.fdRecordId;
    }

    /**
     * 钉钉业务ID
     */
    public void setFdRecordId(String fdRecordId) {
        this.fdRecordId = fdRecordId;
    }

    /**
     * 钉钉通知人
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉通知人
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }

	public String getFdNotifyId() {
		return fdNotifyId;
	}

	public void setFdNotifyId(String fdNotifyId) {
		this.fdNotifyId = fdNotifyId;
	}

	public Boolean isFdEKPDel() {
		return fdEKPDel;
	}

	public Boolean getFdEKPDel() {
		return fdEKPDel;
	}

	public void setFdEKPDel(Boolean fdEKPDel) {
		this.fdEKPDel = fdEKPDel;
	}

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public SysOrgPerson getFdUser() {
		return fdUser;
	}

	public void setFdUser(SysOrgPerson fdUser) {
		this.fdUser = fdUser;
	}

	private String fdSubject;

	private SysOrgPerson fdUser;

	@Override
    public Class<ThirdDingNotifyWorkrecordForm> getFormClass() {
		return ThirdDingNotifyWorkrecordForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreateTime",
					new ModelConvertor_Common("docCreateTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdUser.fdName", "fdUserName");
			toFormPropertyMap.put("fdUser.fdId", "fdUserId");
		}
		return toFormPropertyMap;
	}

	public String getFdSystemId() {
		return fdSystemId;
	}

	public void setFdSystemId(String fdSystemId) {
		this.fdSystemId = fdSystemId;
	}

	private String fdSystemId;

}
