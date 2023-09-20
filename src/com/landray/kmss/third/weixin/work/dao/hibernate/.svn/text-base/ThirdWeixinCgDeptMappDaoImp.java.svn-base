package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinCgDeptMappDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgDeptMapp;

public class ThirdWeixinCgDeptMappDaoImp extends BaseDaoImp implements IThirdWeixinCgDeptMappDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinCgDeptMapp thirdWeixinCgDeptMapp = (ThirdWeixinCgDeptMapp) modelObj;
        if (thirdWeixinCgDeptMapp.getDocCreateTime() == null) {
            thirdWeixinCgDeptMapp.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinCgDeptMapp);
    }
}
