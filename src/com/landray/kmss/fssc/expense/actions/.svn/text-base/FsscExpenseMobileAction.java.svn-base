package com.landray.kmss.fssc.expense.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataAuthorizeService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBaiwangService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonExpenseService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonNuoService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonPresService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseOffsetLoan;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMobileService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.mobile.IMobileAddressService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.workflow.engine.WorkflowEngineContext;
import com.landray.kmss.sys.workflow.interfaces.ISysWfProcessSubService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 报销移动端action
 * @author zhongcx
 *
 */

public class FsscExpenseMobileAction extends ExtendAction{

	protected  IFsscExpenseMobileService fsscExpenseMobileService;
	
	@Override
	protected IFsscExpenseMobileService getServiceImp(HttpServletRequest request) {
		if (fsscExpenseMobileService == null) {
			fsscExpenseMobileService = (IFsscExpenseMobileService)getBean("fsscExpenseMobileService");
		}
		return  fsscExpenseMobileService;
	}
	
	protected IFsscExpenseMainService fsscExpenseMainService;
	
	public IFsscExpenseMainService getFsscExpenseMainService() {
		if (fsscExpenseMainService == null) {
			fsscExpenseMainService = (IFsscExpenseMainService)getBean("fsscExpenseMainService");
		}
		return fsscExpenseMainService;
	}

	public IFsscCommonExpenseService fsscCommonExpenseService;
	

	public IFsscCommonExpenseService getFsscCommonExpenseService() {
		if (fsscCommonExpenseService == null) {
			fsscCommonExpenseService = (IFsscCommonExpenseService) getBean("fsscExpenseCommonService");
		}
		return fsscCommonExpenseService;
	}
	
	private IEopBasedataCompanyService eopBasedataCompanyService;

	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if(eopBasedataCompanyService==null){
			eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}
	
	private IEopBasedataCostCenterService eopBasedataCostCenterService;
	
	public IEopBasedataCostCenterService getEopBasedataCostCenterService() {
		if(eopBasedataCostCenterService==null){
			eopBasedataCostCenterService = (IEopBasedataCostCenterService) getBean("eopBasedataCostCenterService");
		}
		return eopBasedataCostCenterService;
	}
	
	protected IMobileAddressService mobileAddressService;

	public IMobileAddressService getMobileAddressService() {
		if (mobileAddressService == null) {
			mobileAddressService = (IMobileAddressService)getBean("mobileAddressService");
		}
		return mobileAddressService;
	}
	
	private ISysAttMainCoreInnerService sysAttMainService;

