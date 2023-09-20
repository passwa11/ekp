package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataMaterialDao;
import com.landray.kmss.eop.basedata.model.EopBasedataMaterial;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:物料dao实现类
 * @date 2021/5/7
 */
public class EopBasedataMaterialDaoImp extends BaseDaoImp implements IEopBasedataMaterialDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataMaterial eopBasedataMaterial = (EopBasedataMaterial) modelObj;
        if (eopBasedataMaterial.getDocCreator() == null) {
            eopBasedataMaterial.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataMaterial.getDocCreateTime() == null) {
            eopBasedataMaterial.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataMaterial);
    }
}
