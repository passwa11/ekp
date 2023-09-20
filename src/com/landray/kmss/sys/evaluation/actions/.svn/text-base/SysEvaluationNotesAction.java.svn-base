package com.landray.kmss.sys.evaluation.actions;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationNotesContent;
import com.landray.kmss.sys.evaluation.model.SysEvaluationNotes;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationNotesService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysEvaluationNotesAction extends ExtendAction {

	protected ISysEvaluationNotesService sysEvaluationNotesService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysEvaluationNotesService == null) {
            sysEvaluationNotesService = (ISysEvaluationNotesService) getBean("sysEvaluationNotesService");
        }
		return sysEvaluationNotesService;
	}

	public ActionForward listView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String modelId = request.getParameter("fdModelId");
			String modelName = request.getParameter("fdModelName");
			if (StringUtil.isNull(modelId) || StringUtil.isNull(modelName)) {
                return null;
            }
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
			String whereBlock = "sysEvaluationNotes.fdModelName=:_fdModelName";
			whereBlock += " and sysEvaluationNotes.fdModelId=:_fdModelId";
			hqlInfo.setParameter("_fdModelId", modelId);
			hqlInfo.setParameter("_fdModelName", modelName);
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listNotes", mapping, form, request,
					response);
		}
	}

	/**
	 * 通过URL的方式直接删除一条记录。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 若执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
			request.setAttribute("isSuccess", "true");
			String url = "/sys/evaluation/sys_evaluation_main/sysEvaluationNotes.do?method=listView";
			String fdModelId = request.getParameter("fdModelId");
			String fdModelName = request.getParameter("fdModelName");
			url += "&fdModelId=" + fdModelId;
			url += "&fdModelName=" + fdModelName;
			if ("yes".equals(request.getParameter("isNews"))) {
				url += "&forward=newsList";
			}
			request.setAttribute("redirectto", url);
			return new ActionForward("/resource/jsp/redirect.jsp");
		}
	}

	/**
	 * 异步获取点评信息
	 */
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String e_id = request.getParameter("e_id");
			if (StringUtil.isNotNull(e_id)) {
				// 用列表方式查找，防止在历史版本中查看已在最新版本中删除的点评
				String whereBlock = " sysEvaluationNotes.fdId=:_fdId";
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setParameter("_fdId", e_id);
				hqlInfo.setWhereBlock(whereBlock);
				Object obj = getServiceImp(request).findFirstOne(hqlInfo);
				if (obj != null) {
					SysEvaluationNotes sysEvaluationNotes = (SysEvaluationNotes)obj;
					request.setAttribute("docSubject", sysEvaluationNotes
							.getDocSubject());
					request.setAttribute("docContent", sysEvaluationNotes
							.getFdEvaluationContent());
					request.setAttribute("fdName", sysEvaluationNotes
							.getFdEvaluator().getFdName());
				} else {
					request.setAttribute("isDelete", true);
				}
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
	 * 异步保存段落点评
	 */
	public ActionForward saveEvalNotes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveEvalNotes", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			
			String fdId = getServiceImp(request).add((IExtendForm) form,
								new RequestContext(request));
			
			JSONObject json = new JSONObject();
			json.element("fdId", fdId);
			out.println(json.toString(1));

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveEvalNotes", false, getClass());
		if (messages.hasError()) {
			out.println(messages);
			response.sendError(500);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 段落点评总览
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward listOverView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listOverView", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String userId = request.getParameter("userId");
			String fdEvaluationContent = "";
			String fdModelName = "";
			CriteriaValue cv = new CriteriaValue(request);
			Iterator<Entry<String, String[]>> iterator = cv.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, String[]> a = iterator.next();
				String key = a.getKey();
				String[] values = a.getValue();
				if ("fdModelName".equalsIgnoreCase(key)) {
					fdModelName = values[0];
				}
				if ("fdEvaluationContent".equalsIgnoreCase(key)) {
					fdEvaluationContent = values[0];
				}
			}

			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 0;
			//SysConfigParameters config = new SysConfigParameters();
			if (StringUtil.isNotNull(SysConfigParameters.getFdRowSize())) {
                rowsize = Integer.parseInt(SysConfigParameters.getFdRowSize());
            }
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

			String whereBlock = "1=1";
			if(StringUtil.isNotNull(fdEvaluationContent)){
				whereBlock += " and sysEvaluationNotes.fdEvaluationContent like:fdEvaluationContent ";
				fdEvaluationContent = "%"+fdEvaluationContent+"%";
				hqlInfo.setParameter("fdEvaluationContent", fdEvaluationContent);
			}
			if (StringUtil.isNotNull(fdModelName)) {
				whereBlock += " and sysEvaluationNotes.fdModelName=:fdModelName ";
				hqlInfo.setParameter("fdModelName", fdModelName);
			}
			if (StringUtil.isNotNull(userId)) {
				whereBlock += " and sysEvaluationNotes.fdEvaluator.fdId=:fdEvaluatorId ";
				hqlInfo.setParameter("fdEvaluatorId", userId);
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("notesPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listOverView", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listOverView", mapping, form, request,
					response);
		}
	}

	/**
	 * 获取段落点评的所有模块名
	 */
	public ActionForward listModelNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listModelNames", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			JSONArray notesModelNames = ((ISysEvaluationNotesService) getServiceImp(request))
					.listEvalNotesModels(new RequestContext(request));

			JSONObject json = new JSONObject();
			json.element("modelNames", notesModelNames);
			out.println(json.toString(1));

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-listModelNames", false, getClass());
		if (messages.hasError()) {
			out.println(messages);
			response.sendError(500);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 更新段落内容
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String docContent = request.getParameter("docContent");
			docContent = URLDecoder.decode(docContent, "utf-8");
			String fdModelName = request.getParameter("fdModelName");
			String fdModelId = request.getParameter("fdModelId");
			
			SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
			IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict
					.getServiceBean());
			IBaseModel baseModel = service.findByPrimaryKey(fdModelId);
			
			JSONObject json = new JSONObject();
			if(baseModel instanceof ISysEvaluationNotesContent){
				
				ISysEvaluationNotesContent model =
						((ISysEvaluationNotesContent) baseModel);
				
				if (UserOperHelper.allowLogOper("updateContent",
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putUpdate(baseModel).putSimple(
							"docContent", model.getDocContent(), docContent);
				}
				
				model.setDocContent(docContent);
				
				service.update(baseModel);
				json.element("flag", true);
			}else{
				json.element("flag", false);
			}
			
			out.println(json.toString(1));
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			out.println(messages);
			response.sendError(500);
			return null;
		} else {
			return null;
		}
	}
	/**
	 * 获取所有应用模块
	 */
	public ActionForward getAppModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getAppModules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ISysEvaluationNotesService evalService = 
				(ISysEvaluationNotesService)SpringBeanUtil.getBean("sysEvaluationNotesService");
			JSONArray modelNameList = evalService.listEvalModels(new RequestContext(request));
			JSONArray array = new JSONArray();
			for(int i=0;i<modelNameList.size();i++){
				JSONObject jObject = (JSONObject)modelNameList.get(i);
				String modelName = jObject.getString("modelName");
				String text = jObject.getString("text");
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("text", text);
				jsonObj.put("value", modelName);
				array.add(jsonObj);
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getAppModules", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	/**
	 * 获取被点评文档url
	 */
	public ActionForward getEvalDocUrl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getEvalDocUrl", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String docModelId = request.getParameter("fdModelId");
			String docModelName = request.getParameter("fdModelName");
			
			String docUrl = ((ISysEvaluationNotesService)getServiceImp(request))
									.getDocUrl(docModelId,docModelName,
											new RequestContext(request));
			JSONObject json = new JSONObject();
			json.element("docUrl", docUrl);
			out.println(json.toString(1));  
			
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getEvalDocUrl", false, getClass());
		if (messages.hasError()) {
			out.println(messages);
			response.sendError(500);
			return null;
		} else {
			return null;
		}
	}

}
