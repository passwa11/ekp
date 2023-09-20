package com.landray.kmss.spi.hibernate;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.HibernateException;
import org.hibernate.boot.MappingNotFoundException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.hibernate.IDynamicMetadataBuilder;
import com.landray.kmss.sys.hibernate.spi.XMLHelper;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.LoggerUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class DynamicMetadataBuilderImpl implements IDynamicMetadataBuilder {

	private static final String indexPrefix = "INDX";

	private static final String uniqueKeyPrefix = "UNQK";

	public DynamicMetadataBuilderImpl() {
		super();
		LoggerUtil.setLoggerDebug(this.getClass());
	}

	private final String charset = "utf-8";
	private static final Logger log = LoggerFactory.getLogger(DynamicMetadataBuilderImpl.class);
	private String enableString = ResourceUtil.getKmssConfigString("kmss.add.custom.elements.out.enable");
	private Document document = null;
	private boolean addCustomElements = false;

	private String formatXml() throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.write(document);
		xmlWriter.close();
		return writer.toString();
	}

	/**
	 * 增加属性
	 *
	 * @param parent
	 * @param prop
	 * @throws Exception
	 */
	private void addPropElement(Element parent, SysDictCommonProperty prop) throws Exception {
		Map<String, String> langs = SysLangUtil.getSupportedLangs();
		for (String lang : langs.keySet()) {
			Element propElement = parent.addElement("property");

			propElement.addAttribute("name", prop.getName() + lang);
			String column = prop.getColumn();
			if (column.length() > 27) {
				column = column.substring(0, 27);
			}
			propElement.addAttribute("column", column + "_" + lang);
			propElement.addAttribute("update", "true");
			propElement.addAttribute("insert", "true");
			propElement.addAttribute("not-null", "false");
			int length = 1000;
			if (prop instanceof SysDictSimpleProperty) {
				length = ((SysDictSimpleProperty) prop).getLength();
			}
			propElement.addAttribute("length", length + "");
			propElement.addAttribute("type", "java.lang.String");
			propElement.addAttribute("access", "com.landray.kmss.sys.metadata.service.spring.DynamicPropAccessor");
		}
	}

	/**
	 * 根据配置动态增加字段
	 *
	 * @param parent
	 * @throws Exception
	 */
	private void addPropElement(Element parent, DynamicAttributeField field) throws Exception {
		Element joinElement = parent.element("join");
		Element oriParent = null;
		if (joinElement != null) {
			oriParent = parent;
			parent = joinElement;
		}
		String fieldType = field.getFieldType();
		if (StringUtils.startsWith(fieldType, "com.landray.kmss")) {
			// 类型是landray产品里面的model
			//		if ("com.landray.kmss.sys.organization.model.SysOrgElement".equals(fieldType)) {
			String isMulti = field.getIsMulti();
			if ("true".equals(isMulti)) {
				Element propElement;
				if (oriParent != null) {
					Element bag = DocumentHelper.createElement("bag");
					oriParent.elements().add(0, bag);
					propElement = bag;
				} else {
					propElement = parent.addElement("bag");
				}
				propElement.addAttribute("access", "com.landray.kmss.sys.property.custom.accessor.DynamicAttrAccessor");
				propElement.addAttribute("name", field.getFieldName());
				propElement.addAttribute("table", field.getTableName());
				propElement.addAttribute("lazy", "true");

				Element keyEle = propElement.addElement("key");
				keyEle.addAttribute("column", "fd_source_id");

				Element manyEle = propElement.addElement("many-to-many");
				manyEle.addAttribute("class", fieldType);
				manyEle.addAttribute("column", "fd_target_id");
			} else {
				Element propElement = parent.addElement("many-to-one");
				propElement.addAttribute("access", "com.landray.kmss.sys.property.custom.accessor.DynamicAttrAccessor");
				propElement.addAttribute("name", field.getFieldName());
				propElement.addAttribute("class", field.getFieldType());
				propElement.addAttribute("column", field.getColumnName());
				propElement.addAttribute("update", "true");
				propElement.addAttribute("insert", "true");
			}
		} else {
			Element propElement = parent.addElement("property");
			propElement.addAttribute("name", field.getFieldName());
			propElement.addAttribute("column", field.getColumnName());
			propElement.addAttribute("update", "true");
			propElement.addAttribute("insert", "true");
			propElement.addAttribute("not-null", "false");
			if (field.hasLength()) {
				propElement.addAttribute("length", field.getFieldLength());
			}
			propElement.addAttribute("type", field.getFieldType());
			if (field.hasScale()) {
				// 浮点精度
				propElement.addAttribute("scale", field.getScale());
			}
			propElement.addAttribute("access", "com.landray.kmss.sys.property.custom.accessor.DynamicAttrAccessor");
		}

	}

	/**
	 * 多语言
	 *
	 * @param doc
	 * @throws Exception
	 */
	private void addLangElements(org.dom4j.Document doc) throws Exception {
		if (!SysLangUtil.isLangEnabled()) {
			return;
		}
		Element classElement = doc.getRootElement().element("class");
		if (classElement != null) {
			String name = classElement.attributeValue("name");
			if (StringUtil.isNull(name)) {
				return;
			}
			if ("com.landray.kmss.sys.organization.model.SysOrgElementBak".equals(name)) {
				name = "com.landray.kmss.sys.organization.model.SysOrgElement";
			}
			SysDictModel dict = SysDataDict.getInstance().getModel(name);
			if (dict == null || !dict.isLangSupport()) {
				return;
			}
			List<SysDictCommonProperty> list = dict.getLanguageSuportList();
			if (list != null) {
				for (SysDictCommonProperty prop : list) {
					addPropElement(classElement, prop);
				}
			}
		}
	}

	/**
	 * 动态增加自定义的属性
	 *
	 * @param doc
	 * @throws Exception
	 */
	private void addCustomElements(org.dom4j.Document doc) throws Exception {
		String name = null;
		Element classElement = doc.getRootElement().element("class");
		if (classElement != null) {
			name = classElement.attributeValue("name");
		} else {
			classElement = doc.getRootElement().element("subclass");
			if (classElement != null) {
				name = classElement.attributeValue("name");
			}
		}

		if (StringUtil.isNull(name)) {
			return;
		}

		if ("com.landray.kmss.sys.organization.model.SysOrgPersonBak".equals(name)) {
			name = "com.landray.kmss.sys.organization.model.SysOrgPerson";
		}
		
		if(null != name) {
			DynamicAttributeConfig config = DynamicAttributeUtil.getDynamicAttributeConfig(name);
			if (config != null) {
				addCustomElements = true;
				StringBuffer buf = new StringBuffer();
				for (DynamicAttributeField field : config.getFields()) {
					if (!"true".equals(field.getStatus())) {
						continue; // 不加载失效的属性
					}
					addPropElement(classElement, field);
					buf.append(",").append(field.getFieldName()).append("(").append(field.getColumnName()).append(")");
				}
				if (buf.length() > 0) {
					buf.deleteCharAt(0);
					log.debug("找到自定义字段：model=[" + name + "], fields=[" + buf.toString() + "]，Hibernate将根据实际需求创建相应的字段。");
				}
			}
		}
		
	}

	/**
	 * 处理索引名称长度问题
	 *
	 * @param doc
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void additionIndex(org.dom4j.Document doc) throws Exception {
		Iterator propIt = null;
		Element classElement = doc.getRootElement().element("class");
		Map<String, List<String>> uniqueKeyMap = new HashMap<String, List<String>>();
		if (classElement == null) {
			classElement = doc.getRootElement().element("subclass");
			if (classElement != null) {
				String classFullName = null;
				String classSampleName = null;
				if (null != classElement.attribute("name")) {
					classFullName = classElement.attribute("name").getStringValue();
					classSampleName = classFullName.substring(classFullName.lastIndexOf(".") + 1,
							classFullName.length());
				} else {
					classFullName = classElement.attribute("table").getStringValue();
					classSampleName = classFullName;
				}

				propIt = classElement.elementIterator("property");
				if (null != propIt) {
					additionPropIndex(propIt, classSampleName, uniqueKeyMap);
				}

				Iterator propItm = classElement.elementIterator("many-to-one");
				if (null != propItm) {
					additionPropIndex(propItm, classSampleName, uniqueKeyMap);
				}

				classElement = classElement.element("join");
				if (null != classElement) {
					propIt = classElement.elementIterator("property");
					additionPropIndex(propIt, classSampleName, uniqueKeyMap);

					Iterator joinPropItm = classElement.elementIterator("many-to-one");
					if (null != joinPropItm) {
						additionPropIndex(joinPropItm, classSampleName, uniqueKeyMap);
					}
				}
			}
			
		} else {
			String classFullName = null;
			String classSampleName = null;
			if (null != classElement.attribute("name")) {
				classFullName = classElement.attribute("name").getStringValue();
				classSampleName = classFullName.substring(classFullName.lastIndexOf(".") + 1, classFullName.length());
			} else {
				classFullName = classElement.attribute("table").getStringValue();
				classSampleName = classFullName;
			}

			propIt = classElement.elementIterator("property");
			if (null != propIt) {
				additionPropIndex(propIt, classSampleName, uniqueKeyMap);
			}

			Iterator propItm = classElement.elementIterator("many-to-one");
			if (null != propItm) {
				additionPropIndex(propItm, classSampleName, uniqueKeyMap);
			}
		}
		
		uniqueKeyMap.clear();
		uniqueKeyMap = null;
	}

	@SuppressWarnings("rawtypes")
	private void additionPropIndex(Iterator propIt, String classSampleName, Map<String, List<String>> uniqueKeyMap) throws Exception {

		//复合索引,*hbm.xml同一个文件同名索引
		Map<String, List<String>> indexNameMap = new HashMap<String, List<String>>();

		List<Element> listElement = new ArrayList<Element>();
		while (propIt.hasNext()) {
			Element propElem = (Element) propIt.next();
			listElement.add(propElem);

			String indexName = propElem.attributeValue("index");
			String uniqueKey = propElem.attributeValue("unique-key");

			//普通多字段索引
			if (StringUtil.isNotNull(indexName)) {
				String nameProperty = propElem.attributeValue("name");
				if (!indexNameMap.containsKey(indexName)) {
					List<String> propertyList = new ArrayList<String>();
					propertyList.add(nameProperty);
					indexNameMap.put(indexName, propertyList);
				} else {
					List<String> propertyList = indexNameMap.get(indexName);
					propertyList.add(nameProperty);
				}

			}

			//多字段唯一索引
			if (StringUtil.isNotNull(uniqueKey)) {
				String nameProperty = propElem.attributeValue("name");
				if (!uniqueKeyMap.containsKey(uniqueKey)) {
					List<String> propertyList = new ArrayList<String>();
					propertyList.add(nameProperty);
					uniqueKeyMap.put(uniqueKey, propertyList);
				} else {
					List<String> propertyList = uniqueKeyMap.get(uniqueKey);
					propertyList.add(nameProperty);
				}
			}

		}

		for (Element element : listElement) {
			Element propElem = (Element) element;
			String indexName = propElem.attributeValue("index");
			String uniqueKey = propElem.attributeValue("unique-key");

			if ("many-to-one".equals(propElem.getName())) {
				String foreignKey = propElem.attributeValue("foreign-key");
				if (StringUtil.isNull(indexName)) {
					if (StringUtil.isNull(foreignKey)
							|| (StringUtil.isNotNull(foreignKey) && !"none".equals(foreignKey))) {
						//复合多列索引
						List<String> propertyList = indexNameMap.get(indexName);
						if (null != propertyList && 1 < propertyList.size()) {
							indexName = indexPrefix + hashedName(indexName);
							propElem.addAttribute("index", indexName);
						} else {
							//单列索引
							String addIndexName = classSampleName + "_" + propElem.attributeValue("name");
							addIndexName = indexPrefix + hashedName(addIndexName);
							propElem.addAttribute("index", addIndexName);
						}
					}
				}
			}

			if (StringUtil.isNotNull(indexName)) {

				//复合多列索引
				List<String> propertyList = indexNameMap.get(indexName);
				if (null != propertyList && 1 < propertyList.size()) {
					indexName = indexPrefix + hashedName(indexName);
					propElem.addAttribute("index", indexName);
				} else {
					//单列索引
					indexName = classSampleName + "_" + propElem.attributeValue("name");
					indexName = indexPrefix + hashedName(indexName);
					propElem.addAttribute("index", indexName);
				}

				/*if (indexName.length() > 29) {
					String newIndexNm = indexName.substring(indexName.length() - 28);
				
					//某些数据库索引下划线开头会报错误
					if (newIndexNm.startsWith("_")) {
						newIndexNm = newIndexNm.substring(1, newIndexNm.length());
					}
					propElem.addAttribute("index", newIndexNm);
					log.debug("索引名：'" + indexName + "'长度超过或等于30个字符，系统自动裁剪为：'" + newIndexNm + "'.");
				}*/
			}

			//复合多列索引
			if (StringUtil.isNotNull(uniqueKey)) {
				String nameProperty = propElem.attributeValue("name");
				List<String> propertyList = uniqueKeyMap.get(uniqueKey);

				if (null != propertyList) {
					//默认取第一个属性命名
					uniqueKey = classSampleName + "_" + propertyList.get(0);
					uniqueKey = uniqueKeyPrefix + hashedName(uniqueKey);
				}

				for (String propertyName : propertyList) {
					if (propertyName.equals(nameProperty)) {
						propElem.addAttribute("unique-key", uniqueKey);
						break;
					}
				}
			}
		}

		indexNameMap.clear();
		indexNameMap = null;
