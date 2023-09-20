<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.view">
	<template:replace name="content">
		<script>
		Com_IncludeFile('form.js');
		Com_IncludeFile("validation.js|plugin.js|validation.jsp ");
		</script>
		<script>
		var _validation = $KMSSValidation();
		</script>
		<p class="txttitle">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.quickFactorSetting')}</p>
		<br/>
		<form name="configForm" action="${LUI_ContextPath }/sys/profile/passwordSecurityConfig.do" method="post">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width="15%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyObjects')}</td>
					<td width="85%">
						<div id="policyAddress" _xform_type="address">
							<xform:address subject="${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyObjects')}" 
							textarea="true" showStatus="edit" propertyName="policyObjectNames" 
							propertyId="policyObjectIds" mulSelect="true" required="true"></xform:address>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="35%">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType')}</td>
					<td>
						<label>
							<xform:radio property="policyType" value="${passwordSecurityConfig.policyType}" showStatus="edit">
							 	<xform:simpleDataSource value="enable">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType.allEnable')}</xform:simpleDataSource>
							 	<xform:simpleDataSource value="disable">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType.allDisable')}</xform:simpleDataSource>
							 	<xform:simpleDataSource value="network">${lfn:message('sys-profile:sys.profile.org.passwordSecurityConfig.policyType.network')}</xform:simpleDataSource>
							 </xform:radio>
						</label>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<ol style="list-style-type:disc;">
							<li>${lfn:message('sys-profile:passwordSecurityConfig.policy.enable.tip')}</li>
							<li>${lfn:message('sys-profile:passwordSecurityConfig.policy.disable.tip')}</li>
							<li>${lfn:message('sys-profile:passwordSecurityConfig.policy.network.tip')}</li>
						</ol>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<ui:button text="${lfn:message('button.ok') }" onclick="Com_Submit(document.configForm,'updatePersonDoubleVali');"></ui:button>
						<ui:button text="${lfn:message('button.cancel') }" onclick="Com_CloseWindow();"></ui:button>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
			seajs.use(['lui/jquery'],function($) {
				$(document).ready(function() {
					$("[name='policyType'][value='enable']").prop('checked',true);
				});
			});
		</script>	
	</template:replace>
</template:include>