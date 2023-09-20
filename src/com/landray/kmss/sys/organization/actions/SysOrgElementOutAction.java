package com.landray.kmss.sys.organization.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgElementXmlOutService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class SysOrgElementOutAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgElementOutAction.class);

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	private ISysOrgElementXmlOutService rtxSynchService;

	protected ISysOrgElementXmlOutService getRtxSynchService() {
		if (null == rtxSynchService) {
			rtxSynchService = (ISysOrgElementXmlOutService) getBean(
					"sysOrgPersonToRTXService");
		}
		return rtxSynchService;
	}

	private ISysOrgElementService sysOrgElementService;

	protected ISysOrgElementService getSysOrgElementService() {
		if (null == sysOrgElementService) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private void outXML(HttpServletResponse response, String xml)
			throws IOException {
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Pragma", "No-Cache");
		response.getWriter().write(xml);
	}

	public ActionForward rtx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		outXML(response, getRtxSynchService().getXML());
		return null;
	}

	public ActionForward updatePinYinField(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (UserOperHelper.allowLogOper("updatePinYinField", SysOrgElement.class.getName())) {
				UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
			}
			getSysOrgElementService().updatePinYinField();
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/**
	 * 查询指定组织架构元素的子级元素
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findChildrens(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-findChildren", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("fdIds");
			if (StringUtil.isNull(ids)) {
                messages.addError(new NoRecordException());
            }

			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = "hbmParent.fdName";
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

			List<?> elementList = getSysOrgElementService().findByPrimaryKeys(
					ids.split(","));
			List<?> fdHierarchyIds = ArrayUtil.convertArrayToList(ArrayUtil
					.joinProperty(elementList, "fdHierarchyId", ";0;")[0]
							.split(";0;"));
			String whereBlock = "";
			for (Object fdHierarchyId : fdHierarchyIds) {
				if (StringUtil.isNotNull(whereBlock)) {
					whereBlock += " or ";
				}
				whereBlock += "(fdHierarchyId like '%" + fdHierarchyId
						+ "%' and fdHierarchyId <> '" + fdHierarchyId + "')";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			Page page = getSysOrgElementService().findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-findChildren", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("childrens_list", mapping, form, request,
					response);
		}
	}

	public static void main(String[] args) {
		String s = "fdNameUS";
		System.out.println("name" + s.substring(6));
	}

	public ActionForward getOrgInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String keyword = request.getParameter("keyword");
		String type = request.getParameter("type");
		if (StringUtil.isNull(keyword)) {
			result.put("result", "fail");
			result.put("errMsg", "keyword不能为空");
			outJSON(response, result.toJSONString());
		}
		String[] keywords = keyword.split(";");
		try {
			JSONArray datas = new JSONArray();
			for (String word : keywords) {
				JSONObject data = new JSONObject();
				data.put("keyword", word);
				List<SysOrgElement> list = search(type, word);
				JSONArray array = new JSONArray();
				for (SysOrgElement e : list) {
					JSONObject o = new JSONObject();
					o.put("name", e.getFdNameOri());
					// if (SysLangUtil.isLangEnabled()) {
					// Map<String, String> map = e.getDynamicMap();
					// for (String key : map.keySet()) {
					// if (key.startsWith("fdName")) {
					// o.put("name" + key.substring(6),
					// StringUtil.getString(map.get(key)));
					// }
					// }
					// }
					// o.put("type", e.getFdOrgType());
					// o.put("available", e.getFdIsAvailable());

					SysOrgElement parent = e.getFdParent();
					if (parent != null) {
						o.put("parentName", parent.getFdNameOri());
					}
					if (e instanceof SysOrgPerson) {
						SysOrgPerson person = (SysOrgPerson) e;
						o.put("loginName", person.getFdLoginName());
						// o.put("email",
						// StringUtil.getString(person.getFdEmail()));
						// o.put("mobileNo",
						// StringUtil.getString(person.getFdMobileNo()));
						String img = PersonInfoServiceGetter
								.getPersonHeadimageUrl(e
										.getFdId());
						if (!PersonInfoServiceGetter.isFullPath(img)) {
							img = request.getContextPath() + img;
						}
						o.put("img", img);
					}
					array.add(o);
				}
				data.put("result", array);
				datas.add(data);
			}
			result.put("datas", datas);
		} catch (Exception e1) {
			logger.error(e1.toString());
			result.put("result", "fail");
			result.put("errMsg", e1.getMessage());
			outJSON(response, result.toJSONString());
			return null;
		}

		// JSONObject data = new JSONObject();
		result.put("result", "success");
		// result.put("data", array);
		outJSON(response, result.toJSONString());

		return null;
	}

	private void outJSON(HttpServletResponse response, String json) {
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("Pragma", "No-Cache");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}

	private List<SysOrgElement> search(String type, String word)
			throws Exception {
		// String type = request.getParameter("type");
		if (StringUtil.isNull(word)) {
			return null;
		}
		String whereBlock = "";
		String[] names = word.split(":");
		HQLInfo hqlInfo = new HQLInfo();
		for (int i = 0; i < names.length; i++) {
			whereBlock += " or fdName like :key" + i;
			hqlInfo.setParameter("key" + i, "%" + names[i] + "%");
		}
		whereBlock = "(" + whereBlock.substring(4) + ")";
		if (StringUtil.isNotNull(type)) {
			whereBlock += " and fdOrgType = :type";
			hqlInfo.setParameter("type", Integer.parseInt(type));
		}
		whereBlock += " and fdIsAvailable = :fdIsAvailable";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
		hqlInfo.setRowSize(100);
		// hqlInfo.setCacheable(true);
		hqlInfo.setGetCount(false);
		List<SysOrgElement> list = null;
		list = getSysOrgElementService().findList(hqlInfo);
		return list;
	}
	
	public ActionForward getParentOrgs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JSONArray result = new JSONArray();
		String fdId = request.getParameter("fdId");
		String isEcoPath = request.getParameter("isEcoPath");
		boolean flag = false;
		if (StringUtil.isNotNull(isEcoPath)) {
			flag = Boolean.valueOf(isEcoPath);
		}
		try {
			result = getSysOrgElementService().getParentOrgs(flag, fdId);
			outJSON(response, result.toJSONString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			outJSON(response, result.toJSONString());
			return null;
		}

		return null;
	}
	
	public ActionForward getRecentContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JSONArray result = new JSONArray();
		String orgTypePara = request.getParameter("orgType");
		String selectCount = request.getParameter("selectCount");
		String exceptValue = request.getParameter("exceptValue");
		String isExternal = request.getParameter("isExternal");
		// 生态组织类型
		String cateId = request.getParameter("cateId");
		try {
			result = getSysOrgElementService().getRecentContactList(orgTypePara, selectCount, exceptValue, cateId, isExternal);
			outJSON(response, result.toJSONString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			outJSON(response, result.toJSONString());
			return null;
		}

		return null;
	}

	public void getDeptName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JSONArray result = new JSONArray();
		try {
			result = getSysOrgElementService().getDeptName(new RequestContext(request));
			outJSON(response, result.toJSONString());
		} catch (Exception e) {
			logger.error(e.toString());
			outJSON(response, result.toJSONString());
		}
	}

	public ActionForward getIconInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String orgId = request.getParameter("orgId");
		JSONObject rs = getSysOrgElementService().getIconInfo(orgId);
		request.setAttribute("lui-source", rs);
		return mapping.findForward("lui-source");
	}
}
