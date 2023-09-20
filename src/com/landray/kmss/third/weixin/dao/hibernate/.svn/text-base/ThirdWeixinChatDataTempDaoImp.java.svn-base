package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinChatDataTempDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataTemp;
import org.hibernate.query.Query;

public class ThirdWeixinChatDataTempDaoImp extends BaseDaoImp implements IThirdWeixinChatDataTempDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinChatDataTemp thirdWeixinChatDataTemp = (ThirdWeixinChatDataTemp) modelObj;
        if (thirdWeixinChatDataTemp.getDocCreateTime() == null) {
            thirdWeixinChatDataTemp.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinChatDataTemp);
    }

    @Override
    public void clear() throws Exception {
        Query query = super.getSession()
                .createQuery(
                        "delete from com.landray.kmss.third.weixin.model.ThirdWeixinChatDataTemp thirdWeixinChatDataTemp where thirdWeixinChatDataTemp.fdHandleStatus=3");
        query.executeUpdate();
    }
}
