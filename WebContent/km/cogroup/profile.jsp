<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">


//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		'沟通协作',
		document.getElementById("treeDiv")
	);
	var n1;
	n1 = LKSTree.treeRoot;
		<kmss:ifModuleExist path="/third/weixin">
		n1.AppendURLChild(
		'企业微信会话',
		"<c:url value='/third/weixin/chatdata/index.jsp' />"
		);
		</kmss:ifModuleExist>
		n1.AppendURLChild(
		'会话记录配置',
		"<c:url value='/km/cogroup/chatdata_config.jsp' />"
		);
		n1.AppendURLChild(
			'群协作配置',
			"<c:url value='/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.km.cogroup.model.GroupConfig' />"
		);
		 


	//LKSTree.ExpandNode(n1);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	//LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>