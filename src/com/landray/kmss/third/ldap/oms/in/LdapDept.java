package com.landray.kmss.third.ldap.oms.in;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgDept;
import com.landray.kmss.third.ldap.base.BaseLdapEntry;
import com.landray.kmss.util.StringUtil;

public class LdapDept extends DefaultOrgDept {
	public LdapDept(BaseLdapEntry entry) throws Exception {
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

		if (entry.isPropertyMap("order")) {
			String order = entry.getStringProperty("order");
			if (StringUtil.isNotNull(order)
					&& StringUtil.isNotNull(order.trim())){
				try{
					setOrder(new Integer(order));
				}catch (Exception e) {
					setOrder(null);
				}
			}else {
                setOrder(null);
            }
			required.add("order");
		}

		if (entry.isPropertyMap("parent")) {
			setParent(entry.getImportInfoValue("parent", null));
			/*
			 * LdapEntry otherEntry = entry.getEntryProperty("parent"); for (;
			 * otherEntry != null; otherEntry = otherEntry
			 * .getEntryProperty("parent")) {
			 * setParent(otherEntry.getStringProperty("unid")); break; }
			 */
			required.add("parent");
		}

		if (entry.isPropertyMap("thisleader")) {
			setThisLeader(entry.getImportInfoValue("thisleader", null));
			/*
			 * LdapEntry otherEntry = entry.getEntryProperty("thisleader"); for
			 * (; otherEntry != null; otherEntry = otherEntry
			 * .getEntryProperty("thisleader")) {
			 * setThisLeader(otherEntry.getStringProperty("unid")); break; }
			 */
			required.add("thisLeader");
		}

		if (entry.isPropertyMap("superleader")) {
			setSuperLeader(entry.getImportInfoValue("superleader", null));
			/*
			 * LdapEntry otherEntry = entry.getEntryProperty("superleader"); for
			 * (; otherEntry != null; otherEntry = otherEntry
			 * .getEntryProperty("superleader")) {
			 * setSuperLeader(otherEntry.getStringProperty("unid")); break; }
			 */
			required.add("superLeader");
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

		// this.getDynamicMap().

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
		sb.append("部门::" + split);
		sb.append("名称=" + this.getName() + split);
		if (this.getNo() != null) {
			sb.append("编号=" + this.getNo() + split);
		}
		if (this.getParent() != null) {
			sb.append("上级部门=" + this.getParent() + split);
		}
		if (this.getThisLeader() != null) {
			sb.append("本级领导=" + this.getThisLeader() + split);
		}
		if (this.getSuperLeader() != null) {
			sb.append("上级领导=" + this.getSuperLeader() + split);
		}
		sb.append("修改时间=" + this.getAlterTime() + split);
		return sb.toString();
	}

}