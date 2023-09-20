package com.landray.kmss.third.weixin.work.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard;

public interface IThirdWeixinNotifyTaskcardService extends IExtendDataService {

	public List<ThirdWeixinNotifyTaskcard> findByNotifyId(String notifyId)
			throws Exception;

	public ThirdWeixinNotifyTaskcard findByNotifyIdAndUserid(String notifyId,
			String userId)
			throws Exception;

	public ThirdWeixinNotifyTaskcard findByTaskcardId(String taskcardId)
			throws Exception;

	public ThirdWeixinNotifyTaskcard findByTaskcardId(String corpId, String taskcardId)
			throws Exception;

}
