package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalPopMainDao;
import com.landray.kmss.sys.portal.model.SysPortalPopMain;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopMainDaoImp extends BaseDaoImp implements ISysPortalPopMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysPortalPopMain sysPortalPopMain = (SysPortalPopMain) modelObj;
        if (sysPortalPopMain.getDocCreator() == null) {
            sysPortalPopMain.setDocCreator(UserUtil.getUser());
        }
        if (sysPortalPopMain.getDocCreateTime() == null) {
            sysPortalPopMain.setDocCreateTime(new Date());
        }
        return super.add(sysPortalPopMain);
    }
}
