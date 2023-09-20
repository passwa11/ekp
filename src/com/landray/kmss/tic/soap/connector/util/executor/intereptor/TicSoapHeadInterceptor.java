package com.landray.kmss.tic.soap.connector.util.executor.intereptor;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.model.iface.SubmitContext;
import com.landray.kmss.tic.core.util.DomUtil;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;
import com.landray.kmss.util.StringUtil;

/**
 * 用来设置soap头部的扩展点使用的拦击器
 * 
 * @author zhangtian
 * 
 */
public class TicSoapHeadInterceptor extends AbstractInterceptor {

	private TicSoapSetting soapuiSet;
	private String springName;
	private String className;

	public TicSoapHeadInterceptor(TicSoapSetting soapuiSet,
			String springName, String className, int order) {
		super(order);
		// TODO 自动生成的构造函数存根
		this.soapuiSet = soapuiSet;
		this.springName = springName;
		this.className = className;

	}

	public static Document setHeader(String headTemplate, Document inputNode)
			throws IOException, Exception {
		Document headDoc = DomUtil.stringToDoc(headTemplate);

		Node headNode = DomUtil.selectEle("/Envelope/Header", inputNode);

		Node firstDocImportedNode = inputNode.importNode(headDoc
				.getFirstChild(), true);
		while (headNode.hasChildNodes()) {
			headNode.removeChild(headNode.getFirstChild());
		}
		headNode.appendChild(firstDocImportedNode);

		// headNode.appendChild(headDoc.getFirstChild());

		// headNode.appendChild(headDoc.cloneNode(true));
		// HeaderOperation.nodeToString(headNode);
		// inputNode = DomUtil.stringToDoc(inputNode);
		return inputNode;
	}

	@Override
	public void handlerMessage(SubmitContext submitContext,
			WsdlRequest wsdlRequest, Document data) throws Exception {
		String soapHeaderCustom = soapuiSet.getSoapHeaderCustom();
		// 合并请求XML，主要是为了建造SoapHeader（此种是从"前"台自定义头部信息）
		if (StringUtil.isNotNull(soapHeaderCustom)) {
			setHeader(soapHeaderCustom, data);
			// System.out.println(DomUtil.DocToString(data));
		} else {
			// 获取拥有头部消息的请求参数（此种是从"后"台自定义头部信息）
			HeaderOperation.reBuildDocNode(data, className, springName,
					soapuiSet);
		}

	}

	public TicSoapSetting getSoapuiSet() {
		return soapuiSet;
	}

	public void setSoapuiSet(TicSoapSetting soapuiSet) {
		this.soapuiSet = soapuiSet;
	}

	public String getSpringName() {
		return springName;
	}

	public void setSpringName(String springName) {
		this.springName = springName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
