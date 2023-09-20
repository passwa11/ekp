package com.landray.kmss.sys.oms.notify.service;

import java.util.HashMap;

import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;

/**
 * 创建日期 2006-12-25
 * 
 * @author 吴兵 组织机构同步通知
 */
public interface ISynchroOrgNotify {

	/**
	 *同步通知 
	 */
	public abstract void send(ISysNotifyModel mainModel,NotifyContext notifyContext,HashMap replaceMap)throws Exception;
	
	/**
	 *同步出错上下文
	 */
	public abstract NotifyContext getSyncExceptionNotifyContext()throws Exception;

	/**
	 *创建帐号上下文
	 */
	public abstract NotifyContext getCreateAccountNotifyContext()throws Exception;
	
	/**
	 *删除帐号上下文
	 */
	public abstract NotifyContext getDeleteAccountNotifyContext()throws Exception;
	
	/**
	 *取得替换文内容
	 */
	public abstract HashMap getReplaceMap(String notifyPersonName);
}
