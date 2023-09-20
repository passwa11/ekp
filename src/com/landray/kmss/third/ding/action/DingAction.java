package com.landray.kmss.third.ding.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 获取钉钉授权信息
 */
public class DingAction extends BaseAction {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingAction.class);
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		String fdId = request.getParameter("fdId");
		String url = null;
		if(StringUtil.isNotNull(fdId)){
			url = StringUtil.formatUrl("/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fdId);
		}else{
			logger.warn("fdId的参数为空，无法构建地址");
		}
		if(StringUtil.isNull(url)) {
            return null;
        }
		response.sendRedirect(url);
		return null;
	}
}
