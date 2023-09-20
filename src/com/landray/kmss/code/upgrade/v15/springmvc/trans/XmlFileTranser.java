package com.landray.kmss.code.upgrade.v15.springmvc.trans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.landray.kmss.code.upgrade.utils.StringUtil;


/**
 * struts.xml转springmvc.xml
 */
@SuppressWarnings("unchecked")
public class XmlFileTranser extends FileTranser {
	/** XML头 */
	private String XMLBegin = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<beans"
			+ "\r\n\txmlns=\"http://www.springframework.org/schema/beans\""
			+ "\r\n\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
			+ "\r\n\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">";
	/** XML尾 */
	private String XMLEnd = "\r\n</beans>";
	/** formBean列表 */
	private Map<String, String> formBeans = new HashMap<String, String>();
	/** action列表 */
	private Map<String, String> actionMappings = new HashMap<String, String>();

	@Override
	public Result execute(TransContext context) throws Exception {
		String path = context.getPath();
		if ((path.endsWith("/struts.xml") || path.endsWith("/struts.4pda.xml"))
				&& path.startsWith("WebContent/WEB-INF/KmssConfig")) {
			StringBuffer content = new StringBuffer(); // 内容
			List<String> errors = new ArrayList<String>(); // 错误信息
			try {
				doExecute(context, content, errors);
			} catch (MissFormBeanException e) {
				// form的数据没准备好，延后处理
				return Result.Delay;
			}
			// 处理错误
			if (errors.size() > 0) {
				String group = path + "的配置未能完全转换，已忽略以下错误";
				for (String error : errors) {
					context.addError(group, error);
				}
			}
			// 若有内容，则创建spring-mvc.xml文件
			if (content.length() > 0) {
				String filePath = path.substring(0, path.lastIndexOf('/') + 1);
				if (path.endsWith("/struts.xml")) {
					filePath += "spring-mvc.xml";
				} else {
					filePath += "spring-mvc.4pda.xml";
				}
				context.addFile(filePath, XMLBegin + content + XMLEnd);
			}
			return Result.Break;
		}
		return Result.Continue;
	}

