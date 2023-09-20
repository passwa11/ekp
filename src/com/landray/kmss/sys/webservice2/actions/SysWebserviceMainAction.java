package com.landray.kmss.sys.webservice2.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

/**
 * WebService管理 Action
 * 
 * @author Jeff
 */
public class SysWebserviceMainAction extends ExtendAction {
	protected ISysWebserviceMainService sysWebserviceMainService;

	@Override
	protected ISysWebserviceMainService getServiceImp(HttpServletRequest request) {
		if (sysWebserviceMainService == null) {
            sysWebserviceMainService = (ISysWebserviceMainService) getBean("sysWebserviceMainService");
        }
		return sysWebserviceMainService;
	}

	/**
	 * 启动WebService
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-start", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 停止WebService
	 */
	public ActionForward stop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-stop", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 在list列表中批量启动选定的多条WebService
	 */
	public ActionForward startall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-startall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 在list列表中批量停止选定的多条WebService
	 */
	public ActionForward stopall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-stopall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 生成Java客户端源码供用户下载
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-download", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");

			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				String zipFileName = getServiceImp(request).genClient(id,
						SysWsUtil.getUrlPrefix(request));

				SysWsUtil.downloadFile(response, zipFileName);
			}
			return null;
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
	}

	/**
	 * 根据http请求设置执行list操作需要用到的where语句
	 */
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {

		// 按服务状态查询
		String para = request.getParameter("status");
		String m_where = "1=1";
		if (StringUtil.isNotNull(para)) {
			m_where = "sysWebserviceMain.fdServiceStatus=:status";
			hqlInfo.setParameter("status", Integer.parseInt(para));
		}

		// 按启动类型查询
		para = request.getParameter("type");
		if (StringUtil.isNotNull(para)) {
			m_where = m_where + " and sysWebserviceMain.fdStartupType=:type";
			hqlInfo.setParameter("type", para);
		}

		hqlInfo.setWhereBlock(m_where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysWebserviceMain.class);
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String fdId = request.getParameter("fdId");
		SysWebserviceMain model = (SysWebserviceMain) getServiceImp(request)
				.findByPrimaryKey(fdId);
		String urlPattern = getServiceImp(request).getUrlPattern();
		String urlPrefix = SysWsUtil.getUrlPrefix(request);
		String wsdlUrl = new StringBuffer().append(urlPrefix)
				.append(urlPattern).append(model.getFdAddress())
				.append("?wsdl").toString();
		request.setAttribute("wsdlUrl", wsdlUrl);

		super.loadActionForm(mapping, form, request, response);
	}

}
