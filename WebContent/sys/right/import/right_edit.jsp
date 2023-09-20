<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
<c:set var="expand" value="${empty param.expand ? 'false' : param.expand}"/>
<ui:content title="${ lfn:message('sys-right:right.moduleName') }" cfg-order="${order}" cfg-disable="${disable}" expand="${expand }">
	<table class="tb_normal" width=100%>
	<%@ include file="../right_edit.jsp"%>
	</table>
</ui:content>
