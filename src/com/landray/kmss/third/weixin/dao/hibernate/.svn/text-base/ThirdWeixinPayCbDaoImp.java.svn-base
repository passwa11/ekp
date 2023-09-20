package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinPayCbDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayCb;

public class ThirdWeixinPayCbDaoImp extends BaseDaoImp implements IThirdWeixinPayCbDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinPayCb thirdWeixinPayCb = (ThirdWeixinPayCb) modelObj;
        if (thirdWeixinPayCb.getDocCreateTime() == null) {
            thirdWeixinPayCb.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinPayCb);
    }
}
