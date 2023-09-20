package com.landray.kmss.tic.soap.connector.util.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.util.executor.handler.ITicSoapExecuteHandler;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.util.StringUtil;

/**
 * soapui 执行webservice执行器
 * 
 * @author zhangtian
 * date :2013-1-14 上午11:30:44
 */
public abstract class AbstractSoapExecutor {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractSoapExecutor.class);
	
	//控制执行过程中的前置，后置，request 设置控制动作类
	private ITicSoapExecuteHandler ticSoapExecuteHandler;
	
	private Document postData; 
	
	private String responseTime;
	private String connectTime;
	
	public AbstractSoapExecutor(ITicSoapExecuteHandler TicSoapExecuteHandler,Document postData){
		this.ticSoapExecuteHandler=TicSoapExecuteHandler;
		this.postData=postData;
	}
	
	public AbstractSoapExecutor(ITicSoapExecuteHandler TicSoapExecuteHandler,Document postData,String responseTime,String connectTime){
		this.ticSoapExecuteHandler = TicSoapExecuteHandler;
		this.postData = postData;
		this.responseTime = responseTime;
		this.connectTime = connectTime;
	}
	
	private Response executeSoapuiCore(SubmitContext context, WsdlRequest request) throws SubmitException{
		long timeout = 5000;
		if(StringUtil.isNotNull(responseTime))
		{
			timeout = Long.parseLong(responseTime);
		}
		request.setTimeout(timeout+"");

		if(logger.isDebugEnabled()){
			logger.debug("请求报文:"+request.getRequestContent());
		}
		Long start = System.currentTimeMillis();
		WsdlSubmit<WsdlRequest> submit  = (WsdlSubmit<WsdlRequest>) request
					.submit(context, false);
		Response response = submit.getResponse();
		Long end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("响应报文:"+response.getContentAsString());
		}
		if(response==null || response.getContentAsString()==null){
			if((end-start)>timeout){
				throw new RuntimeException("响应报文为空，可能是请求响应超时了，请检查配置的“响应超时”时间");
			}
		}
		return response;
	}
	
	/**
	 * 执行webservice 
	 * @return
	 * @throws Exception
	 */
	public ITicSoapRtn executeSoapui() throws Exception{
		
		//执行webservice之前初始化WsdlRequest
		WsdlRequest wsdlRequest= ticSoapExecuteHandler.getInitWsdlRequest();
		
		//执行webservice之前初始化submitContext
		SubmitContext submitContext =ticSoapExecuteHandler.getInitSubmitContext();
		
		
		//前置操作
		ticSoapExecuteHandler.beforeExecute(submitContext, wsdlRequest,postData);
		// huangwq 20190115 修复wsdlRequest中的内容有缓存
		if(StringUtil.isNull(wsdlRequest.getRequestContent())){
			wsdlRequest.setRequestContent(DOMHelper.nodeToString(postData));
		}

		String requestContent = wsdlRequest.getRequestContent();
		if(StringUtil.isNotNull(requestContent)){
			requestContent = requestContent.replaceAll("(?s)<\\!\\-\\-.+?\\-\\->", "");
			wsdlRequest.setRequestContent(requestContent);
			if(logger.isDebugEnabled()){
				logger.debug("请求报文："+wsdlRequest.getRequestContent());
			}
		}

		//执行webservice
		Response response=executeSoapuiCore(submitContext, wsdlRequest);
		
		//原始返回值
		String content=response.getContentAsString();
		if(logger.isDebugEnabled()){
			logger.debug("响应报文："+content);
		}

		Document document=null;
		//转化为dom
		if(StringUtil.isNotNull(content)){
			document=DOMHelper.parseXmlString(content);
		}
		//处理后续返回值
		Document  doc =ticSoapExecuteHandler.afterExecute(submitContext, wsdlRequest, response,document);
		
		//返回值转换
		ITicSoapRtn  rtnObject=ticSoapExecuteHandler.parseResponse(response,doc);
		
		return rtnObject;
		
	}

	public Document getPostData() {
		return postData;
	}

	public void setPostData(Document postData) {
		this.postData = postData;
	}

	public ITicSoapExecuteHandler getTicSoapExecuteHandler() {
		return ticSoapExecuteHandler;
	}

	public void setTicSoapExecuteHandler(
			ITicSoapExecuteHandler ticSoapExecuteHandler) {
		this.ticSoapExecuteHandler = ticSoapExecuteHandler;
	}

	
	
	
	
	
	
	

}
