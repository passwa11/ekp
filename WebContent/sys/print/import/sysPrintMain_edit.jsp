<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:if test="${not empty mainModelForm.printTemplateId }">
	<input type="hidden" name="printTemplateId"  value="${mainModelForm.printTemplateId }"/>
</c:if>