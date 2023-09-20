package com.landray.kmss.km.calendar.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.forms.KmCalendarAgendaLabelForm;
import com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel;
import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.agenda.label.AgendaLabelPlugin;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 新日程标签管理 Action
 */
public class KmCalendarAgendaLabelAction extends ExtendAction {

	protected IKmCalendarAgendaLabelService kmCalendarAgendaLabelService;

	@Override
	protected IKmCalendarAgendaLabelService getServiceImp(
			HttpServletRequest request) {
		if (kmCalendarAgendaLabelService == null) {
			kmCalendarAgendaLabelService = (IKmCalendarAgendaLabelService) getBean("kmCalendarAgendaLabelService");
		}
		return kmCalendarAgendaLabelService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmCalendarAgendaLabel.class);
	}

	protected IKmCalendarLabelService kmCalendarLabelService;

	protected IKmCalendarLabelService getKmCalendarLabelService() {
		if (kmCalendarLabelService == null) {
			kmCalendarLabelService = (IKmCalendarLabelService) getBean("kmCalendarLabelService");
		}
		return kmCalendarLabelService;
	}

	protected IKmCalendarMainService kmCalendarMainService;

	protected IKmCalendarMainService getkmCalendarMainService() {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	public ActionForward importAgendaLabel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter
				.logCurrentTime("Action-importAgendaLabel", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo info = new HQLInfo();
			info.setSelectBlock("kmCalendarAgendaLabel.fdAgendaModelName");
			info.setWhereBlock("kmCalendarAgendaLabel.isAgendaLabel = :isAgendaLabel");
			info.setParameter("isAgendaLabel", true);
			List<String> modelNames = getServiceImp(request).findList(info);
			List<Map<String, String>> extensions = AgendaLabelPlugin
					.getExtensionList();
			for (Map<String, String> extension : extensions) {
				String modelName = extension.get("modelName");
				if (modelNames.contains(modelName)) {
					continue;
				}
				KmCalendarAgendaLabel kmCalendarAgendaLabel = new KmCalendarAgendaLabel();
				kmCalendarAgendaLabel.setFdId(IDGenerator.generateID());
				kmCalendarAgendaLabel.setFdColor(extension.get("labelColor"));
				kmCalendarAgendaLabel.setFdAgendaModelName(modelName);
				kmCalendarAgendaLabel.setFdName(extension.get("labelName"));
				kmCalendarAgendaLabel.setIsAgendaLabel(true);
				kmCalendarAgendaLabel.setFdIsAvailable(true);
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_ADD,
						getServiceImp(request).getModelName())) {
					UserOperContentHelper
							.putAdd(kmCalendarAgendaLabel, "fdId", "fdName")
							.putSimple("fdId", kmCalendarAgendaLabel.getFdId())
							.putSimple("fdName",
									kmCalendarAgendaLabel.getFdName());
				}
				getServiceImp(request).add(kmCalendarAgendaLabel);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-importAgendaLabel", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
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
			KmCalendarAgendaLabelForm agendaLabelForm = (KmCalendarAgendaLabelForm) form;
			String agendaModelName = agendaLabelForm.getFdAgendaModelName();
			HQLInfo info = new HQLInfo();
			info
					.setWhereBlock("kmCalendarAgendaLabel.fdAgendaModelName = :agendaModelName");
			info.setParameter("agendaModelName", agendaModelName);
			List result = getServiceImp(request).findList(info);
			if (result != null && result.size() > 0) {
				messages.addError(new KmssMessage(
						"km-calendar:error.agendaModelName.reduplicate"));
			} else {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
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
	 * 执行保存并添加操作。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，将成功信息添加到页面中并执行add操作，否则将错误信息添加到页面中并返回edit页面
	 * @throws Exception
	 */
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
			KmCalendarAgendaLabelForm agendaLabelForm = (KmCalendarAgendaLabelForm) form;
			String agendaModelName = agendaLabelForm.getFdAgendaModelName();
			HQLInfo info = new HQLInfo();
			info.setWhereBlock(
					"kmCalendarAgendaLabel.fdAgendaModelName = :agendaModelName");
			info.setParameter("agendaModelName", agendaModelName);
			List result = getServiceImp(request).findList(info);
			if (result != null && result.size() > 0) {
				messages.addError(new KmssMessage(
						"km-calendar:error.agendaModelName.reduplicate"));
			} else {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
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

	@SuppressWarnings("unchecked")
	public ActionForward updateIsAvailable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateIsAvailable", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = request.getParameter("fdId");
			String fdIsAvailable = request.getParameter("fdIsAvailable");
			KmCalendarAgendaLabel kmCalendarAgendaLabel = null;
			Boolean oldFdIsAvailable = null;
			if (!StringUtil.isNull(fdId)) {
				kmCalendarAgendaLabel = (KmCalendarAgendaLabel) getServiceImp(request)
								.findByPrimaryKey(fdId, null, true);
				oldFdIsAvailable = kmCalendarAgendaLabel.getFdIsAvailable();
			}
			if(kmCalendarAgendaLabel != null){
				if("true".equals(fdIsAvailable) || "false".equals(fdIsAvailable)) {
                    kmCalendarAgendaLabel.setFdIsAvailable(Boolean.parseBoolean(fdIsAvailable));
                }
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putUpdate(kmCalendarAgendaLabel)
							.putSimple("fdIsAvailable", oldFdIsAvailable,
									kmCalendarAgendaLabel.getFdIsAvailable());
					if ("true".equals(fdIsAvailable)) {
                        UserOperHelper.setEventType("启用");
                    } else {
                        UserOperHelper.setEventType("停用");
                    }
				}
				getServiceImp(request).update(kmCalendarAgendaLabel);
				// 标签置为无效
				if ("false".equals(fdIsAvailable)) {
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setSelectBlock("kmCalendarLabel.fdId");
					hqlInfo.setWhereBlock("kmCalendarLabel.fdModelName = :fdModelName");
					hqlInfo.setParameter("fdModelName", kmCalendarAgendaLabel.getFdAgendaModelName());
					List<String> labelIds = getKmCalendarLabelService().findList(hqlInfo);
					getkmCalendarMainService().updateBatchClearCalendarLabel(labelIds);
					getKmCalendarLabelService().deleteBatch(labelIds);
				}
			}			
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updateIsAvailable", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmCalendarAgendaLabelForm mainForm = (KmCalendarAgendaLabelForm) form;
		String fdAgendaModelName = mainForm.getFdAgendaModelName();
		SysDictModel sysDictModel = SysDataDict.getInstance()
				.getModel(fdAgendaModelName);
		String fdName = mainForm.getFdName();
		if (sysDictModel != null) {
			String messageKey = sysDictModel.getMessageKey();
			String fdName_lang = ResourceUtil.getString(messageKey,
					request.getLocale());
			fdName = fdName_lang != null ? fdName_lang : fdName;
		}
		mainForm.setFdName(fdName);
	}
}
