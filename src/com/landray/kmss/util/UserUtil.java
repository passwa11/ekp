package com.landray.kmss.util;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.background.BackgroundTempUserHolder;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidateCore;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.Globals;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public abstract class UserUtil {

	private static ISysOrgCoreService sysOrgCoreService = null;

	private static IAuthenticationValidateCore authenticationValidateCore = null;

	private static KMSSUser anonymousUser;
	private static KMSSUser everyoneKMSSUser;

	private static Log logger = LogFactory.getLog(UserUtil.class);

	/**
	 * 校验当前用户是否具有某个指定资源的权限
	 *
	 * @param url
	 * @param mothod
	 * @return true有权限
	 */
	public static boolean checkAuthentication(String url, String mothod) {
		return getAuthenticationValidateCore().checkAuthentication(url, mothod);
	}

	/**
	 * 判断当前用户是否属于某个ID
	 *
	 * @param id
	 *            id，可以为个人、岗位、部门、机构、常用群组的ID
	 * @return
	 */
	public static boolean checkUserId(String id) {
		return getKMSSUser().getUserAuthInfo().getAuthOrgIds().contains(id);
	}

	/**
	 * 判断当前用户是否属于id列表中的一个
	 *
	 * @param ids
	 *            id列表，可以为个人、岗位、部门、机构、常用群组的ID
	 * @return
	 */
	public static boolean checkUserIds(List<String> ids) {
		return ArrayUtil.isListIntersect(ids, getKMSSUser().getUserAuthInfo()
				.getAuthOrgIds());
	}

	/**
	 * 判断当前用户是否属于某个组织架构
	 *
	 * @param element
	 * @return
	 */
	public static boolean checkUserModel(ISysOrgElement element) {
		return getKMSSUser().getUserAuthInfo().getAuthOrgIds().contains(
				element.getFdId());
	}

	/**
	 * 判断当前用户是否属于组织架构列表中的一个
	 *
	 * @param elements
	 * @return
	 */
	public static boolean checkUserModels(List<?> elements) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < elements.size(); i++) {
			ids.add(((ISysOrgElement) elements.get(i)).getFdId());
		}
		// 内部人员在判断权限时，加上everyone
		List orgIds = getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (!BooleanUtils.isTrue(getUser().getFdIsExternal())) {
			orgIds.add(getEveryoneUser().getFdId());
		}
		return ArrayUtil.isListIntersect(ids, orgIds);
	}

	/**
	 * 判断当前用户是否具有某个角色
	 *
	 * @param role
	 * @return
	 */
	public static boolean checkRole(String role) {
		KMSSUser user = getKMSSUser();
		if (user.isAdmin()) {
			return true;
		}
		return user.getUserAuthInfo().getAuthRoleAliases().contains(role);
	}

	public static boolean checkRoles(List<String> roles) {
		KMSSUser user = getKMSSUser();
		if (user.isAdmin()) {
			return true;
		}
		return ArrayUtil.isListIntersect(roles, user.getUserAuthInfo()
				.getAuthRoleAliases());
	}

	/**
	 * 判断当前用户是否可以使用某个角色操作目标场所的数据
	 *
	 * @param role
	 *            角色
	 * @param areaHierarchyId
	 *            目标场所的层级ID
	 * @param type
	 *            场所的可用性类型。ISysAuthConstant.TYPE_VISIBLE_BRANCH
	 *            表示用户可以使用同一分支上所有上下级场所的数据 ；ISysAuthConstant.TYPE_VISIBLE_CHILD
	 *            表示用户仅可使用本场所和下级场所的数据。
	 * @return
	 * @throws Exception
	 */
	public static boolean checkAreaRole(String role, String areaHierarchyId,
										SysAuthConstant.AreaIsolation type) throws Exception {
		boolean isAvailable = getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases().contains(role);

		if (isAvailable) {
			isAvailable = SysAuthAreaUtils.isAvailableArea(areaHierarchyId,
					type);
		}

		return isAvailable;
	}

	public static boolean checkAreaRole(String role, IBaseModel model,
										SysAuthConstant.AreaIsolation type) throws Exception {
		boolean isAvailable = getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases().contains(role);

		if (isAvailable) {
			isAvailable = SysAuthAreaUtils.isAvailableModel(model, type);
		}

		return isAvailable;
	}

	/**
	 * 在程序中使用，获取当前登录员工
	 *
	 * @return
	 */
	public static KMSSUser getKMSSUser() {
		KMSSUser rtnVal = BackgroundTempUserHolder.get();
		if (rtnVal == null) {
			try {
				SecurityContext sc = SecurityContextHolder.getContext();
				if(sc!=null){
					Authentication authentication = sc.getAuthentication();
					if(authentication!=null){
						rtnVal = (KMSSUser)authentication.getPrincipal();
					}
				}
			} catch (Exception e) {
				//should not happen
				logger.warn("" +  e);
			}
		}
		if (rtnVal == null){
			rtnVal = anonymousUser;
		}

		return rtnVal;
	}

	/**
	 * 在页面中使用，从会话（如果有的话）中，获取当前登录KMSS用户
	 *
	 * @param request
	 * @return
	 */
	public static KMSSUser getKMSSUser(HttpServletRequest request) {
		//不主动创建session
		return getKMSSUser(request,false);
	}

	/**
	 * 在页面中使用，从会话中获取当前登录KMSS用户
	 * @param request
	 * @param createSession
	 * @return
	 */
	private static KMSSUser getKMSSUser(HttpServletRequest request ,boolean createSession) {
		KMSSUser rtnVal = BackgroundTempUserHolder.get();
		if (rtnVal == null) {
			try {
				HttpSession session = request.getSession(createSession);
				if(session!=null){
					Object context = session.getAttribute(
							Globals.SPRING_SECURITY_CONTEXT_KEY);
					if (context != null && context instanceof SecurityContext) {
						rtnVal = (KMSSUser) ((SecurityContext) context)
								.getAuthentication().getPrincipal();
					}

				}
			} catch (Exception e) {
				logger.warn("" + e);
			}
		}
		if (rtnVal == null){
			rtnVal = anonymousUser;
		}
		return rtnVal;
	}

	/**
	 * 在程序中使用，获取当前登录用户
	 *
	 * @return
	 */
	public static SysOrgPerson getUser() {
		return getUser(getKMSSUser());
	}

	/**
	 * 在页面中使用，获取当前登录用户
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static SysOrgPerson getUser(HttpServletRequest request) {
		return getUser(getKMSSUser(request));
	}

	/**
	 * 获取用户对象
	 *
	 * @param user
	 * @return
	 */
	public static SysOrgPerson getUser(KMSSUser user) {
		try {
			return (SysOrgPerson) getSysOrgCoreService().findByPrimaryKey(
					user.getUserId(), SysOrgPerson.class, false);
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
	}

	public static SysOrgPerson getUser(String userId) {
		try {
			return (SysOrgPerson) getSysOrgCoreService().findByPrimaryKey(userId, SysOrgPerson.class, false);
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
	}

	/**
	 * 在页面中使用，获取当前用户名，屏蔽了匿名的错误
	 *
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request) {
		return getKMSSUser(request).getUserName();
	}

	public static void setAnonymousUser(KMSSUser anonymousUser) {
		UserUtil.anonymousUser = anonymousUser;
	}

	public static KMSSUser getAnonymousUser() {
		return anonymousUser;
	}

	public static void setEveryoneKMSSUser(KMSSUser everyoneKMSSUser) {
		UserUtil.everyoneKMSSUser = everyoneKMSSUser;
	}
	public static KMSSUser getEveryoneKMSSUser(){
		return everyoneKMSSUser;
	}

	/**
	 * 获取所有者用户
	 *
	 * @return
	 */
	public static SysOrgPerson getEveryoneUser() {
		try {
			return (SysOrgPerson) getSysOrgCoreService().findByPrimaryKey(
					SysOrgConstant.ORG_PERSON_EVERYONE_ID, SysOrgPerson.class,
					false);
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
	}

	private static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private static IAuthenticationValidateCore getAuthenticationValidateCore() {
		if (authenticationValidateCore == null) {
			authenticationValidateCore = (IAuthenticationValidateCore) SpringBeanUtil
					.getBean("authenticationValidateCore");
		}
		return authenticationValidateCore;
	}
}
