package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.service.IEopBasedataBerthService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataBerthDataAction extends BaseAction {

    private IEopBasedataBerthService eopBasedataBerthService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataBerthService == null) {
            eopBasedataBerthService = (IEopBasedataBerthService) getBean("eopBasedataBerthService");
        }
        return eopBasedataBerthService;
    }

    public ActionForward fdBerth(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdBerth", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String keyWord = request.getParameter("q._keyword");
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            String where = "1=1";
            if (StringUtil.isNotNull(keyWord)) {
                where += " and (eopBasedataBerth.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataBerth.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
            }
            String fdVehicleId = request.getParameter("fdVehicleId");
            if(StringUtil.isNotNull(fdVehicleId)){
	            	where+=" and eopBasedataBerth.fdVehicle.fdId=:fdVehicleId";
	            	hqlInfo.setParameter("fdVehicleId",fdVehicleId);
            }
            hqlInfo.setWhereBlock(where);
            hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataBerth.fdIsAvailable=:fdIsAvailable "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
			}else{
				hqlInfo.setParameter("fdIsAvailable",true);
			}
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
                hqlInfo.setJoinBlock(" left join eopBasedataBerth.fdCompanyList comp");
                if(fdCompanyId.indexOf(";")>-1) {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "("+ HQLUtil.buildLogicIN("comp.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or comp.fdId is null)");
                }else {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "(comp.fdId=:fdCompanyId or comp.fdId is null)"));
                    hqlInfo.setParameter("fdCompanyId", fdCompanyId);
                }
            }
            hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataBerth.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdBerth", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdBerth");
        }
    }
}
