<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        "<bean:message key="third.welink.notify" bundle="third-welink"/>",//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
   
    node.isExpanded = true;
    
        
    node.AppendURLChild(
    	"<bean:message key="table.thirdWelinkNotifyLog" bundle="third-welink"/>",
        '<c:url value="/third/welink/third_welink_notify_log/list.jsp"/>');
     
    node.AppendURLChild(
    	"<bean:message key="table.thirdWelinkNotifyQueueErr" bundle="third-welink"/>",
        '<c:url value="/third/welink/third_welink_notify_queue_err/list.jsp"/>');
     
    LKSTree.Show();
}
</template:replace>
</template:include>