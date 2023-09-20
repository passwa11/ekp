package com.landray.kmss.third.payment.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.payment.dao.IThirdPaymentOrderDao;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class ThirdPaymentOrderDaoImp extends BaseDaoImp implements IThirdPaymentOrderDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdPaymentOrder thirdPaymentOrder = (ThirdPaymentOrder) modelObj;
        if (thirdPaymentOrder.getDocCreator() == null) {
            thirdPaymentOrder.setDocCreator(UserUtil.getUser());
        }
        if (thirdPaymentOrder.getDocCreateTime() == null) {
            thirdPaymentOrder.setDocCreateTime(new Date());
        }
        return super.add(thirdPaymentOrder);
    }
}
