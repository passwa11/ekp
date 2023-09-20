<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">
	<template:replace name="body">
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.usageNote') }" style="" >
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
var flag=0;
<%
if("true".equals((String)request.getAttribute("successFlag"))){
	out.print("flag=1");
}else{
	out.print("flag=0");
}
%>
if(flag==1){
    seajs.use('lui/dialog', function(dialog) {
        dialog.success("${ lfn:message('sys-lbpmservice-support:lbpmSetting.setpersonUsage.saveSuccess')}");
    });
}

Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery'],function($){
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			setTimeout(function(){
				window.frameElement.style.height =  $(document.body).height() +10+ "px";
			},100);
		}
	});
});
</script>

<html:form action="/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do">
<p class="txttitle"></p>
<center>
<table class="tb_normal" width=100%>
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
		<%-- <i><bean:message bundle="sys-lbpmservice-support" key="lbpmUsage.fdIsAppend.description"/></i> --%>
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
<center style="margin-top: 10px;">
			<ui:button text="${ lfn:message('button.save') }" 
		onclick="Com_Submit(document.lbpmUsageForm, 'updateDefinePerson');"  height="35" width="120" />
</center>
<html:hidden property="fdId" />
<html:hidden property="fdIsSysSetup" value="false"/>
<html:hidden property="fdCreatorId"/>
<html:hidden property="fdCreateTime"/>
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
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
</ui:content>
</ui:tabpanel>
</template:replace>
</template:include>