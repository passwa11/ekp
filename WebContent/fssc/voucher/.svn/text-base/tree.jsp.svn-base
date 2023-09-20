<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-voucher:module.fssc.voucher") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot;
    node.isExpanded = true;
	<kmss:authShow roles="ROLE_FSSCVOUCHER_SETTING">
    /*凭证编号规则*/
    node.AppendURLChild(
    '${ lfn:message("fssc-voucher:py.TongYongBianHaoGui") }',
    '<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.voucher.model.FsscVoucherMain"/>');
	  /*凭证_列表自定义*/
   node.AppendURLChild(
        '${ lfn:message("fssc-voucher:py.XuQiuShenQingDan") }',
        '<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.fssc.voucher.model.FsscVoucherMain"/>'); 
    /*凭证规则模块配置*/
    node.AppendURLChild(
    '${ lfn:message("fssc-voucher:table.fsscVoucherModelConfig") }',
    '<c:url value="/fssc/voucher/fssc_voucher_model_config/index.jsp"/>');
	</kmss:authShow>
	<kmss:authShow roles="ROLE_FSSCVOUCHER_RULE">
    /*凭证规则设置*/
    node.AppendURLChild(
    '${ lfn:message("fssc-voucher:table.fsscVoucherRuleConfig") }',
    '<c:url value="/fssc/voucher/fssc_voucher_rule_config/index.jsp"/>');
	</kmss:authShow>


    LKSTree.Show();
}
</template:replace>
</template:include>
