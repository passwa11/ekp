package com.landray.kmss.third.welink.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办推送失败队列
  */
public class ThirdWelinkNotifyQueueErrForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSubject;

    private String fdMethod;

    private String fdData;

    private String fdErrMsg;

    private String fdRepeatHandle;

    private String fdFlag;

    private String fdMd5;

    private String docCreateTime;

    private String docAlterTime;

    private String fdWelinkUserId;

    private String fdSendType;

    private String fdNotifyId;

	private String fdToUserId;

	private String fdToUserName;

	private String fdFromUserId;

	private String fdFromUserName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSubject = null;
        fdMethod = null;
        fdData = null;
        fdErrMsg = null;
        fdRepeatHandle = null;
        fdFlag = null;
        fdMd5 = null;
        docCreateTime = null;
        docAlterTime = null;
        fdWelinkUserId = null;
        fdSendType = null;
        fdNotifyId = null;
		setFdToUserId(null);
		setFdToUserName(null);
		setFdFromUserId(null);
		setFdFromUserName(null);
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkNotifyQueueErr> getModelClass() {
        return ThirdWelinkNotifyQueueErr.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
			toModelPropertyMap.put("fdToUserId", new FormConvertor_IDToModel(
					"fdToUser", SysOrgPerson.class));
			toModelPropertyMap.put("fdFromUserId", new FormConvertor_IDToModel(
					"fdFromUser", SysOrgPerson.class));
		}
        return toModelPropertyMap;
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
    public String getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 动作
     */
    public void setFdMethod(String fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 消息内容
     */
    public String getFdData() {
        return this.fdData;
    }

    /**
     * 消息内容
     */
    public void setFdData(String fdData) {
        this.fdData = fdData;
    }

    /**
     * 异常信息
     */
    public String getFdErrMsg() {
        return this.fdErrMsg;
    }

    /**
     * 异常信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = fdErrMsg;
    }

    /**
     * 重复处理次数
     */
    public String getFdRepeatHandle() {
        return this.fdRepeatHandle;
    }

    /**
     * 重复处理次数
     */
    public void setFdRepeatHandle(String fdRepeatHandle) {
        this.fdRepeatHandle = fdRepeatHandle;
    }

    /**
     * 处理标识
     */
    public String getFdFlag() {
        return this.fdFlag;
    }

    /**
     * 处理标识
     */
    public void setFdFlag(String fdFlag) {
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
    public String getFdSendType() {
        return this.fdSendType;
    }

    /**
     * 推送类型
     */
    public void setFdSendType(String fdSendType) {
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

	public String getFdToUserId() {
		return fdToUserId;
	}

	public void setFdToUserId(String fdToUserId) {
		this.fdToUserId = fdToUserId;
	}

	public String getFdToUserName() {
		return fdToUserName;
	}

	public void setFdToUserName(String fdToUserName) {
		this.fdToUserName = fdToUserName;
	}

	public String getFdFromUserId() {
		return fdFromUserId;
	}

	public void setFdFromUserId(String fdFromUserId) {
		this.fdFromUserId = fdFromUserId;
	}

	public String getFdFromUserName() {
		return fdFromUserName;
	}

	public void setFdFromUserName(String fdFromUserName) {
		this.fdFromUserName = fdFromUserName;
	}


}
