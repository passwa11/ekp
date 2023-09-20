package com.landray.kmss.third.weixin.work.sso;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

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
		String errorMsg = null;
		if (StringUtil.isNull(code)) {
			errorMsg = "缺少code参数";
		} else {
			String userId = getLoginUserId(code);
			if (StringUtil.isNull(userId)) {
				errorMsg = "获取user_id参数失败";
			} else {
				AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
				if (autoLoginHelper.hasLogin()) {
					autoLoginHelper.doLogout(request, response);
				}
				if(userId.contains("/")){
					loginCorpGroupUser(userId,autoLoginHelper,request.getSession());
				}else{
					loginInnerUser(userId,autoLoginHelper,request.getSession());
				}
			}
		}
		if (StringUtil.isNotNull(errorMsg)) {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(errorMsg);
		} else {
			String redirectUrl = WeixinWorkConfig.newInstance().getWxDomain();
			if(StringUtil.isNull(redirectUrl)){
				redirectUrl=ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			}
			response.sendRedirect(redirectUrl);
		}
		return null;
	}

	private void loginInnerUser(String userId,AutoLoginHelper autoLoginHelper, HttpSession session) {
		IWxworkOmsRelationService wxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil
				.getBean("wxworkOmsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("wxworkOmsRelationModel.fdAppPkId = :fdAppPkId");
		hqlInfo.setParameter("fdAppPkId", userId);
		try {
			List<WxworkOmsRelationModel> relations = wxworkOmsRelationService.findList(hqlInfo);
			if (relations.size() > 0) {
				if (relations.size() == 1) {
					UserOperHelper.logFind(relations.get(0));// 记录日志信息
					String ekpid = relations.get(0).getFdEkpId();
					autoLoginHelper.doAutoLogin(ekpid, "id",
							session);
				} else if (relations.size() > 1) {
					ISysOrgElementService elementService = (ISysOrgElementService) SpringBeanUtil
							.getBean("sysOrgElementService");
					SysOrgElement person = null;
					for (WxworkOmsRelationModel model : relations) {
						if (model != null && StringUtil
								.isNotNull(model.getFdEkpId())) {
							person = (SysOrgElement) elementService
									.findByPrimaryKey(
											model.getFdEkpId(), null,
											true);
							if (person.getFdOrgType().intValue() == 8) {
								UserOperHelper.logFind(model);// 记录日志信息
								autoLoginHelper.doAutoLogin(
										model.getFdEkpId(), "id",
										session);
								break;
							}
						}
					}
				}
			} else {
				logger.debug("映射表找不到人员对照信息！userId：" + userId);
				autoLoginHelper.doAutoLogin(userId, session);
			}
		} catch (Exception e) {
			logger.warn("PC端扫码，单点失败！", e);
		}
	}

	/**
	 * 下游企业用户登录
	 * @param userId
	 * @param autoLoginHelper
	 * @param session
	 */
	private void loginCorpGroupUser(String userId,AutoLoginHelper autoLoginHelper, HttpSession session) {
		String[] ss = userId.split("/");
		String corpid = ss[0];
		String userid = ss[1];
		IThirdWeixinCgUserMappService thirdWeixinCgUserMappService = (IThirdWeixinCgUserMappService)SpringBeanUtil.getBean("thirdWeixinCgUserMappService");
		try {
			ThirdWeixinCgUserMapp mapp = thirdWeixinCgUserMappService.findByUserId(corpid, userid);
			if (mapp != null) {
				autoLoginHelper.doAutoLogin(mapp.getFdEkpId(), "id", session);
			}else{
				logger.debug("映射表找不到人员对照信息！userId：" + userId);
			}
		}catch (Exception e){
			logger.warn("PC端扫码，单点失败！", e);
		}
	}

	/**
	 * 获取登陆用户相关信息
	 */
	private String getLoginUserId(String authCode) throws Exception {
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		String agentId = config.getWxSSOAgentId();
		if(StringUtil.isNull(agentId)) {
            agentId = config.getWxAgentid();
        }
		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		String url = WxworkUtils.getWxworkApiUrl() +"/user/getuserinfo?access_token="
				+ wxworkApiService.getAccessTokenByAgentid(agentId);
		url += "&code=" + authCode;
		String result = WxworkHttpClientUtil.httpGet(url, null, String.class);
		try {
			JSONObject resultJson = JSONObject.fromObject(result);
			return resultJson.getString("UserId");
		} catch (Exception e) {
			logger.error("result:" + result);
			logger.error("解析`get_login_info`返回值出错\n" + e);
		}
		return null;
	}

}
