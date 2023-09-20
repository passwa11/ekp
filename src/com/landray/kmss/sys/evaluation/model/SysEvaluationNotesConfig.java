package com.landray.kmss.sys.evaluation.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysEvaluationNotesConfig extends BaseAppConfig {
	public SysEvaluationNotesConfig() throws Exception {
		//是否开启段落点评
		super();
		String fdEnable = "";
		fdEnable = super.getValue("fdEnable");
		if (StringUtil.isNull(fdEnable)) {
			fdEnable = "false";
		}
		super.setValue("fdEnable", fdEnable);
		
		// 一天针对同一篇文档最大点评数
		String fdMaxTimesOneDay = "";
		fdMaxTimesOneDay = super.getValue("fdMaxTimesOneDay");
		if (StringUtil.isNull(fdMaxTimesOneDay)) {
			fdMaxTimesOneDay = "5";
		}
		super.setValue("fdMaxTimesOneDay", fdMaxTimesOneDay);

		// 点评间隔时间
		String fdIntervalTime = "";
		fdIntervalTime = super.getValue("fdIntervalTime");
		if (StringUtil.isNull(fdIntervalTime)) {
			fdIntervalTime = "1";
		}
		super.setValue("fdIntervalTime", fdIntervalTime);
	}
	@Override
    public String getJSPUrl() {
		return "/sys/evaluation/import/sysEvaluationNotes_config.jsp";
	}

	public String getFdEnable() {
		String fdEnable = "";
		fdEnable = super.getValue("fdEnable");
		if (StringUtil.isNull(fdEnable)) {
			fdEnable = "false";
		}
		return fdEnable;
	}

	public void setFdEnable(String fdEnable) {
		setValue("fdEnable", fdEnable);
	}
	
	/**
	 * 敏感词检测开关 敏感词统一到sys/profile 里面处理
	 */
	@Deprecated
	public String getIsWordCheck() {
		return getValue("isWordCheck")==null?"false":getValue("isWordCheck");
	}
	
	@Deprecated
	public void setIsWordCheck(String isWordCheck) {
		setValue("isWordCheck", isWordCheck);
	}
	
	/**
	 * 敏感词
	 */
	@Deprecated
	public String getWords() {
		return getValue("words");
	}

	@Deprecated
	public void setWords(String words) {
		setValue("words", words);
	}
	
	/**
	 *  是否需重新获取敏感词
	 */
	public String getIsNeedAcquire() {
		return getValue("isNeedAcquire");
	}

	public void setIsNeedAcquire(String isNeedAcquire) {
		setValue("isNeedAcquire", isNeedAcquire);
	}
	
	
	/**
	 * 点评间隔时间
	 */
	public String getFdIntervalTime() {
		return getValue("fdIntervalTime");
	}

	public void setFdIntervalTime(String fdIntervalTime) {
		setValue("fdIntervalTime", fdIntervalTime);
	}

	/**
	 * 一天点评次数
	 */
	public String getFdMaxTimesOneDay() {
		return getValue("fdMaxTimesOneDay");
	}

	public void setFdMaxTimesOneDay(String fdMaxTimesOneDay) {
		setValue("fdMaxTimesOneDay", fdMaxTimesOneDay);
	} 
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-evaluation:sysEvaluationWords.word.setting");
	}
}
