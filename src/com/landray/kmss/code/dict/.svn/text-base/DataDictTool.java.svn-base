package com.landray.kmss.code.dict;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.code.hbm.HbmBag;
import com.landray.kmss.code.hbm.HbmClass;
import com.landray.kmss.code.hbm.HbmId;
import com.landray.kmss.code.hbm.HbmList;
import com.landray.kmss.code.hbm.HbmManyToOne;
import com.landray.kmss.code.hbm.HbmMapping;
import com.landray.kmss.code.hbm.HbmOneToOne;
import com.landray.kmss.code.hbm.HbmProperty;
import com.landray.kmss.code.hbm.HbmSubclass;
import com.landray.kmss.code.hbm.NamingProperty;
import com.landray.kmss.code.spring.SpringBean;
import com.landray.kmss.code.spring.SpringBeans;
import com.landray.kmss.code.struts.ActionMapping;
import com.landray.kmss.code.struts.StrutsConfig;
import com.landray.kmss.code.util.XMLReaderUtil;
import com.landray.kmss.sys.config.dict.util.XmlJsonDictType;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class DataDictTool {
	static String LOG_FILE;
	static boolean LOG_WARN = true;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private static Map<String, String> TYPES = new HashMap<String, String>();
	static {
		TYPES.put("boolean", "Boolean");
		TYPES.put("byte", "Integer");
		TYPES.put("short", "Integer");
		TYPES.put("int", "Integer");
		TYPES.put("integer", "Integer");
		TYPES.put("long", "Long");
		TYPES.put("float", "Double");
		TYPES.put("double", "Double");
		TYPES.put("string", "String");
		TYPES.put("date", "DateTime");
		TYPES.put("clob", "RTF");
		TYPES.put("blob", "Blob");
	}
	/** 常见隐藏字段 */
	private static List<String> HIDDEN_FIELDS = Arrays
			.asList(new String[] { "fdHierarchyId", "fdLastModifiedTime",
					"extendDataXML", "extendFilePath", "authOtherReaders",
					"authOtherEditors", "authAllReaders", "authAllEditors",
					"authNotReaderFlag", "authReaderFlag", "authEditorFlag" });
	/** 其它可选隐藏字段 */
	private static List<String> MORE_HIDDEN_FIELDS = Arrays
			.asList(new String[] { "fdModelName", "fdModelId", "fdKey" });
	/** 常用message */
	private static Map<String, String> MESSAGE_KEYS = new HashMap<String, String>();
	static {
		MESSAGE_KEYS.put("docCreator",
				"sys-doc:sysDocBaseInfo.docCreator");
		MESSAGE_KEYS.put("docCreateTime",
				"sys-doc:sysDocBaseInfo.docCreateTime");
		MESSAGE_KEYS.put("docAlteror",
				"sys-doc:sysDocBaseInfo.docAlteror");
		MESSAGE_KEYS.put("docAlterTime",
				"sys-doc:sysDocBaseInfo.docAlterTime");
		MESSAGE_KEYS.put("docDept",
				"sys-doc:sysDocBaseInfo.docDept");
		MESSAGE_KEYS.put("docAuthor",
				"sys-doc:sysDocBaseInfo.docAuthor");
		MESSAGE_KEYS.put("docPublishTime",
				"sys-doc:sysDocBaseInfo.docPublishTime");
		MESSAGE_KEYS.put("docStatus",
				"sys-doc:sysDocBaseInfo.docStatus");
		MESSAGE_KEYS.put("docSubject",
				"sys-doc:sysDocBaseInfo.docSubject");
		MESSAGE_KEYS.put("docAlterClientIp",
				"sys-doc:sysDocBaseInfo.docAlterClientIP");
		MESSAGE_KEYS.put("docReadCount",
				"sys-doc:sysDocBaseInfo.docReadCount");
		MESSAGE_KEYS.put("docIsIntroduced",
				"sys-doc:sysDocBaseInfo.docIsIntroduced");
		MESSAGE_KEYS.put("docCategoryMain",
				"sys-doc:sysDocBaseInfo.docCategoryMain");
		MESSAGE_KEYS.put("docKeyword",
				"sys-doc:sysDocBaseInfo.docKeyword");
		MESSAGE_KEYS.put("docExpire",
				"sys-doc:sysDocBaseInfo.docExpire");
		MESSAGE_KEYS.put("docContent",
				"sys-doc:sysDocBaseInfo.docContent");
		MESSAGE_KEYS.put("docReaders",
				"sys-doc:sysDocBaseInfo.docReaders");
		MESSAGE_KEYS.put("docEditors",
				"sys-doc:sysDocBaseInfo.docEditors");

		MESSAGE_KEYS.put("docIsNewVersion",
				"sys-doc:sysDocBaseInfo.docIsNewVersion");
		MESSAGE_KEYS.put("docIsLocked",
				"sys-doc:sysDocBaseInfo.docIsLocked");
		MESSAGE_KEYS.put("docMainVersion",
				"sys-doc:sysDocBaseInfo.docMainVersion");
		MESSAGE_KEYS.put("docAuxiVersion",
				"sys-doc:sysDocBaseInfo.docAuxiVersion");
		MESSAGE_KEYS.put("docHistoryEditions",
				"sys-doc:sysDocBaseInfo.docHistoryEditions");
		MESSAGE_KEYS.put("docOriginDoc",
				"sys-doc:sysDocBaseInfo.docOriginDoc");

		MESSAGE_KEYS.put("authReaders",
				"sys-right:right.read.authReaders");
		MESSAGE_KEYS.put("authTmpReaders",
				"sys-right:right.read.authTmpReaders");
		MESSAGE_KEYS.put("authOtherReaders",
				"sys-right:right.read.authOtherReaders");
		MESSAGE_KEYS.put("authAllReaders",
				"sys-right:right.read.authAllReaders");
		MESSAGE_KEYS.put("authReaderFlag",
				"sys-right:right.read.authReaderFlag");

		MESSAGE_KEYS.put("authEditors",
				"sys-right:right.edit.authEditors");
		MESSAGE_KEYS.put("authTmpEditors",
				"sys-right:right.edit.authTmpEditors");
		MESSAGE_KEYS.put("authOtherEditors",
				"sys-right:right.edit.authOtherEditors");
		MESSAGE_KEYS.put("authAllEditors",
				"sys-right:right.edit.authAllEditors");
		MESSAGE_KEYS.put("authEditorFlag",
				"sys-right:right.edit.authEditorFlag");

		MESSAGE_KEYS.put("authAttCopys",
				"sys-right:right.att.authAttCopys");
		MESSAGE_KEYS.put("authAttNocopy",
				"sys-right:right.att.authAttNocopy");
		MESSAGE_KEYS.put("authAttDownloads",
				"sys-right:right.att.authAttDownloads");
		MESSAGE_KEYS.put("authAttNodownload",
				"sys-right:right.att.authAttNodownload");
		MESSAGE_KEYS.put("authAttPrints",
				"sys-right:right.att.authAttPrints");
		MESSAGE_KEYS.put("authAttNoprint",
				"sys-right:right.att.authAttNoprint");
		MESSAGE_KEYS.put("authTmpAttCopys",
				"sys-right:right.att.authAttCopys");
		MESSAGE_KEYS.put("authTmpAttNocopy",
				"sys-right:right.att.authAttNocopy");
		MESSAGE_KEYS.put("authTmpAttDownloads",
				"sys-right:right.att.authAttDownloads");
		MESSAGE_KEYS.put("authTmpAttNodownload",
				"sys-right:right.att.authAttNodownload");
		MESSAGE_KEYS.put("authTmpAttPrints",
				"sys-right:right.att.authAttPrints");
		MESSAGE_KEYS.put("authTmpAttNoprint",
				"sys-right:right.att.authAttNoprint");

		MESSAGE_KEYS.put("authArea",
				"sys-authorization:sysAuthArea.authArea");
	}

	Map<String, HbmClass> hbmClasses = new HashMap<String, HbmClass>();
	List<String> springBeans = new ArrayList<String>();
	List<String> strutsPaths = new ArrayList<String>();
	List<File> dictFiles = new ArrayList<File>();
	JSONObject docBaseAttrs;

	void prepare(String path) throws Exception {
		String _path = path == null ? "" : "/" + path;
		scan(new File("src/com/landray/kmss" + _path), false);
		scan(new File("WebContent/WEB-INF/KmssConfig" + _path), true);

		File docFile = new File(
				"WebContent/WEB-INF/KmssConfig/sys/doc/data-dict/SysDocBaseInfo.json");
		if (docFile.exists()) {
			try {
				JSONObject json = JSONObject.fromObject(
						FileUtils.readFileToString(docFile, "UTF-8"));
				docBaseAttrs = json.optJSONObject("attrs");
			} catch (IOException e) {
				return;
			}
		}
	}

	/** 文件扫描 */
	private void scan(File dir, boolean isConfig) throws Exception {
		if (!dir.exists() || dir.isFile()) {
			return;
		}
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			if (fileName.startsWith(".")) {
				continue;
			}
			if (file.isDirectory()) {
				scan(file, isConfig);
			} else {
				if (isConfig) {
					if ("spring.xml".equals(fileName)) {
						loadSpringXml(file);
					} else if ("struts.xml".equals(fileName)) {
						loadStrutsXml(file);
					} else if ("data-dict".equals(dir.getName())) {
						if (fileName.endsWith(".xml")
								|| fileName.endsWith(".json")) {
							// 数据字典
							dictFiles.add(file);
						}
					}
				} else {
					if (fileName.endsWith(".hbm.xml")) {
						loadHbmXml(file);
					}
				}
			}
		}
	}

	private void loadSpringXml(File file) throws Exception {
		SpringBeans beans = (SpringBeans) XMLReaderUtil.getInstance(file,
				SpringBeans.class);
		for (SpringBean bean : beans.getBeans()) {
			springBeans.add(bean.getId());
		}
	}

	private void loadStrutsXml(File file) throws Exception {
		StrutsConfig config = (StrutsConfig) XMLReaderUtil.getInstance(file,
				StrutsConfig.class);
		for (ActionMapping action : config.getActionMappings()) {
			strutsPaths.add(action.getPath());
		}
	}

	private void loadHbmXml(File file) throws Exception {
		HbmMapping mapping = (HbmMapping) XMLReaderUtil.getInstance(file,
				HbmMapping.class);
		for (int i = 0; i < mapping.getClasses().size(); i++) {
			HbmClass hbm = (HbmClass) mapping.getClasses().get(i);
			hbmClasses.put(hbm.getName(), hbm);
		}
	}

	/** 修复数据字典 */
	boolean fixDict(JSONObject json, File file, boolean logFix)
			throws Exception {
		// 取modelName
		String modelName = null;
		JSONObject global = json.optJSONObject("global");
		if (global != null) {
			modelName = global.optString("modelName");
		}
		if (modelName == null) {
			logDetail("错误：无法读取modelName信息：" + file.getPath());
			return false;
		}
		JSONObject baseAtts = "com.landray.kmss.sys.doc.model.SysDocBaseInfo"
				.equals(global.optString("extendClass")) ? docBaseAttrs : null;

		FixContext ctx = new FixContext(file, modelName, logFix);
		// 修复model数据
		fixMessage(ctx, global, null);
		fixModelHbmAttr(ctx, global);
		fixModelOtherAttr(ctx, global);

		// 修复字段数据
		JSONObject attrs = json.optJSONObject("attrs");
		if (attrs == null) {
			attrs = new JSONObject();
			json.put("attrs", attrs);
			attrs = json.getJSONObject("attrs");
		}
		// 数据字典的字段列表
		List<String> properties = new ArrayList<String>();
		Iterator keys = attrs.keys();
		while (keys.hasNext()) {
			properties.add(keys.next().toString());
		}
		if (baseAtts != null) {
			keys = baseAtts.keys();
			while (keys.hasNext()) {
				String key = keys.next().toString();
				if (!properties.contains(key)) {
					properties.add(key);
				}
			}
		}
		// 根据Hbm修复
		if (ctx.hbm != null) {
			fixHbmClass(ctx, attrs, baseAtts, ctx.hbm, properties, true);
			if (ctx.hbm instanceof HbmSubclass) {
				HbmSubclass hbmClass = (HbmSubclass) ctx.hbm;
				if (hbmClass.getJoin() != null) {
					fixHbmClass(ctx, attrs, baseAtts, hbmClass.getJoin(),
							properties, true);
				}
				HbmClass extClass = hbmClasses.get(hbmClass.getExtendClass());
				if (extClass != null) {
					fixHbmClass(ctx, attrs, baseAtts, extClass, properties,
							false);
				}
			}
		}
		JSONObject attachments = json.optJSONObject("attachments");
		if (attachments == null) {
			attachments = new JSONObject();
			json.put("attachments", attachments);
			attachments = json.getJSONObject("attachments");
		} else {
			// 附件修复
			for (Object key : attachments.keySet()) {
				JSONObject jsonProperty = attachments
						.getJSONObject(key.toString());
				replaceFix(ctx, jsonProperty, "propertyType",
						XmlJsonDictType.ATTACHMENT.getJsonName(),
						key.toString());
				fixMessage(ctx, jsonProperty, key.toString());
			}
		}
		for (String property : properties) {
			JSONObject jsonProperty = attrs.optJSONObject(property);
			if (jsonProperty != null) {
				String propertyType = jsonProperty.optString("propertyType");
				if (XmlJsonDictType.ATTACHMENT.getJsonName()
						.equals(propertyType)) {
					// 附件修复
					fixMessage(ctx, jsonProperty, property);
					attachments.put(property, jsonProperty);
					attrs.remove(property);
					ctx.modify = true;
					continue;
				}
				if (XmlJsonDictType.COMPLEX.getJsonName()
						.equals(propertyType)) {
					// 复合属性
					fixMessage(ctx, jsonProperty, property);
					fixPropertyType(ctx, jsonProperty, property, null);
					continue;
				}
				if (ctx.hbm == null || ctx.hbm.getId() == null) {
					// 无hibernate文件
					if (checkField(ctx.clazz, property) >= 0) {
						fixMessage(ctx, jsonProperty, property);
						fixPropertyType(ctx, jsonProperty, property, null);
						continue;
					}
				}
				ctx.modify = true;
				attrs.remove(property);
				ctx.log("删除：" + property);
			}
			if (baseAtts != null && baseAtts.containsKey(property)) {
				ctx.log("错误：" + property
						+ "属性在hbm.xml中未定义（继承SysDocBaseInfo的Model必须拥有所有的字段）");
			}
		}
		if (attachments.isEmpty()) {
			json.remove("attachments");
		}
		return ctx.modify;
	}

	/** 修复messageKey */
	private void fixMessage(FixContext ctx, JSONObject json, String fieldName)
			throws Exception {
		// 校验原有配置是否正确
		String oldKey = json.optString("messageKey");
		if (StringUtil.isNotNull(oldKey)) {
			int index = oldKey.indexOf(':');
			String bundle = "ApplicationResources";
			String key = oldKey;
			if (index > -1) {
				bundle = "com.landray.kmss."
						+ oldKey.substring(0, index).replace('-', '.').trim()
						+ ".ApplicationResources";
				key = oldKey.substring(index + 1).trim();
			}
			try {
				if (ResourceBundle.getBundle(bundle).containsKey(key)) {
					fixCanDisplay(ctx, json, fieldName, false);
					return;
				}
			} catch (MissingResourceException e) {
			}
		}
		// 若定义了语言包
		if (ctx.resource != null) {
			// 选取新的key
			if (fieldName == null) {
				String newKey = "table." + ctx.simpleName;
				if (ctx.resource.containsKey(newKey)) {
					newKey = ctx.bundle + ":" + newKey;
					json.put("messageKey", newKey);
					ctx.logFix("model.messageKey", oldKey, newKey);
					return;
				}
			} else {
				String newKey = ctx.simpleName + "." + fieldName;
				if (ctx.resource.containsKey(newKey)) {
					newKey = ctx.bundle + ":" + newKey;
				} else if (ctx.resource.containsKey(newKey + "Id")) {
					newKey = ctx.bundle + ":" + newKey + "Id";
				} else if (ctx.resource.containsKey(newKey + "Name")) {
					newKey = ctx.bundle + ":" + newKey + "Name";
				} else {
					newKey = MESSAGE_KEYS.get(fieldName);
				}
				if (newKey != null) {
					json.put("messageKey", newKey);
					ctx.logFix(fieldName + ".messageKey", oldKey, newKey);
					fixCanDisplay(ctx, json, fieldName, false);
					return;
				}
			}
		}
		fixCanDisplay(ctx, json, fieldName, true);
		// 读取不了打印错误
		if (StringUtil.isNull(oldKey)) {
			if (LOG_WARN && fieldName == null) {
				ctx.log("警告：model.messageKey未定义");
			} else if (LOG_WARN && !"fdId".equals(fieldName)
					&& !"false".equals(json.optString("canDisplay"))) {
				ctx.log("警告：" + fieldName + ".messageKey未定义");
			}
		} else {
			ctx.log("错误：" + (fieldName == null ? "model" : fieldName)
					+ ".messageKey：" + oldKey);
		}
	}

	private void fixCanDisplay(FixContext ctx, JSONObject json,
			String fieldName, boolean checkMore) throws Exception {
		String propertyType = json.optString("propertyType");
		if (fieldName == null
				|| StringUtil.isNotNull(json.optString("canDisplay"))
				|| XmlJsonDictType.ATTACHMENT.getJsonName()
						.equals(propertyType)) {
			return;
		}
		// 是否是常见的隐藏字段
		if (HIDDEN_FIELDS.contains(fieldName)) {
			defaultFix(ctx, json, "canDisplay", "false", fieldName);
			return;
		}
		if (!checkMore) {
			return;
		}
		// 是否是可选的隐藏字段
		if (MORE_HIDDEN_FIELDS.contains(fieldName)) {
			defaultFix(ctx, json, "canDisplay", "false", fieldName);
			return;
		}
		// 若在Form中没有出现，则也归属于隐藏字段
		if (ctx.clazz == null || !XmlJsonDictType.SIMPLE.getJsonName()
				.equals(propertyType)) {
			return;
		}
		String formName = StringUtil.replace(ctx.clazz.getName(),
				".model.", ".forms.") + "Form";
		Class<?> formClazz = loadClass(formName);
		if (checkField(formClazz, fieldName) == -1) {
			defaultFix(ctx, json, "canDisplay", "false", fieldName);
		}
	}

	/** 修复model的hibernate属性 */
	private void fixModelHbmAttr(FixContext ctx, JSONObject global)
			throws IOException {
		if (ctx.hbm == null) {
			return;
		}
		if (ctx.hbm instanceof HbmSubclass) {
			// 继承关系，子类没定义表，则用父类的表
			HbmSubclass subclass = (HbmSubclass) ctx.hbm;
			String table = null;
			if (subclass.getJoin() != null) {
				table = subclass.getJoin().getTable();
			} else {
				HbmClass sup = hbmClasses.get(subclass.getExtendClass());
				if (sup != null) {
					table = sup.getTable();
				}
			}
			replaceFix(ctx, global, "table", table, "model");
			defaultFix(ctx, global, "extendClass", subclass.getExtendClass(),
					"model");
			replaceFix(ctx, global, "discriminatorValue", subclass
					.getDiscriminatorValue(), "model");
		} else {
			replaceFix(ctx, global, "table", ctx.hbm.getTable(), "model");
			replaceFix(ctx, global, "discriminatorValue", null, "model");
		}
	}

	/** 修复model的其它属性 */
	private void fixModelOtherAttr(FixContext ctx, JSONObject json)
			throws Exception {
		// 修复displayProperty
		if (ctx.clazz != null) {
			String displayProperty = null;
			String[] opts = { json.optString("displayProperty", null), "fdName",
					"docSubject" };
			for (String opt : opts) {
				if (StringUtil.isNull(opt)) {
					continue;
				}
				if (checkField(ctx.clazz, opt) > 0) {
					displayProperty = opt;
					break;
				}
			}
			replaceFix(ctx, json, "displayProperty", displayProperty, "model");
		}
		// 修复serviceBean
		String bean = json.optString("serviceBean");
		if (StringUtil.isNull(bean) || !springBeans.contains(bean)) {
			bean = ctx.simpleName + "Service";
			if (!springBeans.contains(bean)) {
				bean = null;
			}
		}
		replaceFix(ctx, json, "serviceBean", bean, "model");
		// 修复URL
		String url = json.optString("url");
		if (ctx.bundle != null && StringUtil.isNull(url)) {
			url = "/" + ctx.bundle.replace('-', '/') + "/";
			for (int i = 0; i < ctx.simpleName.length(); i++) {
				char c = ctx.simpleName.charAt(i);
				if (Character.isUpperCase(c)) {
					url += "_" + Character.toLowerCase(c);
				} else {
					url += c;
				}
			}
			url += "/" + ctx.simpleName;
			if (strutsPaths.contains(url)) {
				defaultFix(ctx, json, "url",
						url + ".do?method=view&fdId=${fdId}", "model");
			}
		}
	}

	private void fixPropertyType(FixContext ctx, JSONObject json, String name,
			String type)
			throws Exception {
		String oldValue = json.optString("type");
		String newValue = getPropertyType(type);
		if (newValue == null && ctx.clazz != null) {
			PropertyDescriptor descriptor = ObjectUtil
					.getPropertyDescriptor(ctx.clazz, name);
			if (descriptor != null) {
				newValue = getPropertyType(
						descriptor.getPropertyType().getName());
			}
		}
		if (newValue == null || "DateTime".equals(newValue)
				&& ("Date".equals(oldValue) || "Time".equals(oldValue))) {
			return;
		}
		replaceFix(ctx, json, "type", newValue, name);
	}

	private String getPropertyType(String type) {
		if (StringUtil.isNull(type)) {
			return null;
		}
		if ("com.landray.kmss.common.dao.ClobStringType".equals(type)) {
			return "RTF";
		}
		if (type.startsWith("com.landray.kmss.")) {
			return type;
		}
		String shortType = type.toLowerCase();
		int i = type.lastIndexOf('.');
		if (i > -1) {
			shortType = shortType.substring(i + 1);
		}
		return TYPES.get(shortType);
	}

	private void fixHbmClass(FixContext ctx, JSONObject attrs,
			JSONObject baseAttrs, HbmClass hbmClass, List<String> properties,
			boolean addProperty) throws Exception {
		// 处理其它属性
		boolean logFix = ctx.logFix;
		List hbmProperties = hbmClass.getProperties();
		for (Object object : hbmProperties) {
			NamingProperty hbmProperty = (NamingProperty) object;
			String name = hbmProperty.getName();
			properties.remove(name);
			// 创建字段
			JSONObject jsonProperty;
			if (attrs.containsKey(name)) {
				ctx.logFix = logFix;
			} else if (addProperty) {
				jsonProperty = new JSONObject();
				attrs.put(name, jsonProperty);
				ctx.modify = true;
				ctx.logFix = false;
				if (logFix) {
					ctx.log("创建：" + name);
				}
			} else {
				continue;
			}
			// 初始化字段信息
			jsonProperty = attrs.getJSONObject(name);
			if (!"fdId".equals(name) && baseAttrs != null
					&& baseAttrs.containsKey(name)) {
				JSONObject baseProperty = baseAttrs.getJSONObject(name);
				for (Object key : baseProperty.keySet()) {
					defaultFix(ctx, jsonProperty, key.toString(),
							baseProperty.optString(key.toString()), name);
				}
			}
			// 根据Hibernate类型修复字段
			if (hbmProperty instanceof HbmId) {
				fixHbmId(ctx, jsonProperty, (HbmId) hbmProperty);
			} else if (hbmProperty instanceof HbmProperty) {
				fixHbmProperty(ctx, jsonProperty,
						(HbmProperty) hbmProperty);
			} else if (hbmProperty instanceof HbmOneToOne) {
				fixHbmOneToOne(ctx, jsonProperty,
						(HbmOneToOne) hbmProperty);
			} else if (hbmProperty instanceof HbmManyToOne) {
				fixHbmManyToOne(ctx, jsonProperty,
						(HbmManyToOne) hbmProperty);
			} else if (hbmProperty instanceof HbmBag) {
				fixHbmBag(ctx, jsonProperty, (HbmBag) hbmProperty);
			}
		}
		ctx.logFix = logFix;
	}

	private void fixHbmId(FixContext ctx, JSONObject json, HbmId property)
			throws IOException {
		String assigned = property.getGenerator() == null ? "assigned"
				: property.getGenerator().getType();
		String value = "{\"propertyType\":\"id\",\"generator\":{\"type\":\""
				+ assigned + "\"}}";
		String oldValue = json.toString();
		if (value.equals(oldValue)) {
			return;
		}
		json.clear();
		json.putAll(JSONObject.fromObject(value));
		ctx.logFix("fdId", oldValue, value);
	}

	private void fixHbmProperty(FixContext ctx, JSONObject jsonProperty,
			HbmProperty property) throws Exception {
		replaceFix(ctx, jsonProperty, "propertyType",
				XmlJsonDictType.SIMPLE.getJsonName(),
				property.getName());
		fixMessage(ctx, jsonProperty, property.getName());
		fixPropertyType(ctx, jsonProperty, property.getName(),
				property.getType());
		replaceFix(ctx, jsonProperty, "column", property.getColumn(),
				property.getName());
		replaceFix(ctx, jsonProperty, "length", property.getLength(),
				property.getName());
		if ("true".equals(property.getNotNull())) {
			replaceFix(ctx, jsonProperty, "notNull", "true",
					property.getName());
		}
		if ("true".equals(property.getUnique())) {
			replaceFix(ctx, jsonProperty, "unique", "true",
					property.getName());
		}
	}

	private void fixHbmOneToOne(FixContext ctx, JSONObject jsonProperty,
			HbmOneToOne property) throws Exception {
		replaceFix(ctx, jsonProperty, "propertyType",
				XmlJsonDictType.MODEL.getJsonName(),
				property.getName());
		fixMessage(ctx, jsonProperty, property.getName());
		fixPropertyType(ctx, jsonProperty, property.getName(),
				property.getType());
		replaceFix(ctx, jsonProperty, "column", "fd_id", property.getName());
		replaceFix(ctx, jsonProperty, "notNull", "true", property.getName());
		replaceFix(ctx, jsonProperty, "unique", "true", property.getName());
		replaceFix(ctx, jsonProperty, "cascade", property.getCascade(),
				property.getName());
		replaceFix(ctx, jsonProperty, "constrained", property.getConstrained(),
				property.getName());
	}

	private void fixHbmManyToOne(FixContext ctx, JSONObject jsonProperty,
			HbmManyToOne property) throws Exception {
		replaceFix(ctx, jsonProperty, "propertyType",
				XmlJsonDictType.MODEL.getJsonName(),
				property.getName());
		fixMessage(ctx, jsonProperty, property.getName());
		fixPropertyType(ctx, jsonProperty, property.getName(),
				property.getType());
		replaceFix(ctx, jsonProperty, "column", property.getColumn(),
				property.getName());
		if ("true".equals(property.getNotNull())) {
			replaceFix(ctx, jsonProperty, "notNull", "true",
					property.getName());
		}
		if ("true".equals(property.getUnique())) {
			replaceFix(ctx, jsonProperty, "unique", "true",
					property.getName());
		}
		replaceFix(ctx, jsonProperty, "cascade", property.getCascade(),
				property.getName());
	}

	private void fixHbmBag(FixContext ctx, JSONObject jsonProperty,
			HbmBag property) throws Exception {
		String table = property.getTable();
		replaceFix(ctx, jsonProperty, "propertyType",
				XmlJsonDictType.LIST.getJsonName(),
				property.getName());
		fixMessage(ctx, jsonProperty, property.getName());
		if (property.getOneToMany() != null) {
			fixPropertyType(ctx, jsonProperty, property.getName(),
					property.getOneToMany().getType());
			replaceFix(ctx, jsonProperty, "column",
					property.getKey().getColumn(),
					property.getName());
			if (StringUtil.isNull(table)) {
				HbmClass clz = hbmClasses
						.get(property.getOneToMany().getType());
				if (clz != null) {
					table = clz.getTable();
				}
			}
		} else {
			if(null != property.getManyToMany()) {
				fixPropertyType(ctx, jsonProperty, property.getName(),
						property.getManyToMany().getType());
			}
			
			replaceFix(ctx, jsonProperty, "column",
					property.getKey().getColumn(),
					property.getName());
			
			if(null != property.getManyToMany()) {
				replaceFix(ctx, jsonProperty, "elementColumn",
						property.getManyToMany().getColumn(),
						property.getName());
			}
			
			if (property instanceof HbmList && null != ((HbmList) property).getIndex()) {
				replaceFix(ctx, jsonProperty, "indexColumn",
						((HbmList) property).getIndex().getColumn(),
						property.getName());
			}
		}
		if ("true".equals(property.getKey().getNotNull())) {
			replaceFix(ctx, jsonProperty, "notNull", "true",
					property.getName());
		}
		if ("true".equals(property.getKey().getUnique())) {
			replaceFix(ctx, jsonProperty, "unique", "true",
					property.getName());
		}
		replaceFix(ctx, jsonProperty, "orderBy", property.getOrderBy(),
				property.getName());
		replaceFix(ctx, jsonProperty, "table", table, property.getName());
		replaceFix(ctx, jsonProperty, "cascade", property.getCascade(),
				property.getName());
		replaceFix(ctx, jsonProperty, "inverse", property.getInverse(),
				property.getName());
	}

	/** 直接替换 */
	private void replaceFix(FixContext ctx, JSONObject json, String key,
			String value, String object) throws IOException {
		String oldValue = json.optString(key, null);
		if (StringUtil.isNull(oldValue)) {
			oldValue = null;
		}
		if (ObjectUtil.equals(oldValue, value)) {
			return;
		}
		json.put(key, value);
		ctx.logFix(object + "." + key, oldValue, value);
	}

	/** 没定义，则使用默认值 */
	private void defaultFix(FixContext ctx, JSONObject json, String key,
			String value,
			String object) throws IOException {
		String oldValue = json.optString(key, null);
		if (StringUtil.isNull(oldValue) && StringUtil.isNotNull(value)) {
			json.put(key, value);
			ctx.logFix(object + "." + key, oldValue, value);
		}
	}

	private class FixContext {
		File file;
		String simpleName;
		String bundle;
		ResourceBundle resource;
		Class<?> clazz;
		HbmClass hbm;
		boolean logFix;
		boolean modify = false;

		FixContext(File file, String modelName, boolean logFix)
				throws Exception {
			this.file = file;
			this.logFix = logFix;
			int index = modelName.lastIndexOf('.');
			this.simpleName = modelName.substring(index + 1, index + 2)
					.toLowerCase()
					+ modelName.substring(index + 2);

			// 找ResouceBundle
			String path = "src/" + modelName.replace('.', '/');
			for (index = path.lastIndexOf('/'); index > -1; index = path
					.lastIndexOf('/', index - 1)) {
				String bundle = path.substring(0, index);
				if (bundle.length() <= "src/com/landray/kmss".length()) {
					break;
				}
				if (new File(bundle + "/ApplicationResources.properties")
						.exists()) {
					this.bundle = bundle
							.substring("src/com/landray/kmss".length() + 1)
							.replace('/', '-');
					this.resource = ResourceBundle.getBundle("com.landray.kmss."
							+ this.bundle.replaceAll("-", ".")
							+ ".ApplicationResources");
				}
			}

			// 读取Class信息
			this.clazz = loadClass(modelName);
			if (this.clazz == null) {
				log("错误：无法读取类信息：" + modelName);
			}
			// hbm文件
			this.hbm = hbmClasses.get(modelName);
			if (this.hbm == null) {
				log("错误：无法读取Hibernate文件：" + modelName);
			}
		}

		boolean firstLog = true;

		void log(String message) throws IOException {
			if (firstLog) {
				logFile("\r\n===== " + file.getPath() + " =====");
				firstLog = false;
			}
			logDetail(message);
		}

		void logFix(String object, String oldValue, String newValue)
				throws IOException {
			modify = true;
			if (!logFix) {
				return;
			}
			String message = "修订：" + object + "：" + oldValue + " → " + newValue;
			log(message);
		}
	}

	private Class<?> loadClass(String name) {
		try {
			return DataDictTool.class.getClassLoader().loadClass(name);
		} catch (Throwable e) {
			return null;
		}
	}

	/** 0:不知道，1：存在，-1不存在 */
	private int checkField(Class<?> clazz, String field) {
		if (clazz == null) {
			return 0;
		}
		String _field = Character.toUpperCase(field.charAt(0))
				+ field.substring(1);
		try {
			clazz.getMethod("get" + _field);
			return 1;
		} catch (Throwable e) {
		}
		try {
			clazz.getMethod("is" + _field);
			return 1;
		} catch (Throwable e) {
		}
		return -1;
	}

	/** 保存数据 */
	void saveDict(File file, JSONObject json) throws Exception {
		FileUtils.writeStringToFile(file, json.toString(4), "UTF-8");
	}

	private FileOutputStream logOut;

	/** 打印日志 */
	void logDetail(String message) throws IOException {
		logFile(message);
		logger.info(message);
	}

	private void logFile(String message) throws IOException {
		if (logOut == null) {
			logOut = FileUtils.openOutputStream(new File(LOG_FILE));
		}
		IOUtils.write(message + "\r\n", logOut, "UTF-8");
	}

	/** 完成 */
	void finish() {
		if (logOut == null) {
			logger.info("操作已完成，未发现错误！");
		} else {
			IOUtils.closeQuietly(logOut);
			logger.info("操作已完成，关键信息已经写入：" + LOG_FILE);
		}
	}
}
