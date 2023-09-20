﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.tic.core.mapping" bundle="tic-core-mapping"/>",
		document.getElementById("treeDiv")
	);
	var n1_common, n2_common, n3, n4, n5;
	n1_common = LKSTree.treeRoot;
	
	<%-- 应用模块注册 --%>
	n2_common = n1_common.AppendURLChild(
		"<bean:message key="table.ticCoreMappingModule" bundle="tic-core-mapping" />",
		"<c:url value="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=list" />"
	);
	<%-- 表单流程映射  --%>
	n2_common = n1_common.AppendURLChild(
		"<bean:message key="tree.form.flow.mapping" bundle="tic-core-mapping" />",
		""
	);
	<%--加载模块树--%>
	n2_common = n2_common.AppendBeanData("ticCoreMappingModuleTreeService&id=!{value}"
	);
	//LKSTree.EnableRightMenu();
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
