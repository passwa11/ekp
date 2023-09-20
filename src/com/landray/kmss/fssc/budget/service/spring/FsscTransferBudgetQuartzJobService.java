package com.landray.kmss.fssc.budget.service.spring;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.fssc.budget.actions.FsscBudgetPortletAction;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


/**
 * 预算结转定时任务
 * @author 蛋蛋
 *
 */
public class FsscTransferBudgetQuartzJobService extends BaseServiceImp {
	private static final Log logger = LogFactory.getLog(FsscTransferBudgetQuartzJobService.class);

	IFsscBudgetMainService fsscBudgetMainService;

	public void setFsscBudgetMainService(IFsscBudgetMainService fsscBudgetMainService) {
		this.fsscBudgetMainService = fsscBudgetMainService;
	}
	
	IEopBasedataCompanyService eopBasedataCompanyService;

	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if(eopBasedataCompanyService==null){
			eopBasedataCompanyService=(IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
			
		}
		return eopBasedataCompanyService;
	}


	public void transferBudget() throws Exception{
		System.out.println("预算结转开始=============");
		//每月一号执行结转
		Date startMonthDate=DateUtil.getBeginDayOLastMonth();//上个月
		String startMonth=DateUtil.convertDateToString(startMonthDate, "yyyyMM");
		Date endMonthDate=DateUtil.getBeginDayOfMonth();//本个月
		String endMonth=DateUtil.convertDateToString(endMonthDate, "yyyyMM");
		
		startMonth=cutNumToString(startMonth);  //对应需结转月份
		endMonth=cutNumToString(endMonth);  //对应需结转月份

		List<EopBasedataCompany> companys= getEopBasedataCompanyService().findList(new HQLInfo());
		logger.warn("预算结转日期:startMonth"+startMonth+";endMonth"+endMonth);
		if(companys!=null&&companys.size()>0){
			//获取公司id
			String fdCompanyIds=companys.get(0).getFdId();
			logger.warn("预算结转公司:fdCompanyIds"+fdCompanyIds);
			fsscBudgetMainService.updateTransferBudget(startMonth, endMonth, fdCompanyIds);
		}
	}
	
	private String cutNumToString(String startMonth){
		String month=startMonth.substring(4, 6);  //对应需结转月份

		int monthNum=Integer.valueOf(month)-1;
		if(monthNum<10){
			month="0"+monthNum;
		}
		return startMonth.substring(0, 4)+month;
	}
}
