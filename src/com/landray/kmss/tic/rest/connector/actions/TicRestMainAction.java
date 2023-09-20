package com.landray.kmss.tic.rest.connector.actions;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.tic.core.common.actions.TicExtendAction;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.service.ITicCoreTransSettService;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.rest.connector.TicRestConstants;
import com.landray.kmss.tic.rest.connector.Utils.ListToJSONArrayUtil;
import com.landray.kmss.tic.rest.connector.forms.TicRestMainForm;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.tic.rest.connector.service.ITicRestQueryService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;



/**
 * Rest服务请尔方法配置
 * 
 */
public class TicRestMainAction extends TicExtendAction {

	protected ITicRestMainService TicRestMainService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (TicRestMainService == null) {
			TicRestMainService = (ITicRestMainService) getBean("ticRestMainService");
		}
		return TicRestMainService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TicRestMainForm ticRestMainForm = (TicRestMainForm) form;
		ticRestMainForm.reset(mapping, request);
		ticRestMainForm.setDocCreatorId(UserUtil.getKMSSUser().getUserId());
		ticRestMainForm.setDocCreatorName(UserUtil.getKMSSUser().getUserName());
		ticRestMainForm.setDocCreateTime(DateUtil.convertDateToString(new Date(), null, null));

		return ticRestMainForm;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String categoryId = request.getParameter("categoryId");
		String hql = hqlInfo.getWhereBlock();
		if (!StringUtil.isNull(categoryId)) {
			hql = StringUtil.linkString(hql, " and ", "ticRestMain.docCategory.fdHierarchyId like :fdHierarchyId ");
			hqlInfo.setParameter("fdHierarchyId", "%" + categoryId + "%");
		}
		hqlInfo.setWhereBlock(hql);

	}

	/**
	 * 生成新版本 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward newEdition(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String originId = request.getParameter("originId");
			if (StringUtil.isNull(originId)) {
				throw new NoRecordException();
			}
			TicRestMainForm TicRestMainForm = (TicRestMainForm) form;
			TicRestMain TicRestMain = (TicRestMain) getServiceImp(request).findByPrimaryKey(originId);
			TicRestMainForm mainForm = new TicRestMainForm();
			mainForm = (TicRestMainForm) getServiceImp(request).cloneModelToForm(TicRestMainForm, TicRestMain,
					new RequestContext(request));
			mainForm.setMethod("add");
			mainForm.setMethod_GET("add");
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	public ActionForward viewQueryEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-viewQueryEdit", true, getClass());
		KmssMessages messages = new KmssMessages();
		String funcId = request.getParameter("funcId");

		ITicRestMainService TicRestMainService = (ITicRestMainService) SpringBeanUtil.getBean("ticRestMainService");
		TicRestMain ticRestMain = (TicRestMain) TicRestMainService.findByPrimaryKey(funcId);
		request.setAttribute("ticRestMainId", funcId);
		request.setAttribute("ticRestMainName", ticRestMain.getFdName());
		request.setAttribute("reqParam", ticRestMain.getFdReqParam());
		TimeCounter.logCurrentTime("Action-viewQueryEdit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("viewQuery", mapping, form, request,
					response);
		}
	}

	/**
	 * 重写save方法，为了添加返回按钮
	 */
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicRestMainForm ticRestMainForm = (TicRestMainForm) form;
			ticRestMainForm.setFdAppType(request.getParameter("fdAppType"));
			String fdParaIn = ticRestMainForm.getFdParaIn();
			if (StringUtil.isNotNull(fdParaIn)) {
				JSONArray paraIn = JSONArray.parseArray(fdParaIn);
				JSONArray paraIn_result = new JSONArray();
				for (int i = 0; i < paraIn.size(); i++) {
					JSONObject o = paraIn.getJSONObject(i);
					JSONArray children = o.getJSONArray("children");
					if (children != null && children.size() > 0) {
						paraIn_result.add(o);
					}
				}
				ticRestMainForm.setFdParaIn(paraIn_result.toString());
			}
			getServiceImp(request).add(ticRestMainForm, new RequestContext(request));
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				// 生成默认转换配置信息
				((ITicCoreTransSettService) SpringBeanUtil.getBean("ticCoreTransSettService"))
						.addTicCoreTransSett((TicCoreFuncBase) ((ITicRestMainService) getServiceImp(request))
								.findByPrimaryKey(((IExtendForm) form).getFdId()));
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage
					.getInstance(request).addMessages(messages).addButton("button.back",
							"ticRestMain.do?method=edit&fdId=" + ((IExtendForm) form).getFdId(), false)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 实现编辑后还可以返回编辑
	 */
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicRestMainForm ticRestMainForm = (TicRestMainForm) form;
			String fdParaIn = ticRestMainForm.getFdParaIn();
			if (StringUtil.isNotNull(fdParaIn)) {
				JSONArray paraIn = JSONArray.parseArray(fdParaIn);
				JSONArray paraIn_result = new JSONArray();
				for (int i = 0; i < paraIn.size(); i++) {
					JSONObject o = paraIn.getJSONObject(i);
					JSONArray children = o.getJSONArray("children");
					if (children != null && children.size() > 0) {
						paraIn_result.add(o);
					}
				}
				ticRestMainForm.setFdParaIn(paraIn_result.toString());
			}
			getServiceImp(request).update((IExtendForm) form, new RequestContext(request));
			String licenseLding = LicenseUtil.get("license-lding");
			if(!"true".equals(licenseLding)){//蓝钉场景不用生成默认的转换函数
				// 生成默认转换配置信息
				((ITicCoreTransSettService) SpringBeanUtil.getBean("ticCoreTransSettService"))
						.addTicCoreTransSett((TicCoreFuncBase) ((ITicRestMainService) getServiceImp(request))
								.findByPrimaryKey(((IExtendForm) form).getFdId()));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage
					.getInstance(request).addMessages(messages).addButton("button.back",
							"ticRestMain.do?method=edit&fdId=" + ((IExtendForm) form).getFdId(), false)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 主要用于之前保存时可用的webservice服务，当再次编辑时已经不可用了，则给出错误提示
	 */
	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			/*TicRestMainForm mainForm = (TicRestMainForm) form;
			ITicRestSettingService TicRestSettingService = (ITicRestSettingService) SpringBeanUtil
					.getBean("ticRestSettingService");*/
			TicRestMainForm mainForm = (TicRestMainForm) form;
			String fdReqParam = mainForm.getFdReqParam();
			if (StringUtil.isNotNull(fdReqParam)) {
				JSONObject o = JSONObject.parseObject(fdReqParam, Feature.OrderedField);
				JSONArray headers = o.getJSONArray("header");
				if (headers != null) {
					for (int i = 0; i < headers.size(); i++) {
						JSONObject header = headers.getJSONObject(i);
						if (!header.containsKey("value")) {
							continue;
						}
						String value = header.getString("value");
						value = Base64.encodeBase64String(value.getBytes());
						header.put("value", "BASE64ENCODERED:" + value);
					}
				}
				mainForm.setFdReqParam(o.toString());
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
    public ActionForward saveadd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return add(mapping, form, request, response);
		}
	}

	public ActionForward getRestData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getAccessToken", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			TicRestMain tm = (TicRestMain) getServiceImp(request).findByPrimaryKey(request.getParameter("fdId"), null, true);
			String fdInputParams = request.getParameter("fdInputParams");
			// System.out.println("fdInputParams::"+fdInputParams);
			// if (tm.getFdOauthEnable()) {
			// if (tm.getFdUseCustAt() == null || !tm.getFdUseCustAt()) {
			String ret = ((ITicRestMainService) getServiceImp(request)).doTest(fdInputParams, tm);
			JSONObject js = new JSONObject();
			js.put("errcode", "0");
			js.put("result", ret);
			if (StringUtil.isNotNull(ret)) {
				js.put("result_base64",
						new String(Base64.encodeBase64(ret.getBytes("UTF-8")),
								"UTF-8"));
			}
			out.println(js.toString());
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			JSONObject js = new JSONObject();
			js.put("errcode", "1");
			js.put("errmsg", e.getMessage());
			out.println(js.toString());
		}

		TimeCounter.logCurrentTime("Action-getAccessToken", false, getClass());
		return null;
	}


	public ActionForward paseJsonTransParamInJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-paseJsonTransParamInJson", true, getClass());
		String jsonstr =  URLDecoder.decode(request.getParameter("jsonstr"), "UTF-8");
		String integrateType=request.getParameter(TicRestConstants.INTEGRATE_TYPE);
		if(TicRestConstants.EKP_LIST_TYPE.equals(integrateType)){
			jsonstr=ListToJSONArrayUtil.listToJSONArrayList(JSONObject.parseObject(jsonstr, Feature.OrderedField)).toString();
		}

