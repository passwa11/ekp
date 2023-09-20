package com.landray.kmss.sys.restservice.client.model;

import org.springframework.util.Assert;

import com.landray.kmss.util.StringUtil;

/**
 * <pre>
 * 通用的验证信息对象，name+password仅用于type=="BASIC"的情况
 * 由于认证方式的配置多样性，内容不确定，统一由authConfig字段来描述
 * 目前只支持Basic和Oauth2
 * </pre>
 * @author Kilery.Chen
 *
 */
public class CommonAuthInfo implements AuthInfo{

	public static final String AUTH_TYPE_BASIC = "BASIC";
	public static final String AUTH_TYPE_OAUTH2 = "OAUTH2";
	
	public CommonAuthInfo(String name,String password,String authType) {
		Assert.isTrue(StringUtil.isNotNull(name), "auth name must not be null.");
		Assert.isTrue(StringUtil.isNotNull(authType), 
		        "authType must not be null and should be one of ["+AUTH_TYPE_BASIC+","+AUTH_TYPE_OAUTH2+"]");
		this.name = name;
		this.password = password;
	    if(!AUTH_TYPE_BASIC.equalsIgnoreCase(authType)
                && !AUTH_TYPE_OAUTH2.equalsIgnoreCase(authType)) {
            this.type = AUTH_TYPE_BASIC;
        }else {
            this.type = authType;
        }
	}
	
	public CommonAuthInfo() {
	}
	
	/**
     * 当type==BASIC时使用的用户名
     */
	private String name;
	
	/**
	 * 当type==BASIC时使用的密码
	 */
	private String password;
	
	/**
	 * 认证方式，空表示没有认证
	 */
	private String type;

	/**
	 * 认证详细信息，每种认证方式的配置可以不同，但必须是JsonString，可空
	 */
	private String authConfig;
	
	public String getAuthConfig() {
		return authConfig;
	}

	public void setAuthConfig(String authConfig) {
		this.authConfig = authConfig;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
