package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetItemService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataBudgetItemDataAction extends BaseAction {

    private IEopBasedataBudgetItemService eopBasedataBudgetItemService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataBudgetItemService == null) {
            eopBasedataBudgetItemService = (IEopBasedataBudgetItemService) getBean("eopBasedataBudgetItemService");
        }
        return eopBasedataBudgetItemService;
    }

    public ActionForward fdParent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdParent", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String type=request.getParameter("type");
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
                where += "(eopBasedataBudgetItem.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += " or eopBasedataBudgetItem.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdCompanyId=request.getParameter("fdCompanyId");
			if("budgeting".equals(type)){
				String where="eopBasedataBudgetItem.fdCode in (select t.fdBudgetItemList.fdCode from FsscBudgetingAuth t ";
				where+=" where t.fdIsAvailable=:fdIsAvailable and t.fdPersonList.fdId=:fdPersonId)";
				where+=" and eopBasedataBudgetItem.fdId not in (select distinct com.hbmParent.fdId from EopBasedataBudgetItem com where com.fdIsAvailable=:fdIsAvailable and com.hbmParent.fdId is not null)";
				if(StringUtil.isNotNull(fdCompanyId)) {
					where+=" and (company.fdId=:fdCompanyId or company.fdId is null) ";
					hqlInfo.setJoinBlock(" left join eopBasedataBudgetItem.fdCompanyList company ");
					hqlInfo.setParameter("fdCompanyId",fdCompanyId);
				}
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",where));
				hqlInfo.setParameter("fdIsAvailable", true);	
				hqlInfo.setParameter("fdPersonId", UserUtil.getUser().getFdId());	
			}else {
				if(StringUtil.isNotNull(fdCompanyId)){
					hqlInfo.setJoinBlock(" left join eopBasedataBudgetItem.fdCompanyList company");
					if(fdCompanyId.indexOf(";")>-1) {
						hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
								"("+HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or company is null)");
					}else {
						hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
								"(company.fdId=:fdCompanyId or company is null)"));
						hqlInfo.setParameter("fdCompanyId", fdCompanyId);
					}
				}
			}
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",valid);
			}else{
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			 String fdNotId=request.getParameter("fdNotId");
				if(StringUtil.isNotNull(fdNotId)){
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataBudgetItem.fdId<>:fdNotId "));
					hqlInfo.setParameter("fdNotId", request.getParameter("fdNotId"));
				}
				 String selectType=request.getParameter("selectType");
				if(StringUtil.isNotNull(selectType)&&"adjust".equals(selectType)){
					 String docTemplateName=request.getParameter("docTemplateName");
					if(StringUtil.isNotNull(docTemplateName)){
						if(docTemplateName.contains("费用类")){//费用类6602、6601
							String fdCodeSql=" (eopBasedataBudgetItem.fdCode like '%6602%' or eopBasedataBudgetItem.fdCode like '%6601%')";
							if(docTemplateName.contains("销售费用")){//管理费用6602、销售费用6601
								fdCodeSql=" eopBasedataBudgetItem.fdCode like '%6601%'";
							}else if(docTemplateName.contains("管理费用")){
								fdCodeSql=" eopBasedataBudgetItem.fdCode like '%6602%'";
							}
							hqlInfo.setWhereBlock(
									StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",fdCodeSql));
							hqlInfo.setWhereBlock(
									StringUtil.linkString(hqlInfo.getWhereBlock(), " and "," eopBasedataBudgetItem.fdCode like '%.%.%' "));//编号必须包含两个点。表示三级科目
							
						}else if(docTemplateName.contains("资产类")){//费用类P001
							hqlInfo.setWhereBlock(
									StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataBudgetItem.fdCode like '%P001%' "));
							hqlInfo.setWhereBlock(
									StringUtil.linkString(hqlInfo.getWhereBlock(), " and "," eopBasedataBudgetItem.fdCode like '%.%.%' "));//编号必须包含两个点。表示三级科目
						}
					}
					//只查询末级科目
					//1先查询所有作为父级的科目
					String parentIdSql="SELECT DISTINCT fd_parent_id FROM eop_basedata_budget_item WHERE fd_parent_id is not null ";
					List<String> list = getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(parentIdSql).list();
					String notIniD="";
					for (String parentId : list) {
						notIniD+="'"+parentId+"',";
					}
					if(notIniD.lastIndexOf(",")!=0){
						notIniD=notIniD.substring(0, notIniD.length()-1);
					}
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataBudgetItem.fdId not in (" +notIniD+")"));
				}
				
				
            hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataBudgetItem.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdParent", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdParent");
        }
    }
    public ActionForward fdBudgetItem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return fdParent(mapping, form, request, response);
    	
    }
}
