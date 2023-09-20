package com.landray.kmss.third.weixin.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.weixin.dao.IThirdWeixinPayOrderDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdWeixinPayOrderDaoImp extends BaseDaoImp implements IThirdWeixinPayOrderDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinPayOrder thirdWeixinPayOrder = (ThirdWeixinPayOrder) modelObj;
        if (thirdWeixinPayOrder.getDocCreator() == null) {
            thirdWeixinPayOrder.setDocCreator(UserUtil.getUser());
        }
        if (thirdWeixinPayOrder.getDocCreateTime() == null) {
            thirdWeixinPayOrder.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinPayOrder);
    }
}
