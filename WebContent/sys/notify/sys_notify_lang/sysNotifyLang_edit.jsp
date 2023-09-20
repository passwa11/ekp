<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
	123
	</template:replace>
	<template:replace name="toolbar">

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:choose>
				<c:when test="${ sysNotifySelfTitleSettingForm.method_GET == 'add' }">
				
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.sysNotifySelfTitleSettingForm, 'save');">
					</ui:button>

				</c:when>
				<c:when test="${ sysNotifySelfTitleSettingForm.method_GET == 'edit' }">

					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.sysNotifySelfTitleSettingForm, 'update');">
					</ui:button>
		
				</c:when>
			</c:choose>
			<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<script type="text/javascript">
				function commitMethod(commitType, saveDraft){
					var formObj = document.sysNotifySelfTitleSettingForm;
					if('save'==commitType){
						Com_Submit(formObj, commitType,'fdId');
				    }else{
				    	Com_Submit(formObj, commitType); 
				    }
				}

		</script>

		<c:set var="sysNotifySelfTitleSettingForm" value="${sysNotifySelfTitleSettingForm}" scope="request" />
		
		<html:form action="/sys/notify/sys_notify_lang/sysNotifyLang.do">
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />

		<div class="lui_form_content_frame" style="padding-top:20px">
			<table class="tb_simple" width=100%>
				<tr>
					<td width="15%" class="td_normal_title">
						key-bundle
					</td>
					<td colspan="3">
						<xform:text 
							property="fdBundle" required="true" subject="${lfn:message('sys-doc:sysDocBaseInfo.docSubject')}"  style="width:97%;"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						多语言
					</td>
					<td colspan="3">
						<xform:text 
							property="fdBundle" required="true" subject="${lfn:message('sys-doc:sysDocBaseInfo.docSubject')}"  style="width:97%;"/>
					</td>
				</tr>
			</table>
		</div>

		</html:form>
		<script language="JavaScript">
		var _validator = $KMSSValidation(document.forms['sysNotifySelfTitleSettingForm']);
		</script>
	</template:replace>

</template:include>