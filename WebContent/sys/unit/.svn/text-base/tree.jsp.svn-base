<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.unit" bundle="sys-unit" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSUNIT_CONFIG_SETTING">	
	n2=n1.AppendURLChild(
		"<bean:message key="sysUnit.tree.displayConfig" bundle="sys-unit" />",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.unit.model.SysUnitConfig" />"
	);	
	
	defaultNode=n1.AppendURLChild(
		"<bean:message key="table.kmImissiveUnitCategory" bundle="sys-unit" />",
		"<c:url value="/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory_tree.jsp" />"
	);
	
	//单位设置
	units=n1.AppendURLChild(
		"<bean:message key="table.kmImissiveUnit" bundle="sys-unit" />",
		"<c:url value="/sys/unit/km_imissive_unit/index.jsp" />"
	);
	units.AppendBeanData("kmImissiveUnitTreeService&parentId=!{value}");
	n2.isExpanded = true;
	<%if(SysUnitUtil.getExchangeEnable()){ %>
		n2=n1.AppendURLChild(
			"<bean:message key="sysUnitDialog.dec" bundle="sys-unit" />",
			"<c:url value="/sys/unit/km_imissive_unit/indexOuter.jsp"/>"
		);
		n2=n1.AppendURLChild(
		"<bean:message key="table.sysUnitSecretary" bundle="sys-unit" />",
		"<c:url value="/sys/unit/sys_unit_secretary/index.jsp" />"
		);
		n2=n1.AppendURLChild(
		"<bean:message key="table.sysUnitDataCenter" bundle="sys-unit" />",
		"<c:url value="/sys/unit/sys_unit_data_center/index.jsp" />"
		);
		n2=n1.AppendURLChild(
		"对接单位设置",
		"<c:url value="/sys/unit/sys_unit_data_center_unit/index.jsp" />"
		);
	<%} %>
	n2=n1.AppendURLChild(
		"<bean:message key="table.kmImissiveUnitBrunchLeader" bundle="sys-unit" />", 
		"<c:url value="/sys/unit/km_imissive_brunchleader/index.jsp" />"
	);
	group=n1.AppendURLChild(
		"<bean:message key="table.group.setting" bundle="sys-unit" />", 
		"<c:url value="/sys/unit/sys_unit_group/index.jsp" />"
	);
	</kmss:authShow>
		
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
  </template:replace>
</template:include>