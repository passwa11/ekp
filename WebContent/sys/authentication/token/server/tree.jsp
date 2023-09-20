<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("sys-authentication:module.sys.token") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot;



    node.isExpanded = true;

    /*基础设置*/
    var node_1_0_node = node.AppendURLChild(
    '<bean:message key="token.share.basis.setting" bundle="sys-authentication" />',
    '<c:url value="/sys/authentication/token/server/index.jsp"/>');
    node_1_0_node.isExpanded = true;

    /*全局设置*/
    var node_1_0_node = node.AppendURLChild(
    '<bean:message key="token.share.global.setting" bundle="sys-authentication" />',
    '<c:url value="/sys/token/sys_token_config/sysTokenConfig.do?method=editTokenConfig"/>');
    node_1_0_node.isExpanded = true;
    
    LKSTree.Show();
}
</template:replace>
</template:include>