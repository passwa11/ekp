package com.landray.kmss.third.im.kk.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.landray.kmss.third.im.kk.KkConfig;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.constant.KkNotifyConstants;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KK5Util {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KK5Util.class);

	public static String getKK5Url(String api) throws Exception {
		if (StringUtil.isNull(api)) {
			return null;
		}
		StringBuffer sBuffer = new StringBuffer();
		KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_INNER_DOMAIN);
		String kk5ServerUrl = url + "serverj";
		if (StringUtil.isNotNull(kk5ServerUrl)) {
			kk5ServerUrl = kk5ServerUrl.trim();
			sBuffer.append(kk5ServerUrl).append(
					kk5ServerUrl.endsWith("/") ? (api.startsWith("/") ? api
							.substring(1) : api) : (api.startsWith("/") ? api
							: "/" + api));
			return sBuffer.toString();
		} else {
			return "noKK5ServerUrl";
		}
	}

	/**	获取外网地址
	 * @param api
	 * @return
	 * @throws Exception
	 */
	public static String getKK5Url_new(String api) throws Exception {
		if (StringUtil.isNull(api)) {
			return null;
		}
		StringBuffer sBuffer = new StringBuffer();
		KkConfig config = new KkConfig();
		String url = config.getValue(KeyConstants.KK_OUTER_DOMAIN);
		String kk5ServerUrl = url + "serverj";
		if (StringUtil.isNotNull(kk5ServerUrl)) {
			kk5ServerUrl = kk5ServerUrl.trim();
			sBuffer.append(kk5ServerUrl).append(
					kk5ServerUrl.endsWith("/") ? (api.startsWith("/") ? api
							.substring(1) : api) : (api.startsWith("/") ? api
							: "/" + api));
			return sBuffer.toString();
		} else {
			return "noKK5ServerUrl";
		}
	}

	public static String getGZHJson() throws Exception {
		return getGZHJson(KkNotifyConstants.KK5_GETGZH_API);
	}

	public static String getGZHJson(String api) throws Exception {
		String result = "";
		String getGZHUrl = (api.startsWith("http")) ? api : getKK5Url(api);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(getGZHUrl);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				JSONObject resultJson = JSONObject.fromObject(IOUtils.toString(
						entity.getContent(), "UTF-8"));
				JSONArray gzhArray = resultJson.getJSONArray("result");
				result = gzhArray.toString();
			}
		} catch (Exception e) {
			System.out.println(ResourceUtil.getString("getGzhInfoError",
					"third-im-kk"));
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(ResourceUtil.getString(
							"getGzhInfoError", "third-im-kk"),e);
				}
			}
			if(httpclient!=null){
				try {
					httpclient.close();
				} catch (IOException e) {
					logger.error(ResourceUtil.getString(
							"getGzhInfoError", "third-im-kk"),e);
				}
			}
		}
		return "".equals(result) ? "[]" : result;
	}

	public static JSONObject pushToServiceUser(JSONObject postData)
			throws Exception {
		String returnData = "";
		String pushServiceUserUrl = getKK5Url(KkNotifyConstants.KK5_PUSH_TO_SERVICEUSER_API);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(pushServiceUserUrl);
		logger.debug("pushServiceUserUrl:" + pushServiceUserUrl);
		logger.debug("postData:" + postData == null ? "" : postData.toString());
		httpPost.setEntity(new StringEntity(postData.toString(), Consts.UTF_8));
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				returnData = IOUtils.toString(entity.getContent(), "UTF-8");
			}
			logger.debug("returnData:" + returnData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(response!=null){
				try {
					response.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			if(httpclient!=null) {
				try {
					httpclient.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}
		return JSONObject.fromObject(returnData);
	}

}