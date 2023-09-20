package com.landray.kmss.eop.basedata.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplierAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierAccountService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataSupplierAccountDataAction extends ExtendAction {

    private IEopBasedataSupplierAccountService eopBasedataSupplierAccountService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataSupplierAccountService == null) {
            eopBasedataSupplierAccountService = (IEopBasedataSupplierAccountService) getBean("eopBasedataSupplierAccountService");
        }
        return eopBasedataSupplierAccountService;
    }
    
    public ActionForward getSupplierAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getSupplierAccount", true, getClass());
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
                where += "(eopBasedataSupplierAccount.fdAccountName like :fdAccountName";
                hqlInfo.setParameter("fdAccountName", "%" + keyWord + "%");
                where += " or eopBasedataSupplierAccount.fdBankName like :fdBankName";
                hqlInfo.setParameter("fdBankName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }

            String fdSupplierId = request.getParameter("fdSupplierId");
            if(StringUtil.isNotNull(fdSupplierId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataSupplierAccount.docMain doc");
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        "doc.fdId=:fdSupplierId"));
                hqlInfo.setParameter("fdSupplierId", fdSupplierId);
            }
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataSupplierAccount.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getSupplierAccount", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getSupplierAccount");
        }
    }
    
    public ActionForward getSupplierCustomerAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getSupplierCustomerAccount", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            
            List<HQLParameter> params = new ArrayList<HQLParameter>();
            Page page = new Page();
			String countSql = splicingSql(request,true,params);
			Query query = getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(countSql);
			for(HQLParameter p:params){
				if(p.getValue() instanceof Collection){
					query.setParameterList(p.getName(), (Collection) p.getValue());
				}else{
					query.setParameter(p.getName(), p.getValue());
				}
			}
			Number cnt = (Number) query.uniqueResult();
			page.setRowsize(rowsize);
			page.setTotal(cnt.intValue());
			page.setTotalrows(cnt.intValue());
			page.setPageno(pageno);
			page.excecute();
			params.clear();
			String querySql = splicingSql(request,false,params);
			query = getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(querySql);
			for(HQLParameter p:params){
				if(p.getValue() instanceof Collection){
					query.setParameterList(p.getName(), (Collection) p.getValue());
				}else{
					query.setParameter(p.getName(), p.getValue());
				}
			}
			List<Map> list = query.setFirstResult(page.getStart()).setMaxResults(page.getRowsize()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			page.setList(list);
			request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getSupplierCustomerAccount", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getSupplierCustomerAccount");
        }
    }
    
    private String splicingSql(HttpServletRequest request, boolean isCount,List<HQLParameter> params) {
    	String keyWord = request.getParameter("q._keyword");
    	String fdSupplierId = request.getParameter("fdSupplierId");
    	StringBuilder sql = new StringBuilder("select");
    	if(isCount) {
    		sql.append(" count(1) ");
    	}else {
    		sql.append("  t.fd_id,t.fd_account_name,t.fd_bank_name,t.fd_bank_account,t.type");
    	}
    	sql.append(" from (");
    	sql.append(" select DISTINCT s.fd_id,s.fd_account_name,s.fd_bank_name,s.fd_bank_account,1 as type  from eop_basedata_supplier_account s");
    	if(StringUtil.isNotNull(fdSupplierId)){
        	sql.append("  where  s.doc_main_id =:fdSupplierId");
			HQLParameter param = new HQLParameter("fdSupplierId",fdSupplierId);
			params.add(param);
        }
		if (StringUtil.isNotNull(keyWord)) {
			sql.append(" and (s.fd_account_name like :keyWord");
			HQLParameter name = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(name);
			
			sql.append(" or s.fd_bank_name like :keyWord");
			HQLParameter code = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(code);
		}
        
        sql.append(" union all ");
        
        sql.append(" select DISTINCT ct.fd_id,ct.fd_account_name,ct.fd_bank_name,ct.fd_bank_account,2 as type  from eop_basedata_customer_account ct");
        if(StringUtil.isNotNull(fdSupplierId)){
        	sql.append("  where  ct.doc_main_id =:fdSupplierId");
			HQLParameter param = new HQLParameter("fdSupplierId",fdSupplierId);
			params.add(param);
        }
		if (StringUtil.isNotNull(keyWord)) {
			sql.append(" and (ct.fd_account_name like  :keyWord");
			HQLParameter name = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(name);
			
			sql.append(" or ct.fd_bank_name like :keyWord ");
			HQLParameter code = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(code);
		}
		sql.append(" ) t");
    	return sql.toString();
    }
}
