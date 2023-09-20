package com.landray.kmss.third.ding.oms;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.Date;

public class DingOmsConfig extends BaseAppConfig{

	public DingOmsConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "";
	}
	
	public void setLastUpdateTime(String lastUpdateTime) {
		setValue("lastUpdateTime", lastUpdateTime);
	}
	
	public String getLastUpdateTime() {
		return getValue("lastUpdateTime");
	}

	public String getCallbackRouteKey() {
		return getValue("callbackRouteKey");
	}
	public void setCallbackRouteKey(String callbackRouteKey) {
		setValue("callbackRouteKey", callbackRouteKey);
	}

	public String getCallbackApiSecrect() {
		return getValue("callbackApiSecrect");
	}
	public void setCallbackApiSecrect(String callbackApiSecrect) {
		setValue("callbackApiSecrect", callbackApiSecrect);
	}

	public void setHasCheckDing(String hasCheckDing) {
		setValue("hasCheckDing", hasCheckDing);
	}

	public String getHasCheckDing() {
		return getValue("hasCheckDing");
	}


	public String getSyncThreadSize(){
		String syncThreadSize = getValue("syncThreadSize");
		if(StringUtil.isNull(syncThreadSize)){
			return "3";
		}
		return syncThreadSize;
	}

	public void setSyncThreadSize(String syncThreadSize){
		setValue("syncThreadSize", syncThreadSize);
	}

	/**
	 * 获取全员推送的次数
	 */
	public String getHadSendAllNotifyNums() {
		String val = getValue("hadSendAllNotifyNums");
		if(StringUtil.isNotNull(val)){
			JSONObject data = JSONObject.fromObject(val);
			String date = data.getString("date");
			if(DateUtil.convertDateToString(new Date(),"yyyy-MM-dd").equals(date)){
				return String.valueOf(data.getInt("num"));
			}else{
				//非今天数据清空值
				setValue("hadSendAllNotifyNums", "");
			}
		}
		return "0";
	}

	/**
	 * 全员推送的次数叠加
	 */
	public void addHadSendAllNotifyNums() {
		JSONObject rs = new JSONObject();
		rs.put("date",DateUtil.convertDateToString(new Date(),"yyyy-MM-dd"));
		int num = 1;
		String val = getValue("hadSendAllNotifyNums");
		if(StringUtil.isNotNull(val)){
			JSONObject data = JSONObject.fromObject(val);
			//如果是今天的日期，则次数加1
			if(DateUtil.convertDateToString(new Date(),"yyyy-MM-dd").equals(data.getString("date"))){
				num=data.getInt("num")+1;
			}
		}
		rs.put("num",num);
		setValue("hadSendAllNotifyNums", rs.toString());
	}

}
