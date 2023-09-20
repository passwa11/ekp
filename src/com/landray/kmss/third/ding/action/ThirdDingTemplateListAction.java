package com.landray.kmss.third.ding.action;

import java.util.ArrayList;
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
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.forms.ThirdDingDtemplateForm;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.third.ding.model.ThirdDingTemplateList;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询钉钉的模板
 * 
 * @author chw
 *
 */
public class ThirdDingTemplateListAction extends ExtendAction {

    private IThirdDingDtemplateService thirdDingDtemplateService;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingTemplateListAction.class);

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingDtemplateService == null) {
            thirdDingDtemplateService = (IThirdDingDtemplateService) getBean("thirdDingDtemplateService");
        }
        return thirdDingDtemplateService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingDtemplate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingDtemplate.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingDtemplateForm thirdDingDtemplateForm = (ThirdDingDtemplateForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingDtemplateService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingDtemplateForm;
    }

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward data(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!UserUtil.getKMSSUser().isAdmin()) {
				logger.warn("非超级管理员禁止访问！");
				return getActionForward("failure", mapping, form, request,
						response);
			}
			String s_pageno = request.getParameter("pageno");
			logger.debug("s_pageno:" + s_pageno);
			String s_rowsize = request.getParameter("rowsize");
			logger.debug("s_rowsize:" + s_rowsize);
			int pageno = 1;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			Page page = new Page();
			List<ThirdDingTemplateList> list = new ArrayList<ThirdDingTemplateList>();
			ThirdDingTemplateList temp = null;
			SysOrgPerson user = UserUtil.getUser();
			String userId = ((IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService"))
							.getDingUserIdByEkpUserId(user.getFdId());

			logger.debug("userId:" + userId);
			String rs = DingUtils.getDingApiService()
					.getDingTemplateList(userId, 0L, 500L);
			JSONArray ja = new JSONArray();
			if (StringUtil.isNotNull(rs)) {
				JSONObject rsJSON = JSONObject.fromObject(rs);
				if (rsJSON.containsKey("errcode")
						&& rsJSON.getInt("errcode") == 0) {
					ja = rsJSON.getJSONObject("result")
							.getJSONArray("template_list");
					for (int i = (pageno - 1)
							* rowsize; i < ((pageno * rowsize) < ja.size()
									? pageno * rowsize : ja.size()); i++) {
						JSONObject currentTemp = ja.getJSONObject(i);
						logger.debug(currentTemp.toString());
						temp = new ThirdDingTemplateList();
						temp.setProcessCode(
								currentTemp.getString("process_code"));
						temp.setName(currentTemp.getString("name"));
						String description = "";
						try {
							description = JSONObject
									.fromObject(
											currentTemp
													.getString("form_content"))
									.getString("description");
						} catch (Exception e) {
						}

						temp.setDescription(description);
						temp.setFdId(currentTemp.getString("process_code"));
						list.add(temp);
					}
				} else {
					logger.warn("接口异常：" + rs);
				}

			}

			page.setList(list);
			page.setTotal(ja.size());
			page.setTotalrows(ja.size());
			page.setAscending(false);
			page.setPageno(pageno);
			page.setRowsize(rowsize);

			page.setRowsize(rowsize);
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

	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!UserUtil.getKMSSUser().isAdmin()) {
			logger.warn("非超级管理员禁止访问！");
			return getActionForward("failure", mapping, form, request,
					response);
		}
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			for (int i = 0; i < ids.length; i++) {
				String rs = DingUtils.getDingApiService().delTemplate(ids[i]);
				logger.debug("----delete:" + rs);
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
}
