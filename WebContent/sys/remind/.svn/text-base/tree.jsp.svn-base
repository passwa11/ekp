<%@ page import="com.landray.kmss.sys.remind.util.SysRemindUtil"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	JSONArray modules = SysRemindUtil.getModules();
%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message bundle="sys-remind" key="module.sys.remind"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3,n4,n5, defaultNode;
	n1 =  LKSTree.treeRoot;
	<%
	for(int i=0; i<modules.size(); i++) {
		JSONObject module = modules.getJSONObject(i);
		JSONArray templates = module.getJSONArray("templates");
	%>
		n2 = n1.AppendChild(
			"<%=module.getString("title")%>"
		);
		<%
		for(int j=0; j<templates.size(); j++) {
			JSONObject template = templates.getJSONObject(j);
			JSONArray tmpls = template.getJSONArray("tmpls");
		%>
			n3 = n2.AppendChild(
				"<%=template.getString("title")%>"
			);
			<%
			for(int k=0; k<tmpls.size(); k++) {
				JSONObject tmpl = tmpls.getJSONObject(k);
				String tmplId = tmpl.getString("tmplId");
			%>
				n3.AppendURLChild(
					"<%=tmpl.getString("title")%>",
					"<c:url value="/sys/remind/sys_remind_main/index.jsp" />"+"?tmplId="+"<%=tmplId%>"
				);
			<%
			}
			%>
		<%
		}
		%>
	<%
	}
	%>
	<!-- 提醒任务 -->
	n1.AppendURLChild(
		"${lfn:message('sys-remind:table.sysRemindMainTask')}",
		"<c:url value="/sys/remind/sys_remind_main_task/index.jsp" />"
	);
	<!-- 提醒任务日志 -->
	n1.AppendURLChild(
		"${lfn:message('sys-remind:table.sysRemindMainTaskLog')}",
		"<c:url value="/sys/remind/sys_remind_main_task_log/index.jsp" />"
	);
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>