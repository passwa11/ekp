<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.profile.service.ISysOrgExportService"%>
<% 

	ISysOrgExportService sysOrgExportService = (ISysOrgExportService) SpringBeanUtil.getBean("sysOrgExportService");
	Boolean isValidate = sysOrgExportService.isValidate();

%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/org/io/import.css"/>
		<script>
		Com_IncludeFile("security.js");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">
				<bean:message bundle="sys-profile" key="sys.profile.orgExport.title" />
			</span>
		</h2>
		
		<html:form action="/sys/profile/org/orgExport.do">
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=20%>
						<bean:message bundle="sys-profile" key="sys.profile.orgExport.type" />
					</td>
					<td width="80%">
						<xform:address propertyId="exportIds" propertyName="exportNames" mulSelect="true" 
							showStatus="edit" orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" style="width:95%" required="true"></xform:address>
					</td>
				</tr>
				<% if(isValidate) {%>
				<tr>
					<td class="td_normal_title" width=20%>
						<bean:message bundle="sys-profile" key="sys.profile.orgExport.password" />
					</td>
					<td width="80%">
						<xform:text property="password" required="true" htmlElementProperties="type='password'" showStatus="edit" style="width:95%"></xform:text>
					</td>
				</tr>
				<html:hidden property="validate" value="true" />
				<% } else { %>
				<html:hidden property="validate" value="false" />
				<% } %>
			</table>
		</html:form>
		
		<center>
			<div class="profile_orgIO_btn_wrap">
				<a href="javascript:void(0)" class="profile_orgIO_btn btn_def" onclick="_export();"><bean:message bundle="sys-profile" key="sys.profile.orgExport.export" /></a>
				<a href="javascript:void(0)" class="profile_orgIO_btn btn_gray" onclick="_cancel();"><bean:message key="button.cancel" /></a>
			</div>
		</center>		
		
		<script type="text/javascript">
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				window._cancel = function() {
					window.$dialog.hide();
				};

				window._export = function() {
					var check = $KMSSValidation(document.forms['orgExportForm']).validate();
					if(check) {
						var _validate = $("input[name=validate]").val();
						var _password = $("input[name=password]").val();
						if("false"==_validate){
							window.$dialog.hide($("form").serializeArray());
						}else{
							// 校验当前密码是否正确
							$.post('<c:url value="/sys/profile/org/orgExport.do?method=checkPassword" />', 
								{ password: desEncrypt(_password), exportIds: $("input[name=exportIds]").val()},
								function(data){
									if(data == "true") {
										$("input[name=password]").val(desEncrypt(_password));
										window.$dialog.hide($("form").serializeArray());
									} else if(data == "false") {
										dialog.alert('<bean:message bundle="sys-profile" key="sys.profile.orgExport.password.err" />');
									} else {
										$("input[name=password]").val(desEncrypt(_password));
										dialog.alert('<bean:message bundle="sys-profile" key="sys.profile.orgExport.no.authority" />' + data);
									}
						   });
						}
					}
				};
			});
		</script>
	</template:replace>
</template:include>
