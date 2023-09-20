package com.landray.kmss.sys.restservice.server.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 访问安全策略 Form
 * 
 * @author  
 */
public class SysRestserviceServerPolicyForm extends ExtendForm {
	
	/**
	 * 策略名称
	 */
	protected String fdName;
	
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	/**
	 * 策略模式
	 */
	private String fdPolicy;

	public String getFdPolicy() {
		return fdPolicy;
	}

	public void setFdPolicy(String fdPolicy) {
		this.fdPolicy = fdPolicy;
	}

	/**
	 * 用户帐号
	 */
	protected String fdLoginId = null;

	/**
	 * @return 用户帐号
	 */
	public String getFdLoginId() {
		return fdLoginId;
	}

	/**
	 * @param fdLoginId
	 *            用户帐号
	 */
	public void setFdLoginId(String fdLoginId) {
		this.fdLoginId = fdLoginId;
	}

	/**
	 * 用户密码
	 */
	protected String fdPassword = null;

	/**
	 * @return 用户密码
	 */
	public String getFdPassword() {
		return fdPassword;
	}

	/**
	 * @param fdPassword
	 *            用户密码
	 */
	public void setFdPassword(String fdPassword) {
		this.fdPassword = fdPassword;
	}

	/**
	 * appname(固定密钥访问)
	 */
	protected String fdAppname;
	
	public String getFdAppname() {
		return fdAppname;
	}

	public void setFdAppname(String fdAppname) {
		this.fdAppname = fdAppname;
	}
	
	/**
	 * headername(固定密钥访问)
	 */
	protected String fdHeadername;

	public String getFdHeadername() {
		return fdHeadername;
	}

	public void setFdHeadername(String fdHeadername) {
		this.fdHeadername = fdHeadername;
	}
	
	/**
	 * 密钥(固定密钥访问)
	 */
	protected String fdSecretKey;
	
	public String getFdSecretKey() {
		return fdSecretKey;
	}

	public void setFdSecretKey(String fdSecretKey) {
		this.fdSecretKey = fdSecretKey;
	}
	
	/**
	 * 账号认证方式
	 */
	protected String fdAuthType;
	
	public String getFdAuthType() {
		return fdAuthType;
	}

	public void setFdAuthType(String fdAuthType) {
		this.fdAuthType = fdAuthType;
	}
	
	/**
	 * 允许的客户端IP地址
	 */
	protected String fdAccessIp = null;

	/**
	 * @return 允许的客户端IP地址
	 */
	public String getFdAccessIp() {
		return fdAccessIp;
	}

	/**
	 * @param fdAccessIp
	 *            允许的客户端IP地址
	 */
	public void setFdAccessIp(String fdAccessIp) {
		this.fdAccessIp = fdAccessIp;
	}

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 备注
	 */
	protected String fdDescription = null;

	/**
	 * @return 备注
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	/**
	 * @param fdDescription
	 *            备注
	 */
	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 创建人的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建人的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建人的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建人的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建人的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 主服务ID的ID列表
	 */
	protected String fdServiceIds = null;

	/**
	 * @return 主服务ID的ID列表
	 */
	public String getFdServiceIds() {
		return fdServiceIds;
	}

	/**
	 * @param fdServiceIds
	 *            主服务ID的ID列表
	 */
	public void setFdServiceIds(String fdServiceIds) {
		this.fdServiceIds = fdServiceIds;
	}

	/**
	 * 主服务ID的名称列表
	 */
	protected String fdServiceNames = null;

	/**
	 * @return 主服务ID的名称列表
	 */
	public String getFdServiceNames() {
		return fdServiceNames;
	}

	/**
	 * @param fdServiceNames
	 *            主服务ID的名称列表
	 */
	public void setFdServiceNames(String fdServiceNames) {
		this.fdServiceNames = fdServiceNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPolicy = null;
		fdLoginId = null;
		fdPassword = null;
		fdAccessIp = null;
		docCreateTime = null;
		fdDescription = null;
		docCreatorId = null;
		docCreatorName = null;
		fdServiceIds = null;
		fdServiceNames = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysRestserviceServerPolicy.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("fdServiceIds",
					new FormConvertor_IDsToModelList("fdService",
							SysRestserviceServerMain.class));
		}
		return toModelPropertyMap;
	}
}
