package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCostCenterDao;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCostCenterDaoImp extends BaseTreeDaoImp implements IEopBasedataCostCenterDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCostCenter eopBasedataCostCenter = (EopBasedataCostCenter) modelObj;
        if (eopBasedataCostCenter.getDocCreator() == null) {
            eopBasedataCostCenter.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCostCenter.getDocCreateTime() == null) {
            eopBasedataCostCenter.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCostCenter);
    }
}
