<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.common.service.IXMLDataBean"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<%
	IXMLDataBean bean = (IXMLDataBean) SpringBeanUtil
			.getBean("sysPropertyOptionListService");

	out.print(JSONArray
			.fromObject(bean.getDataList(new RequestContext(request))));
%>


