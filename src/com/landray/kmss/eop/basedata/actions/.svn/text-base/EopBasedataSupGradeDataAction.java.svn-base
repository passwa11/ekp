package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataSupGrade;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupGradeService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangwh
 * @description:供应商等级数据action
 * @date 2021/5/7
 */
public class EopBasedataSupGradeDataAction extends BaseAction {

    private IEopBasedataSupGradeService eopBasedataSupGradeService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataSupGradeService == null) {
            eopBasedataSupGradeService = (IEopBasedataSupGradeService) getBean("eopBasedataSupGradeService");
        }
        return eopBasedataSupGradeService;
    }

    public ActionForward supplierGrade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("supplierGrade", true, getClass());
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
            if (StringUtil.isNotNull(keyWord)) {
                String where = "";
                where += "(eopBasedataSupGrade.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
			HQLHelper.by(request).where("fdStatus").eq(Integer.valueOf("0"))
					.buildHQLInfo(hqlInfo, EopBasedataSupGrade.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("supplierGrade", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("supplierGrade");
        }
    }
}
