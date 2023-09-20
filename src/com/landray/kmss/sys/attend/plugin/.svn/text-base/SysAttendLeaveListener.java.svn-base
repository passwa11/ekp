package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainJobService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendReportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.service.spring.AttendStatThread;
import com.landray.kmss.sys.attend.util.AttendComparableTime;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendOverTimeUtil;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveResume;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveResumeService;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

import ch.qos.logback.core.util.TimeUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 请假流程结束事件
 *
 * @author 王京
 */
public class SysAttendLeaveListener
        extends SysAttendListenerCommonImp
        implements IEventListener, IEventMulticasterAware,
        ApplicationListener<Event_Common> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendLeaveListener.class);

    private ISysTimeLeaveDetailService sysTimeLeaveDetailService;
    private ISysTimeLeaveResumeService sysTimeLeaveResumeService;


    private IEventMulticaster multicaster;

    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;

    }

    private ThreadPoolTaskExecutor leaveTaskExecutor;

    public void setLeaveTaskExecutor(ThreadPoolTaskExecutor leaveTaskExecutor) {
        this.leaveTaskExecutor = leaveTaskExecutor;
    }
    private ISysAttendMainService sysAttendMainService;
	public ISysAttendMainService getSysAttendMainService() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) SpringBeanUtil.getBean("sysAttendMainService");
		}
		return sysAttendMainService;
	}
    private ISysAttendCategoryService sysAttendCategoryService;
	public ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}
    private ISysAttendBusinessService sysAttendBusinessService;
	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}
    private ISysOrgPersonService sysOrgPersonService;
	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}
    @Override
    public void handleEvent(EventExecutionContext execution, String parameter)
            throws Exception {
    	String templateId = execution.getProcessInstance().getFdTemplateId();
    	String fdProcessId = execution.getProcessInstance().getFdId();
    	HQLInfo hQLInfo = new HQLInfo();
    	hQLInfo.setWhereBlock("fdProcessId=:fdProcessId");
    	hQLInfo.setParameter("fdProcessId", fdProcessId);
    	SysAttendBusiness sysAttendBusiness = (SysAttendBusiness) getSysAttendBusinessService().findFirstOne(hQLInfo);
    	if("188d7f2c28e9cbaad29cd974e11b64f3".equals(templateId)){
    		JSONObject params = JSONObject.fromObject(parameter);
            IBaseModel mainModel = execution.getMainModel();
            if (mainModel instanceof IExtendDataModel) {
    			IExtendDataModel model = (IExtendDataModel) mainModel;
    			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
        		JSONObject fdOffTypeObj = (JSONObject) params.get("fdOffType");
        		String fdOffType = (String) fdOffTypeObj.get("value");
        		String fdOffTypeAll = fdOffType.split("\\.")[0];
        		ArrayList arr3 = (ArrayList)modelData.get(fdOffTypeAll);
        		String fdOffType2 = fdOffType.split("\\.")[1];
        		HashMap<String, Object> fdOffTypeObj1 = (HashMap<String, Object>) arr3.get(0);
    			String offType = (String)fdOffTypeObj1.get(fdOffType2);
        		JSONObject fdLeaveTargetsObj = (JSONObject) params.get("fdLeaveTargets");
        		String fdLeaveTargets = (String) fdLeaveTargetsObj.get("value");
        		HashMap<String, Object> fdLeaveTargets1 = (HashMap)modelData.get(fdLeaveTargets);
    			String personid = (String) fdLeaveTargets1.get("id");
        		JSONObject day_startDateObj = (JSONObject) params.get("day_startDate");
        		String day_startDate = (String) day_startDateObj.get("value");
        		String dayStartDateAll = day_startDate.split("\\.")[0];
        		ArrayList arr1 = (ArrayList)modelData.get(dayStartDateAll);
        		HashMap<String, Object> day_startDateObj1 = (HashMap<String, Object>) arr1.get(0);
        		String dayStartDate = day_startDate.split("\\.")[1];
        		Date day_startDate2 = (Date) day_startDateObj1.get(dayStartDate);
        		ZoneId timeZone = ZoneId.systemDefault();
        		LocalDate local1 = day_startDate2.toInstant().atZone(timeZone).toLocalDate();
        		JSONObject day_endDateObj = (JSONObject) params.get("day_endDate");
        		String day_endDate = (String) day_endDateObj.get("value");
        		String dayEndDateAll = day_endDate.split("\\.")[0];
        		ArrayList arr2 = (ArrayList)modelData.get(dayEndDateAll);
        		HashMap<String, Object> day_endDateObj1 = (HashMap<String, Object>) arr2.get(0);
        		String dayEndDate = day_endDate.split("\\.")[1];
        		Date day_endDate2 = (Date) day_endDateObj1.get(dayEndDate);
        		LocalDate local2 = day_endDate2.toInstant().atZone(timeZone).toLocalDate();
        			String startTime = null;
    				String endTime = null;
    				String whereBlock="sysAttendHisCategory.";
    				String day;
    				if(local1.getMonthValue()<10)
    					if(local1.getDayOfMonth()<10)
    						day = ""+local1.getYear()+"-0"+local1.getMonthValue()+"-0"+local1.getDayOfMonth(); 
    					else
        					day = ""+local1.getYear()+"-0"+local1.getMonthValue()+"-"+local1.getDayOfMonth(); 
    				else
    					if(local1.getDayOfMonth()<10)
    						day = ""+local1.getYear()+"-"+local1.getMonthValue()+"-0"+local1.getDayOfMonth(); 
    					else
    						day = ""+local1.getYear()+"-"+local1.getMonthValue()+"-"+local1.getDayOfMonth(); 
    				String sql1 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+day+"'"
    						+ "and doc_creator_id='"+personid+"' and doc_Status=0 and fd_is_across=0";
    				String sql11 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+day+"'"
    						+ "and doc_creator_id='"+personid+"' and doc_Status=0 and fd_is_across=1";
    				String sql111 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+day+"'"
    						+ "and doc_creator_id='"+personid+"' and doc_Status=0";
    				String sql2 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+day+"'"
    						+ "and doc_creator_id='"+personid+"' and doc_Status=0 and fd_work_type=0";
    				String sql3 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+day+"'"
    						+ "and doc_creator_id='"+personid+"' and doc_Status=0 and fd_work_type=1";
    				List list1 = HrCurrencyParams.getListBySql(sql1);
    				System.out.println(sql1);
    				int ddd;
    				ddd=32+3;
    				List list11 = HrCurrencyParams.getListBySql(sql111);
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
    				boolean flag = true;
    				if(list1.size()!=2){
    					flag=false;
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
    				if(startTime!=null&&endTime!=null){
    				startTime = day+" "+startTime;
    				if(endTime.contains("次日")){
    					LocalDate local = local1.minusDays(-1);
    					if(local1.getMonthValue()<10)
    						endTime=""+local.getYear()+"-0"+local.getMonthValue()+"-"+local.getDayOfMonth()+" "+endTime.split("次日")[1]; 
    				}else
    				endTime = day+" "+endTime;
    				Date startDate = DateUtil.convertStringToDate(startTime);
    				Date endDate = DateUtil.convertStringToDate(endTime);
    				HQLInfo hqlInfo = new HQLInfo();
    				hqlInfo.setWhereBlock("docCreator=:person and fdBaseWorkTime=:startDate");
    				hqlInfo.setParameter("person", getSysOrgPersonService().findByPrimaryKey(personid));
    				hqlInfo.setParameter("startDate", startDate);
					List<SysAttendMain> sysAttendMainLst = getSysAttendMainService().findList(hqlInfo);
					SysAttendMain sysAttendMain = sysAttendMainLst.get(0);
					SysAttendCategory sysAttendCategory = sysAttendMain.getFdCategory();
    				HQLInfo hqlInfo1 = new HQLInfo();
    				hqlInfo1.setWhereBlock("docCreator=:person and fdBaseWorkTime=:endDate");
    				hqlInfo1.setParameter("person", getSysOrgPersonService().findByPrimaryKey(personid));
    				hqlInfo1.setParameter("endDate", endDate);
					List<SysAttendMain> sysAttendMainLst1 = getSysAttendMainService().findList(hqlInfo1);
					List<SysAttendCategory> sysAttendCategoryList1 = getSysAttendCategoryService().findList("", "");
					SysAttendMain sysAttendMain1 = sysAttendMainLst1.get(0);
					SysAttendCategory sysAttendCategory1 = sysAttendMain1.getFdCategory();
					Date restStart = null;
					Date restEnd = null;
//					for(SysAttendCategory sysAttendCategory12:sysAttendCategoryList1){
//						List<SysOrgElement> sysOrgPersonList = sysAttendCategory12.getFdTargets();
//						for(SysOrgElement sysOrgPerson:sysOrgPersonList){
//							if(personid.equals(sysOrgPerson.getFdId())){
//								restStart=sysAttendCategory12.getFdRestStartTime();
//								restEnd=sysAttendCategory12.getFdRestEndTime();
//							}
//						}
//					}
					SysAttendCategory category = getSysAttendCategoryService().getCategoryInfo((SysOrgElement)getSysOrgPersonService().findByPrimaryKey(personid), startDate, true);
					if(category!=null){
						restStart=category.getFdRestStartTime();
						restEnd=category.getFdRestEndTime();
					}
					if(restStart!=null&&restEnd!=null){
	        		LocalDate local3 = restStart.toInstant().atZone(timeZone).toLocalDate();
	        		String dtf= "yyyy-MM-dd HH:mm:ss"; 
	        		String restStartStr = DateUtil.convertDateToString(restStart, dtf);
	        		String restEndStr = DateUtil.convertDateToString(restEnd, dtf);
	        		String startDate2 = DateUtil.convertDateToString(day_startDate2, dtf);
	        		String endDate2 = DateUtil.convertDateToString(day_endDate2, dtf);
	        		restStart = DateUtil.convertStringToDate(startDate2.split(" ")[0]+" "+restStartStr.split(" ")[1]);
	        		restEnd = DateUtil.convertStringToDate(endDate2.split(" ")[0]+" "+restEndStr.split(" ")[1]);
					if((day_startDate2.before(startDate)||!startDate.before(day_startDate2))&&(restStart.before(day_endDate2)||!day_endDate2.before(restStart))&&(day_endDate2.before(restEnd)||!restEnd.before(day_endDate2)))
					{
						sysAttendMain.setFdBaseWorkTime(restEnd);
						sysAttendMain.setFdBusiness(sysAttendBusiness);
//    				getSysAttendMainService().update(sysAttendMain);
    				getSysAttendMainService().getBaseDao().update(sysAttendMain);
    				getSysAttendMainService().getBaseDao().flushHibernateSession();
					}
					if((day_startDate2.before(startDate)||!startDate.before(day_startDate2))&&(day_endDate2.before(restStart))&&(day_endDate2.before(restEnd)||!restEnd.before(day_endDate2)))
					{
						sysAttendMain.setFdBaseWorkTime(day_endDate2);
						sysAttendMain.setFdBusiness(sysAttendBusiness);
//    				getSysAttendMainService().update(sysAttendMain);
    				getSysAttendMainService().getBaseDao().update(sysAttendMain);
    				getSysAttendMainService().getBaseDao().flushHibernateSession();
					}
//					if((day_startDate2.before(restEnd)||!restEnd.before(day_startDate2))&&(endDate.before(day_endDate2)||!day_endDate2.before(endDate))){
//						sysAttendMain1.setFdBaseWorkTime(restStart);
//						sysAttendMain1.setFdBusiness(sysAttendBusiness);
//    				getSysAttendMainService().update(sysAttendMain1);
//					}
					if((!day_startDate2.before(restEnd)||restEnd.before(day_startDate2))&&(endDate.before(day_endDate2)||!day_endDate2.before(endDate))){
						sysAttendMain1.setFdBaseWorkTime(restStart);
						sysAttendMain1.setFdStatus(5);
						sysAttendMain1.setFdBusiness(sysAttendBusiness);
    				getSysAttendMainService().getBaseDao().update(sysAttendMain1);
    				getSysAttendMainService().getBaseDao().flushHibernateSession();
					}
            }else{
//            	sysAttendMain1.setFdBaseWorkTime(sysAttendBusiness.getFdBusStartTime());
				sysAttendMain.setFdBusiness(sysAttendBusiness);
				sysAttendMain.setFdBaseWorkTime(sysAttendBusiness.getFdBusEndTime());
//				sysAttendMain1.setFdBusiness(sysAttendBusiness);
				getSysAttendMainService().getBaseDao().update(sysAttendMain);
				getSysAttendMainService().getBaseDao().flushHibernateSession();
//				getSysAttendMainService().update(sysAttendMain1);
            }
    				}
            }
            
    	}
        String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
        if (PROCESS_FLAG_RUN_VALUE.equals(processFlag) || PROCESS_FLAG_ERROR_VALUE.equals(processFlag) || processFlag==null) {
            //非考勤标识的流程。不支持
            //新流程时间不进老流程处理
            return;
        }

        logger.debug(
                "receive SysAttendLeaveListener,parameter=" + parameter);
        String routeType = execution.getNodeInstance().getFdRouteType();
        String docStatus = execution.getExecuteParameters()
                .getExpectMainModelStatus();
        if ((NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)
                || NodeInstanceUtils.ROUTE_TYPE_JUMP.equals(routeType))
                && !SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)
                && !SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
            logger.debug("start handleEvent...");
            JSONObject params = JSONObject.fromObject(parameter);
            IBaseModel mainModel = execution.getMainModel();
            if (mainModel instanceof IExtendDataModel) {
                List<SysAttendBusiness> list = getSysAttendBusinessService()
                        .findByProcessId(mainModel.getFdId());
                // 同一流程不重复处理
                if (list.isEmpty()) {
                    List<SysAttendBusiness> busList = getBusinessList(params,
                            mainModel);
                    if (busList == null || busList.isEmpty()) {
                        logger.warn("请假流程数据配置不准确,忽略处理!parameter:" + parameter);
                        throw new Exception("请假流程数据配置不准确");
                    }

                    for (Iterator it = busList.iterator(); it.hasNext(); ) {
                        SysAttendBusiness bus = (SysAttendBusiness) it.next();
                        if (bus.getFdBusType() != null) {
                            SysTimeLeaveRule sysTimeLeaveRule = AttendUtil
                                    .getLeaveRuleByType(bus.getFdBusType());
                            if (sysTimeLeaveRule == null || Boolean.FALSE.equals(sysTimeLeaveRule.getFdIsAvailable())) {
                                it.remove();// 若该请假类型找不到或无效则不处理
                                logger.warn("请假流程中请假类型无法找到匹配规则,忽略处理!fdBussType:"
                                        + bus.getFdBusType());
                                throw new Exception(ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.leaveNotFound"));
                            }
                        }
                    }
                    Map<String, String> idMap = new HashMap<String, String>();
                    for (SysAttendBusiness bus : busList) {
                        // 1.新建请假明细
                        SysTimeLeaveDetail leaveDetail = addLeaveDetail(bus);
                        // 2.扣除假期额度
                        String msg = getSysTimeLeaveDetailService().updateDeduct(leaveDetail);
                        if (StringUtil.isNotNull(msg) && !Boolean.TRUE.equals(leaveDetail.getFdCanUpdateAttend())) {
                            leaveDetail.setFdOprDesc(msg);
                            sendNotify(leaveDetail);
                            throw new RuntimeException(msg);
                        }
                        idMap.put(bus.getFdId(), leaveDetail.getFdId());
                    }
                    List<SysAttendBusiness> statBusList = new ArrayList<>();
                    for (SysAttendBusiness bus : busList) {
                        if (idMap.containsKey(bus.getFdId())) {

                            String detailId = idMap.get(bus.getFdId());
                            SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) getSysTimeLeaveDetailService().findByPrimaryKey(detailId);
                            // 扣减成功或没有开启额度限制
                            if (leaveDetail != null && (Boolean.TRUE.equals(leaveDetail.getFdCanUpdateAttend()) || Integer.valueOf(1).equals(leaveDetail.getFdOprStatus()))) {
                                //需要更新考勤记录的判断是否有考勤组
                                //boolean haveCategory = this.checkUserHaveCategory(bus.getFdTargets(), bus.getFdBusStartTime());
                                //if (haveCategory) {
                                    // 3.保存流程表单数据
                                    bus.setFdBusDetailId(detailId);
                                    getSysAttendBusinessService().add(bus);
                                    // 4.更新打卡记录
                                    updateSysAttendMainByLeaveBis(bus);
                                    // 更新成功
                                    leaveDetail.setFdUpdateAttendStatus(1);
                                    getSysTimeLeaveDetailService().update(leaveDetail);
                                    statBusList.add(bus);
                                //}
                            } else {
                                logger.warn("不符合同步请假数据到考勤,忽略处理!用户:" + bus.getFdTargets());
                                if (leaveDetail != null && Integer.valueOf(2)
                                        .equals(leaveDetail.getFdOprStatus())) {
                                    sendNotify(leaveDetail);
                                }
                                //throw new Exception("不符合同步请假数据到考勤");
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(statBusList)) {
                        // 5.重新统计考勤
                        restat(busList);
                    }
                } else {
                    logger.warn("写入请假数据失败：同一流程不重复处理");
                    throw new Exception("写入请假数据失败：同一流程不重复处理");
                }
            }
        }
    }

    /**
     * 新建请假明细
     *
     * @param business
     * @return
     * @throws Exception
     */
    protected SysTimeLeaveDetail addLeaveDetail(SysAttendBusiness business) throws Exception {
        SysTimeLeaveDetail leaveDetail = new SysTimeLeaveDetail();
        Integer startNoon = null;
        Integer endNoon = null;
        leaveDetail.setFdId(IDGenerator.generateID());
        List<SysOrgPerson> personList = getSysOrgCoreService()
                .expandToPerson(business.getFdTargets());
        SysOrgPerson person = personList.get(0);
        leaveDetail.setFdLeaveName(business.getFdLeaveName());
        SysTimeLeaveRule leaveRule = AttendUtil
                .getLeaveRuleByType(business.getFdBusType());
        String fdLeaveType = "";
        if (leaveRule != null) {
            fdLeaveType = leaveRule.getFdSerialNo();
        }
        leaveDetail.setFdLeaveType(fdLeaveType);
        leaveDetail.setFdPerson(person);
        leaveDetail.setFdStartTime(business.getFdBusStartTime());
        leaveDetail.setFdEndTime(business.getFdBusEndTime());
        SysTimeLeaveTimeDto dto = getLeaveTimes(person, Integer.valueOf(fdLeaveType),
                business.getFdBusStartTime(),
                business.getFdBusEndTime(), business.getFdStatType(),
                business.getFdStartNoon(),
                business.getFdEndNoon());
        int fdTotalTime =dto.getLeaveTimeMins();
        leaveDetail.setFdLeaveTime(dto.getLeaveTimeDays());
        leaveDetail.setFdTotalTime((float) fdTotalTime);
        leaveDetail.setFdOprType(1);
        leaveDetail.setFdOprStatus(0);
        leaveDetail.setFdOprDesc(null);
        leaveDetail.setFdReviewId(business.getFdProcessId());
        leaveDetail.setFdReviewName(business.getFdProcessName());
        if (business.getFdStatType().equals(2)) {
            //兼容如果不传上下午标志给默认值
            startNoon = (business.getFdStartNoon() == null ? 1 : business.getFdStartNoon());
            endNoon = (business.getFdEndNoon() == null ? 2 : business.getFdEndNoon());
        } else {
            startNoon = business.getFdStartNoon();
            endNoon = business.getFdEndNoon();
        }
        leaveDetail.setFdStatType(business.getFdStatType());
        leaveDetail.setFdStartNoon(startNoon);
        leaveDetail.setFdEndNoon(endNoon);
        leaveDetail.setDocCreateTime(new Date());
        leaveDetail.setDocCreator(UserUtil.getUser());
        leaveDetail.setFdIsUpdateAttend(true);
        // 设置场所
        leaveDetail.setAuthArea(business.getAuthArea());
        getSysTimeLeaveDetailService().add(leaveDetail);
        return leaveDetail;
    }






    private ISysAttendMainJobService sysAttendMainJobService;

    public ISysAttendMainJobService getSysAttendMainJobService() {
        if (sysAttendMainJobService == null) {
            sysAttendMainJobService = (ISysAttendMainJobService) SpringBeanUtil.getBean("sysAttendMainJobService");
        }
        return sysAttendMainJobService;
    }

    private ISysAttendSynDingService sysAttendSynDingService;

    public ISysAttendSynDingService getSysAttendSynDingService() {
        if (sysAttendSynDingService == null) {
            sysAttendSynDingService = (ISysAttendSynDingService) SpringBeanUtil.getBean("sysAttendSynDingService");
        }
        return sysAttendSynDingService;
    }

    @Override
    public void onApplicationEvent(Event_Common event) {
        try {
            if ("resetLeaveInfo".equals(event.getSource().toString())) {
                Map params = ((Event_Common) event).getParams();
                //重新生成某人，某天的有效考勤数据 以人、天为粒度
                List<String> personIds = (List<String>) params.get("personIds");
                List<Date> totalDateList = (List<Date>) params.get("dateList");
                if(CollectionUtils.isNotEmpty(personIds) && CollectionUtils.isNotEmpty(totalDateList)) {
                    AttendStatThread task = new AttendStatThread();
                    task.setDateList(totalDateList);
                    task.setOrgList(personIds);
                    task.setFdMethod("restat");
                    task.setFdIsCalMissed("true");
                    task.setFdOperateType("create");
                    task.setLogId(null);
                    AttendThreadPoolManager manager = AttendThreadPoolManager
                            .getInstance();
                    if (!manager.isStarted()) {
                        manager.start();
                    }
                    manager.submit(task);
                    params.clear();
                }
            } else if ("updateAttend".equals(event.getSource().toString())) {
                Map params = ((Event_Common) event).getParams();
                if (null == params || params.size() <= 0) {
                    return;
                }
                String leaveDetailId = (String) params.get("leaveDetailId");
                if(StringUtil.isNull(leaveDetailId)){
                    return;
                }
                //判断如果请假明细id 存在。则不更新
                List<String> haveList = getSysAttendBusinessService().findValue("sysAttendBusiness.fdId",String.format("sysAttendBusiness.fdBusDetailId='%s'",leaveDetailId),"sysAttendBusiness.fdBusStartTime");
                if(CollectionUtils.isNotEmpty(haveList)){
                    //如果明细表已经写成功了。则不在处理
                    return;
                }
                SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) getSysTimeLeaveDetailService().findByPrimaryKey(leaveDetailId);

                Date startTime = leaveDetail.getFdStartTime();
                Date endTime = leaveDetail.getFdEndTime();
                String personIds = leaveDetail.getFdPerson().getFdId();
                String leaveType = leaveDetail.getFdLeaveType();
                String leaveName = leaveDetail.getFdLeaveName();
                Integer statType = leaveDetail.getFdStatType();
                Integer startNoon = leaveDetail.getFdStartNoon();
                Integer endNoon = leaveDetail.getFdEndNoon();
                String processId = leaveDetail.getFdReviewId();
                String processName = leaveDetail.getFdReviewName();
                SysAttendBusiness business = getBusinessModel(startTime,
                        endTime, personIds, processId,
                        StringUtil.isNotNull(leaveType)
                                ? Integer.parseInt(leaveType) : null,
                        statType, startNoon, endNoon, leaveName, processName, null);
                business.setFdBusDetailId(leaveDetailId);
                getSysAttendBusinessService().add(business);
                // 更新考勤记录
                updateSysAttendMainByLeaveBis(business);
                List<SysAttendBusiness> list = new ArrayList<SysAttendBusiness>();
                list.add(business);

                // 更新成功
                leaveDetail.setFdUpdateAttendStatus(1);
                getSysTimeLeaveDetailService().update(leaveDetail);
                // 重新统计
                restat(list);
            }
            // 请假数据重新生成事件
            if ("regenUserAttendMain".equals(event.getSource().toString())) {
                logger.debug("receive regenUserAttendMain request...");
                Map params = ((Event_Common) event).getParams();
                String fdCategoryId = "";
                String fdTimeAreaChange = "";
                if (params != null && params.containsKey("fdCategoryId")) {
                    fdCategoryId = (String) params.get("fdCategoryId");
                }
                if (params != null && params.containsKey("fdTimeAreaChange")) {
                    fdTimeAreaChange = (String) params.get("fdTimeAreaChange");
                }
                List<SysOrgElement> areaMembers = null;
                if (params != null && params.containsKey("areaMembers")) {
                    areaMembers = (List<SysOrgElement>) params
                            .get("areaMembers");
                }
                RegenAttendThread task = new RegenAttendThread();
                task.setAreaMembers(areaMembers);
                task.setFdCategoryId(fdCategoryId);
                task.setFdTimeAreaChange(fdTimeAreaChange);
                task.setFdMethod("regenUserLeave");
                AttendThreadPoolManager manager = AttendThreadPoolManager
                        .getInstance();
                if (!manager.isStarted()) {
                    manager.start();
                }
                manager.submit(task);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 重新统计用户的考勤信息
     *
     * @param fdCategoryId     考勤组ID
     * @param fdTimeAreaChange 变更时间
     * @param areaMembers      人员列表
     * @throws Exception
     */
    private void regenUserAttendMain(String fdCategoryId,
                                     String fdTimeAreaChange, List<SysOrgElement> areaMembers)
            throws Exception {
        Set<Date> dateSet = new HashSet<Date>();
        // 节假日调整，重新计算最近2个星期的请假信息
        Date statDate = AttendUtil.getDate(new Date(), -14);
        if ("true".equals(fdTimeAreaChange)) {
            List<SysAttendCategory> cateList = this.getSysAttendCategoryService().findCategorysByTimeArea();
            if (cateList == null || cateList.isEmpty()) {
                logger.debug("没有排班考勤组,忽略处理!");
                return;
            }
            List<String> categoryIds = new ArrayList<>();
            for (SysAttendCategory category : cateList) {
                categoryIds.add(category.getFdId());
            }
            this.updateUserAttendLeave(areaMembers, categoryIds, statDate, dateSet);

            logger.debug("regenUserAttendMain finish,相应排班考勤组有:" + cateList);
            return;
        } else if (StringUtil.isNotNull(fdCategoryId)) {
            List tempList =Lists.newArrayList();
            tempList.add(fdCategoryId);
            updateUserAttendLeave(null,tempList , statDate, dateSet);
        }
        logger.debug("regenUserAttendMain finish,fdCategoryId:" + fdCategoryId);
    }

    /**
     * 重新统计考勤信息
     *
     * @param dateSet
     */
    private void restatLeave(Set<Date> dateSet, List<String> orgIdsList) {
        try {
            final List<Date> dateList = new ArrayList<Date>(dateSet);
            if (dateSet.isEmpty()) {
                return;
            }
            multicaster.attatchEvent(
                    new EventOfTransactionCommit(StringUtils.EMPTY),
                    new IEventCallBack() {
                        @Override
                        public void execute(ApplicationEvent arg0)
                                throws Throwable {
                            AttendStatThread task = new AttendStatThread();
                            task.setDateList(dateList);

                            List<String> tempList =Lists.newArrayList();
                            tempList.addAll(orgIdsList);

                            task.setOrgList(tempList);
                            AttendThreadPoolManager manager = AttendThreadPoolManager
                                    .getInstance();
                            if (!manager.isStarted()) {
                                manager.start();
                            }
                            manager.submit(task);
                        }
                    });

        } catch (Exception e) {
            logger.error("regenUserAttendMain重新统计失败:", e);
        }
    }

    protected List<SysAttendBusiness> getBusinessList(JSONObject params,
                                                      IBaseModel mainModel) throws Exception {
        List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
        try {
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo()
                    .getModelData();

            // 人员
            JSONObject targetsJson = JSONObject
                    .fromObject(params.get("fdLeaveTargets"));
            String targetsFieldName = (String) targetsJson.get("value");
            String targetIds = null;
            if (modelData.containsKey(targetsFieldName)) {
                Object targetsObj = modelData.get(targetsFieldName);
                targetIds = BeanUtils.getProperty(targetsObj, "id");
            } else {
                SysOrgElement org = (SysOrgElement) PropertyUtils
                        .getProperty(mainModel, targetsFieldName);
                targetIds = org.getFdId();
            }

            if (targetIds == null) {
                throw new Exception("获取请假数据出错：人员为空");
            }

            boolean isLeaveTypeConfig = params.containsKey("fdOffType")
                    && params.get("fdOffType") instanceof JSONObject;
            boolean isDayConfig = params.containsKey("day_startDate")
                    && params.get("day_startDate") instanceof JSONObject &&
                    params.containsKey("day_endDate")
                    && params.get("day_endDate") instanceof JSONObject;
            boolean isHalfConfig = (params.containsKey("half_startDate")
                    && params.get("half_startDate") instanceof JSONObject
                    || params.containsKey("fdLeaveStartTime")
                    && params
                    .get("fdLeaveStartTime") instanceof JSONObject)
                    &&
                    (params.containsKey("half_endDate")
                            && params.get("half_endDate") instanceof JSONObject
                            || params.containsKey("fdLeaveEndTime")
                            && params.get(
                            "fdLeaveEndTime") instanceof JSONObject);
            boolean isHourConfig = params.containsKey("hour_startTime")
                    && params.get("hour_startTime") instanceof JSONObject &&
                    params.containsKey("hour_endTime")
                    && params.get("hour_endTime") instanceof JSONObject;

            // 请假类型
            if (isLeaveTypeConfig) {
                // 配置了请假类型字段
                JSONObject leaveTypeJson = JSONObject
                        .fromObject(params.get("fdOffType"));
                String leaveTypeFieldName = (String) leaveTypeJson
                        .get("value");
                String leaveTypeFieldType = (String) leaveTypeJson
                        .get("model");

                if ("String".equals(leaveTypeFieldType)
                        || "Double".equals(leaveTypeFieldType)) {
                    // 不是明细表
                    Object tmpLeaveType = getSysMetadataParser()
                            .getFieldValue(
                                    mainModel,
                                    leaveTypeFieldName, false);
                    Integer leaveType = getCheckboxFieldValue(tmpLeaveType);
                    addBizWithLeaveType(targetIds, params, model, busList,
                            leaveType, isDayConfig, isHalfConfig, isHourConfig);

                } else if ("String[]".equals(leaveTypeFieldType)
                        || "Double[]".equals(leaveTypeFieldType)) { // 明细表
                    String detailName = leaveTypeFieldName.split("\\.")[0];
                    String leaveTypeName = leaveTypeFieldName.split("\\.")[1];
                    List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
                            .get(detailName);
                    for (int k = 0; k < detailList.size(); k++) {
                        HashMap detail = detailList.get(k);
                        Integer leaveType = getCheckboxFieldValue(
                                detail.get(leaveTypeName));
                        params.put("detailName", detailName);
                        params.put("detailIdx", k);
                        addBizWithLeaveType(targetIds, params, model, busList,
                                leaveType, isDayConfig, isHalfConfig,
                                isHourConfig);
                    }
                } else {
                    throw new Exception("获取请假数据出错：请假类型字段必须是单选框或下拉框");
                }
            } else {
                throw new Exception("获取请假数据出错：没有配置请假类型字段");
            }
            if (CollectionUtils.isNotEmpty(busList)) {
                // 设置场所（明细的场所跟随流程文档）
                SysAuthArea authArea = null;
                if (mainModel instanceof BaseAuthModel) {
                    BaseAuthModel authModel = (BaseAuthModel) mainModel;
                    authArea = authModel.getAuthArea();
                }
                for (SysAttendBusiness busines : busList) {
                    busines.setAuthArea(authArea);
                }
            }
            return busList;
        } catch (Exception e) {
            logger.error("获取请假数据出错:" + e.getMessage());
            return null;
        }
    }

    /**
     * 按请假类型
     *
     * @param targetIds
     * @param params
     * @param mainModel
     * @param busList
     * @param leaveType
     * @param isDayConfig
     * @param isHalfConfig
     * @param isHourConfig
     * @throws Exception
     */
    private void addBizWithLeaveType(String targetIds, JSONObject params,
                                     IBaseModel mainModel, List<SysAttendBusiness> busList,
                                     Integer leaveType, boolean isDayConfig, boolean isHalfConfig,
                                     boolean isHourConfig) throws Exception {
        IExtendDataModel model = (IExtendDataModel) mainModel;
        Map<String, Object> modelData = model.getExtendDataModelInfo()
                .getModelData();
        if (leaveType != null) {
            SysTimeLeaveRule sysTimeLeaveRule = AttendUtil
                    .getLeaveRuleByType(leaveType);
            if (sysTimeLeaveRule != null
                    && Boolean.TRUE.equals(sysTimeLeaveRule.getFdIsAvailable())) {
                Integer fdStatType = sysTimeLeaveRule.getFdStatType();
                String fdLeaveName = sysTimeLeaveRule.getFdName();
                // 按照配置的请假规则的统计方式，选择取哪些字段的值
                if (fdStatType == 1 && isDayConfig) {
                    addBizByDay(targetIds, params, model, busList, leaveType,
                            fdLeaveName);
                } else if (fdStatType == 2 && isHalfConfig) {
                    addBizByHalfDay(targetIds, params, model, busList,
                            leaveType, fdLeaveName);
                } else if (fdStatType == 3 && isHourConfig) {
                    addBizByHour(targetIds, params, model, busList, leaveType,
                            fdLeaveName);
                } else {
                    throw new Exception("请假类型和配置的统计方式不对应");
                }
            } else {
                logger.error("没找到该请假类型");
            }
        } else {// 没有填请假类型
            if (isDayConfig) {
                addBizByDay(targetIds, params, model, busList, null, null);
            } else if (isHalfConfig) {
                addBizByHalfDay(targetIds, params, model, busList, null, null);
            } else if (isHourConfig) {
                addBizByHour(targetIds, params, model, busList, null, null);
            }
        }
    }

    /**
     * 按天
     *
     * @param targetIds
     * @param params
     * @param mainModel
     * @param busList
     * @param leaveType
     * @throws Exception
     */
    private void addBizByDay(String targetIds, JSONObject params,
                             IBaseModel mainModel, List<SysAttendBusiness> busList,
                             Integer leaveType, String leaveName) throws Exception {
        try {
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo()
                    .getModelData();
            String docSubject = (String) getSysMetadataParser()
                    .getFieldValue(mainModel, "docSubject", false);

            JSONObject startJson = JSONObject
                    .fromObject(params.get("day_startDate"));
            JSONObject endJson = JSONObject
                    .fromObject(params.get("day_endDate"));
            if (startJson == null || endJson == null) {
                return;
            }
            String startName = (String) startJson.get("value");
            String endName = (String) endJson.get("value");

            Date startTime = null;
            Date endTime = null;
            if (startName.indexOf(".") == -1
                    && endName.indexOf(".") == -1) {// 是否明细表
                // 开始日期，结束日期
                startTime = (Date) getSysMetadataParser()
                        .getFieldValue(
                                mainModel,
                                startName, false);
                endTime = (Date) getSysMetadataParser().getFieldValue(
                        mainModel,
                        endName, false);
            } else {// 明细表
                String detailName = startName.split("\\.")[0];
                String leaveTypeDetailName = (String) params.get("detailName");
                Integer detailIdx = (Integer) params.get("detailIdx");
                if (detailName.equals(leaveTypeDetailName)
                        && detailIdx != null) {
                    List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
                            .get(detailName);
                    HashMap detail = detailList.get(detailIdx);
                    // 开始日期结束日期
                    startTime = (Date) detail.get(startName.split("\\.")[1]);
                    endTime = (Date) detail.get(endName.split("\\.")[1]);
                }
            }

            if (startTime == null || endTime == null
                    || startTime.getTime() > endTime.getTime()) {
                return;
            }

            busList.add(getBusinessModel(AttendUtil.getDate(startTime, 0),
                    AttendUtil.getDate(endTime, 0), targetIds,
                    mainModel.getFdId(), leaveType, 1, null, null, leaveName,
                    docSubject, mainModel));
        } catch (Exception e) {
            logger.error("获取“按天”请假数据出错:" + e.getMessage());
        }
    }

    /**
     * 按半天
     *
     * @param targetIds
     * @param params
     * @param mainModel
     * @param busList
     * @param leaveType
     * @throws Exception
     */
    private void addBizByHalfDay(String targetIds, JSONObject params,
                                 IBaseModel mainModel, List<SysAttendBusiness> busList,
                                 Integer leaveType, String leaveName) throws Exception {
        try {
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo()
                    .getModelData();
            String docSubject = (String) getSysMetadataParser()
                    .getFieldValue(mainModel, "docSubject", false);

            JSONObject startJson = null;
            JSONObject endJson = null;
            if (params.containsKey("fdLeaveStartTime")) {
                startJson = JSONObject
                        .fromObject(params.get("fdLeaveStartTime"));
            } else if (params.containsKey("half_startDate")) {
                startJson = JSONObject.fromObject(params.get("half_startDate"));
            }
            if (params.containsKey("fdLeaveEndTime")) {
                endJson = JSONObject.fromObject(params.get("fdLeaveEndTime"));
            } else if (params.containsKey("half_endDate")) {
                endJson = JSONObject.fromObject(params.get("half_endDate"));
            }
            if (startJson == null || endJson == null) {
                return;
            }
            boolean isStartNoonConfig = params.containsKey("half_startNoon")
                    && params.get("half_startNoon") instanceof JSONObject;
            boolean isEndNoonConfig = params.containsKey("half_endNoon")
                    && params.get("half_endNoon") instanceof JSONObject;
            JSONObject startNoonJson = JSONObject
                    .fromObject(params.get("half_startNoon"));
            JSONObject endNoonJson = JSONObject
                    .fromObject(params.get("half_endNoon"));

            if(logger.isDebugEnabled()){
                logger.debug("流程参数--开始：{}",params);
            }
            String startName = (String) startJson.get("value");
            String startType = (String) startJson.get("model");
            String endName = (String) endJson.get("value");
            String endType = (String) endJson.get("model");

            Date startTime = null;
            Date endTime = null;
            Integer startNoon = null;
            Integer endNoon = null;
            if (startName.indexOf(".") == -1
                    && endName.indexOf(".") == -1) {// 不是明细表
                // 开始日期/时间，结束日期/时间
                startTime = (Date) getSysMetadataParser()
                        .getFieldValue(
                                mainModel,
                                startName, false);
                endTime = (Date) getSysMetadataParser().getFieldValue(
                        mainModel,
                        endName, false);
                // 开始上下午标识
                if (isStartNoonConfig) {
                    Object obj = getSysMetadataParser().getFieldValue(mainModel,
                            (String) startNoonJson.get("value"), false);
                    startNoon = getCheckboxFieldValue(obj);
                }
                // 结束上下午标识
                if (isEndNoonConfig) {
                    Object obj = getSysMetadataParser().getFieldValue(mainModel,
                            (String) endNoonJson.get("value"), false);
                    endNoon = getCheckboxFieldValue(obj);
                }
            } else {// 明细表
                String detailName = startName.split("\\.")[0];
                String leaveTypeDetailName = (String) params.get("detailName");
                Integer detailIdx = (Integer) params.get("detailIdx");
                if (detailName.equals(leaveTypeDetailName)
                        && detailIdx != null) {
                    List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
                            .get(detailName);
                    HashMap detail = detailList.get(detailIdx);
                    // 开始日期/时间，结束日期/时间
                    startTime = (Date) detail.get(startName.split("\\.")[1]);
                    endTime = (Date) detail.get(endName.split("\\.")[1]);
                    // 开始上下午标识
                    if (isStartNoonConfig) {
                        Object obj = detail.get(startNoonJson.getString("value")
                                .split("\\.")[1]);
                        startNoon = getCheckboxFieldValue(obj);
                    }
                    // 结束上下午标识
                    if (isEndNoonConfig) {
                        Object obj = detail.get(
                                endNoonJson.getString("value").split("\\.")[1]);
                        endNoon = getCheckboxFieldValue(obj);
                    }
                }
            }
            if (startTime == null || endTime == null
                    || startTime.getTime() > endTime.getTime()) {
                return;
            }
            if (startNoon == null && endNoon != null
                    || startNoon != null && endNoon == null) {
                startNoon = null;
                endNoon = null;
            }
            // 兼容以前配置
            if (("DateTime".equals(startType) && "DateTime".equals(endType)
                    || "DateTime[]".equals(startType)
                    && "DateTime[]".equals(endType))
                    && !isStartNoonConfig && !isEndNoonConfig) {
                if (startTime.getTime() < endTime.getTime()) {
                    // fdStatType为null，兼容以前数据
                    busList.add(getBusinessModel(startTime, endTime,
                            targetIds, mainModel.getFdId(), leaveType, null,
                            null, null, leaveName, docSubject, mainModel));
                }
            } else {
                busList.add(
                        getBusinessModel(AttendUtil.getDate(startTime, 0),
                                AttendUtil.getDate(endTime, 0), targetIds,
                                mainModel.getFdId(), leaveType, 2,
                                startNoon, endNoon, leaveName, docSubject, mainModel));
            }


        } catch (Exception e) {
            logger.error("获取“按半天”请假数据出错:" + e.getMessage());
        }finally {
            if(logger.isDebugEnabled()){
                logger.debug("流程参数--结束：{}",params);
            }
        }
    }

    /**
     * 按小时
     *
     * @param targetIds
     * @param params
     * @param mainModel
     * @param busList
     * @param leaveType
     * @throws Exception
     */
    private void addBizByHour(String targetIds, JSONObject params,
                              IBaseModel mainModel, List<SysAttendBusiness> busList,
                              Integer leaveType, String leaveName)
            throws Exception {
        try {
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo()
                    .getModelData();
            String docSubject = (String) getSysMetadataParser()
                    .getFieldValue(mainModel, "docSubject", false);
            JSONObject startJson = JSONObject
                    .fromObject(params.get("hour_startTime"));
            JSONObject endJson = JSONObject
                    .fromObject(params.get("hour_endTime"));
            if (startJson == null || endJson == null) {
                return;
            }

            String startName = (String) startJson.get("value");
            String endName = (String) endJson.get("value");
            String endType = (String) endJson.get("model");

            Date startTime = null;
            Date endTime = null;
            if (startName.indexOf(".") == -1
                    && endName.indexOf(".") == -1) {// 不是明细表
                // 开始日期时间，结束日期时间
                startTime = (Date) getSysMetadataParser().getFieldValue(
                        mainModel,
                        startName, false);
                endTime = (Date) getSysMetadataParser().getFieldValue(mainModel,
                        endName, false);

                // 结束为日期类型时，设置为第二天凌晨
                if ("Date".equals(endType) && endTime != null
                        && endTime.getHours() == 0
                        && endTime.getMinutes() == 0) {
                    endTime = AttendUtil.getDate(endTime, 1);
                }
            } else {
                String detailName = startName.split("\\.")[0];
                String leaveTypeDetailName = (String) params.get("detailName");
                Integer detailIdx = (Integer) params.get("detailIdx");
                if (detailName.equals(leaveTypeDetailName)
                        && detailIdx != null) {
                    List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
                            .get(detailName);
                    HashMap detail = detailList.get(detailIdx);

                    // 开始日期时间，结束日期时间
                    startTime = (Date) detail
                            .get(startName.split("\\.")[1]);
                    endTime = (Date) detail
                            .get(endName.split("\\.")[1]);

                    // 结束为日期类型时，设置为第二天凌晨
                    if ("Date[]".equals(endType) && endTime != null
                            && endTime.getHours() == 0
                            && endTime.getMinutes() == 0) {
                        endTime = AttendUtil.getDate(endTime, 1);
                    }

                }
            }
            if (startTime == null || endTime == null
                    || startTime.getTime() >= endTime.getTime()) {
                return;
            }
            busList.add(getBusinessModel(startTime, endTime, targetIds,
                    mainModel.getFdId(), leaveType, 3, null, null, leaveName,
                    docSubject, model));
        } catch (Exception e) {
            logger.error("获取“按小时”请假数据出错:" + e.getMessage());
        }
    }

    private SysAttendBusiness getBusinessModel(Date fdBusStartTime,
                                               Date fdBusEndTime, String targetIds, String fdProcessId,
                                               Integer fdBusType, Integer fdStatType, Integer fdStartNoon,
                                               Integer fdEndNoon, String fdLeaveName, String fdProcessName, IBaseModel model)
            throws Exception {
        SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
        sysAttendBusiness.setFdId(IDGenerator.generateID());
        sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
        sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
        sysAttendBusiness.setFdProcessId(fdProcessId);
        if (StringUtil.isNotNull(fdProcessId)) {
            sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(model, fdProcessId));
        }
        sysAttendBusiness.setFdTargets(getSysOrgCoreService()
                .findByPrimaryKeys(targetIds.split(";")));
        sysAttendBusiness.setFdType(5);
        sysAttendBusiness.setFdBusType(fdBusType);
        sysAttendBusiness.setFdStatType(fdStatType);
        sysAttendBusiness.setFdStartNoon(fdStartNoon);
        sysAttendBusiness.setFdEndNoon(fdEndNoon);
        sysAttendBusiness.setDocCreateTime(new Date());
        sysAttendBusiness.setFdLeaveName(fdLeaveName);
        sysAttendBusiness.setFdProcessName(fdProcessName);
        return sysAttendBusiness;
    }

    private void restat(List<SysAttendBusiness> busList) {
        try {
            reStatistics(busList,multicaster);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("请假重新统计出错" + e.getMessage(), e);
        }
    }

    private List<SysOrgElement> getStatOrgList(List<SysAttendBusiness> busList)
            throws Exception {
        List<SysOrgElement> statOrgs = new ArrayList<SysOrgElement>();
        Set<SysOrgElement> orgs = new HashSet<SysOrgElement>();
        for (SysAttendBusiness bus : busList) {
            List<SysOrgElement> targets = bus.getFdTargets();
            if (targets != null && !targets.isEmpty()) {
                List personList = getSysOrgCoreService().expandToPerson(targets);
                orgs.addAll(personList);
            }
        }
        statOrgs.addAll(orgs);
        return statOrgs;
    }

    private List<Date> getStatDateList(List<SysAttendBusiness> busList)
            throws Exception {
        List<Date> statDates = new ArrayList<Date>();
        for (SysAttendBusiness bus : busList) {
            Date startTime = AttendUtil.getDate(bus.getFdBusStartTime(), 0);
            Date endTime = AttendUtil.getDate(bus.getFdBusEndTime(), 0);
            Calendar cal = Calendar.getInstance();
            for (cal.setTime(startTime); cal.getTime()
                    .compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
                statDates.add(cal.getTime());
            }
        }
        return statDates;
    }

    private Integer getCheckboxFieldValue(Object value) {
        if (value != null) {
            try {
                if (value instanceof String) {
                    if ("am".equalsIgnoreCase((String) value)) {
                        value = "1";
                    }
                    if ("pm".equalsIgnoreCase((String) value)) {
                        value = "2";
                    }
                    return Integer.parseInt((String) value);
                } else if (value instanceof Number) {
                    return ((Number) value).intValue();
                } else {
                    return null;
                }
            } catch (Exception e) {
                logger.error("请假类型字段获取数据出错:" + value);
            }
        }
        return null;
    }

    /**
     * 重新生成用户请假记录
     *
     * @param categoryIds 考勤组ID组
     * @param date        重新生成开始日期,目前只支持重新生成当天之后的请假记录
     * @throws Exception
     */
    private void updateUserAttendLeave(List<SysOrgElement> areaMembers, List<String> categoryIds, Date date, Set<Date> dateSet) throws Exception {
        if (date == null) {
            date = new Date();
        }
        date = AttendUtil.getDate(date, 0);
        List<String> personList = new ArrayList<>();
        //根据原始考勤组 获取对应日期的考勤人员
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            //根据排班配置中的人员和考勤组来对应人员
            if (CollectionUtils.isNotEmpty(areaMembers)) {
                personList.addAll(this.getSysAttendCategoryService().getTimeAreaAttendPersonIds(areaMembers, categoryIds, date));
            } else {
                personList.addAll(this.getSysAttendCategoryService().getAttendPersonIds(categoryIds, date, true));
            }
        }
        if (CollectionUtils.isNotEmpty(personList)) {
            // 获取用户请假流程
            List<SysAttendBusiness> bussList = getSysAttendBusinessService().findList(personList, date, 5);
            // 获取有效请假用户ID集合
            List<String> userList = getLeaveUsers(bussList);
            if (bussList == null || bussList.isEmpty()) {
                logger.warn("重新计算用户请假考勤记录时,用户记录为空,不需处理!");
                return;
            }
            int size = userList.size();
            if (size > 20) {
                CountDownLatch latch = new CountDownLatch(size);
                for (int i = 0; i < size; i++) {
                    String docCreatorId = (String) userList.get(i);
                    MulitiUpdate update = new MulitiUpdate(
                            bussList, docCreatorId,
                            date, dateSet, latch);
                    leaveTaskExecutor.execute(update);
                }
                latch.await(1, TimeUnit.HOURS);
            } else {
                for (int i = 0; i < userList.size(); i++) {
                    String docCreatorId = (String) userList.get(i);
                    multiUpdate(date, dateSet, bussList, docCreatorId);
                }
            }
            //重新统计人员
            restatLeave(dateSet, userList);
        }

    }

    private void multiUpdate(Date date, Set<Date> dateSet,
                             List<SysAttendBusiness> bussList, String docCreatorId)
            throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            // 1.重新计算用户假期额度
            // 获取用户请假明细
            List<SysTimeLeaveDetail> userLeaveList = getSysTimeLeaveDetailService().findLeaveDetail(docCreatorId, date, true);

            if (userLeaveList == null || userLeaveList.isEmpty()) {
                logger.warn("获取用户请假明细记录为空,不需处理!用户:" + docCreatorId);
                return;
            }
            // 某用户请假流程
            List<SysAttendBusiness> busList = getUserLeaveBuss(bussList,
                    docCreatorId);
            // 更新用户请假额度
            updateUserLeaveAmount(userLeaveList, date);

            // 2.重新生成考勤记录
            for (SysAttendBusiness buss : busList) {
                Date fdStartTime = buss.getFdBusStartTime();
                Date fdEndTime = buss.getFdBusEndTime();
                if (fdStartTime.before(date)) {
                    fdStartTime = date;
                }
                if (fdEndTime.before(date)) {
                    continue;
                }
                // 重新生成请假记录
                updateUserLeaveMain(buss, fdStartTime, dateSet);
            }
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error("重新生成用户请假数据过程失败,事务回滚!用户ID:" + docCreatorId, e);
            if (status != null) {
                TransactionUtils.getTransactionManager()
                        .rollback(status);
            }
        }
    }

    /**
     * @param userLeaveList 请假明细
     * @param startTime     开始时间
     * @throws Exception
     */
    private void updateUserLeaveAmount(List<SysTimeLeaveDetail> userLeaveList,
                                       Date startTime) throws Exception {
        for (SysTimeLeaveDetail leaveDetail : userLeaveList) {
            Date fdStartTime = leaveDetail.getFdStartTime();
            Date fdEndTime = leaveDetail.getFdEndTime();
            Integer statType = leaveDetail.getFdStatType();
            Integer fdOprStatus = leaveDetail.getFdOprStatus();
            Float fdLeaveTime = leaveDetail.getFdLeaveTime();
            Float fdTotalTime = leaveDetail.getFdTotalTime();
            String fdLeaveType = leaveDetail.getFdLeaveType();
            SysOrgPerson person = leaveDetail.getFdPerson();
            boolean isStarted = false;
            if (!Integer.valueOf(1).equals(fdOprStatus)) {
                // 未减扣成功时,不处理
                logger.warn(
                        "未减扣成功时,不处理!leaveDetailId:" + leaveDetail.getFdId());
                continue;
            }
            if (fdEndTime.before(startTime)) {
                continue;
            }
            // 请假开始时间在当天之前,则认为已开启
            if (fdStartTime.before(startTime)) {
                fdStartTime = startTime;
                isStarted = true;
            }
            SysTimeLeaveRule sysTimeLeaveRule = AttendUtil
                    .getLeaveRuleByType(Integer.valueOf(fdLeaveType));
            if (sysTimeLeaveRule == null) {
                logger.warn(
                        "重新生成请假记录时,用户请假假期类型为空,忽略处理!fdLeaveType:" + fdLeaveType
                                + ";leaveDetailId:" + leaveDetail.getFdId());
                continue;
            }
            // 上下午标识
            Integer startNoon = leaveDetail.getFdStartNoon();
            if (Integer.valueOf(2).equals(statType) && isStarted) {
                startNoon = 1;
            }
            SysTimeLeaveTimeDto dto = getLeaveTimes(person,
                    Integer.valueOf(fdLeaveType), fdStartTime,
                    fdEndTime, statType, startNoon,
                    leaveDetail.getFdEndNoon());
            Integer fdNewTotalTime=dto.getLeaveTimeMins();
            if (fdNewTotalTime == fdTotalTime.intValue()) {
                continue;
            }
            if (!isStarted) {
                logger.warn("重新计算当天及之后的请假天数,用户:" + person.getFdName());
                getSysTimeLeaveDetailService().updateLeaveDetail(
                        leaveDetail.getFdId(), fdNewTotalTime, statType,
                        fdStartTime);
                continue;
            }
            // 需要计算过去请假时长(注:半天请假类型时,结束时间的statType应为下午)
            Integer endNoon = leaveDetail.getFdEndNoon();
            if (Integer.valueOf(2).equals(statType)) {
                endNoon = 2;
            }
            List<Date> dateList = SysTimeUtil.getDateList(statType,
                    leaveDetail.getFdStartTime(),
                    AttendUtil.getEndDate(startTime, -1),
                    leaveDetail.getFdStartNoon(), endNoon);
            if (dateList.size() < 2) {
                logger.error("更新考勤请假记录出错：日期有误" + ";leaveDetailId:"
                        + leaveDetail.getFdId());
                throw new Exception("更新考勤请假记录出错：日期有误");
            }
            List<SysAttendMain> mainList = this.getSysAttendMainService().findList(
                    person.getFdId(), leaveDetail.getFdStartTime(),
                    AttendUtil.getDate(startTime, -1));
            if (mainList.isEmpty()) {
                logger.warn("重新计算历史考勤请假记录时数据为空,重新计算当天及之后的请假天数!用户:"
                        + person.getFdName());
                getSysTimeLeaveDetailService().updateLeaveDetail(
                        leaveDetail.getFdId(), fdNewTotalTime, statType,
                        fdStartTime);
                continue;
            }
            // 已休的请假时长
            int leaveMins = getLeaveTimeMins(dateList, statType, person,
                    mainList, sysTimeLeaveRule);
            if (leaveMins + fdNewTotalTime == fdTotalTime.intValue()) {
                continue;
            }
            getSysTimeLeaveDetailService().updateLeaveDetail(leaveDetail.getFdId(),
                    fdNewTotalTime + leaveMins, statType, fdStartTime);
        }

    }


    private int getLeaveTimeMins(List<Date> dateList, Integer statType,
                                 SysOrgPerson person, List<SysAttendMain> mainList,
                                 SysTimeLeaveRule sysTimeLeaveRule)
            throws Exception {
        int leaveMins = 0;
        for (int i = 0; i < dateList.size() - 1; i++) {
            Date leaveStart = dateList.get(i);
            Date leaveEnd = dateList.get(i + 1);
            Date date = AttendUtil.getDate(leaveStart, 0);
            int daysMins = 0;

            if (leaveStart == null || leaveEnd == null
                    || leaveStart.getTime() > leaveEnd.getTime()) {
                continue;
            }
            //某天打卡记录
            List<SysAttendMain> dateMainList = getUserAttendMainByDate(mainList, date);
            long totalMin = SysTimeUtil.getTotalMins(leaveStart, leaveEnd);
            if (statType == 1 && !dateMainList.isEmpty()) {// 按天
                if (totalMin >= 24 * 60) {
                    daysMins = 24 * 60;
                }
            } else if (statType == 2 && !dateMainList.isEmpty()) {// 按半天
                if (totalMin > 0 && totalMin <= 12 * 60) {
                    daysMins = 12 * 60;
                } else if (totalMin >= 24 * 60) {
                    daysMins = 24 * 60;
                }
            } else if (statType == 3) {// 按小时
                if (totalMin > 0) {
                    daysMins = getLeaveMins(leaveStart, leaveEnd, person,
                            date, dateMainList);
                }
            }
            leaveMins += daysMins;
        }
        return leaveMins;
    }

    private int getLeaveMins(Date leaveStart, Date leaveEnd,
                             SysOrgPerson person, Date date, List<SysAttendMain> recordList)
            throws Exception {
        if (recordList == null || recordList.isEmpty()) {
            return 0;
        }
        Set<Integer> sets = new HashSet<Integer>();
        for (SysAttendMain main : recordList) {
            Date fdBaseWorkTime = main.getFdBaseWorkTime();
            if (fdBaseWorkTime != null) {
                sets.add(AttendUtil.getHMinutes(fdBaseWorkTime));
            }
        }
        List<Integer> list = new ArrayList<Integer>(sets);
        if (list.size() % 2 != 0) {
            String error = "重新计算用户请假历史记录小时数时出错,忽略该用户数据!用户名:"
                    + person.getFdName();
            logger.error(error);
            throw new Exception(error);
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        SysAttendMain record = recordList.get(0);
        List<Map<String, Object>> signTimeList = new ArrayList<Map<String, Object>>();
        // 获取班次休息时间
        List<Map<String, Object>> nowSignTimeList = this.getSysAttendCategoryService().getAttendSignTimes(CategoryUtil.getFdCategoryInfo(record), date, person, true);
        Date restStart = null;
        Date restEnd = null;
        if (nowSignTimeList != null && !nowSignTimeList.isEmpty()) {
            Map<String, Object> map = nowSignTimeList.get(0);
            if (map.containsKey("fdRestStartTime")
                    && map.containsKey("fdRestEndTime")) {
                restStart = (Date) map.get("fdRestStartTime");
                restEnd = (Date) map.get("fdRestEndTime");
            }
        }

        for (Integer signTimeMin : list) {
            Date signTime = new Date(date.getTime());
            signTime.setHours(signTimeMin / 60);
            signTime.setMinutes(signTimeMin % 60);
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("signTime", signTime);
            if (restStart != null) {
                m.put("fdRestStartTime", restStart);
                m.put("fdRestEndTime", restEnd);
            }
            signTimeList.add(m);
        }
        int mins = SysTimeUtil.getLeaveMins(leaveStart, leaveEnd, date,
                signTimeList);
        return mins;
    }

    private List getUserAttendMainByDate(List<SysAttendMain> mainList,
                                         Date date) {
        List<SysAttendMain> newList = new ArrayList<SysAttendMain>();
        for (SysAttendMain main : mainList) {
            Date workDate = main.getFdWorkDate();
            if (date.equals(AttendUtil.getDate(workDate, 0))) {
                newList.add(main);
            }
        }
        return newList;
    }

    private void updateUserLeaveMain(SysAttendBusiness business,
                                     Date fdStartTime, Set<Date> dateSet) throws Exception {
        if (business.getFdTargets() == null
                || business.getFdTargets().isEmpty()) {
            logger.error("该请假流程没有配置用户:business:" + business.getFdId());
            return;
        }
        SysOrgElement person = business.getFdTargets().get(0);
        Date fdEndTime = business.getFdBusEndTime();
        Integer fdStartNoon = business.getFdStartNoon();
        Integer fdEndNoon = business.getFdEndNoon();
        Integer fdStatType = business.getFdStatType();
        Integer fdLeaveType = business.getFdBusType();
        String fdCategoryId = this.getSysAttendCategoryService()
                .getAttendCategory(person);
        if (StringUtil.isNull(fdCategoryId)) {
            logger.warn("用户没有分配考勤组,忽略处理!用户名:" + person.getFdName());
            return;
        }

        // 分割成每天
        List<Date> dateList = SysTimeUtil.getDateList(fdStatType,
                fdStartTime, fdEndTime, fdStartNoon, fdEndNoon);
        if (dateList.size() < 2) {
            logger.error("更新考勤请假记录出错：日期有误");
            return;
        }

        SysAttendCategory category = null;
        if (StringUtil.isNotNull(fdCategoryId)) {
            category = (SysAttendCategory) getSysAttendCategoryService()
                    .findByPrimaryKey(fdCategoryId, null, true);
        }
        if (category == null) {
            logger.warn("用户考勤组为空,忽略处理!用户名:" + person.getFdName());
            return;
        }
        // 每天
        for (int i = 0; i < dateList.size() - 1; i++) {
            Date startTime = dateList.get(i);
            Date endTime = dateList.get(i + 1);
            Date nextEndTime = null;
            if ((i + 2) < dateList.size()) {
                nextEndTime = dateList.get(i + 2);
            }
            Date startDate = AttendUtil.getDate(startTime, 0);
            dateSet.add(startDate);
            List<Map<String, Object>> signTimeList = getSignTimeList(category, startTime, person, true);

            if (signTimeList.isEmpty()) {
                logger.warn("更新考勤请假记录时,获取班次信息为空,忽略处理!");
                continue;
            }
            //开始时间大于当天班次的最晚打卡时间点，则不查昨日的。否则就查昨日的
            Date workDate = getSysAttendCategoryService().getTimeAreaDateOfDate(startTime,startDate,signTimeList);
            boolean searchDay =true;
            if(workDate ==null){
                searchDay =false;
                //判断其是否属于昨日排班范围内
                List<Map<String, Object>>  yesterdaySignTimeList = getSignTimeList(category,AttendUtil.getDate(startTime,-1), person,false);
                if(org.apache.commons.collections.CollectionUtils.isNotEmpty(yesterdaySignTimeList)){
                    workDate = getSysAttendCategoryService().getTimeAreaDateOfDate(startTime,startDate,signTimeList);
                }
            }
            // 某天的打卡记录
            List<SysAttendMain> attendRecords = getUserAttendMainByDay( person, startTime, endTime,searchDay);
            // 某天请假记录
            List<SysAttendMain> leaveRecordList = getUserLeaveAttendMain(attendRecords, startDate);
            if (isRestDay(startDate, category, person)) {
                if (fdLeaveType != null) {
                    SysTimeLeaveRule sysTimeLeaveRule = AttendUtil
                            .getLeaveRuleByType(fdLeaveType);
                    if (sysTimeLeaveRule != null) {
                        if (Integer.valueOf(1)
                                .equals(sysTimeLeaveRule
                                        .getFdStatDayType())) {
                            // 节假日按工作日计算时 休息日不需生成请假记录
                            if (!leaveRecordList.isEmpty()) {
                                for (SysAttendMain main : leaveRecordList) {
                                    main.setDocStatus(1);
                                    main.setDocAlterTime(new Date());
                                    main.setFdAlterRecord(
                                            "假期类型按工作日计算,请假数据置为无效");
                                    this.getSysAttendMainService().getBaseDao()
                                            .update(main);
                                }
                                logger.warn("假期类型按工作日计算,请假数据置为无效!userName:"
                                        + person.getFdName() + ";date:"
                                        + startDate + ";考勤组:"
                                        + category.getFdName());
                            }
                            continue;
                        }
                    } else {
                        logger.warn("无法获取假期类型,假期类型编号为:" + fdLeaveType);
                        throw new Exception("无法获取假期类型,假期类型编号为:" + fdLeaveType);
                    }
                } else {
                    logger.warn("无法获取假期类型,假期类型编号为空");
                    throw new Exception("无法获取假期类型,假期类型编号为空");
                }
            }



            for (Iterator<SysAttendMain> it = attendRecords.iterator(); it
                    .hasNext(); ) {
                SysAttendMain record = (SysAttendMain) it.next();
                String categoryId = record.getFdHisCategory() != null ? record.getFdHisCategory().getFdId() : null;
                // 换了考勤组
                if (!categoryId.equals(fdCategoryId)) {
                    // 删除原有记录
                    record.setDocStatus(1);
                    record.setFdAlterRecord(
                            "更换了考勤组,原请假记录置为无效,并重新生成");
                    record.setDocAlterTime(new Date());
                    getSysAttendMainService().getBaseDao().update(record);
                    //待办置为已办
                    setAttendNotifyToDone(record);
                    it.remove();
                }
            }
            updateAttendMainExcetion(person, category, attendRecords, signTimeList, startTime, endTime, nextEndTime, business, 5);

        }
    }

    /**
     * 获取用户某天的请假记录
     *
     * @param mainList
     * @param date
     * @return
     */
    private List getUserLeaveAttendMain(List<SysAttendMain> mainList,
                                        Date date) {
        List<SysAttendMain> list = new ArrayList<SysAttendMain>();
        for (SysAttendMain main : mainList) {
            Date docCreateTime = main.getDocCreateTime();
            if (Boolean.TRUE.equals(main.getFdIsAcross())) {
                docCreateTime = AttendUtil.getDate(docCreateTime, -1);
            }
            if (AttendUtil.getDate(date, 0)
                    .equals(AttendUtil.getDate(docCreateTime, 0))
                    && main.getFdStatus() == 5) {
                list.add(main);
            }
        }
        return list;
    }

    /**
     * 获取用户同步到考勤的请假流程,并过滤重复
     *
     * @param recordList
     * @param docCreatorId
     * @return
     */
    private List getUserLeaveBuss(List<SysAttendBusiness> recordList,
                                  String docCreatorId) {
        Set<SysAttendBusiness> busSet = new HashSet<SysAttendBusiness>();
        for (SysAttendBusiness main : recordList) {
            List<SysOrgElement> targets = main.getFdTargets();
            if (targets == null || targets.isEmpty()) {
                continue;
            }
            for (SysOrgElement org : targets) {
                if (org.getFdId().contains(docCreatorId)) {
                    busSet.add(main);
                }
            }
        }
        List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
        // 过滤重复数据
        Map<String, SysAttendBusiness> maps = new HashMap<String, SysAttendBusiness>();
        for (SysAttendBusiness bus : busSet) {
            Date startTime = bus.getFdBusStartTime();
            Date endTime = bus.getFdBusEndTime();
            Integer busType = bus.getFdBusType();
            // 根据这三个参数判断是否重复
            String key = startTime.getTime() + "_" + endTime.getTime() + "_"
                    + busType;
            maps.put(key, bus);
        }
        for (String key : maps.keySet()) {
            busList.add(maps.get(key));
        }
        return busList;
    }


    /**
     * 获取有效请假用户集合
     *
     * @param bussList 出差流程记录
     * @return
     */
    private List getLeaveUsers(List<SysAttendBusiness> bussList) {
        Set<String> userSet = new HashSet<String>();
        for (SysAttendBusiness buss : bussList) {
            List<SysOrgElement> targets = buss.getFdTargets();
            if (targets == null || targets.isEmpty()) {
                continue;
            }
            for (SysOrgElement org : targets) {
                userSet.add(org.getFdId());
            }
        }
        List<String> orgList = new ArrayList<String>(userSet);
        return orgList;
    }

    protected void sendNotify(SysTimeLeaveDetail leaveDetail) throws Exception {
        if (leaveDetail == null) {
            logger.warn("发送请假数据失败通知：请假明细不存在1");
            return;
        }
        logger.warn("发送请假数据失败通知：请假人：" + leaveDetail.getFdPerson().getFdId()
                + "，请假明细：" + leaveDetail.getFdId() + "，请假失败原因："
                + leaveDetail.getFdOprDesc());
        NotifyContext notifyContext = getSysNotifyMainCoreService()
                .getContext(null);
        notifyContext.setNotifyType("todo");
        notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
        notifyContext.setKey("sysTimeLeaveDetailHandel");
        List<SysOrgElement> list = new ArrayList<SysOrgElement>();
        list.add(leaveDetail.getFdPerson());
        notifyContext.setNotifyTarget(list);
        notifyContext.setSubject(ResourceUtil
                .getString("sysAttendMain.leave.notify", "sys-attend"));
        notifyContext.setContent(ResourceUtil
                .getString("sysAttendMain.leave.notify", "sys-attend"));
        notifyContext.setLink("/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=view&fdId=" + leaveDetail.getFdId());
        getSysNotifyMainCoreService().sendNotify(leaveDetail, notifyContext, null);
    }

    /**
     * 根据请假单中的类型、参数等进行最终开始结束时间的转换
     * @param fdStatType 假期类型
     * @param fdBusStartTime 流程开始时间
     * @param fdBusEndTime 结束时间
     * @param startNoon 开始类型【上下午】
     * @param endNoon 结束类型【上下午】
     * @description: 根据请假单中的类型、参数等进行最终开始结束时间的转换
     * @author: 王京
     * @time: 2022/07/11 10:09 下午
     * @return 转换后的开始结束时间
     */
    private AttendComparableTime transformTime(
            Integer fdStatType,
            Date fdBusStartTime,
            Date fdBusEndTime,
            Integer startNoon,
            Integer endNoon ) {
        Date tempStartTime =fdBusStartTime;
        Date tempEndTime =fdBusEndTime;
        //请假类型1：按天，2：按半天，3，按小时
        if (AttendConstant.FD_STAT_TYPE[1].equals(fdStatType)) {
            //开始时间需要设置成yyyy-MM-dd 00:00:00
            tempStartTime = AttendUtil.getDate(fdBusStartTime, 0);
            //结束时间需要设置成yyyy-MM-dd 23:59:59
            tempEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
        } else if (AttendConstant.FD_STAT_TYPE[2].equals(fdStatType)) {
            //开始是上午
            boolean startIsMorning =AttendConstant.FD_NOON_TYPE[1].equals(startNoon);
            //开始是下午
            boolean startIsAfternoon =AttendConstant.FD_NOON_TYPE[2].equals(startNoon);
            //结束是上午
            boolean endIsMorning =AttendConstant.FD_NOON_TYPE[1].equals(endNoon);
            //结束是下午
            boolean endIsAfternoon =AttendConstant.FD_NOON_TYPE[2].equals(endNoon);

            //如果是上午半天则需要把上午的时间设置成yyyy-MM-dd 00:00:00 开始日期上下午，1：上午，2：下午
            if(startIsMorning && endIsAfternoon){
                //说明是全天,并且是从上午开始下午结束
                tempStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                tempEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
            }else if( startIsAfternoon && endIsMorning){
                //说明是全天,并且是从下午开始上午结束
                tempStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                tempEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
            }else {
                if (startIsMorning) {
                    tempStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                } else if (startIsAfternoon) {
                    //如果是上午半天则需要把上午的时间设置成yyyy-MM-dd 12:00:00 开始日期上下午
                    tempStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                }
                if (endIsMorning) {
                    tempEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
                } else if (endIsAfternoon) {
                    tempEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
                }
            }
        }
        return new AttendComparableTime(tempStartTime,tempEndTime);
    }
    /**
     * @param detail
     * @description: 做开始时间和结束时间的转换，不改变原来的值，注意：返回值只有startTime和endTime
     * @return: com.landray.kmss.sys.attend.model.SysAttendBusiness
     * @author: wangjf
     * @time: 2022/3/3 10:09 下午
     */
    private SysTimeLeaveDetail transformTime(SysTimeLeaveDetail detail) {
        Date fdBusStartTime = detail.getFdStartTime();
        Date fdBusEndTime = detail.getFdEndTime();
        //请假类型1：按天，2：按半天，3，按小时
        if (1 == detail.getFdStatType()) {
            //开始时间需要设置成yyyy-MM-dd 00:00:00
            fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
            //结束时间需要设置成yyyy-MM-dd 23:59:59
            fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
        } else if (2 == detail.getFdStatType()) {
            //如果是上午半天则需要把上午的时间设置成yyyy-MM-dd 00:00:00 开始日期上下午，1：上午，2：下午
            if(detail.getFdStartNoon() != null && 1 == detail.getFdStartNoon() && detail.getFdEndNoon() != null && 2 == detail.getFdEndNoon()){
                //说明是全天,并且是从上午开始下午结束
                fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
            }else if(detail.getFdStartNoon() != null && 2 == detail.getFdStartNoon() && detail.getFdEndNoon() != null && 1 == detail.getFdEndNoon()){
                //说明是全天,并且是从下午开始上午结束
                fdBusStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                fdBusEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
            }else {
                if (detail.getFdStartNoon() != null && 1 == detail.getFdStartNoon()) {
                    fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
                } else if (detail.getFdStartNoon() != null && 2 == detail.getFdStartNoon()) {
                    //如果是上午半天则需要把上午的时间设置成yyyy-MM-dd 12:00:00 开始日期上下午
                    fdBusStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
                }
                if (detail.getFdEndNoon() != null && 1 == detail.getFdEndNoon()) {
                    fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
                } else if (detail.getFdEndNoon() != null && 2 == detail.getFdEndNoon()) {
                    fdBusStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
                }
            }
        }
        //不能直接修改bus中的值，因为该值属于引用类型会导致list中的原始值被修改掉
        SysTimeLeaveDetail temp = new SysTimeLeaveDetail();
        temp.setFdStartTime(fdBusStartTime);
        temp.setFdEndTime(fdBusEndTime);
        return temp;
    }

    /**
     *
     * @description: 时间转换成消息
      * @param detail
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2022/3/11 8:15 下午
     */
    private String transformTimeMsg(SysTimeLeaveDetail detail) {
        String resultMsg = "";
        String datePattern="yyyy-MM-dd";
        Date fdBusStartTime = detail.getFdStartTime();
        Date fdBusEndTime = detail.getFdEndTime();
        //请假类型1：按天，2：按半天，3，按小时
        if (1 == detail.getFdStatType()) {
            //开始时间需要设置成yyyy-MM-dd 00:00:00
            fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
            //结束时间需要设置成yyyy-MM-dd 23:59:59
            fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
            resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日";
        } else if (2 == detail.getFdStatType()) {
            //如果是上午半天则需要把上午的时间设置成yyyy-MM-dd 00:00:00 开始日期上下午，1：上午，2：下午
            if(detail.getFdStartNoon() != null && 1 == detail.getFdStartNoon() && detail.getFdEndNoon() != null && 2 == detail.getFdEndNoon()){
                //说明是全天,并且是从上午开始下午结束
                fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
                resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日上午-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日下午";
            }else if(detail.getFdStartNoon() != null && 2 == detail.getFdStartNoon() && detail.getFdEndNoon() != null && 1 == detail.getFdEndNoon()){
                //说明是全天,并且是从下午开始上午结束
                fdBusStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                fdBusEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
                resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日下午-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日上午";
            }else {
                if (detail.getFdStartNoon() != null && 1 == detail.getFdStartNoon()) {
                    fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
                    resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日上午-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日上午";
                } else if (detail.getFdStartNoon() != null && 2 == detail.getFdStartNoon()) {
                    //如果是上午半天则需要把上午的时间设置成yyyy-MM-dd 12:00:00 开始日期上下午
                    fdBusStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
                    resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日下午-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日下午";
                }
                if (detail.getFdEndNoon() != null && 1 == detail.getFdEndNoon()) {
                    fdBusStartTime = AttendUtil.getDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getMiddleDate(fdBusEndTime, 0);
                    resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日上午-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日上午";
                } else if (detail.getFdEndNoon() != null && 2 == detail.getFdEndNoon()) {
                    fdBusStartTime = AttendUtil.getMiddleDate(fdBusStartTime, 0);
                    fdBusEndTime = AttendUtil.getEndDate(fdBusEndTime, 0);
                    resultMsg = DateUtil.convertDateToString(fdBusStartTime, datePattern)+"日下午-"+DateUtil.convertDateToString(fdBusEndTime, datePattern)+"日下午";
                }
            }
        }else{
            resultMsg = DateUtil.convertDateToString(fdBusStartTime, "yyyy-MM-dd HH:mm")+"-"+DateUtil.convertDateToString(fdBusEndTime, "yyyy-MM-dd HH:mm");
        }

        return resultMsg;
    }

    /**
     * 验证流程数据是否重复
     *
     * @param bus
     * @description:
     * @return: String
     * @author: wangjf
     * @time: 2022/2/21 6:20 下午
     */
    protected String checkAttendLeaveRepeat(SysAttendBusiness bus) throws Exception {
        //当前流程的所有用户获取
        List<SysOrgElement> targets = getTargetsFromAttendBusiness(bus);
        //temp只可使用startTime和endTime，其他值都为空
        SysTimeLeaveDetail sysTimeLeaveDetail = sysAttendBusinessTransformToSysTimeLeaveDetail(bus);

        AttendComparableTime tempLeaveData = transformTime(sysTimeLeaveDetail.getFdStatType(),sysTimeLeaveDetail.getFdStartTime(),sysTimeLeaveDetail.getFdEndTime(),sysTimeLeaveDetail.getFdStartNoon(),sysTimeLeaveDetail.getFdEndNoon());
        Date fdBusStartTime = tempLeaveData.getStartDate();
        Date fdBusEndTime =tempLeaveData.getEndDate();
        List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(targets);
        //查询数据的时候可以扩大范围进行查询
        Date startTime = AttendUtil.getDate(fdBusStartTime,-1);
        Date endTime = AttendUtil.getEndDate(fdBusEndTime,1);
        String isRepeat = "";
        for (SysOrgPerson person : personList) {
            //获取请假记录明细,leaveType = null  代表查询出全部的请假明细
            List<SysTimeLeaveDetail> busList = getSysTimeLeaveDetailService().findLeaveDetail(person.getFdId(), startTime, endTime, null);
            if(CollectionUtils.isEmpty(busList)){
                continue;
            }

            for (SysTimeLeaveDetail timeLeaveDetail : busList) {
                AttendComparableTime tempTimeLeaveData = transformTime(timeLeaveDetail.getFdStatType(),timeLeaveDetail.getFdStartTime(),timeLeaveDetail.getFdEndTime(),timeLeaveDetail.getFdStartNoon(),timeLeaveDetail.getFdEndNoon());
                //如果当前单有重叠 再去验证其下面是否有销假
                if(checkRepeatUserBusiness(tempTimeLeaveData.getStartDate(),tempTimeLeaveData.getEndDate(), fdBusStartTime, fdBusEndTime)){
                    //再次验证销假时间
                    List<SysTimeLeaveResume> resumeList = getSysTimeLeaveResumeService().findResumeList(person.getFdId(),timeLeaveDetail.getFdId());
                    if(CollectionUtils.isNotEmpty(resumeList)) {
                        List<AttendComparableTime> diffList = Lists.newArrayList();
                        for (SysTimeLeaveResume resume : resumeList) {
                            AttendComparableTime tempResumeData = transformTime(timeLeaveDetail.getFdStatType(), resume.getFdStartTime(), resume.getFdEndTime(), resume.getFdStartNoon(), resume.getFdEndNoon());
                            diffList.add(tempResumeData);
                        }
                        List<AttendComparableTime> resultList = AttendOverTimeUtil.differences(tempTimeLeaveData, diffList);
                        for (AttendComparableTime checkInfo:resultList  ) {
                            if (checkRepeatUserBusiness(checkInfo.getStartDate(), checkInfo.getEndDate(), fdBusStartTime, fdBusEndTime)) {
                                isRepeat = (StringUtil.isNotNull(timeLeaveDetail.getFdReviewName()) ? timeLeaveDetail.getFdReviewName() : "") + "(" + transformTimeMsg(timeLeaveDetail) + ")";
                                return isRepeat;
                            }
                        }
                    } else {
                        isRepeat = (StringUtil.isNotNull(timeLeaveDetail.getFdReviewName()) ? timeLeaveDetail.getFdReviewName() : "") + "(" + transformTimeMsg(timeLeaveDetail) + ")";
                        return isRepeat;
                    }
                }
            }
        }

        return isRepeat;
    }

    /**
     * 检查出差流程时间重复
     *
     * @param startTime 出差开始时间
     * @param endTime   出差结束时间
     * @return
     * @throws Exception
     */
    private boolean checkRepeatUserBusiness(Date fdBusStartTime, Date fdBusEndTime, Date startTime, Date endTime) {
        // 实际时间区间
        if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {
            return true;
        } else if (fdBusEndTime.getTime() > startTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {
            return true;
        } else if (fdBusEndTime.getTime() <= endTime.getTime() && fdBusStartTime.getTime() >= startTime.getTime()) {
            return true;
        } else if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < endTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 获取指定用户的请假流程,并过滤重复
     *
     * @param busList
     * @param person
     * @description:
     * @return: java.util.List
     * @author: wangjf
     * @time: 2022/2/21 6:17 下午
     */
    private List getUserLeaveList(List<SysAttendBusiness> busList,
                                  SysOrgElement person) {
        List<SysAttendBusiness> userBusList = new ArrayList<SysAttendBusiness>();
        if (busList == null || busList.isEmpty()) {
            return userBusList;
        }

        for (SysAttendBusiness bus : busList) {
            List<SysOrgElement> targets = bus.getFdTargets();
            if (targets.contains(person)) {
                userBusList.add(bus);
            }
        }
        // 过滤重复数据
        Map<String, SysAttendBusiness> maps = new HashMap<String, SysAttendBusiness>();
        for (SysAttendBusiness bus : userBusList) {
            Date startTime = bus.getFdBusStartTime();
            Date endTime = bus.getFdBusEndTime();
            // 根据时间参数判断是否重复
            String key = startTime.getTime() + "_" + endTime.getTime();
            maps.put(key, bus);
        }
        List<SysAttendBusiness> newUserBusList = new ArrayList<SysAttendBusiness>();
        for (String key : maps.keySet()) {
            newUserBusList.add(maps.get(key));
        }
        return newUserBusList;
    }

    /**
     * sysAttendBusiness 转换成 sysTimeLeaveDetail
     *
     * @param sysAttendBusiness
     * @description:
     * @return: com.landray.kmss.sys.time.model.SysTimeLeaveDetail
     * @author: wangjf
     * @time: 2022/2/22 10:35 上午
     */
    protected SysTimeLeaveDetail sysAttendBusinessTransformToSysTimeLeaveDetail(SysAttendBusiness sysAttendBusiness) throws Exception {

        SysTimeLeaveDetail leaveDetail = new SysTimeLeaveDetail();
        leaveDetail.setFdId(IDGenerator.generateID());
        List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(sysAttendBusiness.getFdTargets());
        SysOrgPerson person = personList.get(0);
        leaveDetail.setFdLeaveName(sysAttendBusiness.getFdLeaveName());
        SysTimeLeaveRule leaveRule = AttendUtil.getLeaveRuleByType(sysAttendBusiness.getFdBusType());
        String fdLeaveType = "";
        if (leaveRule != null) {
            fdLeaveType = leaveRule.getFdSerialNo();
        }
        leaveDetail.setFdLeaveType(fdLeaveType);
        leaveDetail.setFdPerson(person);
        leaveDetail.setFdStartTime(sysAttendBusiness.getFdBusStartTime());
        leaveDetail.setFdEndTime(sysAttendBusiness.getFdBusEndTime());
        SysTimeLeaveTimeDto dto =getLeaveTimes(person, Integer.valueOf(fdLeaveType), sysAttendBusiness.getFdBusStartTime(),
                sysAttendBusiness.getFdBusEndTime(), sysAttendBusiness.getFdStatType(), sysAttendBusiness.getFdStartNoon(), sysAttendBusiness.getFdEndNoon());
        leaveDetail.setFdLeaveTime(Float.parseFloat(String.format("%.2f",dto.getLeaveTimeDays())));
        leaveDetail.setFdTotalTime(dto.getLeaveTimeMins().floatValue());
        leaveDetail.setFdOprType(1);
        leaveDetail.setFdOprStatus(0);
        leaveDetail.setFdOprDesc(null);
        leaveDetail.setFdReviewId(sysAttendBusiness.getFdProcessId());
        leaveDetail.setFdReviewName(sysAttendBusiness.getFdProcessName());
        Integer startNoon = sysAttendBusiness.getFdStartNoon();
        Integer endNoon = sysAttendBusiness.getFdEndNoon();
        if (sysAttendBusiness.getFdStatType().equals(2)) {
            //兼容如果不传上下午标志给默认值
            startNoon = (sysAttendBusiness.getFdStartNoon() == null ? 1 : sysAttendBusiness.getFdStartNoon());
            endNoon = (sysAttendBusiness.getFdEndNoon() == null ? 2 : sysAttendBusiness.getFdEndNoon());
        }
        leaveDetail.setFdStatType(sysAttendBusiness.getFdStatType());
        leaveDetail.setFdStartNoon(startNoon);
        leaveDetail.setFdEndNoon(endNoon);
        leaveDetail.setDocCreateTime(new Date());
        leaveDetail.setDocCreator(UserUtil.getUser());
        leaveDetail.setFdIsUpdateAttend(true);
        // 设置场所
        leaveDetail.setAuthArea(sysAttendBusiness.getAuthArea());
        return leaveDetail;

    }


    /**
     * 获取请假人员，包括随从，做了去重处理
     *
     * @param bus
     * @return
     */
    protected List<SysOrgElement> getTargetsFromAttendBusiness(SysAttendBusiness bus) {
        Map<String, SysOrgElement> sysOrgElementMap = new HashMap<String, SysOrgElement>();
        for (SysOrgElement sysOrgElement : bus.getFdTargets()) {
            sysOrgElementMap.put(sysOrgElement.getFdId(), sysOrgElement);
        }
        return new ArrayList<SysOrgElement>(sysOrgElementMap.values());
    }

    private class MulitiUpdate extends Thread {
        List<SysAttendBusiness> busList;
        String docCreatorId;
        Date date;
        Set<Date> dateSet;
        CountDownLatch latch;

        public MulitiUpdate(List<SysAttendBusiness> busList,
                            String docCreatorId,
                            Date date, Set<Date> dateSet,
                            CountDownLatch latch) {
            this.busList = busList;
            this.docCreatorId = docCreatorId;
            this.date = date;
            this.dateSet = dateSet;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                multiUpdate(date, dateSet, busList, docCreatorId);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    public class RegenAttendThread implements Runnable {
        // 用户集合
        List<SysOrgElement> areaMembers = new ArrayList<SysOrgElement>();
        // 操作类型
        private String fdMethod = null;
        private String fdCategoryId = null;
        private String fdTimeAreaChange = null;

        @Override
        public void run() {
            if ("regenUserLeave".equals(fdMethod)) {
                boolean isExecotion = false;
                logger.debug("regenattendThread restat start...");
                TransactionStatus status = null;
                try {
                    status = TransactionUtils.beginNewTransaction();
                    regenUserAttendMain(fdCategoryId, fdTimeAreaChange,
                            areaMembers);
                    TransactionUtils.getTransactionManager().commit(status);
                    logger.debug("regenattendThread restat finish...");
                } catch (Exception e) {
                    isExecotion = true;
                    e.printStackTrace();
                    logger.error("重新统计用户请假记录,考勤组：fdCategoryId：" + fdCategoryId + ";", e);
                } finally {
                    if (isExecotion && status != null) {
                        TransactionUtils.getTransactionManager()
                                .rollback(status);
                    }
                }
            }
        }

        public List<SysOrgElement> getAreaMembers() {
            return areaMembers;
        }

        public void setAreaMembers(List<SysOrgElement> areaMembers) {
            this.areaMembers = areaMembers;
        }

        public String getFdCategoryId() {
            return fdCategoryId;
        }

        public void setFdCategoryId(String fdCategoryId) {
            this.fdCategoryId = fdCategoryId;
        }

        public String getFdMethod() {
            return fdMethod;
        }

        public void setFdMethod(String fdMethod) {
            this.fdMethod = fdMethod;
        }

        public String getFdTimeAreaChange() {
            return fdTimeAreaChange;
        }

        public void setFdTimeAreaChange(String fdTimeAreaChange) {
            this.fdTimeAreaChange = fdTimeAreaChange;
        }

    }


    public ISysTimeLeaveResumeService getSysTimeLeaveResumeService() {
        if (sysTimeLeaveResumeService == null) {
            sysTimeLeaveResumeService = (ISysTimeLeaveResumeService) SpringBeanUtil.getBean("sysTimeLeaveResumeService");
        }
        return sysTimeLeaveResumeService;
    }

    public void setSysTimeLeaveDetailService(

            ISysTimeLeaveDetailService sysTimeLeaveDetailService) {
        this.sysTimeLeaveDetailService = sysTimeLeaveDetailService;
    }

    public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
        if (sysTimeLeaveDetailService == null) {
            sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil.getBean("sysTimeLeaveDetailService");
        }
        return sysTimeLeaveDetailService;
    }
}
