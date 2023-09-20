package com.landray.kmss.sys.restservice.auth;

public interface OauthConstants {

	/**
	 * <pre>
	OAuth 2 定义了四种 Grant Type，每一种都有适用的应用场景。
	Authorization Code
	结合普通服务器端应用使用。
	Implicit
	结合移动应用或 Web App 使用。
	Resource Owner Password Credentials
	适用于受信任客户端应用，例如同个组织的内部或外部应用。
	Client Credentials
	适用于客户端调用主服务API型应用
	目前EKP只提供client模式支持
	 * </pre>
	 */
	public enum GrantType {
		Authorization_Code(new String[] {}),
		Implicit(new String[] {}),
		Resource_Owner_Password_Credentials(new String[] {}),
		Client_Credentials(new String[] {CLIENT_ID_KEY,CLIENT_SECRET_KEY,TOKEN_NAME_KEY,ACCESS_TOKEN_URI_KEY,SCOPE_KEY});
		
		private String[] keys;
		public String[] getKeys() {
			return keys;
		}
		private GrantType(String[] keys) {
			
		}
	}
	String PASSWORD="password";
	String USERNAME="username";
	
	String CLIENT_ID_KEY="clientId";
	
	String CLIENT_SECRET_KEY = "clientSecret";
	
	String TOKEN_NAME_KEY = "tokenName";
	
	String ACCESS_TOKEN_URI_KEY= "accessTokenUri";
	
	String SCOPE_KEY = "scope";
}
