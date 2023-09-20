package com.landray.kmss.eop.basedata.actions;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataCompanyForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ExceptionUtil;
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

public class EopBasedataCompanyAction extends EopBasedataBusinessAction {
	private Logger logger = LoggerFactory.getLogger(getClass());

    private IEopBasedataCompanyService eopBasedataCompanyService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCompanyService == null) {
            eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
        }
        return eopBasedataCompanyService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	List<SysOrgElement> financial=EopBasedataAuthUtil.getFinanceStaffAndManagerList(null);
		SysOrgElement user=UserUtil.getUser();
    	if(!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
    		//没有公司权限，财务只能看到有权限看到的公司，普通人无权限查看
			if(financial.contains(user)){//财务
				List<EopBasedataCompany> companyList=((IEopBasedataCompanyService) getServiceImp(request)).findCompanyByUserId(user.getFdId());
	    		if(!ArrayUtil.isEmpty(companyList)){
	    			whereBlock=StringUtil.linkString(whereBlock, " and ", HQLUtil.buildLogicIN("eopBasedataCompany.fdId", 
	    					ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";"))));
	    		}
			}else{//普通用户
				whereBlock = StringUtil.linkString(whereBlock, " and ", "1=2"); //拼接不可能条件
			}
    	}
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCompany.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataCompanyForm eopBasedataCompanyForm = (EopBasedataCompanyForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataCompanyService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataCompanyForm;
    }

	/**
	 * 重写该方案，用于编辑时财务系统显示
	 * 
	 * @param form
	 * @param request
	 * @return form对象
	 * @throws Exception
	 */
	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdFinancialSystem = EopBasedataFsscUtil.getSwitchValue("fdFinancialSystem");
		if (StringUtil.isNotNull(fdFinancialSystem)) {
			String[] property = fdFinancialSystem.split(";");
			request.setAttribute("financialSystemList", ArrayUtil.convertArrayToList(property));
		}
	}
	/**
	 * 将公司所属公司组清空
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward clearCompanyGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			((IEopBasedataCompanyService)getServiceImp(request)).updateCompanyGroup();
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		PrintWriter out = response.getWriter();
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			out.print("false");
		} else {
			out.print("true");
		}
		return null;
	}


	/**
	 * 获取联系人信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getContactorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getContactorInfo", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject rtnData = new JSONObject();
		try {
			String contactorId = request.getParameter("contactorId");
			EopBasedataUtil.getService(IEopBasedataCompanyService.class, null).fillContactorInfo(rtnData, contactorId);
		} catch (Exception e) {
			rtnData.put("error", ExceptionUtil.getExceptionString(e));
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}
		TimeCounter.logCurrentTime("Action-getContactorInfo", false, getClass());
		rtnData.put("success", !messages.hasError());
		request.setAttribute("lui-source", rtnData);
		return mapping.findForward("lui-source");
	}

	/**
	 * 为案件提供数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dataForCase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-dataForCase", true, getClass());
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
			Page page = ((IEopBasedataCompanyService) getServiceImp(request)).findPageForCase(request, hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-dataForCase", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("dataForCase", mapping, form, request, response);
		}
	}
}
