package com.landray.kmss.third.pda.actions;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.ftsearch.config.LksField;
import com.landray.kmss.sys.ftsearch.db.actions.SearchBuilderAction;
import com.landray.kmss.sys.ftsearch.search.LksHit;
import com.landray.kmss.sys.ftsearch.util.SysFtsearchUtil;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.service.IPdaModuleConfigMainService;
import com.landray.kmss.third.pda.util.PdaModuleConfigUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

public class PdaFtsearchAction extends SearchBuilderAction {

	/**
	 * 全文检索
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward custom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		Page page = new Page();
		try {
			String queryString = request.getParameter("keyword");
			queryString = URLDecoder.decode(queryString);
			String modelName = request.getParameter("modelName");
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			int pageno = 0;
			int rowsize = 20;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			Map<String, String> parameters = new HashMap<String, String>();

			String dateFormat = ResourceUtil.getString("search.format",
					"sys-ftsearch-db");

			if (StringUtil.isNull(modelName)) {
				List<PdaModuleConfigMain> list = getModules(request);
				if (list.size() > 0) {
					int i = 0;
					for (PdaModuleConfigMain pdaModuleConfigMain : list) {
						String fdUrlPrefix = pdaModuleConfigMain
								.getFdUrlPrefix();
						String model = PdaModuleConfigUtil
								.getFtSearchModelName(fdUrlPrefix);
						if (StringUtil.isNotNull(model)) {
							modelName = i == 0 ? model
									: (modelName + ";" + model);
							i++;
						}
					}
				}
			}

			RequestContext requestContext = new RequestContext(request);
			requestContext.getParameterMap().remove("modelName");
			requestContext.setParameter("modelName", modelName);
			requestContext.setParameter("queryString", queryString);
			SysFtsearchUtil.setParameters(parameters, requestContext,
					dateFormat);
			parameters.put("pageno", "" + pageno);
			parameters.put("rowsize", "" + rowsize);
			page = getServiceImp(request).search(parameters);

			while (page.getList().size() == 0 && pageno > 1) {
				parameters.put("pageno", "" + pageno--);
				page = getServiceImp(request).search(parameters);
			}
			JSON json = this.buildPageBean(page, requestContext);
			setClearCache(response);
			request.setAttribute("ftSearchDetail", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("ftSearchDetail");
		}
	}

	/**
	 * 根据全文检索page构建json对象
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private JSON buildPageBean(Page page, RequestContext requestContext)
			throws Exception {
		page.setPagingListSize(5);
		JSONObject json = new JSONObject();
		int docCount = page.getTotalrows();
		json.put("pageCount", page.getTotal());
		json.put("pageno", page.getPageno());
		json.put("count", page.getTotalrows());
		if (page.getEnd() < docCount - 1) {
            json.accumulate("nextPageStart", page.getEnd() + 1); // 下页开始数
        }
		List<LksHit> list = page.getList();
		JSONArray jsonArray = new JSONArray();
		for (LksHit resultModel : list) {
			JSONObject jsonObject = new JSONObject();
			String docSubject = "";
			String linkUrl = "";
			String modelName = "";
			String docCreatorName = "";
			String docCreateTime = "";
			LksHit lksHit = (LksHit) resultModel;

			if (lksHit != null) {
				Map lksFieldsMap = lksHit.getLksFieldsMap();
				if (lksFieldsMap != null && !lksFieldsMap.isEmpty()) {
					LksField subject = (LksField) lksFieldsMap.get("subject");
					LksField title = (LksField) lksFieldsMap.get("title");
					LksField fileName = (LksField) lksFieldsMap.get("fileName");
					if (subject != null) {
						docSubject = subject.getValue();
					} else if (title != null) {
						docSubject = title.getValue();
					} else if (fileName != null) {
						docSubject = fileName.getValue();
					}
					LksField link = (LksField) lksFieldsMap.get("linkStr");
					if (link != null) {
						linkUrl = link.getValue();
					}
					LksField model = (LksField) lksFieldsMap.get("modelName");
					if (model != null) {
						modelName = model.getValue();
					}
					LksField createTime = (LksField) lksFieldsMap
							.get("createTime");
					if (createTime != null) {
						docCreateTime = createTime.getValue();
					}
					LksField creator = (LksField) lksFieldsMap.get("creator");
					if (creator != null) {
						docCreatorName = creator.getValue();
					}

					String preRegex = "<(em|font)>";
					String lastRegex = "</(em|font)>";
					// 去掉样式
					if (StringUtil.isNotNull(docSubject)) {
						docSubject = docSubject.replaceAll(
								"<strong class=\"lksHit\">", "").replaceAll(
								"</strong>", "").replaceAll(preRegex, "")
								.replaceAll(lastRegex, "");
					}
					if (StringUtil.isNotNull(docCreateTime)) {
						docCreateTime = docCreateTime.replaceAll(
								"<strong class=\"lksHit\">", "").replaceAll(
								"</strong>", "").replaceAll(preRegex, "")
								.replaceAll(lastRegex, "");
					}
					if (StringUtil.isNotNull(docCreatorName)) {
						docCreatorName = docCreatorName.replaceAll(
								"<strong class=\"lksHit\">", "").replaceAll(
								"</strong>", "").replaceAll(preRegex, "")
								.replaceAll(lastRegex, "");
					}
					jsonObject.put("type", "doc");
					jsonObject.put("subject", docSubject);
					jsonObject.put("summary", docCreatorName
							+ " "
							+ (createTime.getValue() != null ? createTime
									.getValue() : ""));
					jsonObject.put("url", linkUrl + "&isAppflag=1");
					SysDictModel dict = SysDataDict.getInstance().getModel(
							modelName);
					if (dict != null) {
						modelName = ResourceUtil.getString(
								dict.getMessageKey(), requestContext
										.getLocale());
					}
					jsonObject.put("fdModelName", modelName);
					jsonArray.add(jsonObject);
				}
			}
		}
		json.accumulate("docs", jsonArray);
		return json;
	}

	protected IPdaModuleConfigMainService pdaModuleConfigMainService;

	/**
	 * 模块配置service
	 * 
	 * @param request
	 * @return
	 */
	protected IPdaModuleConfigMainService getPdaModuleConfigMainServiceImp() {
		if (pdaModuleConfigMainService == null) {
            pdaModuleConfigMainService = (IPdaModuleConfigMainService) SpringBeanUtil
                    .getBean("pdaModuleConfigMainService");
        }
		return pdaModuleConfigMainService;
	}

	private List<PdaModuleConfigMain> getModules(HttpServletRequest request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("pdaModuleConfigMain.fdStatus=:fdStatus and pdaModuleConfigMain.fdSubMenuType!=:fdSubType");
		hqlInfo.setParameter("fdStatus",
				PdaModuleConfigConstant.PDA_MODULE_STATUS_ENABLE);
		hqlInfo.setParameter("fdSubType",
				PdaModuleConfigConstant.PDA_MENUS_MODULE);
		hqlInfo.setOrderBy(" pdaModuleConfigMain.fdOrder asc");
		return getPdaModuleConfigMainServiceImp().findValue(hqlInfo);
	}

	/**
	 * 设置不需要缓存
	 * 
	 * @param response
	 */
	private void setClearCache(HttpServletResponse response) {
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
	}
}
