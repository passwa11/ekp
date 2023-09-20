package com.landray.kmss.third.ldap.ssl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapsAuthn {
//    public static void main(String args[]) throws Exception {
//        String server = "192.168.2.169";
//        String port = "636";
//        String admin = "cn=administrator,cn=users,dc=test,dc=com";
//        String adminPass = "landray@123";
//        String testUser = "alimama";
//        String baseDN = "OU=26天津瑞能,OU=广东明阳风电产业集团有限公司,DC=test,DC=com";
//        if (connect(server, port, admin, adminPass, testUser, baseDN)) {
//            System.out.println("Successful");
//        } else {
//            System.out.println("Fail");
//        }
//    }
//
//    public static boolean connect(String server, String port, String user, String passwd, String testUser, String baseDN) throws Exception {
//        boolean result = false;
//        Properties env = new Properties();
//        String ldapURL = "ldap://" + server + ":" + port;
//
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, user);
//        env.put(Context.SECURITY_CREDENTIALS, passwd);
//        env.put(Context.PROVIDER_URL, ldapURL);
//        env.put(Context.REFERRAL,"ignore");
//        env.put(Context.SECURITY_PROTOCOL,"ssl");
//        // DummySSLSocketFactory会绕过证书
//        //env.put("java.naming.ldap.factory.socket", "com.landray.kmss.third.ldap.ssl.DummySSLSocketFactory");
//        // MySSLSocketFactory使用证书库管理加载对应证书，
//        env.put("java.naming.ldap.factory.socket", "com.landray.kmss.third.ldap.ssl.MySSLSocketFactory");
//        try {
//            DirContext ctx = new InitialDirContext(env);
//            SearchControls searchCtls = new SearchControls();
//            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            NamingEnumeration<?> results = ctx.search(baseDN, "uid=" + testUser, searchCtls);
//            while (results.hasMoreElements()) {
//                SearchResult sr = (SearchResult) results.next();
//                Attributes attributes = sr.getAttributes();
//                System.out.println(attributes);
//            }
//
//
//            ModificationItem[] mods = new ModificationItem[1];
//    		String newQuotedPassword = "\"" + "Landray1234567" + "\"";
//    		byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
//    		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
//    				new BasicAttribute("unicodePwd", newUnicodePassword));
//    		ctx.modifyAttributes("CN=阿里妈妈,OU=26天津瑞能,OU=广东明阳风电产业集团有限公司,DC=test,DC=com", mods);
//    		System.out.println("密码修改成功");
//
//            ctx.close();
//            result = true;
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
}
