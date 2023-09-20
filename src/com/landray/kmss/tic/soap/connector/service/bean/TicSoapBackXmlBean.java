/**
 * 
 */
package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 传入参数+主文档名称获取传出参数
 * @author 邱建华
 * @version 1.0 2012-12-26
 */
public class TicSoapBackXmlBean implements IXMLDataBean{

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		try {
			
			String requestXml = requestInfo.getParameter("requestXml");
			String mainName = requestInfo.getParameter("mainName");
			ITicSoap ticSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
			String resultXml = ticSoap.funcNameAndContentToOutput(mainName, requestXml);
			map.put("resultXml", resultXml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		rtnList.add(map);
		return rtnList;
	}
	
}
