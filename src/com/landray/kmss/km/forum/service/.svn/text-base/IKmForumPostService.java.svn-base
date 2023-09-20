package com.landray.kmss.km.forum.service;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.forum.model.KmForumPost;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 帖子业务对象接口
 */
public interface IKmForumPostService extends IBaseService {
	/*
	 * 设置论坛用户积分
	 */
	public void setPosterScore(List list) throws Exception;

	/**
	 * @return 根据修改时间间隔取帖子集合
	 */
	public abstract List getLastPosts(Date begin, Date end, int start, int count)
			throws Exception;

	/**
	 * 发送回复通知
	 * 
	 * @param kmForumPost
	 * @throws Exception
	 */
	public abstract void saveSendNotify(KmForumPost kmForumPost, String fdIsAnonymous)
			throws Exception;

	/**
	 * 跟据主题ID查找楼主的发表信息
	 * 
	 * @param fdTopicId
	 * @return
	 * @throws Exception
	 */
	public abstract KmForumPost findFirstFloorPost(String fdTopicId)
			throws Exception;
	/**
	 * 跟据板块ID查找该板块最新回复
	 * 
	 * @param fdTopicId
	 * @return
	 * @throws Exception
	 */
	public abstract KmForumPost findLastFloorPostByCategoryId(String fdTopicId)
			throws Exception;
	
	/**
	 * 删除引用楼层
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public abstract void deleteQuotePost(IBaseModel model)
			throws Exception;

}
