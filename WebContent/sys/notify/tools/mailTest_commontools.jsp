<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="sysNotify.config.emailtest" bundle="sys-notify"/></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message key="sysNotify.config.emailtest" bundle="sys-notify"/></span>
		</h2>
		
		<center>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-profile" key="sys.email.info.emailSender" />
					</td>
					<td width="85%">
						<xform:select property="fdSendEmail" showStatus="edit"  htmlElementProperties="id='fdSendEmail'" subject="${lfn:message('sys-profile:sys.email.info.send.name') }">
							<xform:customizeDataSource className="com.landray.kmss.sys.profile.service.spring.SysSenderEmailInfoDataSource" ></xform:customizeDataSource>
						</xform:select>
					</td>
				</tr>

				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="sysNotify.config.emailtest.org" bundle="sys-notify"/>
					</td>
					<td width="85%">
						<xform:address propertyId="fdOrgPersonIds" propertyName="fdOrgPersonNames" mulSelect="true" showStatus="edit" orgType="ORG_TYPE_PERSON" style="width:85%">
						</xform:address>
						<span class="txtstrong">*</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="sysNotify.config.emailtest.title" bundle="sys-notify"/>
					</td>
					<td width="85%">
						<input type="text" name="fdEmailTestTitle"  class="inputsgl" style="width:85%;" value="<bean:message key="sysNotify.config.emailtest.title.defaultContent" bundle="sys-notify"/>"/><span class="txtstrong">*</span>
					</td>
				</tr>	
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message key="sysNotify.config.emailtest.content" bundle="sys-notify"/>
					</td>
					<td width="85%">
						<textarea name="fdEmailTestContent" style="width: 85%; height: 90px" ><bean:message key="sysNotify.config.emailtest.content.defaultContent" bundle="sys-notify"/></textarea><span class="txtstrong">*</span>
					</td>
				</tr>
			</table>
		
			<br>
			<!-- 保存 -->
			<ui:button text="${lfn:message('sys-notify:sysNotify.config.emailtest.execute') }" height="35" width="120" onclick="doTest();" order="1" ></ui:button>
		</center>
		
	 	<script type="text/javascript">
	 	seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	 		window.doTest = function() {
				var fdSendEmail = $('#fdSendEmail').val();
	 			var fdOrgPersonIds = $('input[name=fdOrgPersonIds]').val();
	 			var fdEmailTestTitle = $('input[name=fdEmailTestTitle]').val();
	 			var fdEmailTestContent = $('textarea[name=fdEmailTestContent]').val();
	 			if (fdOrgPersonIds == "") {
	 				dialog.alert('<bean:message key="sysNotify.config.emailtest.orgPersonIds.err" bundle="sys-notify"/>');
	 				return;
	 			}
	 			if (fdEmailTestTitle == "") {
	 				dialog.alert('<bean:message key="sysNotify.config.emailtest.emailTestTitle.err" bundle="sys-notify"/>');
	 				return;
	 			}
	 			if(fdEmailTestTitle!=null && fdEmailTestTitle.length > 300){
	 				dialog.alert('<bean:message key="sysNotify.config.emailtest.emailTestTitle.length" bundle="sys-notify"/>');
	 				return;
	 			}
	 			if (fdEmailTestContent == "") {
	 				dialog.alert('<bean:message key="sysNotify.config.emailtest.emailTestContent.err" bundle="sys-notify"/>');
	 				return;
	 			}
	 			 
	 			if(fdEmailTestContent!=null && fdEmailTestContent.length > 8000){
	 				dialog.alert('<bean:message key="sysNotify.config.emailtest.emailTestContent.length" bundle="sys-notify"/>');
	 				return;
	 			}
	 			var url = '<c:url value="/sys/notify/sys_notify_main/sysNotifyMailTest.do?method=sendMailTest"/>';
	 			var params = {
					fdSendEmail: fdSendEmail,
	 				fdOrgPersonIds : fdOrgPersonIds,
	 				fdEmailTestTitle : fdEmailTestTitle,
	 				fdEmailTestContent : fdEmailTestContent
	 			};
	 			window.del_load = dialog.loading();
	 			$.post(url, params, function(data) {
	 				if(window.del_load != null) {
						window.del_load.hide(); 
					}
	 				if (data != null && data.length > 0) {
	 					dialog.alert('<bean:message key="sysNotify.config.emailtest.faile" bundle="sys-notify"/>' + data);
	 				} else {
	 					dialog.result({title:'<bean:message key="sysNotify.config.emailtest.success" bundle="sys-notify"/>', status:true});
	 				}
	 			});
	 		}
	 	});
	 	</script>
	</template:replace>
</template:include>
