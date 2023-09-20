package com.landray.kmss.sys.webservice2.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.model.SysWebserviceRestConfig;



/**
 * REST服务配置 Form
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public class SysWebserviceRestConfigForm  extends ExtendForm  {

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
	private String fdOrder;
	
	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return this.fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(String fdOrder) {
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
	private String docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 修改时间
	 */
	private String docAlterTime;
	
	/**
	 * @return 修改时间
	 */
	public String getDocAlterTime() {
		return this.docAlterTime;
	}
	
	/**
	 * @param docAlterTime 修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	/**
	 * 创建者的ID
	 */
	private String docCreatorId;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	private String docCreatorName;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
	/**
	 * 修改者的ID
	 */
	private String docAlterorId;
	
	/**
	 * @return 修改者的ID
	 */
	public String getDocAlterorId() {
		return this.docAlterorId;
	}
	
	/**
	 * @param docAlterorId 修改者的ID
	 */
	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}
	
	/**
	 * 修改者的名称
	 */
	private String docAlterorName;
	
	/**
	 * @return 修改者的名称
	 */
	public String getDocAlterorName() {
		return this.docAlterorName;
	}
	
	/**
	 * @param docAlterorName 修改者的名称
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}
	
	/**
	 * 设置对应model数据字典列表
	 */
	private AutoArrayList fdDictItems = new AutoArrayList(SysWebserviceDictConfigForm.class);
	
	public AutoArrayList getFdDictItems() {
		return this.fdDictItems;
	}
	
	public void setFdDictItems(AutoArrayList fdDictItems) {
		this.fdDictItems = fdDictItems;
	}
	
	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdPrefix = null;
		fdDes = null;
		docCreateTime = null;
		docAlterTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdDictItems = new AutoArrayList(SysWebserviceDictConfigForm.class);
		
 
		super.reset(mapping, request);
	}

	@Override
    public Class<SysWebserviceRestConfig> getModelClass() {
		return SysWebserviceRestConfig.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId",
					new FormConvertor_IDToModel("docAlteror",
						SysOrgElement.class));
			toModelPropertyMap.put("fdDictItems",
					new FormConvertor_FormListToModelList("fdDictItems",
							"sysWebserviceDictConfig"));
		}
		return toModelPropertyMap;
	}
}
