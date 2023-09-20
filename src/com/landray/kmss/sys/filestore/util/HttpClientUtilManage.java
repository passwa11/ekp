package com.landray.kmss.sys.filestore.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * 构造通用的httlclient
 */
public class HttpClientUtilManage {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(HttpClientUtilManage.class);

    private static HttpClientUtilManage instance = new HttpClientUtilManage();


    private HttpClientUtilManage(){

    }

    public static HttpClientUtilManage getInstance(){
        return instance;
    }

	private CloseableHttpClient getHttpclient() throws Exception {
		return HttpClients.createDefault();
	}

	private void closeHttpclient(CloseableHttpClient httpclient)
			throws Exception {
		if (httpclient != null) {
			httpclient.close();
		}
	}

    
    /**
     * 发送HttpPost请求，参数为parameter
     * @return
     */
    public String doPost(String url, Map<String,Object> parameter, Map<String, String> header) throws Exception{
		if (logger.isDebugEnabled())
		{
			logger.debug("HttpClientUtilManage.doPost，url:" + url);
			logger.debug("HttpClientUtilManage.doPost请求URL：" + url);
            logger.debug("请求parameter："+parameter);
            logger.debug("请求header："+header);
		}
			
        HttpPost httppost = new HttpPost(url);
		/**
		 * 设置超时时间
		 */
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(20000).setConnectTimeout(20000)
				.build();
		httppost.setConfig(requestConfig);
        if(header.containsKey("Content-Type")){
            switch (header.get("Content-Type")){
                // form表单提交数据
                case StaticParametersUtil.CONTENT_TYPE_FORM:
                    UrlEncodedFormEntity entity = getFormEntity(parameter);
                    entity.setContentType(StaticParametersUtil.CONTENT_TYPE_FORM);
                    httppost.setEntity(entity);
                    break;

                // json表单提交数据
                case StaticParametersUtil.CONTENT_TYPE_JSON:
                    StringEntity stringEntity = getJsonEntity(parameter);
                    httppost.setEntity(stringEntity);
                    break;
            }
        }

        // 给请求添加head信息
        addHeader(httppost,header);

        CloseableHttpResponse response = null;
		CloseableHttpClient httpclient;
		if (url != null && url.startsWith("https")) {
			httpclient = SSLClient.sslClient();
		} else {
			httpclient = getHttpclient();
		}
		String result = null;
        try {
            response = httpclient.execute(httppost);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
        } catch(SocketTimeoutException e) {
			logger.error("doPost报错", e);
      	    throw e;
        }catch (IOException e) {
			logger.error("doPost报错", e);
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
			throw e;
		}finally {
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
		}
       
        
        return result;
    }

   
    private URIBuilder getUriBuilder(String url , Map<String,String> parameter) throws Exception{
        /*
         * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
         */
        URIBuilder uriBuilder = new URIBuilder(url);
        List<NameValuePair> list = new LinkedList<>();
        if(!CollectionUtils.isEmpty(parameter)){
            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                BasicNameValuePair param = new BasicNameValuePair(entry.getKey(),entry.getValue());
                list.add(param);
            }
        }
        uriBuilder.setParameters(list);
        return uriBuilder;
    }

    private void addHeader(HttpRequestBase httpRequestBase,Map<String,String> header) throws Exception{
        if(!CollectionUtils.isEmpty(header)){
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpRequestBase.addHeader(entry.getKey(),entry.getValue());
            }
        }
    }

    private UrlEncodedFormEntity getFormEntity(Map<String,Object> map){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if(!CollectionUtils.isEmpty(map)){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }
        return new UrlEncodedFormEntity(formparams, Consts.UTF_8);
    }

    private StringEntity getJsonEntity(Map<String,Object> map){
        JSONObject jsonParam = new JSONObject();
        if(!CollectionUtils.isEmpty(map)){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                jsonParam.put(entry.getKey(),entry.getValue());
            }
        }
        return new StringEntity(jsonParam.toJSONString(), ContentType.APPLICATION_JSON);
    }

    /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public String doGet(String url,Map<String,String> parameter ,Map<String,String> header) throws Exception{

		if (logger.isDebugEnabled()) {
			logger.debug("HttpClientUtilManage.doGet,url:" + url);
            logger.debug("请求parameter："+parameter);
            logger.debug("请求header："+header);
		}

        HttpGet httpget = new HttpGet(getUriBuilder(url,parameter).build());

		/**
		 * 设置超时时间
		 */
	//	RequestConfig requestConfig = RequestConfig.custom()
	//			.setSocketTimeout(10000).setConnectTimeout(10000)
	//			.build();
	//	httpget.setConfig(requestConfig);
        // 给http请求添加head信息
        addHeader(httpget,header);

        CloseableHttpResponse response = null;
		CloseableHttpClient httpclient;
		if (url != null && url.startsWith("https")) {
			httpclient = SSLClient.sslClient();
		} else {
			httpclient = getHttpclient();
		}


        try {
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
			logger.error("doGet报错", e1);
            e1.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
		}

        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
				if (logger.isDebugEnabled()) {
                    logger.debug("HttpClientUtilManage.doGet,result:" + result);
                }
            }
        } catch (ParseException | IOException e) {
			logger.error("doGet报错", e);
        }finally {
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
		}
        return result;
    }
}

