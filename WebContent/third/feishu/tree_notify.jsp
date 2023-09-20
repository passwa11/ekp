<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        "<bean:message key="third.feishu.notify" bundle="third-feishu"/>",//根节点的名称
        document.getElementById('treeDiv')
    );
    
    var node = LKSTree.treeRoot; 
    node.isExpanded = true;
    
    var node_1_3_node = node.AppendURLChild(
    	"<bean:message key="table.thirdFeishuNotifylog" bundle="third-feishu"/>",
        '<c:url value="/third/feishu/third_feishu_notify_log/list.jsp"/>');
         
    node.AppendURLChild(
    	"<bean:message key="table.thirdFeishuNotifyQueueError" bundle="third-feishu"/>",
        '<c:url value="/third/feishu/third_feishu_notify_queue_err/list.jsp"/>');
    
    LKSTree.Show();
}
</template:replace>
</template:include>