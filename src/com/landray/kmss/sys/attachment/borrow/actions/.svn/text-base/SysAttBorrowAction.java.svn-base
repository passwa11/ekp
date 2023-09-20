package com.landray.kmss.sys.attachment.borrow.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attachment.borrow.forms.SysAttBorrowForm;
import com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow;
import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysAttBorrowAction extends ExtendAction {

	private ISysAttBorrowService sysAttBorrowService;

	@Override
    public ISysAttBorrowService getServiceImp(HttpServletRequest request) {
		if (sysAttBorrowService == null) {
			sysAttBorrowService =
					(ISysAttBorrowService) getBean("sysAttBorrowService");
		}
		return sysAttBorrowService;
	}

	/**
	 * 配合附件借阅明细前端权限筛选器使用
	 */
	private final static String READ_KEY = "read";
	private final static String DOWNLOAD_KEY = "download";
	private final static String PRINT_KEY = "print";
	private final static String COPY_KEY = "copy";

	private final static String DETAIL_FORWARD = "detail";
	private final static String MY_FORWARD = "my";

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request,
                                      HQLInfo hqlInfo) throws Exception {

		HQLHelper.by(request).buildHQLInfo(hqlInfo, SysAttBorrow.class);

		String forward = request.getParameter("forward");
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));

		// 附件借阅明细
		if (DETAIL_FORWARD.equals(forward)) {

			// 借阅权限
			String[] fdAuth = request.getParameterValues("q.fdAuth");

			if (ArrayUtils.isNotEmpty(fdAuth)) {

				StringBuffer hql = new StringBuffer();

				for (int i = 0; i < fdAuth.length; i++) {

					if (i == 0) {
						hql.append("(");
					} else {
						hql.append(" or ");
					}

					if (READ_KEY.equals(fdAuth[i])) {
						hql.append("sysAttBorrow.fdReadEnable = :fdReadEnable");
					}

					if (DOWNLOAD_KEY.equals(fdAuth[i])) {
						hql.append("sysAttBorrow.fdDownloadEnable = :fdDownloadEnable");
					}

					if (PRINT_KEY.equals(fdAuth[i])) {
						hql.append("sysAttBorrow.fdPrintEnable = :fdPrintEnable");
					}

					if (COPY_KEY.equals(fdAuth[i])) {
						hql.append("sysAttBorrow.fdCopyEnable = :fdCopyEnable");
					}

					if (i == fdAuth.length - 1) {
						hql.append(")");
					}
				}

				hqlInfo.setWhereBlock(StringUtil.linkString(
						hqlInfo.getWhereBlock(), " and ", hql.toString()));
				for (int i = 0; i < fdAuth.length; i++) {
					if (READ_KEY.equals(fdAuth[i])) {
						hqlInfo.setParameter("fdReadEnable", true);
					}
					if (DOWNLOAD_KEY.equals(fdAuth[i])) {
						hqlInfo.setParameter("fdDownloadEnable", true);
					}
					if (PRINT_KEY.equals(fdAuth[i])) {
						hqlInfo.setParameter("fdPrintEnable", true);
					}
					if (COPY_KEY.equals(fdAuth[i])) {
						hqlInfo.setParameter("fdCopyEnable", true);
					}
				}
			}

			// 附件主键
			String fdAttId = request.getParameter("attId");
			if (StringUtil.isNotNull(fdAttId)) {
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
								"sysAttBorrow.fdAttId = :fdAttId"));
				hqlInfo.setParameter("fdAttId", fdAttId);
			}

			return;
		}

		String type = request.getParameter("type");

		// 我的借阅
		if (MY_FORWARD.equals(type)) {

			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "sysAttBorrow.fdBorrowers.fdId = :fdUserId"));
			hqlInfo.setParameter("fdUserId", UserUtil.getUser().getFdId());
		}
		
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_NO);

		hqlInfo.setJoinBlock(
				", com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain");
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
				" and ", "sysAttBorrow.fdAttId = sysAttMain.fdId"));
		hqlInfo.setSelectBlock("sysAttBorrow.fdId," + "sysAttBorrow.fdStatus,"
				+ "sysAttBorrow.fdBorrowEffectiveTime,"
				+ "sysAttBorrow.fdDuration,"
				+ "sysAttMain.fdFileName,sysAttMain.fdSize,sysAttMain.fdModelName");
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAttBorrowForm sysAttBorrowForm =
				(SysAttBorrowForm) super.createNewForm(mapping, form, request,
						response);
		((ISysAttBorrowService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
		return sysAttBorrowForm;
	}

	/**
	 * 借阅数量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward count(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-count", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			JSONObject json = new JSONObject();

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setGettingCount(true);

			String type = request.getParameter("type");

			// 我借阅的
			if ("create".equals(type)) {
				hqlInfo.setWhereBlock(
						"sysAttBorrow.fdBorrowers.fdId =:fdBorrowerId");
				hqlInfo.setParameter("fdBorrowerId",
						UserUtil.getUser().getFdId());
			}

			hqlInfo.setSelectBlock("count(distinct sysAttBorrow.fdId)");

			List<Long> count = this.getServiceImp(request).findValue(hqlInfo);
			json.put("count", count.get(0));

			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-count", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 关闭借阅
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward close(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-close", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			String[] fdIds = request.getParameterValues("fdIds");

			if (!ArrayUtils.isEmpty(fdIds)) {
				getServiceImp(request).updateCloseStatus(fdIds);
			}

			JSONObject json = new JSONObject();
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-close", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
}
