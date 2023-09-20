package com.landray.kmss.tic.soap.mapping.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;
import com.landray.kmss.tic.core.util.DomUtil;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class TicSoapMappingFuncXmlService implements IXMLDataBean {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicSoapMappingFuncXmlService.class);

	private ITicSoapMainService ticSoapMainService;

	private ITicCoreMappingFuncService ticCoreMappingFuncService;

	public Node findCommentNode(Node curNode) {
		if (curNode != null) {
			Node preNode = curNode.getPreviousSibling();
			// 上一个节点就是尽头
			if (preNode == null) {
				return null;
			} else if (preNode.getNodeType() == Node.ELEMENT_NODE) {
				return null;
			} else if (preNode.getNodeType() == Node.COMMENT_NODE) {
				return preNode;
			} else {
				return findCommentNode(preNode);
			}
		}
		return null;
	}

	public void updateMappingInfo(NodeList nodeList, String xpath_str,
			Document document) throws Exception {

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			// 如果是element 才处理循环
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				// 获取comment 节点信息
				Element element = (Element) node;
				element.getTagName();
				Node comment = findCommentNode(node);
				String nodeName = node.getNodeName();
				if (nodeName.indexOf(":") > -1) {
					nodeName = nodeName.substring(nodeName.indexOf(":") + 1);
				}
				if (comment != null) {
					String textContent = comment.getTextContent();
					XPath xpath = XPathFactory.newInstance().newXPath();
					Node node_tmp = (Node) xpath.evaluate(xpath_str + "/"
							+ nodeName, document, XPathConstants.NODE);

					Node comment_tmp = findCommentNode(node_tmp);
					if (comment_tmp != null) {
						comment_tmp.setTextContent(textContent);
					}
				}
				NodeList n_list = node.getChildNodes();

				updateMappingInfo(n_list, xpath_str + "/" + nodeName, document);
			}
		}
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String fdSoapMainId = requestInfo.getParameter("fdSoapMainId");
		String oldRefId = requestInfo.getParameter("oldRefId");
		String mappingFuncId = requestInfo.getParameter("mappingFuncId");
		if (StringUtil.isNull(fdSoapMainId)) {
			return rtnList;
		}

		try {
			TicSoapMain ticSoapMain = (TicSoapMain) ticSoapMainService
					.findByPrimaryKey(fdSoapMainId);
			String funcXml = ticSoapMain.getWsMapperTemplate();
			// 移除禁用的节点
			funcXml = ParseSoapXmlUtil.disableFilter(funcXml);
			Map<String, String> map = new HashMap<String, String>(1);
			if (fdSoapMainId.equals(oldRefId)) {
				TicCoreMappingFunc ticCoreMappingFunc = (TicCoreMappingFunc) ticCoreMappingFuncService
						.findByPrimaryKey(mappingFuncId);
				String rfcParamXml = ticCoreMappingFunc.getFdRfcParamXml();
				Document document = DomUtil.stringToDoc(funcXml);
				updateMappingInfo(DomUtil.stringToDoc(rfcParamXml)
						.getChildNodes(), "", document);
				funcXml = DomUtil.DocToString(document);
			}
			map.put("funcXml", funcXml);
			Map<String, String> map2 = new HashMap<String, String>(1);
			map2.put("MSG", "SUCCESS");
			rtnList.add(map);
			rtnList.add(map2);
			return rtnList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			Map<String, String> map = new HashMap<String, String>(1);
			map.put("funcXml", "");
			Map<String, String> map2 = new HashMap<String, String>(1);
			map2.put("MSG", ResourceUtil.getString(
					"erpSoapuiMain.dataException", "tic-soap"));
			rtnList.add(map);
			rtnList.add(map2);
		}
		return rtnList;

	}

	public ITicSoapMainService getticSoapMainService() {
		return ticSoapMainService;
	}

	public void setticSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

	public void setTicCoreMappingFuncService(
			ITicCoreMappingFuncService ticCoreMappingFuncService) {
		this.ticCoreMappingFuncService = ticCoreMappingFuncService;
	}

	public ITicCoreMappingFuncService getTicCoreMappingFuncService() {
		return ticCoreMappingFuncService;
	}

}
