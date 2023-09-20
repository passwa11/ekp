<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
<template:replace name="body"> 
<script>
	seajs.use(['theme!form']);
	Com_IncludeFile("jquery.js");
</script>
<script language="JavaScript">
$(document).ready(function(){ 
	var value = opener.values;
	document.getElementsByName("values")[0].value = value;
});


		Com_IncludeFile("dialog.js");
		function save() {
			var tName = document.getElementsByName("fdTemplateName");
			if(tName[0].value=="") {
				alert("<bean:message key="message.no.template" bundle="km-review"/>");
				return false;
			}
			Com_Submit(document.kmReviewMainForm, 'changeTemplate');
		}
</script>
<html:form action="/km/review/km_review_main/kmReviewMain.do">
	<p class="txttitle"><bean:message bundle="km-review"
		key="review.title.trans" />
    </p>
	<br>
	<center>
	<table class="tb_normal" width=90%>
	    <input type="hidden" name ="values"/>
		<tr>
			<td class="td_normal_title" width=30%>
			<bean:message key="kmReviewTemplate.fdName" bundle="km-review"/></td>
			<td>
			<html:hidden property="fdTemplateId"/>
			<html:text property="fdTemplateName" style="width:70%;" styleClass="inputsgl" readonly="true"/>
			<span class="txtstrong">*</span>&nbsp;&nbsp;&nbsp;
			<a href="#"
						onclick="Dialog_Template('com.landray.kmss.km.review.model.KmReviewTemplate', 'fdTemplateId::fdTemplateName',false,true, '02');"><bean:message
						key="dialog.selectOther" /></a>
						
			</td>
		</tr>

	</table>
	<br>
	<div>
	   <input type=button class="lui_form_button" value="<bean:message key="button.update"/>" onclick="save();">&nbsp;
	   <input type="button" class="lui_form_button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	</center>
	<html:hidden property="method_GET" />
</html:form>
</template:replace>
</template:include>
