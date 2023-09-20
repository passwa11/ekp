package com.landray.kmss.third.weixin.mutil.spi.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.weixin.mutil.spi.forms.WxworkOmsRelationModelForm;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 中间映射表 Action
 * 
 * @author
 * @version 1.0 2017-06-20
 */
public class WxworkOmsRelationAction extends ExtendAction {

	protected IWxworkOmsRelationService mutilWxworkOmsRelationService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (mutilWxworkOmsRelationService == null) {
			mutilWxworkOmsRelationService = (IWxworkOmsRelationService) getBean("mutilWxworkOmsRelationService");
		}
		return mutilWxworkOmsRelationService;
	}

	protected ISysOrgElementService sysOrgElementService;

	protected ISysOrgElementService getSysOrgElementServiceImp(
			HttpServletRequest request) {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	protected ISysOrgPersonService sysOrgPersonService;

	protected ISysOrgPersonService getSysOrgPersonServiceImp(
			HttpServletRequest request) {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean(
					"sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	public ActionForward loadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		WxworkOmsRelationModelForm mainForm = (WxworkOmsRelationModelForm) form;
		FormFile file = mainForm.getFile();
		JSONArray jsonArr = null;
		try {
			jsonArr = ((IWxworkOmsRelationService) getServiceImp(request))
					.addExcel(file);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		response.setCharacterEncoding("UTF-8");
		if (jsonArr != null && jsonArr.size() > 0) {
			response.getWriter()
					.write("<script>parent.callback(" + jsonArr + ")</script>");
		} else if (jsonArr != null && jsonArr.size() == 0) {
			response.getWriter()
					.write("<script>parent.uploadSucess()</script>");
		}
		if (messages.hasError()) {
			response.getWriter().write("<script>parent.loadError();</script>");
		}
		return null;
	}

	public ActionForward addOrUpdateCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-addOrUpdateCheck", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "ok");
		try {
			String fdAppPkId = request.getParameter("fdAppPkId");
			String fdId = request.getParameter("fdId");
			String type = request.getParameter("type");
			String fdWxKey = request.getParameter("fdWxKey");
			String fdEkpId = request.getParameter("fdEkpId");
			if (StringUtil.isNull(fdAppPkId) || StringUtil.isNull(type)
					|| StringUtil.isNull(fdId) || StringUtil.isNull(fdWxKey)) {
				json.put("status", 0);
				json.put("msg", ResourceUtil.getString(
						"wxOmsRelation.param.error", "third-weixin"));
			} else {
				Map<String, String> map = ((IWxworkOmsRelationService) getServiceImp(
						request)).handle(fdId.trim(), fdAppPkId.trim(), type, fdWxKey,fdEkpId);
				if (map != null) {
					if ("1".equals(map.get("status"))) {
						json.put("status", 1);
						json.put("msg", map.get("msg"));
					} else {
						json.put("status", 0);
						json.put("msg", map.get("msg"));
					}
				} else {
					json.put("status", 0);
					json.put("msg", ResourceUtil.getString(
							"wxOmsRelation.param.no.error", "third-weixin"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		if (UserOperHelper.allowLogOper("handle",
				getServiceImp(request).getModelName())) {
			UserOperHelper.logMessage(json.toString());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-addOrUpdateCheck", false,
				getClass());
		return null;
	}

	public ActionForward checkEKP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkEKP", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "ok");
		try {
			String fdEkpId = request.getParameter("fdEkpId");
			if (StringUtil.isNotNull(fdEkpId)) {
				SysOrgPerson person = (SysOrgPerson) getSysOrgPersonServiceImp(
						request).findByPrimaryKey(fdEkpId, null, true);
				if (person != null) {
					json.put("status", 1);
					json.put("msg", person.getFdLoginName());
				} else {
					json.put("status", 0);
					json.put("msg", ResourceUtil.getString(
							"wxOmsRelation.loginname.error", "third-weixin"));
				}
			} else {
				json.put("status", 0);
				json.put("msg", ResourceUtil.getString(
						"wxOmsRelation.loginname.error", "third-weixin"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-checkEKP", false, getClass());
		return null;
	}

	public ActionForward checkThird(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkThird", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "ok");
		try {
			String fdId = request.getParameter("fdId");
			String fdAppPkId = request.getParameter("fdAppPkId");
			String fdWxKey = request.getParameter("fdWxKey");
			boolean flag = ((IWxworkOmsRelationService) getServiceImp(request))
					.checkThird(fdId, fdAppPkId, fdWxKey);
			if (!flag) {
				json.put("status", 0);
				json.put("msg", ResourceUtil.getString(
						"wxOmsRelation.repeat.error", "third-weixin"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		TimeCounter.logCurrentTime("Action-checkThird", false, getClass());
		return null;
	}

	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		String type = request.getParameter("type");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null,
					true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				WxworkOmsRelationModelForm modelForm = (WxworkOmsRelationModelForm) rtnForm;
				SysOrgElement element = null;
				SysOrgPerson person = null;
				if (StringUtil.isNotNull(modelForm.getFdEkpId())) {
					if ("dept".equals(type)) {
						element = (SysOrgElement) getSysOrgElementServiceImp(
								request).findByPrimaryKey(
										modelForm.getFdEkpId(), null, true);
						if (element != null) {
                            modelForm.setFdEkpName(element.getFdName());
                        }
					} else {
						person = (SysOrgPerson) getSysOrgPersonServiceImp(
								request).findByPrimaryKey(
										modelForm.getFdEkpId(), null, true);
						if (person != null) {
							modelForm.setFdEkpName(person.getFdName());
							modelForm
									.setFdEkpLoginName(person.getFdLoginName());
						}
					}
				}
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

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

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				String langFieldName = SysLangUtil.getLangFieldName(
						((IExtendForm) form).getModelClass().getName(),
						orderby);
				if (StringUtil.isNotNull(langFieldName)) {
					orderby = langFieldName;
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			String type = request.getParameter("type");
			String search = request.getParameter("search");
			String fdWxKey = request.getParameter("fdWxKey");
			if (StringUtil.isNotNull(search)) {
				String sql = " and (m.fd_app_pk_id like '%" + search.trim()
						+ "%' or d.fd_name like '%" + search.trim() + "%'";
				if ("person".equals(type)) {
					sql += " or p.fd_login_name like '%" + search.trim()
							+ "%')";
					hqlInfo.setWhereBlock(sql);
				} else {
					hqlInfo.setWhereBlock(sql + ")");
				}
			}
			if (StringUtil.isNotNull(fdWxKey)) {
				String sql = " and m.fd_wx_key='" + fdWxKey + "'";
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " ", sql));
			}
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			Page page = ((IWxworkOmsRelationService) getServiceImp(request))
					.getPage(hqlInfo, type);
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
			return getActionForward("list", mapping, form, request, response);
		}
	}
}
