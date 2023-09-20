package com.landray.kmss.third.ekp.java;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.sys.config.action.SysConfigAdminAction;
import com.landray.kmss.sys.config.form.SysConfigAdminForm;
import com.landray.kmss.third.ekp.java.oms.in.EkpOmsConfig;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class ConfigAction extends SysConfigAdminAction{
	
	@Override
    public ActionForward config(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysConfigAdminForm configForm = new SysConfigAdminForm();
			Map<String,String> map = new HashMap<String,String>();
			map.putAll(ResourceUtil.getKmssConfig("kmss.integrate.java"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.java"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.oms.in.java"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.notify.todoExtend.java"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.notify.mq.send.enabled"));
			map.putAll(ResourceUtil.getKmssConfig("kmss.notify.mq.receive.enabled"));
			configForm.setMap(map);
				request.setAttribute("configForm", configForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("configView");
		}
	}

	/**
	 * 删除LDAP同步时间戳
	 */
	public ActionForward deleteTimeStamp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		EkpOmsConfig config = new EkpOmsConfig();
		config.setLastUpdateTime(null);
		config.setRoleLastUpdateTime(null);
		// 清空生态组织同步时间戳
		config.setEcoLastUpdateTime(null);
		config.save();

		KmssMessage message = new KmssMessage("admin.config.success");
		messages.addMsg(message);

		KmssReturnPage.getInstance(request).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");

	}

	/**
	 * 更新LDAP同步时间戳
	 */
	public ActionForward updateTimeStamp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String time = request.getParameter("time");
		if (StringUtil.isNull(time)) {
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write("更新失败，必须在URL中设置time属性");
			return null;
		}
		EkpOmsConfig config = new EkpOmsConfig();
		config.setLastUpdateTime(time);
		config.setRoleLastUpdateTime(time);
		config.save();

		KmssMessage message = new KmssMessage("admin.config.success");
		messages.addMsg(message);

		KmssReturnPage.getInstance(request).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");

	}

}
