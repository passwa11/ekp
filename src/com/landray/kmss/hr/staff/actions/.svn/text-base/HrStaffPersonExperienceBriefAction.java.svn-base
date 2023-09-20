package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBrief;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBriefService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 项目经历
 * 
 * @author 朱湖强 2017-01-09
 * 
 */
public class HrStaffPersonExperienceBriefAction extends ExtendAction {
	private IHrStaffPersonExperienceBriefService hrStaffPersonExperienceBriefService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonExperienceBriefService == null) {
            hrStaffPersonExperienceBriefService = (IHrStaffPersonExperienceBriefService) getBean("hrStaffPersonExperienceBriefService");
        }
		return hrStaffPersonExperienceBriefService;
	}

	
	/**
	 * 根据人员ID获取相应的数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void listData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String personInfoId = request.getParameter("personInfoId");

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffPersonExperienceBrief> list = getServiceImp(request)
				.findPage(hqlInfo).getList();
		// 添加日志信息
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		JSONArray array = handleJSONArray(list);
		response.setHeader("content-type", "application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		JSONObject obj = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			obj.put("state", true);
		} catch (Exception e) {
			obj.put("msg", e);
			obj.put("state", false);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		JSONObject obj = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			obj.put("state", true);
		} catch (Exception e) {
			obj.put("msg", e);
			obj.put("state", false);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	public JSONArray handleJSONArray(List<HrStaffPersonExperienceBrief> list) {
		JSONArray array = new JSONArray();
		for (HrStaffPersonExperienceBrief _info : list) {
			HrStaffPersonExperienceBrief info = (HrStaffPersonExperienceBrief)_info;
			JSONObject obj = new JSONObject();
			obj.put("fdId", info.getFdId());
			obj.put("fdContent", info.getFdContent());
			array.add(obj);
		}
		return array;
	}
}
