package com.landray.kmss.third.ding.notify.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.notify.dao.IThirdDingNotifyWorkrecordDao;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;

public class ThirdDingNotifyWorkrecordDaoImp extends BaseDaoImp implements IThirdDingNotifyWorkrecordDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
		ThirdDingNotifyWorkrecord thirdDingNotifyWorkrecord = (ThirdDingNotifyWorkrecord) modelObj;
		if (thirdDingNotifyWorkrecord.getDocCreateTime() == null) {
			thirdDingNotifyWorkrecord.setDocCreateTime(new Date());
        }
		return super.add(thirdDingNotifyWorkrecord);
    }
}
