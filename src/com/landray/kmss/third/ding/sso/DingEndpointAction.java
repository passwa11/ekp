package com.landray.kmss.third.ding.sso;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.component.locker.interfaces.ComponentLockerInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.model.ThirdDingCallbackLog;
import com.landray.kmss.third.ding.oms.*;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupMappService;
import com.landray.kmss.third.ding.service.*;
import com.landray.kmss.third.ding.service.spring.SynDingDirService;
import com.landray.kmss.third.ding.service.spring.ThirdDingFlowEventHandlerService;
import com.landray.kmss.third.ding.service.spring.ThirdDingLBPMEventHandlerService;
import com.landray.kmss.third.ding.util.DingTalkEncryptor;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.HttpClientUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.redis.RedisTemplateUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DingEndpointAction extends BaseAction implements ApplicationListener, InitializingBean {
	
	private static Map<String, Long> eventMaps = new ConcurrentHashMap<String, Long>(3000);
	protected SynchroOrgDing2Ekp synchroOrgDing2Ekp;

	public SynchroOrgDing2Ekp getSynchroOrgDing2Ekp() {
		if (synchroOrgDing2Ekp == null) {
			synchroOrgDing2Ekp = (SynchroOrgDing2Ekp) getBean("synchroOrgDing2Ekp");
		}
		return synchroOrgDing2Ekp;
	}

	protected SynchroOrgDingRole2Ekp synchroOrgDingRole2Ekp;
	public SynchroOrgDingRole2Ekp getSynchroOrgDingRole2Ekp() {
		if (synchroOrgDingRole2Ekp == null) {
			synchroOrgDingRole2Ekp = (SynchroOrgDingRole2Ekp) getBean("synchroOrgDingRole2Ekp");
		}
		return synchroOrgDingRole2Ekp;
	}
	
	private ThirdDingLBPMEventHandlerService thirdDingLBPMEventHandlerService;

	public ThirdDingLBPMEventHandlerService getThirdDingLBPMEventHandlerService() {
		if (thirdDingLBPMEventHandlerService == null) {
			thirdDingLBPMEventHandlerService = (ThirdDingLBPMEventHandlerService) getBean("thirdDingLBPMEventHandlerService");
		}
		return thirdDingLBPMEventHandlerService;
	}
	
	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private static Map<String,String> personMaps = new ConcurrentHashMap<String,String>(3000);
	private static Map<String,String> deptMaps = new ConcurrentHashMap<String,String>(1000);
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingEndpointAction.class);

	private IThirdDingCalendarLogService thirdDingCalendarLogService = null;

	public IThirdDingCalendarLogService getThirdDingCalendarLogService() {
		if (thirdDingCalendarLogService == null) {
			thirdDingCalendarLogService = (IThirdDingCalendarLogService) SpringBeanUtil
					.getBean("thirdDingCalendarLogService");
		}
		return thirdDingCalendarLogService;
	}

	private IThirdDingCardMappingService thirdDingCardMappingService = null;

	public IThirdDingCardMappingService getThirdDingCardMappingService() {
		if (thirdDingCardMappingService == null) {
			thirdDingCardMappingService = (IThirdDingCardMappingService) SpringBeanUtil
					.getBean("thirdDingCardMappingService");
		}
		return thirdDingCardMappingService;
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
		DingOmsConfig dingOmsConfig = new DingOmsConfig();
		if ((!"true".equals(DingConfig.newInstance().getDingEnabled())) ||
				((StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection()) && !"2".equals(DingConfig.newInstance().getSyncSelection())) 
				|| !"true".equals(DingConfig.newInstance().getDingOmsInEnabled()))) {
				result = "admin.do未开启钉钉接入,注册钉钉事件回调接口失败.";	
		} else if (StringUtil.isNull(DingConfig.newInstance().getDingToken())
				|| StringUtil.isNull(DingConfig.newInstance().getDingAeskey())) {
			result = "admin.do中钉钉token或aeskey为空,注册钉钉事件回调接口失败..";
		} else if (StringUtil.isNotNull(dingOmsConfig.getHasCheckDing())
				&& "true".equals(dingOmsConfig.getHasCheckDing())) {
			result = "注册钉钉事件回调已开启,不重复开启..";
		}
		if (StringUtil.isNotNull(result)) {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
			return null;
		}
		try {
			DingApiService dingService = DingUtils.getDingApiService();
			String url = DingConstant.DING_PREFIX + "/call_back/register_call_back?access_token=";
			url += dingService.getAccessToken()
					+ DingUtil.getDingAppKeyByEKPUserId("&", null);
			logger.debug("钉钉接口：" + url);
			JSONObject json = new JSONObject();
			String[] cbTag = { "user_add_org", "user_leave_org", "user_modify_org", "org_dept_create",
					"org_dept_modify", "org_dept_remove" };
			json.put("call_back_tag", Arrays.asList(cbTag));
			json.put("token", DingConfig.newInstance().getDingToken());
			json.put("aes_key", DingConfig.newInstance().getDingAeskey());
			json.put("url", DingConfig.newInstance().getDingCallbackurl());
			logger.info("注册的回调事件信息："+json.toString());
			HttpClient httpClient = HttpClientUtil.createClient();
			PostMethod postMethod = HttpClientUtil.createPostMethod(url);
			postMethod.setRequestEntity(new StringRequestEntity(
					json.toString(), "application/json", "utf-8"));
			result = HttpClientUtil.getDataByHttpClient(httpClient,
					postMethod);
			if (UserOperHelper.allowLogOper("check", "*")) {
				UserOperHelper.logMessage(result);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			logger.error("测试回调URL出错:" + e.getMessage());
			logger.error("", e);
		}
		return null;
	}
	
	private IThirdDingOrmTempService thirdDingOrmTempService;

    public IBaseService getThirdDingOrmTempServiceImp() {
        if (thirdDingOrmTempService == null) {
            thirdDingOrmTempService = (IThirdDingOrmTempService) SpringBeanUtil.getBean("thirdDingOrmTempService");
        }
        return thirdDingOrmTempService;
    }
    
    private AutoLoginHelper autoLoginHelper;

	public void setAutoLoginHelper(AutoLoginHelper autoLoginHelper) {
		this.autoLoginHelper = autoLoginHelper;
	}
	
    /**
     * 来自f4的单点登录,返回sessionId
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward f4Sso(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {
    	
    	logger.debug("-----------------------来自callback的f4单点登录-------------------");
    	HttpSession session = request.getSession();
    	/** url中的签名 **/
		String msgSignature = request.getParameter("signature");
		/** url中的时间戳 **/
		String timeStamp = request.getParameter("timestamp");
		/** url中的随机字符串 **/
		String nonce = request.getParameter("nonce");
		
		logger.debug("来自F4的单点登录请求参数->msgSignature:"+msgSignature+",timeStamp:"+timeStamp+",nonce:"+nonce);
		
		/** post数据包数据中的加密数据 **/
		ServletInputStream sis = request.getInputStream();
		Reader isr = new InputStreamReader(sis);
		BufferedReader br = new BufferedReader(isr);
		try{
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonEncrypt = JSONObject.fromObject(sb.toString());
			String encrypt = jsonEncrypt.getString("encrypt");

			/** 对encrypt进行解密 **/
			DingTalkEncryptor dingTalkEncryptor = null;
			String plainText = null;
			
			// 对于DingTalkEncryptor的第三个参数，ISV进行配置的时候传对应套件的SUITE_KEY，普通企业传Corpid
			DingConfig config = DingConfig.newInstance();
			String corpid = config.getDingCorpid();
			String jsonEncrypt_corpId = jsonEncrypt.containsKey("corpId")
					? jsonEncrypt.getString("corpId") : null;
			if (StringUtil.isNotNull(jsonEncrypt_corpId)) {
				corpid = jsonEncrypt_corpId;
			}
			logger.debug("corpid:" + corpid);
			String dev = config.getDevModel();
			if("3".equals(dev)){
				corpid = config.getCustomKey();
			}
			dingTalkEncryptor = new DingTalkEncryptor(config.getDingToken(), config.getDingAeskey(), corpid);
			
			plainText = dingTalkEncryptor.getDecryptMsg(msgSignature,
					timeStamp, nonce, encrypt);
			
			/** 对从encrypt解密出来的明文进行处理 **/
			JSONObject plainTextJson = JSONObject.fromObject(plainText);
			
			String userId = plainTextJson.getString("userId");
			
			logger.debug("来自F4的单点登录请求参数->userId:"+userId);
			
			/** 登录操作 **/
			IOmsRelationService omsRelationService = (IOmsRelationService)SpringBeanUtil.getBean("omsRelationService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"omsRelationModel.fdAppPkId = :fdAppPkId and omsRelationModel.fdType = 8");
			hqlInfo.setParameter("fdAppPkId", userId);

			OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne(hqlInfo);
			
			boolean isLoginSuccess = false;
			
			if(model != null){
				String ekpid = model.getFdEkpId();
				 autoLoginHelper.doAutoLogin(ekpid,"id" ,session);
				 isLoginSuccess = true;
				 logger.debug("F4获取用户名登录EKP成功ID="+ekpid);
			}else{
				autoLoginHelper.doAutoLogin(userId ,session);
				isLoginSuccess = true;
				logger.debug("F4获取用户名登录EKP成功LoginName=" + userId);
			}
			/** 单点登录成功后将sessionId返回 **/
			if(isLoginSuccess){
				logger.debug("单点登录成功返回的sessionId:"+session.getId());
				//** 对返回信息进行加密 **//*
				long timeStampLong = Long.parseLong(timeStamp);
				Map<String, String> jsonMap = null;
				jsonMap = dingTalkEncryptor.getEncryptedMap(session.getId(),timeStampLong, nonce);
				JSONObject json = new JSONObject();
				json.putAll(jsonMap);
				response.getWriter().append(json.toString());
			}
			
		}catch (Exception e) {
			logger.error("f4单点登录出错:",e);
		}finally {
			if (isr != null) {
				isr.close();
			}
		}
    	return null;
    }
	
	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}


	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response,
								   String parameter) throws Exception {
		String keyName = request.getParameter(parameter);
		if (keyName == null || keyName.length() == 0) {
			String method = (String) request.getAttribute("method_0");
			if (com.landray.kmss.sys.authentication.util.StringUtil.isNotNull(method)) {
				return method;
			}
			return "service";
		}
		String methodName = getLookupMapName(request, keyName, mapping);
		return methodName;
	}

	public ActionForward service(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.debug("-----------------------钉钉事件回调-------------------");
		DingConfig config = DingConfig.newInstance();
		if(!"true".equals(config.getDingEnabled())){
			logger.debug("未开启钉钉集成");
			return null;
		}
		/** url中的签名 **/
		String msgSignature = request.getParameter("signature");
		/** url中的时间戳 **/
		String timeStamp = request.getParameter("timestamp");
		/** url中的随机字符串 **/
		String nonce = request.getParameter("nonce");
		Reader isr = null;
		String plainText = null;
		String eventType = null;
		JSONObject plainTextJson = null;
		String dingAppKey = null;
		/** 对encrypt进行解密 **/
		DingTalkEncryptor dingTalkEncryptor = null;

		try {
			/** post数据包数据中的加密数据 **/
			ServletInputStream sis = request.getInputStream();
			isr = new InputStreamReader(sis);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonEncrypt = JSONObject.fromObject(sb.toString());

			//互动卡片的回调（内容没有加密）
			if(jsonEncrypt.containsKey("outTrackId")){
				logger.debug("互动卡片回调："+sb.toString());
				Long signatureTimestamp = Long.parseLong(request.getHeader("x-ddpaas-signature-timestamp"));
				String signature = request.getHeader("x-ddpaas-signature");
				String apiSecrect = new DingOmsConfig().getCallbackApiSecrect();
				if(StringUtil.isNull(apiSecrect)){
					logger.warn("-----apiSecrect为空，回调忽略----");
					return null;
				}
				Long time = System.currentTimeMillis()-signatureTimestamp; //间隔时间
				if(time<0) {
                    time = -time;
                }
				logger.debug("signature:"+signature);
				String _signature = calcSignature(apiSecrect, signatureTimestamp);
				logger.debug("校验signature:"+_signature);
				if(!_signature.equals(signature) && time > 1000*60){
					logger.warn("----回调签名校验不通过，回调将会被忽略----time:{}",time);
					return null;
				}

				boolean isSuccess = false;
				String errorMsg = "";
				if (jsonEncrypt.getString("corpId").equals(config.getDingCorpid())){
					String fdId= DingUtil.getEkpIdByUserid(jsonEncrypt.getString("userId"));
					if(StringUtil.isNotNull(fdId)){
						logger.debug("准备发送回调处理事件，待业务处理");
                         //获取卡片动作内容
						JSONObject cardPrivateData = JSONObject.fromObject(jsonEncrypt.getString("content")).getJSONObject("cardPrivateData");
						logger.debug("cardPrivateData:{}",cardPrivateData);
						getThirdDingCardMappingService().updateDealWithCardCallback(jsonEncrypt.getString("outTrackId"),fdId,cardPrivateData);
						isSuccess=true;
					}else {
						errorMsg = "卡片触发用户不在映射表中";
						logger.warn("----卡片触发用户不在映射表中，回调忽略---");
					}
				}else{
					errorMsg = "CorpId不对应";
					logger.warn("CorpId不对应，卡片回调忽略");
				}
				addCardCallBackLog(jsonEncrypt,isSuccess,errorMsg);
				return null;
			}

			String encrypt = jsonEncrypt.getString("encrypt");

			// 对于DingTalkEncryptor的第三个参数，ISV进行配置的时候传对应套件的SUITE_KEY，普通企业传Corpid
			String corpid = config.getDingCorpid();
			String jsonEncrypt_corpId = jsonEncrypt.containsKey("corpId")
					? jsonEncrypt.getString("corpId") : null;
			if (StringUtil.isNotNull(jsonEncrypt_corpId)) {
				corpid = jsonEncrypt_corpId;
			}
			logger.debug("corpid:" + corpid);

			String dev = config.getDevModel();
			if ("3".equals(dev)) {
				corpid = config.getCustomKey();
			}
			logger.debug("签名校验信息：token=" + config.getDingToken() + ",aes="
					+ config.getDingAeskey() + ",corpid=" + corpid);
			dingTalkEncryptor = new DingTalkEncryptor(config.getDingToken(),
					config.getDingAeskey(), corpid);
			plainText = dingTalkEncryptor.getDecryptMsg(msgSignature,
					timeStamp, nonce, encrypt);

			dingAppKey = dingTalkEncryptor.getCallbackAppKey(encrypt);
			/** 对从encrypt解密出来的明文进行处理 **/
			plainTextJson = JSONObject.fromObject(plainText);
			logger.debug("plainTextJson:" + plainTextJson);
			eventType = plainTextJson.getString("EventType");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (isr != null) {
				isr.close();
			}
		}

		// 群事件回调
		if ("chat_update_title".equals(eventType)
				|| "chat_disband".equals(eventType)) {
			IThirdDingScenegroupMappService thirdDingScenegroupMappService = (IThirdDingScenegroupMappService) SpringBeanUtil
					.getBean("thirdDingScenegroupMappService");
			thirdDingScenegroupMappService.updateByCallback(plainTextJson);
			return null;
		}

		SynchroInModel model = new SynchroInModel();
		try {
			ComponentLockerInfo lockerInfo = getComponentLockService()
					.hasLock(model);
			if (lockerInfo.isLocked()) {
				logger.warn("正在执行从钉钉到ekp的同步，不处理回调");
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Object lock = "lock";
		synchronized (lock) {
			// 钉钉回调，取消发布生态组织事件
			getSysOrgElementService().setEventEco(false);
			try {
				DingApiService dingApiService = DingUtils.getDingApiService();
				JSONObject element = null;
				JSONArray deptIds = null;
				JSONArray userIds = null;
				String errorMsg="";
				boolean calendarLogFlag = true; //判断是否需要记录日程回调日志，过去api调用时产生的回调

				if ("user_add_org".equals(eventType)) {// 创建人员
					userIds = plainTextJson.getJSONArray("UserId");
					for (Object object : userIds) {
						String userid = (String) object;
						try {
							element = dingApiService.userGet(userid, null);
							if (element != null) {
								if (personMaps.containsKey(userid) && "user_add_org".equals(eventType)) {
									logger.debug("钉钉多次回调同一人员(" + userid + ")系统直接跳过");
									continue;
								}
								try {
									getSynchroOrgDing2Ekp().saveOrUpdateCallbackUser(element, true);
									personMaps.put(userid, userid);
								} catch (NullPointerException e) {
									logger.error("钉钉回调-事件类型：user_add_org--" + "userid:" + userid + "----失败");
									errorMsg = "DingEndpointAction.saveOrUpdateCallbackUser()----NullPointerException";
								} catch (Exception e) {
									logger.error("钉钉回调-事件类型：user_add_org--" + "userid:" + userid + "----失败");
									errorMsg = e.getMessage();
								}
							}
						} catch (Exception e) {
							logger.error("钉钉回调-事件类型：user_add_org--" + "userid:" + userid + "----全局锁异常");
						}
					}
				} else if ("user_modify_org".equals(eventType) || "user_dept_change".equals(eventType)
						|| "user_role_change".equals(eventType) || "user_active_org".equals(eventType)) {// 更新人员
					userIds = plainTextJson.getJSONArray("UserId");
					for (Object object : userIds) {
						String userid = (String) object;
						element = dingApiService.userGet(userid, null);
						if (element != null) {
							try {
								if ("user_role_change".equals(eventType)) {
									// F4回调 管理员角色表更
									dealWithF4RoleChange(element);
								}
								getSynchroOrgDing2Ekp().saveOrUpdateCallbackUser(element, false);

							}catch (NullPointerException e) {
								logger.error("钉钉回调-事件类型："+eventType +"--"+"userid:"+element.getString("userid")+"----失败");
								errorMsg = "DingEndpointAction.saveOrUpdateCallbackUser()----NullPointerException";
							} catch (Exception e) {
								logger.error("钉钉回调-事件类型："+eventType +"--"+"userid:"+element.getString("userid")+"----失败");
								errorMsg =e.getMessage();
							}
							
						} else {
							logger.error(
									"钉钉回调根据userid获取人员信息失败：userid -> " + userid);
						}
					}
				} else if ("user_leave_org".equals(eventType)) {// 删除人员
					userIds = plainTextJson.getJSONArray("UserId");
					for (Object object : userIds) {
						String userid = (String) object;
						personMaps.remove(userid);
						try {
							getSynchroOrgDing2Ekp().deleteCallbackUser(userid);
						}catch (NullPointerException e) {
							logger.error("钉钉回调-事件类型：user_leave_org--"+"userid:"+userid+"----失败",e);
							errorMsg = "DingEndpointAction.deleteCallbackUser()----NullPointerException";
						} catch (Exception e) {
							logger.error("钉钉回调-事件类型：user_leave_org--"+"userid:"+userid+"----失败",e);
							errorMsg =e.getMessage();
						}
						
					}
				} else if ("org_dept_create".equals(eventType)) {// 创建部门
					deptIds = plainTextJson.getJSONArray("DeptId");
					for (Object object : deptIds) {
						Long deptId = Long.valueOf(object.toString());
						element = dingApiService.departGet(deptId);
						if (element != null) {
							if(deptMaps.containsKey(deptId+"") && "org_dept_create".equals(eventType)){
								logger.debug("钉钉多次回调同一部门("+deptId+")系统直接跳过");
								continue;
							}
							try {
								getSynchroOrgDing2Ekp().saveOrUpdateCallbackDept(
										element,true);
								deptMaps.put(deptId+"", deptId+"");
							}catch (NullPointerException e) {
								logger.error("钉钉回调-事件类型：org_dept_create--"+"deptId:"+deptId+"----失败");
								errorMsg = "DingEndpointAction.saveOrUpdateCallbackDept()----NullPointerException";
							} catch (Exception e) {
								logger.error("钉钉回调-事件类型：org_dept_create--"+"deptId:"+deptId+"----失败");
								errorMsg =e.getMessage();
							}
							
						}
					}
				} else if ("org_dept_modify".equals(eventType)) {// 更新部门
					deptIds = plainTextJson.getJSONArray("DeptId");
					for (Object object : deptIds) {
						Long deptId = Long.valueOf(object.toString());
						element = dingApiService.departGet(deptId);
						if (element != null) {
							try {
								getSynchroOrgDing2Ekp().saveOrUpdateCallbackDept(
										element,false);
							}catch (NullPointerException e) {
								logger.error("钉钉回调-事件类型：org_dept_modify--"+"deptId:"+ element.getString("id")+"----失败");
								errorMsg = "DingEndpointAction.saveOrUpdateCallbackDept()----NullPointerException";
							} catch (Exception e) {
								logger.error("钉钉回调-事件类型：org_dept_modify--"+"deptId:"+ element.getString("id")+"----失败");
								errorMsg =e.getMessage();
							}
							
						}
					}
				} else if ("org_dept_remove".equals(eventType)) {// 删除人员
					deptIds = plainTextJson.getJSONArray("DeptId");
					for (Object object : deptIds) {
						Long deptId = Long.valueOf(object.toString());
						try {
							getSynchroOrgDing2Ekp().deleteCallbackDept(deptId);
						}catch (NullPointerException e) {
							logger.error("钉钉回调-事件类型：org_dept_remove--"+"deptId:"+ deptId+"---失败");
							errorMsg = "DingEndpointAction.deleteCallbackDept()----NullPointerException";
						} catch (Exception e) {
							logger.error("钉钉回调-事件类型：org_dept_remove--"+"deptId:"+ deptId+"---失败");
							errorMsg =e.getMessage();
						}
						
					}
				} else if ("bpms_instance_change".equals(eventType)) {// 回调函数验证
					
				} else if ("bpms_task_change".equals(eventType)) {// 回调函数验证
					
				} else if ("check_url".equals(eventType)) {// 回调函数验证
					DingOmsConfig dingOmsConfig = new DingOmsConfig();
					dingOmsConfig.setHasCheckDing("true");
					dingOmsConfig.save();
				} else if ("user_active_org".equals(eventType)) {
					logger.debug("用户加入企业回调事件，将建立映射关系");
					userIds = plainTextJson.getJSONArray("UserId");
					logger.debug("刚加入的 userIds：" + userIds);
				} else if ("bpms_dir_insert".equals(eventType)
						|| "bpms_dir_delete".equals(eventType)
						|| "bpms_dir_update".equals(eventType)
						|| "bpms_dir_orders_changed".equals(eventType)) {
					// 钉钉审批分类回调
					logger.debug("钉钉审批分类回调:" + eventType);
					SynDingDirService.newInstance()
							.dingDirCallBack(JSONObject.fromObject(
									plainTextJson.getString("bizData")));

				} else if ("org_admin_add".equals(eventType)) {
					// 钉钉管理员回调
					logger.debug("添加钉钉管理员回调:" + eventType);
					getSynchroOrgDing2Ekp().saveOrUpdateCallbackDingAdmin(
							plainTextJson.getJSONArray("UserId").getString(0),
							true);
				} else if ("org_admin_remove".equals(eventType)) {
					// 钉钉管理员回调
					logger.debug("删除钉钉管理员回调:" + eventType);
					getSynchroOrgDing2Ekp().saveOrUpdateCallbackDingAdmin(
							plainTextJson.getJSONArray("UserId").getString(0),
							false);
				}else if ("label_conf_add".equals(eventType)
						|| "label_conf_modify".equals(eventType)
						|| "label_conf_del".equals(eventType)) {
					// 钉钉审批分类回调
					logger.debug("角色组/角色回调:" + eventType);
					getSynchroOrgDingRole2Ekp()
							.saveOrUpdateRolesCallback(plainTextJson);

				} else if ("label_user_change".equals(eventType)
						|| "label_user_scope_change".equals(eventType)) {
					getSynchroOrgDingRole2Ekp()
							.saveOrUpdateUserRolesCallback(plainTextJson);
				}else if("calendar_event_change".equals(eventType)){

					ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
					String dingScheduleEnabled = "false";
					Map orgMap = null;
					try {
						orgMap = sysAppConfigService
								.findByKey("com.landray.kmss.third.ding.model.DingConfig");
						if (orgMap != null && orgMap.containsKey("dingScheduleEnabled")) {
							String noObject = (String) orgMap.get("dingScheduleEnabled");
							if (noObject != null) {
								dingScheduleEnabled = noObject;
							}
						}
						if("false".equals(dingScheduleEnabled)){
							//日程开关没打开
							logger.debug("钉钉同步日程开关没开启{}", dingScheduleEnabled);
							return null;
						}
					} catch (Exception e) {
						logger.error("钉钉同步开关未找到", e);
					}
					if(!"V3".equals(DingConfig.newInstance().getCalendarApiVersion())){
						calendarLogFlag = false;
						logger.debug("***日程非V3接口的回调不处理***");
					}else{
						boolean flag = RedisTemplateUtil.getInstance().hasKey(plainTextJson.getString("CalendarEventId"));
						if(flag){
							logger.debug("---------存在key---------"+plainTextJson.getString("CalendarEventId"));
							Long tempTime = (Long) RedisTemplateUtil.getInstance().get(plainTextJson.getString("CalendarEventId"));
							logger.debug("原时间为"+tempTime);
							logger.debug("相隔时间为："+(System.currentTimeMillis()-tempTime)+" ms");
							if((System.currentTimeMillis()-tempTime)<4000){
								//小于4s的回调过滤调
								calendarLogFlag = false;
								logger.debug("回调和api的时间间隔不超过4s,回调忽略！");
							}
						}else{
							logger.debug("---------不存在key---------"+plainTextJson.getString("CalendarEventId"));
						}
						if(calendarLogFlag){
							//处理日程回调业务
							getThirdDingCalendarLogService().updateCallbackCalendar(plainTextJson);
						}
					}
				}
				// 对返回信息进行加密
				logger.debug("【回调加密】:"+dingAppKey);
				dingTalkEncryptor = new DingTalkEncryptor(config.getDingToken(),
						config.getDingAeskey(), dingAppKey);
				long timeStampLong = Long.parseLong(timeStamp);
				Map<String, String> jsonMap = null;
				jsonMap = dingTalkEncryptor.getEncryptedMap("success", timeStampLong, nonce);
				JSONObject json = new JSONObject();
				json.putAll(jsonMap);
				response.getWriter().append(json.toString());
				logger.debug("回调事件返回的数据：" + plainTextJson);
				//回调事件先不处理直接添加到回调日志表 状态为false
				if (!"check_url".equals(eventType)) {
					JSONObject plainTextJson2 = JSONObject.fromObject(plainText);
					Long time = 0L;
					plainTextJson2.remove("TimeStamp");
					if (eventMaps.size() >= 3000) {
                        eventMaps.clear();
                    }
					
					if(eventMaps.containsKey(plainTextJson2.toString())){
						time = eventMaps.get(plainTextJson2.toString());
					}
					if (calendarLogFlag&&(!plainTextJson.containsKey("TimeStamp")
							|| time + 4000 < Long.parseLong(
									plainTextJson.getString("TimeStamp")))) { // 4s内
						ThirdDingCallbackLog callbackLog = new ThirdDingCallbackLog();
						callbackLog.setDocContent(plainTextJson.toString());
						callbackLog.setFdEventType(plainTextJson.getString("EventType"));
						callbackLog.setFdEventTypeTip(ThirdDingUtil.geFdEventTypeTipt(plainTextJson.getString("EventType")));
						callbackLog.setFdEventTime(
								plainTextJson.containsKey("TimeStamp")
										? Long.parseLong(plainTextJson
												.getString("TimeStamp"))
										: System.currentTimeMillis());
						if (StringUtil.isNull(errorMsg) || "".equals(errorMsg)) {
							callbackLog.setFdIsSuccess(true);
						}else {
							callbackLog.setFdIsSuccess(false);
							callbackLog.setFdErrorInfo(errorMsg);
						}
						IThirdDingCallbackLogService thirdDingCallbackLogService = (IThirdDingCallbackLogService)SpringBeanUtil.getBean("thirdDingCallbackLogService");
						thirdDingCallbackLogService.add(callbackLog);
						eventMaps.put(plainTextJson2.toString(),
								plainTextJson.containsKey("TimeStamp")
										? Long.parseLong(plainTextJson
												.getString("TimeStamp"))
										: System.currentTimeMillis());
					}else {
						logger.debug("钉钉4秒内重复回调，直接跳过。" + plainTextJson);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				getSysOrgElementService().removeEventEco();
			}
		}
		return null;
	}

	public static String calcSignature(String apiSecret, long ts) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
			mac.init(key);
			return Base64.getEncoder()
					.encodeToString(mac.doFinal(Long.toString(ts).getBytes()));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException("sign api secret failed", e);
		}
	}

	/**
	 * 添加互动卡片回调日志
	 * @param plainTextJson
	 */
	private void addCardCallBackLog(JSONObject plainTextJson,boolean isSuccess,String errorMsg) {
		try {
			ThirdDingCallbackLog callbackLog = new ThirdDingCallbackLog();
			callbackLog.setDocContent(plainTextJson.toString());
			callbackLog.setFdEventType("dingInteractiveCardCallBack");
			callbackLog.setFdEventTypeTip("互动卡片动作触发");
			callbackLog.setFdEventTime(System.currentTimeMillis());
			callbackLog.setFdIsSuccess(isSuccess);
			if(!isSuccess){
				callbackLog.setFdErrorInfo(errorMsg);
			}
			IThirdDingCallbackLogService thirdDingCallbackLogService = (IThirdDingCallbackLogService)SpringBeanUtil.getBean("thirdDingCallbackLogService");
			thirdDingCallbackLogService.add(callbackLog);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	private void dealWithF4RoleChange(JSONObject element) {
		try {
			if (element == null) {
                return;
            }
			logger.debug("element:" + element);
			logger.warn("钉钉管理员变更:" + element.getString("name"));
			if (element.getBoolean("isAdmin")) {
				// 添加管理员
				logger.debug("添加钉钉管理员回调");
				getSynchroOrgDing2Ekp().saveOrUpdateCallbackDingAdmin(
						element.getString("userid"),
						true);
			} else {
				// 删除管理员
				logger.debug("删除钉钉管理员回调");
				getSynchroOrgDing2Ekp().saveOrUpdateCallbackDingAdmin(
						element.getString("userid"),
						false);
			}

		} catch (Exception e) {
			logger.error("处理钉钉角色变更中发生异常：" + e.getMessage(), e);
		}

	}

	private IThirdDingFinstanceService thirdDingFinstanceService;

    public IBaseService getThirdDingFinstanceService() {
        if (thirdDingFinstanceService == null) {
            thirdDingFinstanceService = (IThirdDingFinstanceService) getBean("thirdDingFinstanceService");
        }
        return thirdDingFinstanceService;
    }
    
    private ThirdDingFlowEventHandlerService thirdDingFlowEventHandlerService;

	public ThirdDingFlowEventHandlerService getDingFlowEventHandlerService() {
		if (thirdDingFlowEventHandlerService == null) {
			thirdDingFlowEventHandlerService = (ThirdDingFlowEventHandlerService) getBean("thirdDingFlowEventHandlerService");
        }
		return thirdDingFlowEventHandlerService;
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if ("com.landray.kmss.third.ding.model.DingConfig".equals(event.getSource().toString())) {
			// 回调事件管理
			try {
				DingUtils.getDingApiService().eventManager();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}
	}
	
	
	public ActionForward repeatRegisterBackUrl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			JSONObject jsonObject = new JSONObject();
		response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
		String callBackUrl = request.getParameter("callBackUrl");
		String idDelete = request.getParameter("idDelete");
		String dingDomain = request.getParameter("dingDomain");
		if (StringUtil.isNull(dingDomain)) {
			String domainName = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
			if (domainName.trim().endsWith("/")) {
				domainName = domainName.substring(0,
						domainName.length() - 1);
			}
			callBackUrl = domainName + callBackUrl;
			// dingDomain = domainName;
		}

		logger.debug("callBackUrl:" + callBackUrl + "  idDelete:" + idDelete
				+ " dingDomain:" + dingDomain);
		if (StringUtil.isNotNull(callBackUrl)) {
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallbackurl(callBackUrl);
			dingConfig.setDingDomain(dingDomain);
			dingConfig.save();
		}

		String message = "";
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			if ("true".equals(idDelete)) {
				// 删除并注册
				logger.debug("删除并注册回调：" + callBackUrl);
				if (DingUtils.getDingApiService().delCallBackEvent(token)) {
					message = "处理成功!";
					try {
						DingUtils.getDingApiService().eventManager();
					} catch (Exception e) {
						message = "注册回调过程中发生不可预知的异常!";
					}
					jsonObject.put("success", true);
					jsonObject.put("message", message);
					printWriter.print(jsonObject);
				} else {
					jsonObject.put("success", false);
					jsonObject.put("message", "刪除失败！");
					printWriter.print(jsonObject);
				}
			} else if ("false".equals(idDelete)) {
				// 注册
				logger.debug("注册回调：" + callBackUrl);
				message = "处理成功!";
				try {
					DingUtils.getDingApiService().eventManager();
				} catch (Exception e) {
					message = "注册回调过程中发生不可预知的异常!";
				}
				jsonObject.put("success", true);
				jsonObject.put("message", message);
				printWriter.print(jsonObject);
				}

		} catch (Exception e) {
				jsonObject.put("success", false);
			jsonObject.put("message",
					"操作失败：" + e.getMessage());
				printWriter.print(jsonObject);
				e.printStackTrace();
				logger.error("", e);
			}finally{
				printWriter.close();
			}
		
		
		return null;
		
	}

	public ActionForward deleteCallBack(ActionMapping mapping, ActionForm form,
											   HttpServletRequest request, HttpServletResponse response)throws Exception {

		String token = DingUtils.getDingApiService().getAccessToken();
		JSONObject jsonObject = new JSONObject();
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		if (DingUtils.getDingApiService().delCallBackEvent(token)) {
			jsonObject.put("success", true);
			jsonObject.put("message", "删除成功!");
			printWriter.print(jsonObject);
		} else {
			jsonObject.put("success", false);
			jsonObject.put("message", "刪除失败！");
			printWriter.print(jsonObject);
		}
		return null;
	}
	
	// ========================= 以下代码用于每隔10分钟清除一次缓存 ===========================

	/**
	 * 线程锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();
	private static final Condition working = lock.newCondition();
	
	/**
	 * 最长休眠时间
	 */
	private static final long SLEEP_TIME = 10 * 60 * 1000;

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		taskExecutor.execute(new CleanRunner());
	}

	class CleanRunner implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					// 睡觉
					try {
						lock.lock();
						try {
							working.await(SLEEP_TIME, TimeUnit.MILLISECONDS);
						} finally {
							lock.unlock();
						}
					} catch (Exception e) {
					}
					// 清除缓存
					personMaps.clear();
					deptMaps.clear();
				} catch (Exception e) {
					logger.error("清除缓存异常：", e);
				}
			}
		}
	}
	
}
