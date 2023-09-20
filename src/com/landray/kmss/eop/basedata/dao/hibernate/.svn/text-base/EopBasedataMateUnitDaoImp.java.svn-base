package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataMateUnitDao;
import com.landray.kmss.eop.basedata.model.EopBasedataMateUnit;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:物料单位dao实现类
 * @date 2021/5/7
 */
public class EopBasedataMateUnitDaoImp extends BaseDaoImp implements IEopBasedataMateUnitDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataMateUnit eopBasedataMateUnit = (EopBasedataMateUnit) modelObj;
        if (eopBasedataMateUnit.getDocCreator() == null) {
            eopBasedataMateUnit.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataMateUnit.getDocCreateTime() == null) {
            eopBasedataMateUnit.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataMateUnit);
    }
}
