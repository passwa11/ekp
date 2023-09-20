package com.landray.kmss.km.archives.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.archives.forms.KmArchivesAppraiseDetailsForm;
import com.landray.kmss.km.archives.forms.KmArchivesAppraiseForm;
import com.landray.kmss.km.archives.model.KmArchivesAppraise;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseService;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseTemplateService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class KmArchivesAppraiseAction extends ExtendAction {

    private IKmArchivesAppraiseService kmArchivesAppraiseService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesAppraiseService == null) {
            kmArchivesAppraiseService = (IKmArchivesAppraiseService) getBean("kmArchivesAppraiseService");
        }
        return kmArchivesAppraiseService;
    }

	private IKmArchivesMainService kmArchivesMainService;

	public IKmArchivesMainService getKmArchivesMainService() {
		if (kmArchivesMainService == null) {
			kmArchivesMainService = (IKmArchivesMainService) getBean(
					"kmArchivesMainService");
		}
		return kmArchivesMainService;
	}

	private IKmArchivesAppraiseTemplateService kmArchivesAppraiseTemplateService;

	public IKmArchivesAppraiseTemplateService
			getKmArchivesAppraiseTemplateService() {
		if (kmArchivesAppraiseTemplateService == null) {
			kmArchivesAppraiseTemplateService = (IKmArchivesAppraiseTemplateService) getBean(
					"kmArchivesAppraiseTemplateService");
		}
		return kmArchivesAppraiseTemplateService;
	}

	private ICoreOuterService dispatchCoreService;

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean(
					"dispatchCoreService");
		}
		return dispatchCoreService;
	}

	private IKmArchivesAppraiseDetailsService kmArchivesAppraiseDetailsService;

	public IKmArchivesAppraiseDetailsService
			getKmArchivesAppraiseDetailsService() {
		if (kmArchivesAppraiseDetailsService == null) {
			kmArchivesAppraiseDetailsService = (IKmArchivesAppraiseDetailsService) getBean(
					"kmArchivesAppraiseDetailsService");
		}
		return kmArchivesAppraiseDetailsService;
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request,
									  HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String isRecord = request.getParameter("isRecord");
		if ("true".equals(isRecord)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmArchivesAppraise.docTemplate is null");
		} else {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmArchivesAppraise.docTemplate is not null");
		}
		CriteriaValue cv = new CriteriaValue(request);
		String fdArchivesNumber = cv.poll("archivesNumber");
		if (StringUtil.isNotNull(fdArchivesNumber)) {
			HQLInfo info = new HQLInfo();
			info.setSelectBlock("kmArchivesMain.fdId");
			info.setWhereBlock("kmArchivesMain.docNumber like :docNumber");
			info.setParameter("docNumber", "%" + fdArchivesNumber + "%");
			List mainList = getKmArchivesMainService().findList(info);
			if (mainList != null && mainList.size() > 0) {
				HQLInfo valueInfo = new HQLInfo();
				valueInfo.setSelectBlock(
						"kmArchivesAppraiseDetails.docMain.fdId");
				valueInfo.setWhereBlock(
						HQLUtil.buildLogicIN(
								"kmArchivesAppraiseDetails.fdArchives.fdId",
								mainList));
				List valueList = getKmArchivesAppraiseDetailsService()
						.findList(valueInfo);
				if (valueList != null && valueList.size() > 0) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							HQLUtil.buildLogicIN(
									"kmArchivesAppraise.fdId",
									valueList));
				} else {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							"1 = 2");
				}
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"1 = 2");
			}
		}
		
		String docStatus = cv.poll("docStatus");
		if (StringUtil.isNotNull(docStatus)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmArchivesAppraise.docStatus =:docStatus");
			hqlInfo.setParameter("docStatus", docStatus);
		}

		hqlInfo.setWhereBlock(whereBlock);
		HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesAppraise.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

	@Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmArchivesAppraiseForm kmArchivesAppraiseForm = (KmArchivesAppraiseForm) super.createNewForm(
				mapping, form, request, response);
		((IKmArchivesAppraiseService) getServiceImp(request)).initFormSetting(
				kmArchivesAppraiseForm, new RequestContext(request));
        return kmArchivesAppraiseForm;
    }

	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();

		String type = request.getParameter("type");
		try {
			if (StringUtil.isNull(type)) {
				ActionForm newForm = createNewForm(mapping, form, request,
						response);
				if (newForm != form) {
                    request.setAttribute(getFormName(newForm, request),
                            newForm);
                }
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {

			if (StringUtil.isNotNull(type) && "change".equals(type)) {
				return change(mapping, form, request, response);
			} else {
				return getActionForward("edit", mapping, form, request,
						response);
			}
		}
	}

	public ActionForward change(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-change", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			KmArchivesAppraiseForm kmArchivesAppraiseForm = (KmArchivesAppraiseForm) form;

			String docTemplateId = request.getParameter("docTemplateId");

			if (StringUtil.isNotNull(docTemplateId)) {
				KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate = (KmArchivesAppraiseTemplate) getKmArchivesAppraiseTemplateService()
						.findByPrimaryKey(docTemplateId);
				kmArchivesAppraiseForm.setDocTemplateId(docTemplateId);
				kmArchivesAppraiseForm
						.setDocTemplateName(
								kmArchivesAppraiseTemplate.getFdName());
				kmArchivesAppraiseForm
						.setDocCreatorId(UserUtil.getUser().getFdId());
				kmArchivesAppraiseForm
						.setDocCreatorName(UserUtil.getUser().getFdName());
				kmArchivesAppraiseForm
						.setDocCreateTime(DateUtil.convertDateToString(
								new Date(), DateUtil.TYPE_DATETIME,
								request.getLocale()));
				kmArchivesAppraiseForm.setMethod_GET("add");
				// 启动模板流程
				kmArchivesAppraiseForm.getSysWfBusinessForm()
						.setCanStartProcess("true");
				getDispatchCoreService().initFormSetting(
						kmArchivesAppraiseForm, "kmArchivesAppraise",
						kmArchivesAppraiseTemplate, "kmArchivesAppraise",
						new RequestContext(request));
			}

			// #67953 借阅界面变更流程后，档案明细表部分字段为空；出现了不正常的审批记录界面
//			List<KmArchivesAppraiseDetailsForm> detailForms = kmArchivesAppraiseForm
//					.getFdAppraiseDetail_Form();
//			for (KmArchivesAppraiseDetailsForm detailForm : detailForms) {
//				KmArchivesMain kmArchivesMain = (KmArchivesMain) getKmArchivesMainService()
//						.findByPrimaryKey(detailForm.getFdArchivesId());
//				detailForm.setFdArchives(kmArchivesMain);
//			}
			
			// #108006 借阅流程和密级程度绑定，所以变更流程后需要清空借阅明细（变更后的流程的密级程度可能和之前所选的档案的密级程度不匹配）
			kmArchivesAppraiseForm.setFdAppraiseDetail_Form(
					new AutoArrayList(KmArchivesAppraiseDetailsForm.class));

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	public ActionForward printAppraiseList(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String selectIdstr = request.getParameter("selectIds");
			if (StringUtil.isNull(selectIdstr)) {
                throw new NoRecordException();
            }
			String[] selectIds = selectIdstr.split(";");
			request.setAttribute("appraiseList",
					getServiceImp(request).findByPrimaryKeys(selectIds));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("print", mapping, form, request,
					response);
		}
	}
}
