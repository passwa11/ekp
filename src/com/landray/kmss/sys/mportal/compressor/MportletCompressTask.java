/**
 * 
 */
package com.landray.kmss.sys.mportal.compressor;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.mobile.compressor.CompressTask;
import com.landray.kmss.sys.mobile.compressor.config.CompressTaskInfo;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageService;
import com.landray.kmss.sys.mportal.service.ISysMportalPageService;
import com.landray.kmss.util.SpringBeanUtil;

public class MportletCompressTask implements CompressTask {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CompressTask.class);

	private CompressTaskInfo taskInfo;

	public MportletCompressTask(CompressTaskInfo taskInfo) {
		super();
		this.taskInfo = taskInfo;
	}
	
	@Override
	public void run() {

		try {
			ISysMportalPageService service = (ISysMportalPageService) SpringBeanUtil
					.getBean("sysMportalPageService");
			Map<String, List<String>> jsAndCssUrls =  service.loadMporletJsAndCssList();
			
			ISysMportalCpageService cpageService = (ISysMportalCpageService) SpringBeanUtil
					.getBean("sysMportalCpageService");
			jsAndCssUrls.putAll(cpageService.loadMporletJsAndCssList());
			 
			String key = taskInfo.getName();

			List<String> values = jsAndCssUrls.get(key);
			
			if (values == null) {
				return;
			}
			CompressFactory.run(key, values);

		} catch (Exception e) {
			logger.error("门户部件压缩报错：",e);
			throw new RuntimeException(e);
		}
	}

}
