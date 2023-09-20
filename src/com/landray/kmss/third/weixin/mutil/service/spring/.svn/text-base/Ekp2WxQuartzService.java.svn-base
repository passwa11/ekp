package com.landray.kmss.third.weixin.mutil.service.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.third.weixin.mutil.model.WeixinWorkConfig;

public class Ekp2WxQuartzService
		implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Ekp2WxQuartzService.class);

	private ISysQuartzJobService sysQuartzJobService;

	public void
			setSysQuartzJobService(ISysQuartzJobService sysQuartzJobService) {
		this.sysQuartzJobService = sysQuartzJobService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 系统启动
		if (event.getApplicationContext().getParent() == null) {
			try {
				// addConfig();
			} catch (Exception e) {
				logger.error("启动系统初始化同步配置失败" + e);
				System.out.println("启动系统初始化同步配置失败" + e);
			}
		}
	}

	/**
	 * 整体初始化更新
	 */
	public void addConfig(String fdKey) throws Exception {
		// 生成定时任务
		logger.debug("生成定时任务 " + fdKey + " 中");
		clearSysQuartzJob();// 清除多企业微信的系统任务
		addQuartzJob(fdKey);
	}

	private void clearSysQuartzJob() {
		try {
			List<SysQuartzJob> allJobs = sysQuartzJobService.findList(
					"fdSubject in ('third-weixin-mutil:third.wx.OMSSynchroOutTitle','third-weixin-mutil:third.wxwork.person.sync')",
					null);
			if (allJobs.size() > 0) {
				for (int i = 0; i < allJobs.size(); i++) {
					sysQuartzJobService.delete(allJobs.get(i));
				}
			}
		} catch (Exception e) {
			logger.debug("删除多企业微信的系统任务失败");
			logger.debug("", e);
		}
	}

	private void addQuartzJob(String fdWxKey) {

		// 判断定时任务是否存在，不存在则新建
		try {
			List<SysQuartzJob> allJobs = sysQuartzJobService.findList(
					"fdJobService in ('thirdMutilWxworkOmsInitTarget','mutilWxworkSynchroOrg2WxTarget')  and fdIsSysJob = 0 and fdParameter= '"
							+ fdWxKey + "'",
					null);
			if (allJobs.size() == 2) {
				logger.debug(fdWxKey + " 定时任务已经存在");
				return;
			} else if (allJobs.size() == 1) {
				logger.debug(fdWxKey + " 只有一个定时任务，先删除，后新建");
				sysQuartzJobService.delete(allJobs.get(0));
			}
		} catch (Exception e1) {
			logger.error("检查并处理多企业微信定时任务失败！");
			logger.error("", e1);
		}
		// 人员对照表定时任务
		SysQuartzJob jobModel = new SysQuartzJob();
		jobModel.setFdIsSysJob(Boolean.TRUE);
		jobModel.setFdTitle(
				"【" + WeixinWorkConfig.newInstance(fdWxKey).getWxName() + " ("
						+ fdWxKey + ") 更新人员对照表 --多企业微信】");
		jobModel.setFdSubject(
				"【" + WeixinWorkConfig.newInstance(fdWxKey).getWxName() + " ("
						+ fdWxKey + ") 更新人员对照表 --多企业微信】");
		jobModel.setFdJobService("thirdMutilWxworkOmsInitTarget");
		jobModel.setFdJobMethod("updatePerson2");
		jobModel.setFdDescription(
				"third-weixin-mutil:init.quartz.description");
		jobModel.setFdIsSysJob(false);
		jobModel.setFdEnabled(true);
		jobModel.setFdParameter(fdWxKey);
		jobModel.setFdCronExpression("0 0 0/2 * * ? *");

		try {
			sysQuartzJobService.add(jobModel);
		} catch (Exception e) {
			logger.debug("【" + WeixinWorkConfig.newInstance(fdWxKey).getWxName()
					+ " (" + fdWxKey + ") 更新人员对照表 --多企业微信】" + "定时任务添加失败！！！");
			e.printStackTrace();
		}
		// 组织架构同步定时任务
		SysQuartzJob jobModel2 = new SysQuartzJob();
		jobModel2.setFdIsSysJob(Boolean.TRUE);
		jobModel2.setFdTitle(
				"【" + WeixinWorkConfig.newInstance(fdWxKey).getWxName() + " ("
						+ fdWxKey + ")组织架构同步到企业微信 --多企业微信】");
		jobModel2.setFdSubject(
				"【" + WeixinWorkConfig.newInstance(fdWxKey).getWxName() + " ("
						+ fdWxKey + ")组织架构同步到企业微信 --多企业微信】");
		jobModel2.setFdJobService("mutilWxworkSynchroOrg2WxTarget");
		jobModel2.setFdJobMethod("synchro");
		jobModel2.setFdDescription(
				"third-weixin-mutil:syn.quartz.description");
		jobModel2.setFdIsSysJob(false);
		jobModel2.setFdEnabled(true);
		jobModel2.setFdParameter(fdWxKey);
		jobModel2.setFdCronExpression("0 0 1 * * ? *");

		try {
			sysQuartzJobService.add(jobModel2);
		} catch (Exception e) {
			logger.debug("【" + WeixinWorkConfig.newInstance(fdWxKey).getWxName()
					+ " (" + fdWxKey + ")组织架构同步到企业微信 --多企业微信】" + "定时任务添加失败！！！");
			e.printStackTrace();
		}

	}

	public void deleteConfig(String fdkey) throws Exception {
		List<SysQuartzJob> allJobs = sysQuartzJobService.findList(
				"fdJobService in ('thirdMutilWxworkOmsInitTarget','mutilWxworkSynchroOrg2WxTarget')  and fdIsSysJob = 0 and fdParameter= '"
						+ fdkey + "'",
				null);
		if (allJobs.size() > 0) {
			for (int i = 0; i < allJobs.size(); i++) {
				logger.debug("删除多企业微信定时任务：" + allJobs.get(i).getFdParameter());
				sysQuartzJobService.delete(allJobs.get(i));
			}
		}
	}
}
