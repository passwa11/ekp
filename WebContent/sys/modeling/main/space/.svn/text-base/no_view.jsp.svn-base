<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <template:replace name="title">选择流程</template:replace>
    <template:replace name="head">
        <style>
            .space-not-content {
                width: 250px;
                font-size: 0;
                position: absolute;
                left: 50%;
                transform: translateX(-50%);
                text-align: center;
            }
            .space-not-div{
                position: relative;
                height: 200px;
            }
            .space-images{
                width: 100%;
                height: 200px;
                background: url(../resources/images/not-content.png) no-repeat center;
                background: url(../resources/images/not-content@2x.png) no-repeat center \9;
                background-position: 50% 50%;
                background-size: contain;
                margin-left: 5px;
            }
            .space-tips{
                width: 100%;
                font-size: 14px;
                line-height: 12px;
                color: #333333;
                margin-top: 10px
            }
        </style>
    </template:replace>
    <template:replace name="body">
        <div class="space-not-div">
        <div class="space-not-content">
            <div class="space-images"></div>
            <div class="space-tips">所选视图不存在或已被删除</div>
        </div>
        </div>
    </template:replace>
</template:include>