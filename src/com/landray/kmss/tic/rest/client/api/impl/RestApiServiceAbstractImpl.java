package com.landray.kmss.tic.rest.client.api.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.formula.parser.IForumlaVarProvider;
import com.landray.kmss.sys.formula.parser.JSONForumlaProvider;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.rest.client.api.RestApiService;
import com.landray.kmss.tic.rest.client.config.RestConfigStorage;
import com.landray.kmss.tic.rest.client.config.RestConstant;
import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.error.RestErrorKeys;
import com.landray.kmss.tic.rest.client.http.RequestExecutor;
import com.landray.kmss.tic.rest.client.http.RequestHttp;
import com.landray.kmss.tic.rest.client.http.SimpleGetRequestExecutor;
import com.landray.kmss.tic.rest.client.http.SimplePostRequestExecutor;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.util.StringUtil;


public abstract class RestApiServiceAbstractImpl<H, P, R> implements RestApiService, RequestHttp<H, P, R> {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected volatile Map<String, RestConfigStorage> restConfigStorages = new HashMap<String, RestConfigStorage>();

	//JSON取值器
	protected IForumlaVarProvider varProvider=new JSONForumlaProvider();

	@Override
	public Map<String, RestConfigStorage> getRestConfigStorages() {
		return this.restConfigStorages;
	}

	@Override
	public RestConfigStorage addRestConfigStorage(RestConfigStorage configStorage) {
		if (StringUtil.isNull(configStorage.getAppId())) {
			return configStorage;
		}
		if (!restConfigStorages.keySet().contains(configStorage.getAppId())) {
			restConfigStorages.put(configStorage.getAppId(), configStorage);
		} else {
			// TODO 可能需要跟新里面的属性值
			configStorage = restConfigStorages.get(configStorage.getAppId());
		}
		return configStorage;
	}

	/**
	 * 全局的是否正在刷新access token的锁
	 */
	protected final Object globalAccessTokenRefreshLock = new Object();

	protected RestConfigStorage restConfigStorage;

	/**
	 * 临时文件目录
	 */
	protected File tmpDirFile;
	private int retrySleepMillis = 1000;
	private int maxRetryTimes = 1;

	@Override
	public String getAccessToken() throws RestErrorException {
		return getAccessToken(false);
	}

	@Override
	public String get(String url, String queryParam,
			TicCoreLogMain ticCoreLogMain) throws RestErrorException {
		return execute(SimpleGetRequestExecutor.create(this), url, queryParam,
				ticCoreLogMain);
	}

	@Override
	public String post(String url, String postData,
			TicCoreLogMain ticCoreLogMain) throws RestErrorException {
		return execute(SimplePostRequestExecutor.create(this), url, postData,
				ticCoreLogMain);
	}

