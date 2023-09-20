package com.landray.kmss.code.dict;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.landray.kmss.sys.config.dict.util.XmlJsonDictType;

import net.sf.json.JSONObject;

/**
 * 数据字典修复工具
 * 
 * @author 叶中奇
 */
@SuppressWarnings("unchecked")
public class DataDictFixTool extends DataDictTool {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	String modulePath;
	boolean saveFile;
	List<String> modelNames = new ArrayList<String>();

	/** 修复指定模块数据字典 */
	public DataDictFixTool(String modulePath) {
		this.modulePath = modulePath;
	}

	/** 修复所有模块数据字典 */
	public DataDictFixTool() {
		this.modulePath = null;
	}

	/** 仅检查，不修改 */
	public void check() throws Exception {
		saveFile = false;
		doFix();
	}

	/** 检查并修改 */
	public void fix() throws Exception {
		saveFile = true;
		doFix();
	}

	void doFix() throws Exception {
		logger.info("正在读取相关配置文件...");
		prepare(modulePath);
		modelNames.clear();
		for (File file : dictFiles) {
			if (file.getName().endsWith(".json")) {
				doFixJson(file);
			} else {
				doFixXml(file);
			}
		}
		if (LOG_WARN) {
			logDetail("");
			for (String modelName : hbmClasses.keySet()) {
				if (!modelNames.contains(modelName)) {
					logDetail("警告：数据字典未定义：" + modelName);
				}
			}
		}
		finish();
	}

	void doFixJson(File file) throws Exception {
		// 读取原有数据字典
		JSONObject json;
		try {
			json = JSONObject
					.fromObject(FileUtils.readFileToString(file, "UTF-8"));
		} catch (IOException e) {
			logDetail("错误：无法读取文件：" + file.getPath());
			e.printStackTrace();
			return;
		}
		// 修复
		logger.info("正在处理文件：" + file.getPath());
		boolean modify = fixDict(json, file, true);
		String modelName = json.getJSONObject("global")
				.getString("modelName");
		modelNames.add(modelName);
		// 保存
		if (saveFile && modify) {
			saveDict(file, json);
			logger.info("成功修复文件：" + file.getPath());
		}
	}

	void doFixXml(File file) throws Exception {
		List<JSONObject> models = new ArrayList<JSONObject>();
		// 读取原有数据字典
		try {
			// 创建SAXReader对象
			SAXReader reader = new SAXReader();
			// 通过read方法读取一个文件 转换成Document对象
			Document document = reader.read(file);
			// 获取根节点元素对象
			Element root = document.getRootElement();
			listNodes(root, models, null);
		} catch (Exception e) {
			logDetail("错误：无法读取文件：" + file.getPath());
			e.printStackTrace();
			return;
		}
		// 修复
		logger.info("正在处理文件：" + file.getPath());
		for (JSONObject model : models) {
			fixDict(model, file, true);
			String modelName = model.getJSONObject("global")
					.getString("modelName");
			modelNames.add(modelName);
			// 保存
			if (saveFile) {
				int index = modelName.lastIndexOf('.');
				String targetFile = file.getParent() + "/"
						+ modelName.substring(index + 1) + ".json";
				File target = new File(targetFile);
				saveDict(target, model);
				logger.info("成功修复文件：" + target.getPath());
			}
		}
		if (saveFile) {
			logger.info("删除原文件：" + file.getPath());
			file.delete();
		}
	}

	static Map<String, String> propertyNode = new HashMap<String,String>();
	static {
		propertyNode.put(XmlJsonDictType.ID.getName(),
				XmlJsonDictType.ID.getJsonName());
		propertyNode.put(XmlJsonDictType.SIMPLE.getName(),
				XmlJsonDictType.SIMPLE.getJsonName());
		propertyNode.put(XmlJsonDictType.MODEL.getName(),
				XmlJsonDictType.MODEL.getJsonName());
		propertyNode.put(XmlJsonDictType.LIST.getName(),
				XmlJsonDictType.LIST.getJsonName());
		propertyNode.put(XmlJsonDictType.COMPLEX.getName(),
				XmlJsonDictType.COMPLEX.getJsonName());
		propertyNode.put(XmlJsonDictType.ATTACHMENT.getName(),
				XmlJsonDictType.ATTACHMENT.getJsonName());
	}

	void listNodes(Element node, List<JSONObject> models, JSONObject current) {
		String name = node.getName();
		if ("models".equals(name)) {
		} else if ("model".equals(name)) {
			// model
			JSONObject global = new JSONObject();
			node2Json(node, global);
			JSONObject model = new JSONObject();
			model.put("global", global);
			model.put("attrs", new JSONObject());
			models.add(model);

			current = model.getJSONObject("attrs");
		} else if (propertyNode.containsKey(name)) {
			// 字段
			JSONObject property = new JSONObject();
			property.put("propertyType", propertyNode.get(name));
			node2Json(node, property);
			if (XmlJsonDictType.ID.getName().equals(name)) {
				name = "fdId";
			} else {
				name = property.getString("name");
			}
			property.remove("name");
			current.put(name, property);
			current = current.getJSONObject(name);
		} else {
			// 字段下的属性
			JSONObject child = new JSONObject();
			node2Json(node, child);
			current.put(name, child);
			current = current.getJSONObject(name);
		}

		// 迭代当前节点下面的所有子节点
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e, models, current);
		}
	}

	private static void node2Json(Element node, JSONObject json) {
		// 首先获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attribute : list) {
			json.put(attribute.getName(), attribute.getValue());
		}
	}
}
