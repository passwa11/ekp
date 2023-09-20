package com.landray.kmss.tic.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fdOriParamIn = "[{\"name\":\"pname\",\"title\":\"名称\",\"type\":\"string\",\"children\":\"\"},{\"name\":\"pcode\",\"title\":\"编号\",\"type\":\"string\",\"children\":\"\"},{\"name\":\"pmembers\",\"title\":\"成员\",\"type\":\"array\",\"children\":\"[{\\\"name\\\":\\\"mname\\\",\\\"title\\\":\\\"成员名称\\\",\\\"type\\\":\\\"string\\\",\\\"children\\\":\\\"\\\"},{\\\"name\\\":\\\"mcode\\\",\\\"title\\\":\\\"成员编号\\\",\\\"type\\\":\\\"string\\\",\\\"children\\\":\\\"\\\"}]\"}]";
		JSONArray params = new  JSONArray();
		if(StringUtil.isNotNull(fdOriParamIn)) {
			JSONArray oriParams = JSONArray.fromObject(fdOriParamIn);
			for(int i=0;i<oriParams.size();i++) {
				JSONObject oriParam = oriParams.getJSONObject(i);
				JSONObject param = new JSONObject();
				param.accumulate("name", oriParam.getString("name"));
				param.accumulate("title", oriParam.getString("title"));
				param.accumulate("type", oriParam.getString("type"));
				recursionPaseData(param, oriParam.getString("children"));
				params.add(param);
			}
		}
		System.out.println(params.toString(2));
		
//		String msg = "PerformanceManager{第1个中括号}Product{第2个中括号}<{第3个中括号}79~";
//		List<String> list = extractMessage(msg);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(i+"-->"+list.get(i));
//		}

//		String s="1{";
//		System.out.println(s.indexOf("["));
	}

	public static void recursionPaseData(JSONObject param, String childern) {
		if(StringUtil.isNotNull(childern)) {
			JSONArray cp = new  JSONArray();
			JSONArray array = JSONArray.fromObject(childern);
			for(int i=0;i<array.size();i++) {
				JSONObject op = array.getJSONObject(i);
				JSONObject p = new JSONObject();
				p.accumulate("name", op.getString("name"));
				p.accumulate("title", op.getString("title"));
				p.accumulate("type", op.getString("type"));
				recursionPaseData(p,op.getString("children"));
				cp.add(p);
			}
			param.put("children", cp);
		}else {
			param.put("childern", new JSONArray());
		}
	
	}
	
	
	public static List<String> extractMessage(String msg) {
		 
		List<String> list = new ArrayList<String>();
		int start = 0;
		int startFlag = 0;
		int endFlag = 0;
		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '{') {
				startFlag++;
				if (startFlag == endFlag + 1) {
					start = i;
				}
			} else if (msg.charAt(i) == '}') {
				endFlag++;
				if (endFlag == startFlag) {
					list.add(msg.substring(start + 1, i));
				}
			}
		}
		return list;
	}


}
