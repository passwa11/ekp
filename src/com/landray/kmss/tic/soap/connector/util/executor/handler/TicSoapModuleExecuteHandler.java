package com.landray.kmss.tic.soap.connector.util.executor.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.util.executor.intereptor.AbstractInterceptor;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.executor.vo.TicSoapModuleRtn;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.util.StringUtil;

/**
 * 
 * @author zhangtian
 * soap执行的控制
 *
 */
public class TicSoapModuleExecuteHandler implements ITicSoapExecuteHandler  {
	
	private WsdlRequest wsdlRequest;
	
	private SubmitContext context;
	
	//前置拦截器集合,仿照cxf的拦截功能
	private List<AbstractInterceptor> beforeInterceptors=new ArrayList<AbstractInterceptor>(1); 
	
	//后置拦截器集合,仿照cxf的拦截功能
	private List<AbstractInterceptor> afterInterceptors=new ArrayList<AbstractInterceptor>(1); 
	
	public TicSoapModuleExecuteHandler(SoapInfo soapInfo) {
		super();
		this.soapInfo = soapInfo;
	}
	private SoapInfo soapInfo;
	
	public SoapInfo getSoapInfo() {
		return soapInfo;
	}
	
	
	
	private Document getSourceData() throws Exception{
		
		Object source=soapInfo.getSource();
		if (source != null) {
			if (source instanceof Document) {
				return (Document) source;
			} else if (source instanceof String) {
				Document doc = DOMHelper.parseXmlString((String) source);
				return doc;
			} else {

			}
		}
		return null;
	}
	
	public Document getPostData() throws Exception{
		
		Document document =getSourceData();
		Node postNode= HeaderOperation.allToPartNode(document, "//Input");	
		
		Document doc=DOMHelper.Node2Document(postNode);
		return doc;
	}

	public void setSoapInfo(SoapInfo soapInfo) {
		this.soapInfo = soapInfo;
	}

	@Override
    public Document afterExecute(SubmitContext submitContext,
                                 WsdlRequest wsdlRequest, Response response, Document document) {
		// TODO 自动生成的方法存根
		// 数据排序,order越大,排序越前
		Collections.sort(afterInterceptors, new Comparator<AbstractInterceptor>() {
			@Override
            public int compare(AbstractInterceptor o1, AbstractInterceptor o2) {
				// TODO 自动生成的方法存根
				return o1.getOrder()-o2.getOrder();
			}
		});
		
//		执行拦截器后置操作
		for(AbstractInterceptor interceptor :afterInterceptors){
			try{
			interceptor.handlerMessage(submitContext, wsdlRequest,document);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return document;
	}

	@Override
    public void beforeExecute(SubmitContext submitContext,
                              WsdlRequest wsdlRequest, Document data) throws Exception {
		// 数据排序,order越大,排序越前
		Collections.sort(beforeInterceptors, new Comparator<AbstractInterceptor>() {
			@Override
            public int compare(AbstractInterceptor o1, AbstractInterceptor o2) {
				// TODO 自动生成的方法存根
				return o1.getOrder()-o2.getOrder();
			}
		});
//		执行拦截器前置操作
		for(AbstractInterceptor interceptor :beforeInterceptors){
			interceptor.handlerMessage(submitContext, wsdlRequest,data);
		}
		if(StringUtil.isNull(wsdlRequest.getRequestContent())){
			if(data!=null){
				String postData=DOMHelper.nodeToString(data, true);
				wsdlRequest.setRequestContent(postData);
			}
			else{
				wsdlRequest.setRequestContent("");
			}
		}
	}

	/**
	 * 提供给getInitWsdlRequest使用，因为getInitWsdlRequest已经重载,所以不需要
	 */
	@Override
    @Deprecated
	public WsdlOperation getExecuteOperation() throws Exception {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
    public SubmitContext getInitSubmitContext() {
		context = new WsdlSubmitContext(getWsdlRequest());
		return context;
	}

	private void initRequestPwd(TicSoapSetting soapuiSet){
		// 是否设置加密方式
		if (StringUtil.isNotNull(soapuiSet.getPasswordType())) {
			wsdlRequest.setWssPasswordType(soapuiSet.getPasswordType());
		}
		// 加入受HTTP保护的验证信息
		if (soapuiSet.getFdProtectWsdl()) {
			wsdlRequest.setUsername(soapuiSet.getFdloadUser());
			wsdlRequest.setPassword(soapuiSet.getFdloadPwd());
		}
		// 判断何种登录方式，根据登录方式赋值给予不同Soap消息头信息
		if (soapuiSet.getFdCheck()) {
			// 设置用户名密码
			if (!soapuiSet.getFdProtectWsdl()) {
				wsdlRequest.setUsername(soapuiSet.getFdUserName());
				wsdlRequest.setPassword(soapuiSet.getFdPassword());
			}
	}
	}
	
	@Override
    public WsdlRequest getInitWsdlRequest() throws Exception {
		
		//获取request的必要数据
		TicSoapMain main = soapInfo.getTicSoapMain();
		String operationName = main.getWsBindFunc();
		String soapVersion = main.getWsSoapVersion();
		TicSoapSetting soapuiSet = main.getTicSoapSetting();
		
		
		// 创建请求
		wsdlRequest = TicSoapProjectFactory.getRequest(soapuiSet,
				operationName, soapVersion);
		
		//初始化request的密码,用户名;
		initRequestPwd(soapuiSet);
		
		return wsdlRequest;
	}

	/**
	 * 提供给getInitWsdlRequest使用，因为getInitWsdlRequest已经重载,所以不需要
	 */
	@Override
    @Deprecated
	public WsdlInterface getTargetWsdlFace() throws Exception {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
    public ITicSoapRtn parseResponse(Response response, Document doc) {
		// TODO 自动生成的方法存根
		
		TicSoapModuleRtn ticSoapModuleRtn= new TicSoapModuleRtn(response.getContentAsString(), response, doc);
		
		return ticSoapModuleRtn;
	}



	public WsdlRequest getWsdlRequest() {
		return wsdlRequest;
	}
	public void setWsdlRequest(WsdlRequest wsdlRequest) {
		this.wsdlRequest = wsdlRequest;
	}



	public SubmitContext getContext() {
		return context;
	}
	
	public void setContext(SubmitContext context) {
		this.context = context;
	}



	public List<AbstractInterceptor> getBeforeInterceptors() {
		return beforeInterceptors;
	}



	public void setBeforeInterceptors(List<AbstractInterceptor> beforeInterceptors) {
		this.beforeInterceptors = beforeInterceptors;
	}



	public List<AbstractInterceptor> getAfterInterceptors() {
		return afterInterceptors;
	}



	public void setAfterInterceptors(List<AbstractInterceptor> afterInterceptors) {
		this.afterInterceptors = afterInterceptors;
	}







	
}
