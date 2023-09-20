package com.landray.kmss.tic.soap.connector.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.apache.xmlbeans.XmlException;
import com.eviware.soapui.config.EndpointsConfig;
import com.eviware.soapui.config.WsaVersionTypeConfig;
import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlUtils;
import com.eviware.soapui.support.SoapUIException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.sso.client.util.StringUtil;

/**
 *
 * wsdlproject 工厂
 * date :2012-11-13 下午10:21:08
 */
public class TicSoapProjectFactory {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicSoapProjectFactory.class);
	
	// WsdlInterface存储仓库用来缓存已经抽取过的数据
	private static ConcurrentHashMap<String, Map<String,WsdlInterface>> wsdlInterfaceStore = 
			new ConcurrentHashMap<String, Map<String,WsdlInterface>>(1);
 	
	/**
	 * 获取WsdlProject实例
	 * @return
	 * @throws XmlException
	 * @throws IOException
	 * @throws SoapUIException
	 */
	public static WsdlProject getWsdlProjectInstance() 
			throws XmlException, IOException, SoapUIException { 
		return new WsdlProject();
	}
	
	/**
	 * 获取缓存对象中的WsdlInterface数据
	 * 如果没有这个wsdl 的对象就创建，有就直接获取
	 * @param soapuiSett
	 * @param soapVersion soap版本
	 * @return
	 * @throws SoapUIException
	 * @throws XmlException
	 * @throws IOException
	 */
	public static synchronized WsdlInterface getWsdlInterfaceInstance(TicSoapSetting soapuiSett,
			String soapVersion) throws SoapUIException, XmlException, IOException{
		TimeCounter.logCurrentTime("TicSoapProjectFactory-getWsdlInterfaceInstance", true, TicSoapProjectFactory.class);
		String serviceId = soapuiSett.getFdId();
		soapVersion = StringUtils.deleteWhitespace(soapVersion);
		// 如果这个连接的数据在缓存区间内 
		if(wsdlInterfaceStore.containsKey(serviceId)){
			log.debug("从缓存中获取到WsdlInterface:"+serviceId);
			Map<String, WsdlInterface> wsdlFaces= wsdlInterfaceStore.get(serviceId);
			if (wsdlFaces.containsKey(soapVersion)) {
				log.debug("从缓存中获取到WsdlInterface:"+soapVersion);
				return wsdlFaces.get(soapVersion);
			}
		}
		WsdlInterface wsdlInterface = updateCache(soapuiSett,soapVersion);
		if(wsdlInterface!=null){
			return wsdlInterface;
		}
		TimeCounter.logCurrentTime("TicSoapProjectFactory-getWsdlInterfaceInstance", false,TicSoapProjectFactory.class);
		log.info("获取不到对应的数据");
		return null;
		
	}

	public static WsdlInterface updateCache(TicSoapSetting soapuiSett,
									 String soapVersion) throws XmlException, IOException, SoapUIException {
		log.debug("加载WSDL："+soapuiSett.getFdWsdlUrl());
		// 不在缓存区间
		WsdlInterface[] ifaces = importWsdl(soapuiSett);
		if (ifaces.length > 0) {
			WsdlInterface iface = null;
			Map<String, WsdlInterface> ifaceMap = new HashMap<String, WsdlInterface>(1);
			// 把WsdlInterface全部存入大Map
			for (WsdlInterface face : ifaces) {
				String displaySoapVersionName = face.getSoapVersion().getName();
				displaySoapVersionName = StringUtils.deleteWhitespace(displaySoapVersionName);
				ifaceMap.put(displaySoapVersionName, face);
				if (soapVersion != null
						&& displaySoapVersionName.equals(soapVersion)) {
                    iface = face;
                }
				String fdEndpoint = soapuiSett.getFdEndpoint();
				if (StringUtil.isNotNull(fdEndpoint)) {
					EndpointsConfig endpointsConfig = face.getConfig()
							.getEndpoints();
					List<String> endPointList_new = new ArrayList<String>();
					List<String> endPointList = endpointsConfig
							.getEndpointList();
					for (String endPoint : endPointList) {
						if (fdEndpoint.equals(endPoint)) {
							endPointList_new.add(endPoint);
							break;
						}
					}
					if (endPointList_new.size() > 0) {
						String[] array = new String[endPointList_new.size()];
						for (int i = 0; i < endPointList_new.size(); i++) {
							array[i] = endPointList_new.get(i);
						}
						endpointsConfig.setEndpointArray(array);
					}
				}
			}
			wsdlInterfaceStore.put(soapuiSett.getFdId(), ifaceMap);
			return iface;
		}
		return null;
	}
	
	/**
	 * 获取WsdlInterface数组
	 * @param soapuiSett
	 * @return					WsdlInterface数组
	 * @throws SoapUIException
	 * @throws XmlException
	 * @throws IOException
	 */
	public static WsdlInterface[] importWsdl(TicSoapSetting soapuiSett) 
					throws SoapUIException, XmlException, IOException{
		// 取出需要设置的参数
		String wsdlUrl = soapuiSett.getFdWsdlUrl();
		String proUsername = soapuiSett.getFdloadUser();
		String proPassword = soapuiSett.getFdloadPwd();
		String connTimeOut = soapuiSett.getFdOverTime();
		String soTimeOut = soapuiSett.getFdResponseTime();
		// 创建WsdlLoader，包含HttpClient设置

		// if(StringUtil.isNotNull(proUsername)&&StringUtil.isNotNull(proPassword)){
		// TicSoapWsdlLoader wsdlLoader = new TicSoapWsdlLoader(wsdlUrl,
		// proUsername, proPassword, connTimeOut,
		// soTimeOut);
		// return WsdlInterfaceFactory.importWsdl(getWsdlProjectInstance(),
		// wsdlUrl, false, wsdlLoader);
		// } else {
		// return WsdlInterfaceFactory.importWsdl(getWsdlProjectInstance(),
		// wsdlUrl, false);
		// }
		TicSoapWsdlLoader wsdlLoader = new TicSoapWsdlLoader(wsdlUrl,
				proUsername, proPassword, connTimeOut, soTimeOut);
		return WsdlInterfaceFactory.importWsdl(getWsdlProjectInstance(),
				wsdlUrl, false, wsdlLoader);



	}

	/**
	 * 通过wsdlUrl,operationName获取WsdlOperation
	 * @param soapuiSett		soapuiSett
	 * @param operationName		操作名称（一般为发布WEBSERVICE方法名，在模块中叫绑定函数名称）
	 * @param soapVersion		Soap版本
	 * @return					返回Operation
	 * @throws Exception
	 */
	public static WsdlOperation getWsdlOperation(TicSoapSetting soapuiSett, String operationName, 
			String soapVersion) throws Exception {
		// 实例WsdlInterface
		WsdlInterface iface = getWsdlInterfaceInstance(soapuiSett, soapVersion);
		// 找出为当前调用的WebService方法，进行获取Operation
		WsdlOperation wsdlOperation = iface.getOperationByName(operationName);
		return wsdlOperation;
	} 
	
	/**
	 * 获取WSDL请求
	 * @param soapuiSett
	 * @param operationName		操作名称（一般为发布WEBSERVICE方法名，在模块中叫绑定函数名称）
	 * @param soapVersion		Soap版本
	 * @return
	 * @throws Exception
	 */
	public static WsdlRequest getRequest(TicSoapSetting soapuiSett, String operationName, 
			String soapVersion) throws Exception{
		String wsdlUrl = soapuiSett.getFdWsdlUrl();
		// 拼串一个唯一的请求名称
		String requestName = wsdlUrl +"/"+ operationName +"/"+ soapVersion;
		// 添加一个请求
		WsdlOperation wsdlOperation = getWsdlOperation(soapuiSett, operationName, soapVersion);
		//WsdlRequest wsdlRequest = wsdlOperation.getRequestByName(requestName);
		//if (wsdlRequest == null) {
		// WsdlRequest wsdlRequest = wsdlOperation.addNewRequest(requestName);
		//}
		WsdlRequest wsdlRequest = getNewRequest(requestName, wsdlOperation);
		return wsdlRequest;
	}
	
	public static WsdlRequest getNewRequest(String name,
			WsdlOperation wsdlOperation) {
		WsdlRequest requestImpl = new WsdlRequest(wsdlOperation,
				wsdlOperation.getConfig().addNewCall());
		requestImpl.setName(name);

		if (!wsdlOperation.getInterface().getWsaVersion()
				.equals(WsaVersionTypeConfig.NONE.toString())) {
			requestImpl.setWsAddressing(true);
		}
		WsdlUtils.setDefaultWsaAction(requestImpl.getWsaConfig(), false);
		WsdlUtils.getAnonymous(wsdlOperation);

		(wsdlOperation.getInterface()).fireRequestAdded(requestImpl);
		return requestImpl;
	}

	/**
	 * 通过服务ID清除缓存
	 * @param serviceId
	 */
	public static void cleanCacheByServiceId(String serviceId){
		if(StringUtils.isEmpty(serviceId)){
			return ;
		}
		if (wsdlInterfaceStore.containsKey(serviceId)) {
			//wsdlInterfaceStore.remove(serviceId);
			ITicSoapSettingService ticSoapSettingService = (ITicSoapSettingService) SpringBeanUtil.getBean("ticSoapSettingService");
			if(ticSoapSettingService==null){
				log.error("获取不到ticSoapSettingService");
				return;
			}
			try {
				TicSoapSetting setting = (TicSoapSetting)ticSoapSettingService.findByPrimaryKey(serviceId);
				String soapVersion = setting.getFdSoapVerson();
				if(soapVersion!=null && soapVersion.contains(";")){
					String[] versions = soapVersion.split(";");
					for(String version:versions){
						updateCache(setting, version);
					}
				}else{
					updateCache(setting, soapVersion);
				}
			}catch (Exception e){
				log.error(e.getMessage(),e);
			}
		}
	}
	
	
	
	

	
	
	
}
