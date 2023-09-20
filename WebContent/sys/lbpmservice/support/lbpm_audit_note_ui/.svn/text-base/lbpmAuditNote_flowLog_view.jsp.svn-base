<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<body>
<c:set var="mainForm" value="${requestScope[formBeanName]}" scope="page" />
<html>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
seajs.use(['theme!form']);
</script>
<html:form action="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do">
	<p class="txttitle"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdAuditNote" /></p>
	<table class="tb_normal" width="95%">
		<c:forEach items="${auditNotes}" var="lbpmAuditNote" varStatus="vStatus">
		<tr class="tr_normal">
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdAuditNote" />
			</td>
			<td>
				<c:if test="${lbpmAuditNote.fdActionKey != 'subFlowNode_start'}">
					<table class="tb_noborder" width="100%">
					<c:if test="${lbpmAuditNote.fdIsHide=='2'}">
							<tr>
								<td style="word-wrap: break-word;word-break: break-word;">
								<c:choose>
									<c:when test="${lbpmAuditNote.fdActionKey == 'share_handler_pass' || lbpmAuditNote.fdActionKey == 'share_handler_refuse'}">
										<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='1'}">
											<strong><label class="auditNote" style="white-space:pre-wrap">${lbpmAuditNote.fdAuditNote}</label></strong>
										</c:if>
										<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='0'}">
												<label class="auditNote" style="white-space:pre-wrap">${lbpmAuditNote.fdAuditNote}</label>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='1'}">
											<strong><label class="auditNote" style="white-space:pre-wrap"><c:out value="${lbpmAuditNote.fdAuditNote}" /></label></strong>
										</c:if>
										<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='0'}">
											<label class="auditNote" style="white-space:pre-wrap"><c:out value="${lbpmAuditNote.fdAuditNote}" /></label>
										</c:if>
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="word-wrap: break-word;word-break: break-all;">
									<c:if test="${not empty lbpmAuditNote.fdExtNote}">
										<c:out value="${lbpmAuditNote.fdExtNote}" escapeXml="false"/>
									</c:if>
								</td>
							</tr>
							<c:if test="${not empty lbpmAuditNote.auditNoteListsJsps4Pc}">
							<tr>
								<td colspan="2">
							<c:forEach items="${lbpmAuditNote.auditNoteListsJsps4Pc}" var="auditNoteListsJsp" varStatus="vstatus">
								<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
									<c:param name="auditNoteFdId" value="${lbpmAuditNote.fdId}" />
									<c:param name="modelName" value="${lbpmAuditNote.fdProcess.fdModelName}" />
									<c:param name="modelId" value="${lbpmAuditNote.fdProcess.fdModelId}" />
									<c:param name="formName" value="${formBeanName}"/>
									<c:param name="curHanderId" value="${lbpmAuditNote.fdHandler.fdId}"/>
								</c:import>
							</c:forEach>
								</td>
							</tr>
							</c:if>
							<c:if test="${not empty mainForm.attachmentForms[lbpmAuditNote.fdId] and not empty mainForm.attachmentForms[lbpmAuditNote.fdId].attachments}">
							<tr>
								<td colspan="2">
							        <c:choose>
										<c:when test="${param.approveType eq 'right'}">
											<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
									          <c:param name="formBeanName" value="${formBeanName}"/>
									          <c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
									          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
									          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
									          <%-- 修改附件模板类型，让审批意见附件功能支持全部下载功能 2017-9-21 王祥 --%>
									          <%-- <c:param name="fdViewType" value="simple" /> --%>
									          <c:param name="fdViewType" value="byte" />
									          <c:param name="fdForceDisabledOpt" value="edit" />
									          <c:param name="fdLabel" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.auditnote')}" />
											  <c:param name="fdGroup" value="lbpm" />
											  <c:param name="fdGroupName" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.group')}" />
									       	  <c:param name="isShowDownloadCount" value="false" />
									        </c:import>
										</c:when>
										<c:otherwise>
											<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
									          <c:param name="formBeanName" value="${formBeanName}"/>
									          <c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
									          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
									          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
									          <%-- 修改附件模板类型，让审批意见附件功能支持全部下载功能 2017-9-21 王祥 --%>
									          <%-- <c:param name="fdViewType" value="simple" /> --%>
									          <c:param name="fdViewType" value="byte" />
									          <c:param name="fdForceDisabledOpt" value="edit" />
									          <c:param name="fdLabel" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.auditnote')}" />
											  <c:param name="fdGroup" value="lbpm" />
											  <c:param name="fdGroupName" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.group')}" />
									       	</c:import>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							</c:if>
							<c:if test="${not empty lbpmAuditNote.auditNoteFrom && fn:length(requestScope.lbpmPostscriptMap[lbpmAuditNote.fdId]) > 0}">
							<tr>
								<td colspan="2" align="right" style="color:#999;">
									<kmss:showText value="${lbpmAuditNote.auditNoteFrom}" />
								</td>
							</tr>
							</c:if>
							<!-- 流程附言 -->
							<tr>
								<td colspan="3" class="lbpmPostscript" style="padding-top:10px;">
									<c:import url="/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript_list.jsp" charEncoding="UTF-8">
								          <c:param name="formBeanName" value="${formBeanName}"/>
								          <c:param name="fdAuditNoteId" value="${lbpmAuditNote.fdId}"/>
								          <c:param name="fdFactNodeId" value="${lbpmAuditNote.fdFactNodeId}" />
							        </c:import>
								</td>
							</tr>
					</c:if>
					<c:if test="${lbpmAuditNote.fdIsHide=='1'}" >
						<tr><td colspan="2"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font></td></tr>
					</c:if>
					<c:if test="${lbpmAuditNote.fdIsHide=='3'}" >
						<tr><td colspan="2"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font></td></tr>
					</c:if>
					<c:if test="${not empty lbpmAuditNote.auditNoteFrom && !(fn:length(requestScope.lbpmPostscriptMap[lbpmAuditNote.fdId]) > 0 && lbpmAuditNote.fdIsHide=='2')}">
					<tr>
						<td colspan="2" align="right" style="color:#999;">
							<kmss:showText value="${lbpmAuditNote.auditNoteFrom}" />
						</td>
					</tr>
					</c:if>
					</table>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
</html:form>
</body>
</html>