<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="order" value="${empty param.order ? '65' : param.order}"/>
<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
<ui:content title="${lfn:message('km-imeeting:kmImeetingMain.control.tab')}" titleicon="${not empty param.titleicon?param.titleicon:''}" cfg-order="${order}" cfg-disable="${disable}">
	<%@include file="/km/imeeting/km_imeeting_main/import/kmImeeting_control_include.jsp" %>
</ui:content>