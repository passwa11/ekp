package com.landray.kmss.sys.remind.service;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.remind.model.SysRemindMain;

/**
 * 提醒中心
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public interface ISysRemindMainService extends IBaseService {

	/**
	 * 创建提醒任务
	 * 
	 * @param main
	 * @param model
	 * @throws Exception
	 */
	public abstract void createRemindTask(SysRemindMain main, IBaseModel model) throws Exception;

	/**
	 * 执行提醒任务
	 * 
	 * @param context
	 * @throws Exception
	 */
	public abstract void remindJob(SysQuartzJobContext context) throws Exception;

}
