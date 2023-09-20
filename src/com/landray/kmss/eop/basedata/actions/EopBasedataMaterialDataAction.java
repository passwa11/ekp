package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataMaterial;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialService;
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
 * @description:物料数据action
 * @date 2021/5/7
 */
public class EopBasedataMaterialDataAction extends BaseAction {

    private IEopBasedataMaterialService eopBasedataMaterialService;

    public IEopBasedataMaterialService getServiceImp(HttpServletRequest request) {
        if (eopBasedataMaterialService == null) {
            eopBasedataMaterialService = (IEopBasedataMaterialService) getBean("eopBasedataMaterialService");
        }
        return eopBasedataMaterialService;
    }

    /**
     * 物料选择框使用，如果存在eps/commom模块，则物料价格为eps/common模块的价格库价格（存在有效价格取最低价，否则取无效价格最低价，否则为null，取物料本身价格，否则为null）
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward materialList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("materialList", true, getClass());
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
                where += "(eopBasedataMaterial.fdName like :fdName or eopBasedataMaterial.fdCode like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            HQLHelper.by(request).where("fdStatus").eq(Integer.valueOf("0")).buildHQLInfo(hqlInfo, EopBasedataMaterial.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("materialList", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("materialList");
        }
    }
    
    public ActionForward selectMaterial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("selectMaterial", true, getClass());
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
                where += "(eopBasedataMaterial.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataMaterial.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdStatus = request.getParameter("fdStatus");
            hqlInfo.setWhereBlock(
                    StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataMaterial.fdStatus = :fdStatus "));
            if(StringUtil.isNotNull(fdStatus)) {
                hqlInfo.setParameter("fdStatus", Integer.valueOf(fdStatus));
            }else {
            	 hqlInfo.setParameter("fdStatus", Integer.valueOf("0"));
            }
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectMaterial", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectMaterial");
        }
    }
}
