package com.landray.kmss.sys.zone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.zone.dict.SysZonePhotoMap;

public class JAXBUtil {
	/**
	 * 
	 * @param reader
	 *            StringReader
	 * @param clazz
	 *            转换的bean class
	 * @return 对应class 的bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(StringReader reader, Class<T> clazz) {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			T Object = (T) u.unmarshal(reader);
			return Object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过xml 流转换成bean
	 * 
	 * @param is
	 *            xml 流
	 * @param clazz
	 *            转换的bean class
	 * @return 对应class 的bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(InputStream is, Class<T> clazz) {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();
			T Object = (T) u.unmarshal(is);
			return Object;
		} catch (Exception e) {
			// throw new RuntimeException(e);
			System.out.println("xml 转 对象失败 ");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对象转xml
	 * 
	 * @param obj
	 *            对象
	 * @param clazz
	 *            转换的bean class
	 * @return StringWriter sr
	 */
	@SuppressWarnings("unchecked")
	public static StringWriter marshaller(Object obj, Class clazz) {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			// 用于输出元素
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter strWriter = new StringWriter();
			StringBuffer stringBuffer = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			strWriter.append(stringBuffer);
			marshaller.marshal(obj, strWriter);
			return strWriter;
		} catch (Exception e) {
			System.out.println("xml 转 对象失败 ");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		try {
			String path = ConfigLocationsUtil.getWebContentPath();
			String _path = "D:/java/workspace_n/kms/WebContent/sys/zone/sys_zone_photo_template/template.xml";
			FileInputStream is = new FileInputStream(new  File(_path));
			unmarshal(is, SysZonePhotoMap.class);
		}catch(Exception e) { 
			e.printStackTrace();
		}
	}
}
