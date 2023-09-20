package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.component.dbop.ds.DBPoolManager;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.component.dbop.service.ICompDbcpService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.attend.model.*;
import com.landray.kmss.sys.attend.service.*;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveConfig;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 月统计报表业务接口实现
 * 
 * @author
 * @version 1.0 2017-07-27
 */

public class SysAttendReportServiceImp extends BaseServiceImp
		implements ISysAttendReportService, ApplicationContextAware {

	private ISysAttendStatMonthService sysAttendStatMonthService;

	private ISysOrgCoreService sysOrgCoreService; 

	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendOrgService sysAttendOrgService;

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private ISysAttendReportMonthService sysAttendReportMonthService;

	private ISysAttendStatService sysAttendStatService;

	private ISysAttendStatDetailService sysAttendStatDetailService;

	private ISysAttendMainService sysAttendMainService;

	private ISysAttendStatPeriodService sysAttendStatPeriodService;

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	private ISysAttendConfigService sysAttendConfigService;

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

	private ISysTimeCountService sysTimeCountService;
	public void
	setSysTimeCountService(ISysTimeCountService sysTimeCountService) {
	this.sysTimeCountService = sysTimeCountService;
	}
	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;
	private static String  fdMonth;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendReportServiceImp.class);

	private static final String SUFFIX = "_count";
	private static final String TYPE = "_type";
	private ISysTimeHolidayService sysTimeHolidayService;

	public ISysTimeHolidayService getSysTimeHolidayService() {
		if (sysTimeHolidayService == null) {
			sysTimeHolidayService = (ISysTimeHolidayService) SpringBeanUtil.getBean(
					"sysTimeHolidayService");
		}
		return sysTimeHolidayService;
	}
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	private ISysAttendSynDingService sysAttendSynDingService;
	public void setSysAttendSynDingService(ISysAttendSynDingService sysAttendSynDingService) {
		this.sysAttendSynDingService = sysAttendSynDingService;
	}

	private void publishAttendEvent(String appName, net.sf.json.JSONArray jsonArray,String operatorType,SysAttendImportLog sysAttendImportLog) {
		// 发送事件通知
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("datas", jsonArray);
		params.put("operatorType", operatorType);
		params.put("sysAttendImportLog", sysAttendImportLog);
		applicationContext.publishEvent(new Event_Common(
				"importOriginAttendMain", params));
	}
	private static JSONObject offDetailJson1 ;
	public ISysAttendStatService getSysAttendStatService() {
		if (sysAttendStatService == null) {
			sysAttendStatService = (ISysAttendStatService) SpringBeanUtil.getBean("sysAttendStatService");
		}
		return sysAttendStatService;
	}
	protected ICompDbcpService compDbcpService;

	protected ICompDbcpService getCompDbcpService() {
		if (compDbcpService == null) {
            compDbcpService = (ICompDbcpService) SpringBeanUtil.getBean("compDbcpService");
        }
		return compDbcpService;
	}
	 private ISysOrgElementService sysOrgElementService;

	private Map<String, List> stataicStatMap;

	    public ISysOrgElementService getSysOrgElementService() {
	        if (sysOrgElementService == null) {
	            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
	        }
	        return sysOrgElementService;
	    }
	@Override
	public Page statList(RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
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
		changeListStatHqlInfo(request, hqlInfo);
		if (StringUtil.isNotNull(orderby)) {
			orderby = "sysAttendStatMonth." + orderby;
			hqlInfo.setOrderBy(orderby);
		}

		Page page = sysAttendStatMonthService.findPage(hqlInfo);
		// 用于展示考勤组名
		/*request.setAttribute("categoryMap",
				sysAttendCategoryService.getCategoryMap());*/
		page.setList(formatStatMonth(page.getList()));
		
		// 每日明细数据
		genDateDetailList(page.getList(), request);
		List<SysTimeHoliday> list = new ArrayList<SysTimeHoliday>();
		List<String> idList=new ArrayList<String>();
		for (Object monthModel : page.getList()) {
			SysOrgPerson docCreator = (SysOrgPerson) PropertyUtils
					.getProperty(monthModel, "docCreator");
			if(docCreator!=null) {
				idList.add(docCreator.getFdId());
				SysTimeArea exchangeTimeArea = this.sysTimeCountService.getTimeArea(docCreator);
				if(exchangeTimeArea!=null)
				list.add(exchangeTimeArea.getFdHoliday());
			}

			String fdCategoryId = (String) PropertyUtils.getProperty(monthModel,"fdCategoryId");
			SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
			if(list!=null&&!list.isEmpty()&&list.get(0)!=null)
			list.add(sysAttendCategory.getFdHoliday());
			if(StringUtil.isNotNull(fdCategoryId)){
				SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(fdCategoryId);
				if(hisCategory !=null){
					PropertyUtils.setProperty(monthModel,"fdCategoryName",hisCategory.getFdName());
				}
			}

		}
		fdMonth = request.getParameter("fdMonth");
		request.setAttribute("personInfoMap",
				getAllPersonInfo(idList,list));
		return page;
	}
	@Override
	public  JSONObject syncAttendDatabase(RequestContext request) throws Exception{
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		Date date = new Date();
		Date beginDate = DateUtil.convertStringToDate(begin);
		Date endDate = DateUtil.convertStringToDate(end);
		int beforeNum1 = (int) ((date.getTime()-beginDate.getTime())/(24*60*60*1000));
		int beforeNum2 = (int) ((date.getTime()-endDate.getTime())/(24*60*60*1000));
		System.out.println(111111);
		JSONObject resp = new JSONObject();
		ArrayList all = new ArrayList();
		for(int r=beforeNum2;r<=beforeNum1;r++){
		Connection conn = null;
		String dataSource = "考勤机";//数据源名称
		if (!DBPoolManager.isInDBPool(dataSource)) {
		   CompDbcp dbcp = (CompDbcp) getCompDbcpService().getCompDbcpByName(dataSource);
		   Map map = new HashMap();
		       map.put("driver", dbcp.getFdDriver());
		       map.put("url", dbcp.getFdUrl());
		       map.put("user", dbcp.getFdUsername());
		       map.put("password", dbcp.getFdPassword());
		       map.put("alias", dataSource);
		       DBPoolManager.registerPool(map);
		}
		conn = DBPoolManager.getConnection(dataSource);
		Connection conn2 = null;
		String dataSource2 = "考勤机1";//数据源名称
		if (!DBPoolManager.isInDBPool(dataSource2)) {
		   CompDbcp dbcp2 = (CompDbcp) getCompDbcpService().getCompDbcpByName(dataSource2);
		   Map map = new HashMap();
		       map.put("driver", dbcp2.getFdDriver());
		       map.put("url", dbcp2.getFdUrl());
		       map.put("user", dbcp2.getFdUsername());
		       map.put("password", dbcp2.getFdPassword());
		       map.put("alias", dataSource2);
		       DBPoolManager.registerPool(map);
		}
		conn2 = DBPoolManager.getConnection(dataSource2);
		Connection conn1 = null;
		String dataSource1 = "ekp";//数据源名称
		if (!DBPoolManager.isInDBPool(dataSource1)) {
		   CompDbcp dbcp1 = (CompDbcp) getCompDbcpService().getCompDbcpByName(dataSource1);
		   Map map = new HashMap();
		       map.put("driver", dbcp1.getFdDriver());
		       map.put("url", dbcp1.getFdUrl());
		       map.put("user", dbcp1.getFdUsername());
		       map.put("password", dbcp1.getFdPassword());
		       map.put("alias", dataSource1);
		       DBPoolManager.registerPool(map);
		}
		conn1 = DBPoolManager.getConnection(dataSource1);
			Statement stmt = conn.createStatement();
			Statement stmt6 = conn.createStatement();

			Statement stmt1 = conn1.createStatement();

			Statement stmt11 = conn1.createStatement();
			Statement stmt2 = conn2.createStatement();
			String sql22 = "select fd_hierarchy_id from sys_org_element where fd_id=?";
//			String sql = "select * from Work_Source where cID in (select max(cID) from Work_Source  where Source_Date > '2023-02-01' group by cCardNO,Date,Source_Data)";
			String sql = "select * from Work_Source where cID in (select max(cID) from Work_Source  where DATEDIFF(day,Source_Date,GETDATE())="+r +" group by cCardNO,Date,Source_Data)";
//			String sql6 = "select recid,phyid,skdate,sktime from dbo.dlc_record_kq where recid in (select max(recid) from dbo.dlc_record_kq  where skdate  > '2023-02-01' group by phyid,skdate,sktime)";
			String sql6 = "select recid,phyid,skdate,sktime from dbo.dlc_record_kq where recid in (select max(recid) from dbo.dlc_record_kq  where DATEDIFF(day,skdate,GETDATE())="+r+" group by phyid,skdate,sktime)";
//			String sql = "select * from Work_Day_Data where Work_Date>=dateadd(day,3,getdate())";
			String sql1 = "insert into Work_Source (cID,Source_Date,Per_ID,Source_Data,Define1,Define2,Define3,Modify_User,Modify_Time,IFLAG,cCardNO,iSouece) values("
					+ "?,?,?,?,?,?,?,?,?,?,?,?)";
//			String sql2 = "select  a.fd_id,b.Source_Date,b.Source_Data,b.cCardNo from hr_staff_person_info a,(select distinct Source_Date,Source_Data,cCardNo from Work_Source where cID not in (select cID from sys_attend_flag)) b where b.cCardNO=a.fd_time_cardNo";
//			String sql5 = "select a.fd_id,b.Source_Date,b.Source_Data,b.cCardNo from hr_staff_person_info a,Work_Source b where b.cCardNO=a.fd_time_cardNo and 1=1";
			String sql4 = "select fd_id from sys_org_element where fd_no=?";
			String sql3 = "insert into sys_attend_syn_ding (fd_person_id)values(?)";
//			String sql7 = "delete from Work_Source";
//			String sql8 = "select a.fd_person_id,a.fd_user_check_time,b.fd_time_cardNo from sys_attend_syn_ding a left join hr_staff_person_info b on a.fd_person_id=b.fd_id where doc_create_time > '2023-02-01'";
			String sql8 = "select a.fd_person_id,a.fd_user_check_time,b.fd_time_cardNo from sys_attend_syn_ding a left join hr_staff_person_info b on a.fd_person_id=b.fd_id where TO_DAYS(doc_create_time) = TO_DAYS(NOW())-"+r;
//			String sql9 = "select cID from Work_Source where Source_Date=? and Source_Data=? and cCardNo=?";
//			String sql11 = "insert into sys_attend_flag (cID)values(?)";
			String sql12 = "select  fd_id from hr_staff_person_info where fd_time_cardNo=?";
			String sql23 = "select a.fd_category_id,b.fd_hierarchy_id from sys_attend_category_target a left join sys_org_element b on a.fd_org_id=b.fd_id";
//			String sql34 = "select fd_id from sys_attend_his_category where fd_category_id=?";
//			String sql341 = "select fd_id from sys_attend_category_worktime where fd_category_id=?";
//			String sql78 = "insert into sys_attend_main(fd_id,doc_creator_hid,doc_create_time,fd_category_his_id,fd_app_name,fd_status,fd_work_type,fd_work_id,doc_creator_id)values(?,?,?,?,?,?,?,?,?)";
			ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs2 = stmt2.executeQuery(sql6);
//			PreparedStatement pstmt = conn1.prepareStatement(sql1);
//			PreparedStatement pstmt1 = conn1.prepareStatement(sql4);
//			PreparedStatement pstmt2 = conn1.prepareStatement(sql3);
			PreparedStatement pstmt12 = conn1.prepareStatement(sql22);
			Statement stmt8 = conn1.createStatement();
//			PreparedStatement pstmt112 = conn1.prepareStatement(sql34);
//			PreparedStatement pstmt1121 = conn1.prepareStatement(sql341);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

			while(rs.next()){
//				boolean flag = false;
				Statement stmt4 = conn1.createStatement();
				ResultSet rs6= stmt4.executeQuery(sql8);
//				boolean isInsert = true;
				String [] split = rs.getString(4).split(":");
				int time=60*60*Integer.parseInt(split[0])+60*Integer.parseInt(split[1])+Integer.parseInt(split[2]);
				int maxTime = 0;
				int maxTime1 = Integer.MAX_VALUE;
				boolean flag=false;
				int m=0;
				int n=0;
//				ResultSet rs1 = stmt6.executeQuery(sql);
//				while(rs1.next()){
//					if(rs.getString(12).equals(rs1.getString(12)))
//					{
//						String [] split1 = rs1.getString(4).split(":");
//						int time1=60*60*Integer.parseInt(split1[0])+60*Integer.parseInt(split1[1])+Integer.parseInt(split1[2]);
//						if(time1<time){
//							if(time-time1>3*60*60)
//								continue;
//							if(maxTime<time1){
//								m++;
//								maxTime=time1;
//							}else{
//								m++;
//							}
//						}
//						if(time1>time){
//							
//							if(time-time1>3*60*60)
//								continue;
//							if(maxTime1>time1){
//								n++;
//								m++;
//								maxTime1=time1;
//							}else{
//								n++;
//								m++;
//							}
//						}
//						else{
//							if(time1<time){
//								if(time-time1>4*60*60);
//								else  if(maxTime<time1){
//									n++;
//									m++;
//									maxTime=time1;
//								}else{
//									n++;
//									m++;
//								}
//							}
//							if(time1>time){
//								
//								if(time-time1>4*60*60);
//								else  if(maxTime<time1){
//									m++;
//									maxTime1=time1;
//								}else{
//									m++;
//								}
//							}
//						}
						
//					}
//				}
//				if(m!=n){
//					if(time<60*60*12)
//					if(time-maxTime<20*60)continue;
//					if(time>60*60*12)
//					if(maxTime1-time<20*60)continue;
//				}
				while(rs6.next()){
					if(rs.getString(12).equals(rs6.getString(3)))
					{
						String [] split1 = (""+rs6.getTime(2)).split(":");
						int time1=60*60*Integer.parseInt(split1[0])+60*Integer.parseInt(split1[1])+Integer.parseInt(split1[2]);
						if(time1==time){
							flag=true;
							break;
						}
//						if(time1<time ){
//							if(time-time1>4*60*60);
//							else if(maxTime1>time1){
//								maxTime=time1;
//							}
//						}
//						if(time1>time){
//							if(time1-time>4*60*60);
//							else if(maxTime1>time1){
//								maxTime1=time1;
//							}
//						}
						
					}
				}
				stmt4.close();
				if(flag)continue;
					PreparedStatement pstmt6 = conn1.prepareStatement(sql12);
					pstmt6.setString(1, rs.getString(12));
					
					ResultSet rs15 = pstmt6.executeQuery();
					String fdId=null;
					while(rs15.next()){
						fdId = rs15.getString(1);
					}
					pstmt6.close();
					if(fdId==null)
						continue;

					ResultSet rs12 = stmt11.executeQuery(sql23);
					String groupId=null;
					pstmt12.setString(1, fdId);
					
					ResultSet rs115 = pstmt12.executeQuery();
					String fdHierachyId=null;
					while(rs115.next()){
						fdHierachyId = rs115.getString(1);
					}
					String fdContentId=null;
					String fdWorkTimeId=null;
					while(rs12.next()){
						if(fdHierachyId.contains(rs12.getString(2)))
							groupId=rs12.getString(1);
					}
					if(groupId!=null){
//					pstmt112.setString(1, groupId);
//					
//					ResultSet rs25 = pstmt112.executeQuery();
//					while(rs25.next()){
//						fdContentId = rs25.getString(1);
//					}
//					pstmt1121.setString(1, groupId);
//					
//					ResultSet rs251 = pstmt1121.executeQuery();
//					while(rs251.next()){
//						fdWorkTimeId = rs251.getString(1);
//					}
					}
					
	//				HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(fdId);
					SysAttendSynDing sysAttendSynDing = new SysAttendSynDing();
					if(fdContentId!=null)
						sysAttendSynDing.setFdGroupId(fdContentId);
					sysAttendSynDing.setFdPersonId(fdId);
					sysAttendSynDing.setDocCreator((SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdId));
					Date testTime = sdf.parse(sdf1.format( rs.getDate(2))+" "+ rs.getString(4));
					sysAttendSynDing.setFdUserCheckTime(testTime);
					sysAttendSynDing.setFdAppName("考勤机");
					sysAttendSynDing.setDocCreateTime(new Date(System.currentTimeMillis()));
//					sysAttendSynDingService.add(sysAttendSynDing);

					String fdId1 = IDGenerator.generateID();
					Object userSignDatas;
					Object category;
					Object userList;
					JSONObject obj= new JSONObject();
					JSONObject docCreatorObj= new JSONObject();
					docCreatorObj.put("Id",fdId);
					obj.put("docCreator", docCreatorObj);
					obj.put("createTime", sdf1.format( rs.getDate(2))+" "+ rs.getString(4));

					net.sf.json.JSONArray jsonArray=new net.sf.json.JSONArray();
					jsonArray.add(obj);
					SysAttendImportLog sysAttendImportLog = null;
					publishAttendEvent("考勤机",jsonArray,"import",sysAttendImportLog);
					 SysOrgElement sysOrgElement = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(fdId);
					resp.put(sysOrgElement.getFdName(), sdf1.format( rs.getDate(2))+" "+ rs.getString(4));
					
					sysAttendSynDingService.getBaseDao().getHibernateSession().flush();
//					pstmt78.setString(1, fdId1);
//					pstmt78.setString(2, 'x'+groupId+'x');
//					pstmt78.setTimestamp(3, Timestamp.valueOf((sdf1.format( rs.getDate(2))+" "+ rs.getString(4))));
//					pstmt78.setString(4, fdContentId);
//					pstmt78.setString(5, "考勤机");
//					pstmt78.setString(6, "1");
//					pstmt78.setString(7, "1");
//					pstmt78.setString(8, fdWorkTimeId);
//					pstmt78.setString(9, fdId);
//					pstmt78.executeUpdate();
			}
			while(rs2.next()){
				int maxTime1 = Integer.MAX_VALUE;
				int maxTime = 0;
				String [] split = (""+rs2.getTime(4)).split(":");
				int time=60*60*Integer.parseInt(split[0])+60*Integer.parseInt(split[1])+Integer.parseInt(split[2]);
				boolean flag1 = false;
				ResultSet rs16= stmt8.executeQuery(sql8);
				boolean flag=false;
				int m=0;
				int n=0;

//				Statement stmt7 = conn2.createStatement();
//				ResultSet rs3 = stmt7.executeQuery(sql6);
//				while(rs3.next()){
//					if(rs2.getString(2).equals(rs3.getString(2)))
//					{
//						String [] split1 = (""+rs3.getTime(4)).split(":");
//						int time1=60*60*Integer.parseInt(split1[0])+60*Integer.parseInt(split1[1])+Integer.parseInt(split1[2]);
//						if(time1<time){
//							if(time-time1>3*60*60)
//								continue;
//							if(maxTime<time1){
//								m++;
//								maxTime=time1;
//							}else{
//								m++;
//							}
//						}
//						if(time1>time){
//							if(time1-time>3*60*60)
//								continue;
//							if(maxTime1>time1){
//								m++;
//								n++;
//								maxTime1=time1;
//							}else{
//								m++;
//								n++;
//							}
//						}
//						else{
//							if(time1<time){
//								if(time-time1>4*60*60);
//								else  if(maxTime<time1){
//									m++;
//									n++;
//									maxTime=time1;
//								}else{
//									m++;
//									n++;
//								}
//							}
//							if(time1>time){
//								if(time1-time>4*60*60);
//								else if(maxTime1>time1){
//									m++;
//									maxTime1=time1;
//								}else{
//									m++;
//								}
//							}
//						}
						
//					}
//				}
//				stmt7.close();
//				if(m!=n){
//					if(time<60*60*12)
//					if(time-maxTime<20*60)continue;
//					if(time>60*60*12)
//					if(maxTime1-time<20*60)continue;
//				}
				while(rs16.next()){
					if(rs2.getString(2).equals(rs16.getString(3)))
					{
						String [] split1 = (""+rs16.getTime(2)).split(":");
						int time1=60*60*Integer.parseInt(split1[0])+60*Integer.parseInt(split1[1])+Integer.parseInt(split1[2]);
						if(time1==time){
							flag=true;
							break;
						}
//						if(time1<time){
//							if(time-time1>4*60*60);
//							else if(maxTime<time1){
//								
//								maxTime=time1;
//							}
//						}
//						if(time1>time){
//							if(time1-time>4*60*60);
//							else if(maxTime1>time1){
//								
//								maxTime1=time1;
//							}
//						}
//						
					}
				}
				if(flag)continue;
//				while(rs16.next()){
//					if((sdf1.format( rs2.getDate(3))+" "+ rs2.getTime(4)).equals(rs16.getDate(2)+" "+rs16.getTime(2)) && rs2.getString(2).equals(rs16.getString(3)))
//					{
//						flag1=true;
//						break;
//					}
//				}
//				if(flag1)continue;
//				Statement stmt16 = conn2.createStatement();
//				ResultSet rs18 = stmt16.executeQuery(sql6);
//				boolean isInsert1 = true;
//				String [] split2 = (""+rs2.getTime(4)).split(":");
//				int time3=60*60*Integer.parseInt(split2[0])+60*Integer.parseInt(split2[1])+Integer.parseInt(split2[2]);
//				int maxTime1 = 0;
//				while(rs18.next())
//				{
//					String [] split1 = (""+rs18.getTime(4)).split(":");
//					int time1=60*60*Integer.parseInt(split1[0])+60*Integer.parseInt(split1[1])+Integer.parseInt(split1[2]);
//					if(rs2.getString(2).equals(rs18.getString(2)) && time1<time3 ){
//						if(maxTime1<time1){
//							
//							maxTime1=time1;
//						}
//					}
//				}
//				if(maxTime1==0);
//				else if(time3-maxTime1<20*60){
//					isInsert1=false;
//				}
					PreparedStatement pstmt6 = conn1.prepareStatement(sql12);
					pstmt6.setString(1, rs2.getString(2));
					
					ResultSet rs15 = pstmt6.executeQuery();
					String fdId=null;
					while(rs15.next()){
						fdId = rs15.getString(1);
					}
					pstmt6.close();
					if(fdId==null)
						continue;
					String groupId=null;

					ResultSet rs12 = stmt11.executeQuery(sql23);
					
					pstmt12.setString(1, fdId);
					
					ResultSet rs115 = pstmt12.executeQuery();
					String fdHierachyId=null;
					while(rs115.next()){
						fdHierachyId = rs115.getString(1);
					}
					while(rs12.next()){
						if(fdHierachyId.contains(rs12.getString(2)))
							groupId=rs12.getString(1);
					}

					String fdContentId=null;
					String fdWorkTimeId=null;
	//				HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(fdId);
					if(groupId!=null){
//					pstmt112.setString(1, groupId);
//					
//					ResultSet rs25 = pstmt112.executeQuery();
//					while(rs25.next()){
//						fdContentId = rs25.getString(1);
//					}
//					pstmt1121.setString(1, groupId);
					
//					ResultSet rs251 = pstmt1121.executeQuery();
//					while(rs251.next()){
//						fdWorkTimeId = rs251.getString(1);
//					}
					}
					SysAttendSynDing sysAttendSynDing = new SysAttendSynDing();
					if(fdContentId!=null)
						sysAttendSynDing.setFdGroupId(fdContentId);
					sysAttendSynDing.setFdPersonId(fdId);
					sysAttendSynDing.setDocCreator((SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdId));
					Date testTime = sdf.parse(sdf1.format( rs2.getDate(3))+" "+ rs2.getTime(4));
					sysAttendSynDing.setFdUserCheckTime(testTime);
					sysAttendSynDing.setFdAppName("考勤机");
					sysAttendSynDing.setDocCreateTime(new Date(System.currentTimeMillis()));
//					sysAttendSynDingService.add(sysAttendSynDing);
					String fdId1 = IDGenerator.generateID();
					Object userSignDatas;
					Object category;
					Object userList;
					JSONObject obj= new JSONObject();
					JSONObject docCreatorObj= new JSONObject();
					docCreatorObj.put("Id",fdId);
					obj.put("docCreator", docCreatorObj);
					obj.put("createTime", sdf1.format( rs2.getDate(3))+" "+ rs2.getTime(4));

					net.sf.json.JSONArray jsonArray=new  net.sf.json.JSONArray();
					jsonArray.add(obj);
					SysAttendImportLog sysAttendImportLog = null;
					publishAttendEvent("考勤机",jsonArray,"import",sysAttendImportLog);
					 SysOrgElement sysOrgElement = (SysOrgElement)getSysOrgElementService().findByPrimaryKey(fdId);
					resp.put(sysOrgElement.getFdName(), sdf1.format( rs2.getDate(3))+" "+ rs2.getTime(4));
					sysAttendSynDingService.getBaseDao().getHibernateSession().flush();
//					pstmt78.setString(1, fdId1);
//					pstmt78.setString(2, 'x'+groupId+'x');
//					pstmt78.setTimestamp(3, Timestamp.valueOf((sdf1.format( rs.getDate(2))+" "+ rs.getString(4))));
//					pstmt78.setString(4, fdContentId);
//					pstmt78.setString(5, "考勤机");
//					pstmt78.setString(6, "1");
//					pstmt78.setString(7, "1");
//					pstmt78.setString(8,fdWorkTimeId);
//					pstmt78.setString(9, fdId);
////					pstmt78.executeUpdate();
			}
//				pstmt.setInt(1, rs.getInt(1));
//				pstmt.setDate(2, rs.getDate(2));
//				pstmt.setString(3, rs.getString(3));
//				pstmt.setString(4, rs.getString(4));
//				pstmt.setString(5, rs.getString(5));
//				pstmt.setString(6, rs.getString(6));
//				pstmt.setString(7, rs.getString(7));
//				pstmt.setString(8, rs.getString(8));
//				pstmt.setString(9, rs.getString(11));
//				pstmt.setString(10, rs.getString(13));
//				pstmt.setString(11, rs.getString(12));
//				pstmt.setString(12, rs.getString(14));
//				
//				try{
//					pstmt.executeUpdate();
//				}catch(Exception e){
//					
//				}
//				int id = rs.getInt(1);
//		        Date name = rs.getDate(2);
//		        String gender = rs.getString(3);
//		        System.out.println("id:"+id+" 姓名："+name+" 性别："+gender+','+rs.getString(4)+','+rs.getString(5)+','+rs.getString(6)+','+rs.getString(7)+','+rs.getString(8)+','+rs.getTimestamp(9)+','+rs.getString(10)+','+rs.getString(11)+','+rs.getString(12)+','+rs.getString(13)+','+rs.getInt(14));
			
//			ResultSet rs1 = stmt1.executeQuery(sql2);


//	        int m = 0;
//	        while (rs1.next()) {
//	        	m++;
//	        	String[] list1 = new String[10];
//
//	            	list1[3]=rs1.getString(3);
//	            	list1[4]=rs1.getString(4);
//	            	list1[1]=rs1.getString(1);
//	            	list1[2]=sdf1.format(rs1.getDate(2));
//	            	arr[m]=list1;
//
//	        }
//			while(rs1.next()){
//				boolean isInsert = true;
//				String [] split = rs1.getString(3).split(":");
//				int time=60*60*Integer.parseInt(split[0])+60*Integer.parseInt(split[1])+Integer.parseInt(split[2]);
//				int maxTime = 0;
//
//				Statement stmt2 = conn1.createStatement();
//				ResultSet rs2 = stmt2.executeQuery(sql2);
//				String str1 = null;
//				String str2 = null;
//				Date date1 = null;
//				str1=rs1.getString(3);
//				str2=rs1.getString(4);
//				date1=rs1.getDate(2);
//				while(rs2.next()){
//					String [] split1 = rs2.getString(3).split(":");
//					int time1=60*60*Integer.parseInt(split1[0])+60*Integer.parseInt(split1[1])+Integer.parseInt(split1[2]);
//					if(sdf1.format( rs1.getDate(2)).equals(sdf1.format( rs2.getDate(2))) && rs1.getString(4).equals(rs2.getString(4)) && time1<time){
//						if(maxTime<time1){
//							
//							maxTime=time1;
//						}
//					}
//				}
//					if(maxTime==0);
//					else if(time-maxTime<20*60){
//						PreparedStatement pstmt6 = conn1.prepareStatement(sql9);
//						PreparedStatement pstmt7 = conn1.prepareStatement(sql11);
//						pstmt6.setDate(1,(java.sql.Date) date1);
//						pstmt6.setString(2,str1);
//						pstmt6.setString(3, str2);
//						ResultSet rs15 = pstmt6.executeQuery();
//						String cID = null;
//						while(rs15.next()){
//							cID=rs15.getString(1);
//						}
//						pstmt7.setString(1, cID);
//						try{
//							pstmt7.executeUpdate();
//						}catch(Exception e){
//							
//						}
//						isInsert=false;
//					}
//				if(isInsert){
//					String fdId = rs1.getString(1);
//					if(fdId==null)
//						continue;
//	//				HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(fdId);
//					SysAttendSynDing sysAttendSynDing = new SysAttendSynDing();
//					sysAttendSynDing.setFdPersonId(fdId);
//					Date testTime = sdf.parse(sdf1.format( rs1.getDate(2))+" "+ rs1.getString(3));
//					sysAttendSynDing.setFdUserCheckTime(testTime);
//					sysAttendSynDing.setFdAppName("考勤机");
//					sysAttendSynDingService.add(sysAttendSynDing);
//	//				ResultSet rs3 = pstmt1.executeQuery();
//	//				if(rs3.getRow()==0)
//	//					continue;
//	//				else{
//	//					pstmt2.setString(1, rs3.getString(1));
//	//				personId = rs3.getString(1);
//	//				System.out.println("personId:"+personId);
//	//				}
//	//				try{
//	//					pstmt2.executeUpdate();
//	//				}catch(Exception e){
//	//					
//	//				}
//					}
//			}
//			stmt6.executeUpdate(sql7);
			stmt.close();
			stmt1.close();
			pstmt12.close();
			stmt8.close();
			stmt11.close();
			stmt2.close();
		conn.close();
		conn1.close();
		conn2.close();
		}
		return resp;
	}
	/**
	 * 格式化月统计数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatStatMonth(List list) {
		try {
			// 以下格式化请假天数细分的数据
			// 1.获取后台配置的请假分类
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysTimeLeaveRule.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			List<SysTimeLeaveRule> leaveList = sysTimeLeaveRuleService.findList(hqlInfo);
			if (leaveList.isEmpty()) {
				return list;
			}
			//请假名称的封装
			Map<String,String> leavelNameMap=new HashMap<String,String>();
			//假期类型，按小时 按天，按半天
			Map<String,Integer> leavelTypeMap=new HashMap<String,Integer>();
			for (SysTimeLeaveRule sysTimeLeaveRule : leaveList) {
				leavelNameMap.put(sysTimeLeaveRule.getFdSerialNo(),sysTimeLeaveRule.getFdName());
				leavelTypeMap.put(sysTimeLeaveRule.getFdSerialNo(),sysTimeLeaveRule.getFdStatType());
			}
//			SysTimeLeaveConfig leaveConfig = new SysTimeLeaveConfig();
			List rtnList = new ArrayList();
			// 2.拼装请假细分数据json
			for (Object statMonth : list) {
				String fdOffDaysDetail = (String) PropertyUtils.getProperty(statMonth,"fdOffDaysDetail");
				JSONObject countJson = new JSONObject();
				//假期明细汇总的默认值
				for (Map.Entry leaveInfo:leavelNameMap.entrySet()) {
					countJson.put(leaveInfo.getValue(), 0);
//					countJson.put(leaveInfo.getValue() + SUFFIX, 0);
//					countJson.put(leaveInfo.getValue() + TYPE, leavelTypeMap.get(leaveInfo.getKey()));
				}
				SysOrgPerson person = (SysOrgPerson) PropertyUtils.getProperty(statMonth,"docCreator");
				Date month = (Date) PropertyUtils.getProperty(statMonth,"fdMonth");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
				ca.setTime(month);
				// 本月最后一天
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				Date benyue = ca.getTime();
				Timestamp benyue_31=new Timestamp(benyue.getTime());
				HQLInfo hqlInfo1 = new HQLInfo();
				String whereBlock="sysAttendStat.fdDate>=:month and sysAttendStat.fdDate<=:benyue_31 and sysAttendStat.docCreator=:person";
				hqlInfo1.setParameter("month", month);
				hqlInfo1.setParameter("benyue_31", benyue_31);
				hqlInfo1.setParameter("person", person);
				hqlInfo1.setWhereBlock(whereBlock);
				List page = getSysAttendStatService().findList(hqlInfo1);
				float count1 = (float) 0.0;
				double fdOffDays = 0.0;
				for(int i=0; i<page.size();i++){
					SysAttendStat sysAttendStat = (SysAttendStat) page.get(i);
					count1+=sysAttendStat.getFdOffDays();
					String countDetail = sysAttendStat.getFdOffCountDetail();
					JSONObject statMonthJson1 = JSONObject.fromObject(countDetail);
					countJson.put("事假", NumberUtil.roundDecimal(sysAttendStat.getFdPersonalLeaveDays()+countJson.getDouble("事假"),10));
//					fdOffDays+=sysAttendStat.getFdPersonalLeaveDays();
					if(countDetail!=null){
						boolean isOk = false;
					Iterator it1 = statMonthJson1.keys();
					while (it1.hasNext()) {
						String offKey = (String) it1.next();
						// 对应的请假名称
						String offName = leavelNameMap.get(offKey);
						Integer offType =leavelTypeMap.get(offKey);
						// 计数

						if(offKey.equals("totalDay")&&isOk)
							count1+=statMonthJson1.getDouble(offKey);
//						if(offKey.equals("2")){
						Object countObj = statMonthJson1.get(offKey);
						String countStr = countObj.toString();
						Float count = 0f;
						if (countObj instanceof JSONObject) {
							JSONObject json = (JSONObject) countObj;
							if (json.getInt("statType") == 3) {
								isOk=true;
							}
					}
//						}
						if (countJson.containsKey(offName)) {
								if (countObj instanceof JSONObject) {
									JSONObject json = (JSONObject) countObj;
									for (SysTimeLeaveRule sysTimeLeaveRule : leaveList) {
										if(offKey.equals(sysTimeLeaveRule.getFdSerialNo())&&sysTimeLeaveRule.getFdName().equals(offName)){
											if (json.getInt("statType") == 3) {
												countJson.put(offName, NumberUtil.roundDecimal(Float.parseFloat(String.format("%.2f",json.getDouble("count")/sysAttendStat.getFdWorkTime()))+countJson.getDouble(offName),10));
											}}
									}
//									if(offKey.equals("1")&&"年休假".equals(offName)){
//									if (json.getInt("statType") == 3) {
//										countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//									}}else if(offKey.equals("13")&&"调休".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("12")&&"工伤假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("2")&&"事假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("3")&&"病假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("4")&&"婚假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("5")&&"产前检查假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("6")&&"产前工间休息".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("7")&&"产假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("8")&&"计划生育假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("9")&&"陪产假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}else if(offKey.equals("10")&&"哺乳假".equals(offName)){
//										if (json.getInt("statType") == 3) {
//											countJson.put(offName, NumberUtil.roundDecimal(json.getDouble("count")/sysAttendStat.getFdWorkTime()+countJson.getDouble(offName),10));
//										}
//									}
							}
//							countJson.put(offName, NumberUtil.roundDecimal(sysAttendStat.getFdOffDays()+countJson.getDouble(offName),10));
//							countJson.put(offName + SUFFIX, count1);
//							countJson.put(offName + TYPE, offType);
//								fdOffDays+=sysAttendStat.getFdOffDays();
						}
				}
				}
				}
//				try{
//				countJson.put("事假", NumberUtil.roundDecimal((Float)PropertyUtils.getProperty(statMonth,"fdPersonalLeaveDays")+countJson.getDouble("事假"),6));
//				}catch(Exception e){
//					int ddd;
//					ddd=333;
//				}
//				if (StringUtil.isNotNull(fdOffDaysDetail)) {
//					JSONObject statMonthJson = JSONObject.fromObject(fdOffDaysDetail);
//
//					Iterator it = statMonthJson.keys();
//					String offName = "";
//					while (it.hasNext()) {
//						String offKey = (String) it.next();
//						// 对应的请假名称
//						if(!leavelNameMap.get(offKey).equals(""))
//						offName = leavelNameMap.get(offKey);
//						Integer offType =leavelTypeMap.get(offKey);
//						// 计数
//						Object countObj = statMonthJson.get(offKey);
//						String countStr = countObj.toString();
//						Float count = 0f;
//						if (countObj instanceof JSONObject) {
//							JSONObject json = (JSONObject) countObj;
//							if (json.getInt("statType") == 3) {
//								Number _count = (Number) json.get("count");
//								count =  _count.floatValue();
//									countJson.put("事假", NumberUtil.roundDecimal(count+countJson.getDouble("事假"),6));
//								countStr = SysTimeUtil.formatHourTimeStr(count);
//								//如果是按小时 统计，每月汇总的时候，小时不准换
//								/**
//								Float hour = _count.floatValue();
//								countStr = SysTimeUtil.formatLeaveTimeStr(0f,hour);
//								count = hour / SysTimeUtil.getConvertTime();*/
//							} else {
//								count = (float) json.getDouble("count");
//								countStr = NumberUtil.roundDecimal(count, 1);
//							}
//						} else if (countObj instanceof Number) {
//							// 以前的数据只有天
//							count = (float) countObj;
//							countStr = NumberUtil.roundDecimal(count, 1);
//						}
////						if (countJson.containsKey(offName)) {
////							countJson.put(offName, NumberUtil.roundDecimal(count,2));
////							countJson.put(offName + SUFFIX, count1);
////							countJson.put(offName + TYPE, offType);
////						}
//					}
//				}else{
//					
////						if (countJson.containsKey("事假")) {
////							countJson.put("事假", count1);
////							countJson.put("事假" + SUFFIX, count1*60);
////							countJson.put("事假" + TYPE, 3);
////						}
//						int a=0;
//						int b=a+1;
//				}
				// {"年假":"1","事假":"2小时"}
				Iterator it2 = countJson.keys();
				while (it2.hasNext()) {
					String offKey = (String) it2.next();
					
					countJson.put(offKey, NumberUtil.roundDecimal(countJson.getDouble(offKey),2));
					fdOffDays+=countJson.getDouble(offKey);
				}
				countJson.put("fdOffDays", NumberUtil.roundDecimal(fdOffDays,2));
				PropertyUtils.setProperty(statMonth, "fdOffDaysDetailJson", countJson);
				rtnList.add(statMonth);
			}
			return list;
		} catch (Exception e) {
			logger.debug("格式化月考勤数据出错:" + e.getMessage(), e);
			return list;
		}
	}

	/**
	 * 每月汇总中的每天明细
	 */
	private void genDateDetailList(List list, RequestContext request)
			throws Exception {
		if (list == null || list.isEmpty()) {
			return;
		}
		String fdShowCols = request.getParameter("fdShowCols");
		String fdExportShowCols = request.getParameter("fdExportShowCols");
		if (StringUtil.isNotNull(fdExportShowCols)
				&& "fdExportAllCols".equals(fdExportShowCols)
				&& StringUtil.isNotNull(fdShowCols)
				&& !fdShowCols.contains("fdDateDetail")) {
			fdShowCols = fdShowCols + ";fdDateDetail";
		}
		if (StringUtil.isNotNull(fdExportShowCols)
				&& "fdExportAllCols".equals(fdExportShowCols)
				&& StringUtil.isNull(fdShowCols)) {
			fdShowCols = (String) request.getAttribute("fdShowCols");
		}
		if (StringUtil.isNull(fdShowCols)
				|| fdShowCols.indexOf("fdDateDetail") == -1) {
			return;
		}
		String fdDateType = request.getParameter("fdDateType");
		List newList = new ArrayList();
		List<String> userIds = new ArrayList<String>();
		Date fdStartTime = null;
		Date fdEndTime = null;
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			SysOrgElement docCreator = (SysOrgElement) PropertyUtils
					.getProperty(obj, "docCreator");
			userIds.add(docCreator.getFdId());
			if (i == 0) {
				if ("2".equals(fdDateType)) {
					Date fdStartDate = (Date) PropertyUtils.getProperty(obj,
							"fdStartTime");
					Date fdEndDate = (Date) PropertyUtils.getProperty(obj,
							"fdEndTime");
					fdStartTime = AttendUtil.getDate(fdStartDate, 0);
					fdEndTime = AttendUtil.getDate(fdEndDate, 1);
				} else {
					Date fdMonth = (Date) PropertyUtils.getProperty(obj,
							"fdMonth");
					fdStartTime = AttendUtil.getMonth(fdMonth, 0);
					fdEndTime = AttendUtil.getMonth(fdMonth, 1);
				}
			}
		}
		// 获取日统计数据
		HQLInfo hqlInfo = new HQLInfo();
		String s_rowsize = request.getParameter("rowsize");
		int rowsize = SysConfigParameters.getRowSize();
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setPageNo(0);
		StringBuffer sb = new StringBuffer();
		sb.append(HQLUtil.buildLogicIN(
				"sysAttendStat.docCreator.fdId", userIds));
		sb.append(
				" and sysAttendStat.fdDate>=:beginDate and sysAttendStat.fdDate<:endDate");
		hqlInfo.setParameter("beginDate", fdStartTime);
		hqlInfo.setParameter("endDate", fdEndTime);
		hqlInfo.setWhereBlock(sb.toString());
		List<SysAttendStat> statList = this.sysAttendStatService
				.findList(hqlInfo);
		// 获取日统计详情数据
		hqlInfo = new HQLInfo();
		sb = new StringBuffer();
		sb.append(HQLUtil.buildLogicIN(
				"sysAttendStatDetail.docCreator.fdId", userIds));
		sb.append(
				" and sysAttendStatDetail.fdDate>=:beginDate and sysAttendStatDetail.fdDate<:endDate");
		hqlInfo.setParameter("beginDate", fdStartTime);
		hqlInfo.setParameter("endDate", fdEndTime);
		hqlInfo.setWhereBlock(sb.toString());
		List<SysAttendStatDetail> detailList = this.sysAttendStatDetailService
				.findList(hqlInfo);

		Map<String, List> statMap = new HashMap<String, List>();
		// 实际的天
		Date fdStartDate = AttendUtil.getDate(fdStartTime, 0);
		Date fdEndDate = AttendUtil.getDate(fdEndTime, -1);		
		Map<String, List<SysAttendMain>> userMainMap = sysAttendMainService.findList(userIds, fdStartDate, fdEndDate);		
		for (int i = 0; i < list.size(); i++) {			
			Object obj = list.get(i);
			PropertyUtils.setProperty(obj, "enterDays", 1);
			SysOrgElement docCreator = (SysOrgElement) PropertyUtils
					.getProperty(obj, "docCreator");
			String fdId = (String) PropertyUtils.getProperty(obj, "fdId");
			String docCreatorId = docCreator.getFdId();
			// 获取某个人的统计数据
			List<SysAttendStat> userStatList = getUserStatList(docCreatorId,
					statList);
			List<SysAttendStatDetail> userDetailList = getUserAttendList(
					docCreatorId, detailList);

			List resultList = new ArrayList();
			Calendar cal = Calendar.getInstance();
			Map<String, JSONArray> resultMap = new HashMap<String, JSONArray>();
			for (cal.setTime(fdStartDate); cal.getTime()
					.compareTo(fdEndDate) <= 0; cal.add(Calendar.DATE, 1)) {
				Date statDate = cal.getTime();
				SysAttendStat stat = getAttendStat(userStatList, statDate);
				JSONObject record = new JSONObject();
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DATE);
				SysAttendStatDetail detail = getAttendDetailStat(userDetailList,
						statDate);
				record.put("key", "statKey_" + month + "_" + day);
				record.put("title", month + "." + day);
				try{
				record.put("value",
						getUserAttendStatus(stat, detail, statDate));
				}catch(Exception e){
					e.printStackTrace();
				}
				record.put("statDate", DateUtil.convertDateToString(statDate,
						DateUtil.TYPE_DATE, null));
				// 获取当天用户出差/请假/外出相关时间区间
				List<Object> userobj=new ArrayList();
				String user=new String(docCreatorId);
				userobj.add(user);
				Map<String, List<JSONObject>> bussList = this
						.getUserBussList(userobj, statDate);
				if (!bussList.isEmpty()) {
					Map<String,String> offTimeMap=new LinkedMap();
					Map<String,String> tripTimeMap=new LinkedMap();
					List<String> outTimeList = new ArrayList<String>();
					for (JSONObject bus : bussList.get(docCreatorId)) {
						Long fdBusStartTime = (Long) bus.get("fdBusStartTime");
						Long fdBusEndTime = (Long) bus.get("fdBusEndTime");
						Integer fdBusType = (Integer) bus.get("fdBusType");// 业务类型
						String busId = (String) bus.get("fdBusId");
						Integer fdStatType = (Integer) bus.get("fdStatType");
						Integer fdLeaveType=(Integer)bus.get("fdLeaveType");

						// 时间格式转换(时分)
						SimpleDateFormat sdYM = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						SimpleDateFormat sdfYMD = new SimpleDateFormat(
								"yyyy-MM-dd");
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						Date fdBusStartdate = new Date(fdBusStartTime);
						Date fdBusEnddate = new Date(fdBusEndTime);
						String fdBusStart = sdf.format(fdBusStartdate);
						String fdBusEnd = sdf.format(fdBusEnddate);
						// 获取当天考勤组打卡时间点相关信息
						List listTime = sysAttendCategoryService
								.getAttendSignTimes(docCreator, statDate);
						if (listTime.size() > 0) {
							// 获取第一次考勤打开作为上班开始时间,最后一次作为下班时间
							Object ftime = listTime.get(0);
							Date signTime = (Date) PropertyUtils
									.getProperty(ftime, "signTime");
							String fdYMD = sdfYMD.format(statDate);
							String fdHM = sdf.format(signTime);
							Date dayStart = sdYM.parse(fdYMD + " " + fdHM);

							Object atime = listTime.get(listTime.size() - 1);
							Integer overTimeType = (Integer) PropertyUtils
									.getProperty(atime, "overTimeType");
							Date signTime2 = (Date) PropertyUtils
									.getProperty(atime, "signTime");
							String fdHM2 = sdf.format(signTime2);
							// 跨天排班
							if (AttendConstant.FD_OVERTIME_TYPE[2]
									.equals(overTimeType)) {
								fdYMD = sdfYMD.format(
										AttendUtil.addDate(statDate, 1));
							}
							Date dayEnd = sdYM.parse(fdYMD + " " + fdHM2);

							// 出差，且不满一天
							if (fdBusType == 4) {
								Date tempStart = AttendUtil.getDate(dayStart,
										0);
								Date tempEnd = AttendUtil.getDate(dayStart, 1);
								if (!(fdBusStartdate.getTime()
										- tempStart.getTime() <= 0
										&& tempEnd.getTime()
										- fdBusEnddate.getTime() <= 1000)) {
									if (((dayStart.before(fdBusStartdate)
											&& dayEnd.after(fdBusStartdate)) ||
											(dayStart.before(fdBusEnddate)
													&& dayEnd
															.after(fdBusEnddate)))) {
										if (dayStart.after(fdBusStartdate)) {
											fdBusStart = sdf.format(dayStart);
										}
										if (dayEnd.before(fdBusEnddate)) {
											fdBusEnd = sdf.format(dayEnd);
										}
										tripTimeMap.put(fdBusStartdate.getTime()+"",fdBusStart + "~" + fdBusEnd);
									}
								}

							}
							// 请假：按小时请假才显示，且不满一天
							if (fdBusType == 5 && fdStatType == 3
									&& ((dayStart.before(fdBusStartdate)
											&& dayEnd.after(fdBusStartdate)) ||
											(dayStart.before(fdBusEnddate)
													&& dayEnd
															.after(fdBusEnddate)))) {
								if (dayStart.after(fdBusStartdate)) {
									fdBusStart = sdf.format(dayStart);
								}
								if (dayEnd.before(fdBusEnddate)) {
									fdBusEnd = sdf.format(dayEnd);
								}
								StringBuffer outStr = new StringBuffer(
										fdBusStart + "~" + fdBusEnd);
								if (AttendUtil.getDate(dayStart, 1)
										.before(fdBusEnddate)) {
									outStr.append("(次日)");
								}
								offTimeMap.put(fdLeaveType + "_" + fdBusStartTime,
										outStr.toString());
							}
							// 外出
							if (fdBusType == 7) {
								StringBuffer outStr = new StringBuffer(fdBusStart + "~" + fdBusEnd);
								if (AttendUtil.getDate(dayStart, 1)
										.before(fdBusEnddate)) {
									outStr.append("(次日)");
								}
								outTimeList.add(outStr.toString());

							}
						}
					}
					if(!offTimeMap.isEmpty()) {
						record.put("fdOffTime",offTimeMap);
					}
					if(!tripTimeMap.isEmpty()) {
						record.put("fdTripTime",tripTimeMap);
					}
					if (!outTimeList.isEmpty()) {
						record.put("fdOutgoingTime", outTimeList);
					}
				}
				if (stat!=null) {
					// 用户所有打卡记录
					List<SysAttendMain> allMainList = userMainMap.get(stat.getDocCreator().getFdId());
					if (allMainList == null || allMainList.isEmpty()) {
						resultMap.put(stat.getFdId(), new JSONArray());
						resultList.add(record);
						logger.warn("用户所有打卡记录为空"+stat.getFdId());
						continue;
					}
					// 某天打卡记录
					List<SysAttendMain> dateMainList = getUserAttendMainList(
							allMainList, stat.getFdDate());
					if (dateMainList == null || dateMainList.isEmpty()) {
						resultMap.put(stat.getFdId(), new JSONArray());
						resultList.add(record);
						logger.warn("用户打卡记录为空,"+stat.getDocCreator().getFdId()+stat.getFdOffCountDetail());
						continue;
					}
					List<SysAttendMain> mainList = dateMainList;
					Map<String,JSONObject> offMap=new LinkedMap();
					List<String> offTypeList=new ArrayList<>();
					if (mainList != null && !mainList.isEmpty()) {
						for (int k = 0; k < mainList.size(); k++) {
							SysAttendMain main = mainList.get(k);
							if (main.getFdStatus().equals(5)) {
								if(!offMap.containsKey(main.getFdOffType()+"_")) {
									offMap.put(main.getFdOffType()+"_", new JSONObject());
								}
								offTypeList.add(main.getFdOffType()+"");
								JSONObject offObj=offMap.get(main.getFdOffType()+"_");
								offObj.put("fdOffTypeText", AttendUtil.getLeaveTypeText(main.getFdOffType()));
								offObj.put("fdOffType", main.getFdOffType()+"_");
								String tmpStatType = "", noon = "";
								// 半天请假相关信息
								String offDetail = stat.getFdOffCountDetail();
								if (StringUtil.isNotNull(offDetail)) {
									JSONObject offDetailJson = JSONObject
											.fromObject(offDetail);
									if (offDetailJson != null
											&& main.getFdOffType() != null) {
										JSONObject countDetail=null;
										try{
										countDetail = (JSONObject) offDetailJson
												.get(main.getFdOffType()
														.toString());
										}catch(Exception e){
											e.printStackTrace();
										}
										if (countDetail != null) {
											Integer statType = (Integer) countDetail
													.get("statType");
											Number count = (Number) countDetail
													.get("count");
											count = count == null ? 0 : count;
											tmpStatType = statType != null
													? statType.toString() : "";
											Float tmpCount = count.floatValue();
											if (statType == 2
													&& tmpCount < 1f) {// 半天请假
												Date baseWorkTime = main
														.getFdBaseWorkTime() == null
																? main.getDocCreateTime()
																: main.getFdBaseWorkTime();
												Date workTime = AttendUtil
														.getDate(baseWorkTime,
																0);
												workTime.setHours(12); 
												if (!baseWorkTime.after(workTime)) {
													noon = ResourceUtil
															.getString(
																	"sysAttendMain.buss.am",
																	"sys-attend");
												} else {
													noon = ResourceUtil
															.getString(
																	"sysAttendMain.buss.pm",
																	"sys-attend");
												}
												if (!bussList.isEmpty() && main.getFdBusiness()!=null) {
													for (JSONObject bus : bussList.get(docCreatorId)) {
														String busId = (String) bus.get("fdBusId");
														if(main.getFdBusiness().getFdId().equalsIgnoreCase(busId)) {
															//开始日期和上下午标识和统计日期相等，则取上下午标识
															Long fdBusStartTime = (Long) bus.get("fdBusStartTime");
															Long fdBusEndTime = (Long) bus.get("fdBusEndTime");

															Date fdBusStartdate = new Date(fdBusStartTime);
															Date fdBusEnddate = new Date(fdBusEndTime);
															Integer fdStartNoon=null;
															if(AttendUtil.getDate(fdBusStartdate, 0).getTime()==AttendUtil.getDate(baseWorkTime, 0).getTime()) {

																//结束日期和上下午标识和统计日期相等，则取上下午标识
																fdStartNoon=bus.getInt("fdStartNoon");
															}else if(AttendUtil.getDate(fdBusEnddate, 0).getTime()==AttendUtil.getDate(baseWorkTime, 0).getTime()) {
																fdStartNoon=bus.getInt("fdEndNoon");
															}
															if(fdStartNoon!=null) {
																if (fdStartNoon==1) {
																	noon = ResourceUtil
																			.getString(
																					"sysAttendMain.buss.am",
																					"sys-attend");
																} else {
																	noon = ResourceUtil
																			.getString(
																					"sysAttendMain.buss.pm",
																					"sys-attend");
																}
																break;
															}
														}
													}
												}
											}
										}
									}
								} // end if
									// 半天请假标识
								offObj.put("fdNoonText", noon);
								offObj.put("fdStatType", tmpStatType);
							}
						}
						String offDetail = stat.getFdOffCountDetail();
						if (StringUtil.isNotNull(offDetail)) {
							JSONObject offDetailJson = JSONObject
									.fromObject(offDetail);
							if (offDetailJson != null) {
								Iterator keys = offDetailJson.keys();
								while (keys.hasNext()) {
									String key = keys.next().toString();
									String value = offDetailJson.getString(key);
									if (!offTypeList.contains(key) && value.startsWith("{")) {
										JSONObject countDetail = (JSONObject) offDetailJson.get(key);
										if (countDetail != null) {
											if (!offMap.containsKey(key + "_")) {
												offMap.put(key + "_", new JSONObject());
											}
											JSONObject offObj = offMap.get(key + "_");
											offObj.put("fdOffTypeText",
													AttendUtil.getLeaveTypeText(Integer.valueOf(key)));
											offObj.put("fdOffType", key + "_");
											offObj.put("fdNoonText", "");
											String tmpStatType = "";
											Integer statType = (Integer) countDetail.get("statType");
											tmpStatType = statType != null ? statType.toString() : "";
											offObj.put("fdStatType", tmpStatType);
										}
									}
								}
							}
						}
					}
					record.put("offMap", offMap);
				}
				resultList.add(record);
			}
			statMap.put(fdId, resultList);
		}
		stataicStatMap = statMap;
		request.setAttribute("statMap", statMap);
	}
	
	
	
	private List getUserAttendMainList(List<SysAttendMain> userMainList,Date statDate) {
		List<SysAttendMain> mainList = new ArrayList<SysAttendMain>();
		for (SysAttendMain main : userMainList) {
			Date createTime = AttendUtil.getDate(main.getDocCreateTime(), 0);
			if (Boolean.TRUE.equals(main.getFdIsAcross())) {
				createTime = AttendUtil.getDate(main.getDocCreateTime(), -1);
			}
			if (AttendUtil.getDate(statDate, 0).compareTo(createTime) == 0) {
				mainList.add(main);
			}
		}
//		boolean flag = false;
//		for (SysAttendMain main : mainList) {
//			 SysAttendCategory category = null;
//					try {
//						category = CategoryUtil.getCategoryById(main.getFdHisCategory().getFdId());
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if(category.getFdShiftType()==4){
//						if(main.getFdStatus()==6 && main.getFdWorkType()==1){
//							flag=true;
//						}
//					}
//		}
//		for (SysAttendMain main : mainList) {
//			 SysAttendCategory category = null;
//					try {
//						category = CategoryUtil.getCategoryById(main.getFdHisCategory().getFdId());
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if(category.getFdShiftType()==4){
//						if(flag && main.getFdStatus()==0 && main.getFdWorkType()==0){
//							main.setFdStatus(1);
//						}
//					}
//		}
		return mainList;
	}

	private List getAttendDetailList(List<String> userIds, Date statMonth)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		sb.append(HQLUtil.buildLogicIN(
				"sysAttendStatDetail.docCreator.fdId", userIds));
		sb.append(
				" and sysAttendStatDetail.fdDate>=:beginDate and sysAttendStatDetail.fdDate<:endDate");
		hqlInfo.setParameter("beginDate", AttendUtil.getMonth(statMonth, 0));
		hqlInfo.setParameter("endDate", AttendUtil.getMonth(statMonth, 1));
		hqlInfo.setWhereBlock(sb.toString());
		List<SysAttendStatDetail> detailList = this.sysAttendStatDetailService
				.findList(hqlInfo);
		return detailList;
	}

	/**
	 * 获取用户某天考勤详细状态 01:正常,02:休息,03:旷工,04:请假,05:出差,06:外出,07:上班缺卡,08:上班迟到,09:上班迟到
	 * 外勤,10:下班缺卡,11:下班早退, 12:下班早退 外勤,13:加班,14:上班外勤,15:下班外勤
	 * 
	 * @param list
	 * @param day
	 * @return
	 */
	private String getUserAttendStatus(SysAttendStat stat,
			SysAttendStatDetail detail, Date statDate) {
		Date now = new Date();
		if (AttendUtil.getDate(statDate, 0)
				.compareTo(AttendUtil.getDate(now, 0)) >= 0) {
			if (stat == null) {
				return "";
			}
		}
		// 休息
		if (stat == null) {
			return ",02";
		}
		Integer fdDateType = stat.getFdDateType();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("docCreateTime>:startDate and docCreateTime<:endDate and docCreator.fdId=:personId");
		hqlInfo.setParameter("startDate", AttendUtil.getDate(statDate, 0));
		hqlInfo.setParameter("endDate", AttendUtil.getDate(statDate, 100));
		hqlInfo.setParameter("personId", stat.getDocCreator().getFdId());
		SysAttendMain main = null;
		SysAttendCategory sysAttendCategory = null;
		try {
			main = (SysAttendMain) sysAttendMainService.findFirstOne(hqlInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sysAttendCategory = CategoryUtil.getFdCategoryInfo(main);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		Integer docStatus1 = detail.getDocStatus() == null ? -1
				: detail.getDocStatus();
		Integer docStatus2 = detail.getDocStatus2() == null ? -1
				: detail.getDocStatus2();
		Integer docStatus3 = detail.getDocStatus3() == null ? -1
				: detail.getDocStatus3();
		Integer docStatus4 = detail.getDocStatus4() == null ? -1
				: detail.getDocStatus4();

		Boolean fdOutside1 = detail.getFdOutside() == null ? false
				: detail.getFdOutside();
		Boolean fdOutside2 = detail.getFdOutside2() == null ? false
				: detail.getFdOutside2();
		Boolean fdOutside3 = detail.getFdOutside3() == null ? false
				: detail.getFdOutside3();
		Boolean fdOutside4 = detail.getFdOutside4() == null ? false
				: detail.getFdOutside4();

		Integer fdState1 = detail.getFdState() == null ? -1
				: detail.getFdState();
		Integer fdState2 = detail.getFdState2() == null ? -1
				: detail.getFdState2();
		Integer fdState3 = detail.getFdState3() == null ? -1
				: detail.getFdState3();
		Integer fdState4 = detail.getFdState4() == null ? -1
				: detail.getFdState4();

		// 如果有外出、出差、请假，则按外出、出差、请假显示
		boolean stutasf = (stat.getFdOutgoingTime() != null
				&& stat.getFdOutgoingTime() > 0)
				|| BooleanUtils.toBoolean(stat.getFdTrip())
				|| BooleanUtils.toBoolean(stat.getFdOff());
		// 有加班
		boolean over = stat.getFdOverTime() != null && stat.getFdOverTime() > 0;
		// stutasf = false;
		// 真正的全天旷工（上班日什么记录都没有）
		if (!stutasf && !over && stat.getFdAbsent() != null
				&& stat.getFdAbsent().booleanValue()) {
			return ",03";
		}
		// 全天正常（准时上下班）
		if (!stutasf && stat.getFdStatus() != null
				&& stat.getFdStatus().booleanValue()) {
			if (!stutasf && (stat.getFdOutside() == null
					|| !stat.getFdOutside().booleanValue())) {
				sb.append(",01");
				// return ",01";
			}
		}
		// 外出
		if (stat.getFdOutgoingTime() != null && stat.getFdOutgoingTime() > 0) {
			sb.append(",06");
		}
		// 出差
		if (BooleanUtils.toBoolean(stat.getFdTrip())) {
			sb.append(",05");
		}
		// 请假
		if (BooleanUtils.toBoolean(stat.getFdOff())) {
			sb.append(",04");
		}
		// 是否正常1、2、3、4
		boolean normal1 = true;
		boolean normal2 = true;
		boolean normal3 = true;
		boolean normal4 = true;
		if(sysAttendCategory.getFdShiftType()==4&&(stat.getFdOutgoingTime() != null && stat.getFdOutgoingTime() > 0))sb.append(",211");
		else
		// 1上班缺卡
		if (docStatus1 == 0) {
			if (docStatus1 == 0 && (fdState1 == null || fdState1 != 2 )) {
				sb.append(",071");
				normal1 = false;
			}
		}
		// 1下班缺卡
		if(sysAttendCategory!=null && sysAttendCategory.getFdShiftType()==4){
			if (docStatus1==0&&docStatus2 == 0) {
				if (docStatus2 == 0 && (fdState2 == null || fdState2 != 2)) {
					sb.append(",101");
					normal2 = false;
				}
			}
		}
			else{
		if (docStatus2 == 0) {
			if (docStatus2 == 0 && (fdState2 == null || fdState2 != 2)) {
				sb.append(",101");
				normal2 = false;
			}
		}
			}
		// 2上班缺卡
		if (docStatus3 == 0) {
			if (docStatus3 == 0 && (fdState3 == null || fdState3 != 2)) {
				sb.append(",072");
				normal3 = false;
			}
		}
		// 2下班缺卡
		if (docStatus4 == 0) {
			if (docStatus4 == 0 && (fdState4 == null || fdState4 != 2)) {
				sb.append(",102");
				normal4 = false;
			}
		}
		// 1上班迟到
		if (docStatus1 == 2) {
			String status = "081";
			if (docStatus1 == 2 && fdState1 != 2) {
				status = fdOutside1 != null && fdOutside1.booleanValue() ? "091"
						: status;
				sb.append("," + status);
				normal1 = false;
			}
		}
		// 1下班早退
		if (docStatus2 == 3) {
			String status = "111";
			if (docStatus2 == 3 && fdState2 != 2) {
				status = fdOutside2 != null && fdOutside2.booleanValue() ? "121"
						: status;
				sb.append("," + status);
				normal2 = false;
			}
		}
		// 2上班迟到
		if (docStatus3 == 2) {
			String status = "082";
			if (docStatus3 == 2 && fdState3 != 2) {
				status = fdOutside3 != null && fdOutside3.booleanValue() ? "092"
						: status;
				sb.append("," + status);
				normal3 = false;
			}
		}
		// 2下班早退
		if (docStatus4 == 3) {
			String status = "112";
			if (docStatus4 == 3 && fdState4 != 2) {
				status = fdOutside4 != null && fdOutside4.booleanValue() ? "122"
						: status;
				sb.append("," + status);
				normal4 = false;
			}
		}
		// 1上班外勤
		if (docStatus1 == 1 || fdState1 == 2) {
			if (fdOutside1 != null && fdOutside1.booleanValue()) {
				sb.append(",141");
				normal1 = false;
			}
		}
		// 1下班外勤
		if (docStatus2 == 1 || fdState2 == 2) {
			if (fdOutside2 != null && fdOutside2.booleanValue()) {
				sb.append(",151");
				normal2 = false;
			}
		}
		// 2上班外勤
		if (docStatus3 == 1 || fdState3 == 2) {
			if (fdOutside3 != null && fdOutside3.booleanValue()) {
				sb.append(",142");
				normal3 = false;
			}
		}
		// 2下班外勤
		if (docStatus4 == 1 || fdState4 == 2) {
			if (fdOutside4 != null && fdOutside4.booleanValue()) {
				sb.append(",152");
				normal4 = false;
			}
		}
		// 加班标识以考勤配置为准
		if (stat.getFdOverTime() != null && stat.getFdOverTime() > 0) {
			sb.append(",13");
		}
		// 旷工
		if (stat.getFdAbsentDays() != null && stat.getFdAbsentDays() > 0) {
			sb.append(",03");
		}
		// 休息
		if (sb.length() == 0) {
			return ",02";
		}
		// 正常
		if ((docStatus1 == 1 || fdState1 == 2)&& normal1 == true) {
			sb.append(",211");
		}
		if ((docStatus2 == 1 || fdState2 == 2)&& normal2 == true) {
			sb.append(",212");
		}
		if ((docStatus3 == 1 || fdState3 == 2)&& normal3 == true) {
			sb.append(",213");
		}
		if ((docStatus4 == 1 || fdState4 == 2) && normal4 == true) {
			sb.append(",214");
		}
		String fdStatus = sb.toString();
		return fdStatus;
	}

	private ISysAttendBusinessService sysAttendBusinessService;

	private Calendar ca24;

	private Calendar ca6;

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
					.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}
	/**
	 * 获取用户出差,请假,外出等记录
	 * 
	 * @param userSignDatas
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private Map getUserBussList(List<Object> userList, Date date)
			throws Exception {
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(AttendConstant.FD_ATTENDBUS_TYPE[4]);// 出差
		fdTypes.add(AttendConstant.FD_ATTENDBUS_TYPE[5]);// 请假
		fdTypes.add(AttendConstant.FD_ATTENDBUS_TYPE[7]);// 外出
		// 出差/请假/外出记录
		List<SysAttendBusiness> busList = this.getSysAttendBusinessService()
				.findBussList(userList, date, AttendUtil.getDate(date, 2),
						fdTypes);
		if (!busList.isEmpty()) {
			busList = getUserBusList(busList, userList, date);
			if (!busList.isEmpty() && busList.size()>1) {
				Collections.sort(busList,
						new Comparator<SysAttendBusiness>() {
							@Override
							public int compare(SysAttendBusiness w1,
									SysAttendBusiness w2) {
								if (w1.getFdBusStartTime() != null
										&& w2.getFdBusStartTime() != null) {
									return w1.getFdBusStartTime()
											.compareTo(w2.getFdBusStartTime());
								}
								return 0;
							}
						});
			}
        }
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			for (SysOrgElement ele : targets) {
				JSONObject ret = new JSONObject();
				String docCreatorId = ele.getFdId();
				Long fdBusStartTime = getUserBusTime(bus, true);
				Long fdBusEndTime = getUserBusTime(bus, false);
				if (fdBusStartTime == null || fdBusEndTime == null) {
					continue;
				}
				ret.put("fdBusStartTime", fdBusStartTime);
				ret.put("fdBusEndTime", fdBusEndTime);
				ret.put("fdBusType", bus.getFdType());
				ret.put("fdBusId", bus.getFdId());
				ret.put("fdLeaveType", bus.getFdBusType());// 假期编号
				ret.put("fdStatType", bus.getFdStatType());
				ret.put("fdStartNoon", bus.getFdStartNoon());
				ret.put("fdEndNoon", bus.getFdEndNoon());
				if (!records.containsKey(docCreatorId)) {
					records.put(docCreatorId, new ArrayList<JSONObject>());
				}
				List<JSONObject> recordList = records.get(docCreatorId);
				recordList.add(ret);
			}
		}
		return records;
	}

	/**
	 * 筛选流程记录
	 * 
	 * @param busList
	 *            原始流程数据
	 * @param userList
	 *            用户id列表
	 * @param date
	 *            日期
	 * @return
	 * @throws Exception
	 */
	private List<SysAttendBusiness> getUserBusList(
			List<SysAttendBusiness> busList,
			List<Object> userList, Date date) throws Exception {
		List<SysAttendBusiness> recordList = new ArrayList<SysAttendBusiness>();
		Set<SysAttendBusiness> tempBusSet = new HashSet<SysAttendBusiness>();
		for (Object obj : userList) {
			List<SysAttendBusiness> tempBusList = this
					.getSysAttendBusinessService()
					.genUserBusiness(UserUtil.getUser(obj.toString()), date,
							busList);
			for (SysAttendBusiness bus : tempBusList) {
				tempBusSet.add(bus);
			}
		}
		if (!tempBusSet.isEmpty()) {
			recordList = new ArrayList<SysAttendBusiness>(tempBusSet);
		}
		return recordList;
	}

	/**
	 * 获取用户出差/请假/外出的开始或结束时间
	 * 
	 * @param main
	 * @param isStartTime
	 * @return
	 */
	private Long getUserBusTime(SysAttendBusiness buss, boolean isStartTime) {
		if (buss == null) {
			return null;
		}
		Date startTime = buss.getFdBusStartTime();
		Date endTime = buss.getFdBusEndTime();
		if (Integer.valueOf(5).equals(buss.getFdType())) {
			// 请假
			if (buss.getFdStatType() != 3) {
				startTime = AttendUtil.getDate(startTime, 0);
				endTime = AttendUtil.getDate(endTime, 0);
			}
			if (buss.getFdStatType() == 2) {
				Integer startNoon = buss.getFdStartNoon();
				Integer endNoon = buss.getFdEndNoon();
				Calendar cal = Calendar.getInstance();
				if (startNoon == 2) {
					cal.setTime(startTime);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					startTime = cal.getTime();
				} 
				if (endNoon == 2) {
					cal.setTime(endTime);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					endTime = cal.getTime();
				}
			}
		}
		if (isStartTime) {
			return startTime.getTime();
		}
		return endTime.getTime();
	}
	private SysAttendStat getAttendStat(List<SysAttendStat> statList,
			Date date) {
		for (SysAttendStat stat : statList) {
			if (AttendUtil.isSameDate(stat.getFdDate(), date)) {
				return stat;
			}
		}
		return null;
	}

	private SysAttendStatDetail
			getAttendDetailStat(List<SysAttendStatDetail> statList, Date date) {
		for (SysAttendStatDetail stat : statList) {
			if (AttendUtil.isSameDate(stat.getFdDate(), date)) {
				return stat;
			}
		}
		return null;
	}

	private List getUserStatList(String docCreatorId,
			List<SysAttendStat> statList) {
		List<SysAttendStat> userStatList = new ArrayList();
		for (SysAttendStat stat : statList) {
			if (docCreatorId.equals(stat.getDocCreator().getFdId())) {
				userStatList.add(stat);
			}
		}
		return userStatList;
	}

	/**
	 * 获取用户打卡详细记录
	 * 
	 * @param docCreatorId
	 * @param statList
	 * @return
	 */
	private List getUserAttendList(String docCreatorId,
			List<SysAttendStatDetail> statList) {
		List<SysAttendStatDetail> userStatList = new ArrayList();
		for (SysAttendStatDetail detail : statList) {
			if (docCreatorId.equals(detail.getDocCreator().getFdId())) {
				userStatList.add(detail);
			}
		}
		return userStatList;
	}

	/**
	 * 处理月统计实时数据查询条件
	 * 
	 * @param request
	 * @param hqlInfo
	 * @throws Exception
	 */
	private void changeListStatHqlInfo(RequestContext request,
			HQLInfo hqlInfo) throws Exception {
		StringBuffer whereBlock = new StringBuffer("1=1 ");
		String fdMonth = request.getParameter("fdMonth");
		Date fdStartDate =null;
		if (StringUtil.isNotNull(fdMonth)) {
			whereBlock.append(
					" and sysAttendStatMonth.fdMonth>=:fdStartDate and sysAttendStatMonth.fdMonth<:fdEndDate");
			whereBlock.append(
					" and (sysAttendStatMonth.docCreator.fdHiredate<:fdEndDate or sysAttendStatMonth.docCreator.fdHiredate is null)");
			fdStartDate = DateUtil.convertStringToDate(fdMonth,
					DateUtil.TYPE_DATETIME, request.getLocale());
			hqlInfo.setParameter("fdStartDate",
					AttendUtil.getMonth(fdStartDate, 0));
			hqlInfo.setParameter("fdEndDate",
					AttendUtil.getMonth(fdStartDate, 1));
		}

		// 查询对象
		String fdTargetType = request.getParameter("fdTargetType");
		String fdCategoryIds = request.getParameter("fdCategoryIds");
		String fdDeptIds = request.getParameter("fdDeptIds");

		// 按部门查询
		if (StringUtil.isNull(fdTargetType) || "1".equals(fdTargetType)) {
			// AI接口新增
			String ai = request.getParameter("ai");
			if (StringUtil.isNotNull(ai) && "true".equals(ai)
					&& StringUtil.isNull(fdDeptIds)) {
				fdDeptIds = UserUtil.getUser().getFdId();
			}

			if (StringUtil.isNotNull(fdDeptIds)) {
				List<String> orgIds = ArrayUtil
						.convertArrayToList(fdDeptIds.split(";"));
				List<String> personIds = sysOrgCoreService
						.expandToPersonIds(orgIds);
				if (!personIds.isEmpty()) {
					whereBlock.append(
							" and (" + HQLUtil.buildLogicIN(
									"sysAttendStatMonth.docCreator.fdId",
									personIds));
					whereBlock.append(
							" or (" + AttendUtil.buildLikeHql(
									"sysAttendStatMonth.docCreatorHId", orgIds)
									+ "))");
//					hqlInfo.setParameter("fdIsAvailable0", false);

//					whereBlock.append(
//							" or (" + HQLUtil.buildLogicIN(
//									"sysAttendStatMonth.docCreator.fdId",
//									personIds));
//					whereBlock.append(")");
				} else {
					throw new NoRecordException();
				}

			}
			// 按考勤组查询
		} else if ("2".equals(fdTargetType)) {
			setCategoryWhere(fdCategoryIds,whereBlock,"sysAttendStatMonth.fdCategoryId");
		}

		String fdIsQuit = request.getParameter("fdIsQuit");
		if (!"true".equals(fdIsQuit)) {
//			whereBlock.append(
//					" and sysAttendStatMonth.docCreator.fdIsAvailable=:isAvailable1");
//			hqlInfo.setParameter("isAvailable1", true);
			whereBlock.append(
					" and (sysAttendStatMonth.docCreator.fdLeaveDate is null or sysAttendStatMonth.docCreator.fdLeaveDate>=:fdLeaveDate)");
			hqlInfo.setParameter("fdLeaveDate", new Date());
		}
		// 业务相关
		whereBlock.append(
				" and sysAttendStatMonth.docCreator.fdIsBusiness=:isBusiness");
		hqlInfo.setParameter("isBusiness", true);
		hqlInfo.setOrderBy("sysAttendStatMonth.docCreator.fdNamePinYin asc");
		
		
		SysAttendConfig config = sysAttendConfigService.getSysAttendConfig();
		if (config != null) {

			String fdExcTargetIdStr = config.getFdExcTargetIds();
			// 系统配置的不参与考勤人员
			if (StringUtil.isNotNull(fdExcTargetIdStr)) {
				List<String> fdExcTargetIds = ArrayUtil
						.convertArrayToList(fdExcTargetIdStr.split(";"));
				if (!fdExcTargetIds.isEmpty()) {
					whereBlock.append(
							" and sysAttendStatMonth.docCreator.fdId not in('")
							.append(StringUtil.join(fdExcTargetIds, "','"))
							.append("')");
				}
			} else {
				logger.warn("当前不参与考勤人员为空！");
			}
		}
		//过滤考勤组的排除人员
		//whereBlock.append(" and NOT EXISTS (select 1 from SysAttendCategory sysAttendCategory where sysAttendStatMonth.fdCategoryId=sysAttendCategory.fdId  and sysAttendCategory.fdExcTargets.fdId in (sysAttendStatMonth.docCreator.fdId) )");
		String fdType = request.getParameter("fdType");
		if(StringUtil.isNotNull(fdType)){
			whereBlock.append(" and (sysAttendStatMonth.fdOverTime > 0 or sysAttendStatMonth.fdOverApplyTime > 0)");
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	/**
	 * 处理月统计报表数据查询条件
	 * 
	 * @param request
	 * @param hqlInfo
	 * @throws Exception
	 */
	private void changeListMonthHqlInfo(RequestContext request,
			HQLInfo hqlInfo) throws Exception {
		StringBuffer whereBlock = new StringBuffer("1=1 ");
		String fdMonth = request.getParameter("fdMonth");
		if (StringUtil.isNotNull(fdMonth)) {
			whereBlock.append(" and sysAttendReportMonth.fdMonth>=:fdStartDate and sysAttendReportMonth.fdMonth<:fdEndDate");
			Date fdStartDate =DateUtil.convertStringToDate(fdMonth, DateUtil.TYPE_DATETIME, request.getLocale());
			hqlInfo.setParameter("fdStartDate", AttendUtil.getMonth(fdStartDate, 0));
			hqlInfo.setParameter("fdEndDate", AttendUtil.getMonth(fdStartDate, 1));
		}

		// 查询对象
		String fdTargetType = request.getParameter("fdTargetType");
		String fdCategoryIds = request.getParameter("fdCategoryIds");
		String fdDeptIds = request.getParameter("fdDeptIds");

		// 按部门查询
		if (StringUtil.isNull(fdTargetType) || "1".equals(fdTargetType)) {
			if (StringUtil.isNotNull(fdDeptIds)) {
				List<String> orgIds = ArrayUtil
						.convertArrayToList(fdDeptIds.split(";"));
				List<String> personIds = sysOrgCoreService
						.expandToPersonIds(orgIds);
				if (!personIds.isEmpty()) {
					whereBlock.append(
							" and (" + HQLUtil.buildLogicIN(
									"sysAttendReportMonth.docCreator.fdId",
									personIds));
					whereBlock.append(
							" or (" + AttendUtil.buildLikeHql(
									"sysAttendReportMonth.docCreatorHId",
									orgIds)
									+ " and sysAttendReportMonth.docCreator.fdIsAvailable=:fdIsAvailable0))");
					hqlInfo.setParameter("fdIsAvailable0", false);
				} else {
					throw new NoRecordException();
				}

			}
			// 按考勤组查询
		} else if ("2".equals(fdTargetType)) {
			setCategoryWhere(fdCategoryIds,whereBlock,"sysAttendReportMonth.fdCategoryId");
		}

		String fdIsQuit = request.getParameter("fdIsQuit");
		if (!"true".equals(fdIsQuit)) {
			whereBlock.append(
					" and sysAttendReportMonth.docCreator.fdIsAvailable=:isAvailable1");
			hqlInfo.setParameter("isAvailable1", true);
			whereBlock.append(
					" and (sysAttendReportMonth.docCreator.fdLeaveDate is null or sysAttendReportMonth.docCreator.fdLeaveDate>=:fdLeaveDate)");
			hqlInfo.setParameter("fdLeaveDate", new Date());
		}
		// 业务相关
		whereBlock.append(
				" and sysAttendReportMonth.docCreator.fdIsBusiness=:isBusiness");
		hqlInfo.setParameter("isBusiness", true);
		hqlInfo.setOrderBy("sysAttendReportMonth.docCreator.fdNamePinYin asc");

		String fdReportId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdReportId)) {
			whereBlock.append(
					" and sysAttendReportMonth.fdReportId=:reportId");
			hqlInfo.setParameter("reportId", fdReportId);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	/**
	 * 设置考勤组的查询条件
	 * @param fdCategoryIds
	 * @param whereBlock
	 */
	private void setCategoryWhere(String fdCategoryIds,StringBuffer whereBlock,String whereProperty) throws Exception {
		if (StringUtil.isNotNull(fdCategoryIds)) {
			List<String> categoryIds = ArrayUtil
					.convertArrayToList(fdCategoryIds.split(";"));
			if (!categoryIds.isEmpty()) {
				List<String> tempList =new ArrayList<>();
				tempList.addAll(CategoryUtil.getAllCategorys(categoryIds));

				whereBlock.append(
						" and " + HQLUtil.buildLogicIN(
								whereProperty,
								tempList));
			} else {
				throw new NoRecordException();
			}
		}
	}

	/**
	 * 获取月报表导出的HQL语句
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public HQLInfo getExportHqlInfo(RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		changeListStatHqlInfo(request, hqlInfo);
		// findList不做权限过滤，这里手动处理
		if (!UserUtil.checkRole("SYSROLE_ADMIN")) {
			if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.DEFAULT);
			}
		}
		return hqlInfo;
	}

	@Override
	public HSSFWorkbook exportExcel(HQLInfo hqlInfo,RequestContext request) throws Exception {
		if (UserOperHelper.allowLogOper("exportExcel", getModelName())) {
			UserOperHelper.setEventType(ResourceUtil
					.getString("sysAttendReport.exportExcel", "sys-attend"));
		}
		long alltime = System.currentTimeMillis();
		List list = sysAttendStatMonthService.findList(hqlInfo);
		if(logger.isDebugEnabled()){
			logger.debug("获取每月报表数据耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
		}
		String fdName = request.getParameter("fdName");
		fdName = StringUtil.isNotNull(fdName) ? fdName
				: ResourceUtil.getString("table.sysAttendReport",
						"sys-attend");
		// 获取每日统计数据
		request.setParameter("fdShowColsCustom", "fdDateDetail");
		alltime = System.currentTimeMillis();
		
		genDateDetailList(list, request);
		if(logger.isDebugEnabled()){
			logger.debug("每月报表每日明细汇总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
		}
		return buildWorkBookByCols(formatStatMonth(list), fdName, request);
	}
	
	public HSSFWorkbook buildWorkBook(List list, String sheetName,
			RequestContext request)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.createFreezePane(0, 2);
		String fdDateType = request.getParameter("fdDateType");
		SysTimeLeaveConfig leaveConfig = new SysTimeLeaveConfig();

		int colNum =66;

		for (int k = 0; k < colNum; k++) {
			sheet.setColumnWidth(k, 3000);
		}
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		if ("2".equals(fdDateType)) {
			sheet.setColumnWidth(4, 6000);
		}

		workbook.setSheetName(0, sheetName);

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
		titleCellStyle.setLocked(true);
		titleCellStyle.setWrapText(true);

		HSSFCellStyle cStylesBlue = workbook.createCellStyle();
		cStylesBlue.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		cStylesBlue.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		cStylesBlue.setWrapText(true);

		HSSFFont fontBulue = workbook.createFont();
		fontBulue.setColor(org.apache.poi.ss.usermodel.IndexedColors.BLUE.index);
		cStylesBlue.setFont(fontBulue);

		HSSFCellStyle cStylesRed = workbook.createCellStyle();
		cStylesRed.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		cStylesRed.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		cStylesRed.setWrapText(true);

		HSSFCell[] titleCells = new HSSFCell[colNum];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}

		titleCells[0].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.docCreatorName"));
		titleCells[1].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.docLoginName"));
		titleCells[2].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.dept"));
		titleCells[3].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.category"));
		titleCells[4].setCellValue("2".equals(fdDateType) ? ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.period")
				: ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdMonth"));
		titleCells[5].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdShouldDays.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[6].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdWorkDateDays.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[7].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdActualDays.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[8].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdTotalTime.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[9].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdStatusDays.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[10].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdAbsentDays.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[11].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdTripDays.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[12].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdOutgoingTime.br")
				.replaceAll("<br/>", "\r\n")));

		titleCells[13].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdLateCount.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[14].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdLateTime.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[15].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdLateExcCount.br")
				.replaceAll("<br/>", "\r\n")));

		titleCells[16].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdLeftCount.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[17].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdLeftTime.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[18].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdLeftExcCount.br")
				.replaceAll("<br/>", "\r\n")));

		titleCells[19].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdOutsideCount.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[20].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdMissedCount.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[21].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString(
						"sys-attend:sysAttendStatMonth.fdMissedExcCount.br")
				.replaceAll("<br/>", "\r\n")));

		titleCells[22].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdWorkOverTime.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[23].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdOffOverTime.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[24].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString(
						"sys-attend:sysAttendStatMonth.fdHolidayOverTime.br")
				.replaceAll("<br/>", "\r\n")));
		titleCells[25].setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdOverTime.br")
				.replaceAll("<br/>", "\r\n")));

		// 请假天数细分
		int titleIndex = colNum;
		JSONObject offDetailJson = null;
		if (list != null && !list.isEmpty()) {
			offDetailJson = (JSONObject) PropertyUtils
					.getProperty(list.get(0), "fdOffDaysDetailJson");
		}
		if (offDetailJson != null && !offDetailJson.isEmpty()) {
			Iterator iterator = offDetailJson.keys();
			while (iterator.hasNext()) {
				String offName = (String) iterator.next();

				if (!offName.contains(SUFFIX)) {
					HSSFCell titleCell = titlerow.createCell(titleIndex);
					titleCell.setCellStyle(titleCellStyle);
					titleCell.setCellValue(new HSSFRichTextString(
							offName + "\r\n" + ResourceUtil.getString(
									"sys-attend:sysAttendStatMonth.day")));
					sheet.setColumnWidth(titleIndex, 3000);
					titleIndex++;
				}
			}
		}

		// 请假总天数
		HSSFCell titleCell = titlerow.createCell(titleIndex);
		titleCell.setCellStyle(titleCellStyle);
		titleCell.setCellValue(new HSSFRichTextString(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdOffDays.br")
				.replaceAll("<br/>", "\r\n")));

		// 请假和加班的表头
		List<Map<String, Object>> infos = new ArrayList<Map<String, Object>>();
		Map<String, Object> overTimeInfo = new HashMap<String, Object>();
		overTimeInfo.put("colStart", 22);
		overTimeInfo.put("colEnd", 25);
		overTimeInfo.put("title", ResourceUtil
				.getString("sys-attend:sysAttendMain.fdStatus.overtime"));
		Map<String, Object> offDaysInfo = new HashMap<String, Object>();
		offDaysInfo.put("colStart", colNum);
		offDaysInfo.put("colEnd", titleIndex);
		offDaysInfo.put("title", ResourceUtil
				.getString("sys-attend:sysAttendMain.fdStatus.askforleave"));
		infos.add(overTimeInfo);
		infos.add(offDaysInfo);
		buildMergeHeader(sheet, titlerow, infos);
		rowIndex++;

		/* 内容行 */
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		int tmp = 0;
		List<String> idsList=new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			DecimalFormat df = new DecimalFormat("#.#");
			for (int i = 0; i < list.size(); i++) {
				HSSFRow contentrow = sheet.createRow(rowIndex++);
				contentrow.setHeight((short) 400);
				HSSFCell[] contentcells = new HSSFCell[colNum];
				for (int j = 0; j < contentcells.length; j++) {
					contentcells[j] = contentrow.createCell(j);
					contentcells[j].setCellStyle(contentCellStyle);
				}
				Object monthModel = list.get(i);
				SysOrgPerson docCreator = (SysOrgPerson) PropertyUtils
						.getProperty(monthModel, "docCreator");
				idsList.add(docCreator.getFdId());
				Date fdStartTime = null;
				Date fdEndTime = null;
				Date fdMonth = null;
				if ("2".equals(fdDateType)) {
					fdStartTime = (Date) PropertyUtils
							.getProperty(monthModel, "fdStartTime");
					fdEndTime = (Date) PropertyUtils
							.getProperty(monthModel, "fdEndTime");
				} else {
					fdMonth = (Date) PropertyUtils
							.getProperty(monthModel, "fdMonth");
				}
				String fdCategoryId = (String) PropertyUtils
						.getProperty(monthModel, "fdCategoryId");
				Integer fdShouldDays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdShouldDays");
				Integer fdWorkDateDays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdWorkDateDays");
				Integer fdActualDays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdActualDays");
				Integer fdStatusDays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdStatusDays");
				Integer fdAbsentDays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdAbsentDays");
				Float fdAbsentDaysCount = (Float) PropertyUtils
						.getProperty(monthModel, "fdAbsentDaysCount");
				Float fdTripDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdTripDays");
				Float fdOffDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdOffDays");
				Integer fdLateCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLateCount");
				Integer fdLateExcCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLateExcCount");
				Integer fdLateTime = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLateTime");
				Integer fdLeftCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLeftCount");
				Integer fdLeftExcCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLeftExcCount");
				Integer fdLeftTime = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLeftTime");
				Integer fdOutsideCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdOutsideCount");
				Integer fdMissedCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdMissedCount");
				Integer fdMissedExcCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdMissedExcCount");
				Long fdTotalTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdTotalTime");
				Long fdOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverTime");
				Long fdWorkOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverTime");
				Long fdOffOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverTime");
				Long fdHolidayOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverTime");
				JSONObject fdOffDaysDetailJson = (JSONObject) PropertyUtils
						.getProperty(monthModel, "fdOffDaysDetailJson");
				Float fdOutgoingTime = (Float) PropertyUtils
						.getProperty(monthModel, "fdOutgoingTime");
				Float fdOffTime = (Float) PropertyUtils
						.getProperty(monthModel, "fdOffTimeHour");

				contentcells[0]
						.setCellValue(
								docCreator.getFdIsAvailable()
										? docCreator.getFdName()
										: (docCreator.getFdName()
												+ ResourceUtil
														.getString(
																"sys-attend:sysAttendStatDetail.alreadyQuit")));
				contentcells[1]
						.setCellValue(
								docCreator == null
										? ""
										: docCreator.getFdLoginName());
				contentcells[2]
						.setCellValue(
								docCreator.getFdParent() == null
										? ""
										: docCreator
												.getFdParent().getFdName());

				String fdCategoryName = "";
				if (StringUtil.isNotNull(fdCategoryId)) {
					SysAttendCategory cate = CategoryUtil.getCategoryById(fdCategoryId);
					if (cate != null) {
						fdCategoryName = cate.getFdName();
					}
				}
				contentcells[3].setCellValue(fdCategoryName);
				String dateStr = "";
				if ("2".equals(fdDateType)) {
					dateStr = DateUtil.convertDateToString(fdStartTime,
							DateUtil.TYPE_DATE, null) + "~"
							+ DateUtil.convertDateToString(fdEndTime,
									DateUtil.TYPE_DATE, null);
				} else {
					dateStr = DateUtil.convertDateToString(fdMonth, "yyyy-MM");
				}
				contentcells[4].setCellValue(dateStr);
				contentcells[5].setCellValue(fdShouldDays);
				contentcells[6].setCellValue(
						fdWorkDateDays == null ? 0 : fdWorkDateDays);
				contentcells[7].setCellValue(fdActualDays);
				contentcells[8].setCellValue(
						fdTotalTime == null ? 0 : fdTotalTime.longValue() / 60);
				contentcells[9].setCellValue(fdStatusDays == null
						? 0 : fdStatusDays.intValue());
				contentcells[10].setCellValue(
						fdAbsentDaysCount == null
								? (fdAbsentDays == null ? 0 : fdAbsentDays)
								: Float.parseFloat(
										df.format(fdAbsentDaysCount)));
				contentcells[11]
						.setCellValue(fdTripDays == null ? 0 : Float.parseFloat(
								df.format(fdTripDays)));
				contentcells[12]
						.setCellValue(fdOutgoingTime == null
								? 0
								: fdOutgoingTime.intValue());

				contentcells[13].setCellValue(fdLateCount == null
						? 0 : fdLateCount.intValue());
				contentcells[14].setCellValue(fdLateTime == null
						? 0 : fdLateTime.intValue());
				contentcells[15]
						.setCellValue(fdLateExcCount == null
								? 0 : fdLateExcCount.intValue());

				contentcells[16].setCellValue(fdLeftCount == null
						? 0 : fdLeftCount.intValue());
				contentcells[17].setCellValue(fdLeftTime == null
						? 0 : fdLeftTime.intValue());
				contentcells[18]
						.setCellValue(fdLeftExcCount == null
								? 0 : fdLeftExcCount.intValue());

				contentcells[19]
						.setCellValue(fdOutsideCount == null
								? 0 : fdOutsideCount.intValue());
				contentcells[20]
						.setCellValue(fdMissedCount == null
								? 0 : fdMissedCount.intValue());
				contentcells[21]
						.setCellValue(fdMissedExcCount == null
								? 0
								: fdMissedExcCount.intValue());

				contentcells[22]
						.setCellValue(fdWorkOverTime == null
								? 0
								: fdWorkOverTime.longValue()
										/ 60);
				contentcells[23]
						.setCellValue(fdOffOverTime == null
								? 0
								: fdOffOverTime.longValue()
										/ 60);
				contentcells[24]
						.setCellValue(fdHolidayOverTime == null
								? 0
								: fdHolidayOverTime.longValue()
										/ 60);
				contentcells[25]
						.setCellValue(fdOverTime == null
								? 0
								: fdOverTime.longValue() / 60);

				// 请假天数细分
				int colIndex = colNum;
				if (fdOffDaysDetailJson != null
						&& !fdOffDaysDetailJson.isEmpty()) {
					Iterator iterator = fdOffDaysDetailJson.keys();
					while (iterator.hasNext()) {
						String offName = (String) iterator.next();
						if (!offName.contains(SUFFIX)) {
							String offValue = fdOffDaysDetailJson
									.getString(offName);
							HSSFCell contentcell = contentrow
									.createCell(colIndex);
							contentcell.setCellStyle(contentCellStyle);
							try {
								contentcell
										.setCellValue(
												Float.parseFloat(offValue));
							} catch (Exception e) {
								contentcell.setCellValue(offValue);
							}
							sheet.setColumnWidth(colIndex, 3000);
							colIndex++;
						}
					}
				}
				// 请假总天数
				HSSFCell contentcell = contentrow.createCell(colIndex);
				contentcell.setCellStyle(contentCellStyle);
				fdOffDays = fdOffDays == null ? 0 : fdOffDays;
				fdOffTime = fdOffTime == null ? 0 : fdOffTime;
				Float convertHour = SysTimeUtil.getConvertTime();
				Float tmpOffDayHour = (fdOffDays - fdOffDays.intValue())
						* convertHour;
				fdOffTime += tmpOffDayHour;

				Float _fdOffHourDays = fdOffTime / convertHour;
				int days = fdOffDays.intValue() + _fdOffHourDays.intValue();
				fdOffTime = fdOffTime % convertHour;
				int hour = fdOffTime.intValue();
				int min = (int) ((fdOffTime - fdOffTime.intValue()) * 60);
				String offText = "";
				if (days > 0) {
					offText = days
							+ ResourceUtil.getString("date.interval.day");
				}
				if (hour > 0) {
					offText += hour
							+ ResourceUtil.getString("date.interval.hour");
				}
				if (min > 0) {
					offText += min
							+ ResourceUtil.getString("date.interval.minute");
				}
				if (days == 0 && hour == 0 && min == 0) {
					offText = "0";
				}

				contentcell.setCellValue(offText);
				sheet.setColumnWidth(colIndex, 3000);
				colIndex++;

				// 生成每日明细
				createStatDateDetailExcel(monthModel, request, i,
						colIndex, titlerow, contentrow, sheet,
						titleCellStyle, contentCellStyle,workbook,cStylesBlue,cStylesRed);
			}

		}

		
		// 生成原始记录表
		buildRecordSheet(workbook, request,idsList);

		return workbook;
	}

	public HSSFWorkbook buildWorkBookByCols(List list, String sheetName,
			RequestContext request)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.createFreezePane(0, 2);
		String fdDateType = request.getParameter("fdDateType");
		String fdExportShowCols = request.getParameter("fdExportShowCols");
		//导出的假期合计单位转换 参数。只用在假期中
		String fdDateFormat = request.getParameter("fdDateFormat");
		String fdDateFormatHoliday = fdDateFormat;
		//其他列全部默认其为默认的格式。
		fdDateFormat = "default";


		String fdShowCols = request.getParameter("fdShowCols");
		if (StringUtil.isNull(fdShowCols)) {
			fdShowCols = (String) request.getParameter("fdShowColsCustom");
		}
		fdShowCols=StringEscapeUtils.unescapeHtml(fdShowCols);
		if (StringUtil.isNull(fdExportShowCols)) {
			fdExportShowCols = "fdExportSelectCols";
		}
		if (StringUtil.isNull(fdDateFormatHoliday)) {
			fdDateFormatHoliday = "default";
		}
		String[] fdShowColsArr = fdShowCols.split(";");
		String fdAllCols = "fdAffiliatedCompany;fdFirstLevelDepartment;fdSecondLevelDepartment;fdThirdLevelDepartment"
				+ ";fdStaffNo;fdOrgPost;fdStaffingLevel.fdName;fdEntryTime;fdResignationDate;fdStaffType;"
				+ "fdTotalDays;fdUsedDays;fdRestDays;fdTxTotalDays;fdTxUsedDays;fdTxRestDays;"
				+ ";fdShouldDays;fdHolidays;fdActualDays;enterDays;leaveDays;fdWorkDateDays;fdTotalTime;fdStatusDays;"
				
				+ "fdAbsentDays;fdTripDays;fdOutgoingTime;fdLateCount;fdLateTime;fdLateExcCount;fdLeftCount;fdLeftTime;"
				+ "fdLeftExcCount;fdMissedCount;fdMissedExcCount;"
				
				+ "fdWorkOverTime;fdOffOverTime;fdHolidayOverTime;fdOverTime;"
				+ "fdWorkOverApplyTime;fdOffOverApplyTime;fdHolidayOverApplyTime;fdOverApplyTime;"
				+ "fdWorkOverPayTime;fdOffOverPayTime;fdHolidayOverPayTime;fdOverPayTime;"
				+ "fdWorkOverPayApplyTime;fdOffOverPayApplyTime;fdHolidayOverPayApplyTime;fdOverPayApplyTime;"
				+ "fdWorkOverTurnTime;fdOffOverTurnTime;fdHolidayOverTurnTime;fdOverTurnTime;"
				+ "fdWorkOverTurnApplyTime;fdOffOverTurnApplyTime;fdHolidayOverTurnApplyTime;fdOverTurnApplyTime;"
				+ "fdWorkOverRestTime;fdOffOverRestTime;fdHolidayOverRestTime;fdOverRestTime;"
				+ "fdWorkRestTurnTime;fdOffRestTurnTime;fdHolidayRestTurnTime;fdRestTurnTime;mark;sign;";
		String[] fdAllColsArry = fdAllCols.split(";");
		int colNum = 3;
		// 确保fdColsList列表的顺序与fdAllCols一致
		List<String> fdColsList = new ArrayList<String>();
		for (int j = 0; j < fdAllColsArry.length; j++) {
			for (int i = 0; i < fdShowColsArr.length; i++) {
				if (fdAllColsArry[j].equalsIgnoreCase(fdShowColsArr[i])) {
					colNum++;
					fdColsList.add(fdShowColsArr[i]);
				}
			}
		}
		for (int i = 0; i < fdShowColsArr.length; i++) {
			if (!fdColsList.contains(fdShowColsArr[i])) {
				fdColsList.add(fdShowColsArr[i]);
			}
		}
		fdColsList.add("mark");
		fdColsList.add("sign");
		boolean isExportAllCols = false;
		String method = request.getParameter("method");
		// 判断是否导出全部列
		if ("fdExportAllCols".equals(fdExportShowCols)
				|| (StringUtil.isNotNull(method)
						&& "exportMonthReport".equals(method))) {
			colNum = 61;
			isExportAllCols = true;
		}
		for (int k = 0; k < colNum; k++) {
			sheet.setColumnWidth(k, 3000);
		}
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		int typeIndex = 2;
		String[] preCols= new String[]{"fdAffiliatedCompany","fdFirstLevelDepartment","fdSecondLevelDepartment","fdThirdLevelDepartment","fdStaffNo","fdOrgPost","fdStaffingLevel.fdName","fdStaffType"
				,"enterDays","leaveDays","fdResignationDate","fdDept","fdEntryTime","fdCategoryName"};
		for (int i = 0; i < preCols.length; i++) {
			if(fdColsList.contains(preCols[i])) {
				sheet.setColumnWidth(typeIndex++, 4000);
			}
		}
		if(isExportAllCols) {
			typeIndex = 6;
		}
		long alltime =System.currentTimeMillis();
		if ("2".equals(fdDateType)) {
			sheet.setColumnWidth(typeIndex, 6000);
		}
		workbook.setSheetName(0, sheetName);

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
		titleCellStyle.setLocked(true);
		titleCellStyle.setWrapText(true);

		HSSFCellStyle cStylesBlue = workbook.createCellStyle();
		cStylesBlue.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		cStylesBlue.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		cStylesBlue.setWrapText(true);

		HSSFFont fontBulue = workbook.createFont();
		fontBulue.setColor(org.apache.poi.ss.usermodel.IndexedColors.BLUE.index);
		cStylesBlue.setFont(fontBulue);

		HSSFCellStyle cStylesRed = workbook.createCellStyle();
		cStylesRed.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		cStylesRed.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		cStylesRed.setWrapText(true);

		HSSFFont fontRed = workbook.createFont();
		fontRed.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index);
		cStylesRed.setFont(fontRed);


		HSSFCell[] titleCells = new HSSFCell[100];
//		HSSFCell[] titleCells = new HSSFCell[colNum+2];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}
		int index=0;
		if (fdColsList.contains("fdAffiliatedCompany") || isExportAllCols) {
			titleCells[index++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatMonth.fdAffiliatedCompany"));
		}
		if (fdColsList.contains("fdFirstLevelDepartment") || isExportAllCols) {
			titleCells[index++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.fdFirstLevelDepartmentName"));
		}
		if (fdColsList.contains("fdSecondLevelDepartment") || isExportAllCols) {
			titleCells[index++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.fdSecondLevelDepartmentName"));
		}
		if (fdColsList.contains("fdThirdLevelDepartment") || isExportAllCols) {
			titleCells[index++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.fdThirdLevelDepartmentName"));
		}

		titleCells[index++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.docCreatorName"));
		if (fdColsList.contains("fdStaffNo") || isExportAllCols) {
			titleCells[index++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatMonth.fdStaffNo"));
		}
		titleCells[index++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.docLoginName"));
		int cateIndex = 0;
		if (fdColsList.contains("fdCategoryName") || isExportAllCols) {
			cateIndex = index++;
			titleCells[cateIndex].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatMonth.category"));
		}
		if (fdColsList.contains("fdOrgPost") || isExportAllCols) {
			titleCells[index++].setCellValue("岗位");
		}
		if (fdColsList.contains("fdStaffingLevel.fdName") || isExportAllCols) {
			titleCells[index++].setCellValue("职务");
		}
		int entryIndex = 0;
		if (fdColsList.contains("fdEntryTime") || isExportAllCols) {
			entryIndex = index++;
			titleCells[entryIndex].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatMonth.fdEntryTime"));
		}
		if (fdColsList.contains("fdResignationDate") || isExportAllCols) {
			titleCells[index++].setCellValue("离职时间");
		}
		titleCells[index++].setCellValue("2".equals(fdDateType) ? ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.period")
				: ResourceUtil
						.getString("sys-attend:sysAttendStatMonth.fdMonth"));
		if (fdColsList.contains("fdStaffType") || isExportAllCols) {
			titleCells[index++].setCellValue("人员类别");
		}
		int deptIndex = 0;
		if (fdColsList.contains("fdDept") || isExportAllCols) {
			deptIndex = index++;
			titleCells[deptIndex].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatMonth.dept"));
		}
//		index++;
//		titleCells[typeIndex].setCellValue("2".equals(fdDateType) ? ResourceUtil
//				.getString("sys-attend:sysAttendStatMonth.period")
//				: ResourceUtil
//						.getString("sys-attend:sysAttendStatMonth.fdMonth"));
		int fdShouldDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdShouldDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdShouldDays.br"
						: "sysAttendStatMonth.fdShouldDays.br.format",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
		int fdHolidaysIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdShouldDaysAndHolidays.br"
						: "sysAttendStatMonth.fdShouldDaysAndHolidays.br.format",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
		int fdWorkDateDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkDateDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkDateDays.br"
						: "sysAttendStatMonth.fdWorkDateDays.br.format",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
		int fdActualDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdActualDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdActualDays.br"
						: "sysAttendStatMonth.fdActualDays.br.format",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
		int fdEnterDaysIndex = genTitleCells(fdColsList, titleCells,
				"enterDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.enterDays"
						: "sysAttendStatMonth.enterDays",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
		int fdLeaveDaysIndex = genTitleCells(fdColsList, titleCells,
				"leaveDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.leaveDays"
						: "sysAttendStatMonth.leaveDays",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
//		if (fdColsList.contains("enterDays") || isExportAllCols) {
//			titleCells[index++].setCellValue(ResourceUtil
//					.getString("sys-attend:sysAttendStatMonth.enterDays"));
//		}
//		if (fdColsList.contains("leaveDays") || isExportAllCols) {
//			titleCells[index++].setCellValue(ResourceUtil
//					.getString("sys-attend:sysAttendStatMonth.leaveDays"));
//		}
		int fdTotalTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdTotalTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdTotalTime.br"
						: "sysAttendStatMonth.fdTotalTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdStatusDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdStatusDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdStatusDays.br"
						: "sysAttendStatMonth.fdStatusDays.br.format",
				"sys-attend",
				isExportAllCols, index++, fdDateFormat);
		
		
//		int fdOutsideCountIndex = genTitleCells(fdColsList, titleCells,
//				"fdOutsideCount",
//				"sysAttendStatMonth.fdOutsideCount.br", "sys-attend",
//				isExportAllCols, index++, null);
//		int fdMissedCountIndex = genTitleCells(fdColsList, titleCells,
//				"fdMissedCount",
//				"sysAttendStatMonth.fdMissedCount.br", "sys-attend",
//				isExportAllCols, index++, null);
		
		int fdTotalDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdTotalDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdTotalDays.br"
						: "sysAttendStatMonth.fdTotalDays.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdUsedDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdUsedDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdUsedDays.br"
						: "sysAttendStatMonth.fdUsedDays.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdRestDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdRestDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdRestDays.br"
						: "sysAttendStatMonth.fdRestDays.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdTxTotalDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdTxTotalDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdTxTotalDays.br"
						: "sysAttendStatMonth.fdTxTotalDays.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdTxUsedDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdTxUsedDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdTxUsedDays.br"
						: "sysAttendStatMonth.fdTxUsedDays.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdTxRestDaysIndex = genTitleCells(fdColsList, titleCells,
				"fdTxRestDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdTxRestDays.br"
						: "sysAttendStatMonth.fdTxRestDays.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		
		//实际加班费平日加班
		int fdWorkOverPayTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkOverPayTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		//实际加班费周末加班
		int fdOffOverPayTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOffOverPayTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		//实际加班费法定节假日加班
		int fdHolidayOverPayTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidayOverPayTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		//实际加班费小时
		int fdOverPayTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOverPayTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);

		//申请加班费平日加班
		int fdWorkOverPayApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkOverPayApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdWorkOverTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkOverTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdWorkOverRestTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkOverRestTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdWorkOverTurnApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkOverTurnApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdWorkRestTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdWorkRestTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		//申请加班费周末加班
		int fdOffOverPayApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOffOverPayApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOffOverTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOffOverTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOffOverRestTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOffOverRestTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOffRestTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOffRestTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdHolidayOverTurnApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidayOverTurnApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		//申请加班费法定节假日加班
		int fdHolidayOverPayApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidayOverPayApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdHolidayOverTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidayOverTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdHolidayOverRestTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidayOverRestTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOffOverTurnApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOffOverTurnApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdHolidayRestTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdHolidayRestTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		//申请加班费小时
		int fdOverPayApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOverPayApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOverTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOverTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOverRestTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOverRestTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdRestTurnTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdRestTurnTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
		int fdOverTurnApplyTimeIndex = genTitleCells(fdColsList, titleCells,
				"fdOverTurnApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				index++, fdDateFormat);
//		int fdOverPayApplyTimeIndex1 = genTitleCells(fdColsList, titleCells,
//				"备注",
//				"default".equals(fdDateFormat)
//						? "sysAttendStatMonth.mark.br"
//						: "sysAttendStatMonth.mark.br.format",
//				"sys-attend",
//				isExportAllCols,
//				index++, fdDateFormat);
//		int fdOverPayApplyTimeIndex11 = genTitleCells(fdColsList, titleCells,
//				"员工签字",
//				"default".equals(fdDateFormat)
//						? "sysAttendStatMonth.sign.br"
//						: "sysAttendStatMonth.sign.br.format",
//				"sys-attend",
//				isExportAllCols,
//				index++, fdDateFormat);
		// 请假天数细分
//		int titleIndex = colNum;
		int titleIndex = fdStatusDaysIndex+1;
		JSONObject offDetailJson = null;
		offDetailJson1 = new JSONObject();
		if (list != null && !list.isEmpty()) {
			offDetailJson = (JSONObject) PropertyUtils
					.getProperty(list.get(0), "fdOffDaysDetailJson");
		}
		boolean hasOffDeatil = false;
		boolean hasOffDays = false;
		if (offDetailJson != null && !offDetailJson.isEmpty()) {
			Iterator iterator1 = offDetailJson.keys();
			while (iterator1.hasNext()) {
				String offName = (String) iterator1.next();
				if("事假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator2 = offDetailJson.keys();
			while (iterator2.hasNext()) {
				String offName = (String) iterator2.next();
				if("病假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator3 = offDetailJson.keys();
			while (iterator3.hasNext()) {
				String offName = (String) iterator3.next();
				if("婚假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator4 = offDetailJson.keys();
			while (iterator4.hasNext()) {
				String offName = (String) iterator4.next();
				if("丧假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator5 = offDetailJson.keys();
			while (iterator5.hasNext()) {
				String offName = (String) iterator5.next();
				if("产假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator6 = offDetailJson.keys();
			while (iterator6.hasNext()) {
				String offName = (String) iterator6.next();
				if("陪产假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator7 = offDetailJson.keys();
			while (iterator7.hasNext()) {
				String offName = (String) iterator7.next();
				if("年休假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator8 = offDetailJson.keys();
			while (iterator8.hasNext()) {
				String offName = (String) iterator8.next();
				if("调休".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator9 = offDetailJson.keys();
			while (iterator9.hasNext()) {
				String offName = (String) iterator9.next();
				if("工伤假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator10 = offDetailJson.keys();
			while (iterator10.hasNext()) {
				String offName = (String) iterator10.next();
				if("产前检查假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator11 = offDetailJson.keys();
			while (iterator11.hasNext()) {
				String offName = (String) iterator11.next();
				if("哺乳假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator12 = offDetailJson.keys();
			while (iterator12.hasNext()) {
				String offName = (String) iterator12.next();
				if("产前工间休息".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator13 = offDetailJson.keys();
			while (iterator13.hasNext()) {
				String offName = (String) iterator13.next();
				if("计划生育假".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator14 = offDetailJson.keys();
			while (iterator14.hasNext()) {
				String offName = (String) iterator14.next();
				if("fdOffDays".equals(offName))
				offDetailJson1.put(offName, offDetailJson.get(offName));
			}
			Iterator iterator = offDetailJson1.keys();
			while (iterator.hasNext()) {
				String offName = (String) iterator.next();
				if (fdColsList.contains(offName) && !offName.equals("fdOffDays") || (!offName.contains(TYPE) && !offName.contains(SUFFIX)
						&& isExportAllCols)) {

					HSSFCell titleCell = titlerow.createCell(titleIndex);
					titleCell.setCellStyle(titleCellStyle);
					//请假类型 不区分按小时、按天
					if(offDetailJson.containsKey(String.format("%s%s",offName,TYPE))) {
						int type =offDetailJson.getInt(String.format("%s%s", offName, TYPE));
						if( type==1){
							//按天
							offName = offName + "\r\n" + ResourceUtil.getString(
									"sys-attend:sysAttendStatMonth.day");
						}else if( type==2){
							//按半天
							offName = offName + "\r\n" + ResourceUtil.getString(
									"sys-attend:sysAttendStatMonth.day");
						}else if( type==3){
							//按小时
							offName = offName + "\r\n" + ResourceUtil.getString(
									"sys-attend:sysAttendStatMonth.hour");
						}
					}
					titleCell.setCellValue(new HSSFRichTextString(offName));
					sheet.setColumnWidth(titleIndex, 3000);
					titleIndex++;
					hasOffDeatil = true;
				}
			}
		}
		int tmpIndex = titleIndex+1;
		int fdAbsentDaysIndex = genTitleCells1(fdColsList, titleCells,
				"fdAbsentDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdAbsentDays.br"
						: "sysAttendStatMonth.fdAbsentDays.br.format",
				"sys-attend",
				isExportAllCols, tmpIndex++, fdDateFormat);
		int fdLateCountIndex = genTitleCells1(fdColsList, titleCells,
				"fdLateCount",
				"sysAttendStatMonth.fdLateCount.br", "sys-attend",
				isExportAllCols,
				tmpIndex++, null);
		int fdLateTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdLateTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdLateTime.br"
						: "sysAttendStatMonth.fdLateTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdTripDaysIndex = genTitleCells1(fdColsList, titleCells,
				"fdTripDays",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdTripDays.br"
						: "sysAttendStatMonth.fdTripDays.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdOutgoingTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdOutgoingTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOutgoingTime.br"
						: "sysAttendStatMonth.fdOutgoingTime.br.format",
				"sys-attend",
				isExportAllCols, tmpIndex++, fdDateFormat);
		
		int fdLateExcCountIndex = genTitleCells1(fdColsList, titleCells,
				"fdLateExcCount",
				"sysAttendStatMonth.fdLateExcCount.br", "sys-attend",
				isExportAllCols, tmpIndex++, null);
		int fdLeftCountIndex = genTitleCells1(fdColsList, titleCells,
				"fdLeftCount",
				"sysAttendStatMonth.fdLeftCount.br", "sys-attend",
				isExportAllCols,
				tmpIndex++, null);
		int fdLeftTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdLeftTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdLeftTime.br"
						: "sysAttendStatMonth.fdLeftTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdLeftExcCountIndex = genTitleCells1(fdColsList, titleCells,
				"fdLeftExcCount",
				"sysAttendStatMonth.fdLeftExcCount.br", "sys-attend",
				isExportAllCols, tmpIndex++, null);
		int fdMissedExcCountIndex = genTitleCells1(fdColsList, titleCells,
				"fdMissedExcCount",
				"sysAttendStatMonth.fdMissedExcCount.br", "sys-attend",
				isExportAllCols, tmpIndex++, null);
		int fdWorkOverTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdWorkOverTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverTime.br"
						: "sysAttendStatMonth.fdWorkOverTime.br.format",
				"sys-attend",
				isExportAllCols, tmpIndex++, fdDateFormat);
		int fdOffOverTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdOffOverTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverTime.br"
						: "sysAttendStatMonth.fdOffOverTime.br.format",
				"sys-attend",
				isExportAllCols, tmpIndex++, fdDateFormat);
		int fdHolidayOverTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdHolidayOverTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverTime.br"
						: "sysAttendStatMonth.fdHolidayOverTime.br.format",
				"sys-attend",
				isExportAllCols, tmpIndex++, fdDateFormat);
		int fdOverTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdOverTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverTime.br"
						: "sysAttendStatMonth.fdOverTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdWorkOverApplyTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdWorkOverApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdWorkOverApplyTime.br"
						: "sysAttendStatMonth.fdWorkOverApplyTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdOffOverApplyTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdOffOverApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOffOverApplyTime.br"
						: "sysAttendStatMonth.fdOffOverApplyTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdHolidayOverApplyTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdHolidayOverApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdHolidayOverApplyTime.br"
						: "sysAttendStatMonth.fdHolidayOverApplyTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		int fdOverApplyTimeIndex = genTitleCells1(fdColsList, titleCells,
				"fdOverApplyTime",
				"default".equals(fdDateFormat)
						? "sysAttendStatMonth.fdOverApplyTime.br"
						: "sysAttendStatMonth.fdOverApplyTime.br.format",
				"sys-attend",
				isExportAllCols,
				tmpIndex++, fdDateFormat);
		
		if (fdColsList.contains("fdOffDays") || isExportAllCols) {
			hasOffDays = true;
			// 请假总天数
			HSSFCell titleCell = titlerow.createCell(titleIndex);
			titleCell.setCellStyle(titleCellStyle);
			String offDaysName = genMessageByFormat(
					"default".equals(fdDateFormatHoliday)
							? "sysAttendStatMonth.fdOffDays.br"
							: "sysAttendStatMonth.fdOffDays.br.format",
					"sys-attend", fdDateFormatHoliday);
			titleCell.setCellValue(new HSSFRichTextString(offDaysName.replaceAll("<br/>", "\r\n")));
		}
		// 请假和加班的表头
		List<Map<String, Object>> infos = new ArrayList<Map<String, Object>>();
		Map<String, Object> overTimeInfo = new HashMap<String, Object>();
		String[] overtimes = new String[] { "fdWorkOverTime", "fdOffOverTime",
				"fdHolidayOverTime", "fdOverTime" };
		int overtimeIndex = 0;
		int maxIndex = 0;
		for (int i = 0; i < overtimes.length; i++) {
			if (fdColsList.contains(overtimes[i])) {
				int idx = fdWorkOverTimeIndex+3;
//				int idx = fdColsList.indexOf(overtimes[i]) + 3+tmpIndex-titleIndex;
				maxIndex = Math.max(maxIndex, idx);
				overtimeIndex++;
			}
		}
		overTimeInfo.put("colStart",
				isExportAllCols ? 25 : maxIndex - overtimeIndex + 1);
		overTimeInfo.put("colEnd", isExportAllCols ? 28 : maxIndex);
		overTimeInfo.put("title", ResourceUtil
				.getString("sys-attend:sysAttendStatMonth.fdOverTime"));
		Map<String, Object> yearAmountInfo = new HashMap<String, Object>();
		String[] yearAmount = new String[] { "fdTotalDays", "fdUsedDays",
				"fdRestDays"};
		int yearAmountIndex = 0;
		int maxIndex1 = 0;
		for (int i = 0; i < yearAmount.length; i++) {
			if (fdColsList.contains(yearAmount[i])) {
				int idx = fdColsList.indexOf(yearAmount[i]) + 3;
				maxIndex1 = Math.max(maxIndex1, idx);
				yearAmountIndex++;
			}
		}
		yearAmountInfo.put("colStart",
				isExportAllCols ? 30 : maxIndex1 - yearAmountIndex + 1);
		yearAmountInfo.put("colEnd", isExportAllCols ? 32 : maxIndex1);
		yearAmountInfo.put("title", "年假休假明细");
		Map<String, Object> yearTxAmountInfo = new HashMap<String, Object>();
		String[] yearTxAmount = new String[] { "fdTxTotalDays","fdTxUsedDays","fdTxRestDays"};
		int yearTxAmountIndex = 0;
		int maxIndex2 = 0;
		for (int i = 0; i < yearTxAmount.length; i++) {
			if (fdColsList.contains(yearTxAmount[i])) {
				int idx = fdColsList.indexOf(yearTxAmount[i]) + 3;
				maxIndex2 = Math.max(maxIndex2, idx);
				yearTxAmountIndex++;
			}
		}
		yearTxAmountInfo.put("colStart",
				isExportAllCols ? 33 : maxIndex2 - yearTxAmountIndex + 1);
		yearTxAmountInfo.put("colEnd", isExportAllCols ? 35 : maxIndex2);
		yearTxAmountInfo.put("title", "调休假休假明细");
		Map<String, Object> overApplyTimeInfo = new HashMap<String, Object>();
		String[] overApplyTime = new String[] { "fdWorkOverApplyTime","fdOffOverApplyTime","fdHolidayOverApplyTime","fdOverApplyTime"};
		int overApplyTimeIndex = 0;
		int maxIndex3 = 0;
		for (int i = 0; i < overApplyTime.length; i++) {
			if (fdColsList.contains(overApplyTime[i])) {
				int idx = fdWorkOverApplyTimeIndex+3;
//				int idx = fdColsList.indexOf(overApplyTime[i]) + 3+tmpIndex-titleIndex;
				maxIndex3 = Math.max(maxIndex3, idx);
				overApplyTimeIndex++;
			}
		}
		overApplyTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex3 - overApplyTimeIndex + 1);
		overApplyTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex3);
		overApplyTimeInfo.put("title", "当月申请加班小时数");
		Map<String, Object> OverPayTimeInfo = new HashMap<String, Object>();
		String[] overPayTime = new String[] { "fdOverPayTime", "fdHolidayOverPayTime", "fdOffOverPayTime", "fdWorkOverPayTime"};
		int overPayTimeIndex = 0;
		int maxIndex4 = 0;
		for (int i = 0; i < overPayTime.length; i++) {
			if (fdColsList.contains(overPayTime[i])) {
//				int idx = fdOverPayTimeIndex + 3;
				int idx = fdColsList.indexOf(overPayTime[i]) + 3;
				maxIndex4 = Math.max(maxIndex4, idx);
				overPayTimeIndex++;
			}
		}
		OverPayTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex4 - overPayTimeIndex + 1);
		OverPayTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex4);
		OverPayTimeInfo.put("title", "实际加班费小时");
		Map<String, Object> overPayApplyTimeInfo = new HashMap<String, Object>();
		String[] overPayApplyTime = new String[] { "fdOverPayApplyTime", "fdHolidayOverPayApplyTime", "fdOffOverPayApplyTime", "fdWorkOverPayApplyTime"};
		int overPayApplyTimeIndex = 0;
		int maxIndex5 = 0;
		for (int i = 0; i < overPayApplyTime.length; i++) {
			if (fdColsList.contains(overPayApplyTime[i])) {
				int idx = fdColsList.indexOf(overPayApplyTime[i]) + 3;
				maxIndex5 = Math.max(maxIndex5, idx);
				overPayApplyTimeIndex++;
			}
		}
		overPayApplyTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex5 - overPayApplyTimeIndex + 1);
		overPayApplyTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex5);
		overPayApplyTimeInfo.put("title", "申请加班费小时");
		
		Map<String, Object> overTurnTimeInfo = new HashMap<String, Object>();
		String[] overTurnTime = new String[] { "fdOverTurnTime", "fdHolidayOverTurnTime", "fdOffOverTurnTime", "fdWorkOverTurnTime"};
		int overTurnTimeIndex = 0;
		int maxIndex6 = 0;
		for (int i = 0; i < overTurnTime.length; i++) {
			if (fdColsList.contains(overTurnTime[i])) {
				int idx = fdColsList.indexOf(overTurnTime[i]) + 3;
				maxIndex6 = Math.max(maxIndex6, idx);
				overTurnTimeIndex++;
			}
		}
		overTurnTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex6 - overTurnTimeIndex + 1);
		overTurnTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex6);
		overTurnTimeInfo.put("title", "实际调休小时");
		
		
		Map<String, Object> overTurnApplyTimeInfo = new HashMap<String, Object>();
		String[] overTurnApplyTime = new String[] { "fdWorkOverTurnApplyTime","fdOffOverTurnApplyTime","fdHolidayOverTurnApplyTime","fdOverTurnApplyTime"};
		int overTurnApplyTimeIndex = 0;
		int maxIndex7 = 0;
		for (int i = 0; i < overTurnApplyTime.length; i++) {
			if (fdColsList.contains(overTurnApplyTime[i])) {
				int idx = fdColsList.indexOf(overTurnApplyTime[i]) + 3;
				maxIndex7 = Math.max(maxIndex7, idx);
				overTurnApplyTimeIndex++;
			}
		}
		overTurnApplyTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex7 - overTurnApplyTimeIndex + 1);
		overTurnApplyTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex7);
		overTurnApplyTimeInfo.put("title", "申请调休小时");
		
		

		Map<String, Object> overRestTimeInfo = new HashMap<String, Object>();
		String[] overRestTime = new String[] { "fdOverRestTime", "fdHolidayOverRestTime", "fdOffOverRestTime", "fdWorkOverRestTime"};
		int overRestTimeIndex = 0;
		int maxIndex8 = 0;
		for (int i = 0; i < overRestTime.length; i++) {
			if (fdColsList.contains(overRestTime[i])) {
				int idx = fdColsList.indexOf(overRestTime[i]) + 3;
				maxIndex8 = Math.max(maxIndex8, idx);
				overRestTimeIndex++;
			}
		}
		overRestTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex8 - overRestTimeIndex + 1);
		overRestTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex8);
		overRestTimeInfo.put("title", "加班结转小时");


		Map<String, Object> restTurnTimeInfo = new HashMap<String, Object>();
		String[] restTurnTime = new String[] { "fdRestTurnTime", "fdHolidayRestTurnTime", "fdOffRestTurnTime", "fdWorkRestTurnTime"};
		int restTurnTimeIndex = 0;
		int maxIndex9 = 0;
		for (int i = 0; i < restTurnTime.length; i++) {
			if (fdColsList.contains(restTurnTime[i])) {
				int idx = fdColsList.indexOf(restTurnTime[i]) + 3;
				maxIndex9 = Math.max(maxIndex9, idx);
				restTurnTimeIndex++;
			}
		}
		restTurnTimeInfo.put("colStart",
				isExportAllCols ? 36 : maxIndex9 - restTurnTimeIndex + 1);
		restTurnTimeInfo.put("colEnd", isExportAllCols ? 39 : maxIndex9);
		restTurnTimeInfo.put("title", "结转调休小时");
		
		
		Map<String, Object> offDaysInfo = new HashMap<String, Object>();
		offDaysInfo.put("colStart", fdStatusDaysIndex+1);
//		offDaysInfo.put("colStart", colNum);
		offDaysInfo.put("colEnd",
				hasOffDeatil && !hasOffDays ? titleIndex - 1
						: titleIndex);
		offDaysInfo.put("title", ResourceUtil
				.getString("sys-attend:sysAttendMain.fdStatus.askforleave"));
		if (isExportAllCols || maxIndex != 0) {
			infos.add(overTimeInfo);
		}
		if (isExportAllCols || maxIndex1 != 0) {
			infos.add(yearAmountInfo);
		}
		if (isExportAllCols || maxIndex2 != 0) {
			infos.add(yearTxAmountInfo);
		}
		if (isExportAllCols || maxIndex3 != 0) {
			infos.add(overApplyTimeInfo);
		}
		if (isExportAllCols || maxIndex4 != 0) {
			infos.add(OverPayTimeInfo);
		}
		if (isExportAllCols || maxIndex5 != 0) {
			infos.add(overPayApplyTimeInfo);
		}
		if (isExportAllCols || maxIndex6 != 0) {
			infos.add(overTurnTimeInfo);
		}
		if (isExportAllCols || maxIndex7 != 0) {
			infos.add(overTurnApplyTimeInfo);
		}
		if (isExportAllCols || maxIndex8 != 0) {
			infos.add(overRestTimeInfo);
		}
		if (isExportAllCols || maxIndex9 != 0) {
			infos.add(restTurnTimeInfo);
		}
		if (isExportAllCols || colNum != titleIndex || hasOffDays) {
			infos.add(offDaysInfo);
		}
		if (infos.isEmpty()) {
			infos.add(new HashMap<String, Object>());
		}
		buildMergeHeader(sheet, titlerow, infos);
		rowIndex++;

		/* 内容行 */
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		contentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
		//每月统计的人员ID列表
		List<String> idList=new ArrayList<String>();
		int tmp = 0; 
		if (list != null && !list.isEmpty()) {
			DecimalFormat df = new DecimalFormat("#.##");
			Float convertHour = SysTimeUtil.getConvertTime();
			for (int i = 0; i < list.size(); i++) {
				Object monthModel = list.get(i);
				SysOrgPerson docCreator = (SysOrgPerson) PropertyUtils
						.getProperty(monthModel, "docCreator");
				if(docCreator!=null) {
					idList.add(docCreator.getFdId());
				}
			}
			Map<String, JSONObject> personInfoMap=getAllPersonInfo(idList);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow contentrow = sheet.createRow(rowIndex++);
				contentrow.setHeight((short) 400);
				HSSFCell[] contentcells = new HSSFCell[89];
				for (int j = 0; j < contentcells.length; j++) {
					contentcells[j] = contentrow.createCell(j);
					contentcells[j].setCellStyle(contentCellStyle);
					contentcells[j].setCellType(CellType.STRING);
				}
				Object monthModel = list.get(i);
				SysOrgPerson docCreator = (SysOrgPerson) PropertyUtils
						.getProperty(monthModel, "docCreator");
				Date fdStartTime = null;
				Date fdEndTime = null;
				Date fdMonth = null;
				if ("2".equals(fdDateType)) {
					fdStartTime = (Date) PropertyUtils
							.getProperty(monthModel, "fdStartTime");
					fdEndTime = (Date) PropertyUtils
							.getProperty(monthModel, "fdEndTime");
				} else {
					fdMonth = (Date) PropertyUtils
							.getProperty(monthModel, "fdMonth");
				}
				String fdCategoryId = (String) PropertyUtils
						.getProperty(monthModel, "fdCategoryId");
				Float fdShouldDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdShouldDays");
				Integer fdHolidays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdHolidays");

				Float fdHolidaysNew =fdHolidays.floatValue();
				Float fdWorkDateDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdWorkDateDays");
				Float fdActualDays = (Float) PropertyUtils.getProperty(monthModel, "fdActualDays");


				Float fdStatusDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdStatusDays");
				Integer fdAbsentDays = (Integer) PropertyUtils
						.getProperty(monthModel, "fdAbsentDays");
				Float fdAbsentDaysCount = (Float) PropertyUtils
						.getProperty(monthModel, "fdAbsentDaysCount");
				Float fdTripDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdTripDays");
				Float fdOffDays = (Float) PropertyUtils
						.getProperty(monthModel, "fdOffDays");
				Integer fdLateCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLateCount");
				Integer fdLateExcCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLateExcCount");
				Integer fdLateTime = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLateTime");
				Integer fdLeftCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLeftCount");
				Integer fdLeftExcCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLeftExcCount");
				Integer fdLeftTime = (Integer) PropertyUtils
						.getProperty(monthModel, "fdLeftTime");
				Integer fdOutsideCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdOutsideCount");
				Integer fdMissedCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdMissedCount");
				Integer fdMissedExcCount = (Integer) PropertyUtils
						.getProperty(monthModel, "fdMissedExcCount");
				Long fdTotalTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdTotalTime");
				Long fdOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverTime");
				Long fdWorkOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverTime");
				Long fdOffOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverTime");
				Long fdHolidayOverTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverTime");
				JSONObject fdOffDaysDetailJson = (JSONObject) PropertyUtils
						.getProperty(monthModel, "fdOffDaysDetailJson");
				Float fdOutgoingTime = (Float) PropertyUtils
						.getProperty(monthModel, "fdOutgoingTime");
				Float fdOffTime = (Float) PropertyUtils
						.getProperty(monthModel, "fdOffTimeHour");
				Long fdWorkOverPayTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverPayTime");
				Long fdOffOverPayTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverPayTime");
				Long fdHolidayOverPayTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverPayTime");
				Long fdOverPayTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverPayTime");
				Long fdWorkOverPayApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverPayApplyTime");
				Long fdWorkOverTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverTurnTime");
				Long fdOverTurnApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverTurnApplyTime");
				Long fdOffOverPayApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverPayApplyTime");
				Long fdOffOverTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverTurnTime");
				Long fdHolidayOverTurnApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverTurnApplyTime");
				Long fdHolidayOverPayApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverPayApplyTime");
				Long fdHolidayOverTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverTurnTime");
				Long fdOffOverTurnApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverTurnApplyTime");
				Long fdOverPayApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverPayApplyTime");
				Long fdOverTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverTurnTime");
				Long fdWorkOverTurnApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverTurnApplyTime");
				Long fdOverRestTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOverRestTime");
				Long fdHolidayOverRestTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverRestTime");
				Long fdOffOverRestTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverRestTime");
				Long fdWorkOverRestTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverRestTime");


				Long fdRestTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdRestTurnTime");
				Long fdHolidayRestTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayRestTurnTime");
				Long fdOffRestTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffRestTurnTime");
				Long fdWorkRestTurnTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkRestTurnTime");
				
				
				Long fdWorkOverApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdWorkOverApplyTime");
				Integer workOverApplyTime = fdWorkOverApplyTime==null ? 0:fdWorkOverApplyTime.intValue();
				String workOverApplyTimeStr = NumberUtil.roundDecimal(workOverApplyTime/60.0,2);

				Long fdOffOverApplyTime = (Long) PropertyUtils
						.getProperty(monthModel, "fdOffOverApplyTime");
				Integer offOverApplyTime = fdOffOverApplyTime==null ? 0:fdOffOverApplyTime.intValue();
				String offOverApplyTimeStr = NumberUtil.roundDecimal(offOverApplyTime/60.0,2);
				Long fdHolidayOverApplyTime =(Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverApplyTime");
				Integer holidayOverApplyTime = fdHolidayOverApplyTime==null ? 0:fdHolidayOverApplyTime.intValue();
				String holidayOverApplyTimeStr = NumberUtil.roundDecimal(holidayOverApplyTime/60.0,2);
				Long fdOverApplyTime =(Long) PropertyUtils
						.getProperty(monthModel, "fdHolidayOverApplyTime");
				Integer overApplyTime1 = fdOverApplyTime==null ? 0:fdOverApplyTime.intValue();
				String overApplyTimeStr = NumberUtil.roundDecimal(overApplyTime1/60.0,2);
				Float fdLeaveDays = (Float) PropertyUtils.getProperty(monthModel, "fdLeaveDays");
				fdLeaveDays = fdLeaveDays ==null?0F:fdLeaveDays;

				Float fdOutgoingDay = (Float) PropertyUtils.getProperty(monthModel, "fdOutgoingDay");
				fdOutgoingDay = fdOutgoingDay ==null?0F:fdOutgoingDay;
				JSONObject jsonObject = personInfoMap.get(docCreator.getFdId());
				int noIndex=0;
				if(fdColsList.contains("fdAffiliatedCompany") || isExportAllCols) {
					String fdAffiliatedCompany="";
					if(jsonObject!=null&&jsonObject.containsKey("fdAffiliatedCompany")) {
						fdAffiliatedCompany=jsonObject.getString("fdAffiliatedCompany");
					}
					contentcells[noIndex++].setCellValue(fdAffiliatedCompany);
				}
				if(fdColsList.contains("fdFirstLevelDepartment") || isExportAllCols) {
					String fdFirstLevelDepartment="";
					if(jsonObject!=null&&jsonObject.containsKey("fdFirstLevelDepartment")) {
						fdFirstLevelDepartment=jsonObject.getString("fdFirstLevelDepartment");
					}
					contentcells[noIndex++].setCellValue(fdFirstLevelDepartment);
				}
				if(fdColsList.contains("fdSecondLevelDepartment") || isExportAllCols) {
					String fdSecondLevelDepartment="";
					if(jsonObject!=null&&jsonObject.containsKey("fdSecondLevelDepartment")) {
						fdSecondLevelDepartment=jsonObject.getString("fdSecondLevelDepartment");
					}
					contentcells[noIndex++].setCellValue(fdSecondLevelDepartment);
				}
				if(fdColsList.contains("fdThirdLevelDepartment") || isExportAllCols) {
					String fdThirdLevelDepartment="";
					if(jsonObject!=null&&jsonObject.containsKey("fdThirdLevelDepartment")) {
						fdThirdLevelDepartment=jsonObject.getString("fdThirdLevelDepartment");
					}
					contentcells[noIndex++].setCellValue(fdThirdLevelDepartment);
				}


				contentcells[noIndex++]
						.setCellValue(
								docCreator.getFdIsAvailable()
										? docCreator.getFdName()
										: (docCreator.getFdName()
												+ ResourceUtil
														.getString(
																"sys-attend:sysAttendStatDetail.alreadyQuit")));
				if(fdColsList.contains("fdStaffNo") || isExportAllCols) {
					String fdStaffNo="";
					if(jsonObject!=null&&jsonObject.containsKey("fdStaffNo")) {
						fdStaffNo=jsonObject.getString("fdStaffNo");
					}
					contentcells[noIndex++].setCellValue(fdStaffNo);
				}
				contentcells[noIndex++]
						.setCellValue(
								docCreator == null
										? ""
										: docCreator.getFdLoginName());

				if (cateIndex > 0) {
					String fdCategoryName = "";
					if (StringUtil.isNotNull(fdCategoryId)) {
						SysAttendCategory cate = CategoryUtil.getCategoryById(fdCategoryId);
						if (cate != null) {
							fdCategoryName = cate.getFdName();
						}
					}
					noIndex++;
					contentcells[cateIndex].setCellValue(fdCategoryName);
				}
				if(fdColsList.contains("fdOrgPost") || isExportAllCols) {
					String fdOrgPost="";
					if(jsonObject!=null&&jsonObject.containsKey("fdOrgPost")) {
						fdOrgPost=jsonObject.getString("fdOrgPost");
					}
					contentcells[noIndex++].setCellValue(fdOrgPost);
				}
				if(fdColsList.contains("fdStaffingLevel.fdName") || isExportAllCols) {
					String fdStaffingLevel="";
					if(jsonObject!=null&&jsonObject.containsKey("fdStaffingLevel")) {
						fdStaffingLevel=jsonObject.getString("fdStaffingLevel");
					}
					contentcells[noIndex++].setCellValue(fdStaffingLevel);
				}

				if (entryIndex > 0) {
					String fdEntryTime="";
					if(jsonObject!=null&&jsonObject.containsKey("fdEntryTime")) {
						fdEntryTime=jsonObject.getString("fdEntryTime");
						if(StringUtil.isNotNull(fdEntryTime)&&!"null".equals(fdEntryTime)) {
							Date entryTime=DateUtil.convertStringToDate(fdEntryTime, DateUtil.TYPE_DATE, null);
							fdEntryTime=DateUtil.convertDateToString(entryTime, DateUtil.TYPE_DATE, null);
						}
					}
					noIndex++;
					contentcells[entryIndex].setCellValue(fdEntryTime);
				}
				if(fdColsList.contains("fdResignationDate") || isExportAllCols) {
					String fdResignationDate="";
					if(jsonObject!=null&&jsonObject.containsKey("fdResignationDate")) {
						fdResignationDate=jsonObject.getString("fdResignationDate");
					}
					contentcells[noIndex++].setCellValue(fdResignationDate);
				}
				String dateStr = "";
				if ("2".equals(fdDateType)) {
					dateStr = DateUtil.convertDateToString(fdStartTime,
							DateUtil.TYPE_DATE, null) + "~"
							+ DateUtil.convertDateToString(fdEndTime,
									DateUtil.TYPE_DATE, null);
				} else {
					dateStr = DateUtil.convertDateToString(fdMonth, "yyyy-MM");
				}
//				contentcells[typeIndex].setCellValue(dateStr);
				contentcells[noIndex++].setCellValue(dateStr);
				if(fdColsList.contains("fdStaffType") || isExportAllCols) {
					String fdStaffType="";
					if(jsonObject!=null&&jsonObject.containsKey("fdStaffType")) {
						fdStaffType=jsonObject.getString("fdStaffType");
					}
					contentcells[noIndex++].setCellValue(fdStaffType);
				}
				if(fdColsList.contains("fdTotalDays") || isExportAllCols) {
					String fdTotalDays="";
					if(jsonObject!=null&&jsonObject.containsKey("fdTotalDays")) {
						fdTotalDays=jsonObject.getString("fdTotalDays");
					}
					contentcells[fdTotalDaysIndex].setCellValue(fdTotalDays);
				}
				if(fdColsList.contains("fdUsedDays") || isExportAllCols) {
					String fdUsedDays="";
					if(jsonObject!=null&&jsonObject.containsKey("fdUsedDays")) {
						fdUsedDays=jsonObject.getString("fdUsedDays");
					}
					contentcells[fdUsedDaysIndex].setCellValue(fdUsedDays);
				}
				if(fdColsList.contains("fdRestDays") || isExportAllCols) {
					String fdRestDays="";
					if(jsonObject!=null&&jsonObject.containsKey("fdRestDays")) {
						fdRestDays=jsonObject.getString("fdRestDays");
					}
					contentcells[fdRestDaysIndex].setCellValue(fdRestDays);
				}
				if(fdColsList.contains("fdTxTotalDays") || isExportAllCols) {
					String fdTxTotalDays="";
					if(jsonObject!=null&&jsonObject.containsKey("fdTxTotalDays")) {
						fdTxTotalDays=jsonObject.getString("fdTxTotalDays");
					}
					contentcells[fdTxTotalDaysIndex].setCellValue(fdTxTotalDays);
				}
				if(fdColsList.contains("fdTxUsedDays") || isExportAllCols) {
					String fdTxUsedDays="";
					if(jsonObject!=null&&jsonObject.containsKey("fdTxUsedDays")) {
						fdTxUsedDays=jsonObject.getString("fdTxUsedDays");
					}
					contentcells[fdTxUsedDaysIndex].setCellValue(fdTxUsedDays);
				}
				if(fdColsList.contains("fdTxRestDays") || isExportAllCols) {
					String fdTxRestDays="";
					if(jsonObject!=null&&jsonObject.containsKey("fdTxRestDays")) {
						fdTxRestDays=jsonObject.getString("fdTxRestDays");
					}
					contentcells[fdTxRestDaysIndex].setCellValue(fdTxRestDays);
				}
				if(fdColsList.contains("fdWorkOverApplyTime") || isExportAllCols) {
					contentcells[fdWorkOverApplyTimeIndex].setCellValue(workOverApplyTimeStr);
				}
				if(fdColsList.contains("fdOffOverApplyTime") || isExportAllCols) {
					contentcells[fdOffOverApplyTimeIndex].setCellValue(offOverApplyTimeStr);
				}
				if(fdColsList.contains("fdHolidayOverApplyTime") || isExportAllCols) {
					contentcells[fdHolidayOverApplyTimeIndex].setCellValue(holidayOverApplyTimeStr);
				}
				if(fdColsList.contains("fdOverApplyTime") || isExportAllCols) {
					contentcells[fdOverApplyTimeIndex].setCellValue(overApplyTimeStr);
				}
				if(fdColsList.contains("enterDays") || isExportAllCols) {
					String enterDays="";
					if(jsonObject!=null&&jsonObject.containsKey("enterDays")) {
						enterDays=jsonObject.getString("enterDays");
					}
					contentcells[fdEnterDaysIndex].setCellValue(enterDays);
				}
				if(fdColsList.contains("leaveDays") || isExportAllCols) {
					String leaveDays="";
					if(jsonObject!=null&&jsonObject.containsKey("leaveDays")) {
						leaveDays=jsonObject.getString("leaveDays");
					}
					contentcells[fdLeaveDaysIndex].setCellValue(leaveDays);
				}
				if (deptIndex > 0) {
					contentcells[deptIndex]
							.setCellValue(
									docCreator.getFdParent() == null
											? ""
											: docCreator
													.getFdParent().getFdName());
				}

				if (fdShouldDaysIndex > 0) {
					if ("hour".equals(fdDateFormat)) {
						Float shouldDays = convertHour.floatValue()
								* fdShouldDays.floatValue();
						contentcells[fdShouldDaysIndex].setCellValue(
								Double.parseDouble(df.format(shouldDays)));
					} else {
						contentcells[fdShouldDaysIndex]
								.setCellValue(NumberUtil.roundDecimal(fdShouldDays,2));
					}
				}
				if (fdHolidaysIndex > 0) {
					fdHolidaysNew=fdHolidaysNew==null?fdShouldDays:fdHolidays+fdShouldDays;
					if ("hour".equals(fdDateFormat)) {
						Float holidays =convertHour.floatValue() * fdHolidaysNew;
						contentcells[fdHolidaysIndex].setCellValue(
								Double.parseDouble(df.format(holidays)));
					} else {
						contentcells[fdHolidaysIndex]
								.setCellValue(NumberUtil.roundDecimal(fdHolidaysNew,2));
					}
				}
				if (fdWorkDateDaysIndex > 0) {
					if ("hour".equals(fdDateFormat)) {
						Float workDateDays=fdWorkDateDays == null ? 0 : fdWorkDateDays;
						Float workDays = convertHour.floatValue()
								* workDateDays.floatValue();
						contentcells[fdWorkDateDaysIndex].setCellValue(
								Double.parseDouble(df.format(workDays)));
					} else {
						contentcells[fdWorkDateDaysIndex].
								setCellValue(fdWorkDateDays == null ? "0" : NumberUtil.roundDecimal(fdWorkDateDays,2));
					}
				}
				if (fdActualDaysIndex > 0) {
					if ("hour".equals(fdDateFormat)) {
						Float actualDays = convertHour.floatValue() * fdActualDays.floatValue();
						contentcells[fdActualDaysIndex].setCellValue(
								Double.parseDouble(df.format(actualDays)));
					} else {
						contentcells[fdActualDaysIndex]
								.setCellValue(NumberUtil.roundDecimal(fdActualDays,2));
					}
				}
				if (fdTotalTimeIndex > 0) {
					Float toTime = fdTotalTime == null ? 0
							: fdTotalTime.floatValue() / 60;
					if ("day".equals(fdDateFormat)) {
						double timed = toTime
								/ convertHour.doubleValue();
						contentcells[fdTotalTimeIndex].setCellValue(
								Double.parseDouble(df.format(timed)));
					} else if ("hour".equals(fdDateFormat)) {
						contentcells[fdTotalTimeIndex].setCellValue(
								Double.parseDouble(df.format(toTime)));
					} else {
//						contentcells[fdTotalTimeIndex]
//								.setCellValue(SysTimeUtil.formatLeaveTimeStr(0,
//										toTime));
						contentcells[fdTotalTimeIndex]
								.setCellValue(String.format("%.2f", toTime.floatValue()));
					}

				}
				if (fdStatusDaysIndex > 0) {
					fdStatusDays = (fdStatusDays ==null ) ? 0F : fdStatusDays;
					if ("hour".equals(fdDateFormat)) {
						Float statusDaysf = convertHour.floatValue() * fdStatusDays;
						contentcells[fdStatusDaysIndex].setCellValue(
								Double.parseDouble(df.format(statusDaysf)));
					} else {
						contentcells[fdStatusDaysIndex]
								.setCellValue(NumberUtil.roundDecimal(fdStatusDays,2));
					}
				}
				if (fdAbsentDaysIndex > 0) {
					if ("hour".equals(fdDateFormat)) {
						Float absentDays = fdAbsentDaysCount == null
								? (fdAbsentDays == null ? 0 : fdAbsentDays)
								: Float.parseFloat(
										df.format(fdAbsentDaysCount));
						Float absentDaysf = convertHour.floatValue()
								* absentDays;
						contentcells[fdAbsentDaysIndex].setCellValue(
								Double.parseDouble(df.format(absentDaysf)));
					} else {
						contentcells[fdAbsentDaysIndex].setCellValue(
								fdAbsentDaysCount == null
										? (fdAbsentDays == null ? 0
												: fdAbsentDays)
										: Float.parseFloat(
												df.format(fdAbsentDaysCount)));
					}
				}
				if (fdTripDaysIndex > 0) {
					Float tripDays = fdTripDays == null ? 0
							: fdTripDays;
					if ("hour".equals(fdDateFormat)) {
						Float tripDaysf = convertHour * tripDays;
						contentcells[fdTripDaysIndex].setCellValue(
								Double.parseDouble(df.format(tripDaysf)));
					} else {
						contentcells[fdTripDaysIndex].setCellValue(
								Double.parseDouble(df.format(tripDays)));
					}
				}
				if (fdOutgoingTimeIndex > 0) {
					Float outgoingTime = fdOutgoingTime == null ? 0F : fdOutgoingTime;
					if ("day".equals(fdDateFormat)) {
						contentcells[fdOutgoingTimeIndex].setCellValue(NumberUtil.roundDecimal(fdOutgoingDay,2));
					} else if ("hour".equals(fdDateFormat)) {
						contentcells[fdOutgoingTimeIndex].setCellValue(Double.parseDouble(df.format(outgoingTime)));
					} else {
						//默认按照天显示
						contentcells[fdOutgoingTimeIndex].setCellValue(NumberUtil.roundDecimal(fdOutgoingDay,2));
					}
				}
				if (fdLateCountIndex > 0) {
					contentcells[fdLateCountIndex]
							.setCellValue(fdLateCount == null
									? 0 : fdLateCount.intValue());
				}
				if (fdLateTimeIndex > 0) {
					double lateTime = fdLateTime == null ? 0
							: fdLateTime.doubleValue() / 60;
					if ("day".equals(fdDateFormat)) {
						double lateTimef = lateTime / convertHour.floatValue();
						contentcells[fdLateTimeIndex].setCellValue(
								Double.parseDouble(df.format(lateTimef)));
					} else if ("hour".equals(fdDateFormat)) {
						contentcells[fdLateTimeIndex].setCellValue(
								Double.parseDouble(df.format(lateTime)));
					} else {
						contentcells[fdLateTimeIndex]
								.setCellValue(fdLateTime == null
										? 0 : fdLateTime.intValue());
					}
				}
				if (fdLateExcCountIndex > 0) {
					contentcells[fdLateExcCountIndex]
							.setCellValue(fdLateExcCount == null
									? 0 : fdLateExcCount.intValue());
				}
				if (fdLeftCountIndex > 0) {
					contentcells[fdLeftCountIndex]
							.setCellValue(fdLeftCount == null
									? 0 : fdLeftCount.intValue());
				}
				if (fdLeftTimeIndex > 0) {
					double leftTime = fdLeftTime == null ? 0
							: fdLeftTime.doubleValue() / 60;
					if ("day".equals(fdDateFormat)) {
						double leftTimef = leftTime / convertHour.doubleValue();
						contentcells[fdLeftTimeIndex].setCellValue(
								Double.parseDouble(df.format(leftTimef)));
					} else if ("hour".equals(fdDateFormat)) {
						contentcells[fdLeftTimeIndex].setCellValue(
								Double.parseDouble(df.format(leftTime)));
					} else {
						contentcells[fdLeftTimeIndex]
								.setCellValue(fdLeftTime == null
										? 0 : fdLeftTime.intValue());
					}
				}
				if (fdLeftExcCountIndex > 0) {
					contentcells[fdLeftExcCountIndex]
							.setCellValue(fdLeftExcCount == null
									? 0 : fdLeftExcCount.intValue());
				}
//				if (fdOutsideCountIndex > 0) {
//					contentcells[fdOutsideCountIndex]
//							.setCellValue(fdOutsideCount == null
//									? 0 : fdOutsideCount.intValue());
//				}
//				if (fdMissedCountIndex > 0) {
//					contentcells[fdMissedCountIndex]
//							.setCellValue(fdMissedCount == null
//									? 0 : fdMissedCount.intValue());
//				}
				if (fdMissedExcCountIndex > 0) {
					contentcells[fdMissedExcCountIndex]
							.setCellValue(fdMissedExcCount == null
									? 0
									: fdMissedExcCount.intValue());
				}
				if (fdWorkOverTimeIndex > 0) {
					genCellValueMinToDay(fdDateFormat, fdWorkOverTimeIndex, df,
							convertHour, contentcells, fdWorkOverTime);
				}
				if (fdOffOverTimeIndex > 0) {
					genCellValueMinToDay(fdDateFormat, fdOffOverTimeIndex, df,
							convertHour, contentcells, fdOffOverTime);
				}
				if (fdHolidayOverTimeIndex > 0) {
					genCellValueMinToDay(fdDateFormat, fdHolidayOverTimeIndex,
							df, convertHour, contentcells, fdHolidayOverTime);
				}
				if (fdOverTimeIndex > 0) {
					genCellValueMinToDay(fdDateFormat, fdOverTimeIndex, df,
							convertHour, contentcells, fdOverTime);
				}
//
//				if (fdTotalDaysIndex > 0) {
//					genCellValueMinToDay(fdDateFormat, fdTotalDaysIndex, df,
//							convertHour, contentcells, fdTotalDays);
//				}
				if(fdWorkOverPayTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdWorkOverPayTimeIndex, df, convertHour, contentcells, fdWorkOverPayTime);
				}

				if(fdOffOverPayTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOffOverPayTimeIndex, df, convertHour, contentcells, fdOffOverPayTime);
				}

				if(fdHolidayOverPayTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayOverPayTimeIndex, df, convertHour, contentcells, fdHolidayOverPayTime);
				}

				if(fdOverPayApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOverPayApplyTimeIndex, df, convertHour, contentcells, fdOverPayApplyTime);
				}
				if(fdOverPayTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOverPayTimeIndex, df, convertHour, contentcells, fdOverPayTime);
				}
				
				if(fdOverTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOverTurnTimeIndex, df, convertHour, contentcells, fdOverTurnTime);
				}
				if(fdWorkOverTurnApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdWorkOverTurnApplyTimeIndex, df, convertHour, contentcells, fdWorkOverTurnApplyTime);
				}
				if(fdOverRestTimeIndex   > 0){
					genCellValueMinToDay(fdDateFormat, fdOverRestTimeIndex, df, convertHour, contentcells, fdOverRestTime);
				}

				if(fdHolidayOverRestTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayOverRestTimeIndex, df, convertHour, contentcells, fdHolidayOverRestTime);
				}

				if(fdOffOverRestTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOffOverRestTimeIndex, df, convertHour, contentcells, fdOffOverRestTime);
				}
				if(fdWorkOverRestTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdWorkOverRestTimeIndex, df, convertHour, contentcells, fdWorkOverRestTime);
				}
				
				
				
				if(fdRestTurnTimeIndex   > 0){
					genCellValueMinToDay(fdDateFormat, fdRestTurnTimeIndex, df, convertHour, contentcells, fdRestTurnTime);
				}

				if(fdHolidayRestTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayRestTurnTimeIndex, df, convertHour, contentcells, fdHolidayRestTurnTime);
				}

				if(fdOffRestTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOffRestTurnTimeIndex, df, convertHour, contentcells, fdOffRestTurnTime);
				}
				if(fdWorkRestTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdWorkRestTurnTimeIndex, df, convertHour, contentcells, fdWorkRestTurnTime);
				}
				
				
				
				if(fdWorkOverPayApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdWorkOverPayApplyTimeIndex, df, convertHour, contentcells, fdWorkOverPayApplyTime);
				}

				if(fdWorkOverTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdWorkOverTurnTimeIndex, df, convertHour, contentcells, fdWorkOverTurnTime);
				}

				if(fdOverTurnApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOverTurnApplyTimeIndex, df, convertHour, contentcells, fdOverTurnApplyTime);
				}
				if(fdOffOverPayApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOffOverPayApplyTimeIndex, df, convertHour, contentcells, fdOffOverPayApplyTime);
				}

				if(fdOffOverTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOffOverTurnTimeIndex, df, convertHour, contentcells, fdOffOverTurnTime);
				}
				if(fdHolidayOverTurnApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayOverTurnApplyTimeIndex, df, convertHour, contentcells, fdHolidayOverTurnApplyTime);
				}
				if(fdHolidayOverPayApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayOverPayApplyTimeIndex, df, convertHour, contentcells, fdHolidayOverPayApplyTime);
				}
				if(fdHolidayOverTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayOverTurnTimeIndex, df, convertHour, contentcells, fdHolidayOverTurnTime);
				}

				if(fdHolidayOverTurnTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdHolidayOverTurnTimeIndex, df, convertHour, contentcells, fdHolidayOverTurnTime);
				}
				if(fdOffOverTurnApplyTimeIndex > 0){
					genCellValueMinToDay(fdDateFormat, fdOffOverTurnApplyTimeIndex, df, convertHour, contentcells, fdOffOverTurnApplyTime);
				}
				
				// 请假天数细分
				int colIndex = fdStatusDaysIndex+1;
