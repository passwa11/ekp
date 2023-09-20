package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.model.SysAttendOutPerson;
import com.landray.kmss.sys.attend.model.SysAttendOutPersonLog;
import com.landray.kmss.sys.attend.service.ISysAttendOutPersonLogService;
import com.landray.kmss.sys.attend.service.ISysAttendOutPersonService;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.exception.SmsMaxTimesLimitationException;
import com.landray.kmss.sys.notify.exception.SmsSendingIntervalTimeException;
import com.landray.kmss.sys.notify.interfaces.*;
import com.landray.kmss.sys.notify.provider.SmsCodeNotifySendOperator;
import com.landray.kmss.sys.notify.provider.SysNotifySendImpl;
import com.landray.kmss.sys.notify.provider.SysNotifySmsSendBuilder;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.profile.model.PasswordSecurityConfig;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-22
 */
public class SysAttendOutPersonAction extends ExtendAction {

	private ISysAttendOutPersonService sysAttendOutPersonService;
	private ISysAttendOutPersonLogService sysAttendOutPersonLogService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@Override
	protected ISysAttendOutPersonService
			getServiceImp(HttpServletRequest request) {
		if (sysAttendOutPersonService == null) {
			sysAttendOutPersonService = (ISysAttendOutPersonService) getBean(
					"sysAttendOutPersonService");
		}
		return sysAttendOutPersonService;
	}

	public ISysAttendOutPersonLogService getSysAttendOutPersonLogService() {
		if (sysAttendOutPersonLogService == null) {
			sysAttendOutPersonLogService = (ISysAttendOutPersonLogService) getBean(
					"sysAttendOutPersonLogService");
		}
		return sysAttendOutPersonLogService;
	}

