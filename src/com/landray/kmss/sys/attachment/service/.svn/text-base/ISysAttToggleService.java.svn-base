package com.landray.kmss.sys.attachment.service;

import org.json.simple.JSONObject;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 附件迁移服务类
 * @author peng
 */
public interface ISysAttToggleService{

	/**
	 * 迁移附件
	 * @param operate
	 * @return
	 */
	public JSONObject saveToggleAttchment(String operate,String source,String target,String startDate
			,String endDate,int toggleStartTime,int toggleEndTime) throws Exception;
	
	/**
	 * 供定时任务调用，迁移附件
	 * @param jobContext
	 * @throws Exception
	 */
	public void saveAndDoToggleAttchment(SysQuartzJobContext jobContext);
	
	
	
}
