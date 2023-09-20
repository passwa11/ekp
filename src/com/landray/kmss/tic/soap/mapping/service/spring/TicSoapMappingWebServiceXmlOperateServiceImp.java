package com.landray.kmss.tic.soap.mapping.service.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.exception.KmssUnExpectFieldException;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.tic.core.mapping.constant.TicCoreBussniessExection;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFuncExt;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncXmlOperateService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingModuleService;
import com.landray.kmss.tic.core.mapping.service.spring.TicCoreMappingBaseFuncXmlOperateServiceImpl;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class TicSoapMappingWebServiceXmlOperateServiceImp extends
		TicCoreMappingBaseFuncXmlOperateServiceImpl implements
		ITicCoreMappingFuncXmlOperateService { 

	private ITicCoreMappingFuncService ticCoreMappingFuncService;

	public void setTicCoreMappingFuncService(
			ITicCoreMappingFuncService ticCoreMappingFuncService) {
		this.ticCoreMappingFuncService = ticCoreMappingFuncService;
	}

	private ITicCoreMappingModuleService ticCoreMappingModuleService;

	public void setTicCoreMappingModuleService(
			ITicCoreMappingModuleService ticCoreMappingModuleService) {
		this.ticCoreMappingModuleService = ticCoreMappingModuleService;
	}

	private ISysMetadataParser sysMetadataParser;

	public void setSysMetadataParser(ISysMetadataParser sysMetadataParser) {
		this.sysMetadataParser = sysMetadataParser;
	}

	@Override
    public void businessException(Document document,
                                  TicCoreMappingFunc ticCoreMappingFunc, IBaseModel mainModel)
			throws Exception {
		TicCoreMappingFuncExt exBusiness = ticCoreMappingFunc.getFdExtend()
				.get(0);
		Boolean fdIsAssign = exBusiness.getFdIsAssign();
		Boolean fdIsIgnore = exBusiness.getFdIsIgnore();
		// 节点的值
		if (fdIsAssign) {
			sysMetadataParser.setFieldValue(mainModel, getEkpid(exBusiness
					.getFdAssignFieldid()), exBusiness.getFdAssignVal());
		}
		if (fdIsIgnore) {
			throw new TicCoreBussniessExection(exBusiness.getFdAssignVal());
		}
	}

	@Override
    public boolean ifRegister(String templateName, int fdType)
			throws Exception {
		return ticCoreMappingModuleService.ifRegister(templateName, fdType);
	}

	@Override
    public boolean ifRegister(IBaseModel model, int fdType) throws Exception {
		return ticCoreMappingModuleService.ifRegister(model, fdType);
	}

	@Override
    public void programException(Exception e,
                                 TicCoreMappingFuncExt exProgram, IBaseModel mainModel)
			throws Exception {
		if (!(e instanceof TicCoreBussniessExection)) {
			// 程序异常处理
			Boolean fdIsAssign = exProgram.getFdIsAssign();
			Boolean fdIsIgnore = exProgram.getFdIsIgnore();
			if (fdIsAssign) {
				sysMetadataParser.setFieldValue(mainModel, getEkpid(exProgram
						.getFdAssignFieldid()), exProgram.getFdAssignVal());
			}
			if (fdIsIgnore) {
				throw new Exception("Program Exception");
			}
		} else {
			throw new Exception("Busniess Exception");
		}
	}

	// ====================
	// 设置传出参数中table类型的参数,只支持a或a.b格式
	@Override
    @Deprecated
	public void setFuncExportTable(Document document, IBaseModel baseModel)
			throws Exception {
		// // TODO 自动生成的方法存根
	}

	// 设置函数xml传出参数field内容或者structure下的field
	@Override
    @Deprecated
	public void setFuncExportXml(Document document, IBaseModel mainModel)
			throws Exception {
		// // TODO 自动生成的方法存根
	}

	// 设置传入参数table类型参数field内容
	/***************************************************************************
	 * 注意row0的field中既有数据也有影射信息 基于表单中的明细表和表参数是一致的包括行数是一致的，只是有些字段可能映射的是非明细表中的字段
	 * 两种处理逻辑：1.映射的是自定义表单中明细表的字段，结果形如$a
	 * .b$,公式解析后得到的是list;2.映射的是非明细表的字段形如$a$,得到的是单一数据;
	 **************************************************************************/
	@Override
    @Deprecated
	public void setFuncImportTableByFormula(Document document,
			FormulaParser parser) throws Exception {
		// TODO 自动生成的方法存根
	}

	// 设置函数xml传入参数field内容或者structure下的field
	@Override
    @Deprecated
	public void setFuncImportXmlByFormula(Document document,
			FormulaParser parser) throws Exception {
		// TODO 自动生成的方法存根
	}

	@Override
    public void setInputParamXmlByFormula(List<Element> nodeList,
                                          FormulaParser parser) throws Exception {
		// TODO 自动生成的方法存根
		if (nodeList == null || nodeList.isEmpty()) {
			return;
		}
		for (Element elem : nodeList) {
			// System.out.println(elem);
			elem.content();
			List<Element> childrenElem = elem.elements();

			setInputParamXmlByFormula(childrenElem, parser);

		}
	}

	@Override
    public void setInputInfo(NodeList nodeList, List<Node> insert_list,
                             FormulaParser parser) {
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
						if(!"vouc:Voucher".equals(node.getNodeName())){
							if("vouc:CashFlow".equals(node.getNodeName())||"vouc:DetailEntries".equals(node.getNodeName())){
								node.getParentNode().removeChild(node);
							}else{
						        dealListMapping(node, parser, insert_list);
							}
						    continue;
						}
					}
					JSONObject jsonComment = analystsComment(textContent);
					dealEkpMapping(jsonComment, node, parser, insert_list);
				}
				NodeList n_list = node.getChildNodes();
				setInputInfo(n_list, insert_list, parser);
			}
		}
	}

	// 处理列表类型数据，现只支持单层列表
	public void dealListMapping(Node node, FormulaParser parser,
			List<Node> insert_list) {

		NodeList n_list = node.getChildNodes();

		// 明细表映射属性
		Map<String, Set<String>> listFields = new HashMap<String, Set<String>>();

		Map<String, String> fieldMappings = new HashMap<String, String>();

		if (n_list.getLength() == 0) {
			Node comment = findCommentNode(node);
			if (comment != null) {
				String textContent = comment.getTextContent();
				JSONObject jsonComment = analystsComment(textContent);
				if (jsonComment != null) {
					String ekpid = (String) jsonComment.get("ekpid");
					ekpid = filter(ekpid);
					if (StringUtil.isNotNull(ekpid)) {
						Object fieldValue = parser.parseValueScript(ekpid);
						if (fieldValue instanceof Collection) {
							Iterator it = ((Collection) fieldValue).iterator();
							for (Iterator iterator = it; iterator.hasNext();) {
								Node cloneNode = node.cloneNode(true);
								insert_list.add(cloneNode);
								insert_list.add(node);
								cloneNode
										.setTextContent(
												(String) iterator.next());
							}
						} else {
							node.setTextContent((String) fieldValue);
						}
						// if (fieldValue instanceof Collection) {
						// int index = ekpid.indexOf(".");
						// if (index > 0) {
						// fieldMappings.put(ekpid.substring(index + 1,
						// ekpid.length() - 1), node
						// .getNodeName());
						// // 明细表id
						// String listId = ekpid.substring(0, index);
						// Set<String> fields = listFields.get(listId);
						// if (fields == null) {
						// fields = new HashSet<String>();
						// fields.add(ekpid.substring(index + 1, ekpid
						// .length() - 1));
						// listFields.put(listId, fields);
						// } else {
						// fields.add(ekpid);
						// }
						// }
						// } else {
						// node.setTextContent(String.valueOf(fieldValue));
						// }
					}
				}
			}
			return;
		}
		for (int i = 0; i < n_list.getLength(); i++) {
			Node child = n_list.item(i);

			if (child.getNodeType() == Node.ELEMENT_NODE) {
				// 获取comment 节点信息
				Node comment = findCommentNode(child);
				if (comment != null) {
					String textContent = comment.getTextContent();
					if(textContent!=null && textContent.startsWith("Zero or more") ){//if("vouc:DetailEntries".equals(node.getNodeName()) ){// textContent.startsWith("Zero or more"),后续修改
						child.getParentNode().removeChild(child);
						continue;
					}
					JSONObject jsonComment = analystsComment(textContent);
					if (jsonComment == null) {
						continue;
					}
					String ekpid = (String) jsonComment.get("ekpid");
					ekpid = filter(ekpid);

					if (StringUtil.isNotNull(ekpid)) {

						Object fieldValue = parser.parseValueScript(ekpid);
						if (fieldValue instanceof Collection) {
							int index = ekpid.indexOf(".");
							if (index > 0) {
								fieldMappings.put(ekpid.substring(index + 1,
										ekpid.length() - 1), child
										.getNodeName());
								// 明细表id
								String listId = ekpid.substring(0, index);
								Set<String> fields = listFields.get(listId);
								if (fields == null) {
									fields = new HashSet<String>();
									fields.add(ekpid.substring(index + 1, ekpid
											.length() - 1));
									listFields.put(listId, fields);
								} else {
									fields.add(ekpid);
								}
							}
						} else {
							child.setTextContent(String.valueOf(fieldValue));
						}
					}
				}
			}
		}
		for (String key : listFields.keySet()) {
			Set<String> fields = listFields.get(key);
			// 获取明细表的值（为map的列表）
			Object listValues = parser.parseValueScript(filter(key) + "$");
			if (listValues instanceof Collection) {
				Iterator it = ((Collection) listValues).iterator();
				for (Iterator iterator = it; iterator.hasNext();) {
					Node cloneNode = node.cloneNode(true);
					insert_list.add(cloneNode);
					insert_list.add(node);

					Map rtn = (Map) iterator.next();
					setListNodeValue(cloneNode, fieldMappings, rtn);
				}
			}
		}
	}

	private void setListNodeValue(Node cloneNode,
			Map<String, String> fieldMappings, Map rtn) {

		for (String fieldId : fieldMappings.keySet()) {
			Object value = rtn.get(fieldId);
			if (value != null) {
				NodeList n_list = cloneNode.getChildNodes();
				for (int i = 0; i < n_list.getLength(); i++) {
					Node child = n_list.item(i);
					if (child.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					if (child.getNodeName().equals(fieldMappings.get(fieldId))) {
						child.setTextContent(value.toString());
						break;
					}

				}
			}
		}

	}

	public void dealEkpMapping(JSONObject jsonComment, Node node,
			FormulaParser parser, List<Node> insert_list) {
		if (jsonComment == null) {
			return;
		}
		String ekpid = (String) jsonComment.get("ekpid");
		// 获取ekpid
		if (StringUtil.isNotNull(ekpid)) {
			Object fieldValue = parser.parseValueScript(filter(ekpid));
			//System.out.println(ekpid + "---" + fieldValue);
			if (fieldValue instanceof Collection) {
				Iterator it = ((Collection) fieldValue).iterator();
				for (Iterator iterator = it; iterator.hasNext();) {

					Object rtn = iterator.next();
					Node cloneNode = node.cloneNode(true);
					cloneNode.setTextContent(rtn.toString());

					insert_list.add(cloneNode);
					insert_list.add(node);
				}
			} else if (fieldValue instanceof Date) {
				node.setTextContent(DateUtil.convertDateToString(
						(Date) fieldValue, "yyyyMMdd HH:mm:ss"));
			} else {
				node.setTextContent(String.valueOf(fieldValue));
			}
		}
	}

	/**
	 * 把comment 的数据解析成json对象
	 * 
	 * @return
	 */
	public JSONObject analystsComment(String comment) {
		// comment = StringUtils.deleteWhitespace(comment);
		Integer n = null;
		if ((n = comment.indexOf("erp_web=")) > 0) {
			comment = comment.substring(n + "erp_web=".length());
			String comment2 = comment.replaceAll("\\\\\"", "&quot;");
			JSONObject jsonObj = JSONObject.fromObject(comment2);
			return jsonObj;
		}
		// jsonObj.get
		// System.out.println(comment);
		return null;

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
    public void setOutputParamXml(List<Element> nodeList, IBaseModel mainModel)
			throws Exception {

	}

	@Override
    public List<TicCoreMappingFunc> getFuncList(String fdTemplateId,
                                                int fdInvokeType, int fdIntegrationType) throws Exception {
		// TODO 自动生成的方法存根
		// 防止sql注入以及sql调整
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("ticCoreMappingFunc.fdTemplateId=:fdTemplateId and ticCoreMappingFunc.fdInvokeType =:fdInvokeType and fdIntegrationType=:fdIntegrationType ");
		hqlInfo.setOrderBy("ticCoreMappingFunc.fdOrder asc");
		hqlInfo.setParameter("fdTemplateId", fdTemplateId);
		hqlInfo.setParameter("fdInvokeType", fdInvokeType);
		hqlInfo.setParameter("fdIntegrationType", fdIntegrationType + "");
		return (List<TicCoreMappingFunc>) ticCoreMappingFuncService
				.findList(hqlInfo);
	}

	public static void main(String[] args) throws Exception {

		InputStream in = TicSoapMappingWebServiceXmlOperateServiceImp.class
				.getResourceAsStream("respone.xml");
		String result = IOUtils.toString(in);
		TicSoapMappingWebServiceXmlOperateServiceImp spi = new TicSoapMappingWebServiceXmlOperateServiceImp();
		org.w3c.dom.Document doc = DOMHelper.parseXmlString(result);
		NodeList nodelist = doc.getElementsByTagName("Input");

	}

	/**
	 * 设置传出值
	 */
	@Override
    public boolean setOutputInfo(NodeList nodeList, FormulaParser parser,
                                 IBaseModel mainModel, boolean flagSuccess) throws Exception {
		Map<String, List<DealStore>> m_store = new HashMap<String, List<DealStore>>();
		boolean flagBussiness = setOutputInfoToMap(m_store, nodeList,
				flagSuccess);
		// 进行插入数据库
		dealEkpOutput(mainModel, parser, m_store);
		return flagBussiness;
	}

	/**
	 * 把值放入map
	 * 
	 * @param m_store
	 * @param nodeList
	 * @param flagSuccess
	 * @return
	 */
	public boolean setOutputInfoToMap(Map<String, List<DealStore>> m_store,
			NodeList nodeList, boolean flagSuccess) {
		boolean first = true;
		Node firstNodeComment = null;
		Node firstNode = null;
		// Map<String, List<DealStore>> m_store = new HashMap<String,
		// List<DealStore>>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
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
					if(textContent!=null && textContent.startsWith("Zero or more") && "vouc:DetailEntries".equals(node.getNodeName())){//if("vouc:DetailEntries".equals(node.getNodeName()) ){// textContent.startsWith("Zero or more"),后续修改
						node.getParentNode().removeChild(node);
						continue;
					}
					JSONObject jsonComment = analystsComment(textContent);
					// dealEkpMapping(jsonComment, node, parser);
					if (jsonComment != null) {

						String key = (String) jsonComment.get("ekpid");
						if (StringUtil.isNotNull(key)) {
							String mapkey = key + "_" + node.getNodeName();
							if (m_store.containsKey(mapkey)) {
								DealStore ds = new DealStore(node, key);
								m_store.get(mapkey).add(ds);
							} else {
								DealStore ds = new DealStore(node, key);
								List<DealStore> dl = new ArrayList<DealStore>(1);
								dl.add(ds);
								m_store.put(mapkey, dl);
							}
						}
						// 标记业务异常
						String suc = (String) jsonComment.get("isSuccess");
						String fail = (String) jsonComment.get("isFail");
						String text = node.getTextContent();
						String txt = StringUtils.deleteWhitespace(text);
						if (StringUtil.isNotNull(fail)) {
							String fa = StringUtils.deleteWhitespace(fail);
							if (fa.equals(txt)) {
								// 判断到有失败
								flagSuccess = flagSuccess && false;
							}
						}
						if (StringUtil.isNotNull(suc)) {
							String sc = StringUtils.deleteWhitespace(suc);
							if (sc.equals(txt)) {
								// 判断到有失败
								flagSuccess = flagSuccess && true;
							}
						}
					}
				}
				NodeList n_list = node.getChildNodes();
				flagSuccess = setOutputInfoToMap(m_store, n_list, flagSuccess);
			}
		}
		// dealEkpOutput(mainModel, parser, m_store);
		return flagSuccess;
	}

	private void dealEkpOutput(IBaseModel mainModel, FormulaParser parser,
			Map<String, List<DealStore>> m_store)
			throws KmssUnExpectFieldException, Exception {
		for (String key : m_store.keySet()) {
			List<DealStore> ds = m_store.get(key);
			String ekpid = null;
			if (!ds.isEmpty()) {
				ekpid = getEkpid(ds.get(0).getEkpid());
			}
			List<String> pl = parseList(ds);

			if (pl.isEmpty()) {
				continue;
			}
			if (ekpid.indexOf(".") > -1) {
				sysMetadataParser.setFieldValue(mainModel, ekpid, pl);
			} else {
				String values = StringUtils.join(pl, ",");
				sysMetadataParser.setFieldValue(mainModel, ekpid, values);
			}
		}
	}

	private List<String> parseList(List<DealStore> m_store) {
		List<String> p_list = new ArrayList<String>();
		for (DealStore ds : m_store) {
			String tc = ds.getNode().getTextContent();
			p_list.add(tc);
		}

		return p_list;
	}

	public class DealStore {
		private Node node;
		private String ekpid;

		DealStore() {

		}

		DealStore(Node node, String ekpid) {
			this.node = node;
			this.ekpid = ekpid;
		}

		public Node getNode() {
			return node;
		}

		public void setNode(Node node) {
			this.node = node;
		}

		public String getEkpid() {
			return ekpid;
		}

		public void setEkpid(String ekpid) {
			this.ekpid = ekpid;
		}

	}

	@Override
	public void setInputInfo(NodeList nodeList, FormulaParser parser) {
		// TODO 自动生成的方法存根

	}

}
