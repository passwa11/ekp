<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
<template:replace name="head">
    <style>
        .bg-pc {
            margin: 0 auto;
            width: 140px;
            height: 140px;
            background: url(resources/images/application-no-data.png) no-repeat center;
            background-size: 140px 140px;
        }
        .bg-title{
            text-align: center;
            margin: 16px 0px 0px;
            font-weight: bold;
            font-size: 18px;
            color: #666666;
        }
    </style>
</template:replace>
<template:replace name="content">
    <div class="box">
<div class="bg-pc"></div>
<div class="bg-title">${lfn:message('sys-modeling-base:modelingDbchecker.run.checking') }</div>
    </div>
</template:replace>
</template:include>
