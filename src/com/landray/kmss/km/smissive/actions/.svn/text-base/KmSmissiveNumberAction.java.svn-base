package com.landray.kmss.km.smissive.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.smissive.service.IKmSmissiveNumberService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 创建日期 2014-11-20
 * 
 * @author 朱湖强
 */
public class KmSmissiveNumberAction extends ExtendAction {
	protected IKmSmissiveNumberService kmSmissiveNumberService;

	@Override
	protected IKmSmissiveNumberService getServiceImp(HttpServletRequest request) {
		if (kmSmissiveNumberService == null) {
            kmSmissiveNumberService = (IKmSmissiveNumberService) getBean("kmSmissiveNumberService");
        }
		return kmSmissiveNumberService;
	}

	public ActionForward getTempNumFromDb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getTempNumFromDb", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdNumberId = request.getParameter("fdNumberId");
		String docNum = "";
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isNotNull(fdNumberId)) {

				docNum = getServiceImp(request).getTempNumFromDb(fdNumberId);
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			json.put("hasError", "true");
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-getTempNumFromDb", false, getClass());
		if (StringUtil.isNotNull(docNum)) {
			json.put("docNum", docNum);
		}
		response.setHeader("content-type", "application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	public ActionForward delTempNumFromDb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delTempNumFromDb", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdNumberId = request.getParameter("fdNumberId");
		String docBufferNum = request.getParameter("docBufferNum");

		String docNum = "";
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isNotNull(fdNumberId)) {

				getServiceImp(request).deleteTempNumFromDb(fdNumberId, docBufferNum);
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			json.put("hasError", "true");
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-delTempNumFromDb", false, getClass());
		if (StringUtil.isNotNull(docNum)) {
			json.put("docNum", docNum);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;

	}
}
