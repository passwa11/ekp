package com.landray.kmss.tic.soap.connector.util.executor.helper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;
import com.landray.kmss.tic.soap.connector.impl.TicSoapWsdlLoader;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.util.StringUtil;


public class SoapHelper {
	
	
	public static WsdlInterface[] getWsdlInterfacesNoCache(String url,String soapVersion,String requestUser,String passwrod) throws Exception{
		if(StringUtil.isNotNull(requestUser)&&StringUtil.isNotNull(passwrod)){
			TicSoapWsdlLoader wsdlLoader = new TicSoapWsdlLoader(url, requestUser, passwrod, "10000", 
					"10000");
			return WsdlInterfaceFactory.importWsdl(TicSoapProjectFactory.getWsdlProjectInstance(),
					url, false, wsdlLoader);
		}
		else{
			return WsdlInterfaceFactory.importWsdl(TicSoapProjectFactory.getWsdlProjectInstance(), url, false);
		}
	}
	
	public static WsdlInterface getWsdlInterfaceNoCache(String url,String soapVersion,String opernateName,String requestUser,String passwrod) throws Exception{
		WsdlInterface[]  ifaces=getWsdlInterfacesNoCache(url, soapVersion, requestUser, passwrod);
		WsdlInterface iface = null;
		if (ifaces.length > 0) {
			Map<String, WsdlInterface> ifaceMap = new HashMap<String, WsdlInterface>(1);
			// 把WsdlInterface全部存入大Map
			for (WsdlInterface face : ifaces) {
				String displaySoapVersionName = face.getSoapVersion().getName();
				displaySoapVersionName = StringUtils.deleteWhitespace(displaySoapVersionName);
				ifaceMap.put(displaySoapVersionName, face);
				if (soapVersion != null
						&& displaySoapVersionName.equals(soapVersion)){
					iface = face;
					return iface;
				}
			}
		}
		return iface;
	} 
	
	public static WsdlInterface getWsdlInterfaceInstanceCache(TicSoapSetting soapuiSett,String soapVersion) throws Exception{
		WsdlInterface  wsdlInterface = TicSoapProjectFactory.getWsdlInterfaceInstance(soapuiSett, soapVersion);
	    return wsdlInterface;
	}
	
	public  WsdlOperation getWsdlOperation(WsdlInterface face,String operationName){
		WsdlOperation wsdlOperation=null;
		if(face!=null){
			wsdlOperation = face.getOperationByName(operationName);
		}
		return wsdlOperation;
	}
	
	
	
	
	
	
	

}
