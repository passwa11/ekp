package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalPopCategoryDao;
import com.landray.kmss.sys.portal.model.SysPortalPopCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopCategoryDaoImp extends SysSimpleCategoryDaoImp implements ISysPortalPopCategoryDao  {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysPortalPopCategory sysPortalPopCategory = (SysPortalPopCategory) modelObj;
        if (sysPortalPopCategory.getDocCreator() == null) {
        	sysPortalPopCategory.setDocCreator(UserUtil.getUser());
        }
        if (sysPortalPopCategory.getDocCreateTime() == null) {
        	sysPortalPopCategory.setDocCreateTime(new Date());
        }
        return super.add(sysPortalPopCategory);
    }
	
}

