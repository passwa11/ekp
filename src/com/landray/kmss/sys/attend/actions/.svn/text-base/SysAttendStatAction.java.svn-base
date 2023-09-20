package com.landray.kmss.sys.attend.actions;

import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendRestatLog;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainJobService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendRestatLogService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.service.spring.AttendStatThread;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 人员统计表 Action
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public class SysAttendStatAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendStatAction.class);
	protected ISysAttendStatService sysAttendStatService;
	private ISysAttendMainJobService sysAttendMainJobService;
	private ISysAttendOrgService sysAttendOrgService;
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysAttendStatMonthJobService sysAttendStatMonthJobService;
	private ISysOrgCoreService sysOrgCoreService;


	protected ISysOrgCoreService getSysOrgServiceImp() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	@Override
    protected ISysAttendStatService getServiceImp(HttpServletRequest request) {
		if(sysAttendStatService == null){
			sysAttendStatService = (ISysAttendStatService)getBean("sysAttendStatService");
		}
		return sysAttendStatService;
	}

	public ISysAttendMainJobService getSysAttendMainJobService() {
		if (sysAttendMainJobService == null) {
			sysAttendMainJobService = (ISysAttendMainJobService) getBean(
					"sysAttendMainJobService");
		}
		return sysAttendMainJobService;
	}

	public ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) getBean(
					"sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}

	public ISysAttendStatMonthJobService getSysAttendStatMonthJobService() {
		if (sysAttendStatMonthJobService == null) {
			sysAttendStatMonthJobService = (ISysAttendStatMonthJobService) getBean(
					"sysAttendStatMonthJobService");
		}
		return sysAttendStatMonthJobService;
	}

	public ISysAttendOrgService getSysAttendOrgService() {
		if (sysAttendOrgService == null) {
			sysAttendOrgService = (ISysAttendOrgService) getBean(
					"sysAttendOrgService");
		}
		return sysAttendOrgService;
	}

	public ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	/**
	 * 统计日志服务
	 */
	private ISysAttendRestatLogService sysAttendRestatLogService;
	public ISysAttendRestatLogService getSysAttendRestatLogService() {
		if (sysAttendRestatLogService == null) {
			sysAttendRestatLogService = (ISysAttendRestatLogService) getBean("sysAttendRestatLogService");
		}
		return sysAttendRestatLogService;
	}
	public ActionForward stat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-stat", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			String fdDate = request.getParameter("fdDate");
			String statType = request.getParameter("statType");
			String fdDeptId = request.getParameter("fdDeptId");
			Date statDate = new Date();
			if (StringUtil.isNotNull(fdDate)) {
				statDate = new Date(Long.valueOf(fdDate));
			}
			statType = StringUtil.isNotNull(statType) ? statType : "1";
			result = getServiceImp(null).sumAttendCount(statDate, statType,
					fdDeptId, request);
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-stat", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 根据时间区间 重新生成统计数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward restat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdCategoryIds = request.getParameter("fdCategoryIds");
			String fdStartTime = request.getParameter("fdStartTime");
			String fdEndTime = request.getParameter("fdEndTime");
			String fdIsCalMissed = request.getParameter("fdIsCalMissed");
			String fdTargetIds = request.getParameter("fdTargetIds");
			String fdOperate = request.getParameter("fdOperate");
			Date startTime = AttendUtil
					.getDate(DateUtil.convertStringToDate(fdStartTime,
							DateUtil.TYPE_DATE, null), 0);
			Date endTime = AttendUtil
					.getDate(DateUtil.convertStringToDate(fdEndTime,
							DateUtil.TYPE_DATE, null), 0);
			if (endTime.after(AttendUtil.getDate(new Date(), 0))) {
				// 不统计当天数据
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(
								KmssReturnPage.BUTTON_RETURN)
						.save(request);
				return getActionForward("failure", mapping, form, request,
						response);
			}

			List<Date> dateList = new ArrayList<Date>();
			// 获取考勤人员集合
			List<String> orgList = new ArrayList<String>();
			//重新统计日志表数据
			SysAttendRestatLog restatLog=new SysAttendRestatLog();
			restatLog.setFdBeginDate(startTime);
			restatLog.setFdEndDate(endTime);
			restatLog.setFdStatus(0);
			restatLog.setFdCreateMiss("true".equals(fdIsCalMissed));
			restatLog.setDocSubject(fdOperate);
			StringBuilder categoryNames=new StringBuilder();
			if (StringUtil.isNotNull(fdCategoryIds)) {
				String[] cateIds = fdCategoryIds.split(";");
				//考勤组名称获取
				List<SysAttendCategory> categoryList = getSysAttendCategoryService().findByPrimaryKeys(cateIds);
				Set<String> tempOrgIds =new HashSet<>();
				if(CollectionUtils.isNotEmpty(categoryList)){
					for (SysAttendCategory category: categoryList) {
						tempOrgIds.addAll(CategoryUtil.getOldCategoryUsers(category.getFdId(),startTime,endTime));
						categoryNames.append(category.getFdName()).append(";");
					}
 					//获取到的组织转换为人员
					orgList.addAll(getSysOrgServiceImp().expandToPersonIds(Lists.newArrayList(tempOrgIds)));
				}
				restatLog.setFdCategoryName(categoryNames.toString());
			}
			StringBuilder orgNames=new StringBuilder();
			// 组织架构
			if (StringUtil.isNotNull(fdTargetIds)) {
				String[] fdTargets = fdTargetIds.split(";");
				List<String> fdTargetList = Arrays.asList(fdTargets);
				fdTargetList = getSysOrgServiceImp()
						.expandToPersonIds(fdTargetList);
				for (String id : fdTargetList) {
					if (!orgList.contains(id)) {
						orgList.add(id);
					}
				}
				//组织架构对应的名称
				List<SysOrgElement> orgObjList = getSysOrgServiceImp().findByPrimaryKeys(fdTargets);
				if(CollectionUtils.isNotEmpty(orgObjList)){
					for (SysOrgElement org: orgObjList) {
						orgNames.append(org.getFdName()).append(";");
					}
				}
				restatLog.setFdStatUserNames(orgNames.toString());
			}
			while (startTime != null) {
				dateList.add(startTime);
				startTime = AttendUtil.getDate(startTime, 1);
				if (startTime.after(endTime)) {
					startTime = null;
				}
			}
			if(CollectionUtils.isEmpty(orgList) || CollectionUtils.isEmpty(dateList)){
				throw new Exception("No parameters found to synchronize");
			}
			//保存日志信息
			getSysAttendRestatLogService().add(restatLog);

			this.restat(dateList, orgList, fdIsCalMissed,fdOperate,restatLog.getFdId());

			if (UserOperHelper.allowLogOper("restat",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putUpdate(fdCategoryIds)
						.putSimple("fdStartTime", null, fdStartTime)
						.putSimple("fdEndTime", null, fdEndTime)
						.putSimple("fdIsCalMissed", null, fdIsCalMissed);
			}
		} catch (Exception e) {
			messages.addError(e);
			logger.error("考勤重新统计报错:", e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 重新统计考勤信息
	 * @param dateList 日期
	 * @param orgList 人员
	 * @param fdIsCalMissed 是否生成缺卡
	 * @param fdOperateType 操作类型：stat 统计有效考勤,create 生成有效考勤记录
	 */
	private void restat(List<Date> dateList, List<String> orgList,String fdIsCalMissed,String fdOperateType,String logId) {
		try {
			if (dateList.isEmpty() || orgList.isEmpty()) {
				return;
			}

			AttendStatThread task = new AttendStatThread();
			task.setDateList(dateList);
			task.setOrgList(orgList);
			task.setFdMethod("restat");
			task.setFdIsCalMissed(fdIsCalMissed);
			task.setFdOperateType(fdOperateType);
			task.setLogId(logId);
			AttendThreadPoolManager manager = AttendThreadPoolManager
					.getInstance();
			if (!manager.isStarted()) {
				manager.start();
			}
			manager.submit(task);
		} catch (Exception e) {
			logger.error("后台手工重新统计失败:", e);
		}
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer("1=1 ");

		String fdDate = request.getParameter("fdDate");
		Date fdStartTime =null;
		if (StringUtil.isNotNull(fdDate)) {
			Date statDate = new Date(Long.valueOf(fdDate));
			fdStartTime = AttendUtil.getDate(statDate, 0);
			Date fdEndTime = AttendUtil.getDate(statDate, 1);
			String operType = request.getParameter("opertype");
			if ("chart".equals(operType)
					&& !statDate.before(AttendUtil.getDate(new Date(), 0))) {// 不统计当天数据
				sb.append(" and 1=2 ");
			} else {
				sb.append(
						" and sysAttendStat.fdDate>=:fdStartTime and sysAttendStat.fdDate <:fdEndTime ");
				hqlInfo.setParameter("fdStartTime", fdStartTime);
				hqlInfo.setParameter("fdEndTime", fdEndTime);
			}
		}
	
		// 根据人员权限过滤
		if (!getServiceImp(null).isStatAllReader()) {
			// 部门列表
			List deptIds = getSysAttendOrgService()
					.findDeptsByLeader(UserUtil.getUser());
			// 人员列表
			List personIds = getSysAttendOrgService()
					.findPersonsByLeader(UserUtil.getUser());
			// 考勤组负责人
			List<String> cateIds = getServiceImp(null).findCategoryIds();
			// 考勤组可阅读者和可编辑者
			cateIds.addAll(getSysAttendCategoryService()
					.findCateIdsByAuthId(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 1));
			if (deptIds != null && !deptIds.isEmpty() || !cateIds.isEmpty() || !personIds.isEmpty()) {
				StringBuffer tmp = new StringBuffer(" (");
				for (int i = 0; i < deptIds.size(); i++) {
					String deptId = "deptId" + i;
					String prefix = " ";
					if (i > 0) {
						prefix = " or ";
					}
					tmp.append(
							prefix + "sysAttendStat.docCreator.fdHierarchyId like :"
									+ deptId);
					hqlInfo.setParameter(deptId, "%" + deptIds.get(i) + "%");
				}
				if (!cateIds.isEmpty()) {
					// 获取考勤组列表中的所有有效人员
					List<String> orgIds = getSysAttendCategoryService().getAttendPersonIds(cateIds,fdStartTime ==null?new Date():fdStartTime,true);
					if (!orgIds.isEmpty()) {
						personIds.addAll(orgIds);
					}
				}
				if (!personIds.isEmpty()) {
					String prefix = " ";
					if (tmp.toString().indexOf("sysAttendStat") > -1) {
						prefix = " or ";
					}
					tmp.append(prefix + HQLUtil.buildLogicIN(
							"sysAttendStat.docCreator.fdId", personIds));
				}
				tmp.append(")");
				sb.append(" and " + tmp.toString());
			}
		}

		String fdDeptId = request.getParameter("fdDeptId");
		if (StringUtil.isNotNull(fdDeptId)) {
			sb.append(
					" and sysAttendStat.docCreator.fdHierarchyId like :fdDeptId");
			hqlInfo.setParameter("fdDeptId", "%" + fdDeptId + "%");
		}

		String fdType = request.getParameter("fdType");
		if (StringUtil.isNotNull(fdType)) {
			setFdTypeHql(sb, hqlInfo, fdType);
		}
		hqlInfo.setWhereBlock(sb.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendStat.class);
		request.setAttribute("fdType", fdType);
	}

	private void setFdTypeHql(StringBuffer sb, HQLInfo hqlInfo, String fdType) {
		if ("0".equals(fdType)) {
			sb.append(" and sysAttendStat.fdMissed=:fdMissed");
			hqlInfo.setParameter("fdMissed", true);
		} else if ("1".equals(fdType)) {
			sb.append(" and sysAttendStat.fdStatus=:fdStatus");
			hqlInfo.setParameter("fdStatus", true);
		} else if ("2".equals(fdType)) {
			sb.append(" and sysAttendStat.fdLate=:fdLate");
			hqlInfo.setParameter("fdLate", true);
		} else if ("3".equals(fdType)) {
			sb.append(" and sysAttendStat.fdLeft=:fdLeft");
			hqlInfo.setParameter("fdLeft", true);
		} else if ("4".equals(fdType)) {
			sb.append(" and sysAttendStat.fdOutside=:fdOutside");
			hqlInfo.setParameter("fdOutside", true);
		} else if ("5".equals(fdType)) {
			sb.append(" and (sysAttendStat.fdAbsent=:fdAbsent or sysAttendStat.fdAbsentDays > 0)");
			hqlInfo.setParameter("fdAbsent", true);
		} else if ("6".equals(fdType)) {
			sb.append(" and sysAttendStat.fdTripDays > 0");
		} else if ("7".equals(fdType)) {
			sb.append(
					" and (sysAttendStat.fdOffDays > 0 or sysAttendStat.fdOffTime > 0 or sysAttendStat.fdOffTimeHour > 0)");
		} else if ("8".equals(fdType)) {
			sb.append(" and sysAttendStat.fdOverTime>0");
		} else if ("9".equals(fdType)) {
			sb.append(" and sysAttendStat.fdOutgoingTime>0");
		}

	}

	public ActionForward addressList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List addressList = getServiceImp(request)
					.addressList(new RequestContext(request));
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

	/**
	 * 重新统计日志列表查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statLoglist(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-stat-log-list", true, getClass());
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

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			if (StringUtil.isNull(orderby)) {
				orderby = "sysAttendRestatLog.docCreateTime desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			Page page = getSysAttendRestatLogService().findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("loglist", mapping, form, request, response);
		}
	}
}

