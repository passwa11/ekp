<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="title">
		
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="90%">
			<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		
	</template:replace>	
	<template:replace name="content">
			<p class="lui_form_subject">
				<bean:message bundle="sys-auditlog" key="table.auditlog" />
			</p>
			<div class="lui_form_content_frame">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%><bean:message bundle="sys-auditlog" key="sysAuditlog.fdSubject" /></td>
						<td colspan=3>
							<c:if test="${ _fdModelUrl!=null}">
								<a href="${LUI_ContextPath}${_fdModelUrl}" target="_self" class="com_btn_link_light">
							</c:if>
							<xform:text property="fdSubject"></xform:text>
							<c:if test="${ _fdModelUrl!=null}">
								</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-auditlog" key="sysAuditlog.fdOperator" />
						</td>
						<td width="35%">
							<xform:text property="fdOperator"></xform:text>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-auditlog" key="sysAuditlog.fdModelName" />
						</td>
						<td width="35%">
							<c:out value="${_fdModuleName}"/><br/>
							(<c:out value="${sysAuditlogForm.fdModelName}"/>)
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-auditlog" key="sysAuditlog.fdOptType" />
						</td>
						<td width="35%">
							<sunbor:enumsShow value="${sysAuditlogForm.fdOptType}"	
		   						bundle="sys-auditlog" enumsType="sysAuditlog_fdOptType"/>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-auditlog" key="sysAuditlog.fdIp" />
						</td>
						<td width="35%">
							<xform:text property="fdIp"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-auditlog" key="sysAuditlog.fdCreateTime" />
						</td>
						<td colspan=3>
							<c:out value="${sysAuditlogForm.fdCreateTime}"/>
						</td>
					</tr>
				</table>
			</div>
	</template:replace>
</template:include>