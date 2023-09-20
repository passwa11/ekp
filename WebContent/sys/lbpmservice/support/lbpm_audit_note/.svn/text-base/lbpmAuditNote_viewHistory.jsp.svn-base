<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script type="text/javascript">
		Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
		Com_IncludeFile("jquery.js");
		Com_IncludeFile("swf_attachment.js?mode=edit","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);
		</script>
		<style>
			.tb_normal .td_normal_title {
				text-align: center;
			}
		</style>
		<p class="txttitle" style="color:#333;"><bean:message bundle="sys-lbpmservice-support" key="table.lbpmAuditNote.viewHistoryAuditNote"/></p>

		<center>
			<table class="tb_normal" width="100%" id="auditNoteTable" >
				<tr class="tr_normal">
					<!-- 审批意见 -->
					<td width="43%" class="td_normal_title">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdAuditNote" />
					</td>
					<!-- 修改时间 -->
					<td width="12%" class="td_normal_title">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.date" />
					</td>
					<!-- 处理人 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdHandlerId" />
					</td>
					<!-- 附件 -->
					<%-- <c:if test="${not empty sysAttMains}"> --%>
					<td class="td_normal_title">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdAttachment" />
					</td>
					<%-- </c:if> --%>
				</tr>
				<c:forEach items="${lbpmHistoryAuditNotes}" var="lbpmHistoryAuditNote" varStatus="vStatus">
					<tr>
						
						<td style="word-wrap: break-word;word-break: break-all;">
							<kmss:showText value="${lbpmHistoryAuditNote.fdAuditNote}" />
						</td>
						
						<td style="white-space: nowrap;word-break: keep-all;">
							<kmss:showDate type="datetime" value="${lbpmHistoryAuditNote.fdCreateTime}"/>
						</td>
						
						<td style="text-align:center;">
							<span title='<c:out value="${lbpmHistoryAuditNote.fdHandlerName}" />'>
								<c:out value="${lbpmHistoryAuditNote.fdHandlerName}" escapeXml="false"/>
							</span>
						</td>
						<td>
							<table class="tb_noborder" width="100%">
								<c:if test="${not empty sysAttMains}">
								<tr>
									<td>
									<c:import url="/sys/lbpmservice/support/lbpm_audit_note/att/historyAuditNote_att_view.jsp" charEncoding="UTF-8">
							          <c:param name="formBeanName" value="${formBeanName}"/>
							          <c:param name="fdKey" value="${lbpmHistoryAuditNote.fdId}"/>
							          <c:param name="fdModelId" value=""/>
							          <c:param name="fdModelName" value=""/>
							          <c:param name="fdViewType" value="byte" />
							          <c:param name="fdForceDisabledOpt" value="edit" />
							        </c:import>
									</td>
								</tr>
								</c:if>
						</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</center>
</template:replace>
</template:include>