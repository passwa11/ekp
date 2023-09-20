package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinChatDataBakDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataBak;

public class ThirdWeixinChatDataBakDaoImp extends BaseDaoImp implements IThirdWeixinChatDataBakDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinChatDataBak thirdWeixinChatDataBak = (ThirdWeixinChatDataBak) modelObj;
        if (thirdWeixinChatDataBak.getDocCreateTime() == null) {
            thirdWeixinChatDataBak.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinChatDataBak);
    }
}
