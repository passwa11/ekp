package com.landray.kmss.sys.organization.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.log.forms.SysLogChangePwdForm;
import com.landray.kmss.sys.log.forms.SysLogLoginForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.forms.SysOrgPersonInfoForm;
import com.landray.kmss.sys.organization.interfaces.OrgPassUpdatePlugin;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.AjaxUtil;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.profile.model.PasswordSecurityConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.action.ActionRedirect;
import com.landray.kmss.web.filter.security.TrustSiteChecker;

/**
 * @version 1.0
 * @author
 */
public class SysOrgPersonInfoAction extends BaseAction implements
		SysOrgConstant {
	/**
	 * 是否禁用本系统数据库的身份验证
	 */
	private static String filterDisable = ResourceUtil
			.getKmssConfigString("kmss.authentication.processing.filter.disable");
	/**
	 * 例外人员
	 */
	private static String excludeUser = ResourceUtil
			.getKmssConfigString("kmss.authentication.processing.filter.exclude.user");
	private ISysOrgPersonService personService = null;

	private ISysOrgElementService elementService = null;

	private ISysOrgElementService getElementService() {
		if (elementService == null) {
            elementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
		return elementService;
	}

	protected ISysOrgPersonService getPersonService() {
		if (personService == null) {
            personService = (ISysOrgPersonService) getBean("sysOrgPersonService");
        }
		return personService;
	}

	public ActionForward chgMyPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtil.isNotNull(filterDisable)
				&& "true".equalsIgnoreCase(filterDisable)) {
			// 是否允许修改个人密码
			boolean canModify = false;
			if (StringUtil.isNotNull(excludeUser)) {
				String username = UserUtil.getKMSSUser().getUsername();
				String[] excludeUsers = excludeUser.split("\\s*[,;]\\s*");
				for (int i = 0; i < excludeUsers.length; i++) {
					if (excludeUsers[i].equals(username)) {
						canModify = true;
						break;
					}
				}
			}
			if (!canModify) {
				KmssReturnPage.getInstance(request).setOperationKey(
						"sys-organization:sysOrgPerson.chgMyPwd.errorMsg")
						.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
				return mapping.findForward("failure");
			}
		}
		return mapping.findForward("chgMyPwd");
	}
	
	public ActionForward chgPwdSecure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-chgPwdSecure", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List<SysLogLoginForm>  sysLogLoginFormList = getElementService().getLoginLogByOperatorList(UserUtil.getKMSSUser().getUserId(), 10,
					 "sysLogLogin.fdCreateTime desc");
			List<SysLogChangePwdForm>  sysLogChangePwdFormList= getElementService().getSysLogChangePwdList(UserUtil.getKMSSUser().getUserId(), 5, "sysLogChangePwd.fdCreateTime desc");
			request.setAttribute("logLoginDatas", sysLogLoginFormList);
			request.setAttribute("LogChangePwdDatas", sysLogChangePwdFormList);
			request.setAttribute("logLoginCount", sysLogLoginFormList!=null?sysLogLoginFormList.size():0);
			//是否禁用本系统数据库的身份验证登录			
			Boolean authDisable = ("true".equals(filterDisable) && OrgPassUpdatePlugin.getEnabledExtensionList().size()<=0) ? true : false;
			request.setAttribute("authDisable", authDisable);
			// 判断是否需要显示密码到期剩余时间
			boolean needShowExpire = false;
			if (isEnabled()) {
				SysOrgPerson person = (SysOrgPerson) getPersonService().findByPrimaryKey(UserUtil.getKMSSUser().getUserId());
				needShowExpire = needChange(person);
			}
			request.setAttribute("needShowExpire", needShowExpire);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-chgPwdSecure", false, getClass());
		if (messages.hasError()){
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		}else{
			return mapping.findForward("chgPwdSecure");
		}
	}
	
	/**
	 * 检查admin.do是否开启此功能
	 * @return
	 */
	private boolean isEnabled() {
		String passwordchangeday = PasswordSecurityConfig.newInstance().getKmssOrgPasswordchangeday(); // 到期天数
		String passwordremindday = PasswordSecurityConfig.newInstance().getKmssOrgPasswordremindday(); // 提醒天数
		if (StringUtil.isNull(passwordchangeday) || "0".equals(passwordchangeday)
				|| StringUtil.isNull(passwordremindday) || "0".equals(passwordremindday)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取密码到期时间（天数）
	 * 
	 * @param person
	 * @return
	 */
	private boolean needChange(SysOrgPerson person) {
		String passwordchangeday = PasswordSecurityConfig.newInstance().getKmssOrgPasswordchangeday(); // 到期天数
		String passwordremindday = PasswordSecurityConfig.newInstance().getKmssOrgPasswordremindday(); // 提醒天数
		int changeday = Integer.valueOf(passwordchangeday);
		int remindday = Integer.valueOf(passwordremindday);
		
		Date date = person.getFdLastChangePwd(); // 获取上次修改密码时间
		if (date == null) {
			date = person.getFdCreateTime(); // 如果没有修改过密码，则获取用户的创建时间
			if (date == null) {
				date = person.getFdAlterTime(); // 如果是导入的数据，创建时间可能会为空，则获取修改时间
			}
		}

		return ((new Date().getTime() - date.getTime()) / DateUtil.DAY) >= (changeday - remindday);
	}
	
	public ActionForward chgPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("chgPwd");
	}

	public ActionForward chgInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String forward = request.getParameter("forward");
		try {
			if (StringUtil.isNull(forward)) {
                forward = "chgInfo";
            }
			String para = request.getParameter("fdId");
			String id = null;
			if (!StringUtil.isNull(para)) {
				id = para;
			} else {
				id = UserUtil.getUser().getFdId();
			}
			SysOrgPerson person = (SysOrgPerson) getPersonService()
					.findByPrimaryKey(id);
			SysOrgPersonInfoForm personForm = (SysOrgPersonInfoForm) form;
			personForm.setFdName(person.getFdName());
			personForm.setFdEmail(person.getFdEmail());
			personForm.setFdMobileNo(person.getFdMobileNo());
			personForm.setFdWorkPhone(person.getFdWorkPhone());
			personForm.setFdDefaultLang(person.getFdDefaultLang());
			personForm.setFdMemo(person.getFdMemo());
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.setOperationKey(
							"sys-organization:sysOrgPerson.button.changeInfo")
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward(forward);
		}
	}

	public ActionForward saveInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String para = request.getParameter("fdId");
			String id = null;
			if (!StringUtil.isNull(para)) {
				id = para;
			} else {
				id = UserUtil.getUser().getFdId();
			}
			SysOrgPerson person = (SysOrgPerson) getPersonService()
					.findByPrimaryKey(id);
			SysOrgPersonInfoForm personForm = (SysOrgPersonInfoForm) form;
			person.setFdEmail(personForm.getFdEmail());
			person.setFdMobileNo(personForm.getFdMobileNo());
			person.setFdMemo(personForm.getFdMemo());
			person.setFdWorkPhone(personForm.getFdWorkPhone());
			person.setFdDefaultLang(personForm.getFdDefaultLang());
			getPersonService().update(person);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addMessages(messages).setOperationKey(
				"sys-organization:sysOrgPerson.button.changeInfo")
				.save(request);
		boolean ajax = AjaxUtil.requiredJson(request);
		if (messages.hasError()) {
			if (ajax) {
				AjaxUtil.saveMessagesToJson(request, rtnPage);
				return mapping.findForward("lui-failure");
			}
			return mapping.findForward("chgInfo");
		} else {
			if (ajax) {
				AjaxUtil
						.saveMessagesToJson(
								request,
								ResourceUtil
										.getString("sys-organization:sysOrgPerson.chgInfo.success"));
				return mapping.findForward("lui-source");
			}
			rtnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return mapping.findForward("success");
		}
	}

	/**
	 * 管理员重置密码，不需要遵循admin.do中密码长度和强度要求，admin用户除外
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward savePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
			String pwd = request.getParameter("fdNewPassword");
			if (StringUtil.isNull(pwd)) {
                throw new KmssException(new KmssMessage("errors.required",
                        new KmssMessage(
                                "sys-organization:sysOrgPerson.newPassword")));
            }
			String id = request.getParameter("fdId");
			SysOrgPerson person = (SysOrgPerson) getPersonService()
					.findByPrimaryKey(id);
			if (UserOperHelper.allowLogOper("savePwd", SysOrgPerson.class.getName())) {
				UserOperContentHelper.putUpdate(person);
			}
			if ("admin".equals(person.getFdLoginName())) {
				adminPasswordStrength(pwd);
			}

			getPersonService().savePassword(id, pwd, new RequestContext(request));
			
			if (StringUtil.isNotNull(filterDisable)
					&& "true".equalsIgnoreCase(filterDisable) && OrgPassUpdatePlugin.getEnabledExtensionList().isEmpty()) {
				// 修改密码后，使用本系统登录是否生效，如果配置了禁用使用本系统数据库密码认证，并且不在例外人员列表里面的话，即使密码修改成功也不会生效
				boolean isEffect = false;
				if (StringUtil.isNotNull(excludeUser)) {
					String username = person == null ? "" : person
							.getFdLoginName();
					String[] excludeUsers = excludeUser.split("\\s*[,;]\\s*");
					for (int i = 0; i < excludeUsers.length; i++) {
						if (excludeUsers[i].equals(username)) {
							isEffect = true;
							break;
						}
					}
				}
				if (!isEffect) {
					messages.addMsg(new KmssMessage(
							"sys-organization:sysOrgPerson.isEffect.prompt"));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addMessages(messages).setOperationKey(
				"sys-organization:sysOrgPerson.button.changePassword").save(
				request);
		if (messages.hasError()) {
			return mapping.findForward("chgPwd");
		} else {
			rtnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return mapping.findForward("success");
		}
	}

	/**
	 * 用户修改密码，需要遵循admin.do中密码长度和强度要求
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveMyPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		try {
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

			String oldPwd = request.getParameter("fdOldPassword");
			getPersonService().savePassword(UserUtil.getUser().getFdId(),
					oldPwd, newPwd, new RequestContext(request));
			// 密码修改成功后，解除强制修改标识
			request.getSession().removeAttribute("compulsoryChangePassword");
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addMessages(messages).setOperationKey(
				"sys-organization:sysOrgPerson.button.changePassword").save(
				request);
		boolean ajax = AjaxUtil.requiredJson(request);
		if (messages.hasError()) {
			if (ajax) {
				String pwdErrorType = (String) request
						.getAttribute("pwdErrorType");
				if (StringUtil.isNotNull(pwdErrorType)) {
					AjaxUtil.saveMessagesToJson(request, pwdErrorType);
				} else {
					AjaxUtil.saveMessagesToJson(request, messages.getMessages()
							.get(0).getThrowable().getMessage());
				}
				return mapping.findForward("lui-failure");
			}
			return mapping.findForward("chgMyPwd");
		} else {
			if (ajax) {
				AjaxUtil.saveMessagesToJson(
						request,
						ResourceUtil
								.getString("sys-organization:sysOrgPerson.chgMyPwd.success"));
				return mapping.findForward("lui-source");
			}
			rtnPage.addButton(KmssReturnPage.BUTTON_RETURN);
			
			String url = request.getParameter("redto");
			Boolean pass = false;
			if(StringUtil.isNotNull(url)){
				if ("true".equals(PasswordSecurityConfig.newInstance().getKmssRedirecttoCheck())) {
					String[] domains = TrustSiteChecker.getTrustSite();	
					
					if (domains != null && domains.length > 0) {
						if((url.startsWith("/")||url.contains(request.getServerName()))&&!url.contains("file://")) {
                            pass = true;
                        }
						for (int i = 0; i < domains.length; i++) {
							if("*".equals(domains[i])){
								pass = true;
								break;
							}
							
							if (StringUtil.isNotNull(domains[i])) {
								if(url.contains(domains[i])){
									pass = true;
									break;
								}
							}
						}
						if(pass==true) {
                            return new ActionRedirect(request.getParameter("redto"));
                        }
					}
				}else {
                    return new ActionRedirect(request.getParameter("redto"));
                }
			}

			return mapping.findForward("success");
		}
	}

	public ActionForward loadSuccessPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
		rtnPage.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		return mapping.findForward("success");
	}
	
	public ActionForward changePerson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-changePerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		String forward = "personPwd";
		try {
			if(UserUtil.getUser().isAnonymous()){
				KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
				return mapping.findForward("failure");
			}
			String setting = request.getParameter("setting");
			//清除首次登录标识
			request.getSession().removeAttribute("FIRST_LOGIN_ONLINE");
			
			if("person_edit".equalsIgnoreCase(setting)){
				forward = "personEdit";
			}
			if("person_image".equalsIgnoreCase(setting)){
				forward = "personImage";
			}

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-changePerson", false, getClass());
		if (messages.hasError()){
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		}else{
			return mapping.findForward(forward);
		}
	}
	
	public ActionForward updatePerson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-updatePerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String mobileNo = request.getParameter("mobilPhone");

			String fdSex = request.getParameter("fdSex");
			String _fdSex = "false".equals(fdSex) ? "M" : "F";
			String personId = UserUtil.getUser().getFdId();
			
			SysOrgPerson orgPerson = (SysOrgPerson) getPersonService().findByPrimaryKey(
					personId);
			if (UserOperHelper.allowLogOper("updatePerson", SysOrgPerson.class.getName())) {
				UserOperContentHelper.putUpdate(orgPerson)
						.putSimple("fdSex", orgPerson.getFdSex(), _fdSex)
						.putSimple("fdMobileNo", orgPerson.getFdMobileNo(), mobileNo);
			}
			orgPerson.setFdSex(_fdSex);
			orgPerson.setFdMobileNo(mobileNo);
			getPersonService().update(orgPerson);
			TimeCounter.logCurrentTime("Action-updatePerson", false,
					getClass());
			
		} catch (Exception e) {
			log.error("完善个人信息出错：", e);
			messages.addError(e);
		}
		
		if (messages.hasError()) {
			AjaxUtil.saveMessagesToJson(request, "false");
			return mapping.findForward("lui-failure");
		} else {
			AjaxUtil.saveMessagesToJson(request, "true");
			return mapping.findForward("lui-source");
		}
	}

	protected void passwordStrength(String pwd) throws KmssException {
		// 密码长度要求
		String kmssOrgPasswordlength = PasswordSecurityConfig.newInstance().getKmssOrgPasswordlength();
		int pwdlength = StringUtil.isNull(kmssOrgPasswordlength) ? 1 : Integer.parseInt(kmssOrgPasswordlength);
		if (pwd == null || pwd.length() < pwdlength) {
			throw new KmssException(new KmssMessage(
					"sys-organization:sysOrgPerson.newPasswordLength",
					pwdlength));
		}

		// 密码安全级别
		String kmssOrgPasswordstrength = PasswordSecurityConfig.newInstance().getKmssOrgPasswordstrength();
		int pwdstreng = StringUtil.isNull(kmssOrgPasswordstrength) ? 0 : Integer.parseInt(kmssOrgPasswordstrength);
		// 密码安全级别大于0的时候要判断当前登录密码是否符合要求
		if (pwdstreng > 0) {
			int pwdth = PasswordUtil.pwdStrength(pwd);
			if (pwdth < pwdstreng) {
				throw new KmssException(new KmssMessage(
						"sys-organization:sysOrgPerson.newPasswordStrength",
						pwdstreng));
			}
		}
	}

	/**
	 * 管理员密码校验
	 * 
	 * @param pwd
	 * @throws KmssException
	 */
	protected void adminPasswordStrength(String pwd) throws KmssException {
		if (pwd == null || pwd.length() < 8) {
			throw new KmssException(new KmssMessage(
					"sys-organization:sysOrgPerson.newPasswordLength", 8));
		}

		int pwdth = PasswordUtil.pwdStrength(pwd);
		if (pwdth < 3) {
			throw new KmssException(new KmssMessage(
					"sys-organization:sysOrgPerson.newPasswordStrength", 3));
		}
	}
}
