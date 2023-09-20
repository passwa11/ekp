package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalPopTplDao;
import com.landray.kmss.sys.portal.model.SysPortalPopTpl;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopTplDaoImp extends BaseDaoImp implements ISysPortalPopTplDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysPortalPopTpl sysPortalPopTpl = (SysPortalPopTpl) modelObj;
        if (sysPortalPopTpl.getDocCreator() == null) {
            sysPortalPopTpl.setDocCreator(UserUtil.getUser());
        }
        if (sysPortalPopTpl.getDocCreateTime() == null) {
            sysPortalPopTpl.setDocCreateTime(new Date());
        }
        return super.add(sysPortalPopTpl);
    }
}