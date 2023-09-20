package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.rest.connector.model.TicRestCookieSetting;
import com.landray.kmss.tic.rest.connector.service.ITicRestCookieSettingService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

public class TicRestLoadCookieSettingService implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String settingId = requestInfo.getParameter("settingId");
		JSONArray fieldList = new JSONArray();
		if (StringUtil.isNotNull(settingId)) {
			ITicRestCookieSettingService ticRestCookieSettingService = (ITicRestCookieSettingService) SpringBeanUtil
					.getBean("ticRestCookieSettingService");
			TicRestCookieSetting ticRestCookieSetting = (TicRestCookieSetting) ticRestCookieSettingService
					.findByPrimaryKey(settingId);
			Map<String, String> map = new HashMap<String, String>();
			map.put("docSubject", ticRestCookieSetting.getDocSubject());
			if (ticRestCookieSetting.getFdUseCustCt()) {
				map.put("fdUseCustCt", ResourceUtil.getString(
						"tic-rest-connector:ticRestCookieSetting.JAVA"));
				map.put("fdAccessTokenClazz",
						ticRestCookieSetting.getFdCookieSettingClazz());
			} else {

				map.put("fdUseCustCt", ResourceUtil.getString(
						"tic-rest-connector:ticRestCookieSetting.HTTP"));
			}
			fieldList.add(map);
		}
		return  fieldList;
	}
}