	private void doExecute(TransContext context, StringBuffer content,
			List<String> errors) throws Exception {
		List<Element> actions = new ArrayList<Element>();
		// 读取XML文件
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new IgnoreDTDEntityResolver());
		Document document = reader.read(new File(context.getPath()));
		// 遍历第二层节点
		Iterator<Element> it = document.getRootElement().elementIterator();
		while (it.hasNext()) {
			Element element = it.next();
			String nodeName = element.getName();
			if ("form-beans".equals(nodeName)) {
				// 加入formBean列表
				appendForms(element, errors);
			} else if ("action-mappings".equals(nodeName)) {
				// 加入Action列表，最后再一起处理
				appendActions(element, actions);
			} else if ("global-forwards".equals(nodeName)) {
				// 转换全局的forward
				String body = transForward(context, element, errors);
				content.append(body);
			} else if ("message-resources".equals(nodeName)) {
				// 不处理
			} else {
				errors.add("不支持" + nodeName + "的配置迁移");
			}
		}
		Map<String, String> actionMap = new LinkedHashMap<String, String>();
		for (Element element : actions) {
			// 转换action，比对当前文件的重复定义
			String path = getValue(element, "path");
			String body = transAction(context, element, errors);
			String _body = actionMap.get(path);
			if (_body != null) {
				if (!_body.equals(body)) {
					errors.add("action(" + path + ")重复定义，自动选择最后的定义");
				}
			}
			actionMap.put(path, body);
		}
		// 若全局未重复定义，写入文件
		for (Entry<String, String> entry : actionMap.entrySet()) {
			String id = entry.getKey();
			String currentPath = context.getPath();
			String otherPath = actionMappings.get(id);
			if (otherPath == null || currentPath.equals(otherPath)) {
				content.append(entry.getValue());
				actionMappings.put(id, currentPath);
			} else {
				errors.add("action(" + id + ")在" + otherPath
						+ "中已经定义，忽略本文件配置");
			}
		}
	}

	/** 加入formBean列表 */
	private void appendForms(Element node, List<String> errors)
			throws Exception {
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element element = it.next();
			String formName = getValue(element, "name");
			String formType = getValue(element, "type");
			formBeans.put(formName, formType);
			// 校验Form
			String dynamic = getValue(element, "dynamic");
			if ("true".equals(dynamic)
					|| "org.apache.struts.action.DynaActionForm".equals(
							formType)
					|| !element.elements().isEmpty()) {
				errors.add("不支持动态表单：" + formName);
				continue;
			}
		}
	}

	/** 加入Action列表 */
	private void appendActions(Element node, List<Element> actions)
			throws Exception {
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element element = it.next();
			actions.add(element);
		}
	}

	/** 转换Action */
	private String transAction(TransContext context, Element element,
			List<String> errors) throws Exception {
		StringBuffer content = new StringBuffer();
		String path = getValue(element, "path");
		String type = getValue(element, "type");
		boolean notFound = false;
		if (StringUtil.isNull(type)) {
			notFound = true;
		} else {
			notFound = !isClassFound(type);
		}
		// 类不存在，注释掉bean
		if (notFound) {
			content.append("\r\n\t<!-- Error : Class Not Found!");
			errors.add("action对应的Java类" + type + "不存在");
		}
		// <bean name="xxx" class="xxx">
		content.append("\r\n\t<bean\r\n\t\tname=\"")
				.append(StringUtil.XMLEscape(path))
				.append(".do\"\r\n\t\tclass=\"").append(type).append('"')
				.append("\r\n\t\tlazy-init=\"true\"")
				.append("\r\n\t\tparent=\"KmssBaseAction\">");

		// formName和formType属性
		String formName = getValue(element, "name");
		if (StringUtil.isNotNull(formName)) {
			// formName已经配置，查找formType
			String formType = formBeans.get(formName);
			if (formType == null) {
				// 找不到，延后处理，若已经是延后处理则提示错误
				if (context.isDelay()) {
					if (!notFound) {
						errors.add("action(" + path + ")对应的表单未找到");
					}
				} else {
					throw new MissFormBeanException();
				}
			} else {
				// 若没按照命名规范，则添加formName属性
				String expName = formType
						.substring(formType.lastIndexOf('.') + 1);
				expName = expName.substring(0, 1).toLowerCase()
						+ expName.substring(1);
				if (!expName.equals(formName)) {
					content.append(
							"\r\n\t\t<property\r\n\t\t\tname=\"formName\"\r\n\t\t\tvalue=\"")
							.append(formName).append("\" />");
				}
				content.append(
						"\r\n\t\t<property\r\n\t\t\tname=\"formType\"\r\n\t\t\tvalue=\"")
						.append(formType).append("\" />");
			}
		}

		// 校验parameter和scope属性
		String parameter = getValue(element, "parameter");
		if (StringUtil.isNotNull(parameter) && !"method".equals(parameter)) {
			errors.add("action(" + path + ")的parameter参数必须为空或method");
		}
		String scope = getValue(element, "scope");
		if (StringUtil.isNotNull(scope) && !"request".equals(scope)) {
			errors.add("action(" + path + ")的scope参数必须为request");
		}

		// 添加forward
		Iterator<Element> it = element.elementIterator();
		Map<String, String> forwards = new LinkedHashMap<String, String>();
		while (it.hasNext()) {
			Element forward = it.next();
			if (!"forward".equals(forward.getName())) {
				continue;
			}
			// 添加一个forward
			String f_name = getValue(forward, "name");
			String f_path = getValue(forward, "path");
			String f_redirect = getValue(forward, "redirect");
			// 校验
			if (forwards.containsKey(f_name)) {
				errors.add("action(" + path + ")的name为" + f_name
						+ "的forward重复定义，自动取最后一个");
			}
			if ("true".equals(f_redirect)) {
				errors.add("action(" + path + ")的forward的redirect不能为true");
			}
			forwards.put(f_name, f_path);
		}
		if (!forwards.isEmpty()) {
			content.append(
					"\r\n\t\t<property name=\"forwards\">\r\n\t\t\t<map>");
			for (Entry<String, String> entry : forwards.entrySet()) {
				content.append("\r\n\t\t\t\t<entry\r\n\t\t\t\t\tkey=\"")
						.append(entry.getKey())
						.append("\"\r\n\t\t\t\t\tvalue=\"")
						.append(StringUtil.XMLEscape(entry.getValue()))
						.append("\" />");
			}
			content.append("\r\n\t\t\t</map>\r\n\t\t</property>");
		}

		content.append("\r\n\t</bean>");
		// 类不存在，注释掉bean
		if (notFound) {
			content.append("\r\n\t-->");
		}
		return content.toString();
	}

	private boolean isClassFound(String className) throws IOException {
		// 根据类名找对应的java文件
		String javaPath = "src/" + className.replace('.', '/') + ".java";
		File javaFile = new File(javaPath);
		if (javaFile.exists()) {
			// java文件存在，判断大小写的情况
			javaPath = javaFile.getCanonicalPath();
			String realType = javaPath
					.substring(javaPath.length() - className.length() - 5,
							javaPath.length() - 5);
			realType = realType.replace('\\', '.').replace('/', '.');
			return realType.equals(className);
		}
		try {
			Thread.currentThread().getContextClassLoader().loadClass(className);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/** 转换全局forward */
	private String transForward(TransContext context, Element element,
			List<String> errors) throws Exception {
		StringBuffer content = new StringBuffer();
		content.append(
				"\r\n\t<bean\r\n\t\tclass=\"com.landray.kmss.web.config.ExtendModuleConfig\">")
				.append("\r\n\t\t<constructor-arg>")
				.append("\r\n\t\t\t<map>");
		Iterator<Element> it = element.elementIterator();
		while (it.hasNext()) {
			Element forward = it.next();
			if (!"forward".equals(forward.getName())) {
				continue;
			}
			// 添加一个forward
			String f_name = getValue(forward, "name");
			String f_path = getValue(forward, "path");
			content.append("\r\n\t\t\t\t<entry\r\n\t\t\t\t\tkey=\"")
					.append(f_name).append("\"\r\n\t\t\t\t\tvalue=\"")
					.append(f_path).append("\" />");
			// 校验redirect参数
			String f_redirect = getValue(forward, "redirect");
			if ("true".equals(f_redirect)) {
				errors.add("global-forwards的forward的redirect不能为true");
			}
		}
		content.append("\r\n\t\t\t</map>")
				.append("\r\n\t\t</constructor-arg>")
				.append("\r\n\t</bean>");
		return content.toString();
	}

	private String getValue(Element element, String attName) {
		Attribute att = element.attribute(attName);
		if (att == null) {
			return null;
		}
		return att.getValue();
	}

	private class MissFormBeanException extends Exception {
		private static final long serialVersionUID = -2315515169184024510L;
	}
}
