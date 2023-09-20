package com.landray.kmss.third.im.kk.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.third.im.kk.service.IKkNotifyLogService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * kk待办集成日志 Action
 * 
 * @author
 * @version 1.0 2012-04-13
 */
public class KkNotifyAction extends ExtendAction {

	private static String xml_pre = "<?xml version='1.0' encoding='UTF-8'?><data><number>";
	private static String xml_suf = "</number></data>";

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	protected IKkNotifyLogService kkNotifyLogService;

	protected ISysNotifyTodoService sysNotifyTodoService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kkNotifyLogService == null) {
            kkNotifyLogService = (IKkNotifyLogService) getBean("kkNotifyLogService");
        }
		return kkNotifyLogService;
	}

	protected ISysNotifyTodoService getSysNotifyServiceImp(
			HttpServletRequest request) {
		if (sysNotifyTodoService == null) {
            sysNotifyTodoService = (ISysNotifyTodoService) getBean("sysNotifyTodoService");
        }
		return sysNotifyTodoService;
	}

	public ActionForward getTodoNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		sysNotifyTodoService = getSysNotifyServiceImp(request);
		String userId = getCurUserId(request);
		long num = sysNotifyTodoService.getTodoCount(userId, 1);

		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// NotifyNum notifyNum = new NotifyNum();
			// notifyNum.setNumber(num + "");
			// String result = XmlHelper.parseBeanToXml(notifyNum);
			String result = xml_pre + num + xml_suf;
			if (UserOperHelper.allowLogOper("getTodoNotify", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(result);
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}

	private String getCurUserId(HttpServletRequest request) {
		// javax.servlet.http.Cookie[] c=request.getCookies();
		String userId = UserUtil.getUser(request).getFdId();
		return userId;
	}

	public ActionForward getToReadNotify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		sysNotifyTodoService = getSysNotifyServiceImp(request);
		String userId = getCurUserId(request);
		long num = sysNotifyTodoService.getTodoCount(userId, 2);
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// NotifyNum notifyNum = new NotifyNum();
			// notifyNum.setNumber(num + "");
			// String result = XmlHelper.parseBeanToXml(notifyNum);
			String result = xml_pre + num + xml_suf;
			if (UserOperHelper.allowLogOper("getToReadNotify", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						getServiceImp(request).getModelName());
				UserOperHelper.logMessage(result);
				UserOperHelper.setOperSuccess(true);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}

	/**
	 * kk跳转，先跳到这里再中转到其他页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward kkSkip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String notify_url = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=!{fdId}";
		String fdId = request.getParameter("fdId");
		if (UserOperHelper.allowLogOper("kkSkip", null)) {
			UserOperHelper.setModelNameAndModelDesc(
					getServiceImp(request).getModelName());
			UserOperHelper.logMessage(notify_url.replace("!{fdId}", fdId));
		}
		request.setAttribute("redirectto", notify_url.replace("!{fdId}", fdId));
		return mapping.findForward("redirect");

	}

	@Deprecated
	public ActionForward kkSkipOld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String notify_url = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=!{fdId}";
		String fdId = request.getParameter("fdId");
		try {
			SysNotifyTodo todo = (SysNotifyTodo) getSysNotifyServiceImp(request)
					.findByPrimaryKey(fdId);
			// 防止空值数据
			String todoId = todo.getFdId();
			Integer type = todo.getFdType();
			if (type != null) {
				request.setAttribute("redirectto", notify_url.replace(
						"!{fdId}", fdId));
			}
			return mapping.findForward("redirect");

		} catch (Exception e) {
			logger.warn(" notify error " + e.getMessage());
			// return mapping.findForward("noNotifyData");
		}

		return mapping.findForward("noNotifyData");
	}

}
