package com.landray.kmss.sys.time.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.service.IBaseService;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-15
 */
public interface ISysTimeLeaveResumeService extends IBaseService {

	public void updateLeave(String id) throws Exception;

	public void updateLeave(String id, Map dataMap) throws Exception;

	public void updateAttend(String id) throws Exception;

	/*
	 * 根据用户与请假信息,获取对应的有效销假记录
	 * 
	 * @param fdLeaveId 请假记录ID
	 * 
	 * @return
	 */
	public List findResumeList(String fdPersonId, String fdLeaveId)
			throws Exception;

}
