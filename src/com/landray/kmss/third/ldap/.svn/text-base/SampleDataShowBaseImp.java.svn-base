package com.landray.kmss.third.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public abstract class SampleDataShowBaseImp {

	Map<String, String> langs = new HashMap<String, String>();

	List<LdapCustomProp> customPorps = new ArrayList<LdapCustomProp>();

	protected String deptInfo;
	protected String personInfo;
	protected String postInfo;
	protected String groupInfo;

	protected int showCount = 5;

	protected static String[] deptProps = new String[] { "name", "parent",
			"thisleader", "superleader", "number", "order", "keyword", "memo",
			"unid" };
	protected static String[] personProps = new String[] { "name", "loginName",
			"password", "mail", "dept", "mobileNo", "workPhone", "lang", "rtx",
			"wechat", "shortNo", "scard", "post", "number", "order", "keyword",
			"memo", "unid", "sex" };
	protected static String[] postProps = new String[] { "name", "dept",
			"thisleader", "member", "number", "order", "keyword", "memo",
			"unid" };
	protected static String[] groupProps = new String[] { "name", "member",
			"number", "order", "keyword", "memo", "unid" };

	public SampleDataShowBaseImp() {
		try {
			String sc = LdapUtil.getPropertyValue("kmss.ldap.config.showCount");
			if (StringUtil.isNotNull(sc)) {
				showCount = Integer.parseInt(sc);
			}
			if (SysLangUtil.isLangEnabled()
					&& SysLangUtil.isPropertyLangSupport(
							SysOrgElement.class.getName(), "fdName")) {
				langs = SysLangUtil.getSupportedLangs();
			}
			customPorps = LdapUtil.getLdapCustomProps(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getDeptInfo() {
		return deptInfo;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public String getPostInfo() {
		return postInfo;
	}

	public String getGroupInfo() {
		return groupInfo;
	}

	public String getOtherInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append(ResourceUtil.getString("kmss.ldap.config.showCount",
				"third-ldap") + showCount + " "
				+ ResourceUtil.getString("ldap.sample.data", "third-ldap")
				+ "<br>\n");
		return sb.toString();
	}

	public String getString(String str) {
		if (StringUtil.isNotNull(str)) {
			return str;
		}
		return "&nbsp;";
	}

	abstract public void handle() throws Exception;

}
