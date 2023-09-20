<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="table.kmSmissiveMain" bundle="km-smissive"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2,n3,defaultNode; 
	n1 = LKSTree.treeRoot;
			//类别设置
	defaultNode = n1.AppendURLChild(
			"<bean:message bundle="km-smissive" key="menu.smissive.categoryconfig"/>",
			"<c:url value="/sys/simplecategory/sys_simple_category/sysCategoryMain_tree.jsp?modelName=com.landray.kmss.km.smissive.model.KmSmissiveTemplate&actionUrl=/km/smissive/km_smissive_template/kmSmissiveTemplate.do&formName=kmSmissiveTemplateForm&mainModelName=com.landray.kmss.km.smissive.model.KmSmissiveMain&docFkName=fdTemplate&authReaderNoteFlag=2" />"
		);
		
	<kmss:authShow roles="ROLE_KMSMISSIVE_COMMONWORKFLOW">
		//模块设置
		n2 = n1.AppendURLChild(
			"<bean:message bundle="km-smissive" key="menu.smissive.module.title"/>"
		);
	    n2.isExpanded = true;	
		//参数配置
		<%if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()){ %>
		 n2.AppendURLChild(
			"<bean:message bundle="km-smissive" key="tree.setting.other"/>",
			"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.km.smissive.model.KmSmissiveConfig" />"
		);
		<%}%>
	
		<% if(com.landray.kmss.sys.number.util.NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.km.smissive.model.KmSmissiveMain")){ %>
		n2.AppendURLChild(
			"<bean:message bundle="sys-number" key="sysNumber.config.tree.numberMain"/>",
			"<c:url value="/sys/number/sys_number_main/index.jsp?modelName=com.landray.kmss.km.smissive.model.KmSmissiveMain" />"
		);
		<%} %>
		
		//流程模板设置
	
		n2.AppendURLChild(
			"<bean:message key="tree.workflowTemplate" bundle="km-smissive" />",
			"<c:url value="/sys/lbpmservice/support/lbpm_template/index.jsp?fdModelName=com.landray.kmss.km.smissive.model.KmSmissiveTemplate&fdKey=smissiveDoc" />"
		);
		
		//列表显示设置
		n2.AppendURLChild(
			"<bean:message bundle="sys-profile" key="sys.profile.list.display.config"/>",
			"<c:url value="/sys/profile/listShow/sys_listshow_category/index.jsp?modelName=com.landray.kmss.km.smissive.model.KmSmissiveMain"/>"
		);
		
		n2.AppendURLChild(
			"<bean:message bundle="km-smissive" key="smissive.tree.cacheNumberQuery"/>",
			"<c:url value="/km/smissive/km_smissive_number/index.jsp" />"
		);
	
	</kmss:authShow>
	//文档维护
	n2 = n1.AppendURLChild("<bean:message key="tree.sysCategory.maintains" bundle="sys-category" />")
	n2.authType="01";
	<kmss:authShow roles="ROLE_KMSMISSIVE_OPTALL">
	n2.authRole="optAll";
	</kmss:authShow>
	n2.AppendSimpleCategoryDataWithAdmin ("com.landray.kmss.km.smissive.model.KmSmissiveTemplate",
	"<c:url value="/km/smissive/km_smissive_main/kmSmissiveMain_list.jsp?categoryId=!{value}&status=all"/>",
	"<c:url value="/km/smissive/km_smissive_main/kmSmissiveMain_list.jsp?type=category&categoryId=!{value}"/>");
	//=========回收站========
	<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.smissive.model.KmSmissiveMain")) { %>
	n3 = n1.AppendURLChild("<bean:message key="module.sys.recycle" bundle="sys-recycle" />","<c:url value="/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.km.smissive.model.KmSmissiveMain" />");	
	<% } %>
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
	
}
 </template:replace>
</template:include>