package com.landray.kmss.third.ding.sso;

import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DingPcScanLoginAction extends BaseAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingPcScanLoginAction.class);

	/**
	 * 钉钉扫码登陆重定向逻辑
	 */
	@SuppressWarnings("unchecked")
	public ActionForward service(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		String errorMsg = null;
		try {
			if (StringUtil.isNotNull(code)) {
				String userid = getDingUserInfo(code);
				if(StringUtil.isNotNull(userid)){
					AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
					IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
							.getBean("omsRelationService");
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock(
							"omsRelationModel.fdAppPkId = :fdAppPkId and omsRelationModel.fdType = 8");
					hqlInfo.setParameter("fdAppPkId", userid);
					try {
						OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne(hqlInfo);
						if (model != null) {
							String ekpid = model.getFdEkpId();
							autoLoginHelper.doAutoLogin(ekpid, "id", request.getSession());
							logger.debug("从钉钉开放授权中获取用户名登录EKP成功[扫码登录]ID="+ekpid);
						} else {
							autoLoginHelper.doAutoLogin(userid, request.getSession());
							logger.debug("从钉钉开放授权中获取用户名登录EKP成功[扫码登录]LoginName=" + userid);
						}
					} catch (Exception e) {
						logger.error("从钉钉开放授权中获取用户名失败！", e);
					}
				}else{
					logger.debug("无法获取userid("+userid+")，无法登录");
				}
				
				/*String accessToken = getAccessToken();
				if (StringUtil.isNull(accessToken)) {
					errorMsg = "accessToken为空";
				} else {
					JSONObject result = getPersistentCode(accessToken, code);
					String snsToken = getSnsToken(accessToken, result.getString("openid"),
							result.getString("persistent_code"));
					JSONObject userInfo = getUserInfo(snsToken);
					userInfo = (userInfo != null && userInfo.has("user_info")) ? userInfo.getJSONObject("user_info")
							: null;
					if (userInfo != null && userInfo.has("unionid")) {
						String userid = getUseridByUnionid(userInfo.getString("unionid"));
						AutoLoginHelper autoLoginHelper = (AutoLoginHelper) SpringBeanUtil.getBean("autoLoginHelper");
						IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
								.getBean("omsRelationService");
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock("omsRelationModel.fdAppPkId = :fdAppPkId");
						hqlInfo.setParameter("fdAppPkId", userid);
						try {
							List<OmsRelationModel> relations = omsRelationService.findList(hqlInfo);
							if (relations.size() > 0) {
								String ekpid = relations.get(0).getFdEkpId();
								autoLoginHelper.doAutoLogin(ekpid, "id", request.getSession());
							} else {
								autoLoginHelper.doAutoLogin(userid, request.getSession());
							}
						} catch (Exception e) {
							logger.warn("从钉钉开放授权中获取用户名失败！", e);
						}
					} else {
						errorMsg = "缺少userInfo";
					}
				}*/
			} else {
				errorMsg = "缺少code参数";
			}
			if (StringUtil.isNotNull(errorMsg)) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(errorMsg);
			} else {
				response.sendRedirect(request.getContextPath() + "/");
			}
		} catch (Exception e) {
			logger.error("", e);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("java程序出错");
		}
		return null;
	}

	private String getDingUserInfo(String code) throws Exception{
		String userid = null;
		logger.debug(
				"钉钉接口：" + DingConstant.DING_PREFIX + "/sns/getuserinfo_bycode");
		ThirdDingTalkClient client = new ThirdDingTalkClient(DingConstant.DING_PREFIX + "/sns/getuserinfo_bycode");
		OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
		req.setTmpAuthCode(code);
		OapiSnsGetuserinfoBycodeResponse res = client.execute(req,DingConfig.newInstance().getDingPcScanappId(),DingConfig.newInstance().getDingPcScanappSecret());
		if(res.getErrcode()==0){
			String unid = res.getUserInfo().getUnionid();
			if(StringUtil.isNotNull(unid)){
				logger.debug("钉钉接口：" + DingConstant.DING_PREFIX
						+ "/user/getUseridByUnionid");
				ThirdDingTalkClient uclient = new ThirdDingTalkClient(DingConstant.DING_PREFIX + "/user/getUseridByUnionid");
				OapiUserGetUseridByUnionidRequest ureq = new OapiUserGetUseridByUnionidRequest();
				ureq.setUnionid(unid);
				ureq.setHttpMethod("GET");
				DingApiService dingService = DingUtils.getDingApiService();
				OapiUserGetUseridByUnionidResponse ures = uclient.execute(ureq, dingService.getAccessToken());
				if(ures.getErrcode()==0){
					userid = ures.getUserid();
				}else{
					logger.error("无法通过unionid("+unid+")获取userid："+ures.getBody());
				}
			}else{
				logger.debug("unionid为空("+unid+"),无法获取用户信息");
			}
		}else{
			logger.debug("无法通过临时码("+code+")无法获取用户的信息："+res.getBody());
		}
		return userid;
	}
	
	// 获取临时token
	private String getAccessToken() {
		String url = DingConstant.DING_PREFIX + "/sns/gettoken?";
		url += "appid=" + DingConfig.newInstance().getDingPcScanappId();
		url += "&appsecret=" + DingConfig.newInstance().getDingPcScanappSecret();
		return DingHttpClientUtil.httpGet(url, "access_token", String.class);
	}

	// 获取持久授权码
	private JSONObject getPersistentCode(String accessToken, String code) throws Exception {
		String url = DingConstant.DING_PREFIX + "/sns/get_persistent_code?access_token=" + accessToken;
		JSONObject param = new JSONObject();
		param.accumulate("tmp_auth_code", code);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}

	// 获取该用户授权的SNS_TOKEN
	private String getSnsToken(String accessToken, String openId, String persistentCode) throws Exception {
		String url = DingConstant.DING_PREFIX + "/sns/get_sns_token?access_token=" + accessToken;
		JSONObject param = new JSONObject();
		param.accumulate("openid", openId);
		param.accumulate("persistent_code", persistentCode);
		return DingHttpClientUtil.httpPost(url, param, "sns_token", String.class);
	}

	// 获取用户授权的个人信息
	private JSONObject getUserInfo(String snsToken) {
		String url = DingConstant.DING_PREFIX + "/sns/getuserinfo?sns_token=" + snsToken;
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	private String getUseridByUnionid(String unionid) throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/getUseridByUnionid?access_token="
				+ DingUtils.getDingApiService().getAccessToken();
		url += "&unionid=" + unionid;
		return DingHttpClientUtil.httpGet(url, "userid", String.class);
	}

}
