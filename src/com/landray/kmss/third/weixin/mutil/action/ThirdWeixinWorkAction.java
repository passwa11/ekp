package com.landray.kmss.third.weixin.mutil.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.forms.ThirdWeixinWorkForm;
import com.landray.kmss.third.weixin.mutil.model.ThirdWeixinWorkMutil;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkMutilMenuModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkMenuService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 应用配置 Action
 * 
 * @author
 * @version 1.0 2017-05-02
 */
public class ThirdWeixinWorkAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWeixinWorkAction.class);
	
	protected IThirdWeixinWorkService thirdMutilWeixinWorkService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (thirdMutilWeixinWorkService == null) {
			thirdMutilWeixinWorkService = (IThirdWeixinWorkService) getBean("thirdMutilWeixinWorkService");
		}
		return thirdMutilWeixinWorkService;
	}

	private IWxworkMenuService wxworkMenuService;

	protected IWxworkMenuService getwxworkMenuServiceImp(
			HttpServletRequest request) {
		if (wxworkMenuService == null) {
			wxworkMenuService = (IWxworkMenuService) getBean(
					"mutilWxworkMenuService");
		}
		return wxworkMenuService;
	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		ThirdWeixinWorkMutil weixinWork = null;
		String id = request.getParameter("fdId");
		String url = "/resource/third/wxwork/mutil/cpEndpoint.do?method=service&agentId=";
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null,
					true);
			if (model != null) {
				UserOperHelper.logFind(model);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				ThirdWeixinWorkForm tform = (ThirdWeixinWorkForm) rtnForm;
				weixinWork = (ThirdWeixinWorkMutil) model;
				if (StringUtil.isNotNull(tform.getFdAgentid())) {
					String fdWxKey = tform.getFdWxKey();
					String wxdomain = WeixinMutilConfig.newInstance(fdWxKey)
							.getWxDomain();
					if (!(wxdomain + url + tform.getFdAgentid().trim())
							.equals(tform.getFdCallbackUrl())) {
						tform.setFdCallbackUrl(
								wxdomain + url + tform.getFdAgentid().trim() + "&key=" + fdWxKey);
						weixinWork.setFdCallbackUrl(tform.getFdCallbackUrl());
						weixinWork.setFdSystemUrl(wxdomain);
						getServiceImp(request).update(weixinWork);
					}
				}
				logger.debug("fdWxKey:" + tform.getFdWxKey());
				WxworkMutilMenuModel module = (WxworkMutilMenuModel) getwxworkMenuServiceImp(request).findFirstOne(
						"fdAgentId='" + weixinWork.getFdAgentid()
								+ "' and fdWxKey='" + tform.getFdWxKey() + "'",
						null);
				if (module != null) {
					logger.debug(
							"---------fdMenuId:" + module.getFdId());
					request.setAttribute("fdMenuId",
							module.getFdId());
					request.setAttribute("fdMenuPuslished",
							module.getFdPublished());
				}
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	private WxmutilApiService wxmutilApiService = null;

	public ActionForward appList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-appList", true, getClass());
		KmssMessages messages = new KmssMessages();
		String key = request.getParameter("key");
		if (StringUtil.isNull(key)) {
			logger.warn("请先选择微信配置key!");
			return null;
		}
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		JSONArray ja = new JSONArray();
		JSONObject jn = null;
		JSONObject jo = null;
		String agentSimple = null;
		try {
			wxmutilApiService = WxmutilUtils.getWxmutilApiServiceList()
					.get(key);

			List<ThirdWeixinWorkMutil> wxlist = getServiceImp(request).findList(null,
					"docCreateTime desc");
			UserOperHelper.logFindAll(wxlist,
					getServiceImp(request).getModelName());
			for (ThirdWeixinWorkMutil wk : wxlist) {
				agentSimple = wxmutilApiService.agentGet(wk.getFdAgentid());

				if (StringUtil.isNotNull(agentSimple)) {
					jo = JSONObject.fromObject(agentSimple);

					if (301002 == jo.getInt("errcode")) {
						logger.info(
								"AgentId=" + wk.getFdAgentid() + "的配置异常，直接跳过");
						continue;
					} else {
						if (jo.containsKey("errcode")
								&& 0 == jo.getInt("errcode")
								&& jo.containsKey("close")
								&& 0 == jo.getInt("close")) {
							jn = new JSONObject();
							jn.put("name", wk.getFdName());
							jn.put("appId", wk.getFdAgentid());
							ja.add(jn);
						}
					}
				}
			}
			json.put("data", ja);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-appList", false, getClass());
		return null;
	}
	
	
	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdWxKey = request.getParameter("fdWxKey");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			hqlInfo.setWhereBlock("thirdWeixinWorkMutil.fdWxKey =:fdWxKey");
			hqlInfo.setParameter("fdWxKey", fdWxKey);
			Page page = getServiceImp(request).findPage(hqlInfo);
			//UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
			request.setAttribute("fdWxKey", fdWxKey);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}
	
	/**
	 * 打开新增页面。<br>
	 * 该操作的大部分代码有具体业务逻辑由runAddAction实现，这里仅做错误以及页面跳转的处理。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
				String fdWxKey = request.getParameter("fdWxKey");
				request.setAttribute("fdWxKey", fdWxKey);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}


}
