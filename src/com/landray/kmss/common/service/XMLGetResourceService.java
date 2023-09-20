package com.landray.kmss.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于根据key值查询资源的XML的通用Service
 * 
 * @author 叶中奇
 */
@Controller
@RequestMapping(value = "/data/sys-common/XMLGetResourceService", method = RequestMethod.POST)
public class XMLGetResourceService implements IXMLDataBean {

	@ResponseBody
	@RequestMapping("getDataList")
	public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		return RestResponse.ok(getDataList(new RequestContext(wrapper)));
	}

	@Override
    public List getDataList(RequestContext xmlContext) throws Exception {
		String para = xmlContext.getParameter("key");
		String locale = xmlContext.getParameter("locale");
		List rtnList = new ArrayList();
		if (!StringUtil.isNull(para)) {
			String[] paraArr = para.split("\\s*;\\s*");
			for (int i = 0; i < paraArr.length; i++) {
				if (StringUtil.isNull(paraArr[i])) {
					rtnList.add("");
				} else {
					if (StringUtil.isNotNull(locale)
							&& ResourceUtil.getLocale(locale) != null) {
						rtnList.add(ResourceUtil.getStringValue(paraArr[i], null,
								ResourceUtil.getLocale(locale)));
					} else {
						rtnList.add(ResourceUtil.getString(paraArr[i],
								xmlContext.getLocale()));
					}
				}
			}
		}
		return rtnList;
	}
}
