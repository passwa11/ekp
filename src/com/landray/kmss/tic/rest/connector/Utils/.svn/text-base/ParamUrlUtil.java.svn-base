package com.landray.kmss.tic.rest.connector.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.tic.rest.connector.TicRestConstants;

public class ParamUrlUtil {

	
	/**
	 * 是否ekp列表配置
	 * @param params
	 * @return
	 */
	public static boolean isEkpListType(JSONObject req,JSONObject params) {
		boolean flag=false;
		if(params!=null && params.containsKey("header")) {//页面传入
			JSONObject h = params.getJSONObject("header");
			for(String k:h.keySet()) {
				if(TicRestConstants.INTEGRATE_TYPE.equals(k)){
					flag=isEkpList(k,h.getString(k));
					break;
				}
			}
		}else {
			if(req!=null && req.containsKey("header")) {//从请求配置中取
				JSONArray h = req.getJSONArray("header");
				for(int i=0;i<h.size();i++) {
					JSONObject o = h.getJSONObject(i);
					if(TicRestConstants.INTEGRATE_TYPE.equals(o.getString("name"))){
						flag=isEkpList(o.getString("name"),o.getString("value"));
						break;
					}
					
				}
			}
		}
		return flag;
	}
	
	private static boolean isEkpList(String key,String value){
		if(TicRestConstants.INTEGRATE_TYPE.equals(key)
				&&TicRestConstants.EKP_LIST_TYPE.equals(value)){
			return true;
		}else {
			return false;
		}
	}
}
