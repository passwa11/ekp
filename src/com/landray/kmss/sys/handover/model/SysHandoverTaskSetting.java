package com.landray.kmss.sys.handover.model;

import java.util.Date;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 交接任务设置
 * 
 * @author 潘永辉 2017年7月3日
 *
 */
public class SysHandoverTaskSetting extends BaseAppConfig {

	protected String startTime; // 开始时间
	protected int runtimes; // 运行时长
	protected int batchUpdateCount; // 批量提交大小

	public SysHandoverTaskSetting() throws Exception {
		super();
		// 开始时间默认为：00:00（凌晨0点0分）
		String startTime = super.getValue("startTime");
		if (StringUtil.isNull(startTime)) {
			startTime = "00:00";
		}
		super.setValue("startTime", startTime);

		// 运行时长默认为：8小时
		String runtimes = super.getValue("runtimes");
		if (StringUtil.isNull(runtimes)) {
			runtimes = "8";
		}
		super.setValue("runtimes", runtimes);

		// 批量提交大小默认为200条
		String batchUpdateCount = super.getValue("batchUpdateCount");
		if (StringUtil.isNull(batchUpdateCount)) {
			batchUpdateCount = "200";
		}
		super.setValue("batchUpdateCount", batchUpdateCount);
	}

	@Override
	public String getJSPUrl() {
		return "/sys/handover/taskSetting_config.jsp";
	}

	/**
	 * 获取交接任务运行开始时间
	 * 
	 * @return
	 */
	public Date getRunTimeForJob() {
		// 获取明天的日期，交接任务都是在第二天执行
		String s_date = DateUtil.convertDateToString(DateUtil.getDate(1),
				DateUtil.PATTERN_DATE) + " " + getStartTime();
		return DateUtil.convertStringToDate(s_date, DateUtil.PATTERN_DATETIME);
	}

	public String getStartTime() {
		String _startTime = getValue("startTime");
		if (StringUtil.isNull(_startTime)) {
			return "00:00";
		} else {
			return _startTime;
		}
	}

	public int getRuntimes() {
		String _runtimes = getValue("runtimes");
		if (StringUtil.isNull(_runtimes)) {
			runtimes = 8;
		} else {
			runtimes = Integer.valueOf(_runtimes);
		}
		return runtimes;
	}

	public int getBatchUpdateCount() {
		String _batchUpdateCount = getValue("batchUpdateCount");
		if (StringUtil.isNull(_batchUpdateCount)) {
			batchUpdateCount = 200;
		} else {
			batchUpdateCount = Integer.valueOf(_batchUpdateCount);
		}
		return batchUpdateCount;
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-handover:sysHandoverConfigMain.task.setting");
	}

}
