<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-budget:module.fssc.budget") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    <%-- 预算调整配置  --%>
    node1 = node.AppendURLChild(
		"${lfn:message('fssc-budget:fsscBudgetAdjustCategory.config')}"
	);
    <%-- 预算调整类别  --%>
    node2 = node1.AppendURLChild(
		"${lfn:message('fssc-budget:table.fsscBudgetAdjustCategory')}",
		'<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory&actionUrl=/fssc/budget/fssc_budget_adjust_category/fsscBudgetAdjustCategory.do&formName=fsscBudgetAdjustCategoryForm&mainModelName=com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain&docFkName=docTemplate" />'
	);
    <%-- 预算调整流程机制  --%>
    node2 = node1.AppendURLChild(
		"${lfn:message('config.common.workflow')}",
		'<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory&fdKey=fsscBudgetAdjustMain" />'
	);
    <%-- 预算调整编号机制  --%>
    node2 = node1.AppendURLChild(
		"${lfn:message('sys-number:table.sysNumberMain')}",
		'<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />'
	);
     
     LKSTree.ExpandNode(node1);
    LKSTree.Show();
}
</template:replace>
</template:include>
