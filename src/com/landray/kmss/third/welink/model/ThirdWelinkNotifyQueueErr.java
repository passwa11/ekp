package com.landray.kmss.third.welink.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.welink.forms.ThirdWelinkNotifyQueueErrForm;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 待办推送失败队列
  */
public class ThirdWelinkNotifyQueueErr extends BaseModel implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdSubject;

	private Integer fdMethod;

    private String fdData;

    private String fdErrMsg;

    private Integer fdRepeatHandle;

    private Integer fdFlag;

    private String fdMd5;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdWelinkUserId;

    private Integer fdSendType;

    private String fdNotifyId;

	private SysOrgPerson fdToUser;

	private SysOrgPerson fdFromUser;

    @Override
    public Class<ThirdWelinkNotifyQueueErrForm> getFormClass() {
        return ThirdWelinkNotifyQueueErrForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdToUser.fdName", "fdToUserName");
			toFormPropertyMap.put("fdToUser.fdId", "fdToUserId");
			toFormPropertyMap.put("fdFromUser.fdName", "fdFromUserName");
			toFormPropertyMap.put("fdFromUser.fdId", "fdFromUserId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 标题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * 动作
     */
	public Integer getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 动作
     */
	public void setFdMethod(Integer fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 消息内容
     */
    public String getFdData() {
        return (String) readLazyField("fdData", this.fdData);
    }

    /**
     * 消息内容
     */
    public void setFdData(String fdData) {
        this.fdData = (String) writeLazyField("fdData", this.fdData, fdData);
    }

    /**
     * 异常信息
     */
    public String getFdErrMsg() {
        return (String) readLazyField("fdErrMsg", this.fdErrMsg);
    }

    /**
     * 异常信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = (String) writeLazyField("fdErrMsg", this.fdErrMsg, fdErrMsg);
    }

    /**
     * 重复处理次数
     */
    public Integer getFdRepeatHandle() {
        return this.fdRepeatHandle;
    }

    /**
     * 重复处理次数
     */
    public void setFdRepeatHandle(Integer fdRepeatHandle) {
        this.fdRepeatHandle = fdRepeatHandle;
    }

    /**
     * 处理标识
     */
    public Integer getFdFlag() {
        return this.fdFlag;
    }

    /**
     * 处理标识
     */
    public void setFdFlag(Integer fdFlag) {
        this.fdFlag = fdFlag;
    }

    /**
     * 待办MD5
     */
    public String getFdMd5() {
        return this.fdMd5;
    }

    /**
     * 待办MD5
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
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
     * welink用户ID
     */
    public String getFdWelinkUserId() {
        return this.fdWelinkUserId;
    }

    /**
     * welink用户ID
     */
    public void setFdWelinkUserId(String fdWelinkUserId) {
        this.fdWelinkUserId = fdWelinkUserId;
    }

    /**
     * 推送类型
     */
    public Integer getFdSendType() {
        return this.fdSendType;
    }

    /**
     * 推送类型
     */
    public void setFdSendType(Integer fdSendType) {
        this.fdSendType = fdSendType;
    }

    /**
     * 待办ID
     */
    public String getFdNotifyId() {
        return this.fdNotifyId;
    }

    /**
     * 待办ID
     */
    public void setFdNotifyId(String fdNotifyId) {
        this.fdNotifyId = fdNotifyId;
    }

	public SysOrgPerson getFdToUser() {
		return fdToUser;
	}

	public void setFdToUser(SysOrgPerson fdToUser) {
		this.fdToUser = fdToUser;
	}

	public SysOrgPerson getFdFromUser() {
		return fdFromUser;
	}

	public void setFdFromUser(SysOrgPerson fdFromUser) {
		this.fdFromUser = fdFromUser;
	}


}
