package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataInnerOrderDao;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.util.UserUtil;

public class EopBasedataInnerOrderDaoImp extends BaseTreeDaoImp implements IEopBasedataInnerOrderDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataInnerOrder eopBasedataInnerOrder = (EopBasedataInnerOrder) modelObj;
        if (eopBasedataInnerOrder.getDocCreator() == null) {
            eopBasedataInnerOrder.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataInnerOrder.getDocCreateTime() == null) {
            eopBasedataInnerOrder.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataInnerOrder);
    }
}
