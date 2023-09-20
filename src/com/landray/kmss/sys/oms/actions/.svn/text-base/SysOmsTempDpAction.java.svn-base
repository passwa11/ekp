package com.landray.kmss.sys.oms.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.oms.forms.SysOmsTempDpForm;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.service.ISysOmsTempDeptService;
import com.landray.kmss.sys.oms.service.ISysOmsTempDpService;
import com.landray.kmss.sys.oms.service.ISysOmsTempPersonService;
import com.landray.kmss.sys.oms.temp.OmsTempSynFailType;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class SysOmsTempDpAction extends ExtendAction {

    private ISysOmsTempDpService sysOmsTempDpService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysOmsTempDpService == null) {
            sysOmsTempDpService = (ISysOmsTempDpService) getBean("sysOmsTempDpService");
        }
        return sysOmsTempDpService;
    }
    
    private ISysOmsTempPersonService sysOmsTempPersonService;

    public ISysOmsTempPersonService getSysOmsTempPersonService() {
        if (sysOmsTempPersonService == null) {
            sysOmsTempPersonService = (ISysOmsTempPersonService) getBean("sysOmsTempPersonService");
        }
        return sysOmsTempPersonService;
    }
    
    private ISysOmsTempDeptService sysOmsTempDeptService;

    public ISysOmsTempDeptService getSysOmsTempDeptService() {
        if (sysOmsTempDeptService == null) {
            sysOmsTempDeptService = (ISysOmsTempDeptService) getBean("sysOmsTempDeptService");
        }
        return sysOmsTempDeptService;
    }

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOmsTempDp.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        String fdTrxId = request.getParameter("fdTrxId");
        if(StringUtil.isNotNull(fdTrxId)){
        	String where = StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"fdTrxId=:fdTrxId");
        	hqlInfo.setWhereBlock(where);
        	hqlInfo.setParameter("fdTrxId",fdTrxId);
        }
        String fdSearchName = request.getParameter("q.fdSearchName");
        if (StringUtil.isNotNull(fdSearchName)) {
        	String whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"(sysOmsTempDp.fdPersonId like :fdPersonId  or sysOmsTempDp.fdDeptId like :fdDeptId)");
			hqlInfo.setParameter("fdPersonId", "%" + fdSearchName + "%");
			hqlInfo.setParameter("fdDeptId", "%" + fdSearchName + "%");
			hqlInfo.setWhereBlock(whereBlock);
		}
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.sys.oms.model.SysOmsTempDp.class);
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysOmsTempDpForm sysOmsTempDpForm = (SysOmsTempDpForm) super.createNewForm(mapping, form, request, response);
        ((ISysOmsTempDpService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysOmsTempDpForm;
    }
    
    
    /**
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
   			String fdTrxId = request.getParameter("fdTrxId");
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
   			List<SysOmsTempDp> dps = page.getList();
   			for (SysOmsTempDp sysOmsTempDp : dps) {
   				//查询人员名称
   				SysOmsTempPerson person = getSysOmsTempPersonService().findByDp(sysOmsTempDp);
   				if(person != null) {
                    sysOmsTempDp.setFdPersonName(person.getFdName());
                }
   				//查询部门名称
   				SysOmsTempDept dept = getSysOmsTempDeptService().finDept(sysOmsTempDp);
   				if(dept != null) {
                    sysOmsTempDp.setFdDeptName(dept.getFdName());
                }
   				if(StringUtil.isNotNull(sysOmsTempDp.getFdFailReason())) {
   					sysOmsTempDp.setFdFailReasonDesc(OmsTempSynFailType.getEnumByValue(sysOmsTempDp.getFdFailReason()).getDesc()); 
   				}
			}
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
}
