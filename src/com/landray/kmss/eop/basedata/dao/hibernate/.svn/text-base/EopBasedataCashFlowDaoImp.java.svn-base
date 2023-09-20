package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCashFlowDao;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCashFlowDaoImp extends BaseTreeDaoImp implements IEopBasedataCashFlowDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCashFlow eopBasedataCashFlow = (EopBasedataCashFlow) modelObj;
        if (eopBasedataCashFlow.getDocCreator() == null) {
            eopBasedataCashFlow.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCashFlow.getDocCreateTime() == null) {
            eopBasedataCashFlow.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCashFlow);
    }
}
