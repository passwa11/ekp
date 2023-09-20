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
import com.landray.kmss.km.archives.forms.KmArchivesDestroyDetailsForm;
import com.landray.kmss.km.archives.forms.KmArchivesDestroyForm;
import com.landray.kmss.km.archives.model.KmArchivesDestroy;
import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyService;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyTemplateService;
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

public class KmArchivesDestroyAction extends ExtendAction {

    private IKmArchivesDestroyService kmArchivesDestroyService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmArchivesDestroyService == null) {
            kmArchivesDestroyService = (IKmArchivesDestroyService) getBean("kmArchivesDestroyService");
        }
        return kmArchivesDestroyService;
    }

	private IKmArchivesMainService kmArchivesMainService;

	public IKmArchivesMainService getKmArchivesMainService() {
		if (kmArchivesMainService == null) {
			kmArchivesMainService = (IKmArchivesMainService) getBean(
					"kmArchivesMainService");
		}
		return kmArchivesMainService;
	}

	private IKmArchivesDestroyTemplateService kmArchivesDestroyTemplateService;

	public IKmArchivesDestroyTemplateService
			getKmArchivesDestroyTemplateService() {
		if (kmArchivesDestroyTemplateService == null) {
			kmArchivesDestroyTemplateService = (IKmArchivesDestroyTemplateService) getBean(
					"kmArchivesDestroyTemplateService");
		}
		return kmArchivesDestroyTemplateService;
	}

	private ICoreOuterService dispatchCoreService;

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean(
					"dispatchCoreService");
		}
		return dispatchCoreService;
	}

	private IKmArchivesDestroyDetailsService kmArchivesDestroyDetailsService;

	public IKmArchivesDestroyDetailsService
			getKmArchivesDestroyDetailsService() {
		if (kmArchivesDestroyDetailsService == null) {
			kmArchivesDestroyDetailsService = (IKmArchivesDestroyDetailsService) getBean(
					"kmArchivesDestroyDetailsService");
		}
		return kmArchivesDestroyDetailsService;
	}

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String isRecord = request.getParameter("isRecord");
		if ("true".equals(isRecord)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmArchivesDestroy.docTemplate is null");
		} else {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmArchivesDestroy.docTemplate is not null");
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
						"kmArchivesDestroyDetails.docMain.fdId");
				valueInfo.setWhereBlock(
						HQLUtil.buildLogicIN(
								"kmArchivesDestroyDetails.fdArchives.fdId",
								mainList));
				List valueList = getKmArchivesDestroyDetailsService()
						.findList(valueInfo);
				if (valueList != null && valueList.size() > 0) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							HQLUtil.buildLogicIN(
									"kmArchivesDestroy.fdId",
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
					"kmArchivesDestroy.docStatus =:docStatus");
			hqlInfo.setParameter("docStatus", docStatus);
		}
		hqlInfo.setWhereBlock(whereBlock);
		HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesDestroy.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }


	@Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmArchivesDestroyForm kmArchivesDestroyForm = (KmArchivesDestroyForm) super.createNewForm(
				mapping, form, request, response);
		((IKmArchivesDestroyService) getServiceImp(request)).initFormSetting(
				kmArchivesDestroyForm, new RequestContext(request));
        return kmArchivesDestroyForm;
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
			KmArchivesDestroyForm kmArchivesDestroyForm = (KmArchivesDestroyForm) form;

			String docTemplateId = request.getParameter("docTemplateId");

			if (StringUtil.isNotNull(docTemplateId)) {
				KmArchivesDestroyTemplate kmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) getKmArchivesDestroyTemplateService()
						.findByPrimaryKey(docTemplateId);
				kmArchivesDestroyForm.setDocTemplateId(docTemplateId);
				kmArchivesDestroyForm
						.setDocTemplateName(
								kmArchivesDestroyTemplate.getFdName());
				kmArchivesDestroyForm
						.setDocCreatorId(UserUtil.getUser().getFdId());
				kmArchivesDestroyForm
						.setDocCreatorName(UserUtil.getUser().getFdName());
				kmArchivesDestroyForm
						.setDocCreateTime(DateUtil.convertDateToString(
								new Date(), DateUtil.TYPE_DATETIME,
								request.getLocale()));
				kmArchivesDestroyForm.setMethod_GET("add");
				// 启动模板流程
				kmArchivesDestroyForm.getSysWfBusinessForm()
						.setCanStartProcess("true");
				getDispatchCoreService().initFormSetting(
						kmArchivesDestroyForm, "kmArchivesDestroy",
						kmArchivesDestroyTemplate, "kmArchivesDestroy",
						new RequestContext(request));
			}

			// #67953 借阅界面变更流程后，档案明细表部分字段为空；出现了不正常的审批记录界面
//			List<KmArchivesDestroyDetailsForm> detailForms = kmArchivesDestroyForm
//					.getFdDestroyDetail_Form();
//			for (KmArchivesDestroyDetailsForm detailForm : detailForms) {
//				KmArchivesMain kmArchivesMain = (KmArchivesMain) getKmArchivesMainService()
//						.findByPrimaryKey(detailForm.getFdArchivesId());
//				detailForm.setFdArchives(kmArchivesMain);
//			}

			// #108006 借阅流程和密级程度绑定，所以变更流程后需要清空借阅明细（变更后的流程的密级程度可能和之前所选的档案的密级程度不匹配）
			kmArchivesDestroyForm.setFdDestroyDetail_Form(
					new AutoArrayList(KmArchivesDestroyDetailsForm.class));
			
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

	/**
	 * 打印销毁清单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printDestroyList(ActionMapping mapping,
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
			request.setAttribute("destroyList",
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
