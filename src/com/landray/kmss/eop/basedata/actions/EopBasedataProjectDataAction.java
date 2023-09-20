package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.service.IEopBasedataProjectService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLHelper.ORDERTYPE;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataProjectDataAction extends BaseAction {

    private IEopBasedataProjectService eopBasedataProjectService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataProjectService == null) {
            eopBasedataProjectService = (IEopBasedataProjectService) getBean("eopBasedataProjectService");
        }
        return eopBasedataProjectService;
    }

	public ActionForward fdParent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			whereBlock.append(" eopBasedataProject.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", true);
			if (StringUtil.isNotNull(keyWord)) {
				String where = "";
				where += "(eopBasedataProject.fdName like :fdName";
				hqlInfo.setParameter("fdName", "%" + keyWord + "%");
				where += " or eopBasedataProject.fdCode like :fdCode";
				hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
				where += ")";
				whereBlock.append(" and ").append(where);
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataProject.fdId<>:fdNotId "));
			hqlInfo.setParameter("fdNotId", request.getParameter("fdNotId"));
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataProject.fdIsAvailable=:fdIsAvailable "));
			//项目能选择到项目和两者均是，核算项目能选择到核算项目和两者均是
			String fdProjectType = request.getParameter("fdProjectType");//1项目，2核算项目，3两者均是，空则所有
			if(StringUtil.isNotNull(fdProjectType)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " (eopBasedataProject.fdType = :fdType or eopBasedataProject.fdType ='3') "));
				hqlInfo.setParameter("fdType", fdProjectType);
			}
			
			String fdCompanyId = request.getParameter("fdCompanyId");
			if(StringUtil.isNotNull(fdCompanyId)){
				hqlInfo.setJoinBlock("left join eopBasedataProject.fdCompanyList comp");
				if(fdCompanyId.indexOf(";")>-1) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+ HQLUtil.buildLogicIN("comp.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or comp is null)");
				}else {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(comp.fdId=:fdCompanyId or comp is null)"));
					hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				}
			}
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
			}else{
				hqlInfo.setParameter("fdIsAvailable",true);
			}
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataProject.class);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("fdParent", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("project");
		}
	}

    public ActionForward project(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("project", true, getClass());
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
                where += "(eopBasedataProject.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataProject.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"eopBasedataProject.fdIsAvailable=:fdIsAvailable"));
			hqlInfo.setParameter("fdIsAvailable", true);

			String fdCompanyId = request.getParameter("fdCompanyId");
			if(StringUtil.isNotNull(fdCompanyId)){
				hqlInfo.setJoinBlock("left join eopBasedataProject.fdCompanyList comp");
				if(fdCompanyId.indexOf(";")>-1) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+ HQLUtil.buildLogicIN("comp.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or comp.fdId is null)");
				}else {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(comp.fdId=:fdCompanyId or comp.fdId is null)"));
					hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				}
			}
			
			String project = request.getParameter("project");
			//WBS能选择项目性质是项目和两者均是的项目
			if("wbs".equals(project)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						" (eopBasedataProject.fdType =:fdType or eopBasedataProject.fdType ='3') "));
				hqlInfo.setParameter("fdType", "1");
			}
			//预算能选择项目性质是项目和两者均是的项目
			if("budgeting".equals(type)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						"eopBasedataProject.fdId in (select t.fdProjectList.fdId from FsscBudgetingAuth t where t.fdIsAvailable=:fdIsAvailable and t.fdPersonList.fdId=:fdPersonId)"));
				hqlInfo.setParameter("fdPersonId", UserUtil.getUser().getFdId());
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						" (eopBasedataProject.fdType =:fdType or eopBasedataProject.fdType='3') "));
				hqlInfo.setParameter("fdType", EopBasedataConstant.FSSC_BASE_PROJECT_1);	//项目
			}else if("budget".equals(type)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						" (eopBasedataProject.fdType=:fdType or eopBasedataProject.fdType='3') "));
				hqlInfo.setParameter("fdType", EopBasedataConstant.FSSC_BASE_PROJECT_1);	//项目
			}
			String fdProjectType = request.getParameter("fdProjectType");
			//项目能选择到项目和两者均是，核算项目能选择到核算项目和两者均是
			if(StringUtil.isNotNull(fdProjectType)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " (eopBasedataProject.fdType =:fdType or eopBasedataProject.fdType ='3') "));
				hqlInfo.setParameter("fdType", fdProjectType);
			}
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).order("fdCode", ORDERTYPE.asc).buildHQLInfo(hqlInfo, EopBasedataProject.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("project", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("project");
        }
    }
    /*********************************
     * 选取所有的项目
    *********************************/
    public ActionForward selectAllProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeCounter.logCurrentTime("project", true, getClass());
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
    			where += "(eopBasedataProject.fdName like :fdName";
    			hqlInfo.setParameter("fdName", "%" + keyWord + "%");
    			where += " or eopBasedataProject.fdCode like :fdCode";
    			hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
    			where += ")";
    			hqlInfo.setWhereBlock(where);
    		}
    		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"eopBasedataProject.fdIsAvailable=:fdIsAvailable"));
			hqlInfo.setParameter("fdIsAvailable", true);
			String fdProjectType=request.getParameter("fdProjectType");
			//项目能选择到项目和两者均是，核算项目能选择到核算项目和两者均是
			if(StringUtil.isNotNull(fdProjectType)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " (eopBasedataProject.fdType =:fdType or eopBasedataProject.fdType='3') "));
				hqlInfo.setParameter("fdType", fdProjectType);
			}
    		HQLHelper.by(request).order("fdCode", ORDERTYPE.asc).buildHQLInfo(hqlInfo, EopBasedataProject.class);
    		Page page = getServiceImp(request).findPage(hqlInfo);
    		request.setAttribute("queryPage", page);
    	} catch (Exception e) {
    		messages.addError(e);
    	}
    	TimeCounter.logCurrentTime("project", false, getClass());
    	if (messages.hasError()) {
    		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
    		return mapping.findForward("failure");
    	} else {
    		return mapping.findForward("project");
    	}
    }
}
