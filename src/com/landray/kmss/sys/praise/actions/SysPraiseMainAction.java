package com.landray.kmss.sys.praise.actions;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.praise.model.SysPraiseMain;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysPraiseMainAction extends ExtendAction {

	ISysPraiseMainService sysPraiseMainService;

	@Override
	protected ISysPraiseMainService getServiceImp(HttpServletRequest request) {
		if (sysPraiseMainService == null) {
			sysPraiseMainService =
					(ISysPraiseMainService) getBean("sysPraiseMainService");
		}
		return sysPraiseMainService;
	}

	/**
	 * 点赞(踩)或取消点赞(踩)
	 * 
	 */
	public ActionForward executePraise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-executePraise", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelName = request.getParameter("fdModelName");
			String fdModelId = request.getParameter("fdModelId");
			String fdType = request.getParameter("fdType");
			String fdItemType = SysPraiseMain.SYSPRAISEMAIN_PRAISE;
			if (StringUtil.isNotNull(fdType) && "negative".equals(fdType)) {
				fdItemType = SysPraiseMain.SYSPRAISEMAIN_NEGATIVE;
			}
			getServiceImp(request).addOrDel(fdModelId, fdModelName, fdItemType,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-executePraise", false, getClass());
		if (messages.hasError()) {
			response.sendError(505);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 新增移动端点赞
	 * 
	 */

	public ActionForward addNewPraiseOnMobile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelId = request.getParameter("fdModelId");
			String fdModelName = request.getParameter("fdModelName");

			Boolean praise = getServiceImp(request).checkPraised(
					UserUtil.getUser().getFdId(), fdModelId, fdModelName, null);
			if (praise) {
				throw new KmssRuntimeException(new KmssMessage(
						"sys-praise:sysPraiseMain.praise.alert"));
			}

			String serviceBean = SysDataDict.getInstance().getModel(fdModelName)
					.getServiceBean();
			IBaseService service =
					(IBaseService) SpringBeanUtil.getBean(serviceBean);
			BaseModel docbase = (BaseModel) service.findByPrimaryKey(fdModelId);

			SysPraiseMain sysPraiseMainMain = new SysPraiseMain();
			sysPraiseMainMain.setFdPraisePerson(UserUtil.getUser());
			sysPraiseMainMain.setFdPraiseTime(new Date());
			sysPraiseMainMain.setFdModelId(fdModelId);
			sysPraiseMainMain.setFdModelName(fdModelName);

			getServiceImp(request).add(sysPraiseMainMain);
			getServiceImp(request).updatePraiseCount(service, docbase, "+", "");
			JSONObject jsonObj = new JSONObject();

			// 记录日志
			if (UserOperHelper.allowLogOper("addNewPraiseOnMobile",
					getServiceImp(request).getModelName())) {

				UserOperContentHelper.putAdd(sysPraiseMainMain,
						"fdPraisePerson", "fdPraiseTime", "fdModelId",
						"fdModelName");

				UserOperContentHelper.putUpdate(docbase.getFdId(), "",
						service.getModelName());
			}

			jsonObj.accumulate("fdId", sysPraiseMainMain.getFdId());
			request.setAttribute("lui-source", jsonObj);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	/**
	 * 新增移动端取消点赞
	 * 
	 */
	public ActionForward deletePraiseOnMobile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String fdModelId = request.getParameter("fdModelId");
			String fdModelName = request.getParameter("fdModelName");
			String praiseId = getServiceImp(request).getPraiseId(
					UserUtil.getUser().getFdId(), fdModelId, fdModelName, null);
			if (StringUtil.isNotNull(praiseId)) {
				getServiceImp(request).delete(praiseId);
				String serviceBean = SysDataDict.getInstance()
						.getModel(fdModelName).getServiceBean();
				IBaseService service =
						(IBaseService) SpringBeanUtil.getBean(serviceBean);
				BaseModel docbase =
						(BaseModel) service.findByPrimaryKey(fdModelId);
				getServiceImp(request).updatePraiseCount(service, docbase, "-",
						"");
			}

			JSONObject result = new JSONObject();
			result.put("msg", ResourceUtil.getString("return.optSuccess"));
			request.setAttribute("lui-source", result);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("lui-source", new JSONObject().element("msg",
					ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request,
					response);
		}

		return getActionForward("lui-source", mapping, form, request, response);
	}

	/**
	 * 获取点赞(踩)人
	 * 
	 */
	public ActionForward getPraisedPersons(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getPraisedPersons", true,
				getClass());
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_isShowAll = request.getParameter("isShowAll");
			String isShowPage = request.getParameter("isShowPage");
			String fdModelName = request.getParameter("fdModelName");
			String fdModelId = request.getParameter("fdModelId");
			String fdType = request.getParameter("fdType");
			String praiseCount = request.getParameter("showPraiserCount");
			int showPraiserCount = 0;
			if (StringUtil.isNotNull(praiseCount)) {
				showPraiserCount = Integer
						.parseInt(request.getParameter("showPraiserCount"));// 显示点赞(踩)人数量（简版显示）
			}

			int pageno = 1;
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}

			Boolean isShowAll = true;
			if (StringUtil.isNotNull(s_isShowAll)
					&& "false".equals(s_isShowAll)) {
                isShowAll = false;
            }

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(" fdPraiseTime desc ");
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(8);
			String whereBlock =
					"fdModelName =:fdModelName and fdModelId =:fdModelId ";
			if (StringUtil.isNotNull(fdType) && "negative".equals(fdType)) {
				whereBlock = whereBlock + " and fdType=:fdType";
				hqlInfo.setParameter("fdType",
						SysPraiseMain.SYSPRAISEMAIN_NEGATIVE);
			} else {
				whereBlock =
						whereBlock + "and (fdType is null or fdType=:fdType)";
				hqlInfo.setParameter("fdType",
						SysPraiseMain.SYSPRAISEMAIN_PRAISE);
			}
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdModelName", fdModelName);
			hqlInfo.setParameter("fdModelId", fdModelId);

			Page page = getServiceImp(request).findPage(hqlInfo);
			List<SysPraiseMain> praisedPersons = page.getList();

			JSONObject json = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			for (int i = 0; i < praisedPersons.size(); i++) {
				if (!isShowAll && i >= showPraiserCount) {
					break;
				}
				JSONObject jsonObject = new JSONObject();
				String personId =
						praisedPersons.get(i).getFdPraisePerson().getFdId();
				jsonObject.put("personId", personId);
				jsonObject.put("personName",
						praisedPersons.get(i).getFdPraisePerson().getFdName());
				String path =
						PersonInfoServiceGetter.getPersonHeadimageUrl(personId);
				if (!PersonInfoServiceGetter.isFullPath(path)) {
					path = request.getContextPath() + path;
				}
				jsonObject.put("imgUrl", path);
				jsonArray.add(jsonObject);

				// 记录日志
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FINDALL,
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putFind(praisedPersons.get(i));
				}

			}
			json.element("personsList", jsonArray);
			if (!isShowAll && praisedPersons.size() > showPraiserCount) {
				json.element("hasMore", true);
			} else {
				json.element("hasMore", false);
			}
			// 展示所有点赞者时的翻页
			if (StringUtil.isNotNull(isShowPage) && "true".equals(isShowPage)) {
				int totalPage = page.getTotal();
				if (pageno < totalPage) {
					json.element("nextPage", true);
				} else {
					json.element("nextPage", false);
				}
				if (pageno > 1) {
					json.element("prePage", true);
				} else {
					json.element("prePage", false);
				}
			}

			out.println(json.toString());
			UserOperHelper.setOperSuccess(true);

		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		TimeCounter.logCurrentTime("Action-getPraisedPersons", false,
				getClass());
		if (messages.hasError()) {
			response.sendError(505);
			out.println(messages);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 检测当前人员是否已赞过文档（多个）
	 * 
	 */
	public ActionForward checkPraisedByIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkPraisedByIds", true,
				getClass());
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelIds = request.getParameter("fdModelIds");
			String fdModelName = request.getParameter("fdModelName");
			List praiseIds = getServiceImp(request).checkPraisedByIds(
					UserUtil.getUser().getFdId(), fdModelIds, fdModelName);

			JSONObject json = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < praiseIds.size(); i++) {
				jsonArray.add(praiseIds.get(i));
			}

			json.element("praiseIds", jsonArray);
			out.println(json.toString());
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}finally {
			UserOperHelper.setModelNameAndModelDesc(SysPraiseMain.class.getName(), ResourceUtil.getString("sys-praise:module.sys.praise")); 
		}
		TimeCounter.logCurrentTime("Action-checkPraisedByIds", false,
				getClass());
		if (messages.hasError()) {
			response.sendError(505);
			out.println(messages);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 检测当前人员是否已赞或者踩过文档（多个）
	 * 
	 */
	public ActionForward checkPraiseAndNegativeByIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-checkPraisedByIds", true,
				getClass());
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelIds = request.getParameter("fdModelIds");
			String fdModelName = request.getParameter("fdModelName");
			JSONArray praiseArray = new JSONArray();
			JSONArray negativeArray = new JSONArray();

			List<SysPraiseMain> infoList = getServiceImp(request)
					.checkPraiseAndNegativeByIds(UserUtil.getUser().getFdId(),
							fdModelIds, fdModelName);

			// 记录日志
			// UserOperHelper.logFindAll(infoList,
			// getServiceImp(request).getModelName());

			JSONObject json = new JSONObject();

			for (SysPraiseMain sysPraiseMain : infoList) {
				if (StringUtil.isNotNull(sysPraiseMain.getFdType())
						&& SysPraiseMain.SYSPRAISEMAIN_NEGATIVE
								.equals(sysPraiseMain.getFdType())) {
					negativeArray.add(sysPraiseMain.getFdModelId());
				} else {
					praiseArray.add(sysPraiseMain.getFdModelId());
				}

			}

			json.element("praiseIds", praiseArray);
			json.element("negativeIds", negativeArray);
			out.println(json.toString());
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}finally {
			UserOperHelper.setModelNameAndModelDesc(SysPraiseMain.class.getName(), ResourceUtil.getString("sys-praise:module.sys.praise"));
		}
		TimeCounter.logCurrentTime("Action-checkPraisedByIds", false,
				getClass());
		if (messages.hasError()) {
			response.sendError(505);
			out.println(messages);
			return null;
		} else {
			return null;
		}
	}

	public ActionForward isPraised(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdModelId = request.getParameter("modelId");
			String fdModelName = request.getParameter("modelName");
			List praiseIds = getServiceImp(request).checkPraisedByIds(
					UserUtil.getUser().getFdId(), fdModelId, fdModelName);

			Boolean isPraised = Boolean.FALSE;

			JSONObject json = new JSONObject();
			if (praiseIds.size() > 0) {
				isPraised = Boolean.TRUE;
				json.element("fdId", praiseIds.get(0).toString());
			}
			request.setAttribute("lui-source",
					json.element("isPraised", isPraised));

		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("lui-source", mapping, form, request,
                    response);
        }

	}
}
