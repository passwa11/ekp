<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
		<%@ include file="/resource/jsp/watermarkPcDialog.jsp" %>
<script language="JavaScript">
seajs.use(['theme!form']);
	Com_IncludeFile("dialog.js");

	function fn_submit(){
		document.kmSmissiveMainForm.submit();
		return true;
	}
</script>
<html:form action="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=modifyIssuer" >
<center>
<html:hidden property="fdId"/>
<table class="tb_normal" width=95% style="margin-top:20px">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-smissive" key="kmSmissiveMain.fdIssuerId"/>
		</td><td width=35%>
			<xform:address propertyId="fdIssuerId" propertyName="fdIssuerName" orgType="ORG_TYPE_PERSON" mulSelect="false" className="inputsgl" style="width:60%"></xform:address>
		</td>
	</tr>


</table>
<div style="padding-top:17px">
   <ui:button text="${ lfn:message('button.submit') }"  onclick="fn_submit();">
   </ui:button>
   <ui:button text="${ lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();">
   </ui:button>
</div>
</center>
<html:hidden property="method_GET"/>
</html:form>
	</template:replace>
</template:include>
