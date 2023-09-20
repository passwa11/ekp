<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%
	//给url添加时间戳 解决浏览器缓存
	long date = new Date().getTime();
	request.setAttribute("date", date);
%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
		<ui:dataview id="test-view" format="sys.ui.html">
			<ui:source type="AjaxText" id="test">
			{"url":"/sys/ftsearch/simdoc.jsp?fdId=${param.fdId}&modelName=${param.modelName}&size=${param.size}&date=${date}","formatKey":"html"}
			</ui:source>
		</ui:dataview>
		

		
	