//				int colIndex = colNum;
//				if (fdOffDaysDetailJson != null
//						&& !fdOffDaysDetailJson.isEmpty()) {
					if (offDetailJson1 != null
							&& !offDetailJson1.isEmpty()) {
					Iterator iterator = offDetailJson1.keys();
//					Iterator iterator = fdOffDaysDetailJson.keys();
					while (iterator.hasNext()) {
						String offName = (String) iterator.next();
						if (fdColsList.contains(offName) || (!offName.contains(TYPE) && !offName.contains(SUFFIX) && isExportAllCols)) {
							String offValueStr = fdOffDaysDetailJson.getString(offName);
							HSSFCell contentcell = contentrow.createCell(colIndex);
							contentcell.setCellStyle(contentCellStyle);
							try {
								/**假期明细，不根据 小时和天转换 */
								contentcell.setCellValue(String.format("%2.f", Float.parseFloat(offValueStr)));
							} catch (Exception e) {
								contentcell.setCellValue(offValueStr);
								/**假期明细，不根据 小时和天转换
								try {
									if ("hour".equals(fdDateFormat)) {
										double _offValue = Double
												.parseDouble(offValue)
												* convertHour.doubleValue();
										contentcell.setCellValue(
												Double.parseDouble(
														df.format(_offValue)));
									} else if ("day".equals(fdDateFormat)) {
										double _offValue = Double
												.parseDouble(offValue);
										contentcell.setCellValue(
												Double.parseDouble(
														df.format(_offValue)));
									} else {
										contentcell.setCellValue(offValueStr);
									}
								} catch (Exception ex) {
									contentcell.setCellValue(offValueStr);
								}
								 */
							}
							sheet.setColumnWidth(colIndex, 3000);
							colIndex++;
						}
					}
				}
				// 请假总天数
