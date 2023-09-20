package com.landray.kmss.third.ldap.ssl;


import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Active Derictory 操作类
 * 
 * @author Administrator
 * 
 */
public class ADOperator {

	private static Logger log = org.slf4j.LoggerFactory.getLogger(ADOperator.class);

	private DirContext ctx;

	private static final String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	// private static final int UF_ACCOUNTDISABLE = 0x0002;// 帐户禁用

	private static final int UF_PASSWD_NOTREQD = 0x0020;// 密码可以不设（为空）

	// private static final int UF_PASSWD_CANT_CHANGE = 0x0040;// 用户不能更改密码

	private static final int UF_NORMAL_ACCOUNT = 0x0200;// 普通帐户

	private static final int UF_DONT_EXPIRE_PASSWD = 0x10000;// 密码永不过期

	// private static final int UF_PASSWORD_EXPIRED = 0x800000;// 用户下次登陆时须更改密码

	private static String ldap_url; // ldap地址

	private static String auth_type;// 认证类型，一般为simple

	private static String manager_dn; // ldap管理员的dn

	private static String manager_password;// ldap管理员密码
	
	private static String certficationPath;// JDK中导入的证书路径
	
	private static String certficationPwd;// 证书密码

	private static String domain; // 域名，包括@符号

	private static String baseUserDN;// 用户的DN基础路径

	//private static String enabledLCS; // 是否开户用户的LCS功能

	//private static String LCServerDN; // LCS服务器的ldap DN

	static {
		//readLdapInfo();
	}
	
	public static ADOperator getInstance() {
		return new ADOperator();
	}

	public ADOperator() {
			//readLdapInfo();
	}

//	private static void readLdapInfo() {
//		Properties p = new Properties();
//		try {
//			//p.load(ADOperator.class.getResourceAsStream("/conf/ldap.properties"));
//			ldap_url = "ldap://192.168.2.169:636";
//			auth_type = "simple";
//			manager_dn = "cn=administrator,cn=users,dc=test,dc=com";
//			manager_password = "landray@123";
//			//domain = "@" + p.getProperty("domain");
//			//enabledLCS = p.getProperty("enabledLCS");
//			//LCServerDN = p.getProperty("LCServerDN");
//			baseUserDN = "OU=26天津瑞能,OU=广东明阳风电产业集团有限公司,DC=test,DC=com";
//			certficationPath = "d:/ssl2.jks";
//			certficationPwd = "landray";
//		} catch (Exception e) {
//			throw new RuntimeException("ldap.properties文件读取失败.", e);
//			//log.error("数据读取失败!!!!!");
//		}
//	}

	/**
	 * 关闭LDAP连接
	 * 
	 * @param dirContext
	 *            DirContext 已连接的LDAP的Context实例
	 */
	private void closeDirContext() {
		try {
			if (ctx != null) {
                ctx.close();
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取 LDAP 服务器连接的方法
	 * 
	 * @param env
	 *            连接LDAP的连接信息
	 * @return DirContext - LDAP server的连接
	 */
	private void initDirContext() {
		try {
			//设置系统属性中ssl连接的证书和密码
			System.setProperty("javax.net.ssl.trustStore", certficationPath);
			System.setProperty("javax.net.ssl.trustStorePassword", certficationPwd);
			
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
			env.put(Context.PROVIDER_URL, ldap_url);
			env.put(Context.SECURITY_AUTHENTICATION, auth_type);
			env.put(Context.SECURITY_PRINCIPAL, manager_dn);
			env.put(Context.SECURITY_CREDENTIALS, manager_password);
			env.put(Context.SECURITY_PROTOCOL, "ssl");
			// 通过参数连接LDAP/AD
			ctx = new InitialDirContext(env);
			
			System.out.println("连接成功");


		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
	}

	

	/**
	 * 修改密码需要证书，请确认已经导入了证书JDK中
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void changePassword() throws Exception {
//		if(this.ctx == null) {
//			initDirContext();
//		}
//
//		ModificationItem[] mods = new ModificationItem[1];
//		String newQuotedPassword = "\"" + "Landray1234" + "\"";
//		byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
//		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
//				new BasicAttribute("unicodePwd", newUnicodePassword));
//		ctx.modifyAttributes(getDN("阿里妈妈", baseUserDN), mods);
//
//		this.closeDirContext();
	}

	
	private String getDN(String cn, String baseDn) {
		return "cn=" + cn + "," + baseDn;
	}

	// 查找用户
	private Attributes findUser(String cn, String baseDn) {
		Attributes attr = null;
		try {
			attr = ctx.getAttributes(getDN(cn, baseDn));
		} catch(NameNotFoundException e) {
			if(log.isDebugEnabled()) {
				log.debug("AD中用户" + cn + "不存在。");
			}
		} catch (Exception e) {
			log.error("ldap查找AD中的用户出错", e);
			throw new RuntimeException(e);
		}
		return attr;
	}

	// 验证用户是否存在
	public boolean isUserexist(String cn, String baseDn) {
		boolean isThisOpen = false;//标志是否是本方法里打开的上下文
		if(this.ctx == null) {
			initDirContext();
			isThisOpen = true;
		}
		
		boolean rtn = true;

		Attributes attrs = findUser(cn, baseDn);
		if (attrs == null) {
			rtn = false;
		}
		
		if(isThisOpen) {
			//如果是本方法打开的上下文则关掉上下文
			this.closeDirContext();
		}

		return rtn;
	}
	
	// 验证用户是否存在
	public boolean isUserexist(String cn) {
		return isUserexist(cn, baseUserDN);
	}

	// 设置属性
	private void putAttribute(Attributes attrs, String attrName,
			Object attrValue) {
		if (attrValue != null && attrValue.toString().length() != 0) {
			Attribute attr = new BasicAttribute(attrName, attrValue);
			attrs.put(attr);
		}
	}

	private Attributes addObjclassAttrs(Attributes attrs) {
		Attribute objclass = new BasicAttribute("objectclass");
		objclass.add("top");
		objclass.add("person");
		objclass.add("organizationalPerson");
		objclass.add("user");
		attrs.put(objclass);
		return attrs;
	}

	

	/**
	 * 以防万一
	 */
	@Override
    protected void finalize() {
		closeDirContext();
	}

	public static void main(String[] args) throws Exception {
		//ADOperator a = new ADOperator();
		//a.changePassword();
		
//		Class socketFactoryClass = ClassUtils.forName("com.landray.kmss.third.ldap.LdapSSLSocketFactory");
//        Method getDefault =
//            socketFactoryClass.getMethod("getDefault", new Class[]{});
//        Object factory = getDefault.invoke(null, new Object[]{});
//        System.out.println(factory);
        
        
        Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.2.169:389".replace("389", "636"));
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL,
				"administrator");
		env.put(Context.SECURITY_CREDENTIALS,
				"landray@123");
		env.put(Context.SECURITY_PROTOCOL, "ssl");
		env.put("java.naming.ldap.factory.socket", "com.landray.kmss.third.ldap.MySSLSocketFactory");
		//env.put("java.naming.ldap.factory.socket", "com.landray.kmss.third.ldap.ssl.MySSLSocketFactory");
        
		// 通过参数连接LDAP/AD
		InitialDirContext ctx = new InitialDirContext(env);

		System.out.println(ctx);
	}
}
