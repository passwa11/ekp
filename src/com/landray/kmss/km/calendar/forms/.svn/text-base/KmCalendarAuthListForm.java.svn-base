package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 个人共享权限
  */
public class KmCalendarAuthListForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdIsEdit;

    private String fdIsRead;

    private String fdIsModify;

    private String fdShareDate;

    private String fdIsShare;

    private String fdPersonIds;

    private String fdPersonNames;

	private String fdAuthId;

	private String fdAuthName;

	private String fdIsPartShare;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdIsEdit = null;
        fdIsRead = null;
        fdIsModify = null;
        fdShareDate = null;
        fdIsShare = null;
        fdPersonIds = null;
        fdPersonNames = null;
		fdAuthId = null;
		fdAuthName = null;
		fdIsPartShare = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<KmCalendarAuthList> getModelClass() {
        return KmCalendarAuthList.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdShareDate", new FormConvertor_Common("fdShareDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdPersonIds", new FormConvertor_IDsToModelList("fdPerson", SysOrgElement.class));
			toModelPropertyMap.put("fdAuthId", new FormConvertor_IDToModel(
					"fdAuth", KmCalendarAuth.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 是否允许创建
     */
    public String getFdIsEdit() {
        return this.fdIsEdit;
    }

    /**
     * 是否允许创建
     */
    public void setFdIsEdit(String fdIsEdit) {
        this.fdIsEdit = fdIsEdit;
    }

    /**
     * 是否允许阅读
     */
    public String getFdIsRead() {
        return this.fdIsRead;
    }

    /**
     * 是否允许阅读
     */
    public void setFdIsRead(String fdIsRead) {
        this.fdIsRead = fdIsRead;
    }

    /**
     * 是否允许维护
     */
    public String getFdIsModify() {
        return this.fdIsModify;
    }

    /**
     * 是否允许维护
     */
    public void setFdIsModify(String fdIsModify) {
        this.fdIsModify = fdIsModify;
    }

    /**
     * 历史日程共享时间
     */
    public String getFdShareDate() {
        return this.fdShareDate;
    }

    /**
     * 历史日程共享时间
     */
    public void setFdShareDate(String fdShareDate) {
        this.fdShareDate = fdShareDate;
    }

    /**
     * 是否共享历史日程
     */
    public String getFdIsShare() {
        return this.fdIsShare;
    }

    /**
     * 是否共享历史日程
     */
    public void setFdIsShare(String fdIsShare) {
        this.fdIsShare = fdIsShare;
    }

    /**
     * 共享人员/组织
     */
    public String getFdPersonIds() {
        return this.fdPersonIds;
    }

    /**
     * 共享人员/组织
     */
    public void setFdPersonIds(String fdPersonIds) {
        this.fdPersonIds = fdPersonIds;
    }

    /**
     * 共享人员/组织
     */
    public String getFdPersonNames() {
        return this.fdPersonNames;
    }

    /**
     * 共享人员/组织
     */
    public void setFdPersonNames(String fdPersonNames) {
        this.fdPersonNames = fdPersonNames;
    }

	public String getFdAuthId() {
		return fdAuthId;
	}

	public void setFdAuthId(String fdAuthId) {
		this.fdAuthId = fdAuthId;
	}

	public String getFdAuthName() {
		return fdAuthName;
	}

	public void setFdAuthName(String fdAuthName) {
		this.fdAuthName = fdAuthName;
	}

	/**
	 * 是否部分共享
	 */
	public String getFdIsPartShare() {
		return fdIsPartShare;
	}

	/**
	 * 是否部分共享
	 */
	public void setFdIsPartShare(String fdIsPartShare) {
		this.fdIsPartShare = fdIsPartShare;
	}

}
