<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.design.*,java.util.List" %>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<%
String fdMn = request.getParameter("fdModelName");
boolean hasTemp = false;
if (fdMn != null) {
	List allFlowDef = SysConfigs.getInstance().getAllFlowDef();
	for (int i = 0; i < allFlowDef.size(); i++) {
		SysCfgFlowDef flowDef = (SysCfgFlowDef) allFlowDef.get(i);
		// 判断是否有配置
		if (fdMn.equals(flowDef.getTemplateModelName())) {
			hasTemp = true;
			break;
		}
	}
}
if (hasTemp) {
%>
<c:choose>
<c:when test="${param.isCategory == 'true' or param.isCategory == '1'}">
	<%-- 简单分类 --%>
	<div id="updateTemplatePrivilegerBtn" style="display:none;">
		<input type="button" value="<bean:message key="lbpmTemplate.updatePrivileger.button" bundle="sys-lbpmservice-support"/>"
				onclick="updateCategoryPrivileger('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do" />?method=updatePrivileger&type=2&fdModelName=${JsParam.fdModelName}');">
	</div>
</c:when>
<c:when test="${param.byCategory == 'true' or param.byCategory == '1'}">
	<%-- 全局分类 --%>
	<div id="updateTemplatePrivilegerBtn" style="display:none;">
		<input type="button" value="<bean:message key="lbpmTemplate.updatePrivileger.button" bundle="sys-lbpmservice-support"/>"
			onclick="updateCategoryPrivileger('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do" />?method=updatePrivileger&type=3&fdModelName=${JsParam.fdModelName}');">
	</div>
</c:when>
<c:otherwise>
	<%-- 业务模板 --%>
	<kmss:auth 
		requestURL="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do?method=updatePrivileger" 
		requestMethod="GET">
	<div id="updateTemplatePrivilegerBtn" style="display:none;">
		<input type="button" value="<bean:message key="lbpmTemplate.updatePrivileger.button" bundle="sys-lbpmservice-support"/>"
				onclick="updateTemplatePrivileger('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do" />?method=updatePrivileger&type=2&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&cateid=${JsParam.cateid}');">
	</div>
	<script>
	function updateTemplatePrivileger(url) {
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
		requestURL="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do?method=updatePrivileger" 
		requestMethod="GET">

<script>
OptBar_AddOptBar("updateTemplatePrivilegerBtn");
function updateCategoryPrivileger(url) {
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