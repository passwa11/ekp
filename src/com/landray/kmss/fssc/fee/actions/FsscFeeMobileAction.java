package com.landray.kmss.fssc.fee.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.exception.UnexpectedRequestException;
import net.sf.json.JSON;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetMatchService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.PdaFlagUtil;
import com.landray.kmss.fssc.fee.forms.FsscFeeMainForm;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.fssc.fee.service.spring.FsscFeeMobileService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscFeeMobileAction extends ExtendAction {
	
	private IFsscFeeMainService fsscFeeMainService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeMainService == null) {
            fsscFeeMainService = (IFsscFeeMainService) getBean("fsscFeeMainService");
        }
        return fsscFeeMainService;
    }
    
    private IFsscCommonBudgetMatchService fsscCommonBudgetService;

	public IFsscCommonBudgetMatchService getFsscCommonBudgetService() {
		if (fsscCommonBudgetService == null) {
			fsscCommonBudgetService = (IFsscCommonBudgetMatchService) getBean("fsscBudgetMatchService");
        }
		return fsscCommonBudgetService;
	}
	
    
    private IEopBasedataStandardService eopBasedataStandardService;

    public IEopBasedataStandardService getEopBasedataStandardService() {
    	if (eopBasedataStandardService == null) {
    		eopBasedataStandardService = (IEopBasedataStandardService) getBean("eopBasedataStandardService");
        }
		return eopBasedataStandardService;
	}
    
    private FsscFeeMobileService fsscFeeMobileService;
    

	public FsscFeeMobileService getFsscFeeMobileService() {
		if (fsscFeeMobileService == null) {
			fsscFeeMobileService = (FsscFeeMobileService) getBean("fsscFeeMobileService");
        }
		return fsscFeeMobileService;
	}
	
	private ISysNumberFlowService sysNumberFlowService;
	
	public ISysNumberFlowService getSysNumberFlowService() {
		if (sysNumberFlowService == null) {
			sysNumberFlowService = (ISysNumberFlowService) getBean("sysNumberFlowService");
        }
		return sysNumberFlowService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeMain.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }
	
	@Override
    public ActionForward data(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			super.data(mapping, form, request, response);
			Page page=(Page) request.getAttribute("queryPage");
			Map moreInfo=getFsscFeeMobileService().getMoreInfo(page);
			request.setAttribute("moreInfo", moreInfo);
			request.setAttribute("feeTemplateList",getFsscFeeMobileService().getTemplate(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}
	
	public ActionForward moreData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			super.data(mapping, form, request, response);
			Page page=(Page) request.getAttribute("queryPage");
			Map moreInfo=getFsscFeeMobileService().getMoreInfo(page);
			request.setAttribute("moreInfo", moreInfo);
		} catch (Exception e) {
			messages.addError(e);
		}
		
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
			.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("moreData", mapping, form, request, response);
		}
	}

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscFeeMainForm fsscFeeMainForm = (FsscFeeMainForm) super.createNewForm(mapping, form, request, response);
        ((IFsscFeeMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        String docTemplateId = request.getParameter("i.docTemplate");
        FsscFeeTemplate temp = (FsscFeeTemplate) getServiceImp(request).findByPrimaryKey(docTemplateId, FsscFeeTemplate.class, true);
        request.setAttribute("docTemplate", temp);
        Map fieldMap=getFsscFeeMobileService().getFormFields(request);
        if(fieldMap.containsKey("mainFieldList")){
        	 request.setAttribute("mainFieldList",fieldMap.get("mainFieldList"));
        }
        if(fieldMap.containsKey("detailFieldList")){
        	 request.setAttribute("detailFieldMap",fieldMap.get("detailFieldList"));
        }
        if(fieldMap.containsKey("budgetMatchList")){
        	request.setAttribute("budgetMatchList",fieldMap.get("budgetMatchList"));
        }
        if(fieldMap.containsKey("displayList")){
        	request.setAttribute("displayList",fieldMap.get("displayList"));
        }
        if(fieldMap.containsKey("funcMap")){
        	request.setAttribute("funcMap",fieldMap.get("funcMap"));
        }
        if(fieldMap.containsKey("fdCompanyId")){
        	request.setAttribute("fdCompanyId",fieldMap.get("fdCompanyId"));
        }
        String fdMappFeild=getFsscFeeMobileService().findMappFeild(docTemplateId);
        if(StringUtil.isNotNull(fdMappFeild)){
        	request.setAttribute("fdMappFeild",fdMappFeild);
        }
        if(PdaFlagUtil.checkIsPdaLogin(request)){
			request.setAttribute("isShowDraftsmanStatus", EopBasedataFsscUtil.getIsEnableDraftorStatus());
			request.setAttribute("isMulClaimantStatus", EopBasedataFsscUtil.isMulClaimantStatu(UserUtil.getUser().getFdId()));
		}
        HashMap map=(HashMap) fieldMap.get("expressMap");
        request.setAttribute("expressMap",map);
        return fsscFeeMainForm;
    }
    
    @Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	if(PdaFlagUtil.checkIsPdaLogin(request)){
			request.setAttribute("isShowDraftsmanStatus", EopBasedataFsscUtil.getIsEnableDraftorStatus());
			request.setAttribute("isMulClaimantStatus", EopBasedataFsscUtil.isMulClaimantStatu(UserUtil.getUser().getFdId()));
		}
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		FsscFeeMain main = (FsscFeeMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		request.setAttribute("docTemplate", main.getDocTemplate());
		SysOrgElement parent=main.getDocCreator()!=null?main.getDocCreator().getFdParent():null;
		if(parent!=null){
			request.setAttribute("docDeptName", parent.getFdName());
		}
		Map fieldMap=getFsscFeeMobileService().getFormFields(request);
        if(fieldMap.containsKey("mainFieldList")){
        	 request.setAttribute("mainFieldList",fieldMap.get("mainFieldList"));
        }
        if(fieldMap.containsKey("detailFieldList")){
        	 request.setAttribute("detailFieldMap",fieldMap.get("detailFieldList"));
        }
        if(fieldMap.containsKey("budgetMatchList")){
        	request.setAttribute("budgetMatchList",fieldMap.get("budgetMatchList"));
        }
        if(fieldMap.containsKey("funcMap")){
        	request.setAttribute("funcMap",fieldMap.get("funcMap"));
        }
        if(fieldMap.containsKey("fdCompanyId")){
        	request.setAttribute("fdCompanyId",fieldMap.get("fdCompanyId"));
        }
        request.setAttribute("expressMap",fieldMap.get("expressMap"));
        if(fieldMap.containsKey("displayList")&&fieldMap.get("displayList")!=null){
        	List<String> disPlayList=(List) fieldMap.get("displayList");
        	if("view".equals(((FsscFeeMainForm) form).getMethod_GET())) {
        		List<String> disPlayList_new=new ArrayList<>();
            	for(String disPlay:disPlayList){
            		String[] diss=disPlay.split("\\.");
            		if(diss.length==2){
            			disPlayList_new.add(diss[1]);
            		}else if(diss.length==1){
            			disPlayList_new.add(diss[0]);
            		}
            	}
            	request.setAttribute("displayList",disPlayList_new);
        	}else if("edit".equals(((FsscFeeMainForm) form).getMethod_GET())) {
        		request.setAttribute("displayList",disPlayList);
        	}
        	request.setAttribute("currencyMap",getFsscFeeMobileService().getCurrencyData());
        }
        String fdMappFeild=getFsscFeeMobileService().findMappFeild(main.getDocTemplate()!=null?main.getDocTemplate().getFdId():"");
        if(StringUtil.isNotNull(fdMappFeild)){
        	JSONObject mapp=JSONObject.fromObject(fdMappFeild.replaceAll("&quot;","\""));
        	String tableId=mapp.optString("fdTableId","");
        	request.setAttribute("fdTableId",tableId);
        	request.setAttribute("fdMappFeild",fdMappFeild);
        }
        request.setAttribute("attData",((IFsscFeeMainService) getServiceImp(request)).getAttData(request));
	}

    /**
	 * 单条明细匹配预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward matchBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String param = request.getParameter("data");
		rtn.put("result", "success");
		try {
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				JSONObject data = JSONObject.fromObject(param);
				JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(data);
				EopBasedataCompany comp = (EopBasedataCompany) getServiceImp(request).findByPrimaryKey(data.getString("fdCompanyId"), EopBasedataCompany.class, true);
				String hql = "from com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate where fdIsAvailable=:fdIsAvailable and fdCompany.fdId=:fdCompanyId and fdSourceCurrency.fdId=:fdSourceCurrencyId and fdTargetCurrency.fdId=:fdTargetCurrencyId";
				Query query = getServiceImp(request).getBaseDao().getHibernateSession().createQuery(hql);
				query.setParameter("fdCompanyId", data.getString("fdCompanyId"));
				query.setParameter("fdIsAvailable", true);
				query.setParameter("fdSourceCurrencyId",data.getString("fdCurrencyId"));
				query.setParameter("fdTargetCurrencyId", comp.getFdBudgetCurrency().getFdId());
				List<EopBasedataExchangeRate> rates = query.list();
				if(ArrayUtil.isEmpty(rates)){
					//如果没有查到相应汇率，但匹配到了预算，提示用户不能申请
					if("2".equals(budget.get("result"))&&budget.getJSONArray("data").size()>0){
						rtn.put("result", "failure");
						rtn.put("message", ResourceUtil.getString("tips.exchangeRateNotExist","fssc-fee"));
					}else{
						rtn.put("budget", budget);
						rtn.put("fdBudgetRate", "0");
					}
				}else{
					rtn.put("budget", budget);
					rtn.put("fdBudgetRate", rates.get(0).getFdRate());
				}
			}else{
				rtn.put("budget", new JSONObject());
			}
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
    
    /**
	 * 查找预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForm getBudgetData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String json = request.getParameter("data");
		try {
			JSONArray param = JSONArray.fromObject(json);
			JSONArray budgets = new JSONArray();
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				for(int i=0;i<param.size();i++){
					JSONObject row = param.getJSONObject(i);
					String fdDetailId = row.getString("index");
					JSONObject budget = getFsscCommonBudgetService().matchFsscBudget(row);
					row.clear();
					row.put("fdDetailId", fdDetailId);
					row.put("budget", budget);
					budgets.add(row);
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(budgets.toString());
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
        return null;
    }
	
	/**
	 * 匹配标准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStandardData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String params = request.getParameter("params");
		rtn.put("result", "success");
		JSONArray object = new JSONArray();
		try {
			JSONArray data = JSONArray.fromObject(params);
			for(int i=0;i<data.size();i++){
				JSONObject obj = getEopBasedataStandardService().getStandardData(data.getJSONObject(i));
				obj.put("index", data.getJSONObject(i).get("index"));
				object.add(obj);
			}
			rtn.put("data", object);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	
	
	/**
	 * 校验是否选中待审、发布单据
	 */
	public ActionForward checkDeleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		if(!EopBasedataFsscUtil.checkStatus(ids,FsscFeeMain.class.getName(),"docStatus","20;30")){
			messages.addError(new KmssMessage("待审和发布的文档不允许删除"));	
		}
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	/**
	 * 校验是否可以关闭申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkCloseFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		rtn.put("result", "success");
		try {
			Boolean result = ((IFsscFeeMainService)getServiceImp(request)).checkCanCloseFee(fdId);
			rtn.put("result", result?"success":"failure");
			if(!result){
				rtn.put("message", ResourceUtil.getString("tips.closeFee.error","fssc-fee"));
			}
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	/**
	 * 关闭申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward closeFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		rtn.put("result", "success");
		try {
			((IFsscFeeMainService)getServiceImp(request)).updateCloseFee(fdId);
		} catch (Exception e) {
			rtn.put("result", "failure");
			rtn.put("message", ResourceUtil.getString("errors.unknown")+"<br>"+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
	
	/**
	 * 移动端显示流程
	 * 
	 */
	public ActionForward viewLbpm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		return getActionForward("viewFlow", mapping, form, request, response);
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
			JSONObject jsonObject = getFsscFeeMobileService().saveAtt(request);
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
	 * 更新附件，设置fdModelId，fdModelName
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAtt(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject jsonObject = getFsscFeeMobileService().updateAtt(request);
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
	 * 根据人员查找记账公司
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDefaultCompany(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject comObj = getFsscFeeMobileService().getDefaultCompany(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(comObj.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
    
	/**
	 * 根据人员查找部门
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDefaultOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject comObj = getFsscFeeMobileService().getDefaultOrg(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(comObj.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
	
	/**
	 * 获取币种汇率
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExchangeRate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject jsonObject = getFsscFeeMobileService().getExchangeRate(request);
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
	 * 搜索分类
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findFeeTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray jsonArray = getFsscFeeMobileService().getTemplate(request);
			response.setCharacterEncoding("UTF-8");
			JSONObject rtn=new JSONObject();
			rtn.put("result", "success");
			rtn.put("data", jsonArray);
			response.getWriter().write(rtn.toString());
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
	 * 是否需要校验预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getIsNeedBudget(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			Boolean fdIsNeedBudget = getFsscFeeMobileService().getIsNeedBudget(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(fdIsNeedBudget.toString());
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
			FsscFeeMainForm mainForm=(FsscFeeMainForm) form;
			mainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			request.setAttribute("fsscFeeMainForm", form);   //设置为起草，由流程去修改状态
			FsscFeeMain main = (FsscFeeMain)getServiceImp(request).convertFormToModel((IExtendForm)form, null, new RequestContext());
			if(!SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
				main.setDocCreator(null);;  //起草人设置为空，由提交流程去修改状态，不然如果刷新起草人会看到未正式提交的流程
				request.setAttribute("fdBillId", main.getFdId());
				((IFsscFeeMainService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()){
   			return getActionForward("failure", mapping, form, request, response);
		} else {
			if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {//暂存返回列表，提交跳转流程页面，二次提交
				return new ActionForward("/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=data",true);
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
					FsscFeeMain main=(FsscFeeMain) getServiceImp(request).findByPrimaryKey(fdBillId,FsscFeeMain.class,true);
					main.setDocStatus(paramJson.optString("docStatus",SysDocConstant.DOC_STATUS_DRAFT));
					main.setDocCreator(UserUtil.getUser());
					main.setDocNumber(getSysNumberFlowService().generateFlowNumber(main));
					getServiceImp(request).update(main);
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
			FsscFeeMainForm mainForm=(FsscFeeMainForm) form;
			mainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);  //设置为起草，由流程去修改状态
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			request.setAttribute("fsscFeeMainForm", form);
			super.loadActionForm(mapping, form, request, response);
			String fdId = request.getParameter("fdId");
			request.setAttribute("fdBillId", fdId);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
   			if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {//暂存返回列表，提交跳转流程页面，二次提交
				return new ActionForward("/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=data",true);
			}else {
				return getActionForward("editTemp", mapping, form, request, response);
			}
   		}
	}
	
}
