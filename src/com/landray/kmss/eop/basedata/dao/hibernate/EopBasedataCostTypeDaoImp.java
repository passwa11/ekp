package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCostTypeDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataCostType;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataCostTypeDaoImp extends BaseDaoImp implements IEopBasedataCostTypeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCostType eopBasedataCostType = (EopBasedataCostType) modelObj;
        if (eopBasedataCostType.getDocCreator() == null) {
            eopBasedataCostType.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCostType.getDocCreateTime() == null) {
            eopBasedataCostType.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCostType);
    }
}
