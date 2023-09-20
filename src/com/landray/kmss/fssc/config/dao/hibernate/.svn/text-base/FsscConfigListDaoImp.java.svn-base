package com.landray.kmss.fssc.config.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.config.dao.IFsscConfigListDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.config.model.FsscConfigList;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
  * 物资清单 Dao层实现
  */
public class FsscConfigListDaoImp extends BaseDaoImp implements IFsscConfigListDao {

    public String add(IBaseModel modelObj) throws Exception {
        FsscConfigList fsscConfigList = (FsscConfigList) modelObj;
        if (fsscConfigList.getDocCreator() == null) {
            fsscConfigList.setDocCreator(UserUtil.getUser());
        }
        if (fsscConfigList.getDocCreateTime() == null) {
            fsscConfigList.setDocCreateTime(new Date());
        }
        return super.add(fsscConfigList);
    }
}
