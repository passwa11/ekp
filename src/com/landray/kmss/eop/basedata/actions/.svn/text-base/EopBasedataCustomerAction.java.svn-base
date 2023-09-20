package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataCustomerForm;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
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

public class EopBasedataCustomerAction extends EopBasedataBusinessAction {

    private IEopBasedataCustomerService eopBasedataCustomerService;


    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCustomerService == null) {
            eopBasedataCustomerService = (IEopBasedataCustomerService) getBean("eopBasedataCustomerService");
        }
        return eopBasedataCustomerService;
    }
    
    private IEopBasedataCompanyService eopBasedataCompanyService;
    
    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	 if (eopBasedataCompanyService == null) {
    		 eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
         }
		return eopBasedataCompanyService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCustomer.class);
        String fdCompanyName=request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)) {
        	hqlInfo.setJoinBlock(" left join eopBasedataCustomer.fdCompanyList company ");
        	hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "company.fdName like :fdCompanyName"));
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
        if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
         	//只能看到自己公司的
         	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
         	if(!ArrayUtil.isEmpty(companyList)){
         		hqlInfo.setJoinBlock(" left join eopBasedataCustomer.fdCompanyList company ");
         		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
         				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)")));
         	}
         }
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataCustomer.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataCustomerForm eopBasedataCustomerForm = (EopBasedataCustomerForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataCustomerService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataCustomerForm;
    }
    
    /**
     * 下载模板
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward downloadTemp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KmssMessages msgs = new KmssMessages();
        try {
			((IEopBasedataCustomerService) getServiceImp(request)).downloadTemp(response);
        } catch (Exception e) {
        	msgs.addError(e);
		}
        if(msgs.hasError()){
        	KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        	return getActionForward("failure", mapping, form, request, response);
        }else{
        	return null;
        }
    }
    
    /**
     * 导入数据
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public ActionForward saveImport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	EopBasedataCustomerForm uploadForm = (EopBasedataCustomerForm) form;
        List<EopBasedataImportMessage> messages = null;
        KmssMessages msgs = new KmssMessages();
        try {
			messages = ((IEopBasedataCustomerService) getServiceImp(request)).saveImport(uploadForm.getFdFile());
			request.setAttribute("messages", messages);
        } catch (Exception e) {
        	msgs.addError(e);
		}
        if(msgs.hasError()){
        	KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        	return getActionForward("failure", mapping, uploadForm, request, response);
        }else if(ArrayUtil.isEmpty(messages)){
        	return getActionForward("success", mapping, uploadForm, request, response);
        }
        return getActionForward("importResult", mapping, uploadForm, request, response);
    }
    
    /**
     * 导出
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward exportCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fdCompanyId = request.getParameter("fdCompanyId");
        KmssMessages msgs = new KmssMessages();
        try {
			((IEopBasedataCustomerService) getServiceImp(request)).exportCustomer(response,fdCompanyId);
        } catch (Exception e) {
        	msgs.addError(e);
		}
        if(msgs.hasError()){
        	KmssReturnPage.getInstance(request).addMessages(msgs).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
        	return getActionForward("failure", mapping, form, request, response);
        }else{
        	return null;
        }
    }

}
