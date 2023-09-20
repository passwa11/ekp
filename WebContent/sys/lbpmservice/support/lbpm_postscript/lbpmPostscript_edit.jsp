<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
<style>
	.fdNotifyType{
		display:none;
	}
	.fdNotifyTarget{
		display:none;
	}
	.notifyTarget_label{
   	 	display: inline-block;
    	margin-right: 10px;
	}
</style>
<script>
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
Com_IncludeFile("swf_attachment.js?mode=edit","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);
/**
 * 校验附言必填和长度小于2000
 */
function _validateFdPostscript(){
	var $KMSSValidation = $GetKMSSDefaultValidation();
	var fdAduitNote = $("textarea[name='fdPostscript']",document)[0];
	var result = $KMSSValidation.validateElement(fdAduitNote);
	if (!result){
		return false;
	}else{
		return true;
	}
}

/**
 * 获取节点通知方式
 */
getNotifyType4NodeHTML = function(nodeId) {
	var processId = "${lbpmPostscriptForm.fdModelId}";
	var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do?method=getNotifyType&fdFactNodeId=" + nodeId + "&fdProcessId=" + processId;
	$.ajaxSettings.async = false;
	var html=[];
	$.get(url, function(result){
		var data = JSON.parse(result);
		var fieldName = "__notify_type_4opr_" + nodeId;
		var hiddenId = fieldName + "_param";

		var jsCode = [];
		jsCode.push("var fields=document.getElementsByName('" + fieldName
				+ "');");
		jsCode.push("var values='';");
		jsCode.push("for(var i=0; i<fields.length; i++) "
				+ "if(fields[i].checked) values+=';'+fields[i].value;");
		jsCode.push("if(values!='') document.getElementById('" + hiddenId
				+ "').value=values.substring(1);");
		jsCode.push("else document.getElementById('" + hiddenId
				+ "').value='';");


		html.push("<input type=hidden name='_notifyType_node' value='"+nodeId+"'>");
		var s="";var n=0;
		for (var key in data) {
			html.push("<label><input type='checkbox'  name='"+fieldName+"' value='"+key+"'");
			if("todo" === key){
				html.push(" checked ");
				if(n>0){
					s+=";";
				}
				s+=key;
				n++;
			}
			html.push("onclick=\"" + jsCode.join("") + "\">"+data[key]+"</label>&nbsp;");
		}
		html.push("<input id='"+hiddenId+"' type=hidden name='_notifyType_"+nodeId+"' value='"+s+"'>");
		
	});
	$.ajaxSettings.async = true;
	return html.join("");
	
}

/**
 * 通知对象复选框值改变事件
 */
function notifyTargetTypeChange(src){
	var fields = document.getElementsByName('_fdNotifyTargetType');
	var values='';
	for (var i = 0; i < fields.length; i++) {
		if (fields[i].checked) {
			values += ';' + fields[i].value;
		}
	}
	var fdNotifyTargetType = document.getElementById('notifyTargetType');
	if (values != '') {
		fdNotifyTargetType.value = values.substring(1);
	} else {
		fdNotifyTargetType.value = '';
	}
}

/**
 * 通知checkbox值改变事件
 */
function notifySwitchChange(src){
	var fdFactNodeId = '${param.fdFactNodeId}';
	if(src.checked){
		var html = getNotifyType4NodeHTML(fdFactNodeId);
		//起草节点只有起草人可以附言,因此不显示通知起草人选项
		if (fdFactNodeId == "N2"){
			$("#notifyDrafter").hide();
		}
		$("#notifyType").empty();
		$("#notifyType").append(html);
		$("#fdNotifyTargetRow").show();
		$("#fdNotifyTypeRow").show();
	}else{
		$("#fdNotifyTargetRow").hide();
		$("#fdNotifyTypeRow").hide();
	}
}
</script>
<html:form action="/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do">
	<p class="txttitle"> <bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdPostscript" /></p>
<%--  <ui:toolbar layout="sys.ui.toolbar.float">
	<ui:button text="${ lfn:message('button.save') }" styleClass="lui_toolbar_btn_gray"
		onclick="if (_validateFdPostscript()) {Com_Submit(document.lbpmPostscriptForm, 'save');}" />
	<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
</ui:toolbar> --%>
	<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdPostscript"/>
				</td><td width="85%">
					<xform:textarea property="fdPostscript" style="width:95%;height:200px" required="true" validators="maxLength(4000)" showStatus="edit" value="${lbpmPostscript.fdPostscript}" />
				</td>
			</tr>
			 <tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdAttachments"/>
				</td>
				 <td width="85%">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
				          <c:param name="formBeanName" value="${requestScope['formBeanName']}"/>
				          <c:param name="fdKey" value="${lbpmPostscriptForm.fdId}"/>
				          <c:param name="fdModelId" value="${lbpmPostscriptForm.fdModelId}"/>
				          <c:param name="fdModelName" value="${lbpmPostscriptForm.fdModelName}"/>
				          <c:param name="fdViewType" value="byte" />
					 </c:import>
				</td>
			</tr>
			<!-- 是否通知 -->
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotify"/>
				</td>
				 <td width="85%">
				 	 <input type="checkbox" name="fdIsNotify" value="1" onclick="notifySwitchChange(this);"/>
				</td>
			</tr>
			<!-- 通知对象 -->
			<tr id="fdNotifyTargetRow" class="fdNotifyTarget">
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyTarget"/>
				</td>
				 <td width="85%">
				 	 <div id="notifyContent">
						<label id="notifyDrafter" class="notifyTarget_label">
							<input type="checkbox" name="_fdNotifyTargetType" value="0" onclick="notifyTargetTypeChange(this);"/>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyDrafter"/>
						</label>
						<label class="notifyTarget_label">
							<input type="checkbox" name="_fdNotifyTargetType" value="1" onclick="notifyTargetTypeChange(this);"/>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyHandled"/>
						</label>
						<label class="notifyTarget_label">
							<input type="checkbox" name="_fdNotifyTargetType" value="2" onclick="notifyTargetTypeChange(this);"/>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyCurrentHandler"/>
						</label>
						<input type="hidden" id="notifyTargetType" name="fdNotifyTargetType" value />
					</div>
				</td>
			</tr>
			<!-- 通知方式 -->
			<tr id="fdNotifyTypeRow" class="fdNotifyType">
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyType"/>
				</td>
				 <td width="85%">
				 	 <span id="notifyType"></span>
				</td>
			</tr>
		</table>
		<div style="padding-top: 17px;">
			<ui:button text="${ lfn:message('button.save') }"
				onclick="if (_validateFdPostscript()) {Com_Submit(document.lbpmPostscriptForm, 'save');}" />
			<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
		</div>
	</center>
<html:hidden property="fdId" value="${lbpmPostscriptForm.fdId}"/>
<html:hidden property="fdAuditNoteId" value="${lbpmPostscriptForm.fdAuditNoteId}"/>
<html:hidden property="fdModelName" value="${lbpmPostscriptForm.fdModelName}"/>
<html:hidden property="fdModelId" value="${lbpmPostscriptForm.fdModelId}"/>
<html:hidden property="fdCreatorId" value="${lbpmPostscriptForm.fdCreatorId}"/>
<html:hidden property="fdProcessId" value="${lbpmPostscriptForm.fdModelId}"/>
</html:form>
	</template:replace>
</template:include>