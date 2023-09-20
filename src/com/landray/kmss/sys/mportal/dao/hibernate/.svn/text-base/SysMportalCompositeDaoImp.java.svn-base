package com.landray.kmss.sys.mportal.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.sys.mportal.dao.ISysMportalCompositeDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.mportal.model.SysMportalComposite;

public class SysMportalCompositeDaoImp extends ExtendDataDaoImp implements ISysMportalCompositeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysMportalComposite sysMportalComposite = (SysMportalComposite) modelObj;
        if (sysMportalComposite.getDocCreator() == null) {
            sysMportalComposite.setDocCreator(UserUtil.getUser());
        }
        if (sysMportalComposite.getDocCreateTime() == null) {
            sysMportalComposite.setDocCreateTime(new Date());
        }
        return super.add(sysMportalComposite);
    }
}
