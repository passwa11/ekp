package com.landray.kmss.third.ding.sso;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.component.locker.interfaces.ComponentLockerInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.model.ThirdDingCallbackLog;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.oms.DingOmsConfig;
import com.landray.kmss.third.ding.oms.SynchroInModel;
import com.landray.kmss.third.ding.oms.SynchroOrgDing2Ekp;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingScenegroupMappService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingCallbackLogService;
import com.landray.kmss.third.ding.service.IThirdDingFinstanceService;
import com.landray.kmss.third.ding.service.IThirdDingOrmTempService;
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

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DingAppCallBackAction extends BaseAction implements ApplicationListener, InitializingBean {
	
	private static Map<String, Long> eventMaps = new ConcurrentHashMap<String, Long>(3000);
	protected SynchroOrgDing2Ekp synchroOrgDing2Ekp;

	public SynchroOrgDing2Ekp getSynchroOrgDing2Ekp() {
		if (synchroOrgDing2Ekp == null) {
			synchroOrgDing2Ekp = (SynchroOrgDing2Ekp) getBean("synchroOrgDing2Ekp");
		}
		return synchroOrgDing2Ekp;
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
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingAppCallBackAction.class);

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

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	public ActionForward service(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.warn("-----------------------钉钉应用事件订阅-------------------");
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
		String dingAppKey =null; //钉钉回调来源的应用
		DingConfig config = DingConfig.newInstance();
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
			if(sb==null||StringUtil.isNull(sb.toString())){
				return null;
			}
			JSONObject jsonEncrypt = JSONObject.fromObject(sb.toString());
			String encrypt = jsonEncrypt.getString("encrypt");
			// 对于DingTalkEncryptor的第三个参数，ISV进行配置的时候传对应套件的SUITE_KEY，普通企业传Corpid
			String corpid = config.getDingCorpid();
			String jsonEncrypt_corpId = jsonEncrypt.containsKey("corpId")
					? jsonEncrypt.getString("corpId") : null;
			if (StringUtil.isNotNull(jsonEncrypt_corpId)) {
				corpid = jsonEncrypt_corpId;
			}
			logger.debug("签名校验信息：token=" + config.getDingToken() + ",aes="
					+ config.getDingAeskey() + ",corpid=" + corpid);
			dingTalkEncryptor = new DingTalkEncryptor(config.getDingToken(),
					config.getDingAeskey(), corpid);
			plainText = dingTalkEncryptor.getDecryptMsg(msgSignature,
					timeStamp, nonce, encrypt);

			dingAppKey = dingTalkEncryptor.getCallbackAppKey(encrypt);
			logger.warn("回调应用来源："+dingAppKey);
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
			try {
				DingApiService dingApiService = DingUtils.getDingApiService();
				JSONObject element = null;
				JSONArray deptIds = null;
				JSONArray userIds = null;
				String errorMsg="";
				// 对返回信息进行加密
				long timeStampLong = Long.parseLong(timeStamp);
				Map<String, String> jsonMap = null;
				dingTalkEncryptor = new DingTalkEncryptor(config.getDingToken(),
						config.getDingAeskey(), dingAppKey);
				jsonMap = dingTalkEncryptor.getEncryptedMap("success", timeStampLong, nonce);
				JSONObject json = new JSONObject();
				json.putAll(jsonMap);
				response.getWriter().append(json.toString());
				logger.info("回调事件返回的数据：" + plainTextJson);
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
					if (!plainTextJson.containsKey("TimeStamp")
							|| time + 4000 < Long.parseLong(
									plainTextJson.getString("TimeStamp"))) { // 4s内
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
