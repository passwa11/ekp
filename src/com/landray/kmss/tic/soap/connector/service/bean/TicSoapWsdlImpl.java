/**
 * 
 */
package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eviware.soapui.config.EndpointsConfig;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.util.StringUtil;

/**
 * @author 邱建华
 * todo soapui 需要做一个这样的功能===
 * @version 1.0
 * @2012-8-14
 */
public class TicSoapWsdlImpl implements IXMLDataBean {
	
	@Override
    public List getDataList(RequestContext requestInfo) {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		try {
			String fdWsdlUrl = requestInfo.getParameter("fdWsdlUrl");
			String fdloadUser = requestInfo.getParameter("user");
			String fdloadPwd = requestInfo.getParameter("pwd");
			String fdOverTime = requestInfo.getParameter("fdOverTime");
			String fdResponseTime = requestInfo.getParameter("fdResponseTime");
			TicSoapSetting soapuiSett = new TicSoapSetting();
			soapuiSett.setFdWsdlUrl(fdWsdlUrl);
			soapuiSett.setFdloadUser(fdloadUser);
			soapuiSett.setFdloadPwd(fdloadPwd);
			soapuiSett.setFdOverTime(fdOverTime);
			soapuiSett.setFdResponseTime(fdResponseTime);
			WsdlInterface[]  faces=TicSoapProjectFactory.importWsdl(soapuiSett);
			String endpoint_str = "";
			if(faces!=null){
				for(WsdlInterface wf:faces){
					EndpointsConfig endpointsConfig = wf.getConfig()
							.getEndpoints();
					if (endpointsConfig != null) {
						List<String> endpoints = endpointsConfig
								.getEndpointList();
						if (endpoints != null && endpoints.size() > 1) {
							for (int i = 0; i < endpoints.size(); i++) {
								Map<String, String> map = new HashMap<String, String>();
								String endpoint = endpoints.get(i);
								endpoint_str += endpoint + ";";
							}
						}
					}
					Map<String, String> map=new HashMap<String, String>();
					String verison=wf.getSoapVersion().getName();
					map.put("version", verison);
					rtnList.add(map);
				}
				if (StringUtil.isNotNull(endpoint_str)) {
					endpoint_str = endpoint_str.substring(0,
							endpoint_str.length() - 1);
					Map<String, String> map = new HashMap<String, String>();
					map.put("endpoint", endpoint_str);
					rtnList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnList;
	}
}
	
