package com.landray.kmss.third.ldap.oms.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgPerson;
import com.landray.kmss.third.ldap.LdapCustomProp;
import com.landray.kmss.third.ldap.LdapUtil;
import com.landray.kmss.third.ldap.base.BaseLdapEntry;
import com.landray.kmss.util.SecureUtil;

public class LdapPerson extends DefaultOrgPerson {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapPerson.class);
	
	public LdapPerson(BaseLdapEntry entry) throws Exception {
		List required = new ArrayList();
		setLdapDN(entry.getObjectProperty("dn").toString());
		if (entry.isPropertyMap("loginName")) {
			setLoginName(entry.getStringProperty("loginName"));
			required.add("loginName");
		}

		if (entry.isPropertyMap("unid")) {
			setImportInfo(entry.getStringProperty("unid"));
			required.add("importInfo");
		}

		if (entry.isPropertyMap("password")) {
			if (entry.getObjectProperty("password") != null) {
				String ldapPassword = new String(
						(byte[]) entry.getObjectProperty("password"));
				setPassword(SecureUtil.BASE64Encoder(ldapPassword));
				required.add("password");
			}
		}
		if (entry.isPropertyMap("name")) {
			setName(entry.getStringProperty("name"));
			required.add("name");
		}
		if (entry.isPropertyMap("mail")) {
			setEmail(entry.getStringProperty("mail"));
			required.add("email");
		}
		if (entry.isPropertyMap("order")) {
			try {
				logger.debug("设置排序号：" + entry.getStringProperty("order"));
				setOrder(new Integer(entry.getStringProperty("order")));
			} catch (Exception ex) {
				logger.error(
						"设置排序号出错," + entry.getStringProperty("order") + ".",
						ex);
				setOrder(new Integer(0));
			}
			required.add("order");
		}
		if (entry.isPropertyMap("dept")) {
			setParent(entry.getImportInfoValue("dept", null));
			/*
			 * LdapEntry otherEntry = entry.getEntryProperty("dept"); for (;
			 * otherEntry != null; otherEntry = otherEntry
			 * .getEntryProperty("dept")) {
			 * setParent(otherEntry.getStringProperty("unid")); break; }
			 */
			required.add("parent");
		}

		if (entry.isPropertyMap("post")) {
			setPosts((String[]) entry.getImportInfoValues("post").toArray(
					new String[0]));
			/*
			 * List list = entry.getEntryListProperty("post"); String[] posts =
			 * new String[list.size()]; for (int j = 0; j < list.size(); j++) {
			 * posts[j] = ((LdapEntry) list.get(j)).getStringProperty("unid"); }
			 * setPosts(posts);
			 */
			required.add("posts");
		}

		setAlterTime(entry.getDateProperty("modifytimestamp"));

		required.add("shortName");
		required.add("isAvailable");
		required.add("isBusiness");

		if (entry.isPropertyMap("mobileNo")) {
			setMobileNo(entry.getStringProperty("mobileNo"));
			required.add("mobileNo");
		}
		if (entry.isPropertyMap("workPhone")) {
			setWorkPhone(entry.getStringProperty("workPhone"));
			required.add("workPhone");
		}
		if (entry.isPropertyMap("cardNumber")) {
			setAttendanceCardNumber(entry.getStringProperty("cardNumber"));
			required.add("cardNumber");
		}

		if (entry.isPropertyMap("keyword")) {
			setKeyword(entry.getStringProperty("keyword"));
			required.add("keyword");
		}
		if (entry.isPropertyMap("memo")) {
			setMemo(entry.getStringProperty("memo"));
			required.add("memo");
		}
		if (entry.isPropertyMap("scard")) {
			setScard(entry.getStringProperty("scard"));
			required.add("scard");
		}
		if (entry.isPropertyMap("rtx")) {
			setRtx(entry.getStringProperty("rtx"));
			required.add("rtx");
		}
		if (entry.isPropertyMap("wechat")) {
			setWechat(entry.getStringProperty("wechat"));
			required.add("wechat");
		}
		if (entry.isPropertyMap("sex")) {
			String sex = entry.getStringProperty("sex"); // 读取配置
			if (sex != null) {
				if (entry.getSexMale() != null
						&& sex.equalsIgnoreCase(entry.getSexMale())) {
					sex = "M";
				} else if (entry.getSexFemale() != null
						&& sex.equalsIgnoreCase(entry.getSexFemale())) {
					sex = "F";
				}
				setSex("F".equalsIgnoreCase(sex) || "女".equalsIgnoreCase(sex) ? "F"
						: "M");
				required.add("sex");
			}
		}
		if (entry.isPropertyMap("shortNo")) {
			setShortNo(entry.getStringProperty("shortNo"));
			required.add("shortNo");
		}

		if (entry.isLangSupport()) {
			Map<String, String> langs = SysLangUtil.getSupportedLangs();
			for (String lang : langs.keySet()) {
				if (entry.isPropertyMap("name" + lang)) {
					this.getDynamicMap().put("fdName" + lang,
							entry.getStringProperty("name" + lang));
					required.add("langProps");
				}
			}
		}
		List<LdapCustomProp> customPorps = LdapUtil.getLdapCustomProps(null);
		for (LdapCustomProp prop : customPorps) {
			String fieldName = prop.getFieldName();
			if (entry.isPropertyMap(fieldName)) {
				String fieldType = prop.getFieldType();
				Object value = null;
				if ("Date".equals(fieldType)) {
					value = entry.getDateProperty(fieldName);
				} else {
					value = entry.getStringProperty(fieldName);
				}
				this.getCustomMap().put(fieldName,
						value);
				required.add("customProps");
			}
		}

		if (entry.isPropertyMap("lang")) {
			setLang(entry.getStringProperty("lang"));
			required.add("lang");
		}

		setRequiredOms(required);

	}

	@Override
    public String getShortName() {
		return getName();
	}

	@Override
    public Boolean getIsAvailable() {
		return new Boolean(true);
	}

	@Override
    public Boolean getIsBusiness() {
		return new Boolean(true);
	}

	public String toString(String split) {
		StringBuffer sb = new StringBuffer();
		sb.append("个人::" + split);
		sb.append("UNID=" + this.getKeyword() + split);
		sb.append("名称=" + this.getName() + split);
		sb.append("登录名=" + this.getLoginName() + split);
		if (this.getPassword() != null) {
			sb.append("登录密码=" + SecureUtil.BASE64Decoder(this.getPassword())
					+ split);
		}
		if (this.getPassword() != null) {
			sb.append("Email地址=" + this.getEmail() + split);
		}
		if (this.getMobileNo() != null) {
			sb.append("手机号=" + this.getMobileNo() + split);
		}
		if (this.getWorkPhone() != null) {
			sb.append("办公电话=" + this.getWorkPhone() + split);
		}
		if (this.getSex() != null) {
			sb.append("性别=" + ("F".equals(this.getSex()) ? "女" : "男") + split);
		}
		if (this.getAttendanceCardNumber() != null) {
			sb.append("cardNumber=" + this.getAttendanceCardNumber() + split);
		}
		if (this.getParent() != null) {
			sb.append("部门=" + this.getParent() + split);
		}
		if (this.getPosts() != null) {
			sb.append("岗位=[" + split);
			for (int i = 0; i < this.getPosts().length; i++) {
				sb.append(this.getPosts()[i] + split);
			}
			sb.append("]" + split);
		}
		sb.append("修改时间=" + this.getAlterTime() + split);
		return sb.toString();
	}

}