<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        "<bean:message key="module.third.ding.notify" bundle="third-ding"/>",//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    var node_wf = node.AppendURLChild("<bean:message key='module.third.ding.notify.api.workflow' bundle='third-ding'/>",'');
    var node_wr = node.AppendURLChild("<bean:message key='module.third.ding.notify.api.todo' bundle='third-ding'/>",'');
    
    
    node.isExpanded = true;
    /*钉钉待办模板*/
    <kmss:auth requestURL="/third/ding/third_ding_dtemplate/index.jsp" requestMethod="GET">
    var node_1_0_node = node_wf.AppendURLChild(
        "<bean:message key="table.thirdDingDtemplate" bundle="third-ding"/>",
        '<c:url value="/third/ding/third_ding_dtemplate/index.jsp"/>');
    </kmss:auth> 
    /*钉钉待办实例*/
    <kmss:auth requestURL="/third/ding/third_ding_dinstance/index.jsp" requestMethod="GET">
    var node_1_1_node = node_wf.AppendURLChild(
        "<bean:message key="table.thirdDingDinstance" bundle="third-ding"/>",
        '<c:url value="/third/ding/third_ding_dinstance/index.jsp"/>');
    </kmss:auth> 
    /*钉钉任务*/
    <kmss:auth requestURL="/third/ding/third_ding_dtask/index.jsp" requestMethod="GET">
    var node_1_2_node = node_wf.AppendURLChild(
    	"<bean:message key="table.thirdDingDtask" bundle="third-ding"/>",
        '<c:url value="/third/ding/third_ding_dtask/index.jsp"/>');
        
    var node_1_3_node = node_wf.AppendURLChild(
    	"<bean:message key="table.thirdDingNotifylog" bundle="third-ding"/>",
        '<c:url value="/third/ding/third_ding_notifylog/indexLog.jsp"/>');
        
    node_wr.AppendURLChild(
    	"<bean:message key='module.third.ding.notify.template' bundle='third-ding'/>",
        '<c:url value="/third/ding/third_ding_todo_template/thirdDingNotify_template.jsp"/>');
            
    node_wr.AppendURLChild(
    	"<bean:message key="table.thirdDingNotifyLog" bundle="third-ding-notify"/>",
        '<c:url value="/third/ding/third_ding_notify_log/list.jsp"/>');
        
    node_wr.AppendURLChild(
    	"<bean:message key="table.thirdDingNotifyWorkrecord" bundle="third-ding-notify"/>",
        '<c:url value="/third/ding/third_ding_notify_workrecord/list.jsp"/>');

   node_wr.AppendURLChild(
        "<bean:message key="table.thirdDingNotifyMessage" bundle="third-ding-notify"/>",
        '<c:url value="/third/ding/third_ding_notify_message/list.jsp"/>');
        
    node_wr.AppendURLChild(
    	"<bean:message key="table.thirdDingNotifyQueueError" bundle="third-ding-notify"/>",
        '<c:url value="/third/ding/third_ding_notify_queue_error/list.jsp"/>');
    </kmss:auth> 
     var n2 = node.AppendURLChild(
        "<bean:message key="thirdDingDtask.cleaning.tool" bundle="third-ding"/>",
        '<c:url value="/third/ding/third_ding_notify/cleaning_tool.jsp"/>');
    LKSTree.ExpandNode(n2);
    
    var n3 = node.AppendURLChild(
        "<bean:message key="table.thirdDingSendDing" bundle="third-ding"/>",
        '<c:url value="/third/ding/third_ding_send_ding/index.jsp"/>');
    LKSTree.ExpandNode(n3);
    LKSTree.Show();
}
</template:replace>
</template:include>