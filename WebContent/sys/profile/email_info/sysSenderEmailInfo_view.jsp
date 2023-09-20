<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="no">

	<template:replace name="title">
		<c:out value="${sysSenderEmailInfoForm.docSubject}"></c:out>
	</template:replace>
	
	<template:replace name="head">
		<script>
			seajs.use('sys/profile/email_info/js/view.js');
		</script>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}"  order="2" onclick="toEditView();">
				</ui:button>
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="3" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-profile:sys.email.info.setting') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<template:replace name="content">
    	<div style="overflow: hidden">
			<div  style="float: left;width:80%">		
				<table class="tb_simple" style="width:100%">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.docSubject') }</td>
						<td width="60%" colspan="3">
							<xform:text property="docSubject" style="width:84.4%" />
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailHost') }</td>
						<td width="60%" colspan="3">
							<xform:text property="fdEmailHost" style="width:84.4%"/>
						</td>
					</tr>
					
					<c:if test="${sysSenderEmailInfoForm.fdEmailAgreement != 'none'}">
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailAgreement') }</td>
							<td width="60%" colspan="3">
								<xform:text property="fdEmailAgreement" style="width:84.4%"/>
							</td>
						</tr>
					</c:if>
					
					
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailUsername') }</td>
						<td width="60%" colspan="3">
							<xform:text property="fdEmailUsername" style="width:84.4%"/>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailEncoding') }</td>
						<td width="60%" colspan="3">
							<xform:text property="fdEmailEncoding" style="width:84.4%"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<ui:tabpage expand="true">
			<!--权限机制-->
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysSenderEmailInfoForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.sys.profile.model.SysSenderEmailInfo" />
			</c:import>
		</ui:tabpage>
		
		<html:hidden property="fdId" />
	</template:replace>
</template:include>
