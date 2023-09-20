package com.landray.kmss.third.ekp.java;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.xml.XMLFault;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.landray.kmss.util.StringUtil;

/**
 * 消息拦截器，在报文中添加消息头，以支持服务端的验证
 * 
 */
public class AddSoapHeader extends AbstractSoapInterceptor {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AddSoapHeader.class);

	public AddSoapHeader() {
		super(Phase.WRITE);
	}

	/**
	 * 处理消息，添加消息头
	 * 
	 * @param message
	 *            SOAP消息
	 * @throws Exception
	 */
	@Override
    public void handleMessage(SoapMessage message) throws Fault{
		Document doc = DOMUtils.createDocument();
		
		EkpJavaConfig config = null;
		
		try {
			config = new EkpJavaConfig();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new XMLFault("获取配置信息时出错");
		}
		
		Element root = doc.createElementNS("http://sys.webservice.client",
				"tns:RequestSOAPHeader");

		String userName = config.getValue("kmss.java.webservice.userName");
		String password = config.getWebServicePassword();
		logger.debug("password md5 :" + password);
		if (StringUtil.isNotNull(userName) && StringUtil.isNotNull(password)) {
			Element userElement = doc.createElement("tns:user");
			Text userInfo = doc.createTextNode(userName);
			userElement.appendChild(userInfo);
			root.appendChild(userElement);

			Element pwdElement = doc.createElement("tns:password");
			Text passInfo = doc.createTextNode(password);
			pwdElement.appendChild(passInfo);
			root.appendChild(pwdElement);
		}

		QName qname = new QName("RequestSOAPHeader");
		SoapHeader head = new SoapHeader(qname, root);
		message.getHeaders().add(head);
	}
}