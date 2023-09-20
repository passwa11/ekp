package com.landray.kmss.third.im.kk.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.im.kk.constant.KkNotifyConstants;
import com.landray.kmss.third.im.kk.model.KkImNotify;
import com.landray.kmss.third.im.kk.service.IKkImNotifyService;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <P>线程向kk推送消息</P>
 * @author 孙佳
 */
public class KkNotifyTaskRunner implements Runnable {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkNotifyTaskRunner.class);

	private Integer sendType;

	private String url;

	private IKkImNotifyService kkImNotifyService;

	public IKkImNotifyService getKkImNotifyService() {
		if (kkImNotifyService == null) {
			kkImNotifyService = (IKkImNotifyService) SpringBeanUtil.getBean("kkImNotifyService");
		}
		return kkImNotifyService;
	}

	public KkNotifyTaskRunner() {
	}

	public KkNotifyTaskRunner(Integer sendType, String url) {
		this.sendType = sendType;
		this.url = url;
		kkImNotifyService = getKkImNotifyService();
	}

	/**
	 * <p>向kk推送消息通知</p>
	 * @param title
	 * @param url
	 * @param jsonObjects
	 * @throws Exception
	 * @author 孙佳
	 */
	private void executePostDataKK5(HttpClient httpClient, String title, String url, String jsonObjects,
			String notifyId, Integer notifyType)
			throws Exception {
		logger.debug("executePostDataKK5:" + jsonObjects);
		if (jsonObjects.isEmpty()) {
			return;
		}
		KkPostDataRunner runner = new KkPostDataRunner(httpClient, url);
		runner.postKK5JsonData(title, jsonObjects, notifyId, notifyType);
	}

	/**
	 * <p>创建httpclient</p>
	 * @return
	 * @author 孙佳
	 */
	private HttpClient createHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置超时
		httpClient.setConnectionTimeout(15000);
		httpClient.setTimeout(15000);

		return httpClient;
	}


	/**
	 * <p>查询未发送成功的消息</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private List<?> getNotify() throws Exception {
		synchronized (kkImNotifyService) {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer sbf = new StringBuffer();
			sbf.append(" 1 = 1 and");
			if (!sendType.equals(KkNotifyConstants.SNED_NOTIFY__TODO)) {
				sbf.append(" fdType !=" + KkNotifyConstants.SNED_NOTIFY__TODO + " and");
			}
			sbf.append(" fdStatus = 0");
			sbf.append(" GROUP BY fdNotifyId, fdType");

			hqlInfo.setSelectBlock(" kkImNotify.fdNotifyId, kkImNotify.fdType "); //兼容sql server
			hqlInfo.setWhereBlock(sbf.toString());
			hqlInfo.setCacheable(true);
			List<?> list = kkImNotifyService.findValue(hqlInfo);

			//更新记录状态为发送中
			updateNotifyStatus(list);

			return list;
		}
	}
	
	private void updateNotifyStatus(List<?> list){
		for(int i=0; i<list.size(); i++){
			Object[] info = (Object[]) list.get(i);
			kkImNotifyService.updateStatus(info[0].toString(), Integer.valueOf(info[1].toString()), KkNotifyConstants.NOTIFY_STATUS_USE);
		}
	}


	private List<JSONObject> buildNotifyJson(List<KkImNotify> list) {
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

		JSONObject jsonObject = new JSONObject();
		JSONArray notifyUsers = new JSONArray();
		for(KkImNotify imNotify : list){
			jsonObject = JSONObject.fromObject(imNotify.getFdNotifyData());
			JSONObject object = JSONObject.fromObject(imNotify.getFdNotifyData());
			notifyUsers.add(object.get("users"));

			if (notifyUsers.size() % 100 == 0) {
				jsonObject.put("users", notifyUsers);
				jsonObjects.add(jsonObject);
				notifyUsers = new JSONArray();
			}
		}
		if (notifyUsers.size() > 0) {
			jsonObject.put("users", notifyUsers);
			jsonObjects.add(jsonObject);
		}
		logger.info("buildNotifyJson==" + jsonObjects);
		return jsonObjects;
	}

	private List<JSONObject> buildNotifyCountJsons(List<KkImNotify> list) {
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

		JSONObject jsonObject = new JSONObject();
		JSONArray notifyUsers = new JSONArray();
		JSONArray alert = new JSONArray();
		JSONArray version = new JSONArray();
		JSONArray lang = new JSONArray();
		for (KkImNotify imNotify : list) {
			jsonObject = JSONObject.fromObject(imNotify.getFdNotifyData());
			JSONObject object = JSONObject.fromObject(imNotify.getFdNotifyData());
			notifyUsers.add(object.get("users"));
			alert.add(object.get("alert"));
			version.add(object.get("version"));
			lang.add(object.get("lang"));

			if (notifyUsers.size() % 100 == 0) {
				jsonObject.put("users", notifyUsers);
				jsonObject.put("alert", alert);
				jsonObject.put("version", version);
				jsonObject.put("lang", lang);
				jsonObjects.add(jsonObject);
				notifyUsers = new JSONArray();
				alert = new JSONArray();
				version = new JSONArray();
				lang = new JSONArray();
			}
		}

		if (notifyUsers.size() > 0) {
			jsonObject.put("users", notifyUsers);
			jsonObject.put("alert", alert);
			jsonObject.put("version", version);
			jsonObject.put("lang", lang);
			jsonObjects.add(jsonObject);
		}
		logger.info("buildNotifyCountJsons==" + jsonObjects);
		return jsonObjects;
	}

	/**
	 * <p>组装发送通知JSON</p>
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private List<Map<String, Object>> buildNotifyJson() throws Exception {
		List<?> notifyList = getNotify();

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		HQLInfo hqlInfo = null;
		String fdNotifyId = null;
		Integer fdType = null;
		Object[] info = null;
		if(null != notifyList && notifyList.size() > 0){
			for (int i=0; i<notifyList.size(); i++) {
				info = (Object[]) notifyList.get(i);
				fdNotifyId = info[0].toString();
				fdType = Integer.valueOf(info[1].toString());
				
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(" fdNotifyId =:fdNotifyId and fdType = :fdType");
				hqlInfo.setParameter("fdNotifyId", fdNotifyId);
				hqlInfo.setParameter("fdType", fdType);
				List<KkImNotify> list = kkImNotifyService.findList(hqlInfo);

				Map<String, Object> map = null;
				if (KkNotifyConstants.SNED_NOTIFY__TODO.equals(fdType)) {
					//组装待办

					List<JSONObject> todoJson = buildNotifyJson(list);

					for (JSONObject json : todoJson) {
						map = new HashMap<String, Object>();
						map.put("notifyData", json.toString());
						map.put("sendUrl", url.trim() + KkNotifyConstants.PUSH_USER_URL);
						map.put("notifyId", fdNotifyId);
						map.put("type", fdType);
						map.put("subject", list.get(0).getFdSubject());
						listMap.add(map);
					}

				} else if (KkNotifyConstants.UPDATE_NOTIFY__TODO_NUM.equals(fdType)) {
					//组装更新数量

					List<JSONObject> todoCountJson = buildNotifyCountJsons(list);

					for (JSONObject json : todoCountJson) {
						map = new HashMap<String, Object>();
						map.put("notifyData", json.toString());
						map.put("sendUrl", url.trim() + KkNotifyConstants.PUSH_BADGEBATCH2_URL);
						map.put("notifyId", fdNotifyId);
						map.put("type", fdType);
						map.put("subject", list.get(0).getFdSubject());
						listMap.add(map);
					}
				}
			}
		}
		return listMap;
	}

	

	@Override
    public void run() {
		logger.info("run----------------------------------------------");
		try {
			List<Map<String, Object>> list = buildNotifyJson();
			if (null != list && list.size() > 0) {
				HttpClient httpClient = createHttpClient();
				for (Map<String, Object> map : list) {
					executePostDataKK5(httpClient, map.get("subject").toString(), map.get("sendUrl").toString(),
							map.get("notifyData").toString(), map.get("notifyId").toString(), (Integer)map.get("type"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
