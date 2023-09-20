package com.landray.kmss.km.calendar.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarRequestAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarShareGroupService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * 日程共享人员业务action
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarAuthAction extends ExtendAction {

	protected IKmCalendarAuthService kmCalendarAuthService;
	private IKmCalendarMainService kmCalendarMainService;
	private IKmCalendarShareGroupService kmCalendarShareGroupService;
	private ISysOrgCoreService sysOrgCoreService = null;
	private ISysNotifyTodoService sysNotifyTodoService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmCalendarAuthService == null) {
			kmCalendarAuthService = (IKmCalendarAuthService) getBean("kmCalendarAuthService");
		}
		return kmCalendarAuthService;
	}

	protected IKmCalendarMainService getkmCalendarMainServiceImp(
			HttpServletRequest request) {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	protected ISysOrgCoreService getSysOrgCoreServiceImp(
			HttpServletRequest request) {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public IKmCalendarShareGroupService getKmCalendarShareGroupService() {
		if (kmCalendarShareGroupService == null) {
			kmCalendarShareGroupService = (IKmCalendarShareGroupService) getBean("kmCalendarShareGroupService");
		}
		return kmCalendarShareGroupService;
	}

	public ISysNotifyTodoService getSysNotifyTodoService() {
		if (sysNotifyTodoService == null) {
			sysNotifyTodoService = (ISysNotifyTodoService) getBean(
					"sysNotifyTodoService");
		}
		return sysNotifyTodoService;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		KmCalendarAuth auth = null;
		String fdId = request.getParameter("fdId");
		if(StringUtil.isNull(fdId)){
			 fdId = UserUtil.getUser(request).getFdId();
		}
		if (!StringUtil.isNull(fdId)) {
			auth = findKmCalendarAuth(request, fdId);
			if (auth != null) {
				UserOperHelper.logFind(auth);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, auth, new RequestContext(request));
			}
		}
		if (rtnForm != null) {
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		}
		if (auth != null) {
			request.setAttribute("kmCalendarAuth", auth);
		}
	}

	private KmCalendarAuth findKmCalendarAuth(HttpServletRequest request,
			String userid) throws Exception {
		KmCalendarAuth auth = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmCalendarAuth.docCreator.fdId=:docCreatorFdId");
		hqlInfo.setParameter("docCreatorFdId", userid);
		List<KmCalendarAuth> auths = getServiceImp(request).findList(hqlInfo);
		KmCalendarBaseConfig config = new KmCalendarBaseConfig();
		String deptCanRead = config.getDeptCanRead();
		SysOrgElement element = (SysOrgElement) getSysOrgCoreServiceImp(
				request).findByPrimaryKey(userid);
		SysOrgPerson person = (SysOrgPerson) getSysOrgCoreServiceImp(
				request).format(element);
		if (auths != null && auths.size() > 0) {
			auth = auths.get(0);
			if (!"false".equals(deptCanRead) && person.getFdParent() != null) {
				boolean update = false;
				// 加入authReaders
				List<SysOrgElement> readList = auth.getAuthReaders();
				if (!readList.contains(person.getFdParent())) {
					readList.add(person.getFdParent());
					update = true;
				}
				// 同时加入KmCalendarAuthList
				List<KmCalendarAuthList> authLists = auth
						.getKmCalendarAuthList();
				if (authLists == null) {
					authLists = new ArrayList<>();
				}
				boolean addList = true;
				for (KmCalendarAuthList kmCalendarAuthList : authLists) {
					List<SysOrgElement> elements = kmCalendarAuthList
							.getFdPerson();
					boolean isRead = BooleanUtils
							.isTrue(kmCalendarAuthList.getFdIsRead());
					if (isRead && elements.contains(person.getFdParent())) {
						addList = false;
						break;
					}
				}
				if (addList) {
					KmCalendarAuthList authList = new KmCalendarAuthList();
					authList.setFdIsRead(true);
					authList.setFdIsShare(false);
					authList.setFdIsPartShare(false);
					List<SysOrgElement> reads = new ArrayList<SysOrgElement>();
					reads.add(person.getFdParent());
					authList.setFdPerson(reads);
					authLists.add(authList);
					auth.setKmCalendarAuthList(authLists);
					update = true;
				}
				// 更新
				if (update) {
					getServiceImp(request).update(auth);
				}
			}
		} else {
			auth = new KmCalendarAuth();
			auth.setDocCreator(person);
			List<SysOrgElement> readList = new ArrayList<SysOrgElement>();
			if (!"false".equals(deptCanRead) && person.getFdParent() != null) {
				readList.add(person.getFdParent());
				auth.setAuthReaders(readList);
				// 同时加入KmCalendarAuthList
				KmCalendarAuthList authList = new KmCalendarAuthList();
				authList.setFdIsRead(true);
				authList.setFdIsShare(false);
				authList.setFdPerson(readList);
				authList.setFdIsPartShare(false);
				List<KmCalendarAuthList> authLists = new ArrayList<>();
				authLists.add(authList);
				auth.setKmCalendarAuthList(authLists);
			}
			getServiceImp(request).add(auth);
		}
		return auth;
	}
	
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try { 
			try {
				if (!"POST".equals(request.getMethod())) {
                    throw new UnexpectedRequestException();
                }
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			} catch (Exception e) {
				messages.addError(e);
			}
			String updateAuth = request.getParameter("updateAuth");
			// 更新日程共享
			if ("true".equals(updateAuth)) {
				KmCalendarAuth kmCalendarAuth = ((IKmCalendarAuthService) getServiceImp(request))
						.findByPerson(UserUtil.getUser().getFdId());
				// 开始时间
				Date startDate = DateUtil.convertStringToDate(request
						.getParameter("startDate"), DateUtil.PATTERN_DATE,
						request.getLocale());
				List<KmCalendarMain> calendars = getkmCalendarMainServiceImp(
						request).getRangeCalendars(startDate, null, "event",
						true, UserUtil.getUser().getFdId(), null);
				for (KmCalendarMain calendar : calendars) {
					calendar.getAuthReaders().clear();
					calendar.getAuthEditors().clear();
					if (kmCalendarAuth != null) {
						for (Object obj : kmCalendarAuth.getAuthReaders()) {
							// 增加可阅读者
							calendar.getAuthReaders().add(obj);
						}
						for (Object obj : kmCalendarAuth.getAuthModifiers()) {
							// //增加可维护者
							calendar.getAuthEditors().add(obj);
						}
					}
					getkmCalendarMainServiceImp(request).update(calendar);
				}
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
			//返回按钮
				KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(
						"button.back","kmCalendarAuth.do?method=edit", false).save(request);
			
			return getActionForward("success", mapping, form, request, response);
		}
	}

	
	public ActionForward manageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try{
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdSearchName = request.getParameter("fdSearchName");
			if(StringUtil.isNull(fdSearchName)){
				CriteriaValue cv = new CriteriaValue(request);
				fdSearchName = cv.poll("fdSearchName");
			}
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "sysOrgPerson.fdIsAvailable= :fdIsAvailable and sysOrgPerson.fdLoginName!='anonymous' and sysOrgPerson.fdLoginName!='everyone' and sysOrgPerson.fdLoginName!='sysadmin' and sysOrgPerson.fdLoginName!='secadmin' and sysOrgPerson.fdLoginName!='secauditor' ";
			hqlInfo.setParameter("fdIsAvailable", true);
			whereBlock += " and (sysOrgPerson.fdIsAbandon = :fdIsAbandon or sysOrgPerson.fdIsAbandon is null) ";
			hqlInfo.setParameter("fdIsAbandon", false);
			if(StringUtil.isNotNull(fdSearchName)){
				whereBlock += " and (sysOrgPerson.fdName like :fdSearchName or sysOrgPerson.fdNamePinYin like :fdSearchName or sysOrgPerson.fdLoginName like :fdSearchName)";
				hqlInfo.setParameter("fdSearchName", "%"+fdSearchName+"%");
			}
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setModelName("com.landray.kmss.sys.organization.model.SysOrgPerson");
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 记录日志
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		}catch(Exception e){
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-manageList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("manageList", mapping, form, request, response);
		}
	}
	
	public ActionForward manageView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-manageView", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-manageView", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("manageView", mapping, form, request, response);
		}
	}
	
	public ActionForward manageEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-manageEdit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-manageEdit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("manageEdit", mapping, form, request, response);
		}
	}

	private IKmCalendarRequestAuthService kmCalendarRequestAuthService;

	public IKmCalendarRequestAuthService getKmCalendarRequestAuthService() {
		if (kmCalendarRequestAuthService == null) {
			kmCalendarRequestAuthService = (IKmCalendarRequestAuthService) getBean(
					"kmCalendarRequestAuthService");
		}
		return kmCalendarRequestAuthService;
	}

	/**
	 * 进入请求他人权限待办页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward requestAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		TimeCounter.logCurrentTime("Action-requestAuth", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdRequestAuthId = request.getParameter("fdRequestAuthId");
		try{
			KmCalendarRequestAuth kmCalendarRequestAuth = (KmCalendarRequestAuth) getKmCalendarRequestAuthService()
					.findByPrimaryKey(fdRequestAuthId);
			if (kmCalendarRequestAuth == null) {
				throw new NoRecordException();
			}
			request.setAttribute("kmCalendarRequestAuth",
					kmCalendarRequestAuth);

			// 请求待办是否被处理
			boolean isConfirm = true;
			List list = getSysNotifyTodoService()
					.getCoreModels(kmCalendarRequestAuth, "requestAuthKey");
			for (int i = 0; i < list.size(); i++) {
				SysNotifyTodo todo = (SysNotifyTodo) list.get(i);
				List targets = todo.getHbmTodoTargets();
				if (targets.contains(UserUtil.getUser())) {
					isConfirm = false;
					break;
				}
			}
			request.setAttribute("isConfirm", isConfirm);
		}catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-invite", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("requestAuth", mapping, form, request,
					response);
		}
	}

	/**
	 * 同意授权
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-confirmRequest", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdRequestAuthId = request.getParameter("fdRequestAuthId");
		try {
			KmCalendarRequestAuth kmCalendarRequestAuth = (KmCalendarRequestAuth) getKmCalendarRequestAuthService()
					.findByPrimaryKey(fdRequestAuthId);
			if (kmCalendarRequestAuth == null) {
				throw new NoRecordException();
			}
			SysOrgPerson user = UserUtil.getUser();
			KmCalendarAuth kmCalendarAuth = findKmCalendarAuth(request,
					user.getFdId());
			((IKmCalendarAuthService) getServiceImp(request))
					.updateAuthByPerson(kmCalendarAuth,
							kmCalendarRequestAuth.getDocCreator(),
							new RequestContext(request));
			// 发送同意待阅
			List notifyTarget = new ArrayList();
			notifyTarget.add(kmCalendarRequestAuth.getDocCreator());
			getKmCalendarRequestAuthService().saveSendRequestYesNotify(
					kmCalendarRequestAuth, user, notifyTarget,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-confirmRequest", false, getClass());
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

	/**
	 * 不同意授权
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmRequestNo(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-confirmRequestNo", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdRequestAuthId = request.getParameter("fdRequestAuthId");
		try {
			KmCalendarRequestAuth kmCalendarRequestAuth = (KmCalendarRequestAuth) getKmCalendarRequestAuthService()
					.findByPrimaryKey(fdRequestAuthId);
			if (kmCalendarRequestAuth == null) {
				throw new NoRecordException();
			}
			SysOrgPerson user = UserUtil.getUser();
			KmCalendarAuth kmCalendarAuth = findKmCalendarAuth(request,
					user.getFdId());
			((IKmCalendarAuthService) getServiceImp(request))
					.updateAuthByPerson(kmCalendarAuth,
							kmCalendarRequestAuth.getDocCreator(),
							new RequestContext(request));
			// 发送拒绝待阅
			List notifyTarget = new ArrayList();
			notifyTarget.add(kmCalendarRequestAuth.getDocCreator());
			getKmCalendarRequestAuthService().saveSendRequestNoNotify(
					kmCalendarRequestAuth, user, notifyTarget);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-confirmRequestNo", false,
				getClass());
		TimeCounter.logCurrentTime("Action-confirmRequest", false, getClass());
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

	/**
	 * 进入邀请授权页面
	 */
	public ActionForward invite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invite", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdGroupId = request.getParameter("fdGroupId");
		try {
			KmCalendarShareGroup kmCalendarShareGroup = (KmCalendarShareGroup) getKmCalendarShareGroupService()
					.findByPrimaryKey(fdGroupId);
			if (kmCalendarShareGroup == null) {
				throw new NoRecordException();
			}
			String userId = kmCalendarShareGroup.getDocCreator().getFdId();
			Map<String, String> authstatusMap = ((IKmCalendarAuthService) getServiceImp(request))
					.getAuthStatusByUserId(userId);
			String authStatus = new String();
			for (String key : authstatusMap.keySet()) {
				if ("true".equals(authstatusMap.get(key))) {
					authStatus += key + ";";
				}
			}
			request.setAttribute("kmCalendarShareGroup", kmCalendarShareGroup);
			request.setAttribute("authStatus",
					authStatus.length() > 0 ? authStatus.substring(0,
							authStatus.length() - 1) : "");
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-invite", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("invite", mapping, form, request,
					response);
		}
	}

	/**
	 * 确认授权页面
	 */
	public ActionForward confirmInvite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-confirmInvite", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdGroupId = request.getParameter("fdGroupId");
		try {
			KmCalendarShareGroup kmCalendarShareGroup = (KmCalendarShareGroup) getKmCalendarShareGroupService()
					.findByPrimaryKey(fdGroupId);
			if (kmCalendarShareGroup == null) {
				throw new NoRecordException();
			}
			KmCalendarAuth kmCalendarAuth = findKmCalendarAuth(request,
					UserUtil.getUser().getFdId());
			((IKmCalendarAuthService) getServiceImp(request))
					.updateAuthByPerson(kmCalendarAuth,
							kmCalendarShareGroup.getDocCreator(),
							new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-confirmInvite", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 获取日程共享状态
	 */
	public ActionForward getAuthStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-confirmInvite", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdGroupId = request.getParameter("fdGroupId");
		try {
			KmCalendarShareGroup kmCalendarShareGroup = (KmCalendarShareGroup) getKmCalendarShareGroupService()
					.findByPrimaryKey(fdGroupId);
			if (kmCalendarShareGroup == null) {
				throw new NoRecordException();
			}
			String userId = kmCalendarShareGroup.getDocCreator().getFdId();
			Map<String, String> result = ((IKmCalendarAuthService) getServiceImp(request))
					.getAuthStatusByUserId(userId);
			request.setAttribute("result", result);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("getAuthStatus", mapping, form, request,
					response);
		}
	}

}
