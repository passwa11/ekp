<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        "<bean:message key="tree.thirdDingCardConfig" bundle="third-ding"/>",//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    node.AppendURLChild("<bean:message key='config.thirdDingCardConfig' bundle='third-ding'/>",
        '<c:url value="/third/ding/third_ding_card_config/list.jsp"/>');
    node.AppendURLChild("<bean:message key='table.thirdDingCardMapping' bundle='third-ding'/>",
    '<c:url value="/third/ding/third_ding_card_mapping/list.jsp"/>');
    node.AppendURLChild("<bean:message key='table.thirdDingCardLog' bundle='third-ding'/>",
    '<c:url value="/third/ding/third_ding_card_log/list.jsp"/>');
    
    LKSTree.Show();
}
</template:replace>
</template:include>