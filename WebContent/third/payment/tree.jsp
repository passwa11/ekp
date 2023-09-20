<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("third-payment:module.third.payment") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot;

    var node = LKSTree.treeRoot;
    node.isExpanded = true;
    var payManager = node.AppendChild("${ lfn:message("third-payment:payment.weixin.thirdPayment") }");
    payManager.AppendURLChild(
        "<bean:message key="table.thirdPaymentOrder" bundle="third-payment"/>",
        '<c:url value="/third/payment/third_payment_order/list.jsp"/>'
    );

    node.AppendURLChild(
    "<bean:message key="table.thirdPaymentMerchant" bundle="third-payment"/>",
    '<c:url value="/third/payment/third_payment_merchant/list.jsp"/>'
    );

    node.AppendURLChild(
    "<bean:message key="payment.setting" bundle="third-payment"/>",
    '<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.payment.model.ThirdPaymentConfig"/>'
    );

    node.AppendURLChild(
    "<bean:message key="table.thirdPaymentCallLog" bundle="third-payment"/>",
    '<c:url value="/third/payment/third_payment_call_log/list.jsp"/>'
    );

    <kmss:ifModuleExist path="/third/weixin">
        var node_weixin = node.AppendURLChild(
        "<bean:message key="payment.wxwork" bundle="third-payment"/>"
        );
        /*支付订单*/
        <kmss:auth requestURL="/third/weixin/third_weixin_pay_order/index.jsp" requestMethod="GET">
            node_weixin.AppendURLChild(
            '${ lfn:message("third-weixin:table.thirdWeixinPayOrder") }',
            '<c:url value="/third/weixin/third_weixin_pay_order/index.jsp"/>');
        </kmss:auth>
        /*支付接口调用日志*/
        <kmss:auth requestURL="/third/weixin/third_weixin_pay_log/index.jsp" requestMethod="GET">
            node_weixin.AppendURLChild(
            '${ lfn:message("third-weixin:table.thirdWeixinPayLog") }',
            '<c:url value="/third/weixin/third_weixin_pay_log/index.jsp"/>');
        </kmss:auth>
        /*支付回调日志*/
        <kmss:auth requestURL="/third/weixin/third_weixin_pay_cb/index.jsp" requestMethod="GET">
            node_weixin.AppendURLChild(
            '${ lfn:message("third-weixin:table.thirdWeixinPayCb") }',
            '<c:url value="/third/weixin/third_weixin_pay_cb/index.jsp"/>');
        </kmss:auth>
    </kmss:ifModuleExist>

    LKSTree.Show();
}
</template:replace>
</template:include>