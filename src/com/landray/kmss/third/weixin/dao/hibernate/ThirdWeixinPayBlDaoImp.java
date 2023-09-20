package com.landray.kmss.third.weixin.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.weixin.dao.IThirdWeixinPayBlDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayBl;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdWeixinPayBlDaoImp extends BaseDaoImp implements IThirdWeixinPayBlDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinPayBl thirdWeixinPayBl = (ThirdWeixinPayBl) modelObj;
        if (thirdWeixinPayBl.getDocCreator() == null) {
            thirdWeixinPayBl.setDocCreator(UserUtil.getUser());
        }
        if (thirdWeixinPayBl.getDocCreateTime() == null) {
            thirdWeixinPayBl.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinPayBl);
    }
}
