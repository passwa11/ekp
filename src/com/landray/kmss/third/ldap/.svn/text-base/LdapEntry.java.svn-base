package com.landray.kmss.third.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.third.ldap.base.BaseLdapContext;
import com.landray.kmss.third.ldap.base.BaseLdapEntry;

/**
 * Ldap的单条记录
 * 
 * @author 叶中奇
 * 
 */
@SuppressWarnings("unchecked")
public class LdapEntry extends BaseLdapEntry {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapEntry.class);

	private Attributes attributes = null;


	/**
	 * 构造函数
	 * 
	 * @param attributes
	 * @param context
	 * @param type
	 * @param dn
	 */
	public LdapEntry(BaseLdapContext context, Attributes attributes,
			String type,
			String dn) {
		super(context, type, dn);
		this.attributes = attributes;
	}


	/**
	 * 获取对象属性
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 */
	@Override
    public Object getObjectProperty(String name) throws Exception {
		if ("dn".equals(name)) {
            return dn;
        }
		if (!isPropertyMap(name)) {
            return null;
        }
		String attName = context.getLdapAttribute(getType(), name);
		if ("dn".equals(attName)) {
			return dn;
		}
		Attribute attribute = attributes.get(attName);
		if (attribute == null) {
            return null;
        }
		return attribute.get();
	}


	@Override
    public List getImportInfoValues(String name) throws Exception {
		List returnValues = new ArrayList();
		if (!isPropertyMap(name)) {
            return returnValues;
        }
		Attribute attribute = attributes.get(context.getLdapAttribute(
				getType(), name));
		if (attribute == null) {
            return returnValues;
        }
		NamingEnumeration values = attribute.getAll();
		while (values.hasMore()) {
			returnValues.add(values.next().toString());
		}
		return returnValues;
	}



//	public String searchImportInfo2(String searchType, String searchKey,
//			String searchValue) throws Exception {
//		String attr = context.getTypeConfig(searchType, "prop.unid");
//		String val = null;
//		NamingEnumeration enumeration = null;
//		if (searchKey.equalsIgnoreCase("dn")) {
//			enumeration = context
//					.searchByDN(searchValue, new String[] { attr });
//		} else {
//			enumeration = context.searchByKey(searchType,
//					context.getLdapAttribute(searchType, searchKey),
//					new String[] { attr }, searchValue);
//		}
//		if (enumeration != null && enumeration.hasMore()) {
//			SearchResult result = (SearchResult) enumeration.next();
//			if ("objectGUID".equalsIgnoreCase(attr)) {
//				val = StringUtil.toHexString((byte[]) result.getAttributes()
//						.get(attr).get());
//			} else {
//				val = result.getAttributes().get(attr).get().toString();
//			}
//		}
//		return val;
//	}


}
