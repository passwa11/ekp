<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil"%>

<%
	String _placeholder = request.getParameter("placeholder");
	String _subject = request.getParameter("subject");
	String _mobile = request.getParameter("mobile");
	String _required = request.getParameter("required");
	
	if(StringUtil.isNotNull(_placeholder)){
		request.setAttribute("placeholder", ResourceUtil.getString(_placeholder));
	}
	if(StringUtil.isNotNull(_subject)){
		request.setAttribute("subject", ResourceUtil.getString(_subject));
	}
	request.setAttribute("mobile", StringUtil.isNotNull(_mobile)? _mobile:"false");
	request.setAttribute("required", StringUtil.isNotNull(_required)? _required:"false");
%>

<map:location 
	propertyName="${param.propertyName }"
	nameValue="${param.nameValue }"
	propertyCoordinate="${param.propertyCoordinate }"
	coordinateValue="${param.coordinateValue }"
	propertyDetail="${param.propertyDetail }"
	detailValue="${param.detailValue }"
	isLoadDataDict="${param.isLoadDataDict }"
	id="${param.id }"
	mobile="${mobile }"
	placeholder="${placeholder}"
	required="${required }"
	showStatus="${param.showStatus }"
	style="${param.style }"
	subject="${subject}"
	validators="${param.validators }">
</map:location>