package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.forms.KmImeetingStatForm;
import com.landray.kmss.km.imeeting.model.KmImeetingStat;
import com.landray.kmss.km.imeeting.service.IKmImeetingStatService;
import com.landray.kmss.km.imeeting.util.StatResult;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSON;
import net.sf.json.JSONObject;


/**
 * 会议统计 Action
 */
public class KmImeetingStatAction extends ExtendAction {
	protected IKmImeetingStatService kmImeetingStatService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(kmImeetingStatService == null) {
            kmImeetingStatService = (IKmImeetingStatService)getBean("kmImeetingStatService");
        }
		return kmImeetingStatService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNotNull(fdType)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmImeetingStat.fdType = :fdType");
			hqlInfo.setParameter("fdType", fdType);

		}
		String type = request.getParameter("type");// 移动端会议统计列表页
		if (StringUtil.isNotNull(type)) {
			if ("dept".equals(type)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingStat.fdType in ('dept.stat','dept.statMon')");
			} else if ("person".equals(type)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingStat.fdType in ('person.stat','person.statMon')");
			} else if ("res".equals(type)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingStat.fdType in ('resource.stat','resource.statMon')");
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForm actionForm = super.createNewForm(mapping, form, request,
				response);
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNull(fdType)) {
			KmImeetingStatForm kmImeetingStatForm = (KmImeetingStatForm) form;
			fdType = kmImeetingStatForm.getFdType();
		}
		request.setAttribute("fdType", fdType);
		return actionForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNull(fdType)) {
			KmImeetingStatForm kmImeetingStatForm = (KmImeetingStatForm) form;
			fdType = kmImeetingStatForm.getFdType();
		}
		request.setAttribute("fdType", fdType);
	}

	/**
	 * 统计(图表)
	 */
	public final ActionForward statChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-statChart", true, getClass());
		KmssMessages messages = new KmssMessages();
		String chartType = "barLine";// 默认
		try {
			String isWorkbench = request.getParameter("isWorkbench");
			StatResult result = ((IKmImeetingStatService) getServiceImp(request))
					.statChart(
					(IExtendForm) form,
					new RequestContext(request));
			chartType = result.getChartType();
			// 工作台图表
			if ("1".equals(isWorkbench)) {
				result.setxAxisShowAll("true");
				result.setxAxisRotate("30");
				request.setAttribute("isWorkbench", isWorkbench);
			}
			request.setAttribute("result", result);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-statChart", false, getClass());
		if (messages.hasError()) {
			request.setAttribute(
					"lui-source",
					new JSONObject().element("msg",
							ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward(chartType, mapping, form, request, response);
		}

	}

	/**
	 * 统计(列表)
	 */
	public final ActionForward statList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-statList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSON result = ((IKmImeetingStatService) getServiceImp(request))
					.statList((IExtendForm) form, new RequestContext(request));
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-statList", false, getClass());
		if (messages.hasError()) {
			request.setAttribute(
					"lui-source",
					new JSONObject().element("msg",
							ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 统计(列表详情)
	 */
	public final ActionForward statListDetail(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-statListDetail", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSON result = ((IKmImeetingStatService) getServiceImp(request))
					.statListDetail((IExtendForm) form, new RequestContext(
							request));
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-statListDetail", false, getClass());
		if (messages.hasError()) {
			request.setAttribute(
					"lui-source",
					new JSONObject().element("msg",
							ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * excel导出
	 */
	public final ActionForward exportExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}


	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = request.getParameter("fdId");
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String method = request.getParameter("method_GET");
			if ("add".equalsIgnoreCase(method)) {
				id = getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			} else {
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			request.setAttribute("redirectto", mapping.getPath()
					+ ".do?method=view&fdId=" + id);
			return new ActionForward("/resource/jsp/redirect.jsp");
		}
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.view(mapping, form, request, response);
		KmImeetingStatForm mainForm = (KmImeetingStatForm) form;
		String fdId = mainForm.getFdId();
		String fdName = mainForm.getFdName();
		String modelName = getServiceImp(request).getModelName();
		if (UserOperHelper.allowLogOper("statView", modelName)) {
			UserOperContentHelper.putFind(fdId, fdName, modelName);
		}
		return forward;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.edit(mapping, form, request, response);
		KmImeetingStatForm mainForm = (KmImeetingStatForm) form;
		String fdId = mainForm.getFdId();
		String fdName = mainForm.getFdName();
		String modelName = getServiceImp(request).getModelName();
		if (UserOperHelper.allowLogOper("statEdit", modelName)) {
			UserOperContentHelper.putFind(fdId, fdName, modelName);
		}
		return forward;
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		Page page2 = (Page) request.getAttribute("queryPage");
		String modelName = getServiceImp(request).getModelName();
		if (UserOperHelper.allowLogOper("statList", modelName)) {
			List<KmImeetingStat> statList = page2.getList();
			for (KmImeetingStat stat : statList) {
				UserOperContentHelper.putFind(stat.getFdId(), stat.getFdName(),
						modelName);
			}
		}
		return forward;
	}
}

