package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attend.model.SysAttendReportLog;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendReportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendReportService;
import com.landray.kmss.sys.attend.service.ISysAttendStatDetailService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录导出记录表
 *@author 王京
 *@date 2021-10-13
 */
public class SysAttendReportLogServiceImp extends ExtendDataServiceImp implements ISysAttendReportLogService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendReportLogServiceImp.class);
    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    protected ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if(sysNotifyMainCoreService ==null){
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
                    .getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    protected IBackgroundAuthService backgroundAuthService;

    public IBackgroundAuthService getBackgroundAuthService() {
        if (backgroundAuthService == null) {
            backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil.getBean(
                    "backgroundAuthService");
        }
        return backgroundAuthService;
    }
    private Integer addNumber=1;

    /**
     * 异步下载任务
     */
    class SyncExportTaskThread implements Runnable{
        SysAttendReportLog sysAttendReportLog;
        int taskType;
        HQLInfo hqlInfo;
        RequestContext context;
        String fileName;
        String fdUserId;

        public SyncExportTaskThread(SysAttendReportLog sysAttendReportLog, int taskType, HQLInfo hqlInfo, RequestContext context, String fileName, String fdUserId) {
            this.sysAttendReportLog = sysAttendReportLog;
            this.taskType = taskType;
            this.hqlInfo = hqlInfo;
            this.context = context;
            this.fileName = fileName;
            this.fdUserId = fdUserId;
        }

        @Override
        public void run() {
            boolean updateStatus =false;
            try {
                getBackgroundAuthService().switchUserById(fdUserId, new Runner() {
                    @Override
                    public Object run(Object parameter) throws Exception {
                        syncExportTask(sysAttendReportLog,taskType,hqlInfo,context,fileName);
                        return null;
                    }
                },null);
            } catch (Exception e) {
                updateStatus =true;
                e.printStackTrace();
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-执行线程异常=="+e.getMessage());
                }
            }
            if(updateStatus){
                TransactionStatus transactionStatus2 = TransactionUtils.beginNewTransaction();
                try {
                    sysAttendReportLog.setFdDesc("切换用户-执行线程异常");
                    sysAttendReportLog.setFdStatus(2);
                    getSysAttendReportLogService().update(sysAttendReportLog);
                    TransactionUtils.commit(transactionStatus2);
                } catch (Exception e) {
                    TransactionUtils.rollback(transactionStatus2);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 每日汇总的导出
     * @param hqlInfo
     * @param request
     */
    @Override
    public void addSyncStatDetailDownReport(HQLInfo hqlInfo, HttpServletRequest request) throws Exception {

        //记录导出记录
        String filename = ResourceUtil.getString("table.sysAttendStatDetail", "sys-attend");
        RequestContext context =new RequestContext(request);
        // 开始时间
        String fdStartTime = context.getParameter("fdStartTime");
        // 结束时间
        String fdEndTime = context.getParameter("fdEndTime");
        String fdType= String.valueOf(1);

        Integer no =getUserDayDownNumber(fdType);
        String fileName  =String.format("%s%s%s%s(%s).xls",fdStartTime,ResourceUtil.getString("sysAttendReport.fdName.to","sys-attend"),fdEndTime,filename, no+addNumber);
        SysAttendReportLog sysAttendReportLog=new SysAttendReportLog();
        sysAttendReportLog.setFdNo(String.valueOf(no));
        //文件名称
        sysAttendReportLog.setFdName(fileName);
        sysAttendReportLog.setFdType(fdType);
        sysAttendReportLog.setFdStatus(0);
        super.add(sysAttendReportLog);
        
        SyncExportTaskThread task =new SyncExportTaskThread(sysAttendReportLog,1,hqlInfo,context,fileName,UserUtil.getKMSSUser().getUserId());
        AttendThreadPoolManager manager = AttendThreadPoolManager
                .getInstance();
        if (!manager.isStarted()) {
            manager.start();
        }
        manager.submit(task);

    }
    /**
     * 每月汇总的导出
     * @param request
     */
    @Override
    public void addSyncStatMonthDownReport(HQLInfo hqlInfo,HttpServletRequest request) throws Exception {
        //记录导出记录
        String filename = ResourceUtil.getString("table.sysAttendReport", "sys-attend");
        RequestContext content = new RequestContext(request);
        String fdName = content.getParameter("fdName");
        if (StringUtil.isNotNull(fdName)) {
            filename = fdName;
        }
        String fdType= String.valueOf(2);
        Integer no =getUserDayDownNumber(fdType);
        String fileName  =String.format("%s(%s).xls",filename, no+addNumber);
        SysAttendReportLog sysAttendReportLog=new SysAttendReportLog();
        sysAttendReportLog.setFdNo(String.valueOf(no));
        //文件名称
        sysAttendReportLog.setFdName(fileName);
        sysAttendReportLog.setFdType(fdType);
        sysAttendReportLog.setFdStatus(0);
        super.add(sysAttendReportLog);

        SyncExportTaskThread task =new SyncExportTaskThread(sysAttendReportLog,2,hqlInfo,content,fileName,UserUtil.getKMSSUser().getUserId());
        AttendThreadPoolManager manager = AttendThreadPoolManager
                .getInstance();
        if (!manager.isStarted()) {
            manager.start();
        }
        manager.submit(task);

    }
    /**
     * 每月汇总的导出 按照时间区间
     * @param request
     */
    @Override
    public void addSynceMonthPeriodDownReport(HQLInfo hqlInfo,HttpServletRequest request) throws Exception {
        //记录导出记录
        String filename = ResourceUtil.getString("table.sysAttendReport", "sys-attend");
        RequestContext content = new RequestContext(request);
        String fdName = content.getParameter("fdName");
        if (StringUtil.isNotNull(fdName)) {
            filename = fdName;
        }
        String fdType= String.valueOf(2);
        Integer no =getUserDayDownNumber(fdType);
        String fileName  =String.format("%s(%s).xls",filename, no+addNumber);
        SysAttendReportLog sysAttendReportLog=new SysAttendReportLog();
        sysAttendReportLog.setFdNo(String.valueOf(no));
        //文件名称
        sysAttendReportLog.setFdName(fileName);
        sysAttendReportLog.setFdType(fdType);
        sysAttendReportLog.setFdStatus(0);
        super.add(sysAttendReportLog);
        SyncExportTaskThread task =new SyncExportTaskThread(sysAttendReportLog,3,hqlInfo,content,fileName,UserUtil.getKMSSUser().getUserId());
        AttendThreadPoolManager manager = AttendThreadPoolManager
                .getInstance();
        if (!manager.isStarted()) {
            manager.start();
        }
        manager.submit(task);
    }

    /**
     * 每月汇总的导出
     * @param request
     */
    @Override
    public void addSyncReportDown(HQLInfo hqlInfo,HttpServletRequest request) throws Exception {
        //记录导出记录
        String filename = ResourceUtil.getString("table.sysAttendReport", "sys-attend");
        RequestContext content = new RequestContext(request);
        String fdName = content.getParameter("fdName");
        if (StringUtil.isNotNull(fdName)) {
            filename = fdName;
        }
        String fdType =content.getParameter("fdId");
        Integer no =getUserDayDownNumber(fdType);
        String fileName  =String.format("%s(%s).xls",filename, no+addNumber);
        SysAttendReportLog sysAttendReportLog=new SysAttendReportLog();
        sysAttendReportLog.setFdNo(String.valueOf(no));
        //文件名称
        sysAttendReportLog.setFdName(fileName);
        sysAttendReportLog.setFdType(fdType);
        sysAttendReportLog.setFdStatus(0);
        super.add(sysAttendReportLog);
        SyncExportTaskThread task =new SyncExportTaskThread(sysAttendReportLog,2,hqlInfo,content,fileName,UserUtil.getKMSSUser().getUserId());
        AttendThreadPoolManager manager = AttendThreadPoolManager
                .getInstance();
        if (!manager.isStarted()) {
            manager.start();
        }
        manager.submit(task);
    }

    /**
     * 获取单个用户今日导出次数
     * @return
     */
    private Integer getUserDayDownNumber(String fdType) throws Exception {
        String hql=" select count(fd_id) from sys_attend_report_log sysAttendReportLog where sysAttendReportLog.doc_creator_Id=:userId and sysAttendReportLog.fd_type=:fdType ";
        Object objResult = this.getBaseDao().getHibernateSession().createSQLQuery(hql).setParameter("userId",UserUtil.getUser().getFdId()) .setParameter("fdType",fdType).uniqueResult();
        if(objResult !=null){
            return Integer.valueOf(objResult.toString());
        }
        return 0;
    }

    /**
     * 获取单个用户今日导出次数
     * @param type 1是每日汇总，2是每月汇总
     * @return
     */
    @Override
    public Integer getExportNumber(String type) throws Exception {
        String hql=" select count(fd_id) from sys_attend_report_log sysAttendReportLog where sysAttendReportLog.doc_creator_Id=:userId and sysAttendReportLog.fd_type=:fdType and sysAttendReportLog.fd_status=:fdStatus ";
        Object objResult = this.getBaseDao().getHibernateSession().createSQLQuery(hql)
                .setParameter("userId",UserUtil.getUser().getFdId())
                .setParameter("fdType",type)
                .setParameter("fdStatus",0)
                .uniqueResult();
        if(objResult !=null){
            return Integer.valueOf(objResult.toString());
        }
        return 0;
    }


    /**
     * 异步生成EXCEL附件
     * @param sysAttendReportLog
     * @param taskType
     * @param hqlInfo
     * @param request
     * @param fileName
     */
    private void syncExportTask( SysAttendReportLog sysAttendReportLog,int taskType,HQLInfo hqlInfo,RequestContext request,String fileName){
        if (sysAttendReportLog == null) {
            return;
        }
        String fileKey="downLoadExcel";
        int status = 0;
        String msg = null;
        String fileId = null;
        HSSFWorkbook workbook = null;
        boolean isExecption =false;
        String title = String.format("您的报表《%s》已生成，可点击下载。",sysAttendReportLog.getFdName());
        TransactionStatus transactionStatus = TransactionUtils.beginNewTransaction();
        try {
            if(logger.isDebugEnabled()){
                logger.debug("导出考勤Excel-开始执行导出任务！");
            }

            if (taskType == 1) {

                List<SysAttendStat> detailsList= getSysAttendStatService().findList(hqlInfo);
                for(int i=0;i<detailsList.size();i++){
//                String sql1 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getDocCreateTime()).split(" ")[0]+"'"
//						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"'";
                String startTime = null;
				String endTime = null;
				String whereBlock="sysAttendHisCategory.";
				String sql1 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1 and doc_create_time="
								+ "(select max(doc_create_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1)"
								+ "union select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=0";
				String sql11 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(detailsList.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1";
				String sql111 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0";
				String sql1115 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1 and doc_create_time=(select max(doc_create_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0 and fd_work_type=1) "
								+ " union select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=0  and fd_work_type=0 union "
								+ "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(detailsList.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1 and fd_work_type=0 union"
						+" select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(detailsList.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1 and fd_work_type=1 and doc_create_time=(select max(doc_create_time) from sys_attend_main where substring(fd_base_work_time,1,10) ='"+DateUtil.convertDateToString(AttendUtil.getDate(detailsList.get(i).getFdDate(), 1), "yyyy-MM-dd HH:mm:ss").split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_is_across=1 and fd_work_type=1)";
				String sql2 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_work_type=0";
				String sql3 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(""+detailsList.get(i).getFdDate()).split(" ")[0]+"'"
						+ "and doc_creator_id='"+detailsList.get(i).getDocCreator().getFdId()+"' and doc_Status=0 and fd_work_type=1";
				List list1 = HrCurrencyParams.getListBySql(sql1);
//				System.out.println(sql1);
//				int ddd;d
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
				detailsList.get(i).setStartTime(startTime.split(":")[0]+":"+startTime.split(":")[1]);
				detailsList.get(i).setEndTime(endTime.split(":")[0]+":"+endTime.split(":")[1]);
				}catch(Exception e){
					e.printStackTrace();
				}
            }
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-每日汇总导出！导出数据条数=="+detailsList.size());
                }
                // 1) 得到Excel数据
                Map<String, Object> resultMap = getSysAttendStatDetailService().formatStatDetail(detailsList);
                List list = (List) resultMap.get("list");
                Map<String, List<List<JSONObject>>> worksMap = (Map<String, List<List<JSONObject>>>) resultMap.get("worksMap");

                int maxWorkTimeCount = AttendUtil.getWorkTimeCount(worksMap);
                long beginTime =System.currentTimeMillis();
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-每日汇总导出！开始封装Excel！开始时间："+beginTime);
                }
                // 2）获取Excel
                workbook = getSysAttendStatDetailService().buildWorkBook(list, maxWorkTimeCount, worksMap);
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-每日汇总导出！封装Excel结束！结束，耗时："+ ( System.currentTimeMillis()-beginTime));
                }
            } else if (taskType == 2) {

                long beginTime =System.currentTimeMillis();
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-每月汇总导出！开始封装Excel！开始时间："+beginTime);
                }
                //每月导出
                workbook = getSysAttendReportService().exportExcel(hqlInfo, request);
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-每月汇总导出！封装Excel结束！结束，耗时："+ ( System.currentTimeMillis()-beginTime));
                }
            } else if (taskType == 3) {

                long beginTime =System.currentTimeMillis();
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-考勤报表导出！开始封装Excel！开始时间："+beginTime);
                }
                //每月导出 按照时间区间
                workbook = getSysAttendReportService().exportPeriod(hqlInfo, request);
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-考勤报表导出！封装Excel结束！结束，耗时："+ ( System.currentTimeMillis()-beginTime));
                }
            }
            if(logger.isDebugEnabled()){
                logger.debug("导出考勤Excel-封装结束，开始写入本地文件！");
            }
            // 3) 把Excel生成本地文件
            String filePath = createTempFile(workbook);
            if (StringUtil.isNotNull(filePath)) {
                File file = new File(filePath);
                // 4) 把Excel 上传到附件机制
                SysAttMain attMain = new SysAttMain();
                attMain.setInputStream(new FileInputStream(file));
                attMain.setFdModelId(sysAttendReportLog.getFdId());
                attMain.setFdModelName(SysAttendReportLog.class.getName());
                attMain.setFdKey(fileKey);
                attMain.setFdFileName(fileName);
                attMain.setFdUploadTime(new Date());
                attMain.setFdSize(Double.valueOf(file.length()));
                attMain.setFdContentType("application/vnd.ms-excel");
                fileId = getSysAttMainService().add(attMain);
                //上传完以后。删除本地附件
                file.delete();
                // 5) 修改下载实体的状态
                status = 1;
                if(logger.isDebugEnabled()){
                    logger.debug("导出考勤Excel-上传导出的附件完成！");
                }
            } else {
                status = 2;
                msg = "文件生成错误！";
            }

        } catch (Exception e) {
            e.printStackTrace();
            status = 2;
            msg = "系统执行异常！";
            isExecption =true;
        } finally {
            if(logger.isDebugEnabled()){
                logger.debug("导出考勤Excel-结束，进行修改状态！");
            }
            if(isExecption){
                TransactionUtils.rollback(transactionStatus);
            }else{
                TransactionUtils.commit(transactionStatus);
            }
            //对状态的保存启用新的事务处理
            transactionStatus = TransactionUtils.beginNewTransaction();
            try {
                //理论上来说下面不会有异常信息。
                if (workbook != null) {
                    workbook.close();
                }
                sysAttendReportLog.setFdFileName(fileId);
                sysAttendReportLog.setFdDesc(msg);
                sysAttendReportLog.setFdStatus(status);
                getSysAttendReportLogService().update(sysAttendReportLog);
                //发送待办结果给创建人
                if(status ==1){

                    String url=String.format("/sys/attend/sys_attend_report_log/sysAttendReportLog.do?method=downloadExcel&fdId=%s",sysAttendReportLog.getFdId());;
                    sendNotify(title,title,url,sysAttendReportLog);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e) {
                e.printStackTrace();
                isExecption =false;
            } finally {
                if(isExecption && transactionStatus !=null){
                    TransactionUtils.rollback(transactionStatus);
                }
            }
        }
    }

    /**
     * 发送下载待办
     * @param title
     * @param content
     * @param url
     * @param sysAttendReportLog
     * @throws Exception
     */
    private void sendNotify(String title,String content,String url,SysAttendReportLog sysAttendReportLog) throws Exception {
        NotifyContext notifyContext = getSysNotifyMainCoreService().getContext(null);
        notifyContext.setNotifyType("todo");
        notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
        notifyContext.setKey("sysAttendReportHandel");
        List<SysOrgElement> list = new ArrayList<SysOrgElement>();
        list.add(sysAttendReportLog.getDocCreator());
        notifyContext.setNotifyTarget(list);
        notifyContext.setSubject(title);
        notifyContext.setContent(content);
        notifyContext.setLink(url);
        getSysNotifyMainCoreService().sendNotify(sysAttendReportLog, notifyContext,null);
    }

    /**
     * Excel生成本地文件
     * @param workbook
     * @return
     */
    private String createTempFile(HSSFWorkbook workbook) throws Exception {
        String fdSystemTempPath = FileUtil.getSystemTempPath();
        if (!fdSystemTempPath.endsWith("/")) {
            fdSystemTempPath = fdSystemTempPath + "/";
        }
        String tmpId = IDGenerator.generateID();
        String filePath =String.format("%ssysAttendTmp/%s.xls",fdSystemTempPath,tmpId);
        File tmpFile = new File(filePath);
        if(!tmpFile.getParentFile().exists()){
            tmpFile.getParentFile().mkdirs();
        }
        FileOutputStream out =null;
        try {
            out = new FileOutputStream(tmpFile);
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            if(logger.isDebugEnabled()){
                logger.debug("导出考勤Excel-生成导出文件错误！");
            }
            return null;
        } finally {
            if(out !=null){
                out.flush();
                out.close();
            }
        }
        return filePath;
    }


    private ISysAttendReportLogService sysAttendReportLogService;
    private ISysAttendReportLogService getSysAttendReportLogService() {
        if (sysAttendReportLogService == null) {
            sysAttendReportLogService = (ISysAttendReportLogService) SpringBeanUtil.getBean("sysAttendReportLogService");
        }
        return sysAttendReportLogService;
    }

    private ISysAttendStatService sysAttendStatService;
    private ISysAttendStatService getSysAttendStatService() {
        if (sysAttendStatService == null) {
            sysAttendStatService = (ISysAttendStatService) SpringBeanUtil.getBean("sysAttendStatService");
        }
        return sysAttendStatService;
    }
    private ISysAttendStatDetailService sysAttendStatDetailService;
    private ISysAttendStatDetailService getSysAttendStatDetailService() {
        if (sysAttendStatDetailService == null) {
            sysAttendStatDetailService = (ISysAttendStatDetailService) SpringBeanUtil.getBean("sysAttendStatDetailService");
        }
        return sysAttendStatDetailService;
    }
    protected ISysAttMainCoreInnerService sysAttMainService;

    private ISysAttMainCoreInnerService getSysAttMainService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
                    .getBean("sysAttMainService");
        }
        return sysAttMainService;
    }
    protected ISysAttendReportService sysAttendReportService;
    protected ISysAttendReportService getSysAttendReportService() {
        if (sysAttendReportService == null) {
            sysAttendReportService = (ISysAttendReportService) SpringBeanUtil.getBean("sysAttendReportService");
        }
        return sysAttendReportService;
    }
}
