package com.landray.kmss.third.weixin.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat;

public interface IThirdWeixinGroupChatService extends IExtendDataService {

    ThirdWeixinGroupChat findByRoomId(String roomid) throws Exception;
}
