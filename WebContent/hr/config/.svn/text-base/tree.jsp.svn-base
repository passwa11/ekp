<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("hr-config:table.hrConfigOvertimeConfig") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    		node.AppendURLChild('<bean:message bundle="hr-config" key="py.WhiteListConfig"/>','<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.config.model.WhiteListConfig"/>');
    
     
    
    LKSTree.Show();
}
</template:replace>
</template:include>