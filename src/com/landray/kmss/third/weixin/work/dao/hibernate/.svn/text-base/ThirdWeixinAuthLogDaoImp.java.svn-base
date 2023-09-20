package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinAuthLogDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinAuthLog;

public class ThirdWeixinAuthLogDaoImp extends BaseDaoImp implements IThirdWeixinAuthLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinAuthLog thirdWeixinAuthLog = (ThirdWeixinAuthLog) modelObj;
        if (thirdWeixinAuthLog.getDocCreateTime() == null) {
            thirdWeixinAuthLog.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinAuthLog);
    }
}
