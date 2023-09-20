package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eviware.soapui.model.iface.Operation;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.SpringBeanUtil;

public class TicSoapSelectOptionsBean implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>(1);
		try {
			String serviceId = requestInfo.getParameter("serviceId");
			String soapversion = requestInfo.getParameter("soapversion");
			ITicSoapSettingService TicSoapSettingService = (ITicSoapSettingService) SpringBeanUtil
					.getBean("ticSoapSettingService");
			ITicSoap TicSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
			TicSoapSetting soapuiSetting = (TicSoapSetting) TicSoapSettingService
					.findByPrimaryKey(serviceId);
			if (soapuiSetting == null) {
				return rtnList;
			}
			Map<String, Operation> operationMap = new HashMap<String, Operation>(1);
			operationMap = TicSoap.getAllOperation(soapuiSetting, soapversion);

			for (String methodName : operationMap.keySet()) {
				Map<String, String> map = new HashMap<String, String>(1);
				map.put("key", methodName);
				map.put("name", methodName);
				rtnList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnList;
	}

}
