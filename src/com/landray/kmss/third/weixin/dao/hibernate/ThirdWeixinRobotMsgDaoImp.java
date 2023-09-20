package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinRobotMsgDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinRobotMsg;

public class ThirdWeixinRobotMsgDaoImp extends BaseDaoImp implements IThirdWeixinRobotMsgDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinRobotMsg thirdWeixinRobotMsg = (ThirdWeixinRobotMsg) modelObj;
        if (thirdWeixinRobotMsg.getDocCreateTime() == null) {
            thirdWeixinRobotMsg.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinRobotMsg);
    }
}
