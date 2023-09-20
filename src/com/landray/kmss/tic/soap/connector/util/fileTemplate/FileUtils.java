package com.landray.kmss.tic.soap.connector.util.fileTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;

import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.util.StringUtil;

public class FileUtils {

	//读取外部文件
	public static InputStream getInputStream(String fileName,String address) throws Exception{
		InputStream is=null;
		try {
			is =FileUtils.class.getResourceAsStream(fileName);
			SAXReader reader = new SAXReader();
			org.dom4j.Document document = reader.read(is);		
			//获取根节点
			Element root = document.getRootElement();
			String rootStr = root.asXML();
			if(StringUtil.isNotNull(address) && StringUtil.isNotNull(rootStr)){
				rootStr=rootStr.replace("$address$",address);
			}
			InputStream in = new ByteArrayInputStream(rootStr.getBytes());
			return in;
		} catch (Exception e) {
			IOUtils.closeQuietly(is);
			throw e;
		}
	}


	//读取外部文件
	public static Document getDocument(String fileName,Map<String,String> dataMap) throws Exception{
		InputStream is=null;
		try {
			is =FileUtils.class.getResourceAsStream(fileName);
			SAXReader reader = new SAXReader();
			org.dom4j.Document document = reader.read(is);		
			//获取根节点
			Element root = document.getRootElement();
			String rootStr = root.asXML();
			if(StringUtil.isNotNull(rootStr)){
				if(dataMap!=null && !dataMap.isEmpty()){
					for(String key:dataMap.keySet()){
						String value=dataMap.get(key);
						if(StringUtil.isNotNull(value)){
							rootStr=rootStr.replace(key,value);
						}
					}
				}
			}
			return DOMHelper.parseXmlString(rootStr);
		} catch (Exception e) {
			throw e;
		}finally{
			IOUtils.closeQuietly(is);
		}
	}


	//去掉wsdl后面的占位符
	public static String replaceWsdl(String address){
		int index =address.lastIndexOf("?");
		if(index>0){
			return address.substring(0, index);
		}
		return address; 
	}

	//http://192.168.234.130:6888/ormrpc/services/EASLogin
	public static void main(String[] args) throws Exception{
		//getInputStream("EAS.xml","http://192.168.234.130:6888/ormrpc");
		System.out.println(replaceWsdl("http://192.168.234.130:6888/ormrpc/services/EASLogin?wsdl"));
	}
}