package com.landray.kmss.third.weixin.mutil.sso;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PC端扫码登陆
 */
public class WxWorkPcScanLoginAction extends BaseAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxWorkPcScanLoginAction.class);

	/**
	 * 企业微信扫码登陆重定向逻辑
	 */
	@SuppressWarnings("unchecked")
	public ActionForward service(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		String fdwxkey = request.getParameter("fdwxkey");
		String errorMsg = null;
		if (StringUtil.isNull(code)) {
			errorMsg = "缺少code参数";
		} else {
			String userId = getLoginUserId(code, fdwxkey);
			if (StringUtil.isNull(userId)) {
				errorMsg = "获取user_id参数失败";
			} else {
				AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
				if (autoLoginHelper.hasLogin()) {
					autoLoginHelper.doLogout(request, response);
				}
				IWxworkOmsRelationService wxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil
						.getBean("wxworkOmsRelationService");
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("wxworkOmsRelationModel.fdAppPkId = :fdAppPkId");
				hqlInfo.setParameter("fdAppPkId", userId);
				try {
					WxworkOmsRelationMutilModel model = (WxworkOmsRelationMutilModel) wxworkOmsRelationService.findFirstOne(hqlInfo);
					if (model != null) {
						UserOperHelper.logFind(model);// 记录日志信息
						String ekpid = model.getFdEkpId();
						autoLoginHelper.doAutoLogin(ekpid, "id", request.getSession());
					} else {
						autoLoginHelper.doAutoLogin(userId, request.getSession());
					}
				} catch (Exception e) {
					logger.warn("PC端扫码，单点失败！", e);
				}
			}
		}
		if (StringUtil.isNotNull(errorMsg)) {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(errorMsg);
		} else {
			response.sendRedirect(ResourceUtil.getKmssConfigString("kmss.urlPrefix"));
		}
		return null;
	}

	/**
	 * 获取登陆用户相关信息
	 */
	private String getLoginUserId(String authCode, String fdwxkey) throws Exception {
		WeixinMutilConfig config = WeixinMutilConfig.newInstance(fdwxkey);
		String agentId = config.getWxSSOAgentId();
		if(StringUtil.isNull(agentId)) {
            agentId = config.getWxAgentid();
        }
		WxmutilApiService wxmutilApiService = WxmutilUtils
				.getWxmutilApiServiceList().get(fdwxkey);
		String url = WxmutilUtils.getWxworkApiUrl(fdwxkey)
				+ "/user/getuserinfo?access_token="
				+ wxmutilApiService.getAccessTokenByAgentid(agentId);
		url += "&code=" + authCode;
		String result = WxworkHttpClientUtil.httpGet(url, null, String.class);
		try {
			JSONObject resultJson = JSONObject.fromObject(result);
			return resultJson.getString("UserId");
		} catch (Exception e) {
			logger.error("解析`get_login_info`返回值出错\n" + e);
		}
		return null;
	}

}
