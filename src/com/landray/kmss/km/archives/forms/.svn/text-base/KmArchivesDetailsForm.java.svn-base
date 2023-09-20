package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案借阅明细
  */
public class KmArchivesDetailsForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String fdBorrowerId;

	private String fdArchId;

	private KmArchivesMain fdArchives;

    private String fdStatus;

    private String fdReturnDate;

    private String fdRenewReturnDate;

	private String fdAuthorityRange;

	private String fdValidityDate;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdBorrowerId = null;
		fdArchId = null;
		fdArchives = new KmArchivesMain();
        fdStatus = null;
        fdReturnDate = null;
        fdRenewReturnDate = null;
		fdAuthorityRange = null;
		fdValidityDate = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesDetails> getModelClass() {
        return KmArchivesDetails.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdAuthorityRange", "fdAuthorityRange");
            toModelPropertyMap.put("fdReturnDate", new FormConvertor_Common("fdReturnDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdRenewReturnDate", new FormConvertor_Common("fdRenewReturnDate").setDateTimeType(DateUtil.TYPE_DATETIME));
			toModelPropertyMap.put("fdArchId", new FormConvertor_IDToModel(
					"fdArchives", KmArchivesMain.class));
			toModelPropertyMap.put("fdBorrowerId", new FormConvertor_IDToModel(
					"fdBorrower", SysOrgPerson.class));
        }
        return toModelPropertyMap;
    }

	public String getFdBorrowerId() {
		return fdBorrowerId;
	}

	public void setFdBorrowerId(String fdBorrowerId) {
		this.fdBorrowerId = fdBorrowerId;
	}

	public String getFdArchId() {
		return fdArchId;
	}

	public void setFdArchId(String fdArchId) {
		this.fdArchId = fdArchId;
	}

	public KmArchivesMain getFdArchives() {
		return fdArchives;
	}

	public void setFdArchives(KmArchivesMain fdArchives) {
		this.fdArchives = fdArchives;
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
    public String getFdReturnDate() {
        return this.fdReturnDate;
    }

    /**
     * 归还时间
     */
    public void setFdReturnDate(String fdReturnDate) {
        this.fdReturnDate = fdReturnDate;
    }

    /**
     * 续借归还时间
     */
    public String getFdRenewReturnDate() {
        return this.fdRenewReturnDate;
    }

    /**
     * 续借归还时间
     */
    public void setFdRenewReturnDate(String fdRenewReturnDate) {
        this.fdRenewReturnDate = fdRenewReturnDate;
    }

	public String getFdAuthorityRange() {
		return fdAuthorityRange;
	}

	public void setFdAuthorityRange(String fdAuthorityRange) {
		this.fdAuthorityRange = fdAuthorityRange;
	}

	/**
	 * 档案有效期
	 */
	public void setFdValidityDate(String fdValidityDate) {
		this.fdValidityDate = fdValidityDate;
	}

	public String getFdValidityDate() {
		return fdValidityDate;
	}
}
