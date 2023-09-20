package com.landray.kmss.tic.jdbc.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.tic.jdbc.model.TicJdbcMappManage;
import com.landray.kmss.tic.jdbc.model.TicJdbcRelation;
import com.landray.kmss.tic.jdbc.model.TicJdbcTaskManage;


/**
 * 映射关系 Form
 * 
 * @author 
 * @version 1.0 2013-08-13
 */
public class TicJdbcRelationForm extends ExtendForm {

	/**
	 * 用途说明
	 */
	protected String fdUseExplain = null;
	
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
	protected String fdSyncType = null;
	
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
	 * 所属映射的ID
	 */
	protected String ticJdbcMappManageId = null;
	
	/**
	 * @return 所属映射的ID
	 */
	public String getTicJdbcMappManageId() {
		return ticJdbcMappManageId;
	}
	
	/**
	 * @param ticJdbcMappManageId 所属映射的ID
	 */
	public void setTicJdbcMappManageId(String ticJdbcMappManageId) {
		this.ticJdbcMappManageId = ticJdbcMappManageId;
	}
	
	/**
	 * 所属映射的名称
	 */
	protected String ticJdbcMappManageName = null;
	
	/**
	 * @return 所属映射的名称
	 */
	public String getTicJdbcMappManageName() {
		return ticJdbcMappManageName;
	}
	
	/**
	 * @param ticJdbcMappManageName 所属映射的名称
	 */
	public void setTicJdbcMappManageName(String ticJdbcMappManageName) {
		this.ticJdbcMappManageName = ticJdbcMappManageName;
	}
	
	/**
	 * 所属任务的ID
	 */
	protected String ticJdbcTaskManageId = null;
	
	/**
	 * @return 所属任务的ID
	 */
	public String getTicJdbcTaskManageId() {
		return ticJdbcTaskManageId;
	}
	
	/**
	 * @param ticJdbcTaskManageId 所属任务的ID
	 */
	public void setTicJdbcTaskManageId(String ticJdbcTaskManageId) {
		this.ticJdbcTaskManageId = ticJdbcTaskManageId;
	}
	
	/**
	 * 所属任务的名称
	 */
	protected String ticJdbcTaskManageName = null;
	
	/**
	 * @return 所属任务的名称
	 */
	public String getTicJdbcTaskManageName() {
		return ticJdbcTaskManageName;
	}
	
	/**
	 * @param ticJdbcTaskManageName 所属任务的名称
	 */
	public void setTicJdbcTaskManageName(String ticJdbcTaskManageName) {
		this.ticJdbcTaskManageName = ticJdbcTaskManageName;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdUseExplain = null;
		fdSyncType = null;
		ticJdbcMappManageId = null;
		ticJdbcMappManageName = null;
		ticJdbcTaskManageId = null;
		ticJdbcTaskManageName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicJdbcRelation.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("ticJdbcMappManageId",
					new FormConvertor_IDToModel("ticJdbcMappManage",
						TicJdbcMappManage.class));
			toModelPropertyMap.put("ticJdbcTaskManageId",
					new FormConvertor_IDToModel("ticJdbcTaskManage",
						TicJdbcTaskManage.class));
		}
		return toModelPropertyMap;
	}
}
