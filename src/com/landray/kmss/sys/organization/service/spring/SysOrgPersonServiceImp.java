package com.landray.kmss.sys.organization.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.EKPAuthenticationLockEvent;
import com.landray.kmss.sys.authentication.user.LoginConfig;
import com.landray.kmss.sys.authentication.user.validate.Config;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonDao;
import com.landray.kmss.sys.organization.event.SysOrgElementEffectivedEvent;
import com.landray.kmss.sys.organization.event.SysOrgElementInvalidatedEvent;
import com.landray.kmss.sys.organization.interfaces.ISysOrgPassUpdate;
import com.landray.kmss.sys.organization.interfaces.OrgPassUpdatePlugin;
import com.landray.kmss.sys.organization.interfaces.OrgPassUpdatePluginData;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPersonPrivilege;
import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonRestrictService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.PasswordEncoderUtils;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.profile.model.SysProfileNetworkStrategy;
import com.landray.kmss.sys.profile.service.ISysProfileNetworkStrategyService;
import com.landray.kmss.sys.profile.util.IPUtil;
import com.landray.kmss.sys.transport.service.ISysTransportImport;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SysOrgPersonServiceImp extends SysOrgElementServiceImp
		implements ISysOrgPersonService, ICheckUniqueBean, ApplicationListener, ISysTransportImport {
	private IKmssPasswordEncoder passwordEncoder;

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgPersonRestrictService sysOrgPersonRestrictService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgPersonRestrictService(
			ISysOrgPersonRestrictService sysOrgPersonRestrictService) {
		this.sysOrgPersonRestrictService = sysOrgPersonRestrictService;
	}

	private boolean isInitPassword = true;

	@Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		SysOrgPerson person = (SysOrgPerson) super.convertFormToModel(form, model, requestContext);
		String fdNewpassword = person.getFdNewPassword();
		if (StringUtil.isNotNull(fdNewpassword)) {
			if (isInitPassword) {
				person.setFdInitPassword(PasswordUtil.desEncrypt(fdNewpassword));
			}
		}
		if (UserUtil.getAnonymousUser().getPerson().equals(model)) {
			if (!"anonymous".equals(person.getFdLoginName()) || person.getFdIsAvailable().booleanValue() == false) {
                throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.anonymous.modify"));
            }
		}
		checkLoginName(person);
		checkMobileNo(person);
		return person;
	}

	public void setPasswordEncoder(IKmssPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
    public boolean validatePassword(String id, String password) throws Exception {
		if (StringUtil.isNull(password)) {
			return false;
		}
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(id);
		//密码比对
		boolean formerMatched = PasswordEncoderUtils.checkPassWordEquals(password,person.getFdPassword());
		if (formerMatched) {
			return true;
		}
		return false;
	}

	public void savePasswordExtend(List<OrgPassUpdatePluginData> list, String id, String oldPassword,
			String newPassword, RequestContext requestContext) throws Exception {
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(id);
		String pwdErrorType = "";
		if(StringUtil.isNotNull(person.getFdPassword())){
			if (oldPassword == null) {
				pwdErrorType = "1";
			} else {
				// 校验旧密码
				boolean validate = false;
				for (OrgPassUpdatePluginData data : list) {
					ISysOrgPassUpdate sysOrgPassUpdate = data.getBean();
					if (sysOrgPassUpdate.validatePassword(requestContext.getRequest(), person.getFdLoginName(),
							oldPassword)) {
						validate = true;
						break;
					}
				}
				if (!validate) {
					//密码比对
					boolean formerMatched = PasswordEncoderUtils.checkPassWordEquals(oldPassword,person.getFdPassword());
					if (formerMatched) {
						validate = true;
					}
				}
				if (!validate) {
					pwdErrorType = "2";
				}
			}
		}
		if (StringUtil.isNotNull(pwdErrorType)) {
			requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
			throw new KmssRuntimeException(
					new KmssMessage("sys-organization:sysOrgPerson.error.passwordIncorrectness"));
		}

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrgPerson.fdLoginName,sysOrgPerson.fdPassword");
		hqlInfo.setWhereBlock("sysOrgPerson.fdId='" + StringUtil.replace(id, "'", "''") + "' and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setOrderBy(null);
		List rtnList = findValue(hqlInfo);
		for (Object obj : rtnList) {
			Object[] objArr = (Object[]) obj;
			if (newPassword.toLowerCase().equals(objArr[0].toString().toLowerCase())) {
				pwdErrorType = "4";
				requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
				throw new KmssRuntimeException(
						new KmssMessage("sys-organization:sysOrgPerson.error.newPwdCanNotSameLoginName"));
			}
		}

		if (isInitPassword) {
			person.setFdInitPassword(PasswordUtil.desEncrypt(newPassword));
		}
		String newPassword_md5 = null;
		if (newPassword != null) {
			newPassword_md5 = passwordEncoder.encodePassword(newPassword);
		}
		person.setFdPassword(newPassword_md5);
		person.setFdLastChangePwd(new Date()); // 修改密码时间
		// 此方法为用户主动修改密码，因此在修改密码后，需要将此用户置为“非新用户”
		person.setFdUserType("1");
		// 还需要移除“新用户”的标识，此标识会在KmssAuthenticationProcessingFilter过滤器中使用
		requestContext.getRequest().getSession().removeAttribute("isNewUser");
		// 记录修改操作日志
		addPersonPasswordModifyLog(person, requestContext);
		update(person);

		for (OrgPassUpdatePluginData data : list) {
			ISysOrgPassUpdate sysOrgPassUpdate = data.getBean();
			try {
				sysOrgPassUpdate.changePassword(person.getFdLoginName(), newPassword);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				throw new Exception("数据库密码修改成功，" + data.getKey() + "密码修改失败", e);
			}
		}

	}

	@Override
    public void savePassword(String id, String oldPassword, String newPassword, RequestContext requestContext)
			throws Exception {
		List<OrgPassUpdatePluginData> list = OrgPassUpdatePlugin.getEnabledExtensionList();
		if (list.size() > 0) {
			savePasswordExtend(list, id, oldPassword, newPassword, requestContext);
			return;
		}
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(id);
		// 具体错误类型
		String pwdErrorType = "";
		String fdPassword = person.getFdPassword();
		//密码比对
		boolean formerMatched = PasswordEncoderUtils.checkPassWordEquals(oldPassword,fdPassword);
		//新密码是否与旧密码重复
		boolean isRepeat = PasswordEncoderUtils.checkPassWordEquals(newPassword,person.getFdPassword());
		if(StringUtil.isNotNull(fdPassword)){
			if (oldPassword == null || fdPassword == null
					|| !formerMatched
					|| isRepeat) {
				
				if (oldPassword == null || fdPassword == null) {
					pwdErrorType = "1";
				} else if (!formerMatched) {
					pwdErrorType = "2";
				} else if (isRepeat) {
					pwdErrorType = "3";
				} else {
				}
				requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
				throw new KmssRuntimeException(
						new KmssMessage("sys-organization:sysOrgPerson.error.passwordIncorrectness"));
			}
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrgPerson.fdLoginName,sysOrgPerson.fdPassword");
		hqlInfo.setWhereBlock("sysOrgPerson.fdId='" + StringUtil.replace(id, "'", "''") + "' and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setOrderBy(null);

		List rtnList = findValue(hqlInfo);
		String encodeNewPwd = passwordEncoder.encodePassword(newPassword);// 加密后的密码
		for (Object obj : rtnList) {
			Object[] objArr = (Object[]) obj;
			if (newPassword.toLowerCase().equals(objArr[0].toString().toLowerCase())) {
				pwdErrorType = "4";
				requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
				throw new KmssRuntimeException(
						new KmssMessage("sys-organization:sysOrgPerson.error.newPwdCanNotSameLoginName"));
			}
			if(objArr[1]!=null&&StringUtil.isNotNull(objArr[1].toString())){
				if (encodeNewPwd.equals(objArr[1].toString().toLowerCase())) {
					pwdErrorType = "5";
					requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
					throw new KmssRuntimeException(
							new KmssMessage("sys-organization:sysOrgPerson.error.newPwdCanNotSameOldPwd"));
				}
			}
		}
		if (isInitPassword) {
			person.setFdInitPassword(PasswordUtil.desEncrypt(newPassword));
		}
		if (newPassword != null) {
			newPassword = passwordEncoder.encodePassword(newPassword);
		}
		person.setFdPassword(newPassword);
		person.setFdLastChangePwd(new Date()); // 修改密码时间
		// 此方法为用户主动修改密码，因此在修改密码后，需要将此用户置为“非新用户”
		person.setFdUserType("1");
		// 还需要移除“新用户”的标识，此标识会在KmssAuthenticationProcessingFilter过滤器中使用
		requestContext.getRequest().getSession().removeAttribute("isNewUser");
		// 记录修改操作日志
		addPersonPasswordModifyLog(person, requestContext);
		update(person);
	}

	@Override
    public void saveNewPassword(String id, String newPassword, RequestContext requestContext) throws Exception {
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(id);

		// 具体错误类型
		String pwdErrorType = "";
		//新密码是否与旧密码重复
		boolean isRepect = PasswordEncoderUtils.checkPassWordEquals(newPassword,person.getFdPassword());
		if (StringUtil.isNull(newPassword)
				|| isRepect) {

			pwdErrorType = "3";
			requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
			throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.same"));
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrgPerson.fdLoginName,sysOrgPerson.fdPassword");
		hqlInfo.setWhereBlock("sysOrgPerson.fdId='" + StringUtil.replace(id, "'", "''") + "' and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setOrderBy(null);

		List rtnList = findValue(hqlInfo);
		String encodeNewPwd = passwordEncoder.encodePassword(newPassword);// 加密后的密码
		for (Object obj : rtnList) {
			Object[] objArr = (Object[]) obj;
			if (newPassword.toLowerCase().equals(objArr[0].toString().toLowerCase())) {
				pwdErrorType = "4";
				requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
				throw new KmssRuntimeException(
						new KmssMessage("sys-organization:sysOrgPerson.error.newPwdCanNotSameLoginName"));
			}
			if (objArr[1] != null && encodeNewPwd.equals(objArr[1].toString().toLowerCase())) {
				pwdErrorType = "5";
				requestContext.getRequest().setAttribute("pwdErrorType", pwdErrorType);
				throw new KmssRuntimeException(
						new KmssMessage("sys-organization:sysOrgPerson.error.newPwdCanNotSameOldPwd"));
			}
		}
		if (isInitPassword) {
			person.setFdInitPassword(PasswordUtil.desEncrypt(newPassword));
		}
		String newPssword_md5 = newPassword;
		if (newPassword != null) {
			newPssword_md5 = passwordEncoder.encodePassword(newPassword);
		}
		person.setFdPassword(newPssword_md5);
		person.setFdLastChangePwd(new Date()); // 修改密码时间
		// 记录修改操作日志
		addPersonPasswordModifyLog(person, requestContext);
		update(person);

		List<OrgPassUpdatePluginData> list = OrgPassUpdatePlugin.getEnabledExtensionList();
		for (OrgPassUpdatePluginData data : list) {
			ISysOrgPassUpdate sysOrgPassUpdate = data.getBean();
			try {
				sysOrgPassUpdate.changePassword(person.getFdLoginName(), newPassword);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				throw new Exception("数据库密码修改成功，" + data.getKey() + "密码修改失败", e);
			}
		}
	}

	/**
	 * 添加组织架构变动日志-修改密码
	 * 
	 * @param element
	 * @param requestContext
	 * @throws Exception
	 */
	public void addPersonPasswordModifyLog(SysOrgElement element, RequestContext requestContext) throws Exception {
		SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
		String method = requestContext.getParameter("method");
		if ("saveMyPwd".equals(method)) {
			log.setFdDetails(ResourceUtil.getString("sysLogOrganization.saveMyPwd.details", "sys-log",
					requestContext.getLocale(), log.getFdOperator()));// 设置详细信息
		} else if ("savePwd".equals(method)) {
			Object[] params = new String[] { log.getFdOperator(), element.getFdName() };
			log.setFdDetails(ResourceUtil.getString("sysLogOrganization.savePwd.details", "sys-log",
					requestContext.getLocale(), params));// 设置详细信息
		}
		// 找回密码日志逻辑
		if ("true".equals(requestContext.getParameter("retrievePassword"))) {
			String targetId = requestContext.getParameter("targetId");
			String targetName = requestContext.getParameter("targetName");
			log.setFdOperatorId(targetId);
			log.setFdOperator(targetName);
			// 详细日志信息：张三通过“找回密码”功能修改了个人密码
			log.setFdDetails(ResourceUtil.getString("sysOrgPerson.saveNewPwd.details", "sys-organization",
					requestContext.getLocale(), targetName));// 设置详细信息
		}
		getSysLogOrganizationService().add(log);
	}

	@Override
    public void savePassword(String id, String newPassword, RequestContext requestContext) throws Exception {
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(id);
		if (isInitPassword) {
			person.setFdInitPassword(PasswordUtil.desEncrypt(newPassword));
		}
		String newPassword_md5 = null;
		if (newPassword != null) {
			newPassword_md5 = passwordEncoder.encodePassword(newPassword);
		}
		person.setFdPassword(newPassword_md5);
		person.setFdLastChangePwd(new Date()); // 修改密码时间
		// 记录修改操作日志
		addPersonPasswordModifyLog(person, requestContext);
		update(person);

		List<OrgPassUpdatePluginData> list = OrgPassUpdatePlugin.getEnabledExtensionList();
		for (OrgPassUpdatePluginData data : list) {
			ISysOrgPassUpdate sysOrgPassUpdate = data.getBean();
			try {
				sysOrgPassUpdate.changePassword(person.getFdLoginName(), newPassword);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.ldap"));
			}
		}

	}

	private void checkLoginName(SysOrgPerson person) throws Exception {
		if (person.getFdIsAvailable().booleanValue()) {
			if (StringUtil.isNull(person.getFdLoginName())){
				throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.loginName.empty"));
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			hqlInfo.setWhereBlock("sysOrgPerson.fdLoginName='"
					+ StringUtil.replace(person.getFdLoginName(), "'", "''") + "' and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			hqlInfo.setOrderBy(null);

			List rtnList = findValue(hqlInfo);
			for (int i = 0; i < rtnList.size(); i++) {
                if (!rtnList.get(i).equals(person.getFdId())) {
                    throw new KmssRuntimeException(new KmssMessage(
                            "sys-organization:sysOrgPerson.error.loginName.notUnique", person.getFdLoginName()));
                }
            }
		}
	}

	@Override
    public void checkMobileNo(SysOrgPerson person) throws Exception {
		if (StringUtil.isNull(person.getFdMobileNo())) {
			return;
		}
		if (person.getFdIsAvailable().booleanValue()) {

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			hqlInfo.setWhereBlock( "sysOrgPerson.fdMobileNo='"
					+ StringUtil.replace(person.getFdMobileNo(), "'", "''") + "' and sysOrgPerson.fdIsAvailable= :fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			hqlInfo.setOrderBy(null);

			List rtnList = findValue(hqlInfo);

			for (int i = 0; i < rtnList.size(); i++) {
                if (!rtnList.get(i).equals(person.getFdId())) {
                    throw new KmssRuntimeException(new KmssMessage(
                            "sys-organization:sysOrgPerson.error.mobileNo.notUnique", person.getFdMobileNo()));
                }
            }
		}
	}

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysOrgPerson person = (SysOrgPerson) modelObj;
		checkCount(person.getFdId(), person.getFdIsExternal());
		return super.add(handlePerson(modelObj));
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysOrgPerson person = handlePerson(modelObj);
		super.update(person);
		//根据状态决定是否需要发生事件
		publishPersonEvent(person);
		// 用户信息更新，黄页信息最后更新时间也要更新
		ISysZonePersonInfoService sysZonePersonInfoService = (ISysZonePersonInfoService) SpringBeanUtil
				.getBean("sysZonePersonInfoService");
		sysZonePersonInfoService.updatePersonLastModifyTime(person);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String modelName = form.getModelClass().getName();
		modelName = StringUtil.isNotNull(modelName) ? modelName : getModelName();
		UserOperHelper.logUpdate(modelName);

		// 根据人员现在的状态判断发布不同的事件
		String sql = "select fd_is_available from sys_org_element where fd_id = :id";
		Object available = getBaseDao().getHibernateSession().createSQLQuery(sql).setParameter("id", form.getFdId()).getSingleResult();
		IBaseModel model = convertFormToModel(form, null, requestContext);
		//根据状态决定是否需要发生事件
		publishPersonEvent((SysOrgPerson) model, available);

		update(model);
	}

	/**
	 * 发送人员状态变动事件
	 * @description:
	 * @param person
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/2/28 11:40 上午
	 */
	private void publishPersonEvent(SysOrgPerson person) {
		try {
			// 根据人员现在的状态判断发布不同的事件
			String sql = "select fd_is_available from sys_org_element where fd_id = :id";
			Object available = getBaseDao().getHibernateSession().createSQLQuery(sql).setParameter("id", person.getFdId()).getSingleResult();
			if (logger.isDebugEnabled()) {
				logger.debug("获取数据库中人员的状态：{}", available);
			}
			if (available != null && !person.getFdIsAvailable().equals(available)) {
				if (logger.isDebugEnabled()) {
					logger.debug("人员ID:{},人员数据库状态:{},当前提交状态:{},状态不一致，需要发送状态变更事件.", person.getFdId(), available, person.getFdIsAvailable());
				}
				if (BooleanUtils.isNotTrue(person.getFdIsAvailable())) {
					// 发布置为无效事件
					super.applicationContext.publishEvent(new SysOrgElementInvalidatedEvent(person, new RequestContext()));
				} else {
					// 发布置为有效事件
					super.applicationContext.publishEvent(new SysOrgElementEffectivedEvent(person, new RequestContext()));
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("人员[" + person.getFdId() + " - " + person.getFdName() + " - " + person.getFdLoginName() + "]状态发布事件失败：", e);
			}
		}
	}

	/**
	 * 发送人员状态变动事件
	 * @param person
	 * @param available
	 */
	private void publishPersonEvent(SysOrgPerson person,Object available) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("获取数据库中人员的状态：{}", available);
			}
			if (available != null && !person.getFdIsAvailable().equals(available)) {
				if (logger.isDebugEnabled()) {
					logger.debug("人员ID:{},人员数据库状态:{},当前提交状态:{},状态不一致，需要发送状态变更事件.", person.getFdId(), available, person.getFdIsAvailable());
				}
				if (BooleanUtils.isNotTrue(person.getFdIsAvailable())) {
					// 发布置为无效事件
					super.applicationContext.publishEvent(new SysOrgElementInvalidatedEvent(person, new RequestContext()));
				} else {
					// 发布置为有效事件
					super.applicationContext.publishEvent(new SysOrgElementEffectivedEvent(person, new RequestContext()));
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("人员[" + person.getFdId() + " - " + person.getFdName() + " - " + person.getFdLoginName() + "]状态发布事件失败：", e);
			}
		}
	}

	/**
	 * 数据处理
	 * 
	 * @param modelObj
	 * @return
	 */
	private SysOrgPerson handlePerson(IBaseModel modelObj) {
		SysOrgPerson person = (SysOrgPerson) modelObj;
		// 密码加密及设置初始化
		String fdNewPassword = person.getFdNewPassword();
		if (StringUtil.isNotNull(fdNewPassword)) {
			person.setFdPassword(passwordEncoder.encodePassword(fdNewPassword));
			if (isInitPassword) {
				person.setFdInitPassword(PasswordUtil.desEncrypt(fdNewPassword));
			}
		}
		// 手机号处理
		String fdMobileNo = person.getFdMobileNo();
		if (StringUtil.isNotNull(fdMobileNo)) {
			// 如果手机号是以+86开头，则强制去掉+86
			if (fdMobileNo.startsWith("+86")) {
				person.setFdMobileNo(fdMobileNo.substring(3).replaceAll("-", ""));
			}
		}

		return person;
	}

	@Override
    public void delete(IBaseModel model) throws Exception {
		if (UserUtil.getAnonymousUser().getPerson().equals(model)) {
            throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.anonymous.delete"));
        }
		super.delete(model);
	}

	@Override
    public boolean isExistLoginName(String fdLoginName, String fdImportInfo) throws Exception {
		List list = findList(
				"sysOrgPerson.fdLoginName='" + fdLoginName + "' and sysOrgPerson.fdImportInfo!='" + fdImportInfo + "'",
				null);
		return (list != null && !list.isEmpty());
	}

	private void checkCount(String personId, boolean isExternal) throws Exception {
	    if("true".equals(LicenseUtil.get("license-org-person-import"))) {
	    	// 许可受限，超出人员增加后不可登录
			int i_count = 0;
			if(isExternal) {
				i_count = StringUtil.getIntFromString(LicenseUtil.get("license-org-person-external"), -1);
			} else {
				i_count = StringUtil.getIntFromString(LicenseUtil.get("license-org-person"), -1);
			}
			if (i_count > -1) { //unlimited
				int count = ((ISysOrgPersonDao) getBaseDao()).getCountByRegistered(isExternal, true);
				if (count >= i_count) {
					// 当可使用数量不足时，不抛异常，只是创建的用户不能登录，无法使用
					// throw new KmssRuntimeException(new KmssMessage("sys-organization:sysOrgPerson.error.exceed.limit"));
					// 保存为受限制账号
					sysOrgPersonRestrictService.addRestrict(personId);
				}
			}
	    } else {
	    	// 兼容原许可配置，即不限制
	    }
	}

	/**
	 * 判断新注册登录名是否在已经失效的登录中存在重复的情况/判断登录名是否有重复的情况
	 */
	@Override
    public String checkUnique(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String mobileNo = requestInfo.getParameter("mobileNo");
		if (StringUtil.isNotNull(mobileNo)) {
			return checkMobileNoUnique(fdId, mobileNo);
		}
		String loginName = requestInfo.getParameter("loginName");
		String checkType = requestInfo.getParameter("checkType");
		HQLInfo hqlInfo = new HQLInfo();
		String result = "";
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(fdId, SysOrgPerson.class, true);
		String hql = " sysOrgPerson.fdLoginName=:fdLoginName and sysOrgPerson.fdId!=:fdId and ";
		if ("unique".equals(checkType)) {
			hql = hql + " sysOrgPerson.fdIsAvailable = :fdIsAvailable "; // 1 表示有效的登录名
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		} else {
			if ((person != null) && (loginName.equals(person.getFdLoginName()))) {
				// 编辑用户并且登录名没有改动过则 无需校验无效部分是否重名
				return result;
			}
			hql = hql + " sysOrgPerson.fdIsAvailable = :fdIsAvailable ";// 0 表示无效的登录名
			// 检测无效部分是否重名
			hqlInfo.setParameter("fdIsAvailable", Boolean.FALSE);
		}
		hqlInfo.setWhereBlock(hql);
		hqlInfo.setParameter("fdLoginName", loginName);
		hqlInfo.setParameter("fdId", fdId);
		List<SysOrgPerson> lists = findList(hqlInfo);

		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdLoginName();
		}
		return result;
	}

	public String checkMobileNoUnique(String personId, String mobileNo) throws Exception {
		if (mobileNo.startsWith("x")) {
			mobileNo = mobileNo.replace("x", "+");
		}
		String result = "";
		/**
		 * 因为手机号支持多种格式，无法在数据库中进行校验，需要将疑似的手机号查询出来后再进行校验
		 * 由于重复的手机号需要同时支持以下格式：+xxxxxxxxx和+xx-xxxxxxx
		 * 假设数据库存在手机号为：+85212345678，而页面上输入的是：+852-12345678，此时只需要把输入的手机号中的"-"去除再进行比较；
		 * 但如果数据库中存在的手机号为：+852-12345678，而页面输入的是：+85212345678，此时无法在正确的位置插入"-"；
		 * 根据上述需求，校验逻辑需要调整为： 1. 如果是国内手机号，保存在数据库的数据已经去
		 */

		// 如果手机号是以+86开头，则强制去掉+86
		if (mobileNo.startsWith("+86")) { // 国内手机号去掉国际区号
			mobileNo = mobileNo.substring(3).replaceAll("-", "");
			HQLInfo hqlInfo = new HQLInfo();
			String hql = " sysOrgPerson.fdMobileNo=:fdMobileNo and sysOrgPerson.fdId!=:fdId and sysOrgPerson.fdIsAvailable= :fdIsAvailable";
			hqlInfo.setWhereBlock(hql);
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", personId);
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			List<SysOrgPerson> lists = findList(hqlInfo);
			if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
				result += lists.get(0).getFdLoginName();
			}
		} else if (mobileNo.length() > 6) { // 国外手机号需要单独处理
			String temp = mobileNo.substring(mobileNo.length() - 6);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysOrgPerson.fdId, sysOrgPerson.fdMobileNo, sysOrgPerson.fdLoginName");
			hqlInfo.setWhereBlock(" sysOrgPerson.fdMobileNo like :temp and sysOrgPerson.fdIsAvailable= :fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
			hqlInfo.setParameter("temp", "%" + temp);
			List<Object[]> list = findList(hqlInfo);
			if (CollectionUtils.isNotEmpty(list)) {
				mobileNo = mobileNo.replaceAll("-", "");
				for (Object[] obj : list) {
					String id = String.valueOf(obj[0]);
					String mno = String.valueOf(obj[1]).replaceAll("-", "");
					if (mno.equals(mobileNo) && !id.equals(personId)) {
						result = String.valueOf(obj[2]);
					}
				}
			}
		}
		return result;
	}

	@Override
    public void savePersonUnLock(SysOrgPerson person, RequestContext requestContext) throws Exception {
		person.setFdLockTime(null);
		getBaseDao().update(person);
		// 对用户进行解锁操作日志
		addPersonUnLockLog(person, requestContext);
	}

	/**
	 * 添加组织架构变动日志-用户解锁
	 * 
	 * @param element
	 * @param requestContext
	 * @throws Exception
	 */
	public void addPersonUnLockLog(SysOrgElement element, RequestContext requestContext) throws Exception {
		SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
		Object[] params = new String[] { log.getFdOperator(), element.getFdName() };
		log.setFdDetails(ResourceUtil.getString("sysLogOrganization.unLock.details", "sys-log",
				requestContext.getLocale(), params));// 设置详细信息
		getSysLogOrganizationService().add(log);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof EKPAuthenticationLockEvent) {
			SysOrgPerson person = (SysOrgPerson) event.getSource();
			// 保存锁定期限时间
			person.setFdLockTime(new Date(System.currentTimeMillis() + LoginConfig.getLOCK_MINU() * DateUtil.MINUTE));
			TransactionStatus status = TransactionUtils.beginNewTransaction();
			try {
				getBaseDao().update(person);
				TransactionUtils.commit(status);
			} catch (Exception e) {
				TransactionUtils.rollback(status);
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void addImport(IBaseModel modelObj) throws Exception {
		SysOrgPerson person = (SysOrgPerson) modelObj;
		// 检查人员许可数量
		checkCount(person.getFdId(), person.getFdIsExternal());

		// 处理密码加密
		String fdPassword = person.getFdPassword();
		//如果密码为空，则获取设置的默认密码 #152413 add by zhouwen 20220117
		if (StringUtil.isNull(fdPassword)) {
			fdPassword = new SysOrgDefaultConfig().getOrgDefaultPassword();
		}
		// 增加用户，只要有设置密码字段，就进行加密码处理
		if (StringUtil.isNotNull(fdPassword)) {
			person.setFdPassword(passwordEncoder.encodePassword(fdPassword));
			if (isInitPassword) {
				person.setFdInitPassword(PasswordUtil.desEncrypt(fdPassword));
			}
		}
		super.add(person);
	}

	@Override
	public void updateImport(IBaseModel modelObj) throws Exception {
		SysOrgPerson person = (SysOrgPerson) modelObj;
		// 处理密码加密
		String originalPassword = getSysOrgPersonDao().getOriginalPassword(person);
		if (StringUtil.isNull(person.getFdPassword())) {
			// 如果导入时有密码字段，但是没有数据，则保持原始的密码
			person.setFdPassword(originalPassword);
		} else {
			String fdPassword = passwordEncoder.encodePassword(person.getFdPassword()); // 导入的新密码
			if (!person.getFdPassword().equals(originalPassword)) {

				// 如果导入时有设置新的密码，则加密后保存
				if (!fdPassword.equals(originalPassword)) {
					person.setFdPassword(fdPassword);
					if (isInitPassword) {
						person.setFdInitPassword(PasswordUtil.desEncrypt(person.getFdPassword()));
					}
				} else {
					// 如果密码还是原密码则保持原始的密码
					person.setFdPassword(originalPassword);
				}
			}
		}
		super.update(person);
	}

	private ISysOrgPersonDao sysOrgPersonDao;

	public ISysOrgPersonDao getSysOrgPersonDao() {
		if (sysOrgPersonDao == null) {
			sysOrgPersonDao = (ISysOrgPersonDao) SpringBeanUtil.getBean("sysOrgPersonDao");
		}
		return sysOrgPersonDao;
	}

	private ISysProfileNetworkStrategyService sysProfileNetworkStrategyService;

	public ISysProfileNetworkStrategyService getSysProfileNetworkStrategyService() {
		if (sysProfileNetworkStrategyService == null) {
			sysProfileNetworkStrategyService = (ISysProfileNetworkStrategyService) SpringBeanUtil
					.getBean("sysProfileNetworkStrategyService");
		}
		return sysProfileNetworkStrategyService;
	}

	@Override
	public boolean doubleValidation(HttpServletRequest request, String personId) throws Exception {
		boolean doubleValidationState = false;
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(personId);

		// 开启二次认证
		if ("enable".equals(person.getFdDoubleValidation())) {
			doubleValidationState = true;
		}
		// 开启“网段策略”的二次认证
		if ("network".equals(person.getFdDoubleValidation())) {
			doubleValidationState = true;
			// 本次登录的IP
			String addr = IPUtil.getIpAddr(request);
			// 获取网段策略
			List<SysProfileNetworkStrategy> networkList = getSysProfileNetworkStrategyService().findList(new HQLInfo());
			for (SysProfileNetworkStrategy network : networkList) {
				// 如果登录的IP不在网段策略中，则需要二次认证
				if (IPUtil.isInclude(network.getFdStartIp(), network.getFdEndIp(), addr)) {
					doubleValidationState = false;
					break;
				}
			}
		}

		// 需要二次认证
		if (doubleValidationState) {
			request.getSession().setAttribute("DOUBLE_VALIDATION_STATE", "true");
		} else {
			request.getSession().setAttribute("DOUBLE_VALIDATION_STATE", "false");
		}
		return doubleValidationState;
	}

	@Override
	public void updateDeptByPersons(String[] personIds, String deptId, RequestContext requestContext) throws Exception {
		SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId);
		for (String personId : personIds) {
			SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(personId);
			if(!"everyone".equals(person.getFdLoginName()) && !"anonymous".equals(person.getFdLoginName())) {
				// 记录日志
				if (UserOperHelper.allowLogOper("changeDept", getModelName())) {
					UserOperContentHelper.putUpdate(person).putSimple("fdParent", person.getFdParent(), parent);
				}
				if (person != null) {
					person.setFdParent(parent);
					update(person);
					SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
					Object[] params = new String[] { log.getFdOperator(), person.getFdName(),ResourceUtil.getString("sysOrgElement.person", "sys-organization") };
					log.setFdDetails(ResourceUtil.getString("sysLogOrganization.changeDept.details", "sys-log",
							requestContext.getLocale(), params));// 设置详细信息
					log.setFdTargetId(person.getFdId());
					getSysLogOrganizationService().add(log);
				}
			}
		}
	}

	@Override
    public SysOrgPerson resumePerson(String fdId) throws Exception {
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(fdId);
		checkCount(fdId, person.getFdIsExternal());
		person.setFdIsAvailable(true);
		checkLoginName(person);
		SysOrgElement dept = person.getFdParent();
		List<SysOrgElement> posts = person.getFdPosts();
		if (StringUtil.isNotNull(person.getFdPreDeptId())) {
			SysOrgElement preDept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(person.getFdPreDeptId());
			if (preDept != null && preDept.getFdIsAvailable()) {
                person.setFdParent(preDept);
            }
		}
		if (person.getFdPrePostIdsArr() != null && person.getFdPrePostIdsArr().length > 0) {
			List<SysOrgElement> prePosts = sysOrgElementService.findByPrimaryKeys(person.getFdPrePostIdsArr());
			List<SysOrgElement> isAvailPrePosts = new ArrayList();
			for (SysOrgElement post : prePosts) {
				if (post.getFdIsAvailable()) {
					isAvailPrePosts.add(post);
				}
			}

			if (isAvailPrePosts.size() > 0) {
                person.setFdPosts(isAvailPrePosts);
            }
		}

		return person;
	}

	@Override
	public void updateTripartiteAdminActivation(String fdId) throws Exception {
		SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(fdId, null, true);
		// 激活三员管理账号，需要清除所有关系
		person.setFdIsAvailable(false);
		update(person);
	}

	@Override
	public void updatePersonActivation(String fdId) throws Exception {
		String sql = "update sys_org_person set fd_is_activated = :fd_is_activated where fd_id = :fdId";
		getBaseDao().getHibernateSession().createSQLQuery(sql)
				.setParameter("fd_is_activated", Boolean.TRUE)
				.setParameter("fdId", fdId).executeUpdate();
	}

	@Override
	public Page findPage(HQLInfo hqlInfo) throws Exception {
		// 开启三员管理后，需要排除一些人员
		// 查询激活状态需要过滤几个特殊的人员（三员管理员，系统内置的3个用户：admin，everyone，anonymous）
		if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			List<String> exclude = new ArrayList<String>();
			exclude.add("admin");
			// exclude.add("everyone");
			exclude.add("anonymous");
			exclude.addAll(TripartiteAdminUtil.ADMIN_AUDITOR_NAMES);
			exclude.addAll(TripartiteAdminUtil.ADMIN_SECURITY_NAMES);
			exclude.addAll(TripartiteAdminUtil.ADMIN_SYSTEM_NAMES);

			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
					"sysOrgPerson.fdLoginName not in (:exclude) and sysOrgPerson.fdId != '"
							+ SysOrgConstant.ORG_PERSON_EVERYONE_ID + "'"));
			hqlInfo.setParameter("exclude", exclude);
		}
		return super.findPage(hqlInfo);
	}

	@Override
    public void saveResumePerson(SysOrgPerson person, RequestContext requestContext) throws Exception {
		update(person);
		SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
		Object[] params = new String[] { log.getFdOperator(), person.getFdName() };
		log.setFdDetails(ResourceUtil.getString("sysLogOrganization.resume.details", "sys-log",
				requestContext.getLocale(), params));// 设置详细信息
		getSysLogOrganizationService().add(log);
	}

	@Override
	public int savePrivilege(String[] personIds) throws Exception {
		if (personIds == null) {
			return 0;
		}
		// 已经设置特权人员数量
		int totalSize = getPrivilegeSize(null) + personIds.length;
		if (totalSize > Config.getLicPrivCount()) {
			// 超出限制，提示无法保存
			throw new RuntimeException(ResourceUtil.getString("sys.profile.maintenance.overview.privilege.over", "sys-profile", null, Config.getLicPrivCount()));
		}
		if (personIds.length > 0) {
			IBaseService service = (IBaseService) SpringBeanUtil.getBean("KmssBaseService");
			for (String userId : personIds) {
				SysOrgPerson person = (SysOrgPerson) findByPrimaryKey(userId);
				SysOrgPersonPrivilege privilege = getByPerson(userId);
				if (privilege == null) {
					// 只保存不存在的特权人员
					privilege = new SysOrgPersonPrivilege();
					privilege.setFdPerson(person);
					privilege.setFdIsExternal(person.getFdIsExternal());
					privilege.setDocCreateTime(new Date());
					service.getBaseDao().add(privilege);
				}
			}
			// 更新缓存
			KmssCache cache = new KmssCache(SysOrgCoreServiceImp.class);
			cache.remove("sys_org_person_privilege_ids");
		}
		return personIds.length;
	}

	@Override
	public SysOrgPersonPrivilege getByPerson(String personId) throws Exception {
		// 先查询特权用户是否是超过许可限制，防止直接修改数据库；如果超过了许可限制只取许可限制内的数据进行判断 #146828
		KmssCache cache = new KmssCache(SysOrgCoreServiceImp.class);
		List<String> personPrivilegeList = (List<String>) cache.get("sys_org_person_privilege_ids");
		if (personPrivilegeList == null) {
			String sql = "select fd_person_id from sys_org_person_privilege order by fd_id";
			personPrivilegeList = getBaseDao().getHibernateSession().createNativeQuery(sql).setMaxResults(Config.getLicPrivCount()).list();
			cache.put("sys_org_person_privilege_ids", personPrivilegeList);
		}
		if(CollectionUtils.isNotEmpty(personPrivilegeList) && personPrivilegeList.contains(personId)){
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setModelName(SysOrgPersonPrivilege.class.getName());
			hqlInfo.setWhereBlock("fdPerson.fdId = :personId");
			hqlInfo.setParameter("personId", personId);
			List<SysOrgPersonPrivilege> list = findList(hqlInfo);
			if (CollectionUtils.isNotEmpty(list)) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public void deletePrivilege(String personId) throws Exception {
		if (StringUtil.isNotNull(personId)) {
			String hql = "delete from com.landray.kmss.sys.organization.model.SysOrgPersonPrivilege privilege where privilege.fdPerson.fdId = :personId";
			getBaseDao().getSession().createQuery(hql).setParameter("personId", personId).executeUpdate();
			// 更新缓存
			KmssCache cache = new KmssCache(SysOrgCoreServiceImp.class);
			cache.remove("sys_org_person_privilege_ids");
		}
	}

	@Override
	public JSONArray getPrivileges(HttpServletRequest request) throws Exception {
		JSONArray array = new JSONArray();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysOrgPersonPrivilege.class.getName());
		hqlInfo.setOrderBy("sysOrgPersonPrivilege.fdId");
		List<SysOrgPersonPrivilege> list = findValue(hqlInfo);
		if (CollectionUtils.isNotEmpty(list)) {
			String contextPath = request.getContextPath();
			HashMap<String, String> map = new HashMap<String, String>();
			int count = Config.getLicPrivCount();
			for (int i = 0; i < list.size(); i++) {
				SysOrgPersonPrivilege privilege = list.get(i);
				JSONObject obj = new JSONObject();
				SysOrgPerson person = privilege.getFdPerson();
				obj.put("id", privilege.getFdId());
				obj.put("userId", person.getFdId());
				obj.put("userName", person.getFdName());
				obj.put("isExternal", person.getFdIsExternal());
				obj.put("available", person.getFdIsAvailable());
				obj.put("status", i < count);
				OrgDialogUtil.setPersonAttrs(person, contextPath, map);
				obj.put("img", map.get("img"));
				map.clear();
				array.add(obj);
			}
		}
		return array;
	}

	@Override
	public JSONObject getPrivilegeCounts() throws Exception {
		JSONObject obj = new JSONObject();
		// 获取许可中允许的总数量
		obj.put("licenseCount", Config.getLicPrivCount());

		// 获取已经设置了特权用户的总数量
		obj.put("totalCount", getPrivilegeSize(null));
		obj.put("outCount", getPrivilegeSize(true));
		obj.put("inCount", getPrivilegeSize(false));
		return obj;
	}

	/**
	 * 获取已经设置特权人员的数量
	 * @return
	 */
	private int getPrivilegeSize(Boolean isExternal) throws Exception {
		String hql = "select count(*) from " + SysOrgPersonPrivilege.class.getName();
		if (isExternal != null) {
			hql += " where fdIsExternal = :isExternal";
		}
		Query query = getBaseDao().getSession().createQuery(hql);
		if (isExternal != null) {
			query.setParameter("isExternal", isExternal);
		}
		List totalCount = query.list();
		return Integer.parseInt(totalCount.get(0).toString());
	}

}
