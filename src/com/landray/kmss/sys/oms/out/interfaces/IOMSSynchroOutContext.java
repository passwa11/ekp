package com.landray.kmss.sys.oms.out.interfaces;

import java.util.List;

/**
 * OMS同步上下文内容接口
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public interface IOMSSynchroOutContext extends Cloneable {
	
	public abstract void setAppName(String appName);
	
	public abstract void initAllContext()throws Exception;
	
	public abstract void initIncContext()throws Exception;
	
	/*
	 * 需要增加的组织元素
	 */
	public abstract List getAddOrgElements();

	/*
	 * 需要更新的组织元素
	 */
	public abstract List getUpdateOrgElements();

	/*
	 * 需要删除的组织元素
	 */
	public abstract List getDeleteOrgElements();

	/**
	 * 如是同步成功，则删除相应的本地缓存记录
	 * 
	 */
	public abstract void deleteLocalOMSCache() throws Exception;

	/**
	 * 如是同步成功，则删除相应的本地缓存记录
	 * 
	 * @param fdElementId
	 *            要删除的元素
	 */
	public abstract void deleteLocalOMSCache(String fdElementId)
			throws Exception;

	/**
	 * 更新相应的本地缓存记录
	 * 
	 * @param fdElementId
	 *            要删除的元素
	 */
	public abstract void updateLocalOMSCache(String fdElementId)
			throws Exception;
	
	public Object clone();

}
