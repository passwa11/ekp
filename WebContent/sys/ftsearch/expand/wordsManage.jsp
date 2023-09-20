<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.notify.actions.SysNotifyTodoAction"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysFtsearch.ftsearch.wordsManage" bundle="sys-ftsearch-expand" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSFTSEARCHEXPAND_MAINTAINER;ROLE_SYSFTSEARCHEXPAND_DEFAULT">
    <%-- 汉字联想词表 --%>
    defaultNode = n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchChineseLegend" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_chinese_legend/sysFtsearchChineseLegend.do?method=list&orderby=sysFtsearchChineseLegend.fdSearchFrequency&ordertype=down" />"
    );
    <%-- 分面搜索配置 --%>
     //n2 = n1.AppendURLChild(
        //"<bean:message key="table.sysFtsearchFacet" bundle="sys-ftsearch-expand" />",
        //"<c:url value="/sys/ftsearch/expand/sys_ftsearch_facet/sysFtsearchFacet.do?method=list" />"
    //);
    <%-- 同义词结果表     --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchSynonymsSet" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=list" />"
    );
    <%-- 搜索热词 --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchHotword" bundle="sys-ftsearch-expand" />"
    );
    n3 = n2.AppendURLChild(
        "<bean:message key="table.sysFtsearchCurrentHotword" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=list&orderby=sysFtsearchHotword.fdWordOrder desc,sysFtsearchHotword.fdSearchFrequency&ordertype=down" />"
    );
    n3 = n2.AppendURLChild(
        "<bean:message key="table.sysFtsearchDisabledHotword" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=list&fdDisabled=1&orderby=sysFtsearchHotword.fdWordOrder desc,sysFtsearchHotword.fdSearchFrequency&ordertype=down" />"
    );    
   <%-- 词库管理表 --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchParticiple" bundle="sys-ftsearch-expand" />"
    );
    <%-- 自定义词典 --%>
    n3 = n2.AppendURLChild(
        "<bean:message key="table.sysFtsearchUserParticiple" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_user_participle/sysFtsearchUserParticiple.do?method=list" />"
    )
    <%--歧义词分词管理 --%>
    n3 = n2.AppendURLChild(
        "<bean:message key="table.sysFtsearchAmbParticiple" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do?method=list" />"
    )
    </kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>