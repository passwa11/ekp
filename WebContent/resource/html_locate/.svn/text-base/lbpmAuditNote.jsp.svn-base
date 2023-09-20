<%@page import="com.landray.kmss.sys.lbpmservice.support.actions.LbpmAuditNoteAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="fdModelId" value="${requestScope[param.formName].fdId}" scope="request"/>
<c:set var="fdModelName" value="${requestScope[param.formName].modelClass.name}" scope="request"/>
<% new LbpmAuditNoteAction().listNote(null, null, request, response);%>
<c:set var="mainForm" value="${requestScope[param.formName]}" scope="page" />
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
					id="${lbpmAuditNote.fdExecutionId}"
					<c:if test="${empty rootExecutionId}">
						<c:set var="rootExecutionId" value="${lbpmAuditNote.fdParentExecutionId}" />
					</c:if>
					<c:if test="${lbpmAuditNote.fdParentExecutionId != rootExecutionId}">
						class="child-of-${lbpmAuditNote.fdParentExecutionId}"
					</c:if>
				</c:if>
				<c:if test="${lbpmAuditNote.fdActionKey != '_concurrent_branch'}">
					id="${lbpmAuditNote.fdId}"
					<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
						class="child-of-${lbpmAuditNote.fdExecutionId}"
					</c:if>
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
					<c:out value="${lbpmAuditNote.fdActionInfo}" />
				</td>
				<td>
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
							<style>
							.initialized .lui_upload_img_box{ display: block;}
							</style>
							<%
								pageContext.setAttribute("__fdModelId", com.landray.kmss.util.IDGenerator.generateID());
								pageContext.setAttribute("__fdUID", System.currentTimeMillis());
							%>
							<c:choose>
								<c:when test="${auditNoteListsJsp == \"/sys/lbpmservice/mobile/audit_note_ext/sign/sysLbpmProcess_pc_signlog.jsp\"}">
									<%-- 手机端签名 --%>
									<c:import url="/resource/html_locate/sysAttMain_view.jsp" charEncoding="UTF-8">
							    		<c:param name="formBeanName" value="${param.formName}" />
										<c:param name="fdKey" value="${param.auditNoteFdId}_sg" />
									    <c:param name="fdViewType" value="simple" />
									   	<c:param name="fdForceDisabledOpt" value="edit" />
									   	<c:param name="fdModelId" value="${__fdModelId}"></c:param>
									   	<c:param name="fdUID" value="${__fdUID}"></c:param>
									</c:import>
								</c:when>
								<c:when test="${auditNoteListsJsp == \"/sys/lbpmservice/mobile/audit_note_ext/speech/sysLbpmProcess_pc_speechlog.jsp\"}">
									<%-- 手机端语音 --%>
									<c:import url="/resource/html_locate/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="formBeanName" value="${param.formName}" />
										<c:param name="fdKey" value="${param.auditNoteFdId}_sp" />
										<c:param name="fdViewType" value="simple" />
									   	<c:param name="fdForceDisabledOpt" value="edit" />
									   	<c:param name="fdModelId" value="${__fdModelId}"></c:param>
									   	<c:param name="fdUID" value="${__fdUID}"></c:param>
									</c:import>
								</c:when>
								<c:otherwise>
									<%-- 其它页面，预期为:
										/sys/lbpmext/auditpoint/pc/view.jsp, 
										/sys/lbpmservice/signature/sysLbpmProcess_signatureList.jsp,
										/sys/lbpmservice/signature/handSignture/lbpmAuditNote_handlerSignature.jsp, 
										/sys/lbpmservice/mobile/audit_note_ext/hand/sysLbpmProcess_handlog.jsp, 
									 --%>
									<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
										<c:param name="auditNoteFdId" value="${lbpmAuditNote.fdId}" />
										<c:param name="modelName" value="${lbpmAuditNote.fdProcess.fdModelName}" />
										<c:param name="modelId" value="${lbpmAuditNote.fdProcess.fdModelId}" />
										<c:param name="formName" value="${param.formName}"/>
										<c:param name="curHanderId" value="${lbpmAuditNote.fdHandler.fdId}"/>
									</c:import>
								</c:otherwise>
							</c:choose>
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
					        		<c:import url="/resource/html_locate/sysAttMain_view.jsp" charEncoding="UTF-8">
								    	<c:param name="formBeanName" value="${formBeanName}"/>
								        <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
								        <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
										<c:param name="fdMulti" value="${ param.fdMulti }" />
										<c:param name="fdAttType" value="${ param.fdAttType }" />
								        <c:param name="fdViewType" value="simple" />
								        <c:param name="fdKey" value="${lbpmAuditNote.fdWorkitemId}_${lbpmAuditNote.fdHandler.fdId}"/>
							        	<c:param name="fdUID" value="${__fdUID}"></c:param>
									</c:import>
						        </c:if>
						        <c:if test="${not empty mainForm.attachmentForms[lbpmAuditNote.fdId] and not empty mainForm.attachmentForms[lbpmAuditNote.fdId].attachments}">
					        		<c:import url="/resource/html_locate/sysAttMain_view.jsp" charEncoding="UTF-8">
							         	<c:param name="formBeanName" value="${formBeanName}"/>
							          	<c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
							          	<c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
										<c:param name="fdMulti" value="${ param.fdMulti }" />
										<c:param name="fdAttType" value="${ param.fdAttType }" />
							          	<c:param name="fdViewType" value="simple" />
							          	<c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
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
					</table>
				</td>
			</tr>
		</c:forEach>
	</table>