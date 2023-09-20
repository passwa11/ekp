package com.landray.kmss.sys.filestore.util;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * 构造通用的httlclient
 */
public class HttpClientUtil {
	private static Log logger = LogFactory.getLog(HttpClientUtil.class);
	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";
    public static final String CONTENT_TYPE_JSON = "application/json";
    private static HttpClientUtil instance = new HttpClientUtil();


    private HttpClientUtil(){

    }

    public static HttpClientUtil getInstance(){
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
     * 原生字符串发送put请求
     * 
     * @param url
     * @return
	 * @throws Exception 
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doPut(String url, Map<String,Object> parameter, Map<String, String> header) throws Exception {

        HttpPut httpPut = new HttpPut(url);
        
        if(header.containsKey("Content-Type")){
            switch (header.get("Content-Type")){
                // form表单提交数据
                case CONTENT_TYPE_FORM:
                    UrlEncodedFormEntity entity = getFormEntity(parameter);
                    entity.setContentType(CONTENT_TYPE_FORM);
                    httpPut.setEntity(entity);
                    break;

                // json表单提交数据
                case CONTENT_TYPE_JSON:
                    StringEntity stringEntity = getJsonEntity(parameter);
                    httpPut.setEntity(stringEntity);
                    break;
            }
        }
        
        
        // 给请求添加head信息
        addHeader(httpPut,header);
        
        CloseableHttpResponse response = null;
		CloseableHttpClient httpclient;
		if (url != null && url.startsWith("https")) {
			httpclient = SSLClient.sslClient();
		} else {
			httpclient = getHttpclient();
		}

        try {
            response = httpclient.execute(httpPut);
        } catch(SocketTimeoutException e) {
			logger.error("doPost报错", e);
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
        	  throw e;
        }catch (IOException e) {
			logger.error("doPost报错", e);
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
            throw e;
		} 
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
			if (logger.isDebugEnabled()) {
                logger.debug("HttpClientUtilManage.doPost,result:" + result);
            }
        } catch (ParseException | IOException e) {
			logger.error("doPost报错", e);
        }finally {
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
		}
        return result;
    }
	
    /**
     * 发送HttpPost请求，参数为parameter
     * @return
     */
    public String doPost(String url, Map<String,Object> parameter, Map<String, String> header) throws Exception{
		if (logger.isDebugEnabled()) {
            logger.debug("HttpClientUtilManage.doPost，url:" + url);
        }

        HttpPost httppost = new HttpPost(url);

        if(header.containsKey("Content-Type")){
            switch (header.get("Content-Type")){
                // form表单提交数据
                case CONTENT_TYPE_FORM:
                    UrlEncodedFormEntity entity = getFormEntity(parameter);
                    entity.setContentType(CONTENT_TYPE_FORM);
                    httppost.setEntity(entity);
                    break;

                // json表单提交数据
                case CONTENT_TYPE_JSON:
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

        try {
            response = httpclient.execute(httppost);
        } catch(SocketTimeoutException e) {
			logger.error("doPost报错", e);
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
        	  throw e;
        }catch (IOException e) {
			logger.error("doPost报错", e);
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
            throw e;
		} 
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
			if (logger.isDebugEnabled()) {
                logger.debug("HttpClientUtilManage.doPost,result:" + result);
            }
        } catch (ParseException | IOException e) {
			logger.error("doPost报错", e);
			throw e;
        }finally {
			if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
		}
        return result;
    }

    /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public byte[] doGet(String url,Map<String,String> parameter ,Map<String,String> header) throws Exception{

		if (logger.isDebugEnabled()) {
			logger.debug("HttpClientUtilManage.doGet,url:" + url);
		}

		HttpGet httpget = null;
		if(parameter != null) {
			 httpget = new HttpGet(getUriBuilder(url,parameter).build());
		} else {
			
			httpget = new HttpGet(url);
		}

		/**
		 * 设置超时时间
		 */
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(10000).setConnectTimeout(10000)
				.build();
		httpget.setConfig(requestConfig);
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
            if (response != null) {
				response.close();
			}
			closeHttpclient(httpclient);
		}
        byte[] result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
               // result = EntityUtils.toString(entity);
            	result = EntityUtils.toByteArray(entity);
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

    private StringEntity getJsonEntity(Map<String,Object> map) throws Exception{
        JSONObject jsonParam = new JSONObject();
        if(!CollectionUtils.isEmpty(map)){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                jsonParam.put(entry.getKey(),entry.getValue());
            }
        }
        return new StringEntity(jsonParam.toJSONString());
    }

    /**
     * 请求方式 post multipart/form-data
     * @param url  请求地址
     * @param files  上传的文件
     * @param fileInfos  文件信息
     * @param headers  请求头
     * @return
     * @throws Exception
     */
    public String doMultipartFormDataPost(String url, Map<String,List<File>> files, Map<String,String> fileInfos, Map<String, String> headers) throws Exception{
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(60000).setConnectTimeout(60000)
                    .build();

            httpPost.setConfig(requestConfig);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for(Map.Entry<String, String> fileInfo : fileInfos.entrySet()) {
                String key = fileInfo.getKey();
                String value = fileInfo.getValue();
                // 请求普通参数
                builder.addTextBody(key, value, ContentType.MULTIPART_FORM_DATA);
               // builder.addTextBody("fileInfos", fileInfos, ContentType.MULTIPART_FORM_DATA);
            }

            for(Map.Entry<String, List<File>> file : files.entrySet()) {
                String key = file.getKey();
                List<File> value = file.getValue();
                // 请求文件参数
                for(File f : value) {
                    builder.addBinaryBody(key, f,  ContentType.APPLICATION_OCTET_STREAM, f.getName());
                    //builder.addBinaryBody("files", f,  ContentType.APPLICATION_OCTET_STREAM, f.getName());
                }
            }



            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            // 给请求添加head信息
            if(headers != null && headers.size() > 0) {
                addHeader(httpPost,headers);
            }

            if (url != null && url.startsWith("https")) {
                httpClient = SSLClient.sslClient();
            }

            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity, "UTF-8");

            return result;
        } catch (Exception e) {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(httpClient);

            logger.error("response报错：{}", e);
            throw e;
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(httpClient);
        }

    }

    /**
     * 发送HttpPost请求，Content-Type 是Json 返回byte[]
     * (用于文件下载)
     * @return
     */
    public byte[] doJsonPost(String url, Map<String,Object> parameter, Map<String, String> header) throws Exception{

        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = null;
        try{
            HttpPost httppost = new HttpPost(url);
            StringEntity stringEntity = getJsonEntity(parameter);
            httppost.setEntity(stringEntity);

            // 给请求添加head信息
            addHeader(httppost,header);

            if (url != null && url.startsWith("https")) {
                httpclient = SSLClient.sslClient();
            } else {
                httpclient = getHttpclient();
            }

            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            byte[] result = null;
            result = EntityUtils.toByteArray(entity);

            return result;
        } catch (Exception e) {
           logger.error("error:{}", e);
           throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            if(httpclient != null) {
                closeHttpclient(httpclient);
            }
        }

    }

}

