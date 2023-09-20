package com.landray.kmss.third.weixin.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.weixin.dao.IThirdWeixinDomainCheckDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinDomainCheck;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdWeixinDomainCheckDaoImp extends BaseDaoImp implements IThirdWeixinDomainCheckDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinDomainCheck thirdWeixinDomainCheck = (ThirdWeixinDomainCheck) modelObj;
        if (thirdWeixinDomainCheck.getDocCreator() == null) {
            thirdWeixinDomainCheck.setDocCreator(UserUtil.getUser());
        }
        if (thirdWeixinDomainCheck.getDocCreateTime() == null) {
            thirdWeixinDomainCheck.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinDomainCheck);
    }
}
