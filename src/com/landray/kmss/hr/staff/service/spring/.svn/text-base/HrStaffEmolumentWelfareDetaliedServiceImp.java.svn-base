package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 薪酬福利明细
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfareDetaliedServiceImp extends BaseServiceImp
		implements IHrStaffEmolumentWelfareDetaliedService {

	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	
	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}
	
	private ISysQuartzCoreService sysQuartzCoreService;

	public ISysQuartzCoreService getSysQuartzCoreService() {
		if (sysQuartzCoreService == null) {
			sysQuartzCoreService = (ISysQuartzCoreService) SpringBeanUtil.getBean("sysQuartzCoreService");
		}
		return sysQuartzCoreService;
	}
	
	@Override
	public Double getSalaryByStaffId(HrStaffPersonInfo personInfo) throws Exception {
		
		if(personInfo.getFdSalary()!=null){
			return personInfo.getFdSalary();
		}else{
			return 0.0;
		}
		
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		//删除定时任务
		HrStaffEmolumentWelfareDetalied info = (HrStaffEmolumentWelfareDetalied)modelObj;
		if(info.getFdPersonInfo() !=null) {
			getSysQuartzCoreService().delete(info.getFdPersonInfo(), "setSalarySchedulerJob");
		}
		super.delete(modelObj);
	}
	/**
	 * 更新调薪记录
	 * @param fdId
	 * @param fdTransSalary
	 * @param fdEffectTime 修改后的有效日期
	 * @param fdOrgPersonId
	 * @throws Exception
	 */
	@Override
    public void updateSalary(String fdId , Double fdTransSalary , Date fdEffectTime , String fdOrgPersonId) throws Exception {
		
		HrStaffEmolumentWelfareDetalied modelObj = (HrStaffEmolumentWelfareDetalied)this.findByPrimaryKey(fdId);
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByOrgPersonId(fdOrgPersonId);
		Double fdAdjustAmount = fdTransSalary - modelObj.getFdBeforeEmolument();
		Date now =new Date();
		if(fdEffectTime!=null){
			if(fdEffectTime.after(now)){
				//定时任务
				setSalarySchedulerJob(fdEffectTime, personInfo, fdId);
			}else{
				//立刻执行
				personInfo.setFdSalary(fdTransSalary);
				modelObj.setFdIsEffective(true);
				getSysQuartzCoreService().delete(personInfo, "setSalarySchedulerJob");
			}
			modelObj.setFdEffectTime(fdEffectTime);
		}
		
		modelObj.setFdAdjustDate(now);
		modelObj.setFdAdjustAmount(fdAdjustAmount);
		modelObj.setFdAfterEmolument(fdTransSalary);
		update(modelObj);
	}
	
	@Override
    public void setSalarySchedulerJob(Date fdEffectTime, HrStaffPersonInfo personInfo, String fdId) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fdEffectTime);
		String cron = HrStaffDateUtil.getCronExpression(calendar);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("setSalarySchedulerJob");
			quartzContext.setQuartzJobService("hrStaffPersonInfoService");
			quartzContext.setQuartzKey("setSalarySchedulerJob");
			quartzContext.setQuartzParameter(personInfo.getFdId()+";"+fdId);
			quartzContext.setQuartzSubject(personInfo.getFdName() + "的薪资修改定时任务");
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			getSysQuartzCoreService().saveScheduler(quartzContext, personInfo);
		}
	}
}
