package com.landray.kmss.third.ding.scenegroup.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.scenegroup.dao.IThirdDingRobotDao;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobot;
import com.landray.kmss.util.UserUtil;

public class ThirdDingRobotDaoImp extends BaseDaoImp implements IThirdDingRobotDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingRobot thirdDingRobot = (ThirdDingRobot) modelObj;
        if (thirdDingRobot.getDocCreator() == null) {
            thirdDingRobot.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingRobot.getDocCreateTime() == null) {
            thirdDingRobot.setDocCreateTime(new Date());
        }
        return super.add(thirdDingRobot);
    }
}
