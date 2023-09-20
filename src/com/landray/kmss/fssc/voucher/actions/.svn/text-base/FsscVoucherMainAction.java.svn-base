package com.landray.kmss.fssc.voucher.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPayBankService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonU8Service;
import com.landray.kmss.fssc.voucher.constant.FsscVoucherConstant;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherDetailForm;
import com.landray.kmss.fssc.voucher.forms.FsscVoucherMainForm;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class FsscVoucherMainAction extends ExtendAction {

    private IFsscVoucherMainService fsscVoucherMainService;

    private IFsscCommonU8Service fsscCommonU8Service;

    public IFsscCommonU8Service getFsscCommonU8Service() {
        if (fsscCommonU8Service == null) {
            fsscCommonU8Service = (IFsscCommonU8Service) getBean("fsscCommonU8Service");
        }
        return fsscCommonU8Service;
    }

    @Override
    public IFsscVoucherMainService getServiceImp(HttpServletRequest request) {
        if (fsscVoucherMainService == null) {
            fsscVoucherMainService = (IFsscVoucherMainService) getBean("fsscVoucherMainService");
        }
        return fsscVoucherMainService;
    }
    
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	 if (eopBasedataCompanyService == null) {
    		 eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
         }
		return eopBasedataCompanyService;
	}
    
    private IEopBasedataPayBankService eopBasedataPayBankService;
	
    public IEopBasedataPayBankService getEopBasedataPayBankService() {
    	 if (eopBasedataPayBankService == null) {
    		 eopBasedataPayBankService = (IEopBasedataPayBankService) getBean("eopBasedataPayBankService");
         }
		return eopBasedataPayBankService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	SysOrgElement user=UserUtil.getUser();
    	if(!UserUtil.getKMSSUser().isAdmin()){
    		if(UserUtil.checkRole("ROLE_FSSCVOUCHER_VIEW")){//查看权限
    			String tempWhere="";
        		//公司财务人员、财务管理员
        		List<String> companyIdList=EopBasedataAuthUtil.getCompanyListAuth(user.getFdId());
    			if(!ArrayUtil.isEmpty(companyIdList)){
    				tempWhere=HQLUtil.buildLogicIN("fsscVoucherMain.fdCompany.fdId", companyIdList);
    			}
    			//只能看自己创建的
    			tempWhere=StringUtil.linkString(tempWhere, " or ", "fsscVoucherMain.docCreator.fdId=:userId");
        		hqlInfo.setParameter("userId", user.getFdId());
        		whereBlock=StringUtil.linkString(whereBlock, " and ", "("+tempWhere+")");
    		}else {
    			//只能看自己创建的
        		whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscVoucherMain.docCreator.fdId=:userId");
        		hqlInfo.setParameter("userId", user.getFdId());
    		}
    	}
    	String fdCompanyName = request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)){
        	whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscVoucherMain.fdCompany.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName","%"+fdCompanyName+"%");
        }
    	hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscVoucherMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscVoucherMainForm fsscVoucherMainForm = (FsscVoucherMainForm) super.createNewForm(mapping, form, request, response);
        ((IFsscVoucherMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscVoucherMainForm;
    }


    /**
     * 根据http请求，获取model，将model转化为form并返回。<br>
     * URL中必须包含fdId参数，该参数为记录id。<br>
     * 若获取model不成功，则抛出errors.norecord的错误信息。
     *
     * @param form
     * @param request
     * @return form对象
     * @throws Exception
     */
    @Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        super.loadActionForm(mapping, form, request, response);
        FsscVoucherMainForm fsscVoucherMainForm = (FsscVoucherMainForm) form;
        List<FsscVoucherDetailForm> list=fsscVoucherMainForm.getFdDetail_Form();
        for(FsscVoucherDetailForm detailForm:list){
			if(detailForm.getFdBasePayBankName()!=null){
             EopBasedataPayBank eopBasedataPayBank=(EopBasedataPayBank)getEopBasedataPayBankService().findByPrimaryKey(detailForm.getFdBasePayBankId());
        	detailForm.setFdBasePayBankName(detailForm.getFdBasePayBankName()+"("+eopBasedataPayBank.getFdBankAccount()+")");
			}
        }
        if(StringUtil.isNotNull(fsscVoucherMainForm.getFdModelId()) && StringUtil.isNotNull(fsscVoucherMainForm.getFdModelName())){
            SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMainForm.getFdModelName());
            if(dict != null){
                fsscVoucherMainForm.setFdModelUrl(dict.getUrl().replace("${fdId}", fsscVoucherMainForm.getFdModelId()));
            }
        }
        EopBasedataCompany eopBasedataCompany=(EopBasedataCompany)getEopBasedataCompanyService().findByPrimaryKey(fsscVoucherMainForm.getFdCompanyId());
        //是否勾选了SAP
        request.setAttribute("fdIsChechedSAP", eopBasedataCompany.getFdJoinSystem());
    }

    /**
     * 新增凭证
     */
    public ActionForward addVoucher(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-add", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            ActionForm newForm = createNewForm(mapping, form, request, response);
            String fdModelId = request.getParameter("fdModelId");
            String fdModelName = request.getParameter("fdModelName");
            String fdModelNumber = request.getParameter("fdModelNumber");
            String fdPushType=request.getParameter("fdPushType");
            FsscVoucherMainForm voucherForm = (FsscVoucherMainForm) form;
            if(StringUtil.isNotNull(fdModelId) && StringUtil.isNotNull(fdModelName)){
                SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
                if(dict != null){
                    voucherForm.setFdModelUrl(dict.getUrl().replace("${fdId}", fdModelId));
                }
            }
            voucherForm.setFdModelId(fdModelId);
            voucherForm.setFdModelName(fdModelName);
            voucherForm.setFdModelNumber(fdModelNumber);
            voucherForm.setFdPushType(fdPushType);
            voucherForm.setMethod_GET("add");
            if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-add", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("edit", mapping, form, request, response);
        }
    }


    /**
     * 打开编辑页面。<br>
     * URL中必须包含fdId参数，该参数为记录id。<br>
     * 该操作一般以HTTP的GET方式触发。
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return 执行成功，返回edit页面，否则返回failure页面
     * @throws Exception
     */
    @Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-edit", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            loadActionForm(mapping, form, request, response);
            FsscVoucherMainForm fsscVoucherMainForm = (FsscVoucherMainForm) form;
            if(!(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10.equals(fsscVoucherMainForm.getFdBookkeepingStatus())
                 || FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11.equals(fsscVoucherMainForm.getFdBookkeepingStatus()))){
                //如果不是待记账或者记账失败，就不允许修改，直接跳到view页面
                ActionForward forward = new ActionForward();
                forward.setPath(SysDataDict.getInstance().getModel(FsscVoucherMain.class.getName()).getUrl().replace("${fdId}", fsscVoucherMainForm.getFdId()));
                forward.setRedirect(true);//重定向
                return forward;
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-edit", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("edit", mapping, form, request, response);
        }
    }

    /**
     * 记账
     */
    public ActionForward bookkeeping(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JSONObject jsonObject = new JSONObject();
        String message = "";
        List<FsscVoucherMain> fsscVoucherMainList = new ArrayList<FsscVoucherMain>();
        try {
            String fdVoucherMainId = request.getParameter("fdVoucherMainId");
            if(StringUtil.isNotNull(fdVoucherMainId)){
                fsscVoucherMainList.add((FsscVoucherMain) getServiceImp(request).findByPrimaryKey(fdVoucherMainId));
            }else{
                String fdModelId = request.getParameter("fdModelId");
                String fdModelName = request.getParameter("fdModelName");
                List<FsscVoucherMain> mainList = getServiceImp(request).findNoBookkeeping(fdModelId, fdModelName, FsscVoucherConstant.FSSC_VOUCHER_FD_PUSH_TYPE_1, FsscVoucherConstant.FSSC_VOUCHER_FD_IS_AMORTIZE_FALSE);
                if(!ArrayUtil.isEmpty(mainList)){
                    fsscVoucherMainList.addAll(mainList);
                }
            }
            if(ArrayUtil.isEmpty(fsscVoucherMainList)){
                //找不到对应的凭证，请检查！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.null.error", "fssc-voucher"));
            }else if(!(fsscVoucherMainList.size() == 1 || fsscVoucherMainList.size() == 2)){
                //对应的凭证的数量异常：查询到%number%个凭证，请检查！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.number.error", "fssc-voucher").replace("%number%", fsscVoucherMainList.size()+""));
            }else{
                Map<String, String> map = getServiceImp(request).updateBookkeeping(fsscVoucherMainList);
                message = map.get("message");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message += e.getMessage()+"<br/>";
        }
        boolean fdIsBoolean = false;
        if(StringUtil.isNull(message)){
            fdIsBoolean = true;
            //操作成功
            message = ResourceUtil.getString("bookkeeping.success", "fssc-voucher");
        }
        jsonObject.put("fdIsBoolean", fdIsBoolean);//true 记账成功 false 记账失败
        jsonObject.put("message", message);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
        return null;
    }

    /**
     * 重新制证
     */
    public ActionForward refreshVoucher(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JSONObject jsonObject = new JSONObject();
        String fdModelId = request.getParameter("fdModelId");
        String fdModelName = request.getParameter("fdModelName");

        String message = "";
        boolean fdIsBoolean = true;
        try{
            IBaseModel model = getServiceImp(request).findByPrimaryKey(fdModelId, fdModelName, true);
            getServiceImp(request).addOrUpdateVoucher(model);
        }catch (Exception e){
            e.printStackTrace();
            fdIsBoolean = false;
            message = e.getMessage();
        }
        jsonObject.put("fdIsBoolean", fdIsBoolean+"");
        jsonObject.put("messageStr", message);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
        return null;
    }
    
    /**
	 * 校验是否选中已记账单据
	 */
	public ActionForward checkDeleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		if(!EopBasedataFsscUtil.checkStatus(ids, FsscVoucherMain.class.getName(), "fdBookkeepingStatus", "30")){
			messages.addError(new KmssMessage(ResourceUtil.getString("fssc.common.examine.or.publish.delete.tips", "fssc-common")));	
		}
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	/**
	 * 批量记账
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ActionForward batchBookkeeping(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
		 String fdVoucherMainIds = request.getParameter("fdVoucherMainIds");
		 JSONObject jsonObject = new JSONObject();
		 boolean fdIsBoolean = false;
	      String message = "";
		 try{
			 Map<String, String> map = getServiceImp(request).updateBatchBookkeeping(fdVoucherMainIds);
			 message = map.get("message");
		 }catch (Exception e){
            e.printStackTrace();
            fdIsBoolean = false;
            message = e.getMessage();
	      }
       
        jsonObject.put("fdIsBoolean", fdIsBoolean+"");
        jsonObject.put("messageStr", message);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
		 return null;
	}
}
