package com.landray.kmss.km.calendar.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.km.calendar.forms.KmCalendarBaseConfigForm;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * @author 孟磊
 * @version 创建时间：2013-11-13 上午11:47:49 类说明
 */
public class KmCalendarBaseConfigAction extends BaseAction {
	/**
	 * 时间管理edit页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			config.setFdType(1);
			UserOperHelper.setModelNameAndModelDesc(
					KmCalendarBaseConfig.class.getName(),
					config.getModelDesc());
			configForm.setFdStartTime(config.getFdStartTime());
			configForm.setFdEndTime(config.getFdEndTime());
			configForm.setFdKeepDay(config.getFdKeepDay());
			request.setAttribute("type", request.getParameter("type"));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	public ActionForward editAuthConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			config.setFdType(2);
			UserOperHelper.setModelNameAndModelDesc(
					KmCalendarBaseConfig.class.getName(),
					config.getModelDesc());
			String deptCanRead = config.getDeptCanRead();
			if (StringUtil.isNull(deptCanRead)) {
				deptCanRead = "true";
			}
			configForm.setDeptCanRead(deptCanRead);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("editAuthConfig");
		}
	}

	public ActionForward updateAuthConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			config.setFdType(2);
			UserOperHelper.setModelNameAndModelDesc(
					KmCalendarBaseConfig.class.getName(),
					config.getModelDesc());
			config.setDeptCanRead(configForm.getDeptCanRead());
			config.save();
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}

	public ActionForward editSynchroThreadSize(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			config.setFdType(3);
			UserOperHelper.setModelNameAndModelDesc(
					KmCalendarBaseConfig.class.getName(),
					config.getModelDesc());
			String threadPoolSize = config.getThreadPoolSize();
			if (StringUtil.isNull(threadPoolSize)) {
				threadPoolSize = "2";
			}
			configForm.setDefaultAuthorityType(config.getDefaultAuthorityType());
			configForm.setThreadPoolSize(threadPoolSize);
			configForm.setSynchroDirect(config.getSynchroDirect());
			// request.setAttribute("type", request.getParameter("type"));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("editSynchroThreadSize");
		}
	}

	public ActionForward updateSynchroThreadSize(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			config.setFdType(3);
			UserOperHelper.setModelNameAndModelDesc(
					KmCalendarBaseConfig.class.getName(),
					config.getModelDesc());
			config.setThreadPoolSize(configForm.getThreadPoolSize());
			config.setDefaultAuthorityType(configForm.getDefaultAuthorityType());
			config.setSynchroDirect(configForm.getSynchroDirect());
			config.save();
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}
	
	/**
	 * 时间参数设置
	 */
	public ActionForward editTimeParameter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			SysAgendaBaseConfig config = new SysAgendaBaseConfig();
			UserOperHelper.setModelNameAndModelDesc(
					SysAgendaBaseConfig.class.getName(), config.getModelDesc());
			String calendarMinuteStep = config.getCalendarMinuteStep();
			if (StringUtil.isNull(calendarMinuteStep)) {
				calendarMinuteStep = "1";
			}
			configForm.setCalendarMinuteStep(calendarMinuteStep);
			
			String calendarWeekStartDate = config.getCalendarWeekStartDate();
			if(StringUtil.isNull(calendarWeekStartDate)) {
				calendarWeekStartDate = "1";
			}
			configForm.setCalendarWeekStartDate(calendarWeekStartDate);
			
			String calendarDisplayType = config.getCalendarDisplayType();
			if(StringUtil.isNull(calendarDisplayType)) {
				calendarDisplayType = "month";
			}
			configForm.setCalendarDisplayType(calendarDisplayType);

			String updateAuthDate = config.getUpdateAuthDate();
			if (StringUtil.isNull(updateAuthDate)) {
				updateAuthDate = "month";
			}
			configForm.setUpdateAuthDate(updateAuthDate);

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("editTimeParameter");
		}
	}
	
	public ActionForward updateTimeParameter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		
		try {
						
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			SysAgendaBaseConfig config = new SysAgendaBaseConfig();
			UserOperHelper.setModelNameAndModelDesc(
					SysAgendaBaseConfig.class.getName(), config.getModelDesc());
			config.setCalendarMinuteStep(configForm.getCalendarMinuteStep());
			config.setCalendarWeekStartDate(configForm.getCalendarWeekStartDate());
			config.setCalendarDisplayType(configForm.getCalendarDisplayType());
			config.setUpdateAuthDate(configForm.getUpdateAuthDate());
			config.save();
			
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}

	/**
	 * 时间管理view页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			configForm.setFdStartTime(config.getFdStartTime());
			configForm.setFdEndTime(config.getFdEndTime());
			configForm.setFdKeepDay(config.getFdKeepDay());
			request.setAttribute("type", request.getParameter("type"));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("view");
		}
	}

	/**
	 * 保存时间管理模块设置信息且转向时间管理edit页面。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回manage页面
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String type = request.getParameter("type");
			KmCalendarBaseConfigForm configForm = (KmCalendarBaseConfigForm) form;
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			config.setFdType(1);
			UserOperHelper.setModelNameAndModelDesc(
					KmCalendarBaseConfig.class.getName(),
					config.getModelDesc());
			if (StringUtil.isNotNull(type)) {
				if ("day".equals(type)) {
					config.setFdKeepDay(configForm.getFdKeepDay());
				} else if ("time".equals(type)) {
					config.setFdStartTime(configForm.getFdStartTime());
					config.setFdEndTime(configForm.getFdEndTime());
				}
				config.save();
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("edit");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}

}
