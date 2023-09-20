package com.landray.kmss.km.calendar.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.forms.KmCalendarAuthListForm;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthListService;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class KmCalendarAuthListAction extends ExtendAction {

    private IKmCalendarAuthListService kmCalendarAuthListService;
	private IKmCalendarAuthService kmCalendarAuthService;
	private IKmCalendarMainService kmCalendarMainService;
	private ISysOrgCoreService sysOrgCoreService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (kmCalendarAuthListService == null) {
            kmCalendarAuthListService = (IKmCalendarAuthListService) getBean("kmCalendarAuthListService");
        }
        return kmCalendarAuthListService;
    }

	public IKmCalendarAuthService
			getKmCalendarAuthServiceImp(HttpServletRequest request) {
		if (kmCalendarAuthService == null) {
			kmCalendarAuthService = (IKmCalendarAuthService) getBean(
					"kmCalendarAuthService");
		}
		return kmCalendarAuthService;
	}

	public IKmCalendarMainService getkmCalendarMainServiceImp(
			HttpServletRequest request) {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) getBean(
					"kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	protected ISysOrgCoreService
			getSysOrgCoreServiceImp(HttpServletRequest request) {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		CriteriaValue cv = new CriteriaValue(request);
		String whereBlock = "1=1";
		String fdPersonId = request.getParameter("fdPersonId");
		if (StringUtil.isNull(fdPersonId)) {
			fdPersonId = UserUtil.getUser(request).getFdId();
		}
		if (StringUtil.isNotNull(fdPersonId)) {
			whereBlock += " and kmCalendarAuthList.fdAuth.docCreator.fdId = :docCreatorFdId "
					+ "and ( kmCalendarAuthList.fdIsPartShare is null or kmCalendarAuthList.fdIsPartShare =:isPartShare )";
			hqlInfo.setParameter("docCreatorFdId", fdPersonId);
			hqlInfo.setParameter("isPartShare", Boolean.FALSE);
		}

		// 共享人员/组织 查询
		String keyword = cv.poll("keyword");
		if (StringUtil.isNotNull(keyword)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmCalendarAuthList.fdPerson.fdName like '%" + keyword
							+ "%' ");
		}

		hqlInfo.setWhereBlock(whereBlock);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, KmCalendarAuthList.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdPersonId = request.getParameter("fdPersonId");
			if (StringUtil.isNull(fdPersonId)) {
				fdPersonId = UserUtil.getUser(request).getFdId();
			}
			if (StringUtil.isNotNull(fdPersonId)) {
				findKmCalendarAuth(request, fdPersonId);
			}

			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			//keyword模糊查询去重 #170059
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

	/**
	 * 后台共享参数设置勾选时，权限数据初始化
	 * 
	 * @param request
	 * @param userid
	 * @throws Exception
	 */
	private void findKmCalendarAuth(HttpServletRequest request,
			String userid) throws Exception {
		KmCalendarAuth auth = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmCalendarAuth.docCreator.fdId=:docCreatorFdId");
		hqlInfo.setParameter("docCreatorFdId", userid);
		List<KmCalendarAuth> auths = getKmCalendarAuthServiceImp(request)
				.findList(hqlInfo);
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
					getKmCalendarAuthServiceImp(request).update(auth);
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
				authList.setFdIsPartShare(false);
				authList.setFdPerson(readList);
				List<KmCalendarAuthList> authLists = new ArrayList<>();
				authLists.add(authList);
				auth.setKmCalendarAuthList(authLists);
			}
			getKmCalendarAuthServiceImp(request).add(auth);
		}
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
			// 1.获取个人共享权限
			KmCalendarAuth kmCalendarAuth = null;
			String fdAuthCreatorId = request.getParameter("fdAuthCreatorId");
			if (StringUtil.isNotNull(fdAuthCreatorId)) {
				kmCalendarAuth = getKmCalendarAuthServiceImp(request)
						.findByPerson(fdAuthCreatorId);
			}
			if (kmCalendarAuth == null) {
				throw new NoRecordException();
			}
			// 2.新增对应共享权限list
			KmCalendarAuthListForm kmCalendarAuthListForm = (KmCalendarAuthListForm) form;
			// 共享权限类型
			String fdAuthType = request.getParameter("fdAuthType");
			setFormAuthType(kmCalendarAuthListForm, fdAuthType);
			kmCalendarAuthListForm.setFdAuthId(kmCalendarAuth.getFdId());
			KmCalendarAuthList model = (KmCalendarAuthList) getServiceImp(
					request).convertFormToModel(kmCalendarAuthListForm, null,
							new RequestContext(request));
			getServiceImp(request).add(model);
			// 3.更新个人共享权限
			getKmCalendarAuthServiceImp(request)
					.updateByAuthList(kmCalendarAuth);
			// 4.历史日程进行同步
			((IKmCalendarAuthListService) getServiceImp(request))
					.updateCalendarByAddList(kmCalendarAuth, model);
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

	private void setFormAuthType(KmCalendarAuthListForm kmCalendarAuthListForm,
			String fdAuthType) {
		if (StringUtil.isNotNull(fdAuthType)) {
			if (fdAuthType.indexOf("read") > -1) {
				kmCalendarAuthListForm.setFdIsRead("true");
			} else {
				kmCalendarAuthListForm.setFdIsRead("false");
			}
			if (fdAuthType.indexOf("modify") > -1) {
				kmCalendarAuthListForm.setFdIsModify("true");
			} else {
				kmCalendarAuthListForm.setFdIsModify("false");
			}
			if (fdAuthType.indexOf("edit") > -1) {
				kmCalendarAuthListForm.setFdIsEdit("true");
			} else {
				kmCalendarAuthListForm.setFdIsEdit("false");
			}
		}
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			// 1.获取个人共享权限
			KmCalendarAuth kmCalendarAuth = null;
			String fdAuthCreatorId = request.getParameter("fdAuthCreatorId");
			if (StringUtil.isNotNull(fdAuthCreatorId)) {
				kmCalendarAuth = getKmCalendarAuthServiceImp(request)
						.findByPerson(fdAuthCreatorId);
			}
			if (kmCalendarAuth == null) {
				throw new NoRecordException();
			}
			List<KmCalendarAuthList> deleteLists = new ArrayList<>();

			// 2.删除权限list
			String[] ids = request.getParameterValues("List_Selected");
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					for (int i = 0; i < authIds.length; i++) {
						KmCalendarAuthList deleteList = new KmCalendarAuthList();
						KmCalendarAuthList authList = (KmCalendarAuthList) getServiceImp(
								request).findByPrimaryKey(authIds[i]);
						cloneAuthList(authList, deleteList);
						deleteLists.add(deleteList);
					}
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					KmCalendarAuthList deleteList = new KmCalendarAuthList();
					KmCalendarAuthList authList = (KmCalendarAuthList) getServiceImp(
							request).findByPrimaryKey(ids[i]);
					cloneAuthList(authList, deleteList);
					deleteLists.add(deleteList);
				}
				getServiceImp(request).delete(ids);
			}

			// 3.更新个人共享权限
			getKmCalendarAuthServiceImp(request)
					.updateByAuthList(kmCalendarAuth);

			// 4.历史日程进行同步
			if (!deleteLists.isEmpty()) {
				for (KmCalendarAuthList list : deleteLists) {
					((IKmCalendarAuthListService) getServiceImp(request))
							.updateCalendarByDeleteList(kmCalendarAuth, list);
				}
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	private void cloneAuthList(KmCalendarAuthList fromList,
			KmCalendarAuthList toList) {
		toList.setFdId(fromList.getFdId());
		toList.setFdIsEdit(fromList.getFdIsEdit());
		toList.setFdIsRead(fromList.getFdIsRead());
		toList.setFdIsModify(fromList.getFdIsModify());
		toList.setFdIsShare(fromList.getFdIsShare());
		toList.setFdShareDate(fromList.getFdShareDate());
		toList.setFdIsPartShare(fromList.getFdIsPartShare());
		List<SysOrgElement> elements = new ArrayList<>();
		elements.addAll(fromList.getFdPerson());
		toList.setFdPerson(elements);
		toList.setFdAuth(fromList.getFdAuth());
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmCalendarAuthListForm kmCalendarAuthListForm = (KmCalendarAuthListForm) form;
		String fdAuthType = "";
		if ("true".equals(kmCalendarAuthListForm.getFdIsRead())) {
            fdAuthType += "read;";
        }
		if ("true".equals(kmCalendarAuthListForm.getFdIsModify())) {
            fdAuthType += "modify;";
        }
		if ("true".equals(kmCalendarAuthListForm.getFdIsEdit())) {
            fdAuthType += "edit;";
        }
		if (StringUtil.isNotNull(fdAuthType)) {
            fdAuthType = fdAuthType.substring(0, fdAuthType.length() - 1);
        }
		request.setAttribute("fdAuthType", fdAuthType);
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
			// 1.获取个人共享权限
			String fdId = request.getParameter("fdId");
			KmCalendarAuth kmCalendarAuth = null;
			String fdAuthCreatorId = request.getParameter("fdAuthCreatorId");
			if (StringUtil.isNotNull(fdAuthCreatorId)) {
				kmCalendarAuth = getKmCalendarAuthServiceImp(request)
						.findByPerson(fdAuthCreatorId);
			}
			if (StringUtil.isNull(fdId) || kmCalendarAuth == null) {
				throw new NoRecordException();
			}
			// 2.更新authList
			// 更新之前的权限
			KmCalendarAuthList authListFromDb = (KmCalendarAuthList) getServiceImp(
					request).findByPrimaryKey(fdId);
			KmCalendarAuthList updateList_pre = new KmCalendarAuthList();
			cloneAuthList(authListFromDb, updateList_pre);
			// 更新之后的权限
			KmCalendarAuthListForm listForm = (KmCalendarAuthListForm) form;
			String fdAuthType = request.getParameter("fdAuthType");
			setFormAuthType(listForm, fdAuthType);
			KmCalendarAuthList updateList_after = (KmCalendarAuthList) getServiceImp(
					request).convertFormToModel(listForm, null,
					new RequestContext(request));
			// 阅读权限不能修改，保留以前的值
			updateList_after.setFdIsRead(updateList_pre.getFdIsRead());
			getServiceImp(request).update(updateList_after);
			// 3.更新个人共享权限
			getKmCalendarAuthServiceImp(request)
					.updateByAuthList(kmCalendarAuth);
			// 4.历史日程进行同步更新
			((IKmCalendarAuthListService) getServiceImp(request))
					.updateCalendarByEditList(kmCalendarAuth, updateList_pre,
							updateList_after);
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
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

}
