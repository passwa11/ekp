package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
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

public class EopBasedataCostCenterDataAction extends BaseAction {

    private IEopBasedataCostCenterService eopBasedataCostCenterService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCostCenterService == null) {
            eopBasedataCostCenterService = (IEopBasedataCostCenterService) getBean("eopBasedataCostCenterService");
        }
        return eopBasedataCostCenterService;
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
			StringBuilder whereBlock = new StringBuilder();
			whereBlock.append(" eopBasedataCostCenter.fdIsGroup=:fdIsGroup");
			hqlInfo.setParameter("fdIsGroup", "2");
			whereBlock.append(" and eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
            if (StringUtil.isNotNull(keyWord)) {
                String where = "";
                where += "(eopBasedataCostCenter.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataCostCenter.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
				whereBlock.append(" and ").append(where);
            }
			hqlInfo.setWhereBlock(whereBlock.toString());
			String fdNotId=request.getParameter("fdNotId");
			if(StringUtil.isNotNull(fdNotId)){
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataCostCenter.fdId<>:fdNotId "));
				hqlInfo.setParameter("fdNotId", request.getParameter("fdNotId"));
			}
			String fdCompanyId = request.getParameter("fdCompanyId");
			if(StringUtil.isNotNull(fdCompanyId)){
				hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.fdCompanyList company");
				if(fdCompanyId.indexOf(";")>-1) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or company.fdId is null)");
				}else {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(company.fdId=:fdCompanyId or company is null)"));
					hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				}
			}
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCostCenter.class);
			hqlInfo.setOrderBy(" eopBasedataCostCenter.fdOrder nulls last ");//按排序号排序，空值在后
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
    public ActionForward selectCostCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeCounter.logCurrentTime("selectCostCenter", true, getClass());
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
    		StringBuilder whereBlock = new StringBuilder();
    		whereBlock.append(" eopBasedataCostCenter.fdIsGroup=:fdIsGroup");
    		hqlInfo.setParameter("fdIsGroup", "1");
    		whereBlock.append(" and eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable");
    		hqlInfo.setParameter("fdIsAvailable", true);
    		if (StringUtil.isNotNull(keyWord)) {
    			String where = "";
    			where += "(eopBasedataCostCenter.fdName like :fdName";
    			hqlInfo.setParameter("fdName", "%" + keyWord + "%");
    			where += " or eopBasedataCostCenter.fdCode like :fdCode";
    			hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
				where += " or eopBasedataCostCenter.fdType.fdName like :fdTypeName";
				hqlInfo.setParameter("fdTypeName", "%" + keyWord + "%");
    			where += ")";
    			whereBlock.append(" and ").append(where);
    		}
    		hqlInfo.setWhereBlock(whereBlock.toString());
    		String fdCompanyId = request.getParameter("fdCompanyId");
			if(StringUtil.isNotNull(fdCompanyId)){
				hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.fdCompanyList company");
				if(fdCompanyId.indexOf(";")>-1) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or company.fdId is null)");
				}else {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(company.fdId=:fdCompanyId or company.fdId is null)"));
					hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				}
			}
    		String fdNotId=request.getParameter("fdNotId");
    		if(StringUtil.isNotNull(fdNotId)){
    			hqlInfo.setWhereBlock(
        				StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataCostCenter.fdId<>:fdNotId "));
        		hqlInfo.setParameter("fdNotId", fdNotId);
    		}
    		String selectType = request.getParameter("selectType");
    		if("person".equals(selectType)){
				String fdPersonId = request.getParameter("fdPersonId");
				SysOrgPerson person = null;
				if(StringUtil.isNotNull(fdPersonId)){
					person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(fdPersonId, SysOrgPerson.class, true);
				}
				if(person == null){
					person = UserUtil.getUser();
				}
				if(StringUtil.isNotNull(person.getFdHierarchyId())){
					hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.fdFirstCharger first"
							+ " left join eopBasedataCostCenter.fdSecondCharger second left join eopBasedataCostCenter.fdManager manager");
					fdPersonId=person.getFdId();
					String personWhere= "( (:fdHierarchyId like concat(eopBasedataCostCenter.fdEkpOrg.fdHierarchyId,'%')) "
//							+ "or (:fdPersonId like concat(eopBasedataCostCenter.fdFirstCharger.fdId,'%')) "
//							+ "or (:fdPersonId like concat(eopBasedataCostCenter.fdSecondCharger.fdId,'%')) "
//							+ "or (:fdPersonId like concat(eopBasedataCostCenter.fdManager.fdId,'%')))";
							+ "or (first.fdId =:fdPersonId) "
							+ "or (second.fdId =:fdPersonId) "
							+ "or (manager.fdId =:fdPersonId))";
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",personWhere));
					hqlInfo.setParameter("fdHierarchyId", person.getFdHierarchyId());
					hqlInfo.setParameter("fdPersonId", fdPersonId);
				}
				
    		}
    		//根据成本中心组过滤
			String fdCostCenterGroupId=request.getParameter("fdCostCenterGroupId");
			if(StringUtil.isNotNull(fdCostCenterGroupId)){
				String joinBlock=hqlInfo.getJoinBlock()==null?"":hqlInfo.getJoinBlock();
				hqlInfo.setJoinBlock(joinBlock+" left join eopBasedataCostCenter.hbmParent parent");
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						"parent.fdId=:fdCostCenterGroupId"));
				hqlInfo.setParameter("fdCostCenterGroupId", fdCostCenterGroupId);
			}
    		HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCostCenter.class);
			hqlInfo.setOrderBy(" eopBasedataCostCenter.fdOrder nulls last ");////按排序号排序，空值在后
			hqlInfo.setDistinctType(hqlInfo.DISTINCT_YES);

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

    /*********************************
     * 选取所有的成本中心
    *********************************/
    public ActionForward selectAllCostCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeCounter.logCurrentTime("selectCostCenter", true, getClass());
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
    		StringBuilder whereBlock = new StringBuilder();
    		whereBlock.append("  eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable");
    		hqlInfo.setParameter("fdIsAvailable", true);
    		if (StringUtil.isNotNull(keyWord)) {
    			String where = "";
    			where += "(eopBasedataCostCenter.fdName like :fdName";
    			hqlInfo.setParameter("fdName", "%" + keyWord + "%");
    			where += " or eopBasedataCostCenter.fdCode like :fdCode";
    			hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
    			where += ")";
    			whereBlock.append(" and ").append(where);
    		}
    		hqlInfo.setWhereBlock(whereBlock.toString());
    		HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCostCenter.class);
    		Page page = getServiceImp(request).findPage(hqlInfo);
    		request.setAttribute("queryPage", page);
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return mapping.findForward("failure");
    	} else {
    		return mapping.findForward("fdParent");
    	}
    }
}
