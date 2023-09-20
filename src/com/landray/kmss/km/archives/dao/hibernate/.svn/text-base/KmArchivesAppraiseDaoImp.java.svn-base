package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesAppraiseDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesAppraise;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesAppraiseDaoImp extends BaseDaoImp implements IKmArchivesAppraiseDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesAppraise kmArchivesAppraise = (KmArchivesAppraise) modelObj;
        if (kmArchivesAppraise.getDocCreator() == null) {
            kmArchivesAppraise.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesAppraise.getDocCreateTime() == null) {
            kmArchivesAppraise.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesAppraise);
    }
}
