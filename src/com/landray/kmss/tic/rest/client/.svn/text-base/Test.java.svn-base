package com.landray.kmss.tic.rest.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.util.TextUtils;

import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.rest.client.api.RestApiService;
import com.landray.kmss.tic.rest.client.api.impl.RestApiServiceImpl;
import com.landray.kmss.tic.rest.client.config.RestInMemoryConfigStorage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestApiService rest = new RestApiServiceImpl();
		RestInMemoryConfigStorage configStorage = new RestInMemoryConfigStorage();
		configStorage.setAppId("3010011");
		//configStorage.setAgentId(3010011);
		configStorage.setCorpId("wx58c49b09c2342f56");
		configStorage.setCorpSecret("GAAVZGYu1gHioPdPhNv7ru_xE6clSmFmDibPUr9qQlU");
		configStorage.setToken("B8ErIQGbjMZVhhQV77MAfVB98KrAu");
		configStorage.setAesKey("Q6eh3DI9UYzQilOlsudJN5fSNlzaHBUotLtsV31r17f");
		configStorage.setAccessTokenURL("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={CORPID}&corpsecret={CORPSECRET}");
		rest.initHttp(configStorage);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token={ACCESSTOKEN}";
		JSONObject o = new JSONObject();
		o.accumulate("opencheckindatatype", 3);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -5);
		o.accumulate("starttime", cal.getTime().getTime()/1000);
		o.accumulate("endtime",new Date().getTime()/1000);
		//o.accumulate("starttime", (DateUtil.convertStringToDate("2018-10-01", "yyyy-MM-dd").getTime()/1000));
		//o.accumulate("endtime", (DateUtil.convertStringToDate("2018-10-19", "yyyy-MM-dd").getTime()/1000));
		JSONArray us = new JSONArray();
		us.add("wub");
		us.add("luoc");
		us.add("fanj");
		o.accumulate("useridlist", us);
		System.out.println(o.toString(2));
		//String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token={ACCESSTOKEN}";	
		try { 
			String r = rest.post(url, o.toString(), new TicCoreLogMain());
			//String r = rest.get(url,null);
			//System.out.println(r);
			Map<String,List> records = new HashMap<String,List>();
			JSONObject jo = JSONObject.fromObject(r);
			if(jo.getInt("errcode")==0) {
				JSONArray ja = jo.getJSONArray("checkindata");
				for(int i=0;i<ja.size();i++) {
					JSONObject job = ja.getJSONObject(i);
					int ct = job.getInt("checkin_time");
					String s = TimeStamp2Date(""+ct,null);
					String d = s.substring(0, 10);
					String t = s.substring(10,s.length());
					String usid = job.getString("userid");
					String key = usid+"_"+d;
					if(records.containsKey(key)) {
						List day = records.get(key);
						Map<String,String> map = new HashMap<String,String>();
						map.put("checkin_type", job.getString("checkin_type"));
						map.put("exception_type", job.getString("exception_type"));
						map.put("checkin_d", d);
						map.put("checkin_t", t);
						map.put("location_title", job.getString("location_title"));
						map.put("location_detail", job.getString("location_detail"));
						map.put("wifiname",  job.getString("wifiname"));
						day.add(map);
					}else {
						List day = new ArrayList();
						Map<String,String> map = new HashMap<String,String>();
						map.put("checkin_type", job.getString("checkin_type"));
						map.put("exception_type", job.getString("exception_type"));
						map.put("checkin_d", d);
						map.put("checkin_t", t);
						map.put("location_title", job.getString("location_title"));
						map.put("location_detail", job.getString("location_detail"));
						map.put("wifiname",  job.getString("wifiname"));
						day.add(map);
						records.put(key, day);
					}
				}
				JSONObject jj = JSONObject.fromObject(records);
				System.out.println(jj.toString(2));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

}
