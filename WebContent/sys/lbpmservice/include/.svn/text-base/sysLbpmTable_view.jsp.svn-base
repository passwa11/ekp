<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|formula.js|dialog.js|data.js");
</script>
<script>
var qdomain = Com_GetUrlParameter(location.href, "domain");
if (qdomain != null && qdomain != '') {
	document.domain = qdomain;
}
function initialPage(){
	try {
		var arguObj = document.getElementById("flowTable");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var height = arguObj.offsetHeight + 0;
			if(height>0)
				window.frameElement.style.height = height + "px";
		}
		setTimeout(initialPage, 200);
	} catch(e) {
	}
}
Com_AddEventListener(window,'load',function()  {
	setTimeout(initialPage, 200);
});
</script>
<script>
( function(lbpm) {
	lbpm.constant.REVIEWPOINT="<bean:message bundle='sys-lbpmservice' key='lbpmProcessTable.reviewPoint' />" ;//审批
	lbpm.constant.SIGNPOINT="<bean:message bundle='sys-lbpmservice' key='lbpmProcessTable.signPoint' />"; //签字
	
	lbpm.constant.STATUS_NORMAL_MSG="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.STATUS_NORMAL'/>"; //未执行
	lbpm.constant.STATUS_PASSED_MSG="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.STATUS_PASSED'/>" ;//已执行
	lbpm.constant.STATUS_RUNNING_MSG="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.STATUS_RUNNING'/>";//当前节点
	
	lbpm.constant.PROCESSTYPE="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.processType'/>";
	lbpm.constant.PROCESSTYPE_0="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.processType_0'/>";
	lbpm.constant.PROCESSTYPE_1="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.processType_1'/>";
	lbpm.constant.PROCESSTYPE_20="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.processType_20'/>";
	lbpm.constant.PROCESSTYPE_21="<bean:message bundle='sys-lbpmservice' key='lbpmSupport.processType_21'/>";
})(parent.lbpm);
</script>
<script>
Com_IncludeFile("syslbpmtable.js","${KMSS_Parameter_ContextPath}sys/lbpmservice/include/","js",true);
Com_IncludeFile("workflow.js","${KMSS_Parameter_ContextPath}sys/lbpm/flowchart/js/","js",true);
</script>

</head>
<body style="margin:0px" onload="FlowTable_Initialize();" >
<div id="fieldDiv" style="display:none" >
</div>

<table class="tb_normal" width="100%"  id="flowTable">
	<tr class="tr_normal_title">
		<td colspan="6" align="left">
			<label>
			<input type="checkbox" name="isShowAllRows" id="isShowAllRows" onclick="FlowTable_IsShowAllRows(this)" />
			<bean:message key = "lbpmProcessTable.showAllRow" bundle="sys-lbpmservice" />
			</label>
			<label>
			<input type="checkbox" name="isFilterRow" id="isFilterRow" onclick="FlowTable_IsFilterRow(this)" />
			<bean:message key = "lbpmProcessTable.filterRow" bundle="sys-lbpmservice" />
			</label>
		</td>
	</tr>
	<tr class="tr_normal_title">
		<td  width="5%"><bean:message key = "lbpmProcessTable.nodeId" bundle="sys-lbpmservice" />
		</td>
		<td  width="20%"><bean:message key = "lbpmProcessTable.nodeName" bundle="sys-lbpmservice" />
		</td>
		<td  width="30%"><bean:message key = "lbpmProcessTable.nodeHandler" bundle="sys-lbpmservice" />
		</td>
		<td  width="15%"><bean:message key = "lbpmProcessTable.nodeHandMethod" bundle="sys-lbpmservice" />
		</td>
		<td  width="20%"><bean:message key = "lbpmProcessTable.nodeFlowTo" bundle="sys-lbpmservice" />
		</td>
		<td ><bean:message key = "lbpmProcessTable.nodeDescribe" bundle="sys-lbpmservice" />
		</td>
	</tr>
	<tbody id="flowTableTr" >
	</tbody>
</table>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>