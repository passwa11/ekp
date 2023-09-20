package com.landray.kmss.km.archives.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.archives.forms.KmArchivesDetailsForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 档案借阅明细
  */
public class KmArchivesDetails extends BaseModel implements ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAuthorityRange;

    private String fdStatus;

    private Date fdReturnDate;

    private Date fdRenewReturnDate;

    private SysOrgPerson fdBorrower;

    private KmArchivesMain fdArchives;

	private KmArchivesBorrow docMain;

    @Override
    public Class<KmArchivesDetailsForm> getFormClass() {
        return KmArchivesDetailsForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdAuthorityRange", "fdAuthorityRange");
            toFormPropertyMap.put("fdReturnDate", new ModelConvertor_Common("fdReturnDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdRenewReturnDate", new ModelConvertor_Common("fdRenewReturnDate").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdArchives.fdValidityDate",
					new ModelConvertor_Common("fdValidityDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdArchives.fdId", "fdArchId");
			toFormPropertyMap.put("fdArchives", "fdArchives");
			toFormPropertyMap.put("fdBorrower.fdId", "fdBorrowerId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 授权范围
     */
    public String getFdAuthorityRange() {
        return this.fdAuthorityRange;
    }

    /**
     * 授权范围
     */
    public void setFdAuthorityRange(String fdAuthorityRange) {
        this.fdAuthorityRange = fdAuthorityRange;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 归还时间
     */
    public Date getFdReturnDate() {
        return this.fdReturnDate;
    }

    /**
     * 归还时间
     */
    public void setFdReturnDate(Date fdReturnDate) {
        this.fdReturnDate = fdReturnDate;
    }

    /**
     * 续借归还时间
     */
    public Date getFdRenewReturnDate() {
        return this.fdRenewReturnDate;
    }

    /**
     * 续借归还时间
     */
    public void setFdRenewReturnDate(Date fdRenewReturnDate) {
        this.fdRenewReturnDate = fdRenewReturnDate;
    }

    /**
     * 借阅人
     */
    public SysOrgPerson getFdBorrower() {
        return this.fdBorrower;
    }

    /**
     * 借阅人
     */
    public void setFdBorrower(SysOrgPerson fdBorrower) {
        this.fdBorrower = fdBorrower;
    }

    /**
     * 档案
     */
    public KmArchivesMain getFdArchives() {
        return this.fdArchives;
    }

    /**
     * 档案
     */
    public void setFdArchives(KmArchivesMain fdArchives) {
        this.fdArchives = fdArchives;
    }

	public KmArchivesBorrow getDocMain() {
		return docMain;
	}

	public void setDocMain(KmArchivesBorrow docMain) {
		this.docMain = docMain;
	}

}
