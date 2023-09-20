<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
<template:replace name="content">
function generateTree()
{
    window.LKSTree = new TreeView( 
        'LKSTree',
        
        '${ lfn:message("fssc-mobile:py.YiDongTianBao") }',//根节点的名称
        document.getElementById('treeDiv')
    );
    var node = LKSTree.treeRoot; 
    
    
    node.isExpanded = true;
    /*发票抬头*/
    <kmss:auth requestURL="/fssc/mobile/fssc_mobile_invoice_title/index.jsp" requestMethod="GET">
    <%-- var node_1_0_node = node.AppendURLChild(
        '${ lfn:message("fssc-mobile:table.fsscMobileInvoiceTitle") }',
        '<c:url value="/fssc/mobile/fssc_mobile_invoice_title/index.jsp"/>'); --%>
    </kmss:auth> 
     /*个人*/    
     var node_1_0_node = node.AppendURLChild(
        '${ lfn:message("fssc-mobile:fsscMobileLink.fdPersonLink") }',
        '<c:url value="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=add&fdType=2"/>');
     <kmss:authShow roles="ROLE_FSSCMOBILE_LINK">
     /*应用模块*/
    var node_1_0_node = node.AppendURLChild(
        '${ lfn:message("fssc-mobile:fsscMobileLink.fdModuleLink") }',
        '<c:url value="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=add&fdType=1"/>');
     /*第三方*/
     var node_1_0_node = node.AppendURLChild(
        '${ lfn:message("fssc-mobile:fsscMobileLink.fdOthersLink") }',
       <%--  '<c:url value="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=list&fdType=3"/>') --%>
       '<c:url value="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=add&fdType=3"/>');    
	</kmss:authShow>
    LKSTree.Show();
    
}
</template:replace>
</template:include>
