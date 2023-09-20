package com.landray.kmss.sys.portal.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.portal.forms.SysPortalPopTplForm;
import com.landray.kmss.sys.portal.model.SysPortalPopTpl;
import com.landray.kmss.sys.portal.model.SysPortalPopTplCategory;
import com.landray.kmss.sys.portal.service.ISysPortalPopTplCategoryService;
import com.landray.kmss.sys.portal.service.ISysPortalPopTplService;
import com.landray.kmss.sys.portal.util.SysPortalPopTemplateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysPortalPopTplAction extends ExtendAction {

    private ISysPortalPopTplService sysPortalPopTplService;
    private ISysPortalPopTplCategoryService sysPortalPopTplCategoryService;
    
    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysPortalPopTplService == null) {
            sysPortalPopTplService = (ISysPortalPopTplService) getBean("sysPortalPopTplService");
        }
        return sysPortalPopTplService;
    }
    
    public IBaseService getCategoryServiceImp(HttpServletRequest request) {
        if (sysPortalPopTplCategoryService == null) {
        	sysPortalPopTplCategoryService = (ISysPortalPopTplCategoryService) getBean("sysPortalPopTplCategoryService");
        }
        return sysPortalPopTplCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysPortalPopTpl.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        
        String categoryId = request.getParameter("categoryId");
        if(StringUtil.isNotNull(categoryId)) {
        	
        	if(StringUtil.isNotNull(hqlInfo.getWhereBlock())) {        		
        		hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
        				+ " and sysPortalPopTpl.fdCategory.fdId = :categoryId");
        	} else {
        		hqlInfo.setWhereBlock("sysPortalPopTpl.fdCategory.fdId = :categoryId");
        	}
        	hqlInfo.setParameter("categoryId", categoryId);
        }
        
        String keyword = request.getParameter("q._keyword");
        if(StringUtil.isNotNull(keyword)) {
        	
        	if(StringUtil.isNotNull(hqlInfo.getWhereBlock())) {        		
        		hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
        				+ " and sysPortalPopTpl.docSubject like :keyword");
        	} else {
        		hqlInfo.setWhereBlock(" and sysPortalPopTpl.docSubject like :keyword");
        	}
        	hqlInfo.setParameter("keyword",  "%" + keyword + "%");
        }  
        
    }
    
    /**
     * 选取弹窗模板
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectTpl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	
    	super.data(mapping, form, request, response);
    	return getActionForward("selectTpl", mapping, form, request, response);
    	
    }
    
    public ActionForward getSysTpls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	TimeCounter.logCurrentTime("Action-getSysTpls", true, getClass());
		KmssMessages messages = new KmssMessages();
    	
    	try {
    		
    		Map<String, String> templates = SysPortalPopTemplateUtil.getTemplates();
    		Object[] objects = templates.keySet().toArray();
    		
    		JSONArray array = new JSONArray();
    		for(Object o : objects) {
    			String id = (String)o;
    			if(id != null) {
    				
    				JSONObject jo = new JSONObject();
    				jo.put("id", id);
    				
    				String template = SysPortalPopTemplateUtil.getTemplate(id);
    				jo.put("design", template);
    				
    				array.add(jo);
    			}
    			
    		}
    		
    		request.setAttribute("lui-source", array);
    		
    	} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getSysTpls", false, getClass());
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
        SysPortalPopTplForm sysPortalPopTplForm = (SysPortalPopTplForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalPopTplService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        
        ISysPortalPopTplCategoryService sysPortalPopTplCategoryService = (ISysPortalPopTplCategoryService) getCategoryServiceImp(request);
        String categoryId = request.getParameter("categoryId");
        if(StringUtil.isNotNull(categoryId)) {
        	SysPortalPopTplCategory sysPortalPopTplCategory = (SysPortalPopTplCategory) sysPortalPopTplCategoryService.findByPrimaryKey(categoryId);
        	if(sysPortalPopTplCategory != null) {
        		sysPortalPopTplForm.setFdCategoryId(sysPortalPopTplCategory.getFdId());
        		sysPortalPopTplForm.setFdCategoryName(sysPortalPopTplCategory.getFdName());
        	}
        }
        
        return sysPortalPopTplForm;
    }

}
