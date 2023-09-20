<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
<script type="text/javascript">
	Com_IncludeFile("jquery.js|xform.js");
	function submitUpdateForm() {
		seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
			var key = $("#fieldNameSelect").val();
			if(document.getElementsByName(key).length == 0) {
				key = key+"Id";
			}
			var $field = $("[name='"+key+"']");
			if($field.val()==null || $.trim($field.val()) == '') {
				var required = $field.attr('validate') != null && $field.attr('validate').indexOf('required')>-1;
				if(required) {
					var subject = $field.attr('subject');
					if(subject == null) {
						subject = $("[name='"+$("#filedNameSelect").val()+"Name']").attr('subject');
					}
					dialog.alert(subject+"${lfn:message('km-archives:kmArchivesMain.canNotNull')}");
					return;
				}
			}
			document.updateForm.submit();
		})
	}
</script>
<style>
.change-div-class {
	display:none;
}
</style>

<center>

<p class="txttitle"><bean:message bundle="km-archives" key="file.right.change.title.doc"/><bean:message key="button.edit"/></p>

<html:form action="/km/archives/km_archives_main/kmArchivesMain.do" method="post">
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=12%>
			<bean:message  bundle="sys-right" key="right.change.opr"/>
		</td>
		<td width=90%>
			<sunbor:enums
				property="oprType"
				enumsType="sys_right_add_or_reset"
				elementType="radio"/>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title" colspan="2">
			<bean:message  bundle="sys-right" key="right.change.updateOption"/>
		</td>
	</tr>	

	<tr id="authReaderZone">
		<td class="td_normal_title" width=10%>
			<bean:message bundle="km-archives" key="kmArchivesMain.authFileReaders" />
		</td>
		<td width=90%>
			<xform:address textarea="true" mulSelect="true" propertyId="authFileReaderIds" required="true" subject="${ lfn:message('km-archives:kmArchivesMain.authFileReaders') }"
				propertyName="authFileReaderNames" style="width:90%;height:90px;" showStatus="edit"></xform:address>
			<p><bean:message bundle="km-archives" key="kmArchivesMain.authFileReader.desc" /></p>
		</td>
	</tr>
</table>
<div style="padding-top:17px">
       <ui:button text="${ lfn:message('button.save') }"  onclick="Com_Submit(document.kmArchivesMainForm, 'docRightUpdate','fdIds');">
	   </ui:button>
       <ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();">
	   </ui:button>
</div>
</center>
<html:hidden property="fdIds" value="${HtmlParam.selectedIds}"/>
<html:hidden property="method_GET"/>
</html:form>
</center>
</template:replace>
</template:include>