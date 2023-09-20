<%@ page import="com.landray.kmss.third.payment.util.ThirdPaymentPayUtil" %>
<%@ page import="com.landray.kmss.third.payment.interfaces.ThirdPaymentWayVo" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    List<ThirdPaymentWayVo> paymentWays = ThirdPaymentPayUtil.getPaymentWays(request);
    request.setAttribute("paymentWays",paymentWays);
%>

<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="title">${lfn:message('third-payment:payment.pay.setting')}</template:replace>
    <template:replace name="head">
        <script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
        <script type="text/javascript">
            Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
        </script>
        <style type="text/css">
            .tb_normal td {
            //padding: 5px;
            //border: 1px #d2d2d2 solid;
            //word-break: break-all;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <h2 align="center" style="margin: 10px 0">
            <span class="profile_config_title">${lfn:message('third-payment:payment.pay.setting')}</span>
        </h2>

        <html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
            <center>
                <table class="tb_normal" width=95% >
                    <tr>
                        <td class="td_normal_title" width=15%  rowSpan="1">
                                ${lfn:message('third-payment:payment.way')}
                        </td>
                        <c:forEach items="${paymentWays}" var="paymentWay">
                           <td>
                                <div style="height: 20px;padding: 5px;">
                                    <div style="float:left;margin-top: -2px;">
                                        <img src='<c:url value="/third/payment/resource/img/wxworkpay.png" />' />
                                    </div>
                                    <div style="float:left;margin-left: 10px;">
                                        ${paymentWay.name}
                                    </div>
                                    <div style="float:right;width: 100px;">
                                        <div style="float:left;margin-top: 1px;">
                                            <a target="_blank" href="${paymentWay.configUrl}" style="color:blue;">${lfn:message('third-payment:payment.privider.setting')}</a>
                                        </div>
                                        <div style="float:right;">
                                            <ui:switch property="wxpay" showType="show" showText="false" checked="${paymentWay.payEnable}"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
                                        </div>
                                    </div>
                                </div>
                            </td>

                        </c:forEach>
                    </tr>

                    <tr>
                        <td class="td_normal_title" width=15%  rowSpan="2">
                                ${lfn:message('third-payment:payment.service')}
                        </td>
                        <td>
                            <xform:checkbox property="value(paymentService1)">
                                <xform:simpleDataSource value="true">${lfn:message('third-payment:payment.receipt.employee') }</xform:simpleDataSource>
                            </xform:checkbox>
                        </td>
                    </tr>
                    <tr>
                        <td>
                                ${lfn:message('third-payment:payment.pay.nosupport')}
                        </td>
                    </tr>
                </table>

            </center>
            <html:hidden property="method_GET" />
            <input type="hidden" name="modelName" value="com.landray.kmss.third.payment.model.ThirdPaymentConfig" />
            <input type="hidden" name="autoclose" value="false" />

            <center style="margin-top: 10px;">
                <!-- 保存 -->
                <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
            </center>
        </html:form>

        <script type="text/javascript">

        </script>
    </template:replace>
</template:include>

