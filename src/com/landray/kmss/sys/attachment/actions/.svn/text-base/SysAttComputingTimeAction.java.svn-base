package com.landray.kmss.sys.attachment.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.model.SysAttComputingTimePlugin;
import com.landray.kmss.sys.attachment.service.ISysAttComputingTimeService;
import com.landray.kmss.sys.attachment.util.SysAttComputingTimeUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class SysAttComputingTimeAction extends ExtendAction {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 学习管理课件计时key
	 */
	public static final String LEARN_COMPUTING_TIME = "learnComputingTime";

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public ActionForward computingTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-computingTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String templateId = request.getParameter("_templateId");
		String contentId = request.getParameter("_contentId");
		JSONObject json = new JSONObject();
		try {
			List<SysAttComputingTimePlugin> plugins = SysAttComputingTimeUtil.getExtensionList();
			if (plugins != null && plugins.size() > 0) {
				for (SysAttComputingTimePlugin plugin : plugins) {
					String fdKey = plugin.getKey();
					if (StringUtil.isNotNull(fdKey)&& LEARN_COMPUTING_TIME.equals(fdKey)) {
						ISysAttComputingTimeService provider = plugin.getProvider();
						String time = plugin.getTimeInterval().toString();
						json = provider.updateComputingTime(fdId, templateId,
								contentId, time);
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}
		request.setAttribute("lui-source", json);
		TimeCounter.logCurrentTime("Action-computingTime", false, getClass());
		return getActionForward("lui-source", mapping, form, request,response);
	}

}
