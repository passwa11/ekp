package com.landray.kmss.sys.mportal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.sys.mportal.dao.ISysMportalCpageDao;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.util.UserUtil;

public class SysMportalCpageDaoImp extends ExtendDataDaoImp implements ISysMportalCpageDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysMportalCpage sysMportalCpage = (SysMportalCpage) modelObj;
        if (sysMportalCpage.getDocCreator() == null) {
            sysMportalCpage.setDocCreator(UserUtil.getUser());
        }
        if (sysMportalCpage.getDocCreateTime() == null) {
            sysMportalCpage.setDocCreateTime(new Date());
        }
        return super.add(sysMportalCpage);
    }
}
