package com.landray.kmss.sys.portal.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.portal.dao.ISysPortalMapTplDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.portal.model.SysPortalMapTpl;
import com.landray.kmss.common.dao.BaseDaoImp;

public class SysPortalMapTplDaoImp extends BaseDaoImp implements ISysPortalMapTplDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysPortalMapTpl sysPortalMapTpl = (SysPortalMapTpl) modelObj;
        if (sysPortalMapTpl.getDocCreator() == null) {
            sysPortalMapTpl.setDocCreator(UserUtil.getUser());
        }
        if (sysPortalMapTpl.getDocCreateTime() == null) {
            sysPortalMapTpl.setDocCreateTime(new Date());
        }
        return super.add(sysPortalMapTpl);
    }
}
