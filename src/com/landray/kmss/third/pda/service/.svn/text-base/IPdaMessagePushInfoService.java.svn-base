package com.landray.kmss.third.pda.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;

public interface IPdaMessagePushInfoService extends IBaseService {
	
	/**
	 * 功能：清理失效消息推送信息
	 * @return int 清理条数
	 */
	public abstract int deleteInvalidPdaMessagePushInfo();
	
	/**
	 * 功能：获取待推送的消息推送信息记录的总条数
	 * @param String fdId 人员Id
	 * @return long 待推送消息总记录条数
	 */
	public abstract long getPdaMessagePushInfoCount(String fdId);
	
	/**
	 * 功能：推送并更新消息推送信息
	 * @param String fdId 人员Id
	 * @return int 推送并更新成功的消息记录数
	 */
	public abstract int updatePdaMessagePushInfo(String fdId);
	
	/**
	 * 功能：查询已推送成功的消息信息
	 * @param String fdId 人员Id
	 * @return List 已推送成功的消息信息列表
	 */
	@SuppressWarnings("unchecked")
	public abstract List findPdaMessagePushInfo(String fdId) throws Exception;
	
	/**
	 * 功能：查阅并更新消息文档
	 * @param String fdId 人员Id
	 * @return int 查阅并更新消息文档记录数
	 */
	public abstract int updatePdaMessagePushInfoFdAvailable(String fdId);

}
