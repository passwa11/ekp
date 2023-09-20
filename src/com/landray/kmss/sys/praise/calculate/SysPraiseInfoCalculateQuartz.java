package com.landray.kmss.sys.praise.calculate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.praise.model.SysPraiseInfo;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonDetailService;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonMonthService;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonalService;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

public class SysPraiseInfoCalculateQuartz {

	private boolean IS_RUNNING = false;

	private JSONObject object = new JSONObject();

	private ISysPraiseInfoPersonDetailService sysPraiseInfoPersonDetailService;

	public void setSysPraiseInfoPersonDetailService(ISysPraiseInfoPersonDetailService sysPraiseInfoPersonDetailService) {
		this.sysPraiseInfoPersonDetailService = sysPraiseInfoPersonDetailService;
	}

	private ISysPraiseInfoPersonMonthService sysPraiseInfoPersonMonthService;

	public void setSysPraiseInfoPersonMonthService(ISysPraiseInfoPersonMonthService sysPraiseInfoPersonMonthService) {
		this.sysPraiseInfoPersonMonthService = sysPraiseInfoPersonMonthService;
	}

	private ISysPraiseInfoService sysPraiseInfoService;

	public void setSysPraiseInfoService(ISysPraiseInfoService sysPraiseInfoService) {
		this.sysPraiseInfoService = sysPraiseInfoService;
	}

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	private ISysPraiseInfoPersonalService sysPraiseInfoPersonalService;

	public void setSysPraiseInfoPersonalService(ISysPraiseInfoPersonalService sysPraiseInfoPersonalService) {
		this.sysPraiseInfoPersonalService = sysPraiseInfoPersonalService;
	}

	/**
	 * 执行点赞统计
	 * 
	 * @throws Exception
	 */
	public void execute() {
		synchronized (object) {
			if (IS_RUNNING) {
				// 定时任务运行中
				return;
			} else {
				// 开始运行定时任务
				IS_RUNNING = true;
			}
		}

		try {

			Date lastTime = sysPraiseInfoService.getLastExecuteTime();
			Date nowTime = getNowTime(new Date());
			if (lastTime == null) {
				lastTime = getEarliestTime();
			}
			if (lastTime == null) {
				// 从未有效执行过该定时任务，并且数据库里赞赏表不存在任何信息，则无需统计。
				return;
			}

			// 赞赏明细解析
			List<String> orgIds = sysPraiseInfoPersonDetailService.executeDetail(lastTime, nowTime);
			// 更新个人月度信息统计
			sysPraiseInfoPersonMonthService.executeDetail(lastTime, nowTime, orgIds);
			// 更新个人统计信息
			sysPraiseInfoPersonalService.executeDetail(lastTime, nowTime,orgIds);

			// 更新结束时间
			updateLastExecuteTime(nowTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IS_RUNNING = false;
		}
	}

	private Date getEarliestTime() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		Date date = null;
		hqlInfo.setRowSize(1);
		hqlInfo.setOrderBy("sysPraiseInfo.docCreateTime ASC");
		SysPraiseInfo praiseInfo = (SysPraiseInfo)sysPraiseInfoService.findFirstOne(hqlInfo);
		if (praiseInfo != null) {
			date = getNowTime(praiseInfo.getDocCreateTime());
		}
		return date;
	}

	/**
	 * 更新更新时间
	 * 
	 * @param nowTime
	 * @throws Exception
	 */
	private void updateLastExecuteTime(Date nowTime) throws Exception {
		if (nowTime == null) {
			return;
		}
		String fdKey = "com.landray.kmss.sys.praise.service.spring.SysPraiseInfoDetailServiceImp";
		String fdField = "fdUpdateTime";
		SysAppConfig config = (SysAppConfig)getSysAppConfigService().findFirstOne("fdKey='" + fdKey + "' and fdField='" + fdField + "'", null);
		if (config == null) {
			config = new SysAppConfig();
			config.setFdKey(fdKey);
			config.setFdField(fdField);
		}
		config.setFdValue(DateUtil.convertDateToString(nowTime, DateUtil.PATTERN_DATETIME));
		getSysAppConfigService().update(config);
	}

	/**
	 * 获取时间 YYYY-MM-DD hh:mm（精确到分）
	 * 
	 * @return
	 */
	private Date getNowTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
