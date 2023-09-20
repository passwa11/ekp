package com.landray.kmss.km.archives.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.archives.forms.KmArchivesRenewForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 档案续借
  */
public class KmArchivesRenew extends BaseModel implements ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdReason;

    private SysOrgPerson docCreator;

	private String fdDetailsId;
	
	private String fdNotifyType;

    @Override
    public Class<KmArchivesRenewForm> getFormClass() {
        return KmArchivesRenewForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 申请时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 申请时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 续借事由
     */
    public String getFdReason() {
        return this.fdReason;
    }

    /**
     * 续借事由
     */
    public void setFdReason(String fdReason) {
        this.fdReason = fdReason;
    }

    /**
     * 申请人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 申请人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
	 * 档案借阅明细ID
	 */
	public String getFdDetailsId() {
		return this.fdDetailsId;
    }

    /**
	 * 档案借阅明细ID
	 */
	public void setFdDetailsId(String fdDetailsId) {
		this.fdDetailsId = fdDetailsId;
    }

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}
	
}
