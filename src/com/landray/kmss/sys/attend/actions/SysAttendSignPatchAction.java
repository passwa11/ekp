package com.landray.kmss.sys.attend.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.forms.SysAttendSignPatchDetailForm;
import com.landray.kmss.sys.attend.forms.SysAttendSignPatchForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendSignPatchService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-10-20
 */
public class SysAttendSignPatchAction extends ExtendAction {

	private ISysAttendSignPatchService sysAttendSignPatchService;

	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendMainService sysAttendMainService;

	private ISysOrgCoreService sysOrgCoreService;

	@Override
	protected ISysAttendSignPatchService
			getServiceImp(HttpServletRequest request) {
		if (sysAttendSignPatchService == null) {
			sysAttendSignPatchService = (ISysAttendSignPatchService) getBean(
					"sysAttendSignPatchService");
		}
		return sysAttendSignPatchService;
	}

	public ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	public ISysAttendMainService getSysAttendMainService() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) getBean(
					"sysAttendMainService");
		}
		return sysAttendMainService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysAttendSignPatchForm sysAttendSignPatchForm = (SysAttendSignPatchForm) form;
		String[] mainIds = request.getParameterValues("mainId");
		String cateId = request.getParameter("cateId");
		if (StringUtil.isNull(cateId)) {
			throw new UnexpectedRequestException();
		}
		SysAttendCategory category = (SysAttendCategory) getSysAttendCategoryService()
				.findByPrimaryKey(cateId, null, true);
		if (category == null) {
			throw new UnexpectedRequestException();
		}
		sysAttendSignPatchForm.setFdCateName(category.getFdName());
		sysAttendSignPatchForm
				.setFdPatchPersonId(UserUtil.getUser().getFdId());
		sysAttendSignPatchForm
				.setFdPatchPersonName(UserUtil.getUser().getFdName());
		sysAttendSignPatchForm.setFdPatchTime(DateUtil.convertDateToString(
				new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
		if (mainIds != null && mainIds.length > 0) {
			AutoArrayList fdPatchDetail = new AutoArrayList(
					SysAttendSignPatchDetailForm.class);
			List mainList = getSysAttendMainService().findList(HQLUtil
					.buildLogicIN("sysAttendMain.fdId", Arrays.asList(mainIds)),
					"");
			if (!mainList.isEmpty()) {
				for (Object obj : mainList) {
					SysAttendMain main = (SysAttendMain) obj;
					SysAttendSignPatchDetailForm detail = new SysAttendSignPatchDetailForm();
					detail.setFdId(IDGenerator.generateID());
					detail.setFdPatchId(sysAttendSignPatchForm.getFdId());
					detail.setFdSignPersonId(main.getDocCreator().getFdId());
					detail.setFdSignPersonName(
							main.getDocCreator().getFdName());
					detail.setFdSignTime(DateUtil.convertDateToString(
							getSignTime(category), DateUtil.TYPE_DATETIME,
							request.getLocale()));
					fdPatchDetail.add(detail);
				}
			}
			sysAttendSignPatchForm.setFdPatchDetail(fdPatchDetail);
		}
		// 已签到人员
		List signedList = getSignedList(category.getFdId());
		if (!signedList.isEmpty()) {
			String signedIds = "";
			for (Object obj : signedList) {
				SysAttendMain signMain = (SysAttendMain) obj;
				signedIds += signMain.getDocCreator().getFdId() + ";";
			}
			request.setAttribute("signedIds", signedIds);
		}
		// 范围内人员
		String targetIds = "";
		if (Boolean.TRUE.equals(category.getFdUnlimitTarget())) {
			targetIds = "all";
		} else {
			for (SysOrgElement ele : category.getFdTargets()) {
				List orgList = new ArrayList();
				orgList.add(ele);
				List ids = getSysOrgCoreService().expandToPersonIds(orgList);
				targetIds += StringUtil.join(
						(String[]) ids.toArray(new String[ids.size()]),
						";");
				targetIds += ";";
			}
		}
		request.setAttribute("targetIds", targetIds);
		// 开始时间，结束时间
		List<SysAttendCategoryTime> dateList = category.getFdTimes();
		if (dateList != null && !dateList.isEmpty()
				&& category.getFdStartTime() != null
				&& category.getFdEndTime() != null) {
			Date date = dateList.get(0).getFdTime();
			request.setAttribute("startTime",
					DateUtil.convertDateToString(
							AttendUtil.joinYMDandHMS(date,
									category.getFdStartTime()),
							DateUtil.TYPE_DATETIME, request.getLocale()));
			request.setAttribute("endTime",
					DateUtil.convertDateToString(
							AttendUtil.joinYMDandHMS(date,
									category.getFdEndTime()),
							DateUtil.TYPE_DATETIME, request.getLocale()));
		}
		return sysAttendSignPatchForm;
	}
	
	private Date getLateTime(SysAttendCategory category) {
		Date signTime = new Date();
		List<SysAttendCategoryTime> dateList = category.getFdTimes();
		List<SysAttendCategoryRule> ruleList = category.getFdRule();
		if (dateList != null && !dateList.isEmpty() && ruleList != null
				&& !ruleList.isEmpty()) {
			Date date = dateList.get(0).getFdTime();
			Date time = ruleList.get(0).getFdInTime();
			if (time == null) {
				return date;
			}
			signTime = AttendUtil.joinYMDandHMS(date, time);
		}
		return signTime;
	}

	private Date getSignTime(SysAttendCategory category) {
		Date signTime = new Date();
		List<SysAttendCategoryTime> dateList = category.getFdTimes();
		List<SysAttendCategoryRule> ruleList = category.getFdRule();
		if (dateList != null && !dateList.isEmpty() && ruleList != null
				&& !ruleList.isEmpty()) {
			Date date = dateList.get(0).getFdTime();
			Date time = ruleList.get(0).getFdInTime();
			if (time == null) {
				time = category.getFdStartTime();
			}
			signTime = AttendUtil.joinYMDandHMS(date, time);
		}
		return signTime;
	}

	private List getSignedList(String categoryId) throws Exception {
		List list = new ArrayList();
		if (StringUtil.isNotNull(categoryId)) {
			SysAttendCategory category= CategoryUtil.getCategoryById(categoryId);
			HQLInfo hqlInfo = new HQLInfo();

			StringBuilder whereTemp=new StringBuilder("sysAttendMain.fdStatus>0 and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			if(category ==null){
				whereTemp.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
			}else{
				whereTemp.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
			}
			hqlInfo.setWhereBlock(whereTemp.toString());
			hqlInfo.setParameter("categoryId", categoryId);
			list = getSysAttendMainService().findList(hqlInfo);
		}
		return list;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String cateId = request.getParameter("cateId");
			SysAttendSignPatchForm patchForm = (SysAttendSignPatchForm) form;
			if (patchForm != null && StringUtil.isNotNull(cateId)) {
				SysAttendCategory category = (SysAttendCategory) getSysAttendCategoryService()
						.findByPrimaryKey(cateId);
				if (category == null) {
					throw new UnexpectedRequestException();
				}
				Date lateTime=getLateTime(category);
				getServiceImp(request).add(patchForm,new RequestContext(request));
				
				AutoArrayList fdPatchDetail = patchForm.getFdPatchDetail();
				if (fdPatchDetail != null && !fdPatchDetail.isEmpty()) {
					Map<String, JSONObject> map = new HashMap<String, JSONObject>();
					for (int i = 0; i < fdPatchDetail.size(); i++) {
						SysAttendSignPatchDetailForm detail = (SysAttendSignPatchDetailForm) fdPatchDetail
								.get(i);
						JSONObject json = new JSONObject();
						json.put("signTime", DateUtil.convertStringToDate(
								detail.getFdSignTime(), DateUtil.TYPE_DATETIME,
								request.getLocale()).getTime());
						json.put("lateTime", lateTime.getTime());
						map.put(detail.getFdSignPersonId(), json);						
					}
					getServiceImp(request).updateMain(category, map,
							patchForm.getFdId());
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public ActionForward checkValidation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkValidation", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String[] signIds = request.getParameterValues("signId");
			String cateId = request.getParameter("cateId");
			JSONObject json = new JSONObject();
			if (StringUtil.isNotNull(cateId) && signIds != null
					&& signIds.length > 0) {
				String signedIds = "";
				List signedList = getSignedList(cateId);
				if (!signedList.isEmpty()) {
					for (Object obj : signedList) {
						SysAttendMain signMain = (SysAttendMain) obj;
						signedIds += signMain.getDocCreator().getFdId() + ";";
					}
				}
				boolean isSigned = false;
				for (String id : signIds) {
					if (signedIds.contains(id)) {
						json.put("status", 0);
						json.put("signedIds", signedIds);
						isSigned = true;
						break;
					}
				}
				if (!isSigned) {
					json.put("status", 1);
				}
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-checkValidation", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

}
