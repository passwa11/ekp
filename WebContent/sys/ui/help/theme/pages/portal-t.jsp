<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="template.t">
    <template:replace name="title">左侧导航模板</template:replace>
    <template:replace name="aside">
        <%@ include file="../jsp/nav.jsp" %>
        <%@ include file="../jsp/changeheader.jsp" %>
        <%@ include file="../jsp/changetemplate.jsp" %>
        <%@ include file="../jsp/usertitle.jsp" %>
        <%@ include file="../jsp/side-nav.jsp" %>
    </template:replace>
    <template:replace name="content">
        <link rel="stylesheet" href="../css/help-theme.css">
        <%@ include file="../jsp/panel.jsp" %>
        <%@ include file="../jsp/tabpanel.jsp" %>
        <%@ include file="../jsp/calendar.jsp" %>
        <%@ include file="../jsp/image-desc.jsp" %>
    </template:replace>
</template:include>