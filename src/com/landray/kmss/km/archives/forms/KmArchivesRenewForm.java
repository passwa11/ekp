package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.archives.model.KmArchivesRenew;
import com.landray.kmss.util.AutoArrayList;

/**
  * 档案续借
  */
public class KmArchivesRenewForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdReason;

    private String docCreatorId;

    private String docCreatorName;

    private String fdDetailsId;
    
    private String fdNotifyType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
        fdReason = null;
        docCreatorId = null;
        docCreatorName = null;
        fdDetailsId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesRenew> getModelClass() {
        return KmArchivesRenew.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 申请时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 申请时间
     */
    public void setDocCreateTime(String docCreateTime) {
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
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 申请人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 申请人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 申请人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 档案借阅明细
     */
    public String getFdDetailsId() {
        return this.fdDetailsId;
    }

    /**
     * 档案借阅明细
     */
    public void setFdDetailsId(String fdDetailsId) {
        this.fdDetailsId = fdDetailsId;
    }

	/**
	 * 新建续借的档案明细
	 */
	private AutoArrayList detailsList = new AutoArrayList(
			KmArchivesDetailsForm.class);

	public AutoArrayList getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(AutoArrayList detailsList) {
		this.detailsList = detailsList;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}
	
}
