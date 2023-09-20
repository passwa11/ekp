<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.design.*,java.util.List" %>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<%
String fdMn4Excel = request.getParameter("fdModelName");
boolean hasTemp4Excel = false;
if (fdMn4Excel != null) {
	List allFlowDef = SysConfigs.getInstance().getAllFlowDef();
	for (int i = 0; i < allFlowDef.size(); i++) {
		SysCfgFlowDef flowDef = (SysCfgFlowDef) allFlowDef.get(i);
		// 判断是否有配置
		if (fdMn4Excel.equals(flowDef.getTemplateModelName())) {
			hasTemp4Excel = true;
			break;
		}
	}
}
if (hasTemp4Excel) {
%>
<c:choose>
<c:when test="${param.isCategory == 'true' or param.isCategory == '1'}">
	<%-- 简单分类 --%>
	<div id="excel4TemplateBtn" style="display:none;">
		<input type="button" value="<bean:message key="lbpmTemplate.nodes2Excel.button" bundle="sys-lbpmservice-support"/>"
				onclick="excel4Category('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do" />?method=doExportNodesExcel&type=2&fdModelName=${JsParam.fdModelName}');">
	</div>
</c:when>
<c:when test="${param.byCategory == 'true' or param.byCategory == '1'}">
	<%-- 全局分类 --%>
	<div id="excel4TemplateBtn" style="display:none;">
		<input type="button" value="<bean:message key="lbpmTemplate.nodes2Excel.button" bundle="sys-lbpmservice-support"/>"
			onclick="excel4Category('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do" />?method=doExportNodesExcel&type=3&fdModelName=${JsParam.fdModelName}');">
	</div>
</c:when>
<c:otherwise>
	<%-- 业务模板 --%>
	<kmss:auth 
		requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel" 
		requestMethod="GET">
	<div id="excel4TemplateBtn" style="display:none;">
		<input type="button" value="<bean:message key="lbpmTemplate.nodes2Excel.button" bundle="sys-lbpmservice-support"/>"
				onclick="excel4Template('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do" />?method=doExportNodesExcel&type=2&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&cateid=${JsParam.cateid}');">
	</div>
	<script>
	function excel4Template(url) {
		if(!List_CheckSelect()) {
			return;
		}
		var s = "";
		$("input[name='List_Selected']:checked").each(function(i){
			if(i > 0) {
				s += ";";
			}
			s += this.value;
		});
		Com_OpenWindow(url + "&fdIds=" + s);
	}
	</script>
	</kmss:auth>
</c:otherwise>
</c:choose>

<kmss:auth 
		requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel" 
		requestMethod="GET">

<script>
OptBar_AddOptBar("excel4TemplateBtn");
function excel4Category(url) {
	if(!List_CheckSelect()){
		return;
	}
	var s = "";
	var selList = LKSTree.GetCheckedNode();
	for(var i = 0; i < selList.length; i++){
		if(i > 0) {
			s += ";";
		}
		s += selList[i].value;
	}
	Com_OpenWindow(url + "&fdIds=" + s);
}
</script>

</kmss:auth>

<%
}
%>