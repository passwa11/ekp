package com.landray.kmss.fssc.expense.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCcardService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonMobileService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonPaymentService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FsscExpenseMainDataAction extends BaseAction {
	
	private IFsscCommonProappService fsscCommonProappService;
	
	public IFsscCommonProappService getFsscCommonProappService() {
		if(fsscCommonProappService==null){
			fsscCommonProappService = (IFsscCommonProappService) SpringBeanUtil.getBean("fsscProappCommonService");
	    	}
		return fsscCommonProappService;
	}

	private IFsscCommonFeeService fsscCommonFeeService;

    public IFsscCommonFeeService getFsscCommonFeeService() {
    	if(fsscCommonFeeService==null){
    		fsscCommonFeeService = (IFsscCommonFeeService) SpringBeanUtil.getBean("fsscCommonFeeService");
    	}
		return fsscCommonFeeService;
	}
    
    private IFsscCommonMobileService fsscCommonMobileService;
    
    public IFsscCommonMobileService getFsscCommonMobileService() {
        if (fsscCommonMobileService == null) {
        	fsscCommonMobileService = (IFsscCommonMobileService) SpringBeanUtil.getBean("fsscCommonMobileService");
        }
        return fsscCommonMobileService;
    }

    private IFsscExpenseMainService fsscExpenseMainService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseMainService == null) {
            fsscExpenseMainService = (IFsscExpenseMainService) getBean("fsscExpenseMainService");
        }
        return fsscExpenseMainService;
    }

    private IFsscCommonPaymentService fsscCommonPaymentService;

    public IFsscCommonPaymentService getFsscCommonPaymentService() {
        if (fsscCommonPaymentService == null) {
            fsscCommonPaymentService = (IFsscCommonPaymentService) getBean("fsscCommonPaymentService");
        }
        return fsscCommonPaymentService;
    }

    private IFsscCommonCcardService fsscCommonCcardService;

    public IFsscCommonCcardService getFsscCommonCcardService() {
        if (fsscCommonCcardService == null) {
            fsscCommonCcardService = (IFsscCommonCcardService) getBean("fsscCommonCcardService");
        }
        return fsscCommonCcardService;
    }
    
    public ActionForward getExpenseMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getExpenseMain", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdShareType = request.getParameter("fdShareType");
            String docTemplateId = request.getParameter("docTemplateId");
            String ids=null;
            if(StringUtil.isNotNull(docTemplateId)){
                FsscExpenseShareCategory cate = (FsscExpenseShareCategory) getServiceImp(request).findByPrimaryKey(docTemplateId, FsscExpenseShareCategory.class, true);
                ids = cate.getFdCateIds();
            }
            Page page = new Page();
            if("2".equals(fdShareType)){	//事后分摊选择付款单据
                page = getFsscCommonPaymentService().findPaymentMainPage(request, ids);
            }else{
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
                    where += "(fsscExpenseMain.docSubject like :docSubject";
                    hqlInfo.setParameter("docSubject", "%" + keyWord + "%");
                    where += ")";
                    hqlInfo.setWhereBlock(where);
                }
                if(StringUtil.isNotNull(ids)){
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscExpenseMain.docTemplate.fdId in(:docTemplateId)"));
                    hqlInfo.setParameter("docTemplateId",Arrays.asList(ids.split(";")));
                }
                String docStatus = request.getParameter("docStatus");
                if(StringUtil.isNotNull(docStatus)){
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscExpenseMain.docStatus=:docStatus"));
                    hqlInfo.setParameter("docStatus",docStatus);
                }
                String share = request.getParameter("isShare");
                if(StringUtil.isNotNull(share)){
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "fsscExpenseMain.fdId not in(select fdModelId from com.landray.kmss.fssc.expense.model.FsscExpenseShareMain where docStatus=:status1 or docStatus=:status2)"));
                    hqlInfo.setParameter("status1",SysDocConstant.DOC_STATUS_EXAMINE);
                    hqlInfo.setParameter("status2",SysDocConstant.DOC_STATUS_PUBLISH);
                }
                HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseMain.class);
                page = getServiceImp(request).findPage(hqlInfo);
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getExpenseMain", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getExpenseMain");
        }
    }
    
    public ActionForward getExpenseDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getExpenseDetail", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
        	String fdMainId = request.getParameter("fdMainId");
            String fdShareType = request.getParameter("fdShareType");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            if(StringUtil.isNotNull(fdMainId)){
                if("2".equals(fdShareType)){	//选择付款费用明细
                    jsonArray = getFsscCommonPaymentService().getPaymentDetail(fdMainId);
                }else{
                    FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdMainId, null, true);
                    for(FsscExpenseDetail detail:main.getFdDetailList()){
                        jsonObject = new JSONObject();
                        jsonObject.put("fdId", detail.getFdId());
                        jsonObject.put("fdCompanyId", detail.getFdCompany() !=null?detail.getFdCompany().getFdId():"");
                        jsonObject.put("fdCompanyName", detail.getFdCompany() !=null?detail.getFdCompany().getFdName():"");
                        jsonObject.put("fdExpenseItemId", detail.getFdExpenseItem() !=null?detail.getFdExpenseItem().getFdId():"");
                        jsonObject.put("fdExpenseItemName", detail.getFdExpenseItem() !=null?detail.getFdExpenseItem().getFdName():"");
                        jsonObject.put("fdCostCenterId", detail.getFdCostCenter() !=null?detail.getFdCostCenter().getFdId():"");
                        jsonObject.put("fdCostCenterName", detail.getFdCostCenter() !=null?detail.getFdCostCenter().getFdName():"");
                        jsonObject.put("fdCurrencyId", detail.getFdCurrency() !=null?detail.getFdCurrency().getFdId():"");
                        jsonObject.put("fdCurrencyName", detail.getFdCurrency() !=null?detail.getFdCurrency().getFdName():"");
                        jsonObject.put("fdRealUserId", detail.getFdRealUser() !=null?detail.getFdRealUser().getFdId():"");
                        jsonObject.put("fdRealUserName", detail.getFdRealUser() !=null?detail.getFdRealUser().getFdName():"");
                        jsonObject.put("fdHappenDate", DateUtil.convertDateToString(detail.getFdHappenDate(), DateUtil.PATTERN_DATE));
                        jsonObject.put("fdApplyMoney", detail.getFdApplyMoney());
                        jsonObject.put("fdStandardMoney", detail.getFdStandardMoney());
                        jsonObject.put("fdApprovedApplyMoney", detail.getFdApprovedApplyMoney());
                        jsonObject.put("fdApprovedStandardMoney", detail.getFdApprovedStandardMoney());
                        jsonObject.put("fdExchangeRate", detail.getFdExchangeRate());
                        jsonObject.put("fdUse", detail.getFdUse());
                        jsonArray.add(jsonObject);
                    }
                }
            }
            request.setAttribute("queryList", jsonArray);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getExpenseDetail", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("detail");
        }
    }
    
    /**
     * 报销选择事前
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectFee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("selectFee", true, getClass());
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
            Page page = new Page();
            page.setPageno(pageno);
            page.setRowsize(rowsize);
            String docTemplateId = request.getParameter("docTemplateId");
            if(FsscCommonUtil.checkHasModule("/fssc/fee/")){
            	Map<String,String> params = new HashMap<String,String>();
            	if(StringUtil.isNotNull(docTemplateId)){
            		FsscExpenseCategory cate = (FsscExpenseCategory) getServiceImp(request).findByPrimaryKey(docTemplateId, FsscExpenseCategory.class, true);
            		params.put("docTemplateId", cate.getFdFeeTemplateId());
            	}
            	params.put("keyWord", keyWord);
            	params.put("source", "expense");
            	String fdPersonId = null;
            	String fdIsAuthorize = EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
            	if("true".equals(fdIsAuthorize)||StringUtil.isNull(fdIsAuthorize)) {//如果启用了提单授权，可以选择授权人的申请
            		fdPersonId = request.getParameter("fdPersonId");
            		params.put("fdPersonId", fdPersonId);
            	}
            	getFsscCommonFeeService().getFeePage(page,params);
            }else{
            	page.setList(new ArrayList());
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectFee", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectFee");
        }
    }
    /**
	 * 选择未报费用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectNote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("selectNote", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String keyWord = request.getParameter("q._keyword");
            String fdCategoryId=request.getParameter("fdCategoryId");
            String fdCompanyId=request.getParameter("fdCompanyId");
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            Page page = new Page();
            page.setPageno(pageno);
            page.setRowsize(rowsize);
            if(FsscCommonUtil.checkHasModule("/fssc/mobile/")){
            	Map<String,String> params = new HashMap<String,String>();
            	params.put("keyWord", keyWord);
                params.put("fdCategoryId", fdCategoryId);
                params.put("fdCompanyId", fdCompanyId);
            	getFsscCommonMobileService().getMobileNotePage(page,params);         		
            }else{
            		page.setList(new ArrayList());
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectNote", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectNote");
        }
	}
	
	/**
     * 报销选择立项
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectProapp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("selectProapp", true, getClass());
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
            Page page = new Page();
            page.setPageno(pageno);
            page.setRowsize(rowsize);
            if(FsscCommonUtil.checkHasModule("/fssc/proapp/")){
	            	Map<String,String> params = new HashMap<String,String>();
	            	params.put("keyWord", keyWord);
	            	String source=request.getParameter("source");
	            	if("ledger".equals(source)) {
	            		params.put("source", source);
	            	}else {
	            		params.put("source", "expense");
	            	}
	            	String companyId = request.getParameter("fdCompanyId");
	            	if(StringUtil.isNotNull(companyId)) {
	            		params.put("fdCompanyId", companyId);
		            	getFsscCommonProappService().getProappPage(page,params);
	            	}
            }else{
            		page.setList(new ArrayList());
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectProapp", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectFee");
        }
    }

    /**
     * 选择交易数据
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectTranData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("selectTranData", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String keyWord = request.getParameter("q._keyword");
            String fdPersonId = request.getParameter("fdPersonId");
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            Page page = new Page();
            page.setPageno(pageno);
            page.setRowsize(rowsize);
            if(FsscCommonUtil.checkHasModule("/fssc/ccard/")){
                Map<String,String> params = new HashMap<String,String>();
                params.put("keyWord", keyWord);
                params.put("fdPersonId", fdPersonId);
                getFsscCommonCcardService().getTranDataPage(page,params);
            }else{
                page.setList(new ArrayList());
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectTranData", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectTranData");
        }
    }

}
