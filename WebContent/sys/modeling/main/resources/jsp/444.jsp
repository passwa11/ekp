<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style>
            .bg-pc {
                margin: 0 auto;
                width: 140px;
                height: 140px;
                background: url(${LUI_ContextPath}/sys/modeling/main/resources/images/application-no-data.png) no-repeat center;
                background-size: 140px 140px;
            }
            .bg-title{
                text-align: center;
                margin: 16px 0px 0px;
                font-weight: bold;
                font-size: 18px;
                color: #666666;
            }
            .box {
                margin: 100px auto 0;
                position: absolute;
                top: 90px;
                left: 50%;
                margin-left: -250px;
                width: 500px;
                height: 400px;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <div class="box">
            <div class="bg-pc"></div>
            <div class="bg-title"> ${lfn:message('sys-modeling-base:module.main.444.msg.title') }</div>
            <div class="bg-title"> ${lfn:message('sys-modeling-base:module.main.444.msg.title.span') }</div>
        </div>
        <%--#169527 这里作为特殊标记，用于建模判断，主要用于ajax请求后，被过滤器拦截直接返回页面时的一些交互判断--%>
        <div flag="module.main.444.flag" style="display: none"></div>
    </template:replace>
</template:include>