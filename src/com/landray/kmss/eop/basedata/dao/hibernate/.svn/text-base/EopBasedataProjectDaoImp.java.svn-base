package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataProjectDao;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.util.UserUtil;

public class EopBasedataProjectDaoImp extends BaseTreeDaoImp implements IEopBasedataProjectDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataProject eopBasedataProject = (EopBasedataProject) modelObj;
        if (eopBasedataProject.getDocCreator() == null) {
            eopBasedataProject.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataProject.getDocCreateTime() == null) {
            eopBasedataProject.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataProject);
    }
}
