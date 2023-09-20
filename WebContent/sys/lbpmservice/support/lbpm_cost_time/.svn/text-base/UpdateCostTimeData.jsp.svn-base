<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|json2.js");
</script>
<title><bean:message bundle="sys-lbpmservice-support"
		key="lbpmAuditNote.totalTimeTitle" /></title>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle">
	<bean:message bundle="sys-lbpmservice-support"
		key="lbpmAuditNote.totalTimeTitle" />
</p>
<center>
<form id="form1" method="post" action="${pageContext.request.contextPath}/sys/lbpmservice/support/lbpm_cost_time/lbpmCostTime.do?method=updateCostTimeData">
	<table class="tb_normal" width=85%>
		<tr>
			<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.totalTimeIs" /></td>
			<td width="85%"><input type="checkbox" name="cIsCostTime"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.totalTimeRemarks" />
			</td>
		</tr>
		<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.updateRange" /><span style="color: red">*</span></td>
			<td width="85%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="templateId">
			<input type="text" name="templateName" readOnly style="width:90%" class="inputSgl">
			<a href="javascript:void(0);" onclick="selectSubFlow();"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
			</td>
		</tr>
	</table>
	</br>
	<table class="tb_nobrder" width=85%>
	<tr>
		<td align="center">
			<input type="submit" class="btnopt" value="<kmss:message key="button.ok"/>" onclick="return submitAction();">
		</td>
	</tr>
</table>
	</form>
</center>
<script type="text/javascript">
//流程模板选择框
function selectSubFlow() {
	var dialog = new KMSSDialog(true, false);
	var treeTitle = '<kmss:message key="FlowChartObject.Lang.Node.seriesprocess" bundle="sys-lbpmservice-event-seriesprocess" />';
	var node = dialog.CreateTree(treeTitle);
	dialog.winTitle = treeTitle;
	var fdId = null;
	try {
		if (FlowChartObject.IsTemplate) { // just for template
			var dialogObject=window.dialogArguments?window.dialogArguments:(window.parent.dialogArguments?window.parent.dialogArguments:null);
			if(!dialogObject){
				dialogObject=opener?opener.Com_Parameter.Dialog:(parent.opener?parent.opener.Com_Parameter.Dialog:null);
			}
			var url = dialogObject.Window.parent.parent.location.href;
			fdId = Com_GetUrlParameter(url, 'fdId');
		}
	} catch (e) {}
	node.AppendBeanData("lbpmSeriesProcessDialogService", null, null, false, fdId);
	dialog.notNull = true;
	dialog.BindingField('templateId', 'templateName');
	dialog.SetAfterShow(function(rtnData){
		//alert(rtnData);被选中的节点对象集合
	});
	dialog.Show();
}
function submitAction(){
	var vtemplateId=document.getElementsByName('templateId')[0].value;
	if(vtemplateId==""){
		alert("请选择需要更新的流程模板");
		return false;
	}else{
		return true;
	}
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>