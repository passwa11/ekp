package com.landray.kmss.third.weixin.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;

public interface IThirdWeixinChatGroupService extends IExtendDataService {

    public ThirdWeixinChatGroup findByMd5(String md5) throws Exception;

    public String genRelateUserId(String fromId, String toId);

    public String genMd5(String fromId, String toId, String roomId);

    public ThirdWeixinChatGroup findByRoomId(String roomId) throws Exception;
}
