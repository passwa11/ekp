package com.landray.kmss.tic.soap.mapping.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFuncExt;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncXmlOperateService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingMainService;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class TicSoapMappingRunFunction {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicSoapWfRobotNodeRunWebServiceService.class);
	private ITicCoreMappingMainService ticCoreMappingMainService;
	private ITicCoreMappingFuncXmlOperateService ticSoapMappingWebServiceXmlOperateService;
	private ITicSoapMainService ticSoapMainService;
	private ITicSoap ticSoap;
	private ISysMetadataParser sysMetadataParser;

	public void setTicCoreMappingMainService(
			ITicCoreMappingMainService ticCoreMappingMainService) {
		this.ticCoreMappingMainService = ticCoreMappingMainService;
	}

	public void setTicSoapMappingWebServiceXmlOperateService(
			ITicCoreMappingFuncXmlOperateService ticSoapMappingWebServiceXmlOperateService) {
		this.ticSoapMappingWebServiceXmlOperateService = ticSoapMappingWebServiceXmlOperateService;
	}

	public void setTicSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

	public void setTicSoap(ITicSoap ticSoap) {
		this.ticSoap = ticSoap;
	}

	public void setSysMetadataParser(ISysMetadataParser sysMetadataParser) {
		this.sysMetadataParser = sysMetadataParser;
	}

	public void runWS(TicCoreMappingFunc ticCoreMappingFunc,
			IBaseModel mainModel) throws Exception {
		try {
			if (ticCoreMappingFunc == null) {
				log
						.error("执行 Soap TicCoreMappingFunc 中找不到关联的配置映射信息,请检查TicCoreMappingFunc是否存在");
				throw new Exception(
						"Execute soap robot node, See log TicCoreMappingFunc");
			}

			// 判断是否注册启用
			String fdTemplateId = ticCoreMappingFunc.getFdTemplateId();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" ticCoreMappingMain.fdMainModelName ");
			hqlInfo
					.setWhereBlock(" ticCoreMappingMain.fdTemplateId=:fdTemplateId");
			hqlInfo.setParameter("fdTemplateId", fdTemplateId);
			String fdTemplateName = (String) ticCoreMappingMainService.findFirstOne(hqlInfo);

			if (StringUtils.isNotBlank(fdTemplateName)) {
				// 没注册
				if (!ticSoapMappingWebServiceXmlOperateService.ifRegister(
						fdTemplateName, Constant.FD_TYPE_SOAP)) {
					return;
				} else {
					// 已经注册
					Document doc = DOMHelper
							.parseXmlString(ticCoreMappingFunc
									.getFdRfcParamXml());
					// Document doc_result=
					// DOMHelper.parseXmlString(ticCoreMappingFunc
					// .getFdRfcParamXml());
					NodeList n_list = doc.getElementsByTagName("web");
					Node curNode = DOMHelper.getElementNode(n_list, 0);
					Node attrNode = curNode.getAttributes().getNamedItem("ID");
					String attrId = attrNode.getTextContent();
					if (StringUtil.isNull(attrId)) {
						log.error("Soap 模板中配置的web节点ID属性 没有找到对应的配置信息!");
						return;
					} else {
						TicSoapMain ticSoapMain = (TicSoapMain) ticSoapMainService
								.findByPrimaryKey(attrId, null, true);
						if (ticSoapMain == null) {
							log.error("Soap 模板中没有找到配置的web 节点没有ID属性!");
							return;
						}
						FormulaParser formulaParser = FormulaParser
								.getInstance(mainModel);
						NodeList nodelist = doc.getElementsByTagName("Input");
						// NodeList
						// nodelist_result=doc_result.getElementsByTagName("Input");
						List<Node> insert_list = new ArrayList<Node>();
						ticSoapMappingWebServiceXmlOperateService.setInputInfo(
								nodelist, insert_list, formulaParser);
						for (int i = 0; i < insert_list.size() / 2; i++) {
							doc.insertBefore(insert_list.get(i * 2),
									insert_list.get(i * 2 + 1));
						}
						if (insert_list.size() > 0) {
							// 删除原始节点
							Node node2del = insert_list
									.get(insert_list.size() - 1);
							NodeList node_list = node2del.getChildNodes();
							if (node_list.getLength() > 0) {
								for (int i = 0; i < node_list.getLength(); i++) {
									Node child = node_list.item(i);
									if (child.getNodeType() == Node.ELEMENT_NODE) {
										if ("?".equals(child.getTextContent())) {
											node2del.getParentNode()
													.removeChild(node2del);
											break;
										}
									}
								}
							}
						}

						String resetXml = DOMHelper.nodeToString((Node) doc);
						SoapInfo soapInfo = new SoapInfo();
						soapInfo.setRequestXml(resetXml);
						soapInfo.setTicSoapMain(ticSoapMain);
						String result = ticSoap.inputToAllXml(soapInfo);

						String[] mergeXml = new String[4];
						mergeXml[0] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
						mergeXml[1] = "<web ID=\"!{fdId}\">".replace("!{fdId}",
								ticCoreMappingFunc.getFdId());
						mergeXml[2] = result;
						mergeXml[3] = "</web>";
						String real_result = StringUtils.join(mergeXml);
						org.w3c.dom.Document res_doc = DOMHelper
								.parseXmlString(real_result);
						NodeList res_nList = res_doc
								.getElementsByTagName("Output");
						// 把值存入m_store
						boolean flagBussiness = ticSoapMappingWebServiceXmlOperateService
								.setOutputInfo(res_nList, formulaParser,
										mainModel, true);
						NodeList fault_nList = res_doc
								.getElementsByTagName("Fault");
						// 程序异常
						if (fault_nList.getLength() > 0) {
							programFaultInfo(fault_nList, formulaParser,
									mainModel);
							Node faultNode = DOMHelper.getElementNode(
									fault_nList, 0);
							String faultStr = DOMHelper.nodeToString(faultNode,
									true);
							throw new Exception("WEBSERVICE return Fault:\n"
									+ faultStr);
						}
						// 发生了业务异常进行处理
						if (!flagBussiness) {
							ticSoapMappingWebServiceXmlOperateService
									.businessException(null,
											ticCoreMappingFunc, mainModel);
						}
						sysMetadataParser.saveModel(mainModel);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 程序异常
			TicCoreMappingFuncExt exProgram = ticCoreMappingFunc
					.getFdExtend().get(1);
			ticSoapMappingWebServiceXmlOperateService.programException(e,
					exProgram, mainModel);
		} finally {
			// 用传出参数修改model后保存更新此model，出现异常也必须保存可能赋值的错误信息
			sysMetadataParser.saveModel(mainModel);
		}
	}

	private void programFaultInfo(NodeList nodeList, FormulaParser parser,
			IBaseModel mainModel) throws Exception {
		// fault节点赋值
		ticSoapMappingWebServiceXmlOperateService.setOutputInfo(nodeList,
				parser, mainModel, true);
	}
}
