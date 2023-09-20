package com.landray.kmss.third.ldap.apache;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Value;

import com.landray.kmss.third.ldap.base.BaseLdapContext;
import com.landray.kmss.third.ldap.base.BaseLdapEntry;


@SuppressWarnings("unchecked")
public class ApacheLdapEntry extends BaseLdapEntry {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ApacheLdapEntry.class);

	private List<Attribute> attributes = null;

	/**
	 * 构造函数
	 * 
	 * @param attributes
	 * @param context
	 * @param type
	 * @param dn
	 */
	public ApacheLdapEntry(BaseLdapContext context, List<Attribute> attributes,
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
		Attribute attribute = getAttribute(attName);
		if (attribute == null) {
			return null;
		}
		Value<?> value = attribute.get();
		if ("objectGUID".equalsIgnoreCase(attName)) {
			return value.getBytes();
		} else {
			return value.getString();
		}
	}

	private Attribute getAttribute(String attName) {
		if (attributes == null) {
			return null;
		}
		for (Attribute attribute : attributes) {
			if (attName.equalsIgnoreCase(attribute.getId())) {
				return attribute;
			}
		}
		return null;
	}


	@Override
    public List getImportInfoValues(String name) throws Exception {
		List returnValues = new ArrayList();
		if (!isPropertyMap(name)) {
			return returnValues;
		}
		Attribute attribute = getAttribute(context.getLdapAttribute(
				getType(), name));
		if (attribute == null) {
			return returnValues;
		}
		for (Value<?> value : attribute) {
			returnValues.add(value.toString());
		}
		return returnValues;
	}


	@Override
    public String toString() {
		return "type: " + type + ", dn: " + dn + "\r\nattributes: "
				+ attributes;
	}

}
