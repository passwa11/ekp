package com.landray.kmss.sys.portal.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.portal.dao.ISysPortalMaterialMainDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.portal.model.SysPortalMaterialMain;
import com.landray.kmss.common.dao.BaseDaoImp;

public class SysPortalMaterialMainDaoImp extends BaseDaoImp implements ISysPortalMaterialMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysPortalMaterialMain sysPortalMaterialMain = (SysPortalMaterialMain) modelObj;
        if (sysPortalMaterialMain.getDocCreator() == null) {
            sysPortalMaterialMain.setDocCreator(UserUtil.getUser());
        }
        if (sysPortalMaterialMain.getDocCreateTime() == null) {
            sysPortalMaterialMain.setDocCreateTime(new Date());
        }
        return super.add(sysPortalMaterialMain);
    }
}
