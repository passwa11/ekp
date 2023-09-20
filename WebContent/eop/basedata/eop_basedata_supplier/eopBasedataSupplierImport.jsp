<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog" sidebar="auto">
	<template:replace name="content">
		<html:form action="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do" method="post" enctype="multipart/form-data">
		<script>
		</script>
		<p class="txttitle">${lfn:message('eop-basedata:message.import.title') }</p>
		<table class="tb_normal" width="95%">
			<tr>
				<td width="25%" class="td_normal_title">${lfn:message('eop-basedata:message.table.label.selectFile') }</td>
				<td width="75%">
					<input type="file" name="fdFile">
					<html:hidden property="modelName" value="${param.modelName }"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<ui:button text="${lfn:message('button.submit') }" onclick="submit()"/>
				</td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/dialog'],function(dialog){
				window.submit = function(){
					var fdFile = $("[name=fdFile]").val();
					if(!fdFile){
						dialog.alert("${lfn:message('eop-basedata:message.import.file.tip') }");
						return;
					}
					Com_Submit(document.forms[0],'saveImport');
				}
			});
		</script>
		</html:form>
	</template:replace>
</template:include>
