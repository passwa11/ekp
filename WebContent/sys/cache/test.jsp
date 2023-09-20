<%@page import="com.landray.kmss.sys.cache.test.CacheFunctionTest"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.sys.cache.KmssCache"%>
<%@page import="com.landray.kmss.sys.cache.CacheLoader"%>
<%@page import="com.landray.kmss.sys.cache.CacheConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	String step = request.getParameter("step");
	if("1".equals(step)){
		CacheConfig.get(CacheFunctionTest.class).setCacheLoader(new CacheFunctionTest.CacheFunctionTestLoader());
	}else if("2".equals(step)){
		CacheConfig.get(CacheFunctionTest.class).setCacheLoader(null);
	}else if("3".equals(step)){
		KmssCache cache = new KmssCache(CacheFunctionTest.class);
		cache.update("k1");
		cache.remove("k2");
		cache.clear();
	}
	String now = new Date().toString();
	System.out.println(now);
	out.println(now);
%>