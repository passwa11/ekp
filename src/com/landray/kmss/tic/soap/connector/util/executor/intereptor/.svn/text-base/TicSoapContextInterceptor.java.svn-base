package com.landray.kmss.tic.soap.connector.util.executor.intereptor;

import org.w3c.dom.Document;

import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.model.iface.SubmitContext;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;

public class TicSoapContextInterceptor extends AbstractInterceptor {
	private TicSoapSetting soapuiSet;

	private TicSoapMain ticSoapMain;
	private String springName;
	private String className;
	
	public TicSoapContextInterceptor(TicSoapSetting soapuiSet,TicSoapMain ticSoapMain,String springName,String className, int order) {
		super(order);
		// TODO 自动生成的构造函数存根
		this.soapuiSet=soapuiSet;
		this.ticSoapMain=ticSoapMain;
		this.springName=springName;
		this.className=className;
		
	}

	@Override
	public void handlerMessage(SubmitContext submitContext,
			WsdlRequest wsdlRequest, Document data) throws Exception {
		// TODO 自动生成的方法存根
		HeaderOperation.setAuthContext(submitContext, wsdlRequest, soapuiSet,ticSoapMain, springName, className,data);
	}

	public TicSoapSetting getSoapuiSet() {
		return soapuiSet;
	}

	public void setSoapuiSet(TicSoapSetting soapuiSet) {
		this.soapuiSet = soapuiSet;
	}

	public TicSoapMain getTicSoapMain() {
		return ticSoapMain;
	}

	public void setTicSoapMain(TicSoapMain ticSoapMain) {
		this.ticSoapMain = ticSoapMain;
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
