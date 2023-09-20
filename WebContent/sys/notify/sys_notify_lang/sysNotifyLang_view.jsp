<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
	123
	</template:replace>
	<template:replace name="toolbar">

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
				
			<ui:button text="${lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('sysNotifyLang.do?method=edit&fdId=${param.fdId}','_self');">
			</ui:button>

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
					<td class="td_normal_title" width=15%>
						key-bundle
					</td>
					<td colspan=3>
						<c:out value="${ sysNotifySelfTitleSettingForm.fdBundle}"></c:out>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						多语言
					</td>
					<td colspan=3>
						<c:out value="${ sysNotifySelfTitleSettingForm.fdLangTitles}"></c:out>
					</td>
				</tr>
			</table>
		</div>

		</html:form>
	</template:replace>

</template:include>