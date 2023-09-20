package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.hr.staff.model.HrStaffFileAuthor;



/**
 * 档案授权 Form
 * 
 * @author 
 * @version 1.0 2017-11-10
 */
public class HrStaffFileAuthorForm  extends ExtendForm  {

	/**
	 * 对应架构
	 */
	private String fdName;
	
	/**
	 * @return 对应架构
	 */
	public String getFdName() {
		return this.fdName;
	}
	
	/**
	 * @param fdName 对应架构
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 授权人员的ID列表
	 */
	private String authorDetailIds;
	
	/**
	 * @return 授权人员的ID列表
	 */
	public String getAuthorDetailIds() {
		return this.authorDetailIds;
	}
	
	/**
	 * @param authorDetailIds 授权人员的ID列表
	 */
	public void setAuthorDetailIds(String authorDetailIds) {
		this.authorDetailIds = authorDetailIds;
	}
	
	/**
	 * 授权人员的名称列表
	 */
	private String authorDetailNames;
	
	/**
	 * @return 授权人员的名称列表
	 */
	public String getAuthorDetailNames() {
		return this.authorDetailNames;
	}
	
	/**
	 * @param authorDetailNames 授权人员的名称列表
	 */
	public void setAuthorDetailNames(String authorDetailNames) {
		this.authorDetailNames = authorDetailNames;
	}
	
	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		authorDetailIds = null;
		authorDetailNames = null;
		
 
		super.reset(mapping, request);
	}

	@Override
    public Class<HrStaffFileAuthor> getModelClass() {
		return HrStaffFileAuthor.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("authorDetailIds", new FormConvertor_IDsToModelList(
					"authorDetail", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
