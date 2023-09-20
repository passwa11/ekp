package com.landray.kmss.tic.jdbc.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.tic.jdbc.forms.TicJdbcRelationForm;

/**
 * 映射关系
 * 
 * @author 
 * @version 1.0 2013-08-13
 */
public class TicJdbcRelation extends BaseModel {

	/**
	 * 用途说明
	 */
	protected String fdUseExplain;
	
	/**
	 * @return 用途说明
	 */
	public String getFdUseExplain() {
		return fdUseExplain;
	}
	
	/**
	 * @param fdUseExplain 用途说明
	 */
	public void setFdUseExplain(String fdUseExplain) {
		this.fdUseExplain = fdUseExplain;
	}
	
	/**
	 * 同步方式
	 */
	protected String fdSyncType;
	
	/**
	 * @return 同步方式
	 */
	public String getFdSyncType() {
		return fdSyncType;
	}
	
	/**
	 * @param fdSyncType 同步方式
	 */
	public void setFdSyncType(String fdSyncType) {
		this.fdSyncType = fdSyncType;
	}
	
	/**
	 * 所属映射
	 */
	protected TicJdbcMappManage ticJdbcMappManage;
	
	/**
	 * @return 所属映射
	 */
	public TicJdbcMappManage getTicJdbcMappManage() {
		return ticJdbcMappManage;
	}
	
	/**
	 * @param ticJdbcMappManage 所属映射
	 */
	public void setTicJdbcMappManage(TicJdbcMappManage ticJdbcMappManage) {
		this.ticJdbcMappManage = ticJdbcMappManage;
	}
	
	/**
	 * 所属任务
	 */
	protected TicJdbcTaskManage ticJdbcTaskManage;
	
	/**
	 * @return 所属任务
	 */
	public TicJdbcTaskManage getTicJdbcTaskManage() {
		return ticJdbcTaskManage;
	}
	
	/**
	 * @param ticJdbcTaskManage 所属任务
	 */
	public void setTicJdbcTaskManage(TicJdbcTaskManage ticJdbcTaskManage) {
		this.ticJdbcTaskManage = ticJdbcTaskManage;
	}
	
	@Override
    public Class getFormClass() {
		return TicJdbcRelationForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("ticJdbcMappManage.fdId", "ticJdbcMappManageId");
			toFormPropertyMap.put("ticJdbcMappManage.docSubject", "ticJdbcMappManageName");
			toFormPropertyMap.put("ticJdbcTaskManage.fdId", "ticJdbcTaskManageId");
			toFormPropertyMap.put("ticJdbcTaskManage.fdId", "ticJdbcTaskManageName");
		}
		return toFormPropertyMap;
	}
}
