package com.landray.kmss.third.wechat.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.third.wechat.dto.WeChatPostData;
import com.landray.kmss.third.wechat.model.WechatMainConfig;
import com.landray.kmss.third.wechat.util.WeChatConfigUtil;

import net.sf.json.JSONObject;

public class WeChatPostDataRunner implements Runnable {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WeChatPostDataRunner.class);
	private HttpClient httpClient;
	private List<WeChatPostData> postDatas;
	private String scene;
	private int type;

	public WeChatPostDataRunner(HttpClient httpClient,
			List<WeChatPostData> postDatas,int type) {
		this.postDatas = postDatas;
		this.type = type;

		if (this.httpClient == null) {
			this.httpClient = httpClient;
		}
		if (scene == null || "".equals(scene)) {
			this.scene = WeChatConfigUtil.scene;
		}
	}

	@Override
    public void run() {
		PostMethod post = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scene", scene);
		map.put("data", postDatas);
		JSONObject jo = JSONObject.fromObject(map);
		try {
			RequestEntity entity = new StringRequestEntity(jo.toString(), null,"utf-8");
			WechatMainConfig config = new WechatMainConfig();
			
			if (config != null) {
				String wyEnable = config.getLwechat_wyEnable();
				String qyEnable = config.getLwechat_qyEnable();

				// 服务号微云待办
				if (StringUtils.isNotEmpty(wyEnable) && "true".equals(wyEnable)) {
					String wyisSendTodo = config.getLwechat_wyisSendTodo();
					String wyisSendView = config.getLwechat_wyisSendView();
					boolean wySendFlag= false;
					 
					//type =1 待审    type = 2 待阅
					if(type ==1 && StringUtils.isNotEmpty(wyisSendTodo) && "true".equals(wyisSendTodo)){
						wySendFlag=true;
					}
					
					if(type == 2 && StringUtils.isNotEmpty(wyisSendView) && "true".equals(wyisSendView)){
						wySendFlag=true;
					}
					
					if(wySendFlag){
						String baseUrl = config.getLwechat_wyUrl();
						String notifyUrl = config.getLwechat_wyNotifyUrl();
						String sendTaget = baseUrl + notifyUrl;
						
						if (StringUtils.isNotEmpty(sendTaget)) {
							post = new PostMethod(sendTaget);
							try {
								post.setRequestEntity(entity);
								int result = httpClient.executeMethod(post);
								if (result == 200) {
									String resString = post.getResponseBodyAsString();
									logger.info("推送到微云消息推送结果:"+resString);
								} else {
									String resString = post.getResponseBodyAsString();
									logger.info("推送到微云消息推送结果:"+resString);
								} 
							} catch (Exception e) {
								logger.error("WeChatPostDataRunner.run 推送到微云时发生异常,异常信息:"+e.getMessage());
							}
							if (post != null){
								post.releaseConnection();
								post = null;
							}
						}else{
							logger.error("WeChatPostDataRunner.run 推送到微云时发生异常,异常信息:消息接收人员为空");
						}
					}else{
						 logger.info("WeChatPostDataRunner.run  待审待阅推送开关未打开,无法推送到微云");
					}
				}

				// 企业号发待办
				if (StringUtils.isNotEmpty(qyEnable) && "true".equals(qyEnable)) {
					 //type =1 待审    type = 2 待阅
					String qyisSendTodo = config.getLwechat_qyisSendTodo();
					String qyisSendView = config.getLwechat_qyisSendView();
					boolean qySendFlag= false;
					 
					if(type ==1 && StringUtils.isNotEmpty(qyisSendTodo) && "true".equals(qyisSendTodo)){
						qySendFlag=true;
					}
					
					if(type == 2 && StringUtils.isNotEmpty(qyisSendView) && "true".equals(qyisSendView)){
						qySendFlag=true;
					}
					
					if(qySendFlag){
						String qyBaseUrl = config.getLwechat_qyUrl();
						String qyNotifyUrl = config.getLwechat_qyNotifyUrl();
						String qySendTaget = qyBaseUrl + qyNotifyUrl;
						
						if (StringUtils.isNotEmpty(qySendTaget)) {
							post = new PostMethod(qySendTaget);
							try {
								post.setRequestEntity(entity);
								int result = httpClient.executeMethod(post);
								if (result == 200) {
									String resString = post.getResponseBodyAsString();
									logger.info("推送到企业号消息推送结果:"+resString);
								} else {
									String resString = post.getResponseBodyAsString();
									logger.info("推送到企业号消息推送结果:"+resString);
								}
							} catch (Exception e) {
								logger.error("WeChatPostDataRunner.run 推送到企业号时发生异常,异常信息:"+e.getMessage());
							}
							if (post != null){
								post.releaseConnection();
								post = null;
							}
						}else{
							logger.error("WeChatPostDataRunner.run 推送到企业号时发生异常,异常信息:消息接收人员为空");
						}
					}else{
						 logger.info("WeChatPostDataRunner.run  待审待阅推送开关未打开,无法推送到企业号");
					}
				}
			}
		} catch (Exception e) {
			logger.error("WeChatPostDataRunner.run 推送消息时发生异常,异常信息:"+e.getMessage());
		} finally {
			if (post != null){
				post.releaseConnection();
				post = null;
			}
		}
	}
}
