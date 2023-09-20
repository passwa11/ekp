package com.landray.kmss.sys.portal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.ResourceUtil;

public class TemplateUtil {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(TemplateUtil.class);

	public static List<String> analysisTemplate(String path) {
		String resourcePath = ResourceUtil.KMSS_RESOURCE_PATH;
		if (path.startsWith("/ui-component")) {
			path = "/resource" + path;
		}
		List<String> names = new ArrayList<String>();
		try {
			String fileName = PluginConfigLocationsUtil.getWebContentPath()
					+ path;
			String text = FileUtil.getFileString(fileName);
			Pattern pattern = Pattern
					.compile("<template:block name=\"([\\w]+)\">");
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				names.add(matcher.group(1));
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return names;
	}

	public static void main(String[] args) {
		// analysisTemplate("/sys/ui/extend/template/module/index.jsp");
		System.out.println(java.net.URLEncoder
				.encode("/sys/ui/extend/template/module/index.jsp"));
	}
}
