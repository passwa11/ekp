<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.dbcenter.echarts.application.util.ApplicationUtil,net.sf.json.JSONObject" %>
<%
	JSONObject typeJson = ApplicationUtil.getChartMode();
	String type = typeJson.toString();
%>
	var _typeObj = <%=type%>;
	var ptNode = LKSTree.treeRoot;//父节点默认为根节点，如果要修改，则传入参数ptNode
	<%
		if(StringUtil.isNotNull(request.getParameter("ptNode"))){
	%>
		ptNode = <%=request.getParameter("ptNode") %>;
	<%}%>
	var cdNode = ptNode.AppendURLChild("${lfn:message('dbcenter-echarts-application:dbEchartsNavTree.chartNav')}");
	for(var key in _typeObj){
		var mode = _typeObj[key];
		cdNode.AppendURLChild(mode["text"],"<c:url value='/dbcenter/echarts/application/dbEchartsNavTree.do?method=treeIndex&mainModelName=${param.mainModelName }&fdKey=${param.fdKey}&modelName='/>" + mode['navTreeModelName']);
	}
	cdNode.AppendURLChild("${lfn:message('dbcenter-echarts-application:dbEchartsNavTree.treeShow')}","<c:url value='/dbcenter/echarts/application/navTree/tree_show_index.jsp?mainModelName=${param.mainModelName }&fdKey=${param.fdKey}' />");
	
