package com.landray.kmss.fssc.config.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.config.dao.IFsscConfigScoreDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.config.model.FsscConfigScore;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
  * 点赞积分配置 Dao层实现
  */
public class FsscConfigScoreDaoImp extends BaseDaoImp implements IFsscConfigScoreDao {

    public String add(IBaseModel modelObj) throws Exception {
        FsscConfigScore fsscConfigScore = (FsscConfigScore) modelObj;
        if (fsscConfigScore.getDocCreator() == null) {
            fsscConfigScore.setDocCreator(UserUtil.getUser());
        }
        if (fsscConfigScore.getDocCreateTime() == null) {
            fsscConfigScore.setDocCreateTime(new Date());
        }
        return super.add(fsscConfigScore);
    }
}
