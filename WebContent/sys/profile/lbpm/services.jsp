<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.lbpm.services" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list" requestMethod="GET">
	<%-- 对接主文档 --%>
	defaultNode = n2 = n1.AppendURLChild(
		"<bean:message key="table.lbpmDockingMain" bundle="sys-lbpmdocking" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list" />"
	);
	
	//========== 我的文档 ==========
	n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.tree.myDoc" bundle="sys-lbpmdocking" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=true&status=all" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.discard" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=true&status=00" />"
	);	
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.draft" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=true&status=10" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.refuse" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=true&status=11" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.examine" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=true&status=20" />"
	);	
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.publish" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=true&status=30" />"
	);	
	
	//========== 我审批的文档 ==========
	//待审批的文档
	n1.AppendURLChild(
		"<bean:message key="lbpmDocking.doc.owner.append" bundle="sys-lbpmdocking" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&type=unExecuted" />"
	);	
	//已审批的文档
	n1.AppendURLChild(
		"<bean:message key="lbpmDocking.doc.owner.disposed" bundle="sys-lbpmdocking" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&type=executed" />"
	);	
	//所有文档
	n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.doc.owner.all" bundle="sys-lbpmdocking" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list" />"
	);
	//=========按类别=======
	n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.tree.category" bundle="sys-lbpmdocking" />"
	);
    n2.AppendCategoryData(
    	"com.landray.kmss.sys.lbpmdocking.config.model.LbpmDockingTemplate",
    	"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=listChildren&type=category&categoryId=!{value}" />"
    );
     // 按辅类别 //-----modify by zhouchao 
     
 	// 按辅分类     
    n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.tree.category.property" bundle="sys-lbpmdocking" />"
	);
   
 	n2.AppendPropertyData(
 		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=listChildren&type=category&categoryId=!{value}" />",
 		'true',
 		"com.landray.kmss.sys.lbpmdocking.config.model.LbpmDockingTemplate"
 	);
 	
	//=========按部门========
	n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.tree.department" bundle="sys-lbpmdocking" />"
	);
	
	n2.AppendOrgData(
		"organizationTree&fdId=!{value}",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&type=department&departmentId=!{value}"/>"
	);
	
	
	//=========按状态========
	n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.tree.status" bundle="sys-lbpmdocking" />"
	);
	
	n3 = n2.AppendURLChild(
		"<bean:message   key="status.discard" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=false&status=00" />"
	);	
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.refuse" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=false&status=11" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message  key="status.examine" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=false&status=20" />"
	);	
	n3 = n2.AppendURLChild(
		"<bean:message key="status.publish" />",
		"<c:url value="/sys/lbpmdocking/lbpm_docking_main/index.jsp?method=list&mydoc=false&status=30" />"
	);	
	
	<%--  模块设置 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="lbpmDocking.tree.modelSet" bundle="sys-lbpmdocking" />"
	);
		<%-- 类别设置 --%>
		n2.AppendURLChild(
			"<bean:message key="lbpmDocking.tree.categorySet" bundle="sys-lbpmdocking" />",
			"<c:url value="/sys/category/sys_category_main/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.sys.lbpmdocking.config.model.LbpmDockingTemplate&mainModelName=com.landray.kmss.sys.lbpmdocking.config.model.LbpmDockingMain&templateName=fdTemplate&categoryName=docCategory&authReaderNoteFlag=2" />"
		);
		<kmss:auth requestURL="/sys/category/sys_category_property/sysCategoryProperty_tree.jsp" requestMethod="GET">
		<%-- 辅类别设置 --%>
		n2.AppendURLChild(
			"<bean:message bundle="sys-category" key="menu.sysCategory.property"/>",
			"<c:url value="/sys/category/sys_category_property/sysCategoryProperty_tree.jsp"/>"
		);
	   </kmss:auth>
		<%-- 模板设置 --%>
		n2.AppendCV2Child("<bean:message key="lbpmDocking.tree.templateSet" bundle="sys-lbpmdocking" />",
			"com.landray.kmss.sys.lbpmdocking.config.model.LbpmDockingTemplate",
			"<c:url value="/sys/lbpmdocking/lbpm_docking_template/index.jsp?method=listChildren&parentId=!{value}&ower=1" />");
	</kmss:auth>
	<kmss:authShow roles="ROLE_SYSLBPMDOCKING_SETTING">
	<%-- 对接系统配置 --%>
		n2 = n1.AppendURLChild(
			"<bean:message key="tree.dockingSystemConfig" bundle="sys-lbpmdocking" />",
			"<c:url value="" />"
		);
			<%-- 对接系统 --%>
			n3 = n2.AppendURLChild(
				"<bean:message key="table.lbpmDockingSystem" bundle="sys-lbpmdocking" />",
				"<c:url value="/sys/lbpmdocking/lbpm_docking_system/index.jsp" />"
			);
	<%-- 对接模块交互日志 --%>
		n2 = n1.AppendURLChild(
			"<bean:message key="table.lbpmDockingExchangeLog" bundle="sys-lbpmdocking"/>","<c:url value="/sys/lbpmdocking/lbpm_docking_exchange_log/index.jsp" />"
		);
			<%-- 服务端 --%>
			n2.AppendURLChild(
				"<bean:message key="enums_exchangelog_fdtype_server" bundle="sys-lbpmdocking"/>","<c:url value="/sys/lbpmdocking/lbpm_docking_exchange_log/index.jsp?fdType=SERVER" />"
			);
			<%-- 客户端 --%>
			n2.AppendURLChild(
				"<bean:message key="enums_exchangelog_fdtype_client" bundle="sys-lbpmdocking"/>","<c:url value="/sys/lbpmdocking/lbpm_docking_exchange_log/index.jsp?fdType=CLIENT" />"
			);
			<%-- 日志参数配置 --%>
			n2.AppendURLChild(
				"<bean:message key="lbpmDockingExchangeLog.config" bundle="sys-lbpmdocking"/>","<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.lbpmdocking.config.model.LbpmDockingExchangeLogConfig" />"
			);
		<%-- 历史日志 --%>
		n3 = n2.AppendURLChild(
			"<bean:message key="table.lbpmDockingExchangeLogBak" bundle="sys-lbpmdocking"/>","<c:url value="/sys/lbpmdocking/lbpm_docking_exchange_log/index.jsp?isBak=true" />"
		);
			<%-- 服务端 --%>
			n3.AppendURLChild(
				"<bean:message key="enums_exchangelog_fdtype_server" bundle="sys-lbpmdocking"/>","<c:url value="/sys/lbpmdocking/lbpm_docking_exchange_log/index.jsp?isBak=true&fdType=SERVER" />"
			);
			<%-- 客户端 --%>
			n3.AppendURLChild(
				"<bean:message key="enums_exchangelog_fdtype_client" bundle="sys-lbpmdocking"/>","<c:url value="/sys/lbpmdocking/lbpm_docking_exchange_log/index.jsp?isBak=true&fdType=CLIENT" />"
			);

	</kmss:authShow>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>