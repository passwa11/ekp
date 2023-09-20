package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.model.SysAttendStatDetail;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendReportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendStatDetailService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员统计详情 Action
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendStatDetailAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendStatDetailAction.class);

	protected ISysAttendStatDetailService sysAttendStatDetailService;

	protected ISysAttendCategoryService sysAttendCategoryService;
	protected ISysAttendStatService sysAttendStatService;
	protected ISysAttendHisCategoryService sysAttendHisCategoryService;
	private ISysAttendConfigService sysAttendConfigService;
 
	protected ISysAttendConfigService getSysAttendConfigService(HttpServletRequest request) {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) getBean(
					"sysAttendConfigService");
		}
		return sysAttendConfigService;
	}

	@Override
	protected ISysAttendStatDetailService getServiceImp(HttpServletRequest request) {
		if (sysAttendStatDetailService == null) {
			sysAttendStatDetailService = (ISysAttendStatDetailService) getBean("sysAttendStatDetailService");
		}
		return sysAttendStatDetailService;
	}

	protected ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	public ISysAttendStatService getSysAttendStatService() {
		if (sysAttendStatService == null) {
			sysAttendStatService = (ISysAttendStatService) getBean("sysAttendStatService");
		}
		return sysAttendStatService;
	}
	public ISysAttendHisCategoryService getSysAttendHisCategoryService() {
		if (sysAttendHisCategoryService == null) {
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}
	private ISysAttendReportLogService sysAttendReportLogService;
	public ISysAttendReportLogService getSysAttendReportLogService() {
		if (sysAttendReportLogService == null) {
			sysAttendReportLogService = (ISysAttendReportLogService) getBean("sysAttendReportLogService");
		}
		return sysAttendReportLogService;
	}


	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendStatDetail.class);
	}

	public ActionForward listDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("listDetailStatus", "processing");
		TimeCounter.logCurrentTime("Action-listDetail", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			changeListDetailHQLInfo(request, hqlInfo);
			Page page = getSysAttendStatService().findPage(hqlInfo);
			Map<String,Object> resultMap = getServiceImp(request).formatStatDetail(page.getList());
			List<SysAttendStat> list = (List) resultMap.get("list");
//			for(int i=0;i<list.size();i++){
//				String whereBlock="sysAttendHisCategory.";
//				SysAttendHisCategory sysAttendHisCategory=(SysAttendHisCategory) getSysAttendHisCategoryService().findByPrimaryKey(list.get(i).getFdCategoryId());
//				String sql1 = "select fd_start_time from sys_attend_category_worktime where fd_is_available=1 and fd_category_id='"+sysAttendHisCategory.getFdCategoryId()+"'";
//				String startTime = HrCurrencyParams.getStringBySql(sql1, "fd_start_time");
//				String a = startTime.split(" ")[1];
//				String b = startTime.split(" ")[0];
//				list.get(i).setStartTime(startTime.split(" ")[1].split("\\.")[0].split(":")[0]+":"+startTime.split(" ")[1].split("\\.")[0].split(":")[1]);
//				String sql2 = "select fd_end_time from sys_attend_category_worktime where fd_is_available=1 and fd_category_id='"+sysAttendHisCategory.getFdCategoryId()+"'";
//				String endTime = HrCurrencyParams.getStringBySql(sql2, "fd_end_time");
//				list.get(i).setEndTime(endTime.split(" ")[1].split("\\.")[0].split(":")[0]+":"+endTime.split(" ")[1].split("\\.")[0].split(":")[1]);
//			}
			for(int i=0;i<list.size();i++){
				String startTime = null;
				String endTime = null;
				String whereBlock="sysAttendHisCategory.";
				String sql1 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1 and doc_create_time="
								+ "(select max(doc_create_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1)"
								+ "union select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=0";
				String sql11 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1";
				String sql111 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0"
								+ " union select min(fd_base_work_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0";
				String sql1115 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1 and doc_create_time=(select max(doc_create_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1) "
								+ " union select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0  and fd_work_type=0 union "
								+ "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1 and fd_work_type=0 union"
						+" select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1 and fd_work_type=1 and doc_create_time=(select max(doc_create_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1 and fd_work_type=1)";
//				String sql1115 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
//						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 union "
//								+ "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
//						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1";
				String sql2 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_work_type=0";
				String sql3 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+list.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_work_type=1";
				String sql333 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(list.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+list.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0";
				List list1 = HrCurrencyParams.getListBySql(sql1);
//				System.out.println(sql1);
//				int ddd;
//				ddd=32+3;
				List list11 = HrCurrencyParams.getListBySql(sql1115);
				if(list11.size()==1){
					List list3 = HrCurrencyParams.getListBySql(sql2);
					if(list3.size()==1)
					if((""+list3.get(0)).contains("T"))
					startTime=(""+list3.get(0)).split("T")[1].split("}")[0];
					else
						startTime=(""+list3.get(0)).split(" ")[1].split("\\.")[0];

					List list4 = HrCurrencyParams.getListBySql(sql3);
					if(list4.size()==1)
					if((""+list4.get(0)).contains("T"))
						endTime=(""+list4.get(0)).split("T")[1].split("}")[0];
					else
						endTime=(""+list4.get(0)).split(" ")[1].split("\\.")[0];
				}
				List list13 = HrCurrencyParams.getListBySql(sql11);
				boolean flag = true;
				if(list13.size()==1){
					flag=false;
					list1 = HrCurrencyParams.getListBySql(sql1115);
				}
				
				List list221 = HrCurrencyParams.getListBySql(sql333);
				if(list1.size()==1 && list221!=null&&list221.size()!=0){
					list1 = HrCurrencyParams.getListBySql(sql111);
				}
				if(flag){
				if(list1.size()==2&&(""+list1.get(0)).contains("T")){
					if(!(""+list1.get(0)).equals("")&&!(""+list1.get(1)).equals("")&&Integer.parseInt((""+list1.get(0)).split("T")[1].split(":")[0])>Integer.parseInt((""+list1.get(1)).split("T")[1].split(":")[0])){
						startTime=(""+list1.get(1)).split("T")[1].split("}")[0];
						endTime=(""+list1.get(0)).split("T")[1].split("}")[0];
					}else{
						endTime=(""+list1.get(1)).split("T")[1].split("}")[0];
						startTime=(""+list1.get(0)).split("T")[1].split("}")[0];
					}
				}else if(list1.size()==2){
					if(!(""+list1.get(0)).equals("")&&!(""+list1.get(1)).equals("")&&Integer.parseInt((""+list1.get(0)).split(" ")[1].split(":")[0])>Integer.parseInt((""+list1.get(1)).split(" ")[1].split(":")[0])){
						startTime=(""+list1.get(1)).split(" ")[1].split("\\.")[0];
						endTime=(""+list1.get(0)).split(" ")[1].split("\\.")[0];
					}else{
						endTime=(""+list1.get(1)).split(" ")[1].split("\\.")[0];
						startTime=(""+list1.get(0)).split(" ")[1].split("\\.")[0];
					}
				}  
				}else{
					if(list1.size()==2&&(""+list1.get(0)).contains("T")){
						if(!(""+list1.get(0)).equals("")&&!(""+list1.get(1)).equals("")&&Integer.parseInt((""+list1.get(0)).split("T")[1].split(":")[0])>Integer.parseInt((""+list1.get(1)).split("T")[1].split(":")[0])){
							startTime=(""+list1.get(0)).split("T")[1].split("}")[0];
							endTime="次日"+(""+list1.get(1)).split("T")[1].split("}")[0];
						}else{
							endTime="次日"+(""+list1.get(0)).split("T")[1].split("}")[0];
							startTime=(""+list1.get(1)).split("T")[1].split("}")[0];
						}
					}else if(list1.size()==2){
						if(!(""+list1.get(0)).equals("")&&!(""+list1.get(1)).equals("")&&Integer.parseInt((""+list1.get(0)).split(" ")[1].split(":")[0])>Integer.parseInt((""+list1.get(1)).split(" ")[1].split(":")[0])){
							startTime=(""+list1.get(0)).split(" ")[1].split("\\.")[0];
							endTime="次日"+(""+list1.get(1)).split(" ")[1].split("\\.")[0];
						}else{
							endTime="次日"+(""+list1.get(0)).split(" ")[1].split("\\.")[0];
							startTime=(""+list1.get(1)).split(" ")[1].split("\\.")[0];
						}
					}  

				}
				try{
				list.get(i).setStartTime(startTime.split(":")[0]+":"+startTime.split(":")[1]);	
				list.get(i).setEndTime(endTime.split(":")[0]+":"+endTime.split(":")[1]);
				}catch(Exception e){
					e.printStackTrace();
				}
//				String a = startTime.split(" ")[1];
//				String b = startTime.split(" ")[0];
//				list.get(i).setStartTime(startTime.split(" ")[1].split("\\.")[0].split(":")[0]+":"+startTime.split(" ")[1].split("\\.")[0].split(":")[1]);
//				String sql2 = "select fd_end_time from sys_attend_category_worktime where fd_is_available=1 and fd_category_id='"+sysAttendHisCategory.getFdCategoryId()+"'";
//				String endTime = HrCurrencyParams.getStringBySql(sql2, "fd_end_time");
//				list.get(i).setEndTime(endTime.split(" ")[1].split("\\.")[0].split(":")[0]+":"+endTime.split(" ")[1].split("\\.")[0].split(":")[1]);
			}
			page.setList(list);

			Map<String, List<List<JSONObject>>>  worksMap = (Map<String, List<List<JSONObject>>>) resultMap.get("worksMap");
			request.setAttribute("worksMap",  worksMap);
			// 添加日志信息
			UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
			// 班制数
			request.setAttribute("workTimeCount", AttendUtil.getWorkTimeCount(worksMap));
			request.setAttribute("queryPage", page);
			// 用于展示考勤组名
			//		request.setAttribute("categoryMap", getSysAttendCategoryService().getCategoryMap());
			// 有权限操作的考勤组id
			List authCateIds = new ArrayList();
			authCateIds.addAll(getSysAttendCategoryService().findCategorysByLeader(UserUtil.getUser(), 1));
			authCateIds.addAll(getSysAttendCategoryService().findCateIdsByEditorId(UserUtil.getUser().getFdId(), 1));
			request.setAttribute("authCateIds", authCateIds);
			request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}
		request.getSession().removeAttribute("listDetailStatus");
		TimeCounter.logCurrentTime("Action-listDetail", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listDetail", mapping, form, request, response);
		}

	}

	public ActionForward exportDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportDetail", true, getClass());
		KmssMessages messages = new KmssMessages();
		//记录
		HQLInfo hqlInfo = new HQLInfo();
		changeListDetailHQLInfo(request, hqlInfo);
		// findList不做权限过滤，这里手动处理
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		}
		getSysAttendReportLogService().addSyncStatDetailDownReport(hqlInfo,request);
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		TimeCounter.logCurrentTime("Action-exportDetail", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			json.put("data", "success");

		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward updateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			String fdStatus = request.getParameter("fdStatus");
			if (StringUtil.isNull(fdId) || StringUtil.isNull(fdStatus)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).updateStatus(fdId, fdStatus);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward listAttendRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		String fdType = request.getParameter("fdType");
		String fdMonth = request.getParameter("fdMonth");
		String docCreatorId = request.getParameter("docCreatorId");
		try {
			Integer _fdType = Integer.valueOf(fdType);

			fdMonth = StringUtil.isNull(fdMonth) ? new Date().getTime() + "" : fdMonth;
			Date date = new Date(Long.valueOf(fdMonth));
			Date fdStartTime = AttendUtil.getMonth(date, 0);
			Date fdEndTime = AttendUtil.getMonth(date, 1);

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setRowSize(100);
			StringBuffer where = new StringBuffer();
			where.append(" sysAttendStatDetail.fdDate>=:fdStartTime and sysAttendStatDetail.fdDate <:fdEndTime ")
					.append(" and sysAttendStatDetail.docCreator.fdId=:docCreatorId");
			hqlInfo.setParameter("fdStartTime", fdStartTime);
			hqlInfo.setParameter("fdEndTime", fdEndTime);
			hqlInfo.setParameter("docCreatorId", docCreatorId);

			setFdTypeHql(where, hqlInfo, _fdType);
			hqlInfo.setWhereBlock(where.toString());
			List list = getServiceImp(null).findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
			JSONArray array = getServiceImp(null).renderAttendRecord(list, _fdType);
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	private void setFdTypeHql(StringBuffer sb, HQLInfo hqlInfo, int fdType) {
		if (fdType == 0 || fdType == 2 || fdType == 3) {
			sb.append(" and (sysAttendStatDetail.docStatus=:docStatus or sysAttendStatDetail.docStatus2=:docStatus2 or "
					+ "sysAttendStatDetail.docStatus3=:docStatus3 or sysAttendStatDetail.docStatus4=:docStatus4)");
			hqlInfo.setParameter("docStatus", fdType);
			hqlInfo.setParameter("docStatus2", fdType);
			hqlInfo.setParameter("docStatus3", fdType);
			hqlInfo.setParameter("docStatus4", fdType);
		} else if (fdType == 4) {
			sb.append(" and (sysAttendStatDetail.fdOutside=:fdOutside or sysAttendStatDetail.fdOutside2=:fdOutside2 or "
					+ "sysAttendStatDetail.fdOutside3=:fdOutside3 or sysAttendStatDetail.fdOutside4=:fdOutside4)");
			hqlInfo.setParameter("fdOutside", true);
			hqlInfo.setParameter("fdOutside2", true);
			hqlInfo.setParameter("fdOutside3", true);
			hqlInfo.setParameter("fdOutside4", true);
		}

	}

	private void changeListDetailHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		if (isReserve) {
			orderby += " desc";
		}
		String s_pageno = request.getParameter("pageno");
		String s_rowsize = request.getParameter("rowsize");
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);

		StringBuffer sb = new StringBuffer(" 1=1 ");
		// 开始时间
		String fdStartTime = request.getParameter("fdStartTime");
		// AI接口新增
		String ai = request.getParameter("ai");
		String aiToday = null;
		if (StringUtil.isNotNull(ai) && "true".equals(ai)) {
			aiToday = DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale());
		}
		if (StringUtil.isNotNull(ai) && "true".equals(ai) && StringUtil.isNull(fdStartTime)) {
			fdStartTime = aiToday;
		}
		Date startTime =null;
		if (StringUtil.isNotNull(fdStartTime)) {
			startTime = DateUtil.convertStringToDate(fdStartTime, DateUtil.TYPE_DATETIME, request.getLocale());
			sb.append(" and sysAttendStat.fdDate>=:fdStartTime");
			hqlInfo.setParameter("fdStartTime", startTime);
		}

		// 结束时间
		String fdEndTime = request.getParameter("fdEndTime");
		// AI接口新增
		if (StringUtil.isNotNull(ai) && "true".equals(ai) && StringUtil.isNull(fdEndTime)) {
			fdEndTime = aiToday;
		}

		if (StringUtil.isNotNull(fdEndTime)) {
			Date endTime = DateUtil.convertStringToDate(fdEndTime, DateUtil.TYPE_DATETIME, request.getLocale());
			Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
			cal.add(Calendar.DATE, 1);
			sb.append(" and sysAttendStat.fdDate<:fdEndTime");
			hqlInfo.setParameter("fdEndTime", cal.getTime());
		}

		// 查询对象
		String fdTargetType = request.getParameter("fdTargetType");
		String fdTargetId = request.getParameter("fdTargetId");
		String fdCategoryIds = request.getParameter("fdCategoryIds");
		
		// 按部门查询
		if (StringUtil.isNull(fdTargetType) || "1".equals(fdTargetType)) {
			// AI接口新增
			if (StringUtil.isNotNull(ai) && "true".equals(ai) && StringUtil.isNull(fdTargetId)) {
				fdTargetId = UserUtil.getUser().getFdId();
			}
			if (StringUtil.isNotNull(fdTargetId)) {
				List<String> targetIds = ArrayUtil.convertArrayToList(fdTargetId.split(";"));
				//ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
				List<String> personIds = AttendPersonUtil.expandToPersonIds(targetIds);
				if (!personIds.isEmpty()) {
					sb.append(" and (" + HQLUtil.buildLogicIN("sysAttendStat.docCreator.fdId", personIds));
					sb.append(" or (" + AttendUtil.buildLikeHql("sysAttendStat.docCreatorHId", targetIds)
							+ " and sysAttendStat.docCreator.fdIsAvailable=:fdIsAvailable))");// 某个部门下的离职人员
					hqlInfo.setParameter("fdIsAvailable", false);
				} else {
					sb.append(" and sysAttendStat.docCreator.fdId='-1' ");// 不存在记录
				}
			}
			// 按考勤组查询
		} else if ("2".equals(fdTargetType)) {
			if (StringUtil.isNotNull(fdCategoryIds)) {
				List<String> categoryIds = ArrayUtil.convertArrayToList(fdCategoryIds.split(";"));
				if (!categoryIds.isEmpty()) {
					List<String> tempList =new ArrayList<>();
					tempList.addAll(CategoryUtil.getAllCategorys(categoryIds));
					sb.append(" and " + HQLUtil.buildLogicIN("sysAttendStat.fdCategoryId", tempList));
				} else {
					sb.append(" and sysAttendStat.fdCategoryId='-1'");
				}
			}
		}

		// 状态
		String fdStatus = request.getParameter("fdStatus");
		if (StringUtil.isNotNull(fdStatus)) {
			setFdstatusHql(hqlInfo, sb, fdStatus);
		}
		// 是否离职
		String fdIsQuit = request.getParameter("fdIsQuit");
		if (!"true".equals(fdIsQuit)) {
			sb.append(
					" and sysAttendStat.docCreator.fdIsAvailable=:isAvailable");
			hqlInfo.setParameter("isAvailable", true);
			sb.append(
					" and (sysAttendStat.docCreator.fdLeaveDate is null or sysAttendStat.docCreator.fdLeaveDate>=:fdLeaveDate)");
			hqlInfo.setParameter("fdLeaveDate", new Date());
		}
		// 业务相关
		sb.append(" and sysAttendStat.docCreator.fdIsBusiness=:isBusiness");
		hqlInfo.setParameter("isBusiness", true);
		
		SysAttendConfig config = getSysAttendConfigService(request).getSysAttendConfig();
		if (config != null) { 
			String fdExcTargetIdStr = config.getFdExcTargetIds();
			// 系统配置的不参与考勤人员
			if (StringUtil.isNotNull(fdExcTargetIdStr)) {
				List<String> fdExcTargetIds = new ArrayList<String>();
				fdExcTargetIds.addAll(ArrayUtil.convertArrayToList(fdExcTargetIdStr.split(";"))); 
				if (!ArrayUtil.isEmpty(fdExcTargetIds)) {
					sb.append(
							" and sysAttendStat.docCreator.fdId not in('")
							.append(StringUtil.join(fdExcTargetIds, "','"))
							.append("')");
				}
			}
		} 
		//过滤考勤组的排除人员 
		//sb.append(" and NOT EXISTS (select 1 from SysAttendCategory sysAttendCategory where sysAttendStat.fdCategoryId=sysAttendCategory.fdId  and sysAttendCategory.fdExcTargets.fdId in (sysAttendStat.docCreator.fdId) )");
		
		hqlInfo.setWhereBlock(sb.toString());
		if (StringUtil.isNull(orderby)) {
			hqlInfo.setOrderBy("sysAttendStat.fdDate desc, sysAttendStat.docCreator.fdNamePinYin asc");
		} else {
			if (orderby.indexOf("fdDate") > -1) {
				orderby = orderby.replace("fdDate", "sysAttendStat.fdDate");
			}
			hqlInfo.setOrderBy(orderby);
		}
	}



	private void setFdstatusHql(HQLInfo hqlInfo, StringBuffer sb, String fdStatus) {
		sb.append(" and  ( 1=2 ");
		if (fdStatus.contains("0")) {// 缺卡
			sb.append(" or sysAttendStat.fdMissed=:fdMissed ");
			hqlInfo.setParameter("fdMissed", true);
		}
		if (fdStatus.contains("1")) {// 正常
			sb.append(" or sysAttendStat.fdStatus=:fdStatus");
			hqlInfo.setParameter("fdStatus", true);
		}
		if (fdStatus.contains("2")) {// 迟到
			sb.append(" or sysAttendStat.fdLate=:fdLate");
			hqlInfo.setParameter("fdLate", true);
		}
		if (fdStatus.contains("3")) {// 早退
			sb.append(" or sysAttendStat.fdLeft=:fdLeft");
			hqlInfo.setParameter("fdLeft", true);
		}
		if (fdStatus.contains("4")) {// 出差
			sb.append(" or sysAttendStat.fdTripDays > 0");
		}
		if (fdStatus.contains("5")) {// 请假
			sb.append(
					" or (sysAttendStat.fdOffDays > 0 or sysAttendStat.fdOffTime > 0 or sysAttendStat.fdOffTimeHour > 0)");
		}
		if (fdStatus.contains("6")) {// 外勤
			sb.append(" or sysAttendStat.fdOutside=:fdOutside");
			hqlInfo.setParameter("fdOutside", true);
		}
		if (fdStatus.contains("7")) {// 旷工
			sb.append(
					" or (sysAttendStat.fdAbsent=:fdAbsent or sysAttendStat.fdAbsentDays > 0)");
			hqlInfo.setParameter("fdAbsent", true);
		}
		if (fdStatus.contains("8")) {// 加班
			sb.append(" or sysAttendStat.fdOverTime>0");
		}
		if (fdStatus.contains("9")) {// 外出
			sb.append(" or sysAttendStat.fdOutgoingTime>0");
		}
		sb.append(" ) ");
	}

}
