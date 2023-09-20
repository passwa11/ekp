package com.landray.kmss.third.weixin.sso;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.spi.model.WxOmsRelationModel;
import com.landray.kmss.third.weixin.spi.service.IWxOmsRelationService;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PC端扫码登陆
 */
public class WxPcScanLoginAction extends BaseAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxPcScanLoginAction.class);

	/**
	 * 微信扫码登陆重定向逻辑
	 */
	public ActionForward service(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String authCode = request.getParameter("auth_code");
		String errorMsg = null;
		if (StringUtil.isNull(authCode)) {
			errorMsg = "缺少auth_code参数";
		} else {
			String userId = getLoginUserId(authCode);
			if (StringUtil.isNull(userId)) {
				errorMsg = "获取user_id参数失败";
			} else {
				AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
				if (autoLoginHelper.hasLogin()) {
					autoLoginHelper.doLogout(request, response);
				}
				IWxOmsRelationService wxOmsRelationService = (IWxOmsRelationService) SpringBeanUtil
						.getBean("wxOmsRelationService");
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("wxkOmsRelationModel.fdAppPkId = :fdAppPkId");
				hqlInfo.setParameter("fdAppPkId", userId);
				try {
					WxOmsRelationModel model = (WxOmsRelationModel) wxOmsRelationService.findFirstOne(hqlInfo);
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
	private String getLoginUserId(String authCode) throws Exception {
		WxApiService wxApiService = WxUtils.getWxApiService();

		try {
			JSONObject resultJson = wxApiService.getLoginInfo(authCode);
			JSONObject userInfo = resultJson.getJSONObject("user_info");
			return userInfo.getString("userid");
		} catch (Exception e) {
			logger.error("解析`get_login_info`返回值出错\n" + e);
		}
		return null;
	}
}
