package com.landray.kmss.sys.time.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.time.forms.SysTimeCommonTimeForm;
import com.landray.kmss.sys.time.forms.SysTimeWorkForm;
import com.landray.kmss.sys.time.forms.SysTimeWorkTimeForm;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.service.ISysTimeCommonTimeService;
import com.landray.kmss.sys.time.service.ISysTimeWorkService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeWorkAction extends ExtendAction {
	protected ISysTimeWorkService sysTimeWorkService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeWorkService == null) {
			sysTimeWorkService = (ISysTimeWorkService) getBean("sysTimeWorkService");
		}
		return sysTimeWorkService;
	}

	/**
	 * 根据http请求，返回执行list操作需要用到的where语句。
	 * @param request
	 * @param hqlInfo
	 * @return where语句字符串（不包含where关键字）
	 * @throws Exception
	 */
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String sysTimeAreaId = request.getParameter("sysTimeAreaId");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		if (!StringUtil.isNull(sysTimeAreaId)) {
			whereBlock += " and sysTimeWork.sysTimeArea.fdId = :sysTimeAreaId ";
			hqlInfo.setParameter("sysTimeAreaId", sysTimeAreaId);
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	/**
	 * 执行添加操作主要的业务代码。 仅用于add的操作。 新增页面时将PersonId给一个值
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		SysTimeCommonTime common = null;
		SysTimeCommonTimeForm commonForm = null;

		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			SysTimeWork timeWork = (SysTimeWork) model;
			common = ((SysTimeWork) model).getSysTimeCommonTime();
			List<SysTimeWorkDetail> detais= new ArrayList<>();
			if (common != null) {
				for (SysTimeWorkDetail detail : common
						.getSysTimeWorkDetails()) {
					SysTimeWorkDetail newDetail = new SysTimeWorkDetail();
					newDetail.setFdWorkStartTime(
							detail.getFdWorkStartTime());
					newDetail
							.setFdWorkEndTime(detail.getFdWorkEndTime());
					detais.add(detail);
				}
				common.setSysTimeWorkDetails(detais);
				ISysTimeCommonTimeService commonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
						.getBean("sysTimeCommonTimeService");
				commonForm = (SysTimeCommonTimeForm) commonTimeService
						.convertModelToForm(
								new SysTimeCommonTimeForm(),
								common, new RequestContext(request));
			}

			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				UserOperHelper.logFind(model);
			}
			commonForm.setFdTotalDay(common.getFdTotalDay());
			commonForm.setFdRestStartType(common.getFdRestStartType());
			commonForm.setFdRestEndType(common.getFdRestEndType());
			if (commonForm != null) {
				((SysTimeWorkForm) rtnForm)
						.setSysTimeCommonTimeForm(commonForm);
			}

		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysTimeWorkForm sysTimeWorkForm = (SysTimeWorkForm) form;
		sysTimeWorkForm.reset(mapping, request);
		sysTimeWorkForm.setSysTimeAreaId(request.getParameter("sysTimeAreaId"));
		
		sysTimeWorkForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		sysTimeWorkForm.setDocCreatorId(UserUtil.getUser().getFdId());
		sysTimeWorkForm.setDocCreatorName(UserUtil.getUser().getFdName());
		return sysTimeWorkForm;
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
			SysTimeWorkForm workForm = (SysTimeWorkForm) form;
			SysTimeWork timeWork = (SysTimeWork) getServiceImp(request)
					.convertFormToModel(workForm, null,
							new RequestContext(request));
			ISysTimeCommonTimeService commonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
					.getBean("sysTimeCommonTimeService");
			if ("1".equals(workForm.getTimeType())) {
				SysTimeCommonTime commonTimes = (SysTimeCommonTime) commonTimeService
						.findByPrimaryKey(workForm.getSysTimeCommonId());
				timeWork.setFdTimeWorkColor(commonTimes.getFdWorkTimeColor());
			}
			else {
				SysTimeCommonTime common = new SysTimeCommonTime();
				common.setType("2");
				common.setFdName(workForm.getFdName());
				common.setFdWorkTimeColor(workForm.getFdTimeWorkColor());
				common.setFdRestStartTime(DateUtil.convertStringToDate(
						workForm.getSysTimeCommonTimeForm()
								.getFdRestStartTime(),
						DateUtil.TYPE_TIME, null));
				common.setFdRestEndTime(DateUtil.convertStringToDate(
						workForm.getSysTimeCommonTimeForm().getFdRestEndTime(),
						DateUtil.TYPE_TIME, null));
				common.setFdRestStartType(workForm.getSysTimeCommonTimeForm().getFdRestStartType());
				common.setFdRestEndType(workForm.getSysTimeCommonTimeForm().getFdRestEndType());
				common.setFdWorkHour(
						workForm.getSysTimeCommonTimeForm().getFdWorkHour());
				List<SysTimeWorkTimeForm> twts = workForm
						.getSysTimeWorkTimeFormList();
				List<SysTimeWorkDetail> detais = new ArrayList<>();
				for (SysTimeWorkTimeForm twt : twts) {
					SysTimeWorkDetail newDetail = new SysTimeWorkDetail();
					newDetail.setFdWorkStartTime(DateUtil.convertStringToDate(
							twt.getFdWorkStartTime(), DateUtil.TYPE_TIME,
							null));
					newDetail.setFdWorkEndTime(DateUtil.convertStringToDate(
							twt.getFdWorkEndTime(), DateUtil.TYPE_TIME, null));
					newDetail.setFdOverTimeType(
							Integer.parseInt(twt.getFdOverTimeType()));
					//最早打卡时间
					newDetail.setFdStartTime(DateUtil.convertStringToDate(
							twt.getFdStartTime(), DateUtil.TYPE_TIME,
							null));
					//最晚打卡时间
					newDetail.setFdOverTime(DateUtil.convertStringToDate(
							twt.getFdOverTime(), DateUtil.TYPE_TIME, null));
					newDetail.setFdEndOverTimeType(twt.getFdEndOverTimeType()==null?1:twt.getFdEndOverTimeType());
					detais.add(newDetail);
				}
				common.setFdTotalDay(workForm.getFdTotalDay());
				common.setSysTimeWorkDetails(detais);
				commonTimeService.add(common);
				timeWork.setSysTimeCommonTime(common);
			}
			getServiceImp(request).add(timeWork);
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
}
