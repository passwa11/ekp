package com.landray.kmss.third.im.kk.provider;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.im.kk.queue.constant.KkNotifyQueueErrorConstants;
import com.landray.kmss.third.im.kk.queue.model.KkNotifyQueueError;
import com.landray.kmss.third.im.kk.queue.service.IKkNotifyQueueErrorService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.entity.ContentType;

import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyException;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.constant.KkNotifyConstants;
import com.landray.kmss.third.im.kk.model.KkNotifyLog;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkImNotifyService;
import com.landray.kmss.third.im.kk.service.IKkNotifyLogService;
import com.landray.kmss.third.im.kk.util.KKConfigUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.CircuitBreakerConfig;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;

import net.sf.json.JSONObject;

public class KkPostDataRunner {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkPostDataRunner.class);

	private HttpClient httpClient;
	private String url;
	private String requestId;
	private IKkNotifyLogService kkLogService = (IKkNotifyLogService) SpringBeanUtil.getBean("kkNotifyLogService");

	private IKkImNotifyService kkImNotifyService = (IKkImNotifyService) SpringBeanUtil.getBean("kkImNotifyService");

	private IKkImConfigService kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");

	private static ISysOrgCoreService sysOrgCoreService = null;
	private static ISysNotifyMainCoreService sysNotifyMainCoreService;

	public static ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	public KkPostDataRunner(HttpClient httpClient, String url, String requestId) {
		this.httpClient = httpClient;
		this.url = url;
		this.requestId = requestId;
	}
	
	public KkPostDataRunner(HttpClient httpClient, String url) {
		this.httpClient = httpClient;
		this.url = url;
	}

	public static Map<String, Integer> getFailTimes() throws Exception {
		Map<String, Integer> failTimes = new KkNotifyConfig().getFailTimesMap();

		return failTimes;
	}

	public static void resetFailTime(String url) throws Exception {

		KkNotifyConfig config = new KkNotifyConfig();
		config.setFailTimes(url, 0 + "");
		config.save();

	}


	/*private static void sendNotify() throws Exception {
		KkConfig config = new KkConfig();
		String notify_name = config.getValue("kmss.ims.notify.kk.failure.notify");
		if (StringUtil.isNull(notify_name)) {
			return;
		}
		SysOrgPerson person = null;
		try {
			person = getSysOrgCoreService().findByLoginName(notify_name);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		if (person == null) {
			return;
		}
		List<SysOrgElement> elements = new ArrayList<SysOrgElement>();
		elements.add(person);
	
		NotifyContext notifyContext = getSysNotifyMainCoreService().getContext(
				null);
	
		// 获取通知方式
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyTarget(elements);
	
		String subject = "kk待办发送失败次数已经超过限制，请检查ekp服务器域kk服务器之间的连接是否有问题！";
		String content = subject
				+ "问题修复后，在此处重新启用同步：<a target=\"_blank\" href='<c:url value=\"/third/im/kk.index\"/>'>/third/im/kk.index</a>";
		notifyContext.setSubject(subject);
		notifyContext.setContent(content);
		notifyContext.setLink("/third/im/kk.index");
	
		try {
			sysNotifyMainCoreService.sendNotify(null, notifyContext, null);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}*/

	private IKkNotifyQueueErrorService kkNotifyQueueErrorService;

	public IKkNotifyQueueErrorService getKkNotifyQueueErrorService(){
		if(kkNotifyQueueErrorService == null){
			kkNotifyQueueErrorService = (IKkNotifyQueueErrorService) SpringBeanUtil.getBean("kkNotifyQueueErrorService");
		}
		return kkNotifyQueueErrorService;
	}

	public void postKK5JsonData(SysNotifyTodo todo, String title, List<JSONObject> jsonObjects){
		logger.debug("=========KkPostDataRunner.run========");

		// TODO 自动生成的方法存根
		for (JSONObject obj : jsonObjects) {
			KkNotifyLog kkNotifyLog = new KkNotifyLog();
			
			kkNotifyLog.setFdSubject(title);
			
			StringBuffer buf = new StringBuffer();
			buf.append("[subject:" + title + "]\n");

			kkNotifyLog.setSendTime(new Date());
			logger.debug("sendTime 发送时间:" + new Date());
			kkNotifyLog.setKkNotifyData(obj.toString());
			PostMethod post = getPost(url);
			logger.debug("kkNotifyData 发送消息包:" + obj.toString());
			logger.debug("title:" + todo.getFdSubject() + " url:" + url);
			RequestEntity entity;
			try {
				entity = new StringRequestEntity(obj.toString(),
						ContentType.APPLICATION_JSON.toString(), "UTF-8");
				post.setRequestEntity(entity);
				
				int result = executePostBreaker(post);
				
				if (result == 200) {
					logger.debug("返回结果 200，推送成功");
					String resString = new String(post.getResponseBody(),
							"UTF-8");
					logger.debug("resString：" + resString);
					kkNotifyLog.setKkRtnMsg(resString);
				} else {
					logger.debug("推送失败，返回结果：" + result);
					String resString = post.getResponseBodyAsString();
					logger.debug("resString：" + resString);
					kkNotifyLog.setKkRtnMsg(resString);
					buf.append("[result:" + result + "]\n");
					save2ErrorQueue(todo.getFdId(),title,todo.getFdAppName() ,url,obj,"[result:" + result + "]\n");
				}
			} catch (OpenCircuitException e) {
				String ip = getLocalIP();
				logger.error(ip + "处于熔断状态");
				buf.append("[Exception:" + ip + "---" + e.getClass().getName()
						+ "---" + e.getMessage() + "]\n");
				save2ErrorQueue(todo.getFdId(),title ,todo.getFdAppName(),url,obj,"处于熔断状态[Exception:" + ip + "---" + e.getClass().getName()
						+ "---" + e.getMessage() + "]\n");
			} catch (Exception e) {
				logger.error("待办推送失败", e);
				buf.append("[Exception:" + e.getMessage() + "]\n");
				save2ErrorQueue(todo.getFdId(),title,todo.getFdAppName(),url,obj,"[Exception:" + e.getMessage() + "]\n");
			} finally {
				logger.debug(buf.toString());
				post.releaseConnection();
				kkNotifyLog.setFdParams(buf.toString());
				kkNotifyLog.setRtnTime(new Date());
				try {
					logger.debug("=========保存日志==========");
					saveLog(kkNotifyLog);
				} catch (Exception e2) {
					logger.error("=========保存日志失败==========");
					logger.error(e2.toString());
				}

			}
		}
	}
	/*---------------------------------消息重发  start ---------------------------------*/
	private void save2ErrorQueue(String todoId,String title,String fdAppName, String url, JSONObject jsonObjects,String erroMsg) {
		try {
			//新增
			KkNotifyQueueError error = new KkNotifyQueueError(title,fdAppName,url,jsonObjects.toString(),erroMsg,todoId);
			getKkNotifyQueueErrorService().add(error);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	public void resendPostKK5JsonData(String errorId){
		logger.debug("=========KkPostDataRunner  resend ========");
		try {
			KkNotifyQueueError error = (KkNotifyQueueError) getKkNotifyQueueErrorService().findByPrimaryKey(errorId,null,true);
			if(error == null) {
                return;
            }
			JSONObject obj = JSONObject.fromObject(error.getFdJson());
			KkNotifyLog kkNotifyLog = new KkNotifyLog();

			kkNotifyLog.setFdSubject(error.getFdSubject());

			StringBuffer buf = new StringBuffer();
			buf.append("[subject:" + error.getFdSubject() + "]\n");

			kkNotifyLog.setSendTime(new Date());
			logger.debug("sendTime 发送时间:" + new Date());
			kkNotifyLog.setKkNotifyData(obj.toString());
			PostMethod post = getPost(error.getFdUrl());
			logger.debug("kkNotifyData 发送消息包:" + obj.toString());
			logger.debug("title:" + error.getFdSubject() + " url:" + error.getFdUrl());
			RequestEntity entity;
			try {
				entity = new StringRequestEntity(obj.toString(),
						ContentType.APPLICATION_JSON.toString(), "UTF-8");
				post.setRequestEntity(entity);

				int result = executePostBreaker(post);

				if (result == 200) {
					logger.debug("返回结果 200，推送成功");
					String resString = new String(post.getResponseBody(),
							"UTF-8");
					logger.debug("resString：" + resString);
					kkNotifyLog.setKkRtnMsg(resString);
					updateErrorQueue(error,true,"--重发成功--");
				} else {
					logger.debug("推送失败，返回结果：" + result);
					String resString = post.getResponseBodyAsString();
					logger.debug("resString：" + resString);
					kkNotifyLog.setKkRtnMsg(resString);
					buf.append("[result:" + result + "]\n");
					updateErrorQueue(error,false,"[result:" + result + "]\n");
				}
			} catch (OpenCircuitException e) {
				String ip = getLocalIP();
				logger.error(ip + "处于熔断状态");
				String msg = "处于熔断状态[Exception:" + ip + "---" + e.getClass().getName()
						+ "---" + e.getMessage() + "]\n";
				buf.append(msg);
				updateErrorQueue(error,false,msg);
			} catch (Exception e) {
				logger.error("待办推送失败", e);
				buf.append("[Exception:" + e.getMessage() + "]\n");
				updateErrorQueue(error,false,"[Exception:" + e.getMessage() + "]\n");
			} finally {
				logger.debug(buf.toString());
				post.releaseConnection();
				kkNotifyLog.setFdParams(buf.toString());
				kkNotifyLog.setRtnTime(new Date());
				try {
					logger.debug("=========保存日志==========");
					saveLog(kkNotifyLog);
				} catch (Exception e2) {
					logger.error("=========保存日志失败==========");
					logger.error(e2.toString());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}
	private void updateErrorQueue(KkNotifyQueueError error, boolean isSuccess,String msg) {
		try {
			if(isSuccess){
				logger.debug("---重发成功，删除记录---："+error.getFdSubject());
				getKkNotifyQueueErrorService().delete(error);
			}else{
				error.setFdFlag(KkNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR);
				error.setFdRepeatHandle(error.getFdRepeatHandle()-1);
				error.setFdErrorMsg(msg);
				error.setFdSendTime(new Date());
				getKkNotifyQueueErrorService().update(error);
				logger.debug("------------重发失败，更新队列成功-------------");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/*---------------------------------消息重发  end ---------------------------------*/


	private PostMethod getPost(String url) {
		PostMethod post = new PostMethod(url);
		//设置http header -- 接口鉴权
		logger.info("-----接口鉴权-----");
		String serverjAuthId = kkImConfigService.getValuebyKey(KeyConstants.KK_SERVERJ_AUTHID);
		String sererrjAuthKey = kkImConfigService.getValuebyKey(KeyConstants.KK_SERERRJ_AUTHKEY);
		post.setRequestHeader("push-id", serverjAuthId);
		post.setRequestHeader("push-sign", KKConfigUtil.getKKCurrDateSign(serverjAuthId, sererrjAuthKey));
		return post;
	}

	public void postKK5JsonData(String title, String jsonObjects, String notifyId, Integer notifyType)
			throws Exception {
		logger.debug("KkPostDataRunner.run");
		logger.debug("jsonObjects---" + jsonObjects);
		String resString = null;
		KkNotifyLog kkNotifyLog = new KkNotifyLog();

		kkNotifyLog.setFdSubject(title);

		StringBuffer buf = new StringBuffer();
		buf.append("[subject:" + title + "]\n");

		kkNotifyLog.setSendTime(new Date());
		kkNotifyLog.setKkNotifyData(jsonObjects);
		PostMethod post = getPost(url);
		RequestEntity entity;
		try {
			entity = new StringRequestEntity(jsonObjects, ContentType.APPLICATION_JSON.toString(), "UTF-8");
			post.setRequestEntity(entity);


			int result = executePostBreaker(post);
			logger.info("success:" + result);
			if (result == 200) {
				resString = new String(post.getResponseBody(), "UTF-8");
				logger.debug(jsonObjects + "---success:" + resString);
				kkNotifyLog.setKkRtnMsg(resString);
			} else {
				resString = post.getResponseBodyAsString();
				kkNotifyLog.setKkRtnMsg(resString);
				buf.append("[result:" + result + "]\n");
			}
		} catch (OpenCircuitException e) {
			String ip = getLocalIP();
			logger.error(ip + "处于熔断状态");
			buf.append("[Exception:" + ip + "---" + e.getClass().getName() + "---" + e.getMessage() + "]\n");
			updateNotify(notifyId, notifyType, KkNotifyConstants.NOTIFY_STATUS_WAIT);
		} catch (Exception e) {
			logger.error("待办推送失败", e);
			buf.append("[Exception:" + e.getMessage() + "]\n");
			updateNotify(notifyId, notifyType, KkNotifyConstants.NOTIFY_STATUS_WAIT);
		} finally {
			post.releaseConnection();
			kkNotifyLog.setFdParams(buf.toString());
			kkNotifyLog.setRtnTime(new Date());
			saveLog(kkNotifyLog);
			if (StringUtil.isNotNull(resString)) {
				JSONObject json = JSONObject.fromObject(resString);
				if ("0".equals(json.get("result").toString())) {
					kkImNotifyService.deleteByNotifyId(notifyId, notifyType);
				} else {
					updateNotify(notifyId, notifyType, KkNotifyConstants.NOTIFY_STATUS_WAIT);
				}
			}
		}
	}

	/**
	 * 
	 * <p>修改推送队列记录状态</p>
	 * @author 孙佳
	 */
	private void updateNotify(String notifyId, Integer notifyType, Integer fdStatus) {
		synchronized (kkImNotifyService) {
			try {
				kkImNotifyService.updateStatus(notifyId, notifyType, fdStatus);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void saveLog(KkNotifyLog kkNotifyLog) {
		synchronized (kkLogService) {
			try {
				kkLogService.add(kkNotifyLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	
	
	private static CircuitBreaker breaker = null;

	public static CircuitBreaker getCircuitBreaker() {
		if (breaker == null) {
			CircuitBreakerConfig config = new CircuitBreakerConfig();
			breaker = new CircuitBreaker("KkPostDataBreaker", config);
		}
		return breaker;
	}

	public int executePostBreaker(final PostMethod post) throws Exception {

		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return httpClient.executeMethod(post);
				}

				@Override
				public boolean isBreakException(Exception e) {
					if (e instanceof IOException || e instanceof HttpException || e instanceof SocketTimeoutException || e instanceof ConnectException) {
						return true;
					}
					return false;	
				}
			});
			return (Integer) result;
		} catch (OpenCircuitException e) {
			// logger.error("熔断器处于打开状态", e);
			throw e;
		} catch (Exception e) {
			// logger.error(e);
			throw e;
		}
	}

	public static void resetCircuitBreaker() {
		getCircuitBreaker().toClosedState();
		breaker = null;
	}

	private static String getLocalIP() {
		InetAddress ia;
		try {
			ia = InetAddress.getLocalHost();
			return ia.getHostAddress();
		} catch (UnknownHostException e) {

		}
		return "";
		
	}

}
