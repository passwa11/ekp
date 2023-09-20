package com.landray.kmss.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.landray.kmss.util.StringUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class XMLUtil {

	/**
	 * 创建文档
	 * 
	 * @return
	 */
	public static Document createDocument() {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		return document;
	}

	/**
	 * 解析xml文档
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document readXML(String xml) throws Exception {
		StringReader in = new StringReader(xml);
		SAXReader reader = new SAXReader();
		return reader.read(in);
	}

	/**
	 * 创建节点
	 * 
	 * @param parent
	 * @param name
	 * @param text
	 */
	public static void createNode(Branch parent, String name, Object text) {
		if (parent == null) {
            return;
        }

		if (text == null) {
            text = "";
        }

		parent.addElement(name).setText(text.toString());
	}

	/**
	 * 创建节点
	 * 
	 * @param parent
	 * @param name
	 * @param text
	 */
	public static void createNodeCDATA(Branch parent, String name, Object text) {
		if (parent == null) {
            return;
        }

		if (text == null) {
            text = "";
        }

		parent.addElement(name).addCDATA(text.toString());
	}

	/**
	 * 获取XML节点内容
	 * 
	 * @param parent
	 * @param node
	 * @return
	 */
	public static String getNodeText(Element parent, String node) {
		if (parent == null) {
			return null;
		}

		if (StringUtil.isNull(node)) {
			return parent.getText();
		}

		Element child = parent.element(node);
		if (child == null) {
			return null;
		}

		return child.getText();
	}

	/**
	 * XML转换为String
	 * 
	 * @param node
	 * @param formater
	 * @return
	 * @throws Exception
	 */
	public static String printXML(Node node, boolean formater) throws Exception {
		StringWriter out = new StringWriter();
		if (formater) {
			OutputFormat format = new OutputFormat();
			// format.setEncoding("UTF-8");
			format.setNewlines(true);
			// format.setXHTML(true);
			format.setIndent(true);
			format.setIndent("\t");

			XMLWriter writer = new XMLWriter(out, format);
			writer.write(node);
		} else {
			node.write(out);
		}
		return out.toString();
	}

	/**
	 * Base64 编码字符串
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String base64Encode(String xml) throws Exception {
		if (StringUtil.isNotNull(xml)) {
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(xml.getBytes("UTF-8"));
		}
		return null;
	}

	/**
	 * Base64 解码字符串
	 * 
	 * @param base64Str
	 * @return
	 * @throws Exception
	 */
	public static String base64Decode(String base64Str) throws Exception {
		if (StringUtil.isNotNull(base64Str)) {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodeBuffer = decoder.decodeBuffer(base64Str);
			return new String(decodeBuffer, "UTF-8");
		}
		return null;
	}

	/**
	 * XML 转换为JSON格式
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static JSONObject convertXML2JSON(String xml) throws Exception {
		Document doc = readXML(xml);
		return (JSONObject) parseChildElement(doc.getRootElement());
	}

	private static Object parseChildElement(Element node) {
		List<?> childList = node.elements();
		if (childList.isEmpty()) {
			return node.getText();
		}

		if (isArray(node)) {
			JSONArray json = new JSONArray();
			for (Object childObject : childList) {
				Element child = (Element) childObject;

				json.add(parseChildElement(child));
			}
			return json;
		} else {
			JSONObject json = new JSONObject();
			for (Object childObject : childList) {
				Element child = (Element) childObject;
				String key = child.getName();

				if (node.elements(key).size() == 1) {
					json.put(key, parseChildElement(child));
				} else {
					JSONArray ja = json.optJSONArray(key);
					if (ja == null) {
						ja = new JSONArray();
					}
					ja.add(child.getText());
					json.put(key, ja);
				}

			}
			return json;
		}
	}

	private static boolean isArray(Element node) {
		Iterator<?> ite = node.elementIterator();
		String key = ((Element) ite.next()).getName();
		while (ite.hasNext()) {
			Element nextNode = (Element) ite.next();
			if (!key.equalsIgnoreCase(nextNode.getName())) {
				return false;
			}
		}
		return true;
	}

}
