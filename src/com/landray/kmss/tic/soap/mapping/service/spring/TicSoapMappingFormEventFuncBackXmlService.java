package com.landray.kmss.tic.soap.mapping.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.util.SpringBeanUtil;

public class TicSoapMappingFormEventFuncBackXmlService implements IXMLDataBean {


	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicSoapMappingFormEventFuncBackXmlService.class);
	
	private ITicSoapMainService ticSoapMainService;

	// 执行函数返回xml
	@Override
    @SuppressWarnings("unchecked")
	public List getDataList(RequestContext requestInfo) throws Exception {
		// TODO 自动生成的方法存根
		String xml = requestInfo.getParameter("xml");
		String funcId=requestInfo.getParameter("funcId");
		TicSoapMain ticSoapMain=(TicSoapMain)ticSoapMainService.findByPrimaryKey(funcId);
		ITicSoap ticSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
		SoapInfo soapInfo=new SoapInfo();
		soapInfo.setRequestXml(xml);
		soapInfo.setTicSoapMain(ticSoapMain);
		
		List<Map<String, String>> rtnList = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>(1);
	
		String responseTime = ticSoapMain.getTicSoapSetting().getFdResponseTime();
		String connectTime = ticSoapMain.getTicSoapSetting().getFdOverTime();
		ITicSoapRtn  ticSoapRtn= ticSoap.inputToOutputRtn(soapInfo,responseTime,connectTime);
		
		Document doc=ticSoapRtn.getRtnDocument();
		String result="";
		if(doc!=null){
			result =DOMHelper.nodeToString(doc, true);
		}
		else{
			log.debug("出现空数据~!");
		}
		
		/*******************old
//    正式
		String resultXml=ticSoap.inputToAllXml(soapInfo);
		String[] mergeXml=new String[4];
		mergeXml[0]="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		mergeXml[1]="<web ID=\"!{fdId}\">".replace("!{fdId}", funcId);
		mergeXml[2]=resultXml;
		mergeXml[3]="</web>";
		String result=StringUtils.join(mergeXml);
		****************/
		
//		测试
//		InputStream in=TicSoapMappingFormEventFuncBackXmlService.class.getResourceAsStream("respone.xml");
//		String result=IOUtils.toString(in);
//		System.out.println(result);
		try {
//			map.put("funcBackXml", result);
			map.put("funcBackXml", result);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", e.toString());
			// if (log.isDebugEnabled()) {
			 log.debug("执行函数时发生错误。执行的xml为\n" + xml);
			// }
		}
		rtnList.add(map);
		return rtnList;
	}

	public ITicSoapMainService getticSoapMainService() {
		return ticSoapMainService;
	}

	public void setticSoapMainService(ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

	
}