//				if (fdColsList.contains("fdOffDays") || isExportAllCols) {
//					HSSFCell contentcell = contentrow.createCell(colIndex);
//					contentcell.setCellStyle(contentCellStyle);
//					if(fdLeaveDays ==null || fdLeaveDays ==0) {
//						//兼容历史数据
//						fdOffDays = fdOffDays == null ? 0 : fdOffDays;
//						fdOffTime = fdOffTime == null ? 0 : fdOffTime;
//						String offText = "";
//						if ("day".equals(fdDateFormatHoliday)) {
//							Float _fdOffDays = fdOffTime / convertHour;
//							offText = df.format(fdOffDays + _fdOffDays);
//						} else if ("hour".equals(fdDateFormatHoliday)) {
//							Float _fdOffTime = fdOffDays * convertHour;
//							offText = df.format(fdOffTime + _fdOffTime);
//						} else {
//							offText = SysTimeUtil.formatLeaveTimeStr(fdOffDays,
//									fdOffTime);
//						}
//						contentcell.setCellValue(offText);
//					}else{
//						if ("hour".equals(fdDateFormatHoliday)) {
//							//请假的总小时数
//							contentcell.setCellValue(fdOffTime);
//						}else {
//							//直接显示汇总的 请假天数。不作转换
//							contentcell.setCellValue(NumberUtil.roundDecimal(fdLeaveDays, 3));
//						}
//					}
//				}
				sheet.setColumnWidth(colIndex, 3000);
