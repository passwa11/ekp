package com.landray.kmss.sys.oms.in.interfaces;

import java.util.List;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 创建日期 2006-11-29
 * 
 * @author 吴兵 接入第三方平台获取数据提供者
 */
public interface IOMSSynchroInOtherProvider extends OMSSynchroInProvider {

	/**
	 * @return 取得需要删除的记录
	 */
	public abstract String[] getKeywordsForDelete() throws Exception;

	/**
	 * 取得最近更新的数据
	 * 
	 * @return 要同步数据集合
	 */
	public abstract List getRecordsForUpdate() throws Exception;

	/**
	 * 取得最近更新的数据
	 * 
	 * @param page
	 *            当前页号
	 * @return 要同步数据集合
	 */
	public abstract List getRecordsForUpdate(int page) throws Exception;

	/**
	 * 页大小
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract int getPageSize() throws Exception;

	public abstract void setRelationOA2IT() throws Exception;

	// //

	/**
	 * @return 岗位关系类型
	 */
	public abstract int getPostType();

	/**
	 * @return 领导关系类型
	 */
	public abstract int getLeaderType();
	
	/**
	 * 由接口实现方来实现同步逻辑，OMS只做任务调度
	 */
	public abstract void handlerSynch(SysQuartzJobContext context) throws Exception;
}
