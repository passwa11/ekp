package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.util.HrOrgAuthorityUtil;
import com.landray.kmss.hr.staff.forms.HrStaffTrackRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 任职记录
 * 
 * 
 */
public class HrStaffTrackRecordAction extends HrStaffImportAction {
	private IHrStaffTrackRecordService hrStaffTrackRecordService;
	private ISysOrgPersonService sysOrgPersonService;
	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	@Override
	protected IHrStaffTrackRecordService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffTrackRecordService == null) {
			hrStaffTrackRecordService = (IHrStaffTrackRecordService) getBean(
					"hrStaffTrackRecordService");
		}
		return hrStaffTrackRecordService;
	}

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) getBean(
                    "sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

	public IHrStaffEmolumentWelfareDetaliedService
			getHrStaffEmolumentWelfareDetaliedService() {
		if (hrStaffEmolumentWelfareDetaliedService == null) {
			hrStaffEmolumentWelfareDetaliedService = (IHrStaffEmolumentWelfareDetaliedService) getBean(
					"hrStaffEmolumentWelfareDetaliedService");
		}
		return hrStaffEmolumentWelfareDetaliedService;
	}
	
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	
	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String _fdDept = cv.poll("_fdDept");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo
					.setWhereBlock(
							"sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);
			whereBlock
					.append(" and ((fdPersonInfo.fdName like :fdKey or fdPersonInfo.fdMobileNo like :fdKey or fdPersonInfo.fdEmail like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or fdPersonInfo.fdId in (:ids)");
				hqlInfo.setParameter("ids", ids);
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}

		// 员工状态
		String[] _fdStatus = cv.polls("_fdStatus");
		if (_fdStatus != null && _fdStatus.length > 0) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			whereBlock.append(" and (fdPersonInfo.fdStatus in (:fdStatus)");
			if (isNull) {
				whereBlock.append(" or fdPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}

		//任职记录类型
		String fdType = cv.poll("fdType");
		if (StringUtil.isNotNull(fdType)) {
			whereBlock.append(" and fdType = :fdType");
			hqlInfo.setParameter("fdType", fdType);
		}
		//员工
		String fdPerson = cv.poll("fdPerson");
		if (StringUtil.isNotNull(fdPerson)) {
			whereBlock.append(" and fdPersonInfo.fdId = :fdPerson");
			hqlInfo.setParameter("fdPerson", fdPerson);
		}
		if ("2".equals(fdType)) {
			whereBlock = HrOrgAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffTrackRecord", hqlInfo);
		} else {
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffTrackRecord", hqlInfo);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		request.setAttribute("fdType", fdType);
	}

	public void listData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String personInfoId = request.getParameter("personInfoId");

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffTrackRecord.fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffTrackRecord> list = getServiceImp(request)
				.findPage(hqlInfo).getList();

		JSONArray source = new JSONArray();
		try {
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("fdId", list.get(i).getFdId());
				Date begin = list.get(i).getFdEntranceBeginDate();
				Date end = list.get(i).getFdEntranceEndDate();
				Date fdInternshipEndDate = list.get(i).getFdInternshipEndDate();
				Date fdEndDateOfInternship = list.get(i)
						.getFdEndDateOfInternship();
				Date fdStartDateOfInternship = list.get(i)
						.getFdStartDateOfInternship();
				Date fdInternshipStartDate = list.get(i)
						.getFdInternshipStartDate();
				json.put("fdEntranceBeginDate",
						DateUtil.convertDateToString(begin,
								DateUtil.PATTERN_DATE));
				json.put("fdInternshipStartDate",
						DateUtil.convertDateToString(fdInternshipStartDate,
								DateUtil.PATTERN_DATE));
				json.put("fdStartDateOfInternship",
						DateUtil.convertDateToString(fdStartDateOfInternship,
								DateUtil.PATTERN_DATE));
				json.put("fdInternshipEndDate",
						DateUtil.convertDateToString(fdInternshipEndDate,
								DateUtil.PATTERN_DATE));
				json.put("fdEndDateOfInternship",
						DateUtil.convertDateToString(fdEndDateOfInternship,
								DateUtil.PATTERN_DATE));
				if (end != null) {
					json.put("fdEntranceEndDate", DateUtil
							.convertDateToString(end, DateUtil.PATTERN_DATE));
				} else {
					json.put("fdEntranceEndDate", "");
				}
				if (fdInternshipEndDate != null) {
					json.put("fdInternshipEndDate", DateUtil
							.convertDateToString(end, DateUtil.PATTERN_DATE));
				} else {
					json.put("fdInternshipEndDate", "");
				}
				if (end != null) {
					json.put("fdEntranceEndDate", DateUtil
							.convertDateToString(end, DateUtil.PATTERN_DATE));
				} else {
					json.put("fdEntranceEndDate", "");
				}
				SysOrgElement dept = list.get(i).getFdRatifyDept();
				String fdDeptName = null;
				if (dept != null) {
					fdDeptName = dept.getFdName();
				} else {
					HrOrganizationElement hrDept = list.get(i).getFdHrOrgDept();
					if ((null != hrDept)) {
						fdDeptName = hrDept.getFdName();
					}
				}
				SysOrgElement orgParentDept = list.get(i).getFdOrgPerson();
				String fdOrgParentDeptName = null;
				if (orgParentDept != null) {
					fdOrgParentDeptName = orgParentDept.getFdName();
				} else {
					HrOrganizationElement hrDept = list.get(i).getFdHrOrgDept();
					if ((null != hrDept)) {
						fdOrgParentDeptName = hrDept.getFdName();
					}
				}
				json.put("fdRatifyDept", fdDeptName);
				json.put("fdOrgParentDeptName", fdOrgParentDeptName);
				List<SysOrgPost> post = list.get(i).getFdOrgPosts();
				StringBuffer strBuffer = new StringBuffer();
				if (!ArrayUtil.isEmpty(post)) {
					for (int j = 0; j < post.size(); j++) {
						strBuffer.append(post.get(j).getFdName());
					}
				} else {
					HrOrganizationPost organizationPost = list.get(i).getFdHrOrgPost();
					if (null != organizationPost) {
						strBuffer.append(organizationPost.getFdName());
					}
				}
				json.put("fdPosts", strBuffer.toString());
				if (list.get(i).getFdStaffingLevel() != null) {
					json.put("fdStaffingLevel",
							list.get(i).getFdStaffingLevel().getFdName());
				}
				String fdType = list.get(i).getFdType(), fdTypeName = null;
				if (StringUtil.isNotNull(fdType)) {
					if ("2".equals(fdType)) {
						fdTypeName = "兼岗";
					} else {
						fdTypeName = "主岗";
					}
				} else {
					fdTypeName = "主岗";
				}
				String fdStatus = list.get(i).getFdStatus(), fdStatusName = null;
				String fdChangeType = list.get(i).getFdChangeType();
				String fdContractChangeRecord = list.get(i)
						.getFdContractChangeRecord();
				String fdIsInspection = list.get(i).getFdIsInspection();
				String fdAppointmentCategory = list.get(i)
						.getFdAppointmentCategory();
				String fdIsSecondEntry = list.get(i).getFdIsSecondEntry();
				if (StringUtil.isNotNull(fdStatus)) {
					if ("1".equals(fdStatus)) {
						fdStatusName = "任职中";
					} else if ("3".equals(fdStatus)) {
						fdStatusName = "待任职";
					} else {
						fdStatusName = "已结束";
					}
				}
				json.put("fdStatusName", fdStatusName);
				json.put("fdIsSecondEntry", fdIsSecondEntry);
				json.put("fdAppointmentCategory", fdAppointmentCategory);
				json.put("fdIsInspection", fdIsInspection);
				json.put("fdStatus", fdStatus);
				json.put("fdChangeType", fdChangeType);
				json.put("fdContractChangeRecord", fdContractChangeRecord);
				json.put("fdTypeName", fdTypeName);
				json.put("fdType", fdType);
				json.put("fdMemo", list.get(i).getFdMemo());
				source.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		// 子类自己处理JOSN数组
		// JSONArray array = handleJSONArray(list);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(source);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 导入员工
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActionForward fileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessage message = null;
		HrStaffTrackRecordForm trackRecordForm = (HrStaffTrackRecordForm) form;
		FormFile file = trackRecordForm.getFile();
		String resultMsg = null;
		boolean state = false;
		if (file == null || file.getFileSize() < 1) {
			resultMsg = ResourceUtil.getString("hrStaff.import.noFile",
					"hr-staff");
		} else {
			try {
				message = getServiceImp(request)
						.saveImportData(trackRecordForm);
				state = message.getMessageType() == KmssMessage.MESSAGE_COMMON;
			} catch (Exception e) {
				e.printStackTrace();
				message = new KmssMessage(e.getMessage());

			}
			resultMsg = message.getMessageKey();
		}
		// 保存导入信息
		request.setAttribute("resultMsg", resultMsg);
		// 状态
		request.setAttribute("state", state);
		// 保存导入的类型
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("uploadActionUrl",
				request.getParameter("uploadActionUrl"));
		request.setAttribute("downLoadUrl",
				request.getParameter("downLoadUrl"));
		return getActionForward("hrStaffFileUpload", mapping, form, request,
				response);
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffTrackRecord.template");
	}

	/**
	 * <p>保存调动调薪</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward addTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HrStaffTrackRecordForm recordForm = (HrStaffTrackRecordForm) form;
			HrStaffTrackRecord model = (HrStaffTrackRecord) getServiceImp(request).convertFormToModel(recordForm, null,
					new RequestContext(request));
			if (null != model) {
				getServiceImp(request).addTransfer(model);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	public ActionForward editHrStaffTrackRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			IExtendForm rtnForm = null;
			String fdId = request.getParameter("fdId");
			HrStaffTrackRecord hrStaffTrackRecord = (HrStaffTrackRecord)getServiceImp(request).findByPrimaryKey(fdId);
			/*
			recordForm.setFdId(fdId);
			recordForm.setFdEntranceBeginDate(DateUtil.convertDateToString(hrStaffTrackRecord.getFdEntranceBeginDate(),null));
			recordForm.setFdEntranceEndDate(DateUtil.convertDateToString(hrStaffTrackRecord.getFdEntranceEndDate(),null));
			recordForm.setFdRatifyDeptId(hrStaffTrackRecord.getFdRatifyDept().getFdId());
			recordForm.setFdRatifyDeptName(hrStaffTrackRecord.getFdRatifyDept().getFdName());*/
			if(hrStaffTrackRecord!= null){
				rtnForm = getServiceImp(request).convertModelToForm((IExtendForm) form, hrStaffTrackRecord, new RequestContext(request));
			}
			HrStaffTrackRecordForm recordForm = (HrStaffTrackRecordForm)rtnForm;
			
			String orgPersonId = request.getParameter("orgId");
			
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByOrgPersonId(orgPersonId);
			
			if (null != personInfo.getFdOrgPerson()) {
				recordForm.setFdOrgPersonId(personInfo.getFdOrgPerson().getFdId());
				recordForm.setFdOrgPersonName(personInfo.getFdOrgPerson().getFdName());
			}
			
			recordForm.setFdPersonInfoId(personInfo.getFdId());
			recordForm.setFdPersonInfoName(personInfo.getFdName());
			Double salary = getHrStaffEmolumentWelfareDetaliedService().getSalaryByStaffId(personInfo);
			request.setAttribute("currSalary", salary);
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
			request.setAttribute("personInfo", personInfo);
			
			request.setAttribute("afterEffectTime", recordForm.getFdTransDate());//之前有效时间
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editHrStaffTrackRecord", mapping, form, request, response);
		}
	}
	

	/**
	 * 更新调岗信息
	 */
	public ActionForward updateHrStaffTrackRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HrStaffTrackRecordForm recordForm = (HrStaffTrackRecordForm) form;
			String afterEffectTime = request.getParameter("afterEffectTime");//如果说异动生效日期允许修改的话
			
			HrStaffTrackRecord model = (HrStaffTrackRecord) getServiceImp(request).convertFormToModel(recordForm, null, new RequestContext(request));
			getServiceImp(request).updateHrStaffTrackRecord(model, afterEffectTime);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	/**
	 * 更新调薪信息
	 */
	public ActionForward updateSalary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HrStaffTrackRecordForm recordForm = (HrStaffTrackRecordForm) form;
			Date fdEffectTime = DateUtil.convertStringToDate(recordForm.getFdTransDate(), "yyyy-MM-dd");//修改后的有效时间
			String fdId = recordForm.getFdId();
			String fdOrgPersonId = recordForm.getFdOrgPersonId();//人员ID
			Double fdTransSalary = recordForm.getFdTransSalary();
			getHrStaffEmolumentWelfareDetaliedService().updateSalary(fdId ,fdTransSalary ,fdEffectTime ,fdOrgPersonId);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * <p>删除兼岗</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteConPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = request.getParameter("fdId");
			if (StringUtil.isNotNull(fdId)) {
				String[] ids = fdId.split(";");
				getServiceImp(request).delete(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * <p>检查同部门同岗位兼岗唯一</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void checkUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = new JSONObject();
			boolean result = true;
			String fdHrOrgDeptId = request.getParameter("fdHrOrgDeptId");
			String fdHrOrgPostId = request.getParameter("fdHrOrgPostId");
			String fdPersonInfoId = request.getParameter("fdPersonInfoId");
			String fdId = request.getParameter("fdId");

			result = getServiceImp(request).checkUnique(fdId, fdPersonInfoId, fdHrOrgDeptId, fdHrOrgPostId, null, "2");
			json.put("result", result);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据人事档案id,获取第一次的任职记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPersonInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		String fdPersonId = request.getParameter("fdPersonId");
		if (StringUtil.isNotNull(fdPersonId)) {
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("fdPersonInfo.fdId=:fdPersonId");
			info.setParameter("fdPersonId", fdPersonId);
			info.setOrderBy("fdCreateTime asc");
			List<HrStaffTrackRecord> records = getServiceImp(request).findList(info);
			if (!ArrayUtil.isEmpty(records)) {
				HrStaffTrackRecord record = records.get(0);
				obj.put("url", ModelUtil.getModelUrl(record));
				System.out.println(obj.get("url"));
				obj.put("fdId", record.getFdId());
			}
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewRecord", mapping, form, request, response);
		}
	}

}
