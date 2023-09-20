package com.landray.kmss.sys.simplecategory.service.spring;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryPreviewModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryPreviewManageService;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryPreviewService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;


public abstract class SysSimpleCategoryPreviewManageServiceImp implements ISysSimpleCategoryPreviewManageService {

	public String getElementNodeRootName(String preContent, String templateId) {
		// 思维导图四个字对应的unicode编码
		return "\u601d\u7ef4\u5bfc\u56fe";
	}

	public String getElementNodeRootDocCount(String templateId, String authAreaId) throws Exception {
		return "0";
	}

	public String getElementNodeRootName(String preContent) {
		// 思维导图四个字对应的unicode编码
		return "\u601d\u7ef4\u5bfc\u56fe";
	}

	protected IBaseService mainService;

	protected IBaseService templateService;

	protected ISysSimpleCategoryPreviewService preService;

	private String modelObjectName;

	private String modelTemplateOjectName;

	protected String getMainModelObjectName() {
		if (modelObjectName == null) {
			modelObjectName = getModelObjectName(mainService);
		}
		return modelObjectName;
	}

	protected String getTemplateModelObjectName() {
		if (modelTemplateOjectName == null) {
			modelTemplateOjectName = getModelObjectName(templateService);
		}
		return modelTemplateOjectName;
	}

	private String getModelObjectName(IBaseService service) {
		String modelName = service.getModelName();
		modelName = new String(modelName.substring(modelName.lastIndexOf(".") + 1, modelName.length()));
		return new StringBuilder().append(Character.toLowerCase(modelName.charAt(0))).append(modelName.substring(1))
				.toString();
	}

	private String getCategoryPreviewContent(String categoryId) throws Exception {
		boolean isDateIssolation = isDateIssolation();
		String authAreaId = getUserAuthAreaId();
		String preContent = null;
		// 如果数据存在则不生成(根据区域ID)，直接取出返回
		SysSimpleCategoryPreviewModel model = preService
				.getCategoryPreview(authAreaId, categoryId);
		if (model != null) {
			// 重新生成数据有一个原则：
			// 1:当前记录在已经开启分解授权但没有开启数据隔离的背景下产生的，但后来开启了数据隔离，需要重新生成
			if (!(String.valueOf(isDateIssolation)
					.equals(model.getIsDataIsolation()))
					&& StringUtil.isNotNull(model.getAuthAreaId())) {
				preContent = this.outContent(categoryId, authAreaId);

				model.setIsDataIsolation(String.valueOf(isDateIssolation));
				executeUpdate(model, preContent);
				return model.getFdPreContent();
			} else {
				return model.getFdPreContent();
			}
		} else {
			model = preService.getCategoryPreview(authAreaId, null);
			if (model != null) {
				// 重新生成数据有一个原则：
				// 1:当前记录在已经开启分解授权但没有开启数据隔离的背景下产生的，但后来开启了数据隔离，需要重新生成
				if (!(String.valueOf(isDateIssolation)
						.equals(model.getIsDataIsolation()))
						&& StringUtil.isNotNull(model.getAuthAreaId())) {
					preContent = this.outContent(null, authAreaId);

					model.setIsDataIsolation(String.valueOf(isDateIssolation));
					executeUpdate(model, preContent);
					String Content = model.getFdPreContent();
					JSONArray jsonArray = JSONArray.parseArray(Content);
					fetchSubJson(jsonArray, categoryId);
					return value;
				} else {
					String Content = model.getFdPreContent();
					JSONArray jsonArray = JSONArray.parseArray(Content);
					fetchSubJson(jsonArray, categoryId);
					return value;
				}
			} else {
				preContent = this.outContent(null, authAreaId);
				preService.addCategoryPreviewBySomething(preContent, null,
						authAreaId,
						StringUtil.isNull(authAreaId) ? null
								: String.valueOf(isDateIssolation));
				if (StringUtil.isNotNull(categoryId)) {
					JSONArray jsonArray = JSONArray.parseArray(preContent);
					fetchSubJson(jsonArray, categoryId);
					return value;
				} else {
					return preContent;
				}
			}
		}
	}

