package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataReceiverTypeDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataReceiverType;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataReceiverTypeDaoImp extends BaseDaoImp implements IEopBasedataReceiverTypeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataReceiverType eopBasedataReceiverType = (EopBasedataReceiverType) modelObj;
        if (eopBasedataReceiverType.getDocCreator() == null) {
            eopBasedataReceiverType.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataReceiverType.getDocCreateTime() == null) {
            eopBasedataReceiverType.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataReceiverType);
    }
}
