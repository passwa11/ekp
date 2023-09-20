package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wangwh
 * @description:供应商数据action
 * @date 2021/5/7
 */
public class EopBasedataSupplierDataAction extends BaseAction {

    private IEopBasedataSupplierService eopBasedataSupplierService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataSupplierService == null) {
            eopBasedataSupplierService = (IEopBasedataSupplierService) getBean("eopBasedataSupplierService");
        }
        return eopBasedataSupplierService;
    }

    public ActionForward getSupplier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getSupplier", true, getClass());
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
            String where = "";
            if (StringUtil.isNotNull(keyWord)) {
                
                where += "(eopBasedataSupplier.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataSupplier.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += " or eopBasedataSupplier.fdTaxNo like :fdTaxNo";
                hqlInfo.setParameter("fdTaxNo", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataSupplier.fdCompanyList company");
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        "(company.fdId=:fdCompanyId or company is null)"));
                hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
        			"eopBasedataSupplier.fdIsAvailable=:fdIsAvailable"));
            String fdIsAvailable = request.getParameter("fdIsAvailable");
            if(StringUtil.isNotNull(fdIsAvailable)){
            	hqlInfo.setParameter("fdIsAvailable", fdIsAvailable);
            }else{
            	hqlInfo.setParameter("fdIsAvailable", true);
            }
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getSupplier", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getSupplier");
        }
    }
    
    /**
     * 查找供应商和客户
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getSupplierAndCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getSupplierAndCustomer", true, getClass());
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
            //兼容sqlserver第2页以后数据空白的问题
			String driveName = EopBasedataFsscUtil.getDataBaseType();
            if ("sqlserver".equals(driveName)&&pageno>1) {
                List<Map> list_=new ArrayList<Map>();
                for(Map map:list){
                    map.put("FD_ID",map.get("page0_"));
                    map.put("FD_NAME",map.get("page1_"));
                    map.put("FD_CODE",map.get("page2_"));
                    map.put("FD_IS_AVAILABLE",map.get("page3_"));
                    map.put("FD_TAX_NO",map.get("page4_"));
                    map.put("FD_ERP_NO",map.get("page5_"));
                    map.put("DOC_CREATOR_ID",map.get("page6_"));
                    map.put("DOC_CREATE_TIME",map.get("page7_"));
                    map.put("TYPE",map.get("page8_"));
                    list_.add(map);
                }
                list=list_;
            }

			page.setList(list);
			request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getSupplierAndCustomer", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getSupplierCustomer");
        }
    }
    
    private String splicingSql(HttpServletRequest request, boolean isCount,List<HQLParameter> params) {
    	String keyWord = request.getParameter("q._keyword");
    	String fdCompanyId = request.getParameter("fdCompanyId");
        String fdIsAvailable = request.getParameter("fdIsAvailable");
    	StringBuilder sql = new StringBuilder("select");
    	if(isCount) {
    		sql.append(" count(1) ");
    	}else {
    		sql.append("  T.FD_ID,T.FD_NAME,T.FD_CODE,T.FD_IS_AVAILABLE,T.FD_TAX_NO,T.FD_ERP_NO,T.DOC_CREATOR_ID,T.DOC_CREATE_TIME,T.TYPE");
    	}
    	sql.append(" from (");
    	sql.append(" select DISTINCT s.fd_id,s.fd_name,s.fd_code,s.fd_is_available,s.fd_tax_no,s.fd_erp_no,s.doc_creator_id,s.doc_create_time,1 as type  from eop_basedata_supplier s");
    	sql.append(" left join  eop_basedata_supplier_com sc  on s.fd_id = sc.fd_source_id");
    	sql.append(" left join eop_basedata_company c  on c.fd_id = sc.fd_target_id");
        if(StringUtil.isNotNull(fdIsAvailable)){
        	sql.append("  where  s.fd_is_available =:fdIsAvailable");
			HQLParameter param = new HQLParameter("fdIsAvailable",fdIsAvailable);
			params.add(param);
        }else{
       	 	sql.append(" where  s.fd_is_available =:fdIsAvailable");
       	 	HQLParameter param = new HQLParameter("fdIsAvailable",true);
			params.add(param);
        }
    	
        if(StringUtil.isNotNull(fdCompanyId)){
        	sql.append(" and (c.fd_id =:fdCompanyId  or c.fd_id is null)");
        	HQLParameter param = new HQLParameter("fdCompanyId",fdCompanyId);
			params.add(param);
        }
        
		if (StringUtil.isNotNull(keyWord)) {
			sql.append(" and (s.fd_name like :keyWord");
			HQLParameter name = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(name);
			
			sql.append(" or s.fd_code like :keyWord");
			HQLParameter code = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(code);
			
			sql.append(" or s.fd_tax_no like :keyWord)");
			HQLParameter taxNo = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(taxNo);
		}
        
        sql.append(" union all ");
        
        sql.append(" select DISTINCT ct.fd_id,ct.fd_name,ct.fd_code,ct.fd_is_available,ct.fd_tax_no,ct.fd_erp_no,ct.doc_creator_id,ct.doc_create_time,2 as type  from eop_basedata_customer ct");
        sql.append(" left join  eop_basedata_customer_com cc  on ct.fd_id = cc.fd_source_id");
    	sql.append(" left join eop_basedata_company c  on c.fd_id = cc.fd_target_id");
        
        if(StringUtil.isNotNull(fdIsAvailable)){
        	sql.append("  where  ct.fd_is_available =:fdIsAvailable");
			HQLParameter param = new HQLParameter("fdIsAvailable",fdIsAvailable);
			params.add(param);
        }else{
       	 	sql.append(" where  ct.fd_is_available =:fdIsAvailable");
       	 	HQLParameter param = new HQLParameter("fdIsAvailable",true);
			params.add(param);
        }
    	
        if(StringUtil.isNotNull(fdCompanyId)){
        	sql.append(" and (c.fd_id =:fdCompanyId  or c.fd_id is null)");
        	HQLParameter param = new HQLParameter("fdCompanyId",fdCompanyId);
			params.add(param);
        }
        
		if (StringUtil.isNotNull(keyWord)) {
			sql.append(" and (ct.fd_name like  :keyWord");
			HQLParameter name = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(name);
			
			sql.append(" or ct.fd_code like :keyWord ");
			HQLParameter code = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(code);
			
			sql.append(" or ct.fd_tax_no like :keyWord)");
			HQLParameter taxNo = new HQLParameter("keyWord","%"+keyWord+"%");
			params.add(taxNo);
		}
		sql.append(" ) t");
    	return sql.toString();
    }
}
