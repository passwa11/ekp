package com.landray.kmss.km.calendar.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.forms.KmCalendarShareGroupForm;
import com.landray.kmss.km.calendar.forms.KmCalendarUserShareGroupForm;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarShareGroupService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2008-十月-16
 * 
 * @author 陈亮
 */
public class KmCalendarUserShareGroupAction extends ExtendAction

{
	protected IKmCalendarShareGroupService kmCalendarShareGroupService;

	protected IKmCalendarShareGroupService getKmCalendarShareGroupService(
			HttpServletRequest request) {
		if (kmCalendarShareGroupService == null) {
            kmCalendarShareGroupService = (IKmCalendarShareGroupService) getBean("kmCalendarShareGroupService");
        }
		return kmCalendarShareGroupService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		whereBlock += " and sysCalendarShareGroup.docCreator.fdId= :docCreatorFdId";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("docCreatorFdId", UserUtil.getUser().getFdId());
	}
	
	/**
	 * 更新共享群组
	 */
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarUserShareGroupForm kmCalenadrUserShareGroupForm = (KmCalendarUserShareGroupForm) form;
		String idStr = kmCalenadrUserShareGroupForm.getDeleteIds();
		// 删除群组
		if (StringUtil.isNotNull(idStr)) {
			String[] ids = idStr.split(",");
			try {
				getKmCalendarShareGroupService(request).delete(ids);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		List<KmCalendarShareGroupForm> list = (List<KmCalendarShareGroupForm>) kmCalenadrUserShareGroupForm
				.getKmCalendarShareGroupFormList();
		try {
			for (KmCalendarShareGroupForm kmCalendarShareGroupForm : list) {
				String fdId = kmCalendarShareGroupForm.getFdId();
				// 群组名称为空说明此群组已被删除，不再做新增更新操作
				if (StringUtil.isNull(kmCalendarShareGroupForm.getFdName())) {
					continue;
				}
				//无fdId的,新增
				if (StringUtil.isNull(fdId)) {
					KmCalendarShareGroup kmCalendarShareGroup = (KmCalendarShareGroup) getKmCalendarShareGroupService(
							request).convertFormToModel(
							kmCalendarShareGroupForm, null,
							new RequestContext(request));
					kmCalendarShareGroup.setFdId(IDGenerator.generateID());
					kmCalendarShareGroup.setDocCreateTime(new Date());
					kmCalendarShareGroup.setDocCreator(UserUtil.getUser());
					if (UserOperHelper.allowLogOper(
							SysLogOperXml.LOGPOINT_UPDATE,
							getKmCalendarShareGroupService(request)
									.getModelName())) {
						UserOperContentHelper
								.putAdd(kmCalendarShareGroup, "fdDescription",
										"fdOrder", "docCreateTime",
										"fdGroupMemberIds",
										"fdGroupMemberNames")
								.putSimple("fdDescription",
										kmCalendarShareGroup.getFdDescription())
								.putSimple("fdOrder",
										kmCalendarShareGroup.getFdOrder())
								.putSimple("docCreateTime",
										kmCalendarShareGroup.getDocCreateTime())
								.putSimple("fdGroupMemberIds",
										kmCalendarShareGroup
												.getFdGroupMemberIds())
								.putSimple("fdGroupMemberNames",
										kmCalendarShareGroup
												.getFdGroupMemberNames());
					}
					getKmCalendarShareGroupService(request).add(
							kmCalendarShareGroup);
				//有fdId的,更新
				} else {
					getKmCalendarShareGroupService(request).update(
							kmCalendarShareGroupForm,
							new RequestContext(request));
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
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String personId = UserUtil.getUser().getFdId();
		List<KmCalendarShareGroup> userShareGroups = getKmCalendarShareGroupService(
				request).getUserShareGroups(personId);
		List<KmCalendarShareGroupForm> userShareGroupForms = new ArrayList<KmCalendarShareGroupForm>();
		for (KmCalendarShareGroup group : userShareGroups) {
			KmCalendarShareGroupForm kmCalendarShareGroupForm = new KmCalendarShareGroupForm();
			getKmCalendarShareGroupService(request).convertModelToForm(
					(IExtendForm) kmCalendarShareGroupForm, group,
					new RequestContext(request));
			userShareGroupForms.add(kmCalendarShareGroupForm);
			UserOperHelper.logFind(group);
		}
		KmCalendarUserShareGroupForm kmCalendarUserShareGroupForm = new KmCalendarUserShareGroupForm();
		kmCalendarUserShareGroupForm
				.setKmCalendarShareGroupFormList(userShareGroupForms);

		if (kmCalendarUserShareGroupForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(
				getFormName(kmCalendarUserShareGroupForm, request),
				kmCalendarUserShareGroupForm);
	}

	public ActionForward test(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO 自动生成的方法存根
		return null;
	}

}
