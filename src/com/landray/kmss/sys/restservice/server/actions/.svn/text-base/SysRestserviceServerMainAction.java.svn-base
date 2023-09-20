package com.landray.kmss.sys.restservice.server.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.restservice.server.constant.SysRsConstant;
import com.landray.kmss.sys.restservice.server.forms.SysRestserviceServerMainForm;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * RestService管理 Action
 * 
 * @author  
 */
public class SysRestserviceServerMainAction extends ExtendAction {
	protected ISysRestserviceServerMainService sysRestserviceServerMainService;

	@Override
	protected ISysRestserviceServerMainService getServiceImp(HttpServletRequest request) {
		if (sysRestserviceServerMainService == null) {
			sysRestserviceServerMainService = (ISysRestserviceServerMainService) getBean("sysRestserviceServerMainService");
		}
		return sysRestserviceServerMainService;
	}

	/**
	 * 启动RestService
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-start", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).startService(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-start", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 停止RestService
	 */
	public ActionForward stop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-stop", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				getServiceImp(request).stopService(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-stop", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 在list列表中批量启动选定的多条RestService
	 */
	public ActionForward startall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-startall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				getServiceImp(request).startService(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-startall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 在list列表中批量停止选定的多条RestService
	 */
	public ActionForward stopall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-stopall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
				getServiceImp(request).stopService(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-stopall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 根据http请求设置执行list操作需要用到的where语句
	 */
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {

		// 按服务状态查询
		String para = request.getParameter("status");
		String m_where = "1=1";
		if (StringUtil.isNotNull(para)) {
			m_where = "sysRestserviceServerMain.fdServiceStatus=:status";
			hqlInfo.setParameter("status", Integer.parseInt(para));
		}

		// 按启动类型查询
		para = request.getParameter("type");
		if (StringUtil.isNotNull(para)) {
			m_where = m_where + " and sysRestserviceServerMain.fdStartupType=:type";
			hqlInfo.setParameter("type", para);
		}

		hqlInfo.setWhereBlock(m_where);
		HQLHelper helper = HQLHelper.by(request);
		helper.buildHQLInfo(hqlInfo, SysRestserviceServerMain.class);
		//下面为旧的使用方法
//		CriteriaValue cv = new CriteriaValue(request);
//		CriteriaUtil.buildHql(cv, hqlInfo, SysRestserviceServerMain.class);
	}
	
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(form instanceof SysRestserviceServerMainForm){
			SysRestserviceServerMainForm main = (SysRestserviceServerMainForm) form;
			//禁用时自动停止服务
			if(SysRsConstant.STARTUP_TYPE_DISABLE.equals(main.getFdStartupType())){
				main.setFdServiceStatus(String.valueOf(SysRsConstant.SERVICE_STATUS_STOP));
			}
			//改为自动时开启服务
			if(SysRsConstant.STARTUP_TYPE_AUTO.equals(main.getFdStartupType())){
				main.setFdServiceStatus(String.valueOf(SysRsConstant.SERVICE_STATUS_START));
			}
		}
		return super.update(mapping, form, request, response);
	}
}
