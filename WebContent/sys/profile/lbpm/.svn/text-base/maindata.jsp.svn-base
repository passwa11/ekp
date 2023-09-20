<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">


//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		'<bean:message key="tree.relation.jdbc.dataCenter" bundle="sys-xform-maindata" />',
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSXFORM_SETTING;ROLE_SYSXFORM_MAINDATA">
		<!-- 类别设置 -->
		n1.AppendURLChild(
			'<bean:message key="tree.relation.jdbc.catg" bundle="sys-xform-maindata" />',
			"<c:url value='/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory&actionUrl=/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do&formName=sysFormJdbcDataSetCategoryForm&rightFlag=1' />"
		);
		 
		//========== 表单数据集 ==========
		n2 = n1.AppendURLChild(
	        '<bean:message key="tree.relation.jdbc.masterData" bundle="sys-xform-maindata" />'
		);
		
		<kmss:auth requestURL="/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=manageList">
		var showTree = n1.AppendURLChild('<bean:message key="tree.relation.jdbc.masterCar" bundle="sys-xform-maindata" />','<c:url value="/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=manageList&status=all&showDocStatus=true" />')
		showTree.authType="01";
		<!-- 加了节点权限过滤的方法，暂时只找到这个方法，第三个参数目前没用 -->
		showTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory",
									'<c:url value="/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
									'<c:url value="/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=listChildren&type=category&categoryId=!{value}" />');
		</kmss:auth>
		
		
		<%-- 未实现功能暂时隐藏 
		<kmss:authShow roles="ROLE_SYSXFORM_TEMPLATE">
		//========== 表单模板 ==========
		n3 = n1.AppendURLChild(
	        "表单模板",
			"<c:url value="/sys/profile/building.jsp" />"
		);
		
		//========== 表单模板范本 ==========
		n4 = n1.AppendURLChild(
	        "表单模板范本",
			"<c:url value="/sys/profile/building.jsp" />"
		);
		</kmss:authShow>
		--%>
	
		//================================== 二级目录 =====================================//	
		
		
		<%-- JDBC业务数据来源    二级目录 --%>
		
		
		<!-- 系统内数据 -->
		var insystemTree = n2.AppendURLChild('<bean:message key="tree.relation.main.dadta.insystem" bundle="sys-xform-maindata" />')
		insystemTree.authType="01";
		<!-- 加了节点权限过滤的方法，暂时只找到这个方法，第三个参数目前没用 -->
		insystemTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory",
									'<c:url value="/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
									'<c:url value="/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=listChildren&type=category&categoryId=!{value}" />');
		<!-- 自定义数据 -->						
		var customTree = n2.AppendURLChild('<bean:message key="tree.relation.jdbc.custom" bundle="sys-xform-maindata" />')
		customTree.authType="01";
		customTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory",
									'<c:url value="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
									'<c:url value="/sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=listChildren&type=category&categoryId=!{value}" />');
		<!-- 扩展数据 -->					
		var expandTree = n2.AppendURLChild('<bean:message key="tree.relation.jdbc.list" bundle="sys-xform-maindata" />')
		expandTree.authType="01";
		expandTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory",
									'<c:url value="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
									'<c:url value="/sys/xform/maindata/jdbc_data_set/xFormjdbcDataSet.do?method=listChildren&type=category&categoryId=!{value}" />');
		
	</kmss:authShow>
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>