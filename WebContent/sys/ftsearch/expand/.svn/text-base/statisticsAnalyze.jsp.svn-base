<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.notify.actions.SysNotifyTodoAction"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/jquery.js"></script>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sysFtsearch.ftsearch.statistics.analyze" bundle="sys-ftsearch-expand" />",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5, defaultNode;
	n1 = LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_SYSFTSEARCHEXPAND_MAINTAINER;ROLE_SYSFTSEARCHEXPAND_DEFAULT">
	<kmss:authShow roles="ROLE_SYSFTSEARCHEXPAND_USELESS">
    <%-- 阅读文档  三员模式下屏蔽--%>
    defaultNode = n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchReadLog" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_read_log/sysFtsearchReadLog.do?method=list" />"
    );
    </kmss:authShow>
    <%-- 用户搜索词排行 --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="table.sysFtsearchWord.ranking" bundle="sys-ftsearch-expand" />"
    );
    n3 = n2.AppendURLChild(
       "<bean:message key="search_word_ranking_config" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sysFtsearchWordStatisticsRankingConfig.do?method=view" />"
    );
    n3 = n2.AppendURLChild(
       "<bean:message key="table.sysFtsearchWord.ranking.day" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=wordRanking&day=-1" />"
    );
    n3 = n2.AppendURLChild(
       "<bean:message key="table.sysFtsearchWord.ranking.week" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=wordRanking&day=-7" />"
    );
    n3 = n2.AppendURLChild(
       "<bean:message key="table.sysFtsearchWord.ranking.month" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=wordRanking&day=-30" />"
    );
    
    <%--  --%>
    n2 = n1.AppendURLChild(
        "<bean:message key="sysFtsearch.index.running.record.history" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_index/sysFtsearchIndexRecord.jsp" />"
    );
	    <kmss:authShow roles="ROLE_SYSFTSEARCHEXPAND_USELESS">
	    <%-- Elasticsearch集群状态、Head插件集成 --%>
			var sendUrl = "<c:url value="/sys/ftsearch/expand/head/elasticsearchHead.do?method=isNewVersion"/>";
			var newVersion =  0;
			$.ajax({url:sendUrl,
	            async:false,timeout:2000, success:function(result){
        				newVersion = result}});

	        if(newVersion == 1 || newVersion == "1"){
	        	n2 = n1.AppendURLChild(
		        	"<bean:message key="sysFtsearch.cluster.status.plugin" bundle="sys-ftsearch-expand" />",
		        	"<c:url value="/sys/ftsearch/expand/elasticsearch-head-master/index.jsp" />"
		    	);
	        }else{ 
	 			n2 = n1.AppendURLChild(
		        	"<bean:message key="sysFtsearch.cluster.status.plugin" bundle="sys-ftsearch-expand" />",
		        	"<c:url value="/sys/ftsearch/expand/sys_ftsearch_head/index.jsp" />"
		    	);
		    }
	    </kmss:authShow>
    </kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>