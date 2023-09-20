package com.landray.kmss.tic.soap.connector.util.init;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class TicSoapInitExecuteUtil {
	
	public static Document getResponseDocByInit(TicSoapSetting soapuiSett, 
			String bindFunc, String soapVersion, Map<String, String> authMap) throws Exception {
		ITicSoap ticSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
		String templateXml = ticSoap.toAllXmlTemplate(soapuiSett, bindFunc, soapVersion);
		Document requestDoc = ParseSoapXmlUtil.parseXmlString("<web>"+ templateXml +"</web>");
		Element ele = ParseSoapXmlUtil.selectElement("//Input/Envelope/Body", requestDoc).get(0);
		// 组装请求参数
		for (String key : authMap.keySet()) {
			NodeList nodeList = ele.getElementsByTagName(key);
			Node node = DOMHelper.getElementNode(nodeList, 0);
			if (node != null) {
				node.setTextContent(authMap.get(key));
			}
		}
		String requestXml = ParseSoapXmlUtil.nodeToString(requestDoc);
		// 设置传入信息VO
		SoapInfo soapInfo = new SoapInfo();
		soapInfo.setRequestXml(requestXml);
		soapInfo.setRequestDocument(requestDoc);
		// 设置主文档信息
		TicSoapMain soapMain = new TicSoapMain();
		soapMain.setWsSoapVersion(soapVersion);
		soapMain.setWsBindFunc(bindFunc);
		soapMain.setTicSoapSetting(soapuiSett);
		soapInfo.setTicSoapMain(soapMain);
		String responseTime = soapMain.getTicSoapSetting().getFdResponseTime();
		String connectTime = soapMain.getTicSoapSetting().getFdOverTime();
		ITicSoapRtn soapRtn = ticSoap.inputToOutputRtn(soapInfo,responseTime,connectTime);
		String responseXml = soapRtn.getRtnContent();
		Document responseDoc = HeaderOperation.stringToDoc(responseXml);
		return responseDoc;
	}
	
}
