<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
</script>
<kmss:windowTitle subjectKey="sys-right:right.change.title"
	 moduleKey="${param.moduleMessageKey}" />

<p class="txttitle"><bean:message bundle="sys-right" key="right.change.title"/><bean:message key="button.edit"/></p>

<html:form action="/sys/right/right.do" onsubmit="return validateChangeRightForm(this);">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.changeRightForm, 'changeRightUpdate');">

	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-right" key="right.change.repElement"/>
		</td><td>
			<input type="hidden" name="moduleModelName" value="${HtmlParam.moduleModelName }">
			<html:hidden property="repElementId" />
			<html:textarea property="repElementName" readonly="true" styleClass="inputmul" /><span class="txtstrong">*</span>
			<a href="#" onclick="Dialog_Address(false, 'repElementId', 'repElementName', ';', ORG_TYPE_ALLORG|ORG_FLAG_AVAILABLEALL|ORG_FLAG_BUSINESSALL, null, null, null, null, null, null, null, null, false);">
				<bean:message key="dialog.selectOrg"/>
			</a>			
		   </td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-right" key="right.change.element"/>
		</td><td>
			<html:hidden property="elementId" />
			<html:textarea property="elementName" readonly="true" styleClass="inputmul" />
			<a href="#" onclick="Dialog_Address(false, 'elementId', 'elementName', ';', ORG_TYPE_ALLORG, null, null, null, null, null, null, null, null, false);">
				<bean:message key="dialog.selectOrg"/>
			</a>			
		   </td>
	</tr>	

</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="changeRightForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>