package com.landray.kmss.third.weixin.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.weixin.dao.IThirdWeixinContactMappDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdWeixinContactMappDaoImp extends BaseDaoImp implements IThirdWeixinContactMappDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinContactMapp thirdWeixinContactMapp = (ThirdWeixinContactMapp) modelObj;
        if (thirdWeixinContactMapp.getDocCreator() == null) {
            thirdWeixinContactMapp.setDocCreator(UserUtil.getUser());
        }
        if (thirdWeixinContactMapp.getDocCreateTime() == null) {
            thirdWeixinContactMapp.setDocCreateTime(new Date());
        }
        if (thirdWeixinContactMapp.getDocAlterTime() == null) {
            thirdWeixinContactMapp.setDocAlterTime(new Date());
        }
        return super.add(thirdWeixinContactMapp);
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        ThirdWeixinContactMapp thirdWeixinContactMapp = (ThirdWeixinContactMapp) modelObj;
        thirdWeixinContactMapp.setDocAlterTime(new Date());
        super.update(thirdWeixinContactMapp);
    }
}
