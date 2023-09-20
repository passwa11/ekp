<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.design.*,java.util.List" %>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog = dialog;
});
</script>
<%
String fdMn4Excel = request.getParameter("fdModelName");
String exportExcel = request.getParameter("exportExcel");
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
if(exportExcel!=null&&exportExcel.equals("false")){
	hasTemp4Excel = false;
}
if (hasTemp4Excel) {
%>
<c:choose>
<c:when test="${param.isCategory == 'true' or param.isCategory == '1'}">
	<%-- 简单分类 --%>
	<kmss:auth  requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel"  requestMethod="GET">
	    <ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.nodes2Excel.button')}" order="4"  onclick="excel4Category('${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel&type=2&fdModelName=${param.fdModelName}');" >
	    </ui:button>
	    <ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.flowchart2Excel.button')}" order="4"  onclick="flowchartExportCategory('${KMSS_Parameter_ContextPath}sys/lbpmservice/flowchartimport/LbpmFlowchartImportAction.do?method=doExportNodesExcel&type=2&fdModelName=${param.fdModelName}&fdKey=${param.fdKey}');" >
	    </ui:button>
	</kmss:auth>
</c:when>
<c:when test="${param.byCategory == 'true' or param.byCategory == '1'}">
	<%-- 全局分类 --%>
	<kmss:auth  requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel"  requestMethod="GET">
	    <ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.nodes2Excel.button')}" order="4"  onclick="excel4Category('${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel&type=3&fdModelName=${param.fdModelName}');" >
	    </ui:button>
	</kmss:auth>
</c:when>
<c:otherwise>
	<%-- 业务模板 --%>
	<kmss:auth  requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel&fdModelName=${param.fdModelName}"  requestMethod="GET">
	    <ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.nodes2Excel.button')}" order="4"  onclick="excel4Template('${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel&type=2&fdModelName=${param.fdModelName}&fdKey=${param.fdKey}&cateid=${param.cateid}');">
	    </ui:button>
	     <ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.flowchart2Excel.button')}" order="4"  onclick="flowchartExportTemplate('${KMSS_Parameter_ContextPath}sys/lbpmservice/flowchartimport/LbpmFlowchartImportAction.do?method=doExportNodesExcel&type=2&fdModelName=${param.fdModelName}&fdKey=${param.fdKey}&cateid=${param.cateid}');" >
	    </ui:button>
	<script>
	function excel4Template(url) {
		if($("input[name='List_Selected']:checked").length == 0) {
			dialog.alert('<bean:message key="page.noSelect"/>');
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
	
	function flowchartExportTemplate(url) {
		if($("input[name='List_Selected']:checked").length == 0) {
			dialog.alert('<bean:message key="page.noSelect"/>');
			return;
		}
		$("input[name='List_Selected']:checked").each(function(i){
			Com_OpenWindow(url + "&fdIds=" + this.value);
		});
	}
	</script>
	</kmss:auth>
</c:otherwise>
</c:choose>

<kmss:auth 
		requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel" 
		requestMethod="GET">

<script>
function excel4Category(url) {
	if($("input[name='List_Selected']:checked").length == 0) {
		dialog.alert('<bean:message key="page.noSelect"/>');
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

function flowchartExportCategory(url) {
	if($("input[name='List_Selected']:checked").length == 0) {
		dialog.alert('<bean:message key="page.noSelect"/>');
		return;
	}
	var selList = LKSTree.GetCheckedNode();
	for(var i = 0; i < selList.length; i++){
		Com_OpenWindow(url + "&fdIds=" + selList[i].value);
	}
}

</script>
</kmss:auth>
<%
}
%>