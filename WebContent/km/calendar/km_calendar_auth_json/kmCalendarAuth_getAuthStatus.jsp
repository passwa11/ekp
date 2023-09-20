<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<json:object name="data">
		<json:property name="authEdit" value="${result.authEdit}"></json:property>
		<json:property name="authModify" value="${result.authModify}"></json:property>
		<json:property name="authRead" value="${result.authRead}"></json:property>
	</json:object>
</json:object>