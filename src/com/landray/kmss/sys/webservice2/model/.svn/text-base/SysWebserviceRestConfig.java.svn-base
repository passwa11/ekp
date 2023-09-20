package com.landray.kmss.sys.webservice2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.model.SysWebserviceRestConfig;

import com.landray.kmss.sys.webservice2.forms.SysWebserviceRestConfigForm;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceDictConfigForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;



/**
 * REST服务配置
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public class SysWebserviceRestConfig  extends BaseModel {

	/**
	 * 名称
	 */
	private String fdName;
	
	/**
	 * @return 名称
	 */
	public String getFdName() {
		return this.fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 排序号
	 */
	private Integer fdOrder;
	
	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return this.fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 模块路径
	 */
	private String fdPrefix;
	
	/**
	 * @return 模块路径
	 */
	public String getFdPrefix() {
		return this.fdPrefix;
	}
	
	/**
	 * @param fdPrefix 模块路径
	 */
	public void setFdPrefix(String fdPrefix) {
		this.fdPrefix = fdPrefix;
	}
	
	/**
	 * 模块描述
	 */
	private String fdDes;
	
	/**
	 * @return 模块描述
	 */
	public String getFdDes() {
		return this.fdDes;
	}
	
	/**
	 * @param fdDes 模块描述
	 */
	public void setFdDes(String fdDes) {
		this.fdDes = fdDes;
	}
	
	/**
	 * 创建时间
	 */
	private Date docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return this.docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 修改时间
	 */
	private Date docAlterTime;
	
	/**
	 * @return 修改时间
	 */
	public Date getDocAlterTime() {
		return this.docAlterTime;
	}
	
	/**
	 * @param docAlterTime 修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	/**
	 * 创建者
	 */
	private SysOrgPerson docCreator;
	
	/**
	 * @return 创建者
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}
	
	/**
	 * @param docCreator 创建者
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
	
	/**
	 * 修改者
	 */
	private SysOrgElement docAlteror;
	
	/**
	 * @return 修改者
	 */
	public SysOrgElement getDocAlteror() {
		return this.docAlteror;
	}
	
	/**
	 * @param docAlteror 修改者
	 */
	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}
	
	/**
	 * 设置对应model数据字典列表
	 */
	private List<SysWebserviceDictConfig> fdDictItems = new ArrayList<SysWebserviceDictConfig>();
	
	public List<SysWebserviceDictConfig> getFdDictItems() {
		return this.fdDictItems;
	}

	public void setFdDictItems(List<SysWebserviceDictConfig> fdDictItems) {
		this.fdDictItems = fdDictItems;
	}

	//机制开始
	//机制结束

	@Override
    public Class<SysWebserviceRestConfigForm> getFormClass() {
		return SysWebserviceRestConfigForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("fdDictItems",
					new ModelConvertor_ModelListToFormList("fdDictItems"));
		}
		return toFormPropertyMap;
	}
}
