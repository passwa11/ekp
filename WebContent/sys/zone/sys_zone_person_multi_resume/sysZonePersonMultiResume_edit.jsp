<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/zone/sys_zone_person_multi_resume/style/edit.css">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${lfn:message('button.submit')}" order="2" onclick="submitUpload()"/>
			<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" >
			<ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp" icon="lui_icon_s_home" />
			<ui:menu-item text="${lfn:message('sys-zone:module.sys.zone') }" />
			<ui:menu-item text="批量上传简历" />
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div style="margin-top:10px;">
			<ui:panel layout="sys.ui.panel.light" scroll="false" toggle="false">
				<ui:content title="批量上传简历">
					<div class="lui_zone_batch_resumetip">
						一次性上传附件（支持word、ppt、pdf）请不要超过15个，请正确填写用户登录名（文件名可用登录名命名，上传后自动填写），若该用户之前已经上传过简历，新上传的简历将会覆盖掉之前的简历
					</div>
					<html:form  action="/sys/zone/sys_zone_person_multi_resume/sysZonePersonMultiResume.do" method="post">
					     	<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							     <c:param name="fdKey" value="multiResume"/>
								 <c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
								 <c:param name="enabledFileType" value="*.doc;*.docx;*.ppt;*.pptx;*.pdf" />
							</c:import> 
					</html:form>
				</ui:content>
			</ui:panel>
		</div>
		<script>
			if(window.attachmentObject_multiResume) {
				window.attachmentObject_multiResume.renderurl = 
					Com_Parameter.ContextPath + 
					"sys/zone/sys_zone_person_multi_resume/sys_zone_resume_render.js";
			}
			window.submitUpload = function() {
				Com_Submit(document.forms['sysZonePersonMultiResumeForm'], "save");
			}
			seajs.use('sys/zone/sys_zone_person_multi_resume/multiResume_edit');
		</script>
	</template:replace>
</template:include>