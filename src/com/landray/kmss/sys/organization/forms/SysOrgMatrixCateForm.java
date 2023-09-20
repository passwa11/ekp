package com.landray.kmss.sys.organization.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgMatrixCate;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 组织矩阵分类
 * 
 * @author 潘永辉 2019年6月4日
 *
 */
public class SysOrgMatrixCateForm extends ExtendForm {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类名称
	 */
	private String fdName;

	/**
	 * 上级分类
	 */
	private String fdParentId;

	private String fdParentName;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	/**
	 * 修改时间
	 */
	private Date fdAlterTime = new Date();

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	public String getFdParentName() {
		return fdParentName;
	}

	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
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
	public Class<?> getModelClass() {
		return SysOrgMatrixCate.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdParentId = null;
		fdParentName = null;
		fdCreateTime = new Date();
		fdAlterTime = new Date();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysOrgMatrixCate.class));
		}
		return toModelPropertyMap;
	}

}
