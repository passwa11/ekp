package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.rest.connector.model.TicRestAuth;
import com.landray.kmss.tic.rest.connector.service.ITicRestAuthService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

public class TicRestLoadAuthInfoService implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String authId = requestInfo.getParameter("authId");
		JSONArray fieldList = new JSONArray();
		if (StringUtil.isNotNull(authId)) {
			ITicRestAuthService ticRestAuthService = (ITicRestAuthService) SpringBeanUtil
					.getBean("ticRestAuthService");
			TicRestAuth ticRestAuth = (TicRestAuth) ticRestAuthService
					.findByPrimaryKey(authId);
			Map<String, String> map = new HashMap<String, String>();
			map.put("docSubject", ticRestAuth.getDocSubject());
			if (ticRestAuth.getFdUseCustAt()) {
				map.put("fdUseCustAt", ResourceUtil.getString(
						"tic-rest-connector:ticRestAuth.fdOauth.HTTP"));
				map.put("AgentID", ticRestAuth.getFdAgentId());
			} else {
				map.put("fdUseCustAt", ResourceUtil.getString(
						"tic-rest-connector:ticRestAuth.fdOauth.JAVA"));
				map.put("fdAccessTokenClazz",
						ticRestAuth.getFdAccessTokenClazz());
			}
			fieldList.add(map);
		}
		return  fieldList;
	}
}
