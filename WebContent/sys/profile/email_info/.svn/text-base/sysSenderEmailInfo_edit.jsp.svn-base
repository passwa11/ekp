<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysSenderEmailInfoForm.method_GET == 'add' }">
				<c:out
					value="${ lfn:message('operation.create') } - ${ lfn:message('sys-profile:sys.email.info.setting') }"></c:out>
			</c:when>
			<c:otherwise>
				<c:out
					value="${sysSenderEmailInfoForm.docSubject}"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	
	<template:replace name="head">
		<script>
			seajs.use('sys/profile/email_info/js/edit.js');
		</script>
	</template:replace>
	
	<template:replace name="toolbar">
	
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:choose>
				<c:when test="${ sysSenderEmailInfoForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethods('save','false');"></ui:button>
				</c:when>
				<c:when test="${ sysSenderEmailInfoForm.method_GET == 'edit' }">
					<ui:button text="${lfn:message('button.submit') }" order="4" onclick="Com_Submit(document.sysSenderEmailInfoForm, 'update');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();" order="5"></ui:button>
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
		
		<html:form action="/sys/sender/sender_email_info/sysSenderEmailInfo.do">
			<div style="overflow: hidden">
				<div  style="float: left;width:80%">		
					<table class="tb_simple" style="width:100%">
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.docSubject') }</td>
							<td width="60%" colspan="3">
								<xform:text property="docSubject" style="width:84.4%" required="true" validators="checkNameUnique" />
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailHost') }</td>
							<td width="60%" colspan="3">
								<xform:text property="fdEmailHost" style="width:84.4%" required="true" />
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailAgreement') }</td>
							<td width="60%" colspan="3">
								<xform:radio property="fdEmailAgreement" className="class_type">
									<xform:simpleDataSource value=""><bean:message bundle="sys-notify" key="sysNotityTodo.mail.config.host.agreement.no"/></xform:simpleDataSource>
									<xform:simpleDataSource value="SSL">SSL</xform:simpleDataSource>
									<xform:simpleDataSource value="TLS">TLS</xform:simpleDataSource>
								</xform:radio>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailUsername') }</td>
							<td width="60%" colspan="3">
								<xform:text property="fdEmailUsername" style="width:84.4%" required="true"/>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailPassword') }</td>
							<td width="60%" colspan="3">
								<input name="fdEmailPassword" style="width:84.4%" validate="pwRequired"  type="password" value="${sysSenderEmailInfoForm.fdEmailPassword}" subject="${lfn:message('sys-profile:sys.email.info.fdEmailPassword')}" /><span class="txtstrong">*</span>
							</td>
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>${ lfn:message('sys-profile:sys.email.info.fdEmailEncoding') }</td>
							<td width="60%" colspan="3">
								<xform:text property="fdEmailEncoding" style="width:84.4%" required="true"/>例：GB2312（若与邮件服务器解码格式不同,邮件信息将产生乱码）
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			
			<ui:tabpage expand="true">
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysSenderEmailInfoForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.sys.profile.model.SysSenderEmailInfo" />
				</c:import>
			</ui:tabpage>
			
			<html:hidden property="fdId" />
			<html:hidden property="docStatus" />
		</html:form>
	</template:replace>
</template:include>