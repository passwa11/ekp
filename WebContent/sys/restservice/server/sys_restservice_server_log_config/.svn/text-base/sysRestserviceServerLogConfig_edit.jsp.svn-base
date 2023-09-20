<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">	
	<template:replace name="content">
	<style type="text/css">
		 .data-type label { 
		    padding-right: 50px;
	    } 
	</style>
		<html:form action="/sys/restservice/server/sys_restservice_server_log_config/sysRestserviceServerLogConfig.do">
			<h2 align="center" style="margin: 10px 0">
				<span class="profile_config_title">
					<bean:message bundle="sys-restservice-server" key="sys.restservice.config"/>
				</span>
			</h2>
			<center>
             	<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15% style="height:100px;text-align: center">
							<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLogConfig.dataType"/>
						</td>
						<td colspan="3" style="height:100px" class="data-type">
							<xform:radio property="value(dataType)" value="${sysRestserviceServerLogConfig.dataType}" >
								<xform:enumsDataSource enumsType="sys_restservice_server_log_config_data_type" />
							</xform:radio>
						</td>
					</tr>
				</table>
				<div style="margin-bottom: 10px;margin-top:25px">
	   				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="submit();" order="1" ></ui:button>
				</div>
			</center>
		<html:hidden property="method_GET" />
		<input type="hidden" name="modelName" value="${sysRestserviceServerLogConfig.modelName }" />
		</html:form>
		<script>
			$KMSSValidation();
			function submit() {
				Com_Submit(document.sysAppConfigForm, 'update');
			}
		</script>
	</template:replace>
</template:include>
