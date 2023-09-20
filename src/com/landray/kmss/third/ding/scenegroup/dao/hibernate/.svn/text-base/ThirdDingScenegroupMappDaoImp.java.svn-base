package com.landray.kmss.third.ding.scenegroup.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.scenegroup.dao.IThirdDingScenegroupMappDao;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.util.UserUtil;

public class ThirdDingScenegroupMappDaoImp extends BaseDaoImp implements IThirdDingScenegroupMappDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingScenegroupMapp thirdDingScenegroupMapp = (ThirdDingScenegroupMapp) modelObj;
        if (thirdDingScenegroupMapp.getDocCreator() == null) {
            thirdDingScenegroupMapp.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingScenegroupMapp.getDocCreateTime() == null) {
            thirdDingScenegroupMapp.setDocCreateTime(new Date());
        }
		thirdDingScenegroupMapp.setDocAlterTime(new Date());
        return super.add(thirdDingScenegroupMapp);
    }
}
