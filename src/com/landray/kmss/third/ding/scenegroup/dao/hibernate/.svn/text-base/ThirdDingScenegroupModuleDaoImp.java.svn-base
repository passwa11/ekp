package com.landray.kmss.third.ding.scenegroup.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.scenegroup.dao.IThirdDingScenegroupModuleDao;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupModule;
import com.landray.kmss.util.UserUtil;

public class ThirdDingScenegroupModuleDaoImp extends BaseDaoImp implements IThirdDingScenegroupModuleDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingScenegroupModule thirdDingScenegroupModule = (ThirdDingScenegroupModule) modelObj;
        if (thirdDingScenegroupModule.getDocCreator() == null) {
            thirdDingScenegroupModule.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingScenegroupModule.getDocCreateTime() == null) {
            thirdDingScenegroupModule.setDocCreateTime(new Date());
        }
        return super.add(thirdDingScenegroupModule);
    }
}
