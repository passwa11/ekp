package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgRoleConfCateForm;
import com.landray.kmss.util.ModelUtil;

/**
 * 角色线类别
 * 
 * @author 潘永辉
 */
public class SysOrgRoleConfCate extends BaseModel {
	/*
	 * 名称
	 */
	private String fdName;

	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public String getFdNameOri() {
		return fdName;
	}

	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 关键字
	 */
	private String fdKeyword;

	public String getFdKeyword() {
		return fdKeyword;
	}

	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
	}
	
	/*
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/*
	 * 最后修改时间
	 */
	private Date fdAlterTime = new Date();

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	/*
	 * 父类别
	 */
	private SysOrgRoleConfCate fdParent;

	public SysOrgRoleConfCate getFdParent() {
		return getHbmParent();
	}

	public void setFdParent(SysOrgRoleConfCate parent) {
		ModelUtil.checkTreeCycle(this, parent, "fdParent");
		setHbmParent(parent);
	}

	public SysOrgRoleConfCate getHbmParent() {
		return fdParent;
	}

	public void setHbmParent(SysOrgRoleConfCate parent) {
		this.fdParent = parent;
	}

	/*
	 * 子类别
	 */
	private List fdChildren;

	public List getFdChildren() {
		List rtnVal = new ArrayList();
		if (getHbmChildren() != null) {
            rtnVal.addAll(getHbmChildren());
        }
		return rtnVal;
	}

	public void setFdChildren(List children) {
		if (this.fdChildren == children) {
            return;
        }
		if (this.fdChildren == null) {
            this.fdChildren = new ArrayList();
        } else {
            this.fdChildren.clear();
        }
		if (children != null) {
            this.fdChildren.addAll(children);
        }
	}

	public List getHbmChildren() {
		return fdChildren;
	}

	public void setHbmChildren(List children) {
		this.fdChildren = children;
	}

	@Override
	public Class getFormClass() {
		return SysOrgRoleConfCateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
		}
		return toFormPropertyMap;
	}
}
