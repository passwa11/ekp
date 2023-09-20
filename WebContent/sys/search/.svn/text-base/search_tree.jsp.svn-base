<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var pSearchNode = ${JsParam['pNode'] };
if(!pSearchNode){
	pSearchNode = LKSTree.treeRoot;
}
var cSearchNode = pSearchNode.AppendURLChild(
	"${JsParam['pNodeName'] }"
);
var cateNode = cSearchNode.AppendURLChild(
	"<bean:message bundle="sys-search" key="sysSearchCate.title"/>",
	"<c:url value="/sys/search/sys_search_cate/index.jsp?fdModelName=${JsParam['fdModelName'] }&fdTemplateModelName=${JsParam['fdTemplateModelName'] }&fdKey=${JsParam['fdKey'] }"/>"
);
var mainNode = cSearchNode.AppendURLChild(
	"<bean:message bundle="sys-search" key="sysSearchMain.title"/>",
	"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=${JsParam['fdModelName'] }&showCate=${JsParam['showCate'] }&fdTemplateModelName=${JsParam['fdTemplateModelName'] }&fdKey=${JsParam['fdKey'] }"/>"
);
mainNode.AppendBeanData(
	"sysSearchCateService&parentId=!{value}&item=fdName:fdId&fdModelName=${JsParam['fdModelName'] }",
	"<c:url value="/sys/search/sys_search_main/index.jsp?category=!{value}&showCate=${JsParam['showCate'] }&fdModelName=${JsParam['fdModelName'] }&fdTemplateModelName=${JsParam['fdTemplateModelName'] }&fdKey=${JsParam['fdKey'] }"/>"
);
	
