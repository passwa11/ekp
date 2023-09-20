package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinAccountDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinAccount;

public class ThirdWeixinAccountDaoImp extends BaseDaoImp implements IThirdWeixinAccountDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinAccount thirdWeixinAccount = (ThirdWeixinAccount) modelObj;
        if (thirdWeixinAccount.getDocCreateTime() == null) {
            thirdWeixinAccount.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinAccount);
    }
}
