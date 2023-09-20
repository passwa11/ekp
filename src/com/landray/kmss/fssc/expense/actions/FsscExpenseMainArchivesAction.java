package com.landray.kmss.fssc.expense.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemBudgetService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPaymentService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBaiwangService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCashierPaymentService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCreditService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonLoanService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonNuoService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonPresService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProappService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonProvisionService;
import com.landray.kmss.fssc.common.util.FsscCommonProcessUtil;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseAccounts;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.fssc.expense.service.IFsscExpenseDetailService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.archives.service.ISysArchivesSignService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FsscExpenseMainArchivesAction extends ExtendAction {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FsscExpenseMainArchivesAction.class);

    private IFsscCommonProappService fsscCommonProappService;
    public IFsscCommonProappService getFsscCommonProappService() {
        if (fsscCommonProappService == null) {
            fsscCommonProappService = (IFsscCommonProappService) getBean("fsscProappCommonService");
        }
        return fsscCommonProappService;
    }
    private IFsscCommonFeeService fsscCommonFeeService;

    public IFsscCommonFeeService getFsscCommonFeeService() {
        if (fsscCommonFeeService == null) {
            fsscCommonFeeService = (IFsscCommonFeeService) getBean("fsscCommonFeeService");
        }
        return fsscCommonFeeService;
    }

    private IFsscCommonLoanService fsscCommonLoanService;

    public IFsscCommonLoanService getFsscCommonLoanService() {
        if (fsscCommonLoanService == null) {
            fsscCommonLoanService = (IFsscCommonLoanService) getBean("fsscCommonLoanService");
        }
        return fsscCommonLoanService;
    }

    private IFsscCommonBudgetMatchService fsscCommonBudgetService;

    public IFsscCommonBudgetMatchService getFsscCommonBudgetService() {
        if (fsscCommonBudgetService == null) {
            fsscCommonBudgetService = (IFsscCommonBudgetMatchService) getBean("fsscBudgetMatchService");
        }
        return fsscCommonBudgetService;
    }

    private IEopBasedataItemBudgetService eopBasedataItemBudgetService;

    public IEopBasedataItemBudgetService getEopBasedataItemBudgetService() {
        if (eopBasedataItemBudgetService == null) {
            eopBasedataItemBudgetService = (IEopBasedataItemBudgetService) getBean("eopBasedataItemBudgetService");
        }
        return eopBasedataItemBudgetService;
    }

    private IFsscExpenseMainService fsscExpenseMainService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseMainService == null) {
            fsscExpenseMainService = (IFsscExpenseMainService) getBean("fsscExpenseMainService");
        }
        return fsscExpenseMainService;
    }

    private IEopBasedataPaymentService eopBasedataPaymentService;

    public IEopBasedataPaymentService getEopBasedataPaymentService() {
        if (eopBasedataPaymentService == null) {
            eopBasedataPaymentService = (IEopBasedataPaymentService) getBean("eopBasedataPaymentService");
        }
        return eopBasedataPaymentService;
    }

    private IEopBasedataStandardService eopBasedataStandardService;

    public IEopBasedataStandardService getEopBasedataStandardService() {
        if (eopBasedataStandardService == null) {
            eopBasedataStandardService = (IEopBasedataStandardService) getBean("eopBasedataStandardService");
        }
        return eopBasedataStandardService;
    }

    private IFsscCommonCashierPaymentService fsscCommonCashierPaymentService;

    public IFsscCommonCashierPaymentService getFsscCommonCashierPaymentService() {
        if (fsscCommonCashierPaymentService == null) {
            fsscCommonCashierPaymentService = (IFsscCommonCashierPaymentService) SpringBeanUtil
                    .getBean("fsscCommonCashierPaymentService");
        }
        return fsscCommonCashierPaymentService;
    }


    private IFsscExpenseDetailService fsscExpenseDetailService;

    public IFsscExpenseDetailService getFsscExpenseDetailService() {
        if (fsscExpenseDetailService == null) {
            fsscExpenseDetailService = (IFsscExpenseDetailService) getBean("fsscExpenseDetailService");
        }
        return fsscExpenseDetailService;
    }

    private IFsscCommonProvisionService fsscCommonProvisionService;

    public IFsscCommonProvisionService getFsscCommonProvisionService() {
        if (fsscCommonProvisionService == null) {
            fsscCommonProvisionService = (IFsscCommonProvisionService) getBean("fsscProvisionCommonService");
        }
        return fsscCommonProvisionService;
    }

    public IFsscCommonBaiwangService fsscCommonBaiwangService;

    public IFsscCommonBaiwangService getFsscCommonBaiwangService() {
        if (fsscCommonBaiwangService == null) {
            fsscCommonBaiwangService = (IFsscCommonBaiwangService) SpringBeanUtil.getBean("fsscCommonBaiwangService");
        }
        return fsscCommonBaiwangService;
    }
    public IFsscCommonNuoService fsscCommonNuoService;

    public IFsscCommonNuoService getFsscCommonNuoService() {
        if (fsscCommonNuoService == null) {
            fsscCommonNuoService = (IFsscCommonNuoService) SpringBeanUtil.getBean("fsscCommonNuoService");
        }
        return fsscCommonNuoService;
    }

    public IFsscCommonCreditService fsscCommonCreditService;

    public IFsscCommonCreditService getFsscCommonCreditService() {
        if (fsscCommonCreditService == null) {
            fsscCommonCreditService = (IFsscCommonCreditService) SpringBeanUtil.getBean("fsscCreditCommonService");
        }
        return fsscCommonCreditService;
    }

    public IFsscCommonPresService fsscCommonPresService;

    public IFsscCommonPresService getFsscCommonPresService() {
        if (fsscCommonPresService == null) {
            fsscCommonPresService = (IFsscCommonPresService) SpringBeanUtil.getBean("fsscPresCommonService");
        }
        return fsscCommonPresService;
    }

    private ISysArchivesSignService sysArchivesSignService;

    public ISysArchivesSignService getSysArchivesSignService() {
        if (sysArchivesSignService == null) {
            sysArchivesSignService = (ISysArchivesSignService) getBean(
                    "sysArchivesSignService");
        }
        return sysArchivesSignService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        FsscCommonProcessUtil.buildLbpmHanderHql(hqlInfo, request, "fsscExpenseMain");
        FsscCommonUtil.getCommonWhereBlock(request,hqlInfo,"fsscExpenseMain.fdCompany.fdName","fdCompanyName","like"," and ");
        FsscCommonUtil.getCommonWhereBlock(request,hqlInfo,"fsscExpenseMain.fdProject.fdName","fdProjectName","like"," and ");
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        FsscExpenseMainForm fsscExpenseMainForm = (FsscExpenseMainForm) super.createNewForm(mapping, form, request,
                response);
        ((IFsscExpenseMainService) getServiceImp(request)).initFormSetting((IExtendForm) form,
                new RequestContext(request));
        String docTemplateName = fsscExpenseMainForm.getDocTemplateName();
        FsscExpenseCategory tem = (FsscExpenseCategory) getServiceImp(request).findByPrimaryKey(fsscExpenseMainForm.getDocTemplateId(),FsscExpenseCategory.class,true);
        while (tem.getFdParent() != null) {
            tem = (FsscExpenseCategory) tem.getFdParent();
            docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
        }
        request.setAttribute("docTemplateName", docTemplateName);
        return fsscExpenseMainForm;
    }

    @Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        super.loadActionForm(mapping, form, request, response);
        String fdId = request.getParameter("fdId");
        FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
        FsscExpenseCategory tem = main.getDocTemplate();
        String docTemplateName = tem.getFdName();
        while (tem.getFdParent() != null) {
            tem = (FsscExpenseCategory) tem.getFdParent();
            docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
        }
        request.setAttribute("docTemplateName", docTemplateName);
        request.setAttribute("docTemplate", main.getDocTemplate());
        if (main.getFdCompany() != null) {
            String fdDeduRule = EopBasedataFsscUtil.getDetailPropertyValue(main.getFdCompany().getFdId(), "fdDeduRule");
            if (StringUtil.isNull(fdDeduRule)) {
                fdDeduRule = "1"; // 为空则默认为含税金额，保留原有逻辑
            }
            request.setAttribute("fdDeduFlag", fdDeduRule);
        }
        String fdIsAuthorize=EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
        if(StringUtil.isNull(fdIsAuthorize)){
            fdIsAuthorize="true";  //默认启用提单转授权
        }
        request.setAttribute("fdIsAuthorize", fdIsAuthorize);
        if(FsscCommonUtil.checkHasModule("/fssc/fee/")) {
            List<Object[]> ledgerList=getServiceImp(request).getBaseDao().getHibernateSession().createQuery("select fdDetailId,sum(fdBudgetMoney) from FsscFeeLedger where fdModelId=:fdModelId and fdType='2' group by fdDetailId")
                    .setParameter("fdModelId", main.getFdId()).list();  //占过事前的台账
            JSONObject ledgerObj=new JSONObject();
            for(int n=0,size=ledgerList.size();n<size;n++) {
                Object[] obj=ledgerList.get(n);
                ledgerObj.put(obj[0], obj[1]);
            }
            request.setAttribute("feeLedgerObj", ledgerObj);
        }
        if(FsscCommonUtil.checkHasModule("/fssc/budget/")) {
            List<Object[]> budgetList=getServiceImp(request).getBaseDao().getHibernateSession().createQuery("select fdDetailId,fdBudgetId,sum(fdMoney) from FsscBudgetExecute where fdModelId=:fdModelId and fdType='2' group by fdDetailId,fdBudgetId")
                    .setParameter("fdModelId", main.getFdId()).list();  //占过预算的信息
            JSONObject budgetObj=new JSONObject();
            for(int n=0,size=budgetList.size();n<size;n++) {
                Object[] obj=budgetList.get(n);
                if(budgetObj.containsKey(obj[0])) {
                    Double old_value=budgetObj.optDouble(String.valueOf(obj[0]), 0.0);
                    Double new_value=obj[2]!=null?Double.parseDouble(String.valueOf(obj[2])):0.0;
                    if(new_value-old_value>0) {
                        budgetObj.put(obj[0], new_value); //占用年度季度月度，如果月份占用了多个的话,统计出来的年度金额肯定是最大的，若是单个月度金额，那和年度一致。项目等预算只会存在一个。
                    }

                }else {
                    budgetObj.put(obj[0], obj[2]); //占用多个预算，
                }
            }
            request.setAttribute("budgetObj", budgetObj);
        }
        //查询当前单据有无未申诉的信用扣分记录
        if(FsscCommonUtil.checkHasModule("/fssc/credit/")) {
            Boolean fdIsAppealed = getFsscCommonCreditService().getFdRecordIsAppealed(fdId, FsscExpenseMain.class.getName());
            request.setAttribute("fdIsAppealed", fdIsAppealed);
        }
        //查询当前单据有无交退单记录
        if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
            Boolean hasPres = getFsscCommonPresService().getIsHasPres(fdId, FsscExpenseMain.class.getName());
            request.setAttribute("hasPres", hasPres);
        }
    }

    public ActionForward printFileDocArchives(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        KmssMessages messages = new KmssMessages();
        String pageType=request.getParameter("pageType");
        try {
            String sign = request.getParameter("Signature");
            String fdId = request.getParameter("fdId");
            String expires = request.getParameter("Expires");
            if(!getSysArchivesSignService().validateArchivesSignature(expires, fdId, sign, logger)){
                messages.addError(new Exception("Signature is invailid."));
                return mapping.findForward("failure");
            }
            HtmlToMht.setLocaleWhenExport(request);
            loadActionForm(mapping, form, request, response);
            String saveApprovalStr = request.getParameter("saveApproval");
            boolean saveApproval = "1".equals(saveApprovalStr);
            request.setAttribute("saveApproval", saveApproval);

            FsscExpenseMainForm rtnForm = null;
            String id = request.getParameter("fdId");
            if (!StringUtil.isNull(id)) {
                FsscExpenseMain model = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(id, null, true);
                request.setAttribute("fsscExpenseMain", model);
                request.setAttribute("docTemplate",model.getDocTemplate());
                request.setAttribute("fdPrintTime",
                        DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));// 打印时间
                if(model.getFdTotalApprovedMoney()!=null) {
                    BigDecimal bd=new BigDecimal(model.getFdTotalApprovedMoney());
                    request.setAttribute("fdTotalApprovedMoney", bd.toPlainString());//核准金额
                }
                Double offsetMoney =0.0;
                if(null !=model.getFdOffsetList()){
                    for(FsscExpenseOffsetLoan offset: model.getFdOffsetList()){
                        if(null !=offset.getFdOffsetMoney() && offset.getFdOffsetMoney()>0) {
                            offsetMoney = FsscNumberUtil.getAddition(offsetMoney, offset.getFdOffsetMoney());
                        }
                    }
                }
                request.setAttribute("offsetMoney", offsetMoney);
                Double accountMoney =0.0;
                if(null !=model.getFdAccountsList()){
                    for(FsscExpenseAccounts account: model.getFdAccountsList()){
                        accountMoney = FsscNumberUtil.getAddition(accountMoney, account.getFdMoney()==null?0d:account.getFdMoney());
                    }
                }
                request.setAttribute("accountMoney", accountMoney);

                if (model != null) {
                    rtnForm = (FsscExpenseMainForm) getServiceImp(request).convertModelToForm((IExtendForm) form, model,
                            new RequestContext(request));
                }
            }

            //出纳付款单明细
            ((IFsscCommonCashierPaymentService)getFsscCommonCashierPaymentService()).addFileDocList(request,fdId);


            //交单记录
            if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
                JSONArray presList = getFsscCommonPresService().getPresData(fdId, FsscExpenseMain.class.getName());
                request.setAttribute("presList", presList);
            }

        } catch (Exception e) {
            messages.addError(e);
        }
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_RETURN).save(request);
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else{
            if("cashier".equals(pageType)){
                request.setAttribute("form",form);
                return getActionForward("cashierPrint", mapping, form, request, response);
            }else if("voucher".equals(pageType)){
                request.setAttribute("form",form);
                request.setAttribute("fdModelName",FsscExpenseMain.class.getName());
                return getActionForward("voucherPrint", mapping, form, request, response);
            }else{
                return getActionForward("expenseArch", mapping, form, request,
                        response);
            }
        }

    }
}
