package com.landray.kmss.third.weixin.work.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;

import com.landray.kmss.third.weixin.work.dao.IThirdWeixinWorkLivingDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkLiving;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdWeixinWorkLivingDaoImp extends BaseDaoImp implements IThirdWeixinWorkLivingDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinWorkLiving thirdWeixinWorkLiving = (ThirdWeixinWorkLiving) modelObj;
        if (thirdWeixinWorkLiving.getDocCreator() == null) {
            thirdWeixinWorkLiving.setDocCreator(UserUtil.getUser());
        }
        if (thirdWeixinWorkLiving.getDocCreateTime() == null) {
            thirdWeixinWorkLiving.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinWorkLiving);
    }
}
