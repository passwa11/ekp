package com.landray.kmss.sys.attachment.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.dao.ISysAttMainBakDao;
import com.landray.kmss.sys.attachment.model.SysAttMainBak;

import java.util.Date;

public class SysAttMainBakDaoImp extends BaseDaoImp implements ISysAttMainBakDao {
    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttMainBak model = (SysAttMainBak) modelObj;
        if (model.getFdBakCreateTime() == null) {
            model.setFdBakCreateTime(new Date());
        }
        if (model.getFdMovingStatus() != 0) {
            model.setFdMovingStatus(0);
        }
        return super.add(modelObj);
    }
}
