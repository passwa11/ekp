<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="isOff" value="true"></c:set>
<c:set var="switchBtShow" value="edit"></c:set>
	<c:forEach items="${sysAppConfigForm.map}" var="obj">
		<c:if test="${obj.value eq 'true' && obj.key eq 'kmsCategoryEnabled'}">
			<c:set var="isOff" value="false"></c:set>
			<c:set var="switchBtShow" value="show"></c:set>
		</c:if>
	</c:forEach>
<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="title">
		${lfn:message('kms-category:module.kms.category.config')}
	</template:replace>

	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">

			<span style="color: #35a1d0;">
				${lfn:message('kms-category:module.kms.category.config')} </span>

		</h2>

		<html:form action="/kms/category/kmsCategoryMainConfig.do">

			<center>

				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('kms-category:kmsCategoryMain.config.refs')}</td>
						<td>
							<ui:switch property="value(kmsCategoryEnabled)"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" 
								>
								
							</ui:switch>

							<span class="message"><font color="red">${lfn:message('kms-category:kmsCategoryMain.config.refs.tip')}</font></span></td>
					</tr>

				</table>

			</center>
						
			<html:hidden property="method_GET" />

			<input type="hidden" name="modelName"
				value="com.landray.kmss.kms.category.model.KmsCategoryConfig" />
			<center style="margin-top: 10px;">
			
				<kmss:authShow roles="ROLE_KMSCATEGORY_BASIC_CONFIGURATION">
						<ui:button text="${lfn:message('button.save')}" height="35"
								width="120"
								onclick="configChange();"></ui:button>
				</kmss:authShow>
			</center>
		</html:form>

		<script type="text/javascript">
		function configChange() {
			var isOn = "true" == $(
					"input[name='value\\\(kmsCategoryEnabled\\\)']")
					.val();

			// 开启
			if (isOn) {
				seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
					dialog.confirm('${ lfn:message("kms-category:kmsCategoryMain.config.refs.on.tip") }', function(ok) {
						if(!ok)
							return;
						else{
							Com_Submit(document.sysAppConfigForm, 'update');
						}
					});
				});
			}else
				Com_Submit(document.sysAppConfigForm, 'update');

		}
		
		</script>
	</template:replace>
</template:include>
