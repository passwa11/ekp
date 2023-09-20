package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataVoucherTypeDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataVoucherTypeDaoImp extends BaseDaoImp implements IEopBasedataVoucherTypeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataVoucherType eopBasedataVoucherType = (EopBasedataVoucherType) modelObj;
        if (eopBasedataVoucherType.getDocCreator() == null) {
            eopBasedataVoucherType.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataVoucherType.getDocCreateTime() == null) {
            eopBasedataVoucherType.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataVoucherType);
    }
}
