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
        .box{
            margin: 100px auto 0;
            position: absolute;
            top: 90px;
            left: 50%;
            margin-left: -250px;
            width: 500px;
            height: 400px;
        }
        .bg-edit{
            font-size: 14px;
            color: #999999;
            letter-spacing: 0;
            text-align: center;
            line-height: 20px;
            font-weight: 400;
            margin-top: 4px;
        }
        .edit-nav{
            width: 89px;
            height: 20px;
            font-family: PingFangSC-Regular;
            font-size: 14px;
            color: #4285F4;
            letter-spacing: 0;
            text-align: center;
            line-height: 20px;
            font-weight: 400;
            cursor:pointer;
        }
    </style>
</template:replace>
<template:replace name="content">
    <div class="box">
<div class="bg-pc"></div>
<div class="bg-title">${lfn:message('sys-modeling-main:nav.current.application.not.config') }</div>
<div>
    <p class="bg-edit">${lfn:message('sys-modeling-main:nav.can.do.follow') } <span class="edit-nav" onclick="editNav()">${lfn:message('sys-modeling-main:nav.complete.navigation.configuration') }</span></p>
</div>
    </div>
<script>
    function editNav(){
        var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=appIndex&fdId=${param.fdAppId}#j_path=%2Fspace";
        Com_OpenWindow(url, "_blank");
    }
</script>
</template:replace>
</template:include>
