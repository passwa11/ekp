<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-cashier:module.fssc.cashier") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot;

    /*付款单编号规则*/
    node.AppendURLChild(
    '${ lfn:message("fssc-cashier:py.TongYongBianHaoGui") }',
    '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.cashier.model.FsscCashierPaymentDetail"/>');

    /*出纳规则模块配置*/
    node.AppendURLChild(
    '${ lfn:message("fssc-cashier:table.fsscCashierModelConfig") }',
    '<c:url value="/fssc/cashier/fssc_cashier_model_config/index.jsp"/>');

    /*出纳规则设置*/
    node.AppendURLChild(
    '${ lfn:message("fssc-cashier:table.fsscCashierRuleConfig") }',
    '<c:url value="/fssc/cashier/fssc_cashier_rule_config/index.jsp"/>');


    LKSTree.Show();
}
</template:replace>
</template:include>
