package com.landray.kmss.tic.rest.connector.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.tic.rest.client.api.RestApiService;
import com.landray.kmss.tic.rest.client.api.impl.RestApiServiceImpl;
import com.landray.kmss.tic.rest.client.config.RestInMemoryConfigStorage;
import com.landray.kmss.tic.rest.client.http.apache.DefaultApacheHttpClientBuilder;
import com.landray.kmss.tic.rest.connector.model.TicRestPrefixReqSetting;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.tic.rest.connector.service.ITicRestPrefixReqSettingService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * REST服务配置 Action
 */
public class TicRestPrefixReqSettingAction extends ExtendAction {

	protected ITicRestPrefixReqSettingService ticRestPrefixReqSettingService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (ticRestPrefixReqSettingService == null) {
			ticRestPrefixReqSettingService = (ITicRestPrefixReqSettingService) getBean(
					"ticRestPrefixReqSettingService");
		}
		return ticRestPrefixReqSettingService;
	}
	
	public ActionForward getPrefixReq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getPrefixReq", true, getClass());
		KmssMessages messages = new KmssMessages();
		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		RestApiService rest = new RestApiServiceImpl();
		try {
//			TicRestPrefixReqSetting PrefixReqSetting = (TicRestPrefixReqSetting) getServiceImp(
//					request)
//					.findByPrimaryKey(request.getParameter("fdId"));
			TicRestPrefixReqSetting PrefixReqSetting = new TicRestPrefixReqSetting();
			PrefixReqSetting.setDocSubject("");
			PrefixReqSetting.setFdPrefixReqSettingClazz(request.getParameter("fdPrefixReqSettingClazz"));
			PrefixReqSetting.setFdPrefixReqSettingParam(request.getParameter("fdPrefixReqSettingParam"));
			PrefixReqSetting.setFdUseCustCt(Boolean.valueOf(request.getParameter("fdUseCustCt")));

			RestInMemoryConfigStorage configStorage = new RestInMemoryConfigStorage();
			configStorage.setApacheHttpClientBuilder(
					DefaultApacheHttpClientBuilder.getNewInstance());
				rest.initHttp(configStorage);
			JSONObject js = new JSONObject();
			js.accumulate("errcode", "0");
			js.accumulate("prefixReq", rest.getPrefixReqTest(PrefixReqSetting));
			out.println(js.toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			JSONObject js = new JSONObject();
			js.accumulate("errcode", "1");
			js.accumulate("errmsg", e.getMessage());
			out.println(js.toString());
		} finally {
			rest.close();
		}

		TimeCounter.logCurrentTime("Action-getPrefixReq", false, getClass());
		return null;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String fdAppType = request.getParameter("fdAppType");
		String fdEnviromentId = request.getParameter("fdEnviromentId");		
		String hql = hqlInfo.getWhereBlock();
		if (!StringUtil.isNull(fdAppType)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticRestPrefixReqSetting.fdAppType =:fdAppType ");
			hqlInfo.setParameter("fdAppType", fdAppType);
		}
		if (StringUtil.isNotNull(fdEnviromentId)) {
			hql = StringUtil.linkString(hql, " and ",
					"ticRestPrefixReqSetting.fdEnviromentId =:fdEnviromentId ");
			hqlInfo.setParameter("fdEnviromentId", fdEnviromentId);
		}
		hqlInfo.setWhereBlock(hql);
	}

	@Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String id = request.getParameter("fdId");
			boolean noPrefixReqSetting = ((ITicRestMainService) SpringBeanUtil
					.getBean("ticRestMainService"))
							.validateRestPrefixReqSetting(id);
			if (!noPrefixReqSetting) {
				throw new KmssException(new KmssMessage(
						"tic-core-common:function.delete.fail"));
			}
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
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			for (String id : ids) {
				boolean noPrefixReqSetting = ((ITicRestMainService) SpringBeanUtil
						.getBean("ticRestMainService"))
								.validateRestPrefixReqSetting(id);
				if (!noPrefixReqSetting) {
					throw new KmssException(new KmssMessage(
							"tic-core-common:function.delete.fail"));
				}
			}
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}
}
