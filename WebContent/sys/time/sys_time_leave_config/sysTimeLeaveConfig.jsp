<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content" >
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<div style="margin-top:25px">
				<p class="configtitle"><bean:message bundle="sys-organization" key="sysOrgConfig"/></p>
				<center>
					<table class="tb_normal" width=95%>
						<tr>
							<td class="td_normal_title" width=20%>
								${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime') }
							</td>
							<td colspan="3">
								<xform:text property="value(dayConvertTime)" validators="required min(1) max(24)" subject="${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime') }">
								</xform:text>
								${ lfn:message('sys-time:sysTimeLeaveRule.fdDayConvertTime.text') }
							</td>
						</tr>
					</table>
					<div style="margin-bottom: 10px;margin-top:25px">
						   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="doSubmit();" order="1" ></ui:button>
					</div>
				</center>
			</div>
		</html:form>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			seajs.use(['lui/jquery'],function($){
				window.doSubmit = function(){
					if(validation.validateElement($('[name="value(dayConvertTime)"]')[0])){
						Com_Submit(document.sysAppConfigForm, 'update');
					}
				};
			});
		</script>
	</template:replace>
</template:include>