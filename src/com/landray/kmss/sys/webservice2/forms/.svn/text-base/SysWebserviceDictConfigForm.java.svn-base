package com.landray.kmss.sys.webservice2.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;

import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.model.SysWebserviceRestConfig;



/**
 * 数据字典配置 Form
 * 
 * @author 
 * @version 1.0 2017-12-22
 */
public class SysWebserviceDictConfigForm  extends ExtendForm  {

	/**
	 * 数据字典名称
	 */
	private String fdName;
	
	/**
	 * @return 数据字典名称
	 */
	public String getFdName() {
		return this.fdName;
	}
	
	/**
	 * @param fdName 数据字典名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 数据字典显示内容配置
	 */
	private String fdMainDisplay;
	
	/**
	 * @return 数据字典显示内容配置
	 */
	public String getFdMainDisplay() {
		return this.fdMainDisplay;
	}
	
	/**
	 * @param fdMainDisplay 数据字典显示内容配置
	 */
	public void setFdMainDisplay(String fdMainDisplay) {
		this.fdMainDisplay = fdMainDisplay;
	}
	
	/**
	 * 数据字典列表显示内容配置
	 */
	private String fdListDisplay;
	
	/**
	 * @return 数据字典列表显示内容配置
	 */
	public String getFdListDisplay() {
		return this.fdListDisplay;
	}
	
	/**
	 * @param fdListDisplay 数据字典列表显示内容配置
	 */
	public void setFdListDisplay(String fdListDisplay) {
		this.fdListDisplay = fdListDisplay;
	}
	
	/**
	 * 所属类名称
	 */
	private String fdModelName;
	
	/**
	 * @return 所属类名称
	 */
	public String getFdModelName() {
		return this.fdModelName;
	}
	
	/**
	 * @param fdModelName 所属类名称
	 */
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	/**
	 * 所属模块路径
	 */
	private String fdPrefix;
	
	/**
	 * @return 所属模块路径
	 */
	public String getFdPrefix() {
		return this.fdPrefix;
	}
	
	/**
	 * @param fdPrefix 所属模块路径
	 */
	public void setFdPrefix(String fdPrefix) {
		this.fdPrefix = fdPrefix;
	}
	
	/**
	 * 所属模块的ID
	 */
	private String fdModuleId;
	
	/**
	 * @return 所属模块的ID
	 */
	public String getFdModuleId() {
		return this.fdModuleId;
	}
	
	/**
	 * @param fdModuleId 所属模块的ID
	 */
	public void setFdModuleId(String fdModuleId) {
		this.fdModuleId = fdModuleId;
	}
	
	/**
	 * 所属模块的名称
	 */
	private String fdModuleName;
	
	/**
	 * @return 所属模块的名称
	 */
	public String getFdModuleName() {
		return this.fdModuleName;
	}
	
	/**
	 * @param fdModuleName 所属模块的名称
	 */
	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
	}
	
	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdMainDisplay = null;
		fdListDisplay = null;
		fdModelName = null;
		fdPrefix = null;
		fdModuleId = null;
		fdModuleName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class<SysWebserviceDictConfig> getModelClass() {
		return SysWebserviceDictConfig.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdModuleId",
					new FormConvertor_IDToModel("fdModule",
						SysWebserviceRestConfig.class));
		}
		return toModelPropertyMap;
	}
}
