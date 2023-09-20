package com.landray.kmss.hr.ratify.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyLeaveDRForm;
import com.landray.kmss.hr.ratify.model.HrRatifyLeave;
import com.landray.kmss.hr.ratify.service.IHrRatifyLeaveService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class HrRatifyLeaveDRAction extends ExtendAction {

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean(
					"hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	private IHrRatifyLeaveService hrRatifyLeaveService;

	public IHrRatifyLeaveService getHrRatifyLeaveService() {
		if (hrRatifyLeaveService == null) {
			hrRatifyLeaveService = (IHrRatifyLeaveService) getBean(
					"hrRatifyLeaveService");
		}
		return hrRatifyLeaveService;
	}

	public ActionForward addLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addLeave", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			String fdUserId = request.getParameter("fdUserId");
			HrRatifyLeave hrRatifyLeave = selectLeaveProcess(fdUserId);
			if(hrRatifyLeave!=null){
				throw new KmssException(new KmssMessage(
						"hr-ratify:hrRatifyLeave.msg.notAllow"));
			} else {
				HrRatifyLeaveDRForm drForm = (HrRatifyLeaveDRForm) form;
				drForm.reset(mapping, request);
				drForm.setFdUserId(fdUserId);
				HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
						.updateGetPersonInfo(fdUserId);
				drForm.setFdUserName(personInfo.getFdName());
				SysOrgElement fdOrgParent = personInfo.getFdOrgParent();
				if (fdOrgParent != null) {
                    drForm.setFdUserParentName(fdOrgParent.getFdName());
                }
				drForm.setFdUserSex(personInfo.getFdSex());
				drForm.setFdUserStatus(personInfo.getFdStatus());
			}

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-addLeave", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("leaveError", mapping, form, request,
					response);
		} else {
			return getActionForward("addLeave", mapping, form, request,
					response);
		}
	}

	public ActionForward saveLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveLeave", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = true;// 执行结果
		try {
			HrRatifyLeaveDRForm drForm = (HrRatifyLeaveDRForm) form;
			getHrRatifyLeaveService().saveLeave(drForm);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-saveLeave", false, getClass());
		if (messages.hasError()) {
			status = false;
			response.setStatus(500);
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	public ActionForward abandonLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveLeave", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = true;// 执行结果
		try {
			String fdUserId = request.getParameter("fdUserId");
			String fdStatus = request.getParameter("fdStatus");
			getHrRatifyLeaveService().updateAbandonLeave(fdUserId, fdStatus);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveLeave", false, getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 查询当前选择人员的离职流程（找实际离职时间最晚的）
	 */
	private HrRatifyLeave selectLeaveProcess(String fdUserId) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
    	String whereBlock = " hrRatifyLeave.fdLeaveStaff.fdId =:fdUserId ";
    	hqlInfo.setParameter("fdUserId",fdUserId);
    	hqlInfo.setWhereBlock(whereBlock);
    	hqlInfo.setOrderBy("hrRatifyLeave.fdLeaveRealDate desc ");
    	List<HrRatifyLeave> list = getHrRatifyLeaveService().findList(hqlInfo);
    	if(!ArrayUtil.isEmpty(list)){	//存在一个或一个以上的离职流程（可能多次离职）
			HrRatifyLeave hrRatifyLeave = list.get(0);
			if (StringUtil.isNull(hrRatifyLeave.getFdLeaveStatus())
					|| !"2".equals(hrRatifyLeave.getFdLeaveStatus())) {// 取最近一次的离职流程做判断,不等于“2”说明流程通过后处于待离职状态
				return hrRatifyLeave;
    		}else{
    			return null;
    		}		
    	}else{
    		return null;
    	}
	}

	public ActionForward editLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editLeave", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HrRatifyLeaveDRForm drForm = (HrRatifyLeaveDRForm) form;
			drForm.reset(mapping, request);
			String fdUserId = request.getParameter("fdUserId");
			drForm.setFdUserId(fdUserId);
			
			HrRatifyLeave hrRatifyLeave = selectLeaveProcess(fdUserId);
			if(hrRatifyLeave!=null){
				throw new KmssException(new KmssMessage("hr-ratify:hrRatifyLeave.msg.notAllow"));
			}
				
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().updateGetPersonInfo(fdUserId);
			drForm.setFdUserName(personInfo.getFdName());
			SysOrgElement fdOrgParent = personInfo.getFdOrgParent();
			if (fdOrgParent != null) {
                drForm.setFdUserParentName(fdOrgParent.getFdName());
            }
			drForm.setFdUserSex(personInfo.getFdSex());
			drForm.setFdUserStatus(personInfo.getFdStatus());
			
			drForm.setFdLeaveApplyDate(DateUtil.convertDateToString(personInfo.getFdLeaveApplyDate(),""));
			drForm.setFdLeavePlanDate(DateUtil.convertDateToString(personInfo.getFdLeavePlanDate(),""));
			drForm.setFdLeaveRealDate(DateUtil.convertDateToString(personInfo.getFdLeaveTime(),""));
			drForm.setFdLeaveSalaryEndDate(DateUtil.convertDateToString(personInfo.getFdLeaveSalaryEndDate(),""));
			
			drForm.setFdLeaveReason(personInfo.getFdLeaveReason());
			drForm.setFdLeaveRemark(personInfo.getFdLeaveRemark());
			drForm.setFdNextCompany(personInfo.getFdNextCompany());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-editLeave", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("addLeave", mapping, form, request, response);
		}
	}
}
