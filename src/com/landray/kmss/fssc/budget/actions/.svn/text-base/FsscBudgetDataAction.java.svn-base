package com.landray.kmss.fssc.budget.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.budget.forms.FsscBudgetDataForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.model.FsscBudgetExecute;
import com.landray.kmss.fssc.budget.model.RegionBean;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.util.FsscBudgetParseXmlUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustomList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public class FsscBudgetDataAction extends ExtendAction {

    private IFsscBudgetDataService fsscBudgetDataService;

    @Override
    public IFsscBudgetDataService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetDataService == null) {
            fsscBudgetDataService = (IFsscBudgetDataService) getBean("fsscBudgetDataService");
        }
        return fsscBudgetDataService;
    }
    
    private IEopBasedataCompanyService eopBasedataCompanyService;
	
    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	 if (eopBasedataCompanyService == null) {
    		 eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
         }
		return eopBasedataCompanyService;
	}
    
    private IFsscBudgetExecuteService fsscBudgetExecuteService;
    
    public IFsscBudgetExecuteService getFsscBudgetExecuteService() {
    	if (fsscBudgetExecuteService == null) {
    		fsscBudgetExecuteService = (IFsscBudgetExecuteService) getBean("fsscBudgetExecuteService");
        }
		return fsscBudgetExecuteService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String whereBlock=hqlInfo.getWhereBlock();
    	String fdSchemeId=request.getParameter("fdSchemeId");
    	if(StringUtil.isNotNull(fdSchemeId)){
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetData.fdBudgetScheme.fdId=:fdSchemeId");
    		hqlInfo.setParameter("fdSchemeId", fdSchemeId);
    		request.setAttribute("colomunList", FsscBudgetParseXmlUtil.getSchemeList("column", fdSchemeId, null, null, null,null));
    	}
    	String fdMonth=request.getParameter("q.fdMonth");
    	if(StringUtil.isNotNull(fdMonth)){
    		String fdPeriodType=fdMonth.substring(0, 1);
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetData.fdPeriodType=:fdPeriodType");
    		String fdPeriod=fdMonth.substring(1, fdMonth.length());
    		if(StringUtil.isNotNull(fdPeriod)){
    			whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetData.fdPeriod=:fdPeriod");
    			hqlInfo.setParameter("fdPeriod", fdPeriod);
    		}else{
    			whereBlock=StringUtil.linkString(whereBlock, " and ", "(fsscBudgetData.fdPeriod='' or fsscBudgetData.fdPeriod is null)");
    		}
			hqlInfo.setParameter("fdPeriodType", fdPeriodType);
    	}
    	String fdCostCenterParentId=request.getParameter("q.fdCostCenter.parent.fdId");
    	if(StringUtil.isNotNull(fdCostCenterParentId)){
			hqlInfo.setJoinBlock(" left join fsscBudgetData.fdCostCenter.hbmParent parent ");
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "parent.fdId=:fdCostCenterParentId");
			hqlInfo.setParameter("fdCostCenterParentId", fdCostCenterParentId);
    	}
    	String fdCostCenterId=request.getParameter("q.fdCostCenterId");
    	if(StringUtil.isNotNull(fdCostCenterId)){
    		whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetData.fdCostCenter.fdId=:fdCostCenterId");
			hqlInfo.setParameter("fdCostCenterId", fdCostCenterId);
    	}
    	
    	hqlInfo.setWhereBlock(whereBlock);
    	buildBudgetWhere(request,hqlInfo);  //拼接维度名称搜索
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetData.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        hqlInfo.setOrderBy("fsscBudgetData.fdCostCenter asc,fsscBudgetData.fdBudgetItem desc,fsscBudgetData.fdYear desc,fsscBudgetData.fdPeriod asc ");
    }
    
  //拼接名称查询
    public void buildBudgetWhere(HttpServletRequest request, HQLInfo hqlInfo) throws Exception{
    	String whereBlock=hqlInfo.getWhereBlock();
    	String[] demiss=new String[] {"fdCompanyGroup","fdCompany","fdCostCenterGroup","fdCostCenter","fdProject","fdWbs","fdWbs","fdInnerOrder","fdInnerOrder","fdBudgetItem"};
    	for(String param:demiss) {
    		String value=request.getParameter("q."+param+"Name");
    		if(StringUtil.isNotNull(value)) {
    			whereBlock=StringUtil.linkString(whereBlock, " and ", "fsscBudgetData."+param+".fdName like :"+param+"Name");
    			hqlInfo.setParameter(param+"Name", "%"+value+"%");
    		}
    	}
    	hqlInfo.setWhereBlock(whereBlock);
	}

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscBudgetDataForm fsscBudgetDataForm = (FsscBudgetDataForm) super.createNewForm(mapping, form, request, response);
        ((IFsscBudgetDataService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscBudgetDataForm;
    }
    /**
   	 * 获取预算调整、占用、已使用金额
   	 */
   	public ActionForward getBudgetInfo(ActionMapping mapping,ActionForm form, HttpServletRequest request,
   			HttpServletResponse response) throws Exception {
   		String fdBudgetId=request.getParameter("fdBudgetId");
   		Map<String, String> map = getServiceImp(request).getBudgetAcountInfo(fdBudgetId,"double");
   		response.setCharacterEncoding("UTF-8");
   		if(!map.isEmpty()){
   			response.getWriter().write(JSONObject.fromObject(map).toString());
   		}else{
   			response.getWriter().write("");
   		}
   		return null;
   	}
   	
   	/**
   	 * 批量删除预算数据(有已使用或占用的不允许删除)
   	 * @param mapping
   	 * @param form
   	 * @param request
   	 * @param response
   	 * @return
   	 * @throws Exception
   	 */
   	public ActionForward deleteAllBudgetData(ActionMapping mapping,ActionForm form, HttpServletRequest request,
   			HttpServletResponse response) throws Exception {
   		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		for(int i=0;i<ids.length;i++){
			Map<String, String> map = getServiceImp(request).getBudgetAcountInfo(ids[i],"double");
			Double fdAlreadyUsedAmount=(map.containsKey("fdAlreadyUsedAmount")&&map.get("fdAlreadyUsedAmount")!=null)?Double.parseDouble(String.valueOf(map.get("fdAlreadyUsedAmount"))):0.00;//已使用
			Double fdOccupyAmount=(map.containsKey("fdOccupyAmount")&&map.get("fdOccupyAmount")!=null)?Double.parseDouble(String.valueOf(map.get("fdOccupyAmount"))):0.00;//占用
	   		if(fdAlreadyUsedAmount>0 || fdOccupyAmount>0){
	   			messages.addError(new KmssMessage("有已使用或占用的预算数据不允许删除"));
	   		}
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
   	}
   	
   	/**
	 * 在data列表中批量操作预算，stop：暂停，restart：重启，close：关闭
	 * 关闭和暂停需判断是否有预算存在暂用情况
	 * 表单中，复选框的域名必须为“List_Selected”，其值为记录id。
	 * 该操作一般以HTTP的POST方式触发。
	 * @throws Exception
	 */
	public ActionForward alterBudgetStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			json=getServiceImp(request).updateBudgetStatus(request);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().write(json.toString());
		}
		return null;
	}
	
	/**
	 * 打开预算调整页面。<br>
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
	public ActionForward adjustBudgetData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.edit(mapping, form, request, response);
			FsscBudgetDataForm mainForm=(FsscBudgetDataForm) form;
			mainForm.setFdMoney(null); //清空金额，由页面填写
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("adjust", mapping, form, request, response);
		}
	}
	/**
	 * 将预算调整金额更新到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	public ActionForward updateData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).updateData((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("adjust", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	/**
	 * 查询预算执行台账
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward executeLedger(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("executeLedger", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            String keyWord = request.getParameter("q._keyword");

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
            if (isReserve&&StringUtil.isNotNull(orderby)) {
				orderby += " desc";
			}
            JSONObject params = new JSONObject();
            params.put("keyWord", keyWord);
            if(StringUtil.isNotNull(orderby)){
            	  params.put("orderby", orderby);
            }
            params.put("pageno", pageno);
            params.put("rowsize", rowsize);
            request.setAttribute("queryPage", getServiceImp(request).getBudgetDataPage(request,params));
            request.setAttribute("fdBudgetWarn", EopBasedataFsscUtil.getSwitchValue("fdBudgetWarn"));
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("executeLedger", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("executeLedger");
        }
	}
	/**
	 * 导出预算执行台账
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportExecuteLedger(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportExecuteLedger", true, getClass());
		KmssMessages messages = new KmssMessages();
		ServletOutputStream os = null;
		try {
			String a = request.getParameter("fdDeptId");
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			Date data = new Date();
			String dataNow = DateUtil.convertDateToString(data, "yyyy-MM-dd");
			String filename = ResourceUtil.getString("table.fsscBudgetExecute", "fssc-budget") + dataNow;
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			response.setHeader("content-disposition", "attachment;filename="+ filename + ".xls");
//			response.addHeader("content-disposition", "attachment;filename="+ filename + ".xls");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 1000000000;
			if (isReserve&&StringUtil.isNotNull(orderby)) {
				orderby += " desc";
			}
			JSONObject params = new JSONObject();
			if(StringUtil.isNotNull(orderby)){
				params.put("orderby", orderby);
			}
			params.put("pageno", pageno);
			params.put("rowsize", rowsize);
			HSSFWorkbook workBook = getServiceImp(request).getExportDataListNew(request, params);
//			response.setCharacterEncoding("UTF-8");
////	        response.getWriter().append(jsonArray.toString());
//	        response.getWriter().flush();
//	        response.getWriter().close();
			os = response.getOutputStream();
			workBook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			messages.addError(e);
		}finally {
			if (os != null) {
				os.close();
			}
		}
		TimeCounter.logCurrentTime("Action-exportExecuteLedger", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}
	
	//==============预算统计台账
	
	/**
	 * 查询预算执行台账
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */					
	public ActionForward countReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("预算统计报表查询");
		TimeCounter.logCurrentTime("executeLedger", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            String keyWord = request.getParameter("q._keyword");

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
            if (isReserve&&StringUtil.isNotNull(orderby)) {
				orderby += " desc";
			}
            JSONObject params = new JSONObject();
            params.put("keyWord", keyWord);
            if(StringUtil.isNotNull(orderby)){
            	  params.put("orderby", orderby);
            }
            params.put("pageno", pageno);
            params.put("rowsize", rowsize);
            request.setAttribute("queryPage", getServiceImp(request).getBudgetCountDataPage(request,params));
            request.setAttribute("fdBudgetWarn", EopBasedataFsscUtil.getSwitchValue("fdBudgetWarn"));
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("executeLedger", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("countReport");
        }
	}
	/**
	 * 导出预算执行台账
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportCountReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-exportExecuteLedger", true, getClass());
		KmssMessages messages = new KmssMessages();
		ServletOutputStream os = null;
		try {
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			Date data = new Date();
			String dataNow = DateUtil.convertDateToString(data, "yyyy-MM-dd");
			String filename = ResourceUtil.getString("message.budget.count.report", "fssc-budget") + dataNow;
			filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			response.addHeader("content-disposition", "attachment;filename="+ filename + ".xls");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 1000000;
			if (isReserve&&StringUtil.isNotNull(orderby)) {
				orderby += " desc";
			}
			JSONObject params = new JSONObject();
			if(StringUtil.isNotNull(orderby)){
				params.put("orderby", orderby);
			}
			params.put("pageno", pageno);
			params.put("rowsize", rowsize);
			HSSFWorkbook workBook = getServiceImp(request).getExportCountDataList(request, params);
			os = response.getOutputStream();
			workBook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			messages.addError(e);
		}finally {
			if (os != null) {
				os.close();
			}
		}
		TimeCounter.logCurrentTime("Action-exportExecuteLedger", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	/**
	 * 通过URL的方式直接删除一条记录。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 若执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.delete(mapping, form, request, response);
			String id = request.getParameter("fdId");
			//删除预算，将其对应的执行数据删除
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock("fsscBudgetExecute.fdBudgetId=:fdBudgetId");
			hqlInfo.setParameter("fdBudgetId", id);
			List<FsscBudgetExecute> exceuteList=getFsscBudgetExecuteService().findList(hqlInfo);
			for(FsscBudgetExecute execute:exceuteList) {
				getFsscBudgetExecuteService().delete(execute);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
	/**
	 * 校验公司预算币种和预算数据币种汇率是否配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkBudgetExchangeRate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject jsonObject = ((IFsscBudgetDataService)getServiceImp(request)).checkBudgetExchangeRate(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}

	public ActionForward getEndFlag(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		try {
			json.put("endFlag", request.getSession().getAttribute("endFlag"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().write(json.toString());
		}
		return null;
	}
	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && ordertype.equalsIgnoreCase("down")) {
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
//			if (isReserve)
//				orderby += " desc";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			

			List<FsscBudgetData> list = page.getList();
			List<String> fdBudgetIds = new ArrayList<>();
			for (FsscBudgetData fsscBudgetData : list) {
				fdBudgetIds.add(fsscBudgetData.getFdId());
				EopBasedataBudgetItem parent=(EopBasedataBudgetItem) fsscBudgetData.getFdBudgetItem().getFdParent();
				fsscBudgetData.setFdBudgetItemParent(parent);
			}
			request.setAttribute("queryPage", page);
			Map<String, Map> executeDataList = getFsscBudgetExecuteService().getExecuteDataList(fdBudgetIds, null, "match");
			request.setAttribute("executeDataList", executeDataList);

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
	
	  private IEopBasedataCostCenterService eopBasedataCostCenterService;
		
	    public IEopBasedataCostCenterService getEopBasedataCostCenterService() {
	    	 if (eopBasedataCostCenterService == null) {
	    		 eopBasedataCostCenterService = (IEopBasedataCostCenterService) getBean("eopBasedataCostCenterService");
	         }
			return eopBasedataCostCenterService;
		}
	
	public ActionForm customregion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		String fdParentId=request.getParameter("parentid");
		try {
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.hbmParent parent ");
			hqlInfo.setSelectBlock("new Map(eopBasedataCostCenter.fdId as value,eopBasedataCostCenter.fdName as text, parent.fdId as fdParentId)");
			String where ="eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable and eopBasedataCostCenter.fdIsGroup=:fdIsGroup";
			if(StringUtil.isNotNull(fdParentId)){
				where+=" and parent.fdId =:fdParentId";
				hqlInfo.setParameter("fdParentId", fdParentId);
			}else{
				where+=" and parent.fdId is null";
			}
			hqlInfo.setWhereBlock(where);
			hqlInfo.setParameter("fdIsAvailable", true);
			hqlInfo.setParameter("fdIsGroup", "2");
			List<EopBasedataCostCenter> bean=getEopBasedataCostCenterService().findList(hqlInfo);
			if(bean!=null&&bean.size()>0){
				List<RegionBean> res=new ArrayList<>();
				for (Iterator iterator = bean.iterator(); iterator.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					RegionBean r=new RegionBean();
					//r.setFdcascade(sysf.getFdCascade().getFdId());
					r.setFdId((String)map.get("value"));
					r.setFdvalue((String)map.get("value"));
					r.setFdvaluetext((String)map.get("text"));
					r.setFdParentId(StringUtil.isNotNull((String)map.get("fdParentId"))?(String)map.get("fdParentId"):"");
					res.add(r);
				}
				net.sf.json.JSONArray result = net.sf.json.JSONArray.fromObject(res);  
		        String listJson = result.toString();
				return this.returnJson(response, true, listJson);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.returnJson(response, false, "error");
	}
	
	private ActionForm returnJson(HttpServletResponse response,boolean isok,String data) throws IOException{
		response.setCharacterEncoding("UTF-8");
    	JSONObject json=new JSONObject();
    	json.put("isok",isok);
    	json.put("data",data);
    	response.getWriter().write(JSONObject.fromObject(json).toString());
    	return null;
	}

}
