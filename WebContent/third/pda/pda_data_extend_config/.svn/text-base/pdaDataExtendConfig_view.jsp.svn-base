<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float">
			<kmss:authShow roles="ROLE_THIRDPDA_ADMIN">
			<ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}third/pda/pda_data_extend_config/pdaDataExtendConfig.do?method=edit','_self');">
			</ui:button>
			</kmss:authShow>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">

		<html:form action="/third/pda/pda_data_extend_config/pdaDataExtendConfig.do">
			<table class="tb_normal" width="95%" style="margin-top: 50px;">
				<tr class="tr_normal_title">
					<td width="35px;"><bean:message bundle="third-pda" key="pdaExtSettingView.serial"/></td>
					<td width="140px;"><bean:message bundle="third-pda" key="pdaExtSettingView.fdName"/></td>
					<td width="140px;"><bean:message bundle="third-pda" key="pdaExtSettingView.fdUrlScheme"/></td>
					<td><bean:message bundle="third-pda" key="pdaExtSettingView.fdDataUrl"/></td>
					<td width="140pc;"><bean:message bundle="third-pda" key="pdaExtSettingView.fdType"/></td>
				</tr>
				<c:if test="${empty pdaDataExtendConfigForm.pdaDataExtendConfigList}">
				<tr>
					<td align="center" colspan="4"><bean:message bundle="third-pda" key="pdaExtSettingView.Info"/></td>
				</tr>
				</c:if>
				<c:if test="${not empty pdaDataExtendConfigForm.pdaDataExtendConfigList}">
				<c:forEach items="${pdaDataExtendConfigForm.pdaDataExtendConfigList }" var="detail" varStatus="vstatus">
				<tr>
					<td>${vstatus.index+1}</td>
					<td>${detail.fdName }</td>
					<td>${detail.fdKey }</td>
					<td>${detail.fdValue }</td>
					<td>${detail.fdType }</td>
				</tr>
				</c:forEach>
				</c:if>
			</table>
		</html:form>

	</template:replace>
</template:include>