	private static String value = null;

	private void fetchSubJson(JSONArray jsonArray, String categoryId)
			throws Exception {
			for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject job = jsonArray.getJSONObject(i);
			if (categoryId.equals(job.get("id"))) {
				JSONArray temp = new JSONArray();
				temp.add(job);
				value = temp.toString();
			} else {
				fetchSubJson(job.getJSONArray("children"), categoryId);
			}
			}
	}
	
	@Override
	public JSONArray generateCategoryPreviewContent(String categoryId)throws Exception {
		String preContent = this.getCategoryPreviewContent(categoryId);
		return cateAuth(preContent, null, "03");
	}

	@Override
	public JSONArray generateJSONCategoryPreviewContent(String categoryId) throws Exception {
		String preContent = this.getCategoryPreviewContent(categoryId);
		return cateAuth(preContent, null, "03");
	}

	/**
	 * 权限过滤
	 * 
	 * @param preContent
	 * @return
	 * @throws Exception
	 */
	private JSONArray cateAuth(String preContent, String modelName, String authType) throws Exception {
		if (StringUtil.isNull(preContent)) {
			return new JSONArray();
		}
		if (StringUtil.isNull(modelName)) {
			modelName = templateService.getModelName();
		}

		SysSimpleCategoryAuthList authService = (SysSimpleCategoryAuthList) SpringBeanUtil
				.getBean("sysSimpleCategoryAuthList");
		List<String> ids = authService.getSimpleCategoryAuthList(modelName, authType, true);
		JSONArray preJson = JSONArray.parseArray(preContent);
		if (ArrayUtil.isEmpty(ids)) {
			return preJson;
		}
		removeNoAuth(preJson, ids);

		return preJson;
	}

	private void removeNoAuth(JSONArray node, List<String> authIds) throws Exception {
		@SuppressWarnings("unchecked")
		Iterator<Object> ite = (Iterator<Object>) node.iterator();
		while (ite.hasNext()) {
			JSONObject item = (JSONObject) ite.next();
			if (!authIds.contains((String) (item.get("id")))) {
				ite.remove();
				continue;
			}
			JSONArray children = item.getJSONArray("children");
			if (children != null && children.size() > 0) {
				removeNoAuth(children, authIds);
			}
		}
	}

	@Override
	public void updateCategoryPreviewContent() throws Exception {
		List<SysSimpleCategoryPreviewModel> modelList = preService.getCategoryPreviewList();
		boolean isEnable = isAreaEnable();
		for (SysSimpleCategoryPreviewModel preModel : modelList) {
			SysSimpleCategoryPreviewModel updateModel = null;
			if (isEnable) {
				if (StringUtil.isNotNull(preModel.getAuthAreaId())) {
					updateModel = preModel;
				}

			} else {
				if (StringUtil.isNull(preModel.getAuthAreaId())) {
					updateModel = preModel;
				}
			}
			if (updateModel != null) {
				// executeUpdate(updateModel, p.generatePreContent());
				String preContent = generateCategoryPreviewContent(updateModel.getFdCategoryId()).toString();
				executeUpdate(updateModel, preContent);
			}

		}
	}

	private void executeUpdate(SysSimpleCategoryPreviewModel updateModel, String content) throws Exception {
		updateModel.setAlterDate(new Date());
		updateModel.setFdPreContent(content);
		preService.update(updateModel);
	}

	/**
	 * 是否开启集团分级授权
	 * 
	 * @return boolean
	 */
	private boolean isAreaEnable() {
		return ISysAuthConstant.IS_AREA_ENABLED;
	}

	/**
	 * 是否开启数据隔离
	 * 
	 * @return boolean
	 */
	private boolean isDateIssolation() {
		return isAreaEnable() && ISysAuthConstant.IS_ISOLATION_ENABLED;
	}

	private String getUserAuthAreaId() {
		return UserUtil.getKMSSUser().getAuthAreaId();
	}

	protected void addAuthAreaCheck(HQLInfo hqlInfo, String authAreaId) {
		if (StringUtil.isNull(authAreaId)) {
			return;
		}
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation, SysAuthConstant.AreaIsolation.BRANCH);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaSpecified, authAreaId);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
	}

	protected abstract String buildUrl(String templateId);

	protected abstract String calculateDocAmount(SysSimpleCategoryAuthTmpModel templateModel, String authAreaId)
			throws Exception;

	private abstract class ContentGenerator {

		/**
		 * 集团分级授权
		 */
		public String getAuthAreaId() {
			return getUserAuthAreaId();
		}

		/**
		 * 根据分类封装数据包
		 * 
		 * @param preContent
		 * @param templateId
		 * @return
		 * @throws Exception
		 */
		abstract public String generatePreContent(String preContent, String templateId) throws Exception;

	}

	/**
	 * 预览json内容生成器
	 *
	 */
	private class JsonContentGenerator extends ContentGenerator {

		private final int level = 2;

		@Override
		public String generatePreContent(String preContent, String templateId) throws Exception {

			if (StringUtil.isNull(templateId)) {
				return generatePreContent(preContent);
			}

			return null;
		}

		public String generatePreContent(JSONArray preContent,
				String templateId) throws Exception {

			if (StringUtil.isNull(templateId)) {
				return generatePreContent(preContent);
			}

			return null;
		}

		private String generatePreContent(String preContent) throws Exception {

			JSONArray array = new JSONArray();
			buildRoot(array, preContent);
			buildContent(array, preContent);
			return array.toString();
		}

		private String generatePreContent(JSONArray preContent)
				throws Exception {

			JSONArray array = new JSONArray();
			buildRoot(array, preContent.toString());
			buildContent(array, preContent);
			return array.toString();
		}

		private void buildRoot(JSONArray array, String preContent) {

			JSONObject obj = new JSONObject();
			obj.put("id", "root");
			obj.put("isroot", true);
			obj.put("topic", getElementNodeRootName(preContent));
			obj.put("badge", "");
			obj.put("expanded", false);
			array.add(obj);

		}

		private void buildContent(JSONArray array, String preContent) {

			JSONArray jsonContent = JSONArray.parseArray(preContent);
			for (int i = 0; i < jsonContent.size(); i++) {

				JSONObject obj = (JSONObject) jsonContent.get(i);

				// 层级控制
				fetchParents(array, obj);

			}

		}

		private void buildContent(JSONArray array, JSONArray jsonContent) {

			for (int i = 0; i < jsonContent.size(); i++) {

				JSONObject obj = (JSONObject) jsonContent.get(i);

				// 层级控制
				fetchParents(array, obj);

			}

		}

		private void fetchParents(JSONArray array, JSONObject obj) {

			JSONObject jsonObj = new JSONObject();

			String topic = (String) obj.get("text");
			String id = (String) obj.get("id");
			String badge = "0";
			if (obj.get("docAmount") != null) {
				badge = obj.get("docAmount").toString();
			}

			JSONArray children = (JSONArray) obj.get("children");

			jsonObj.put("id", id);
			jsonObj.put("topic", topic);
			jsonObj.put("badge", badge);
			jsonObj.put("parentid", "root");
			jsonObj.put("expanded", false);

			array.add(jsonObj);

			fetchChilds(id, children, array);

		}

		@SuppressWarnings("unchecked")
		private void fetchChilds(String parentId, JSONArray children, JSONArray array) {

			if (children == null) {
				return;
			}

			Iterator<Object> iter = children.iterator();

			while (iter.hasNext()) {

				JSONObject jsonObj = new JSONObject();
				JSONObject obj = (JSONObject) iter.next();

				String topic = (String) obj.get("text");
				String id = (String) obj.get("id");
				String badge = "0";
				if (obj.get("docAmount") != null) {
					badge = obj.get("docAmount").toString();
				}

				JSONArray subChildren = (JSONArray) obj.get("children");

				jsonObj.put("id", id);
				jsonObj.put("topic", topic);
				jsonObj.put("badge", badge);
				jsonObj.put("parentid", parentId);
				jsonObj.put("expanded", false);

				array.add(jsonObj);

				fetchChilds(id, subChildren, array);

			}

		}

	}

	/**
	 * 预览xml內容生成器
	 * 
	 * @author Administrator
	 * 
	 */
	private class XmlContentGenerator extends ContentGenerator {

		private static final String ELEMENT_NODE_NAME = "treeElement";

		private String elementNodeName = null;

		@Override
		public String generatePreContent(String preContent, String templateId) throws Exception {

			if (StringUtil.isNull(templateId)) {
				return generatePreContent(preContent);
			}
			Document document = DocumentHelper.createDocument();
			buildXmlContent(document, preContent, templateId);
			return formatXml(document, "UTF-8");
		}

		private String generatePreContent(String preContent) throws Exception {
			Document document = DocumentHelper.createDocument();
			buildXmlContent(document, preContent);
			updateDocSumCount(document);
			return formatXml(document, "UTF-8");
		}

		/**
		 * 更新最顶层节点的文档总数量
		 * 
		 * @param document
		 */
		private void updateDocSumCount(Document document) {
			// 更新文檔总数量
			Element treeElementRoot = (Element) com.landray.kmss.util.jdom.DocumentWrapper.selectNodes(document,"canvasData/module/mind/treeElement").get(0);
			String docSumCount = calculateSumDocCount(document);
			treeElementRoot.attribute("docCount").setValue(String.valueOf(docSumCount));
		}

		/**
		 * 计算所有类别父节頂層節點所包含的文档数量
		 * 
		 * @param document
		 * @return String
		 */

		@SuppressWarnings("unchecked")
		private String calculateSumDocCount(Document document) {
			List<Element> elements = com.landray.kmss.util.jdom.DocumentWrapper.selectNodes(document,"canvasData/module/mind/treeElement/treeElement");
			int sumCount = 0;
			for (Element element : elements) {
				String docCount = element.attribute("docCount").getValue();
				sumCount += Integer.valueOf(docCount);
			}
			return String.valueOf(sumCount);
		}

		/**
		 * 构建xml内容
		 * 
		 * @throws Exception
		 */
		private void buildXmlContent(Document document, String preContent) throws Exception {
			Element rootElement = document.addElement("canvasData");
			Element headerElement = rootElement.addElement("header");
			headerElement.addAttribute("version", "kanvas2.0");
			headerElement.addAttribute("styleID", "White");
			Element backgroundElement = rootElement.addElement("bg");
			setBackGroudAttribute(backgroundElement);
			Element moduleElement = rootElement.addElement("module");
			Element mindElement = moduleElement.addElement("mind");
			setMindAttribute(mindElement);
			Element treeElementRoot = createElementNodeRoot(mindElement, preContent);
			fetchParents(treeElementRoot, preContent);
		}

		private void buildXmlContent(Document document, String preContent, String templateId) throws Exception {
			Element rootElement = document.addElement("canvasData");
			Element headerElement = rootElement.addElement("header");
			headerElement.addAttribute("version", "kanvas2.0");
			headerElement.addAttribute("styleID", "White");
			Element backgroundElement = rootElement.addElement("bg");
			setBackGroudAttribute(backgroundElement);
			Element moduleElement = rootElement.addElement("module");
			Element mindElement = moduleElement.addElement("mind");
			setMindAttribute(mindElement);
			Element treeElementRoot = createElementNodeRoot(mindElement, preContent, templateId);
			fetchParents(treeElementRoot, preContent);
		}

		/**
		 * 创建最顶层的treeElement
		 * 
		 * @param element
		 * @return
		 */
		private Element createElementNodeRoot(Element element, String preContent) {
			Element e = element.addElement(ELEMENT_NODE_NAME);
			if (StringUtil.isNull(elementNodeName)) {
				String ELEMENT_NODE_ROOT_NAME = getElementNodeRootName(preContent);
				e.addAttribute("text", ELEMENT_NODE_ROOT_NAME);
			} else {
				e.addAttribute("text", elementNodeName);
			}
			e.addAttribute("id", "");
			e.addAttribute("property", "");
			e.addAttribute("docCount", "");
			e.addAttribute("url", "");
			return e;
		}

		private Element createElementNodeRoot(Element element, String preContent, String templateId) throws Exception {
			Element e = element.addElement(ELEMENT_NODE_NAME);
			if (StringUtil.isNull(elementNodeName)) {
				String ELEMENT_NODE_ROOT_NAME = getElementNodeRootName(preContent, templateId);
				e.addAttribute("text", ELEMENT_NODE_ROOT_NAME);
			} else {
				e.addAttribute("text", elementNodeName);
			}

			String docCount = getElementNodeRootDocCount(templateId, getAuthAreaId());
			e.addAttribute("id", "");
			e.addAttribute("property", "");
			e.addAttribute("docCount", docCount);
			e.addAttribute("url", "");
			return e;
		}

		/**
		 * 抓取最顶层的类别
		 * 
		 * @param element
		 * @throws Exception
		 */
		private void fetchParents(Element element, String preContent) throws Exception {
			JSONArray jsonContent = JSONArray.parseArray(preContent);
			for (int i = 0; i < jsonContent.size(); i++) {
				JSONObject jsonData = (JSONObject) jsonContent.get(i);
				String text = (String) jsonData.get("text");
				String id = (String) jsonData.get("id");
				String docAmount = "0";
				if (jsonData.get("docAmount") != null) {
					docAmount = jsonData.get("docAmount").toString();
				}
				JSONArray children = (JSONArray) jsonData.get("children");

				Element cElement = element.addElement(ELEMENT_NODE_NAME);
				setElementAttribute(cElement, text, id, docAmount);
				fetchChilds(cElement, children);
			}

		}

		/**
		 * 递归抓取子类别
		 * 
		 * @param element
		 * @param parentId
		 * @throws Exception
		 */
		private void fetchChilds(Element element, JSONArray children) throws Exception {
			for (int i = 0; i < children.size(); i++) {
				JSONObject jsonData = (JSONObject) children.get(i);
				String text = (String) jsonData.get("text");
				String id = (String) jsonData.get("id");
				String docAmount = "0";
				if (jsonData.get("docAmount") != null) {
					docAmount = jsonData.get("docAmount").toString();
				}
				JSONArray subChildren = (JSONArray) jsonData.get("children");

				Element cElement = element.addElement(ELEMENT_NODE_NAME);
				setElementAttribute(cElement, text, id, docAmount);
				fetchChilds(cElement, subChildren);
			}
		}

		private void setElementAttribute(Element e, String text, String id, String docCount) throws Exception {
			// String docAmount = calculateDocAmount(templateModel,
			// authAreaId);
			e.addAttribute("text", text);
			e.addAttribute("id", id);
			e.addAttribute("property", "");
			e.addAttribute("docCount", docCount);
			e.addAttribute("url", encodeUrl(id));
		}

		/**
		 * 将Url进行编码，防止xml对特殊字符进行转义，例如&被转义为&amp;
		 * 
		 * @param fdId
		 * @return String
		 */
		private String encodeUrl(String fdId) {
			String urlPrefix = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			String url = urlPrefix.concat(buildUrl(fdId));
			try {
				url = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// 忽略错误，因为固定为UTF-8
			}
			return url;
		}

		/**
		 * 格式化xml
		 * 
		 * @param document
		 * @param charset
		 * @return
		 * @throws Exception
		 */
		private String formatXml(Document document, String charset) throws Exception {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(charset);
			StringWriter sw = new StringWriter();
			XMLWriter xw = new XMLWriter(sw, format);
			xw.setEscapeText(false);
			try {
				xw.write(document);
				xw.flush();
			} finally {
				xw.close();
			}
			return sw.toString();
		}

		/**
		 * 设置背景属性
		 * 
		 * @param element
		 */
		private void setBackGroudAttribute(Element element) {
			element.addAttribute("colorIndex", "0");
		}

		private void setMindAttribute(Element element) {
			element.addAttribute("type", "auto");
			element.addAttribute("style", "default");
		}

	}

	public void setMainService(IBaseService mainService) {
		this.mainService = mainService;
	}

	public void setTemplateService(IBaseService templateService) {
		this.templateService = templateService;
	}

	public void setPreService(ISysSimpleCategoryPreviewService preService) {
		this.preService = preService;
	}

	@SuppressWarnings("unchecked")
	protected void ____previeQuartz(IBaseService ___service) throws Exception {
		if (___service == null) {
            return;
        }
		HQLInfo hqlInfo = new HQLInfo();
		List<SysSimpleCategoryPreviewModel> ____list = ___service.findList(hqlInfo);
		for (int j = 0; j < ____list.size(); j++) {
			SysSimpleCategoryPreviewModel model = ____list.get(j);

			String preContent = this.outContent(model.getFdCategoryId(), model.getAuthAreaId());
			executeUpdate(____list.get(j), preContent);
		}
	}

	/**
	 * 构造xml格式内容
	 * 
	 * @param preContent
	 * @throws Exception
	 */
	@Override
	public String generateXmlContent(String preContent, String templateId) throws Exception {
		XmlContentGenerator p = new XmlContentGenerator();
		return p.generatePreContent(preContent, templateId);
	}

	/**
	 * 获取多级分类目录列表
	 * 
	 * @param fdCategoryId
	 * @param authAreaId
	 * @return
	 * @throws Exception
	 */
	public List<SysSimpleCategoryAuthTmpModel> getMainContent(String fdCategoryId, String authAreaId) throws Exception {

		List<SysSimpleCategoryAuthTmpModel> firstCategoryList = preService.getCategoryList(fdCategoryId, authAreaId);
		List<SysSimpleCategoryAuthTmpModel> preList = new ArrayList<SysSimpleCategoryAuthTmpModel>();

		for (SysSimpleCategoryAuthTmpModel firstTemplate : firstCategoryList) {
			
			firstTemplate.setDocAmount(
					preService.getDocAmount(firstTemplate, authAreaId));
			List<SysSimpleCategoryAuthTmpModel> secondCategoryList = preService.getCategoryList(firstTemplate.getFdId(),
					authAreaId);

			List<SysSimpleCategoryAuthTmpModel> tempList = new ArrayList<SysSimpleCategoryAuthTmpModel>();

			for (SysSimpleCategoryAuthTmpModel secTemplate : secondCategoryList) {
				
				secTemplate.setDocAmount(
						preService.getDocAmount(secTemplate, authAreaId));
				tempList.add(secTemplate);
			}
			firstTemplate.setTempList(tempList);

			fetchCategoryChilds(secondCategoryList, firstTemplate, authAreaId);
			preList.add(firstTemplate);
		}
		return preList;
	}

	// 获取特定分类下所有子分类
	private void fetchCategoryChilds(List<SysSimpleCategoryAuthTmpModel> categoryList,
			SysSimpleCategoryAuthTmpModel firstPre, String authAreaId) throws Exception {
		List<SysSimpleCategoryAuthTmpModel> tempList = new ArrayList<SysSimpleCategoryAuthTmpModel>();

		for (SysSimpleCategoryAuthTmpModel secTemplate : categoryList) {
			
			secTemplate.setDocAmount(
					preService.getDocAmount(secTemplate, authAreaId));

			List<SysSimpleCategoryAuthTmpModel> subCategoryList = preService.getCategoryList(secTemplate.getFdId(),
					authAreaId);
			fetchCategoryChilds(subCategoryList, secTemplate, authAreaId);
			tempList.add(secTemplate);
		}
		firstPre.setTempList(tempList);
	}

	/**
	 * 生成分类概览
	 * 
	 * @param fdCategoryId
	 * @param authAreaId
	 * @return
	 * @throws Exception
	 */
	public String outContent(String fdCategoryId, String authAreaId) throws Exception {
		JSONArray jsonArray = new JSONArray();

		List<SysSimpleCategoryAuthTmpModel> templatePreTopList = getMainContent(fdCategoryId, authAreaId);
		for (SysSimpleCategoryAuthTmpModel sysSimpleCategoryAuthTmpModel : templatePreTopList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", sysSimpleCategoryAuthTmpModel.getFdId());
			jsonObject.put("text", sysSimpleCategoryAuthTmpModel.getFdNameOri());

			if ("true".equals(ResourceUtil
					.getKmssConfigString("kmss.lang.suportEnabled"))) {
				List<String> langs = SysLangUtil.getSupportedLangList();
				for (String lang : langs) {
					String[] langEnd = lang.split("-");
					Locale locale = ResourceUtil.getLocale(lang);
					String name = SysLangUtil.getPropValueByLand(
							sysSimpleCategoryAuthTmpModel, "fdName",
							sysSimpleCategoryAuthTmpModel.getFdNameOri(),
							SysLangUtil.getLocaleShortName(locale));
					jsonObject.put("text" + "_" + langEnd[1].toLowerCase(),
							name);
				}
			}
			jsonObject.put("ori", SysLangUtil.getCurrentLocaleCountry());

			if (sysSimpleCategoryAuthTmpModel.getDocAmount() > 0) {
				jsonObject.put("docAmount", sysSimpleCategoryAuthTmpModel.getDocAmount());
			}
			fetchSubCategorys(sysSimpleCategoryAuthTmpModel, jsonObject);

			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	/**
	 * 获取子分类
	 * 
	 */
	private void fetchSubCategorys(SysSimpleCategoryAuthTmpModel sysSimpleCategoryAuthTmpModel, JSONObject jsonObject)
			throws Exception {
		JSONArray array = new JSONArray();

		for (SysSimpleCategoryAuthTmpModel temp : sysSimpleCategoryAuthTmpModel.getTempList()) {
			JSONObject JObject = new JSONObject();
			JObject.put("id", temp.getFdId());
			JObject.put("text", temp.getFdName());

			if (temp.getDocAmount() > 0) {
				JObject.put("docAmount", temp.getDocAmount());
			}

			if ("true".equals(ResourceUtil
					.getKmssConfigString("kmss.lang.suportEnabled"))) {
				List<String> langs = SysLangUtil.getSupportedLangList();
				for (String lang : langs) {
					String[] langEnd = lang.split("-");
					Locale locale = ResourceUtil.getLocale(lang);
					String name = SysLangUtil.getPropValueByLand(
							temp, "fdName",
							temp.getFdName(),
							SysLangUtil.getLocaleShortName(locale));
					JObject.put("text" + "_" + langEnd[1].toLowerCase(),
							name);
				}
			}

			fetchSubCategorys(temp, JObject);
			array.add(JObject);
		}
		jsonObject.put("children", array);
	}

	@Override
	public String generateJsonContent(JSONArray preContent, String templateId)throws Exception {
		JsonContentGenerator p = new JsonContentGenerator();
		return p.generatePreContent(preContent, templateId);

	}
}
