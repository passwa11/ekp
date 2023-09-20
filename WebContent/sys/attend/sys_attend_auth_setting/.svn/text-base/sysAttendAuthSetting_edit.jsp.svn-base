<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendAuthSettingForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysAttendAuthSettingForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysAttendAuthSettingForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.save') }" onclick="Com_Submit(document.sysAttendAuthSettingForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysAttendAuthSettingForm, 'saveadd');refresh();"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">
		<p class="txttitle" style="margin: 15px 0;">
			${ lfn:message('sys-attend:sysAttendAuthSetting.edit.title') }
		</p>
		<html:form action="/sys/attend/sys_attend_auth_setting/sysAttendAuthSetting.do">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100%>
					<tr>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-attend:sysAttendAuthSetting.fdElements') }
						</td>
						<td colspan="3">
							<xform:address propertyId="fdElementIds" propertyName="fdElementNames" style="height: 100px;width: 99%;" mulSelect="true" orgType="ORG_TYPE_POSTORPERSON" showStatus="edit" required="true" subject="${ lfn:message('sys-attend:sysAttendAuthSetting.fdElements') }" textarea="true">
							</xform:address>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-attend:sysAttendAuthSetting.fdAuthList') }
						</td>
						<td colspan="3">
							<xform:address propertyId="fdAuthIds" propertyName="fdAuthNames" style="height: 100px;width: 99%;" mulSelect="true" orgType="ORG_TYPE_ORGORDEPT|ORG_TYPE_POSTORPERSON" showStatus="edit" required="true" subject="${ lfn:message('sys-attend:sysAttendAuthSetting.fdAuthList') }" textarea="true">
							</xform:address>
						</td>
					</tr>
				</table>
				<div style="color: red;margin-top: 10px;">
					<p>${ lfn:message('sys-attend:sysAttendAuthSetting.edit.tips') }</p>
				</div>
			</div>	
		</html:form>
		<script>
			seajs.use(['lui/topic'],function(topic){
				$(function(){
					window.refresh = function(){
						LUI.fire({ type: "topic", name: "successReloadPage" }, window.opener);
					}
					refresh();
				});
			})
		</script>
	</template:replace>
</template:include>