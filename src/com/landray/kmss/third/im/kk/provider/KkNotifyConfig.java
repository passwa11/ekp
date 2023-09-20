package com.landray.kmss.third.im.kk.provider;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;

public class KkNotifyConfig extends BaseAppConfig {
    protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkNotifyConfig.class);
    
    private static final String failTimes_name = "failTimes_";

    private String failTimes = null;


    public KkNotifyConfig() throws Exception {
    	super();
    }

    
	@Override
	public String getJSPUrl() {
		// TODO 自动生成的方法存根
		return null;
	}

	public Map<String, Integer> getFailTimesMap(){
		Map<String, Integer> result = new HashMap<String, Integer>();
		Map<String,String> map = getDataMap();
		if(map==null){
			return result;
		}else{
			for(String key:map.keySet()){
				result.put(key, Integer.parseInt(map.get(key)));
			}
		}
		return result;
	}


	public String getFailTimes(String url) {
		return getValue(url);
	}



	public void setFailTimes(String url, String failTimes) {
		setValue(url, failTimes);
	}
}
