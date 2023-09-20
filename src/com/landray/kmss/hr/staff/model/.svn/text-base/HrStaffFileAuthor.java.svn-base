package com.landray.kmss.hr.staff.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.hr.staff.model.HrStaffFileAuthor;

import com.landray.kmss.hr.staff.forms.HrStaffFileAuthorForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;



/**
 * 档案授权
 * 
 * @author 
 * @version 1.0 2017-11-10
 */
public class HrStaffFileAuthor  extends BaseModel {

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
	 * 授权人员
	 */
	private List<SysOrgElement> authorDetail;
	
	/**
	 * @return 授权人员
	 */
	public List<SysOrgElement> getAuthorDetail() {
		return this.authorDetail;
	}
	
	/**
	 * @param authorDetail 授权人员
	 */
	public void setAuthorDetail(List<SysOrgElement> authorDetail) {
		this.authorDetail = authorDetail;
	}
	

	//机制开始
	//机制结束

	@Override
    public Class<HrStaffFileAuthorForm> getFormClass() {
		return HrStaffFileAuthorForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("authorDetail",
					new ModelConvertor_ModelListToString(
							"authorDetailIds:authorDetailNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
