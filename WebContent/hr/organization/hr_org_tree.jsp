<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("hr-organization:module.hr.organization") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    var n1;
    <!-- 暂时在此声明,后续使用组件扩充 -->
    var HR_TYPE_HRORDEPT = 0x3,HR_FLAG_BUSINESSALL = 0xc00;
    <!-- 组织架构一览图 -->
	n1 = node.AppendURLChild(
		"${ lfn:message('hr-organization:hr.organization.info.tree.chart') }", ""
	);
	n1.AppendBeanData(
		"hrOrganizationTree&type=feedback&parent=!{value}&orgType="+(HR_TYPE_HRORDEPT|HR_FLAG_BUSINESSALL), 
		"<c:url value="/hr/organization/hr_organization_chart/index.jsp"/>?parent=!{value}", 
		openChartView, true
	);
     
    
    LKSTree.Show();
}
function openChartView(){
	var url = Com_Parameter.ContextPath+"hr/organization/hr_organization_chart/index.jsp";
	url = Com_SetUrlParameter(url, "s_path", Tree_GetNodePath(this,">>",this.treeView.treeRoot));
	Com_OpenWindow(Com_SetUrlParameter(url, "parent", this.value), 3);
}
</template:replace>
</template:include>