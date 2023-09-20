package com.landray.kmss.km.forum.service;

import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.sunbor.web.tag.Page;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 论坛话题业务对象接口
 */
public interface IKmForumTopicService extends IBaseService {

	/**
	 * 推荐帖子
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public abstract void updateIntroduce(String fdId, String fdTargetIds,
			String fdNotifyType) throws Exception;

	/**
	 * 将该主题结贴
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public abstract void doConclude(String fdId) throws Exception;

	/**
	 * 将该主题置顶
	 * 
	 * @param fdId
	 * @param fdEndTime 有效期
	 * @throws Exception
	 */
	public abstract void doStick(String fdId,Long days) throws Exception;

	/**
	 * 将该主题取消置顶
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public abstract void undoStick(String fdId) throws Exception;

	/**
	 * 将该主题置为精华
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public abstract void doPink(String fdId) throws Exception;

	/**
	 * 将该主题取消精华
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public abstract void undoPink(String fdId) throws Exception;

	/**
	 * 将该主题转移
	 * 
	 * @param fdId
	 * @param fdForumId
	 * @throws Exception
	 */
	public abstract void move(String fdId, String fdForumId) throws Exception;

	/**
	 * 点击计数
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public abstract void hitCount(String fdId) throws Exception;
	
	public void updateHierarchyId(String fdForumId)throws Exception;
	
	/**
	 * 批量转移帖子
	 * 
	 * @param ids
	 * @param templateId
	 * @throws Exception
	 */
	public int updateDucmentCategory(String ids, String forumId)
			throws Exception;
	
	/**
	 * 清除置顶定时任务
	 * 
	 * @throws Exception
	 */
	public void updateTopAgent() throws Exception;
	
	/**
	 * 获得发帖数和获取回帖数 根据分类id
	 * @param categoryId 
	 * @return Integer [] 数组第一个数字是发帖数，第二个数字是回帖数
	 * @throws Exception
	 */
	public Integer [] getForumTopicAndReplyCount(String categoryId) throws Exception;

	/**
	 * 结贴定时任务
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void updateForumExpire() throws Exception;
	
	/**
	 * 获得该分类或子分类下最新的主题
	 * 
	 * @return
	 */
	public KmForumTopic getRecentTopicByCategoryId(String categoryId, boolean filterDraft)
			throws Exception;

	/**
	 * 门户部件数据源
	 * 
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> getTopicList(RequestContext request) throws Exception;

	public Page getRankList(RequestContext request) throws Exception;

	public Page getTopicNewList(RequestContext request) throws Exception;
}
