<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%
    request.setAttribute("themePath", SysUiPluginUtil.getThemePath(request));
%>

<template:include ref="default.simple">
    <template:replace name="title">低代码平台帮助页面</template:replace>
    <template:replace name="head">
    </template:replace>
    <template:replace name="body">
        <link rel="stylesheet" href="./css/vue.css">
        <div id="app"></div>
        <script>
            window.$docsify = {
                name: '',
                repo: '',
                loadSidebar: 'sideBar.md',
                subMaxLevel: 2,
                basePath: "${LUI_ContextPath}/sys/modeling/help/docs",
                homepage: 'home.md',
                // 切换页面后是否自动跳转到页面顶部。
                // auto2top: true
            }
        </script>
        <script src="./js/docsify.min.js"></script>
    </template:replace>
</template:include>
