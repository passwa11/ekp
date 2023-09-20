package com.landray.kmss.tic.soap.connector.util.executor.handler;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.support.SoapUIException;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;
import com.landray.kmss.tic.soap.connector.impl.TicSoapWsdlLoader;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

/**
 * 默认soapui执行webservice时候base控制类
 * 
 * @author zhangtian date :2013-1-14 上午11:31:50
 */
public abstract class TicSoapDefaultExecuteHandler implements
		ITicSoapExecuteHandler {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public TicSoapDefaultExecuteHandler(String wsdlUrl,
			String soapuiVersion, String operationName) {
		this.wsdlUrl = wsdlUrl;
		this.soapuiVersion = soapuiVersion;
		this.operationName = operationName;
	}

	public TicSoapDefaultExecuteHandler(String userName, String password,
			String wsdlUrl, String soapuiVersion, String operationName) {
		this.userName = userName;
		this.password = password;
		this.wsdlUrl = wsdlUrl;
		this.soapuiVersion = soapuiVersion;
		this.operationName = operationName;
	}

	private WsdlRequest wsdlRequest;

	private WsdlInterface wsdlInterface;

	@Override
    public SubmitContext getInitSubmitContext() {
		SubmitContext s_context = new WsdlSubmitContext(wsdlRequest);
		return s_context;
	}

	@Override
    public WsdlRequest getInitWsdlRequest() throws Exception {
		// wsdlRequest=getDefaultRequest(soapuiVersion, operationName);
		if (wsdlRequest == null) {
			wsdlRequest = getRequest();
			if (logger.isDebugEnabled()) {
				logger.debug("解析wsdl,获取需要请求的信息出现异常~!");
			}
		}
		// 根据现在情况，用户名密码都是使用加载时候的request
		if (StringUtil.isNotNull(userName)) {
			wsdlRequest.setUsername(userName);
		}
		if (StringUtil.isNotNull(password)) {
			wsdlRequest.setPassword(password);
		}

		return wsdlRequest;
	}

	/**
	 * 获取当前wsdl 的所有信息
	 * 
	 * @return
	 * @throws SoapUIException
	 * @throws XmlException
	 * @throws IOException
	 */
	private WsdlInterface[] getDefautlWsdlFaces() throws SoapUIException,
			XmlException, IOException {
		String proUsername = "";
		String proPassword ="";
		String connTimeOut ="3000";
		String soTimeOut = "3000";
		TicSoapWsdlLoader wsdlLoader = new TicSoapWsdlLoader(wsdlUrl,
				proUsername, proPassword, connTimeOut, soTimeOut);
		
		return WsdlInterfaceFactory.importWsdl(TicSoapProjectFactory
				.getWsdlProjectInstance(), wsdlUrl, false,wsdlLoader);
	}

	/**
	 * 获取受保护类型wsdl 信息
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws SoapUIException
	 * @throws XmlException
	 * @throws IOException
	 */
	private WsdlInterface[] getVerifyWsdlFaces(String userName, String password)
			throws SoapUIException, XmlException, IOException {

		TicSoapWsdlLoader wsdlLoader = new TicSoapWsdlLoader(wsdlUrl,
				userName, password, "10000", "10000");
		return WsdlInterfaceFactory.importWsdl(TicSoapProjectFactory
				.getWsdlProjectInstance(), wsdlUrl, false, wsdlLoader);
	}

	/**
	 * 获取需要执行的方法对象
	 */
	@Override
    public WsdlOperation getExecuteOperation() throws Exception {
		WsdlInterface iface = getTargetWsdlFace();
		if (iface != null) {
			WsdlOperation operation = iface.getOperationByName(operationName);
			return operation;
		}
		return null;
	}

	/**
	 * 加载request
	 * 
	 * @param soapVersion
	 * @param opernateName
	 * @param userName
	 * @param password
	 * @return
	 * @throws SoapUIException
	 * @throws XmlException
	 * @throws IOException
	 */
	public WsdlRequest getRequest() throws Exception {
		WsdlInterface face = getTargetWsdlFace();
		if (face != null) {
			WsdlOperation w_operation = face.getOperationByName(operationName);
			if (w_operation != null) {
				WsdlRequest wRequest = w_operation.addNewRequest(IDGenerator
						.generateID());
				this.wsdlRequest=wRequest;
				return wRequest;
			}
		}
		return null;
	}

	@Override
    public WsdlInterface getTargetWsdlFace() throws Exception {

		if (wsdlInterface == null) {
			WsdlInterface[] faces = null;
			if (StringUtil.isNull(userName) && StringUtil.isNull(password)) {
				faces = getDefautlWsdlFaces();
			} else {
				faces = getVerifyWsdlFaces(userName, password);
			}
			for (WsdlInterface face : faces) {
				String f_sv = StringUtils.deleteWhitespace(face
						.getSoapVersion().getName());
				String c_sv = StringUtils.deleteWhitespace(soapuiVersion);
				if (f_sv.equalsIgnoreCase(c_sv)) {
					return face;
				}
			}
			return null;
		} else {
			return wsdlInterface;
		}

	}

	private String userName;
	private String password;
	private String wsdlUrl;
	private String soapuiVersion;
	private String operationName;

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public String getSoapuiVersion() {
		return soapuiVersion;
	}

	public void setSoapuiVersion(String soapuiVersion) {
		this.soapuiVersion = soapuiVersion;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public WsdlRequest getWsdlRequest() {
		return wsdlRequest;
	}

	public void setWsdlRequest(WsdlRequest wsdlRequest) {
		this.wsdlRequest = wsdlRequest;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public WsdlInterface getWsdlInterface() {
		return wsdlInterface;
	}

	public void setWsdlInterface(WsdlInterface wsdlInterface) {
		this.wsdlInterface = wsdlInterface;
	}

}
