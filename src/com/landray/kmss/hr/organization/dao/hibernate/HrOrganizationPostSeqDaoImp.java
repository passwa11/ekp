package com.landray.kmss.hr.organization.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.dao.IHrOrganizationPostSeqDao;
import com.landray.kmss.hr.organization.model.HrOrganizationPostSeq;
import com.landray.kmss.util.UserUtil;

public class HrOrganizationPostSeqDaoImp extends BaseDaoImp implements IHrOrganizationPostSeqDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrOrganizationPostSeq hrOrganizationPost1Seq = (HrOrganizationPostSeq) modelObj;
        if (hrOrganizationPost1Seq.getDocCreator() == null) {
            hrOrganizationPost1Seq.setDocCreator(UserUtil.getUser());
        }
        if (hrOrganizationPost1Seq.getDocCreateTime() == null) {
            hrOrganizationPost1Seq.setDocCreateTime(new Date());
        }
        return super.add(hrOrganizationPost1Seq);
    }
}
