package com.landray.kmss.third.payment.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.payment.dao.IThirdPaymentCallLogDao;
import com.landray.kmss.third.payment.model.ThirdPaymentCallLog;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class ThirdPaymentCallLogDaoImp extends BaseDaoImp implements IThirdPaymentCallLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdPaymentCallLog thirdPaymentCallLog = (ThirdPaymentCallLog) modelObj;
        if (thirdPaymentCallLog.getDocCreator() == null) {
            thirdPaymentCallLog.setDocCreator(UserUtil.getUser());
        }
        if (thirdPaymentCallLog.getDocCreateTime() == null) {
            thirdPaymentCallLog.setDocCreateTime(new Date());
        }
        return super.add(thirdPaymentCallLog);
    }
}
