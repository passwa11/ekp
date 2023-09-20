package com.landray.kmss.third.ding.notify.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.notify.dao.IThirdDingNotifyMessageDao;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage;

import java.util.Date;

public class ThirdDingNotifyMessageDaoImp extends BaseDaoImp implements IThirdDingNotifyMessageDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
		ThirdDingNotifyMessage thirdDingNotifyMessage = (ThirdDingNotifyMessage) modelObj;
		if (thirdDingNotifyMessage.getDocCreateTime() == null) {
			thirdDingNotifyMessage.setDocCreateTime(new Date());
        }
		return super.add(thirdDingNotifyMessage);
    }
}
