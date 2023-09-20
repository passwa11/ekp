package com.landray.kmss.tic.soap.connector.util.executor.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.support.soap.SoapMessageBuilder;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.executor.vo.TicSoapEasRtn;
import com.landray.kmss.util.IDGenerator;

public class TicSoapEasHandler extends TicSoapDefaultExecuteHandler {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public TicSoapEasHandler(String userName, String password,
			String wsdlUrl, String soapuiVersion, String operationName,
			Map easMap) {
		super(userName, password, wsdlUrl, soapuiVersion, operationName);
		this.easMap = easMap;
	}

	private Map easMap = new HashMap();

	public Map getEasMap() {
		return easMap;
	}

	public void setEasMap(Map easMap) {
		this.easMap = easMap;
	}

	/**
	 * 获取input模板
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getInputMessageTemplate() throws Exception {

		WsdlInterface iface = getTargetWsdlFace();
		if (iface == null) {
			return null;
		}
		WsdlOperation operation = iface.getOperationByName(getOperationName());
		if (operation == null) {
			return null;
		}
		SoapMessageBuilder builder = iface.getMessageBuilder();
		Definition def = iface.getWsdlContext().getDefinition();
		BindingOperation bo = operation.findBindingOperation(def);
		String rtnMessage = builder.buildSoapMessageFromInput(bo, false);
		return rtnMessage;

	}

	private Document builderEasMessageDocument(Map easInfo, String soapMessage)
			throws Exception {
		Document document = DOMHelper.parseXmlString(soapMessage);
		for (Object easKey : easInfo.keySet()) {
			Object obj = easInfo.get(easKey);
			NodeList nodeList = document.getElementsByTagName((String) easKey);
			Node node = DOMHelper.getElementNode(nodeList, 0);
			if (node != null) {
				// node.setNodeValue((String)obj);
				node.setTextContent((String) obj);
			}
		}
		return document;
	}

	@Override
    public void beforeExecute(SubmitContext submitContext,
                              WsdlRequest wsdlRequest, Document document) throws Exception {
		try {
			if (logger.isWarnEnabled()) {
				logger.warn("推送EAS数据:=============\n"
						+ DOMHelper.nodeToString(document, true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public Document afterExecute(SubmitContext submitContext,
                                 WsdlRequest wsdlRequest, Response response, Document document) {
		if (logger.isWarnEnabled()) {
			logger.warn("返回EAS登录数据:=============\n"
					+ response.getContentAsString());
		}
		return document;
	}

	@Override
    public ITicSoapRtn parseResponse(Response response, Document document) {
		if (logger.isWarnEnabled()) {
			logger.warn("整顿EAS返回数据:=============\n");
		}
		response.getContentAsString();
		TicSoapEasRtn TicSoapEasRtn = new TicSoapEasRtn(response
				.getContentAsString(), response, document);
		return TicSoapEasRtn;
	}

	public Document getPostData() throws Exception {
		String str = getInputMessageTemplate();
		Document document = builderEasMessageDocument(easMap, str);
		return document;
	}

	@Override
	public WsdlRequest getRequest() throws Exception {
		WsdlInterface face = getTargetWsdlFace();

		if (face != null) {
			WsdlOperation w_opernate = face.getOperationByName(getOperationName());
			List<Operation>  options =face.getOperationList();
			w_opernate=(WsdlOperation)options.get(0);
			if (w_opernate != null) {
				WsdlRequest wRequest = w_opernate.addNewRequest(IDGenerator
						.generateID());
				return wRequest;
			}
		}
		return null;
	}

}
