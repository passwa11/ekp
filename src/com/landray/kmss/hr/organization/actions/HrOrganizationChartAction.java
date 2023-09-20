package com.landray.kmss.hr.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.hr.organization.service.IHrOrganizationChartService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrOrganizationChartAction extends ExtendAction {

	private IHrOrganizationChartService hrOrganizationChartService;

	@Override
	protected IHrOrganizationChartService
			getServiceImp(HttpServletRequest request) {
		if (hrOrganizationChartService == null) {
			hrOrganizationChartService = (IHrOrganizationChartService) getBean(
					"hrOrganizationChartService");
		}
		return hrOrganizationChartService;
	}

	public ActionForward GetAllList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
		String fdId = request.getParameter("fdId"); // 机构ID
		String isFirstTimeLoad_param = request.getParameter("isFirstTimeLoad"); // 是否页面初始化时首次加载机构数据（true:是，false：否）
		String expandLevel_param = request.getParameter("expandLevel"); // 展开层级（显示几层）
		if (StringUtil.isNotNull(fdId)
				&& StringUtil.isNotNull(isFirstTimeLoad_param)
				&& StringUtil.isNotNull(expandLevel_param)) {
			boolean isFirstTimeLoad = Boolean
					.parseBoolean(isFirstTimeLoad_param);
			int expandLevel = Integer.parseInt(expandLevel_param);
			result = getServiceImp(request).getChartData(fdId, isFirstTimeLoad,
					expandLevel);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append(result);

		return null;
	}

}
