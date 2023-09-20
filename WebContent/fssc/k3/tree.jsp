<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-k3:module.fssc.k3") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot;

    node.isExpanded = true;
	<kmss:auth requestURL="/fssc/k3/fssc_k3_switch/fsscK3Switch.do?method=add">
    /*K3集成设置*/
    var node_1_0_node = node.AppendURLChild(
    '${ lfn:message("fssc-k3:table.fsscK3Switch") }',
    "<c:url value="/fssc/k3/fssc_k3_switch/fsscK3Switch.do?method=modifySwitch"/>")
     </kmss:auth>
    
    LKSTree.Show();
}
</template:replace>
</template:include>
