package com.landray.kmss.sys.organization.service;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.model.SysOrgRetrievePasswordLog;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线配置业务对象接口
 */
public interface ISysOrgRetrievePasswordLogService extends IBaseService {
	
	public List<SysOrgRetrievePasswordLog> findRetrievePasswordLogs(String personId, Date availableTime) throws Exception;
	
}
