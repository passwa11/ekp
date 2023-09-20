package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.weixin.model.ThirdWxOmsInit;

/**
 * 组织初始化 Form
 * 
 * @author
 * @version 1.0 2017-06-08
 */
public class ThirdWxOmsInitForm extends ExtendForm {

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
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 路径
	 */
	private String fdPath;

	/**
	 * @return 路径
	 */
	public String getFdPath() {
		return this.fdPath;
	}

	/**
	 * @param fdPath
	 *            路径
	 */
	public void setFdPath(String fdPath) {
		this.fdPath = fdPath;
	}

	/**
	 * 微信ID
	 */
	private String fdWeixinId;

	/**
	 * @return 微信ID
	 */
	public String getFdWeixinId() {
		return this.fdWeixinId;
	}

	/**
	 * @param fdWeixinId
	 *            微信ID
	 */
	public void setFdWeixinId(String fdWeixinId) {
		this.fdWeixinId = fdWeixinId;
	}

	/**
	 * 微信处理状态
	 */
	private String fdWxStatus;

	/**
	 * @return 微信处理状态
	 */
	public String getFdWxStatus() {
		return this.fdWxStatus;
	}

	/**
	 * @param fdWxStatus
	 *            微信处理状态
	 */
	public void setFdWxStatus(String fdWxStatus) {
		this.fdWxStatus = fdWxStatus;
	}

	/**
	 * EKP处理状态
	 */
	private String fdEkpStatus;

	/**
	 * @return EKP处理状态
	 */
	public String getFdEkpStatus() {
		return this.fdEkpStatus;
	}

	/**
	 * @param fdEkpStatus
	 *            EKP处理状态
	 */
	public void setFdEkpStatus(String fdEkpStatus) {
		this.fdEkpStatus = fdEkpStatus;
	}

	/**
	 * 是否微信企业号
	 */
	private String fdIsWx;

	/**
	 * @return 是否微信企业号
	 */
	public String getFdIsWx() {
		return this.fdIsWx;
	}

	/**
	 * @param fdIsWx
	 *            是否微信企业号
	 */
	public void setFdIsWx(String fdIsWx) {
		this.fdIsWx = fdIsWx;
	}

	/**
	 * 是否部门
	 */
	private String fdIsOrg;

	/**
	 * @return 是否部门
	 */
	public String getFdIsOrg() {
		return this.fdIsOrg;
	}

	/**
	 * @param fdIsOrg
	 *            是否部门
	 */
	public void setFdIsOrg(String fdIsOrg) {
		this.fdIsOrg = fdIsOrg;
	}

	/**
	 * 状态
	 */
	private String fdStatus;

	/**
	 * @return 状态
	 */
	public String getFdStatus() {
		return this.fdStatus;
	}

	/**
	 * @param fdStatus
	 *            状态
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 处理状态
	 */
	private String fdHandleStatus;
	
	/**
	 * @return 处理状态
	 */
	public String getFdHandleStatus() {
		return this.fdHandleStatus;
	}
	
	/**
	 * @param fdHandleStatus 处理状态
	 */
	public void setFdHandleStatus(String fdHandleStatus) {
		this.fdHandleStatus = fdHandleStatus;
	}

	/**
	 * 映射组织的ID
	 */
	private String fdEkpId;

	/**
	 * @return 映射组织的ID
	 */
	public String getFdEkpId() {
		return this.fdEkpId;
	}

	/**
	 * @param fdEkpId
	 *            映射组织的ID
	 */
	public void setFdEkpId(String fdEkpId) {
		this.fdEkpId = fdEkpId;
	}

	/**
	 * 映射组织的名称
	 */
	private String fdEkpName;

	/**
	 * @return 映射组织的名称
	 */
	public String getFdEkpName() {
		return this.fdEkpName;
	}

	/**
	 * @param fdEkpName
	 *            映射组织的名称
	 */
	public void setFdEkpName(String fdEkpName) {
		this.fdEkpName = fdEkpName;
	}

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPath = null;
		fdWeixinId = null;
		fdWxStatus = null;
		fdEkpStatus = null;
		fdIsWx = null;
		fdIsOrg = null;
		fdStatus = null;
		fdEkpId = null;
		fdEkpName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<ThirdWxOmsInit> getModelClass() {
		return ThirdWxOmsInit.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdEkpId",
					new FormConvertor_IDToModel("fdEkp", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
