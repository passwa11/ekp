<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
</script>
<c:set var="version_MainForm" value="${requestScope[param.formName]}"/>
<c:set var="componentLockerVersionForm" value="${version_MainForm.componentLockerVersionForm}"/>
<%--
	<html:hidden property="componentLockerVersionForm.fdId"/> 
	<html:hidden property="componentLockerVersionForm.fdKey"/>
	<html:hidden property="componentLockerVersionForm.fdModelName"/>
	<html:hidden property="componentLockerVersionForm.fdModelId"/> 
	<html:hidden property="componentLockerVersionForm.fdVersion"/> 
--%>

<input type="hidden" name="componentLockerVersionForm.fdId" value="<c:out value="${componentLockerVersionForm.fdId}"/>">
<input type="hidden" name="componentLockerVersionForm.fdKey" value="<c:out value="${componentLockerVersionForm.fdKey}"/>">
<input type="hidden" name="componentLockerVersionForm.fdModelName" value="<c:out value="${componentLockerVersionForm.fdModelName}"/>">
<input type="hidden" name="componentLockerVersionForm.fdModelId" value="<c:out value="${componentLockerVersionForm.fdModelId}"/>">
<input type="hidden" name="componentLockerVersionForm.fdVersion" value="<c:out value="${componentLockerVersionForm.fdVersion}"/>">

