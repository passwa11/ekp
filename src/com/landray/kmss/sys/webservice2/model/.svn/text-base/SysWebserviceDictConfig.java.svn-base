package com.landray.kmss.sys.webservice2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;

import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.model.SysWebserviceRestConfig;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.sys.webservice2.forms.SysWebserviceRestConfigForm;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceDictConfigForm;



/**
 * 数据字典配置
 * 
 * @author 
 * @version 1.0 2017-12-22
 */
public class SysWebserviceDictConfig  extends BaseModel implements
InterceptFieldEnabled  {

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
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 数据字典主文档显示内容配置
	 */
	private String fdMainDisplay;
	
	/**
	 * @return 数据字典主文档显示内容配置
	 */
	public String getFdMainDisplay() {
		return (String) readLazyField("fdMainDisplay", fdMainDisplay);
	}
	
	/**
	 * @param fdMainDisplay 数据字典主文档显示内容配置
	 */
	public void setFdMainDisplay(String fdMainDisplay) {
		this.fdMainDisplay = (String) writeLazyField("fdMainDisplay", this.fdMainDisplay,
				fdMainDisplay);
	}
	
	/**
	 * 数据字典列表显示内容配置
	 */
	private String fdListDisplay;
	
	/**
	 * @return 数据字典列表显示内容配置
	 */
	public String getFdListDisplay() {
		return (String) readLazyField("fdListDisplay", fdListDisplay);
	}
	
	/**
	 * @param fdListDisplay 数据字典列表显示内容配置
	 */
	public void setFdListDisplay(String fdListDisplay) {
		this.fdListDisplay = (String) writeLazyField("fdListDisplay", this.fdListDisplay,
				fdListDisplay);
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
	 * 所属模块
	 */
	private SysWebserviceRestConfig fdModule;
	
	/**
	 * @return 所属模块
	 */
	public SysWebserviceRestConfig getFdModule() {
		return this.fdModule;
	}
	
	/**
	 * @param fdModule 所属模块
	 */
	public void setFdModule(SysWebserviceRestConfig fdModule) {
		this.fdModule = fdModule;
	}
	

	//机制开始
	//机制结束

	@Override
    public Class<SysWebserviceDictConfigForm> getFormClass() {
		return SysWebserviceDictConfigForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdModule.fdId", "fdModuleId");
			toFormPropertyMap.put("fdModule.fdName", "fdModuleName");
		}
		return toFormPropertyMap;
	}
}
