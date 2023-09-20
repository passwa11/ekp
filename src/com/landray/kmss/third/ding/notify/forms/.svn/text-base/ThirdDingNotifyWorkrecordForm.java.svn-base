package com.landray.kmss.third.ding.notify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办映射表
  */
public class ThirdDingNotifyWorkrecordForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdNotifyId;

	private String fdRecordId;

    private String fdDingUserId;

    private String docCreateTime;

	private String fdEKPDel;

    private String fdSubject;

    private String fdUserId;

    private String fdUserName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdNotifyId = null;
		fdRecordId = null;
        fdDingUserId = null;
        docCreateTime = null;
		setFdEKPDel("false");
        fdSubject = null;
        fdUserId = null;
        fdUserName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingNotifyWorkrecord> getModelClass() {
        return ThirdDingNotifyWorkrecord.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdUserId", new FormConvertor_IDToModel("fdUser", SysOrgElement.class));
        }
        return toModelPropertyMap;
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

    /**
     * 钉钉待办ID
     */
	public String getFdRecordId() {
		return this.fdRecordId;
    }

    /**
     * 钉钉待办ID
     */
	public void setFdRecordId(String fdRecordId) {
		this.fdRecordId = fdRecordId;
    }

    /**
     * 钉钉用户ID
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉用户ID
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
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
     * 待办标题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 待办标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * 所属用户
     */
    public String getFdUserId() {
        return this.fdUserId;
    }

    /**
     * 所属用户
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * 所属用户
     */
    public String getFdUserName() {
        return this.fdUserName;
    }

    /**
     * 所属用户
     */
    public void setFdUserName(String fdUserName) {
        this.fdUserName = fdUserName;
    }

	public String getFdEKPDel() {
		return fdEKPDel;
	}

	public void setFdEKPDel(String fdEKPDel) {
		this.fdEKPDel = fdEKPDel;
	}
}
