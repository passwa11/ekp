package com.landray.kmss.third.ldap.oms.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgGroup;
import com.landray.kmss.third.ldap.base.BaseLdapEntry;

public class LdapGroup extends DefaultOrgGroup {
	public LdapGroup(BaseLdapEntry entry) throws Exception {
		List required = new ArrayList();
		setLdapDN(entry.getObjectProperty("dn").toString());
		if (entry.isPropertyMap("name")) {
			setName(entry.getStringProperty("name"));
			required.add("name");
		}

		if (entry.isPropertyMap("number")) {
			setNo(entry.getStringProperty("number"));
			required.add("no");
		}
		if (entry.isPropertyMap("unid")) {
			setImportInfo(entry.getStringProperty("unid"));
			required.add("importInfo");
		}
		if (entry.isPropertyMap("member")) {
			setMembers((String[]) entry.getImportInfoValues("member").toArray(
					new String[0]));
			/*
			 * List list = entry.getEntryListProperty("member"); String[]
			 * members = new String[list.size()]; for (int j = 0; j <
			 * list.size(); j++) { members[j] = ((LdapEntry) list.get(j))
			 * .getStringProperty("unid"); } setMembers(members);
			 */
			required.add("members");
		}

		if (entry.isPropertyMap("keyword")) {
			setKeyword(entry.getStringProperty("keyword"));
			required.add("keyword");
		}
		if (entry.isPropertyMap("memo")) {
			setMemo(entry.getStringProperty("memo"));
			required.add("memo");
		}

		setAlterTime(entry.getDateProperty("modifytimestamp"));

		required.add("shortName");
		required.add("isAvailable");
		required.add("isBusiness");

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
		sb.append("群组::" + split);
		sb.append("名称=" + this.getName() + split);
		if (this.getNo() != null) {
			sb.append("编号=" + this.getNo() + split);
		}
		if (this.getMembers() != null) {
			sb.append("成员=[" + split);
			for (int i = 0; i < this.getMembers().length; i++) {
				sb.append(this.getMembers()[i] + split);
			}
			sb.append("]" + split);
		}
		sb.append("修改时间=" + this.getAlterTime() + split);
		return sb.toString();
	}

}