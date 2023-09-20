package com.landray.kmss.sys.portal.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.util.FileUtil;

public class SysPortalPopTemplateUtil {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalPopTemplateUtil.class);
	
	/**
	 * 模板信息存储
	 */
	private static Map<String, String> templates = new HashMap<String, String>();
	
	public static Map<String, String> getTemplates() {
		return templates;
	}
	
	public static String getTemplate(String id) {
		return templates.get(id);
	}
	
	static {
		loadTemplates(templates);
	}
	
	public static void loadTemplates(Map<String, String> templates) {
		
		try {
			File dir=new File(PluginConfigLocationsUtil.getWebContentPath() + "/sys/portal/pop/template/designs");
			File[] files = dir.listFiles();
			
			for(File file : files) {
				String _fileName = file.getName();
				String fileName = _fileName.substring(0, _fileName.lastIndexOf("."));
				
				String fileString = FileUtil.getFileString(PluginConfigLocationsUtil.getWebContentPath() + "/sys/portal/pop/template/designs/" + _fileName);
				
				templates.put(fileName, fileString);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取模板文件失败！", e);
		}
		 
	}

}
