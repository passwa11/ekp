package com.landray.kmss.third.ding.notify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.notify.forms.ThirdDingNotifyMessageForm;
import com.landray.kmss.third.ding.notify.forms.ThirdDingNotifyWorkrecordForm;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 钉钉工作通知映射关系
  */
public class ThirdDingNotifyMessage extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdNotifyId;

    private String fdDingTaskId;

    private String fdDingUserId;
    
    private Date docCreateTime;

    public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdDingTaskId(){
    	return this.fdDingTaskId;
	}

	public void setFdDingTaskId(String fdDingTaskId){
    	this.fdDingTaskId = fdDingTaskId;
	}

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Class<ThirdDingNotifyMessageForm> getFormClass() {
		return ThirdDingNotifyMessageForm.class;
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

	private String fdAgentId;

	public String getFdAgentId(){
		return this.fdAgentId;
	}

	public void setFdAgentId(String fdAgentId){
		this.fdAgentId = fdAgentId;
	}

}
