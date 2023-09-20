<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
		function generateTree() {
			LKSTree = new TreeView(
				"LKSTree",
				"${lfn:message('third-alimeeting:module.third.aliMeeting')}",
				document.getElementById("treeDiv")
			);
			var n1, defaultNode;
			n1 = LKSTree.treeRoot;
			
			defaultNode = n1.AppendURLChild(
				"${lfn:message('third-alimeeting:aliMeeting.tree.setting')}",
				"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.alimeeting.model.AliMeetingConfig" />"
			);	
			
			
			LKSTree.EnableRightMenu();
			LKSTree.Show();
			LKSTree.ClickNode(defaultNode);
		}
	</template:replace>
</template:include>