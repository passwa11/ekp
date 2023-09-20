<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String ssoContent = request.getAttribute("ssoContent").toString();
	Object type = request.getAttribute("type");
	if (StringUtil.isNotNull(ssoContent)&&ssoContent.indexOf("text/html")>=0) {
		out.println(ssoContent);
	}else{
		if(type!=null){
			if("mobile".equals(type.toString())){
				out.println("<script>alert('当前人无法查看相关订单');</script>");
			}else{
				out.println("<script>alert('当前人无法查看相关订单');window.close();</script>");
			}
		}else{
			out.println("<script>alert('当前人无法查看相关订单');window.close();</script>");
		}
	}
%>