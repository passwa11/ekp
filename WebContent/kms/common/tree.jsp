﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.landray.kmss.kms.common.util.PluginUtil,com.landray.kmss.kms.common.model.KmsMultidocLifeCycle" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.tree">
	<template:replace name="content">

//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.kms.common" bundle="kms-common"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<kmss:auth requestURL="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntroCategory&actionUrl=/kms/common/kms_home_knowledge_intro_category/kmsHomeKnowledgeIntroCategory.do&formName=kmsHomeKnowledgeIntroCategoryForm&mainModelName=com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntro&docFkName=docCategory"  
		requestMethod="GET">
		//分类设置
		n2 = n1.AppendURLChild(
			"<bean:message key="table.kmsHomeKnowledgeIntroCategory.setting" bundle="kms-common" />",
			"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntroCategory&actionUrl=/kms/common/kms_home_knowledge_intro_category/kmsHomeKnowledgeIntroCategory.do&formName=kmsHomeKnowledgeIntroCategoryForm&mainModelName=com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntro&docFkName=docCategory" />"
		);
	</kmss:auth>
	
	
	//文档维护
	n2 = n1.AppendURLChild("<bean:message key="table.kmsHomeKnowledgeIntro" bundle="kms-common" />")
	n2.authType="01";
	<kmss:authShow roles="ROLE_KMSHOME_INTRODUCEDOC_CATE_ALL">
	n2.authRole="optAll";
	</kmss:authShow>
	n2.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntroCategory",
	"<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro_list.jsp?method=manageList&categoryId=!{value}"/>",
	"<c:url value="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro_list.jsp?method=listChildren&categoryId=!{value}" />");
	
	<%
		List<KmsMultidocLifeCycle> modules = PluginUtil.getMultidocLifeCycleModuleList();
		request.setAttribute("cycleModules", modules);
	%>
	<c:if test="${not empty cycleModules}">
	<%--知识仓库生命周期配置--%>
	<kmss:authShow roles="ROLE_KMSCOMMON_LIFECYCLE_ADMIN">
	n2 = n1.AppendURLChild(
		"<bean:message key="kmsCommon.multidocLifeCycleConfig" bundle="kms-common" />",
		""
	);
	
	<%

	for (int i = 0; i < modules.size(); i ++) {
		KmsMultidocLifeCycle kmsMultidocLifeCycle = (PluginUtil.getMultidocLifeCycleModuleList()).get(i);
	%>
	<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=<%=kmsMultidocLifeCycle.getConfigModelName() %>&currentModelName=<%=kmsMultidocLifeCycle.getModelName() %>">
		n3 = n2.AppendURLChild(
			"<c:out value="<%=kmsMultidocLifeCycle.getName() %>" />",
			"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit">
				<c:param name="currentModelName" value="<%=kmsMultidocLifeCycle.getModelName() %>" />
				<c:param name="modelName" value="<%=kmsMultidocLifeCycle.getConfigModelName() %>" />
			</c:url>"
		);
	</kmss:auth>
	<%
	}
	%>	
	</kmss:authShow>
	</c:if>
	<!-- 知识提醒配置页面 -->
    <kmss:auth requestURL="/kms/common/kms_knowledge_remind_config/kmsKnowledgeRemindConfig.do?method=edit"  
		requestMethod="GET">
		n2 = n1.AppendURLChild(
			"<bean:message key="table.kmsKnowledgeRemindConfig" bundle="kms-common" />",
			"<c:url value="/kms/common/kms_knowledge_remind_config/kmsKnowledgeRemindConfig.do?method=edit" />"
		);
	</kmss:auth>
	
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
}

	</template:replace>
</template:include>