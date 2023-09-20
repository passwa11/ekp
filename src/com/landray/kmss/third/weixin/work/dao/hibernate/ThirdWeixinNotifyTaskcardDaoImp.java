package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinNotifyTaskcardDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyTaskcard;

public class ThirdWeixinNotifyTaskcardDaoImp extends BaseDaoImp implements IThirdWeixinNotifyTaskcardDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinNotifyTaskcard thirdWeixinNotifyTaskcard = (ThirdWeixinNotifyTaskcard) modelObj;
        if (thirdWeixinNotifyTaskcard.getDocCreateTime() == null) {
            thirdWeixinNotifyTaskcard.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinNotifyTaskcard);
    }
}
