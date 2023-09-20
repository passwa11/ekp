package com.landray.kmss.third.ding.ws;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 消息拦截器，在报文中添加消息头，以支持服务端的验证
 * 
 */
public class AddSoapHeader extends AbstractSoapInterceptor {

	public AddSoapHeader() {
		super(Phase.WRITE);
	}

	/**
	 * 处理消息，添加消息头
	 * 
	 * @param message SOAP消息
	 * @throws Exception
	 */
	@Override
    public void handleMessage(SoapMessage message) throws Fault {
		WebServiceConfig cfg = WebServiceConfig.getInstance();
		Document doc = DOMUtils.createDocument();
		
		// 添加用户名信息
		Element userElement = doc.createElement("tns:user");
		userElement.setTextContent(cfg.getUser());
		
		// 添加密码信息
		Element pwdElement = doc.createElement("tns:password");
		pwdElement.setTextContent(cfg.getPassword());

		// 创建消息头节点
		Element root = doc.createElementNS("http://sys.webservice.client"
		    , "tns:RequestSOAPHeader");
		root.appendChild(userElement);
		root.appendChild(pwdElement);

		QName qname = new QName("RequestSOAPHeader");
		SoapHeader head = new SoapHeader(qname, root);
		List<Header> headers = message.getHeaders();
		headers.add(head);
	}

}