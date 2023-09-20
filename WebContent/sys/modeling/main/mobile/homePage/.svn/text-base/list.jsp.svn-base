<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String designCfg = (String)request.getAttribute("designCfg");
	String fdMobileId = request.getParameter("fdMobileId");
	if(StringUtil.isNotNull(designCfg)){
		JSONObject cfg = JSONObject.fromObject(designCfg);
		JSONObject value = new JSONObject();
		//列表式首页旧数据兼容，现在需要支持多列表。需要传fdMobileId 加上area做区分
		try{
			JSONArray values = cfg.getJSONObject("list").getJSONObject("attr").getJSONObject("listView").getJSONArray("value");
			value = values.getJSONObject(0);

		}catch (Exception e){
			value = cfg.getJSONObject("list").getJSONObject("attr").getJSONObject("listView").getJSONObject("value");
		}
		if(value.isEmpty()){
			out.print("<div>请联系管理员配置移动首页设置！</div>");
		}else{
			String redirectto = "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId="
					+ value.optString("listView","") + "&nodeType=" + value.optString("nodeType","")+"&area=list&fdMobileId="+fdMobileId;
			request.setAttribute("redirectto", redirectto);

%>
<c:redirect url="${redirectto}" />
		<% }
	}else{
		out.print("<div>请联系管理员配置移动首页设置！</div>");
	}
%>