package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class TicRestMappingFuncXmlService implements IXMLDataBean {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicRestMappingFuncXmlService.class);

	private ITicRestMainService ticRestMainService;

	private ITicCoreMappingFuncService ticCoreMappingFuncService;

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String fdRestMainId = requestInfo.getParameter("fdRestMainId");
		if (StringUtil.isNull(fdRestMainId)) {
			return rtnList;
		}
		try {
			TicRestMain ticRestMain = (TicRestMain) getTicRestMainService()
					.findByPrimaryKey(fdRestMainId);
			String fdReqURL = ticRestMain.getFdReqURL();
			String fdReqParam =ticRestMain.getFdReqParam();
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("fdReqURL", fdReqURL);
			map.put("fdReqParam", fdReqParam);
			Map<String, String> map2 = new HashMap<String, String>(1);
			map2.put("MSG", "SUCCESS");
			rtnList.add(map);
			rtnList.add(map2);
			return rtnList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("fdReqURL", "");
			map.put("fdReqParam", "");
			Map<String, String> map2 = new HashMap<String, String>(1);
			map2.put("MSG", ResourceUtil.getString(
					"erpRestuiMain.dataException", "tic-rest-connector"));
			rtnList.add(map);
			rtnList.add(map2);
		}
		return rtnList;

	}


	public ITicRestMainService getTicRestMainService() {
		if(ticRestMainService==null){
			ticRestMainService=(ITicRestMainService)SpringBeanUtil.getBean("ticRestMainService");
		}
		return ticRestMainService;
	}

	
	public ITicCoreMappingFuncService getTicCoreMappingFuncService() {
		if(ticCoreMappingFuncService==null){
			ticCoreMappingFuncService=(ITicCoreMappingFuncService)SpringBeanUtil.getBean("ticCoreMappingFuncService");
		}
		return ticCoreMappingFuncService;
	}

}