    public ISysAttMainCoreInnerService getSysAttMainService() {
    	if (sysAttMainService == null) {
    		sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
		return sysAttMainService;
	}
    
	protected IBackgroundAuthService backgroundAuthService;
	
	public IBackgroundAuthService getBackgroundAuthService() {
		if (backgroundAuthService == null) {
			backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil.getBean("backgroundAuthService");
		}
		return backgroundAuthService;
	}
	
	public ISysWfProcessSubService sysWfProcessSubService;
	
	public ISysWfProcessSubService getSysWfProcessSubService() {
		if (sysWfProcessSubService == null) {
			sysWfProcessSubService = (ISysWfProcessSubService) SpringBeanUtil.getBean("sysWfProcessSubService");
		}
		return  sysWfProcessSubService;
	}
	
	public IEopBasedataAuthorizeService eopBasedataAuthorizeService;
	
    public IEopBasedataAuthorizeService getEopBasedataAuthorizeService() {
	   if (eopBasedataAuthorizeService == null) {
		   eopBasedataAuthorizeService = (IEopBasedataAuthorizeService) SpringBeanUtil.getBean("eopBasedataAuthorizeService");
		}
		return eopBasedataAuthorizeService;
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

    private ISysNumberFlowService sysNumberFlowService;
	
	public ISysNumberFlowService getSysNumberFlowService() {
		if (sysNumberFlowService == null) {
			sysNumberFlowService = (ISysNumberFlowService) getBean("sysNumberFlowService");
        }
		return sysNumberFlowService;
	}

	public IFsscCommonPresService fsscCommonPresService;

	public IFsscCommonPresService getFsscCommonPresService() {
		if (fsscCommonPresService == null) {
			fsscCommonPresService = (IFsscCommonPresService) SpringBeanUtil.getBean("fsscPresCommonService");
		}
		return fsscCommonPresService;
	}


   @Override
   public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscExpenseMainForm fsscExpenseMainForm = (FsscExpenseMainForm) super.createNewForm(mapping, form, request, response);
        ((IFsscExpenseMobileService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        request.setAttribute("isShowDraftsmanStatus", EopBasedataFsscUtil.getIsEnableDraftorStatus());
		request.setAttribute("isMulClaimantStatus", EopBasedataFsscUtil.isMulClaimantStatu(UserUtil.getUser().getFdId()));
		request.setAttribute("fdCreatorCheck", EopBasedataFsscUtil.getSwitchValue("fdCreatorCheck"));
        return fsscExpenseMainForm;
    }
	   
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		FsscExpenseMain main = (FsscExpenseMain) getServiceImp(request).findByPrimaryKey(fdId, FsscExpenseMain.class.getName(), true);
		 //设置默认公司
		SysOrgPerson user = UserUtil.getUser();
		JSONArray fdCompanyData = new JSONArray();
        List<EopBasedataCompany> own = getEopBasedataCompanyService().findCompanyByUserId(user.getFdId());
        for (EopBasedataCompany eopBasedataCompany : own) {
    		JSONObject fdCompany  = new JSONObject();
    		fdCompany.put("value",eopBasedataCompany.getFdId());
    		fdCompany.put("text",eopBasedataCompany.getFdName() );
    		fdCompanyData.add(fdCompany);
		}
		//移动端审批加载交单退单记录
		if(FsscCommonUtil.checkHasModule("/fssc/pres/")) {
			JSONArray list = getFsscCommonPresService().getPresData(fdId, FsscExpenseMain.class.getName());
			request.setAttribute("showPres", list.size()>0?true:false);
			request.setAttribute("queryList", list.toString().replaceAll("\"", "'"));
		}
		String fdIsAuthorize=EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
		if(StringUtil.isNull(fdIsAuthorize)){
			fdIsAuthorize="true";  //默认启用提单转授权
		}
		request.setAttribute("fdIsAuthorize", fdIsAuthorize);
		Double offsetMoney =0.0;
		if(null !=main.getFdOffsetList()){
			for(FsscExpenseOffsetLoan offset: main.getFdOffsetList()){
				if(null !=offset.getFdOffsetMoney() && offset.getFdOffsetMoney()>0) {
					offsetMoney = FsscNumberUtil.getAddition(offsetMoney, offset.getFdOffsetMoney());
				}
			}
		}
		request.setAttribute("offsetMoney", offsetMoney);
        //附件初始化
		request.setAttribute("fdAttData", getAttData(fdId));
		request.setAttribute("fdCompanyData", fdCompanyData);
		request.setAttribute("docTemplate", main.getDocTemplate());
		request.setAttribute("isShowDraftsmanStatus", EopBasedataFsscUtil.getIsEnableDraftorStatus());
		request.setAttribute("isMulClaimantStatus", EopBasedataFsscUtil.isMulClaimantStatu(UserUtil.getUser().getFdId()));
		request.setAttribute("fdCreatorCheck", EopBasedataFsscUtil.getSwitchValue("fdCreatorCheck"));
	}
	
	public List<Map<String, String>> getAttData(String fdId) throws Exception {
		List<Map<String,String>> rtnList=new ArrayList<>();
		List<SysAttMain> attList=getEopBasedataCompanyService().getBaseDao().getHibernateSession().createQuery("select t from SysAttMain t where t.fdModelId=:fdModelId")
				.setParameter("fdModelId", fdId).list();
		Map<String,String> iconMap=new HashMap();
	    String[] iconArr=new String[]{"aiv","aud","bmp","documents","excel","gif","html","image","jpg","js","movie","mp4","outlook","pdf","png","ppt","text","tif","video","visio","vsd","word","zip"};
	    for(int i=0,len=iconArr.length;i<len;i++){
		  iconMap.put(iconArr[i],iconArr[i]);
	    }
		for(SysAttMain att:attList){
			Map<String,String> map=new HashMap<>();
			String fileName=att.getFdFileName();
			if(StringUtil.isNotNull(fileName)){
				String suffix_name=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				String icon_name="";
				if("doc".equals(suffix_name)){
					  icon_name="word";
				  }else if("xlsx".equals(suffix_name)||"xls".equals(suffix_name)){
					  icon_name="excel";
				  }else if("pptx".equals(suffix_name)||"ppt".equals(suffix_name)){
					  icon_name="ppt";
				  }else{
					  icon_name= iconMap.get(suffix_name);
				  }
				  if(StringUtil.isNull(icon_name)){
					  icon_name="documents";
				  }
				map.put("fdId", att.getFdId());
				map.put("icon", icon_name);
				map.put("fileName", fileName);
				rtnList.add(map);
			}
		}
		return rtnList;
	}
	
	/**
	 * 
	 * 保存编辑
	 * 
	 */
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String docStatus=request.getParameter("status");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			FsscExpenseMainForm mainForm=(FsscExpenseMainForm) form;
			mainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			request.setAttribute("fsscExpenseMainForm", form);
			FsscExpenseMain main = (FsscExpenseMain)getServiceImp(request).convertFormToModel((IExtendForm)form, null, new RequestContext());
			SysOrgPerson user = UserUtil.getUser();
			if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {//暂存才后台直接启动流程，其他跳转流程页面提交流程
				//启动流程
				getBackgroundAuthService().switchUserById(user.getFdId(), new Runner() {
					@Override
                    public Object run(Object parameter) throws Exception {
						WorkflowEngineContext subContext = getSysWfProcessSubService().init(
								(IBaseModel) parameter, "fsscExpenseMain", (IBaseModel) parameter, "fsscExpenseMain");
						getSysWfProcessSubService().doAction(subContext, (IBaseModel) parameter);
						return null;
					}
				}, main);
			}else {
				main.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);  //设置为起草，由流程去修改状态
				main.setDocCreator(null);;  //设置为起草，由流程去修改状态
				request.setAttribute("fdBillId", main.getFdId());
				((IFsscExpenseMobileService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
			}
			List<FsscExpenseDetail> list = main.getFdDetailList();
			if(null!=list){
				Map<String,String> map = new HashMap<String,String>();
				for (FsscExpenseDetail object : list) {
					if(StringUtil.isNull(object.getFdNoteId())){
						continue;
					}
					map.put(object.getFdNoteId(),object.getFdId());
				}
				//更新记一笔关联信息
				getServiceImp(request).updateRelation(map);
			}
			String[] attId = request.getParameterValues("attId");
			if(attId!=null&&attId.length>0){
				getServiceImp(request).updateAttachmentRelation(attId,main.getFdId());
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()){
   			return getActionForward("failure", mapping, form, request, response);
		} else {
			if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {//暂存返回列表，提交跳转流程页面，二次提交
				return new ActionForward("/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=data",true);
			}else {
				return getActionForward("editTemp", mapping, form, request, response);
			}
   		}
	}
	
	/**
	 * 提交前更新单据创建者信息和流程状态信息
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			try {
				String params=request.getParameter("params");
				if(StringUtil.isNotNull(params)) {
					JSONObject paramJson=JSONObject.fromObject(params);
					String fdBillId=paramJson.optString("fdBillId");
					FsscExpenseMain main=(FsscExpenseMain) getFsscExpenseMainService().findByPrimaryKey(fdBillId,FsscExpenseMain.class,true);
					main.setDocStatus(paramJson.optString("docStatus",SysDocConstant.DOC_STATUS_DRAFT));
					main.setDocCreator(UserUtil.getUser());
					main.setDocNumber(getSysNumberFlowService().generateFlowNumber(main));
					getFsscExpenseMainService().update(main);
				}
			} catch (Exception e) {
				messages.addError(e);
			}
		} catch (Exception e) {
			
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}	
	
	
	/**
	 * 
	 * 保存编辑
	 * 
	 */
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String docStatus=request.getParameter("status");
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			FsscExpenseMainForm mainForm=(FsscExpenseMainForm) form;
			mainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);  //设置为起草，由流程去修改状态
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			request.setAttribute("fsscExpenseMainForm", form);
			super.loadActionForm(mapping, form, request, response);
			String[] attId = request.getParameterValues("attId");
			String fdId = request.getParameter("fdId");
			request.setAttribute("fdBillId", fdId);
			if(attId!=null&&attId.length>0){
				getServiceImp(request).updateAttachmentRelation(attId,fdId);
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
   			if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {//暂存返回列表，提交跳转流程页面，二次提交
				return new ActionForward("/fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=data",true);
			}else {
				return getActionForward("editTemp", mapping, form, request, response);
			}
   		}
	}
	
	
	/**
	 * 报销列表
	 */
	@Override
    public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject expenseCastlist = getServiceImp(request).getExpenseCateList(request);
			JSONObject expenseMainList = getServiceImp(request).getExpenseMainList(request);
			request.setAttribute("expenseCastlist", expenseCastlist.get("data"));//报销类别
			request.setAttribute("expenseMainList", expenseMainList.get("data"));//报销类别
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request,response);
		}
	}
	
	/**
	 * 组织架构选择人员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addressList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List addressList = getMobileAddressService().addressList(new RequestContext(request));
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONArray.fromObject(addressList).toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
	
	/**
	 * 获取上一个节点的parentId
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward parentNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		KmssMessages messages = new KmssMessages();
		try {
			//查找当前人员的部门的id
			String parentId  = request.getParameter("parentId");
			JSONObject obj = getServiceImp(request).getParentId(parentId);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
	
	/**
	 * 保存附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAtt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject jsonObject = getServiceImp(request).saveAtt(request);
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}
	
	/**
	 * 移除附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAtt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject jsonObject = getServiceImp(request).deleteAtt(request);
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findExpenseTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject jsonObject = getServiceImp(request).getExpenseCateList(request);
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}

	/**
	 * 获取已授权给当前登录人的报销人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFdClaimant(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String flag = request.getParameter("flag");
		try{
			JSONObject param=new JSONObject();
			param.put("fdPersonId", UserUtil.getUser().getFdId());
			param.put("keyword", request.getParameter("keyword"));
			List<SysOrgPerson> authorizeByList=getEopBasedataAuthorizeService().getAuthorizeByList(param.toString());
			JSONObject rtn = new JSONObject();
			JSONArray dataArr=new JSONArray();
			for(SysOrgPerson person:authorizeByList){
				JSONObject obj=new JSONObject();
				obj.put("value", person.getFdId());
				obj.put("text", person.getFdName());
				dataArr.add(obj);
			}
			rtn.put("data", dataArr);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(rtn.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		if("mobile".equals(flag)){
			return null;
		} else {
			return getActionForward("listLoan", mapping, form, request, response);
		}
	}
	/**
	 * 获取进项税率
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFdInputTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn=new JSONObject();
		try{
			JSONObject param=new JSONObject();
			param.put("fdCompanyId", request.getParameter("fdCompanyId"));
			param.put("fdExpenseItemId", request.getParameter("fdExpenseItemId"));
			JSONArray rtnArr=getServiceImp(request).getFdInputTax(param);
			rtn.put("data", rtnArr);
			rtn.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			rtn.put("result", "failure");
			rtn.put("message", e.getStackTrace());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	
	/**
	 * 随手记保存发票信息自动验真
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject jsonObject = new JSONObject();
		try {
			String fdInvVerCpy = EopBasedataFsscUtil.getSwitchValue("fdInvVerCpy");
			JSONObject rtn =null;
			if("2".equals(fdInvVerCpy)){
				rtn = getFsscCommonNuoService().checkInvoices(request);
			}else {
				rtn = getFsscCommonBaiwangService().checkInvoices(request);
			}
			jsonObject.put("result", "success");
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(rtn.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else{
   			return null;
   		}
	}

}
