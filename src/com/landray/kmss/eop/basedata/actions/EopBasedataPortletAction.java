package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.eop.basedata.service.spring.EopBasedataPortletService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class EopBasedataPortletAction extends EopBasedataBusinessAction {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EopBasedataPortletService.class);

	private EopBasedataPortletService eopBasedataPortletService;
	
	@Override
    protected EopBasedataPortletService getServiceImp(HttpServletRequest request) {
		if (eopBasedataPortletService == null) {
			eopBasedataPortletService = (EopBasedataPortletService) getBean("eopBasedataPortletService");
		}
		return eopBasedataPortletService;
	}
	
	public ActionForward listApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject obj = getServiceImp(request).listApproval(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			logger.error("财务工作台查询报错："+e);
		}
		return null;
	}
	/**
	 * 根据单号查询对应的数据连接
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getDocData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject obj = getServiceImp(request).getDocData(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(obj.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			logger.error("单据检索报错："+e);
		}
		return null;
	}

}
