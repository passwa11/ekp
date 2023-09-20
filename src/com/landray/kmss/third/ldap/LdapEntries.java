package com.landray.kmss.third.ldap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ldap的记录集合
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class LdapEntries {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapEntries.class);

	private String type = null;

	private List enrties = new ArrayList();
	
	protected void addLdapEntrys(NamingEnumeration enumeration,String baseDN, LdapContext context,
			String type, Date time) throws Exception {
		//String baseDN = context.getTypeConfig(type, "baseDN");
		for (; enumeration.hasMore();) {
			SearchResult result = (SearchResult) enumeration.next();
			String dn = result.getName();
			dn = dn.trim();
			if (dn.length() == 0) {
                continue;
            }
			if (dn.startsWith("\"") && dn.endsWith("\"")) {
				dn = dn.substring(1, dn.length() - 1);
			}
			dn = dn + "," + formatDN(baseDN);
			LdapEntry entry = new LdapEntry(context, result.getAttributes(),
					type, dn);
			if (time != null) {
				if (time.getTime() == entry.getDateProperty("modifytimestamp")
						.getTime()) {
                    continue;
                }
			}
			enrties.add(entry);
		}
		if (logger.isDebugEnabled()) {
            logger.debug("搜索到" + enrties.size() + "条类型为" + type + "的记录");
        }
	}
	
	protected LdapEntries(String type) {
		this.type = type;
	}

//	protected LdapEntries(NamingEnumeration enumeration, LdapContext context,
//			String type, Date time) throws NamingException {
//		this.type = type;
//		String baseDN = context.getTypeConfig(type, "baseDN");
//		for (; enumeration.hasMore();) {
//			SearchResult result = (SearchResult) enumeration.next();
//			String dn = result.getName();
//			dn = dn.trim();
//			if (dn.length() == 0)
//				continue;
//			if (dn.startsWith("\"") && dn.endsWith("\"")) {
//				dn = dn.substring(1, dn.length() - 1);
//
//			}
//			dn = dn + "," + baseDN;
//			LdapEntry entry = new LdapEntry(context, result.getAttributes(),
//					type, dn);
//			if (time != null) {
//				if (time.getTime() == entry.getDateProperty("modifytimestamp")
//						.getTime())
//					continue;
//			}
//			enrties.add(entry);
//		}
//		if (logger.isDebugEnabled())
//			logger.debug("搜索到" + enrties.size() + "条类型为" + type + "的记录");
//	}

	public int size() {
		return enrties.size();
	}

	public LdapEntry get(int i) {
		return (LdapEntry) enrties.get(i);
	}

	public String getType() {
		return type;
	}

	@Override
    public String toString() {
		return "type: " + type + "\r\nenrties: " + enrties;
	}

	public static String formatDN(String orgDN) {
		if (orgDN == null) {
            return null;
        }
		orgDN = orgDN.trim();
		String[] dns = orgDN.split(",");
		StringBuffer rtnVal = new StringBuffer();
		for (int i = 0; i < dns.length; i++) {
			rtnVal.append(',');
			dns[i] = dns[i].trim();
			int index = dns[i].indexOf('=');
			if (index > -1) {
				// 去掉转为小写 huangwq 2012-7-26
				// rtnVal.append(dns[i].substring(0,
				// index).toLowerCase()).append(
				// dns[i].substring(index));
				rtnVal.append(dns[i].substring(0, index)).append(
						dns[i].substring(index));
			} else {
				rtnVal.append(dns[i]);
			}
		}
		return rtnVal.substring(1);
	}
}
