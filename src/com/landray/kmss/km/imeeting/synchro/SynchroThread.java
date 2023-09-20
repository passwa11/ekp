package com.landray.kmss.km.imeeting.synchro;

import java.util.Calendar;
import java.util.Date;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutCacheService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONObject;

public class SynchroThread extends Thread {

	private KmImeetingMain kmImeetingMain;
	private int operate;

	private IKmImeetingMainService kmImeetingMainService;
	private ISysQuartzCoreService sysQuartzCoreService;
	private IKmImeetingOutCacheService kmImeetingOutCacheService;

	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil
					.getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	public ISysQuartzCoreService getSysQuartzCoreService() {
		if (sysQuartzCoreService == null) {
			sysQuartzCoreService = (ISysQuartzCoreService) SpringBeanUtil
					.getBean("sysQuartzCoreService");
		}
		return sysQuartzCoreService;
	}

	public IKmImeetingOutCacheService getKmImeetingOutCacheService() {
		if (kmImeetingOutCacheService == null) {
			kmImeetingOutCacheService = (IKmImeetingOutCacheService) SpringBeanUtil
					.getBean("kmImeetingOutCacheService");
		}
		return kmImeetingOutCacheService;
	}

	public SynchroThread(KmImeetingMain kmImeetingMain, int operate) {
		this.kmImeetingMain = kmImeetingMain;
		this.operate = operate;
	}

	@Override
	public void run() {
		TransactionStatus status = TransactionUtils.beginTransaction();
		try {
			if (this.operate == SynchroConstants.OPERATE_ADD) {
				addOpt();
			}
			if (this.operate == SynchroConstants.OPERATE_UPDATE) {
				updateOpt();
			}
			if (this.operate == SynchroConstants.OPERATE_DELETE) {
				deleteOpt();
			}
			if (this.operate == SynchroConstants.OPERATE_CANCEL) {
				cancelOpt();
			}
			TransactionUtils.getTransactionManager().commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionUtils.getTransactionManager().rollback(status);
		}
	}

	private void addOpt() throws Exception {
		KmImeetingMain kmImeetingMain = this.kmImeetingMain;
		// 同步会议到第三方应用,如exchange
		getKmImeetingOutCacheService().addImeeting(kmImeetingMain);
		// 增加同步接入定时器
		// saveSynchroInQuart(kmImeetingMain);
	}

	private void updateOpt() throws Exception {
		KmImeetingMain kmImeetingMain = this.kmImeetingMain;
		getKmImeetingOutCacheService().updateImeeting(kmImeetingMain);
	}

	private void deleteOpt() throws Exception {
		KmImeetingMain kmImeetingMain = this.kmImeetingMain;
		getKmImeetingOutCacheService().deleteImeeting(kmImeetingMain);
	}

	private void cancelOpt() throws Exception {
		KmImeetingMain kmImeetingMain = this.kmImeetingMain;
		getKmImeetingOutCacheService().cacelImeeting(kmImeetingMain);
	}

	/**
	 * cronExpression表达式
	 */
	private String getCronExpression(Date date, int beforeDay) {
		StringBuffer cronExpression = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0 - beforeDay);
		cronExpression.append("0" + " ")
				.append(calendar.get(Calendar.MINUTE) + " ")
				.append(calendar.get(Calendar.HOUR_OF_DAY) + " ")
				.append(calendar.get(Calendar.DAY_OF_MONTH) + " ")
				.append((calendar.get(Calendar.MONTH) + 1) + " ")
				.append("? " + calendar.get(Calendar.YEAR));
		return cronExpression.toString();
	}

	private void saveSynchroInQuart(KmImeetingMain kmImeetingMain)
			throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("synchroInQuart");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("synchroInQuart");
		JSONObject parameter = new JSONObject();
		parameter.put("fdId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("会议同步接入定时器");
		quartzContext.setQuartzCronExpression(getCronExpression(
				kmImeetingMain.getFdHoldDate(), 0));
		getSysQuartzCoreService().saveScheduler(quartzContext, kmImeetingMain);
	}

}
