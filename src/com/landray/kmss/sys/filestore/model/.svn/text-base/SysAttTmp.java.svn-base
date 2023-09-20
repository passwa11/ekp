package com.landray.kmss.sys.filestore.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;

/**
 * 删除临时表
 * 
 * @author 李衡
 * @version 1.0 2012-08-29
 */
public class SysAttTmp extends BaseModel {

	/**
	 * 文件ID信息
	 */
	protected String fdFileId;
	
	/**
	 * @return 文件ID信息
	 */
	public String getFdFileId() {
		return fdFileId;
	}
	
	/**
	 * @param fdFileId 文件ID信息
	 */
	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId;
	}
	
	/**
	 * 删除时间
	 */
	protected Date fdDeleteTime;
	
	/**
	 * @return 删除时间
	 */
	public Date getFdDeleteTime() {
		return fdDeleteTime;
	}
	
	/**
	 * @param fdDeleteTime 删除时间
	 */
	public void setFdDeleteTime(Date fdDeleteTime) {
		this.fdDeleteTime = fdDeleteTime;
	}
	
	@Override
    public Class getFormClass() {
		return this.getClass();
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
