package com.landray.kmss.sys.organization.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 组织矩阵版本
 * 
 * @author 潘永辉 2020年2月24日
 *
 */
public class SysOrgMatrixVersionForm extends ExtendForm {
	private static final long serialVersionUID = 1L;

	/**
	 * 版本名称
	 */
	private String fdName;
	
	/**
	 * 版本号
	 */
	private Integer fdVersion;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	/**
	 * 是否启用
	 */
	private Boolean fdIsEnable;

	private Boolean fdIsDelete;

	/**
	 * 所属矩阵
	 */
	private String fdMatrixId;

	private String fdMatrixName;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdVersion = null;
		fdMatrixId = null;
		fdMatrixName = null;
		fdIsEnable = null;
		fdCreateTime = new Date();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdMatrixId", new FormConvertor_IDToModel("fdMatrix", SysOrgMatrix.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysOrgMatrixVersion.class;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Integer getFdVersion() {
		return fdVersion;
	}

	public void setFdVersion(Integer fdVersion) {
		this.fdVersion = fdVersion;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getFdMatrixId() {
		return fdMatrixId;
	}

	public void setFdMatrixId(String fdMatrixId) {
		this.fdMatrixId = fdMatrixId;
	}

	public String getFdMatrixName() {
		return fdMatrixName;
	}

	public void setFdMatrixName(String fdMatrixName) {
		this.fdMatrixName = fdMatrixName;
	}

	public Boolean getFdIsEnable() {
		return fdIsEnable;
	}

	public void setFdIsEnable(Boolean fdIsEnable) {
		this.fdIsEnable = fdIsEnable;
	}

	public Boolean getFdIsDelete() {
		if (fdIsDelete == null) {
			fdIsDelete = false;
		}
		return fdIsDelete;
	}

	public void setFdIsDelete(Boolean fdIsDelete) {
		this.fdIsDelete = fdIsDelete;
	}

}
