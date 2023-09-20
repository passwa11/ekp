package com.landray.kmss.sys.evaluation.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.Query;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.edition.interfaces.ISysEditionAutoDeleteModel;
import com.landray.kmss.sys.edition.interfaces.ISysEditionMainModel;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationReplyService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.recycle.util.SysRecycleUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateIntervalShowUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.face.SysFaceConfig;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建日期 2006-九月-01
 * 
 * @author 叶中奇
 */
public class SysEvaluationMainAction extends ExtendAction

{
	protected ISysEvaluationMainService sysEvaluationMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysEvaluationMainService == null) {
            sysEvaluationMainService = (ISysEvaluationMainService) getBean("sysEvaluationMainService");
        }
		return sysEvaluationMainService;
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
			String isDelReply = request.getParameter("isDelReply");
			//删除点评回复
			if(StringUtil.isNotNull(isDelReply) && "true".equals(isDelReply)){
				String replyId = request.getParameter("replyId");//回复信息id
				String evalId = request.getParameter("evalId");//点评信息id 
				String fdModelName = request.getParameter("evalModelName");// 模块名（全文点评/段落点评）
				if (StringUtil.isNull(replyId) || StringUtil.isNull(evalId)) {
					messages.addError(new NoRecordException());
				} else {
					ISysEvaluationReplyService sysEvaluationReplyService = 
							(ISysEvaluationReplyService)SpringBeanUtil.getBean("sysEvaluationReplyService");
					
					//更新子回复fdParentId和fdHierarchyId
					sysEvaluationReplyService.updateSubReply(replyId);
					
					//删除回复记录
					sysEvaluationReplyService.deleteReply(replyId,evalId,fdModelName);
				}
			}else{

				//删除点评
				if (!"GET".equals(request.getMethod())) {
                    throw new UnexpectedRequestException();
                }
				String id = request.getParameter("fdId");
				if (StringUtil.isNull(id)) {
                    messages.addError(new NoRecordException());
                } else {
                    getServiceImp(request).delete(id);
                }
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
			String url = "/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=list";
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
	
	public ActionForward score(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String modelName = request.getParameter("fdModelName");
			String modelId = request.getParameter("fdModelId");
			if (StringUtil.isNotNull(modelName) && StringUtil.isNotNull(modelId)){
				String score = ""+((ISysEvaluationMainService)getServiceImp(request)).score(modelName,modelId);
				request.setAttribute("score",NumberUtil.roundDecimal(score,"#.#"));
			}else{
				messages.addError(new NoRecordException());
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
			return mapping.findForward("score");
		}
	}
	
	
	public ActionForward dataScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			JSONObject json =  new JSONObject();
			String modelName = request.getParameter("fdModelName");
			String modelId = request.getParameter("fdModelId");
			if (StringUtil.isNotNull(modelName) && StringUtil.isNotNull(modelId)){
				ISysEvaluationMainService service = (ISysEvaluationMainService)getServiceImp(request);
				String score = "" + service.score(modelName,modelId);
				JSONArray detail = service.getEvalStarDetail( modelName,modelId);
				String myEvalId = service.getTopEvaluationIdByUserId(modelName, modelId, null);
				json.put("average", NumberUtil.roundDecimal(score,"#.#"));
				json.put("detail", detail);
				if(StringUtil.isNotNull(myEvalId)) {
					json.put("myEvalId", myEvalId);
				}
				request.setAttribute("lui-source", json);
			}else{
				messages.addError(new NoRecordException());
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		}else {
			return mapping.findForward("lui-source");
		}
	}
	
	/**
	 * 根据http请求，返回执行list操作需要用到的where语句。
	 */
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String modelId = request.getParameter("fdModelId");
		String modelName = request.getParameter("fdModelName");
		String whereBlock = hqlInfo.getWhereBlock(); 
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		if (!StringUtil.isNull(modelId) && !StringUtil.isNull(modelName)) {
			whereBlock += " and sysEvaluationMain.fdModelId = :modelId and sysEvaluationMain.fdModelName = :modelName ";
			hqlInfo.setParameter("modelId", modelId);
			hqlInfo.setParameter("modelName", modelName);
		} else {
			throw new KmssException(
					new KmssMessage(
							"sys-evaluation:sysEvaluationMain.error.modelIdAndModelName"));
		}
		
		String type = request.getParameter("type");
		if("top".equals(type)) {
			whereBlock = StringUtil.linkString(whereBlock,  " and ", " fdParentId is null");
		}
		
		String myDoc = request.getParameter("myDoc");
		if("true".equals(myDoc)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", " fdEvaluator.fdId=:userId");
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		} else if("false".equals(myDoc)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", " fdEvaluator.fdId <>:userId");
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		}
		
		
		hqlInfo.setWhereBlock(whereBlock);
	}

	/**
	 * 根据http请求，返回执行list操作需要用到的orderBy语句。
	 * 
	 * @param form
	 * @param request
	 * @param curOrderBy
	 *            默认设置的OrderBy参数
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		if (StringUtil.isNull(curOrderBy)) {
			curOrderBy = "sysEvaluationMain.fdId desc";
		}
		return curOrderBy;
	}

	/**
	 * 文档发布页面的点评编辑页面。<br>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward edit4Doc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request, response);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit4Doc", mapping, form, request,
					response);
		}
	}

	/**
	 * 文档发布页面的点评列表页面。<br>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward list4Doc(ActionMapping mapping, ActionForm form,
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
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
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
			return getActionForward("list4Doc", mapping, form, request,
					response);
		}
	}
	
	/**
	 * 点评总览
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
			Iterator<Entry<String, String[]>> iterator = cv.entrySet().iterator();
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
				whereBlock += " and sysEvaluationMain.fdEvaluationContent like:fdEvaluationContent ";
				fdEvaluationContent = "%"+fdEvaluationContent+"%";
				hqlInfo.setParameter("fdEvaluationContent", fdEvaluationContent);
			}
			//具体模块搜索
			if(StringUtil.isNotNull(fdModelName)){
				whereBlock += " and sysEvaluationMain.fdModelName=:fdModelName ";
				hqlInfo.setParameter("fdModelName", fdModelName);
			}else{
				//全系统搜索
				HQLInfo hql = new HQLInfo();
				hql.setSelectBlock(" DISTINCT sysEvaluationMain.fdModelName ");
				List<String> modelList = getServiceImp(request).findList(hql);
				//所有模块,排除数据库中的数据，在当前环境没有该模块
				List<String> modelNameList = new ArrayList<String>();
				for(String modelName:modelList){
					SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
					if(dict != null){
						modelNameList.add(modelName);
					}
				}
				if(modelNameList.size()>0){
					whereBlock += " and sysEvaluationMain.fdModelName in(:fdModelList) ";
					hqlInfo.setParameter("fdModelList", modelNameList);
				}
			}
			
			if(StringUtil.isNotNull(userId)){
				whereBlock += " and sysEvaluationMain.fdEvaluator.fdId=:fdEvaluatorId ";
				hqlInfo.setParameter("fdEvaluatorId", userId);
			}
			
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setModelName("com.landray.kmss.sys.evaluation.model.SysEvaluationMain");
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
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
	 * 获取全文点评的所有模块名
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
			JSONArray notesModelNames = ((ISysEvaluationMainService)getServiceImp(request))
									.listEvalModels(new RequestContext(request));
			
			JSONObject json = new JSONObject();
			json.element("modelNames", notesModelNames);
			out.println(json.toString(1));  
			
		} catch (Exception e) {
			messages.addError(e);
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
	 * 获取被点评文档名
	 */
	public ActionForward getEvalDocNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getEvalDocNames", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			
			String evalModels = request.getParameter("evalModels");
			if(StringUtil.isNotNull(evalModels)){
				String[] evalDoc = evalModels.split(";");
				JSONArray jsonArr = new JSONArray();
				Boolean allow = UserOperHelper.allowLogOper("getEvalDocNames",
						getServiceImp(request).getModelName());
				for(String evalJson:evalDoc){
					JSONObject jObject = JSONObject.fromObject(evalJson);
					String evalId = (String)jObject.get("evalId");//点评id
					String fdModelId = (String)jObject.get("modelId");//被点评文档id
					String fdModelName = (String)jObject.get("modelName");
					String docSubject = ((ISysEvaluationMainService)getServiceImp(request))
					.getEvalDocSubject(fdModelName,fdModelId);
					JSONObject docSubjectJson = new JSONObject();
					docSubjectJson.accumulate("evalId", evalId);
					docSubjectJson.accumulate("docModelId", fdModelId);
					docSubjectJson.accumulate("docModelName", fdModelName);
					docSubjectJson.accumulate("docSubject", docSubject);
					if (allow) {
						UserOperContentHelper.putFind(fdModelId, docSubject,
								fdModelName);
					}
					jsonArr.add(docSubjectJson);
				}
				JSONObject json = new JSONObject();
				json.put("docSubjects", jsonArr);
				out.println(json.toString(1));  
				UserOperHelper.setOperSuccess(true);
			}
		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		TimeCounter.logCurrentTime("Action-getEvalDocNames", false, getClass());
		if (messages.hasError()) {
			out.println(messages);
			response.sendError(500);
			return null;
		} else {
			return null;
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
			
			String docUrl = ((ISysEvaluationMainService)getServiceImp(request))
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
	/**
	 * 获取所有应用模块
	 */
	public ActionForward getAppModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getAppModules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ISysEvaluationMainService evalService = 
				(ISysEvaluationMainService)getServiceImp(request);
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
	
	
	
	public ActionForward getEvaluationByParentIds(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getEvaluationByParentIds", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String[] fdParentIds = request.getParameterValues("ids");
			HashMap<String, ArrayList<HashMap<String, String>>> map = new HashMap<String, ArrayList<HashMap<String, String>>>();
			if (fdParentIds != null && fdParentIds.length > 0) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("sysEvaluationMain.fdParentId in(:fdParentIds)");
				hqlInfo.setParameter("fdParentIds", ArrayUtil.convertArrayToList(fdParentIds));
				hqlInfo.setOrderBy("sysEvaluationMain.fdEvaluationTime asc");
				@SuppressWarnings("unchecked")
				List<SysEvaluationMain> list = (List<SysEvaluationMain>) this.getServiceImp(request).findList(hqlInfo);

				UserOperHelper.logFindAll(list,
						getServiceImp(request).getModelName());
				
				if (!ArrayUtil.isEmpty(list)) {

					for (SysEvaluationMain main : list) {
						String fdId = main.getFdParentId();
						ArrayList<HashMap<String, String>> arr = map.get(fdId);
						if (arr == null) {
							arr = new ArrayList<HashMap<String, String>>();
							map.put(fdId, arr);
						}
						HashMap<String, String> obj = new HashMap<String, String>();
						obj.put("fdId", main.getFdId());
						String content = main.getFdEvaluationContent();
						String evalCont = StringUtil.XMLEscape(content);
						// 留有div,p和br和空格标签，确保换行空格没问题
						if (StringUtil.isNotNull(evalCont)) {
							evalCont = SysFaceConfig.getUrl(request, evalCont);
						}
						obj.put("fdEvaluationContent", evalCont);
						obj.put("fdEvaluationTime",
								DateUtil.convertDateToString(main.getFdEvaluationTime(), null, null));
						obj.put("fdEvaluationIntervalTime", 
								DateIntervalShowUtil.convertDateToString(main.getFdEvaluationTime(), null));
						arr.add(obj);
					}

				}
			}
			request.setAttribute("lui-source", JSONObject.fromObject(map));

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getEvaluationByParentIds", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	
	/**
	 * 获取全部我的点评
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listMyEva(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listMyEva", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("fdEvaluationTime asc");
			changeFindPageHQLInfo(request, hqlInfo);
			List list = getServiceImp(request).findList(hqlInfo);
			int size = list.size();
			Page page = Page.getEmptyPage();
			page.setRowsize(size);
			page.setTotal(size);
			page.setPageno(1);
			page.setList(list);
			UserOperHelper.logFindAll(list,
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-listMyEva", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listMyEva", mapping, form, request, response);
		}
	}
	
	public ActionForward getAttByIds(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getAttByIds", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String[] fdIds = request.getParameterValues("ids");
			
			JSONObject attObj = ((ISysEvaluationMainService)getServiceImp(request)).getListAtt(fdIds,
					SysEvaluationMain.class.getName());
			
			request.setAttribute("lui-source", attObj);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getAttByIds", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	/**
	 * 获取收藏数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward count(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			JSONObject json = new JSONObject();
			
			String fdModelName = request.getParameter("fdModelName");
			
			if(StringUtil.isNotNull(fdModelName)){
				
				String[] models = null;

				// 针对回收站下知识仓库做的性能优化，表连太多了
				Map<String, Integer> knowledges = new HashMap<>();
				knowledges.put("com.landray.kmss.kms.wiki.model.KmsWikiMain",
						2);
				knowledges.put(
						"com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",
						1);

				Boolean isKnowledge = isKnowledge(fdModelName);
				
				if (isKnowledge) {

					models = knowledges.keySet().toArray(new String[2]);

				} else {
					models = fdModelName.split(";");
				}
				
				HQLInfo hqlInfo = new HQLInfo();

				hqlInfo.setWhereBlock("sysEvaluationMain.fdModelName in (:fdModelName)");
				hqlInfo.setParameter("fdModelName", ArrayUtil.convertArrayToList(models));
				hqlInfo.setSelectBlock("count(distinct sysEvaluationMain.fdModelId)");

				String type = request.getParameter("type");
				// 我点评的
				if ("create".equals(type)) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"sysEvaluationMain.fdEvaluator.fdId=:userId"));
					hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
				}
				
				if (isKnowledge) {
					this.knowledgeHql(hqlInfo, models, knowledges);
				} else {
					// 软删除部署之后计算数量
					String joinBlock = "";
					String whereBlock = "";
					
					for (int i = 0; i < models.length; i++) {
						if (SysRecycleUtil.isEnableSoftDelete(models[i])) {
							String asModelTableName = "modelName_" + i;
							int docDeleteFlag = hqlInfo.getDocDeleteFlag();
							joinBlock += " , " + models[i] + " "
									+ asModelTableName;
							
							String rightStr = " ((sysEvaluationMain.fdModelId = "
									+ asModelTableName + ".fdId)";
							Object mainModel = ClassUtils.forName(models[i])
									.newInstance();
							// 如果部署了版本机制，只计算最新版本
							if (mainModel instanceof ISysEditionMainModel
									|| mainModel instanceof ISysEditionAutoDeleteModel) {
								rightStr += " and (" + asModelTableName
										+ ".docIsNewVersion = :docIsNewVersion)";
								hqlInfo.setParameter("docIsNewVersion", true);
							}
							switch (docDeleteFlag) {
							case 0:
								rightStr += " and (" + asModelTableName
										+ ".docDeleteFlag=0)";
								break;
							case 1:
								rightStr += " and (" + asModelTableName
										+ ".docDeleteFlag =1)";
								break;
							}
							
							rightStr += ")";
							whereBlock = StringUtil.linkString(whereBlock, " or ", rightStr);
						}
					}
					
					if (StringUtil.isNotNull(whereBlock)) {
						hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "(" + whereBlock + ")"));
					}
					
					if (StringUtil.isNotNull(joinBlock)) {
						hqlInfo.setJoinBlock(joinBlock);
					}
				}
				
				List<Long> modelCount = this.getServiceImp(request).findValue(hqlInfo);

				json.element("count", modelCount.get(0));
			}

			request.setAttribute("lui-source", json);
			
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	private Boolean isKnowledge(String fdModelName) {

		return "com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
				.equals(fdModelName);
	}
	
	/**
	 * 针对知识仓库subclass模式进行优化
	 * 
	 * @param hqlInfo
	 * @param models
	 * @param knowledges
	 */
	private void knowledgeHql(HQLInfo hqlInfo, String[] models,
			Map<String, Integer> knowledges) {

		String asModelTableName = "modelName_knowledge";

		hqlInfo.setWhereBlock(
				StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
						" (sysEvaluationMain.fdModelId = " + asModelTableName
								+ ".fdId) and (" + asModelTableName
								+ ".docIsNewVersion = :docIsNewVersion)"));
		hqlInfo.setParameter("docIsNewVersion", true);
		hqlInfo.setJoinBlock(
				" , com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc "
						+ asModelTableName);

		String whereBlock = "";

		for (int i = 0; i < models.length; i++) {
			if (SysRecycleUtil.isEnableSoftDelete(models[i])) {

				int docDeleteFlag = hqlInfo.getDocDeleteFlag();

				String rightBlock = "(";

				switch (docDeleteFlag) {
					case 0 :
						rightBlock += asModelTableName
								+ ".docDeleteFlag=0 and ";
						break;
					case 1 :
						rightBlock += asModelTableName
								+ ".docDeleteFlag=1 and ";
						break;
				}

				rightBlock += (asModelTableName
						+ ".fdKnowledgeType=:fdKnowledgeType_" + i);

				rightBlock += ")";
				hqlInfo.setParameter("fdKnowledgeType_" + i,
						knowledges.get(models[i]));

				whereBlock = StringUtil.linkString(whereBlock, " or ",
						rightBlock);

			}
		}
		
		if (StringUtil.isNotNull(whereBlock)) {
			whereBlock = "(" + whereBlock + ")";
		}

		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
				" and ", whereBlock));

	}
	
	
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		json.put("status", true);
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((IExtendForm) form).setFdId(IDGenerator.generateID());
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			String newId = IDGenerator.generateID();
			json.put("newId", newId); 
		} catch (KmssRuntimeException e) {
			List<KmssMessage> msgList = e.getKmssMessages().getMessages();
			json.put("msg", ResourceUtil.getString(msgList.get(0).getMessageKey(), null, request.getLocale(),
					msgList.get(0).getParameter()));
			json.put("status", false);
		}catch (Exception e) {
			messages.addError(e);
		}
		request.setAttribute("lui-source", json);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	/**
	 * 获取我的点评
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyEvaluations(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			//whereBlock = StringUtil.linkString(whereBlock,  " and ", " fdParentId is not null or (fdParentId is null and fdEvaluationScore is not null)");
			whereBlock = StringUtil.linkString(whereBlock, " and ", " fdEvaluator.fdId=:userId");
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		
			Page page = Page.getEmptyPage();
			page.setPageno(hqlInfo.getPageNo());
			page.setRowsize(hqlInfo.getRowSize());
			HQLWrapper hqlWrap = null;
			StringBuffer hql = new StringBuffer();
			hql.append(
					" select sysEvaluationMain.fdModelId,sysEvaluationMain.fdModelName");
			hql.append(
					" from com.landray.kmss.sys.evaluation.model.SysEvaluationMain sysEvaluationMain where ");
			hql.append(whereBlock);
			hql.append(
					" group by sysEvaluationMain.fdModelId,sysEvaluationMain.fdModelName");
			hql.append(" order by MAX(sysEvaluationMain.fdEvaluationTime) desc ");
			hqlWrap = new HQLWrapper(hql.toString(),
					hqlInfo.getParameterList());
			Query q = getServiceImp(request).getBaseDao()
					.getHibernateSession().createQuery(hqlWrap.getHql());
			HQLUtil.setParameters(q, hqlWrap.getParameterList());
			page.setTotalrows(q.list().size());
			page.excecute();
			q.setFirstResult(page.getStart());
			q.setMaxResults(page.getRowsize());
			List<Object[]> list = q.list();
			List<Map<String,Object>> datas = handleEvaluationListByFdModelIdAndFdModelName(request, list);
						
			JSONObject resultJson = new JSONObject();
			resultJson.put("datas", datas);
			JSONObject pageJson = new JSONObject();
			pageJson.put("currentPage", page.getPageno());
			pageJson.put("pageSize", page.getRowsize());
			pageJson.put("totalSize", page.getTotalrows());	
			resultJson.put("page", pageJson);
			request.setAttribute("lui-source", resultJson);
			
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
	
	/**
	 * 根据modelId和modelName处理评论数据
	 * @param request
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<Map<String,Object>> handleEvaluationListByFdModelIdAndFdModelName(HttpServletRequest request, List<Object[]> list) throws Exception {
		List<Map<String,Object>> results = new ArrayList<>();
		
		for(Object[] object : list) {			
			String fdModelId = (String)object[0];
			String fdModelName = (String)object[1];
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "fdModelId=:fdModelId and fdModelName=:fdModelName and fdEvaluator.fdId=:userId";
			hqlInfo.setParameter("fdModelId", fdModelId);
			hqlInfo.setParameter("fdModelName", fdModelName);
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("fdEvaluationTime");
			//根据fdModelId和fdModelName获取我的评论数据
			List<SysEvaluationMain> items = getServiceImp(request).findList(hqlInfo);
			SysEvaluationMain item = items.get(0);	
			//点评分数
			Long score = item.getFdEvaluationScore();			
			Map<String,Object> result = new HashMap<>();
			result.put("fdId", item.getFdId());			
			//点评列表数据
			List<Map<String,Object>> evalList = new ArrayList<>();
			for(int i=0; i < items.size(); i++) {				
				SysEvaluationMain temp = items.get(i);
				if(temp.getFdEvaluationScore() != null) {
					score = temp.getFdEvaluationScore();
				}
				Map<String,Object> evalObj = getEvalObj(request,temp);
				//第一条数据为评论，其余为追加评论（删除评论会将追加评论数据一起删除）
				if(i > 0) {
					evalObj.put("isAddition", true);
				}
				evalList.add(evalObj);
			}
			result.put("evalList", evalList);					
			result.put("fdModelDocSubject", getMainModelSubject(fdModelId,fdModelName));
			result.put("fdModelModuleName", getMainModelModuleName(request, fdModelName));			 
			result.put("fdModelId", fdModelId);
			result.put("fdModelName", fdModelName);
			result.put("fdEvaluationScore", score);			
			results.add(result);
		}
		return results;
	}
	
	/**
	 * 获取主model模块名
	 * @param request
	 * @param fdModelName
	 * @return
	 */
	private String getMainModelModuleName(HttpServletRequest request, String fdModelName) {
		SysDictModel docModel = SysDataDict.getInstance().getModel(fdModelName);
		String titleName = "";
		if(docModel != null){
			titleName = ResourceUtil.getString(docModel.getMessageKey(),
					request.getLocale());
		}
		//笔记模块
		if("com.landray.kmss.kms.common.model.KmsCourseNotes".equals(fdModelName)) {
			titleName =  ResourceUtil.getString("mobile.msg.note", "sys-evaluation");
		}
		return titleName;
	}
	
	/**
	 * 获取主model标题
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 */
	private String getMainModelSubject(String fdModelId, String fdModelName) {
		String[] fields = { "fdNotesContent", "fdName", "fdSubject","docSubject" };
		String docSubject = "";
		SysDictModel docModel = SysDataDict.getInstance().getModel(fdModelName);
		if(docModel != null){
			try {
				IBaseService docService = (IBaseService) SpringBeanUtil.getBean(docModel.getServiceBean());			
				IBaseModel baseModel = docService.findByPrimaryKey(fdModelId,null,true);
				if(baseModel!=null){
					Map<String, SysDictCommonProperty> propertyMap = docModel.getPropertyMap();
					for (String field : fields) {
						if (propertyMap.get(field) != null) {
							docSubject = (String) PropertyUtils.getSimpleProperty(baseModel, field);
						}
					} 
				}
			}catch(Exception e) {
				log.error("获取主model标题出错",e);
			}
		}
		return docSubject;
	}	
	
	/**
	 * 获取评论数据
	 * @param request
	 * @param addtion
	 * @return
	 */
	private Map<String,Object> getEvalObj(HttpServletRequest request, SysEvaluationMain eval){
		Map<String,Object> obj = new HashMap<>();
		obj.put("fdId", eval.getFdId());
	
		String content = eval.getFdEvaluationContent();
		String evalCont = StringUtil.XMLEscape(content);
		// 留有div,p和br和空格标签，确保换行空格没问题
		if (StringUtil.isNotNull(evalCont)) {
			evalCont = evalCont.replaceAll("\\[face",
					"<img src='" + request.getContextPath()
							+ "/sys/evaluation/import/resource/images/bq/")
					.replaceAll("]", ".gif' type='face'></img>")
					.replaceAll("\n", "<br>")
					.replaceAll("&amp;nbsp;", " ");
		}
		obj.put("fdEvaluationContent", evalCont);
		obj.put("fdEvaluationTime",
				DateUtil.convertDateToString(eval.getFdEvaluationTime(), null, null));
		obj.put("fdEvaluationIntervalTime", 
				DateIntervalShowUtil.convertDateToString(eval.getFdEvaluationTime(), null));
		return obj;
	}
	
	/**
	 * 删除评论
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteByMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			String isDelReply = request.getParameter("isDelReply");
			//删除点评回复
			if(StringUtil.isNotNull(isDelReply) && "true".equals(isDelReply)){
				String replyId = request.getParameter("replyId");//回复信息id
				String evalId = request.getParameter("evalId");//点评信息id 
				String fdModelName = request.getParameter("evalModelName");// 模块名（全文点评/段落点评）
				if (StringUtil.isNull(replyId) || StringUtil.isNull(evalId)) {
					messages.addError(new NoRecordException());
				} else {
					ISysEvaluationReplyService sysEvaluationReplyService = 
							(ISysEvaluationReplyService)SpringBeanUtil.getBean("sysEvaluationReplyService");
					
					//更新子回复fdParentId和fdHierarchyId
					sysEvaluationReplyService.updateSubReply(replyId);
					
					//删除回复记录
					sysEvaluationReplyService.deleteReply(replyId,evalId,fdModelName);
				}
			}else{

				//删除点评
				if (!"GET".equals(request.getMethod())) {
                    throw new UnexpectedRequestException();
                }
				String id = request.getParameter("fdId");
				if (StringUtil.isNull(id)) {
                    messages.addError(new NoRecordException());
                } else {
                    getServiceImp(request).delete(id);
                }
			}			
			result.put("success", true);
		} catch (Exception e) {
			messages.addError(e);
		}
		request.setAttribute("lui-source", result);
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
}
