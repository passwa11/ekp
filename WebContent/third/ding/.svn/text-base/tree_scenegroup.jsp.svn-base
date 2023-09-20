<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        "<bean:message key="dingScenegroup" bundle="third-ding"/>",//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    node.AppendURLChild("<bean:message key='table.thirdDingScenegroupMapp' bundle='third-ding-scenegroup'/>",
        '<c:url value="/third/ding/scenegroup/third_ding_scenegroup_mapp/list.jsp"/>');
    node.AppendURLChild("<bean:message key='table.thirdDingScenegroupModule' bundle='third-ding-scenegroup'/>",
    '<c:url value="/third/ding/scenegroup/third_ding_scenegroup_module/list.jsp"/>');
    node.AppendURLChild("<bean:message key='table.thirdDingGroupmsgLog' bundle='third-ding-scenegroup'/>",
    '<c:url value="/third/ding/scenegroup/third_ding_groupmsg_log/list.jsp"/>');
    
    LKSTree.Show();
}
</template:replace>
</template:include>