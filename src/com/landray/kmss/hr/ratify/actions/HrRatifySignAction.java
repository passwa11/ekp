package com.landray.kmss.hr.ratify.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifySignForm;
import com.landray.kmss.hr.ratify.model.HrRatifyOutSign;
import com.landray.kmss.hr.ratify.model.HrRatifySign;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyOutSignService;
import com.landray.kmss.hr.ratify.service.IHrRatifySignService;
import com.landray.kmss.hr.ratify.service.IHrRatifyYqqSignService;
import com.landray.kmss.hr.ratify.service.spring.HrRatifyYqqSignServiceImp;
import com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition;
import com.landray.kmss.sys.lbpm.engine.builder.NodeInstance;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessDefinition;
import com.landray.kmss.sys.lbpm.engine.integrate.expecterlog.ILbpmExpecterLogService;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpmservice.node.support.AbstractManualNode;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;


public class HrRatifySignAction extends HrRatifyMainAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrRatifySignAction.class);

    private IHrRatifySignService hrRatifySignService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifySignService == null) {
            hrRatifySignService = (IHrRatifySignService) getBean("hrRatifySignService");
        }
        return hrRatifySignService;
    }

	private IHrRatifyYqqSignService hrRatifyYqqSignService = null;

	public IHrRatifyYqqSignService getHrRatifyYqqSignService() {
		if (hrRatifyYqqSignService == null) {
			hrRatifyYqqSignService = (IHrRatifyYqqSignService) SpringBeanUtil
					.getBean("hrRatifyYqqSignService");
		}
		return hrRatifyYqqSignService;
	}

	private IHrRatifyOutSignService hrRatifyOutSignService = null;

	public IHrRatifyOutSignService getHrRatifyOutSignService() {
		if (hrRatifyOutSignService == null) {
			hrRatifyOutSignService = (IHrRatifyOutSignService) SpringBeanUtil
					.getBean("hrRatifyOutSignService");
		}
		return hrRatifyOutSignService;
	}
	
	private ISysOrgCoreService sysOrgCoreService = null;

	@Override
    public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifySign.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifySign.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		((IHrRatifySignService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
        HrRatifySignForm hrRatifySignForm = (HrRatifySignForm) super.createNewForm(mapping, form, request, response);
		hrRatifySignForm.setDocTemplateName(getTemplatePath(hrRatifySignForm.getDocTemplateId()));
		//签章
		String templateId = request.getParameter("i.docTemplate");
		HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService().findByPrimaryKey(templateId);
		hrRatifySignForm.setFdSignEnable(template.getFdSignEnable());
        return hrRatifySignForm;
    }

	/**
	 * 打印
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
    public ActionForward print(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			// 引入打印机制
			HrRatifySignForm ratifyForm = (HrRatifySignForm) form;
			HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(ratifyForm.getDocTemplateId());
			boolean enable = getSysPrintMainCoreService()
					.isEnablePrintTemplate(template, null, request);

			HrRatifySign main = (HrRatifySign) getServiceImp(
					null)
							.convertFormToModel(ratifyForm, null,
									new RequestContext(request));
			getSysPrintMainCoreService().initPrintData(main, ratifyForm,
					request);
			if (enable) {
				request.setAttribute("isShowSwitchBtn", "true");
			}
			// 打印日志
			getSysPrintLogCoreService().addPrintLog(main,
					new RequestContext(request));
			String printPageType = request.getParameter("_ptype");
			if (enable && !"old".equals(printPageType)) {
				return mapping.findForward("sysprint");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-print", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	protected ISysPrintMainCoreService sysPrintMainCoreService;

	@Override
    public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
			sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
					"sysPrintMainCoreService");
		}
		return sysPrintMainCoreService;
	}

	protected ISysPrintLogCoreService sysPrintLogCoreService;

	@Override
    public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
			sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
					"sysPrintLogCoreService");
		}
		return sysPrintLogCoreService;
	}
	

	public ActionForward openYqqExtendInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-openYqqExtendInfo", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String signId = request.getParameter("signId");
			HrRatifySign hrRatifySign = (HrRatifySign) getServiceImp(
					request).findByPrimaryKey(signId);

			if (hrRatifySign != null) {
				SysOrgPerson user = UserUtil.getUser();
				int processType = getProcessType(signId);
				List<SysOrgElement> signPersons = new ArrayList<>();
				if (processType == 0) {// 串行
					signPersons.add(user);
				} else if (processType == 1) {// 并行
					signPersons.add(user);
				} else if (processType == 2) {// 会审
					List<SysOrgElement> currentHandlers = getCurrentHandlers(
							signId);
					if (currentHandlers != null && currentHandlers.size() > 0) {
						signPersons = getSysOrgCoreService()
								.expandToPerson(currentHandlers);
					}
				}
				request.setAttribute("fdSigner", user);
				request.setAttribute("phone", user.getFdMobileNo());
				request.setAttribute("signPersons", signPersons);
				request.setAttribute("hrRatifySign", hrRatifySign);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-openYqqExtendInfo", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("extendinfo", mapping, form, request,
					response);
		}
	}

	/**
	 * 获取流程审批方式
	 * 
	 * @param modelId
	 * @return
	 */
	private int getProcessType(String modelId) {
		ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
				.getBean("lbpmProcessExecuteService");
		ProcessInstanceInfo processInfo = processExecuteService.load(modelId);
		ProcessDefinition processDefinition = processInfo
				.getProcessDefinitionInfo()
				.getDefinition();
		List<NodeInstance> currentNodes = processInfo.getCurrentNodes();
		for (NodeInstance node : currentNodes) {
			NodeDefinition nodeDefinition = processDefinition
					.findActivity(node.getFdActivityId());
			if (nodeDefinition instanceof AbstractManualNode) {
				AbstractManualNode curNode = (AbstractManualNode) nodeDefinition;
				return curNode.getProcessType();
			}
		}
		return -1;
	}

	/**
	 * 获取当前节点审批人
	 * 
	 * @param process
	 * @return
	 * @throws Exception
	 */
	private List<SysOrgElement> getCurrentHandlers(String signId)
			throws Exception {
		List<SysOrgElement> handlers = new ArrayList<SysOrgElement>();
		ILbpmProcessService lbpmProcessService = (ILbpmProcessService) SpringBeanUtil
				.getBean("lbpmProcessService");
		IBaseModel model = lbpmProcessService.findByPrimaryKey(
				signId, null, true);
		if (model != null && model instanceof LbpmProcess) {
			LbpmProcess process = (LbpmProcess) model;
			ILbpmExpecterLogService lbpmExpecterLogService = (ILbpmExpecterLogService) SpringBeanUtil
					.getBean("lbpmExpecterLogService");
			for (Iterator<LbpmExpecterLog> expecterLogIter = lbpmExpecterLogService
					.findActiveByProcessId(process.getFdId(), true)
					.iterator(); expecterLogIter
							.hasNext();) {
				LbpmExpecterLog lbpmExpecterLog = expecterLogIter.next();
				if (!handlers.contains(lbpmExpecterLog.getFdHandler())) {
					handlers.add(lbpmExpecterLog.getFdHandler());
				}
			}
		}
		return handlers;
	}

	public ActionForward sendYqq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-sendYqq", true, getClass());
		logger.debug("hr-Ratify-start");
		response.setCharacterEncoding("utf-8");
		KmssMessages messages = new KmssMessages();
		JSONObject rtnObject = new JSONObject();
		Boolean sendStatus = true;
		try {
			String signId = request.getParameter("signId");
			String datas = request.getParameter("data");
			logger.debug("hr-Ratify-content:" + signId + "---------" + datas);
			JSONObject json = JSONObject.parseObject(datas);
			SysOrgPerson singer = UserUtil.getUser();
			String fdNeedEnterpris=json.getString("fdNeedEnterprise");
			HrRatifySign kmImissiveSendMain = (HrRatifySign) getServiceImp(
					request).findByPrimaryKey(signId);
			sendStatus = getHrRatifyYqqSignService().sendYqq(kmImissiveSendMain,
					null, json);
			logger.debug("hr-Ratify-content:" + sendStatus);
			if (sendStatus) {
				addOutSign(signId, singer, fdNeedEnterpris);
			}
			rtnObject.put("signId", signId);
			request.setAttribute("signId", signId);
		} catch (Exception e) {
			messages.addError(e);
			logger.debug("Exception" + e.getMessage());
		}
		TimeCounter.logCurrentTime("Action-sendYqq", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("error", mapping, form, request, response);
		}else {
			rtnObject.put("sendStatus", String.valueOf(sendStatus));
			response.getWriter().println(rtnObject.toString());
			return null;
		}
	}

	private void addOutSign(String signId, SysOrgElement docCreator,String fdNeedEnterpris)
			throws Exception {
		// 记录签订状态
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrRatifyOutSign.fdMainId=:fdMainId and hrRatifyOutSign.docCreator.fdId =:docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", docCreator.getFdId());
		
		List<HrRatifyOutSign> hrRatifyOutSignList = getHrRatifyOutSignService()
				.findList(hqlInfo);
		if (hrRatifyOutSignList != null
				&& hrRatifyOutSignList.size() > 0) {
			return;
		}
		HrRatifyOutSign outsign = new HrRatifyOutSign();
		outsign.setFdMainId(signId);
		outsign.setFdStatus(HrRatifyYqqSignServiceImp.status_code_init);// 发起签订(初始状态)
		outsign.setFdType(HrRatifyYqqSignServiceImp.outsign_type_yqq);
		outsign.setDocCreator(docCreator);
		outsign.setFdNeedEnterpris(fdNeedEnterpris);
		getHrRatifyOutSignService().add(outsign);
	}
}
