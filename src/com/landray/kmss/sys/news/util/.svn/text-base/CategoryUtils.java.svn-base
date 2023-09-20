package com.landray.kmss.sys.news.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.news.model.SysNewsTemplate;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.util.SpringBeanUtil;

public class CategoryUtils {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CategoryUtils.class);
	
	public static String getCategoryName(String fdCateId) throws Exception {
		String fdCategoryName = "";
		SysNewsTemplate sysNewsTemplate = (SysNewsTemplate) ((ISysNewsTemplateService) SpringBeanUtil
				.getBean("sysNewsTemplateService")).findByPrimaryKey(fdCateId);
		if (sysNewsTemplate != null) {
			fdCategoryName = sysNewsTemplate.getFdName();
		}
		return fdCategoryName;

	}

}
