package com.landray.kmss.hr.ratify.actions;

import com.landray.kmss.common.actions.CategoryNodeAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.ratify.forms.HrRatifyMainForm;
import com.landray.kmss.hr.ratify.model.HrRatifyChange;
import com.landray.kmss.hr.ratify.model.HrRatifyFire;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyOther;
import com.landray.kmss.hr.ratify.model.HrRatifyRehire;
import com.landray.kmss.hr.ratify.model.HrRatifyRemove;
import com.landray.kmss.hr.ratify.model.HrRatifyRetire;
import com.landray.kmss.hr.ratify.model.HrRatifySign;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyFeedbackService;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HrRatifyMainAction extends CategoryNodeAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrRatifyMainAction.class);

    private IHrRatifyMainService hrRatifyMainService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyMainService == null) {
            hrRatifyMainService = (IHrRatifyMainService) getBean("hrRatifyMainService");
        }
        return hrRatifyMainService;
    }

	private IHrRatifyTemplateService hrRatifyTemplateService;

	public IHrRatifyTemplateService getHrRatifyTemplateService() {
		if (hrRatifyTemplateService == null) {
			hrRatifyTemplateService = (IHrRatifyTemplateService) getBean(
					"hrRatifyTemplateService");
		}
		return hrRatifyTemplateService;
	}

	protected ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private IHrRatifyFeedbackService hrRatifyFeedbackService;

	protected IHrRatifyFeedbackService getHrRatifyFeedbackService() {
		if (hrRatifyFeedbackService == null) {
			hrRatifyFeedbackService = (IHrRatifyFeedbackService) getBean(
					"hrRatifyFeedbackService");
		}
		return hrRatifyFeedbackService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	protected IHrStaffPersonInfoService getHrStaffPersonInfoService(HttpServletRequest request) {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyMain.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyMain.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
		CriteriaValue cv = new CriteriaValue(request);
		String doctype = cv.poll("doctype");
		String feedStatus = cv.poll("feedStatus");
		String whereStr = hqlInfo.getWhereBlock();
		StringBuilder hql = new StringBuilder(
				StringUtil.isNull(whereStr) ? "1=1 " : whereStr);
		if (StringUtil.isNotNull(doctype)) {
			if ("follow".equals(doctype)) {
				StringBuffer buff = new StringBuffer();
				if (StringUtil.isNotNull(hqlInfo.getJoinBlock())) {
					buff.append(hqlInfo.getJoinBlock());
				}
				buff.append(
						", com.landray.kmss.sys.lbpmservice.support.model.LbpmFollow lbpmFollow ");
				hqlInfo.setJoinBlock(buff.toString());
				hql.append(
						" and lbpmFollow.fdProcessId = hrRatifyMain.fdId and lbpmFollow.fdWatcher.fdId =:fdWatcher");
				hqlInfo.setParameter("fdWatcher", UserUtil.getUser().getFdId());
			} else if ("feedback".equals(doctype)) {
				if ("41".equals(feedStatus)) { // 未反馈
					hql.append(" and hrRatifyMain.docStatus = '30'");
				} else {
					hql.append(" and hrRatifyMain.docStatus = '31'");
				}
			}
		}
		String fdIsFile = cv.poll("fdIsFile");
		if (StringUtil.isNotNull(fdIsFile)) {
			if ("1".equals(fdIsFile)) {
				hql.append(" and hrRatifyMain.fdIsFiling =:fdIsFiling");
				hqlInfo.setParameter("fdIsFiling", true);
			} else if ("0".equals(fdIsFile)) {
				hql.append(
						" and (hrRatifyMain.fdIsFiling =:fdIsFiling or hrRatifyMain.fdIsFiling is null)");
				hqlInfo.setParameter("fdIsFiling", false);
			}
		}
		hqlInfo.setSelectBlock(
				"hrRatifyMain.fdId,hrRatifyMain.docSubject,hrRatifyMain.docNumber,hrRatifyMain.docCreator.fdName,hrRatifyMain.docCreator.fdId,hrRatifyMain.docCreateTime,hrRatifyMain.docPublishTime,hrRatifyMain.docStatus,hrRatifyMain.fdSubclassModelname");
		String mobile = request.getParameter("mobile");
		if (StringUtil.isNotNull(mobile)) {
			List<String> valueList = new ArrayList<String>();
			if ("contract".equals(mobile)) {
				valueList.add(HrRatifySign.class.getName());
				valueList.add(HrRatifyChange.class.getName());
				valueList.add(HrRatifyRemove.class.getName());
			}
			if ("other".equals(mobile)) {
				valueList.add(HrRatifyFire.class.getName());
				valueList.add(HrRatifyRetire.class.getName());
				valueList.add(HrRatifyRehire.class.getName());
				valueList.add(HrRatifyOther.class.getName());
			}
			if (!valueList.isEmpty()) {
				hql.append(" and " + HQLUtil.buildLogicIN(
						"hrRatifyMain.fdSubclassModelname", valueList));
			}
		}
		hqlInfo.setWhereBlock(hql.toString());
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrRatifyMainForm hrRatifyMainForm = (HrRatifyMainForm) super.createNewForm(mapping, form, request, response);
        ((IHrRatifyMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
		String docTemplateId = hrRatifyMainForm.getDocTemplateId();
		if (StringUtil.isNotNull(docTemplateId)) {
			HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(docTemplateId);
			if (hrRatifyTemplate != null) {
				request.setAttribute("fdTempKey",
						hrRatifyTemplate.getFdTempKey());
				hrRatifyMainForm
						.setTitleRegulation(
								hrRatifyTemplate.getTitleRegulation());
				hrRatifyMainForm
				.setDocTemplateName(getTemplatePath(docTemplateId));
				// 解析实施反馈人
				List fdFeedbacks = hrRatifyTemplate.getFdFeedback();
				List list = null;
				if (fdFeedbacks != null) {
					list = getSysOrgCoreService().parseSysOrgRole(fdFeedbacks,
							getSysOrgCoreService()
									.findByPrimaryKey(
											UserUtil.getUser().getFdId()));
				}
				if (list != null && !list.isEmpty()) {
					String[] orgArray = ArrayUtil.joinProperty(list,
							"fdId:deptLevelNames", ";");
					hrRatifyMainForm.setFdFeedbackIds(orgArray[0]);
					hrRatifyMainForm.setFdFeedbackNames(orgArray[1]);
				} else {
					hrRatifyMainForm.setFdFeedbackIds(null);
					hrRatifyMainForm.setFdFeedbackNames(null);
				}
			}
		}
        return hrRatifyMainForm;
    }

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		HrRatifyMainForm hrRatifyMainForm = (HrRatifyMainForm) form;
		String docTemplateId = hrRatifyMainForm.getDocTemplateId();
		HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) getHrRatifyTemplateService()
				.findByPrimaryKey(docTemplateId);
		request.setAttribute("fdTempKey", hrRatifyTemplate.getFdTempKey());
		hrRatifyMainForm.setDocTemplateName(getTemplatePath(docTemplateId));
		int fdReviewFeedbackInfoCount = getHrRatifyFeedbackService()
				.getKmReviewFeedbackInfoCount(hrRatifyMainForm.getFdId());
		if (fdReviewFeedbackInfoCount > 0) {
			hrRatifyMainForm.setFdReviewFeedbackInfoCount(
					"(" + String.valueOf(fdReviewFeedbackInfoCount)
							+ ")");
		}
		// 判断是否集成了易企签
		Boolean yqqFlag = false;
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.elec.device.contractService",
				IElecChannelRequestMessage.class.getName(), "convertor");
		for (IExtension extension : extensions) {
			String channel = Plugin.getParamValueString(extension,"channel");
			if ("yqq".equals(channel)) {
				yqqFlag = true;
				break;
			}
		}
		request.setAttribute("yqqFlag", String.valueOf(yqqFlag));
	}

	// 获取模板的分类全路径
	public String getTemplatePath(String docTemplateId) throws Exception {
		String templatePath = "";
		if (StringUtil.isNotNull(docTemplateId)) {
			HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(docTemplateId);
			if (hrRatifyTemplate != null) {
				SysCategoryMain sysCategoryMain = hrRatifyTemplate
						.getDocCategory();
				SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain
						.getFdParent();
				if (sysCategoryBaseModel != null) {
					do {
						templatePath = sysCategoryBaseModel.getFdName() + "/"
								+ templatePath;
						sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel
								.getFdParent();
					} while (sysCategoryBaseModel != null);
					templatePath = templatePath + sysCategoryMain.getFdName()
							+ "/"
							+ hrRatifyTemplate.getFdName();
				} else {
					templatePath = sysCategoryMain.getFdName() + "/"
							+ hrRatifyTemplate.getFdName();
				}

			}
		}
		return templatePath;
	}

	@Override
	protected String getParentProperty() {
		return "docTemplate";
	}

	protected HrRatifyTemplate hrRatifyTemplate;

	public ActionForward loadRatifyTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tempId = request.getParameter("tempId");
		String url = null;
		hrRatifyTemplate = (HrRatifyTemplate) getHrRatifyTemplateService()
				.findByPrimaryKey(tempId);
		if (hrRatifyTemplate != null) {
			Map<String, String> urlValues = new HashMap<String, String>();
			urlValues.put("HrRatifyEntryDoc", getUrlByParameters("Entry"));
			urlValues.put("HrRatifyPositiveDoc",
					getUrlByParameters("Positive"));
			urlValues.put("HrRatifyTransferDoc",
					getUrlByParameters("Transfer"));
			urlValues.put("HrRatifyLeaveDoc", getUrlByParameters("Leave"));
			urlValues.put("HrRatifyFireDoc", getUrlByParameters("Fire"));
			urlValues.put("HrRatifyRetireDoc", getUrlByParameters("Retire"));
			urlValues.put("HrRatifyRehireDoc", getUrlByParameters("Rehire"));
			urlValues.put("HrRatifySalaryDoc", getUrlByParameters("Salary"));
			urlValues.put("HrRatifySignDoc", getUrlByParameters("Sign"));
			urlValues.put("HrRatifyChangeDoc", getUrlByParameters("Change"));
			urlValues.put("HrRatifyRemoveDoc", getUrlByParameters("Remove"));
			urlValues.put("HrRatifyOtherDoc", getUrlByParameters("Other"));
			String fdTemplateKey = hrRatifyTemplate.getFdTempKey();
			url = urlValues.get(fdTemplateKey);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(url);
		return null;
	}

	/**
	 * 获取对应流程的url
	 * 
	 * @param applyType
	 * @return
	 */
	public String getUrlByParameters(String applyType) {
		StringBuffer url = new StringBuffer();
		url.append("hr/ratify/hr_ratify_").append(applyType.toLowerCase())
				.append("/hrRatify").append(applyType)
				.append(".do?method=add&i.docTemplate=")
				.append(hrRatifyTemplate.getFdId());
		return url.toString();
	}

	public ActionForward view4Main(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view4Main", true, getClass());
		KmssMessages messages = new KmssMessages();
		String viewUrl = "";
		try {
			String fdId = request.getParameter("fdId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("hrRatifyMain.fdSubclassModelname");
			hqlInfo.setWhereBlock("hrRatifyMain.fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			List<String> list = getServiceImp(request).findList(hqlInfo);
			String fdSubclassModelname = list.get(0);
			String[] packages = fdSubclassModelname.split("[.]");
			String clz = packages[packages.length - 1];
			clz = clz.substring(8);
			viewUrl = "/hr/ratify/hr_ratify_" + clz.toLowerCase()
					+ "/hrRatify" + clz + ".do?method=view&fdId=" + fdId;
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-view4Main", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			ActionForward word = new ActionForward();
			word.setRedirect(true);
			word.setPath(viewUrl);
			return word;
		}
	}


	/**
	 * 流程门户部件数据源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.data(mapping, form, request, response);
		return getActionForward("listPortlet", mapping, form, request,
				response);
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
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			// 引入打印机制
			HrRatifyMainForm ratifyForm = (HrRatifyMainForm) form;
			HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(ratifyForm.getDocTemplateId());
			boolean enable = getSysPrintMainCoreService()
					.isEnablePrintTemplate(template, null, request);

			HrRatifyMain main = (HrRatifyMain) getServiceImp(
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

	public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
			sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
					"sysPrintMainCoreService");
		}
		return sysPrintMainCoreService;
	}

	protected ISysPrintLogCoreService sysPrintLogCoreService;

	public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
			sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
					"sysPrintLogCoreService");
		}
		return sysPrintLogCoreService;
	}

	/**
	 * 归档模板页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printFileDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			String saveApprovalStr = request.getParameter("saveApproval");
			boolean saveApproval = "1".equals(saveApprovalStr);
			request.setAttribute("saveApproval", saveApproval);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("filePrint", mapping, form, request,
					response);
		}
	}

	/**
	 * 修改反馈人
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String feedbackIds = ((HrRatifyMainForm) form).getFdFeedbackIds();
			String mainId = request.getParameter("fdId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("hrRatifyMain.fdSubclassModelname");
			hqlInfo.setWhereBlock("hrRatifyMain.fdId=:fdId");
			hqlInfo.setParameter("fdId", mainId);
			List<String> list = getServiceImp(request).findList(hqlInfo);
			String fdSubclassModelname = list.get(0);
			SysDictModel sysDictModel = SysDataDict.getInstance()
					.getModel(fdSubclassModelname);
			IHrRatifyMainService mainService = (IHrRatifyMainService) SpringBeanUtil
					.getBean(sysDictModel.getServiceBean());
			HrRatifyMain main = (HrRatifyMain) mainService
					.findByPrimaryKey(mainId);
			List feedbackList = main.getFdFeedback();
			List notifyList = new ArrayList();
			String[] ids = feedbackIds.split(";");
			for (int i = 0; i < ids.length; i++) {
				SysOrgElement feedbackMan = (SysOrgElement) getSysOrgCoreService()
						.findByPrimaryKey(ids[i]);
				if (!feedbackList.contains(feedbackMan)) {
					feedbackList.add(feedbackMan);
					notifyList.add(feedbackMan);
				}
			}
			main.setFdNotifyType(((HrRatifyMainForm) form).getFdNotifyType());
			main.setFdFeedbackExecuted(new Long(1));
			hrRatifyMainService.updateFeedbackPeople(main, notifyList);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			return mapping.findForward("failure");
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");
	}

	/**
	 * <p>人事流程移动端首页</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward getRatifyMobileIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getRatifyMobileIndex", true, getClass());
		JSONArray ja = new JSONArray();
		try {
			ja = ((IHrRatifyMainService) getServiceImp(request)).getRatifyMobileIndex();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-getRatifyMobileIndex", false, getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(ja.toString());
		return null;
	}

	public ActionForward getCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getCount", true, getClass());
		JSONObject json = new JSONObject();
		try {
			String modelKey = request.getParameter("modelKey");
			String mobileKey = request.getParameter("mobileKey");
			String modelName = "";
			String prefix = "com.landray.kmss.hr.ratify.model.HrRatify";
			switch (modelKey) {
			case "entry":
				modelName = prefix + "Entry";
				break;
			case "positive":
				modelName = prefix + "Positive";
				break;
			case "transfer":
				modelName = prefix + "Transfer";
				break;
			case "leave":
				modelName = prefix + "Leave";
				break;
			case "salary":
				modelName = prefix + "Salary";
				break;
			case "main":
				if ("contract".equals(mobileKey)) {
					modelName = prefix + "Sign;" + prefix + "Remove;" + prefix
							+ "Change";
				}
				if ("other".equals(mobileKey)) {
					modelName = prefix + "Rehire;" + prefix + "Retire;" + prefix
							+ "Fire;" + prefix + "Other";
				}
				break;
			default:
				break;
			}
			json = ((IHrRatifyMainService) getServiceImp(request))
					.getCount(modelName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		TimeCounter.logCurrentTime("Action-getCount", false, getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(json.toString());
		return null;
	}
}
