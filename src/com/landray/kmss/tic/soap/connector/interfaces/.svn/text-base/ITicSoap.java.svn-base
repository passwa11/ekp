/**
 * 
 */
package com.landray.kmss.tic.soap.connector.interfaces;

import java.util.Map;

import org.w3c.dom.Document;

import com.eviware.soapui.model.iface.Operation;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;

/**
 * @author 邱建华
 * @version 1.0 2012-11-2
 */
public interface ITicSoap {
	
	/**
	 * 通过传入WSDL地址，获取Input传入参数的XML
	 * @param soapuiSett		当前的服务配置
	 * @param soapVersion		SOAP版本(注意中间空格)，比如SOAP 1.1
	 * @return					返回的input输入参数
	 */
	public String toDefaultInputXml(TicSoapSetting soapuiSett, String soapVersion) throws Exception;
	
	/**
	 * 通过传入WSDL地址，获取Input传入参数的XML
	 * @param wsdlUrl			WSDL地址
	 * @param operationName		当前WebService操作名称即绑定函数
	 * @param soapVersion		SOAP版本(注意中间空格)，比如SOAP 1.1
	 * @return					返回的input输入参数
	 */
	public String toInputXml(TicSoapSetting soapuiSett, String operationName, String soapVersion) throws Exception;
	
	/**
	 * 通过传入input参数的XML，获取output输出参数的XML
	 * @param soapInfo			包含soap所有信息，包括请求XML和TicSoapMain对象
	 * 							其中SOAP版本(注意中间空格)，比如SOAP 1.1
	 * @return					返回output响应的XML
	 */
	public String inputToOutputXml(SoapInfo soapInfo) throws Exception;
	
	public String funcNameAndContentToOutput(String Func_Name, 
			String Request_Content) throws Exception;
	
	/**
	 * 通过传入input参数的XML，获取包括input和output输出参数的XML
	 * @param soapInfo			包含soap所有信息，包括请求XML和TicSoapMain对象
	 * 							其中SOAP版本(注意中间空格)，比如SOAP 1.1
	 * @return					返回包括input和output的XML
	 */
	public String inputToAllXml(SoapInfo soapInfo) throws Exception;
	
	/**
	 * 通过传入WSDL地址，获取包括input和output输出参数的XML模版
	 * @param soapuiSett		当前的服务配置
	 * @param operationName		当前WebService操作名称即绑定函数
	 * @param soapVersion		SOAP版本(注意中间空格)，比如SOAP 1.1
	 * @return					返回input和output输出参数的XML模版
	 */
	public String toAllXmlTemplate(TicSoapSetting soapuiSett, String operationName,
			String soapVersion) throws Exception;
	
	/**
	 * 通过WSDL和SOAP版本获取全部的Operation
	 * @param soapuiSett		当前的服务配置
	 * @param soapVersion	Soap版本
	 * @return
	 * @throws Exception
	 */
	public Map<String, Operation> getAllOperation(TicSoapSetting soapuiSett, String soapVersion) throws Exception;;
	
	
	public ITicSoapRtn inputToOutputRtn(SoapInfo soapInfo)
			throws Exception ;
	
	/**
	 * 获取ITicSoapRtn实例时传入响应超时时间和连接超时参数
	 * @param soapInfo
	 * @param responseTime
	 * @param connectTime
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年12月13日
	 */
	public ITicSoapRtn inputToOutputRtn(SoapInfo soapInfo,String responseTime,String connectTime)
			throws Exception ;
	
	public Document inputToOutputDocument(SoapInfo soapInfo)
			throws Exception ;
	
	
}
