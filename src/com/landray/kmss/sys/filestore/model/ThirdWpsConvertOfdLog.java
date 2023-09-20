package com.landray.kmss.sys.filestore.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * WPS转OFD结果日志信息表
 * 
 * @author 林剑文 linjw
 * @date 2020-09-27
 */
public class ThirdWpsConvertOfdLog extends BaseModel{

    private static ModelToFormPropertyMap toFormPropertyMap;
    private String fdAttMainId; //附件ID
    private String fdStatus;//转换成功与否：1：成功；0：失败
    private String fdType;//操作类型 ：1:上传文件  2.转换文件
    private Date fdcreateTime;//请求时间
    private String fdResult;//结果信息
   
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

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public Date getFdcreateTime() {
		return fdcreateTime;
	}

	public void setFdcreateTime(Date fdcreateTime) {
		this.fdcreateTime = fdcreateTime;
	}

	public String getFdResult() {
		return fdResult;
	}

	public void setFdResult(String fdResult) {
		this.fdResult = fdResult;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
