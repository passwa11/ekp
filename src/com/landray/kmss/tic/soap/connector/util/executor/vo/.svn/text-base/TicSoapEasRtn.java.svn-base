package com.landray.kmss.tic.soap.connector.util.executor.vo;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eviware.soapui.model.iface.Response;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.util.StringUtil;

public class TicSoapEasRtn implements ITicSoapRtn  {
	
	private String rtnContent;
	private Response response;
	private String rtnType;
	private String sessionId;
	private Document rtnDocument;
	
	public TicSoapEasRtn(String rtnContent, Response response,Document doc) {
		this.rtnContent = rtnContent;
		this.response = response;
		this.rtnDocument=doc;
		sessionId=findSessionId(rtnContent);
		if(StringUtil.isNull(sessionId)){
			rtnType=ERP_SOAPUI_EAR_TYPE_ERROR;
		}
		else{
			rtnType=ERP_SOAPUI_EAR_TYPE_SUCCESS;
		}
	}
	
	private String findSessionId(String rtnContent){
		try{
		Document doc= DOMHelper.parseXmlString(rtnContent);
		
		NodeList nodeList= doc.getElementsByTagName("sessionId");
		Node node=DOMHelper.getElementNode(nodeList, 0);
		String rtn=node.getTextContent();
		return rtn ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
    public String getRtnContent() {
		return rtnContent;
	}
	
	public void setRtnContent(String rtnContent) {
		this.rtnContent = rtnContent;
	}
	@Override
    public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
    public String getRtnType() {
		return rtnType;
	}

	public void setRtnType(String rtnType) {
		this.rtnType = rtnType;
	}

	@Override
    public Document getRtnDocument() {
		return rtnDocument;
	}

	public void setRtnDocument(Document rtnDocument) {
		this.rtnDocument = rtnDocument;
	}
	@Override
    public Document resetRtnDocument(Document document) {
		// TODO 自动生成的方法存根
		setRtnDocument(document);
		return document;
	}
	
	
	

}
