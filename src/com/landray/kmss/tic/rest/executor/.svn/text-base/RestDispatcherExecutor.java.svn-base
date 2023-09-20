package com.landray.kmss.tic.rest.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.xform.base.service.controls.relation.RelationOuterSearchParams;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.common.util.TicCommonUtil;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.log.service.ITicCoreLogMainService;
import com.landray.kmss.tic.core.log.service.ITicCoreLogManageService;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.core.middleware.executor.ITicDispatcherExecutor;
import com.landray.kmss.tic.core.util.RecursionUtil;
import com.landray.kmss.tic.rest.client.api.RestApiService;
import com.landray.kmss.tic.rest.client.api.impl.RestApiServiceImpl;
import com.landray.kmss.tic.rest.client.config.RestConstant;
import com.landray.kmss.tic.rest.client.config.RestInMemoryConfigStorage;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.connector.Utils.ListToJSONArrayUtil;
import com.landray.kmss.tic.rest.connector.Utils.ParamUrlUtil;
import com.landray.kmss.tic.rest.connector.model.TicRestAuth;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

public class RestDispatcherExecutor
		implements ITicDispatcherExecutor, IRestDataExecutor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RestDispatcherExecutor.class);

	// private RestApiService rest = null;
	// private RestInMemoryConfigStorage configStorage = null;

	@Override
	public String execute(JSONObject jo, String funcId, TicCoreLogMain log)
			throws Exception {
		log.setFdLogType(Constant.FD_TYPE_REST);
		if(logger.isDebugEnabled()){
			logger.debug("rest func in params::" + jo.toJSONString());
		}
		logger.warn(JSON.toJSONStringZ(jo, SerializeConfig.getGlobalInstance(), SerializerFeature.QuoteFieldNames));
		logger.warn("funcId::" + funcId);
		//jo = JSONObject.parseObject(jo.toJSONString());
		JSONObject export = new JSONObject(true);
		RestApiService rest = new RestApiServiceImpl();
		try {
			TicRestMain restMain = (TicRestMain)getTicRestMainService().findByPrimaryKey(funcId, null, true);
			JSONObject req = null;
			if(StringUtil.isNotNull(restMain.getFdReqParam())){
				req =  JSONObject.parseObject(restMain.getFdReqParam(),Feature.OrderedField);
			}

			RestInMemoryConfigStorage configStorage = new RestInMemoryConfigStorage();
			handleSetting(restMain, rest, configStorage);
			if (restMain.getFdOauthEnable()) {
				handleOauth(restMain.getTicRestAuth(), rest, configStorage);
			}
			handleHeader(req, jo, rest, configStorage);
			// if (restMain.getFdCookieEnable()) {
			//handleCookie(restMain, rest, configStorage);
			configStorage.setTicRestMain(restMain);
			// }
			rest.initHttp(configStorage);
			String url = formatUrlParams(restMain.getFdReqURL(), jo, rest,
					configStorage);
			String str = "";
			log.setFdUrl(url);
			if ("get".equals(restMain.getFdReqMethod().toLowerCase())) {
				str = rest.get(url, null, log);
				if(ParamUrlUtil.isEkpListType(req,jo)) {
					str=ListToJSONArrayUtil.listToJSONArrayList(JSONObject.parseObject(str, Feature.OrderedField)).toString();
				}
				log.setFdExportParOri(str);
			}
			if ("post".equals(restMain.getFdReqMethod().toLowerCase())) {
				JSON reqData = jo;
				if (jo.containsKey("body")) {
					JSONObject body = jo.getJSONObject("body");
					if (body.containsKey("arrayTicData")) {
						reqData =  body.getJSONArray("arrayTicData");
					}
				} else {
					if (jo.containsKey("arrayTicData")) {
						reqData = jo.getJSONArray("arrayTicData");
					}
				}
				//判断是否为xml传输
				boolean isXmlReq = false;
				JSONArray headerArray = req.getJSONArray("header");
				for(int i = 0 ; i < headerArray.size(); i++){
					JSONObject headerJo = headerArray.getJSONObject(i);
					String name = headerJo.getString("name");
					if("Content-type".equals(name)){
						String value = headerJo.getString("value");
						if ("application/xml".equals(value)
								|| "text/xml".equals(value)) {
							isXmlReq = true;
						}
					}
				}
				log.setFdImportParOri(reqData.toString());
				if(isXmlReq){//如果为xml传输,需要生成xml字符串信息作为body传输
					// System.out.println(jo.getJSONObject("body"));
					String xmlStr = RecursionUtil.paseJsonCreateXmlString(jo.getJSONObject("body"));
					log.setFdSourceFuncInXml(xmlStr);
					str = rest.post(url, xmlStr, log);
					log.setFdSourceFuncOutXml(str);//返回值同样为xml字符串
					//返回值为xml字符串需要JSON化处理
					str = RecursionUtil.paseXMLTransJson(str);
				}else{
					str = rest.post(url,
							JSON.toJSONStringZ(reqData,
									SerializeConfig.getGlobalInstance(),
									SerializerFeature.QuoteFieldNames),
							log);// jo.toJSONString())
					if(ParamUrlUtil.isEkpListType(req,jo)) {
						str=ListToJSONArrayUtil.listToJSONArrayList(JSONObject.parseObject(str, Feature.OrderedField)).toString();
					}
				}
				log.setFdExportParOri(str);
			}

			Object ret = new Object();
			if (StringUtil.isNotNull(str)) {
				str = str.trim();
				if (str.indexOf("{") == 0) {
					JSONObject o = JSONObject.parseObject(str, Feature.OrderedField);
					ret = o;
					export.put("out", ret);
				} else if (str.indexOf("[") == 0) {
					ret = JSONArray.parseArray(str);
					JSONObject json=new JSONObject(true);
					json.put("arrayTicData", (JSONArray)ret);
					export.put("out", json);
				} else {
					ret = str;
					export.put("out", ret);
				}
			} else {
				export.put("out", new JSONArray());
			}
			logger.warn("export::" + export.toJSONString());
			log.setFdIsErr("0");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			log.setFdExtMsg(ex.getMessage());
			log.setFdMessages(ex.getMessage());
			log.setFdIsErr("1");
			throw ex;
		} finally {
			rest.close();
		}
		return export.toJSONString();
	}

	private void preExecuteLog(TicRestMain restMain, TicCoreLogMain ticLog)
			throws Exception {
		Date startDate = new Date();
		ITicCoreLogManageService ticCoreLogManageService = (ITicCoreLogManageService) SpringBeanUtil
				.getBean("ticCoreLogManageService");
		HQLInfo selectLogTypeHql = new HQLInfo();
		selectLogTypeHql.setSelectBlock("fdLogType");
		Integer logType = 2;
		Integer result = (Integer) ticCoreLogManageService.findFirstOne(selectLogTypeHql);
		if (result == null) {
			logType = result;
		}

		// 日志记录
		ticLog.setFdLogType(restMain.getFdFuncType());
		ticLog.setFdAppType(restMain.getFdAppType());
		ticLog.setFuncName(restMain.getFdName());
		ticLog.setFdType(logType);
		ticLog.setFdStartTime(startDate);
		ticLog.setFuncId(restMain.getFdId());
		ticLog.setFdAppType(restMain.getFdAppType());
	}

	@Override
    public String doTest(String params, TicRestMain restMain)
			throws Exception {
		TicCoreLogMain ticLog = new TicCoreLogMain();
		ticLog.setFdAppType(restMain.getFdAppType());
		try {
			preExecuteLog(restMain, ticLog);
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
		ticLog.setFdExecSource(
				TicCoreLogConstant.TIC_CORE_LOG_SOURCE_TEST + "");

		logger.warn(params);
		JSONObject jo = JSONObject.parseObject(params, Feature.OrderedField);
		JSONObject req = null;
		if(StringUtil.isNotNull(restMain.getFdReqParam())){
			req =  JSONObject.parseObject(restMain.getFdReqParam(), Feature.OrderedField);
		}
		
		RestApiService rest = new RestApiServiceImpl();
		RestInMemoryConfigStorage configStorage = new RestInMemoryConfigStorage();
		handleSetting(restMain, rest, configStorage);
		if (restMain.getFdOauthEnable()) {
			handleOauth(restMain.getTicRestAuth(), rest, configStorage);
		}
		
		handleHeader(req, jo, rest, configStorage);
		configStorage.setTicRestMain(restMain);
		
		//logger.warn(url);
		//logger.warn(jo.toJSONString());
		try {
			rest.initHttp(configStorage);
			String url = formatUrlParams(restMain.getFdReqURL(), jo, rest,
					configStorage);
			ticLog.setFdUrl(url);
			String str = "";

			if ("get".equals(restMain.getFdReqMethod().toLowerCase())) {
				str = rest.get(url, null, ticLog);
				if(ParamUrlUtil.isEkpListType(req,jo)) {
					str=ListToJSONArrayUtil.listToJSONArrayList(JSONObject.parseObject(str,Feature.OrderedField)).toString();
				}
			}
			if ("post".equals(restMain.getFdReqMethod().toLowerCase())) {
				String body = null;
				if(jo.containsKey("body")) {
					try {
						body = jo.getString("body");
						body = new String(Base64.decode(body.getBytes("UTF-8")),"UTF-8");
					} catch (Exception e) {
						body = jo.getJSONObject("body").toString();
					}finally {
						JSONObject copy = JSONObject.parseObject(jo.toString(), Feature.OrderedField);
						JSONObject bodyJson = JSONObject.parseObject(body, Feature.OrderedField);
						copy.put("body", bodyJson);
						ticLog.setFdImportParOri(copy.toString());
					}
					if (jo.containsKey("transType")) {
						// huangwq 转换数据类型
					}
				}else{
					ticLog.setFdImportParOri(jo.toJSONString());
				}
				
				boolean isXmlReq = false;
				JSONArray headerArray = req.getJSONArray("header");
				for(int i = 0 ; i < headerArray.size(); i++){
					JSONObject headerJo = headerArray.getJSONObject(i);
					String name = headerJo.getString("name");
					if("Content-type".equals(name)){
						String value = headerJo.getString("value");
						if ("application/xml".equals(value)
								|| "text/xml".equals(value)) {
							isXmlReq = true;
						}
					}
				}
				if(isXmlReq){//如果为xml传输,需要生成xml字符串信息作为body传输
					String xmlStr = RecursionUtil.paseJsonCreateXmlString(JSON.parseObject(body));
					ticLog.setFdSourceFuncInXml(xmlStr);
					str = rest.post(url, xmlStr, ticLog);
					ticLog.setFdSourceFuncOutXml(str);//返回值同样为xml字符串
					//返回值为xml字符串需要JSON化处理
					str = RecursionUtil.paseXMLTransJson(str);
				}else{
					str = rest.post(url, body, ticLog);
					if(ParamUrlUtil.isEkpListType(req,jo)) {
						str=ListToJSONArrayUtil.listToJSONArrayList(JSONObject.parseObject(str,Feature.OrderedField)).toString();
					}
				}
				
			}
			logger.info("response:" + str);
			//logger.warn(str);
			JSONObject export = new JSONObject(true);
			//JSONArray ret = new JSONArray();
			if (StringUtil.isNotNull(str)) {
				str = str.trim();
				if (str.indexOf("{") == 0 || str.indexOf("[") == 0) {
					if (str.indexOf("{") == 0) {
						JSONObject o = JSONObject.parseObject(str, Feature.OrderedField);
						//ret.add(o);
						export.put("out", o);
					}
					if (str.indexOf("[") == 0) {
						JSONArray ret = JSONArray.parseArray(str);
						JSONObject json=new JSONObject(true);
						json.put("arrayTicData", (JSONArray)ret);
						export.put("out", json);
					}
				} else {
					export.put("out", str);
				}

			} else {
				export.put("out", new JSONArray());
			}
			logger.warn("export::" + export.toJSONString());
			Date endDate = new Date();
			ticLog.setFdEndTime(endDate);
			ticLog.setFdTimeConsuming(String.valueOf(
					endDate.getTime() - ticLog.getFdStartTime().getTime()));
			ticLog.setFdExportParOri(export.toJSONString());
			ticLog.setFdIsErr("0");

			return export.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			ticLog.setFdIsErr("1");
			ticLog.setFdMessages(TicCommonUtil.getExceptionToString(e));
			throw e;
		} finally {
			getTicCoreLogMainService().saveTicCoreLogMain(ticLog);
			rest.close();
		}

	}
	
	
	@Override
    public String doSyncRest(String params, TicRestMain restMain,
                             TicCoreLogMain ticLog)
			throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug(params);
		}
		JSONObject jo = JSONObject.parseObject(params, Feature.OrderedField);
		JSONObject req = null;
		if(StringUtil.isNotNull(restMain.getFdReqParam())){
			req =  JSONObject.parseObject(restMain.getFdReqParam(), Feature.OrderedField);
		}
		
		RestApiService rest = new RestApiServiceImpl();

		RestInMemoryConfigStorage configStorage = new RestInMemoryConfigStorage();
		handleSetting(restMain, rest, configStorage);
		if (restMain.getFdOauthEnable()) {
			handleOauth(restMain.getTicRestAuth(), rest, configStorage);
		}
		
		handleHeader(req, jo, rest, configStorage);
		configStorage.setTicRestMain(restMain);
		

		//logger.warn(url);
		//logger.warn(jo.toJSONString());
		try {
			rest.initHttp(configStorage);
			String url = formatUrlParams(restMain.getFdReqURL(), jo, rest,
					configStorage);
			String str = "";

			if ("get".equals(restMain.getFdReqMethod().toLowerCase())) {
				str = rest.get(url, null, ticLog);
			}
			if ("post".equals(restMain.getFdReqMethod().toLowerCase())) {
				String body = null;
				if(jo.containsKey("body")) {
					try {
						body = jo.getString("body");
						body = new String(Base64.decode(body.getBytes("UTF-8")),"UTF-8");
					} catch (Exception e) {
						body = jo.getJSONObject("body").toString();
					}
					if (jo.containsKey("transType")) {
						// huangwq 转换数据类型
					}
				}
				boolean isXmlReq = false;
				JSONArray headerArray = req.getJSONArray("header");
				for(int i = 0 ; i < headerArray.size(); i++){
					JSONObject headerJo = headerArray.getJSONObject(i);
					String name = headerJo.getString("name");
					if("Content-type".equals(name)){
						String value = headerJo.getString("value");
						if ("application/xml".equals(value)
								|| "text/xml".equals(value)) {
							isXmlReq = true;
						}
					}
				}
				if(isXmlReq){//如果为xml传输,需要生成xml字符串信息作为body传输
					String xmlStr = RecursionUtil.paseJsonCreateXmlString(JSON.parseObject(body));
					str = rest.post(url, xmlStr, ticLog);
					//返回值为xml字符串需要JSON化处理
					str = RecursionUtil.paseXMLTransJson(str);
				}else{
					str = rest.post(url, body, ticLog);
				}
			}
			if(logger.isDebugEnabled()){
				logger.info("response:" + str);
			}
			//logger.warn(str);
			JSONObject export = new JSONObject(true);
			//JSONArray ret = new JSONArray();
			if (StringUtil.isNotNull(str)) {
				str = str.trim();
				if (str.indexOf("{") == 0 || str.indexOf("[") == 0) {
					if (str.indexOf("{") == 0) {
						JSONObject o = JSONObject.parseObject(str, Feature.OrderedField);
						//ret.add(o);
						export.put("out", o);
					}
					if (str.indexOf("[") == 0) {
						JSONArray ret = JSONArray.parseArray(str);
						JSONObject json=new JSONObject(true);
						json.put("arrayTicData", (JSONArray)ret);
						export.put("out", json);
					}
				} else {
					export.put("out", str);
				}

			} else {
				export.put("out", new JSONArray());
			}
			logger.warn("export::" + export.toJSONString());
			// Date endDate = new Date();
			return export.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			rest.close();
		}

	}

	/**
	 * 设置开放授权的参数，参考了微信和钉钉比较标准开放授权，其它的开放授权请实现获取accessToken的接口
	 * 
	 * @param restMain
	 */
	private void handleOauth(TicRestAuth auth, RestApiService rest,
			RestInMemoryConfigStorage configStorage) {
		if(auth!=null) {
			configStorage.setAgentId(auth.getFdAgentId());
			configStorage.setAppId(auth.getFdAgentId());
			if(auth.getFdUseCustAt() != null && auth.getFdUseCustAt()) {
				configStorage.setAccessTokenURL(auth.getFdAccessTokenParam());
			}else {
				 configStorage.setAccessTokenClazz(auth.getFdAccessTokenClazz()); 
			}
		}
	}

	/**
	 * 设置HTTP请求参数
	 * 
	 * @param restMain
	 */
	private void handleSetting(TicRestMain restMain, RestApiService rest,
			RestInMemoryConfigStorage configStorage) {
		if (restMain.getTicRestSetting() == null) {
			return;
		}

		if (StringUtil
				.isNotNull(restMain.getTicRestSetting().getFdHttpProxyHost())) {
			configStorage.setHttpProxyHost(
					restMain.getTicRestSetting().getFdHttpProxyHost());
		}
		if (StringUtil
				.isNotNull(restMain.getTicRestSetting().getFdHttpProxyPort())) {
			configStorage.setHttpProxyPort(Integer.parseInt(
					restMain.getTicRestSetting().getFdHttpProxyPort()));
		}
		if (StringUtil.isNotNull(
				restMain.getTicRestSetting().getFdHttpProxyUsername())) {
			configStorage.setHttpProxyUsername(
					restMain.getTicRestSetting().getFdHttpProxyUsername());
		}
		if (StringUtil.isNotNull(
				restMain.getTicRestSetting().getFdHttpProxyPassword())) {
			configStorage.setHttpProxyPassword(
					restMain.getTicRestSetting().getFdHttpProxyPassword());
		}
		if (restMain.getTicRestSetting().getFdConnRequestTimeout() != null) {
			configStorage.setConnectionRequestTimeout(
					restMain.getTicRestSetting().getFdConnRequestTimeout()
							.intValue());
		}
		if (restMain.getTicRestSetting().getFdConnTimeout() != null) {
			configStorage.setConnectionTimeout(
					restMain.getTicRestSetting().getFdConnTimeout().intValue()
			);
		}
		if (restMain.getTicRestSetting().getFdSoTimeout() != null) {
			configStorage.setSoTimeout(
					restMain.getTicRestSetting().getFdSoTimeout().intValue()
			);
		}

	}

	/**
	 * 设置请求头参数
	 * 
	 * @param restMain
	 */
	private void handleHeader(JSONObject req, JSONObject params,
			RestApiService rest, RestInMemoryConfigStorage configStorage) {
		if(params.containsKey("header")) {//页面传入
			JSONObject h = params.getJSONObject("header");
			JSONArray a = new JSONArray();
			for(String k:h.keySet()) {
				a.add(k+":"+h.getString(k));
			}
			configStorage.setHttpheader(a.toJSONString());
		}else {
			if(req!=null && req.containsKey("header")) {//从请求配置中取
				JSONArray a = new JSONArray();
				JSONArray h = req.getJSONArray("header");
				for(int i=0;i<h.size();i++) {
					JSONObject o = h.getJSONObject(i);
					a.add(o.getString("name")+":"+o.getString("value"));
				}
				configStorage.setHttpheader(a.toJSONString());
			}
		}
	}

	/**
	 * 设置请求头参数
	 * 
	 * @param restMain
	 */
	/*private void handleCookie(TicRestMain restMain, RestApiService rest,
			RestInMemoryConfigStorage configStorage) {
		if (StringUtil.isNull(restMain.getFdReqParam())) {
			return;
		}
		configStorage.setRestMainId(restMain.getFdId());
	}*/

	/**
	 * 替换str中请求参数,参数名用{}区分,请定义的参数名匹配要一致
	 * 
	 * @param params
	 *            函数中的请求参数JSON对象"url":[{name1:"value1"},{name1:"value2"},...]
	 * @return
	 */
	private String formatUrlParams(String url, JSONObject params,
			RestApiService rest, RestInMemoryConfigStorage configStorage) {
		StringBuffer sb = new StringBuffer();
		if(params.containsKey("url")) {
			JSONObject p = params.getJSONObject("url");
			for(String k:p.keySet()) {
				if (url.contains("{" + k + "}")) {
					url = url.replaceAll("\\{" + k + "\\}", p.getString(k));
					continue;
				}
				if(sb.length()>0) {
					sb.append("&");
				}
				sb.append(k+"="+p.getString(k));
			}
		}
		if(sb.length()>0) {
			if(url.indexOf("?")!=-1) {
				url+="&"+sb.toString();
			}else {
				url+="?"+sb.toString();
			}
		}
		if (url.indexOf("ACCESSTOKEN") != -1) {
			if (StringUtil.isNotNull(configStorage.getAccessTokenURL())
					|| StringUtil.isNotNull(
							configStorage.getAccessTokenClazz())) {
				String accessToken;
				try {
					accessToken = rest.getAccessToken(false);
					url = url.replaceAll(RestConstant.REP_ACCESSTOKEN,
							accessToken);
				} catch (RestErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("", e);
				}

			}
		}
		return url;
	}

	
	@Override
	public TicCoreFuncBase findFunc(String fdId) throws Exception {
		// TODO Auto-generated method stub
		TicCoreFuncBase ticCoreFuncBase=null;
		try {
			ticCoreFuncBase=(TicCoreFuncBase) getTicRestMainService().findByPrimaryKey(fdId,null,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticCoreFuncBase;
	}

	@Override
	public TicCoreFuncBase findFuncByKey(String fdKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		//hqlInfo.setSelectBlock("fdFuncType");
		hqlInfo.setWhereBlock("fdKey=:fdKey");
		hqlInfo.setParameter("fdKey", fdKey);
		return (TicCoreFuncBase) getTicRestMainService().findFirstOne(hqlInfo);
	}

	public ITicRestMainService getTicRestMainService() {
		if (ticRestMainService == null) {
			ticRestMainService = (ITicRestMainService) SpringBeanUtil
					.getBean("ticRestMainService");
		}
		return ticRestMainService;
	}

	private ITicRestMainService ticRestMainService;

	private ITicCoreLogMainService ticCoreLogMainService;

	public ITicCoreLogMainService getTicCoreLogMainService() {
		if (ticCoreLogMainService == null) {
			ticCoreLogMainService = (ITicCoreLogMainService) SpringBeanUtil
					.getBean("ticCoreLogMainService");
		}
		return ticCoreLogMainService;
	}

	public void
			setTicCoreLogMainService(
					ITicCoreLogMainService ticCoreLogMainService) {
		this.ticCoreLogMainService = ticCoreLogMainService;
	}

	@Override
	public List<RelationOuterSearchParams> getOutSearchParams(String funcId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeRest(JSONObject jo, String funcId, TicCoreLogMain log)
			throws Exception {
		return execute(jo, funcId, log);
		// logger.debug("rest func in params::" + jo.toJSONString());
		//
		// logger.warn("funcId::" + funcId);
		// JSONObject export = new JSONObject();
		// try {
		// TicRestMain restMain = (TicRestMain) findFunc(funcId);
		// JSONObject req = null;
		// if (StringUtil.isNotNull(restMain.getFdReqParam())) {
		// req = JSONObject.parseObject(restMain.getFdReqParam());
		// }
		//
		// RestApiService rest = new RestApiServiceImpl();
		// RestInMemoryConfigStorage configStorage = new
		// RestInMemoryConfigStorage();
		// handleSetting(restMain, rest, configStorage);
		// if (restMain.getFdOauthEnable()) {
		// handleOauth(restMain.getTicRestAuth(), rest, configStorage);
		// }
		// handleHeader(req, jo, rest, configStorage);
		// handleCookie(restMain, rest, configStorage);
		// rest.initHttp(configStorage);
		// String url = formatUrlParams(restMain.getFdReqURL(), jo, rest,
		// configStorage);
		// String str = "";
		// log.setFdUrl(url);
		// if (restMain.getFdReqMethod().toLowerCase().equals("get")) {
		// str = rest.get(url, null);
		// log.setFdExportParOri(str);
		// }
		// if (restMain.getFdReqMethod().toLowerCase().equals("post")) {
		// str = rest.post(url, jo.toJSONString());
		// log.setFdExportParOri(str);
		// }
		//
		// Object ret = new Object();
		// if (StringUtil.isNotNull(str)) {
		// str = str.trim();
		// if (str.indexOf("{") == 0) {
		// JSONObject o = JSONObject.parseObject(str);
		// ret = o;
		// }
		// if (str.indexOf("[") == 0) {
		// ret = JSONArray.parseArray(str);
		// }
		// export.put("out", ret);
		// } else {
		// export.put("out", new JSONArray());
		// }
		// logger.warn("export::" + export.toJSONString());
		// log.setFdLogType(Integer
		// .parseInt(TicCoreLogConstant.TIC_CORE_LOG_TYPE_SUCCESS));
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// log.setFdLogType(Integer
		// .parseInt(TicCoreLogConstant.TIC_CORE_LOG_TYPE_BIERROR));
		// log.setFdExtMsg(ex.getMessage());
		// }
		// return export.toJSONString();
	}

}
