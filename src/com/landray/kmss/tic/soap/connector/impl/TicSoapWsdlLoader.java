package com.landray.kmss.tic.soap.connector.impl;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.support.definition.support.InvalidDefinitionException;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlLoader;
import com.eviware.soapui.settings.WsdlSettings;
import com.eviware.soapui.support.xml.XmlUtils;
import com.eviware.soapui.tools.PropertyExpansionRemover;
import com.landray.kmss.tic.soap.connector.util.fileTemplate.FileUtils;
import com.landray.sso.client.util.StringUtil;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

public class TicSoapWsdlLoader extends WsdlLoader {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicSoapWsdlLoader.class);
	
	private String userName;
	private String password;
	private Integer connTimeOut = 3000;
	private Integer soTimeOut = 3000;
	private String responseBodyString = null;

	private TicSoapWsdlLoader(String url) {
		super(url);
	}

	public TicSoapWsdlLoader(String url, String userName, String password,
			String connTimeOut, String soTimeOut) {
		super(url);
		if (StringUtil.isNotNull(userName)) {
			this.userName = userName;
		}
		if (StringUtil.isNotNull(password)) {
			this.password = password;
		}
		/*if (StringUtil.isNotNull(connTimeOut))
			this.connTimeOut = Integer.parseInt(connTimeOut);*/
		if (StringUtil.isNotNull(soTimeOut)) {
			this.soTimeOut = Integer.parseInt(soTimeOut);
		}
	}

	@Override
	public XmlObject loadXmlObject(String url, XmlOptions options) throws Exception {
		try {
			if (options == null) {
				options = new XmlOptions();
			}

			if (this.monitor != null) {
				this.monitor.setProgress(this.progressIndex, "Loading [" + url + "]");
			}

			options.setLoadLineNumbers();
			String content = this.readCleanWsdlFrom(url);
			return XmlUtils.createXmlObject(new ByteArrayInputStream(content.getBytes("UTF-8")), options);
		} catch (XmlException var6) {
			XmlError error = var6.getError();
			if (error != null) {
				InvalidDefinitionException ex = new InvalidDefinitionException(var6);
				ex.setMessage("Error loading [" + url + "]");
				throw ex;
			} else {
				throw this.makeInvalidDefinitionException(url, var6);
			}
		} catch (Exception var7) {
			throw this.makeInvalidDefinitionException(url, var7);
		}
	}

	private String readCleanWsdlFrom(String url) throws Exception {
		InputStream in = this.load(url);
		String content = null;
		try{
			content = XmlUtils.createXmlObject(in).xmlText();
		}
		catch(Exception e){
			if(StringUtil.isNotNull(responseBodyString)){
				logger.error("获取soap内容失败,将尝试直接使用responseBodyString", e);
				content = responseBodyString;
			}
			else{
				throw e;
			}
		}
		if (SoapUI.getSettings().getBoolean(WsdlSettings.TRIM_WSDL)) {
			content = content.trim();
		}

		return PropertyExpansionRemover.removeExpansions(content);
	}

	private InvalidDefinitionException makeInvalidDefinitionException(String url, Exception e) throws InvalidDefinitionException {
		e.printStackTrace();
		log.error("Failed to load url [" + url + "]");
		return new InvalidDefinitionException("Error loading [" + url + "]: " + e);
	}

	@Override
	public InputStream load(String address) throws Exception {
		if(address.indexOf("/services/WSGLWebServiceFacade?wsdl")>0){
			return FileUtils.getInputStream("EAS.xml",FileUtils.replaceWsdl(address));
		}else if(address.indexOf("/services/EASLogin?wsdl")>0){
			return  FileUtils.getInputStream("EAS_Login.xml",FileUtils.replaceWsdl(address));
		}
		InputStream is = null;
		GetMethod get = null;
		long start=System.currentTimeMillis();
		try {
			HttpClient client = new HttpClient();
			HttpClientParams params = new HttpClientParams();

			get = new GetMethod(address);
			get.setRequestHeader("Connection", "close");
			// params.setAuthenticationPreemptive(true);
			client.setParams(params);
			// 设置连接超时与请求超时
			HttpConnectionManagerParams connParams = client
					.getHttpConnectionManager().getParams();
			if (connTimeOut != null && soTimeOut != null) {
				connParams.setConnectionTimeout(connTimeOut);
				connParams.setSoTimeout(soTimeOut);
			}
			if (StringUtil.isNotNull(userName)
					&& StringUtil.isNotNull(password)) {
				Credentials credentials = new UsernamePasswordCredentials(
						userName, password);
				AuthScope scope = new AuthScope(new URL(address).getHost(),
						org.apache.commons.httpclient.auth.AuthScope.ANY_PORT,
						org.apache.commons.httpclient.auth.AuthScope.ANY_REALM);
				client.getState().setCredentials(scope, credentials);
			}

			get.setDoAuthentication(true);
			int status = client.executeMethod(get);
			if (status == 404) {
				throw new Exception("WSDL could not be found at: '" + address
						+ '\'');
			}
			boolean authenticated = status > 0 && status < 400;
			if (authenticated) {
				responseBodyString = get.getResponseBodyAsString();
				is = get.getResponseBodyAsStream();
			} else {
				throw new Exception("Could not authenticate user: '" + userName
						+ "' to WSDL: '" + address + '\'');
			}
		} catch (Exception e) {
			IOUtils.closeQuietly(is);
			logger.error("总耗时"+(System.currentTimeMillis()-start));
			logger.error("wsdl load Exception"+e);
			throw e;
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
		return is;

	}

	@Override
    public void close() {

	}

}
