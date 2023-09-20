package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.ThirdDingOmsInit;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 组织初始化 Form
 * 
 * @author
 * @version 1.0 2017-06-14
 */
public class ThirdDingOmsInitForm extends ExtendForm {

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
	 * 钉钉ID
	 */
	private String fdDingId;

	/**
	 * @return 钉钉ID
	 */
	public String getFdDingId() {
		return this.fdDingId;
	}

	/**
	 * @param fdDingId
	 *            钉钉ID
	 */
	public void setFdDingId(String fdDingId) {
		this.fdDingId = fdDingId;
	}

	/**
	 * 钉钉处理状态
	 */
	private String fdDingStatus;

	/**
	 * @return 钉钉处理状态
	 */
	public String getFdDingStatus() {
		return this.fdDingStatus;
	}

	/**
	 * @param fdDingStatus
	 *            钉钉处理状态
	 */
	public void setFdDingStatus(String fdDingStatus) {
		this.fdDingStatus = fdDingStatus;
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
	 * @param fdHandleStatus
	 *            处理状态
	 */
	public void setFdHandleStatus(String fdHandleStatus) {
		this.fdHandleStatus = fdHandleStatus;
	}

	/**
	 * EKP映射组织的ID
	 */
	private String fdEkpId;

	/**
	 * @return EKP映射组织的ID
	 */
	public String getFdEkpId() {
		return this.fdEkpId;
	}

	/**
	 * @param fdEkpId
	 *            EKP映射组织的ID
	 */
	public void setFdEkpId(String fdEkpId) {
		this.fdEkpId = fdEkpId;
	}

	/**
	 * EKP映射组织的名称
	 */
	private String fdEkpName;

	/**
	 * @return EKP映射组织的名称
	 */
	public String getFdEkpName() {
		return this.fdEkpName;
	}

	/**
	 * @param fdEkpName
	 *            EKP映射组织的名称
	 */
	public void setFdEkpName(String fdEkpName) {
		this.fdEkpName = fdEkpName;
	}

	/**
	 * 钉钉账号类型
	 */
	private String fdAccountType;

	public String getFdAccountType() {
		return fdAccountType;
	}

	public void setFdAccountType(String fdAccountType) {
		this.fdAccountType = fdAccountType;
	}

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPath = null;
		fdIsOrg = null;
		fdDingId = null;
		fdDingStatus = null;
		fdEkpStatus = null;
		fdHandleStatus = null;
		fdEkpId = null;
		fdEkpName = null;
		fdAccountType=null;
		super.reset(mapping, request);
	}

	@Override
    public Class<ThirdDingOmsInit> getModelClass() {
		return ThirdDingOmsInit.class;
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
