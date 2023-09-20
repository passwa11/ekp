package com.landray.kmss.third.ekp.java.oms.in;

import com.landray.kmss.sys.oms.in.interfaces.*;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HttpClientUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.cxf.binding.xml.XMLFault;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EkpSynchroIteratorEcoProviderImp extends EkpSynchroInIteratorProviderImp implements IOMSSynchroIteratorEcoProvider {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpSynchroIteratorEcoProviderImp.class);
	
	private String baseServicePath = "/api/sys-organization/sysSynchroGetOrg/getElementsBaseInfoForEco";
	
	private String updateServicePath = "/api/sys-organization/sysSynchroGetOrg/getUpdatedElementsForEco";
	
	private String externalServicePath = "/api/sys-organization/sysSynchroGetOrg/getDynamicExternalData";

	@Override
	public void init() throws Exception {
		TransactionStatus status = null;
		Exception t = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			Map<String, String> map = getSysAppConfigService().findByKey(EkpOmsConfig.class.getName());
			lastUpdateTime = map.get("ecoLastUpdateTime");
			logger.info("同步生态组织：lastUpdateTime=" + lastUpdateTime);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}
	
	@Override
    public void terminate() throws Exception {
		if (StringUtil.isNotNull(lastUpdateTime)) {
			TransactionStatus status = null;
			Exception t = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				EkpOmsConfig ekpOmsConfig = new EkpOmsConfig();
				ekpOmsConfig.setEcoLastUpdateTime(lastUpdateTime);
				ekpOmsConfig.save();
				TransactionUtils.commit(status);
			} catch (Exception e) {
				t = e;
				throw e;
			} finally {
				if (t != null && status != null) {
					TransactionUtils.rollback(status);
				}
			}
		}
	}

	@Override
	public IOMSResultSet getEcoRecordBaseAttributesByHttp() throws Exception {
		EkpResultSet resultSet = null;
		// 判断是否开启 EKP-J
		EkpJavaConfig javaConfig = new EkpJavaConfig();
		String enabled = javaConfig.getValue("kmss.integrate.java.enabled");
		if (StringUtil.isNull(enabled) || "false".equals(enabled)) {
			// 未开启，不同步
			return new EkpResultSet(null);
		}
		String urlPrefix = javaConfig.getValue("kmss.java.webservice.urlPrefix");
		if (StringUtil.isNull(urlPrefix)) {
			logger.error("未配置＂目标服务器url前缀＂");
			return new EkpResultSet(null);
		}
		String url = urlPrefix + baseServicePath;
		GetMethod get = HttpClientUtil.createGetMethod(url);
		get.addRequestHeader("Authorization", getHeader());
		HttpClient httpClient = HttpClientUtil.createClient();
		// 链接超时，60秒
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(60000);
		// 读取超时，30秒
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(30000);
		String result = HttpClientUtil.getDataByHttpClient(httpClient,
				get);
		JSONParser parser=new JSONParser();
		JSONObject resultData = (JSONObject)parser.parse(result);
		String errCode = (String) resultData.get("code");
		Object returnState = resultData.get("returnState");
		if ("error.httpStatus.404".equals(errCode)) {
			logger.info("生态组织同步，数据方可能不包含生态系统。");
		} else if (returnState != null && Integer.parseInt(returnState.toString()) == 2) {
			String message = (String) resultData.get("message");
			resultSet = new EkpResultSet(message);
		} else {
			logger.error("请求生态组织基础http数据失败.");
		}
		
		return resultSet;
	}
	
	private String getHeader() throws Exception {
		EkpJavaConfig config = null;
		try {
			config = new EkpJavaConfig();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			throw new XMLFault("获取配置信息时出错");
		}
		String username = config.getValue("kmss.java.restservice.userName");
		String password = config.getValue("kmss.java.restservice.password");
		if (StringUtil.isNotNull(username) && StringUtil.isNotNull(password)) {
			return "Basic " + org.apache.commons.codec.binary.Base64.encodeBase64String(
					(username + ":" + password).getBytes("UTF-8"));
		}
		
		return "";
	  }

	@Override
	public IOMSResultSet getSynchroRecordsByHttp(Boolean isExternal) throws Exception {
		EkpResultSet resultSet = null;
		List<String> resultList = new ArrayList<String>();
		if (StringUtil.isNotNull(lastUpdateTime)) {
			Date date = DateUtil.convertStringToDate(lastUpdateTime,
					"yyyy-MM-dd HH:mm:ss.SSS");
			date = new Date(date.getTime() - 1000);
			lastUpdateTime = DateUtil.convertDateToString(date,
					"yyyy-MM-dd HH:mm:ss.SSS");
		}
		while (true) {
			String url = new EkpJavaConfig().getValue("kmss.java.webservice.urlPrefix") + updateServicePath;
			JSONObject json = new JSONObject();
			json.put("returnOrgType", "");
			json.put("count", preCount);
			json.put("beginTimeStamp", lastUpdateTime);
			HttpClient httpClient = HttpClientUtil.createClient();
			PostMethod postMethod = HttpClientUtil.createPostMethod(url);
			postMethod.addRequestHeader("Authorization", getHeader());
			postMethod.setRequestEntity(new StringRequestEntity(
					json.toString(), "application/json", "utf-8"));
			// 链接超时，60秒
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(60000);
			// 读取超时，30秒
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(30000);
			String result = HttpClientUtil.getDataByHttpClient(httpClient,
					postMethod);
			if (StringUtil.isNotNull(result)) {
				JSONParser parser=new JSONParser();
				JSONObject resultData = (JSONObject)parser.parse(result);
				String errCode = (String) resultData.get("code");
				Object returnState = resultData.get("returnState");
				if ("error.httpStatus.404".equals(errCode)) {
					logger.info("生态组织同步，数据方可能不包含生态系统。");
					break;
				} else if (returnState != null && Integer.parseInt(returnState.toString()) == 2) {
					String resultStr = (String)resultData.get("message");
					if (StringUtil.isNotNull(resultStr)) {
						resultList.add(resultStr);
						lastUpdateTime = (String)resultData.get("timeStamp");
					}
					if ((Long)resultData.get("count") < preCount) {
						break;
					}
				} else {
					logger.error("获取生态组织基础数据失败.");
					break;
				}
			} else {
				break;
			}
		}
		if (!resultList.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < resultList.size(); i++) {
				String tmpStr = resultList.get(i);
				if (i == 0) {
					sb.append(tmpStr.substring(0, tmpStr.length() - 1));
				} else {
					sb.append("," + tmpStr.substring(1, tmpStr.length() - 1));
				}
			}
			sb.append("]");
			resultSet = new EkpResultSet(sb.toString());
		} else {
			resultSet = new EkpResultSet(null);
		}
		return resultSet;
	}

	@Override
	public IOMSResultEcoSet getDynamicExternalData() throws Exception {
		EKPResultEcoSet resultSet = null;
		String url = new EkpJavaConfig().getValue("kmss.java.webservice.urlPrefix") + externalServicePath;
		GetMethod get = HttpClientUtil.createGetMethod(url);
		get.addRequestHeader("Authorization", getHeader());
		HttpClient httpClient = HttpClientUtil.createClient();
		// 链接超时，60秒
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(60000);
		// 读取超时，30秒
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(30000);
		String result = HttpClientUtil.getDataByHttpClient(httpClient,
				get);
		JSONParser parser=new JSONParser();
		JSONObject resultData = (JSONObject)parser.parse(result);
		String errCode = (String) resultData.get("code");
		Object returnState = resultData.get("returnState");
		if ("error.httpStatus.404".equals(errCode)) {
			logger.info("生态组织同步，数据方可能不包含生态系统。");
		} else if (returnState != null && Integer.parseInt(returnState.toString()) == 2) {
			String message = (String) resultData.get("message");
			resultSet = new EKPResultEcoSet(message);
		} else {
			logger.error("获取生态组织基础数据失败.");
		}
		
		return resultSet;
	}
	
	@Override
	public ValueMapTo getRangeOtherValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getCreatorValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapTo getAdminsValueMapTo() {
		return valueMapTo;
	}

	@Override
	public ValueMapType[] getRangeOtherValueMapType() {
		return new ValueMapType[] { ValueMapType.ORG, ValueMapType.DEPT,
				ValueMapType.PERSON };
	}

	@Override
	public ValueMapType[] getCreatorValueMapType() {
		return new ValueMapType[] { ValueMapType.PERSON };
	}

	@Override
	public ValueMapType[] getAdminsValueMapType() {
		return new ValueMapType[] { ValueMapType.PERSON, ValueMapType.POST };
	}
}
