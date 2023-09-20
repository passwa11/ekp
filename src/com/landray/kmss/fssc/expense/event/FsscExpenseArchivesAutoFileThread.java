package com.landray.kmss.fssc.expense.event;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 流程管理模块
 * 流程结束事件自动归档异步执行的线程
 * 仅在 @KmReviewArchivesAutoFileListener 中调用
 * @author lr-linyuchao
 *
 */
public class FsscExpenseArchivesAutoFileThread implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(FsscExpenseArchivesAutoFileThread.class);

	private String modelName;

	private String modelId;

	private Thread preThread;

	//等待时间
	private long joinTime;

	public FsscExpenseArchivesAutoFileThread(String modelName, String modelId, Thread preThread, long joinTime){
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
		try {
			preThread.join(joinTime);
		} catch (InterruptedException e1) {
			logger.error("等待线程时出错了");
			e1.printStackTrace();
		}
		TransactionStatus status = TransactionUtils.beginTransaction();
		try{
			this.doRun();
			TransactionUtils.commit(status);
		}catch(Exception e){
			logger.error("自动归档线程执行过程出错");
			TransactionUtils.rollback(status);
			e.printStackTrace();
		}
	}
	public void doRun() throws Exception{
		FsscExpenseMain expenseMain = null;
		String serviceName = null;
    	if (StringUtil.isNotNull(modelName)) {
			String modelClassName = modelName.substring(modelName.lastIndexOf(".") + 1,
					modelName.length());
			String firstChar = modelClassName.substring(0, 1).toLowerCase();
			modelClassName = firstChar + modelClassName.substring(1, modelClassName.length());
			serviceName = modelClassName + "Service";
		}
    	if(serviceName != null && modelId != null){ 
    		IBaseService service = (IBaseService) SpringBeanUtil.getBean(serviceName);
				try {
					expenseMain = (FsscExpenseMain) service.findByPrimaryKey(modelId);
				} catch (Exception e) {
					logger.error("查找模型"+modelName+" modelId为"+modelId+"时出错");
					e.printStackTrace();
				}
    	}
    	if (expenseMain == null) {
			throw new NoRecordException();
		}
    	
		// 获取连接客户端工具
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String entityStr = null;
		CloseableHttpResponse response = null;
		try {
			/*
			 * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
			 */
			String url = "/sys/archives/sys_archives_main/sysArchivesFileSign.do?method=attributArchives&mainModelName="
					+ modelName + "&fdModelId=" + modelId;
			String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
			url = serverUrl + url;

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
		} finally {
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
			logger.debug("返回结果："+entityStr);
		}
	}
}
