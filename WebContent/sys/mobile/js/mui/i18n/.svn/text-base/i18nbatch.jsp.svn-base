<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%
	try{
		String queue = request.getParameter("queue");
		JSONObject result = new JSONObject();
		if(StringUtil.isNotNull(queue)){
			String[] array = queue.split(";");
			for(String s : array){
				String message = null;
				if(s.indexOf(":") > -1){
					String bundle = s.split(":")[0];
					String key = s.split(":")[1];
					message = ResourceUtil.getString(key, bundle);
				}else{
					message = ResourceUtil.getString(s);
				}
				if(StringUtil.isNotNull(message)){
					result.accumulate(s, message);
				}
			}
		}
		out.print(result);
	}catch(Exception e){
		e.printStackTrace();
		out.print("['error']");
	}
%>