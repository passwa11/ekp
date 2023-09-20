<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-eas:module.fssc.eas") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot;

    node.isExpanded = true;
	<kmss:auth requestURL="/fssc/eas/fssc_eas_switch/fsscEasSwitch.do?method=add">
    /*Eas集成设置*/
    <%-- var node_1_0_node = node.AppendURLChild(
    '${ lfn:message("fssc-eas:table.fsscEasSwitch") }',
    "<c:url value="/fssc/eas/fssc_eas_switch/fsscEasSwitch.do?method=modifySwitch"/>") --%>
    var node_1_0_node = node.AppendURLChild(
    '${ lfn:message("fssc-eas:table.fsscEasParam") }',
    "<c:url value="/fssc/eas/fssc_eas_param/fsscEasParam.do?method=modifyParam"/>")
     </kmss:auth>
    
    LKSTree.Show();
}
</template:replace>
</template:include>
