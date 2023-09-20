package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataExpenseItemService;
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

import edu.emory.mathcs.backport.java.util.Arrays;

public class EopBasedataExpenseItemDataAction extends BaseAction {

    private IEopBasedataExpenseItemService eopBasedataExpenseItemService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataExpenseItemService == null) {
            eopBasedataExpenseItemService = (IEopBasedataExpenseItemService) getBean("eopBasedataExpenseItemService");
        }
        return eopBasedataExpenseItemService;
    }

    public ActionForward fdParent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdParent", true, getClass());
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
                where += "(eopBasedataExpenseItem.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataExpenseItem.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            
            String initIds = request.getParameter("initIds");
            if(StringUtil.isNotNull(initIds)) {//如果是查询已经选择的数据，则直接使用in条件查询
            	hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("eopBasedataExpenseItem.fdId", Arrays.asList(initIds.split(";"))));
            	List<EopBasedataExpenseItem> list = getServiceImp(request).findList(hqlInfo);
            	Page page = new Page();
            	page.setList(list);
            	page.setTotalrows(list.size());
            	request.setAttribute("queryPage", page);
            	return mapping.findForward("fdParent");
            }
            
            String fdCompanyId = request.getParameter("fdCompanyId");
            String multi=request.getParameter("multi");
			if(StringUtil.isNotNull(fdCompanyId)){ 
				//公司多选
				if("true".equals(multi)) {
					hqlInfo.setJoinBlock("left join eopBasedataExpenseItem.fdCompanyList company");
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))) +" or company is null)")));
				}else {
					//公司单选
					hqlInfo.setJoinBlock("left join eopBasedataExpenseItem.fdCompanyList company");
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(company.fdId=:fdCompanyId or company.fdId is null)"));
					hqlInfo.setParameter("fdCompanyId", request.getParameter("fdCompanyId"));
				}
			}
            
			String type=request.getParameter("type");
			if(!"all".equals(type)){
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataExpenseItem.fdId<>:fdNotId "));
				hqlInfo.setParameter("fdNotId", request.getParameter("fdNotId"));
			}
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataExpenseItem.fdIsAvailable=:fdIsAvailable "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
			}else{
				hqlInfo.setParameter("fdIsAvailable",true);
			}
			//如果是事前选择费用类型，需要根据事前模板中的配置做相应筛选
			String fdTemplateId = request.getParameter("fdTemplateId");
			if(StringUtil.isNotNull(fdTemplateId)){
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem fsscFeeExpenseItem left join fsscFeeExpenseItem.fdItemList item";
				where += " where  fsscFeeExpenseItem.fdTemplate.fdId=:fdTemplateId";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscFeeExpenseItem.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscFeeExpenseItem.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdTemplateId",fdTemplateId);
			}
			//如果是报销选择费用类型，需要根据报销模板中的配置做相应筛选
			String fdCategoryId = request.getParameter("fdCategoryId");
			if(StringUtil.isNotNull(fdCategoryId)){
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig fsscExpenseItemConfig left join fsscExpenseItemConfig.fdItemList item";
				where += " where  fsscExpenseItemConfig.fdCategory.fdId=:fdCategoryId";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscExpenseItemConfig.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscExpenseItemConfig.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdCategoryId",fdCategoryId);
			}
			//如果是付款选择费用类型，需要根据付款类型中的配置做相应筛选
			String fdPaymentTemplateId = request.getParameter("fdPaymentTemplateId");
			if(StringUtil.isNotNull(fdPaymentTemplateId)){
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.payment.model.FsscPaymentItemConfig fsscPaymentItemConfig left join fsscPaymentItemConfig.fdItems item";
				where += " where fsscPaymentItemConfig.docCategory.fdId=:fdCategoryId";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscPaymentItemConfig.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscPaymentItemConfig.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdCategoryId",fdPaymentTemplateId);
			}
			//如果是需求选择费用类型，需要根据需求分类中的配置做相应筛选
			String fdDemandTemplateId = request.getParameter("fdDemandTemplateId");
			if(StringUtil.isNotNull(fdDemandTemplateId)){
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.purch.model.FsscPurchDemandItemConfig fsscPurchDemandItemConfig left join fsscPurchDemandItemConfig.fdItems item";
				where += " where  fsscPurchDemandItemConfig.docCategory.fdId=:fdCategoryId ";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscPurchDemandItemConfig.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscPurchDemandItemConfig.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdCategoryId",fdDemandTemplateId);
			}
			
			//如果是项目立项选择费用类型，需要根据项目立项分类中的配置做相应筛选
			String fdProappTemplateId = request.getParameter("fdProappTemplateId");
			if(StringUtil.isNotNull(fdProappTemplateId)){
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.proapp.model.FsscProappProjectItem fsscProappProjectItem left join fsscProappProjectItem.fdItems item";
				where += " where fsscProappProjectItem.docCategory.fdId=:fdCategoryId";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscProappProjectItem.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscProappProjectItem.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdCategoryId",fdProappTemplateId);
			}
			//如果是领用选择费用类型，需要根据领用分类中的配置做相应筛选
			String fdUseTemplateId = request.getParameter("fdUseTemplateId");
			if(StringUtil.isNotNull(fdUseTemplateId)){
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.purch.model.FsscPurchUseItemConfig fsscPurchUseItemConfig left join fsscPurchUseItemConfig.fdItems item";
				where += " where  fsscPurchUseItemConfig.docCategory.fdId=:fdCategoryId";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscPurchUseItemConfig.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscPurchUseItemConfig.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdCategoryId",fdUseTemplateId);
				String fdDemandId=request.getParameter("fdDemandId");
				if(StringUtil.isNotNull(fdDemandId)){
					 where = "eopBasedataExpenseItem.fdId in(select detail.fdExpenseItem.fdId from com.landray.kmss.fssc.purch.model.FsscPurchDemandDetail detail";
						where += " where detail.docMain.fdId=:fdDemandId)";
						hqlInfo.setWhereBlock(
								StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
						hqlInfo.setParameter("fdDemandId",fdDemandId);
				}
			}
			//如果是项目立项变更选择费用类型，需要根据对应项目立项分类中的配置做相应筛选
			if("prochange".equals(type)){
				String fdProjectId = request.getParameter("fdProjectId");
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.proapp.model.FsscProappProjectItem fsscProappProjectItem left join fsscProappProjectItem.fdItems item";
				where += " where  fsscProappProjectItem.docCategory.fdId in(select docTemplate.fdId from com.landray.kmss.fssc.proapp.model.FsscProappMain where fdId=:fdProjectId))";
				
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("fdProjectId",fdProjectId);
			}
			//如果是费用预提变更选择费用类型，需要根据对应预提分类中的配置做相应筛选
			if("provisionMain".equals(type)){
				String docTemplateId = request.getParameter("docTemplateId");
				String where = "eopBasedataExpenseItem.fdId in(select item.fdId from com.landray.kmss.fssc.provision.model.FsscProvisionItemConfig fsscProvisionItemConfig left join fsscProvisionItemConfig.fdItemList item";
				where += " where  fsscProvisionItemConfig.fdCategory.fdId=:docTemplateId";
				if(StringUtil.isNotNull(fdCompanyId)) {
					if(fdCompanyId.indexOf(";")>-1) {
						where+="  and "+HQLUtil.buildLogicIN("fsscProvisionItemConfig.fdCompany.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";")))+")";
					}else {
						where+="  and fsscProvisionItemConfig.fdCompany.fdId=:fdCompanyId )";
						hqlInfo.setParameter("fdCompanyId",fdCompanyId);
					}
				}
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", where));
				hqlInfo.setParameter("docTemplateId",docTemplateId);
			}
			hqlInfo.setOrderBy("eopBasedataExpenseItem.fdOrder");
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataExpenseItem.class);
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
}
