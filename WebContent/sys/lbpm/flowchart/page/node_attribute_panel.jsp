<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.node.*" %>
<%@ page import="com.landray.kmss.sys.lbpm.engine.manager.flowchart.FlowchartIncludeManager" %>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%
	request.setAttribute("isLangSuportEnabled", MultiLangTextGroupTag.isLangSuportEnabled());
%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ include file="/resource/jsp/common.jsp"%>
<meta http-equiv="x-ua-compatible" content="IE=5"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="../css/panel.css">
<% response.addHeader("X-UA-Compatible", "IE=5"); %>
<script>
Com_IncludeFile("jquery.js|doclist.js|docutil.js|dialog.js|formula.js");
</script>
<script src="../js/workflow.js"></script>
<script src="../js/freeflow/attribute.js"></script>
<script src="../js/freeflow/node_attribute.js"></script>
<script>
	var SettingInfo = null;
	//统一调用此方法获取默认值与功能开关的值
	function getSettingInfo(){
		if (SettingInfo == null) {
			SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
		}
		return SettingInfo;
	}
	
	function saveNodeData(){
		if(!FlowChartObject.Nodes.isFreeFlowCanEdit()){
			return;
		}
		var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
		if(isOpenNewWin=="true"){
			if($("input[name='wf_handlerIds']").length>0&&!$("input[name='wf_handlerIds']").val()&&!$("input[name='wf_ignoreOnHandlerEmpty']").is(':checked')){
				alert(FlowChartObject.Lang.checkFreeFlowHandlerEmptyMsg);
				return false;
			}
		}
		if(AttributeObject.submitDocument()){
			FlowChartObject.Nodes.saveOrUpdateFreeflowVersion();
			if(isOpenNewWin!="true"){
				dialogObject.AfterShow(true);
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
</script>
<%
String nodeTypeParam = request.getParameter("nodeType");
nodeTypeParam = org.apache.commons.lang.StringEscapeUtils.escapeHtml(nodeTypeParam);
String modelNameParam = request.getParameter("modelName");
modelNameParam = org.apache.commons.lang.StringEscapeUtils.escapeHtml(modelNameParam);

FlowchartIncludeManager includes = FlowchartIncludeManager.getInstance();
pageContext.setAttribute("includes", includes.getNodeAttributeJsList(modelNameParam));
%>
<c:forEach items="${includes }" var="include">
<script src="<c:url value="${include }" />"></script>
</c:forEach>

</head>
<body onload="AttributeObject.initDocument();">

<c:if test="${JsParam.isOpenNewWin ne 'true'}">
	<img alt="关闭" id="closeWindw" src='<%=request.getContextPath() %>/sys/ui/extend/theme/default/icon/s/close_x.png'>
</c:if>
<br>
<center>
<kmss:message key="FlowChartObject.Lang.Node.id" bundle="sys-lbpm-engine" />: <input name="wf_id" class="inputread" size="4" readonly> <span id="nodeType"></span>

<%-- 读取扩展点，插入页面 --%>
<%
NodeType nodeType = NodeTypeManager.getInstance().getType(nodeTypeParam, modelNameParam);
String attributePage = nodeType.getAttributesJsp();
attributePage = attributePage.replace("node_attribute", "node_attribute_panel");
pageContext.setAttribute("attributePage", attributePage);
%>
<c:import url="${attributePage}" charEncoding="UTF-8">
	<c:param name="nodeType" value="${param.nodeType}" />
	<c:param name="modelName" value="${param.modelName }" />
</c:import>
<br><br>
<c:if test="${JsParam.isOpenNewWin eq 'true'}">
	<div id="DIV_EditButtons" style="display: none;">
		<input name="btnOK" type="button" class="btnopt" onclick="if(saveNodeData())window.close();" 
			value="  <kmss:message key="FlowChartObject.Lang.OK" bundle="sys-lbpm-engine" />  ">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="btnCancel" type="button" class="btnopt" onclick="window.close();" 
			value="  <kmss:message key="FlowChartObject.Lang.Cancel" bundle="sys-lbpm-engine" />  ">
	</div>
	<div id="DIV_ReadButtons" style="display: none;">
		<input name="btnClose" type="button" class="btnopt" onclick="window.close();" 
			value="  <kmss:message key="FlowChartObject.Lang.Close" bundle="sys-lbpm-engine" />  ">
	</div>
</c:if>
<br><br>
</center>
<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
</script>

<script>
$(document).ready(function () {
	$("#closeWindw").click(function(){
		$('#nodePanel', window.parent.document).css('display','none');;
	});
	
});

</script>
<style>
body, input, textarea, select, div, a, table, tr, td, th {
    font-family: "PingFang SC", "Lantinghei SC", "Helvetica Neue", Arial, "Microsoft YaHei", "WenQuanYi Micro Hei", "Heiti SC", "Segoe UI", sans-serif;
    font-size: 12px;
}
#DIV_EditButtons{position: fixed;    left: 0;    right: 0;    text-align: center;    padding-top: 10px;    padding-bottom: 10px;    bottom: 0;    background-color: #fff;    border-top: 1px solid #d5d5d5;    z-index: 1070;}
</style>
</body>
</html>