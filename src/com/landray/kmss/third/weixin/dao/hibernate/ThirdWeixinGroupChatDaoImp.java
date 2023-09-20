package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinGroupChatDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat;

public class ThirdWeixinGroupChatDaoImp extends BaseDaoImp implements IThirdWeixinGroupChatDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinGroupChat thirdWeixinGroupChat = (ThirdWeixinGroupChat) modelObj;
        if (thirdWeixinGroupChat.getDocCreateTime() == null) {
            thirdWeixinGroupChat.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinGroupChat);
    }
}
