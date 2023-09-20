<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-budgeting:module.fssc.budgeting") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot,node1,node2;
    <kmss:authShow roles="ROLE_FSSCBUDGETING_SETTING;ROLE_FSSCBUDGETING_OPEN">
    <%-- 开启预算编制  --%>
    node1 = node.AppendURLChild(
		"${lfn:message('fssc-budgeting:table.fsscBudgetingOrg')}",
		"<c:url value="/fssc/budgeting/fssc_budgeting_org/fsscBudgetingOrg.do?method=add"/>"
	);
	</kmss:authShow>
	<kmss:authShow roles="ROLE_FSSCBUDGETING_SETTING;ROLE_FSSCBUDGETING_PERIOD">
	<%-- 预算期间  --%>
    node1 = node.AppendURLChild(
		"${lfn:message('fssc-budgeting:table.fsscBudgetingPeriod')}",
		"<c:url value="/fssc/budgeting/fssc_budgeting_period/index.jsp"/>"
	);
	</kmss:authShow>
	<kmss:authShow roles="ROLE_FSSCBUDGETING_SETTING">
    <%-- 预算编制权限控制  --%>
     node1 = node.AppendURLChild(
		"${lfn:message('fssc-budgeting:fssc.budgeting.fsscBudgetingAuth')}"
	);
	<%-- 预算编制权限 --%>
    node2 = node1.AppendURLChild(
		"${lfn:message('fssc-budgeting:table.fsscBudgetingAuth')}",
		"<c:url value="/fssc/budgeting/fssc_budgeting_auth/index.jsp"/>"
	);
	<%-- 预算审批权限 --%>
    node2 = node1.AppendURLChild(
		"${lfn:message('fssc-budgeting:table.fsscBudgetingApprovalAuth')}",
		"<c:url value="/fssc/budgeting/fssc_budgeting_approval_auth/index.jsp"/>"
	);
	<%-- 预算生效权限 --%>
    node2 = node1.AppendURLChild(
		"${lfn:message('fssc-budgeting:table.fsscBudgetingEffectAuth')}",
		"<c:url value="/fssc/budgeting/fssc_budgeting_effect_auth/index.jsp"/>"
	);
	</kmss:authShow>
    LKSTree.Show();
}
</template:replace>
</template:include>
