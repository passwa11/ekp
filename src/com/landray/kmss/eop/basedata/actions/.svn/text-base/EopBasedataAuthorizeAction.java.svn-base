package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataAuthorizeForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAuthorize;
import com.landray.kmss.eop.basedata.service.IEopBasedataAuthorizeService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EopBasedataAuthorizeAction extends EopBasedataBusinessAction {

    private IEopBasedataAuthorizeService eopBasedataAuthorizeService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAuthorizeService == null) {
            eopBasedataAuthorizeService = (IEopBasedataAuthorizeService) getBean("eopBasedataAuthorizeService");
        }
        return eopBasedataAuthorizeService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	if(!UserUtil.checkRole("ROLE_EOPBASEDATA_AUTHORIZE")){
    		//若是无批量授权权限，只能查看自己的授权
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataAuthorize.fdAuthorizedBy.fdId=:fdUserId");
    		hqlInfo.setParameter("fdUserId", UserUtil.getUser().getFdId());
    	}
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataAuthorize.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataAuthorize.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataAuthorizeForm eopBasedataAuthorizeForm = (EopBasedataAuthorizeForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataAuthorizeService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataAuthorizeForm;
    }
    
    /**
	 * 选择提单转授权人员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectAuthorize(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("selectAuthorize", true, getClass());
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
            Page page = new Page();
            page.setPageno(pageno);
            page.setRowsize(rowsize);
    		String hql = "select count(fdId) from SysOrgPerson p where ( p.fdId in(select ab.fdId from EopBasedataAuthorize au left join au.fdAuthorizedBy ab left join au.fdToOrg org where org.fdId=:userId and au.fdIsAvailable=:fdIsAvailable) or p.fdId=:userId)";
    		if(StringUtil.isNotNull(keyWord)){
    			hql += " and (p.fdName like :keyWord or p.fdLoginName like :keyWord or p.fdNo like :keyWord)";
    		}
    		Query query=getServiceImp(request).getBaseDao().getHibernateSession().createQuery(hql).setParameter("userId", UserUtil.getUser().getFdId()).setParameter("fdIsAvailable", Boolean.valueOf(true));
    		if(StringUtil.isNotNull(keyWord)){
    			query.setParameter("keyWord", "%"+keyWord+"%");
    		}
    		Number cnt = (Number) query.uniqueResult();
    		page.setTotalrows(cnt==null?0:cnt.intValue());
    		page.setTotal(cnt.intValue());
    		page.excecute();
    		hql = "select t from SysOrgPerson t where ( t.fdId in (select au.fdAuthorizedBy.fdId from EopBasedataAuthorize au left join au.fdToOrg org where org.fdId=:userId and au.fdIsAvailable=:fdIsAvailable) or t.fdId=:userId)";
     		if(StringUtil.isNotNull(keyWord)){
     			hql += " and (t.fdName like :keyWord or t.fdLoginName like :keyWord or t.fdNo like :keyWord)";
     		}
     		query=getServiceImp(request).getBaseDao().getHibernateSession().createQuery(hql).setParameter("userId", UserUtil.getUser().getFdId()).setParameter("fdIsAvailable", Boolean.valueOf(true));
     		if(StringUtil.isNotNull(keyWord)){
     			query.setParameter("keyWord", "%"+keyWord+"%");
     		}
     		query.setFirstResult(page.getStart());
    		query.setMaxResults(page.getRowsize());
    		page.setList(query.list());
            
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectAuthorize", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectAuthorize");
        }
	}
}
