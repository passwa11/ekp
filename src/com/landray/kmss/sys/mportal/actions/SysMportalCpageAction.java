package com.landray.kmss.sys.mportal.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.mobile.compressor.CompressMessage;
import com.landray.kmss.sys.mobile.compressor.CompressService;
import com.landray.kmss.sys.mportal.forms.SysMportalCpageForm;
import com.landray.kmss.sys.mportal.forms.SysMportalPageForm;
import com.landray.kmss.sys.mportal.model.SysMportalCpage;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageService;
import com.landray.kmss.sys.mportal.util.SysMportalConstant;
import com.landray.kmss.sys.mportal.util.SysMportalImgUtil;
import com.landray.kmss.sys.mportal.util.ThreadCompressPoolManager;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;

/**
 * 移动公共门户页面 Action
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalCpageAction extends ExtendAction {
	protected ISysMportalCpageService sysMportalCpageService;
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysMportalPageAction.class);

	@Override
	protected ISysMportalCpageService getServiceImp(HttpServletRequest request) {
		if (sysMportalCpageService == null) {
			sysMportalCpageService = (ISysMportalCpageService) getBean("sysMportalCpageService");
		}
		return sysMportalCpageService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysMportalCpageForm xform = (SysMportalCpageForm) super.createNewForm(mapping, form, request,
				response);
		xform.setFdIcon("");
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME,
				request.getLocale()));
		xform.setFdEnabled("true");
		return xform;
	}

	/**
	 * 获取公共门户推送的部件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadPortlets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-loadPortlets", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = ((ISysMportalCpageService) getServiceImp(request))
					.getPushPortlets(new RequestContext(request));
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-loadPortlets", false, getClass());
		if (messages.hasError()) {
            return getActionForward("lui-failure", mapping, form, request, response);
        } else {
            return getActionForward("lui-source", mapping, form, request, response);
        }
	}

	/**
	 * 门户页面选择数据源
	 * 
	 * @param mapping
	 * @param form
	 * @param requestloadPortlets
	 * @param responseloadPortlets
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadPages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-loadPages", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("sysMportalCpage.fdOrder asc, sysMportalCpage.docCreateTime desc");
			hqlInfo.setWhereBlock("sysMportalCpage.fdEnabled=:fdEnabled");
			hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			hqlInfo.setSelectBlock(
					"sysMportalCpage.fdId, sysMportalCpage.fdName,sysMportalCpage.fdType,sysMportalCpage.fdIcon,sysMportalCpage.fdImg");

			String rowsize = request.getParameter("rowsize");

			List<Object[]> list = null;
			if (StringUtil.isNotNull(rowsize)) {
				hqlInfo.setRowSize(Integer.valueOf(rowsize));
				list = this.getServiceImp(request).findPage(hqlInfo).getList();
			} else {
				list = this.getServiceImp(request).findList(hqlInfo);
			}

			JSONArray rtnArray = covertListToJSONArray(list);
			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-loadPages", false, getClass());
		if (messages.hasError()) {
            return getActionForward("lui-failure", mapping, form, request, response);
        } else {
            return getActionForward("lui-source", mapping, form, request, response);
        }
	}

	private JSONArray covertListToJSONArray(List<Object[]> list) throws Exception {
		JSONArray rtnArray = new JSONArray();
		for (Object[] obj : list) {
			JSONArray innerArray = new JSONArray();
			innerArray.add(obj[0]);
			innerArray.add(obj[1]);
			innerArray.add(obj[2]);
			innerArray.add(SysMportalImgUtil.logo(obj[0].toString()));
			innerArray.add(obj[3]);
			innerArray.add(obj[4]);
			rtnArray.add(innerArray);
		}
		return rtnArray;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo)
			throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String keyword = request.getParameter("q.keyword");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(keyword)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" sysMportalCpage.fdName like:keyword");
			hqlInfo.setParameter("keyword", "%" + keyword + "%");
		}

		hqlInfo.setWhereBlock(whereBlock);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalCpage.class);

		// 列表页过滤可编辑者
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_EDITOR);

	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			SysMportalPageForm pageForm = (SysMportalPageForm) form;
			String fdType = pageForm.getFdType();
			if (SysMportalConstant.PAGE_TYPE_URL.equals(fdType)) {
				String url = pageForm.getFdUrl();
				if (StringUtil.isNotNull(url) && url.startsWith("/")) {
					url = request.getContextPath() + url;
				}
				response.sendRedirect(url);
				return null;
			} else if (SysMportalConstant.PAGE_TYPE_PERSON.equals(fdType)) {
				String url = "/sys/mportal/mobile/person/";
				return new ActionForward(url);
			} else if (SysMportalConstant.PAGE_TYPE_PAGE.equals(fdType)) {
				// 移动端优化
				request.setAttribute("imgUrl",
						"/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId="
								+ UserUtil.getUser().getFdId());
				request.setAttribute("userName", UserUtil.getUser().getFdName());
				String logo = request.getContextPath() + SysMportalImgUtil.logo(request.getParameter("fdId"));
				request.setAttribute("logo", logo);

			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	/**
	 * 根据当前时间获取对应提示
	 * 
	 * @return
	 */
	private String getTips(JSONArray ret) {
		if (!ret.isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int all = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

			for (int i = 0; i < ret.size(); i++) {
				String[] startArr = ((String) ret.getJSONObject(i).get("startTime")).split(":");
				int start = Integer.parseInt(startArr[0]) * 60 + Integer.parseInt(startArr[1]);
				String[] endArr = ((String) ret.getJSONObject(i).get("endTime")).split(":");
				int end = Integer.parseInt(endArr[0]) * 60 + Integer.parseInt(endArr[1]);

				if ((start <= all && all < end) || (start < all && 19 * 60 <= start)) {
					JSONArray tips = ret.getJSONObject(i).getJSONArray("texts");
					return (String) tips.getJSONObject((int) (Math.random() * tips.size()))
							.get("value");
				}
			}
		}
		return "";
	}

	public ActionForward disableAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getServiceImp(request).updateInvalidatedAll(ids, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		// KmssReturnPage.getInstance(request).addMessages(messages).addButton(
		// KmssReturnPage.BUTTON_RETURN).save(request);
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward enableAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getServiceImp(request).updateValidatedAll(ids, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
			if (CompressService.isDone()) {
				if (CompressService.isDone()) {
					compressJsAndCss();			
				}
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form, new RequestContext(request));
			
			if (CompressService.isDone()) {
				compressJsAndCss();			
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {

			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	/**
	 * 压缩门户卡片JS和CSS
	 */
	private void compressJsAndCss() {
		ThreadCompressPoolManager threadCompressPoolManager = ThreadCompressPoolManager.getInstance();
		if(!threadCompressPoolManager.isStarted()) {
			threadCompressPoolManager.start();
		}
		Thread thread = new Thread() {
			@Override
			public void run() {
				if(CompressService.isDone()) {
					// 重新压缩门户部件
					String[] names = new String[] { "mui-portal-portlets.js", "mui-portal-portlets.css" };
					CompressService.compress(names);
					CompressMessage.publishMessage(names);	
				}
			}
		};
		threadCompressPoolManager.submit(thread);
	}
	
}
