<%@page import="com.landray.kmss.util.KmssReturnPage"%>
<%@page import="com.landray.kmss.util.KmssMessage"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%
KmssReturnPage rtnPage = (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE");
String errorcode = "029999";
String errmsg = null;
if(rtnPage!=null){
	List<KmssMessage> msgList = rtnPage.getMessages().getMessages();
	if(msgList.size()>0){
		KmssMessage msg = msgList.get(0);
		errmsg = msg.getMessageKey();
		if(msg.getMessageType() != 0 && msg.getMessageType() != 1){
			String msgTypeNumber = String.valueOf(msg.getMessageType());
			String code = "029000";
			errorcode = code.substring(0,6 - msgTypeNumber.length()) + msgTypeNumber;
		}
	}
}
if(errmsg !=null){
	request.setAttribute("errcode", errorcode);
	request.setAttribute("errmsg", errmsg);
}
%>
<json:object>
	<json:property name="errcode" value="${errcode }"></json:property>
	<json:property name="errmsg" value="${errmsg }"></json:property>
</json:object>