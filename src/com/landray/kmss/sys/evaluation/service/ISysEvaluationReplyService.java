package com.landray.kmss.sys.evaluation.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;

public interface ISysEvaluationReplyService extends IBaseService {
	
	/**
	 * 删除点评回复
	 * @param id
	 * @throws Exception
	 */
	public abstract void deleteReply(String replyId,String evalId,String fdModelName) throws Exception;
	
	/**
	 *  获取并封装父级回复信息
	 * @param jsonArray
	 * @param parentReplyId
	 * @return
	 * @throws Exception
	 */
	public abstract JSONArray getParentReplyInfo(JSONArray jsonArray,
			String parentReplyId,RequestContext requestContext) throws Exception;
	
	/**
	 * 更新子回复fdParentId和fdHierarchyId
	 * @param replyId
	 * @throws Exception
	 */
	public void updateSubReply(String replyId) throws Exception ;
	
	/**
	 * 获取回复数量
	 * @param ids
	 * @return 
	 * @throws Exception
	 */
	public List<Object[]> getReplyCount(String ids) throws Exception;
}
