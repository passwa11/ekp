<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
<script>
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
</script>
<html:form action="/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do">
<ui:toolbar layout="sys.ui.toolbar.float">
	<ui:button text="${ lfn:message('button.save') }" styleClass="lui_toolbar_btn_gray"
		onclick="Com_Submit(document.lbpmUsageForm, 'updateDefine');" />
	<ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
</ui:toolbar>
<p class="txttitle"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmUsage"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdUsageContent"/>
		</td><td width="85%">
			<xform:textarea property="fdUsageContent" style="width:95%;height:200px" showStatus="edit" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdIsAppend"/>
		</td><td width="85%">
		<c:choose>
			<c:when test="${empty lbpmUsageForm.fdIsAppend || lbpmUsageForm.fdIsAppend}">
				<ui:switch property="fdIsAppend" checked="true"
					enabledText="${lfn:message('sys-lbpmservice-support:lbpmUsage.fdIsAppend.1')}"
					disabledText="${lfn:message('sys-lbpmservice-support:lbpmUsage.fdIsAppend.0')}"
					onValueChange="changeNoteText();"></ui:switch>
					<i id="switchNoteText"><bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdIsAppend.description.yes"/></i>
			</c:when>
			<c:otherwise>
				<ui:switch property="fdIsAppend"
					enabledText="${lfn:message('sys-lbpmservice-support:lbpmUsage.fdIsAppend.1')}"
					disabledText="${lfn:message('sys-lbpmservice-support:lbpmUsage.fdIsAppend.0')}"
					onValueChange="changeNoteText();"></ui:switch>
					<i id="switchNoteText"><bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdIsAppend.description.no"/></i>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription"/>
		</td><td width="85%">
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription.details.1"/><br>
			<bean:message  bundle="sys-lbpmservice-support" key="lbpmUsage.fdDescription.details.2"/>
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="fdIsSysSetup" value="false"/>
<html:hidden property="fdCreatorId"/>
<html:hidden property="fdCreateTime"/>
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
	Com_Parameter.event["submit"].push(function (){
		var fdUsageContent = document.getElementsByName("fdUsageContent")[0].value.replace(/^\s+|\s+$/g, "");
		document.getElementsByName("fdUsageContent")[0].value = fdUsageContent;
		return true;
	});
	//#72732 描述语优化
	function changeNoteText(){
		var status = $("input[name='fdIsAppend']").val();
		//获取描述的文字
		var yesText = "${lfn:message('sys-lbpmservice-support:lbpmUsage.fdIsAppend.description.yes')}";
		var noText = "${lfn:message('sys-lbpmservice-support:lbpmUsage.fdIsAppend.description.no')}";

		if(status == 'true'){
			$("#switchNoteText").text(yesText);	
		}else{
			$("#switchNoteText").text(noText);
		}
	}
</script>
</html:form>
	</template:replace>
</template:include>