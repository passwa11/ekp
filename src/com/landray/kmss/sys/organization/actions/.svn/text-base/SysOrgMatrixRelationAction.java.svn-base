package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixRelationService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 矩阵字段（条件+结果）
 * 
 * @author 潘永辉
 * @dataTime 2021年9月6日 下午2:40:31
 */
public class SysOrgMatrixRelationAction extends ExtendAction {
	private Log logger = LogFactory.getLog(this.getClass());

	private ISysOrgMatrixService sysOrgMatrixService;
	private ISysOrgMatrixRelationService sysOrgMatrixRelationService;

	@Override
	protected ISysOrgMatrixRelationService getServiceImp(HttpServletRequest request) {
		if (sysOrgMatrixRelationService == null) {
			sysOrgMatrixRelationService = (ISysOrgMatrixRelationService) getBean("sysOrgMatrixRelationService");
		}
		return sysOrgMatrixRelationService;
	}

	protected ISysOrgMatrixService getSysOrgMatrixService() {
		if (sysOrgMatrixService == null) {
			sysOrgMatrixService = (ISysOrgMatrixService) getBean("sysOrgMatrixService");
		}
		return sysOrgMatrixService;
	}

	/**
	 * 字段检测
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward check(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-check", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String matrixId = request.getParameter("matrixId");
			if (StringUtil.isNull(matrixId)) {
				throw new UnexpectedRequestException();
			}
			SysOrgMatrix matrix = (SysOrgMatrix) getSysOrgMatrixService().findByPrimaryKey(matrixId);
			request.setAttribute("sysOrgMatrix", matrix);
		} catch (Exception e) {
			messages.addError(e);
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-check", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("check", mapping, form, request, response);
		}
	}

	/**
	 * 字段检测
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void doCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-doCheck", true, getClass());
		response.setCharacterEncoding("UTF-8");
		JSONObject result = new JSONObject();
		try {
			String matrixId = request.getParameter("matrixId");
			if (StringUtil.isNull(matrixId)) {
				throw new UnexpectedRequestException();
			}
			// 检测矩阵字段
			JSONArray data = getServiceImp(request).checkField(matrixId);
			result.put("status", true);
			result.put("data", data);
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", e.getMessage());
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-doCheck", false, getClass());
		response.getWriter().print(result);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 删除字段
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void deleteField(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteField", true, getClass());
		response.setCharacterEncoding("UTF-8");
		JSONObject result = new JSONObject();
		try {
			String matrixId = request.getParameter("matrixId");
			String field = request.getParameter("field");
			if (StringUtil.isNull(matrixId)) {
				throw new UnexpectedRequestException();
			}
			// 检测矩阵字段
			getServiceImp(request).deleteField(matrixId, field);
			result.put("status", true);
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", e.getMessage());
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-deleteField", false, getClass());
		response.getWriter().print(result);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 深度检测数量，可兼容所有数据库
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void depthCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-depthCheck", true, getClass());
		response.setCharacterEncoding("UTF-8");
		JSONObject result = new JSONObject();
		try {
			String matrixId = request.getParameter("matrixId");
			String field = request.getParameter("field");
			if (StringUtil.isNull(matrixId)) {
				throw new UnexpectedRequestException();
			}
			// 检测矩阵字段
			int count = getServiceImp(request).depthCheck(matrixId, field);
			result.put("status", true);
			result.put("count", count);
		} catch (Exception e) {
			result.put("status", false);
			result.put("message", e.getMessage());
			logger.error(e.getMessage(), e);
		}

		TimeCounter.logCurrentTime("Action-depthCheck", false, getClass());
		response.getWriter().print(result);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
