package com.landray.kmss.tic.soap.provider.interfaces.impl;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.tic.core.provider.process.provider.interfaces.impl.TicSysBaseProvider;
import com.landray.kmss.tic.core.provider.util.ProviderXmlOperation;
import com.landray.kmss.tic.core.provider.vo.TicCoreStore;
import com.landray.kmss.tic.core.util.TicCoreUtil;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * soap服务提供者
 * 
 * @author fat_tian
 * 
 */
public class TicSoapProvider extends TicSysBaseProvider {

	private ITicSoap ticSoap;

	private ITicSoapMainService ticSoapMainService;

	public void setTicSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

	@Override
	public Object executeData(TicCoreStore coreStore, Object data)
			throws Exception {
		String refId = coreStore.getImplFuncId();
		TicSoapMain soapMain = (TicSoapMain) ticSoapMainService
				.findByPrimaryKey(refId, TicSoapMain.class, true);
		SoapInfo soapInfo = new SoapInfo();
		if (data instanceof String) {
			soapInfo.setRequestXml((String) data);
		} else if (data instanceof Document) {
			String requestXml = ParseSoapXmlUtil.nodeToString((Document) data);
			soapInfo.setRequestXml(requestXml);
		}
		soapInfo.setTicSoapMain(soapMain);
		String result = ticSoap.inputToAllXml(soapInfo);
		return "<web>"+ result +"</web>";
	}

	public ITicSoap getTicSoap() {
		return ticSoap;
	}

	public void setTicSoap(ITicSoap ticSoap) {
		this.ticSoap = ticSoap;
	}

	@Override
	public Object getTemplateXml(String funcId, boolean isDoc) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticSoapMain.wsMapperTemplate");
		hqlInfo.setWhereBlock("ticSoapMain.fdId = :funcId");
		hqlInfo.setParameter("funcId", funcId);
		String templateXml = (String) ticSoapMainService.findFirstOne(hqlInfo);
		Document doc = ProviderXmlOperation.stringToDoc(templateXml);
		// 移除禁用节点
		ParseSoapXmlUtil.getTemplateXmlLoop(doc.getDocumentElement().getChildNodes());
		if (isDoc) {
			return doc;
		} else {
			return ProviderXmlOperation.DocToString(doc);
		}
	}
	
	@Override
	public Object transformFinishData(TicCoreStore coreStore, Object data)
			throws Exception {
		Document responseDoc = null;
		if (data instanceof String) {
			responseDoc = ProviderXmlOperation.stringToDoc((String) data);
		} else if (data instanceof Document) {
			responseDoc = (Document) data;
		}
		// 移除注释
		// RemoveComment(responseDoc.getChildNodes());
		StringBuffer ticBackXml = new StringBuffer("");
		String bodyXpath = "/web/Output/Envelope/Body/*";
		List<Element> bodyEleList = ProviderXmlOperation.selectElement(bodyXpath, responseDoc);
		if (bodyEleList != null && bodyEleList.size() > 0) {
			ticBackXml.append("<tic><out>");
			TicCoreUtil.loopXMLUnite(bodyEleList.get(0).getChildNodes(), ticBackXml);
			ticBackXml.append("</out><return><result>1</result><message/></return></tic>");
		} else {
			ticBackXml.append("<tic><out/><result>0</result><message>");
			String faultPpath = "/web/Fault";
			Element faultEle = ProviderXmlOperation.selectElement(faultPpath, responseDoc).get(0);
			if (faultEle != null) {
				ticBackXml.append(ParseSoapXmlUtil.nodeToString(faultEle));
			}
			ticBackXml.append("</message></return></tic>");
		}
		return ticBackXml.toString();
	}
}
