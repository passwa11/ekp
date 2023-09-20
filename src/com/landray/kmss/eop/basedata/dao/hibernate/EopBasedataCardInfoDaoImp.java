package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCardInfoDao;
import com.landray.kmss.eop.basedata.model.EopBasedataCardInfo;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class EopBasedataCardInfoDaoImp extends BaseDaoImp implements IEopBasedataCardInfoDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCardInfo eopBasedataCardInfo = (EopBasedataCardInfo) modelObj;
        if (eopBasedataCardInfo.getDocCreator() == null) {
            eopBasedataCardInfo.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCardInfo.getDocCreateTime() == null) {
            eopBasedataCardInfo.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCardInfo);
    }
}
