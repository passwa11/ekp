package com.landray.kmss.sys.mportal.actions;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.mobile.compressor.CompressMessage;
import com.landray.kmss.sys.mobile.compressor.CompressService;
import com.landray.kmss.sys.mportal.forms.SysMportalCardForm;
import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.plugin.MportalMportletUtil;
import com.landray.kmss.sys.mportal.service.ISysMportalCardService;
import com.landray.kmss.sys.mportal.xml.SysMportalMportlet;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author
 * @version 1.0 2015-09-14
 */
public class SysMportalCardAction extends ExtendAction {
	protected ISysMportalCardService SysMportalCardService;

	@Override
	protected ISysMportalCardService getServiceImp(HttpServletRequest request) {
		if (SysMportalCardService == null) {
			SysMportalCardService = (ISysMportalCardService) getBean("sysMportalCardService");
		}
		return SysMportalCardService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysMportalCardForm pForm = (SysMportalCardForm) form;
		KMSSUser user = UserUtil.getKMSSUser();
		pForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, null));
		pForm.setDocCreatorId(user.getUserId());
		pForm.setDocCreatorName(user.getUserName());
		pForm.setFdEnabled("true");
		pForm.setFdIsPushed("true");
		return form;
	}

	/*
	 * /* "cateid": { "cateid": "142167f2dbf6268bdef13a84271966db",
	 * "cateid_name": "新闻类5" },
	 * 
	 * if (val instanceof JSONObject) { param.attr("value", sourceOpt
	 * .getJSONObject(var.getKey()) .getString(var.getKey())); } else {
	 * param.attr("value", sourceOpt.getString(var.getKey())); }
	 */

	public ActionForward loadPortlets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-loadPortlets", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = ((ISysMportalCardService) getServiceImp(request))
					.getPushPortlets(new RequestContext(request));
			request.setAttribute("lui-source", array);

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-loadPortlets", false, getClass());
		if (messages.hasError()) {
            return getActionForward("lui-failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	/**
	 * 通过ID获取门户部件对象
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPortletById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getPortletById", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String portletId = request.getParameter("portletId");
			SysMportalMportlet mportlet = MportalMportletUtil
					.getPortletById(portletId);
			JSONObject mportletJsonObj = new JSONObject();
			mportletJsonObj.put("portletId", mportlet.getId());
			mportletJsonObj.put("portletName", mportlet.getName());
			mportletJsonObj.put("description",
					ResourceUtil.getMessage(mportlet.getDescription()));
			request.setAttribute("lui-source", mportletJsonObj);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getPortletById", false, getClass());
		if (messages.hasError()) {
            return getActionForward("lui-failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = null;
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
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			String contentType = request.getParameter("contentType");
			if ("json".equals(contentType)) {
				return getActionForward("listdata", mapping, form, request,
						response);
			} else {
				return getActionForward("list", mapping, form, request, response);
			}
		}
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
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));

			if (CompressService.isDone()) {
				// 重新压缩门户部件
				String[] names = new String[] { "mui-portal-portlets.js", "mui-portal-portlets.css" };
				CompressService.compress(names);
				CompressMessage.publishMessage(names);
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
			IExtendForm mainForm = (IExtendForm) form;
			String fdModelId = mainForm.getFdId();
			String fdModelName = mainForm.getModelClass().getName();
			SysDictModel model = SysDataDict.getInstance().getModel(fdModelName);
			if(model!=null&&!StringUtil.isNull(model.getUrl())){
				KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(
						"button.back",formatModelUrl(fdModelId,model.getUrl()), false).save(request);
			}
			
			return getActionForward("success", mapping, form, request, response);
		}
	}

	private String formatModelUrl(String value, String url) {
		if (StringUtil.isNull(url)) {
            return null;
        }
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				url = StringUtil.replace(url, "${" + property + "}", value);
			} catch (Exception e) {
			}
		}
		return url;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);

		String keyword = request.getParameter("q.keyword");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(keyword)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" sysMportalCard.fdName like:keyword");

			hqlInfo.setParameter("keyword", "%" + keyword + "%");
		}

		hqlInfo.setWhereBlock(whereBlock);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalCard.class);
		// 列表页面加可维护者权限过滤
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_EDITOR);

		String fdModuleCateId = cv.poll("fdModuleCate");
		if (StringUtil.isNotNull(fdModuleCateId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(
					hqlInfo.getWhereBlock(), " and ",
					" sysMportalCard.fdModuleCate.fdId =  :fdModuleCateId "));
			hqlInfo.setParameter("fdModuleCateId", fdModuleCateId);
		}
	}

	
	public ActionForward disableAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getServiceImp(request).updateInvalidatedAll(ids,
						new RequestContext(request));
				if (CompressService.isDone()) {
					// 重新压缩门户部件
					String[] names = new String[] { "mui-portal-portlets.js",
							"mui-portal-portlets.css" };
					CompressService.compress(names);
					CompressMessage.publishMessage(names);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	public ActionForward enableAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getServiceImp(request).updateValidatedAll(ids,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
				if (CompressService.isDone()) {
					// 重新压缩门户部件
					String[] names = new String[] { "mui-portal-portlets.js",
							"mui-portal-portlets.css" };
					CompressService.compress(names);
					CompressMessage.publishMessage(names);
				}
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/**
	 * 通过ID获取卡片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadCardById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-loadCardById", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = ((ISysMportalCardService) getServiceImp(request))
					.getCardById(new RequestContext(request));
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-loadCardById", false, getClass());
		if (messages.hasError()) {
            return getActionForward("lui-failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }
	}

}
