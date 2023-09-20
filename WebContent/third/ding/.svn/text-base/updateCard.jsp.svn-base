<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    if (UserUtil.getKMSSUser().isAdmin()) {
%>
<template:include ref="config.profile.edit" sidebar="no">

    <template:replace name="title">测试更新卡片功能</template:replace>
    <template:replace name="content">

        <h2 align="center" style="margin:10px 0">
            <span class="profile_config_title">测试更新卡片功能</span>
        </h2>
        <html:form action="/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=testSendCard&isUpdate=true" method="post">
            <table id="dingBaseTable" class="tb_normal" width=100%>
                <tr>
                    <td>标题</td>
                    <td colspan="3">
                        <xform:text property="title" style="width:85%;" value=""  showStatus="edit" required="true" subject="标题" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">模块</td>
                    <td  width="35%">
                        <xform:text property="fdModelName" style="width:85%;" value="com.landray.kmss.km.review.model.KmReviewMain"  showStatus="edit" required="true"  subject="模块" />
                    </td>
                    <td class="td_normal_title" width="15%">文档ID</td>
                    <td  width="35%">
                        <xform:text property="fdModelId" style="width:85%;" showStatus="edit" required="true"  subject="文档ID" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">私有数据</td>
                    <td  width="35%">
                        <xform:textarea showStatus="edit" property="privateData" value='{
    "handler": {
        "审批人fdId": {
            "canOperate": "Y"
        }
    },
    "other": {
        "showStatus": "Y"
    }
}' style="width:95%;height:300px"/>
                    </td>
                    <td class="td_normal_title" width="15%">公有数据</td>
                    <td  width="35%">
                        <xform:textarea showStatus="edit" property="publicData" value='{
  "key":"value"
}' style="width:95%;height:300px"/>
                    </td>
                </tr>

            </table>


            <center>
                <br><br>
                <ui:button text="${ lfn:message('button.save') }" onclick="if($KMSSValidation().validate()){Com_Submit(document.thirdDingCardConfigForm, 'testSendCard');}" />
            </center>

        </html:form>

    </template:replace>
</template:include>
<%
    }else{
    out.print("只有超级管理员才能访问该页面");
    }
%>