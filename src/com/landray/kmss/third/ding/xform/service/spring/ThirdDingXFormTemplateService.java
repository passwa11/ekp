package com.landray.kmss.third.ding.xform.service.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ResourceUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingXFormTemplateService
		implements IXMLDataBean, InitializingBean {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingXFormTemplateService.class);

	private static final String CONFIG_PATH = "/third/ding/ThirdDingXFormTemplate.json";

	private Map<String, JSONObject> configs = new HashMap<String, JSONObject>();

	@SuppressWarnings("unchecked")
	private void loadConfig() {
		if (configs.isEmpty()) {
			String fullPath = ConfigLocationsUtil.getKmssConfigPath()
					+ CONFIG_PATH;
			File configFile;
			try {
				configFile = ResourceUtils.getFile(fullPath);
				String configStr = FileUtils.readFileToString(configFile,
						"UTF-8");
				configs = JSONObject.fromObject(configStr);
			} catch (Exception e) {
				logger.error("钉钉套件获取配置文件异常", e);
			}
		}
	}

	/**
	 * 获取分类
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getCategory() {
		Set<Entry<String, JSONObject>> entrySet = configs.entrySet();
		JSONArray rtnArr = new JSONArray();
		for (Entry<String, JSONObject> entry : entrySet) {
			String key = entry.getKey();
			JSONObject item = entry.getValue();
			String name = item.getString("name");
			String text = ResourceUtil.getString(name);
			JSONObject category = new JSONObject();
			category.put("name", key);
			category.put("text", text);
			rtnArr.add(category);
		}
		return rtnArr;
	}

	/**
	 * 获取模板
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getTempalteByCategory(String categoryName) {
		JSONArray rtnArr = new JSONArray();
		JSONObject categoryObj = configs.get(categoryName);
		if (categoryObj != null && categoryObj.containsKey("children")) {
			JSONArray childrens = categoryObj.getJSONArray("children");
			if (!ArrayUtil.isEmpty(childrens)) {
				for (int i = 0; i < childrens.size(); i++) {
					String childrenStr = childrens.getString(i);
					JSONObject childrenObj = JSONObject.fromObject(childrenStr);
					JSONObject item = new JSONObject();
					item.put("name", childrenObj.getString("key"));
					item.put("text", ResourceUtil
							.getString(childrenObj.getString("name")));
					item.put("previewUrl", childrenObj.getString("previewUrl"));
					rtnArr.add(item);
				}
			}
		}
		return rtnArr;
	}

	/**
	 * 根据模板名称获取对应的配置项
	 * 
	 * @param name
	 *            模板名称
	 * @return
	 */
	public JSONObject getTemplateConfig(String name) {
		Set<Entry<String, JSONObject>> entrySet = configs.entrySet();
		for (Entry<String, JSONObject> entry : entrySet) {
			JSONObject categoryObj = entry.getValue();
			if (categoryObj.containsKey("children")) {
				JSONArray childrens = categoryObj.getJSONArray("children");
				if (!ArrayUtil.isEmpty(childrens)) {
					for (int i = 0; i < childrens.size(); i++) {
						String childrenStr = childrens.getString(i);
						JSONObject childrenObj = JSONObject
								.fromObject(childrenStr);
						String templateName = childrenObj.getString("key");
						if (templateName.equals(name)) {
							return childrenObj;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据模板name回去对应的item
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTemplateByName(String name) {
		List rtnArr = new ArrayList();
		JSONObject templateConfig = getTemplateConfig(name);
		if (templateConfig != null) {
			SysFormTemplate formTemplate = new SysFormTemplate();
			String htmlPath = templateConfig.getString("htmlPath");
			String fullFilePath = ConfigLocationsUtil
					.getWebContentPath() + htmlPath;
			File configFile;
			try {
				configFile = ResourceUtils
						.getFile(fullFilePath);
				String html = FileUtils.readFileToString(
						configFile,
						"UTF-8");
				formTemplate.setFdDesignerHtml(html);
				rtnArr.add(formTemplate);
			} catch (Exception e) {
				logger.error(
						"加载filePath: " + fullFilePath + "文件异常",
						e);
			}
		}
		return rtnArr;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		List rtnData = new JSONArray();
		if ("1".equals(type)) { // 模板
			String category = requestInfo.getParameter("category");
			rtnData = getTempalteByCategory(category);
		}
		if ("0".equals(type)) { // 分类
			rtnData = getCategory();
		}
		if ("2".equals(type)) { // 根据名称获取配置
			String template = requestInfo.getParameter("template");
			rtnData = getTemplateByName(template);
		}
		if ("3".equals(type)) {
			rtnData.add(ThirdDingXFormTemplateUtil.isShowDingSuit());
		}
		return rtnData;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadConfig();
	}

}