//		BufferedReader br = request.getReader();
//		String str, wholeStr = "";
//		while ((str = br.readLine()) != null) {
//			wholeStr += str;
//		}
//		System.out.println("aaa:" + wholeStr);

		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		com.alibaba.fastjson.JSONObject rtnJo = new com.alibaba.fastjson.JSONObject(true);
		if(jsonstr.startsWith("<")){//解析xml
			try {
				String skipNamespaceStr = request.getParameter("skipNamespace");
				boolean skipNamespace = false;
				if ("true".equals(skipNamespaceStr)) {
					skipNamespace = true;
				}
				String rtn = RecursionUtil.paseXMLTransParamInJson(jsonstr,
						skipNamespace);

				rtnJo.put("result", JSON.parseArray(rtn));
				rtnJo.put("isSuccess", true);
			} catch (Exception e) {
				e.printStackTrace();
				rtnJo.put("isSuccess", false);
				rtnJo.put("errorMsg", "请检查XML格式是否正确!");
			}
		}else{//解析json
			try {
				System.out.println(jsonstr);
				String rtn = RecursionUtil.paseJsonTransParamInJson(jsonstr);
				rtnJo.put("result", JSON.parseArray(rtn));
				rtnJo.put("isSuccess", true);
			} catch (Exception e) {
				e.printStackTrace();
				rtnJo.put("isSuccess", false);
				rtnJo.put("errorMsg", "请检查JSON格式是否正确!");
			}
		}
		out.write(rtnJo.toJSONString());
		TimeCounter.logCurrentTime("Action-getAccessToken", false, getClass());
		return null;
	}

	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			for (String id : ids) {
				boolean noTransSett = ((ITicCoreTransSettService) SpringBeanUtil.getBean("ticCoreTransSettService"))
						.validateTransSett(id);
				boolean noQeury = ((ITicRestQueryService) SpringBeanUtil
						.getBean("ticRestQueryService")).validateQuery(id);
				if (noTransSett && noQeury) {
				} else {
					throw new KmssException(new KmssMessage("tic-core-common:function.delete.fail"));
				}
			}
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids, request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage("sys-authorization:area.batch.operation.info", noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			boolean noTransSett = ((ITicCoreTransSettService) SpringBeanUtil.getBean("ticCoreTransSettService"))
					.validateTransSett(id);
			if (noTransSett) {
			} else {
				throw new KmssException(new KmssMessage("tic-core-common:function.delete.fail"));
			}
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).delete(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
