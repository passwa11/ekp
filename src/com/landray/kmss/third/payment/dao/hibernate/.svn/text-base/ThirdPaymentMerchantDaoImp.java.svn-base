package com.landray.kmss.third.payment.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.payment.dao.IThirdPaymentMerchantDao;
import com.landray.kmss.third.payment.model.ThirdPaymentMerchant;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class ThirdPaymentMerchantDaoImp extends BaseDaoImp implements IThirdPaymentMerchantDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdPaymentMerchant thirdPaymentMerchant = (ThirdPaymentMerchant) modelObj;
        if (thirdPaymentMerchant.getDocCreator() == null) {
            thirdPaymentMerchant.setDocCreator(UserUtil.getUser());
        }
        if (thirdPaymentMerchant.getDocCreateTime() == null) {
            thirdPaymentMerchant.setDocCreateTime(new Date());
        }
        return super.add(thirdPaymentMerchant);
    }
}
