<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="title">
	<c:out value="${kmReviewMainForm.docSubject}-${ lfn:message('km-review:module.km.review')}"></c:out>
</template:replace>
<template:replace name="head">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/third/ding/third_ding_xform/resource/css/ding.css">
</template:replace>