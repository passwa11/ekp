package com.landray.kmss.km.review.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 流程管理模块
 * 流程结束事件自动沉淀异步执行的线程
 * 仅在 @KmReviewSubsideAutoFileListener 中调用
 * @author lr-linyuchao
 *
 */
public class KmReviewSubsideAutoFileThread implements Runnable {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewSubsideAutoFileThread.class);
	
	private String modelName;
	
	private String modelId;
	
	//等待时间
	private long joinTime;
	
	private Thread preThread;
	public KmReviewSubsideAutoFileThread(String modelName, String modelId, Thread preThread, long joinTime) {
		this.modelName = modelName;
		this.modelId = modelId;
		this.preThread = preThread;
		if(joinTime == 0){
			joinTime = 3000;
		}
		this.joinTime = joinTime;
	}
	
	@Override
	public void run() {

		if(preThread!=null){
			try {
				preThread.join(joinTime);
			} catch (InterruptedException e) {
				logger.error("等待线程时出错了");
				e.printStackTrace();
			}
		}

		String serviceName = null;
    	if (StringUtil.isNotNull(modelName)) {
			String modelClassName = modelName.substring(modelName.lastIndexOf(".") + 1,
					modelName.length());
			String firstChar = modelClassName.substring(0, 1).toLowerCase();
			if(modelClassName.indexOf("$")>-1){
				modelName = firstChar + modelClassName.substring(1, modelClassName.indexOf("$"));
			}else{
				modelName = firstChar + modelClassName.substring(1);
			}

			serviceName = modelName + "Service";
		}
    	if(serviceName == null){
    		throw new NoRecordException();
    	}
    	
		// 获取连接客户端工具
		CloseableHttpClient httpClient = HttpClients.createDefault();

		String entityStr = null;
		CloseableHttpResponse response = null;
		KmssCache cache = new KmssCache(KmsMultidocSubside.class);
		String signKey = MD5Util.getMD5String(modelId);
		try {
			JSONObject sign = new JSONObject();
			sign.put("isOk", true);
			sign.put("status", 1);
			sign.put("serviceName", serviceName);
			sign.put("userId", UserUtil.getUser().getFdId());
			cache.put(signKey, sign.toJSONString());
			/*
			 * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
			 */
			String url = "/km/review/km_review_main/kmReviewMainSubside.do?method=fileDoc&fdId=" + modelId;
			String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
			url = serverUrl+ url;

			URIBuilder uriBuilder = new URIBuilder(url);

			// 构建Get请求对象
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			/**
			 * 设置超时时间
			 */
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000)
					.build();
			httpGet.setConfig(requestConfig);
			/*
			 * 添加请求头信息
			 */
			// 浏览器表示
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
			// 传输的类型
			httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
			// 执行请求
			response = httpClient.execute(httpGet);
			// 获得响应的实体对象
			HttpEntity entity = response.getEntity();
			// 使用Apache提供的工具类进行转换成字符串
			entityStr = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			logger.error("Http协议出现问题");
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error("解析错误");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			logger.error("URI解析异常");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO异常");
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			// 释放连接
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("释放连接出错");
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("释放连接出错");
					e.printStackTrace();
				}
			}
			/**
			 * 清除签名缓存信息
			 */
			cache.remove(signKey);
			logger.debug("返回结果：" + entityStr);
		}
	}
}