//		uniqueKeyMap.clear();
//		uniqueKeyMap = null;
	}

	/**
	 * hbm文件流获取
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private InputStream getHbmInputStream(String file) throws Exception {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		InputStream rsrc = null;
		if (contextClassLoader != null) {
			rsrc = contextClassLoader.getResourceAsStream(file);
		}
		if (rsrc == null) {
			rsrc = Environment.class.getClassLoader().getResourceAsStream(file);
		}
		if (rsrc == null) {
			File hbmFile = new File(file);
			if (hbmFile.exists()) {
				rsrc = new FileInputStream(new File(file));
			}
		}

		if (rsrc == null) {
			if (0 > file.indexOf("dir:")) {
				throw new MappingNotFoundException("resource", new Origin(SourceType.RESOURCE, file));
			}
		}
		return rsrc;
	}

	/**
	 * 添加动态更新属性，使得hibernate在更新大字段时能精确更新
	 *
	 * @param doc
	 * @throws Exception
	 */
	private void addDynamicUpdateIfNecessary(org.dom4j.Document doc) throws Exception {
		Element classElement = doc.getRootElement().element("class");
		if (classElement == null) {
			classElement = doc.getRootElement().element("subclass");
		}
		if (classElement != null) {
			String name = classElement.attributeValue("name");
			if (StringUtil.isNull(name)) {
				return;
			}
			Attribute attribute = classElement.attribute("dynamic-update");
			if (attribute == null) {
				classElement.addAttribute("dynamic-update", "true");
			} else {
				if (!"true".equals(attribute.getValue())) {
					attribute.setValue("true");
				}
			}
		}
	}

	private String hashedName(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(charset != null ? s.getBytes(charset) : s.getBytes());
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(35);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new HibernateException("Unable to generate a hashed name!", e);
		}
	}

	/**
	 * 额外的方言处理
	 *
	 * @param file
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public InputStream additionalMetadata(String file) {

		BufferedInputStream bufferedInputStream = null;
		InputStream rsrc = null;
		try {
			XMLHelper xmlHelper = new XMLHelper();
			EntityResolver entityResolver = XMLHelper.DEFAULT_DTD_RESOLVER;
			

			rsrc = getHbmInputStream(file);
			if (null == rsrc) {
				return rsrc;
			}
			List errors = new ArrayList();
			document = xmlHelper.createSAXReader(file, errors, entityResolver).read(rsrc);

			addLangElements(document);
			addCustomElements(document);
			additionIndex(document);
			addDynamicUpdateIfNecessary(document);

			if (StringUtils.isNoneBlank(enableString) && Boolean.valueOf(enableString)) {
				if (addCustomElements) {
					String fileName = StringUtils.substringAfterLast(file, "/");
					FileUtils.writeStringToFile(
							new File(DynamicAttributeUtil.attributeModelPath.getPath() + "/" + fileName), formatXml(),
							Charset.forName("UTF-8"));
				}
			}
			addCustomElements = false;
			String s = document.asXML();
			bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(s.getBytes("UTF-8")));
		} catch (Exception e) {
			log.warn("add sources {},{}", file, e.getClass().getCanonicalName());
		}finally {
			if (null != rsrc) {
				try {
					rsrc.close();
				} catch (IOException e) {
					log.error("getHbmInputStream close stream",e);
				}
			}
			
			if(null != document) {
				document.clone();
			}
		}
		return bufferedInputStream;
	}

	@Override
	public void additionalMetadata(MetadataSources sources, String hbmFilePath) {
		String path = hbmFilePath.substring(5);
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		path = ConfigLocationsUtil.getWebContentPath() + path;
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			File[] hbmList = file.listFiles();
			for (File hbmFile : hbmList) {
				InputStream inputStream = additionalMetadata(hbmFile.getPath());
				sources.addInputStream(inputStream);
			}
			log.info("addDirectory : {}", path);
		}
	}

	@Override
	public InputStream additionalMetadataException(String hbmFilePath, InputStream xmlInputStream) {

		if (StringUtils.isNoneBlank(enableString) && Boolean.valueOf(enableString)) {
			try {
				String fileName = StringUtils.substringAfterLast(hbmFilePath, "/");
				FileUtils.writeStringToFile(
						new File(DynamicAttributeUtil.attributeModelPath.getPath() + "/error_hbm/" + fileName),
						formatXml(), Charset.forName("UTF-8"));
			} catch (Exception e1) {
				log.error("writeStringToFile {}", e1);
			}
		}

		try {
			// 动态加载属性解析HBM文件异常的时候，抛弃动态加载的属性，重新加载该hbm文件
			xmlInputStream = getHbmInputStream(hbmFilePath);
			return xmlInputStream;
		} catch (Exception ex) {
			log.error("add sources {},parse mapping throws {}", hbmFilePath, ex);
		}

		return null;
	}

	@Override
	public boolean getEditionNational() {
		String editionNational = LicenseUtil.get("license-edition-national");
		if (StringUtils.isNotEmpty(editionNational)) {
			log.info("license-edition-national value is {}", editionNational);
			return Boolean.valueOf(editionNational);
		}

		log.info("license-edition-national value is {}", false);
		return false;
	}

	@Override
	public String saveDDLFile(String fileName, List<String> lines, boolean isReplace) {
		File file = new File(ResourceUtil.getKmssConfigString("kmss.resource.path") + "/boot/ddl/" + fileName);
		log.debug("saveDDLFile {}", file.getPath());
		try {
			if ((null == lines || lines.isEmpty()) && !isReplace) {
				return file.getPath();
			} else if (isReplace) {
				if (file.exists()) {
					FileUtils.forceDelete(file);
				}
				FileUtils.writeLines(file, lines, true);
			} else {
				FileUtils.writeLines(file, lines, true);
			}

		} catch (Exception e) {
			log.error("save file {},{}", file.getPath(), e);
		}
		return file.getPath();
	}

	@Override
	public String getFKNotifySql() {
		String fkNotifySql = ResourceUtil.getKmssConfigString("adapter.datasource.fkNotifySql");
		return fkNotifySql;
	}

}
