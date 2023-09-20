package com.landray.kmss.third.pda.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.third.pda.forms.PdaTabViewConfigMainForm;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewConfig;
import com.landray.kmss.third.pda.model.PdaTabViewConfigMain;
import com.landray.kmss.third.pda.model.PdaTabViewLabelList;
import com.landray.kmss.third.pda.service.IPdaTabViewConfigMainService;
import com.landray.kmss.third.pda.util.PdaPlugin;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class PdaTabViewConfigMainAction extends ExtendAction {

	protected IPdaTabViewConfigMainService pdaTabViewConfigMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (pdaTabViewConfigMainService == null) {
            pdaTabViewConfigMainService = (IPdaTabViewConfigMainService) getBean("pdaTabViewConfigMainService");
        }
		return pdaTabViewConfigMainService;
	}

	/**
	 * 用于异步请求标签信息(通过扩展点方式获取模块功能区标签信息--暂时不用 menglei)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTabJSON(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String model = request.getParameter("fdUrlPrefix");
			if (StringUtil.isNotNull(model)) {
				List<PdaTabViewConfig> list = PdaPlugin.getTabsByModel(model);
				JSONArray jsonArray = new JSONArray();
				for (PdaTabViewConfig pdaTabViewConfig : list) {
					JSONObject jsonObject = new JSONObject();
					String tabName = pdaTabViewConfig.getTabName();
					Integer tabOrder = pdaTabViewConfig.getTabOrder();
					String tabType = pdaTabViewConfig.getTabType();
					String tabBean = pdaTabViewConfig.getTabBean() != null ? ModelUtil
							.getModelTableName(pdaTabViewConfig.getTabBean())
							: "";
					String tabUrl = pdaTabViewConfig.getTabUrl();
					String tabModelName = pdaTabViewConfig.getModelName();
					String tabIcon = pdaTabViewConfig.getTabIcon();
					jsonObject.put("tabName", tabName);
					jsonObject.put("tabOrder", tabOrder);
					jsonObject.put("tabType", tabType);
					jsonObject.put("tabBean", tabBean);
					jsonObject.put("tabUrl", tabUrl);
					jsonObject.put("tabIcon", tabIcon);
					jsonObject.put("tabModelName", tabModelName);
					jsonArray.element(jsonObject);
				}
				if (UserOperHelper.allowLogOper("getTabJSON", null)) {
					UserOperHelper.logMessage(jsonArray.toString());
					UserOperHelper.setOperSuccess(true);
				}
				if (jsonArray != null) {
					out.println(jsonArray.toString(1));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getTabJSON", false, getClass());
		if (messages.hasError()) {
			response.sendError(505);
			out.println(messages);
			return null;
		} else {
			return null;
		}
	}
	
	/**
	 * 用于异步请求标签信息(直接通过数据库查询方式获取模块功能区标签信息 menglei)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getTabJSONFromDB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String fdUrlPrefix = request.getParameter("fdUrlPrefix");
			if (StringUtil.isNotNull(fdUrlPrefix)) {
				HQLInfo hqInfo = new HQLInfo();
				hqInfo.setWhereBlock("pdaTabViewConfigMain.fdModule.fdUrlPrefix='" + fdUrlPrefix+ "'");
				PdaTabViewConfigMain findPdaTabViewConfigMain = (PdaTabViewConfigMain) getServiceImp(request).findFirstOne(hqInfo);
				if (findPdaTabViewConfigMain != null) {
					JSONArray jsonArray = new JSONArray();
					JSONObject jsonObjectCommon = new JSONObject();
					PdaModuleConfigMain fdModule = findPdaTabViewConfigMain.getFdModule();
					String fdModule_fdName = (fdModule!=null)? fdModule.getFdName():"";
					String fdModule_fdId = (fdModule!=null)? fdModule.getFdId():"";
					jsonObjectCommon.put("tabModule_fdName", fdModule_fdName);
					jsonObjectCommon.put("tabModule_fdId", fdModule_fdId);
					jsonArray.element(jsonObjectCommon);
					
					List<PdaTabViewLabelList> fdLabelList = (List<PdaTabViewLabelList>)findPdaTabViewConfigMain.getFdLabelList();
					for (PdaTabViewLabelList objTemp : fdLabelList) {
						JSONObject jsonObject = new JSONObject();
						String tabName = objTemp.getFdTabName();
						Integer tabOrder = objTemp.getFdTabOrder();
						String tabIcon = objTemp.getFdTabIcon();
						String tabUrl = objTemp.getFdTabUrl();
						String tabType = objTemp.getFdTabType();
						String tabBean = (StringUtil.isNotNull(objTemp.getFdTabBean())) ? ModelUtil.getModelTableName(objTemp.getFdTabBean()): "";
						String tabModelName = objTemp.getFdTabModelName();
						jsonObject.put("tabName", tabName);
						jsonObject.put("tabOrder", tabOrder);
						jsonObject.put("tabIcon", tabIcon);
						jsonObject.put("tabUrl", tabUrl);
						jsonObject.put("tabType", tabType);
						jsonObject.put("tabBean", tabBean);
						jsonObject.put("tabModelName", tabModelName);
						jsonArray.element(jsonObject);
					}
					if (UserOperHelper.allowLogOper("getTabJSONFromDB", null)) {
						UserOperHelper.logMessage(jsonArray.toString());
						UserOperHelper.setOperSuccess(true);
					}
					out.println(jsonArray.toString(1));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-getTabJSON", false, getClass());
		if (messages.hasError()) {
			response.sendError(505);
			out.println(messages);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 更新启用状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String status = request.getParameter("fdEnabled");
			if (ids != null && StringUtil.isNotNull(status)) {
				if (UserOperHelper.allowLogOper("updateStatus",
						PdaTabViewConfigMain.class.getName())) {
					for (String id : ids) {
						PdaTabViewConfigMain pdaTabViewConfigMain = (PdaTabViewConfigMain) getServiceImp(
								request).findByPrimaryKey(id);
						UserOperContentHelper
								.putUpdate(id, pdaTabViewConfigMain.getFdName(),
										PdaTabViewConfigMain.class.getName())
								.putSimple("fdStatus",
										pdaTabViewConfigMain.getFdStatus(),
										status);
					}
				}
				IPdaTabViewConfigMainService mainService = (IPdaTabViewConfigMainService) getServiceImp(request);
				mainService.updateStatus(ids, status);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-updateStatus", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
	
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		PdaTabViewConfigMainForm myForm = (PdaTabViewConfigMainForm) form;
		String fdId = myForm.getFdId();
		PdaTabViewConfigMain pdaTabViewConfigMain = (PdaTabViewConfigMain) getServiceImp(request).findByPrimaryKey(fdId);
		if (pdaTabViewConfigMain != null) {
			UserOperHelper.logFind(pdaTabViewConfigMain);
			String fdUrlPrefix = pdaTabViewConfigMain.getFdModule().getFdUrlPrefix();
			request.setAttribute("fdUrlPrefix", fdUrlPrefix);
		}
	}
	
}
