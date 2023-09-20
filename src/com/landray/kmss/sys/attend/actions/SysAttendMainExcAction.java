package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attend.forms.SysAttendMainExcForm;
import com.landray.kmss.sys.attend.forms.SysAttendMainForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryATemplateService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpmservice.exception.DefaultTemplateUnDefinedException;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 签到异常表 Action
 *
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendMainExcAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainExcAction.class);

	protected ISysAttendMainExcService sysAttendMainExcService;
	protected ISysAttendMainService sysAttendMainService;
	protected ISysAttendCategoryService sysAttendCategoryService;
	private ICoreOuterService dispatchCoreService;
	protected ISysAttendCategoryATemplateService sysAttendCategoryATemplateService;

	@Override
	protected ISysAttendMainExcService getServiceImp(HttpServletRequest request) {
		if (sysAttendMainExcService == null) {
			sysAttendMainExcService = (ISysAttendMainExcService) getBean(
					"sysAttendMainExcService");
		}
		return sysAttendMainExcService;
	}

	public ISysAttendMainService getSysAttendMainService() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) getBean(
					"sysAttendMainService");
		}
		return sysAttendMainService;
	}

	protected ISysAttendCategoryService getCategoryServiceImp() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	protected ISysAttendCategoryATemplateService
	getSysAttendCategoryATemplateService() {
		if (sysAttendCategoryATemplateService == null) {
			sysAttendCategoryATemplateService = (ISysAttendCategoryATemplateService) getBean(
					"sysAttendCategoryATemplateService");
		}
		return sysAttendCategoryATemplateService;
	}

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean(
					"dispatchCoreService");
		}
		return dispatchCoreService;
	}
	private ISysAttendHisCategoryService sysAttendHisCategoryService;
	private ISysAttendHisCategoryService getSysAttendHisCategoryService(){
		if(sysAttendHisCategoryService ==null){
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form = super.createNewForm(mapping, form, request, response);
		SysAttendMainExcForm excForm = (SysAttendMainExcForm) form;
		String fdAttendMainId = request.getParameter("fdAttendMainId");
		if (StringUtil.isNotNull(fdAttendMainId)) {
			SysAttendMain main = (SysAttendMain) getSysAttendMainService()
					.findByPrimaryKey(fdAttendMainId, null, true);
			SysAttendMainForm mainForm = new SysAttendMainForm();
			mainForm = (SysAttendMainForm) getSysAttendMainService()
					.convertModelToForm(mainForm, main,
							new RequestContext(request));
			request.setAttribute("sysAttendMainForm", mainForm);
			SysAttendHisCategory sysAttendHisCategory =main.getFdHisCategory();

			SysAttendCategory cate = getSysAttendHisCategoryService().convertCategory(main.getFdHisCategory());

			excForm.setFdAttendMainId(main.getFdId());
			excForm.setFdAttendMainCateName(cate.getFdName());
			excForm.setFdAttendMainStatus(main.getFdStatus() + "");
			excForm.setFdAttendMainState(main.getFdState() + "");
			excForm.setFdAttendTime(mainForm.getDocCreateTime());
			excForm.setDocSubject(main.getDocCreator().getFdName() + " "
					+ DateUtil.convertDateToString(main.getDocCreateTime(),
					DateUtil.TYPE_DATE, request.getLocale())
					+ " "
					+ ResourceUtil.getString("table.sysAttendMainExc",
					"sys-attend"));
			// 需要进行判空
			if(cate.getFdManager() != null) {
				excForm.setFdManagerId(cate.getFdManager().getFdId());
			}
			// 补卡的时间范围，用于校验
			Map<String, Object> timeMap = getStartEndTime(cate,
					main.getDocCreator(),main.getDocCreateTime(),main.getFdIsAcross());
			Date startTime = (Date) timeMap.get("startTime");
			Date endTime = (Date) timeMap.get("endTime");
			if (startTime != null && endTime != null) {
				request.setAttribute("fdStartTime",
						DateUtil.convertDateToString(startTime,
								DateUtil.TYPE_DATETIME, request.getLocale()));
				request.setAttribute("fdEndTime",
						DateUtil.convertDateToString(endTime,
								DateUtil.TYPE_DATETIME, request.getLocale()));
			}
			boolean noFlow =true;
			if(sysAttendHisCategory !=null){
				SysAttendCategory cateOld = (SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(sysAttendHisCategory.getFdCategoryId());
				// 启动流程
				if (cateOld.getFdATemplate() != null) {
					excForm.setFdCateTemplId(cateOld.getFdATemplate().getFdId());
					getDispatchCoreService().initFormSetting(excForm,
							"attendMainExc",
							cateOld.getFdATemplate(), "attendMainExc",
							new RequestContext(request));
					noFlow =false;
				}
			}
			if (noFlow){
				throw new DefaultTemplateUnDefinedException();
			}
		}
		return excForm;
	}

	/**
	 * 避免权限报错。这里添加异常流程 使用新的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward addExc(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.add(mapping,form,request,response);
	}

	/**
	 * 提交异常时数据库还没有打卡记录，补全打卡记录，用于移动端
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward fixAttendMain(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("categoryId");
			String fdWorkTimeId = request.getParameter("fdWorkTimeId");
			String _fdWorkType = request.getParameter("fdWorkType");
			String fdSignTime = request.getParameter("fdSignTime");
			String _fdOverTimeType = request.getParameter("fdOverTimeType");

			Integer fdOverTimeType = "2".equals(_fdOverTimeType) ? 2 : 1;
			JSONObject json = new JSONObject();

//			SysAttendCategory fdCategory = (SysAttendCategory) getCategoryServiceImp()
//					.findByPrimaryKey(categoryId, null, true);
			SysAttendHisCategory hisCategory = (SysAttendHisCategory) getSysAttendHisCategoryService().findByPrimaryKey(categoryId);
			SysAttendCategory fdCategory =getSysAttendHisCategoryService().convertCategory(hisCategory);
			if (fdCategory != null) {
				// 补全记录
				SysAttendMain main = new SysAttendMain();
				boolean ret = false;
				// 是否排班制
				boolean isTimeArea = Integer.valueOf(1)
						.equals(fdCategory.getFdShiftType());
				String tmpCategoryId = this.getCategoryServiceImp().getAttendCategory(UserUtil.getUser(), new Date());
				if(StringUtil.isNull(tmpCategoryId)){
					//判断用户当前是否需要生成缺卡记录
					logger.warn("休息日不需要生成缺卡记录!userName:"
							+ UserUtil.getUser().getFdName());
					throw new Exception("休息日不需要生成缺卡记录");
				}
				if (!isTimeArea) {
					List<SysAttendCategoryWorktime> workTimes = new ArrayList<SysAttendCategoryWorktime>();
					workTimes = fdCategory.getAllWorkTime();
					for (SysAttendCategoryWorktime workTime : workTimes) {
						if (workTime.getFdId().equals(fdWorkTimeId)) {
							main.setWorkTime(workTime);
							ret = true;
							break;
						}
					}
				} else {
					main.setFdWorkKey(fdWorkTimeId);
					ret = true;
				}

				if (ret) {
					if (!this.isSigned(categoryId, fdWorkTimeId, _fdWorkType,
							request, isTimeArea)) {
						main.setFdHisCategory(hisCategory);
						main.setFdStatus(0);
						main.setFdDateType(0);
						main.setFdWorkType(Integer.valueOf(_fdWorkType));
						Date signTime = null;
						if(!isTimeArea){
							SysAttendCategoryWorktime workTime = main.getWorkTime();
							signTime = workTime.getFdStartTime();
							if ("1".equals(_fdWorkType)) {
								signTime = workTime.getFdEndTime();
								fdOverTimeType=workTime.getFdOverTimeType();
							}
						}else{
							signTime = new Date();
							signTime.setHours(Integer.valueOf(fdSignTime) / 60);
							signTime.setMinutes(
									Integer.valueOf(fdSignTime) % 60);
						}

						Date now = new Date();
						Boolean fdIsAcross=false;
						if (fdCategory.getFdEndDay() != null
								&& fdCategory.getFdEndDay() == 2
								&& now.getTime() > 0 && now
								.getTime() < AttendUtil
								.joinYMDandHMS(now,
										fdCategory
												.getFdEndTime())
								.getTime()&&!Integer.valueOf(2).equals(fdOverTimeType)) {
							now = AttendUtil.addDate(now, -1);
							fdIsAcross=true;
						}
						if(Integer.valueOf(2).equals(fdOverTimeType)) {
							fdIsAcross=true;
						}
						now.setHours(signTime.getHours());
						now.setMinutes(signTime.getMinutes());
						now.setSeconds(0);
						main.setFdBaseWorkTime(now);
						main.setFdIsAcross(fdIsAcross);

						String fdAttendMainId = getSysAttendMainService()
								.add(main, now);
						// 添加日志信息
						if (UserOperHelper.allowLogOper("fixAttendMain",
								getServiceImp(request).getModelName())) {
							UserOperContentHelper.putAdd(fdAttendMainId, "",
									getServiceImp(request).getModelName());
						}
						json.accumulate("fdAttendMainId", fdAttendMainId);
						request.setAttribute("lui-source", json);
					} else {
						json.accumulate("fdAttendMainId", (String) request
								.getAttribute("__fdAttendMainId"));
						request.setAttribute("lui-source", json);
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.toString());
		}
		return getActionForward("lui-source", mapping, form, request,
				response);
	}

	private boolean isSigned(String categoryId, String fdWorkTimeId,
							 String fdWorkType, HttpServletRequest request, boolean isTimeArea)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		String userId = UserUtil.getKMSSUser().getUserId();
		sql.append("select fd_id")
				.append(" from sys_attend_main m ")
				.append(" where doc_creator_id=:docCreatorId and doc_create_time>=:beginTime and doc_create_time<:endTime ")
				.append(" and fd_category_id=:categoryId and "
						+ (isTimeArea ? " fd_work_key=:fdWorkId "
						: " fd_work_id=:fdWorkId ")
						+ " and fd_work_type=:fdWorkType and (fd_is_across is null or fd_is_across=:fdIsAcross)")
				.append(" and (doc_status=0 or doc_status is null)");

		List list = getSysAttendMainService().getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).setParameter("fdIsAcross", false).setString("docCreatorId", userId).setDate("beginTime", DateUtil.getDate(0)).setDate("endTime", DateUtil.getDate(1)).setString("categoryId", categoryId).setString("fdWorkId", fdWorkTimeId).setInteger("fdWorkType", Integer.valueOf(fdWorkType)).list();
		if (list.isEmpty()) {
			return false;
		}
		Object id = list.get(0);
		request.setAttribute("__fdAttendMainId", id.toString());
		return true;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		String mydoc = request.getParameter("mydoc");
		if (StringUtil.isNotNull(mydoc)) {
			if ("approval".equals(mydoc)) {
				SysFlowUtil.buildLimitBlockForMyApproval("sysAttendMainExc",
						hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			}
		}

		// 根据打卡时间查询
		String[] docCreateTime = cv.polls("docCreateTime");
		Date startTime = null, endTime = null;
		if (docCreateTime != null) {
			startTime = DateUtil.convertStringToDate(docCreateTime[0],
					DateUtil.TYPE_DATE, null);
			endTime = DateUtil.convertStringToDate(docCreateTime[1],
					DateUtil.TYPE_DATE, null);
			if (startTime != null && endTime != null) {
				StringBuffer sb = new StringBuffer();
				String where = hqlInfo.getWhereBlock();
				if (StringUtil.isNotNull(where)) {
					sb.append(" and ");
				} else {
					where = "";
				}

				sb.append(
						"sysAttendMainExc.fdAttendMain.docCreateTime>=:startTime and sysAttendMainExc.fdAttendMain.docCreateTime<:endTime");
				Date _startTime = startTime, _endTime = endTime;
				if (startTime.after(endTime)) {
					_startTime = endTime;
					_endTime = startTime;
				}
				hqlInfo.setParameter("startTime",
						AttendUtil.getDate(_startTime, 0));
				hqlInfo.setParameter("endTime",
						AttendUtil.getDate(_endTime, 1));
				hqlInfo.setWhereBlock(where + sb.toString());
			}
		}
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendMainExc.class);
	}

	@Deprecated
	public ActionForward setStatus(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			String _fdStatus = request.getParameter("fdStatus");
			int fdStatus = 3;
			if ("1".equals(_fdStatus)) {
				fdStatus = 2;
			}
			if (ids != null) {
				getServiceImp(request).updateStatus(ids, fdStatus);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}
		else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Deprecated
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String id = request.getParameter("fdId");
			String _fdStatus = request.getParameter("fdStatus");
			int fdStatus = 3;
			if ("1".equals(_fdStatus)) {
				fdStatus = 2;
			}
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			}
			else {
				getServiceImp(request).updateStatus(new String[]{id},
						fdStatus);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		}else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysAttendMainExcForm excForm = (SysAttendMainExcForm) form;
			String mainId = excForm.getFdAttendMainId();
			if (StringUtil.isNotNull(mainId)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setOrderBy("sysAttendMainExc.docCreateTime desc");
				hqlInfo.setPageNo(1);
				hqlInfo.setRowSize(SysConfigParameters.getRowSize());
				hqlInfo.setWhereBlock(
						"sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
				hqlInfo.setParameter("fdAttendMainId", mainId);
				SysAttendMainExc excList = (SysAttendMainExc) getServiceImp(null).findFirstOne(hqlInfo);
				boolean isInsert =false;
				// 若已提交异常单不能再提交，除非不通过
                if (excList == null || "3".equals(excForm.getFdStatus())) {
					isInsert =true;

                } else if (excList != null) {
					SysAttendMainExc mainExc = excList;
					if(Objects.equals(mainExc.getFdStatus(), 3)) {
						isInsert =true;
					}
				}
				if(isInsert){
					//提交之前，再次验证是否超过次数
					JSONObject result = checkNumber(mainId);
					if(result.getInt("status")==1) {
						getServiceImp(request).add((IExtendForm) form,
								new RequestContext(request));
					} else {
						messages.addError(new Exception(ResourceUtil.getString("sys-attend:sysAttendMainExc.error.overdue")));
						KmssReturnPage.getInstance(request).addMessages(messages).save(
								request);
						return getActionForward("failure", mapping, form, request, response);
					}
				}else{
					messages.addError(new Exception(ResourceUtil.getString("sys-attend:mui.exception.doing")));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}



	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		IBaseModel model = null;
		String id = request.getParameter("fdId");
		if (StringUtil.isNotNull(id)) {
			model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
				UserOperHelper.logFind(model);// 添加日志信息
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
			}

		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		SysAttendMainExc mainExc = (SysAttendMainExc) model;
		SysAttendMain main = mainExc.getFdAttendMain();
		//2021-09-30 修改考勤组的获取方式
		SysAttendCategory cate = CategoryUtil.getFdCategoryInfo(main);
		//String categoryId= getCategoryServiceImp().getAttendCategory(main.getDocCreator());
//		if(StringUtil.isNotNull(categoryId) && (cate==null || !categoryId.equals(cate.getFdId()))) {
//			cate=(SysAttendCategory) getCategoryServiceImp().findByPrimaryKey(categoryId,null,true);
//		}
		if(cate !=null){
			SysAttendMainExcForm tempForm =(SysAttendMainExcForm)rtnForm;
			tempForm.setFdAttendMainCateName(cate.getFdName());
		}
		SysAttendMainForm mainForm = new SysAttendMainForm();
		mainForm = (SysAttendMainForm) getSysAttendMainService()
				.convertModelToForm(mainForm, main,
						new RequestContext(request));
		request.setAttribute("sysAttendMainForm", mainForm);
		// 补卡的时间范围，用于校验
		Map<String, Object> timeMap = getStartEndTime(cate,
				main.getDocCreator(),main.getDocCreateTime(),main.getFdIsAcross());
		Date startTime = (Date) timeMap.get("startTime");
		Date endTime = (Date) timeMap.get("endTime");
		if (startTime != null && endTime != null) {
			request.setAttribute("fdStartTime",
					DateUtil.convertDateToString(startTime,
							DateUtil.TYPE_DATETIME, request.getLocale()));
			request.setAttribute("fdEndTime",
					DateUtil.convertDateToString(endTime,
							DateUtil.TYPE_DATETIME, request.getLocale()));
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	/**
	 * 获取某一天考勤组的开始关闭时间
	 *
	 * @param category
	 * @param date
	 * @return
	 */
	private Map<String, Object> getStartEndTime(SysAttendCategory category,
												SysOrgElement person,Date date,Boolean fdIsAcross) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Date startTime = null;
		Date endTime = null;
		Date workDate =date;
		if(Boolean.TRUE.equals(fdIsAcross)){
			workDate =AttendUtil.getDate(date,-1);
		}
		//排班类型的最早最晚时间范围取值
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			//获取某人所在考勤组的最早最晚打卡时间
			List<Map<String, Object>> signTimeList = getCategoryServiceImp().getAttendSignTimes(category, workDate, person);
			if(CollectionUtils.isNotEmpty(signTimeList) ){
				//取第一条的最早时间，取最后一条的最晚时间。
				for (Map<String, Object> signTimeMap:signTimeList) {
					//时间区间范围内
					Date returenDate = getCategoryServiceImp().getTimeAreaDateOfDate(date,workDate,signTimeMap);
					if(returenDate !=null){
						startTime = (Date) signTimeMap.get("fdStartTime");
						endTime = (Date) signTimeMap.get("fdEndTime");
						startTime = AttendUtil.joinYMDandHMS(workDate,startTime );
						endTime = AttendUtil.joinYMDandHMS(workDate,endTime );
						//最晚打卡时间为跨天时
						Integer endOverTimeType = (Integer) signTimeMap.get("endOverTimeType");
						if(endOverTimeType !=null && Integer.valueOf(2).equals(endOverTimeType)){
							endTime =AttendUtil.addDate(endTime,1);
						}
						break;
					}
				}
			}
		} else if (Integer.valueOf(0).equals(category.getFdShiftType())
				&& Integer.valueOf(1).equals(category.getFdSameWorkTime())) {
			//获取某人所在考勤组的最早最晚打卡时间

			//固定周期，不同时间
			List<SysAttendCategoryTimesheet> tSheets = category
					.getFdTimeSheets();
			if (tSheets != null && !tSheets.isEmpty()) {
				SysAttendCategoryTimesheet timeSheet = null;
				for (SysAttendCategoryTimesheet tSheet : tSheets) {
					if (StringUtil.isNotNull(tSheet.getFdWeek())
							&& tSheet.getFdWeek()
							.indexOf(AttendUtil.getWeek(workDate)
									+ "") > -1) {
						timeSheet = tSheet;
						break;
					}
				}
				if (timeSheet != null) {
					map =getBeginDateAndEndDate(workDate,date,
							timeSheet.getFdWork(),
							timeSheet.getFdStartTime1(),timeSheet.getFdStartTime2(),timeSheet.getFdEndTime1(),timeSheet.getFdEndTime2(),timeSheet.getFdEndDay());
					if(map !=null){
						return map;
					}
				}
			}
		}else{
			map =getBeginDateAndEndDate(workDate,date,
					category.getFdWork(),
					category.getFdStartTime(),
					category.getFdStartTime2(),
					category.getFdEndTime1(),
					category.getFdEndTime(),
					category.getFdEndDay());
			if(map !=null){
				return map;
			}
		}
		//按照排班情况没有找到的话，则默认使用考勤组的最早最晚时间
		if(startTime ==null || endTime ==null){
			//其他类型
			startTime = AttendUtil.joinYMDandHMS(workDate,
					category.getFdStartTime());
			endTime = AttendUtil.joinYMDandHMS(workDate,
					category.getFdEndTime());
			if (Integer.valueOf(2).equals(category.getFdEndDay())) {
				endTime = AttendUtil.addDate(endTime, 1);
			}
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}

	/**
	 * 根据不同班次
	 * @param date
	 * @param startTime1
	 * @param startTime2
	 * @param endTime1
	 * @param endTime2
	 * @param fdEndDay
	 * @return
	 */
	public Map<String, Object> getBeginDateAndEndDate(Date workDate,Date date,
													  Integer fdWork,
													  Date startTime1,Date startTime2,Date endTime1,Date endTime2,Integer fdEndDay){

		Map<String, Object> map = new HashMap<String, Object>();
		Date startTime = AttendUtil.joinYMDandHMS(workDate,startTime1);
		Date endTime = AttendUtil.joinYMDandHMS(workDate,endTime1);
		if(Integer.valueOf(1).equals(fdWork)) {
			//只有一班次的情况下。结束时间以最终结束时间来
			endTime = AttendUtil.joinYMDandHMS(workDate,endTime2);
			//没有第二班次的情况下
			if (Integer.valueOf(2).equals(fdEndDay)) {
				endTime = AttendUtil.addDate(endTime, 1);
			}
		} else {
			//两个班次的情况。
			boolean inOneWork = false;
			//当前考勤时间是第一班次。
			if (startTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
				inOneWork = true;
			} else {
				endTime = null;
			}
			//两班次
			if (!inOneWork) {
				startTime = AttendUtil.joinYMDandHMS(workDate, startTime2);
				endTime = AttendUtil.joinYMDandHMS(workDate, endTime2);
				if (Integer.valueOf(2).equals(fdEndDay)) {
					endTime = AttendUtil.addDate(endTime, 1);
				}
				//两班次
				if (startTime.getTime() <= date.getTime() && endTime.getTime() > date.getTime()) {
					//第二班次的范围内
				} else {
					endTime = null;
				}
			}
		}
		if(endTime ==null){
			return null;
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}

	public ActionForward isAllowPatch(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		int status = 1;
		String msg = "";
		result.put("status", status);
		result.put("msg", msg);
		try {
			String fdAttendMainId = request.getParameter("fdAttendMainId");
			if (StringUtil.isNotNull(fdAttendMainId)) {
				result =checkNumber(fdAttendMainId);
			} else {
				throw new NoRecordException();
			}
		} catch (Exception e) {
			messages.addError(e);
			status = 0;
		}
		request.setAttribute("lui-source", result);
		return mapping.findForward("lui-source");
	}

	/**
	 * 验证补卡次数
	 * @param fdAttendMainId
	 * @return
	 * @throws Exception
	 */
	private JSONObject checkNumber(String fdAttendMainId) throws Exception {
		JSONObject result = new JSONObject();
		int status = 1;
		String msg = "";
		SysAttendMain main = (SysAttendMain) getSysAttendMainService()
				.findByPrimaryKey(fdAttendMainId);
		SysAttendCategory category = CategoryUtil.getFdCategoryInfo(main);
		
		Boolean fdIsPatch = category.getFdIsPatch();
		Integer fdPatchTimes = category.getFdPatchTimes();
//		Integer fdPatchTimes = Integer.MAX_VALUE;
		Integer fdPatchDay = category.getFdPatchDay();
		Date docCreateTime = main.getDocCreateTime();
		System.out.println("打卡信息： 签到事项="+category.getFdName()+
				";categoryId="+category.getFdId()+";"
						+ "fdIsPatch="+fdIsPatch+";"
						+ "fdPatchTimes="+fdPatchTimes+";"
								+ "fdPatchDay="+fdPatchDay+";"
								+ "docCreateTime="+docCreateTime);
		Number count = 0;
		if (fdIsPatch == null || fdIsPatch.booleanValue()) {
			if (fdPatchDay != null && docCreateTime != null) {
				// 是否已过了补卡时间
				Date date = AttendUtil.getDate(docCreateTime, 0);
				Date today = AttendUtil.getDate(new Date(), 0);
				long betweenDays = (today.getTime() - date.getTime()) / 86400000L;
				if (betweenDays > fdPatchDay.longValue()) {
					status = 0;
					msg = "overdue";
				}
			}
			if (status == 1 && fdPatchTimes != null && fdPatchTimes != 0
					&& docCreateTime != null) {
				// 这个月内已经提交了多少次补卡
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setSelectBlock("count(*)");
				StringBuffer whereBlock = new StringBuffer("1=1");
				Date startTime = AttendUtil.getMonth(docCreateTime, 0);
				Date endTime = AttendUtil.getMonth(docCreateTime, 1);
				whereBlock.append(
						" and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime");
				hqlInfo.setParameter("startTime", startTime);
				hqlInfo.setParameter("endTime", endTime);
				whereBlock.append(" and sysAttendMain.docCreator.fdId=:docCreatorId and (sysAttendMain.fdState=1 or sysAttendMain.fdState=2)");
				hqlInfo.setParameter("docCreatorId",main.getDocCreator().getFdId());
				whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
				hqlInfo.setWhereBlock(whereBlock.toString());
				List countList = getSysAttendMainService()
						.findValue(hqlInfo);
				
				
				if (!countList.isEmpty()) {
					count = (Number) countList.get(0);
				}
				if (count.intValue() > 0 && count
						.intValue() >= fdPatchTimes.intValue()) {
					status = 1;
					msg = "";
//					msg = "overTimes";
				}
			}
		} else {
			// 不允许补卡
			status = 0;
			msg = "notAllow";
		}
		result.put("status", status);
		result.put("msg", msg);
		result.put("count", count);
		return result;
	}
}
