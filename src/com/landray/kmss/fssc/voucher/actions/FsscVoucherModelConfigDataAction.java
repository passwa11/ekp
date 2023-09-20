package com.landray.kmss.fssc.voucher.actions;

import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.util.*;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.sunbor.web.tag.Page;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherModelConfigService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.actions.BaseAction;
import org.hibernate.query.Query;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import java.util.regex.Pattern;

public class FsscVoucherModelConfigDataAction extends BaseAction {

    private IFsscVoucherModelConfigService fsscVoucherModelConfigService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscVoucherModelConfigService == null) {
            fsscVoucherModelConfigService = (IFsscVoucherModelConfigService) getBean("fsscVoucherModelConfigService");
        }
        return fsscVoucherModelConfigService;
    }

    public ActionForward fdModel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdModel", true, getClass());
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
                where += "(fsscVoucherModelConfig.fdModelName like :fdModelName";
                hqlInfo.setParameter("fdModelName", "%" + keyWord + "%");
                where += " or fsscVoucherModelConfig.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscVoucherModelConfig.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdModel", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdModel");
        }
    }

    public ActionForward fdCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdCategory", true, getClass());
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
            Page page = null;
            String fdCategoryModelName = request.getParameter("fdCategoryModelName");
            if(StringUtil.isNotNull(fdCategoryModelName)){
                page = new Page();
                page.setRowsize(rowsize);
                page.setPageno(pageno);
                Query totalQ = getServiceImp(request).getBaseDao().getHibernateSession().createQuery("select fdId from "+fdCategoryModelName);
                if(!ArrayUtil.isEmpty(totalQ.list())){
                    rowsize = totalQ.list().size();
                }
                String hql=" from "+fdCategoryModelName+" as main";
                if(StringUtil.isNotNull(keyWord)){
                hql+= " where main.fdName like :fdName";
                }
                Query q = getServiceImp(request).getBaseDao().getHibernateSession().createQuery(hql);
                if(StringUtil.isNotNull(keyWord)){
                q.setParameter("fdName", "%"+keyWord+"%");
                }
                page.setTotalrows(q.list().size());
                page.excecute();
                q.setFirstResult(page.getStart());
                q.setMaxResults(page.getRowsize());
                page.setList(q.list());
            }
            if (page == null) {
                page = Page.getEmptyPage();
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdCategory", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdCategory");
        }
    }
}
