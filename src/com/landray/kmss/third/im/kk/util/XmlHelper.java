package com.landray.kmss.third.im.kk.util;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

/**
 * xml解析工具，专用来使用betwixt工具
 * @author zhangtian
 *
 */
public class XmlHelper {

	public static String parseBeanToXml(Object bean) throws IOException,
			SAXException, IntrospectionException {
		StringWriter stringWriter = new StringWriter();
		BeanWriter beanWriter = new BeanWriter(stringWriter);
		beanWriter
				.writeXmlDeclaration("<?xml version='1.0' encoding='UTF-8'?>");
		beanWriter.getBindingConfiguration().setMapIDs(false);
		beanWriter.setWriteEmptyElements(false);
		beanWriter.getXMLIntrospector().getConfiguration()
				.setWrapCollectionsInElement(true);
		beanWriter.enablePrettyPrint();
		beanWriter.write(bean);
		return stringWriter.toString();
	}
	
	public static Object XmlToBean(String str,Class clazz,String rootName) throws IntrospectionException, IOException, SAXException{
		StringReader xmlReader = new StringReader(str);
		BeanReader beanReader = new BeanReader();
		beanReader.getXMLIntrospector().getConfiguration()
				.setAttributesForPrimitives(true);
		beanReader.getBindingConfiguration().setMapIDs(false);
		beanReader.registerBeanClass(rootName, clazz);
		Object obj = beanReader.parse(xmlReader);
		return obj;
		
	}
	

}
