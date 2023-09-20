package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinAppchatDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinAppchat;

public class ThirdWeixinAppchatDaoImp extends BaseDaoImp implements IThirdWeixinAppchatDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinAppchat thirdWeixinAppchat = (ThirdWeixinAppchat) modelObj;
        if (thirdWeixinAppchat.getDocCreateTime() == null) {
            thirdWeixinAppchat.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinAppchat);
    }
}
