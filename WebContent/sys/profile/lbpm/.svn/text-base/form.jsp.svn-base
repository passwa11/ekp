<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">


//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.lbpm.form" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, defaultNode;
	n1 = LKSTree.treeRoot;
	
	//========== 表单数据集 ==========
	<kmss:authShow roles="ROLE_SYSXFORM_SETTING;ROLE_SYSXFORM_MAINDATA">
		n2 = n1.AppendURLChild(
	        "<bean:message key="tree.relation.main.data.option" bundle="sys-xform-maindata" />"
		);
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_SYSXFORM_CONTROL">
		<!-- 表单控件 -->
		n3 = n1.AppendURLChild(
	        '<bean:message key="sysFormProfileManagement.control" bundle="sys-xform" />'
		);
		n1.AppendURLChild("<bean:message key="tree.relation.main.data.defaultSetting" bundle="sys-xform-maindata" />",
			'<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.xform.base.model.SysFormDefaultSwitch"/>');
	</kmss:authShow>
	
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
	<kmss:authShow roles="ROLE_SYSXFORM_SETTING;ROLE_SYSXFORM_MAINDATA">
		<!-- 类别设置 -->
		n2.AppendURLChild(
			'<bean:message key="tree.relation.jdbc.catg" bundle="sys-xform-maindata" />',
			"<c:url value='/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory&actionUrl=/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do&formName=sysFormJdbcDataSetCategoryForm&rightFlag=1' />"
		);
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
	
	<kmss:authShow roles="ROLE_SYSXFORM_CONTROL">
		<!-- 表单控件--关联控件 -->
		var control_relevance = n3.AppendURLChild(
			'<bean:message key="sysFormProfileManagement.control_relevance" bundle="sys-xform" />',
			'<c:url value="/sys/xform/designer/relevance/relevance_rightDiffusion_log.jsp"/>'
		);
		var control_auditshow = n3.AppendURLChild(
			'<bean:message key="sysFormProfileManagement.control_auditshow" bundle="sys-xform" />',
			'<c:url value="/sys/xform/designer/auditshow/index.jsp"/>'
		);
		var control_auditshow = n3.AppendURLChild(
		'<bean:message key="sysFormProfileManagement.control_circulationOpinion" bundle="sys-xform" />',
		'<c:url value="/sys/xform/designer/circulationOpinion/index.jsp"/>'
		);
		<!-- 片段集 -->
		var fragmentSet = n3.AppendURLChild(
	        '<bean:message key="sysFormProfileManagement.sysFormFragmentSet" bundle="sys-xform" />'
		);
		<!-- 类别设置 -->
		fragmentSet.AppendURLChild(
			'<bean:message key="tree.relation.sysFormFragmentSet.catgory" bundle="sys-xform-fragmentSet" />',
			"<c:url value='/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.xform.fragmentSet.model.SysFormFragmentSetCategory&actionUrl=/sys/xform/fragmentSet/category/xFormFragmentSetCategory.do&formName=sysFormFragmentSetCategoryForm&rightFlag=1' />"
		);
		<!-- 片段集模板 -->					
		var fragmentSetTree = fragmentSet.AppendURLChild('<bean:message key="sysFormFragmentSet.templateSet" bundle="sys-xform-fragmentSet" />')
		fragmentSetTree.authType="01";
		fragmentSetTree.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.sys.xform.fragmentSet.model.SysFormFragmentSetCategory",
								'<c:url value="/sys/xform/fragmentSet/xFormFragmentSet.do?method=manageList&categoryId=!{value}&status=all&showDocStatus=true" />',
								'<c:url value="/sys/xform/fragmentSet/xFormFragmentSet.do?method=listChildren&type=category&categoryId=!{value}" />');
	</kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>
