package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinCgUserMappDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;

public class ThirdWeixinCgUserMappDaoImp extends BaseDaoImp implements IThirdWeixinCgUserMappDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinCgUserMapp thirdWeixinCgUserMapp = (ThirdWeixinCgUserMapp) modelObj;
        if (thirdWeixinCgUserMapp.getDocCreateTime() == null) {
            thirdWeixinCgUserMapp.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinCgUserMapp);
    }
}
