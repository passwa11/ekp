package com.landray.kmss.hr.organization.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hr.organization.dao.IHrOrganizationRankDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.common.dao.BaseDaoImp;

public class HrOrganizationRankDaoImp extends BaseDaoImp implements IHrOrganizationRankDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrOrganizationRank hrOrganizationRank = (HrOrganizationRank) modelObj;
        if (hrOrganizationRank.getDocCreator() == null) {
            hrOrganizationRank.setDocCreator(UserUtil.getUser());
        }
        if (hrOrganizationRank.getDocCreateTime() == null) {
            hrOrganizationRank.setDocCreateTime(new Date());
        }
        return super.add(hrOrganizationRank);
    }
}
