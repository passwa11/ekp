package com.landray.kmss.sys.handover.service;

import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 日志生成接口
 * 
 * @author tanyouhao
 *
 */
public interface ISysHandoverLogService {

	/**
	 * 生成主日志并返回数据日志
	 * 
	 * @param from
	 * @param to
	 * @param mainId
	 * @return
	 */
	public String beforeConfigHandover(HandoverConfig config,
			HandoverItem item, SysOrgElement from, SysOrgElement to,
			String mainId);
	
	public String beforeConfigHandover(HandoverConfig config,
			HandoverItem item, SysOrgElement from, SysOrgElement to,
			String mainId, Integer handoverType);

	/**
	 * 
	 * 
	 * @param handoverExecuteContext
	 */
	public void afterConfigHandover(
			HandoverExecuteContext handoverExecuteContext);
}
