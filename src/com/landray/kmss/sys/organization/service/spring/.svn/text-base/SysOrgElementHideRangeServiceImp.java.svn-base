package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.service.ISysOrgElementHideRangeService;

import java.util.List;

public class SysOrgElementHideRangeServiceImp extends BaseServiceImp
        implements ISysOrgElementHideRangeService, SysOrgConstant {

    @Override
    public List getHideRangeIds() throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysOrgElementHideRange.fdIsOpenLimit = :fdIsOpenLimit and sysOrgElementHideRange.fdElement is not null");
        hqlInfo.setParameter("fdIsOpenLimit", Boolean.TRUE);
        return findList(hqlInfo);
    }
}
