package com.landray.kmss.km.imeeting.actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingBookForm;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.util.RecurrenceUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import de.sty.io.mimetype.helper.RuntimeException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议室预约 Action
 */
public class KmImeetingBookAction extends ExtendAction {

	private final SimpleDateFormat format2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final String[] weeks = { "SU", "MO", "TU", "WE", "TH", "FR",
			"SA" };

	private IKmImeetingResService kmImeetingResService;
	protected IKmImeetingBookService kmImeetingBookService;
	private KmImeetingBookForm bookForm;

	@Override
	protected IKmImeetingBookService getServiceImp(HttpServletRequest request) {
		if (kmImeetingBookService == null) {
            kmImeetingBookService = (IKmImeetingBookService) getBean("kmImeetingBookService");
        }
		return kmImeetingBookService;
	}

	protected IKmImeetingResService getKmImeetingResService(
			HttpServletRequest request) {
		if (kmImeetingResService == null) {
			kmImeetingResService = (IKmImeetingResService) getBean("kmImeetingResService");
		}
		return kmImeetingResService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingBookForm bookForm = (KmImeetingBookForm) super.createNewForm(
				mapping, form, request, response);
		// 默认发起人
		bookForm.setDocCreatorId(UserUtil.getUser().getFdId());
		bookForm.setDocCreatorName(UserUtil.getUser().getFdName());
		String resId = request.getParameter("resId");
		String fdHoldDate = request.getParameter("startDate");
		String fdFinishDate = request.getParameter("endDate");
		// 会议室信息
		if (StringUtil.isNotNull(resId)) {
			KmImeetingRes res = (KmImeetingRes) getKmImeetingResService(request)
					.findByPrimaryKey(resId);
			request.setAttribute("res", res);
			bookForm.setFdPlaceId(resId);
			bookForm.setFdPlaceName(res.getFdName());
			bookForm.setFdPlaceDetail(res.getFdDetail());
			if (res.getFdUserTime() != null) {
				bookForm.setFdUserTime(res.getFdUserTime().toString());
			}
		}
		// 初始化召开时间、结束时间
		// #9159 没有时分默认规则：召开时间为9点，结束时间为10点
		if (StringUtil.isNotNull(fdHoldDate)) {
			if (!isDateTime(fdHoldDate)) {
				Date _fdHoldDate = DateUtil.convertStringToDate(fdHoldDate,
						DateUtil.TYPE_DATE,request.getLocale());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(_fdHoldDate);
				calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance()
						.get(Calendar.HOUR_OF_DAY) + 1);// 设置召开时间为当前时间的下一个整点
				fdHoldDate = DateUtil.convertDateToString(calendar.getTime(),
						DateUtil.TYPE_DATETIME,request.getLocale());
			}
			bookForm.setFdHoldDate(fdHoldDate);
		}
		if (StringUtil.isNotNull(fdFinishDate)) {
			if (!isDateTime(fdFinishDate)) {
				Date _fdFinishDate = DateUtil.convertStringToDate(fdFinishDate,
						DateUtil.TYPE_DATE,request.getLocale());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(_fdFinishDate);
				calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance()
						.get(Calendar.HOUR_OF_DAY) + 2);// 设置召开时间为当前时间的下两个整点
				fdFinishDate = DateUtil.convertDateToString(calendar.getTime(),
						DateUtil.TYPE_DATETIME,request.getLocale());
			}
			bookForm.setFdFinishDate(fdFinishDate);
		}
		String fdTime = request.getParameter("fdTime");
		String fdStratTime = request.getParameter("fdStratTime");
		if (!StringUtil.isNull(fdTime) && !StringUtil.isNull(fdStratTime)) {

			String[] fdStartTimeArr = fdStratTime.split(":");
			if (fdStartTimeArr.length > 1) {
				String fdStratTimeA = fdStartTimeArr[0], fdStratTimeB = fdStartTimeArr[1];
				if (fdStartTimeArr[0].length() < 2) {
                    fdStratTimeA = "0" + fdStartTimeArr[0];
                }
				if (fdStartTimeArr[1].length() < 2) {
                    fdStratTimeB = "0" + fdStartTimeArr[1];
                }
				bookForm.setFdHoldDate(fdTime + " " + fdStratTimeA + ":" + fdStratTimeB);
			} else {
				if (fdStratTime.length() < 2) {
                    fdStratTime = "0" + fdStratTime;
                }
				bookForm.setFdHoldDate(fdTime + " " + fdStratTime + ":00");
			}
		}
		String fdFinishTime = request.getParameter("fdFinishTime");
		if (!StringUtil.isNull(fdTime) && !StringUtil.isNull(fdFinishTime)) {
			String[] fdFinishTimeArr = fdFinishTime.split(":");
			if (fdFinishTimeArr.length > 1) {
				if (Integer.parseInt(fdFinishTimeArr[0]) == 24) {
					bookForm.setFdFinishDate(fdTime + " 23:59");
				} else {
					String fdFinishTimeA = fdFinishTimeArr[0], fdFinishTimeB = fdFinishTimeArr[1];
					if (fdFinishTimeArr[0].length() < 2) {
                        fdFinishTimeA = "0" + fdFinishTimeArr[0];
                    }
					if (fdFinishTimeArr[1].length() < 2) {
                        fdFinishTimeB = "0" + fdFinishTimeArr[1];
                    }
					bookForm.setFdFinishDate(fdTime + " " + fdFinishTimeA + ":" + fdFinishTimeB);
				}
			} else {
				if (Integer.parseInt(fdFinishTime) < 10) {
					bookForm.setFdFinishDate(fdTime + " 0" + fdFinishTime + ":00");
				} else if (Integer.parseInt(fdFinishTime) == 24) {
					bookForm.setFdFinishDate(fdTime + " 23:59");
				} else {
					bookForm.setFdFinishDate(fdTime + " " + fdFinishTime + ":00");
				}
			}
		}

		return bookForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmImeetingBookForm bookForm = (KmImeetingBookForm) form;
		String fdHoldDate = request.getParameter("fdHoldDate");
		String fdHoldTime = request.getParameter("fdHoldTime");
		if (StringUtil.isNotNull(fdHoldDate)
				&& StringUtil.isNotNull(fdHoldTime)) {
			String holeDate = StringUtil.linkString(fdHoldDate, " ",
					fdHoldTime);
			if (!holeDate.equals(bookForm.getFdHoldDate())) {
				bookForm.setFdHoldDate(holeDate);
			}
		}
		String fdFinishDate = request.getParameter("fdFinishDate");
		String fdFinishTime = request.getParameter("fdFinishTime");
		if (StringUtil.isNotNull(fdFinishDate)
				&& StringUtil.isNotNull(fdFinishTime)) {
			String finishDate = StringUtil.linkString(fdFinishDate, " ",
					fdFinishTime);
			if (!finishDate.equals(bookForm.getFdFinishDate())) {
				bookForm.setFdFinishDate(finishDate);
			}
		}
		//会议室保管人
		String fdExamerId = bookForm.getFdExamerId(); //会议室保管人/或者岗位
		//获取当前登录人信息
		String[] postIds= UserUtil.getKMSSUser().getPostIds(); //获取当前登录人岗位
		if (postIds != null && postIds.length > 0) {
			List list = Arrays.asList(postIds);
			request.setAttribute("isfdExamer",list.contains(fdExamerId));
		}
		// 会议室信息
		if (StringUtil.isNotNull(bookForm.getFdPlaceId())) {
			String resId = bookForm.getFdPlaceId();
			KmImeetingRes res = (KmImeetingRes) getKmImeetingResService(request)
					.findByPrimaryKey(resId);
			request.setAttribute("res", res);
		}
		Date hDate = DateUtil.convertStringToDate(bookForm.getFdHoldDate(),
				DateUtil.TYPE_DATETIME, ResourceUtil.getLocaleByUser());
		Date fDate = DateUtil.convertStringToDate(bookForm.getFdFinishDate(),
				DateUtil.TYPE_DATETIME, ResourceUtil.getLocaleByUser());
		Date current = new Date();
		if (hDate.getTime() < current.getTime()) {
			bookForm.setIsBegin("true");
		} else {
			bookForm.setIsBegin("false");
		}
		if (fDate.getTime() < current.getTime()) {
			bookForm.setIsEnd("true");
		} else {
			bookForm.setIsEnd("false");
		}
		if (UserUtil.getUser().getFdId().equals(bookForm.getDocCreatorId())) {
			bookForm.setIsCreator("true");
		}
		setRepeat(bookForm);
	}

	private void setRepeat(KmImeetingBookForm bookForm) {
		String fdRecurrenceStr = bookForm.getFdRecurrenceStr();
		if (StringUtil.isNotNull(fdRecurrenceStr)) {
			Date fdHoldDate = DateUtil.convertStringToDate(
					bookForm.getFdHoldDate(),
					DateUtil.TYPE_DATETIME, ResourceUtil.getLocaleByUser());
			Map<String, String> infos = RecurrenceUtil
					.getRepeatInfo(fdRecurrenceStr, fdHoldDate);
			if (StringUtil.isNotNull(infos.get("FREQ"))) {
				bookForm.setFdRepeatType(infos.get("FREQ"));
			}
			if (StringUtil.isNotNull(infos.get("INTERVAL"))) {
				bookForm.setFdRepeatFrequency(infos.get("INTERVAL"));
			}
			if (StringUtil.isNotNull(infos.get("BYDAY"))) {
				bookForm.setFdRepeatTime(infos.get("BYDAY"));
			}
			if (StringUtil.isNotNull(infos.get("COUNT"))) {
				bookForm.setFdRepeatUtil(infos.get("COUNT"));
			} else if (StringUtil.isNotNull(infos.get("UNTIL"))) {
				bookForm.setFdRepeatUtil(infos.get("UNTIL"));
			}

			Map<String, String> params = RecurrenceUtil
					.parseRecurrenceStr(fdRecurrenceStr);
			String freq = params
					.get(ImeetingConstant.RECURRENCE_FREQ);
			String interval = params
					.get(ImeetingConstant.RECURRENCE_INTERVAL);
			String endType = params
					.get(ImeetingConstant.RECURRENCE_END_TYPE);
			bookForm.setRECURRENCE_FREQ(freq);
			if (ImeetingConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
				bookForm.setRECURRENCE_WEEKS(params
						.get(ImeetingConstant.RECURRENCE_BYDAY)
						.replaceAll(",", ";"));
			} else if (ImeetingConstant.RECURRENCE_FREQ_MONTHLY
					.equals(freq)) {
				String monthType = params
						.get(ImeetingConstant.RECURRENCE_MONTH_TYPE);
				bookForm.setRECURRENCE_MONTH_TYPE(monthType);

			}
			bookForm.setRECURRENCE_INTERVAL(interval);

			bookForm.setRECURRENCE_END_TYPE(endType);
			if (ImeetingConstant.RECURRENCE_END_TYPE_COUNT
					.equals(endType)) {
				bookForm.setRECURRENCE_COUNT(params
						.get(ImeetingConstant.RECURRENCE_COUNT));
			} else if (ImeetingConstant.RECURRENCE_END_TYPE_UNTIL
					.equals(endType)) {
				String until = params
						.get(ImeetingConstant.RECURRENCE_UNTIL);
				until = until.substring(0, 4) + "-"
						+ until.substring(4, 6) + "-"
						+ until.substring(6);
				bookForm.setRECURRENCE_UNTIL(until);
			}
		}

	}

	/**
	 * 校验权限（AJAX）
	 */
	public ActionForward checkAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkAuth", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject auth = new JSONObject();
		try {
			String fdId = request.getParameter("fdId");
			Boolean canEdit = false, canDelete = false;
			// 校验是否有编辑权限
			if (UserUtil.checkAuthentication(
					"/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=edit&fdId="
							+ fdId, "get")) {
				canEdit = true;
			}
			auth.put("canEdit", canEdit);
			// 校验是否有删除权限
			if (UserUtil.checkAuthentication(
					"/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=delete&fdId="
							+ fdId, "get")) {
				canDelete = true;
			}
			auth.put("canDelete", canDelete);
		} catch (Exception e) {
			messages.addError(e);
		}
		response.getWriter().write(auth.toString());// 结果
		response.setCharacterEncoding("UTF-8");
		TimeCounter.logCurrentTime("Action-checkAuth", false, getClass());
		return null;
	}

	// 是否日期+时间格式
	private boolean isDateTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat(
				DateUtil.PATTERN_DATETIME);
		try {
			format.parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public ActionForward generateTime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			Date d = new Date();
			JSONObject o = new JSONObject();
			o.accumulate("weekday", ResourceUtil.getString("kmImeeting.today",
					"km-imeeting", request.getLocale()));
			o.accumulate("value", DateUtil.convertDateToString(d, ResourceUtil.getString("date.format.date")));
			array.add(o);
			for (int i = 1; i < 14; i++) {
				d.setDate(d.getDate() + 1);
				JSONObject obj = new JSONObject();
				obj.accumulate("weekday",
						ResourceUtil.getString("kmImeeting.weekDay." + d.getDay(), "km-imeeting", request.getLocale()));
				obj.accumulate("value", DateUtil.convertDateToString(d, ResourceUtil.getString("date.format.date")));
				array.add(obj);
			}
			if (!array.isEmpty()) {
				((JSONObject) array.get(0)).accumulate("selected", true);
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-data", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	public ActionForward exam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String bookId = request.getParameter("bookId");
			String fdHasExam = request.getParameter("fdHasExam");
			String fdExamRemark = request.getParameter("fdExamRemark");
			KmImeetingBook meetingBook = (KmImeetingBook) getServiceImp(request)
					.findByPrimaryKey(bookId);
			// 记录操作日志
			if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putUpdate(meetingBook)
						.putSimple("fdHasExam", meetingBook.getFdHasExam(),
								Boolean.valueOf(fdHasExam))
						.putSimple("fdExamRemark",
								meetingBook.getFdExamRemark(), fdExamRemark);
			}
			if (meetingBook.getFdHasExam() == null) {
				meetingBook.setFdHasExam(Boolean.valueOf(fdHasExam));
				meetingBook.setFdExamRemark(fdExamRemark);
				getServiceImp(request).addExam(meetingBook);
				JSONObject result = new JSONObject();
				result.put("result", true);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(result.toString());
			} else {
				throw new KmssRuntimeException(
						new RuntimeException(ResourceUtil.getString(
								"kmImeetingBook.exam.error", "km-imeeting")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		}
		return null;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		CriteriaValue cv = new CriteriaValue(request);
		String mydoc = cv.poll("mydoc");
		if (StringUtil.isNotNull(mydoc)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmImeetingBook.fdExamer.fdId=:fdExamerId");
			hqlInfo.setParameter("fdExamerId", UserUtil.getUser().getFdId());
			if ("all".equals(mydoc)) {
				String dialect = ResourceUtil
						.getKmssConfigString("hibernate.dialect").toLowerCase();
				if (dialect.indexOf("oracle") > -1) {
                    hqlInfo.setOrderBy("kmImeetingBook.fdHasExam desc");
                } else {
                    hqlInfo.setOrderBy("kmImeetingBook.fdHasExam");
                }
			}
			if ("waitExam".equals(mydoc)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHasExam is null");
			} else if ("agree".equals(mydoc)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHasExam=:fdHasExam");
				hqlInfo.setParameter("fdHasExam",Boolean.TRUE);
			} else if ("reject".equals(mydoc)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHasExam=:fdHasExam");
				hqlInfo.setParameter("fdHasExam",Boolean.FALSE);
			}
		}
		String[] fdDate = cv.polls("fdDate");
		if (fdDate != null) {
			if (StringUtil.isNotNull(fdDate[0])) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHoldDate>=:fdStartDate");
				// 取一天的开始时间
				Date startDate = DateUtil.convertStringToDate(fdDate[0], DateUtil.TYPE_DATETIME, request.getLocale());
				hqlInfo.setParameter("fdStartDate", DateUtil.getDayStartTime(startDate));
			}
			if (StringUtil.isNotNull(fdDate[1])) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdFinishDate<=:fdEndDate");
				// 取一天的结束时间
				Date endDate = DateUtil.convertStringToDate(fdDate[1], DateUtil.TYPE_DATETIME, request.getLocale());
				hqlInfo.setParameter("fdEndDate", DateUtil.getDayEndTime(endDate));
			}
		}
		String status = cv.poll("status");
		if (StringUtil.isNotNull(status)) {
			if ("wait".equals(status)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHasExam is null");
			}
			if ("yes".equals(status)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHasExam=:fdHasExam");
				hqlInfo.setParameter("fdHasExam", Boolean.TRUE);
			}
			if ("no".equals(status)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingBook.fdHasExam=:fdHasExam");
				hqlInfo.setParameter("fdHasExam", Boolean.FALSE);
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingBook.class);
	}

	public ActionForward count(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			HQLInfo hqlInfo = new HQLInfo();
			changeFindPageHQLInfo(request, hqlInfo);
			hqlInfo.setGettingCount(true);
			hqlInfo.setOrderBy(null);
			JSONObject json = new JSONObject();

			List<Long> modelCount = this.getServiceImp(request)
					.findValue(hqlInfo);

			json.element("count", modelCount.get(0));

			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id, new RequestContext(request));
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	public ActionForward earlyEndBook(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-earlyEndBook", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				KmImeetingBook book = (KmImeetingBook) getServiceImp(request)
						.findByPrimaryKey(id);
				book.setFdFinishDate(new Date());// 将结束时间更新为当前时间
				getServiceImp(request).update(book);
			}

		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-earlyEndBook", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/*
	 * 移动端删除
	 */
	public ActionForward mobileDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String flag = "1";
		JSONObject json = new JSONObject();
		try {
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id, new RequestContext(request));
            }
		} catch (Exception e) {
			messages.addError(e);
			flag = "0";
		}
		json.put("flag", flag);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingBookForm kmImeetingBookForm = (KmImeetingBookForm) form;
		// 移动端创建周期性会议 重复规则处理
		initMobileRepeatInfo(kmImeetingBookForm);
		ActionForward actionForward = super.save(mapping, form, request,
				response);
		return actionForward;
	}

	private void initMobileRepeatInfo(KmImeetingBookForm kmImeetingBookForm)
			throws Exception {
		String freq = kmImeetingBookForm.getRECURRENCE_FREQ();
		if (!ImeetingConstant.RECURRENCE_FREQ_NO.equals(freq)) {
			String byday = null;
			if (ImeetingConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
				byday = kmImeetingBookForm.getRECURRENCE_WEEKS()
						.replaceAll(";", ",");
			} else if (ImeetingConstant.RECURRENCE_FREQ_MONTHLY
					.equals(freq)) {
				if (ImeetingConstant.RECURRENCE_MONTH_TYPE_WEEK
						.equals(kmImeetingBookForm
								.getRECURRENCE_MONTH_TYPE())) {
					Date _startDate = format2
							.parse(kmImeetingBookForm.getFdHoldDate());
					Calendar c = Calendar.getInstance();
					c.setFirstDayOfWeek(Calendar.MONDAY);
					c.setTime(_startDate);
					int weekOfMonth = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					String weekStr = weeks[dayOfWeek - 1];
					byday = weekOfMonth + weekStr;
				}
			}
			Map<String, String> params = new HashMap<>();
			params.put("FREQ", kmImeetingBookForm.getRECURRENCE_FREQ());
			params.put("ENDTYPE", kmImeetingBookForm.getRECURRENCE_END_TYPE());
			params.put("COUNT", kmImeetingBookForm.getRECURRENCE_COUNT());
			params.put("UNTIL", kmImeetingBookForm.getRECURRENCE_UNTIL());
			params.put("INTERVAL", kmImeetingBookForm.getRECURRENCE_INTERVAL());
			params.put("BYDAY", byday);
			String recurrenceStr = RecurrenceUtil.buildRecurrenceStr(params);
			kmImeetingBookForm.setFdRecurrenceStr(recurrenceStr);
		}
	}

}
