package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.log.model.BaseSysLogLogin;
import com.landray.kmss.sys.log.service.ISysLogLoginService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.exception.SmsMaxTimesLimitationException;
import com.landray.kmss.sys.notify.exception.SmsSendingIntervalTimeException;
import com.landray.kmss.sys.notify.interfaces.*;
import com.landray.kmss.sys.notify.provider.MobileContext;
import com.landray.kmss.sys.notify.provider.SmsCodeNotifySendOperator;
import com.landray.kmss.sys.notify.provider.SysNotifySendImpl;
import com.landray.kmss.sys.notify.provider.SysNotifySmsSendBuilder;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgRetrievePasswordLog;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgRetrievePasswordLogService;
import com.landray.kmss.sys.organization.util.AjaxUtil;
import com.landray.kmss.sys.profile.model.PasswordSecurityConfig;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class SysOrgRetrievePasswordAction extends SysOrgPersonInfoAction {

	private static final Logger logger = LoggerFactory.getLogger(SysOrgRetrievePasswordAction.class);

	protected ISysOrgRetrievePasswordLogService sysOrgRetrievePasswordLogService;

	protected ISysOrgCoreService sysOrgCoreService;

	protected ISysNotifyMainCoreService sysNotifyMainCoreService;

	protected ISysLogLoginService sysLogLoginService;
	
	protected ISysOrgPersonService sysOrgPersonService;

	protected ISysOrgRetrievePasswordLogService getServiceImp(
			HttpServletRequest request) {
		if (sysOrgRetrievePasswordLogService == null) {
			sysOrgRetrievePasswordLogService = (ISysOrgRetrievePasswordLogService) getBean("sysOrgRetrievePasswordLogService");
		}
		return sysOrgRetrievePasswordLogService;
	}

	protected ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	protected ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	public ISysLogLoginService getSysLogLoginService() {
		if (sysLogLoginService == null) {
			sysLogLoginService = (ISysLogLoginService) getBean("sysLogLoginService");
		}
		return sysLogLoginService;
	}
	
	protected ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(createRandomVcode());
		}
	}

	public Date getDayBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 随机生成6位随机验证码
	 */
	public static String createRandomVcode() {
		// 验证码
		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int) (Math.random() * 10);
		}
		return vcode;
	}
	
	private ISysNotifySendOperator<SysOrgPerson> getISysNotifySendOperator(ISysNotifySendBuilder<SysOrgPerson> builder, final String code) throws Exception {
		return new SmsCodeNotifySendOperator<SysOrgPerson>(builder) {

			@Override
			protected int getSendedLogCount(SysOrgPerson person, Date startTime) throws Exception {
				List<SysOrgRetrievePasswordLog> result = getServiceImp(null)
						.findRetrievePasswordLogs(person.getFdId(), startTime);
				return result == null ? 0 : result.size();
			}

			@Override
			protected void saveSendedLog(String code, SysOrgPerson person) throws Exception {
				SysOrgRetrievePasswordLog log = new SysOrgRetrievePasswordLog();
				log.setFdCreateTime(new Date());
				log.setFdId(IDGenerator.generateID());
				log.setFdMobileCode(code);
				log.setFdPerson(person);
				getServiceImp(null).add(log);
			}

			@Override
			public String getSmsCode(SysOrgPerson person) {
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
	 * 发送短信验证码
	 * 
	 * @param request
	 * @param person
	 * @param code
	 * @param type
	 *            此方法现在有2个场景使用（1：找回密码，2：双因子验证登录）
	 * @throws Exception
	 */
	private void sendMobile(HttpServletRequest request, SysOrgPerson person,
			String code, int type) throws Exception {
		NotifyContext notifyContext = getSysNotifyMainCoreService().getContext(
				null);

		PasswordSecurityConfig config = new PasswordSecurityConfig();

		// 获取通知方式
		notifyContext.setNotifyType("mobile");
		// notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		List<SysOrgPerson> list = new ArrayList<SysOrgPerson>();
		list.add(person);
		notifyContext.setNotifyTarget(list);
		
		String subject = null;
		switch (type) {
		case 1: // 找回密码
			subject = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.send.content", "sys-organization",
					new Object[] { code, config.getCodeEffectiveTime() });
			break;
		case 2: // 双因子验证登录
			subject = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.doubleValidation.send.content",
					"sys-organization",
					new Object[] { code, config.getCodeEffectiveTime() });
			break;
		}

		notifyContext.setSubject(subject);
		notifyContext.setContent(subject);
		
		NotifyContextImp context = (NotifyContextImp) notifyContext;
		context.setModelName(SysOrgRetrievePasswordLog.class.getName());

		MobileContext mobileContext = notifyContext
				.getExtendContext(MobileContext.class);
		mobileContext.setPriority(10);
		SysCfgModule sysConfig = SysConfigs.getInstance().getModule("/sys/organization/");
		String moduleName = ResourceUtil.getString(sysConfig.getMessageKey());
		mobileContext.setModuleSource(moduleName);
		getSysNotifyMainCoreService().sendSms(null, notifyContext, null);
	}

	private static void forwardResult(boolean result, String errMsg,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		json.accumulate("result", result);
		json.accumulate("errMsg", errMsg);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
	}

	private static void forwardResult(boolean result, String errCode,
			String errMsg, HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		json.accumulate("result", result);
		json.accumulate("errMsg", errMsg);
		json.accumulate("errCode", errCode);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
	}

	private Date getAvailableTime(PasswordSecurityConfig config){
		return new Date(new Date().getTime() - config.getCodeEffectiveTime() * 60000L);
	}

	private String getPreAvailableCode(PasswordSecurityConfig config,
			SysOrgPerson person) throws Exception {
		// 取验证的时间应该要关联后台配置中的有效期
		Date availableTime = getAvailableTime(config);
		List<SysOrgRetrievePasswordLog> list = getServiceImp(null)
				.findRetrievePasswordLogs(person.getFdId(), availableTime);
		if (list != null && !list.isEmpty()) {
			return list.get(0).getFdMobileCode();
		}
		return null;
	}

	private void insertLog(String code, SysOrgPerson person) throws Exception {
		SysOrgRetrievePasswordLog log = new SysOrgRetrievePasswordLog();
		log.setFdCreateTime(new Date());
		log.setFdId(IDGenerator.generateID());
		log.setFdMobileCode(code);
		log.setFdPerson(person);

		getServiceImp(null).add(log);

	}

	public ActionForward sendMobileValidationCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-sendMobileValidationCode", true,
				getClass());
		String errMsg = "";
		String errCode = "";
		boolean result = false;
		String personId = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_PERSON");
		SysOrgPerson person = null;
		try {
			if(StringUtil.isNotNull(personId)) {
				person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
			}
		} catch (Exception e1) {
			logger.debug("人员id "+personId+" 不存在");
		}
		
		if (person == null) {
			String url = request.getContextPath()
					+ "/sys/organization/sys_org_retrieve_password/validateUser.jsp";
			errMsg = "<a href=\""
					+ url
					+ "\">"
					+ ResourceUtil.getString(request,
							"sysOrgRetrievePassword.error.noUserInSession",
							"sys-organization") + "</a>";
			errCode = "noUserInSession";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		
		SysCfgModule sysConfig = SysConfigs.getInstance().getModule("/sys/organization/");
		String moduleSource = ResourceUtil.getString(sysConfig.getMessageKey());
		String scene = ResourceUtil.getString("sys-organization:organization.notify.scene.validationCode");
		
		final String code = createRandomVcode();
		PasswordSecurityConfig config = null;
		try {
			config = new PasswordSecurityConfig();
			String subject = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.send.content", "sys-organization",
					new Object[] { code, config.getCodeEffectiveTime() });
			String content = subject;
			
			ISysNotifySendBuilder<SysOrgPerson> builder = new SysNotifySmsSendBuilder<SysOrgPerson>(null, moduleSource, scene, Arrays.asList(person), subject, content);
			NotifyContextImp context = (NotifyContextImp) builder.getNotifyContext();
			context.setModelName(SysOrgRetrievePasswordLog.class.getName());

			ISysNotifySendOperator<SysOrgPerson> operator = getISysNotifySendOperator(builder, code);
			ISysNotifySendAbstract sendInstantce = new SysNotifySendImpl(operator);
			sendInstantce.send();
		}
		catch(SmsMaxTimesLimitationException e)
		{
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.overTimes",
					"sys-organization", new Object[] { config
							.getMaxTimesOneDay() });
			errCode = "overTimes";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		catch(SmsSendingIntervalTimeException e) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.toOften",
					"sys-organization", new Object[] { config
							.getReSentIntervalTime() });
			errCode = "toOften";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		catch (Exception e) {
			logger.error(e.toString());
			errMsg = e.getMessage();
			forwardResult(result, errMsg, response);
			return null;
		}
		forwardResult(result, errMsg, response);
		return null;
	}
	
	public ActionForward sendVCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-sendMobileValidationCode", true,
				getClass());
		String errMsg = "";
		String errCode = "";
		boolean result = false;
		SysOrgPerson person = (SysOrgPerson) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_PERSON");
		if (person == null) {
			errMsg = ResourceUtil.getString(request,
							"sysOrgRetrievePassword.error.noUserInSession",
							"sys-organization");
			errCode = "noUserInSession";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		
		SysCfgModule sysConfig = SysConfigs.getInstance().getModule("/sys/organization/");
		String moduleSource = ResourceUtil.getString(sysConfig.getMessageKey());
		String scene = ResourceUtil.getString("sys-organization:organization.notify.scene.validationCode");
		
		PasswordSecurityConfig config = null;
		final String code = createRandomVcode();
		try {
			config = new PasswordSecurityConfig();
			String subject = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.send.content", "sys-organization",
					new Object[] { code, config.getCodeEffectiveTime() });
			String content = subject;
			
			ISysNotifySendBuilder<SysOrgPerson> builder = new SysNotifySmsSendBuilder<SysOrgPerson>(null, moduleSource, scene, Arrays.asList(person), subject, content);
			NotifyContextImp context = (NotifyContextImp) builder.getNotifyContext();
			context.setModelName(SysOrgRetrievePasswordLog.class.getName());

			ISysNotifySendOperator<SysOrgPerson> operator = getISysNotifySendOperator(builder, code);
			ISysNotifySendAbstract sendInstantce = new SysNotifySendImpl(operator);
			sendInstantce.send();	
		}
		catch(SmsMaxTimesLimitationException e)
		{
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.overTimes",
					"sys-organization", new Object[] { config
							.getMaxTimesOneDay() });
			errCode = "overTimes";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		catch(SmsSendingIntervalTimeException e) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.toOften",
					"sys-organization", new Object[] { config
							.getReSentIntervalTime() });
			errCode = "toOften";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		catch (Exception e) {
			logger.error(e.toString());
			errMsg = e.getMessage();
			forwardResult(result, errMsg, response);
			return null;
		}
		
		forwardResult(result, errMsg, response);
		return null;
	}

	public ActionForward validateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-validateUser", true, getClass());
		KmssMessages messages = new KmssMessages();
		String userid = request.getParameter("userid");
		String vCode = request.getParameter("v_code");
		String errMsg = "";
		try {
			if (StringUtil.isNull(userid) && StringUtil.isNull(vCode)) {
				HttpSession session = request.getSession();
				String personId = (String) session.getAttribute(
						"RETRIEVE_PASSWORD_PERSON");
				SysOrgPerson person = null;
				try {
					if(StringUtil.isNotNull(personId)) {
						person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
					}
				} catch (Exception e1) {
					logger.debug("人员id "+personId+" 不存在");
				}
				if (person != null) {
					Boolean failFlag = (Boolean) session
							.getAttribute("failFlag");
					if (failFlag == true) {
						session.setAttribute("failFlag", false);
					}
					String loginName = person.getFdLoginName();
					String mobileNo = getMobileNoStr(person.getFdMobileNo());
					request.setAttribute("tip", ResourceUtil.getString(request,
							"sysOrgRetrievePassword.validation.type.tip",
							"sys-organization",
							new Object[] { loginName, mobileNo }));
					PasswordSecurityConfig config = new PasswordSecurityConfig();
					String code = getPreAvailableCode(config, person);
					if (StringUtil.isNotNull(code)) {
						Integer vCount = (Integer) session
								.getAttribute("VALIDATION_COUNT");
						if (vCount != null && vCount == 0) {
							code = createRandomVcode();
							insertLog(code, person);
						}
					}
					return mapping.findForward("retrieveType");
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		}

		if (StringUtil.isNull(userid)) {
			errMsg = ResourceUtil
					.getString(request,
							"sysOrgRetrievePassword.error.userNull",
							"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}
		if (StringUtil.isNull(vCode)) {
			errMsg = ResourceUtil
					.getString(request,
							"sysOrgRetrievePassword.error.vCodeNull",
							"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}
		String VALIDATION_CODE = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_VALIDATION_CODE");
		if (StringUtil.isNull(VALIDATION_CODE)) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.noValidationCodeInSession",
					"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}
		if (!VALIDATION_CODE.equalsIgnoreCase(vCode)) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.validationCodeError",
					"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}else {
			request.getSession().setAttribute("RETRIEVE_PASSWORD_VALIDATION_CODE", IDGenerator.generateID());
		}
		
		String personId = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_PERSON");
		SysOrgPerson sessionPerson = null;
		try {
			if(StringUtil.isNotNull(personId)) {
				sessionPerson = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
			}
		} catch (Exception e1) {
			logger.debug("人员id "+personId+" 不存在");
		}
		if (sessionPerson != null
				&& sessionPerson.getFdLoginName().equals(userid)) {
			request.getSession().setAttribute("failFlag", true);
		} else {
			request.getSession().setAttribute("failFlag", false);
		}
		SysOrgPerson person;
		try {
			person = getSysOrgCoreService().findByLoginNameOrMobileNo(userid);
			if (person == null) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.userNoFound",
						"sys-organization");
				request.setAttribute("errMsg", errMsg);
				return mapping.findForward("validateUser");
			}
			String loginName = person.getFdLoginName();
			String mobileNo = getMobileNoStr(person.getFdMobileNo());
			if (StringUtil.isNull(mobileNo)) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.userNoMobileNo",
						"sys-organization", new Object[] { loginName });
				request.setAttribute("errMsg", errMsg);
				return mapping.findForward("validateUser");
			}
			request.getSession().setAttribute("RETRIEVE_PASSWORD_PERSON",
					person.getFdId());
			request.setAttribute("loginName", loginName);
			request.setAttribute("mobileNo", mobileNo);
			request.setAttribute("tip", ResourceUtil.getString(request,
					"sysOrgRetrievePassword.validation.type.tip",
					"sys-organization", new Object[] { loginName, mobileNo }));
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("retrieveType");
		}

	}
	
	public ActionForward validateAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		TimeCounter.logCurrentTime("Action-validateAccount", true, getClass());
		KmssMessages messages = new KmssMessages();
		String userid = request.getParameter("userid");
		String vCode = request.getParameter("v_code");
		String errMsg = "";
		if (StringUtil.isNull(userid)) {
			errMsg = ResourceUtil
					.getString(request,
							"sysOrgRetrievePassword.error.userNull",
							"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}
		if (StringUtil.isNull(vCode)) {
			errMsg = ResourceUtil
					.getString(request,
							"sysOrgRetrievePassword.error.vCodeNull",
							"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}
		String VALIDATION_CODE = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_VALIDATION_CODE");
		if (StringUtil.isNull(VALIDATION_CODE)) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.noValidationCodeInSession",
					"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}
		if (!VALIDATION_CODE.equalsIgnoreCase(vCode)) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.validationCodeError",
					"sys-organization");
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("validateUser");
		}else {
			request.getSession().setAttribute("RETRIEVE_PASSWORD_VALIDATION_CODE", IDGenerator.generateID());
		}
		SysOrgPerson person;
		try {
			person = getSysOrgCoreService().findByLoginNameOrMobileNo(userid);
			if (person == null) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.userNoFound",
						"sys-organization");
				request.setAttribute("errMsg", errMsg);
				return mapping.findForward("validateUser");
			}
			String loginName = person.getFdLoginName();
			String mobileNo = getMobileNoStr(person.getFdMobileNo());
			if (StringUtil.isNull(mobileNo)) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.userNoMobileNo",
						"sys-organization", new Object[] { loginName });
				request.setAttribute("errMsg", errMsg);
				return mapping.findForward("validateUser");
			}
			request.getSession().setAttribute("RETRIEVE_PASSWORD_PERSON",
					person.getFdId());
			request.setAttribute("loginName", loginName);
			request.setAttribute("mobileNo", mobileNo);
			PasswordSecurityConfig config = new PasswordSecurityConfig();
			request.setAttribute("reSentIntervalTime", config.getReSentIntervalTime());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("retrievePassword");
		}
	}
	
	public String getMobileNoStr(String mobileNo) {
		if(StringUtil.isNull(mobileNo)){
			return null;
		}
		if (mobileNo.length() > 7) {
			mobileNo = mobileNo.substring(0, 3) + "****"
					+ mobileNo.substring(7, mobileNo.length());
		}
		return mobileNo;
	}

	public ActionForward toSendValidationCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-toSendValidationCode", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		String errMsg = "";
		String personId = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_PERSON");
		SysOrgPerson person = null;
		try {
			if(StringUtil.isNotNull(personId)) {
				person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
			}
		} catch (Exception e1) {
			logger.debug("人员id "+personId+" 不存在");
		}
		if (person == null) {
			String url = request.getContextPath()
					+ "/sys/organization/sys_org_retrieve_password/validateUser.jsp";

			errMsg = "<a color=\"red\" style=\"color:red;\" href=\""
					+ url
					+ "\">"
					+ ResourceUtil.getString(
							"sysOrgRetrievePassword.error.noUserInSession",
							"sys-organization", request.getLocale()) + "</a>";
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("retrieveType");
		}
		String mobileNo = getMobileNoStr(person.getFdMobileNo());

		request.setAttribute("loginName", person.getFdLoginName());
		request.setAttribute("mobileNo", mobileNo);
		PasswordSecurityConfig config;
		try {
			config = new PasswordSecurityConfig();
			request.setAttribute("reSentIntervalTime", config
					.getReSentIntervalTime());
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("toSendValidationCode");
		}

	}

	public ActionForward validateMobileCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-validateMobileCode", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		String mCode = request.getParameter("m_code");
		String errMsg = "";
		String errCode = "";
		Integer vCount = (Integer) request.getSession()
				.getAttribute("VALIDATION_COUNT");
		if (vCount == null) {
			vCount = 0;
		}
		try {
			PasswordSecurityConfig config = new PasswordSecurityConfig();
			request.setAttribute("reSentIntervalTime", config
					.getReSentIntervalTime());

			String personId = (String) request.getSession().getAttribute(
					"RETRIEVE_PASSWORD_PERSON");
			SysOrgPerson person = null;
			try {
				if(StringUtil.isNotNull(personId)) {
					person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
				}
			} catch (Exception e1) {
				logger.debug("人员id "+personId+" 不存在");
			}
			if (person == null) {
				String url = request.getContextPath()
						+ "/sys/organization/sys_org_retrieve_password/validateUser.jsp";
				errMsg = "<a href=\""
						+ url
						+ "\">"
						+ ResourceUtil.getString(request,
								"sysOrgRetrievePassword.error.noUserInSession",
								"sys-organization") + "</a>";
				errCode = "noUserInSession";
				request.setAttribute("errMsg", errMsg);
				request.setAttribute("errCode", errCode);
				return mapping.findForward("sendValidationCode");
			}

			String mobileNo = getMobileNoStr(person.getFdMobileNo());

			request.setAttribute("loginName", person.getFdLoginName());
			request.setAttribute("mobileNo", mobileNo);

			if (StringUtil.isNull(mCode)) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.mobileCodeNull",
						"sys-organization");
				errCode = "mobileCodeNull";
				request.setAttribute("errMsg", errMsg);
				request.setAttribute("errCode", errCode);
				return mapping.findForward("sendValidationCode");
			}

			Date availableTime = getAvailableTime(config);

			List<SysOrgRetrievePasswordLog> list = getServiceImp(request)
					.findRetrievePasswordLogs(person.getFdId(), availableTime);
			if (list == null || list.isEmpty()) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.mobileCodeOutOfDate",
						"sys-organization");
				errCode = "mobileCodeOutOfDate";
				request.setAttribute("errMsg", errMsg);
				request.setAttribute("errCode", errCode);
				return mapping.findForward("sendValidationCode");
			}
			SysOrgRetrievePasswordLog log = list.get(0);
			if (mCode.equals(log.getFdMobileCode())) {
				getServiceImp(request).delete(log);
				request.getSession().setAttribute("RETRIEVE_PASSWORD_VALIDATION_PASS", "true");
				return mapping.findForward("updatePassword");
			}

			if (vCount == 2) {
				request.getSession().setAttribute("VALIDATION_COUNT", 0);
				request.getSession().setAttribute("failFlag", true);
				return mapping.findForward("sendValidationCode");
			}
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.mobileCodeError",
					"sys-organization");
			errCode = "mobileCodeError";
			request.getSession().setAttribute("VALIDATION_COUNT",
					++vCount);
			request.setAttribute("errMsg", errMsg);
			request.setAttribute("errCode", errCode);
			return mapping.findForward("sendValidationCode");
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("sendValidationCode");
		}

	}
	
	public ActionForward validateVCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-validateVCode", true, getClass());
		String mCode = request.getParameter("m_code");
		String errMsg = "";
		String errCode = "";
		boolean result = false;
		try{
			PasswordSecurityConfig config = new PasswordSecurityConfig();
			String personId = (String) request.getSession().getAttribute(
					"RETRIEVE_PASSWORD_PERSON");
			SysOrgPerson person = null;
			try {
				if(StringUtil.isNotNull(personId)) {
					person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
				}
			} catch (Exception e1) {
				logger.debug("人员id "+personId+" 不存在");
			}
			if (person == null) {
				errMsg = ResourceUtil.getString(request,
								"sysOrgRetrievePassword.error.noUserInSession",
								"sys-organization");
				errCode = "noUserInSession";
				forwardResult(result, errCode, errMsg, response);
				return null;
			}
			
			if (StringUtil.isNull(mCode)) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.mobileCodeNull",
						"sys-organization");
				errCode = "mobileCodeNull";
				forwardResult(result, errCode, errMsg, response);
				return null;
			}
			
			Date availableTime = getAvailableTime(config);

			List<SysOrgRetrievePasswordLog> list = getServiceImp(request)
					.findRetrievePasswordLogs(person.getFdId(), availableTime);
			
			if (list == null || list.isEmpty()) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.mobileCodeOutOfDate",
						"sys-organization");
				errCode = "mobileCodeOutOfDate";
				forwardResult(result, errCode, errMsg, response);
				return null;
			}
			for (SysOrgRetrievePasswordLog log : list) {
				if (mCode.equals(log.getFdMobileCode())) {
					result = true;
					forwardResult(result, errMsg, response);
					return null;
				}
			}
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.mobileCodeError",
					"sys-organization");
			errCode = "mobileCodeError";
			forwardResult(result, errCode, errMsg, response);
			return null;
			
		} catch (Exception e) {
			logger.error(e.toString());
			errMsg = e.getMessage();
			forwardResult(result, errMsg, response);
			return null;
		}
		
	}

	public ActionForward saveNewPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		String personId = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_PERSON");
		SysOrgPerson person = null;
		try {
			if(StringUtil.isNotNull(personId)) {
				person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
			}
		} catch (Exception e1) {
			logger.debug("人员id "+personId+" 不存在");
		}
		String val = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_VALIDATION_PASS");

		try {
			if (person == null || !"true".equals(val)) {
				request.setAttribute("pwdErrorType", "10");
				throw new Exception();
			}
			if (UserOperHelper.allowLogOper("saveNewPwd", getPersonService().getModelName())) {
				UserOperContentHelper.putUpdate(person);
				UserOperHelper.setOperSuccess(true);
			}
			String newPwd = request.getParameter("fdNewPassword");
			if (StringUtil.isNull(newPwd)) {
				throw new KmssException(new KmssMessage("errors.required",
						new KmssMessage(
								"sys-organization:sysOrgPerson.newPassword")));
			}
			if ("admin".equals(UserUtil.getUser().getFdLoginName())) {
				adminPasswordStrength(newPwd);
			} else {
				// 密码安全级别检查
				passwordStrength(newPwd);
			}
			// 增加找回密码的标识，用于记录修改日志
			RequestContext rc = new RequestContext(request);
			rc.setParameter("retrievePassword", "true"); // 找回密码标识
			rc.setParameter("targetId", person.getFdId()); // 操作人ID
			rc.setParameter("targetName", person.getFdName()); // 操作人名称
			getPersonService().saveNewPassword(person.getFdId(), newPwd, rc);
			// 密码修改成功后，解除强制修改标识
			request.getSession().removeAttribute("compulsoryChangePassword");
			request.getSession().removeAttribute("RETRIEVE_PASSWORD_PERSON");
			request.getSession().removeAttribute("RETRIEVE_PASSWORD_VALIDATION_PASS");
		} catch (Exception e) {
			messages.addError(e);
			if (UserOperHelper.allowLogOper("saveNewPwd", getPersonService().getModelName())) {
				UserOperContentHelper.putUpdate(person);
				UserOperHelper.setOperSuccess(false);
				UserOperHelper.logErrorMessage(StringUtil.getStackTrace(e));
			}
		}
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addMessages(messages).setOperationKey(
				"sys-organization:sysOrgPerson.button.changePassword").save(
				request);
		boolean ajax = AjaxUtil.requiredJson(request);
		if (!ajax) {
			String s_ajax = request.getParameter("s_ajax");
			if ("true".equals(s_ajax)) {
				ajax = true;
			}
		}
		if (messages.hasError()) {
			if (ajax) {
				String pwdErrorType = (String) request
						.getAttribute("pwdErrorType");
				if (StringUtil.isNotNull(pwdErrorType)) {
					AjaxUtil.saveMessagesToJson(request, pwdErrorType);
					return mapping.findForward("lui-failure");
				} else {
					AjaxUtil.saveMessagesToJson(request, messages.getMessages()
							.get(0).getThrowable().getMessage());
					return mapping.findForward("lui-source");
				}
			}
			return mapping.findForward("updatePassword");
		} else {
			if (ajax) {
				AjaxUtil
						.saveMessagesToJson(
								request,
								ResourceUtil
										.getString("sys-organization:sysOrgPerson.chgMyPwd.success"));
				return mapping.findForward("lui-source");
			}
			rtnPage.addButton(KmssReturnPage.BUTTON_RETURN);
			return mapping.findForward("retrievePasswordSuccess");

		}
	}
	
	@Override
	public ActionForward savePwd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		String personId = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_PERSON");
		SysOrgPerson person = null;
		try {
			if(StringUtil.isNotNull(personId)) {
				person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(personId);
			}
		} catch (Exception e1) {
			logger.debug("人员id "+personId+" 不存在");
		}
		String val = (String) request.getSession().getAttribute(
				"RETRIEVE_PASSWORD_VALIDATION_PASS");
		try {
			if (person == null || !"true".equals(val)) {
				forwardResult(false, "10", null, response);
				return null;
			}
			String newPwd = request.getParameter("fdNewPassword");
			if (StringUtil.isNull(newPwd)) {
				throw new KmssException(new KmssMessage("errors.required",
								"sys-organization:sysOrgPerson.newPassword"));
			}
			if ("admin".equals(UserUtil.getUser().getFdLoginName())) {
				adminPasswordStrength(newPwd);
			} else {
				// 密码安全级别检查
				passwordStrength(newPwd);
			}
			getPersonService().saveNewPassword(person.getFdId(), newPwd,
					new RequestContext(request));
			// 密码修改成功后，解除强制修改标识
			request.getSession().removeAttribute("compulsoryChangePassword");
			request.getSession().removeAttribute("RETRIEVE_PASSWORD_PERSON");
			request.getSession().removeAttribute("RETRIEVE_PASSWORD_VALIDATION_PASS");
		} catch (Exception e) {
			messages.addError(e);
		}
		try{
			if (messages.hasError()) {
				String pwdErrorType = (String) request.getAttribute("pwdErrorType");
				if (StringUtil.isNotNull(pwdErrorType)) {
					forwardResult(false, pwdErrorType, null, response);
					return null;
				} else {
					KmssMessage message = messages.getMessages().get(0);
					String msg = ResourceUtil.getString(
									message.getMessageKey(), null, request.getLocale(),
									message.getParameter());
					forwardResult(false, null, msg, response);
					return null;
				}
			} else {
				forwardResult(true, null, null, response);
				return null;
			}
		} catch (IOException e) {
			logger.error(e.toString());
			return null;
		}
	}
	
	// =======================================以下代码为双因子验证（账号密码登录成功后还需要手机短信验证）===========================================
	/**
	 * 跳转到短信验证页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDoubleValidation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-toDoubleValidation", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysOrgPerson person = UserUtil.getUser();
		if (person == null || person.isAnonymous()) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return null;
		}

		String mobileNo = getMobileNoStr(person.getFdMobileNo());

		if (StringUtil.isNull(mobileNo)) {
			String userNoMobileNo = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.userNoMobileNo",
					"sys-organization",
					new Object[] { person.getFdLoginName() });
			request.setAttribute("userNoMobileNo", userNoMobileNo);
		}

		request.setAttribute("loginName", person.getFdLoginName());
		request.setAttribute("mobileNo", mobileNo);
		PasswordSecurityConfig config;
		try {
			config = new PasswordSecurityConfig();
			request.setAttribute("reSentIntervalTime", config
					.getReSentIntervalTime());
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("toDoubleValidation");
		}
	}

	/**
	 * 获取短信密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward sendDoubleValidationCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-sendDoubleValidationCode", true,
				getClass());
		String errMsg = "";
		String errCode = "";
		boolean result = false;
		SysOrgPerson person = UserUtil.getUser();
		if (person == null || person.isAnonymous()) {
			errCode = "-1";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		
		SysCfgModule sysConfig = SysConfigs.getInstance().getModule("/sys/organization/");
		String moduleSource = ResourceUtil.getString(sysConfig.getMessageKey());
		String scene = ResourceUtil.getString("sys-organization:organization.notify.scene.doubleValidationCode");
		
		PasswordSecurityConfig config = null;
		final String code = createRandomVcode();
		try {
			config = new PasswordSecurityConfig();
			String subject = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.doubleValidation.send.content",
					"sys-organization",
					new Object[] { code, config.getCodeEffectiveTime() });
			String content = subject;

			ISysNotifySendBuilder<SysOrgPerson> builder = new SysNotifySmsSendBuilder<SysOrgPerson>(null, moduleSource, scene, Arrays.asList(person), subject, content);
			NotifyContextImp context = (NotifyContextImp) builder.getNotifyContext();
			context.setModelName(SysOrgRetrievePasswordLog.class.getName());

			ISysNotifySendOperator<SysOrgPerson> operator = getISysNotifySendOperator(builder, code);
			ISysNotifySendAbstract sendInstantce = new SysNotifySendImpl(operator);
			sendInstantce.send();
			result = true;
		}
		catch(SmsMaxTimesLimitationException e)
		{
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.overTimes",
					"sys-organization", new Object[] { config
							.getMaxTimesOneDay() });
			errCode = "overTimes";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		catch(SmsSendingIntervalTimeException e) {
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.toOften",
					"sys-organization", new Object[] { config
							.getReSentIntervalTime() });
			errCode = "toOften";
			forwardResult(result, errCode, errMsg, response);
			return null;
		}
		catch (Exception e) {
			logger.error(e.toString());
			errMsg = e.getMessage();
			forwardResult(result, errMsg, response);
			return null;
		}
		forwardResult(result, errMsg, response);
		return null;
	}

	/**
	 * 短信密码校验
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward validateDoubleCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-validateVCode", true, getClass());
		String mCode = request.getParameter("m_code");
		String errMsg = "";
		String errCode = "";
		boolean result = false;
		try {
			SysOrgPerson person = UserUtil.getUser();
			if (person == null || person.isAnonymous()) {
				errCode = "-1";
				forwardResult(result, errCode, errMsg, response);
				return null;
			}

			PasswordSecurityConfig config = new PasswordSecurityConfig();
			if (StringUtil.isNull(mCode)) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.mobileCodeNull",
						"sys-organization");
				errCode = "mobileCodeNull";
				forwardResult(result, errCode, errMsg, response);
				return null;
			}

			Date availableTime = getAvailableTime(config);

			List<SysOrgRetrievePasswordLog> list = getServiceImp(request)
					.findRetrievePasswordLogs(person.getFdId(), availableTime);

			if (list == null || list.isEmpty()) {
				errMsg = ResourceUtil.getString(request,
						"sysOrgRetrievePassword.error.mobileCodeOutOfDate",
						"sys-organization");
				errCode = "mobileCodeOutOfDate";
				forwardResult(result, errCode, errMsg, response);
				return null;
			}
			for (SysOrgRetrievePasswordLog log : list) {
				if (mCode.equals(log.getFdMobileCode())) {
					// 短信验证成功，需要清除session信息，更新登录日志
					request.getSession().removeAttribute("DOUBLE_VALIDATION_STATE");
					request.getSession().removeAttribute("j_redirectto");
					getSysLogLoginService().updateLoginLog(person, BaseSysLogLogin.TYPE_SUCCESS);
					result = true;
					forwardResult(result, errMsg, response);
					return null;
				}
			}
			
			// 程序走到这里，就是输入的验证码和发送的验证码不匹配
			getSysLogLoginService().updateLoginLog(person, BaseSysLogLogin.TYPE_FAILURE_DOUBLE_VALIDATE);
			errMsg = ResourceUtil.getString(request,
					"sysOrgRetrievePassword.error.mobileCodeError",
					"sys-organization");
			errCode = "mobileCodeError";
			forwardResult(result, errCode, errMsg, response);
			return null;
		} catch (Exception e) {
			logger.error(e.toString());
			errMsg = e.getMessage();
			forwardResult(result, errMsg, response);
			return null;
		}
	}
	
}
