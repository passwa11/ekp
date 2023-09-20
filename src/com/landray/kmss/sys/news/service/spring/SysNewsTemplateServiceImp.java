package com.landray.kmss.sys.news.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.sys.simplecategory.service.SysSimpleCategoryServiceImp;
import com.landray.kmss.util.ResourceUtil;

/**
 * 创建日期 2007-Sep-17
 * 
 * @author 舒斌 新闻模板设置业务接口实现
 */
public class SysNewsTemplateServiceImp extends SysSimpleCategoryServiceImp
		implements ISysNewsTemplateService, IXMLDataBean {
	private String newsStyle = null;

	public void setNewsStyle(String newsStyle) {
		this.newsStyle = newsStyle;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String[] styleArr = newsStyle.split(";");
		List rtnList = new ArrayList();
		for (int i = 0; i < styleArr.length; i++) {
			Map node = new HashMap();
			node.put("id", styleArr[i]);
			node.put("name", ResourceUtil.getString("sysNewsTemplate.fdStyle."
					+ styleArr[i], "sys-news", requestInfo.getLocale()));
			rtnList.add(node);
		}
		return rtnList;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		// TODO 自动生成的方法存根
		super.update(modelObj);
		//清除缓存
		KmssCache cache = new KmssCache(SysNewsMainPortlet.class);
		cache.clear();
	}
	
	
}
