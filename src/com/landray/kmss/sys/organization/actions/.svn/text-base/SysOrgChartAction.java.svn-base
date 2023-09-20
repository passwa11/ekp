package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.sys.organization.service.ISysOrgChartService;
import com.landray.kmss.util.StringUtil;

public class SysOrgChartAction extends ExtendAction {

	private ISysOrgChartService sysOrgChartService = null;

	@Override
	protected ISysOrgChartService getServiceImp(HttpServletRequest request) {
		if (sysOrgChartService == null) {
            sysOrgChartService = (ISysOrgChartService) getBean("sysOrgChartService");
        }
		return sysOrgChartService;
	}
	
	public ActionForward GetAllList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
		String fdId = request.getParameter("fdId"); // 机构ID
		String isFirstTimeLoad_param = request.getParameter("isFirstTimeLoad"); // 是否页面初始化时首次加载机构数据（true:是，false：否）
		String expandLevel_param = request.getParameter("expandLevel"); // 展开层级（显示几层）
		if (StringUtil.isNotNull(fdId)&&StringUtil.isNotNull(isFirstTimeLoad_param)&&StringUtil.isNotNull(expandLevel_param)) {
			boolean isFirstTimeLoad = Boolean.parseBoolean(isFirstTimeLoad_param);
			int expandLevel = Integer.parseInt(expandLevel_param);
			result = getServiceImp(request).getChartData(fdId, isFirstTimeLoad, expandLevel);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append(result);

		return null;
	}

}