	protected ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	/**
	 * 获取外部人员ID
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-getUserId", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String name = request.getParameter("name");
			String phoneNum = request.getParameter("phoneNum");
			JSONObject json = new JSONObject();
			if (StringUtil.isNotNull(name) && StringUtil.isNotNull(phoneNum)) {
				SysAttendOutPerson outer = getServiceImp(request)
						.findPersonByNameAndPhone(null, phoneNum);
				UserOperHelper.logFind(outer);// 添加日志信息
				if (outer != null) {
					json.accumulate("userId", outer.getFdId());
				}
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getUserId", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	private ISysNotifySendOperator<String> getISysNotifySendOperator(ISysNotifySendBuilder<String> builder, final String code, final String userName) throws Exception {
		return new SmsCodeNotifySendOperator<String>(builder) {

			@Override
			protected int getSendedLogCount(String phoneNum, Date startTime) throws Exception {
				List<SysAttendOutPersonLog> result = getSysAttendOutPersonLogService()
						.findOutPersonLogs(phoneNum, startTime);
				return result == null ? 0 : result.size();
			}

			@Override
			protected void saveSendedLog(String code, String phoneNum) throws Exception {
				SysAttendOutPersonLog sysAttendOutPersonLog = new SysAttendOutPersonLog();
				sysAttendOutPersonLog.setFdId(IDGenerator.generateID());
				sysAttendOutPersonLog.setFdCreateTime(new Date());
				sysAttendOutPersonLog.setFdMobileCode(code);
				sysAttendOutPersonLog.setFdUserName(userName);
				sysAttendOutPersonLog.setFdUserPhoneNum(phoneNum);
				getSysAttendOutPersonLogService().add(sysAttendOutPersonLog);
			}

			@Override
			public String getSmsCode(String phoneNum) {
				return code;
			}

			@Override
			public HashMap getReplaceMap() {
				return null;
			}

			@Override
			public ISysNotifyModel getSysNotifyModel() {
				return null;
			}
		};
	}

	/**
	 * 发送验证码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward sendVcode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-sendVcode", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		String name = request.getParameter("name");
		String phoneNum = request.getParameter("phoneNum");
		PasswordSecurityConfig config = null;
		try {
			// 用户名和手机为空
			if (StringUtil.isNull(name) && StringUtil.isNull(phoneNum)) {
				json.accumulate("status", 0);
				json.accumulate("errMsg", ResourceUtil.getString(
						"sys-attend:sysAttendOutPerson.error.noInput"));
				request.setAttribute("lui-source", json);
				return mapping.findForward("lui-source");
			}
			
			SysCfgModule sysConfig = SysConfigs.getInstance().getModule("/sys/attend/");
			String moduleSource = ResourceUtil.getString(sysConfig.getMessageKey());
			String scene = ResourceUtil.getString("sys-attend:sysAttend.notify.scene.verificationCode");
			
			config = new PasswordSecurityConfig();
			final String code = createRandomVcode();
			
			String subject = ResourceUtil.getString(request,
					"sysAttendOutPerson.send.content", "sys-attend",
					new Object[] { code, config.getCodeEffectiveTime() });
			String content = subject;
			
			ISysNotifySendBuilder<String> builder = new SysNotifySmsSendBuilder<String>(null, moduleSource, scene, Arrays.asList(phoneNum), subject, content);
			NotifyContextImp context = (NotifyContextImp) builder.getNotifyContext();
			context.setModelName(SysAttendOutPersonLog.class.getName());

			ISysNotifySendOperator<String> operator = getISysNotifySendOperator(builder, code, name);
			ISysNotifySendAbstract sendInstantce = new SysNotifySendImpl(operator);
			sendInstantce.send();
		} 
		catch(SmsMaxTimesLimitationException e)
		{
			json.accumulate("status", 0);
			json.accumulate("errMsg", ResourceUtil.getString(request,
					"sysAttendOutPerson.error.overTimes",
					"sys-attend", new Object[] { config
							.getMaxTimesOneDay() }));
			request.setAttribute("lui-source", json);
			return mapping.findForward("lui-source");
		}
		catch(SmsSendingIntervalTimeException e) {
			json.accumulate("status", 0);
			json.accumulate("errMsg", ResourceUtil.getString(request,
					"sysAttendOutPerson.error.toOften",
					"sys-attend", new Object[] { config
							.getReSentIntervalTime() }));
			request.setAttribute("lui-source", json);
			return mapping.findForward("lui-source");
		}
		catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-sendVcode", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			json.accumulate("status", 1);
			request.setAttribute("lui-source", json);
			return mapping.findForward("lui-source");
		}
	}


	private Date getAvailableTime(PasswordSecurityConfig config) {
		return new Date(
				new Date().getTime() - config.getCodeEffectiveTime() * 60000L);
	}

	/**
	 * @return 6位随机验证码
	 */
	public String createRandomVcode() {
		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int) (Math.random() * 10);
		}
		return vcode;
	}

	private String addOutPerson(HttpServletRequest request, String name,
			String phoneNum) throws Exception {
		SysAttendOutPerson person = new SysAttendOutPerson();
		person.setFdId(IDGenerator.generateID());
		person.setFdCreateTime(new Date());
		person.setFdName(name);
		person.setFdPhoneNum(phoneNum);
		return getServiceImp(request).add(person);
	}

	/**
	 * 验证手机验证码，验证成功则增加一个外部人员并跳到签到页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward validateVCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-validateVCode", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String categoryId = request.getParameter("categoryId");
			String fdName = request.getParameter("fdName");
			String fdPhoneNum = request.getParameter("fdPhoneNum");
			String fdVcode = request.getParameter("fdVcode");
			PasswordSecurityConfig config = new PasswordSecurityConfig();

			if (StringUtil.isNull(fdName) || StringUtil.isNull(fdPhoneNum)) {
				// 用户名或手机号为空
				request.setAttribute("errMsg", ResourceUtil.getString(request,
						"sysAttendOutPerson.error.noInput",
						"sys-attend"));
				return mapping.findForward("register");
			}

			if ("true".equals(config.getSmsReceiveEnable())) {
				if (StringUtil.isNull(fdVcode)) {
					// 验证码为空
					request.setAttribute("errMsg",
							ResourceUtil.getString(request,
									"sysAttendOutPerson.error.mobileCodeNull",
									"sys-attend"));
					return mapping.findForward("register");
				}
				Date availableTime = getAvailableTime(config);
				List<SysAttendOutPersonLog> list = getSysAttendOutPersonLogService()
						.findOutPersonLogs(fdPhoneNum, availableTime);
				// 验证码已过期
				if (list == null || list.isEmpty()) {
					request.setAttribute("errMsg",
							ResourceUtil.getString(request,
									"sysAttendOutPerson.error.mobileCodeOutOfDate",
									"sys-attend"));
					return mapping.findForward("register");
				}
				boolean isFindLog = false;
				for (SysAttendOutPersonLog log : list) {
					if (fdVcode.equals(log.getFdMobileCode())) {
						isFindLog = true;
					}
				}
				if (!isFindLog) {
					// 验证码错误
					request.setAttribute("errMsg",
							ResourceUtil.getString(request,
									"sysAttendOutPerson.error.mobileCodeError",
									"sys-attend"));
					return mapping.findForward("register");
				}
			}
			
			if (StringUtil.isNotNull(categoryId)) {
				SysAttendOutPerson outPerson = getServiceImp(request)
						.findPersonByNameAndPhone(null, fdPhoneNum);
				// 一个手机只能注册一个用户
				if (outPerson != null) {
					request.setAttribute("errMsg",
							ResourceUtil.getString(request,
									"sysAttendOutPerson.error.phoneNumUnique",
									"sys-attend"));
					return mapping.findForward("register");
				} else {
					// 新增一个外部人员
					String userId = addOutPerson(request, fdName,
							fdPhoneNum);
					// 跳转到签到页面
					if (StringUtil.isNotNull(userId)) {
						response.sendRedirect(StringUtil.formatUrl(
								"/resource/sys/attend/sysAttendAnym.do?method=signOuter"
										+ "&categoryId="
										+ categoryId
										+ "&userId=" + userId));
						return null;
					} else {
						throw new Exception();
					}
				}
			} else {
				return getActionForward("success", mapping, form,
						request,
						response);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-validateVCode", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

}
