<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %> 
<json:object prettyPrint="true">
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<json:array name="data" items="${queryPage}" var="label">
		<json:object>
			<json:property name="fdId" value="${label.fdId }"></json:property>
			<json:property name="fdName" value="${label.fdName }"></json:property>
			<json:property name="fdDescription" value="${label.fdDescription }"></json:property>
			<json:property name="fdColor" value="${label.fdColor }"></json:property>
		</json:object>
	</json:array>	
</json:object>