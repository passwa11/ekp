package com.landray.kmss.fssc.budgeting.util;

import java.util.Date;

import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingApprovalLogService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class LoggerUtil{
	protected static IFsscBudgetingApprovalLogService loggerService;
	
	public static IFsscBudgetingApprovalLogService getLoggerService() {
		if(loggerService==null){
			loggerService=(IFsscBudgetingApprovalLogService) SpringBeanUtil.getBean("fsscBudgetingApprovalLogService");
		}
		return loggerService;
	}

	public static void createApprovalLog(String fdMainId,String fdDetailId,String fdApprovalType,
			Date fdApprovalTime,String operate,SysOrgPerson fdOperator) throws Exception{
		FsscBudgetingApprovalLog log=new FsscBudgetingApprovalLog();
		String docSubject=ResourceUtil.getString("fsscBudgetingApprovalLog.docSubject.message", "fssc-budgeting");
    	docSubject=docSubject.replace("{operator}", UserUtil.getUser().getFdName())
    	.replace("{fdDate}", DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));
    	docSubject=docSubject.replace("{operate}", operate);
		log.setFdMainId(fdMainId);
		log.setFdDetailId(fdDetailId);
		log.setFdApprovalType(fdApprovalType);
		log.setFdApprovalTime(fdApprovalTime);
		log.setDocSubject(docSubject);
		log.setFdOperator(fdOperator);
		getLoggerService().add(log);
	}
}
