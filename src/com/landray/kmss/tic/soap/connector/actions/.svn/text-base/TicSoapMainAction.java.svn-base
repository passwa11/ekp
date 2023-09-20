package com.landray.kmss.tic.soap.connector.actions;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.tic.core.common.actions.TicExtendAction;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.service.ITicCoreTransSettService;
import com.landray.kmss.tic.core.util.XmlConvertToJsonUtil;
import com.landray.kmss.tic.soap.connector.forms.TicSoapMainForm;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapQueryService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import com.landray.kmss.tic.soap.executor.SoapDispatcherExecutor;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * WEBSERVCIE服务函数 Action
 * 
 * @author
 * @version 1.0 2012-08-06
 */
public class TicSoapMainAction extends TicExtendAction {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SoapDispatcherExecutor.class);

	protected ITicSoapMainService TicSoapMainService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicSoapMainService == null) {
			TicSoapMainService = (ITicSoapMainService) getBean("ticSoapMainService");
		}
		return TicSoapMainService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicSoapMainForm ticSoapMainForm = (TicSoapMainForm) form;
		ticSoapMainForm.reset(mapping, request);
		ticSoapMainForm.setDocCreatorId(UserUtil.getKMSSUser().getUserId());
		ticSoapMainForm.setDocCreatorName(UserUtil.getKMSSUser()
				.getUserName());
		ticSoapMainForm.setDocCreateTime(DateUtil.convertDateToString(
				new Date(), null, null));
		return ticSoapMainForm;
	}

	
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql=hqlInfo.getWhereBlock();
		if(!StringUtil.isNull(categoryId)){
			hql=StringUtil.linkString(hql, " and ", "ticSoapMain.docCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%"+categoryId+"%");
		}
		hqlInfo.setWhereBlock(hql);
		
		
		
	}

	/**
	 * 生成新版本 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward newEdition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String originId = request.getParameter("originId");
			if (StringUtil.isNull(originId)) {
				throw new NoRecordException();
			}
			TicSoapMainForm TicSoapMainForm = (TicSoapMainForm) form;
			TicSoapMain TicSoapMain = (TicSoapMain) getServiceImp(request)
					.findByPrimaryKey(originId);
			TicSoapMainForm mainForm = new TicSoapMainForm();
			mainForm = (TicSoapMainForm) getServiceImp(request)
					.cloneModelToForm(TicSoapMainForm, TicSoapMain,
							new RequestContext(request));
			mainForm.setMethod("add");
			mainForm.setMethod_GET("add");
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	public ActionForward viewQueryEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewQueryEdit", true, getClass());
		KmssMessages messages = new KmssMessages();
		String funcId = request.getParameter("funcId");
		
		ITicSoapMainService TicSoapMainService=(ITicSoapMainService)SpringBeanUtil.getBean("ticSoapMainService");
		TicSoapMain TicSoapMain=(TicSoapMain) TicSoapMainService.findByPrimaryKey(funcId);
		String idXml=TicSoapMain.getWsMapperTemplate();
		logger.debug("idXml1:" + idXml);
		Pattern p = Pattern.compile(">\\s*[?]<");
		Matcher m = p.matcher(idXml);
		idXml = m.replaceAll("><");

		logger.debug("idXml2:" + idXml);
		// 移除禁用的节点
		idXml = ParseSoapXmlUtil.disableFilter(idXml);
		request.setAttribute("ticSoapMainId", funcId);
		request.setAttribute("ticSoapMainName", TicSoapMain.getFdName());
		request.setAttribute("idXml", idXml);

		TimeCounter.logCurrentTime("Action-viewQueryEdit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewQuery", mapping, form, request,
					response);
		}
	}
	
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicSoapMainForm ticSoapMainForm = (TicSoapMainForm) form;
			ticSoapMainForm.setWsSoapVersion("SOAP 1.1");

			try {
				updateNamespace(ticSoapMainForm);
			} catch (Exception e) {
				logger.error("", e);
			}

			ticSoapMainForm.setFdAppType(request.getParameter("fdAppType"));
			getServiceImp(request).add(ticSoapMainForm,
					new RequestContext(request));

			// 更新传入传出json信息
			((ITicSoapMainService) getServiceImp(request))
					.updateImportExportJson(((IExtendForm) form).getFdId());
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService"))
								.addTicCoreTransSett(
										(TicCoreFuncBase)
								((ITicSoapMainService) getServiceImp(request))
						.findByPrimaryKey(((IExtendForm) form).getFdId()));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	/**
	 * 实现编辑后还可以返回编辑
	 */
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			TicSoapMainForm ticSoapMainForm = (TicSoapMainForm) form;
			try {
				updateNamespace(ticSoapMainForm);
			} catch (Exception e) {
				logger.error("", e);
			}

			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			// 更新传入传出json信息
			((ITicSoapMainService) getServiceImp(request))
					.updateImportExportJson(((IExtendForm) form).getFdId());
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService"))
								.addTicCoreTransSett(
										(TicCoreFuncBase) ((ITicSoapMainService) getServiceImp(
												request))
														.findByPrimaryKey(
																((IExtendForm) form)
																		.getFdId()));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	/**
	 * 主要用于之前保存时可用的webservice服务，当再次编辑时已经不可用了，则给出错误提示
	 */
	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			TicSoapMainForm mainForm = (TicSoapMainForm)form;
			ITicSoapSettingService TicSoapSettingService = (ITicSoapSettingService)SpringBeanUtil.getBean("ticSoapSettingService");
			TicSoapSetting sett = (TicSoapSetting) TicSoapSettingService.findByPrimaryKey(mainForm.getWsServerSettingId());
			ITicSoap TicSoap=(ITicSoap)SpringBeanUtil.getBean("ticSoap");
			TicSoap.getAllOperation(sett, mainForm.getWsSoapVersion());
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
    public ActionForward saveadd(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicSoapMainForm ticSoapMainForm = (TicSoapMainForm) form;
			ticSoapMainForm.setWsSoapVersion("SOAP 1.1");
			ticSoapMainForm.setFdAppType(request.getParameter("fdAppType"));
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			// 更新传入传出json信息
			((ITicSoapMainService) getServiceImp(request))
					.updateImportExportJson(((IExtendForm) form).getFdId());
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService")).addTicCoreTransSett(
								(TicCoreFuncBase) ((ITicSoapMainService) getServiceImp(
										request))
												.findByPrimaryKey(
														((IExtendForm) form)
																.getFdId()));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			for (String id : ids) {
				boolean noTransSett = ((ITicCoreTransSettService) SpringBeanUtil
						.getBean("ticCoreTransSettService"))
								.validateTransSett(id);
				boolean noQeury = ((ITicSoapQueryService) SpringBeanUtil
						.getBean("ticSoapQueryService")).validateQuery(id);
				if (noTransSett && noQeury) {
				} else {
					throw new KmssException(
							new KmssMessage(
									"tic-core-common:function.delete.fail"));
				}
			}
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			boolean noTransSett = ((ITicCoreTransSettService) SpringBeanUtil
					.getBean("ticCoreTransSettService"))
							.validateTransSett(id);
			boolean noQeury = ((ITicSoapQueryService) SpringBeanUtil
					.getBean("ticSoapQueryService")).validateQuery(id);
			if (noTransSett && noQeury) {
			} else {
				throw new KmssException(
						new KmssMessage(
								"tic-core-common:function.delete.fail"));
			}
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public String getDataList(String serviceId, String localPart,
			String curFdId) throws Exception {
		try {
			String soapversion = "SOAP 1.1";
			if (StringUtil.isNotNull(serviceId)) {
				ITicSoapSettingService ticSoapSettingService = (ITicSoapSettingService) SpringBeanUtil
						.getBean("ticSoapSettingService");
				TicSoapSetting TicSoapSetting = (TicSoapSetting) ticSoapSettingService
						.findByPrimaryKey(serviceId);
				ITicSoap TicSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
				String xml = null;
				if (TicSoapSetting.getFdProtectWsdl()) {
					xml = TicSoap.toAllXmlTemplate(TicSoapSetting, localPart,
							soapversion);
				} else {
					xml = TicSoap.toAllXmlTemplate(TicSoapSetting, localPart,
							soapversion);
				}

				String[] mergeXml = new String[4];
				mergeXml[0] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				mergeXml[1] = "<web ID=\"!{fdId}\">".replace("!{fdId}",
						curFdId);
				mergeXml[2] = xml;
				mergeXml[3] = "</web>";
				String result = StringUtils.join(mergeXml);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void updateNamespace(TicSoapMainForm ticSoapMainForm)
			throws Exception {
		String serviceId = ticSoapMainForm.getWsServerSettingId();
		String wsBindFunc = ticSoapMainForm.getWsBindFunc();
		String funcXml = getDataList(serviceId, wsBindFunc,
				ticSoapMainForm.getFdId());
		if (StringUtil.isNotNull(funcXml)) {
			String xml = ticSoapMainForm.getWsMapperTemplate();
			Document document_func = DocumentHelper.parseText(funcXml);
			Document document_now = DocumentHelper.parseText(xml);
			// 更新XML里面的命名空间
			Map<String, String> nameSpaces = XmlConvertToJsonUtil
					.getXmlNamespaceSetToDocument(
							XmlConvertToJsonUtil.INPUT,
							document_func);
			Set<String> names = nameSpaces.keySet();
			Element envelope = (Element) ((Element) document_now
					.selectSingleNode(XmlConvertToJsonUtil.INPUT)).elements()
							.get(0);
			for (String name : names) {
				Namespace ns = new Namespace(name, nameSpaces.get(name));
				envelope.remove(ns);
				envelope.addNamespace(name, nameSpaces.get(name));
			}

			nameSpaces = XmlConvertToJsonUtil.getXmlNamespaceSetToDocument(
					XmlConvertToJsonUtil.OUTPUT,
					document_func);
			names = nameSpaces.keySet();
			envelope = (Element) ((Element) document_now
					.selectSingleNode(XmlConvertToJsonUtil.OUTPUT)).elements()
							.get(0);
			for (String name : names) {
				Namespace ns = new Namespace(name, nameSpaces.get(name));
				envelope.remove(ns);
				envelope.addNamespace(name, nameSpaces.get(name));
			}
			ticSoapMainForm.setWsMapperTemplate(document_now.asXML());
		}
		// System.out.println("111:" + ticSoapMainForm.getWsMapperTemplate());
	}
}
