<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" showQrcode="false" sidebar="auto">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="2" var-navwidth="90%">
			<ui:button order="5" onclick="top.close();" text="${ lfn:message('button.close') }"></ui:button>
			 <c:if test="${true == viewDoc}">
				<ui:button text="${lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.originDoc.view')}" order="4"
					onclick="javascript:Com_OpenWindow('${LUI_ContextPath }${modelViewPageUrl}','_blank');">
				</ui:button>
			 </c:if>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.info" /> 
 		</p>
		<center>
			<table class="tb_normal" width=98%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdSubject" />
					</td> 
					<td width=85% colspan=3>
						<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath }${modelViewPageUrl}" style="color: #47b5ea;" target="_blank" >
							<c:if test="${not empty requestScope.fdSubject}">
								<c:out value="${requestScope.fdSubject}" />
							</c:if>
						</a>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdModuleName" />
					</td>
					<td width=35%>
						<bean:write name="lbpmPrivilegeLogForm" property="fdModuleName"/>
					</td>
					<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdUrl" />
						</td>
					<td width=35%>
						<a href="javascript:void(0);" style="color: #47b5ea;" src="${ lbpmPrivilegeLogForm.fdUrl }" onclick="openTemplate(this);">
							<bean:write name="lbpmPrivilegeLogForm" property="fdName"/>
						</a>
					</td>
		
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdActionInfo" />
					</td> 
					<td width=85% colspan=3>
						<c:if test="${not empty lbpmPrivilegeLogForm.fdActionInfo}">
								<c:out value="${lbpmPrivilegeLogForm.fdActionInfo}" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.docCreator" />
					<td width=35%>
						<bean:write name="lbpmPrivilegeLogForm" property="fdHandlerName"/>
					</td>
					</td> 
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdCreateTime" />
					</td>
					<td width=35%>
						<bean:write name="lbpmPrivilegeLogForm" property="fdCreateTime"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdCurrDept" />
					</td>
					<td width=35%>
						<bean:write name="lbpmPrivilegeLogForm" property="fdCurrDept"/>
					</td> 
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmPrivilegeLog.fdIpAddr" />
					</td>
					<td width=35%>
						<bean:write name="lbpmPrivilegeLogForm" property="fdIpAddr"/>
					</td>
				</tr>
			</table>
		</center>
		<script>
			function openTemplate(src) {
				var src = $(src).attr("src");
				console.log(src);
				if (src) {
					if (src.startsWith("/")) {
						src = src.replace("/","");
					}
					Com_OpenWindow(Com_Parameter.ContextPath + src);
				}
			}
			
		</script>
		
	</template:replace>
</template:include>