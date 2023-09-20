package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.profile.model.PasswordSecurityConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 强制修改密码前提醒用户
 * 
 * @author 潘永辉
 * 
 */
public class ChangePwdReminderJob {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ChangePwdReminderJob.class);
	private ISysOrgPersonService sysOrgPersonService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	@SuppressWarnings("unchecked")
	public void reminder() throws Exception {
		// 检查admin.do是否开启此功能
		if (!isEnabled()) {
			return;
		}

		List<SysOrgPerson> temp = new ArrayList<SysOrgPerson>();
		// 获取所有用户
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock("fdLoginName not in ('everyone', 'anonymous') and fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
		hqlInfo.setOrderBy("fdLastChangePwd desc");
		Iterator<String> iter = sysOrgPersonService.getBaseDao().findValueIterator(hqlInfo);
		SysOrgPerson person = null;
		while (iter.hasNext()) {
			String id = iter.next();
			person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(id, SysOrgPerson.class, true);
			if (needChange(person)) {
				// 开启调试日志
				if (logger.isDebugEnabled()) {
					StringBuffer buf = new StringBuffer();
					buf.append("修改密码前提醒：用户名：[").append(person.getFdLoginName()).append("]");
					Date date = person.getFdLastChangePwd(); // 获取上次修改密码时间
					buf.append("，上次修改密码时间：").append(DateUtil.convertDateToString(date, DateUtil.PATTERN_DATETIME));
					if (date == null) {
						date = person.getFdCreateTime(); // 如果没有修改过密码，则获取用户的创建时间
						buf.append("，用户的创建时间：").append(DateUtil.convertDateToString(date, DateUtil.PATTERN_DATETIME));
						if (date == null) {
							date = person.getFdAlterTime(); // 如果是导入的数据，创建时间可能会为空，则获取修改时间
							buf.append("，用户的修改时间：").append(DateUtil.convertDateToString(date, DateUtil.PATTERN_DATETIME));
						}
					}

					// 如果经过上面3次获取后，还是没有取到时间，则强制提醒修改密码
					if (date == null) {
						buf.append("，没有获取到任何时间，强制提醒。");
					}
					logger.debug("",buf);
				}
				temp.add(person);
			}
		}

		if (temp.size() > 0) {
			// 发送通知
			NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);
			// 通知类型
			notifyContext.setSubject(ResourceUtil.getString("sys-organization:sysOrgPerson.changePwd.todo.title"));
			notifyContext.setNotifyType("todo;email");
			notifyContext.setNotifyTarget(temp);
			notifyContext.setLink("/sys/person/setting.do?setting=sys_organization_chg_pwd_secure");
			notifyContext.setMobileLink("/sys/common/changePwd/mobile/change_pwd.jsp");

			sysNotifyMainCoreService.sendNotify(null, notifyContext, null);
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

		// 如果经过上面3次获取后，还是没有取到时间，则强制提醒修改密码
		if (date == null) {
			return true;
		}

		return ((new Date().getTime() - date.getTime()) / DateUtil.DAY) >= (changeday - remindday);
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

}
