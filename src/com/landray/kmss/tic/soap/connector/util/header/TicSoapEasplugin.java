package com.landray.kmss.tic.soap.connector.util.header;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;
import org.w3c.dom.Document;

import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.support.types.StringToStringsMap;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.util.executor.SoapExecutor;
import com.landray.kmss.tic.soap.connector.util.executor.handler.TicSoapEasHandler;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.executor.vo.TicSoapEasRtn;
import com.landray.kmss.tic.soap.connector.util.fileTemplate.FileUtils;
import com.landray.kmss.util.StringUtil;

public class TicSoapEasplugin extends ISoapHeaderType {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public String buildAuthContext(SubmitContext context, WsdlRequest request,
			TicSoapSetting soapuiSet,TicSoapMain ticSoapMain,Document data) throws Exception {
		TicSoapEasRtn rtn=(TicSoapEasRtn)executeEAS(soapuiSet);
		if("凭证写入".equals(ticSoapMain.getFdName())){
			//设置sessionID by hejianhua
			String sessionId=rtn.getSessionId();
			//设置Header头
			//StringToStringsMap map=new StringToStringsMap();
			//map.put("sessionId",sessionId);
			Map<String,String> dataMap=new HashMap<String,String>();
			dataMap.put("$address$", FileUtils.replaceWsdl(soapuiSet.getFdWsdlUrl()));
			dataMap.put("$sessionId$", sessionId);
		    //获取真实的模板	
			Document docTemplate=FileUtils.getDocument("EAS_template.xml",dataMap);
	        //组装最新的报文模板
			DOMHelper.setValue(data,docTemplate);
			data=docTemplate;
			//header里面设置登陆标志sessionId
			//request.setRequestHeaders(map);
			//返回真实的请求报文
			request.setRequestContent(DOMHelper.nodeToString(docTemplate, true));
	        return DOMHelper.nodeToString(docTemplate, true);
		}
		return super.buildAuthContext(context, request, soapuiSet,ticSoapMain,data);
		
  }

	private ITicSoapRtn executeEAS(TicSoapSetting soapuiSet)
			throws Exception {

		if (logger.isWarnEnabled()) {
			logger.warn("执行webservice EAS 扩展~!");
		}
		String userName = null;
		String password = null;
		String wsdl = null;
		String soapVersion = null;
		String opernateName = null;
		Map easMap = null;
		wsdl = findEasWsdl(soapuiSet.getFdWsdlUrl());
		if (StringUtil.isNull(wsdl)) {
			if (logger.isWarnEnabled()) {
				logger.warn("webservice 地址为空");
			}
			return null;
		}
		// 获取扩展参数信息
		easMap = soapuiSet.getExtendInfoMap();
		// 受保护
		if (soapuiSet.getFdProtectWsdl()) {
			userName = soapuiSet.getFdloadUser();
			password = soapuiSet.getFdloadPwd();
		}
		soapVersion = soapuiSet.getFdSoapVerson();
		// eas 登录方法名
		opernateName = "login";
		if (logger.isWarnEnabled()) {
			logger.warn("初始化EAS数据成功~!wsdl:" + wsdl);
		}
		TicSoapEasHandler easHandler = new TicSoapEasHandler(userName,
				password, wsdl, soapVersion, opernateName, easMap);
		SoapExecutor executor = new SoapExecutor(easHandler, easHandler
				.getPostData());
		ITicSoapRtn rtn = executor.executeSoapui();
		if (ITicSoapRtn.ERP_SOAPUI_EAR_TYPE_SUCCESS.equals(rtn.getRtnType())) {
			return rtn;
		} else {
			// 用抛出异常了让外面的方法停止
			throw new Exception("EAS 登录出现异常,登录异常返回信息：\n"
					+ HtmlUtils.htmlEscape(rtn.getRtnContent()));
		}
	}

	private String findEasWsdl(String sourceWsdl) {
		int lastIndex = sourceWsdl.lastIndexOf("/");
		String realWsdl = null;
		if (lastIndex > -1) {
			realWsdl = sourceWsdl.substring(0, lastIndex + 1) + "EASLogin?wsdl";
		}
		return realWsdl;
	}

}
