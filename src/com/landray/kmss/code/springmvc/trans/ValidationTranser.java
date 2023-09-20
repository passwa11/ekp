package com.landray.kmss.code.springmvc.trans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 通过validation.xml，修订数据字典信息
 */
@SuppressWarnings("unchecked")
public class ValidationTranser extends FileTranser {
	@Override
	public Result execute(TransContext context) throws Exception {
		String path = context.getPath();
		if (path.endsWith("/validation.xml")
				&& path.startsWith("WebContent/WEB-INF/KmssConfig")) {
			// 读取validation.xml
			Map<String, List<String>> fieldMap = readXml(context.getPath());
			// 修改数据字典
			for (Entry<String, List<String>> entry : fieldMap.entrySet()) {
				updateDataDict(context, entry.getKey(), entry.getValue());
			}
			return Result.Break;
		}
		return Result.Continue;
	}

	/**
	 * 读取validation.xml
	 */
	private Map<String, List<String>> readXml(String path) throws Exception {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new IgnoreDTDEntityResolver());
		Document document = reader.read(new File(path));
		// formset -> form(name) -> field(property)
		List<Element> formsets = document.getRootElement().elements("formset");
		for (Element formset : formsets) {
			List<Element> forms = formset.elements("form");
			for (Element form : forms) {
				List<Element> fields = form.elements("field");
				if (fields.isEmpty()) {
					continue;
				}
				String formName = form.attributeValue("name");
				if (formName == null) {
					continue;
				}
				List<String> properties = result.get(formName);
				if (properties == null) {
					properties = new ArrayList<String>();
					result.put(formName, properties);
				}
				for (Element field : fields) {
					String fieldName = field.attributeValue("property");
					if (fieldName != null && !properties.contains(fieldName)) {
						properties.add(fieldName);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 修改数据字典
	 */
	private void updateDataDict(TransContext context, String form,
			List<String> fields) throws IOException {
		if (fields.isEmpty()) {
			return;
		}
		// 按命名规范查找数据字典所在文件
		if (!form.endsWith("Form")) {
			return;
		}
		String path = context.getPath();
		int index = path.lastIndexOf('/');
		path = path.substring(0, index) + "/data-dict/";
		path += Character.toUpperCase(form.charAt(0))
				+ form.substring(1, form.length() - 4) + ".xml";
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		// 读取数据字典
		String content = FileUtils.readFileToString(file, "UTF-8");
		boolean modify = false;
		for (String field : fields) {
			// 根据字段名查找字段声明未知
			Pattern p = Pattern.compile("\\s+name\\s*=\\s*\"" + field + "\"");
			Matcher m = p.matcher(content);
			if (m.find()) {
				// 找到匹配信息，查找xml的开始和结束位置
				int begin = content.lastIndexOf("<", m.start());
				int end = content.indexOf(">", m.end());
				if (begin > -1 && end > -1) {
					// body是一个字段的完整声明，查找是否声明了validate属性
					String body = content.substring(begin, end + 1);
					p = Pattern.compile("\\s+validate\\s*=\\s*\"");
					m = p.matcher(body);
					if (m.find()) {
						continue;
					}
					// 未声明，增加validate的属性
					index = content.lastIndexOf("\"", end);
					content = content.substring(0, index)
							+ "\"\r\n\t\t\tvalidate=\"true"
							+ content.substring(index);
					modify = true;
				}
			}
		}
		// 文件已修改，保存
		if (modify) {
			context.addFile(path, content);
		}
	}
}
