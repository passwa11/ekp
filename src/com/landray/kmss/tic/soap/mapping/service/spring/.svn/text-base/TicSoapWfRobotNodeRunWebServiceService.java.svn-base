package com.landray.kmss.tic.soap.mapping.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.workflow.engine.INodeServiceActionResult;
import com.landray.kmss.sys.workflow.engine.WorkflowEngineContext;
import com.landray.kmss.sys.workflow.engine.spi.model.SysWfNode;
import com.landray.kmss.sys.workflow.support.oa.robot.interfaces.ISysWfRobotNodeService;
import com.landray.kmss.sys.workflow.support.oa.robot.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFuncExt;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncXmlOperateService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingMainService;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * WebService函数机器人节点实现
 * 
 * @author LINMINGMING
 * 
 */
public class TicSoapWfRobotNodeRunWebServiceService extends
		AbstractRobotNodeServiceImp implements ISysWfRobotNodeService {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicSoapWfRobotNodeRunWebServiceService.class);

	private ITicCoreMappingFuncService ticCoreMappingFuncService;

	public void setTicCoreMappingFuncService(
			ITicCoreMappingFuncService ticCoreMappingFuncService) {
		this.ticCoreMappingFuncService = ticCoreMappingFuncService;
	}

	private ITicCoreMappingFuncXmlOperateService ticCoreMappingFuncXmlOperateService;

	public void setTicCoreMappingFuncXmlOperateService(
			ITicCoreMappingFuncXmlOperateService ticCoreMappingFuncXmlOperateService) {
		this.ticCoreMappingFuncXmlOperateService = ticCoreMappingFuncXmlOperateService;
	}

	public void setTicSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

	public void setTicSoap(ITicSoap ticSoap) {
		this.ticSoap = ticSoap;
	}

	private ISysMetadataParser sysMetadataParser;

	public void setSysMetadataParser(ISysMetadataParser sysMetadataParser) {
		this.sysMetadataParser = sysMetadataParser;
	}

	private ITicCoreMappingMainService ticCoreMappingMainService;

	public void setTicCoreMappingMainService(
			ITicCoreMappingMainService ticCoreMappingMainService) {
		this.ticCoreMappingMainService = ticCoreMappingMainService;
	}

	private ITicSoapMainService ticSoapMainService;

	private ITicSoap ticSoap;

	@Override
    public INodeServiceActionResult execute(WorkflowEngineContext context,
                                            SysWfNode node) throws Exception {
		String cfgContent = getConfigContent(context, node);
		if (cfgContent == null) {
			return getDefaultActionResult(context, node);
		}
		IBaseModel mainModel = context.getMainModel();
		TicCoreMappingFuncExt exProgram = null;
		try {
			// 获得JSON配置
			JSONObject json = (JSONObject) JSONValue.parse(cfgContent);
			String funcId = (String) json.get("funcId");
			TicCoreMappingFunc ticCoreMappingFunc = getFunc(funcId);

			// if(ticCoreMappingFunc==null){
			// log.error("执行 Soap机器人节点, 执行 Soap TicCoreMappingFunc 中找不到关联的配置映射信息,请检查TicCoreMappingFunc 的"+funcId+"是否存在,或者重新创建机器人节点创建关联关系~");
			// throw new
			// Exception("Execute soap robot node, See log TicCoreMappingFunc");
			// }

			// 程序异常
			exProgram = ticCoreMappingFunc.getFdExtend().get(1);
			if (ticCoreMappingFunc == null) {
				return getDefaultActionResult(context, node);
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
				if (!ticCoreMappingFuncXmlOperateService.ifRegister(
						fdTemplateName, Constant.FD_TYPE_SOAP)) {
					return getDefaultActionResult(context, node);
				}
				// 已经注册
				else {

					org.w3c.dom.Document doc = DOMHelper
							.parseXmlString(ticCoreMappingFunc
									.getFdRfcParamXml());
					// org.w3c.dom.Document doc_result=
					// DOMHelper.parseXmlString(ticCoreMappingFunc
					// .getFdRfcParamXml());
					NodeList n_list = doc.getElementsByTagName("web");
					org.w3c.dom.Node curNode = DOMHelper.getElementNode(n_list,
							0);

					org.w3c.dom.Node attrNode = curNode.getAttributes()
							.getNamedItem("ID");
					String attrId = attrNode.getTextContent();
					if (StringUtil.isNull(attrId)) {
						log.error("Soap 模板中配置的web节点ID属性 没有找到对应的配置信息!");
						return getDefaultActionResult(context, node);
					} else {
						TicSoapMain ticSoapMain = (TicSoapMain) ticSoapMainService
								.findByPrimaryKey(attrId, null, true);

						if (ticSoapMain == null) {
							log.error("Soap 模板中没有找到配置的web 节点没有ID属性!");
							return getDefaultActionResult(context, node);
						}
						FormulaParser formulaParser = FormulaParser
								.getInstance(mainModel);
						NodeList nodelist = doc.getElementsByTagName("Input");
						// NodeList
						// nodelist_result=doc_result.getElementsByTagName("Input");

						List<Node> insert_list = new ArrayList<Node>();
						ticCoreMappingFuncXmlOperateService.setInputInfo(
								nodelist, insert_list, formulaParser);
						for (int i = 0; i < insert_list.size() / 2; i++) {
							Node refChild = insert_list.get(i * 2 + 1);
							refChild.getParentNode().insertBefore(
									insert_list.get(i * 2), refChild);
						}

						// 删除原始节点
						if (insert_list.size() > 0) {
							Node node2del = insert_list
									.get(insert_list.size() - 1);
							NodeList node_list = node2del.getChildNodes();
							if (node_list.getLength() > 0) {
								for (int i = 0; i < node_list.getLength(); i++) {
									Node child = node_list.item(i);
									if (child.getNodeType() == Node.ELEMENT_NODE) {
										String content = child.getTextContent();
										if (content == null
												|| "?".equals(content)
												|| "".equals(content)) {
											node2del.getParentNode()
													.removeChild(node2del);
											break;
										}
									}
								}
							} else {
								String content = node2del.getTextContent();
								if (content == null
										|| "?".equals(content)
										|| "".equals(content)) {
									node2del.getParentNode()
											.removeChild(node2del);
								}
							}
						}

						String resetXml = DOMHelper
								.nodeToString((org.w3c.dom.Node) doc);

						SoapInfo soapInfo = new SoapInfo();
						soapInfo.setRequestXml(resetXml);
						soapInfo.setTicSoapMain(ticSoapMain);
						String result = ticSoap.inputToAllXml(soapInfo);

						String[] mergeXml = new String[4];
						mergeXml[0] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
						mergeXml[1] = "<web ID=\"!{fdId}\">".replace("!{fdId}",
								funcId);
						mergeXml[2] = result;
						mergeXml[3] = "</web>";
						String real_result = StringUtils.join(mergeXml);
						org.w3c.dom.Document res_doc = DOMHelper
								.parseXmlString(real_result);
						NodeList res_nList = res_doc
								.getElementsByTagName("Output");
						// 把值存入m_store
						boolean flagBussiness = ticCoreMappingFuncXmlOperateService
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
							ticCoreMappingFuncXmlOperateService
									.businessException(null,
											ticCoreMappingFunc, mainModel);
						}
						sysMetadataParser.saveModel(mainModel);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("机器人任务执行出错", e);
			ticCoreMappingFuncXmlOperateService.programException(e,
					exProgram, mainModel);
		} finally {
			// 用传出参数修改model后保存更新此model，出现异常也必须保存可能赋值的错误信息
			sysMetadataParser.saveModel(mainModel);
		}
		return getDefaultActionResult(context, node);
	}

	private void programFaultInfo(NodeList nodeList, FormulaParser parser,
			IBaseModel mainModel) throws Exception {
		// fault节点赋值
		ticCoreMappingFuncXmlOperateService.setOutputInfo(nodeList, parser,
				mainModel, true);
	}

	private TicCoreMappingFunc getFunc(String funcId) throws Exception {
		TicCoreMappingFunc ticCoreMappingFunc = (TicCoreMappingFunc) ticCoreMappingFuncService
				.findByPrimaryKey(funcId, TicCoreMappingFunc.class, true);
		return ticCoreMappingFunc;
	}

	public void setticCoreMappingFuncXmlOperateService(
			ITicCoreMappingFuncXmlOperateService ticCoreMappingFuncXmlOperateService) {
		this.ticCoreMappingFuncXmlOperateService = ticCoreMappingFuncXmlOperateService;
	}

	public void setticSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

	public ITicSoap getticSoap() {
		return ticSoap;
	}

	public void setticSoap(ITicSoap ticSoap) {
		this.ticSoap = ticSoap;
	}

}
