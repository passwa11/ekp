package com.landray.kmss.sys.mportal.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.mportal.dao.ISysMportalModuleCateDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.mportal.model.SysMportalModuleCate;
import com.landray.kmss.common.dao.BaseDaoImp;

public class SysMportalModuleCateDaoImp extends BaseDaoImp implements ISysMportalModuleCateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysMportalModuleCate sysMportalModuleCate = (SysMportalModuleCate) modelObj;
        if (sysMportalModuleCate.getDocCreator() == null) {
            sysMportalModuleCate.setDocCreator(UserUtil.getUser());
        }
        if (sysMportalModuleCate.getDocCreateTime() == null) {
            sysMportalModuleCate.setDocCreateTime(new Date());
        }
        return super.add(sysMportalModuleCate);
    }
}
