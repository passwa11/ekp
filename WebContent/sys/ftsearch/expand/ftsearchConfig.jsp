<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.notify.actions.SysNotifyTodoAction"%>
<%@ page import="com.landray.kmss.sys.ftsearch.util.MultSystemlicense" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
	<%
    boolean multSystemFlag=MultSystemlicense.getLicene();
    String searchEngineType= ResourceUtil.getKmssConfigString("sys.ftsearch.config.engineType");
%>
	
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysFtsearch.ftsearch.config" bundle="sys-ftsearch-expand" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSFTSEARCHEXPAND_MAINTAINER">
    <%-- 默认搜索模块配置--%>
    defaultNode = n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchConfig" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/sys_ftsearch_config/sysFtsearchConfig.do?method=edit" />"
    );
    
    <%-- 字段权值配置 --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="table.fieldBoostingConfig" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/fieldBoostingConfig.do?method=getSearchField" />"
    );
    
    <%-- 搜索优先展示区域配置 --%>
    if(<%="elasticsearch".equals(searchEngineType) || StringUtil.isNull(searchEngineType)%>){
        n2 = n1.AppendURLChild(
            "<bean:message key="reaultDisplayArea.customDisplayAreaConfig" bundle="sys-ftsearch-expand" />",
            "<c:url value="/sys/ftsearch/expand/customDisplayAreaConfig.do?method=load" />"
        );
    }
    
    if(<%=multSystemFlag%>){
        <%-- 多系统支持配置 --%>
        n2 = n1.AppendURLChild(
            "<bean:message key="table.sysFtsearchMultisystem" bundle="sys-ftsearch-expand" />"
        );
        <%-- 多系统支持 --%>
        n3 = n2.AppendURLChild(
            "<bean:message key="table.sysFtsearchMultisystem" bundle="sys-ftsearch-expand" />",
            "<c:url value="/sys/ftsearch/expand/sys_ftsearch_multisystem/sysFtsearchMultisystem.do?method=list" />"
        );
        
        <%-- 多系统模块类别分类 --%>
        n3 = n2.AppendURLChild(
            "<bean:message key="table.sysFtsearchModelgroup" bundle="sys-ftsearch-expand" />",
            "<c:url value="/sys/ftsearch/expand/sys_ftsearch_modelgroup/sysFtsearchModelgroup.do?method=list" />"
        );
    }
    <%-- 快照设置 --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="sysFtsearch.snapshotConfig" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/snapshotConfig.do?method=getSnapshotConfig" />"
    );
    
    n2 = n1.AppendURLChild(
        "<bean:message key="sysFtsearch.defaultModeConfigInfo" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/snapshotConfig.do?method=getDefaultFtSearchModeConfig" />"
    );
    <!-- 开关配置 -->
    n2 = n1.AppendURLChild(
        "<bean:message key="sys_ftsearch_config_switch" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_config/sysFtsearchConfig.do?method=edit" />"
    );
    <%-- 模块最后索引记录 --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="sysFtsearch.indexStatus.lastIndexTime" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do?method=list" />"
    );
    </kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>