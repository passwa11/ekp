package com.landray.kmss.km.imeeting.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingResForm;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingInnerScreen;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingOuterScreen;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingInnerScreenService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOuterScreenService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.util.ImeetingCalendarUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.thread.NamedThreadFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.CollectionUtils;

/**
 * 会议室信息 Action
 */
public class KmImeetingResAction extends ExtendAction {

	protected IKmImeetingResService kmImeetingResService;
	private IKmImeetingResCategoryService kmImeetingResCategoryService;
	private IKmImeetingMainService kmImeetingMainService;
	private IKmImeetingBookService kmImeetingBookService;
	private ISysOrgPersonService sysOrgPersonService;

	// create executorPool
	ThreadFactory factory = new NamedThreadFactory("ekp_km_imeeting_thread");
	ExecutorService KM_IMEETING_THEADPOOL = Executors.newFixedThreadPool(3, factory);

	private IKmImeetingInnerScreenService kmImeetingInnerScreenService;

	private IKmImeetingOuterScreenService kmImeetingOuterScreenService;

	public IKmImeetingInnerScreenService getKmImeetingInnerScreenService() {
		if (kmImeetingInnerScreenService == null) {
			kmImeetingInnerScreenService = (IKmImeetingInnerScreenService) getBean(
					"kmImeetingInnerScreenService");
		}
		return kmImeetingInnerScreenService;
	}

	public IKmImeetingOuterScreenService getKmImeetingOuterScreenService() {
		if (kmImeetingOuterScreenService == null) {
			kmImeetingOuterScreenService = (IKmImeetingOuterScreenService) getBean(
					"kmImeetingOuterScreenService");
		}
		return kmImeetingOuterScreenService;
	}

	protected ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	@Override
	protected IKmImeetingResService getServiceImp(HttpServletRequest request) {
		if (kmImeetingResService == null) {
			kmImeetingResService = (IKmImeetingResService) getBean("kmImeetingResService");
		}
		return kmImeetingResService;
	}

	protected IBaseService getCategoryService(HttpServletRequest request) {
		if (kmImeetingResCategoryService == null) {
			kmImeetingResCategoryService = (IKmImeetingResCategoryService) getBean("kmImeetingResCategoryService");
		}
		return kmImeetingResCategoryService;
	}

