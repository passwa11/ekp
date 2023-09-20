package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.rest.connector.model.TicRestPrefixReqSetting;
import com.landray.kmss.tic.rest.connector.service.ITicRestPrefixReqSettingService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

public class TicRestLoadPrefixReqSettingService implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String settingId = requestInfo.getParameter("settingId");
		JSONArray fieldList = new JSONArray();
		if (StringUtil.isNotNull(settingId)) {
			ITicRestPrefixReqSettingService ticRestPrefixReqSettingService = (ITicRestPrefixReqSettingService) SpringBeanUtil
					.getBean("ticRestPrefixReqSettingService");
			TicRestPrefixReqSetting ticRestPrefixReqSetting = (TicRestPrefixReqSetting) ticRestPrefixReqSettingService
					.findByPrimaryKey(settingId);
			Map<String, String> map = new HashMap<String, String>();
			map.put("docSubject", ticRestPrefixReqSetting.getDocSubject());
			if (ticRestPrefixReqSetting.getFdUseCustCt()) {
				map.put("fdUseCustCt", ResourceUtil.getString(
						"tic-rest-connector:ticRestPrefixReqSetting.JAVA"));
				map.put("fdAccessTokenClazz",
						ticRestPrefixReqSetting.getFdPrefixReqSettingClazz());
			} else {

				map.put("fdUseCustCt", ResourceUtil.getString(
						"tic-rest-connector:ticRestPrefixReqSetting.HTTP"));
			}
			fieldList.add(map);
		}
		return  fieldList;
	}
}
