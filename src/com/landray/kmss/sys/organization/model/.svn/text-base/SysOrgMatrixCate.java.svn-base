package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixCateForm;
import com.landray.kmss.util.ModelUtil;

/**
 * 组织矩阵分类
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrixCate extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类名称
	 */
	private String fdName;

	/**
	 * 上级分类
	 */
	private SysOrgMatrixCate fdParent;

	/**
	 * 子类
	 */
	private List<SysOrgMatrixCate> fdChildren;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	/**
	 * 修改时间
	 */
	private Date fdAlterTime = new Date();

	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", fdName);
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public SysOrgMatrixCate getFdParent() {
		return getHbmParent();
	}

	public void setFdParent(SysOrgMatrixCate parent) {
		ModelUtil.checkTreeCycle(this, parent, "fdParent");
		setHbmParent(parent);
	}

	public SysOrgMatrixCate getHbmParent() {
		return fdParent;
	}

	public void setHbmParent(SysOrgMatrixCate parent) {
		this.fdParent = parent;
	}

	public List<SysOrgMatrixCate> getFdChildren() {
		List<SysOrgMatrixCate> rtnVal = new ArrayList<SysOrgMatrixCate>();
		if (getHbmChildren() != null) {
            rtnVal.addAll(getHbmChildren());
        }
		return rtnVal;
	}

	public void setFdChildren(List<SysOrgMatrixCate> children) {
		if (this.fdChildren == children) {
            return;
        }
		if (this.fdChildren == null) {
            this.fdChildren = new ArrayList<SysOrgMatrixCate>();
        } else {
            this.fdChildren.clear();
        }
		if (children != null) {
            this.fdChildren.addAll(children);
        }
	}

	public List<SysOrgMatrixCate> getHbmChildren() {
		return fdChildren;
	}

	public void setHbmChildren(List<SysOrgMatrixCate> children) {
		this.fdChildren = children;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	@Override
	public Class<?> getFormClass() {
		return SysOrgMatrixCateForm.class;
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

	public String getFdNameOri() {
		return fdName;
	}

}
