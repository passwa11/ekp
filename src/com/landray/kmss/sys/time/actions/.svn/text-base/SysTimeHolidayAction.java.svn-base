package com.landray.kmss.sys.time.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.time.forms.SysTimeHolidayDetailForm;
import com.landray.kmss.sys.time.forms.SysTimeHolidayForm;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
import com.landray.kmss.sys.time.service.ISysTimeHolidayPachService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 节假日设置 Action
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayAction extends ExtendAction {
	protected ISysTimeHolidayService sysTimeHolidayService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeHolidayService == null) {
			sysTimeHolidayService = (ISysTimeHolidayService) getBean(
					"sysTimeHolidayService");
		}
		return sysTimeHolidayService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String fdName = request.getParameter("q.fdName");
		if (StringUtil.isNotNull(fdName)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "fdName like '%" + fdName + "%'"));
		}
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		String order = super.getFindPageOrderBy(request, curOrderBy);
		if (StringUtil.isNotNull(order)) {
			order += ",fdName";
		} else {
			order = "fdName";
		}
		return order;
	}

	protected ISysTimeHolidayPachService sysTimeHolidayPachService;

	protected ISysTimeHolidayPachService getSysTimeHolidayPachServiceImp(
			HttpServletRequest request) {
		if (sysTimeHolidayPachService == null) {
			sysTimeHolidayPachService = (ISysTimeHolidayPachService) getBean(
					"sysTimeHolidayPachService");
		}
		return sysTimeHolidayPachService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form = super.createNewForm(mapping, form, request, response);
		List<Integer> years = new ArrayList<Integer>();
		Date d = new Date();
		int initYear = d.getYear()+1900-1;
		for(int y=1;y<=10;y++){
			years.add(initYear+y);
		}
		request.setAttribute("years", years);
		// 设置当年为默认值
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		request.setAttribute("year", cal.get(Calendar.YEAR));
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		return form;

	}

	/*
	 * 所有节假日接口
	 */
	public void getHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<SysTimeHoliday> sysTimeHoliday = getServiceImp(request)
				.findList(hqlInfo);
		List<SysTimeHolidayDetail> detailList = null;
		JSONArray jsonArray = new JSONArray();
		for (SysTimeHoliday holiday : sysTimeHoliday) {
			JSONObject obj = new JSONObject();
			detailList = holiday.getFdHolidayDetailList();
			obj.accumulate("fdId", holiday.getFdId());
			obj.accumulate("fdName", holiday.getFdName());
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for (SysTimeHolidayDetail sysTimeHolidayDetail : detailList) {
				jo = new JSONObject();
				jo.accumulate("fdId", sysTimeHolidayDetail.getFdId());
				jo.accumulate("name", sysTimeHolidayDetail.getFdName());
				jo.accumulate("startDay", DateUtil.convertDateToString(
						sysTimeHolidayDetail.getFdStartDay(),
						DateUtil.TYPE_DATE, request.getLocale()));
				jo.accumulate("endDay", DateUtil.convertDateToString(
						sysTimeHolidayDetail.getFdEndDay(),
						DateUtil.TYPE_DATE, request.getLocale()));
				jo.accumulate("patchDay", sysTimeHolidayDetail.getFdPatchDay());

				ja.add(jo);
			}
			obj.accumulate("fdHolidayDetailList", ja);
			jsonArray.add(obj);
		}
		response.setContentType("application/json;charset=utf-8");
		response.getWriter()
				.write(jsonArray.toString());

	}

	/*
	 * 节假日id获取数据接口
	 */
	public void getHolidayById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		if (!StringUtil.isNull(fdId)) {
			SysTimeHoliday sysTimeHoliday = (SysTimeHoliday) getServiceImp(
					request).findByPrimaryKey(fdId);
			JSONObject obj = new JSONObject();
			JSONArray ja = new JSONArray();
			List<SysTimeHolidayDetail> list = sysTimeHoliday
					.getFdHolidayDetailList();
			obj.accumulate("fdId", sysTimeHoliday.getFdId());
			obj.accumulate("name", sysTimeHoliday.getFdName());
			for (SysTimeHolidayDetail detail : list) {
				JSONObject detailObj = new JSONObject();
				detailObj.accumulate("fdId", detail.getFdId());
				detailObj.accumulate("name", detail.getFdName());
				detailObj.accumulate("startDay", DateUtil.convertDateToString(
						detail.getFdStartDay(),
						DateUtil.TYPE_DATE, request.getLocale()));
				detailObj.accumulate("endDay", DateUtil.convertDateToString(
						detail.getFdEndDay(),
						DateUtil.TYPE_DATE, request.getLocale()));
				detailObj.accumulate("patchDay", detail.getFdPatchDay());

				ja.add(detailObj);

			}
			obj.accumulate("sysTimeHolidayDetail", ja);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter()
					.write(obj.toString());
		}

	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		List<Integer> years = new ArrayList<Integer>();
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null,
					true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				// 追加补班
				SysTimeHolidayForm holidayForm = (SysTimeHolidayForm) rtnForm;
				List<SysTimeHolidayDetailForm> holidayForms = holidayForm
						.getFdHolidayDetailList();
				StringBuffer pachstr = new StringBuffer();
				int ty = 0;
				for (int i = 0; i < holidayForms.size(); i++) {
					ty = Integer.parseInt(holidayForms.get(i).getFdYear());
					if(!years.contains(ty)) {
						years.add(ty);
					}
					pachstr.setLength(0);
					List<SysTimeHolidayPach> list = getSysTimeHolidayPachServiceImp(
							request).findList(
									"fdDetail='" + holidayForms.get(i).getFdId()
											+ "'",
									null);
					for (int k = 0; k < list.size(); k++) {
						if (k == list.size() - 1) {
							pachstr.append(DateUtil.convertDateToString(
									list.get(k).getFdPachTime(), null,
									request.getLocale()).split(" ")[0]);
						} else {
							pachstr.append(DateUtil
									.convertDateToString(
											list.get(k).getFdPachTime(), null,
											request.getLocale())
									.split(" ")[0] + ",");
						}
					}
					holidayForms.get(i).setFdPatchDay(pachstr.toString());
				}
				UserOperHelper.logFind(model);
			}
		}
		Collections.sort(years);
		String method = request.getParameter("method");
		if("edit".equals(method)){
			Date d = new Date();
			int initYear = d.getYear()+1900-1;
			int bigYear = 10;
			if(!years.isEmpty()){
				bigYear = years.get(years.size()-1)-initYear;
				if(bigYear<=10) {
					bigYear = 10;
				}
			}
			for(int y=1;y<=bigYear;y++){
				if(!years.contains(initYear+y)) {
					years.add(initYear+y);
				}
			}
		}
		Collections.sort(years);
		request.setAttribute("years", years);
		//设置当年为默认值
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		request.setAttribute("year", cal.get(Calendar.YEAR));
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
}
