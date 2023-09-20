package com.landray.kmss.third.ding.oms;

import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.taobao.api.ApiException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DingTokenService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DingTokenService.class);

    private static final int ACCESS_TOKEN_EXPIRESIN = 7200;//
    private static final int JSAPI_TICKIT_EXPIRESIN = 7200;

    /**
     * 全局的是否正在刷新access token的锁
     */
    protected final Object globalAccessTokenRefreshLock = new Object();
    /**
     * 全局的是否正在刷新jsapi ticket
     */
    protected final Object globalJsapiTicketRefreshLock = new Object();

    // private long expiresTime = 0;
    private Map<String, String> accessTokenMap = new ConcurrentHashMap();
    // key:agentId value:过期时间
    private Map<String, Long> expiresTimeMap = new ConcurrentHashMap();

    // private String accessToken = null;
    private long jsapiTicketExpiresTime = 0;
    private String jsapiTicket = null;

    private IThirdDingWorkService thirdDingWorkService;

    public IThirdDingWorkService getThirdDingWorkService() {
        if (thirdDingWorkService == null) {
            return (IThirdDingWorkService) SpringBeanUtil
                    .getBean("thirdDingWorkService");
        }
        return thirdDingWorkService;
    }

    private boolean isAccessTokenExpired(String agentId) {
        if (expiresTimeMap.get(agentId) == null) {
            return true;
        }
        return System.currentTimeMillis() > expiresTimeMap.get(agentId);
    }

    public void expireAccessToken(String agentId) {
        expiresTimeMap.put(agentId, 0L);
    }

    public synchronized void updateAccessToken(String accessToken,
                                               int expiresInSeconds, String agentId) {
        log.debug("agentId:" + agentId);
        // 处理配置的默认应用agentId(旧数据由无->有agentId ： 0 -> agentId)
        // 在map中默认应用agentId和0只能存在一个，避免其中一个token已失效
        String ding_defaultAgentId = DingConfig.newInstance().getDingAgentid();
        if (StringUtil.isNotNull(ding_defaultAgentId)
                && ding_defaultAgentId.equals(agentId)) {
            // 将expiresTimeMap中key为0的移除
            if (expiresTimeMap.get("0") != null) {
                expiresTimeMap.remove("0");
            }
            if (accessTokenMap.get("0") != null) {
                accessTokenMap.remove("0");
            }
        } else if ("0".equals(agentId)
                && StringUtil.isNotNull(ding_defaultAgentId)) {
            if (expiresTimeMap.get(ding_defaultAgentId) != null) {
                expiresTimeMap.remove(ding_defaultAgentId);
            }
            if (accessTokenMap.get(ding_defaultAgentId) != null) {
                accessTokenMap.remove(ding_defaultAgentId);
            }
        }
        if (StringUtil.isNull(accessToken)) {
            log.error(
                    "应用agentId：" + agentId + " 获取token失败，请检查应用key以及secrect");
            if (expiresTimeMap.get(agentId) != null) {
                expiresTimeMap.remove(agentId);
            }
            if (accessTokenMap.get(agentId) != null) {
                accessTokenMap.remove(agentId);
            }
        } else {
            accessTokenMap.put(agentId, accessToken);
            expiresTimeMap.put(agentId, System.currentTimeMillis()
                    + (expiresInSeconds - 200) * 1000L);
        }

    }

    public String getCustomToken(String corpid,String customKey,String customSecret) throws ApiException {
        String token = null;
        String dingUrl = DingConstant.DING_PREFIX + "/service/get_corp_token"
                + DingUtil.getDingAppKeyByEKPUserId("?", null);
        log.debug("钉钉接口：" + dingUrl);
        ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(corpid);
        OapiServiceGetCorpTokenResponse execute = client.execute(req,customKey,customSecret, "ticket");
        token = execute.getAccessToken();
        return token;
    }

    public String getAppToken(String appKey, String secret)
            throws ApiException {
        String url = DingConstant.DING_PREFIX + "/gettoken"
                + DingUtil.getDingAppKeyByEKPUserId("?", null);
        log.debug("钉钉接口：" + url);
        ThirdDingTalkClient client = new ThirdDingTalkClient(url);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(secret);
        request.setTopHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    public String getToken(String agentId) throws ApiException {
        String token = null;
        DingConfig config = DingConfig.newInstance();
        String dev = config.getDevModel();
        if(StringUtil.isNotNull(dev)){
            if("1".equals(dev)){
                token = getAppToken(config.getDingCorpid(), config.getDingCorpSecret());
            }else if("2".equals(dev)){
                log.debug("即将获取agentId：" + agentId + "的token信息");
                String[] keyAndSecret = getAppKeyAndAppSecret(agentId);
                if (keyAndSecret != null && keyAndSecret.length == 2) {
                    token = getAppToken(keyAndSecret[0], keyAndSecret[1]);
                } else {
                    log.debug("获取钉钉应用agentId：" + agentId
                            + "的key和Secret异常->keyAndSecret:"
                            + keyAndSecret);
                }

            }else if("3".equals(dev)){
                token = getCustomToken(config.getDingCorpid(), config.getCustomKey(), config.getCustomSecret());
            }
        }else{
            if(StringUtil.isNotNull(config.getDingCorpSecret())){
                token = getAppToken(config.getDingCorpid(), config.getDingCorpSecret());
            }else if(StringUtil.isNotNull(config.getAppKey())&&StringUtil.isNotNull(config.getAppSecret())){
                token = getAppToken(config.getAppKey(), config.getAppSecret());
            }else if(StringUtil.isNotNull(config.getCustomKey())&&StringUtil.isNotNull(config.getCustomSecret())){
                token = getCustomToken(config.getDingCorpid(), config.getCustomKey(), config.getCustomSecret());
            }
        }
        return token;
    }

    // 根据agentId取获取key以及secrect
    private String[] getAppKeyAndAppSecret(String agentId) {
        // 先去基础配置找agentId，如果匹配不上则到应用里找
        log.debug("getAppKeyAndAppSecret agentId" + agentId);
        if (StringUtil.isNull(agentId)) {
            return null;
        }
        String[] key_secrect = new String[2];
        DingConfig config = DingConfig.newInstance();
        String defaut_agentId = config.getDingAgentid();
        log.debug("agentId:" + agentId + " defaut_agentId:" + defaut_agentId);
        if (agentId.equals(defaut_agentId) || "0".equals(agentId)) {
            key_secrect[0] = config.getAppKey();
            key_secrect[1] = config.getAppSecret();
        } else {
            // 应用
            ThirdDingWork thirdDingWork = getThirdDingWorkByAgentId(agentId);
            if (thirdDingWork == null) {
                return null;
            } else {
                key_secrect[0] = thirdDingWork.getFdAppKey();
                key_secrect[1] = thirdDingWork.getFdSecret();
            }

        }

        return key_secrect;
    }

    private ThirdDingWork getThirdDingWorkByAgentId(String agentId) {

        if (StringUtil.isNull(agentId)) {
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdAgentid = :fdAgentid");
        hqlInfo.setParameter("fdAgentid", agentId);
        try {
            return (ThirdDingWork) getThirdDingWorkService().findFirstOne(hqlInfo);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public String getAccessToken(String agentId) throws Exception {
        String token = getAccessToken(false, agentId);
        if (StringUtil.isNotNull(token)) {
            return getAccessToken(false, agentId);
        } else {
            log.warn("根据agentId:" + agentId
                    + " 无法获取token请检查钉钉配置信息,将获取默认应用token！");
            return getAccessToken(false, null);
        }

    }

    public String getAccessToken(boolean forceRefresh)
            throws Exception {

        // 若forceRefresh为true 把map所有token更新一遍，然后返回默认token
        if (forceRefresh) {
            for (String agentId : accessTokenMap.keySet()) {
                log.debug("agentId:" + agentId);
                getAccessToken(forceRefresh, agentId);
            }
        }

        return getAccessToken(forceRefresh, null);

    }

    public String getAccessToken(boolean forceRefresh, String agentId)
            throws Exception {
        log.debug("getAccessToken agentId:" + agentId);
        // 判断agent是否空或者是0，若是则取基础配置的应用token ，key为0
        if (StringUtil.isNull(agentId)) {
            log.debug("基础配置的应用token");
            String ding_default_agentId = DingConfig.newInstance()
                    .getDingAgentid();
            if (StringUtil.isNotNull(ding_default_agentId)) {
                agentId = ding_default_agentId;
            } else {
                agentId = "0";
            }
        }
        if (forceRefresh || null == accessTokenMap.get(agentId)) {
            expireAccessToken(agentId);
        }
        if (isAccessTokenExpired(agentId)) {
            synchronized (globalAccessTokenRefreshLock) {
                if (isAccessTokenExpired(agentId)) {
                    try {
                        String accessToken = getToken(agentId);
                        updateAccessToken(accessToken, ACCESS_TOKEN_EXPIRESIN,
                                agentId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return accessTokenMap.get(agentId);
    }

    // 根据应用名称取agentId
    protected String getAgentIdByAppName(String appName) {
        if (StringUtil.isNull(appName)) {
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("fdAgentid");
        hqlInfo.setWhereBlock("fdName = :fdName");
        hqlInfo.setParameter("fdName", appName.trim());
        try {
            return (String) getThirdDingWorkService()
                    .findFirstOne(hqlInfo);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private boolean isJsapiTicketExpired() {
        return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
    }

    private void expireJsapiTicket() {
        this.jsapiTicketExpiresTime = 0L;
    }

    public synchronized void updateJsapiTicket(String jsapiTicket,
                                               int expiresInSeconds) {
        this.jsapiTicket = jsapiTicket;
        this.jsapiTicketExpiresTime = System.currentTimeMillis()
                + (expiresInSeconds - 200) * 1000L;
    }

    public String getJsapiTicket() throws Exception {
        return getJsapiTicket(false);
    }

    public String getJsapiTicket(boolean forceRefresh) throws Exception {
        if (forceRefresh) {
            expireJsapiTicket();
        }
        if (isJsapiTicketExpired()) {
            synchronized (globalJsapiTicketRefreshLock) {
                if (isJsapiTicketExpired()) {
                    String url = DingConstant.DING_PREFIX + "/get_jsapi_ticket?"
                            + "type=jsapi" + "&access_token="
                            + getAccessToken()
                            + DingUtil.getDingAppKeyByEKPUserId("&", null);
                    log.debug("get_jsapi_ticket start");
                    JSONObject response = DingHttpClientUtil.httpGet(url, null,
                            JSONObject.class);
                    log.debug("get_jsapi_ticket end");
                    if (response.containsKey("ticket")) {
                        this.jsapiTicket = response.getString("ticket");
                    } else {
                        this.jsapiTicket = response.toString();
                    }
                    updateJsapiTicket(this.jsapiTicket, JSAPI_TICKIT_EXPIRESIN);
                }
            }
        }
        return this.jsapiTicket;
    }

    // 默认应用token
    public String getAccessToken() throws Exception {
        return getAccessToken(null);
    }

    /**
     * 获取机器人token
     * @return
     */
    protected String getRobotAccessToken() throws Exception{

        String robotAgentId = DingConfig.newInstance().getRobotAgentId();
        if(StringUtil.isNull(robotAgentId)){
            throw new RuntimeException("---请在集成里先配置机器人信息---");
        }
        if ( null == accessTokenMap.get(robotAgentId)) {
            expireAccessToken(robotAgentId);
        }
        if (isAccessTokenExpired(robotAgentId)) {
            synchronized (globalAccessTokenRefreshLock) {
                if (isAccessTokenExpired(robotAgentId)) {
                    try {
                        String robotAppKey = DingConfig.newInstance().getRobotAppKey();
                        String robotAppSecret = DingConfig.newInstance().getRobotAppSecret();
                        String accessToken = getAppToken(robotAppKey,robotAppSecret);
                        updateAccessToken(accessToken, ACCESS_TOKEN_EXPIRESIN,
                                robotAgentId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return accessTokenMap.get(robotAgentId);
    }

}
