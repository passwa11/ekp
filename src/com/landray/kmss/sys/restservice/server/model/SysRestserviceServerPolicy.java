package com.landray.kmss.sys.restservice.server.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.restservice.server.forms.SysRestserviceServerPolicyForm;

/**
 * 访问安全策略
 * 
 * @author  
 */
@SuppressWarnings("serial")
public class SysRestserviceServerPolicy extends BaseModel {
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
	protected String fdLoginId;

	
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
	protected String fdPassword;

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
	protected String fdAccessIp;

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
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 备注
	 */
	protected String fdDescription;

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
	 * 创建人
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 主服务ID
	 */
	protected List<SysRestserviceServerMain> fdService;

	/**
	 * @return 主服务ID
	 */
	public List<SysRestserviceServerMain> getFdService() {
		return fdService;
	}

	/**
	 * @param fdService
	 *            主服务ID
	 */
	public void setFdService(List<SysRestserviceServerMain> fdService) {
		this.fdService = fdService;
	}

	@Override
    public Class getFormClass() {
		return SysRestserviceServerPolicyForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdService",
					new ModelConvertor_ModelListToString(
							"fdServiceIds:fdServiceNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
