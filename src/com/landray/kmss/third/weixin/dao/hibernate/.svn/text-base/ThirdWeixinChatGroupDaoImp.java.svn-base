package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinChatGroupDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;

public class ThirdWeixinChatGroupDaoImp extends BaseDaoImp implements IThirdWeixinChatGroupDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinChatGroup thirdWeixinChatGroup = (ThirdWeixinChatGroup) modelObj;
        if (thirdWeixinChatGroup.getDocCreateTime() == null) {
            thirdWeixinChatGroup.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinChatGroup);
    }
}
