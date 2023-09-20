package com.landray.kmss.sys.time.actions;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.time.forms.SysTimeAreaForm;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeConvert;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.service.ISysTimeAreaService;
import com.landray.kmss.sys.time.service.ISysTimeCommonTimeService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayPachService;
import com.landray.kmss.sys.time.util.SysTimePersonUtil;
import com.landray.kmss.sys.time.util.UploadResultBean;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeAreaAction extends ExtendAction {
	ISysTimeCommonTimeService commonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
			.getBean("sysTimeCommonTimeService");
	protected ISysTimeAreaService sysTimeAreaService;

	private ISysOrgCoreService sysOrgCoreService;

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean(
					"sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeAreaService == null) {
			sysTimeAreaService = (ISysTimeAreaService) getBean(
					"sysTimeAreaService");
		}
		return sysTimeAreaService;
	}

	private ISysTimeCountService sysTimeCountService;

	public ISysTimeCountService getSysTimeCountService() {
		if (sysTimeCountService == null) {
			sysTimeCountService = (ISysTimeCountService) getBean(
					"sysTimeCountService");
		}
		return sysTimeCountService;
	}

	/**
	 * 校验组内成员是否在另外一个组中
	 * 
	 * @param sysTimeAreaForm
	 * @param messages
	 * @param request
	 * @return 如果不存在，返回success页面，否则返回edit页面
	 * @throws Exception
	 */
	private void validateForm(SysTimeAreaForm sysTimeAreaForm,
			KmssMessages messages, HttpServletRequest request)
			throws Exception {
		String fdId = sysTimeAreaForm.getFdId();
		String areaMemberIds = sysTimeAreaForm.getAreaMemberIds();
		String whereBlock;
		if (!StringUtil.isNull(fdId)) {
			whereBlock = "sysTimeArea.fdId != '" + fdId
					+ "' and sysTimeArea.areaMembers.fdId in("
					+ HQLUtil.replaceToSQLString(areaMemberIds) + ")";
		} else {
			whereBlock = "sysTimeArea.areaMembers.fdId in("
					+ HQLUtil.replaceToSQLString(areaMemberIds) + ")";
		}
		List checkList = getServiceImp(request).findList(whereBlock, null);
		if (checkList != null && !checkList.isEmpty()) {
			messages.addError(new KmssMessage("sys-time:sysTimeArea.repeat"));
			return;
		}
	}

	/**
	 * 将浏览器提交的表单数据更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysTimeAreaForm sysTimeAreaForm = (SysTimeAreaForm) form;
		String fdId = request.getParameter("fdId");
		SysTimeArea sysTimeArea = (SysTimeArea) getServiceImp(request)
				.findByPrimaryKey(fdId);
		sysTimeAreaForm.setSysTimeWorkList(sysTimeArea.getSysTimeWorkList());
		sysTimeAreaForm
				.setSysTimePatchworkList(sysTimeArea.getSysTimePatchworkList());
		sysTimeAreaForm
				.setSysTimeVacationList(sysTimeArea.getSysTimeVacationList());
		validateForm(sysTimeAreaForm, messages, request);
		if (!messages.hasError()) {
			try {
				if (!"POST".equals(request.getMethod())) {
					throw new UnexpectedRequestException();
				}
				getServiceImp(request).update(sysTimeAreaForm,
						new RequestContext(request));
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysTimeAreaForm areaForm = (SysTimeAreaForm) form;
		areaForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		areaForm.setDocCreatorId(UserUtil.getUser().getFdId());
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		return areaForm;
	}

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysTimeAreaForm sysTimeAreaForm = (SysTimeAreaForm) form;
		String fdId = sysTimeAreaForm.getFdId();
		validateForm(sysTimeAreaForm, messages, request);
		String method = request.getParameter("method_GET");
		String cloneModelId = request.getParameter("cloneModelId");
		if ("clone".equals(method) && cloneModelId != null) {
			SysTimeArea model = (SysTimeArea) getServiceImp(request)
					.findByPrimaryKey(cloneModelId, null, true);
			sysTimeAreaForm = ((ISysTimeAreaService) getServiceImp(request))
					.cloneModel(model, sysTimeAreaForm);
			sysTimeAreaForm.setFdId(fdId);
		}

		if (!messages.hasError()) {
			try {
				if (!"POST".equals(request.getMethod())) {
					throw new UnexpectedRequestException();
				}
				getServiceImp(request).add(sysTimeAreaForm,
						new RequestContext(request));
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward clone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-clone", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String cloneModelId = request.getParameter("cloneModelId");
			SysTimeAreaForm newForm = (SysTimeAreaForm) createNewForm(
					mapping, form, request, response);
			if (!StringUtil.isNull(cloneModelId)) {
				SysTimeArea model = (SysTimeArea) getServiceImp(request)
						.findByPrimaryKey(cloneModelId, null, true);
				if (model != null) {
					newForm = (SysTimeAreaForm) getServiceImp(request)
							.cloneModelToForm(newForm, model,
									new RequestContext(request));
				}
				newForm.setAreaMemberIds(null);
				newForm.setAreaMemberNames(null);
			}
			if (newForm == null) {
				throw new NoRecordException();
			}
			if (newForm != form) {
				request.setAttribute(getFormName(newForm, request), newForm);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-clone", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	/**
	 * 执行保存并添加操作。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，将成功信息添加到页面中并执行add操作，否则将错误信息添加到页面中并返回edit页面
	 * @throws Exception
	 */
	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysTimeAreaForm sysTimeAreaForm = (SysTimeAreaForm) form;
		validateForm(sysTimeAreaForm, messages, request);
		if (!messages.hasError()) {
			try {
				if (!"POST".equals(request.getMethod())) {
					throw new UnexpectedRequestException();
				}
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	/**
	 * 日历模式页面统一处理逻辑。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	private void handleCalendarAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
				UserOperHelper.logFind(model);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model,
						new RequestContext(request));
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	/**
	 * 打开日历模式页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward calendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			handleCalendarAction(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("calendar", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开日历模式页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward viewCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewCalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			handleCalendarAction(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-viewCalendar", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("viewCalendar", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开日历模式页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward viewMCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-viewMCalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			handleCalendarAction(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-viewMCalendar", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("viewMCalendar", mapping, form, request,
					response);
		}
	}

	/**
	 * 编辑日历模式页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward editCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editCalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			handleCalendarAction(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editCalendar", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editCalendar", mapping, form, request,
					response);
		}
	}

	/**
	 * 编辑日历模式页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward editMCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editMCalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			handleCalendarAction(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editMCalendar", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editMCalendar", mapping, form, request,
					response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				SysTimeArea.class);
	}

	public ActionForward calendarJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-getTaskApprove", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray days = new JSONArray();
			String fdId = request.getParameter("fdId");
			String colors = request.getParameter("colors");
			Map<String, String> csmap = new HashMap<String, String>();
			if (StringUtil.isNotNull(colors)) {
				JSONArray ja = JSONArray.fromObject(colors);
				JSONObject jo = null;
				for (int i = 0; i < ja.size(); i++) {
					jo = ja.getJSONObject(i);
					csmap.put(jo.getString("fdId"), jo.getString("color"));
				}
			}
			String tempTime = null;
			JSONObject day = null;
			List<SysTimeWork> works = null;
			// 假期时间
			List<SysTimeVacation> vacations = null;
			// 补班时间
			List<SysTimePatchwork> patchworks = null;
			Date date = null;
			if (StringUtil.isNotNull(fdId)) {
				Map<String, String> timeMap = new HashMap<String, String>();
				SysTimeArea area = (SysTimeArea) getServiceImp(request)
						.findByPrimaryKey(fdId);
				UserOperHelper.logFind(area);
				UserOperHelper.setOperSuccess(true);
				works = area.getSysTimeWorkList();
				vacations = area.getSysTimeVacationList();
				patchworks = area.getSysTimePatchworkList();
				Collections.sort(works, new Comparator<SysTimeWork>() {
					@Override
					public int compare(SysTimeWork o1, SysTimeWork o2) {
						int flag = o1.getDocCreateTime()
								.compareTo(o2.getDocCreateTime());
						return flag;
					}
				});
				if (works != null && !works.isEmpty()) {
					for (int i = 0; i < works.size(); i++) {
						SysTimeWork work = works.get(i);
						day = new JSONObject();
						date = work.getDocCreateTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATETIME,
									request.getLocale());
							day.put("createdAt", tempTime);
						}
						date = work.getFdStartTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATE, request.getLocale());
							day.put("fromDate", tempTime.split(" ")[0]);
						}
						date = work.getFdEndTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATE, request.getLocale());
							day.put("toDate", tempTime.split(" ")[0]);
						}
						if (work.getFdWeekStartTime() > 0) {
							day.put("fromWeek",
									work.getFdWeekStartTime());
						}
						if (work.getFdWeekEndTime() > 0) {
							day.put("toWeek", work.getFdWeekEndTime());
						}

						if (work.getSysTimeCommonTime() != null) {
							day.put("color", work.getSysTimeCommonTime()
									.getFdWorkTimeColor());
						} else {
							day.put("color", work.getFdTimeWorkColor());
						}
						day.put("type", 1);
						days.add(day);
					}
				}

				if (vacations != null && !vacations.isEmpty()) {
					for (int i = 0; i < vacations.size(); i++) {
						SysTimeVacation vacation = vacations.get(i);
						day = new JSONObject();
						date = vacation.getDocCreateTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATETIME,
									request.getLocale());
							day.put("createdAt", tempTime);
						}
						date = vacation.getFdStartTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATE, request.getLocale());
							day.put("fromDate", tempTime.split(" ")[0]);
						}
						date = vacation.getFdEndTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATE, request.getLocale());
							day.put("toDate", tempTime.split(" ")[0]);
						}
						day.put("color", "");
						day.put("type", 2);
						days.add(day);
						// 保存假期时间
						if (day.containsKey("fromDate")
								&& day.get("fromDate") != null
								&& day.containsKey("toDate")
								&& day.get("toDate") != null) {
							getDay(day.getString("fromDate"),
									day.getString("toDate"), timeMap, request);
						}
					}
				}

				Collections.sort(patchworks,
						new Comparator<SysTimePatchwork>() {
							@Override
							public int compare(SysTimePatchwork o1,
									SysTimePatchwork o2) {
								int flag = o1.getDocCreateTime()
										.compareTo(o2.getDocCreateTime());
								return flag;
							}
						});
				if (patchworks != null && !patchworks.isEmpty()) {
					for (int i = 0; i < patchworks.size(); i++) {
						SysTimePatchwork patchwork = patchworks.get(i);
						day = new JSONObject();
						date = patchwork.getDocCreateTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATETIME,
									request.getLocale());
							day.put("createdAt", tempTime);
						}
						date = patchwork.getFdStartTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATE, request.getLocale());
							day.put("fromDate", tempTime.split(" ")[0]);
						}
						date = patchwork.getFdEndTime();
						if (date != null) {
							tempTime = DateUtil.convertDateToString(date,
									DateUtil.TYPE_DATE, request.getLocale());
							day.put("toDate", tempTime.split(" ")[0]);
						}
						if (patchwork.getSysTimeCommonTime() != null) {
							day.put("color",
									patchwork.getSysTimeCommonTime()
											.getFdWorkTimeColor());
						} else {
							day.put("color",
									patchwork.getFdPatchWorkColor());
						}

						day.put("type", 3);
						days.add(day);
						// 保存补班时间
						if (day.containsKey("fromDate")
								&& day.get("fromDate") != null
								&& day.containsKey("toDate")
								&& day.get("toDate") != null) {
							getDay(day.getString("fromDate"),
									day.getString("toDate"), timeMap, request);
						}
					}
				}
				// 节假日时间
				SysTimeHoliday holiday = area.getFdHoliday();
				if (holiday != null) {
					Map<String, String> holidayMap = new HashMap<String, String>();
					Map<String, String> holidayPachMap = new HashMap<String, String>();
					List<SysTimeHolidayDetail> holidayDetails = holiday
							.getFdHolidayDetailList();
					String htt = DateUtil.convertDateToString(
							holiday.getDocCreateTime(),
							DateUtil.TYPE_DATETIME,
							request.getLocale());
					List<SysTimeHolidayPach> holidayPachs = null;
					if (holidayDetails != null
							&& !holidayDetails.isEmpty()) {
						for (int i = 0; i < holidayDetails.size(); i++) {
							// 保存节假日时间
							if (holidayDetails.get(i)
									.getFdStartDay() != null
									&& holidayDetails.get(i)
											.getFdEndDay() != null) {
								getDay(DateUtil.convertDateToString(
										holidayDetails.get(i)
												.getFdStartDay(),
										DateUtil.TYPE_DATE,
										request.getLocale()),
										DateUtil.convertDateToString(
												holidayDetails.get(i)
														.getFdEndDay(),
												DateUtil.TYPE_DATE,
												request.getLocale()),
										holidayMap, request);
							}
							// 保存节假日补班时间
							holidayPachs = getSysTimeHolidayPachService()
									.findList(
											"fdDetail.fdId='"
													+ holidayDetails
															.get(i)
															.getFdId()
													+ "'",
											null);
							if (holidayPachs != null
									&& !holidayPachs.isEmpty()) {
								for (int j = 0; j < holidayPachs
										.size(); j++) {
									tempTime = DateUtil.convertDateToString(
											holidayPachs.get(j)
													.getFdPachTime(),
											DateUtil.TYPE_DATE,
											request.getLocale());
									holidayPachMap.put(
											tempTime.split(" ")[0],
											tempTime.split(" ")[0]);
								}
							}
						}
					}
					// 剔除重复的数据（只剔除节假日的时间【补班和节假日】）
					if (holidayMap.size() > 0) {
						List<String> repeat = new ArrayList<String>(
								holidayMap.keySet());
						for (int i = 0; i < repeat.size(); i++) {
							if (!timeMap.containsKey(repeat.get(i))) {
								day = new JSONObject();
								day.put("createdAt", htt);
								day.put("fromDate", repeat.get(i));
								day.put("toDate", repeat.get(i));
								day.put("type", 4);
								day.put("color", "");
								days.add(day);
							}
						}
					}
					if (holidayPachMap.size() > 0) {
						List<String> repeat = new ArrayList<String>(
								holidayPachMap.keySet());
						for (int i = 0; i < repeat.size(); i++) {
							if (!timeMap.containsKey(repeat.get(i))) {
								day = new JSONObject();
								day.put("createdAt", htt);
								day.put("fromDate", repeat.get(i));
								day.put("toDate", repeat.get(i));
								day.put("type", 5);
								day.put("color", getColor(csmap, area,
										repeat.get(i), request));
								days.add(day);
							}
						}
					}
				}
			}
			request.setAttribute("lui-source", days);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getTaskApprove", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 获取按月批量排班数据 - 查看页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward mCalendarJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-getTaskApprove", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		JSONObject result = new JSONObject();
		JSONArray elements = new JSONArray();
		JSONArray data = new JSONArray();
		List<SysTimeWork> works = null;// 工作时间
		List<SysTimeVacation> vacations = null;// 假期时间
		List<SysTimePatchwork> patchworks = null;// 补班时间
		try {
			if (StringUtil.isNotNull(fdId)) {
				SysTimeArea area = (SysTimeArea) getServiceImp(request)
						.findByPrimaryKey(fdId);
				UserOperHelper.logFind(area);
				UserOperHelper.setOperSuccess(true);
				List<SysTimeOrgElementTime> orgElementTimeList =null;
				List<?> orgElementList = SysTimePersonUtil.expandToPerson(area.getAreaMembers());
				List<String> orgIds=new ArrayList<>();
				for (Object object : orgElementList) {
					JSONObject jsonObj = new JSONObject();
					SysOrgElement sysOrgElement = (SysOrgElement) object;
					jsonObj.accumulate("fdId", sysOrgElement.getFdId());
					jsonObj.accumulate("name", sysOrgElement.getFdName());
					jsonObj.accumulate("deptName",
							sysOrgElement.getFdParent() != null
									? sysOrgElement.getFdParent().getFdName()
									: "");
					elements.add(jsonObj);

					orgIds.add(sysOrgElement.getFdId());
				}
//				if(Boolean.TRUE.equals(area.getFdIsBatchSchedule())){
					orgElementTimeList = sysTimeAreaService.getOrgElementTimes(orgIds);
//				} else {
//					orgElementTimeList = area.getOrgElementTimeList();
//				}
				if(CollectionUtils.isNotEmpty(orgElementTimeList)) {
					for (SysTimeOrgElementTime sysTimeOrgElementTime : orgElementTimeList) {
						SysTimeArea timeArea = sysTimeOrgElementTime.getSysTimeArea();
						if(timeArea !=null && Boolean.TRUE.equals(area.getFdIsBatchSchedule()) && !timeArea.getFdId().equals(fdId)){
							//人员的所属区域跟当前区域不一致。将当前的区域组赋值给老的
							sysTimeOrgElementTime.setSysTimeArea(area);
						}
						SysOrgElement sysOrgElement = sysTimeOrgElementTime
								.getSysOrgElement();
						works = sysTimeOrgElementTime.getSysTimeWorkList();
						vacations = sysTimeOrgElementTime.getSysTimeVacationList();
						patchworks = sysTimeOrgElementTime
								.getSysTimePatchworkList();
						calendarJsonResult(data, works, vacations, patchworks,
								request, sysOrgElement, true);
					}
				}
				result.put("elements", elements);
				result.put("data", data);
				request.setAttribute("lui-source", result);
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getTaskApprove", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward searchUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String fdId = request.getParameter("fdId");
		String searchTxt = request.getParameter("searchTxt");
		JSONObject result = new JSONObject();
		try {
			List<String> list = new ArrayList();
			if (StringUtil.isNotNull(searchTxt) && StringUtil.isNotNull(fdId)) {
				SysTimeArea area = (SysTimeArea) getServiceImp(request)
						.findByPrimaryKey(fdId);
				List<SysOrgPerson> orgList =SysTimePersonUtil.expandToPerson(area.getAreaMembers());
				for(SysOrgPerson ele : orgList){
					String deptName = ele.getFdParent()!=null ? ele.getFdParent().getFdName().toLowerCase():"";
					String fdName = ele.getFdName().toLowerCase();
					String fdLoginName = ele.getFdLoginName().toLowerCase();
					if (fdName.indexOf(searchTxt.toLowerCase()) > -1
							|| fdLoginName.indexOf(searchTxt.toLowerCase()) > -1
							|| deptName.indexOf(searchTxt.toLowerCase()) > -1) {
						list.add(ele.getFdId());
					}
				}
			}
			result.put("status", "true");
			result.put("data", list);
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getActionForward("lui-source", mapping, form, request,
				response);
	}

	private void calendarJsonResult(JSONArray data, List<SysTimeWork> works,
			List<SysTimeVacation> vacations, List<SysTimePatchwork> patchworks,
			HttpServletRequest request, SysOrgElement sysOrgElement,
			boolean isBatch) {
		getWorkTimeResult(data, works, request, sysOrgElement, isBatch);
		getPatchWorkResult(data, patchworks, request, sysOrgElement, isBatch);
		getVacationResult(data, vacations, request, sysOrgElement, isBatch);
	}

	/**
	 * 获取批量排班中的人员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getOrgElementData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		JSONArray result = new JSONArray();
		if (!StringUtil.isNull(id)) {
			model = (SysTimeArea) getServiceImp(request).findByPrimaryKey(id,
					null, true);
			List<?> orgElementList = SysTimePersonUtil.expandToPerson(model.getAreaMembers());
			for (Object object : orgElementList) {
				JSONObject jsonObj = new JSONObject();
				SysOrgElement sysOrgElement = (SysOrgElement) object;
				jsonObj.accumulate("orgElementId", sysOrgElement.getFdId());
				jsonObj.accumulate("orgElementName", sysOrgElement.getFdName());
				result.add(jsonObj);
			}
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toString());
		}
	}

	/*
	 * 获取工作时间接口
	 */
	public void getWorkTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		List<SysTimeWork> timeWorkList = null;
		JSONArray result = new JSONArray();
		if (!StringUtil.isNull(id)) {
			model = (SysTimeArea) getServiceImp(request).findByPrimaryKey(id,
					null, true);
			timeWorkList = model.getSysTimeWorkList();
			getWorkTimeResult(result, timeWorkList, request, null, false);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toString());
		}
	}

	private void getWorkTimeResult(JSONArray result,
			List<SysTimeWork> timeWorkList, HttpServletRequest request,
			SysOrgElement sysOrgElement, boolean isBatch) {
		Collections.sort(timeWorkList, new Comparator<SysTimeWork>() {
			@Override
			public int compare(SysTimeWork o1, SysTimeWork o2) {
				int flag = o1.getDocCreateTime()
						.compareTo(o2.getDocCreateTime());
				return flag;
			}
		});
		for (SysTimeWork tw : timeWorkList) {
			JSONObject twObj = new JSONObject();
			twObj.accumulate("fdId", tw.getFdId());
			if (isBatch && sysOrgElement != null) {
				twObj.accumulate("elementId", sysOrgElement.getFdId());
				twObj.accumulate("elementName", sysOrgElement.getFdName());
				twObj.accumulate("date", DateUtil.convertDateToString(
						tw.getFdScheduleDate(), DateUtil.TYPE_DATE,request.getLocale()));
			} else {
				twObj.accumulate("startDate", DateUtil.convertDateToString(
						tw.getFdStartTime(), DateUtil.TYPE_DATE,request.getLocale()));
				twObj.accumulate("endDate", DateUtil.convertDateToString(
						tw.getFdEndTime(), DateUtil.TYPE_DATE,request.getLocale()));
				twObj.accumulate("fromWeek", tw.getFdWeekStartTime());
				twObj.accumulate("toWeek", tw.getFdWeekEndTime());
			}
			twObj.accumulate("type", 1);
			JSONArray workTimeJa = new JSONArray();
			JSONObject clazz = new JSONObject();
			SysTimeCommonTime commonTime = tw.getSysTimeCommonTime();
			if (commonTime == null) {
				continue;
			}
			List<SysTimeWorkDetail> workDetails = commonTime
					.getSysTimeWorkDetails();
			for (SysTimeWorkDetail details : workDetails) {
				JSONObject obj = new JSONObject();
				obj.accumulate("start",
						DateUtil.convertDateToString(
								details.getFdWorkStartTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				obj.accumulate("end",
						DateUtil.convertDateToString(
								details.getFdWorkEndTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				String overTimeType = "1";
				if (details.getFdOverTimeType() != null) {
					overTimeType = details.getFdOverTimeType().toString();
				}
				obj.accumulate("overTimeType", overTimeType);
				/**
				 * 最早最晚打卡时间
				 */
				/**
				 * 最早最晚打卡时间
				 */
				obj.accumulate("fdStartTime",
						DateUtil.convertDateToString(
								details.getFdStartTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				obj.accumulate("fdOverTime",
						DateUtil.convertDateToString(
								details.getFdOverTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				String endOverTimeType = "1";
				if (details.getFdEndOverTimeType() != null) {
					endOverTimeType = details.getFdEndOverTimeType().toString();
				}
				obj.put("fdEndOverTimeType", endOverTimeType);

				workTimeJa.add(obj);
			}
			clazz.accumulate("name", commonTime.getFdName());
			clazz.accumulate("color", commonTime.getFdWorkTimeColor());
			clazz.accumulate("fdId", commonTime.getFdId());
			clazz.accumulate("type", commonTime.getType());
			clazz.accumulate("restStart", DateUtil.convertDateToString(commonTime.getFdRestStartTime(), DateUtil.TYPE_TIME, null));
			clazz.accumulate("restEnd", DateUtil.convertDateToString(commonTime.getFdRestEndTime(), DateUtil.TYPE_TIME, null));
			clazz.accumulate("fdRestStartType",commonTime.getFdRestStartType());
			clazz.accumulate("fdRestEndType",commonTime.getFdRestEndType());

			clazz.accumulate("fdTotalDay", commonTime.getFdTotalDay());
			clazz.accumulate("times", workTimeJa);
			twObj.accumulate("clazz", clazz);
			result.add(twObj);
		}

	}

	/*
	 * 获取班次列表
	 */
	public JSONArray getTimeWorkTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		JSONArray ja = new JSONArray();
		List<SysTimeCommonTime> list = null;
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil
				.getBean("KmssBaseDao");
		if (!StringUtil.isNull(id)) {
			model = (SysTimeArea) getServiceImp(request).findByPrimaryKey(id);
			if (model.getFdIsBatchSchedule()) {
				list = new ArrayList<>();
				for (SysTimeOrgElementTime sysTimeOrgElementTime : model
						.getOrgElementTimeList()) {
					List<SysTimeWork> timeWorks = sysTimeOrgElementTime
							.getSysTimeWorkList();
					for (SysTimeWork sysTimeWork : timeWorks) {
						SysTimeCommonTime commonTime = sysTimeWork
								.getSysTimeCommonTime();
						if ("2".equals(commonTime.getType())) {
							if (!list.contains(commonTime)) {
								list.add(commonTime);
							}
						}
					}
				}
			} else {
				String sql = "select distinct sysTimeCommonTime  from SysTimeWork a where a.sysTimeArea.fdId=:fdId and a.sysTimeCommonTime.type='2'";
				Query query = baseDao.getHibernateSession().createQuery(
						sql).setParameter("fdId", id);
				list = query.list();
			}
		}
		for (SysTimeCommonTime commonTime : list) {
			JSONObject js = new JSONObject();
			JSONArray WorkTimeja = new JSONArray();
			js.put("name", commonTime.getFdName());
			js.put("color", commonTime.getFdWorkTimeColor());
			js.put("type", commonTime.getType());
			js.put("fdId", commonTime.getFdId());
			js.put("restStart", DateUtil.convertDateToString(commonTime.getFdRestStartTime(), DateUtil.TYPE_TIME, null));
			js.put("restEnd", DateUtil.convertDateToString(commonTime.getFdRestEndTime(), DateUtil.TYPE_TIME, null));
			js.put("fdTotalDay", commonTime.getFdTotalDay());
			js.put("fdRestStartType",commonTime.getFdRestStartType());
			js.put("fdRestEndType",commonTime.getFdRestEndType());
			for (SysTimeWorkDetail twt : commonTime
					.getSysTimeWorkDetails()) {
				JSONObject jso = new JSONObject();
				jso.put("fdWorkStartTime",
						twt.getFdWorkStartTime().getTime());
				jso.put("fdWorkEndTime",
						twt.getFdWorkEndTime().getTime());
				String overTimeType = "1";
				if (twt.getFdOverTimeType() != null) {
					overTimeType = twt.getFdOverTimeType().toString();
				}
				jso.put("fdOverTimeType", overTimeType);

				jso.put("fdStartTime",
						twt.getFdStartTime()==null?null:twt.getFdStartTime().getTime());
				jso.put("fdOverTime",
						twt.getFdOverTime()==null?null:twt.getFdOverTime().getTime());
				String endOverTimeType = "1";
				if (twt.getFdEndOverTimeType() != null) {
					endOverTimeType = twt.getFdEndOverTimeType().toString();
				}
				jso.put("fdEndOverTimeType", endOverTimeType);

				WorkTimeja.add(jso);
			}
			js.put("times", WorkTimeja);
			JSONObject newObj = new JSONObject();
			newObj.put("clazz", js);
			newObj.put("type", 1);
			ja.add(newObj);
		}
		return ja;

	}

	public JSONArray getPatchWorkTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		JSONArray ja = new JSONArray();
		List<SysTimeCommonTime> list = null;
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil
				.getBean("KmssBaseDao");
		if (!StringUtil.isNull(id)) {
			model = (SysTimeArea) getServiceImp(request).findByPrimaryKey(id);
			if (model.getFdIsBatchSchedule()) {
				list = new ArrayList<>();
				for (SysTimeOrgElementTime sysTimeOrgElementTime : model
						.getOrgElementTimeList()) {
					List<SysTimePatchwork> patchworks = sysTimeOrgElementTime
							.getSysTimePatchworkList();
					for (SysTimePatchwork sysTimePatchwork : patchworks) {
						SysTimeCommonTime commonTime = sysTimePatchwork
								.getSysTimeCommonTime();
						if ("2".equals(commonTime.getType())) {
							if (!list.contains(commonTime)) {
								list.add(commonTime);
							}
						}
					}
				}
			} else {
				String sql = "select distinct sysTimePatchwork.sysTimeCommonTime from com.landray.kmss.sys.time.model.SysTimePatchwork sysTimePatchwork where sysTimePatchwork.sysTimeArea.fdId=:fdId and sysTimePatchwork.sysTimeCommonTime.type='2'";
				Query query = baseDao.getHibernateSession().createQuery(
						sql).setParameter("fdId", id);
				list = query.list();
			}
		}
		for (SysTimeCommonTime commonTime : list) {
			JSONObject js = new JSONObject();
			JSONArray patchTimeja = new JSONArray();
			js.put("type", commonTime.getType());
			js.put("name", commonTime.getFdName());
			js.put("fdId", commonTime.getFdId());
			js.put("restStart", DateUtil.convertDateToString(commonTime.getFdRestStartTime(), DateUtil.TYPE_TIME, null));
			js.put("restEnd", DateUtil.convertDateToString(commonTime.getFdRestEndTime(), DateUtil.TYPE_TIME, null));
			js.put("fdTotalDay", commonTime.getFdTotalDay());
			js.put("fdRestStartType",commonTime.getFdRestStartType());
			js.put("fdRestEndType",commonTime.getFdRestEndType());
			js.put("color",
					commonTime.getFdWorkTimeColor());
			for (SysTimeWorkDetail pwt : commonTime
					.getSysTimeWorkDetails()) {
				JSONObject jso = new JSONObject();
				jso.put("fdWorkStartTime",
						pwt.getFdWorkStartTime().getTime());
				jso.put("fdWorkEndTime",
						pwt.getFdWorkEndTime().getTime());
				String overTimeType = "1";
				if (pwt.getFdOverTimeType() != null) {
					overTimeType = pwt.getFdOverTimeType().toString();
				}
				jso.put("fdOverTimeType", overTimeType);

				jso.put("fdOverTimeType", overTimeType);

				jso.put("fdStartTime",
						pwt.getFdStartTime()==null?null:pwt.getFdStartTime().getTime());
				jso.put("fdOverTime",
						pwt.getFdOverTime()==null?null:pwt.getFdOverTime().getTime());
				String endOverTimeType = "1";
				if (pwt.getFdEndOverTimeType() != null) {
					endOverTimeType = pwt.getFdEndOverTimeType().toString();
				}
				jso.put("fdEndOverTimeType", endOverTimeType);


				patchTimeja.add(jso);

			}
			js.put("times", patchTimeja);
			JSONObject newObj = new JSONObject();
			newObj.put("clazz", js);
			newObj.put("type", "3");
			newObj.put("name", commonTime.getFdName());

			ja.add(newObj);
		}

		return ja;

	}

	// 对干工作班次接口和补班班次接口合并
	public void getDefine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获取补班的班次
		JSONArray patchWork = getPatchWorkTime(mapping, form, request, response);
		//获取正常班次信息
		JSONArray workTime = getTimeWorkTime(mapping, form, request, response);
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, SysTimeConvert.class);
		List<SysTimeConvert> workList = mapper.readValue(workTime.toString(),javaType);
		List<SysTimeConvert> patchList = mapper.readValue(patchWork.toString(),javaType);
		List<SysTimeConvert> list = new ArrayList<>();
		List<String> listIds = new ArrayList<>();
		for (SysTimeConvert work : workList) {
			listIds.add(work.getClazz().getFdId());
		}
		for (SysTimeConvert patch : patchList) {
			for (SysTimeConvert work : workList) {
				if (listIds.contains(patch.getClazz().getFdId())) {
					break;
				}
				if (!((ISysTimeAreaService) getServiceImp(request))
						.equalList(work.getClazz().getTimes(),
								patch.getClazz().getTimes())
						|| !patch.getClazz().getColor()
								.equals(work.getClazz().getColor())) {
					list.add(patch);
					break;
				}
			}
		}
		workList.addAll(list);
		JSONArray ja = new JSONArray();
		for (SysTimeConvert sysTimeConvert : workList) {
			JSONObject obj = new JSONObject();
			JSONObject clazz = new JSONObject();
			clazz.put("type", "2");
			clazz.put("color", sysTimeConvert.getClazz().getColor());
			clazz.put("fdId", sysTimeConvert.getClazz().getFdId());
			JSONArray times = new JSONArray();
			for (SysTimeWorkDetail detail : sysTimeConvert.getClazz()
					.getTimes()) {
				JSONObject time = new JSONObject();
				// jakson GMT时区相差8小时
				time.put("start",
						DateUtil.convertDateToString(
								new Date(detail.getHbmWorkStartTime()
										- 8 * 60 * 60 * 1000),
								DateUtil.TYPE_TIME, null));
				time.put("end",
						DateUtil.convertDateToString(
								new Date(detail.getHbmWorkEndTime()
										- 8 * 60 * 60 * 1000),
								DateUtil.TYPE_TIME, null));
				String overTimeType = "1";
				if (detail.getFdOverTimeType() != null) {
					overTimeType = detail.getFdOverTimeType().toString();
				}
				time.put("overTimeType", overTimeType);

				if(detail.getHbmStartTime()!=null){
					time.put("fdStartTime",
							DateUtil.convertDateToString(
									new Date(detail.getHbmStartTime()
											- 8 * 60 * 60 * 1000),
									DateUtil.TYPE_TIME, null));
				}
				if(detail.getHbmFdOverTime() !=null) {
					time.put("fdOverTime",
							DateUtil.convertDateToString(
									new Date(detail.getHbmFdOverTime()
											- 8 * 60 * 60 * 1000),
									DateUtil.TYPE_TIME, null));
				}
				String endOverTimeType = "1";
				if (detail.getFdEndOverTimeType() != null) {
					endOverTimeType = detail.getFdEndOverTimeType().toString();
				}
				time.put("fdEndOverTimeType", endOverTimeType);

				times.add(time);
			}
			clazz.put("times", times);
			if( sysTimeConvert.getClazz().getRestStart() !=null){
				clazz.put("restStart",convertShowDate(sysTimeConvert.getClazz().getRestStart()));
				clazz.put("fdRestStartType",sysTimeConvert.getClazz().getFdRestStartType());
			}
			if( sysTimeConvert.getClazz().getRestEnd() !=null){
				clazz.put("restEnd",convertShowDate(sysTimeConvert.getClazz().getRestEnd()));
				clazz.put("fdRestEndType",sysTimeConvert.getClazz().getFdRestEndType());
			}

			clazz.put("fdTotalDay", sysTimeConvert.getClazz().getFdTotalDay());
			clazz.put("name", sysTimeConvert.getClazz().getName());
			obj.put("clazz", clazz);
			ja.add(obj);
		}
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(ja.toString());
	}

	private String convertShowDate(String date){
		if(date ==null){
			return null;
		}
		return DateUtil.convertDateToString(DateUtil.convertStringToDate(date),
				DateUtil.TYPE_TIME, null);
	}

	/*
	 * 获取补班时间接口
	 */
	public void getPatchWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		List<SysTimePatchwork> workPatchList = null;
		JSONArray result = new JSONArray();
		if (!StringUtil.isNull(id)) {
			model = (SysTimeArea) getServiceImp(request)
					.findByPrimaryKey(id, null, true);
			workPatchList = model.getSysTimePatchworkList();
			getPatchWorkResult(result, workPatchList, request,
					null, false);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toString());
		}
	}

	private void getPatchWorkResult(JSONArray result,
			List<SysTimePatchwork> workPatchList, HttpServletRequest request,
			SysOrgElement sysOrgElement, boolean isBatch) {
		Collections.sort(workPatchList, new Comparator<SysTimePatchwork>() {
			@Override
			public int compare(SysTimePatchwork o1, SysTimePatchwork o2) {
				int flag = o1.getDocCreateTime()
						.compareTo(o2.getDocCreateTime());
				return flag;
			}
		});
		for (SysTimePatchwork pw : workPatchList) {
			JSONObject pwObj = new JSONObject();
			pwObj.accumulate("fdId", pw.getFdId());
			pwObj.accumulate("type", 3);
			if (isBatch && sysOrgElement != null) {
				pwObj.accumulate("elementId", sysOrgElement.getFdId());
				pwObj.accumulate("elementName", sysOrgElement.getFdName());
				pwObj.accumulate("date", DateUtil.convertDateToString(
						pw.getFdScheduleDate(), DateUtil.TYPE_DATE,request.getLocale()));
			} else {
				pwObj.accumulate("startDate", DateUtil.convertDateToString(
						pw.getFdStartTime(), DateUtil.TYPE_DATE,request.getLocale()));
				pwObj.accumulate("endDate", DateUtil.convertDateToString(
						pw.getFdEndTime(), DateUtil.TYPE_DATE,request.getLocale()));
			}
			JSONArray patchJa = new JSONArray();
			JSONObject clazz = new JSONObject();
			SysTimeCommonTime commonTime = pw.getSysTimeCommonTime();
			List<SysTimeWorkDetail> workDetails = commonTime
					.getSysTimeWorkDetails();
			for (SysTimeWorkDetail details : workDetails) {
				JSONObject obj = new JSONObject();
				obj.accumulate("start",
						DateUtil.convertDateToString(
								details.getFdWorkStartTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				obj.accumulate("end",
						DateUtil.convertDateToString(
								details.getFdWorkEndTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				String overTimeType = "1";
				if (details.getFdOverTimeType() != null) {
					overTimeType = details.getFdOverTimeType().toString();
				}
				obj.accumulate("overTimeType", overTimeType);
				/**
				 * 最早最晚打卡时间时间转换
				 */
				obj.accumulate("fdStartTime",
						DateUtil.convertDateToString(
								details.getFdStartTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				obj.accumulate("fdOverTime",
						DateUtil.convertDateToString(
								details.getFdOverTime(),
								DateUtil.TYPE_TIME,
								request.getLocale()));
				String endOverTimeType = "1";
				if (details.getFdEndOverTimeType() != null) {
					endOverTimeType = details.getFdEndOverTimeType().toString();
				}
				obj.put("fdEndOverTimeType", endOverTimeType);
				patchJa.add(obj);
			}
			clazz.accumulate("color", commonTime.getFdWorkTimeColor());
			clazz.accumulate("fdId", commonTime.getFdId());
			clazz.accumulate("type", commonTime.getType());
			clazz.accumulate("name", commonTime.getFdName());
			clazz.accumulate("restStart", DateUtil.convertDateToString(commonTime.getFdRestStartTime(), DateUtil.TYPE_TIME, null));
			clazz.accumulate("restEnd", DateUtil.convertDateToString(commonTime.getFdRestEndTime(), DateUtil.TYPE_TIME, null));
			clazz.accumulate("fdRestStartType",commonTime.getFdRestStartType());
			clazz.accumulate("fdRestEndType",commonTime.getFdRestEndType());
			clazz.accumulate("fdTotalDay", commonTime.getFdTotalDay());
			clazz.accumulate("times", patchJa);
			pwObj.accumulate("clazz", clazz);
			result.add(pwObj);
		}
	}

	/*
	 * 获得假期时间接口
	 */
	public void getVacation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		List<SysTimeVacation> vacationTimeList = null;
		JSONArray result = new JSONArray();
		if (!StringUtil.isNull(id)) {
			model = (SysTimeArea) getServiceImp(request)
					.findByPrimaryKey(id, null, true);
			vacationTimeList = model.getSysTimeVacationList();
			getVacationResult(result, vacationTimeList, request,
					null, false);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result.toString());
		}
	}

	private void getVacationResult(JSONArray result,
			List<SysTimeVacation> vacationTimeList, HttpServletRequest request,
			SysOrgElement sysOrgElement, boolean isBatch) {
		for (SysTimeVacation vacation : vacationTimeList) {
			JSONObject obj = new JSONObject();
			obj.accumulate("fdId", vacation.getFdId());
			obj.accumulate("name", vacation.getFdName());
			obj.accumulate("type", 2);
			if (isBatch && sysOrgElement != null) {
				obj.accumulate("elementId", sysOrgElement.getFdId());
				obj.accumulate("elementName", sysOrgElement.getFdName());
				obj.accumulate("date", DateUtil.convertDateToString(
						vacation.getFdScheduleDate(), DateUtil.TYPE_DATE,request.getLocale()));
			} else {
				obj.accumulate("startDate", DateUtil.convertDateToString(
						vacation.getFdStartDate(), DateUtil.TYPE_DATE,
						request.getLocale()));
				obj.accumulate("endDate", DateUtil.convertDateToString(
						vacation.getFdEndDate(), DateUtil.TYPE_DATE,
						request.getLocale()));
			}
			result.add(obj);
		}
	}

	/*
	 * 获得节假日数据接口
	 */
	public void getHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			SysTimeArea model = (SysTimeArea) getServiceImp(request)
					.findByPrimaryKey(id, null, true);
			JSONArray jaHoliday = new JSONArray();
			if (model.getFdHoliday() == null) {
				return;
			}
			for (Object object : model.getFdHoliday()
					.getFdHolidayDetailList()) {
				JSONObject holiday = new JSONObject();

				SysTimeHolidayDetail detail = (SysTimeHolidayDetail) object;

				holiday.accumulate("fdId", detail.getFdId());
				holiday.accumulate("name", detail.getFdName());
				holiday.accumulate("startDate", DateUtil.convertDateToString(
						detail.getFdStartDay(),
						DateUtil.TYPE_DATE,
						request.getLocale()));
				holiday.accumulate("endDate",
						DateUtil.convertDateToString(
								detail.getFdEndDay(),
								DateUtil.TYPE_DATE,
								request.getLocale()));

				if (StringUtil.isNotNull(detail.getFdPatchDay())) {
					holiday.accumulate("patchDay",
							detail.getFdPatchDay().split(","));
				}

				jaHoliday.add(holiday);
			}
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jaHoliday.toString());
		}
	}

	public ActionForward editCalendarJson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editCalendarJson", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			String fdId = request.getParameter("fdId");
			String data = request.getParameter("data");
			String fdHolidayId = request.getParameter("fdHolidayId");
			((ISysTimeAreaService) getServiceImp(request)).updateCalendar(data,
					fdId, fdHolidayId, "1");

			JSONObject o = new JSONObject();
			o.put("success", true);
			request.setAttribute("lui-source", o);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editCalendarJson", false,
				getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 保存人员按月批量排班数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrgElementData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysTimeAreaForm sysTimeAreaForm = (SysTimeAreaForm) form;
		String fdId = sysTimeAreaForm.getFdId();
		String data = sysTimeAreaForm.getOrgElementData();
		String fdHolidayId = sysTimeAreaForm.getFdHolidayId();
		if (!messages.hasError()) {
			try {
				if (!"POST".equals(request.getMethod())) {
					throw new UnexpectedRequestException();
				}
				((ISysTimeAreaService) getServiceImp(request)).updateCalendar(
						data, fdId, fdHolidayId, "2");
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public void validateColor(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		String type = request.getParameter("type");
		String color = request.getParameter("color");
		boolean exist = false;
		if ("1".equals(type) && !getWorkTimeColor(id).contains(color)) {
			exist = true;

		}
		if ("2".equals(type) && !getPatchTimeColor(id).contains(color)) {
			exist = true;
		}
		JSONObject obj = new JSONObject();
		obj.put("exist", exist);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(obj.toString());

	}

	private List getWorkTimeColor(String fdId) {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil
				.getBean("KmssBaseDao");
		String sql = "select distinct a.fdTimeWorkColor from SysTimeWork a where a.sysTimeArea.fdId=:fdId";
		Query query = baseDao.getHibernateSession().createQuery(
				sql).setParameter("fdId", fdId);
		return query.list();

	}

	private List getPatchTimeColor(String fdId) {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil
				.getBean("KmssBaseDao");
		String sql = "select distinct a.fdPatchWorkColor from SysTimePatchwork a where a.sysTimeArea.fdId=:fdId ";
		Query query = baseDao.getHibernateSession().createQuery(
				sql).setParameter("fdId", fdId);
		return query.list();

	}

	private String getColor(Map map) throws Exception {
		String color = "";
		String[] colors = { "10C42F", "FF5900", "0DB2AD", "EEA506", "E106AF",
				"9514D1", "4634C3", "10C42F", "FF5858" };
		for (int i = 0; i < colors.length; i++) {
			if (!map.containsKey("#" + colors[i])) {
				color = "#" + colors[i];
				map.put(color, color);
				break;
			}
		}
		if (StringUtil.isNull(color)) {
			color = "#10C42F";
		}
		return color;
	}

	private String getColor(Map<String, String> map, SysTimeArea area,
			String time, HttpServletRequest request) throws Exception {
		String color = "";
		List<SysTimeWork> works = area.getSysTimeWorkList();
		if (StringUtil.isNull(time) || works == null || works.isEmpty()) {
			return color;
		}
		Date sdate = null;
		Date edate = null;
		time = time.split(" ")[0];
		Date date = DateUtil.convertStringToDate(time, DateUtil.TYPE_DATE,
				request.getLocale());
		for (int i = 0; i < works.size(); i++) {
			sdate = works.get(i).getFdStartTime();
			edate = works.get(i).getFdEndTime();
			if (edate != null && date.getTime() >= sdate.getTime()
					&& date.getTime() <= edate.getTime()) {
				color = map.get(works.get(i).getFdId());
				break;
			} else if (edate == null && date.getTime() >= sdate.getTime()) {
				color = map.get(works.get(i).getFdId());
				break;
			}
		}
		// 获取前后一个星期的工作日设置
		if (StringUtil.isNull(color)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			boolean flag = false;
			for (int j = 1; j <= 14; j++) {
				for (int i = 0; i < works.size(); i++) {
					sdate = works.get(i).getFdStartTime();
					edate = works.get(i).getFdEndTime();
					if (edate != null
							&& cal.getTimeInMillis() >= sdate.getTime()
							&& cal.getTimeInMillis() <= edate.getTime()) {
						color = map.get(works.get(i).getFdId());
						flag = true;
						break;
					} else if (edate == null
							&& cal.getTimeInMillis() >= sdate.getTime()) {
						color = map.get(works.get(i).getFdId());
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		// 获取结束时间为空颜色，获取规则是取创建时间最晚的
		if (StringUtil.isNull(color)) {
			long st = 0L;
			for (int i = 0; i < works.size(); i++) {
				sdate = works.get(i).getFdStartTime();
				edate = works.get(i).getFdEndTime();
				if (sdate != null && edate == null) {
					if (st == 0) {
						st = works.get(i).getDocCreateTime().getTime();
					} else if (st < works.get(i).getDocCreateTime().getTime()) {
						st = works.get(i).getDocCreateTime().getTime();
					}
				}
			}
			for (int i = 0; i < works.size(); i++) {
				sdate = works.get(i).getFdStartTime();
				edate = works.get(i).getFdEndTime();
				if (sdate != null && edate == null
						&& st == works.get(i).getDocCreateTime().getTime()) {
					color = map.get(works.get(i).getFdId());
					break;
				}
			}
		}
		return color;
	}

	private ISysTimeHolidayPachService sysTimeHolidayPachService = null;

	public ISysTimeHolidayPachService getSysTimeHolidayPachService() {
		if (sysTimeHolidayPachService == null) {
			sysTimeHolidayPachService = (ISysTimeHolidayPachService) SpringBeanUtil
					.getBean("sysTimeHolidayPachService");
		}
		return sysTimeHolidayPachService;
	}

	private void getDay(String startDate, String endDate, Map dayMap,
			HttpServletRequest request) {
		if (StringUtil.isNull(startDate) || StringUtil.isNull(endDate)) {
			return;
		}
		startDate = startDate.split(" ")[0];
		endDate = endDate.split(" ")[0];
		dayMap.put(startDate, startDate);
		Date sdate = DateUtil.convertStringToDate(startDate, DateUtil.TYPE_DATE,
				request.getLocale());
		Date edate = DateUtil.convertStringToDate(endDate, DateUtil.TYPE_DATE,
				request.getLocale());
		if (sdate.getTime() > edate.getTime()) {
			return;
		}
		if (!startDate.equals(endDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.convertStringToDate(startDate,
					DateUtil.TYPE_DATE, request.getLocale()));
			int count = 0;
			do {
				count++;
				cal.add(Calendar.DAY_OF_MONTH, 1);
				startDate = DateUtil.convertDateToString(cal.getTime(),
						DateUtil.TYPE_DATE, request.getLocale());
				startDate = startDate.split(" ")[0];
				dayMap.put(startDate, startDate);
				if (count > 365 * 10) {
					break;// 异常处理，强制退出
				}
			} while (!startDate.equals(endDate));
		}
		return;
	}

	public void getHPDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getHPDay", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray ja = new JSONArray();
		try {
			String userId = UserUtil.getUser().getFdId();
			ja = getSysTimeCountService().getHolidayPachDay(userId);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		} finally {
			if (ja != null) {
				response.setHeader("content-type", "application/json;charset=utf-8");
				response.setCharacterEncoding("utf-8");
				PrintWriter printWriter = response.getWriter();
				printWriter.print(ja.toString());
				printWriter.flush();
			}
		}
		TimeCounter.logCurrentTime("Action-getHPDay", false, getClass());
	}

	private ISysOrgElementService sysOrgElementService = null;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public ActionForward listTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			String shour = ResourceUtil
					.getString("calendar.simulator.time.unit.hour", "sys-time");
			String sec = ResourceUtil
					.getString("calendar.simulator.time.unit.sec", "sys-time");
			int day = rowsize * (pageno - 1);
			if (pageno == 0) {
				day = 0;
			}
			String userId = request.getParameter("fdUserId");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			Date st = DateUtil.convertStringToDate(startTime,
					DateUtil.TYPE_DATETIME, request.getLocale());
			Date et = new Date(DateUtil.convertStringToDate(endTime,
					DateUtil.TYPE_DATETIME, request.getLocale()).getTime());
			SysOrgElement person = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(userId, null, true);
			SysTimeArea area = getSysTimeCountService().getTimeArea(person);
			Calendar cal = Calendar.getInstance();
			cal.setTime(st);
			cal.add(Calendar.DAY_OF_MONTH, day);
			List list = new ArrayList();
			Map<String, String> map = null;
			StringBuffer message = new StringBuffer();
			StringBuffer sbs = new StringBuffer();
			for (int i = day; i < day + rowsize; i++) {
				map = new HashMap<String, String>();
				message.setLength(0);
				sbs.setLength(0);
				if (et.getTime() > cal.getTimeInMillis()) {
					map.put("name", area.getFdName());
					map.put("uname", person.getFdName());
					map.put("time", DateUtil.convertDateToString(cal.getTime(),
							DateUtil.TYPE_DATE, request.getLocale()));
					List<String> ls = getSysTimeCountService()
							.getWorkState(userId, cal.getTime());
					for (String stt : ls) {
						if (sbs.length() == 0) {
							sbs.append(stt);
						} else {
							sbs.append(";" + stt);
						}
					}
					map.put("state", sbs.toString());
					cal = DateUtil.removeTime(cal.getTime());
					long fdStartTime = cal.getTimeInMillis();
					long fdEndTime = cal.getTimeInMillis() + 86400000L - 1;
					if (fdStartTime < st.getTime()) {
						fdStartTime = st.getTime();
					}
					if (fdEndTime > et.getTime()) {
						fdEndTime = et.getTime();
					}
					long time = getSysTimeCountService().getManHour(userId,
							fdStartTime, fdEndTime);
					Double minus =Math.ceil(time / 60000D);
					int hour = (int)(minus/60D);
					if (hour > 0) {
						message.append(hour + shour);
					}
					int minu = (int) (minus % 60D);
					if (minu > 0) {
						message.append(minu + sec);
					}
					if (message.length() == 0) {
						message.append("0" + shour);
					}
					map.put("wtime", message.toString());
					list.add(map);
					cal.add(Calendar.DAY_OF_MONTH, 1);

				}
			}
			Page page = Page.getEmptyPage();
			page.setRowsize(rowsize);
			page.setPageno(pageno);
			int size = (int) ((et.getTime() - st.getTime()) / 86400000L);
			if ((et.getTime() - st.getTime()) % 86400000L > 0) {
				size += 1;
			}
			page.setTotalrows(size);
			page.excecute();
			page.setList(list);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listTime", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("listTime", mapping, form, request,
					response);
		}
	}

	/**
	 * 得到通用班次 和 该区域组的自定义班次
	 * 
	 * @throws Exception
	 */
	private List<SysTimeCommonTime> getcommonTimeList(SysTimeArea sysTimeArea)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "type = '1' and status='true' ";
		hqlInfo.setWhereBlock(whereBlock);
		List<SysTimeCommonTime> commonTimes = commonTimeService
				.findList(hqlInfo);
		for (SysTimeOrgElementTime sysTimeOrgElementTime : sysTimeArea
				.getOrgElementTimeList()) {
			List<SysTimePatchwork> patchWorks = sysTimeOrgElementTime
					.getSysTimePatchworkList();
			for (SysTimePatchwork sysTimePatchwork : patchWorks) {
				SysTimeCommonTime commonTime = sysTimePatchwork
						.getSysTimeCommonTime();
				if ("2".equals(commonTime.getType())
						&& !commonTimes.contains(commonTime)) {
					commonTimes.add(commonTime);
				}
			}
			List<SysTimeWork> timeWorks = sysTimeOrgElementTime
					.getSysTimeWorkList();
			for (SysTimeWork sysTimeWork : timeWorks) {
				SysTimeCommonTime commonTime = sysTimeWork
						.getSysTimeCommonTime();
				if ("2".equals(commonTime.getType())
						&& !commonTimes.contains(commonTime)) {
					commonTimes.add(commonTime);
				}
			}
		}
		return commonTimes;
	}

	private HSSFSheet setValidation(HSSFWorkbook workbook, HSSFSheet sheet,
			SysTimeArea model,
			int colNum) throws Exception {
		List<String> resultList = new ArrayList<>();
		List<SysTimeCommonTime> commonTimes = getcommonTimeList(model);
		for (SysTimeCommonTime commonTime : commonTimes) {
			String result = "1:工作日;" + commonTime.getFdName() + ":"
					+ commonTime.getFdId();
			resultList.add(result);
			result = "3:补班;" + commonTime.getFdName() + ":"
					+ commonTime.getFdId();
			resultList.add(result);
		}
		resultList.add("2:休假");
		String[] resultArr = new String[resultList.size()];
		resultList.toArray(resultArr);
		Arrays.sort(resultArr);
		sheet = setHSSFValidation(workbook, sheet, resultArr, 1, 1000, 2,
				(colNum - 1));
		return sheet;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * 
	 * @param workbook
	 * 
	 * @param sheet
	 *            要设置的sheet.
	 * @param textlist
	 *            下拉框显示的内容
	 * @param firstRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param endCol
	 *            结束列
	 * @return 设置好的sheet.
	 */
	private HSSFSheet setHSSFValidation(HSSFWorkbook workbook, HSSFSheet sheet,
			String[] textlist, int firstRow, int endRow, int firstCol,
			int endCol) {
		String hiddenStr = "hiddenSheet";
		HSSFSheet hiddenSheet = workbook.createSheet(); // 创建隐藏域
		workbook.setSheetName(1, hiddenStr);
		for (int i = 0, length = textlist.length; i < length; i++) { // 循环赋值（为了防止下拉框的行数与隐藏域的行数相对应来获取>=选中行数的数组，将隐藏域加到结束行之后）
			hiddenSheet.createRow(i).createCell(0)
					.setCellValue(textlist[i]);
		}
		Name name = workbook.createName();
		name.setNameName(hiddenStr);
		name.setRefersToFormula(
				hiddenStr + "!$A$1:$A$" + (textlist.length)); // A1:A代表隐藏域创建第?列createCell(?)时。以A1列开始A行数据获取下拉数组
		DVConstraint constraint = DVConstraint
				.createFormulaListConstraint(hiddenStr);
		// 加载下拉列表内容
		// DVConstraint constraint = DVConstraint
		// .createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,
				endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(
				regions, constraint);
		workbook.setSheetHidden(1, true);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	/**
	 * 批量导入
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysTimeAreaForm areaForm = (SysTimeAreaForm) form;
		UploadResultBean result = new UploadResultBean();// 操作结果集
		FormFile file = areaForm.getFile();
		String id = request.getParameter("fdId");
		SysTimeArea model = null;
		SysOrgPerson currentUser = UserUtil.getUser();
		Date currentDate = new Date();
		List<SysTimeOrgElementTime> orgElementTimeList = null;
		if (StringUtil.isNotNull(id)) {
			model = (SysTimeArea) getServiceImp(request).findByPrimaryKey(id);
			List<SysOrgPerson> personList = SysTimePersonUtil.expandToPerson(model.getAreaMembers());
			if (file == null) {
				request.setAttribute("sysTimeAreaForm", areaForm);
				ActionForward forward = mapping.findForward("importExcel");
				return forward;
			} else {
				POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				orgElementTimeList = model.getOrgElementTimeList();
				// Excel行内容为空
				if (sheet.getLastRowNum() < 1) {
					String[] fdCodeArgs3 = {
							"" + 1,
							ResourceUtil.getString(
									"sysTimeArea.excel.import.fileIsEmpty",
									"sys-time") };
					result.addErrorMsg(ResourceUtil.getString(
							"sysTimeArea.excel.import.message", "sys-time",
							null,
							fdCodeArgs3));
					result.addFailCount();

				} else {
					boolean hasError = false;
					boolean isEmpty = false;
					HSSFRow titleRow = sheet.getRow(0);
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						HSSFRow row = sheet.getRow(i);
						Cell loginNameCell = row.getCell(1);
						if (loginNameCell == null) {
							continue;
						}
						String loginName = getCellValue(loginNameCell);
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(
								"sysOrgPerson.fdLoginName =:loginName");
						hqlInfo.setParameter("loginName", loginName);
						List<?> list = getSysOrgPersonService()
								.findValue(hqlInfo);
						SysTimeOrgElementTime orgElementTime = null;
						if (list != null && list.size() > 0) {
							SysOrgPerson person = (SysOrgPerson) list.get(0);
							if (personList.contains(person)) {
								if (orgElementTimeList != null
										&& orgElementTimeList.size() > 0) {
									for (int k = 0; k < orgElementTimeList
											.size(); k++) {
										SysOrgElement sysOrgPerson = orgElementTimeList
												.get(k).getSysOrgElement();
										if (sysOrgPerson.getFdId()
												.equals(person.getFdId())) {
											orgElementTime = orgElementTimeList
													.get(k);
											break;
										}
									}
									if (orgElementTime==null) {
										isEmpty = true;
										orgElementTime = new SysTimeOrgElementTime();
										orgElementTime.setSysOrgElement(person);
									}
								} else {
									isEmpty = true;
									orgElementTimeList = new ArrayList<>();
									orgElementTime = new SysTimeOrgElementTime();
									orgElementTime.setSysOrgElement(person);
								}
								
								List<SysTimeWork> workList = orgElementTime
										.getSysTimeWorkList();
								List<SysTimePatchwork> patchworkList = orgElementTime
										.getSysTimePatchworkList();
								List<SysTimeVacation> vacationList = orgElementTime
										.getSysTimeVacationList();
								for (int j = 2; j < row
										.getLastCellNum(); j++) {
									String typeAndCommonTime = getCellValue(
											row.getCell(j));
									if (StringUtil
											.isNotNull(
													typeAndCommonTime)) {
										String type = typeAndCommonTime
												.split(";")[0]
														.split(":")[0];
										if ("1".equals(type)) {
											String[] typeAndCommonTimeStrings=typeAndCommonTime.split(";");
											String fdId = "";
											if(typeAndCommonTimeStrings.length>0) {
												String[] commonTimeStrings =typeAndCommonTimeStrings[typeAndCommonTimeStrings.length-1].split(":");
												if(commonTimeStrings.length>0) {
													fdId = commonTimeStrings[commonTimeStrings.length-1];
												}
											}
											if (StringUtil.isNotNull(
													fdId)) {
												SysTimeCommonTime commonTime = (SysTimeCommonTime) commonTimeService
														.findByPrimaryKey(
																fdId);
												if (commonTime != null) {
													String scheduleDate = getCellValue(
															titleRow.getCell(
																	j));
													if (scheduleDate
															.indexOf(
																	"(") != -1) {
														scheduleDate = scheduleDate
																.substring(
																		0,
																		scheduleDate
																				.indexOf(
																						"("));
													}
													if (StringUtil
															.isNotNull(
																	scheduleDate)) {
														if (DateUtil
																.convertStringToDate(
																		scheduleDate,
																		DateUtil.PATTERN_DATE)
																.getTime() < DateUtil
																		.getDateNumber(
																				currentDate)) {
															String[] fdCodeArgs3 = {
																	"" + (i + 1),
																	"" + (j + 1),
																	scheduleDate
																			+ ResourceUtil
																					.getString(
																							"sysTimeArea.excel.error.date.isOverdue",
																							"sys-time") };
															result.addErrorMsg(
																	ResourceUtil
																			.getString(
																					"sysTimeArea.excel.import.messageIgore2",
																					"sys-time",
																					null,
																					fdCodeArgs3));
															result.addIgoreCount();
															continue;
														}

														// 移除工作日，补班和假期是否存在该日期数据
														removeExistData(
																scheduleDate,
																workList,
																patchworkList,
																vacationList);
														SysTimeWork sysTimeWork = new SysTimeWork();
														sysTimeWork
																.setSysTimeCommonTime(
																		commonTime);
														sysTimeWork
																.setFdScheduleDate(
																		DateUtil.convertStringToDate(
																				scheduleDate,
																				DateUtil.PATTERN_DATE));
														sysTimeWork.setTimeType(
																commonTime
																		.getType());
														sysTimeWork
																.setFdTimeWorkColor(
																		commonTime
																				.getFdWorkTimeColor());
														sysTimeWork
																.setDocCreator(
																		currentUser);
														workList.add(
																sysTimeWork);
														result.addSuccessCount();
														orgElementTime
																.setSysTimeWorkList(
																		workList);
													} else {
														hasError = true;
														String[] fdCodeArgs4 = {
																"" + (i + 1),
																"" + (j + 1),
																ResourceUtil
																		.getString(
																				"sysTimeArea.excel.fdDate",
																				"sys-time"),
																ResourceUtil
																		.getString(
																				"sysTimeArea.excel.error.no.date",
																				"sys-time") };
														result.addErrorMsg(
																ResourceUtil
																		.getString(
																				"sysTimeArea.excel.import.message4",
																				"sys-time",
																				null,
																				fdCodeArgs4));
													}
												} else {
													hasError = true;
													String[] fdCodeArgs4 = {
															"" + (i + 1),
															"" + (j + 1),
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.fdCommonTime",
																			"sys-time"),
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.error.common.time.is.null",
																			"sys-time") };
													result.addErrorMsg(
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.import.message4",
																			"sys-time",
																			null,
																			fdCodeArgs4));
												}
											} else {
												hasError = true;
												String[] fdCodeArgs4 = {
														"" + (i + 1),
														"" + (j + 1),
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.fdCommonTime",
																		"sys-time"),
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.error.no.common.time",
																		"sys-time") };
												result.addErrorMsg(
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.import.message4",
																		"sys-time",
																		null,
																		fdCodeArgs4));
											}
										} else if ("3".equals(type)) {
											String[] typeAndCommonTimeStrings=typeAndCommonTime.split(";");
											String fdId = "";
											if(typeAndCommonTimeStrings.length>0) {
												String[] commonTimeStrings =typeAndCommonTimeStrings[typeAndCommonTimeStrings.length-1].split(":");
												if(commonTimeStrings.length>0) {
													fdId = commonTimeStrings[commonTimeStrings.length-1];
												}
											}
											if (StringUtil.isNotNull(
													fdId)) {
												SysTimeCommonTime commonTime = (SysTimeCommonTime) commonTimeService
														.findByPrimaryKey(
																fdId);
												if (commonTime != null) {
													String scheduleDate = getCellValue(
															titleRow.getCell(
																	j));
													if (scheduleDate
															.indexOf(
																	"(") != -1) {
														scheduleDate = scheduleDate
																.substring(
																		0,
																		scheduleDate
																				.indexOf(
																						"("));
													}
													if (StringUtil
															.isNotNull(
																	scheduleDate)) {
														if (DateUtil
																.convertStringToDate(
																		scheduleDate,
																		DateUtil.PATTERN_DATE)
																.getTime() < DateUtil
																		.getDateNumber(
																				currentDate)) {
															String[] fdCodeArgs3 = {
																	"" + (i + 1),
																	"" + (j + 1),
																	scheduleDate
																			+ ResourceUtil
																					.getString(
																							"sysTimeArea.excel.error.date.isOverdue",
																							"sys-time") };
															result.addErrorMsg(
																	ResourceUtil
																			.getString(
																					"sysTimeArea.excel.import.messageIgore2",
																					"sys-time",
																					null,
																					fdCodeArgs3));
															result.addIgoreCount();
															continue;
														}
														// 移除工作日，补班和假期是否存在该日期数据
														removeExistData(
																scheduleDate,
																workList,
																patchworkList,
																vacationList);
														SysTimePatchwork patchwork = new SysTimePatchwork();
														patchwork
																.setSysTimeCommonTime(
																		commonTime);
														patchwork
																.setFdScheduleDate(
																		DateUtil.convertStringToDate(
																				scheduleDate,
																				DateUtil.PATTERN_DATE));
														patchwork.setFdName(
																commonTime
																		.getFdName());
														patchwork.setTimeType(
																commonTime
																		.getType());
														patchwork
																.setFdPatchWorkColor(
																		commonTime
																				.getFdWorkTimeColor());
														patchwork
																.setDocCreator(
																		currentUser);
														patchworkList
																.add(patchwork);

														result.addSuccessCount();
														orgElementTime
																.setSysTimePatchworkList(
																		patchworkList);
													} else {
														hasError = true;
														String[] fdCodeArgs4 = {
																"" + (i + 1),
																"" + (j + 1),
																ResourceUtil
																		.getString(
																				"sysTimeArea.excel.fdDate",
																				"sys-time"),
																ResourceUtil
																		.getString(
																				"sysTimeArea.excel.error.no.date",
																				"sys-time") };
														result.addErrorMsg(
																ResourceUtil
																		.getString(
																				"sysTimeArea.excel.import.message4",
																				"sys-time",
																				null,
																				fdCodeArgs4));
													}
												} else {
													hasError = true;
													String[] fdCodeArgs4 = {
															"" + (i + 1),
															"" + (j + 1),
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.fdCommonTime",
																			"sys-time"),
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.error.common.time.is.null",
																			"sys-time") };
													result.addErrorMsg(
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.import.message4",
																			"sys-time",
																			null,
																			fdCodeArgs4));
												}
											} else {
												hasError = true;
												String[] fdCodeArgs4 = {
														"" + (i + 1),
														"" + (j + 1),
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.fdCommonTime",
																		"sys-time"),
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.error.no.common.time",
																		"sys-time") };
												result.addErrorMsg(
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.import.message4",
																		"sys-time",
																		null,
																		fdCodeArgs4));
											}
										} else if ("2".equals(type)) {
											String scheduleDate = getCellValue(
													titleRow.getCell(
															j));
											if (scheduleDate
													.indexOf(
															"(") != -1) {
												scheduleDate = scheduleDate
														.substring(
																0,
																scheduleDate
																		.indexOf(
																				"("));
											}
											if (StringUtil.isNotNull(
													scheduleDate)) {
												if (DateUtil
														.convertStringToDate(
																scheduleDate,
																DateUtil.PATTERN_DATE)
														.getTime() < DateUtil
																.getDateNumber(
																		currentDate)) {
													String[] fdCodeArgs3 = {
															"" + (i + 1),
															"" + (j + 1),
															scheduleDate
																	+ ResourceUtil
																			.getString(
																					"sysTimeArea.excel.error.date.isOverdue",
																					"sys-time") };
													result.addErrorMsg(
															ResourceUtil
																	.getString(
																			"sysTimeArea.excel.import.messageIgore2",
																			"sys-time",
																			null,
																			fdCodeArgs3));
													result.addIgoreCount();
													continue;
												}
												// 移除工作日，补班和假期是否存在该日期数据
												removeExistData(
														scheduleDate,
														workList,
														patchworkList,
														vacationList);
												SysTimeVacation sysTimeVacation = new SysTimeVacation();
												sysTimeVacation
														.setFdScheduleDate(
																DateUtil.convertStringToDate(
																		scheduleDate,
																		DateUtil.PATTERN_DATE));
												sysTimeVacation
														.setFdName(
																"假期");
												sysTimeVacation
														.setDocCreator(
																currentUser);
												vacationList
														.add(sysTimeVacation);
												result.addSuccessCount();
												orgElementTime
														.setSysTimeVacationList(
																vacationList);
											} else {
												hasError = true;
												String[] fdCodeArgs4 = {
														"" + (i + 1),
														"" + (j + 1),
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.fdDate",
																		"sys-time"),
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.error.no.date",
																		"sys-time") };
												result.addErrorMsg(
														ResourceUtil
																.getString(
																		"sysTimeArea.excel.import.message4",
																		"sys-time",
																		null,
																		fdCodeArgs4));
											}
										}
									}
								}
								if (isEmpty) {
									orgElementTimeList
											.add(orgElementTime);
								}
							} else {
								String[] fdCodeArgs2 = {
										"" + (i + 1),
										ResourceUtil.getString(
												"sysTimeArea.excel.error.not.in.area",
												"sys-time") };
								result.addErrorMsg(ResourceUtil.getString(
										"sysTimeArea.excel.import.messageIgore",
										"sys-time",
										null, fdCodeArgs2));
								result.addIgoreCount();
								continue;
							}
						} else {
							hasError = true;
							String[] fdCodeArgs2 = {
									"" + (i + 1),
									ResourceUtil.getString(
											"sysTimeArea.excel.error.no.person",
											"sys-time") };
							result.addErrorMsg(ResourceUtil.getString(
									"sysTimeArea.excel.import.messageIgore",
									"sys-time",
									null, fdCodeArgs2));
							result.addIgoreCount();
							continue;
						}
						if (hasError) {
							result.addFailCount();
						}
					}
					try {
						model.setOrgElementTimeList(orgElementTimeList);
						if (UserOperHelper.allowLogOper("importExcel",
								getServiceImp(request).getModelName())) {
							UserOperContentHelper.putUpdate(model);
						}
						getServiceImp(request).update(model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				request.setAttribute("result", result);
			}
		}
		return mapping.findForward("importExcel");
	}

	/**
	 * 移除该日期已经存在的数据
	 * 
	 * @param scheduleDate
	 * @param workList
	 * @param patchworkList
	 * @param vacationList
	 */
	private void removeExistData(String scheduleDate,
			List<SysTimeWork> workList,
			List<SysTimePatchwork> patchworkList,
			List<SysTimeVacation> vacationList) {
		int index = -1;
		for (SysTimeWork work : workList) {
			if (scheduleDate
					.equals(DateUtil
							.convertDateToString(
									work.getFdScheduleDate(),
									DateUtil.PATTERN_DATE))) {
				index = workList.indexOf(work);
			}
		}
		if (index != -1) {
			workList.remove(index);
			index = -1;
		}

		for (SysTimePatchwork patchwork : patchworkList) {
			if (scheduleDate
					.equals(DateUtil
							.convertDateToString(
									patchwork.getFdScheduleDate(),
									DateUtil.PATTERN_DATE))) {
				index = patchworkList.indexOf(patchwork);
			}
		}
		if (index != -1) {
			patchworkList.remove(index);
			index = -1;
		}

		for (SysTimeVacation vacation : vacationList) {
			if (scheduleDate
					.equals(DateUtil
							.convertDateToString(
									vacation.getFdScheduleDate(),
									DateUtil.PATTERN_DATE))) {
				index = vacationList.indexOf(vacation);
			}
		}
		if (index != -1) {
			vacationList.remove(index);
			index = -1;
		}

	}

	private String getCellValue(Cell cell) {
		return ImportUtil.getCellValue(cell);
	}

	private String getTimeAndWeek(int year, int month, int day) {
		StringBuilder result = new StringBuilder();
		result.append(year).append("-");
		if (month < 9) {
			result.append("0");
		}
		result.append((month + 1)).append("-");
		if (day < 10) {
			result.append("0");
		}
		result.append(day).append("(");

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (w) {
		case 0:
			result.append("周日");
			break;
		case 1:
			result.append("周一");
			break;
		case 2:
			result.append("周二");
			break;
		case 3:
			result.append("周三");
			break;
		case 4:
			result.append("周四");
			break;
		case 5:
			result.append("周五");
			break;
		case 6:
			result.append("周六");
			break;
		default:
			break;
		}
		result.append(")");
		return result.toString();
	}

	/**
	 * 导出excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("fdId");
		String fdYear = request.getParameter("fdYear");
		String fdMonth = request.getParameter("fdMonth");
		String isTemplate = request.getParameter("isTemplate");// 是否为下载模版
		int year = Integer.parseInt(fdYear);
		int month = Integer.parseInt(fdMonth);
		Calendar calendar = Calendar.getInstance();
		SysTimeArea model = null;
		if (StringUtil.isNotNull(id)) {
			model = (SysTimeArea) getServiceImp(request).findByPrimaryKey(id);
			SysTimeHoliday holiday = model.getFdHoliday();
			String fileName = model.getFdName() + "_" + year + "年" + (month + 1)
					+ "月_";
			if ("true".equals(isTemplate)) {
				fileName += ResourceUtil
						.getString("sys-time:sysTimeArea.excel.title");
			} else {
				fileName += ResourceUtil
						.getString("sys-time:sysTimeArea.excel.detail");
			}
			if (UserOperHelper.allowLogOper("exportExcel",
					getServiceImp(request).getModelName())) {
				if ("true".equals(isTemplate)) {
					UserOperHelper.setEventType(ResourceUtil
							.getString(
									"sys-time:sysTimeArea.oper.template.download"));
				} else {
					UserOperHelper.setEventType(ResourceUtil
							.getString(
									"sys-time:sysTimeArea.oper.export.detail"));
				}
				UserOperContentHelper.putFind(model);
			}
			response.setContentType(
					"application/vnd.ms-excel; charset=UTF-8");
			response.addHeader("Content-Disposition",
					"attachment;filename=\""
							+ new String(fileName.getBytes("GBK"), "ISO-8859-1")
							+ ".xls\"");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, fileName);

			sheet.createFreezePane(1, 1, 1, 1);

			int colIndex = 0;
			sheet.setColumnWidth(colIndex++, 4000);
			if ("true".equals(isTemplate)) {
				sheet.setColumnWidth(colIndex++, 4000);
			}
			calendar.set(year, month, 1);
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int x = 1; x <= lastDay; x++) {
				sheet.setColumnWidth(colIndex++, 16000);
			}

			int colNum = colIndex;

			int rowIndex = 0;
			/* 标题行 */
			HSSFRow titlerow = sheet.createRow(rowIndex++);
			titlerow.setHeight((short) 400);
			HSSFCellStyle titleCellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
			font.setBold(true);
			titleCellStyle.setFont(font);
			HSSFCell[] titleCells = new HSSFCell[colNum];
			for (int i = 0; i < titleCells.length; i++) {
				titleCells[i] = titlerow.createCell(i);
				titleCells[i].setCellStyle(titleCellStyle);
			}

			int titleIndex = 0;
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-time:sysTimeArea.excel.fdName"));
			if ("true".equals(isTemplate)) {
				titleCells[titleIndex++].setCellValue(ResourceUtil
						.getString("sys-time:sysTimeArea.excel.fdLoginName"));
			}
			for (int day = 1; day <= lastDay; day++) {
				String value = getTimeAndWeek(year, month, day);
				value += isHoliday(year, month, day, holiday);
				titleCells[titleIndex++]
						.setCellValue(value);
			}

			if ("true".equals(isTemplate)) {
				/* 内容行 */
				HSSFCellStyle contentCellStyle = workbook.createCellStyle();
				contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT);
				contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
				List<?> orgElementList = SysTimePersonUtil.expandToPerson(model.getAreaMembers());
				if (orgElementList != null && !orgElementList.isEmpty()) {
					for (int i = 0; i < orgElementList.size(); i++) {
						HSSFRow contentrow = sheet.createRow(rowIndex++);
						contentrow.setHeight((short) 400);
						HSSFCell[] contentcells = new HSSFCell[colNum];
						for (int j = 0; j < contentcells.length; j++) {
							contentcells[j] = contentrow.createCell(j);
							contentcells[j].setCellStyle(contentCellStyle);
						}

						int contentIndex = 0;
						SysOrgPerson sysOrgPerson = (SysOrgPerson) orgElementList
								.get(i);
						contentcells[contentIndex++].setCellValue(
								StringUtil.isNotNull(sysOrgPerson.getFdName())
										? sysOrgPerson.getFdName() : "");
						// 登入名
						String loginName = sysOrgPerson.getFdLoginName();
						if (loginName != null) {
							contentcells[contentIndex++]
									.setCellValue(loginName);
						} else {
							contentcells[contentIndex++].setCellValue("");
						}
					}
				}
				sheet = setValidation(workbook, sheet, model, colNum);
				sheet.setColumnHidden(1, true);
			} else {
				/* 内容行 */
				HSSFCellStyle contentCellStyle = workbook.createCellStyle();
				contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT);
				contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
				List<SysTimeOrgElementTime> orgElementTimes = model
						.getOrgElementTimeList();
				if (orgElementTimes != null && !orgElementTimes.isEmpty()) {
					for (int i = 0; i < orgElementTimes.size(); i++) {
						SysTimeOrgElementTime orgElementTime = (SysTimeOrgElementTime) orgElementTimes
								.get(i);
						List<SysTimeWork> workList = orgElementTime
								.getSysTimeWorkList();
						List<SysTimePatchwork> patchWorkList = orgElementTime
								.getSysTimePatchworkList();
						List<SysTimeVacation> vacationList = orgElementTime
								.getSysTimeVacationList();
						HSSFRow contentrow = sheet.createRow(rowIndex++);
						contentrow.setHeight((short) 400);
						HSSFCell[] contentcells = new HSSFCell[colNum];
						for (int j = 0; j < contentcells.length; j++) {
							contentcells[j] = contentrow.createCell(j);
							contentcells[j].setCellStyle(contentCellStyle);
						}
						// 导出节假日信息
						for (int j = 1; j < contentrow
								.getLastCellNum(); j++) {
							String exportHoliday = isHoliday(year, month, j,
									holiday);
							if (StringUtil.isNotNull(exportHoliday)) {
								contentcells[j].setCellValue(exportHoliday);
							}
						}
						int contentIndex = 0;
						contentcells[contentIndex++].setCellValue(
								StringUtil.isNotNull(orgElementTime
										.getSysOrgElement().getFdName())
												? orgElementTime
														.getSysOrgElement()
														.getFdName()
												: "");
						for (SysTimeWork work : workList) {
							// 日期
							Calendar cal = Calendar.getInstance();
							Date scheduleDate = work.getFdScheduleDate();
							if (scheduleDate != null) {
								cal.setTime(scheduleDate);
								int day = cal.get(Calendar.DATE);
								int currnetYear = cal.get(Calendar.YEAR);
								int currentMonth = cal.get(Calendar.MONTH);
								if (currnetYear == year
										&& currentMonth == month) {
									SysTimeCommonTime commonTime = work
											.getSysTimeCommonTime();
									contentcells[day]
											.setCellValue(
													"1:工作日;" + commonTime
															.getFdName() + ":"
															+ commonTime
																	.getFdId());
								}
							}
						}

						for (SysTimePatchwork patchwork : patchWorkList) {
							// 日期
							Calendar cal = Calendar.getInstance();
							Date scheduleDate = patchwork.getFdScheduleDate();
							if (scheduleDate != null) {
								cal.setTime(scheduleDate);
								int day = cal.get(Calendar.DATE);
								int currnetYear = cal.get(Calendar.YEAR);
								int currentMonth = cal.get(Calendar.MONTH);
								if (currnetYear == year
										&& currentMonth == month) {
									SysTimeCommonTime commonTime = patchwork
											.getSysTimeCommonTime();
									contentcells[day]
											.setCellValue(
													"3:补班;" + commonTime
															.getFdName() + ":"
															+ commonTime
																	.getFdId());
								}
							}
						}

						for (SysTimeVacation vacation : vacationList) {
							// 排班日期
							Calendar cal = Calendar.getInstance();
							Date scheduleDate = vacation.getFdScheduleDate();
							if (scheduleDate != null) {
								cal.setTime(scheduleDate);
								int day = cal.get(Calendar.DATE);
								int currnetYear = cal.get(Calendar.YEAR);
								int currentMonth = cal.get(Calendar.MONTH);
								if (currnetYear == year
										&& currentMonth == month) {
									contentcells[day]
											.setCellValue("2:休假");
								}
							}
						}
					}
				} else {
					List<?> orgElementList = getSysOrgCoreService()
							.expandToPerson(model.getAreaMembers());
					if (orgElementList != null && !orgElementList.isEmpty()) {
						for (int i = 0; i < orgElementList.size(); i++) {
							HSSFRow contentrow = sheet.createRow(rowIndex++);
							contentrow.setHeight((short) 400);
							HSSFCell[] contentcells = new HSSFCell[colNum];
							for (int j = 0; j < contentcells.length; j++) {
								contentcells[j] = contentrow.createCell(j);
								contentcells[j].setCellStyle(contentCellStyle);
							}
							// 导出节假日信息
							for (int j = 1; j < contentrow
									.getLastCellNum(); j++) {
								String exportHoliday = isHoliday(year, month, j,
										holiday);
								if (StringUtil.isNotNull(exportHoliday)) {
									contentcells[j].setCellValue(exportHoliday);
								}
							}
							int contentIndex = 0;
							SysOrgPerson sysOrgPerson = (SysOrgPerson) orgElementList
									.get(i);
							contentcells[contentIndex++].setCellValue(
									StringUtil
											.isNotNull(sysOrgPerson.getFdName())
													? sysOrgPerson.getFdName()
													: "");
						}
					}
				}
			}
			HSSFSheet conditionSheet = workbook.createSheet();
			workbook.setSheetName(workbook.getSheetIndex(conditionSheet),
					ResourceUtil
							.getString("sys-time:sysTimeArea.excel.condition"));

			// 注意事项行
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 20);
			short height = 2400;
			// 在sheet里增加合并单元格
			com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(conditionSheet, cra);
			HSSFRow conditionrow = conditionSheet.createRow(0);
			HSSFCellStyle conditionCellStyle = workbook.createCellStyle();
			HSSFFont conditionFont = workbook.createFont();
			conditionFont.setBold(true);
			conditionFont.setColor(Font.COLOR_RED);
			conditionCellStyle.setFont(conditionFont);
			conditionCellStyle.setWrapText(true);
			List<SysTimeCommonTime> commonTimes = getcommonTimeList(model);
			StringBuilder conditionBuilder = new StringBuilder();
			conditionBuilder.append("3.班次如下：").append("\r\n");
			for (SysTimeCommonTime commonTime : commonTimes) {
				conditionBuilder.append("  ").append(commonTime.getFdName())
						.append(":");
				List<SysTimeWorkDetail> details = commonTime
						.getSysTimeWorkDetails();
				for (SysTimeWorkDetail detail : details) {
					String start = DateUtil.convertDateToString(
							detail.getFdWorkStartTime(),
							DateUtil.PATTERN_DATETIME);
					String end = DateUtil.convertDateToString(
							detail.getFdWorkEndTime(),
							DateUtil.PATTERN_DATETIME);
					conditionBuilder.append(start.split(" ")[1]).append("~")
							.append(end.split(" ")[1]).append("  ");
				}
				conditionBuilder.append("唯一标识:").append(commonTime.getFdId())
						.append("\r\n");
			}
			if (holiday != null) {
				List<SysTimeHolidayDetail> holidayDetails = holiday
						.getFdHolidayDetailList();
				height += holidayDetails.size() * 300;
				conditionBuilder.append("4.节假日如下：\r\n");
				for (SysTimeHolidayDetail detail : holidayDetails) {
					String start = DateUtil.convertDateToString(
							detail.getFdStartDay(), DateUtil.PATTERN_DATE);
					String end = DateUtil.convertDateToString(
							detail.getFdEndDay(), DateUtil.PATTERN_DATE);
					String patchDay = detail.getFdPatchDay();
					conditionBuilder.append("  ").append(detail.getFdName())
							.append(":").append(start).append("~").append(end);
					if (StringUtil.isNotNull(patchDay)) {
						conditionBuilder.append("  ").append("补班：")
								.append(patchDay).append("\r\n");
					} else {
						conditionBuilder.append("\r\n");
					}
				}
				conditionBuilder.append("\r\n");
			}
			conditionrow.setHeight((short) (height + commonTimes.size() * 300));
			HSSFCell conditionCell = conditionrow.createCell(0);
			String condition = ResourceUtil.getString(
					"sysTimeArea.excel.import.condition", "sys-time") + "\r\n"
					+ conditionBuilder.toString();
			conditionCell.setCellValue(new HSSFRichTextString(condition));
			conditionCell.setCellStyle(conditionCellStyle);
			workbook.write(response.getOutputStream());
		}
	}

	private String isHoliday(int year, int month, int day,
			SysTimeHoliday holiday) {
		if (holiday != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			long current = DateUtil.getDateNumber(calendar.getTime());
			List<SysTimeHolidayDetail> holidayDetails = holiday
					.getFdHolidayDetailList();
			for (SysTimeHolidayDetail detail : holidayDetails) {
				long end = DateUtil.getDateNumber(detail.getFdEndDay());
				long start = DateUtil.getDateNumber(detail.getFdStartDay());
				if (current >= start && current <= end) {
					return "假";
				}
				String patchDay = detail.getFdPatchDay();
				if (StringUtil.isNotNull(patchDay)) {
					if (patchDay.contains(DateUtil.convertDateToString(
							calendar.getTime(), DateUtil.PATTERN_DATE))) {
						return "补";
					}
				}
			}
		}
		return "";
	}

	public ActionForward getTimeArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", 1);
		try {
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				SysTimeArea timeArea = (SysTimeArea) getServiceImp(request)
						.findByPrimaryKey(fdId, null, true);
				JSONObject data = new JSONObject();
				data.put("fdId", fdId);
				data.put("fdBatchSchedule",
						Boolean.TRUE.equals(timeArea.getFdIsBatchSchedule())
								? "true" : "false");
				data.put("isScheduled",
						!timeArea.getSysTimeWorkList().isEmpty() ? "true"
								: "false");
				result.put("data", data);
			}
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", 0);
		}
		return mapping.findForward("lui-source");
	}
}
