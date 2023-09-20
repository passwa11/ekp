package com.landray.kmss.km.calendar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarAuthListForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;

/**
 * 个人共享权限
 * 
 * 将KmCalendarAuth的可创建者、可阅读者、可维护者由此model维护
 */
public class KmCalendarAuthList extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Boolean fdIsEdit;

    private Boolean fdIsRead;

    private Boolean fdIsModify;

    private Date fdShareDate;

    private Boolean fdIsShare;

    private List<SysOrgElement> fdPerson = new ArrayList<SysOrgElement>();

	private KmCalendarAuth fdAuth;

	// 部分日程共享，可能不在用户共享权限的可创建者、可阅读者、可维护者
	private Boolean fdIsPartShare;

    @Override
    public Class<KmCalendarAuthListForm> getFormClass() {
        return KmCalendarAuthListForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdShareDate", new ModelConvertor_Common("fdShareDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdPerson", new ModelConvertor_ModelListToString("fdPersonIds:fdPersonNames", "fdId:fdName"));
			toFormPropertyMap.put("fdAuth.fdId", "fdAuthId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 是否允许创建
     */
    public Boolean getFdIsEdit() {
        return this.fdIsEdit;
    }

    /**
     * 是否允许创建
     */
    public void setFdIsEdit(Boolean fdIsEdit) {
        this.fdIsEdit = fdIsEdit;
    }

    /**
     * 是否允许阅读
     */
    public Boolean getFdIsRead() {
        return this.fdIsRead;
    }

    /**
     * 是否允许阅读
     */
    public void setFdIsRead(Boolean fdIsRead) {
        this.fdIsRead = fdIsRead;
    }

    /**
     * 是否允许维护
     */
    public Boolean getFdIsModify() {
        return this.fdIsModify;
    }

    /**
     * 是否允许维护
     */
    public void setFdIsModify(Boolean fdIsModify) {
        this.fdIsModify = fdIsModify;
    }

    /**
     * 历史日程共享时间
     */
    public Date getFdShareDate() {
        return this.fdShareDate;
    }

    /**
     * 历史日程共享时间
     */
    public void setFdShareDate(Date fdShareDate) {
        this.fdShareDate = fdShareDate;
    }

    /**
     * 是否共享历史日程
     */
    public Boolean getFdIsShare() {
        return this.fdIsShare;
    }

    /**
     * 是否共享历史日程
     */
    public void setFdIsShare(Boolean fdIsShare) {
        this.fdIsShare = fdIsShare;
    }

    /**
     * 共享人员/组织
     */
    public List<SysOrgElement> getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 共享人员/组织
     */
    public void setFdPerson(List<SysOrgElement> fdPerson) {
        this.fdPerson = fdPerson;
    }

	public KmCalendarAuth getFdAuth() {
		return fdAuth;
	}

	public void setFdAuth(KmCalendarAuth fdAuth) {
		this.fdAuth = fdAuth;
	}

	/**
	 * 是否部分共享
	 */
	public Boolean getFdIsPartShare() {
		return fdIsPartShare;
	}

	/**
	 * 是否部分共享
	 */
	public void setFdIsPartShare(Boolean fdIsPartShare) {
		this.fdIsPartShare = fdIsPartShare;
	}

}
