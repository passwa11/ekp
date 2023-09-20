package com.landray.kmss.sys.attend.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendSignStatService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;

public class SysAttendSignStatAction extends ExtendAction {
	protected ISysAttendSignStatService sysAttendSignStatService;
	private ISysAttendOrgService sysAttendOrgService;

	public ISysAttendOrgService getSysAttendOrgService() {
		if (sysAttendOrgService == null) {
			sysAttendOrgService = (ISysAttendOrgService) getBean(
					"sysAttendOrgService");
		}
		return sysAttendOrgService;
	}

	@Override
	protected ISysAttendSignStatService
			getServiceImp(HttpServletRequest request) {
		if (sysAttendSignStatService == null) {
			sysAttendSignStatService = (ISysAttendSignStatService) getBean(
					"sysAttendSignStatService");
		}
		return sysAttendSignStatService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		StringBuffer sb = new StringBuffer("1=1 ");
		String fdDate = request.getParameter("fdDate");
		fdDate = StringUtil.isNull(fdDate)? new Date().getTime()+"":fdDate;
		sb.append(
				" and sysAttendSignStat.fdDate>=:fdStartTime and sysAttendSignStat.fdDate <:fdEndTime ");
		hqlInfo.setParameter("fdStartTime",
				AttendUtil.getDate(new Date(Long.valueOf(fdDate)), 0));
		hqlInfo.setParameter("fdEndTime",
				AttendUtil.getDate(new Date(Long.valueOf(fdDate)), 1));
		String operType = request.getParameter("operType");
		if ("0".equals(operType)) {
			sb.append(" and sysAttendSignStat.fdSignCount=0");
		} else {
			sb.append(" and sysAttendSignStat.fdSignCount > 0");
		}
		String fdCategoryId = request.getParameter("fdCategoryId");
		if (StringUtil.isNotNull(fdCategoryId)) {
			sb.append(" and sysAttendSignStat.fdCategoryId=:fdCategoryId");
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		}
		hqlInfo.setWhereBlock(sb.toString());
	}

	public ActionForward addressList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List addressList = getSysAttendOrgService().addressList(
					new RequestContext(request));
			// 添加日志信息
			UserOperHelper.logFindAll(addressList,
					getServiceImp(request).getModelName());
			request.setAttribute("lui-source", JSONArray
					.fromObject(addressList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}

}
