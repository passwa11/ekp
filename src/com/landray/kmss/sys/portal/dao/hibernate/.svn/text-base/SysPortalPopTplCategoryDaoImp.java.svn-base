package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalPopTplCategoryDao;
import com.landray.kmss.sys.portal.model.SysPortalPopTplCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopTplCategoryDaoImp extends SysSimpleCategoryDaoImp implements ISysPortalPopTplCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysPortalPopTplCategory sysPortalPopTplCategory = (SysPortalPopTplCategory) modelObj;
        if (sysPortalPopTplCategory.getDocCreator() == null) {
            sysPortalPopTplCategory.setDocCreator(UserUtil.getUser());
        }
        if (sysPortalPopTplCategory.getDocCreateTime() == null) {
            sysPortalPopTplCategory.setDocCreateTime(new Date());
        }
        return super.add(sysPortalPopTplCategory);
    }
}
