package com.landray.kmss.sys.evaluation.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.evaluation.dao.ISysEvaluationShareDao;
import com.landray.kmss.sys.evaluation.model.SysEvaluationShare;
import com.landray.kmss.util.UserUtil;

public class SysEvaluationShareDaoImp extends BaseDaoImp implements ISysEvaluationShareDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysEvaluationShare sysEvaluationShare = (SysEvaluationShare) modelObj;
        if (sysEvaluationShare.getDocCreator() == null) {
            sysEvaluationShare.setDocCreator(UserUtil.getUser());
        }
		sysEvaluationShare.setFdShareTime(new Date());
        return super.add(sysEvaluationShare);
    }
}
