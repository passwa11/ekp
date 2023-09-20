package com.landray.kmss.sys.mportal.service.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

public class SysMportalLangDataSource implements ICustomizeDataSourceWithRequest {

	private HttpServletRequest httpServletRequest;

	@Override
	public Map<String, String> getOptions() {
		HttpServletRequest request = this.httpServletRequest;
		Map<String, String> options = new HashMap<String, String>();
		// 不限语言
		options.put("", ResourceUtil.getString(request.getSession(), "portlet.var.date.unlimited"));
		String langStr = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if (StringUtil.isNotNull(langStr)) {
			String[] langArr = langStr.trim().split(";");
			for (int i = 0; i < langArr.length; i++) {
				String[] langInfo = langArr[i].split("\\|");
				options.put(langInfo[1], langInfo[0]);
			}
		}
		return options;
	}

	@Override
	public String getDefaultValue() {
		return null;
	}

	@Override
	public void setRequest(ServletRequest request) {
		this.httpServletRequest = (HttpServletRequest) request;
	}

}
