<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style>
            .bg-pc {
                margin: 0 auto;
                width: 140px;
                height: 140px;
                background: url(../main/resources/images/application-no-data.png) no-repeat center;
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
            <%--停用应用提示语--%>
            <c:if test="${ msgForBiddenTitle eq '04'}">
                <div class="bg-title"> ${lfn:message('sys-modeling-base:module.base.445.msg.forbidden.title') }</div>
            </c:if>
                <%--禁用应用提示语--%>
            <c:if test="${ msgForBiddenTitle eq '02' }">
                <div class="bg-title"> ${lfn:message('sys-modeling-base:module.base.445.msg.title') }</div>
            </c:if>

        </div>
    </template:replace>
</template:include>