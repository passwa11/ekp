<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script type="text/javascript">
	Com_IncludeFile("dialog.js|doclist.js|formula.js|validation.jsp|validation.js|plugin.js");
</script>
<script>
DocList_Info.push("paramsConfigList");
var dialogObject = null;
if(window.showModalDialog) {
	dialogObject = window.dialogArguments;
	if (dialogObject == null) {
		dialogObject = parent.Com_Parameter.Dialog;
	}
} else if(opener) {
	dialogObject = opener.Com_Parameter.Dialog;
} else {
	if (dialogObject == null){
		dialogObject = parent.Com_Parameter.Dialog;
	}
}
</script>
<style>
.DIV_EditButtons{position: fixed;    left: 0;    right: 0;    text-align: center;    padding-top: 10px;    padding-bottom: 10px;    bottom: 0;    background-color: #fff;    border-top: 1px solid #d5d5d5;    z-index: 99;}
#paramsConfigList input[name="fdParamName"][readonly]{
	border: none;
}
</style>
<html:form action="/sys/lbpmservice/support/lbpmDynamicSubFlow.do">
<center>
<table class="tb_normal" width=95% id="paramsConfigList" align="center" style="table-layout:fixed;margin-bottom:50px;" frame=void>
	<tr>
		<td width="50%"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.param" /></td>
		<td width="50%"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.column" /></td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display:none" class="content">
		<td>
			<input type="hidden" name="fdParamValue">
			<input type="text" name="fdParamName" readonly="readonly">
		</td>
		<td>
			<input type="hidden" name="fdFormName">
			<select name="fdFormValue" onchange="fillFormName(this);" validate="required" subject="字段">
				<option value=""><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.please.select" /></option>
			</select>
			<span class="txtstrong">*</span>
		</td>
	</tr>
</table>
<div class="DIV_EditButtons" style="height:30px;">
	<ui:button text="${lfn:message('button.save') }" order="1" onclick="save();"  style="width:77px;">
	</ui:button>
	<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeDialog();" style="width:77px;padding-left:10px">
	</ui:button>
</div>
</center>
</html:form>
<script>
	var _validation = $KMSSValidation();

	Com_AddEventListener(window, "load", function() {
		var content = dialogObject.content;
		var nodeParams = dialogObject.nodeParams;
		var paramsConfig = [];
		if(nodeParams){
			paramsConfig = JSON.parse(nodeParams);
		}
		var processData = JSON.parse(content);
			var fields = new KMSSData().AddBeanData("lbpmEmbeddedSubFlowTreeService&type=dict&fdProcessTemplateId="+dialogObject.fdProcessTemplateId).GetHashMapArray();
			for(var i=0;i<processData.length;i++){
				var param = processData[i];
				var fieldValues = new Object();
				fieldValues["fdParamValue"]=param.fdParamValue;
				fieldValues["fdParamName"]=param.fdParamName;
				var tr = DocList_AddRow("paramsConfigList",null,fieldValues);
				var $select = $(tr).find("select[name='fdFormValue']");
				var fdParamType = param.fdParamType;
				if(fdParamType){
					var fdIsMulti = param.fdIsMulti;
					if(fdParamType && fdParamType.indexOf("ORG_TYPE_") != -1){
						if(fdParamType=='ORG_TYPE_PERSON'){
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson";
							}
						}else{
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement";
							}
						}
					}
				}
				for(var j=0;j<fields.length;j++){
					var info = fields[j];
					if(info.type == fdParamType){
						$select.append("<option value='"+info.name+"'>"+info.label+"</option>");
					}
				}
				for(var k=0;k<paramsConfig.length;k++){
					if(paramsConfig[k].fdParamValue == param.fdParamValue){
						$select.val(paramsConfig[k].fdFormValue);
						$(tr).find("[name='fdFormName']").val(paramsConfig[k].fdFormName);
					}
				}
			}
	})

	function fillFormName(select) {
		var $option=$(select).find("option:selected");
		$(select).closest("tr").find("[name='fdFormName']").val($option.text());
	}

	function save(){
		if(!_validation.validate()){
			return;
		}
		var content = [];
		$("#paramsConfigList .content").each(function(){
			var fdParamValue = $(this).find("[name='fdParamValue']").val();
			var fdParamName = $(this).find("[name='fdParamName']").val();
			var fdFormValue = $(this).find("[name='fdFormValue']").val();
			var fdFormName = $(this).find("[name='fdFormName']").val();
			content.push({"fdParamValue":fdParamValue,"fdParamName":fdParamName,"fdFormValue":fdFormValue,"fdFormName":fdFormName});
		});
		var paramsConfig = JSON.stringify(content);
		if(paramsConfig!=dialogObject.nodeParams){
			var url = '<c:url value="/sys/lbpmservice/support/lbpmDynamicSubFlow.do?method=updateParam" />';
			var data = {"fdProcessTemplateId":dialogObject.fdProcessTemplateId,"fdNodeId":dialogObject.fdNodeId,"paramsConfig":paramsConfig};
			$.ajax({
				type : "POST",
				data : data,
				url : url,
				async : false,
				dataType : "json",
				success : function(json){
					if (typeof(window.$dialog) != 'undefined') {
						$dialog.hide(true);
					} else {
						window.close();
					}
				},
				beforeSend : function() {
					seajs.use(['lui/dialog'], function(dialog) {
						window.freeflow_load = dialog.loading();
					});
				},
				complete : function() {
					seajs.use(['lui/dialog'], function(dialog) {
						if(window.freeflow_load != null) {
							window.freeflow_load.hide();
						}
					});
				}
			});
		}else{
			Com_CloseWindow();
		}
	}

	function closeDialog(){
		if(!confirm('<bean:message key="message.closeWindow"/>')){
			return;
		}
		Com_CloseWindow();
	}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>