	/**
	 * 向Rest提供端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
	 */
	@Override
	public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data,
			TicCoreLogMain ticCoreLogMain) throws RestErrorException {
		int retryTimes = 0;
		do {
			try {
				return this.executeInternal(executor, uri, data,
						ticCoreLogMain);
			} catch (RestErrorException e) {
				log.error(e.getMessage(), e);
				if (retryTimes + 1 > this.maxRetryTimes) {
					this.log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
					// 最后一次重试失败后，直接抛出异常，不再等待
					throw new RuntimeException("Rest提供端端异常，超出重试次数");
				}

				RestError error = e.getError();
				/*
				 * -1 系统繁忙, 1000ms后重试
				 */
				if (error.hasError()) {
					int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
					try {
						this.log.debug("Rest提供端系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
						Thread.sleep(sleepMillis);
					} catch (InterruptedException e1) {
						throw new RuntimeException(e1);
					}
				} else {
					throw e;
				}
			}
		} while (retryTimes++ < this.maxRetryTimes);

		this.log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
		throw new RuntimeException("Rest提供端端异常，超出重试次数");
	}

	private void handleHeader(JSONObject prefixReqResult)throws RestErrorException {
		if(StringUtil.isNull(restConfigStorage.getHttpheader())){
			return;
		}
		JSONArray headers = JSONArray.parseArray(restConfigStorage.getHttpheader());
		JSONArray repheaders = new JSONArray();
		for(int i=0;i<headers.size();i++) {
			String header = headers.getString(i);
			if(header.indexOf("ACCESSTOKEN")!=-1) {
				//accessToken在header中，需要替换后进行认证处理，在header中定义必须是{ACCESSTOKEN}
				if (StringUtil.isNotNull(restConfigStorage.getAccessTokenURL())) {
					String accessToken = getAccessToken(false);
					header = header.replaceAll(RestConstant.REP_ACCESSTOKEN,
							accessToken);
				}else if(StringUtil.isNotNull(restConfigStorage.getAccessTokenClazz())) {
					String accessToken = getAccessToken(false);
					header = header.replaceAll(RestConstant.REP_ACCESSTOKEN,
							accessToken);
				}
			} else {
				String[] kv = header.split(":");
				if (kv.length == 2) {
					// String key = kv[0];
					String value = kv[1];
					if (value.contains("{")) {
						Object var_value = getVarValue(prefixReqResult, value);
						if (var_value != null) {
							header = header.replace(value,
									var_value.toString());
						}
					}
				}
			}
			repheaders.add(header);
		}
		//RestErrorKeys rek = (RestErrorKeys)getRequestHttp().getErrorKeys();
		//rek.setHeader(repheaders.toString());
		((RestErrorKeys)this.getErrorKeys()).setHeader(repheaders.toString());
	}
	
	private void handleCookie(JSONObject prefixReqResult, String url)
			throws Exception {
		TicRestMain restMain= restConfigStorage.getTicRestMain();
		if (restMain == null) {
			return;
		}
		String cookies = getCookies(prefixReqResult, url);
		// if (StringUtil.isNull(cookies)) {
		// return;
		// }
		String reqParam = restMain.getFdReqParam();
		JSONObject param = JSONObject.parseObject(reqParam, Feature.OrderedField);
		if (!param.containsKey("cookie")) {
			if (StringUtil.isNotNull(cookies)) {
				((RestErrorKeys) this.getErrorKeys())
						.setCookieStr(cookies);
			}
			return;
		}
		JSONArray cookieParams = param.getJSONArray("cookie");
		if (cookieParams == null || cookieParams.isEmpty()) {
			if (StringUtil.isNotNull(cookies)) {
				((RestErrorKeys) this.getErrorKeys())
						.setCookieStr(cookies);
			}
			return;
		}
		JSONArray cookiesArray_result = new JSONArray();
		JSONArray cookiesArray = null;
		if (StringUtil.isNotNull(cookies)) {
			cookiesArray = JSONArray.parseArray(cookies);
		}
		// String url = restMain.getFdReqURL();
		if (StringUtil.isNotNull(url)) {
			url = restMain.getFdReqURL();
		}
		String domain = getDomain(url);
		for (Object o : cookieParams) {
			JSONObject cookieObj = (JSONObject) o;
			String name = cookieObj.getString("name").trim();
			String value = cookieObj.getString("value").trim();
			JSONObject cookie_result = new JSONObject(true);
			cookie_result.put("name", name);
			if (value.contains("{")) {
				JSONObject co = getCookieValue(name, cookiesArray);
				if (co == null) {
					Object var_value = getVarValue(prefixReqResult, value);
					if (var_value != null) {
						cookie_result.put("value", var_value.toString());
						cookie_result.put("domain", domain);
						cookiesArray_result.add(cookie_result);
					}
					continue;
				}
				cookie_result.put("value", co.getString("value"));
				if (co.containsKey("domain")) {
					cookie_result.put("domain", co.getString("domain"));
				} else {
					cookie_result.put("domain", domain);
				}
				if (co.containsKey("path")) {
					cookie_result.put("path", co.getString("path"));
				}
			} else {
				cookie_result.put("value", value);
				cookie_result.put("domain", domain);
			}
			cookiesArray_result.add(cookie_result);
		}
		((RestErrorKeys) this.getErrorKeys())
				.setCookieStr(cookiesArray_result.toString());
	}

	private JSONObject getCookieValue(String cookieName,
			JSONArray cookiesArray) {
		if (cookiesArray == null) {
			return null;
		}
		for (Object o : cookiesArray) {
			JSONObject cookieObj = (JSONObject) o;
			String name = cookieObj.getString("name").trim();
			// String value = cookieObj.getString("value").trim();
			if (name.equals(cookieName)) {
				return cookieObj;
			}
		}
		return null;
	}

	/**
	* @author:  何建华
	* @methodsName: handlePrefixReq
	* @description:处理前置请求，比如K3 wise调用业务接口之前要先调用获取Token的请求
	* @return: 返回前置请求的Body字符串
	* @throws: 
	*/

	abstract public String handlePrefixReq() throws Exception;

	private Object getVarValue(JSONObject json, String key) {
		if (json == null) {
			return null;
		}
		if (key.contains("{")) {

			int start = key.indexOf("{");
			int end = key.indexOf("}");
			String pre = key.substring(0, start);
			String middle = key.substring(start, end + 1);
			String sub = key.substring(end + 1);
			middle = middle.substring(1);
			middle = middle.substring(0, middle.length() - 1);
			try {
				Object value = varProvider.getValue(net.sf.json.JSONObject.fromObject(json.toString()), middle);
				return pre + value.toString() + sub;
			} catch (Exception e) {
				log.error("获取值失败：" + key + "," + json, e);
			}
		} else {
			return null;
		}
		return null;
	}

	
	protected <T, E> T executeInternal(RequestExecutor<T, E> executor,
			String uri, E data, TicCoreLogMain ticCoreLogMain)
			throws RestErrorException {
		String url = uri;
		if (url.indexOf("ACCESSTOKEN") != -1) {
			// url中的ACCESSTOKEN已替换
		}

        JSONObject bodyJson=null;
		try {

			//处理前置请求
			String bodyRetrun=handlePrefixReq();

			if(StringUtil.isNotNull(bodyRetrun)){
				try {
					bodyJson=JSONObject.parseObject(bodyRetrun,Feature.OrderedField);

				} catch (Exception e) {
					log.error("body转JSON失败", e);
				}
			}

		} catch (Exception e) {
			log.error("处理前置请求失败", e);
		}

		handleHeader(bodyJson);
		// 处理cookie
		try {
			handleCookie(bodyJson, url);
		} catch (Exception e1) {
			log.error("获取cookie出错", e1);
		}

		//System.out.println(url);
		//重新处理URL(http://192.168.190.130/K3API/PO/Save?token="+{Data.Token})
//		String importTarTrans = ticCoreLogMain.getFdImportParTrans();
//		JSONObject importTarTransObj = new JSONObject(true);
//		if (StringUtil.isNotNull(importTarTrans)) {
//			importTarTransObj = JSONObject.parseObject(importTarTrans,Feature.OrderedField);
//		}
//
//		if (StringUtil
//				.isNotNull(((RestErrorKeys) this.getErrorKeys()).getHeader())) {
//			JSONArray headers = JSONArray.parseArray(
//					((RestErrorKeys) this.getErrorKeys()).getHeader());
//			importTarTransObj.put("header", headers);
//		}
//
//		if (StringUtil.isNotNull(
//				((RestErrorKeys) this.getErrorKeys()).getCookieStr())) {
//			JSONArray cookies = JSONArray
//					.parseArray(((RestErrorKeys) this.getErrorKeys())
//							.getCookieStr());
//			importTarTransObj.put("cookie", cookies);
//		}
//		ticCoreLogMain.setFdSourceFuncInXml(importTarTransObj.toString());
     
		if(bodyJson!=null && bodyJson.size()>0
				&& StringUtil.isNotNull(url) && url.indexOf('{')>1&& url.indexOf('}')>1){//url里面有前置请求的信息
			// StringBuilder urlNew=new StringBuilder();
			String urlNew = url;
			for (int index = url.indexOf('{'); index > -1;) {
				int nxtIndex = url.indexOf('}',
						index + 1);
				// index为开始点，nxtIndex为结束点，无结束点则退出循环
				if (nxtIndex == -1) {
                    break;
                }
				String varName = url.substring(index + 1, nxtIndex);
				// urlNew.append(url.substring(0, index));//接{左边的
				
				//取变量
				Object str=null;
				try {
					str=varProvider.getValue(net.sf.json.JSONObject.fromObject(bodyJson.toString()), varName);
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(str!=null){
					urlNew = urlNew.replace("{" + varName + "}",
							str.toString());
				}else{
					urlNew = urlNew.replace("{" + varName + "}", "");
				}
				url = url.substring(nxtIndex + 1);
			}
			if(urlNew.length()>0){
				url = urlNew;
			}
		}
		T ret = null;
		JSONObject reqLog = buildReqLog(url,data==null?null:(String)data);
		if(reqLog!=null) {
			ticCoreLogMain.setFdImportParOri(reqLog.toString());
		}
		long startTime=System.currentTimeMillis();
		try {
			T result = executor.execute(url, data);
			long endTime = System.currentTimeMillis();
			long fdTimeConsuming=((endTime - startTime));
			ticCoreLogMain.setFdTimeConsuming(String.valueOf(fdTimeConsuming));// 设置接口调用耗时
			this.log.info("接口请求耗时:"+fdTimeConsuming+ " ms");
			this.log.debug("\n【请求地址】: {}\n【请求参数】：" + (data!=null? data.toString() : "")
					+ "\n【响应数据】：{}", url, result);
			ret = result;
			return result;
		} catch (RestErrorException e) {
			RestError error = e.getError();
			/*
			 * 发生以下情况时尝试刷新access_token 40001 获取access_token时AppSecret错误，或者access_token无效
			 * 42001 access_token超时 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期）
			 */
//			if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001 || error.getErrorCode() == 40014) {
//				// 强制设置restConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
//				this.restConfigStorage.expireAccessToken();
//				return execute(executor, uri, data);
//			}

			if (error.hasError()) {
				this.log.error(
						"\n【请求地址】: {}\n【请求参数】：" + (data == null ? ""
								: data.toString()) + "\n【错误信息】：{}",
						url, error);
				throw new RestErrorException(error, e);
			}
			return ret;
		} catch (HttpResponseException e) {
			String error = "statusCode:" + e.getStatusCode() + ",message:"
					+ e.getMessage();
			throw new RuntimeException(error, e);
		} catch (IOException e) {
			e.printStackTrace();
			this.log.error(
					"\n【请求地址】: {}\n【请求参数】：" + (data == null ? ""
							: data.toString()) + "\n【异常信息】：{}",
					url, e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			this.log.error(
					"\n【请求地址】: {}\n【请求参数】：" + (data == null ? ""
							: data.toString()) + "\n【异常信息】：{}",
					url, e);
			throw new RuntimeException(e);
		}finally{
			if(StringUtil.isNull(ticCoreLogMain.getFdTimeConsumingOrg())){
				long endTime = System.currentTimeMillis();
				long fdTimeConsuming=((endTime - startTime));
				ticCoreLogMain
						.setFdTimeConsuming(String.valueOf(fdTimeConsuming));// 设置接口调用耗时
			}
		}
	}

	@Override
	public void setRetrySleepMillis(int retrySleepMillis) {
		this.retrySleepMillis = retrySleepMillis;
	}

	@Override
	public void setMaxRetryTimes(int maxRetryTimes) {
		this.maxRetryTimes = maxRetryTimes;
	}

	public File getTmpDirFile() {
		return this.tmpDirFile;
	}

	public void setTmpDirFile(File tmpDirFile) {
		this.tmpDirFile = tmpDirFile;
	}

	@Override
	public RequestHttp getRequestHttp() {
		return this;
	}

	public static void main(String[] args) {
		String url = "https://192.168.2.111:8080/save";
		System.out.println(getDomain(url));
	}

	private static String getDomain(String url) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		String domain = null;
		if (url.indexOf("://") >= 0) {
			domain = url.substring(url.indexOf("://") + 3);
		}
		if (domain.indexOf(":") >= 0) {
			domain = domain.substring(0, domain.indexOf(":"));
		}
		if (domain.indexOf("/") >= 0) {
			domain = domain.substring(0, domain.indexOf("/"));
		}
		return domain;
	}

	private JSONObject buildReqLog(String url, String postEntity){
		try {
			JSONObject o = new JSONObject();
			o.put("url", url);
			try {
				JSONObject body = JSONObject.parseObject(postEntity, Feature.OrderedField);
				if (body.containsKey("body")) {
					body = body.getJSONObject("body");
				}
				o.put("body", body);
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
				o.put("body", postEntity);
			}
			String headerStr = ((RestErrorKeys) this.getErrorKeys()).getHeader();
			if (StringUtil.isNotNull(headerStr)) {
				JSONArray headers = JSONArray.parseArray(headerStr);
				o.put("header", headers);
			}
			String cookieStr = ((RestErrorKeys) this.getErrorKeys()).getCookieStr();
			if (StringUtil.isNotNull(cookieStr)) {
				JSONArray cookies = JSONArray
						.parseArray(cookieStr);
				o.put("cookie", cookies);
			}
			return o;
		}catch (Exception e){
			log.warn(e.getMessage(),e);
		}
		return null;
	}

}
