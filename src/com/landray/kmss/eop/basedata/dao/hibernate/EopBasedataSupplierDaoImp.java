package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataSupplierDao;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:供应商dao实现类
 * @date 2021/5/7
 */
public class EopBasedataSupplierDaoImp extends BaseDaoImp implements IEopBasedataSupplierDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) modelObj;
        if (eopBasedataSupplier.getDocCreator() == null) {
            eopBasedataSupplier.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataSupplier.getDocCreateTime() == null) {
            eopBasedataSupplier.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataSupplier);
    }
}
