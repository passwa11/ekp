<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" showQrcode="false" sidebar="auto">
	<template:replace name="title">
		<c:out value="${ requestScope.docSubject } - ${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.interfacelog.logInfo') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="2">
			<ui:button text="${lfn:message('button.close')}" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
			<c:if test="${true == viewDoc}">
				<ui:button text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.originDoc.view')}" order="4"
					onclick="javascript:Com_OpenWindow('${LUI_ContextPath }${modelViewPageUrl}','_blank');">
				</ui:button>
			</c:if>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams id="simplecategoryId"
				moduleTitle="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.interfacelog.logInfo') }"
				modelName="com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess"
				href="#" />
		</ui:combin>
	</template:replace>
	<template:replace name="content">

		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath }${modelViewPageUrl}" target="_blank" class='lui_form_subject'>
					<c:if test="${not empty requestScope.docSubject}">
						<c:out value="${requestScope.docSubject}" />
					</c:if>
				</a>
			</div>
		</div>

		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.fdId" /></td>
					<td width=35%><c:out value="${requestScope.fdModelId}" /></td>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.status" /></td>
					<td width=35%>
						<c:if test="${requestScope.fdStatus=='20'}">
							<bean:message bundle="sys-lbpmmonitor" key="status.activated" />
						</c:if>
						<c:if test="${requestScope.fdStatus=='21'}">
							<bean:message bundle="sys-lbpmmonitor" key="status.error" />
						</c:if>
						<c:if test="${requestScope.fdStatus=='30'}">
							<bean:message bundle="sys-lbpmmonitor" key="status.completed" />
						</c:if>
						<c:if test="${requestScope.fdStatus=='00'}">
							<bean:message bundle="sys-lbpmmonitor" key="status.discard" />
						</c:if>
						<c:if test="${requestScope.fdStatus=='40'}">
							<bean:message bundle="sys-lbpmmonitor" key="status.suspend" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.docAuthor" /></td>
					<td width=35%><ui:person personId="${creator.fdId}"
						personName="${creator.fdName}"></ui:person></td>
					<td class="td_normal_title" width=15%><bean:message
								bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.docAuthorTime" /></td>
					<td width=35%>
						<c:out value="${fdCreateTime}"></c:out></td>
				</tr>
				<c:if test="${not empty templatePath}">
					<tr>
						<td class="td_normal_title" width=15%><bean:message
								bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.templatePath" /></td>
						<td colspan="3">
							<c:choose>
								<c:when test="${empty tempViewPageUrl}">
									<c:out value="${templatePath}"></c:out>
								</c:when>
								<c:otherwise>
									<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath }${tempViewPageUrl}" target="_blank">
										<c:out value="${templatePath}"></c:out>
									</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		
		
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.transferName" /></td>
					<td width=35%><c:out value="${interfacelogForm.transferDesc}" /></td>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.address" /></td>
					<td width=35%>
						<c:out value="${interfacelogForm.interfaceAddress}" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.beginTime" /></td>
					<td width=35%><c:out value="${interfacelogForm.excutBeginTime}" /></td>
					<td class="td_normal_title" width=15%><bean:message
								bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.endTime" /></td>
					<td width=35%><c:out value="${interfacelogForm.excutEntTime}" /></td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.excutResult" /></td>
					<td width=35%> <c:out value="${interfacelogForm.transferDesc}" /> </td>
					<td class="td_normal_title" width=15%><bean:message
								bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.timeConsuming" /></td>
					<td width=35%><c:out value="${interfacelogForm.timeConsuming}" /> MS</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.formOutGoingparam" /></td>
					<td  colspan="3" ><c:out value="${interfacelogForm.formOutGoingparam}" /></td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.funcOutGoingparam" /></td>
					<td  colspan="3" ><c:out value="${interfacelogForm.funcOutGoingparam}" /></td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.formReturnParam" /></td>
					<td  colspan="3" ><c:out value="${interfacelogForm.formReturnParam}" /></td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-lbpmmonitor" key="sysLbpmMonitor.interfacelog.funcReturnParam" /></td>
					<td  colspan="3" ><c:out value="${interfacelogForm.funcReturnParam}" /></td>
				</tr>
				
				
			</table>
		</div>
		
		<ui:tabpage expand="false" >
			<%--流程机制 --%>
			<c:import url="/sys/lbpmmonitor/import/sysLbpmMonitorProcess_view.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="interfacelogForm" />
				<c:param name="fdKey" value="${interfacelogForm.fdKey}" />
				<c:param name="roleType" value="authority" />
				<c:param name="isSimpleWorkflow" value="true" />
			</c:import>
		</ui:tabpage>
		
	</template:replace>

</template:include>

<c:if test="${not empty extendFilePath}">
	<%@ include file="/sys/lbpmmonitor/include/sysLbpmMonitorForm_script.jsp" %>
</c:if>
