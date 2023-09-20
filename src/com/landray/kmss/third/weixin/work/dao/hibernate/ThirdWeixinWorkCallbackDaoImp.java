package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinWorkCallbackDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback;

public class ThirdWeixinWorkCallbackDaoImp extends BaseDaoImp implements IThirdWeixinWorkCallbackDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinWorkCallback thirdWeixinWorkCallback = (ThirdWeixinWorkCallback) modelObj;
        if (thirdWeixinWorkCallback.getDocCreateTime() == null) {
            thirdWeixinWorkCallback.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinWorkCallback);
    }
}
