package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendSignBak;
import com.landray.kmss.sys.attend.service.ISysAttendSignBakService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class SysAttendSignBakAction extends ExtendAction {

    private ISysAttendSignBakService sysAttendSignBakService;

    @Override
    public ISysAttendSignBakService getServiceImp(HttpServletRequest request) {
        if (sysAttendSignBakService == null) {
            sysAttendSignBakService = (ISysAttendSignBakService) getBean("sysAttendSignBakService");
        }
        return sysAttendSignBakService;
    }
    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        CriteriaValue cv = new CriteriaValue(request);
        CriteriaUtil.buildHql(cv, hqlInfo, SysAttendSignBak.class);
        String fdOperatorId = request.getParameter("q.fdOperatorId");
        if (StringUtil.isNotNull(fdOperatorId)) {
            String whereBlock = hqlInfo.getWhereBlock();
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysAttendSignBak.docCreator.fdId=:fdOperatorId2");
            hqlInfo.setParameter("fdOperatorId2", fdOperatorId);
            hqlInfo.setWhereBlock(whereBlock);
        }
    }
}
