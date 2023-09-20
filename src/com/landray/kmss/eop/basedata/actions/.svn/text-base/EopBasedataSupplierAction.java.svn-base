package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataSupplierForm;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataContact;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.model.SupplierContact;
import com.landray.kmss.eop.basedata.service.IEopBasedataBusinessService;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupplierService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.eop.basedata.util.EopBasedataConstant;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.StringUtil;

/**
 * @author wangwh
 * @description:供应商action
 * @date 2021/5/7
 */
public class EopBasedataSupplierAction extends ExtendAction {

    private IEopBasedataSupplierService eopBasedataSupplierService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataSupplierService == null) {
            eopBasedataSupplierService = (IEopBasedataSupplierService) getBean("eopBasedataSupplierService");
        }
        return eopBasedataSupplierService;
    }
    
    private IEopBasedataBusinessService eopBasedataBusinessService;
    
    public IEopBasedataBusinessService getEopBasedataBusinessService() {
    	if (eopBasedataBusinessService == null) {
    		eopBasedataBusinessService = (IEopBasedataBusinessService) getBean("eopBasedataBusinessService");
        }
		return eopBasedataBusinessService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataSupplier.class);
        String fdCompanyName=request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)) {
        	hqlInfo.setJoinBlock(" left join eopBasedataSupplier.fdCompanyList company ");
        	hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "company.fdName like :fdCompanyName"));
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataSupplier.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataSupplierForm eopBasedataSupplierForm = (EopBasedataSupplierForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataSupplierService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataSupplierForm;
    }
    
    /**
	 * 重写父类方法，列表中增加展示第一联系人相关信息
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
    @Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			//展示第一联系人
			List<EopBasedataSupplier> list = page.getList();
	    	List<SupplierContact> result = new ArrayList<SupplierContact>();
	    	if(CollectionUtils.isNotEmpty(list)){
	    		for(EopBasedataSupplier supplier : list) {
	    			SupplierContact bean = new SupplierContact();
	    			BeanUtils.copyProperties(supplier, bean);
	    			for(EopBasedataContact contact : supplier.getFdContactPerson()){
	    				if(contact.getFdIsfirst() != null && contact.getFdIsfirst()) {
	    					bean.setContactName(contact.getFdName());
	    					bean.setContactPhone(contact.getFdPhone());
	    					bean.setContactEmail(contact.getFdEmail());
	    				}
	    			}
	    			result.add(bean);
	        	}
	    	}
	    	page.setList(result);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
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
			((IEopBasedataSupplierService) getServiceImp(request)).downloadTemp(response);
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
    public ActionForward saveImport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	EopBasedataSupplierForm uploadForm = (EopBasedataSupplierForm) form;
        List<EopBasedataImportMessage> messages = null;
        KmssMessages msgs = new KmssMessages();
        try {
			messages = ((IEopBasedataSupplierService) getServiceImp(request)).saveImport(uploadForm.getFdFile());
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
    public ActionForward exportSupplier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fdCompanyId = request.getParameter("fdCompanyId");
        KmssMessages msgs = new KmssMessages();
        try {
			((IEopBasedataSupplierService) getServiceImp(request)).exportSupplier(response,fdCompanyId);
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
     * 启用档案
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward enable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 String param = request.getParameter("param");
         String ids = request.getParameter("ids");
         String[] idarr = ids.split("&");
         String idstr ="";
         String id="";
         for(int i=0;i<idarr.length;i++){
         	id=idarr[i].replace("List_Selected=", "").trim();
         	if(i<idarr.length) {
				idstr+=id+";";
			} else {
				idstr+=id;
			}
         }
        String modelName = request.getParameter("modelName");
        try {
        	List<String> list =new ArrayList<String>();
        	if(SysDataDict.getInstance().getModel(modelName).getPropertyMap().containsKey("fdCode")){//包含fdCode属性才需要校验
        		list=getEopBasedataBusinessService().checkEnable(ids,modelName);
        	}
    		if(list.size()>0){
    			return getActionForward("failure", mapping, form, request, response);
    		}	
    		getEopBasedataBusinessService().saveEnable(idstr,modelName);
		} catch (Exception e) {
			return getActionForward("failure", mapping, form, request, response);
		}
        return getActionForward("success", mapping, form, request, response);
    }
    /**
     * 禁用档案
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward disable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String ids = request.getParameter("ids");
        String[] idarr = ids.split("&");
        String idstr ="";
        String id="";
        for(int i=0;i<idarr.length;i++){
        	id=idarr[i].replace("List_Selected=", "").trim();
        	if(i<idarr.length) {
				idstr+=id+";";
			} else {
				idstr+=id;
			}
        }
        String modelName = request.getParameter("modelName");
        try {
        	getEopBasedataBusinessService().saveDisable(idstr,modelName);
		} catch (Exception e) {
			return getActionForward("failure", mapping, form, request, response);
		}
        return getActionForward("success", mapping, form, request, response);
    }

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("EopBasedataSupplierAction-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!EopBasedataConstant.REQUEST_METHOD_POST.equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String queryString = "method=delete&fdId=${id}";
				String fdModelName = request.getParameter("fdModelName");
				if(fdModelName != null && !"".equals(fdModelName))
				{
					queryString += "&fdModelName=" + fdModelName;
				}
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, queryString);
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException) {
				messages.addError(new KmssMessage("eop-basedata:error.delete.1"),e);
			}else {
				messages.addError(new KmssMessage("eop-basedata:error.delete.0"),e);
			}
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("EopBasedataSupplierAction-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		}else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("EopBasedataSupplierAction-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!EopBasedataConstant.REQUEST_METHOD_GET.equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
		}catch (DataIntegrityViolationException e) {
			messages.addError(new KmssMessage("eop-basedata:error.delete.2"));
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		TimeCounter.logCurrentTime("EopBasedataSupplierAction-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public void checkFdCreditCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");
		String fdCreditCode = request.getParameter("fdCreditCode");

		boolean check = IEopBasedataSupplierService.class.cast(getServiceImp(request)).checkFdCreditCode(fdId, fdCreditCode);
		JSONObject json = new JSONObject();
		json.put("check", check);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}


}
