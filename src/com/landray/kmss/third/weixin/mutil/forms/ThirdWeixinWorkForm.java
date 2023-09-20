package com.landray.kmss.third.weixin.mutil.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.weixin.mutil.model.ThirdWeixinWorkMutil;
import com.landray.kmss.web.action.ActionMapping;



/**
 * 应用配置 Form
 * 
 * @author 
 * @version 1.0 2017-05-03
 */
public class ThirdWeixinWorkForm  extends ExtendForm  {

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
	private String docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 创建者的ID
	 */
	private String docCreatorId;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	private String docCreatorName;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdAgentid = null;
		fdSecret = null;
		fdSystemUrl = null;
		fdCallbackUrl = null;
		fdToken = null;
		fdAeskey = null;
		fdAccesstoken = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		
 
		super.reset(mapping, request);
	}

	@Override
    public Class<ThirdWeixinWorkMutil> getModelClass() {
		return ThirdWeixinWorkMutil.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

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
