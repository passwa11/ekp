package com.landray.kmss.tic.soap.mapping.plugins;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingFuncExt;
import com.landray.kmss.tic.core.mapping.model.TicCoreMappingMain;
import com.landray.kmss.tic.core.mapping.plugins.IBaseTicCoreMappingIntegration;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingFuncXmlOperateService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingMainService;
import com.landray.kmss.tic.core.mapping.service.ITicCoreMappingModuleService;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模块拆解,代码级别模块分离使用这个扩展点集成类;
 * 
 * @author zhangtian date :2012-10-9 下午06:14:24
 */
public class TicSoapMappingIntegration implements
		IBaseTicCoreMappingIntegration {

	private Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
    public HQLInfo findSettingNameHQLByTempId(String fdTemplateId,
                                              String fdIntegrationType) {
		HQLInfo webhqlInfo = new HQLInfo();
		webhqlInfo
				.setSelectBlock("ticCoreMappingFunc.fdRefName ,ticCoreMappingFunc.fdInvokeType ");
		webhqlInfo
				.setWhereBlock("ticCoreMappingFunc.fdTemplateId=:fdTemplateId and ticCoreMappingFunc.fdIntegrationType=:fdIntegrationType");
		webhqlInfo
				.setOrderBy("ticCoreMappingFunc.fdInvokeType asc,ticCoreMappingFunc.fdOrder asc");
		webhqlInfo.setParameter("fdTemplateId", fdTemplateId);
		webhqlInfo.setParameter("fdIntegrationType", fdIntegrationType);
		return webhqlInfo;
	}

	/**
	 * 代码拆解 \third\erp\ekpweb\webEkpFormEventInclude.jsp 的代码拆解到扩展点中
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
    public List<TicCoreMappingFunc> getFormEventIncludeList(
			HttpServletRequest request) throws Exception {

		// 在编辑页面,驳回页面也能获取到模板ID也能获取模板
		String fdTemplateId_web = "";// =request.getParameter("kmReviewMainForm.fdTemplateId");//基于每个新建文档时都会传递模板id，且都为fdTemplateId
		ITicCoreMappingModuleService ticCoreMappingModuleService = (ITicCoreMappingModuleService) SpringBeanUtil
				.getBean("ticCoreMappingModuleService");
		// 使用缓存记录的modelName;
		ticCoreMappingModuleService.initRegisterModelHash();
		ConcurrentHashMap<String, Map<String, Object>> ticCoreMappingModuleList = ticCoreMappingModuleService
				.getRegisterModelHash();
		if (ticCoreMappingModuleList.isEmpty()) {
			fdTemplateId_web = null;
		} else {
			Iterator<String> modelSet = ticCoreMappingModuleList.keySet()
					.iterator();
			while (modelSet.hasNext()) {
				String modelName = modelSet.next();
				if ("init".equals(modelName)) {
					continue;
				}
				try {
					Object obj = com.landray.kmss.util.ClassUtils.forName(modelName).newInstance();
					if (obj instanceof IBaseModel) {
						Class formClass = (Class) PropertyUtils.getProperty(
								obj, "formClass");
						String formName = formClass.getSimpleName();
						// 注意，如果form 的model 为KmReviewForm 那么 对应的form 在struct
						// 里面配置一定要为 kmReviewForm;首字母小写
						formName = formName.substring(0, 1).toLowerCase()
								+ formName.substring(1, formName.length());
						Object resForm = request.getAttribute(formName);
						if (resForm != null) {
							if (PropertyUtils.isReadable(resForm,
									"fdTemplateId")) {
								fdTemplateId_web = (String) PropertyUtils
										.getProperty(resForm, "fdTemplateId");
							}
						} else {
							// Logger.warn("ERP组件检查到当前request 找不到属性为："+resForm+
							// "的数据,所以获取不到流程模板Id");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
					log.warn("不存在modelName:" + modelName
							+ " 的类,请到SOAP应用注册模块取消注册 ");
				}
			}
		}
		List<TicCoreMappingFunc> ticCoreMappingFuncList = new ArrayList<TicCoreMappingFunc>();
		// 若模版Id不存在，则return 空的list
		if (StringUtil.isNull(fdTemplateId_web)) {
			return ticCoreMappingFuncList;
		}
		ITicCoreMappingMainService ticCoreMappingMainService = (ITicCoreMappingMainService) SpringBeanUtil
				.getBean("ticCoreMappingMainService");

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock(" ticCoreMappingMain.fdTemplateId= :fdTemplateId_web ");
		hqlInfo.setParameter("fdTemplateId_web", fdTemplateId_web);
		TicCoreMappingMain ticCoreMappingMain = (TicCoreMappingMain) ticCoreMappingMainService.findFirstOne(hqlInfo);

		if (ticCoreMappingMain != null) {
			boolean use = ticCoreMappingModuleService.ifRegister(
					ticCoreMappingMain.getFdMainModelName(),
					Constant.FD_TYPE_SOAP);// 判断是否注册并启用的了
			if (use) {
				ticCoreMappingFuncList = ticCoreMappingMain
						.getFdFormEventFunctionList();// 得到表单事件的函数列表
				List<TicCoreMappingFunc> erptempList = new ArrayList<TicCoreMappingFunc>();
				// 一般加入模板不会太多，
				for (TicCoreMappingFunc tempfunc : ticCoreMappingFuncList) {
					// 加入webservice判断，当主模板中不存在对应模板存在,那么久不加入引入列表当中
					if ((Constant.FD_TYPE_SOAP + "").equals(tempfunc
							.getFdIntegrationType())) {
						erptempList.add(tempfunc);
					}
				}
				return erptempList;
			}
		}
		return null;
	}

	/**
	 * invokeType 3为机器人节点类型，还有表单事件，表单删除事件也同样可以用
	 */
	@Override
    public List<Map<String, String>> getSettingNameInfo(String templateId,
                                                        String invokeType) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>(
				1);
		ITicCoreMappingFuncService service = (ITicCoreMappingFuncService) SpringBeanUtil
				.getBean("ticCoreMappingFuncService");

		// 防止sql注入
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock(" ticCoreMappingFunc.fdTemplateId=:fdTemplateId and "
						+ "ticCoreMappingFunc.fdInvokeType=:fdInvokeType and "
						+ "ticCoreMappingFunc.fdIntegrationType=:fdIntegrationType ");
		hqlInfo.setOrderBy(" ticCoreMappingFunc.fdOrder asc");
		hqlInfo.setParameter("fdTemplateId", templateId);
		hqlInfo.setParameter("fdInvokeType", Integer.valueOf(invokeType));
		hqlInfo.setParameter("fdIntegrationType", Constant.FD_TYPE_SOAP);
		List<TicCoreMappingFunc> ticCoreMappingFuncList = service
				.findList(hqlInfo);
		for (int i = 0; i < ticCoreMappingFuncList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>(1);
			TicCoreMappingFunc ticCoreMappingFunc = ticCoreMappingFuncList
					.get(i);
			map.put("text", ticCoreMappingFunc.getFdRefName());
			map.put("value", ticCoreMappingFunc.getFdId());
			rtnList.add(map);
		}
		return rtnList;
	}

	@Override
    public IBaseModel addMethodInvoke(IBaseModel baseModel) throws Exception {
		return executeInitModel(baseModel, Integer
				.valueOf(Constant.INVOKE_TYPE_FORMSAVE));
	}

	private IBaseModel executeInitModel(IBaseModel model, Integer findType)
			throws Exception {
		ITicCoreMappingFuncXmlOperateService ticCoreMappingFuncXmlOperateService = null;
		TicCoreMappingFunc ticCoreMappingFunc = null;
		try {
			ITicCoreMappingModuleService ticCoreMappingModuleService = (ITicCoreMappingModuleService) SpringBeanUtil
					.getBean("ticCoreMappingModuleService");
			// 取得webservice 的实现
			ticCoreMappingFuncXmlOperateService = (ITicCoreMappingFuncXmlOperateService) SpringBeanUtil
					.getBean("soapticCoreWebServiceXmlOperateServiceImp");
			ITicSoap ticSoap = (ITicSoap) SpringBeanUtil
					.getBean("ticSoap");
			ITicSoapMainService ticSoapMainService = (ITicSoapMainService) SpringBeanUtil
					.getBean("ticSoapMainService");
			String modelName = ModelUtil.getModelClassName(model);
			String fdModelTemFieldName = ticCoreMappingModuleService
					.getFdModelTemFieldName(modelName);
			String fdTemplateId = (String) PropertyUtils.getProperty(model,
					fdModelTemFieldName + ".fdId");// 一般为fdTemplate.fdId
			List<TicCoreMappingFunc> ticCoreMappingFuncList = ticCoreMappingFuncXmlOperateService
					.getFuncList(fdTemplateId, findType, Constant.FD_TYPE_SOAP);// 按顺序得到保存事件需要执行的函数列表
			if (ticCoreMappingFuncList == null
					|| ticCoreMappingFuncList.isEmpty()) {
				return model;// 如果没有函数则返回
			}

			FormulaParser formulaParser = FormulaParser.getInstance(model);// 根据model得到公式解析器
			for (int i = 0; i < ticCoreMappingFuncList.size(); i++) {
				ticCoreMappingFunc = ticCoreMappingFuncList.get(i);

				org.w3c.dom.Document doc = DOMHelper
						.parseXmlString(ticCoreMappingFunc.getFdRfcParamXml());
				// org.w3c.dom.Document doc_result=
				// DOMHelper.parseXmlString(ticCoreMappingFunc
				// .getFdRfcParamXml());
				NodeList n_list = doc.getElementsByTagName("web");
				org.w3c.dom.Node curNode = DOMHelper.getElementNode(n_list, 0);

				org.w3c.dom.Node attrNode = curNode.getAttributes()
						.getNamedItem("ID");
				String attrId = attrNode.getTextContent();
				if (StringUtil.isNull(attrId)) {
					log.error("Soap 模板中配置的web节点ID属性 没有找到对应的配置信息!");
					continue;
				}
				TicSoapMain ticSoapMain = (TicSoapMain) ticSoapMainService
						.findByPrimaryKey(attrId, null, true);

				NodeList nodelist = doc.getElementsByTagName("Input");
				// NodeList
				// nodelist_result=doc_result.getElementsByTagName("Input");

				List<Node> insert_list = new ArrayList<Node>();
				ticCoreMappingFuncXmlOperateService.setInputInfo(nodelist,
						insert_list, formulaParser);
				for (int j = 0; j < insert_list.size() / 2; j++) {
					doc.insertBefore(insert_list.get(j * 2), insert_list
							.get(j * 2 + 1));
				}
				// 删除原始节点
				if (insert_list.size() > 0) {
					Node node2del = insert_list.get(insert_list.size() - 1);
					NodeList node_list = node2del.getChildNodes();
					if (node_list.getLength() > 0) {
						for (int j = 0; j < node_list.getLength(); j++) {
							Node child = node_list.item(j);
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								if ("?".equals(child.getTextContent())) {
									node2del.getParentNode().removeChild(
											node2del);
									break;
								}
							}
						}
					}
				}

				String resetXml = DOMHelper
						.nodeToString((org.w3c.dom.Node) doc);

				SoapInfo soapInfo = new SoapInfo();
				soapInfo.setRequestXml(resetXml);
				soapInfo.setTicSoapMain(ticSoapMain);

				/***********/
				Document res_doc = ticSoap.inputToOutputDocument(soapInfo);
				/***********/

				/*******
				 * old ****** String result=ticSoap.inputToAllXml(soapInfo);
				 * String[] mergeXml=new String[4];
				 * mergeXml[0]="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				 * mergeXml[1]="<web ID=\"!{fdId}\">".replace("!{fdId}",
				 * ticCoreMappingFunc.getFdId()); mergeXml[2]=result;
				 * mergeXml[3]="</web>"; String
				 * real_result=StringUtils.join(mergeXml); org.w3c.dom.Document
				 * res_doc= DOMHelper.parseXmlString(real_result);
				 ************************/

				NodeList res_nList = res_doc.getElementsByTagName("Output");
				boolean flagBussiness = ticCoreMappingFuncXmlOperateService
						.setOutputInfo(res_nList, formulaParser, model, true);

				NodeList fault_nList = res_doc.getElementsByTagName("Fault");
				// 程序异常
				if (fault_nList.getLength() > 0) {
					ticCoreMappingFuncXmlOperateService.setOutputInfo(
							fault_nList, formulaParser, model, true);
					Node faultNode = DOMHelper.getElementNode(fault_nList, 0);
					String faultStr = DOMHelper.nodeToString(faultNode, true);
					throw new Exception("WEBSERVICE返回错误Fault:\n" + faultStr);
				}
				// 发生了业务异常进行处理
				if (!flagBussiness) {
					ticCoreMappingFuncXmlOperateService.businessException(
							null, ticCoreMappingFunc, model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TicCoreMappingFuncExt exProgram = ticCoreMappingFunc
					.getFdExtend().get(1);
			ticCoreMappingFuncXmlOperateService.programException(e,
					exProgram, model);
		}
		return model;
	}

	@Override
    public void deleteMethodInvoke(IBaseModel model) throws Exception {
		executeInitModel(model, Integer.valueOf(Constant.INVOKE_TYPE_FORMDEL));
	}

	@Override
    public IBaseModel updateMethodInvoke(IBaseModel model) throws Exception {
		// TODO 自动生成的方法存根
		TransactionStatus status = null;
		try {
			if (null == status) {
				status = TransactionUtils.beginNewReadTransaction();
			}
			Object servie = SpringBeanUtil.getBean(model.getSysDictModel()
					.getServiceBean());
			Method method = servie.getClass().getMethod("findByPrimaryKey",
					String.class);
			IBaseModel result = (IBaseModel) method.invoke(servie, model
					.getFdId());
			String docStatus = null;
			if (PropertyUtils.isReadable(result, "docStatus")) {
				docStatus = (String) PropertyUtils.getProperty(result,
						"docStatus");
			}
			TransactionUtils.getTransactionManager().commit(status);
			status = null;
			// 驳回状态可能存在不驳回到起草节点,===
			if (SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)
					|| SysDocConstant.DOC_STATUS_REFUSE.equals(docStatus)) {
				executeInitModel(model, Integer
						.valueOf(Constant.INVOKE_TYPE_FORMSAVE));
			}
		} finally {
		}
		return model;
	}

}
