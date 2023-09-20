package com.landray.kmss.tic.soap.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.xform.base.service.controls.relation.RelationOuterSearchParams;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.middleware.executor.ITicDispatcherExecutor;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.core.util.DomUtil;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.core.util.XmlConvertToJsonUtil;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.executor.vo.ITicSoapRtn;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.util.SpringBeanUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.slf4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author 严海星 2018年9月28日
 */
public class SoapDispatcherExecutor implements ITicDispatcherExecutor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SoapDispatcherExecutor.class);

	/**
	 * @param jo
	 * @param params
	 * @return
	 * @throws Exception
	 * @author 严海星
	 * 2018年10月23日
	 */
	@Override
	public String execute(JSONObject jo, String funcId ,TicCoreLogMain log) throws Exception {
		//log.setFdLogType(Constant.FD_TYPE_SOAP);//Soap集成方法
		ITicSoapMainService ticSoapMainService = (ITicSoapMainService) SpringBeanUtil.getBean("ticSoapMainService");
		TicSoapMain soapMain = (TicSoapMain) ticSoapMainService
				.findByPrimaryKey(funcId,null,true);
		
		String xml = soapMain.getWsMapperTemplate();
		
		//响应超时时间
		String responseTime = soapMain.getTicSoapSetting().getFdResponseTime();
		//连接超时时间
		String connectTime = soapMain.getTicSoapSetting().getFdOverTime();
		
		Document document = DocumentHelper.parseText(xml);
		//解析xml的input中soap:Envelope标签,获取namespace信息
		Map<String,String> nameSpaces = XmlConvertToJsonUtil.getXmlNamespaceSetToDocument(XmlConvertToJsonUtil.INPUT, document);

		//删除在解析时添加在root的namespace,添加回原来的element上,不然调用soap函数会报错
		Set<String> names = nameSpaces.keySet();
		Element envelope =  (Element) ((Element)document.selectSingleNode(XmlConvertToJsonUtil.INPUT)).elements().get(0);
		for(String name : names)
		{
			Namespace ns = new Namespace(name, nameSpaces.get(name));
			document.getRootElement().remove(ns);
			envelope.addNamespace(name, nameSpaces.get(name));
		}
		
		// 递归解析json设值到函数的xml模板中
		logger.debug("xml:" + xml);
		logger.debug("xml2:" + document.asXML());
		logger.debug("jo:" + jo.toJSONString());
		try {
			logger.debug("document1:" + document.asXML());
			Document input = DocumentHelper.parseText(envelope.asXML());
			logger.debug("input1:" + input.asXML());
			RecursionUtil.recursionPaseJsonToSetXmlValue("/", jo, input);
			logger.debug("input2:" + input.asXML());
			Element parent = envelope.getParent();
			parent.remove(envelope);
			logger.debug("document2:" + document.asXML());
			parent.add(input.getRootElement());
			logger.debug("document3:" + document.asXML());
		} catch (Exception e) {
			logger.error("", e);
		}
		log.setFdSourceFuncInXml(document.asXML());

		// if (false)
		// throw new Exception("123");

		//进行soap远程调用
		ITicSoap ticSoap = (ITicSoap) SpringBeanUtil
				.getBean("ticSoap");

		SoapInfo soapInfo = new SoapInfo();
		//特殊处理Input,有可能要移除一些节点 
		org.w3c.dom.Document doc = DOMHelper
				.parseXmlString(document.asXML());
		NodeList nodelist = doc.getElementsByTagName("Input");
		dealInput(nodelist);
		soapInfo.setRequestDocument(DomUtil.stringToDoc(DOMHelper.nodeToString((org.w3c.dom.Node) doc)));
		soapInfo.setTicSoapMain(soapMain);
		// 实际的soap调用
		long startTime=System.currentTimeMillis();
		ITicSoapRtn soapRtn = ticSoap.inputToOutputRtn(soapInfo,responseTime,connectTime);
		long endTime = System.currentTimeMillis();
		long fdTimeConsuming=((endTime - startTime));
		log.setFdTimeConsumingOrg(String.valueOf(fdTimeConsuming));
		logger.info("请求耗时:"+fdTimeConsuming+ " ms");
		// 返回值为xml字符串
		String outContentXml = soapRtn.getRtnContent();
		log.setFdSourceFuncOutXml(outContentXml);
		
		Document out_document = DocumentHelper.parseText(outContentXml);
		
		//添加命名空间用于解析
		Element rootElement = out_document.getRootElement();
		for(String name : names)
		{
			rootElement.addNamespace(name, nameSpaces.get(name));
		}
		
		JSONObject rtn = new JSONObject(true);
		
		//返回值output的xml解析并转化成json结构
		JSONObject output = null;
		Element outputNode = XmlConvertToJsonUtil.getXmlBodyElement(null, out_document,nameSpaces);
		if(outputNode != null) {
            output = RecursionUtil.paseXmlToJson(outputNode.asXML(), true);
        }
		
		//判断是否是fault信息
		JSONObject faultJo = output.getJSONObject("Fault");
		if(faultJo != null)
		{
			rtn.put("fault", faultJo);
			//return faultJo.toJSONString();
		}else
		{
			if (output != null) {
				if (output.containsKey("Body")) {
					output = output.getJSONObject("Body");
				}
				// else{
				// String key_body = null;
				// for(String key :output.keySet()){
				// if(key.endsWith(":Body")){
				// key_body = key;
				// break;
				// }
				// }
				// output = output.getJSONObject(key_body);
				// }
			}
			rtn.put("out", output);
			//按put的顺序输出json字符串
		}
		return JSON.toJSONStringZ(rtn, SerializeConfig.getGlobalInstance(), SerializerFeature.QuoteFieldNames);
	}

	/*@Override
	public String funcXmlConvertToJson(String funcXml) throws Exception {
		Document document = DocumentHelper.parseText(funcXml);
		
		Element inputNode = XmlConvertToJsonUtil.getXmlBodyElement(XmlConvertToJsonUtil.INPUT, document,null);
		//inputNode = (Element) document.selectSingleNode("//Input/soap:Envelope/soap12:Body");
		
		Element outputNode = XmlConvertToJsonUtil.getXmlBodyElement(XmlConvertToJsonUtil.OUTPUT, document,null);
		
		Element faultNode = XmlConvertToJsonUtil.getXmlBodyElement(XmlConvertToJsonUtil.FAULT, document,null);
		
		JSONObject rtnJson = new JSONObject(true);
		JSONObject importJo = new JSONObject();
		JSONObject exportJo = new JSONObject();
		JSONObject faultJo = new JSONObject();
		
		//递归解析xml,进行json化
		if(inputNode != null)
		{
			RecursionUtil.recursionPaseXmlToJSON(inputNode,importJo);
		}
		if(outputNode != null)
		{
			RecursionUtil.recursionPaseXmlToJSON(outputNode,exportJo);
		}
		if(faultNode != null)
		{
			RecursionUtil.recursionPaseXmlToJSON(faultNode,faultJo);
		}
			
		rtnJson.put("Input", importJo);
		rtnJson.put("Output", exportJo);
		rtnJson.put("Fault", faultJo);
		//按put的顺序输出
		return JSON.toJSONStringZ(rtnJson, SerializeConfig.getGlobalInstance(), SerializerFeature.QuoteFieldNames);
	}*/
	
	/*public static void main(String[] args) throws Exception {
		String xml = "<?xml version=\"1.0\"?><web ID=\"13f9961794bf2e36ae299db483d8703b\"><Input><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:vouc=\"http://www.kingdee.com/Voucher\"><soap:Header/><soap:Body><vouc:UpdateVoucher><vouc:iAisID/><vouc:strUser/><vouc:strPassword/><vouc:Data><vouc:Voucher><vouc:Number/><vouc:Year/><vouc:Period/><vouc:Date/><vouc:TransDate/><vouc:Group><vouc:Name/><vouc:UUID/></vouc:Group><vouc:SerialNum/><vouc:Reference/><vouc:Attachments/><vouc:EntryCount/><vouc:DebitTotal/><vouc:CreditTotal/><vouc:Checked/><vouc:Posted/><vouc:Preparer><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Preparer><vouc:Checker><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Checker><vouc:Poster><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Poster><vouc:Cashier><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Cashier><vouc:Handler/><vouc:VoucherEntry><vouc:Explanation/><vouc:Account><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Account><vouc:Detail><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Detail><vouc:Currency><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:Currency><vouc:ExchangeRate/><vouc:DC/><vouc:AmountFor/><vouc:Amount/><vouc:Quantity/><vouc:MeasureUnit><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:MeasureUnit><vouc:UnitPrice/><vouc:theOtherAccount><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:theOtherAccount><vouc:SettleType><vouc:Number/><vouc:Name/><vouc:UUID/></vouc:SettleType><vouc:SettleNo/><vouc:TransNo/></vouc:VoucherEntry><vouc:UUID/></vouc:Voucher></vouc:Data><vouc:bCheckByUUID/><vouc:bAddNewOnly/></vouc:UpdateVoucher></soap:Body></soap:Envelope></Input><Output><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:vouc=\"http://www.kingdee.com/Voucher\"><soap:Header/><soap:Body><vouc:UpdateVoucherResponse><vouc:UpdateVoucherResult/><vouc:strError/></vouc:UpdateVoucherResponse></soap:Body></soap:Envelope></Output><Fault><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Header></soap:Header><soap:Body><soap:Fault><soap:Code><soap:Value/><soap:Subcode><soap:Value/><soap:Subcode/></soap:Subcode></soap:Code><soap:Reason><soap:Text xml:lang=\"\"/></soap:Reason><soap:Node/><soap:Role/><soap:Detail></soap:Detail></soap:Fault></soap:Body></soap:Envelope></Fault></web>";
		SoapDispatcherExecutor e = new SoapDispatcherExecutor();
		System.out.println(e.funcXmlConvertToJson(xml));
		//System.out.println(jsonStr);
	
	}*/

	@Override
	public TicCoreFuncBase findFunc(String fdId) throws Exception {
		// TODO Auto-generated method stub
		return (TicCoreFuncBase) getTicSoapMainService()
				.findByPrimaryKey(fdId);
	}

	@Override
	public TicCoreFuncBase findFuncByKey(String fdKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		//hqlInfo.setSelectBlock("fdFuncType");
		hqlInfo.setWhereBlock("fdKey=:fdKey");
		hqlInfo.setParameter("fdKey", fdKey);
		return (TicCoreFuncBase) getTicSoapMainService().findFirstOne(hqlInfo);
	}

	public ITicSoapMainService getTicSoapMainService() {
		if (ticSoapMainService == null) {
			ticSoapMainService = (ITicSoapMainService) SpringBeanUtil
					.getBean("ticSoapMainService");
		}
		return ticSoapMainService;
	}

	private ITicSoapMainService ticSoapMainService;

	@Override
	public List<RelationOuterSearchParams> getOutSearchParams(String funcId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//处理Input节点
	public void dealInput(NodeList nodeList){
        boolean first = true;
		Node firstNodeComment = null;
		Node firstNode = null;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			// Node node_result = nodeList_result.item(i);
			// System.out.println(node.getNodeName());
			// 如果是element 才处理循环
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				// 获取comment 节点信息
				Node comment = findCommentNode(node);
				// 如果是首个节点
				if (first) {
					first = false;
					firstNodeComment = comment;
					firstNode = node;
				}
				// 如果注解为空,但是节点名称跟首节点一致可认为是明细表
				if (comment == null
						&& node.getNodeName().equals(firstNode.getNodeName())) {
					comment = firstNodeComment;
				}
				if (comment != null) {
					String textContent = comment.getTextContent();
					// 如果是明细表节点
					if ((textContent.startsWith("Zero or more")
							|| textContent.contains("1 or more"))) {
						if("vouc:CashFlow".equals(node.getNodeName())||"vouc:DetailEntries".equals(node.getNodeName())){
							node.getParentNode().removeChild(node);
						}
					}
				}
				NodeList n_list = node.getChildNodes();
				dealInput(n_list);
			}
		}
	
	}
	
	/**
	 * 回溯查找commnet 节点
	 * 
	 * @param curNode
	 *            当前节点
	 * @return
	 */
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

	@Override
	public String executeRest(JSONObject jo, String funcId, TicCoreLogMain log)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
