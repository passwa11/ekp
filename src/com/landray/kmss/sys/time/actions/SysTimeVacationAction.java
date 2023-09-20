package com.landray.kmss.sys.time.actions;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.time.forms.SysTimeVacationForm;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.service.ISysTimeAreaService;
import com.landray.kmss.sys.time.service.ISysTimeVacationService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeVacationAction extends ExtendAction {
	protected ISysTimeVacationService sysTimeVacationService;
	protected ISysTimeAreaService sysTimeAreaService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeVacationService == null) {
			sysTimeVacationService = (ISysTimeVacationService) getBean("sysTimeVacationService");
		}
		return sysTimeVacationService;
	}

	protected ISysTimeAreaService
			getSysTimeAreaServiceImp(HttpServletRequest request) {
		if (sysTimeAreaService == null) {
			sysTimeAreaService = (ISysTimeAreaService) getBean(
					"sysTimeAreaService");
		}
		return sysTimeAreaService;
	}

	/**
	 * 根据http请求，返回执行list操作需要用到的where语句。
	 * 
	 * @param form
	 * @param request
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
			whereBlock += " and sysTimeVacation.sysTimeArea.fdId = :sysTimeAreaId ";
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
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysTimeVacationForm sysTimeVacationForm = (SysTimeVacationForm) form;
		sysTimeVacationForm.reset(mapping, request);
		sysTimeVacationForm.setSysTimeAreaId(request.getParameter("sysTimeAreaId"));
		
		sysTimeVacationForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		sysTimeVacationForm.setDocCreatorId(UserUtil.getUser().getFdId());
		sysTimeVacationForm.setDocCreatorName(UserUtil.getUser().getFdName());
		return sysTimeVacationForm;
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
			SysTimeVacationForm sysTimeVacationForm = (SysTimeVacationForm) form;
			Boolean validate = checkAreaHoliday(sysTimeVacationForm, request);
			if (validate.booleanValue()) {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} else {
				messages.addError(new KmssMessage(
						"sys-time:sysTimeVacation.time.validateHoliday"));
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

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysTimeVacationForm sysTimeVacationForm = (SysTimeVacationForm) form;
			Boolean validate = checkAreaHoliday(sysTimeVacationForm, request);
			if (validate.booleanValue()) {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} else {
				messages.addError(new KmssMessage(
						"sys-time:sysTimeVacation.time.validateHoliday"));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			SysTimeVacationForm sysTimeVacationForm = (SysTimeVacationForm) form;
			Boolean validate = checkAreaHoliday(sysTimeVacationForm, request);
			if (validate.booleanValue()) {
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			} else {
				messages.addError(new KmssMessage(
						"sys-time:sysTimeVacation.time.validateHoliday"));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			// 返回按钮
			IExtendForm mainForm = (IExtendForm) form;
			String fdModelId = mainForm.getFdId();
			String fdModelName = mainForm.getModelClass().getName();
			SysDictModel model = SysDataDict.getInstance()
					.getModel(fdModelName);
			if (model != null && !StringUtil.isNull(model.getUrl())) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(
								"button.back",
								formatModelUrl(fdModelId, model.getUrl()),
								false)
						.save(request);
			}

			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	private String formatModelUrl(String value, String url) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				url = StringUtil.replace(url, "${" + property + "}", value);
			} catch (Exception e) {
			}
		}
		return url;
	}

	/**
	 * 校验休假时间与区域组绑定的法定节假日时间是否重叠
	 * 
	 * @param sysTimeVacationForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Boolean checkAreaHoliday(SysTimeVacationForm sysTimeVacationForm,
			HttpServletRequest request) throws Exception {
		Boolean rtn = true;
		String fdAreaId = sysTimeVacationForm.getSysTimeAreaId();
		if (StringUtil.isNotNull(fdAreaId)) {
			SysTimeArea sysTimeArea = (SysTimeArea) getSysTimeAreaServiceImp(
					request).findByPrimaryKey(fdAreaId, null, true);
			if (sysTimeArea.getFdHoliday() != null) {
				Date fdStartDate = DateUtil.convertStringToDate(
						sysTimeVacationForm.getFdStartDate(),
						DateUtil.TYPE_DATE, request.getLocale());
				Date fdEndDate = DateUtil.convertStringToDate(
						sysTimeVacationForm.getFdEndDate(), DateUtil.TYPE_DATE,
						request.getLocale());
				List<SysTimeHolidayDetail> details = sysTimeArea.getFdHoliday()
						.getFdHolidayDetailList();
				if (fdStartDate != null && fdEndDate != null) {
					for (int i = 0; i < details.size(); i++) {
						SysTimeHolidayDetail detail = details.get(i);
						if (!(detail.getFdStartDay().getTime() > fdEndDate
								.getTime()
								|| detail.getFdEndDay().getTime() < fdStartDate
										.getTime())) {
							rtn = false;
							break;
						}
					}
				}
			}
		}
		return rtn;
	}

}