//				colIndex++;
				
				// 生成每日明细
//				tmp=createStatDateDetailExcel1(monthModel, request, i,
//						colIndex, titlerow, contentrow, sheet,
//						titleCellStyle, contentCellStyle, workbook,
//						cStylesBlue,cStylesRed
//						);
				tmp=createStatDateDetailExcel1(monthModel, request, i,
						maxIndex3+1, titlerow, contentrow, sheet,
						titleCellStyle, contentCellStyle, workbook,
						cStylesBlue,cStylesRed
						);
			}

		}
		HSSFCell titleCell1 = titlerow.createCell(tmp++);
		titleCell1.setCellStyle(titleCellStyle);
		titleCell1.setCellValue(new HSSFRichTextString("备注"));
		HSSFCell titleCell11 = titlerow.createCell(tmp++);
		titleCell11.setCellStyle(titleCellStyle);
		titleCell11.setCellValue(new HSSFRichTextString("员工签字"));
		try{
		com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, 
				new CellRangeAddress(0, 1, tmp-1, tmp-1));
		}catch(Exception e){
			
		}
		try{
			com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, 
					new CellRangeAddress(0, 1, tmp-2, tmp-2));
			}catch(Exception e){
				
			}
		if(logger.isDebugEnabled()){
			logger.debug("每月报表生成耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
		}
		// 生成原始记录表
		alltime =System.currentTimeMillis();
		buildRecordSheet(workbook, request,idList);
		if(logger.isDebugEnabled()){
			logger.debug("生成原始每日打卡记录耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000);
		}
		return workbook;
	}

	private void genCellValueMinToDay(String fdDateFormat, int index,
			DecimalFormat df, Float convertHour, HSSFCell[] contentcells,
			Long fdTime) {
		Float toTime = fdTime == null ? 0
				: fdTime.floatValue() / 60;
		if ("day".equals(fdDateFormat)) {
			double timed = toTime
					/ convertHour.doubleValue();
			contentcells[index].setCellValue(
					Double.parseDouble(df.format(timed)));
		} else if ("hour".equals(fdDateFormat)) {
			contentcells[index].setCellValue(
					Double.parseDouble(df.format(toTime)));
		} else {
//			contentcells[index].setCellValue(SysTimeUtil.formatLeaveTimeStr(0,
//					toTime.floatValue()));
			contentcells[index].setCellValue(String.format("%.2f", toTime.floatValue())
					);
		}
	}

	/**
	 * 获取列表标题
	 * 
	 */
	private int genTitleCells(List<String> fdColsList, HSSFCell[] titleCells,
			String columnName, String messageKey, String bundle,
			boolean isExportAllCols,
			int index, String fdDateFormat) {
		int colIdx = -1;
		if (fdColsList.contains(columnName) || isExportAllCols) {
			colIdx = isExportAllCols ? index
					: fdColsList.indexOf(columnName) + 3;
			String message = genMessageByFormat(messageKey, bundle,
					fdDateFormat);
			titleCells[colIdx].setCellValue(new HSSFRichTextString(
					message.replaceAll("<br/>", "\r\n")));
		}

		return colIdx;
	}
	private int genTitleCells1(List<String> fdColsList, HSSFCell[] titleCells,
			String columnName, String messageKey, String bundle,
			boolean isExportAllCols,
			int index, String fdDateFormat) {
		int colIdx = -1;
		if (fdColsList.contains(columnName) || isExportAllCols) {
			colIdx =index
					;
			String message = genMessageByFormat(messageKey, bundle,
					fdDateFormat);
			titleCells[colIdx].setCellValue(new HSSFRichTextString(
					message.replaceAll("<br/>", "\r\n")));
		}

		return colIdx;
	}
	private String genMessageByFormat(String messageKey, String bundle,
			String fdDateFormat) {
		String message = "";
		if ("day".equals(fdDateFormat)) {
			message = ResourceUtil.getString(messageKey, bundle,
					UserUtil.getKMSSUser().getLocale(),
					new Object[] { ResourceUtil
							.getString(
									"sys-attend:sysAttendStatMonth.day") });
		} else if ("hour".equals(fdDateFormat)) {
			message = ResourceUtil.getString(messageKey, bundle,
					UserUtil.getKMSSUser().getLocale(),
					new Object[] { ResourceUtil
							.getString(
									"sys-attend:sysAttendStatMonth.hour") });
		} else {
			message = ResourceUtil.getString(messageKey, bundle);
		}
		return message;
	}
	// 增加每日明细导出
	private int createStatDateDetailExcel1(Object monthModel,
			RequestContext request, int rowIndex, int currentColNums,
			HSSFRow titlerow,
			HSSFRow contentrow, HSSFSheet sheet, HSSFCellStyle titleCellStyle,
			HSSFCellStyle contentCellStyle, HSSFWorkbook workbook, HSSFCellStyle cStylesBlue,HSSFCellStyle cStylesRed )
			throws Exception {
		Map<String, List> map = (Map<String, List>) request
				.getAttribute("statMap");
		
		if (stataicStatMap == null) {
			return currentColNums;
		}
		String fdId = (String) PropertyUtils.getProperty(monthModel, "fdId");
		List<JSONObject> list = (List<JSONObject>) stataicStatMap.get(fdId);
		int colIndex = currentColNums;
		

		 
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = list.get(i);
			String title = json.getString("title");
			String value = AttendUtil.getStatStatusRender(json);
			if (rowIndex == 0) {
				HSSFCell titleCell = titlerow
						.createCell(colIndex);
				titleCell.setCellStyle(titleCellStyle);
				titleCell.setCellValue(title);
//				
//				if(currentColNums < colIndex) {
//					com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, 
//							new CellRangeAddress(0, 1, colIndex, colIndex));
//				}
			}
			HSSFCell contentcell = contentrow.createCell(colIndex);
			contentCellStyle.setWrapText(true);
			contentcell.setCellStyle(contentCellStyle);
			String cellValue = json.get("value").toString();
			contentcell.setCellValue(value);
			if((cellValue.indexOf("04") > -1)||(cellValue.indexOf("05") > -1)||(cellValue.indexOf("06") > -1)|| "13".equals(cellValue)||
					(cellValue.indexOf("14") > -1)||(cellValue.indexOf("15") > -1)||(cellValue.indexOf(",13") > -1)){
				contentcell.setCellStyle(cStylesBlue);
			}
			else if (",03".equals(cellValue) || "03".equals(cellValue)
					|| (cellValue.indexOf("9") > -1)
					|| (cellValue.indexOf("12") > -1
					&& cellValue.indexOf("212") < 0)
					||
					(cellValue.indexOf("7") > -1)||(cellValue.indexOf("8") > -1)||(cellValue.indexOf("10") > -1)
					|| (cellValue.indexOf("11") > -1
					&& cellValue.indexOf("211") < 0)) {
				//设置字体颜色
				contentcell.setCellStyle(cStylesRed);
			}
			sheet.setColumnWidth(colIndex, 3000);
			colIndex = colIndex + 1;
		}
		return colIndex;
	}
	
	// 增加每日明细导出
	private void createStatDateDetailExcel(Object monthModel,
			RequestContext request, int rowIndex, int currentColNums,
			HSSFRow titlerow,
			HSSFRow contentrow, HSSFSheet sheet, HSSFCellStyle titleCellStyle,
			HSSFCellStyle contentCellStyle, HSSFWorkbook workbook, HSSFCellStyle cStylesBlue,HSSFCellStyle cStylesRed )
			throws Exception {
		Map<String, List> map = (Map<String, List>) request
				.getAttribute("statMap");
		if (map == null) {
			return;
		}
		String fdId = (String) PropertyUtils.getProperty(monthModel, "fdId");
		List<JSONObject> list = (List<JSONObject>) map.get(fdId);
		int colIndex = currentColNums;
		

		 
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = list.get(i);
			String title = json.getString("title");
			String value = AttendUtil.getStatStatusRender(json);
			if (rowIndex == 0) {
				HSSFCell titleCell = titlerow
						.createCell(colIndex);
				titleCell.setCellStyle(titleCellStyle);
				titleCell.setCellValue(title);
				if(currentColNums < colIndex) {
					com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, 
							new CellRangeAddress(0, 1, colIndex, colIndex));
				}
			}
			HSSFCell contentcell = contentrow.createCell(colIndex);
			contentCellStyle.setWrapText(true);
			contentcell.setCellStyle(contentCellStyle);
			String cellValue = json.get("value").toString();
			contentcell.setCellValue(value);
			if((cellValue.indexOf("04") > -1)||(cellValue.indexOf("05") > -1)||(cellValue.indexOf("06") > -1)|| "13".equals(cellValue)||
					(cellValue.indexOf("14") > -1)||(cellValue.indexOf("15") > -1)||(cellValue.indexOf(",13") > -1)){
				contentcell.setCellStyle(cStylesBlue);
			}
			else if (",03".equals(cellValue) || "03".equals(cellValue)
					|| (cellValue.indexOf("9") > -1)
					|| (cellValue.indexOf("12") > -1
					&& cellValue.indexOf("212") < 0)
					||
					(cellValue.indexOf("7") > -1)||(cellValue.indexOf("8") > -1)||(cellValue.indexOf("10") > -1)
					|| (cellValue.indexOf("11") > -1
					&& cellValue.indexOf("211") < 0)) {
				//设置字体颜色
				contentcell.setCellStyle(cStylesRed);
			}
			sheet.setColumnWidth(colIndex, 3000);
			colIndex = colIndex + 1;
		}
	}

	public void buildRecordSheet(HSSFWorkbook workbook,
			RequestContext request,List<String> idsList) throws Exception {
		//人员列表都不存在，则不查询考勤列表
		if(CollectionUtils.isEmpty(idsList)){
			return;
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer("1=1 ");
		String fdDateType = request.getParameter("fdDateType");
		String fdMonth = request.getParameter("fdMonth");
		String fdStartTime = request.getParameter("fdStartTime");
		String fdEndTime = request.getParameter("fdEndTime");
		Date fdStartDate =null;
		if ("2".equals(fdDateType)) {
			if (StringUtil.isNotNull(fdStartTime)
					&& StringUtil.isNotNull(fdEndTime)) {
				// 按日期区间
				whereBlock.append(
						" and sysAttendMain.docCreateTime>=:fdStartTime and sysAttendMain.docCreateTime<:fdEndTime");
				fdStartDate = DateUtil.convertStringToDate(fdStartTime,
						DateUtil.TYPE_DATETIME, request.getLocale());
				Date fdEndDate = DateUtil.convertStringToDate(fdEndTime,
						DateUtil.TYPE_DATETIME, request.getLocale());
				hqlInfo.setParameter("fdStartTime",
						AttendUtil.getDate(fdStartDate, 0));
				hqlInfo.setParameter("fdEndTime",
						AttendUtil.getDate(fdEndDate, 1));
			}
		} else {
			if (StringUtil.isNotNull(fdMonth)) {
				// 按月
				whereBlock.append(
						" and sysAttendMain.docCreateTime>=:fdStartTime and sysAttendMain.docCreateTime<:fdEndTime");
				fdStartDate = DateUtil.convertStringToDate(fdMonth,
						DateUtil.TYPE_DATETIME, request.getLocale());
				hqlInfo.setParameter("fdStartTime",
						AttendUtil.getMonth(fdStartDate, 0));
				hqlInfo.setParameter("fdEndTime",
						AttendUtil.getMonth(fdStartDate, 1));
			}
		}

		// 查询对象
		//String fdTargetType = request.getParameter("fdTargetType");
		//String fdCategoryIds = request.getParameter("fdCategoryIds");
		//String fdDeptIds = request.getParameter("fdDeptIds");

		/**
		 * 每月统计存在的人才会查询考勤记录表
		 */
		whereBlock.append(
				" and (" + HQLUtil.buildLogicIN(
						"sysAttendMain.docCreator.fdId",
						idsList)+ " and sysAttendMain.fdWorkType is not null) ");
		whereBlock.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
		/*// 按部门查询
		if (StringUtil.isNull(fdTargetType) || "1".equals(fdTargetType)) {
			if (StringUtil.isNotNull(fdDeptIds)) {
				List<String> orgIds = ArrayUtil
						.convertArrayToList(fdDeptIds.split(";"));
				List<String> personIds =idsList;
				if (!personIds.isEmpty()) {

					whereBlock.append(
							" or (" + AttendUtil.buildLikeHql(
									"sysAttendMain.docCreatorHId",
									orgIds)
									+ " and sysAttendMain.docCreator.fdIsAvailable=:fdIsAvailable0))");
					hqlInfo.setParameter("fdIsAvailable0", false);
					hqlInfo.setParameter("hisCategoryfdType", 1);
				} else {
					throw new NoRecordException();
				}

			}
			// 按考勤组查询
		} else if ("2".equals(fdTargetType)) {
			setCategoryWhere(fdCategoryIds,whereBlock,"sysAttendMain.fdHisCategory.fdId");
		}*/

		String fdIsQuit = request.getParameter("fdIsQuit");
		if (!"true".equals(fdIsQuit)) {
			whereBlock.append(
					" and sysAttendMain.docCreator.fdIsAvailable=:isAvailable1");
			hqlInfo.setParameter("isAvailable1", true);
		}
		whereBlock.append(
				" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setOrderBy(
				"sysAttendMain.docCreator.fdNamePinYin asc,sysAttendMain.docCreateTime asc");
		// 防止数据过多，此处分页处理,根据系统admin.do中的rowPage最大来。所以这里设置的值仅供参考
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(500);
		//目前来看这里是根据人员来查找数据，如果人员都有权限，这里就不过滤权限
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,SysAuthConstant.AuthCheck.SYS_NONE);

		Page page = sysAttendMainService.findPage(hqlInfo);
		// 第一页的数据处理
		HSSFSheet sheet = workbook.createSheet(
				ResourceUtil.getString("sysAttendMain.export.filename.attend",
						"sys-attend"));
		// 标题
		int titleIdx = sysAttendMainService.buildAttendTitle(workbook, sheet,
				0);
		//页数开始时间
		int startIndex =titleIdx + 1;
		//内容
		sysAttendMainService.buildAttendContent(workbook,
				sheet,
				startIndex, page.getList());
		//每页总数量的计算
		int sumCount = page.getList().size();
		//下一次开始的坐标
		startIndex+=sumCount;
		//Sheet页数
		int sheetNumber =1;
		int maxRow =60000;
		if (page.getTotal() > 1) {
			for (int i = 2; i <= page.getTotal(); i++) {
				hqlInfo.setPageNo(i);
				Page p = sysAttendMainService.findPage(hqlInfo);
				List dataList =p.getList();
				//判断如果总条数超过60000条，则新创建一个sheet
				int tempSize =dataList.size();
				//计算超出60000多少条。
				int outNumber =sumCount + tempSize -maxRow;
				if(outNumber > 0){
					List tempDataList =new ArrayList();
					List newDataList =new ArrayList();
					for (Object temp:dataList) {
						if(sumCount < maxRow){
							tempDataList.add(temp);
							sumCount++;
						} else {
							newDataList.add(temp);
						}
					}
					//把前一页的写完以后在写新的一页
					if(CollectionUtils.isNotEmpty(tempDataList)) {
						sysAttendMainService.buildAttendContent(
								workbook,
								sheet,
								startIndex, tempDataList);

					}
					if(CollectionUtils.isNotEmpty(newDataList)) {
						//创建新的一页
						sheetNumber++;
						sumCount =newDataList.size();
						sheet = workbook.createSheet(
								ResourceUtil.getString(
										"sysAttendMain.export.filename.attend",
										"sys-attend") + "(" + sheetNumber+ ")");
						// 标题
						titleIdx = sysAttendMainService.buildAttendTitle(workbook,
								sheet,
								0);
						//开始行 从标题之后的一行进行
						startIndex =titleIdx + 1;
						sysAttendMainService.buildAttendContent(
								workbook,
								sheet,
								startIndex, newDataList);
						//下一个开始的行
						startIndex +=newDataList.size();
					}
				} else {
					sysAttendMainService.buildAttendContent(
							workbook,
							sheet,
							startIndex, dataList);
					sumCount+=tempSize;
					startIndex +=tempSize;
				}
			}
		}
	}

	/**
	 * 构建excel合并表头
	 * 
	 * @param sheet
	 * @param titleRow
	 * @param infos
	 */
	public void buildMergeHeader(HSSFSheet sheet, HSSFRow titleRow,
			List<Map<String, Object>> infos) {
		if (sheet == null || titleRow == null || infos == null
				|| infos.isEmpty()) {
			return;
		}
		int headRowIdx = titleRow.getRowNum();
		int headColStart = titleRow.getFirstCellNum();
		int headColEnd = titleRow.getLastCellNum();
		HSSFRow tmpRow = sheet.createRow(headRowIdx + 1);
		tmpRow.setHeight((short) 500);
		List<Integer> notMergeColIdx = new ArrayList<Integer>();
		for (Map<String, Object> map : infos) {
			if (map != null) {
				Integer colStart = (Integer) map.get("colStart");
				Integer colEnd = (Integer) map.get("colEnd");
				if (colStart != null && colEnd != null) {
					for (int i = colStart; i <= colEnd; i++) {
						HSSFCell cell1 = titleRow.getCell(i);
						HSSFCell cell2 = tmpRow.createCell(i);
						if (cell1 != null && cell2 != null) {
							cell2.setCellStyle(cell1.getCellStyle());
							cell2.setCellValue(cell1.getStringCellValue());
						}
						notMergeColIdx.add(i);
					}
					String title = (String) map.get("title");
					if (StringUtil.isNotNull(title)) {
						HSSFCell wtCell = titleRow.getCell(colStart);
						wtCell.setCellValue(title);
					}
					com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, 
							new CellRangeAddress(0, 0, colStart, colEnd));
				}
			}
		}
		for (int k = headColStart; k <= headColEnd; k++) {
			if (!notMergeColIdx.isEmpty() && notMergeColIdx.contains(k)) {
				continue;
			}
			com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, new CellRangeAddress(0, 1, k, k));
		}
	}

	@Override
	public void sendAttendReport(SysQuartzJobContext context) throws Exception {
		try {

			JSONObject param = JSONObject.fromObject(context.getParameter());
			String fdPushLeader = param.getString("fdPushLeader");
			if ("true".equals(fdPushLeader)) {

				List deptInfos = this.getDeptsInfos();
				if (deptInfos != null && !deptInfos.isEmpty()) {
					for (int k = 0; k < deptInfos.size(); k++) {
						Object[] dept = (Object[]) deptInfos
								.get(k);
						String deptId = (String) dept[0];
						String deptName = (String) dept[1];
						String leaderId = (String) dept[2];
						logger.warn("推送月考勤报表人员:" + deptId+","+deptName+","+leaderId);
						if (StringUtil.isNull(leaderId)) {
							continue;
						}

						SysOrgElement leader = sysOrgCoreService
								.findByPrimaryKey(leaderId, null, true);

						NotifyContext notifyContext = sysNotifyMainCoreService
								.getContext(
										"sys-attend:sysAttendReport.push.leader");
						notifyContext.setNotifyType("todo");
						notifyContext
								.setFlag(
										SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
						notifyContext.setFdNotifyEKP(false);
						notifyContext.setFdAppType("all");
						notifyContext.setFdAppReceiver("kk_system");
						List<SysOrgElement> tmpOrg = new ArrayList<SysOrgElement>();
						tmpOrg.add(leader);
						notifyContext.setNotifyTarget(tmpOrg);

						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("docCreator", deptName);
						Date month = AttendUtil.getMonth(new Date(), -1);
						hashMap.put("month",
								String.valueOf(month.getMonth() + 1));
						JSONObject json = sysAttendStatMonthService
								.statLeaderMonth(month, leaderId, deptId);
						String fdCategoryId = "";
						if (json != null) {
							hashMap.put("fdStatusCount",
									String.valueOf(json
											.getInt("fdStatusCount")));
							hashMap.put("fdLeftCount",
									String.valueOf(json
											.getInt("fdLeftCount")));
							hashMap.put("fdLateCount",
									String.valueOf(json
											.getInt("fdLateCount")));
							hashMap.put("fdMissedCount",
									String.valueOf(json
											.getInt("fdMissedCount")));
							hashMap.put("fdAbsentCount",
									String.valueOf(json
											.getInt("fdAbsentCount")));
							String cateId=json.getString(
									"fdCategoryId");
							if (StringUtil.isNotNull(cateId) && !"null".equals(cateId)) {
								fdCategoryId = json
										.getString("fdCategoryId");
							}
						}

						notifyContext.setLink(
								"/sys/attend/sys_attend_report/sysAttendReport_pushLeader.jsp"
										+ "?fdCategoryId=" + fdCategoryId);
						sysNotifyMainCoreService.send(null,
								notifyContext,
								hashMap);

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("推送月考勤报表失败:" + e.getMessage(), e);
		}
	}

	/**
	 * 获取所有考勤组里的领导信息
	 * 
	 * @return
	 * @throws Exception
	 */
	private List getDeptsInfos() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1 ");
		List cateList = sysAttendCategoryService.findList(hqlInfo);
		if (cateList.isEmpty()) {
			return new ArrayList();
		}
		List<SysOrgElement> fdTargets = new ArrayList<SysOrgElement>();
		for (int k = 0; k < cateList.size(); k++) {
			SysAttendCategory category = (SysAttendCategory) cateList
					.get(k);
			fdTargets.addAll(category.getFdTargets());
		}
		List deptsInfos = sysAttendOrgService.findChildDeptsInfo(fdTargets);
		return deptsInfos;
	}

	@Override
	public void saveReportMonth(RequestContext request, String fdReportId)
			throws Exception {
		if (StringUtil.isNotNull(fdReportId)) {
			deleteReportMonth(request, fdReportId);
			HQLInfo hqlInfo = new HQLInfo();
			// 查询实时数据
			changeListStatHqlInfo(request, hqlInfo);
			// findList不做权限过滤，这里手动处理
			if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.DEFAULT);
			}
			List<SysAttendStatMonth> list = sysAttendStatMonthService
					.findList(hqlInfo);
			for (SysAttendStatMonth statMonth : list) {
				SysAttendReportMonth reportMonth = new SysAttendReportMonth();
				// 拷贝到报表
				copyStatToReport(statMonth, reportMonth);
				reportMonth.setFdReportId(fdReportId);
				sysAttendReportMonthService.add(reportMonth);
			}
		}
	}

	@Override
	public void deleteReportMonth(RequestContext request, String fdReportId)
			throws Exception {
		if (StringUtil.isNotNull(fdReportId)) {
			// 删除原有的报表数据
			List<SysAttendReportMonth> list = sysAttendReportMonthService
					.findList(
							"sysAttendReportMonth.fdReportId='" + fdReportId
									+ "'",
							"");
			if (list != null && !list.isEmpty()) {
				for (SysAttendReportMonth monthReport : list) {
					sysAttendReportMonthService.delete(monthReport);
				}
			}
		}
	}

	@Override
	public void deleteReportMonth(RequestContext request, String[] fdReportIds)
			throws Exception {
		for (int i = 0; i < fdReportIds.length; i++) {
			if (StringUtil.isNotNull(fdReportIds[i])) {
				// 删除原有的报表数据
				List<SysAttendReportMonth> list = sysAttendReportMonthService
						.findList(
								"sysAttendReportMonth.fdReportId='"
										+ fdReportIds[i]
										+ "'",
								"");
				if (list != null && !list.isEmpty()) {
					for (SysAttendReportMonth monthReport : list) {
						sysAttendReportMonthService.delete(monthReport);
					}
				}
			}
		}
	}

	@Override
	public Page monthList(RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
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
		changeListMonthHqlInfo(request, hqlInfo);
		// findList不做权限过滤，这里手动处理
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		Page page = sysAttendReportMonthService.findPage(hqlInfo);
		// 添加日志信息
		if (UserOperHelper.allowLogOper("monthList", getModelName())) {
			UserOperContentHelper.putFinds(page.getList());
		}
		// 用于展示考勤组名
		page.setList(formatStatMonth(page.getList()));
		List<String> idList=new ArrayList<String>();
		for (Object monthModel : page.getList()) {
			SysOrgPerson docCreator = (SysOrgPerson) PropertyUtils
					.getProperty(monthModel, "docCreator");
			if(docCreator!=null) {
				idList.add(docCreator.getFdId());
			}
			String fdCategoryId = (String) PropertyUtils.getProperty(monthModel,"fdCategoryId");
			if(StringUtil.isNotNull(fdCategoryId)){
				SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(fdCategoryId);
				if(hisCategory !=null){
					PropertyUtils.setProperty(monthModel,"fdCategoryName",hisCategory.getFdName());
				}
			}
		}
		request.setAttribute("personInfoMap",
				getAllPersonInfo(idList));
		return page;
	}

	@Override
	public HSSFWorkbook exportMonthExcel(RequestContext request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		changeListMonthHqlInfo(request, hqlInfo);
		// findList不做权限过滤，这里手动处理
		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		List list = sysAttendReportMonthService.findList(hqlInfo);
		String fdName = request.getParameter("fdName");
		return buildWorkBook(formatStatMonth(list),
				StringUtil.isNotNull(fdName)
						? fdName
						: ResourceUtil.getString("table.sysAttendReport",
								"sys-attend"),
				request);
	}

	@Override
	public Page listPeriod(RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
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
		changeListPeriodHqlInfo(request, hqlInfo);

		if (StringUtil.isNotNull(orderby)) {
			orderby = "sysAttendStatPeriod." + orderby;
			hqlInfo.setOrderBy(orderby);
		}

		Page page = sysAttendStatPeriodService.findPage(hqlInfo);
		// 添加日志信息
		if (UserOperHelper.allowLogOper("listPeriod", getModelName())) {
			UserOperContentHelper.putFinds(page.getList());
		}

		page.setList(formatStatMonth(page.getList()));
		// 每日明细数据
		genDateDetailList(page.getList(), request);
		List<String> idList=new ArrayList<String>();
		for (Object monthModel : page.getList()) {
			SysOrgPerson docCreator = (SysOrgPerson) PropertyUtils
					.getProperty(monthModel, "docCreator");
			if(docCreator!=null) {
				idList.add(docCreator.getFdId());
			}
			String fdCategoryId = (String) PropertyUtils.getProperty(monthModel,"fdCategoryId");
			if(StringUtil.isNotNull(fdCategoryId)){
				SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(fdCategoryId);
				if(hisCategory !=null){
					PropertyUtils.setProperty(monthModel,"fdCategoryName",hisCategory.getFdName());
				}
			}
		}

		request.setAttribute("personInfoMap",
				getAllPersonInfo(idList));
		return page;
	}

	private void changeListPeriodHqlInfo(RequestContext request,
			HQLInfo hqlInfo) throws Exception {
		StringBuffer whereBlock = new StringBuffer("1=1 ");
		String fdStartTime = request.getParameter("fdStartTime");
		String fdEndTime = request.getParameter("fdEndTime");
		Date fdStartDate =null;
		if (StringUtil.isNotNull(fdStartTime)
				&& StringUtil.isNotNull(fdEndTime)) {
			whereBlock.append(
					" and sysAttendStatPeriod.fdStartTime>=:startFirst and sysAttendStatPeriod.fdStartTime<:startLast"
							+ " and sysAttendStatPeriod.fdEndTime>=:endFirst and sysAttendStatPeriod.fdEndTime<:endLast");
			fdStartDate = DateUtil.convertStringToDate(fdStartTime,
					DateUtil.TYPE_DATETIME, request.getLocale());
			hqlInfo.setParameter("startFirst",
					AttendUtil.getDate(fdStartDate, 0));
			hqlInfo.setParameter("startLast",
					AttendUtil.getDate(fdStartDate, 1));
			Date fdEndDate = DateUtil.convertStringToDate(fdEndTime,
					DateUtil.TYPE_DATETIME, request.getLocale());
			hqlInfo.setParameter("endFirst",
					AttendUtil.getDate(fdEndDate, 0));
			hqlInfo.setParameter("endLast",
					AttendUtil.getDate(fdEndDate, 1));
		} else {
			throw new NoRecordException();
		}

		// 查询对象
		String fdTargetType = request.getParameter("fdTargetType");
		String fdCategoryIds = request.getParameter("fdCategoryIds");
		String fdDeptIds = request.getParameter("fdDeptIds");

		// 按部门查询
		if (StringUtil.isNull(fdTargetType) || "1".equals(fdTargetType)) {
			// AI接口新增
			String ai = request.getParameter("ai");
			if (StringUtil.isNotNull(ai) && "true".equals(ai)
					&& StringUtil.isNull(fdDeptIds)) {
				fdDeptIds = UserUtil.getUser().getFdId();
			}

			if (StringUtil.isNotNull(fdDeptIds)) {
				List<String> orgIds = ArrayUtil
						.convertArrayToList(fdDeptIds.split(";"));
				List<String> personIds = sysOrgCoreService
						.expandToPersonIds(orgIds);
				if (!personIds.isEmpty()) {
					whereBlock.append(
							" and (" + HQLUtil.buildLogicIN(
									"sysAttendStatPeriod.docCreator.fdId",
									personIds));
					whereBlock.append(
							" or (" + AttendUtil.buildLikeHql(
									"sysAttendStatPeriod.docCreatorHId", orgIds)
									+ " and sysAttendStatPeriod.docCreator.fdIsAvailable=:fdIsAvailable0))");
					hqlInfo.setParameter("fdIsAvailable0", false);
				} else {
					throw new NoRecordException();
				}

			}
			// 按考勤组查询
		} else if ("2".equals(fdTargetType)) {
			setCategoryWhere(fdCategoryIds,whereBlock,"sysAttendStatPeriod.fdCategoryId");
		}

		String fdIsQuit = request.getParameter("fdIsQuit");
		if (!"true".equals(fdIsQuit)) {
			whereBlock.append(
					" and sysAttendStatPeriod.docCreator.fdIsAvailable=:isAvailable1");
			hqlInfo.setParameter("isAvailable1", true);
			whereBlock.append(
					" and (sysAttendStatPeriod.docCreator.fdLeaveDate is null or sysAttendStatPeriod.docCreator.fdLeaveDate>=:fdLeaveDate)");
			hqlInfo.setParameter("fdLeaveDate", new Date());
		}
		SysAttendConfig config = sysAttendConfigService.getSysAttendConfig();
		if (config != null) {

			String fdExcTargetIdStr = config.getFdExcTargetIds();
			// 系统配置的不参与考勤人员
			if (StringUtil.isNotNull(fdExcTargetIdStr)) {
				List<String> fdExcTargetIds = ArrayUtil
						.convertArrayToList(fdExcTargetIdStr.split(";"));
				if (!fdExcTargetIds.isEmpty()) {
					whereBlock.append(
							" and sysAttendStatPeriod.docCreator.fdId not in('")
							.append(StringUtil.join(fdExcTargetIds, "','"))
							.append("')");
				}
			} else {
				logger.warn("当前不参与考勤人员为空！");
			}
		} 
		//过滤考勤组的排除人员 
		//whereBlock.append(" and NOT EXISTS (select 1 from SysAttendCategory sysAttendCategory where sysAttendStatPeriod.fdCategoryId=sysAttendCategory.fdId  and sysAttendCategory.fdExcTargets.fdId in (sysAttendStatPeriod.docCreator.fdId) )");
		
		// 业务相关
		whereBlock.append(
				" and sysAttendStatPeriod.docCreator.fdIsBusiness=:isBusiness");
		hqlInfo.setParameter("isBusiness", true);
		hqlInfo.setOrderBy("sysAttendStatPeriod.docCreator.fdNamePinYin asc");
		hqlInfo.setWhereBlock(whereBlock.toString());

	}

	/**
	 * 按月导出 时间区间HQL语句
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public HQLInfo getExportPeriodHqlInfo(RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		changeListPeriodHqlInfo(request, hqlInfo);
		// findList不做权限过滤，这里手动处理
		if (!UserUtil.checkRole("SYSROLE_ADMIN")) {
			if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.DEFAULT);
			}
		}
		return hqlInfo;
	}

	/**
	 *
	 * @param hqlInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public HSSFWorkbook exportPeriod(HQLInfo hqlInfo, RequestContext request) throws Exception {
		if (UserOperHelper.allowLogOper("exportPeriod", getModelName())) {
			UserOperHelper.setEventType(ResourceUtil
					.getString("sysAttendReport.exportExcel", "sys-attend"));
		}
		List list = sysAttendStatPeriodService.findList(hqlInfo);
		String fdName = request.getParameter("fdName");
		fdName = StringUtil.isNotNull(fdName) ? fdName
				: ResourceUtil.getString("table.sysAttendReport",
						"sys-attend");
		// 获取每日统计数据
		request.setAttribute("fdShowCols", "fdDateDetail");
		genDateDetailList(list, request);

		return buildWorkBookByCols(formatStatMonth(list), fdName, request);
	}

	private void copyStatToReport(SysAttendStatMonth statMonth,
			SysAttendReportMonth reportMonth) {
		if (statMonth != null && reportMonth != null) {
			reportMonth.setFdId(IDGenerator.generateID());
			reportMonth.setFdMonth(statMonth.getFdMonth());
			reportMonth.setFdCategoryId(statMonth.getFdCategoryId());
			reportMonth.setFdTotalTime(statMonth.getFdTotalTime());
			reportMonth.setFdOverTime(statMonth.getFdOverTime());
			reportMonth.setFdWorkOverTime(statMonth.getFdWorkOverTime());
			reportMonth.setFdOffOverTime(statMonth.getFdOffOverTime());
			reportMonth
					.setFdHolidayOverTime(statMonth.getFdHolidayOverTime());
			reportMonth.setDocCreateTime(statMonth.getDocCreateTime());
			reportMonth.setFdLateTime(statMonth.getFdLateTime());
			reportMonth.setFdLeftTime(statMonth.getFdLeftTime());
			reportMonth.setFdShouldDays(statMonth.getFdShouldDays());
			reportMonth.setFdHolidays(statMonth.getFdHolidays());
			reportMonth.setFdActualDays(statMonth.getFdActualDays());
			reportMonth.setFdTripDays(statMonth.getFdTripDays());
			reportMonth.setFdOffDays(statMonth.getFdOffDays());
			reportMonth.setFdStatusDays(statMonth.getFdStatusDays());
			reportMonth.setFdAbsentDays(statMonth.getFdAbsentDays());
			reportMonth.setFdAbsentDaysCount(statMonth.getFdAbsentDaysCount());
			reportMonth.setFdMissedCount(statMonth.getFdMissedCount());
			reportMonth.setFdOutsideCount(statMonth.getFdOutsideCount());
			reportMonth.setFdLateCount(statMonth.getFdLateCount());
			reportMonth.setFdLeftCount(statMonth.getFdLeftCount());
			reportMonth.setFdLate(statMonth.getFdLate());
			reportMonth.setFdLeft(statMonth.getFdLeft());
			reportMonth.setFdMissed(statMonth.getFdMissed());
			reportMonth.setFdAbsent(statMonth.getFdAbsent());
			reportMonth.setFdStatus(statMonth.getFdStatus());
			reportMonth.setFdTrip(statMonth.getFdTrip());
			reportMonth.setFdMissedExcCount(statMonth.getFdMissedCount());
			reportMonth.setFdLateExcCount(statMonth.getFdLateExcCount());
			reportMonth.setFdLeftExcCount(statMonth.getFdLeftCount());
			reportMonth.setFdOutside(statMonth.getFdOutside());
			reportMonth.setDocCreator(statMonth.getDocCreator());
			reportMonth.setFdOffDaysDetail(statMonth.getFdOffDaysDetail());
			reportMonth.setFdOffDaysDetailJson(
					statMonth.getFdOffDaysDetailJson());
			reportMonth.setDocCreatorHId(statMonth.getDocCreatorHId());
			reportMonth.setFdAbsentDaysCount(statMonth.getFdAbsentDaysCount());
			reportMonth.setFdOutgoingTime(statMonth.getFdOutgoingTime());
			reportMonth.setFdOffTime(statMonth.getFdOffTime());
			reportMonth.setFdOffTimeHour(statMonth.getFdOffTimeHour());
			reportMonth.setFdWorkDateDays(statMonth.getFdWorkDateDays());
			reportMonth.setFdOutgoingDay(statMonth.getFdOutgoingDay());
			reportMonth.setFdLeaveDays(statMonth.getFdLeaveDays());
		}
	}

	@Override
	public boolean isReportReader() {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setPageNo(0);
			hqlInfo.setRowSize(SysConfigParameters.getRowSize());
			Page page = findPage(hqlInfo);
			List list = page.getList();
			if (list != null && !list.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	private List<Date> getBetweenDates(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	 
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
//	    result.add(start);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}
	private Map<String, JSONObject> getAllPersonInfo(List<String> idList,List<SysTimeHoliday> list) {
		boolean exist = new File(PluginConfigLocationsUtil.getKmssConfigPath() + "/hr/staff").exists();
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		if (exist) {
			if (!ArrayUtil.isEmpty(idList)) {
				try{
					HQLInfo info = new HQLInfo();
					String where = HQLUtil.buildLogicIN("fdOrgPerson.fdId", idList);
					info.setWhereBlock(where);
					List<HrStaffPersonInfo> personInfoList = hrStaffPersonInfoService.findList(info);
					for (HrStaffPersonInfo personInfo : personInfoList) {
						JSONObject obj = new JSONObject();
						logger.info("姓名:-----》{}",personInfo.getFdName());
						String fdPersonId = personInfo.getFdOrgPerson().getFdId();
						//所属公司
						obj.put("fdAffiliatedCompany",personInfo.getFdAffiliatedCompany());
						obj.put("fdStaffType",personInfo.getFdStaffType());
						//一级部门
						obj.put("fdFirstLevelDepartment", personInfo.getFdFirstLevelDepartment() != null ? personInfo.getFdFirstLevelDepartment().getFdName() : "");
						//二级部门
						obj.put("fdSecondLevelDepartment", personInfo.getFdSecondLevelDepartment() != null ? personInfo.getFdSecondLevelDepartment().getFdName() : "");
						//三级部门
						obj.put("fdThirdLevelDepartment", personInfo.getFdThirdLevelDepartment() != null ? personInfo.getFdThirdLevelDepartment().getFdName() : "");
						//人员编号
						obj.put("fdStaffNo", personInfo.getFdStaffNo());
						//岗位
						String fdOrgPostsName="";
						if(personInfo.getFdOrgPosts() != null&& !personInfo.getFdOrgPosts().isEmpty()){
								fdOrgPostsName=personInfo.getFdOrgPosts().get(0).getFdName();
						if(personInfo.getFdOrgPosts().size()!=1)
							for(int i=1;i<personInfo.getFdOrgPosts().size();i++){
								fdOrgPostsName=fdOrgPostsName+','+personInfo.getFdOrgPosts().get(i).getFdName();
							}
						}
						obj.put("fdOrgPost",fdOrgPostsName);
						//职务
						obj.put("fdStaffingLevel",personInfo.getFdStaffingLevel() != null? personInfo.getFdStaffingLevel().getFdName():"");
						//入职时间
						obj.put("fdEntryTime",DateUtil.convertDateToString(personInfo.getFdEntryTime(),"yyyy-MM-dd"));
						
						Date enterDate = personInfo.getFdEntryTime();
						Date leaveDate = personInfo.getFdResignationDate();
						Calendar ca2 = Calendar.getInstance();// 得到一个Calendar的实例
						ZoneId timeZone = ZoneId.systemDefault();
//						List<SysTimeHoliday> list = null;
						LocalDate yiHao = null;
						Date yihao1 = new Date();
						try{
						    LocalDate getLocalDate = enterDate.toInstant().atZone(timeZone).toLocalDate();
							int year1 = getLocalDate.getYear();
//							list = getSysTimeHolidayService().findList("sysTimeHoliday.fdName='"+year1+"年法定假期'", "");
//							list = getSysTimeHolidayService().findList("sysTimeHoliday.fdName='"+year1+"年法定假期'", "");
						
						Date yiHaoDate = DateUtil.getBeginDayOfMonthByDate(enterDate);
						yiHao = yiHaoDate.toInstant().atZone(timeZone).toLocalDate();
						
						yihao1.setTime(yiHaoDate.getTime()-1000*60*60*24);}catch(Exception e){
							
						}
						List<Date> listDate1 = null;
						String[] monthArr = null;
						if(fdMonth!=null)
						monthArr = fdMonth.split("-");
						if(monthArr!=null&&yiHao!=null&&yiHao.getYear()==Integer.parseInt(monthArr[0]) &&yiHao.getMonthValue()==Integer.parseInt(monthArr[1]))
						listDate1 = getBetweenDates(yihao1,enterDate);
						int legalDays=0;
						int weekday = 0;
						if(list!=null&&!list.isEmpty()&&list.get(0)!=null){
						SysTimeHoliday sysTimeHoliday = list.get(0);
//						getSysTimeHolidayService().findByPrimaryKey(sysTimeHoliday.getFdId());
						List<SysTimeHolidayDetail> list1 = ((SysTimeHoliday) getSysTimeHolidayService().findByPrimaryKey(sysTimeHoliday.getFdId())).getFdHolidayDetailList();
////						int month = getLocalDate.getMonthValue();
//						int month = enterDate.getMonth();
//						ca2.set(Calendar.YEAR, Integer.valueOf(year));
//						ca2.set(Calendar.MONTH, Integer.valueOf(month)-1);
//						ca2.set(Calendar.DATE, ca2.getActualMinimum(Calendar.DATE));
						if(listDate1!=null)
						for(Date date1 : listDate1){
							Calendar ca21 = Calendar.getInstance();
							ca21.setTime(date1);
							int index=ca21.get(Calendar.DAY_OF_WEEK)-1;
							boolean flag1 = true;
						for(int i=0;i<list1.size();i++){
						SysTimeHolidayDetail sysTimeHolidayDetail = list1.get(i);
						Date startDay = sysTimeHolidayDetail.getFdStartDay();
						Date endDay = sysTimeHolidayDetail.getFdEndDay();
						Date tmpDate = new Date();
						Date tmpDate1 = new Date();
						tmpDate = (Date) endDay.clone();
						tmpDate.setTime(tmpDate.getTime()+1000*60*60*24);
						tmpDate1.setTime(startDay.getTime()-1000*60*60*24);
						String patchHoliday = sysTimeHolidayDetail.getFdPatchHolidayDay();
						String patchDay = sysTimeHolidayDetail.getFdPatchDay();
						List<Date> listDate = getBetweenDates(tmpDate1,tmpDate);
							if(patchHoliday!=null){
						if(patchHoliday.contains(",")){
							String[] patchHolidayArr = patchHoliday.split(",");
							
							for(Date date : listDate){
								boolean flag = true;
								for(String str : patchHolidayArr){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(time.equals(str)){
										flag=false;
										break;
									}
								}
								if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&flag)
									legalDays++;
								if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
										flag1=false;
							}
						}
						else {
							for(Date date : listDate){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&!time.equals(patchHoliday))
										legalDays++;
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
										flag1=false;
								}
						}
							}else{
								for(Date date : listDate){
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
											legalDays++;
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
										flag1=false;
								}
							}
							
						if(patchDay!=null){
							if(patchDay.contains(",")){
								String[] patchDayArr = patchDay.split(",");
									for(String str : patchDayArr){
										if(str.equals(DateUtil.convertDateToString(date1,"yyyy-MM-dd")))
											weekday++;
									}
							}
							else if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(patchDay))
										weekday++;
						}
						}
						if(index!=0&&index!=6&&flag1)
							weekday++;
						}
						}
						obj.put("enterDays",legalDays+weekday);
						int legalDays1=0;
						int weekday1 = 0;
						if(leaveDate!=null){
							LocalDate getLocalDate1 = leaveDate.toInstant().atZone(timeZone).toLocalDate();
							getLocalDate1 = getLocalDate1.minusMonths(-1);
							int year2 = getLocalDate1.getYear();
							LocalDate yiHao1 = getLocalDate1.with(TemporalAdjusters.firstDayOfMonth());
							List<Date> listDate2 = null;
							int tmp = yiHao1.getMonthValue();
							Date date21 = new Date();
							date21.setTime(leaveDate.getTime()+1000*60*60*24);
							if(yiHao1.getYear()==Integer.parseInt(monthArr[0]) &&(yiHao1.getMonthValue()-1)==Integer.parseInt(monthArr[1]))
								listDate2 = getBetweenDates(leaveDate,Date.from(yiHao1.atStartOfDay().atZone(timeZone).toInstant()));
							List<SysTimeHoliday> list2 = getSysTimeHolidayService().findList("sysTimeHoliday.fdName='"+year2+"年法定假期'", "");
							if(!list2.isEmpty()){
						SysTimeHoliday sysTimeHoliday = list2.get(0);
						List<SysTimeHolidayDetail> list1 = sysTimeHoliday.getFdHolidayDetailList();
////						int month = getLocalDate.getMonthValue();
//						int month = enterDate.getMonth();
//						ca2.set(Calendar.YEAR, Integer.valueOf(year));
//						ca2.set(Calendar.MONTH, Integer.valueOf(month)-1);
//						ca2.set(Calendar.DATE, ca2.getActualMinimum(Calendar.DATE));
						for(int i=0;i<list1.size();i++){
						SysTimeHolidayDetail sysTimeHolidayDetail = list1.get(i);
						Date startDay = sysTimeHolidayDetail.getFdStartDay();
						Date endDay = sysTimeHolidayDetail.getFdEndDay();
						Date data = new Date();
						data.setTime(endDay.getTime()+1000*60*60*24);
						Date data1 = new Date();
						data1.setTime(startDay.getTime()+1000*60*60*24);
						String patchHoliday = sysTimeHolidayDetail.getFdPatchHolidayDay();
						String patchDay = sysTimeHolidayDetail.getFdPatchDay();
						List<Date> listDate = getBetweenDates(data1,data);
						if(listDate2!=null)
						for(Date date1 : listDate2){
							if(patchHoliday!=null){
						if(patchHoliday.contains(",")){
							String[] patchHolidayArr = patchHoliday.split(patchHoliday, ',');
							
							for(Date date : listDate){
								for(String str : patchHolidayArr){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&!time.equals(str)){
										legalDays1++;
										Calendar ca7 = Calendar.getInstance();
										ca7.setTime(date);
										int index=ca7.get(Calendar.DAY_OF_WEEK)-1;
										if(index!=0&&index!=6)
											weekday1--;
									}
								}
							}
						}
						else {
							for(Date date : listDate){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&!time.equals(patchHoliday)){
										legalDays1++;

										Calendar ca7 = Calendar.getInstance();
										ca7.setTime(date);
										int index=ca7.get(Calendar.DAY_OF_WEEK)-1;
										if(index!=0&&index!=6)
											weekday1--;
									}
								}
						}
							}else{
								for(Date date : listDate){
								if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))){
										legalDays1++;
										Calendar ca7 = Calendar.getInstance();
										ca7.setTime(date);
										int index=ca7.get(Calendar.DAY_OF_WEEK)-1;
										if(index!=0&&index!=6)
											weekday1--;
								}
								}
							}
							if(patchDay!=null){
								if(patchDay.contains(",")){
									String[] patchDayArr = patchDay.split(",");
										for(String str : patchDayArr){
											if(str.equals(DateUtil.convertDateToString(date1,"yyyy-MM-dd"))){
												Calendar ca6 = Calendar.getInstance();
												ca6.setTime(date1);
												int index=ca6.get(Calendar.DAY_OF_WEEK)-1;
												if(index==0||index==6)
												weekday1++;
										}
										}
								}
								else if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(patchDay)){
									Calendar ca6 = Calendar.getInstance();
									ca6.setTime(date1);
									int index=ca6.get(Calendar.DAY_OF_WEEK)-1;
									if(index==0||index==6)
									weekday1++;
									}
							}
						}
						
						}
						
						}
							
								
							Calendar ca211 = Calendar.getInstance();
							if(listDate2!=null){
								for(Date date : listDate2){
									ca211.setTime(date);
									int index=ca211.get(Calendar.DAY_OF_WEEK)-1;
									if(index!=0&&index!=6)
										weekday1++;
								}
							}
								obj.put("leaveDays",legalDays1+weekday1);
						}
						//离职时间
						obj.put("fdResignationDate",DateUtil.convertDateToString(personInfo.getFdResignationDate(),"yyyy-MM-dd"));
						//人员类别
						obj.put("fdStaffType",personInfo.getFdStaffType());
						Calendar calendar = Calendar.getInstance();
						int year = calendar.get(Calendar.YEAR);
						//年假信息
						SysTimeLeaveAmountItem item = sysTimeLeaveAmountService.getLeaveAmountItem(year,fdPersonId,"1");
						if(item != null){
							if(item.getFdTotalDay()!=null)
							obj.put("fdTotalDays", String.format("%.2f", item.getFdTotalDay()));
							else
								obj.put("fdTotalDays", item.getFdTotalDay());
							if(item.getFdUsedDay()!=null)
							obj.put("fdUsedDays", String.format("%.2f", item.getFdUsedDay()));
							else
								obj.put("fdUsedDays", item.getFdUsedDay());
							if(item.getFdRestDay()!=null)
							obj.put("fdRestDays", String.format("%.2f", item.getFdRestDay()));
							else
								obj.put("fdRestDays", item.getFdRestDay());
//							obj.put("fdUsedDays", item.getFdUsedDay());
//							obj.put("fdRestDays", item.getFdRestDay());
						}else{
							obj.put("fdTotalDays", 0);
							obj.put("fdUsedDays", 0);
							obj.put("fdRestDays", 0);
						}
						//调休信息
						SysTimeLeaveAmountItem itemTx = sysTimeLeaveAmountService.getLeaveAmountItem(year,fdPersonId,"13");
						if(itemTx != null){
//							obj.put("fdTxTotalDays", itemTx.getFdTotalDay());
//							obj.put("fdTxUsedDays", itemTx.getFdUsedDay());
//							obj.put("fdTxRestDays", itemTx.getFdRestDay());
							if(itemTx.getFdTotalDay()!=null)
								obj.put("fdTxTotalDays", String.format("%.2f", itemTx.getFdTotalDay()));
								else
									obj.put("fdTxTotalDays", itemTx.getFdTotalDay());
								if(itemTx.getFdUsedDay()!=null)
								obj.put("fdTxUsedDays", String.format("%.2f", itemTx.getFdUsedDay()));
								else
									obj.put("fdTxUsedDays", itemTx.getFdUsedDay());
								if(itemTx.getFdRestDay()!=null)
								obj.put("fdTxRestDays", String.format("%.2f", itemTx.getFdRestDay()));
								else
									obj.put("fdTxRestDays", itemTx.getFdRestDay());
						}else{
							obj.put("fdTxTotalDays", 0);
							obj.put("fdTxUsedDays", 0);
							obj.put("fdTxRestDays", 0);
						}
						map.put(fdPersonId, obj);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	private Map<String, JSONObject> getAllPersonInfo(List<String> idList) {
		boolean exist = new File(PluginConfigLocationsUtil.getKmssConfigPath() + "/hr/staff").exists();
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		if (exist) {
			if (!ArrayUtil.isEmpty(idList)) {
				try{
					HQLInfo info = new HQLInfo();
					String where = HQLUtil.buildLogicIN("fdOrgPerson.fdId", idList);
					info.setWhereBlock(where);
					List<HrStaffPersonInfo> personInfoList = hrStaffPersonInfoService.findList(info);
					for (HrStaffPersonInfo personInfo : personInfoList) {
						JSONObject obj = new JSONObject();
						logger.info("姓名:-----》{}",personInfo.getFdName());
						String fdPersonId = personInfo.getFdOrgPerson().getFdId();
						//所属公司
						obj.put("fdAffiliatedCompany",personInfo.getFdAffiliatedCompany());
						obj.put("fdStaffType",personInfo.getFdStaffType());
						//一级部门
						obj.put("fdFirstLevelDepartment", personInfo.getFdFirstLevelDepartment() != null ? personInfo.getFdFirstLevelDepartment().getFdName() : "");
						//二级部门
						obj.put("fdSecondLevelDepartment", personInfo.getFdSecondLevelDepartment() != null ? personInfo.getFdSecondLevelDepartment().getFdName() : "");
						//三级部门
						obj.put("fdThirdLevelDepartment", personInfo.getFdThirdLevelDepartment() != null ? personInfo.getFdThirdLevelDepartment().getFdName() : "");
						//人员编号
						obj.put("fdStaffNo", personInfo.getFdStaffNo());
						//岗位
						String fdOrgPostsName="";
						if(personInfo.getFdOrgPosts() != null&& !personInfo.getFdOrgPosts().isEmpty()){
								fdOrgPostsName=personInfo.getFdOrgPosts().get(0).getFdName();
						if(personInfo.getFdOrgPosts().size()!=1)
							for(int i=1;i<personInfo.getFdOrgPosts().size();i++){
								fdOrgPostsName=fdOrgPostsName+','+personInfo.getFdOrgPosts().get(i).getFdName();
							}
						}
						obj.put("fdOrgPost",fdOrgPostsName);
						//职务
						obj.put("fdStaffingLevel",personInfo.getFdStaffingLevel() != null? personInfo.getFdStaffingLevel().getFdName():"");
						//入职时间
						obj.put("fdEntryTime",DateUtil.convertDateToString(personInfo.getFdEntryTime(),"yyyy-MM-dd"));
						
						Date enterDate = personInfo.getFdEntryTime();
						Date leaveDate = personInfo.getFdResignationDate();
						Calendar ca2 = Calendar.getInstance();// 得到一个Calendar的实例
						ZoneId timeZone = ZoneId.systemDefault();
						List<SysTimeHoliday> list = null;
						LocalDate yiHao = null;
						Date yihao1 = new Date();
						try{
						    LocalDate getLocalDate = enterDate.toInstant().atZone(timeZone).toLocalDate();
							int year1 = getLocalDate.getYear();
							list = getSysTimeHolidayService().findList("sysTimeHoliday.fdName='"+year1+"年法定假期'", "");
//							list = getSysTimeHolidayService().findList("sysTimeHoliday.fdName='"+year1+"年法定假期'", "");
						
						Date yiHaoDate = DateUtil.getBeginDayOfMonthByDate(enterDate);
						yiHao = yiHaoDate.toInstant().atZone(timeZone).toLocalDate();
						
						yihao1.setTime(yiHaoDate.getTime()-1000*60*60*24);}catch(Exception e){
							
						}
						List<Date> listDate1 = null;
						String[] monthArr = null;
						if(fdMonth!=null)
						monthArr = fdMonth.split("-");
						if(monthArr!=null&&yiHao!=null&&yiHao.getYear()==Integer.parseInt(monthArr[0]) &&yiHao.getMonthValue()==Integer.parseInt(monthArr[1]))
						listDate1 = getBetweenDates(yihao1,enterDate);
						int legalDays=0;
						int weekday = 0;
						if(list!=null&&!list.isEmpty()){
						SysTimeHoliday sysTimeHoliday = list.get(0);
						List<SysTimeHolidayDetail> list1 = sysTimeHoliday.getFdHolidayDetailList();
////						int month = getLocalDate.getMonthValue();
//						int month = enterDate.getMonth();
//						ca2.set(Calendar.YEAR, Integer.valueOf(year));
//						ca2.set(Calendar.MONTH, Integer.valueOf(month)-1);
//						ca2.set(Calendar.DATE, ca2.getActualMinimum(Calendar.DATE));
						if(listDate1!=null)
						for(Date date1 : listDate1){
							Calendar ca21 = Calendar.getInstance();
							ca21.setTime(date1);
							int index=ca21.get(Calendar.DAY_OF_WEEK)-1;
							boolean flag1 = true;
						for(int i=0;i<list1.size();i++){
						SysTimeHolidayDetail sysTimeHolidayDetail = list1.get(i);
						Date startDay = sysTimeHolidayDetail.getFdStartDay();
						Date endDay = sysTimeHolidayDetail.getFdEndDay();
						Date tmpDate = new Date();
						Date tmpDate1 = new Date();
						tmpDate = (Date) endDay.clone();
						tmpDate.setTime(tmpDate.getTime()+1000*60*60*24);
						tmpDate1.setTime(startDay.getTime()-1000*60*60*24);
						String patchHoliday = sysTimeHolidayDetail.getFdPatchHolidayDay();
						String patchDay = sysTimeHolidayDetail.getFdPatchDay();
						List<Date> listDate = getBetweenDates(tmpDate1,tmpDate);
							if(patchHoliday!=null){
						if(patchHoliday.contains(",")){
							String[] patchHolidayArr = patchHoliday.split(",");
							
							for(Date date : listDate){
								boolean flag = true;
								for(String str : patchHolidayArr){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(time.equals(str)){
										flag=false;
										break;
									}
								}
								if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&flag)
									legalDays++;
								if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
										flag1=false;
							}
						}
						else {
							for(Date date : listDate){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&!time.equals(patchHoliday))
										legalDays++;
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
										flag1=false;
								}
						}
							}else{
								for(Date date : listDate){
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
											legalDays++;
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd")))
										flag1=false;
								}
							}
							
						if(patchDay!=null){
							if(patchDay.contains(",")){
								String[] patchDayArr = patchDay.split(",");
									for(String str : patchDayArr){
										if(str.equals(DateUtil.convertDateToString(date1,"yyyy-MM-dd")))
											weekday++;
									}
							}
							else if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(patchDay))
										weekday++;
						}
						}
						if(index!=0&&index!=6&&flag1)
							weekday++;
						}
						}
						obj.put("enterDays",legalDays+weekday);
						int legalDays1=0;
						int weekday1 = 0;
						if(leaveDate!=null){
							LocalDate getLocalDate1 = leaveDate.toInstant().atZone(timeZone).toLocalDate();
							getLocalDate1 = getLocalDate1.minusMonths(-1);
							int year2 = getLocalDate1.getYear();
							LocalDate yiHao1 = getLocalDate1.with(TemporalAdjusters.firstDayOfMonth());
							List<Date> listDate2 = null;
							int tmp = yiHao1.getMonthValue();
							Date date21 = new Date();
							date21.setTime(leaveDate.getTime()+1000*60*60*24);
							if(yiHao1.getYear()==Integer.parseInt(monthArr[0]) &&(yiHao1.getMonthValue()-1)==Integer.parseInt(monthArr[1]))
								listDate2 = getBetweenDates(leaveDate,Date.from(yiHao1.atStartOfDay().atZone(timeZone).toInstant()));
							List<SysTimeHoliday> list2 = getSysTimeHolidayService().findList("sysTimeHoliday.fdName='"+year2+"年法定假期'", "");
							if(!list2.isEmpty()){
						SysTimeHoliday sysTimeHoliday = list2.get(0);
						List<SysTimeHolidayDetail> list1 = sysTimeHoliday.getFdHolidayDetailList();
////						int month = getLocalDate.getMonthValue();
//						int month = enterDate.getMonth();
//						ca2.set(Calendar.YEAR, Integer.valueOf(year));
//						ca2.set(Calendar.MONTH, Integer.valueOf(month)-1);
//						ca2.set(Calendar.DATE, ca2.getActualMinimum(Calendar.DATE));
						for(int i=0;i<list1.size();i++){
						SysTimeHolidayDetail sysTimeHolidayDetail = list1.get(i);
						Date startDay = sysTimeHolidayDetail.getFdStartDay();
						Date endDay = sysTimeHolidayDetail.getFdEndDay();
						Date data = new Date();
						data.setTime(endDay.getTime()+1000*60*60*24);
						Date data1 = new Date();
						data1.setTime(startDay.getTime()+1000*60*60*24);
						String patchHoliday = sysTimeHolidayDetail.getFdPatchHolidayDay();
						String patchDay = sysTimeHolidayDetail.getFdPatchDay();
						List<Date> listDate = getBetweenDates(data1,data);
						if(listDate2!=null)
						for(Date date1 : listDate2){
							if(patchHoliday!=null){
						if(patchHoliday.contains(",")){
							String[] patchHolidayArr = patchHoliday.split(patchHoliday, ',');
							
							for(Date date : listDate){
								for(String str : patchHolidayArr){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&!time.equals(str)){
										legalDays1++;
										Calendar ca7 = Calendar.getInstance();
										ca7.setTime(date);
										int index=ca7.get(Calendar.DAY_OF_WEEK)-1;
										if(index!=0&&index!=6)
											weekday1--;
									}
								}
							}
						}
						else {
							for(Date date : listDate){
									String  time = DateUtil.convertDateToString(date,"yyyy-MM-dd");
									if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))&&!time.equals(patchHoliday)){
										legalDays1++;

										Calendar ca7 = Calendar.getInstance();
										ca7.setTime(date);
										int index=ca7.get(Calendar.DAY_OF_WEEK)-1;
										if(index!=0&&index!=6)
											weekday1--;
									}
								}
						}
							}else{
								for(Date date : listDate){
								if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(DateUtil.convertDateToString(date,"yyyy-MM-dd"))){
										legalDays1++;
										Calendar ca7 = Calendar.getInstance();
										ca7.setTime(date);
										int index=ca7.get(Calendar.DAY_OF_WEEK)-1;
										if(index!=0&&index!=6)
											weekday1--;
								}
								}
							}
							if(patchDay!=null){
								if(patchDay.contains(",")){
									String[] patchDayArr = patchDay.split(",");
										for(String str : patchDayArr){
											if(str.equals(DateUtil.convertDateToString(date1,"yyyy-MM-dd"))){
												Calendar ca6 = Calendar.getInstance();
												ca6.setTime(date1);
												int index=ca6.get(Calendar.DAY_OF_WEEK)-1;
												if(index==0||index==6)
												weekday1++;
										}
										}
								}
								else if(DateUtil.convertDateToString(date1,"yyyy-MM-dd").equals(patchDay)){
									Calendar ca6 = Calendar.getInstance();
									ca6.setTime(date1);
									int index=ca6.get(Calendar.DAY_OF_WEEK)-1;
									if(index==0||index==6)
									weekday1++;
									}
							}
						}
						
						}
						
						}
							
								
							Calendar ca211 = Calendar.getInstance();
							if(listDate2!=null){
								for(Date date : listDate2){
									ca211.setTime(date);
									int index=ca211.get(Calendar.DAY_OF_WEEK)-1;
									if(index!=0&&index!=6)
										weekday1++;
								}
							}
								obj.put("leaveDays",legalDays1+weekday1);
						}
						//离职时间
						obj.put("fdResignationDate",DateUtil.convertDateToString(personInfo.getFdResignationDate(),"yyyy-MM-dd"));
						//人员类别
						obj.put("fdStaffType",personInfo.getFdStaffType());
						Calendar calendar = Calendar.getInstance();
						int year = calendar.get(Calendar.YEAR);
						//年假信息
						SysTimeLeaveAmountItem item = sysTimeLeaveAmountService.getLeaveAmountItem(year,fdPersonId,"1");
						if(item != null){
							if(item.getFdTotalDay()!=null)
							obj.put("fdTotalDays", String.format("%.2f", item.getFdTotalDay()));
							else
								obj.put("fdTotalDays", item.getFdTotalDay());
							if(item.getFdUsedDay()!=null)
							obj.put("fdUsedDays", String.format("%.2f", item.getFdUsedDay()));
							else
								obj.put("fdUsedDays", item.getFdUsedDay());
							if(item.getFdRestDay()!=null)
							obj.put("fdRestDays", String.format("%.2f", item.getFdRestDay()));
							else
								obj.put("fdRestDays", item.getFdRestDay());
//							obj.put("fdUsedDays", item.getFdUsedDay());
//							obj.put("fdRestDays", item.getFdRestDay());
						}else{
							obj.put("fdTotalDays", 0);
							obj.put("fdUsedDays", 0);
							obj.put("fdRestDays", 0);
						}
						//调休信息
						SysTimeLeaveAmountItem itemTx = sysTimeLeaveAmountService.getLeaveAmountItem(year,fdPersonId,"13");
						if(itemTx != null){
//							obj.put("fdTxTotalDays", itemTx.getFdTotalDay());
//							obj.put("fdTxUsedDays", itemTx.getFdUsedDay());
//							obj.put("fdTxRestDays", itemTx.getFdRestDay());
							if(itemTx.getFdTotalDay()!=null)
								obj.put("fdTxTotalDays", String.format("%.2f", itemTx.getFdTotalDay()));
								else
									obj.put("fdTxTotalDays", itemTx.getFdTotalDay());
								if(itemTx.getFdUsedDay()!=null)
								obj.put("fdTxUsedDays", String.format("%.2f", itemTx.getFdUsedDay()));
								else
									obj.put("fdTxUsedDays", itemTx.getFdUsedDay());
								if(itemTx.getFdRestDay()!=null)
								obj.put("fdTxRestDays", String.format("%.2f", itemTx.getFdRestDay()));
								else
									obj.put("fdTxRestDays", itemTx.getFdRestDay());
						}else{
							obj.put("fdTxTotalDays", 0);
							obj.put("fdTxUsedDays", 0);
							obj.put("fdTxRestDays", 0);
						}
						map.put(fdPersonId, obj);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	public void
			setSysAttendOrgService(ISysAttendOrgService sysAttendOrgService) {
		this.sysAttendOrgService = sysAttendOrgService;
	}

	public void setSysAttendStatMonthService(
			ISysAttendStatMonthService sysAttendStatMonthService) {
		this.sysAttendStatMonthService = sysAttendStatMonthService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setSysAttendReportMonthService(
			ISysAttendReportMonthService sysAttendReportMonthService) {
		this.sysAttendReportMonthService = sysAttendReportMonthService;
	}

	public void
			setSysAttendStatService(
					ISysAttendStatService sysAttendStatService) {
		this.sysAttendStatService = sysAttendStatService;
	}

	public void setSysAttendStatDetailService(
			ISysAttendStatDetailService sysAttendStatDetailService) {
		this.sysAttendStatDetailService = sysAttendStatDetailService;
	}

	public void
			setSysAttendMainService(
					ISysAttendMainService sysAttendMainService) {
		this.sysAttendMainService = sysAttendMainService;
	}

	public void setSysAttendStatPeriodService(
			ISysAttendStatPeriodService sysAttendStatPeriodService) {
		this.sysAttendStatPeriodService = sysAttendStatPeriodService;
	}

	public void setSysTimeLeaveRuleService(
			ISysTimeLeaveRuleService sysTimeLeaveRuleService) {
		this.sysTimeLeaveRuleService = sysTimeLeaveRuleService;
	}

	public void setSysAttendConfigService(ISysAttendConfigService sysAttendConfigService) {
		this.sysAttendConfigService = sysAttendConfigService;
	}

	public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	public void setSysTimeLeaveAmountService(ISysTimeLeaveAmountService sysTimeLeaveAmountService) {
		this.sysTimeLeaveAmountService = sysTimeLeaveAmountService;
	}

	public void setSysTimeLeaveDetailService(ISysTimeLeaveDetailService sysTimeLeaveDetailService) {
		this.sysTimeLeaveDetailService = sysTimeLeaveDetailService;
	}

	@Override
	public int syncAttendDatabase(String begin, String end) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
