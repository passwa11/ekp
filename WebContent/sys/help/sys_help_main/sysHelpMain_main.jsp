<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<!-- 根据传入的json，构建跳转后的帮助页面，本页面为公共页面 -->
<template:include
		ref="slide.view"
		leftWidth="225"
		leftShow ="yes"
		leftBar="yes">
    <template:replace name="head">
        <%-- <link rel="stylesheet" href="${LUI_ContextPath}/sys/help/sys_help_main/css/sysHelpMain_view.css" type="text/css" />
        <%@ include file="/sys/help/sys_help_main/sysHelpMain_view_js.jsp"%> --%>
    </template:replace>
    <template:replace name="title">
        <c:out value="${sysHelpMainForm.docSubject}" />
    </template:replace>
    
    <template:replace name="toolbar">
    
    </template:replace>
    
    <template:replace name="barLeft">
		
    </template:replace>
    
    <template:replace name="content">
    
    </template:replace>

</template:include>