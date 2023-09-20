package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinContactCbDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactCb;

public class ThirdWeixinContactCbDaoImp extends BaseDaoImp implements IThirdWeixinContactCbDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinContactCb thirdWeixinContactCb = (ThirdWeixinContactCb) modelObj;
        if (thirdWeixinContactCb.getDocCreateTime() == null) {
            thirdWeixinContactCb.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinContactCb);
    }
}
