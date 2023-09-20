<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<script type="text/javascript">
Com_IncludeFile("jquery.treeTable.css","${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/","css",true);
Com_IncludeFile("jquery.js");
Com_IncludeFile("jquery.treeTable.js","${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/","js",true);
Com_AddEventListener(window,'load',function() {
	$("#auditNoteTable").treeTable({
		initialState:"expanded",
		treeColumn:1,
		stringExpand:"",
		stringCollapse:""
	});
});
</script>

<%
	LbpmSetting lbpmSetting = new LbpmSetting();
	pageContext.setAttribute("printLbpmPostscript",lbpmSetting.getPrintLbpmPostscript());
%>
<c:set var="mainForm" value="${requestScope[param.formName]}" scope="request" />
	<table class="tb_normal" width="100%" id="auditNoteTable" >
		<tr class="tr_normal">
			<td width="12%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.date" />
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdFactNodeName" />
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdHandlerId" />
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdActionKey" />
			</td>
			<td width="43%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdAuditNote" />
			</td>
		</tr>
		<c:forEach items="${auditNotes}" var="lbpmAuditNote" varStatus="vStatus">
			<tr
				<c:if test="${lbpmAuditNote.fdActionKey == '_concurrent_branch'}">
					id="${lbpmAuditNote.fdExecutionId}" class="
					<c:if test="${empty rootExecutionId}">
						<c:set var="rootExecutionId" value="${lbpmAuditNote.fdParentExecutionId}" />
					</c:if>
					<c:if test="${lbpmAuditNote.fdParentExecutionId != rootExecutionId}">
						child-of-${lbpmAuditNote.fdParentExecutionId}
					</c:if>
					<c:if test="${not empty rootSubExecutionId &&  lbpmAuditNote.fdParentExecutionId == rootSubExecutionId}">
						child-of-${rootSubId}
					</c:if>
					<c:if test="${true eq lbpmAuditNote.distributeNote || true eq lbpmAuditNote.recoverNote}">
						child-of-${lbpmAuditNote.fdParentExecutionId}
						<c:set var="rootExecutionId" value="" />
					</c:if>
					"
				</c:if>
				<c:if test="${lbpmAuditNote.fdActionKey == 'distributeNote' || lbpmAuditNote.fdActionKey == 'recoverNote'}">
					id="${lbpmAuditNote.fdExecutionId}"
				</c:if>
				<c:if test="${lbpmAuditNote.fdActionKey != '_concurrent_branch' && lbpmAuditNote.fdActionKey != 'distributeNote' && lbpmAuditNote.fdActionKey != 'recoverNote'}">
					<c:choose>
						<c:when test="${lbpmAuditNote.fdActionKey == '_vote_start'}">
							id="${lbpmAuditNote.fdId}"
							<c:set var="rootVoteId" value="${lbpmAuditNote.fdId}" />
							<c:set var="rootVoteExecutionId" value="${lbpmAuditNote.fdExecutionId}" />
							<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
								class="child-of-${lbpmAuditNote.fdExecutionId}"
							</c:if>
						</c:when>
						<c:when test="${lbpmAuditNote.fdActionKey == '_vote_end' || lbpmAuditNote.fdActionKey == '_pocess_end'}">
							id="${lbpmAuditNote.fdId}"
							<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
								class="child-of-${lbpmAuditNote.fdExecutionId}"
							</c:if>
						</c:when>
						<c:when test="${lbpmAuditNote.fdActionKey == 'subFlowNode_start'}">
							id="${lbpmAuditNote.fdId}"
							<c:set var="rootSubId" value="${lbpmAuditNote.fdId}" />
							<c:set var="rootSubExecutionId" value="${lbpmAuditNote.fdExecutionId}" />
							<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
								class="child-of-${lbpmAuditNote.fdExecutionId}"
							</c:if>
						</c:when>
						<c:otherwise>
							id="${lbpmAuditNote.fdId}" class="
							<c:if test="${lbpmAuditNote.fdExecutionId == rootVoteExecutionId}">
								child-of-${rootVoteId}
							</c:if>
							<c:if test="${not empty rootSubExecutionId &&  lbpmAuditNote.fdParentExecutionId == rootSubExecutionId}">
								child-of-${rootSubId}
							</c:if>
							<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
								child-of-${lbpmAuditNote.fdExecutionId}
							</c:if>
							"
						</c:otherwise>
					</c:choose>
				</c:if>
				>
				<td style="white-space: nowrap;word-break: keep-all;">
					<kmss:showDate type="datetime" value="${lbpmAuditNote.fdCreateTime}"/>
				</td>
				<td style="padding-left:14px;">
					<c:out value="${lbpmAuditNote.fdFactNodeName}" />
				</td>
				<td>
					<span title='<c:out value="${lbpmAuditNote.detailHandlerName}" />'>
						<c:out value="${lbpmAuditNote.handlerName}" />
					</span>
				</td>
				<td style="word-wrap: break-word;word-break: break-all;">
					<c:if test="${lbpmAuditNote.fdActionKey != 'subFlowNode_start'}">
						<c:out value="${lbpmAuditNote.fdActionInfo}" />
					</c:if>
				</td>
				<td>
					<c:if test="${lbpmAuditNote.fdActionKey != 'subFlowNode_start'}">
						<c:if test="${lbpmAuditNote.fdIsHide=='2'}">
							<table class="tb_noborder" width="100%">
								<tr>
									<td style="word-wrap: break-word;word-break: break-all;" style="border: none;">
									<kmss:showText value="${lbpmAuditNote.fdAuditNote}" />
									</td>
								</tr>
								<c:if test="${not empty lbpmAuditNote.auditNoteListsJsps4Pc}">
								<tr>
									<td>
								<c:forEach items="${lbpmAuditNote.auditNoteListsJsps4Pc}" var="auditNoteListsJsp" varStatus="vstatus">
									<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
										<c:param name="auditNoteFdId" value="${lbpmAuditNote.fdId}" />
										<c:param name="modelName" value="${lbpmAuditNote.fdProcess.fdModelName}" />
										<c:param name="modelId" value="${lbpmAuditNote.fdProcess.fdModelId}" />
										<c:param name="formName" value="${param.formName}"/>
										<c:param name="curHanderId" value="${lbpmAuditNote.fdHandler.fdId}"/>
									</c:import>
								</c:forEach>
									</td>
								</tr>
								</c:if>
								<tr>
									<td>
										<%
											pageContext.setAttribute("__fdUID", com.landray.kmss.util.IDGenerator.generateID());
										%>
										<c:set var="_workitemId_handlerId_key" value="${lbpmAuditNote.fdWorkitemId}_${lbpmAuditNote.fdHandler.fdId}" />
										<c:if test="${not empty mainForm.attachmentForms[_workitemId_handlerId_key] and not empty mainForm.attachmentForms[_workitemId_handlerId_key].attachments}">
										<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								          <c:param name="formBeanName" value="${formBeanName}"/>
								          <c:param name="fdKey" value="${lbpmAuditNote.fdWorkitemId}_${lbpmAuditNote.fdHandler.fdId}"/>
								          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
								          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
								          <c:param name="fdViewType" value="simple" />
								          <c:param name="fdForceDisabledOpt" value="edit" />
								          <c:param name="fdUID" value="${__fdUID}"></c:param>
								        </c:import>
								        </c:if>
								        <c:if test="${not empty mainForm.attachmentForms[lbpmAuditNote.fdId] and not empty mainForm.attachmentForms[lbpmAuditNote.fdId].attachments}">
										<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								          <c:param name="formBeanName" value="${formBeanName}"/>
								          <c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
								          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
								          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
								          <c:param name="fdViewType" value="simple" />
								          <c:param name="fdForceDisabledOpt" value="edit" />
								          <c:param name="fdUID" value="${__fdUID}"></c:param>
								        </c:import>
								        </c:if>
									</td>
								</tr>
								<c:if test="${not empty lbpmAuditNote.auditNoteFrom}">
								<tr>
									<td align="right" style="color:#999;">
										<kmss:showText value="${lbpmAuditNote.auditNoteFrom}" />
									</td>
								</tr>
								</c:if>
								<!-- 流程附言 -->
								<c:if test="${printLbpmPostscript == 'true'}">
									<tr>
										<td colspan="3" class="lbpmPostscript" style="padding-top:10px;">
											<c:import url="/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript_list.jsp" charEncoding="UTF-8">
										          <c:param name="formBeanName" value="${formBeanName}"/>
										          <c:param name="fdAuditNoteId" value="${lbpmAuditNote.fdId}"/>
										          <c:param name="fdFactNodeId" value="${lbpmAuditNote.fdFactNodeId}" />
										          <c:param name="print" value="true" />
									        </c:import>
										</td>
									</tr>
								</c:if>
							</table>
						</c:if>
						<c:if test="${lbpmAuditNote.fdIsHide=='1'}" >
							<font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font>
						</c:if>
						<c:if test="${lbpmAuditNote.fdIsHide=='3'}" >
							<font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font>
						</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>