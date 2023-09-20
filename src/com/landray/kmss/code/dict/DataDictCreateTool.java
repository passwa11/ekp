package com.landray.kmss.code.dict;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.code.hbm.HbmClass;

import net.sf.json.JSONObject;

/**
 * 数据字典创建工具
 * 
 * @author 叶中奇
 */
public class DataDictCreateTool extends DataDictTool {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	String modulePath;

	public DataDictCreateTool(String modulePath) {
		this.modulePath = modulePath;
	}

	public void create() throws Exception {
		logger.info("正在读取相关配置文件...");
		prepare(modulePath);
		for (HbmClass hbm : hbmClasses.values()) {
			String modelName = hbm.getName();
			int index = modelName.lastIndexOf('.');
			String targetFile = "WebContent/WEB-INF/KmssConfig/" + modulePath
					+ "/data-dict/" + modelName.substring(index + 1);
			File target = new File(targetFile + ".xml");
			if (target.exists()) {
				logger.info("数据字典已经存在：" + target.getPath());
				continue;
			}
			target = new File(targetFile + ".json");
			if (target.exists()) {
				logger.info("数据字典已经存在：" + target.getPath());
				continue;
			}
			logger.info("正在创建数据字典：" + targetFile);
			JSONObject json = new JSONObject();
			JSONObject global = new JSONObject();
			global.put("modelName", modelName);
			json.put("global", global);
			fixDict(json, target, false);
			saveDict(target, json);
		}
		finish();
	}
}
