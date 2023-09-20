package com.landray.kmss.third.feishu.service;

import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.feishu.model.ThirdFeishuMsgMapp;

public interface IThirdFeishuMsgMappService extends IExtendDataService {

	public List<ThirdFeishuMsgMapp> findByNotifyId(String notifyId)
			throws Exception;

	public ThirdFeishuMsgMapp findByNotifyAndPerson(String notifyId,
			String personId) throws Exception;

	public ThirdFeishuMsgMapp findByMessageId(String messageId)
			throws Exception;
}
