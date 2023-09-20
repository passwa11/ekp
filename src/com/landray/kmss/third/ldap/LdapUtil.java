package com.landray.kmss.third.ldap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.third.ldap.form.LdapSettingForm;
import com.landray.kmss.util.DESEncrypt;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.StringUtil;



public class LdapUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapUtil.class);
	
	public static Map<String,String> getDefaultConfig() throws IOException, Exception{
		Map<String, String> map = new HashMap<String, String>();
		Properties dps = new Properties();
		dps.load(FileUtil.getInputStream(LdapConstant.ROOT_PATH
				+ LdapConstant.DEFAULT_FILE_NAME));
		for (Enumeration keys = dps.keys(); keys.hasMoreElements();) {
			String keyName = (String) keys.nextElement();
			map.put(keyName, dps.getProperty(keyName));
		}
		return map;
	}
	
	public static Map<String,String> getPropertyFileConfig() throws IOException, Exception{
		if (!new File(LdapConstant.ROOT_PATH + LdapConstant.FILE_NAME).exists()) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		Properties ps = new Properties();
		InputStream is = FileUtil.getInputStream(LdapConstant.ROOT_PATH
				+ LdapConstant.FILE_NAME);

		try {
			is = doPropertiesDecrypt(is);
		} catch (Exception ex) {
			if (is != null) {
				is.close();
			}
			is = FileUtil.getInputStream(LdapConstant.ROOT_PATH
					+ LdapConstant.FILE_NAME);
			logger.info("解密出错，按原始信息输出。");
		}
		ps.load(is);
		for (Enumeration keys = ps.keys(); keys.hasMoreElements();) {
			String keyName = (String) keys.nextElement();
			map.put(keyName, ps.getProperty(keyName));
		}
		if (is != null) {
			is.close();
		}

		return map;
	}

	public static Map<String, String> loadLdapConfig() throws Exception {
		
		LdapDetailConfig config = new LdapDetailConfig();
		Map map = config.getDataMap();
		if(map==null || map.isEmpty()){
			map = getDefaultConfig();
		}
		return map;
	}

	public static InputStream doPropertiesDecrypt(InputStream in)
			throws Exception {
		DESEncrypt des = new DESEncrypt("kmssPropertiesKey");
		return des.decrypt(in);
	}

	public static String getPropertyValue(String name) throws Exception {
		Map<String, String> map = loadLdapConfig();
		return map.get(name);
	}

	public static String getDefaultValues() throws Exception {
		StringBuffer sb = new StringBuffer();
		Properties dps = new Properties();
		dps.load(FileUtil.getInputStream(LdapConstant.ROOT_PATH
				+ LdapConstant.DEFAULT_FILE_NAME));
		sb.append("var _default={\n");
		int i = 0;
		for (Enumeration keys = dps.keys(); keys.hasMoreElements();) {
			String keyName = (String) keys.nextElement();
			if (StringUtil.isNotNull(dps.getProperty(keyName))) {
				if (i > 0) {
					sb.append(",\n");
				}
				sb.append("'value(" + keyName + ")':'"
						+ dps.getProperty(keyName) + "'");
				i++;
			}
		}
		sb.append("};\n");
		return sb.toString();
	}

	public static Map<String, String> saveLdapConfig(Map<String, String> map)
			throws Exception {
		filter(map);
		Map<String, String> tree = new TreeMap<String, String>();
		if (!map.isEmpty()) {
			for (String key : map.keySet()) {
				if (StringUtil.isNotNull(map.get(key))) {
					tree.put(key, map.get(key));
				}
			}
		}
		tree = sort(tree);
		LdapDetailConfig config = new LdapDetailConfig();
		Map dataMap = config.getDataMap();
		dataMap.clear();
		dataMap.putAll(tree);
		
		if (dataMap != null && dataMap.containsKey("kmss.ldap.config.password")) {
			String value = (String) dataMap.get("kmss.ldap.config.password");
			dataMap
					.put("kmss.ldap.config.password", LdapUtil
							.desEncrypt(value));
		}
		
		config.save();
		
		PropertiesConfiguration p = new PropertiesConfiguration();
		if (!tree.isEmpty()) {
			for (String key : tree.keySet()) {
				p.setProperty(key, tree.get(key));
				// if ("kmss.ldap.config.password".equals(key)) {
				// p.setProperty(key, LdapUtil.desDecrypt(tree.get(key)));
				// }
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		p.save(baos);
		DESEncrypt des = new DESEncrypt("kmssPropertiesKey");
		byte[] bytes = des.encrypt(baos.toByteArray());

		FileOutputStream outputStream = new FileOutputStream(
				LdapConstant.ROOT_PATH + LdapConstant.FILE_NAME);

		outputStream.write(bytes);
		outputStream.close();
		baos.close();
		
		return dataMap;

	}

	private static void filter(Map<String, String> map) {
		if (StringUtil.isNotNull(map.get("kmss.ldap.config.dept.check"))) {
			if (""
					.equals(map
							.get("kmss.ldap.type.dept.prop.parent.byParentDN"))) {
				map.put("kmss.ldap.type.dept.prop.parent", "");
				map.put("kmss.ldap.type.dept.prop.parent.objKey", "");
				map.put("kmss.ldap.type.dept.prop.parent.type", "");
			}
			if ("true".equals(map
					.get("kmss.ldap.type.dept.prop.parent.byParentDN"))) {
				map.put("kmss.ldap.type.dept.prop.parent", "dn");
			}
		}
		if (StringUtil
				.isNotNull(map.get("kmss.ldap.type.dept.prop.thisleader"))) {
			map.put("kmss.ldap.type.dept.prop.thisleader.type", "person;post");
		}
		if (StringUtil.isNotNull(map
				.get("kmss.ldap.type.dept.prop.superleader"))) {
			map.put("kmss.ldap.type.dept.prop.superleader.type", "person;post");
		}

		if (StringUtil.isNotNull(map.get("kmss.ldap.config.person.check"))) {
			if (""
					.equals(map
							.get("kmss.ldap.type.person.prop.dept.byParentDN"))) {
				map.put("kmss.ldap.type.person.prop.dept", "");
				map.put("kmss.ldap.type.person.prop.dept.objKey", "");
				map.put("kmss.ldap.type.person.prop.dept.type", "");
			}
			if ("true".equals(map
					.get("kmss.ldap.type.person.prop.dept.byParentDN"))) {
				map.put("kmss.ldap.type.person.prop.dept", "dn");
			}
		}
		if (StringUtil.isNotNull(map.get("kmss.ldap.type.person.prop.post"))) {
			map.put("kmss.ldap.type.person.prop.post.type", "post");
		}

		if (StringUtil.isNotNull(map.get("kmss.ldap.config.post.check"))) {
			if ("".equals(map.get("kmss.ldap.type.post.prop.dept.byParentDN"))) {
				map.put("kmss.ldap.type.post.prop.dept", "");
				map.put("kmss.ldap.type.post.prop.dept.objKey", "");
				map.put("kmss.ldap.type.post.prop.dept.type", "");
			}
			if ("true".equals(map
					.get("kmss.ldap.type.post.prop.dept.byParentDN"))) {
				map.put("kmss.ldap.type.post.prop.dept", "dn");
			}
		}
		if (StringUtil
				.isNotNull(map.get("kmss.ldap.type.post.prop.thisleader"))) {
			map.put("kmss.ldap.type.post.prop.thisleader.type", "person;post");
		}
		if (StringUtil.isNotNull(map.get("kmss.ldap.type.post.prop.member"))) {
			map.put("kmss.ldap.type.post.prop.member.type", "person");
		}

		if (StringUtil.isNotNull(map.get("kmss.ldap.type.group.prop.member"))) {
			map.put("kmss.ldap.type.group.prop.member.type", "person");
		}

	}

	private static SortedMap<String, String> sort(Map<String, String> map) {
		TreeMap<String, String> tree = new TreeMap<String, String>();

		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys);

		for (int i = 0; i < keys.length; i++) {
			tree.put(keys[i].toString(), map.get(keys[i]));
		}
		return tree.tailMap(tree.firstKey());
	}

	public static void main(String[] args) throws Exception {
		// saveLdapConfig(loadLdapConfig());
		// System.out.println(desEncrypt("黄伟强123456"));
		// String s = "v2Ld79RgT8Wz/fOBnM/CGQ==";
		// System.out.println(desDecrypt(s));

	}

	public static Properties getLdapConfig() throws Exception {
		Properties config = new Properties();
		InputStream is = FileUtil.getInputStream(LdapConstant.ROOT_PATH
				+ LdapConstant.FILE_NAME);

		try {
			is = LdapUtil.doPropertiesDecrypt(is);
			config.load(is);
		} catch (Exception ex) {
			if (is != null) {
				is.close();
			}
			is = FileUtil.getInputStream(LdapConstant.ROOT_PATH
					+ LdapConstant.FILE_NAME);
			config.load(is);
			logger.info("解密出错，按原始信息输出。");
		} finally {
			if (is != null) {
				is.close();
			}
		}

		return config;

	}

	
	private static final String desKey = "kmssSysConfigKey";

	private static DESEncrypt des = null;

	public static DESEncrypt getDesEncrypt() throws Exception {
		if (des == null) {
			des = new DESEncrypt(desKey);
		}
		return des;
	}

	public static String desDecrypt(String pass) {
		if (StringUtil.isNull(pass)) {
			return pass;
		}
		try {
			return getDesEncrypt().decryptString(pass);
		} catch (IllegalBlockSizeException e) {
			return pass;
		} catch (BadPaddingException e) {
			return pass;
		} catch (Exception e) {
			logger.error("", e);
		}
		return pass;
	}

	public static String desEncrypt(String pass) {
		if (StringUtil.isNull(pass)) {
			return pass;
		}
		try {
			return getDesEncrypt().encryptString(pass);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.error("", e);
		}
		return pass;
	}

	public static List<LdapCustomProp>
			getLdapCustomProps(LdapSettingForm form) {
		List<LdapCustomProp> ldapCustomProps = new ArrayList<LdapCustomProp>();
		Map<String, String> propMap = DynamicAttributeUtil
				.getCustomPropMap(SysOrgPerson.class.getName(), true);
		if (propMap == null) {
			return ldapCustomProps;
		}
		Map<String, String> propTypeMap = DynamicAttributeUtil
				.getCustomPropTypeMap(SysOrgPerson.class.getName(), true);
		for (String fieldName : propMap.keySet()) {
			LdapCustomProp prop = new LdapCustomProp();
			prop.setFieldName(fieldName);
			prop.setFieldDisplayName(propMap.get(fieldName));
			prop.setFieldType(propTypeMap.get(fieldName));
			String key = "value(kmss.ldap.type.person.prop." + fieldName + ")";
			prop.setFieldKey(key);
			if (form != null) {
				String value = (String) form
						.getValue("kmss.ldap.type.person.prop." + fieldName);
				if (value != null) {
					prop.setFieldValue(value);
				} else {
					prop.setFieldValue("");
				}
			}
			ldapCustomProps.add(prop);
		}

		return ldapCustomProps;
	}

}
