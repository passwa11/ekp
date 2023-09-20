<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
<%@ page import="com.landray.kmss.util.UserUtil" %>
function generateTree(){
	LKSTree = new TreeView(
		"LKSTree", 
		"<bean:message bundle='sys-tag' key='sysTag.tree.title'/>", 
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, n6;
	n1 = LKSTree.treeRoot;
	
	
	//所有标签
	defaultNode = n1.AppendURLChild(
		"<bean:message key="sysTag.tree.all" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);
	
	
	//公有标签
	n2 = n1.AppendURLChild(
		"<bean:message key="sysTag.tree.public" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsPrivate=1" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdIsPrivate=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);		
	//按状态
	n3 = n2.AppendURLChild(
		"<bean:message key="sysTag.tree.status" bundle="sys-tag" />"
	);
	//生效
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTag.tree.status.true" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=1&fdIsPrivate=1" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdStatus=1&fdIsPrivate=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);	
	//失效
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTag.tree.status.false" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=0&fdIsPrivate=1" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdStatus=0&fdIsPrivate=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);	
	//按类别
	n3 = n2.AppendURLChild(
		"<bean:message key="sysTag.tree.category" bundle="sys-tag" />"
	);
	n4 = n3.AppendBeanData(
		"sysTagCategoryListService",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdCategoryId=!{value}&fdIsPrivate=1" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdCategoryId=!{value}&fdIsPrivate=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);
	//按特殊性
	n3 = n2.AppendURLChild(
		"<bean:message key="sysTag.tree.special" bundle="sys-tag" />"
	);
	//特殊标签
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTag.tree.special.true" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsSpecial=1&fdIsPrivate=1" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdIsSpecial=1&fdIsPrivate=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);	
	//非特殊标签
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTag.tree.special.false" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsSpecial=0&fdIsPrivate=1" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdIsSpecial=0&fdIsPrivate=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);
	// 标签导入
	<kmss:authShow roles="ROLE_SYSTAG_MANAGER">
		n2.AppendURLChild(
			"<bean:message bundle="sys-tag" key="sysTag.public.import"/>",
			"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.sys.tag.model.SysTagTags"/>"
		);
	</kmss:authShow>

	//私有标签		
	n2 = n1.AppendURLChild(
		"<bean:message key="sysTag.tree.private" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsPrivate=0" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdIsPrivate=0&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	); 	
	//按状态
	n3 = n2.AppendURLChild(
		"<bean:message key="sysTag.tree.status" bundle="sys-tag" />"
	);
	//生效
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTag.tree.status.true" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=1&fdIsPrivate=0" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdIsPrivate=0&fdStatus=1&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);	
	//失效
	n4 = n3.AppendURLChild(
		"<bean:message key="sysTag.tree.status.false" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=0&fdIsPrivate=0" />" --%>
		"<c:url value="/sys/tag/sys_tag_tags/sysTagTags_list_index.jsp?fdIsPrivate=0&fdStatus=0&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags" />"
	);	
	
	//系统设置
	n2 = n1.AppendURLChild(
		"<bean:message key="sysTag.tree.system" bundle="sys-tag" />"
	);
	//标签类别管理
	n3 = n2.AppendURLChild(
		"<bean:message key="sysTag.tree.system.category" bundle="sys-tag" />",
		<%-- "<c:url value="/sys/tag/sys_tag_category/sysTagCategory.do?method=list" />" --%>
		"<c:url value="/sys/tag/sys_tag_category/sysTagCategory_list_index.jsp?fdModelName=com.landray.kmss.sys.tag.model.SysTagCategory" />"
	);
	<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=tagCloudSetting"
			   requestMethod="GET">
		//标签云设置
		n3 = n2.AppendURLChild(
			"<bean:message key="sysTag.tags.cloud.setting" bundle="sys-tag"/>",
	      " <c:url value="/sys/tag/sys_tag_tags/sysTagTags.do?method=tagCloudSetting" />"
		);
	</kmss:auth>
	
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysTagGroup" bundle="sys-tag" />",
		"<c:url value="/sys/tag/sys_tag_group/index.jsp" />"
	);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
  
</template:replace>
</template:include>
