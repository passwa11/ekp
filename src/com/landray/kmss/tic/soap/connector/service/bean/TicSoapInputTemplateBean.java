/**
 * 
 */
package com.landray.kmss.tic.soap.connector.service.bean;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过modelName获取xml模版
 * @author 邱建华
 * @version 1.0 2012-12-25
 */
public class TicSoapInputTemplateBean implements IXMLDataBean {

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		String mainName = requestInfo.getParameter("mainName");
		ITicSoapMainService TicSoapMainService = (ITicSoapMainService) SpringBeanUtil
				.getBean("ticSoapMainService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("ticSoapMain.docSubject=:docSubject");
		hqlInfo.setParameter("docSubject", mainName);
		TicSoapMain main = (TicSoapMain) TicSoapMainService
				.findFirstOne(hqlInfo);
		String templateXml = main.getWsMapperTemplate();
		templateXml = HeaderOperation.allToPartXmlByPath(templateXml, "/web/Input");
		// 移除禁用的节点
		templateXml = ParseSoapXmlUtil.disableFilter(templateXml);
		map.put("templateXml", templateXml);
		rtnList.add(map);
		return rtnList;
	}
	
}
