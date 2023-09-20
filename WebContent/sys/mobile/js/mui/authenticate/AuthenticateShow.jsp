<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<c:set var="status" value="false" scope="request"></c:set>
<kmss:authShow
		roles="${param.roles}" 
		authAreaIds="${param.authAreaIds}" 
		baseEmptyShow="${param.baseEmptyShow }"
		baseOrgElements="${param.baseOrgElements }"
		baseOrgIds="${param.baseOrgIds }"
		extendOrgElements="${param.extendOrgElements }"
		extendOrgIds="${param.extendOrgIds }">
	<c:set var="status" value="true" scope="request"></c:set>
</kmss:authShow>
<%
	//设置缓存
	long expires = 7 * 24 * 60 * 60;
	long nowTime = System.currentTimeMillis();
	response.addDateHeader("Last-Modified", nowTime + expires);
	response.addDateHeader("Expires", nowTime + expires * 1000);
	response.addHeader("Cache-Control", "max-age=" + expires);
	JSONObject json = new JSONObject();
	json.accumulate("status", request.getAttribute("status").toString());
	out.print(json.toString());
%>