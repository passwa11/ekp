package com.landray.kmss.third.ding.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.dto.AccessToken;
import com.landray.kmss.third.ding.dto.CorMode;
import com.landray.kmss.third.ding.dto.DingToken;
import com.landray.kmss.util.StringUtil;

public class TokenUtils {
	private static Logger log = org.slf4j.LoggerFactory.getLogger(TokenUtils.class);
	
	public static Map<String, DingToken> tokenMap = new HashMap<String, DingToken>();
	
	public synchronized static String getToken(CorMode corMode) {
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			if(StringUtil.isNotNull(token)) {
                return token;
            }
			token = DingUtils.getDingApiService().getAccessToken(true);
			if(StringUtil.isNotNull(token)) {
                return token;
            }
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}
		String key =corMode.getAgentId();
		DingToken dingToken = tokenMap.get(key);
		
		if (dingToken == null) {
			dingToken = new DingToken();
			Long beginTime = new Date().getTime();
			AccessToken accessToken = TokenUtils.getAccessToken(corMode.getCorpid(),corMode.getCorpsecret());
			if(accessToken!=null){
				dingToken.setFdAgentid(corMode.getAgentId());
				dingToken.setFdBeginTime(beginTime);
				dingToken.setFdExpiresIn(accessToken.getExpiresIn());
				dingToken.setFdToken(accessToken.getToken());
				tokenMap.put(key, dingToken);
			}
		} else {

			// 获取该token时的时间(ms)
			Long beginTime = dingToken.getFdBeginTime();
			// 现在时间(ms)
			Long nowTime = new Date().getTime();

			// 从获取该token时的时间到现在总共过去了多少秒(ms)
			Long timeLag = nowTime - beginTime;

			// 该token最长有效时间(s)
			int expiresTime = dingToken.getFdExpiresIn();

			// 预留200s
			boolean flag = (timeLag / 1000 - 200) > expiresTime;

			// 表示该token已经失效，需要再次获取新的token
			if (flag) {
				Long beginNewTime = new Date().getTime();
				AccessToken accessToken = TokenUtils.getAccessToken(corMode.getCorpid(),corMode.getCorpsecret());
				if(accessToken!=null){
					dingToken.setFdBeginTime(beginNewTime);
					dingToken.setFdExpiresIn(accessToken.getExpiresIn());
					dingToken.setFdToken(accessToken.getToken());
				}
			}
		}
		return dingToken.getFdToken();
	}


	/**
	 * 
	 * @param corpid 企业Id
	 * @param corpsecret  企业应用的凭证密钥
	 * @return
	 */
	private static final AccessToken getAccessToken(String corpid, String corpsecret) {
		AccessToken accessToken = null;
		String access_token_url = "";
		String requestUrl = "";
		
		access_token_url = DingConstant.DING_PREFIX + "/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
		requestUrl = access_token_url.replace("CORPID", corpid).replace(
				"CORPSECRET", corpsecret);

		String tokenId= DingHttpClientUtil.httpGet(requestUrl, "access_token", String.class);
		// 如果请求成功
		if (StringUtils.isNotEmpty(tokenId)) {
				accessToken = new AccessToken();
				accessToken.setToken(tokenId);
				accessToken.setExpiresIn(7200);
				System.out.println("获取token成功:" + accessToken.getToken());
		}else{
			System.out.println("获取token失败 ");
		}
		return accessToken;
	}
}
