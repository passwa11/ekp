package com.landray.kmss.sys.portal.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.portal.forms.SysPortalPopMainForm;
import com.landray.kmss.sys.portal.model.SysPortalPopCategory;
import com.landray.kmss.sys.portal.model.SysPortalPopMain;
import com.landray.kmss.sys.portal.model.SysPortalPopTpl;
import com.landray.kmss.sys.portal.service.ISysPortalPopCategoryService;
import com.landray.kmss.sys.portal.service.ISysPortalPopMainService;
import com.landray.kmss.sys.portal.service.ISysPortalPopTplService;
import com.landray.kmss.sys.portal.util.SysPortalPopTemplateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysPortalPopMainAction extends ExtendAction {

    private ISysPortalPopMainService sysPortalPopMainService;
    private ISysPortalPopCategoryService sysPortalPopCategoryService;
    private ISysPortalPopTplService sysPortalPopTplService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysPortalPopMainService == null) {
            sysPortalPopMainService = (ISysPortalPopMainService) getBean("sysPortalPopMainService");
        }
        return sysPortalPopMainService;
    }
    
    public IBaseService getCategoryServiceImp(HttpServletRequest request) {
        if (sysPortalPopCategoryService == null) {
        	sysPortalPopCategoryService = (ISysPortalPopCategoryService) getBean("sysPortalPopCategoryService");
        }
        return sysPortalPopCategoryService;
    }
    
    public IBaseService getSysPortalPopTplServiceImp(HttpServletRequest request) {
        if (sysPortalPopTplService == null) {
        	sysPortalPopTplService = (ISysPortalPopTplService) getBean("sysPortalPopTplService");
        }
        return sysPortalPopTplService;
    }

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysPortalPopMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        
        String categoryId = request.getParameter("categoryId");
        if(StringUtil.isNotNull(categoryId)) {
        	
        	if(StringUtil.isNotNull(hqlInfo.getWhereBlock())) {        		
        		hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
        				+ " and sysPortalPopMain.fdCategory.fdId = :categoryId");
        	} else {
        		hqlInfo.setWhereBlock("sysPortalPopMain.fdCategory.fdId = :categoryId");
        	}
        	hqlInfo.setParameter("categoryId", categoryId);
        }
        
    }

    /**
     * 获取个人弹窗数据
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward getOwnPopList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-getOwnPopList", true, getClass());
		KmssMessages messages = new KmssMessages();
		
		try {
			
			HQLInfo hqlInfo = new HQLInfo();
			
			String fdId = UserUtil.getKMSSUser().getUserId();
			List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
			String t = HQLUtil.buildLogicIN("sysPortalPopMain.fdNotifiers.fdId", authOrgIds);
			
			hqlInfo.setWhereBlock(t + " and sysPortalPopMain.fdStartTime <= :now and sysPortalPopMain.fdEndTime > :now" +
					" and sysPortalPopMain.fdIsAvailable = :fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);

			Date now = new Date();
			hqlInfo.setParameter("now", now);

			JSONArray pops = new JSONArray();
			
			List list = getServiceImp(request).findList(hqlInfo);
			for(Object obj : list) {
				SysPortalPopMain sysPortalPopMain = (SysPortalPopMain) obj;
				if(sysPortalPopMain != null) {
					
					JSONObject pop = new JSONObject();
					pop.accumulate("fdId", sysPortalPopMain.getFdId());
					pop.accumulate("docSubject", sysPortalPopMain.getDocSubject());
					pop.accumulate("fdDuration", sysPortalPopMain.getFdDuration());
					
					pop.accumulate("fdStartTime", sysPortalPopMain.getFdStartTime().getTime());
					pop.accumulate("fdEndTime", sysPortalPopMain.getFdEndTime().getTime());
					
					pop.accumulate("docContent", sysPortalPopMain.getDocContent());
					pop.accumulate("fdMode", sysPortalPopMain.getFdMode());
					
					pops.add(pop);
				}
			}
			
			request.setAttribute("lui-source", pops);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getOwnPopList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysPortalPopMainForm sysPortalPopMainForm = (SysPortalPopMainForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalPopMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));

        ISysPortalPopCategoryService sysPortalPopCategoryService = (ISysPortalPopCategoryService) getCategoryServiceImp(request);
        String categoryId = request.getParameter("categoryId");
        if(StringUtil.isNotNull(categoryId)) {
        	SysPortalPopCategory sysPortalPopCategory = (SysPortalPopCategory) sysPortalPopCategoryService.findByPrimaryKey(categoryId);
        	if(sysPortalPopCategory != null) {
        		sysPortalPopMainForm.setFdCategoryId(sysPortalPopCategory.getFdId());
        		sysPortalPopMainForm.setFdCategoryName(sysPortalPopCategory.getFdName());
        	}
        }
        
        ISysPortalPopTplService sysPortalPopTplService = (ISysPortalPopTplService) getSysPortalPopTplServiceImp(request);
        String tplId = request.getParameter("tplId");
        if(StringUtil.isNotNull(tplId)) {
        	SysPortalPopTpl sysPortalPopTpl = (SysPortalPopTpl) sysPortalPopTplService.findByPrimaryKey(tplId);
        	if(sysPortalPopTpl != null) {        		
        		sysPortalPopMainForm.setDocContent(sysPortalPopTpl.getDocContent());
        	}
        }
        
        String customCategory = request.getParameter("customCategory");
        if(StringUtil.isNotNull(customCategory)) {
        	sysPortalPopMainForm.setFdCustomCategory(customCategory);
        }
        
        String content = request.getParameter("content");
        if(StringUtil.isNotNull(content)) {
        	sysPortalPopMainForm.setDocContent(content);
        }
        
        String sysTplId = request.getParameter("sysTplId");
        if(StringUtil.isNotNull(sysTplId)) {
        	String template = SysPortalPopTemplateUtil.getTemplate(sysTplId);
        	if(StringUtil.isNotNull(template)) {        		
        		sysPortalPopMainForm.setDocContent(template);
        	}
        }
        
        return sysPortalPopMainForm;
    }

}