	protected IKmImeetingMainService getKmImeetingMainService(
			HttpServletRequest request) {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	protected IKmImeetingBookService getKmImeetingBookService(
			HttpServletRequest request) {
		if (kmImeetingBookService == null) {
			kmImeetingBookService = (IKmImeetingBookService) getBean("kmImeetingBookService");
		}
		return kmImeetingBookService;
	}

	/**
	 * 会议室占用情况
	 */
	public ActionForward listUse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listUse", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = ((IKmImeetingResService) getServiceImp(request))
					.listUse(new RequestContext(request));
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listUse", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			String contentType = request.getParameter("contentType");
			if ("json".equals(contentType)) {
				return getActionForward("listUseData", mapping, form, request,
						response);
			}
			return getActionForward("listUse", mapping, form, request, response);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingResForm resForm = (KmImeetingResForm) super.createNewForm(
				mapping, form, request, response);
		// 是否有效默认为"是"
		if (StringUtil.isNull(resForm.getFdIsAvailable())) {
			resForm.setFdIsAvailable("1");
		}
		String categoryId = request.getParameter("categoryId");
		// 分类ID不为空时,设置分类ID和分类名(分类名取全路径)
		if (StringUtil.isNotNull(categoryId)) {
			KmImeetingResCategory category = (KmImeetingResCategory) getCategoryService(
					request).findByPrimaryKey(categoryId);
			resForm.setDocCategoryId(category.getFdId());
			resForm.setDocCategoryName(SimpleCategoryUtil
					.getCategoryPathName(category));
		}
		return resForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmImeetingResForm resForm = (KmImeetingResForm) form;
		String categoryId = resForm.getDocCategoryId();
		if (StringUtil.isNotNull(categoryId)) {
			KmImeetingResCategory category = (KmImeetingResCategory) getCategoryService(
					request).findByPrimaryKey(categoryId);
			resForm.setDocCategoryName(SimpleCategoryUtil
					.getCategoryPathName(category));
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// hqlInfo.setOrderBy("kmImeetingRes.fdOrder,kmImeetingRes.fdName");
		// 资源分类
		String docCategoryId = request.getParameter("docCategoryId");
		if (StringUtil.isNotNull(docCategoryId)) {
			String whereBlock = hqlInfo.getWhereBlock();

			String whereBlockTmp = "";
			String[] ids = docCategoryId.split(";");
			for (int i = 0; i < ids.length; i++) {
				whereBlockTmp = StringUtil.linkString(whereBlockTmp, " or ",
						"kmImeetingRes.docCategory.fdHierarchyId like:fdHierarchyId"
								+ i);
				hqlInfo.setParameter("fdHierarchyId" + i, "%x" + ids[i] + "%");
			}
			if (StringUtil.isNotNull(whereBlockTmp)) {
				whereBlockTmp = "(" + whereBlockTmp + ")";
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						whereBlockTmp);
			}
			hqlInfo.setWhereBlock(whereBlock);
		}
		String fdName = request.getParameter("fdName");
		if (StringUtil.isNotNull(fdName)) {
			String whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "kmImeetingRes.fdName like :fdName");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdName", "%" + fdName + "%");
		}
		String type = request.getParameter("type");
		// 资源必须为有效资源
		if (StringUtil.isNotNull(type)) {
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingRes.fdIsAvailable=:fdIsAvailable ");
			hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
			hqlInfo.setWhereBlock(whereBlock);
		}
		String[] arr = null;
		String exceptResIds = request.getParameter("exceptResIds");
		if (StringUtil.isNotNull(exceptResIds) && !"undefined".equals(exceptResIds)) {
			arr = exceptResIds.split(";");
		}
		// 只查询空闲资源
		if (StringUtil.isNotNull(type)
				&& ImeetingConstant.MEETING_RES_REFF.equals(type)) {
			String fdHoldDate = request.getParameter("fdHoldDate");
			String fdFinishDate = request.getParameter("fdFinishDate");
			if (StringUtil.isNotNull(fdHoldDate)
					&& StringUtil.isNotNull(fdFinishDate)) {
				Date start = DateUtil.convertStringToDate(fdHoldDate,
						DateUtil.TYPE_DATETIME, request.getLocale());
				Date end = DateUtil.convertStringToDate(fdFinishDate,
						DateUtil.TYPE_DATETIME, request.getLocale());

				List<String> result = concatConflictResList(request , start , end);
                // 例外占有资源
				if (arr != null) {
					result.addAll(Arrays.asList(arr));
				}
				// 查询空闲资源
				if (result != null && !result.isEmpty()) {
					String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							buildLogicNotIN("kmImeetingRes.fdId", result));
					hqlInfo.setModelName(KmImeetingRes.class.getName());
					hqlInfo.setWhereBlock(whereBlock);
				}
			}
		}
		// 查询所有资源，记录冲突资源
		KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
		Boolean check = "true".equals(kmImeetingConfig.getUnShow());// 冲突检测配置
		if (StringUtil.isNotNull(type)
				&& ImeetingConstant.MEETING_RES_ALL.equals(type)) {
			String fdHoldDate = request.getParameter("fdHoldDate");
			String fdFinishDate = request.getParameter("fdFinishDate");
			List<String> result = new ArrayList<String>();
			if (StringUtil.isNotNull(fdHoldDate)
					&& StringUtil.isNotNull(fdFinishDate) && check) {
				Date start = DateUtil.convertStringToDate(fdHoldDate,
						DateUtil.TYPE_DATETIME, request.getLocale());
				Date end = DateUtil.convertStringToDate(fdFinishDate,
						DateUtil.TYPE_DATETIME, request.getLocale());
				// 查找这段时间内有占用资源的会议安排
				result = concatConflictResList(request , start , end);
			}
			if (arr != null) {
				result.addAll(Arrays.asList(arr));
			}
			request.setAttribute("conflictRes", StringUtil.join(result, ";"));
		}
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingRes.class);
	}

	/**
	 * 冲突资源查询
	 * @param request
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	private List<String> concatConflictResList(HttpServletRequest request ,Date start ,Date end)throws Exception {
		// 查找这段时间内有占用资源的会议安排
		List<String> result = getServiceImp(request).findConflictResInMain(request, start, end);
		// 优化查询-异步处理
		CompletableFuture.allOf(
				CompletableFuture.runAsync(() ->{
					// 查找这段时间内有占用资源的会议室预约
					try {
						ArrayUtil.concatTwoList(
								getServiceImp(request).findConflictResInBook(request, start, end), result);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				},KM_IMEETING_THEADPOOL),
				CompletableFuture.runAsync(() ->{
					// 查找这段时间内占用资源的分会场
					try {
						ArrayUtil.concatTwoList(
								getServiceImp(request).findConflictViceResInMain(request, start, end), result);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				},KM_IMEETING_THEADPOOL)
		).join();

		return result;
	}
	
	
	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}



	public ActionForward getAllCate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getAllCate", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			array = findAllCate(request);
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
			throw e;
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

	public List findAllAvailableRes(HttpServletRequest request) throws Exception {
		String parentId = request.getParameter("parentId");
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("kmImeetingRes.fdIsAvailable = :fdIsAvailable");
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		hql.setOrderBy("kmImeetingRes.fdOrder asc");
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		if (StringUtil.isNotNull(parentId)) {
			hql.setWhereBlock(StringUtil.linkString(hql.getWhereBlock(), " and ",
					"kmImeetingRes.docCategory.fdId =:docCategoryId"));
			hql.setParameter("docCategoryId", parentId);
		}
		List ress = new ArrayList();

		ress = getServiceImp(request).findValue(hql);
		return ress;
	}

	// 取有权限的会议室分类
	public JSONArray findAllCate(HttpServletRequest request) throws Exception {
		JSONArray jsonArr = new JSONArray();
		HQLInfo hql = new HQLInfo();
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		hql.setOrderBy(" kmImeetingResCategory.fdOrder ");
		List<KmImeetingResCategory> cateList = getCategoryService(request)
				.findValue(hql);

		String modelName = "KmImeetingResCategory";
		String tableName = ModelUtil.getModelTableName(modelName);
		List<String> hierarchyReaderIds = ImeetingCalendarUtil.findHierarchyReaderIds(
				getCategoryService(request), modelName, tableName);
		for (KmImeetingResCategory kmImeetingResCategory : cateList) {
			boolean flag = UserUtil.getKMSSUser().isAdmin() ? true
					: hierarchyReaderIds
							.contains(kmImeetingResCategory.getFdHierarchyId());
			if (!flag) {
				continue;
			}
			JSONObject obj = new JSONObject();
			obj.accumulate("text", kmImeetingResCategory.getFdName());
			obj.accumulate("value", kmImeetingResCategory.getFdId());
			jsonArr.add(obj);
		}

		return jsonArr;
	}

	public JSONArray findBookDetail(HttpServletRequest request, String resId, Date date) throws Exception {
		JSONArray jsonArr = new JSONArray();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date end = cal.getTime();

		date = DateUtil.removeTime(date).getTime();
		List mainlist = getServiceImp(request).findOccupiedResInMain(request, resId, date, end);
		List booklist = getServiceImp(request).findOccupiedResInBook(request, resId, date, end);
		if(mainlist.size()>0){
			for(int m = 0;m<mainlist.size();m++){
				KmImeetingMain kmImeetingMain = (KmImeetingMain) mainlist.get(m);
				SysOrgElement o = kmImeetingMain.getFdEmcee();
				formatOcupiedData(jsonArr, date, kmImeetingMain.getFdHoldDate(), kmImeetingMain.getFdFinishDate(),
						kmImeetingMain.getFdName(),
						o != null ? o : kmImeetingMain.getDocCreator(), true,
						kmImeetingMain.getFdPlace() != null ? kmImeetingMain.getFdPlace().getDocKeeper() : null, null);

			}
		}
		if(booklist.size()>0){
			for(int n = 0;n<booklist.size();n++){
				KmImeetingBook kmImeetingBook = (KmImeetingBook) booklist.get(n);
				formatOcupiedData(jsonArr, date, kmImeetingBook.getFdHoldDate(), kmImeetingBook.getFdFinishDate(),
						kmImeetingBook.getFdName(), kmImeetingBook.getDocCreator(), kmImeetingBook.getFdHasExam(),
						kmImeetingBook.getFdPlace() != null ? kmImeetingBook.getFdPlace().getDocKeeper() : null, kmImeetingBook.getIsNotify());
			}
		}
		return jsonArr;
	}

	private void formatOcupiedData(JSONArray jsonArr, Date selectedDate, Date fdStartDate, Date fdEndDate,
			String fdName, SysOrgElement o, Boolean fdHasExam, SysOrgElement fdPlaceKeeper, Boolean isNotify)
			throws Exception {
		String fdStartDateT = DateUtil.convertDateToString(fdStartDate, DateUtil.TYPE_DATE,
				null);
		String fdEndDateT = DateUtil.convertDateToString(fdEndDate, DateUtil.TYPE_DATE,
				null);
		String fdStartStr = DateUtil.convertDateToString(fdStartDate, DateUtil.TYPE_TIME, null);
		String fdEndStr = DateUtil.convertDateToString(fdEndDate, DateUtil.TYPE_TIME, null);
		int fdStart = Integer.parseInt(fdStartStr.split(":")[0]);
		if (fdStart < 8) {
			fdStart = 8;
		}
		int fdFinish = 0;
		int x = Integer.parseInt(fdEndStr.split(":")[1]);
		if (x > 0) {
			fdFinish = Integer.parseInt(fdEndStr.split(":")[0]) + 1;
		} else {
			fdFinish = Integer.parseInt(fdEndStr.split(":")[0]);
		}
		//  没有跨天
		if (!fdStartDateT.equals(fdEndDateT)) {
			String selectedDateT = DateUtil.convertDateToString(selectedDate, DateUtil.TYPE_DATE, null);
			if (selectedDateT.equals(fdStartDateT)) {
				fdFinish = 24;
			} else if (selectedDateT.equals(fdEndDateT)) {
				fdStart = 8;
			} else {
				fdStart = 8;
				fdFinish = 24;
			}
		}
		for (int i = fdStart; i < fdFinish; i++) {
				JSONObject obj = new JSONObject();
				obj.accumulate("timeArea", i);
				obj.accumulate("fdName", fdName);
				obj.accumulate("fdStartTime", DateUtil.convertDateToString(fdStartDate,
						DateUtil.PATTERN_DATETIME));
				obj.accumulate("fdFinishTime", DateUtil.convertDateToString(fdEndDate,
						DateUtil.PATTERN_DATETIME));
				if (o != null) {
					SysOrgPerson p = (SysOrgPerson) getSysOrgPersonService()
							.findByPrimaryKey(o.getFdId());
					obj.accumulate("fdHost", p.getFdName());

					obj.accumulate("fdPhone", p.getFdMobileNo());
				}
				
				//通过isNotify判断是否是会议预约旧数据，旧数据的话默认为审批通过
				if(isNotify == null) {
					obj.accumulate("fdHasExam", "true");
				} else {
					if(fdHasExam == null) {
						obj.accumulate("fdHasExam", "wait");
					} else {
						if(fdHasExam == true) {
							obj.accumulate("fdHasExam", "true");
						} else {
							obj.accumulate("fdHasExam", "false");
						}
					}
				}
				// 会议室保管员可能是人员和岗位，只有是人员时才取保管员的手机号
				if (fdPlaceKeeper != null && fdPlaceKeeper.getFdOrgType() == 8) {
					SysOrgPerson p = (SysOrgPerson) getSysOrgPersonService()
							.findByPrimaryKey(fdPlaceKeeper.getFdId());
					obj.accumulate("fdPlaceKeeperPhone", p.getFdMobileNo());
				}
				
				jsonArr.add(obj);
			}
	}

	public ActionForward listAvailable(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-generateTime", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			String fdTime = request.getParameter("fdTime");
			JSONArray array = new JSONArray();
			List ress = findAllAvailableRes(request);
			Date fdDate = new Date();
			Date nowDate = new Date();
			if (!StringUtil.isNull(fdTime)) {
				fdDate = DateUtil.convertStringToDate(fdTime, ResourceUtil.getString("date.format.date"));
			} else {
				fdTime = DateUtil.convertDateToString(fdDate, ResourceUtil.getString("date.format.date"));
			}
			String nowDateStr = DateUtil.convertDateToString(nowDate, ResourceUtil.getString("date.format.date"));
			for (int i = 0; i < ress.size(); i++) {
				KmImeetingRes kmImeetingRes = (KmImeetingRes) ress.get(i);
				JSONObject obj = new JSONObject();
				obj.accumulate("fdId", kmImeetingRes.getFdId());
				obj.accumulate("fdName",
						StringUtil.XMLEscape(kmImeetingRes.getFdName()));
				obj.accumulate("fdSeats", kmImeetingRes.getFdSeats());
				obj.accumulate("fdTime", fdTime);
				obj.accumulate("fdDetail", kmImeetingRes.getFdDetail());
				
				if(kmImeetingRes.getFdNeedExamFlag() != null && 
						kmImeetingRes.getFdNeedExamFlag() == true) {
					obj.accumulate("fdNeedExamFlag", "true");
				} else {
					obj.accumulate("fdNeedExamFlag", "false");
				}
				
				if (nowDateStr.equals(fdTime)) {
					obj.accumulate("today", true);
					String nowTimeStr = DateUtil.convertDateToString(nowDate, DateUtil.TYPE_TIME, null);
					int fdHour = Integer.parseInt(nowTimeStr.split(":")[0]);
					int fdMinute = Integer.parseInt(nowTimeStr.split(":")[1]);
					if (fdMinute > 0) {
						fdHour += 1;
					}
					obj.accumulate("todayHour", fdHour);
				}else{
					obj.accumulate("today", false);
				}
				
				// 保管者
				obj.accumulate("docKeeper",
						kmImeetingRes.getDocKeeper() != null ? kmImeetingRes.getDocKeeper().getFdName() : "");
				// 设备详情
				obj.accumulate("fdAddressFloor", kmImeetingRes.getFdAddressFloor());
				// 会议室占用列表
				JSONArray detailArray = new JSONArray();
				detailArray = findBookDetail(request, kmImeetingRes.getFdId(), fdDate);
				String type = request.getParameter("type");
				String ai = request.getParameter("ai");
				if(StringUtil.isNotNull(ai)&&"true".equals(ai)){
					//过滤重复的数据
					detailArray = getNotReaptMeetingInfo(detailArray);
					JSONArray freeja = getMeetingInfo(detailArray, fdTime);
					if(StringUtil.isNull(type)){
						//空闲+非空闲
						for(int j=0;j<freeja.size();j++){
							detailArray.add(freeja.getJSONObject(j));
						}
						obj.accumulate("fdOccupiedlist", detailArray);
					}else if("0".equals(type)){
						//取空闲
						obj.accumulate("fdOccupiedlist", freeja);
					}else if("1".equals(type)){
						//取非空闲
						obj.accumulate("fdOccupiedlist", detailArray);
					}
				}else{
					obj.accumulate("fdOccupiedlist", detailArray);
				}
				array.add(obj);
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
			throw e;
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
	
	private JSONArray getNotReaptMeetingInfo(JSONArray oja) throws Exception{
		JSONArray rtnja = new JSONArray();
		JSONObject jo = new JSONObject();
		Map<String,String> map = new HashMap<String,String>();
		if(oja!=null&&oja.size()>0){
			String temp = null;
			for(int i=0;i<oja.size();i++){
				jo = oja.getJSONObject(i);
				temp = jo.getString("fdStartTime")+"="+jo.getString("fdFinishTime");
				if(!map.containsKey(temp)){
					map.put(temp, temp);
					rtnja.add(jo);
				}
			}
		}
		return rtnja;
	}

	private JSONArray getMeetingInfo(JSONArray oja, String time) throws Exception{
		JSONArray rtnja = new JSONArray();
		JSONObject jo = new JSONObject();
		Date ft = DateUtil.convertStringToDate(time, DateUtil.PATTERN_DATE);
		long ftime = ft.getTime()+8* 3600000L;
		long etime = ft.getTime()+18* 3600000L;
		List<Long> tl = new ArrayList<Long>();
		Map<Long, Date> tm = new HashMap<Long, Date>();
		Long ttm = 0L;
		String free = ResourceUtil.getString("kmImeeting.ai.meeting", "km-imeeting");
		if(oja!=null&&oja.size()>0){
			for(int i=0;i<oja.size();i++){
				jo = oja.getJSONObject(i);
				if(jo.containsKey("timeArea")){
					ttm = DateUtil.convertStringToDate(jo.getString("fdStartTime"), DateUtil.PATTERN_DATETIME).getTime();
					tl.add(ttm);
					tm.put(ttm, DateUtil.convertStringToDate(jo.getString("fdFinishTime"), DateUtil.PATTERN_DATETIME));
				}
			}
			Collections.sort(tl);
			if(tl.size()>0){
				for(int i=0;i<tl.size();i++){
					jo = new JSONObject();
					jo.put("fdHost", "");
					jo.put("fdPhone", "");
					jo.put("fdName", free);
					if(ftime<tl.get(i)&&i==0){
						jo.put("timeArea", 8);
						jo.put("fdStartTime", time+" 8:00");
						jo.put("fdFinishTime", DateUtil.convertDateToString(new Date(tl.get(i)),DateUtil.PATTERN_DATETIME));
						rtnja.add(jo);
					}else if(i>0){
						String fdStartTime = DateUtil.convertDateToString(
								tm.get(tl.get(i - 1)),
								DateUtil.PATTERN_DATETIME);
						String fdFinishTime = DateUtil.convertDateToString(
								new Date(tl.get(i)), DateUtil.PATTERN_DATETIME);
						if (!fdStartTime.equals(fdFinishTime)) {
							jo.put("timeArea", new Date(tl.get(i)).getHours());
							jo.put("fdStartTime", fdStartTime);
							jo.put("fdFinishTime", fdFinishTime);
							rtnja.add(jo);
						}
					}
					if(i==tl.size()-1&&etime>tm.get(tl.get(i)).getTime()){
						jo = new JSONObject();
						jo.put("fdHost", "");
						jo.put("fdPhone", "");
						jo.put("fdName", free);
						jo.put("timeArea", new Date(tl.get(i)).getHours()+1);
						jo.put("fdStartTime",  DateUtil.convertDateToString(tm.get(tl.get(i)),DateUtil.PATTERN_DATETIME));
						jo.put("fdFinishTime", time+" 18:00");
						rtnja.add(jo);
					}			
				}
			}
		}else{
			jo = new JSONObject();
			jo.put("fdHost", "");
			jo.put("fdPhone", "");
			jo.put("fdName", free);
			jo.put("timeArea", 8);
			jo.put("fdStartTime",  time+" 8:00");
			jo.put("fdFinishTime", time+" 18:00");
			rtnja.add(jo);
		}
		return rtnja;
	}
	
	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);
		Page page = (Page) request.getAttribute("queryPage");
		JSONArray source = new JSONArray();
		for (Object object : page.getList()) {
			KmImeetingRes res = (KmImeetingRes) object;
			JSONObject resObj = new JSONObject();
			resObj.put("value", res.getFdId());
			resObj.put("text", res.getFdName());
			source.add(resObj);
		}
		request.setAttribute("lui-source", source);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	public ActionForward listCategoryDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("categoryId");
			List detailList = new ArrayList();
			if (StringUtil.isNotNull(categoryId)) {
				KmImeetingResCategory category = (KmImeetingResCategory) getCategoryService(request)
						.findByPrimaryKey(categoryId);
				JSONObject obj = new JSONObject();
				obj.put("fdId", category.getFdId());
				obj.put("label", category.getFdName());
				obj.put("parentId", category.getFdParent() == null ? "" : category.getFdParent().getFdId());
				detailList.add(obj);
			}
			request.setAttribute("lui-source", JSONArray.fromObject(detailList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward listCategoryAndRes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listResources", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List categoryAndRes = new ArrayList();
			String parentId = request.getParameter("parentId");
			// 查询出分类
			HQLInfo categoryHqlInfo = new HQLInfo();
			categoryHqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			if (StringUtil.isNotNull(parentId)) {
				categoryHqlInfo.setWhereBlock(StringUtil.linkString("kmImeetingResCategory.hbmParent.fdId = :parentId",
						" and ", categoryHqlInfo.getWhereBlock()));
				categoryHqlInfo.setParameter("parentId", parentId);
			} else {
				categoryHqlInfo.setWhereBlock(StringUtil.linkString("kmImeetingResCategory.hbmParent is null",
						" and ",
						categoryHqlInfo.getWhereBlock()));
			}
			List categroy = getCategoryService(request).findValue(categoryHqlInfo);
			categoryAndRes.addAll(categroy);
			// 查询出相关会议室
			HQLInfo resHqlInfo = new HQLInfo();
			changeFindPageHQLInfo(request, resHqlInfo);
			resHqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			if (StringUtil.isNotNull(parentId)) {
				resHqlInfo.setWhereBlock(StringUtil.linkString(resHqlInfo.getWhereBlock(), " and ",
						"kmImeetingRes.docCategory.fdId =:docCategoryId"));
				resHqlInfo.setParameter("docCategoryId", parentId);
			} else {
				resHqlInfo.getParameterList().clear();
				resHqlInfo.setWhereBlock("1=2");
			}
			List ress = getServiceImp(request).findValue(resHqlInfo);
			categoryAndRes.addAll(ress);
			request.setAttribute("list", categoryAndRes);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listResources", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listCategoryAndRes", mapping, form, request, response);
		}
	}

	// 显示会议室资源
	public ActionForward listResources(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listResources", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// super.list(mapping, form, request, response);
			HQLInfo hqlInfo = new HQLInfo();
			changeFindPageHQLInfo(request, hqlInfo);
			// 使用权限过滤
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			List<KmImeetingRes> list = getServiceImp(request)
					.findValue(hqlInfo);
			for(int i=0;i<list.size();i++){
				KmImeetingRes kmImeetingRes = list.get(i);
				String fdDetailOld = kmImeetingRes.getFdDetail();
				if(StringUtil.isNotNull(fdDetailOld)){
					if(fdDetailOld.indexOf("\r\n")>=0){
						String fdDetailNew = fdDetailOld.replace("\r\n", "<br>");
						kmImeetingRes.setFdDetail(fdDetailNew);
						list.set(i, kmImeetingRes);
					}
				}
			}
			request.setAttribute("list", list);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listResources", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listResources", mapping, form, request,
					response);
		}
	}

	// 资源冲突检测（AJAX调用）
	public ActionForward checkConflict(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json; charset=utf-8");
		TimeCounter.logCurrentTime("Action-checkConflict", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
			Boolean check = "true".equals(kmImeetingConfig.getUnShow());// 冲突检测配置
			String fdPlaceId = request.getParameter("fdPlaceId");
			if (StringUtil.isNotNull(fdPlaceId) && check) {
				json = getServiceImp(request).isConflictRes(request, fdPlaceId);
			} else {
				json.put("result", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		response.getWriter().write(json.toString());// 结果
		// response.setCharacterEncoding("UTF-8");
		TimeCounter.logCurrentTime("Action-checkConflict", false, getClass());
		return null;
	}


	// 获得not in查询语句
	private String buildLogicNotIN(String item, List valueList) {
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String) {
			isString = true;
		}
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0) {
				rtnStr.append(" or ");
			}
			rtnStr.append(item + " not  in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0) {
			return "(" + rtnStr.toString() + ")";
		} else {
			return rtnStr.toString();
		}
	}
	
	public ActionForward placeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray jsonArrLast = new JSONArray();
		jsonArrLast=((IKmImeetingResService) getServiceImp(request)).placeList();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonArrLast.toString());// 结果
		return null;
	}
	
	public ActionForward chenckRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		JSONArray cateJson = new JSONArray();
		JSONArray resJson = new JSONArray();
		String cateId = request.getParameter("cateId");
		String placeId = request.getParameter("placeId");
		response.setCharacterEncoding("UTF-8");
		if(StringUtil.isNotNull(cateId)){
			 cateJson = ((IKmImeetingResService) getServiceImp(request)).getCateById(cateId);
			if(cateJson.size()>0){
				if(StringUtil.isNotNull(placeId)){
					resJson = ((IKmImeetingResService) getServiceImp(request)).getResById(placeId,cateJson);
					response.getWriter().write(resJson.toString());
				}else{
					response.getWriter().write(cateJson.toString());
				}
			}else{
				response.getWriter().write("Category is not found");
			}
		}else{
			resJson = ((IKmImeetingResService) getServiceImp(request)).getResById(placeId,cateJson);
			response.getWriter().write(resJson.toString());
		}
		return null;
	}
	
	public ActionForward chenckResIsFree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		String resId = request.getParameter("resId");
		String fdHoldDate = request.getParameter("fdHoldDate");
		String fdFinishDate = request.getParameter("fdFinishDate");
		String type = request.getParameter("type");
		response.setCharacterEncoding("UTF-8");
		boolean flag=false;
		if (StringUtil.isNotNull(fdHoldDate) && StringUtil.isNotNull(fdFinishDate) && StringUtil.isNotNull(resId) && "free".equals(type)) {
			HQLInfo resHqlInfo = new HQLInfo();
			changeFindPageHQLInfo(request, resHqlInfo);
			resHqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_READER);
			List<KmImeetingRes> ress = getServiceImp(request).findList(resHqlInfo);
			for (KmImeetingRes kmImeetingRes : ress) {
				if(resId.equals(kmImeetingRes.getFdId())){
					//空闲
					flag=true;
					break;
				}
			}
		}
		
		if(flag){
			response.getWriter().write("true");
		}else{
			response.getWriter().write("false");
		}
		return null;
	}

	public ActionForward addSyncToBoen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			((IKmImeetingResService) getServiceImp(request)).addSyncToBoen();
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}

	/**
	 * 获取可用的会议室资源
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFreeRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-getAvailableRes", true, getClass());
		JSONObject retJson = new JSONObject();
		try {
			String fdPlaceId = request.getParameter("fdPlaceId");
			retJson.put("errcode", 0);
			if (StringUtil.isNotNull(fdPlaceId)) {
				KmImeetingRes kmImeetingRes = (KmImeetingRes) ((IKmImeetingResService) getServiceImp(
						request)).findByPrimaryKey(fdPlaceId);
				// 会议室不为空，为检测冲突
				KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
				Boolean check = "true".equals(kmImeetingConfig.getUnShow());// 冲突检测配置
				if (check) {
					JSONObject json = getServiceImp(request).isConflictRes(request, fdPlaceId);
					if (json.getBoolean("result")) {
						// 有冲突
						retJson.put("errcode", 1);
						Object[] args = new Object[] {
								kmImeetingRes.getFdName() };
						retJson.put("errmsg",
								ResourceUtil.getString(
										"kmImeetingRes.conflict.msg",
										"km-imeeting", null, args));
					}
				}
				if (retJson.getInt("errcode") == 0) {
					retJson.put("errmsg", "");
					JSONArray datas = new JSONArray();
					JSONObject data = new JSONObject();
					data.put("id", fdPlaceId);
					data.put("name", kmImeetingRes.getFdName());
					datas.add(data);
					retJson.put("data", datas);
				}
			}
			if (StringUtil.isNull(fdPlaceId)
					|| retJson.getInt("errcode") == 1) {
				// 当非检测冲突，以及有冲突时，获取空闲时段的可用资源
				String s_rowsize = request.getParameter("rowsize");
				int rowsize = 5;
				if (StringUtil.isNotNull(s_rowsize)) {
					rowsize = Integer.parseInt(s_rowsize);
					if (rowsize < 1) {
						rowsize = 5;
					}
				}

				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setRowSize(rowsize);
				changeFindPageHQLInfo(request, hqlInfo);
				// 使用权限过滤
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.DEFAULT);
				Page page = getServiceImp(request).findPage(hqlInfo);
				if (page.getTotal() > 0) {
					List<KmImeetingRes> list = page.getList();
					JSONArray datas = new JSONArray();
					for (KmImeetingRes meetingRes : list) {
						JSONObject data = new JSONObject();
						data.put("id", meetingRes.getFdId());
						data.put("name", meetingRes.getFdName());
						datas.add(data);
					}
					retJson.put("data", datas);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("errcode", -1);
			retJson.put("errmsg", "system error," + e.toString());
		}
		if ("function".equals(request.getAttribute("call"))) {
			request.setAttribute("retJson", retJson);
		} else {
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(retJson.toString());// 结果
		}

		TimeCounter.logCurrentTime("Action-getAvailableRes", false, getClass());
		return null;
	}

	public ActionForward checkUnique(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resId = request.getParameter("resId");
		String fdCode = request.getParameter("fdCode");
		response.setCharacterEncoding("UTF-8");
		HQLInfo hqlInfo = new HQLInfo();
		String hql = " kmImeetingInnerScreen.fdCode=:fdCode and kmImeetingInnerScreen.fdRes.fdId!=:resId and kmImeetingInnerScreen.fdRes.fdIsAvailable=:fdIsAvailable";
		hqlInfo.setWhereBlock(hql);
		hqlInfo.setParameter("fdCode", fdCode);
		hqlInfo.setParameter("resId", resId);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<KmImeetingInnerScreen> lists = getKmImeetingInnerScreenService()
				.findList(hqlInfo);
		if (lists != null && lists.size() > 0) {
			response.getWriter().write("false");
		} else {
			HQLInfo hqlInfo2 = new HQLInfo();
			String hql2 = " kmImeetingOuterScreen.fdCode=:fdCode and kmImeetingOuterScreen.fdRes.fdId!=:resId and kmImeetingOuterScreen.fdRes.fdIsAvailable=:fdIsAvailable";
			hqlInfo2.setWhereBlock(hql2);
			hqlInfo2.setParameter("fdCode", fdCode);
			hqlInfo2.setParameter("resId", resId);
			hqlInfo2.setParameter("fdIsAvailable", Boolean.TRUE);
			List<KmImeetingOuterScreen> lists2 = getKmImeetingOuterScreenService()
					.findList(hqlInfo2);
			if (lists != null && lists.size() > 0) {
				response.getWriter().write("false");
			} else {
				response.getWriter().write("true");
			}
		}

		return null;
	}

}
