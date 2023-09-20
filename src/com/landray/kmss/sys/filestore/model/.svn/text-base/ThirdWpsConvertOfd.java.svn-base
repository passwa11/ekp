package com.landray.kmss.sys.filestore.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * WPS转OFD结果信息表
  * 
  * @author 林剑文 linjw
  * @date 2020-09-27
  */
public class ThirdWpsConvertOfd extends BaseModel{

    private static ModelToFormPropertyMap toFormPropertyMap;
    private String fdAttMainId; //附件ID
    private String resultId;//返回结果ID
    private String downloadId;//返回的下载ID
    private String previewId;//返回的预览ID
    private SysOrgPerson docCreator;
    private SysOrgPerson docAlteror;

    

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
           
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

   
    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

	public String getFdAttMainId() {
		return fdAttMainId;
	}

	public void setFdAttMainId(String fdAttMainId) {
		this.fdAttMainId = fdAttMainId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(String downloadId) {
		this.downloadId = downloadId;
	}

	public String getPreviewId() {
		return previewId;
	}

	public void setPreviewId(String previewId) {
		this.previewId = previewId;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
