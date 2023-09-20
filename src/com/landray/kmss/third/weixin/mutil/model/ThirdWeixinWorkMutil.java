package com.landray.kmss.third.weixin.mutil.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.weixin.mutil.forms.ThirdWeixinWorkForm;

/**
 * 应用配置
 * 
 * @author 
 * @version 1.0 2017-05-03
 */
public class ThirdWeixinWorkMutil extends BaseModel {

	/**
	 * 应用名称
	 */
	private String fdName;

	/**
	 * @return 应用名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * @param fdName 应用名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 应用ID
	 */
	private String fdAgentid;

	/**
	 * @return 应用ID
	 */
	public String getFdAgentid() {
		return this.fdAgentid;
	}

	/**
	 * @param fdAgentid 应用ID
	 */
	public void setFdAgentid(String fdAgentid) {
		this.fdAgentid = fdAgentid;
	}

	/**
	 * 应用密钥
	 */
	private String fdSecret;

	/**
	 * @return 应用密钥
	 */
	public String getFdSecret() {
		return this.fdSecret;
	}

	/**
	 * @param fdSecret 应用密钥
	 */
	public void setFdSecret(String fdSecret) {
		this.fdSecret = fdSecret;
	}

	/**
	 * 类型
	 */
	private String fdType;

	/**
	 * @return 类型
	 */
	public String getFdType() {
		return this.fdType;
	}

	/**
	 * @param fdType 类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 系统地址
	 */
	private String fdSystemUrl;

	/**
	 * @return 系统地址
	 */
	public String getFdSystemUrl() {
		return this.fdSystemUrl;
	}

	/**
	 * @param fdSystemUrl 系统地址
	 */
	public void setFdSystemUrl(String fdSystemUrl) {
		this.fdSystemUrl = fdSystemUrl;
	}

	/**
	 * 回调地址
	 */
	private String fdCallbackUrl;

	/**
	 * @return 回调地址
	 */
	public String getFdCallbackUrl() {
		return this.fdCallbackUrl;
	}

	/**
	 * @param fdCallbackUrl 回调地址
	 */
	public void setFdCallbackUrl(String fdCallbackUrl) {
		this.fdCallbackUrl = fdCallbackUrl;
	}

	/**
	 * token
	 */
	private String fdToken;

	/**
	 * @return token
	 */
	public String getFdToken() {
		return this.fdToken;
	}

	/**
	 * @param fdToken token
	 */
	public void setFdToken(String fdToken) {
		this.fdToken = fdToken;
	}

	/**
	 * AESkey
	 */
	private String fdAeskey;

	/**
	 * @return AESkey
	 */
	public String getFdAeskey() {
		return this.fdAeskey;
	}

	/**
	 * @param fdAeskey AESkey
	 */
	public void setFdAeskey(String fdAeskey) {
		this.fdAeskey = fdAeskey;
	}

	/**
	 * accesstoken
	 */
	private String fdAccesstoken;

	/**
	 * @return accesstoken
	 */
	public String getFdAccesstoken() {
		return this.fdAccesstoken;
	}

	/**
	 * @param fdAccesstoken accesstoken
	 */
	public void setFdAccesstoken(String fdAccesstoken) {
		this.fdAccesstoken = fdAccesstoken;
	}

	/**
	 * 创建时间
	 */
	private Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 创建者
	 */
	private SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * @param docCreator 创建者
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	//机制开始
	//机制结束

	@Override
    public Class<ThirdWeixinWorkForm> getFormClass() {
		return ThirdWeixinWorkForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}

	/**
	 * 应用配置
	 */
	private String fdModelName;

	private String fdUrlPrefix;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdUrlPrefix() {
		return fdUrlPrefix;
	}

	public void setFdUrlPrefix(String fdUrlPrefix) {
		this.fdUrlPrefix = fdUrlPrefix;
	}

	/**
	 * 企业微信标识
	 */
	private String fdWxKey;

	public String getFdWxKey() {
		return fdWxKey;
	}

	public void setFdWxKey(String fdWxKey) {
		this.fdWxKey = fdWxKey;
	}

}
