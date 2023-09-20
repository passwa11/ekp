package com.landray.kmss.sys.handover.interfaces.config;

import com.landray.kmss.common.actions.RequestContext;
import net.sf.json.JSONObject;

/**
 * 配置类工作交接处理接口
 * 
 * @author 缪贵荣
 * 
 */
public interface IHandoverHandler {
	/* 描述连接符 */
	final String CONN_SYM = " >> ";
	/* id分隔符*/
	final String ID_SPLIT = ";;";

	/**
	 * 配置类工作交接查询
	 * 
	 * @param context
	 */
	public void search(HandoverSearchContext handoverSearchContext) throws Exception;

	/**
	 * 配置类工作交接执行
	 * 
	 * @param context
	 */
	public void execute(HandoverExecuteContext context) throws Exception;
}
