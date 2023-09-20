<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil"%>
<c:set var="showWindow" value="<%= SysAttConfigUtil.isShowWindow()%>"></c:set>

 var jg_showWindow = "${showWindow}";
