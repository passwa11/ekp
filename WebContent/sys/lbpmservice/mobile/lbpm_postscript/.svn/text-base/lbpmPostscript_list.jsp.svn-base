<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="mainForm" value="${requestScope[param.formBeanName]}" scope="page" />
<c:forEach items="${requestScope[param.fdAuditNoteId]}" var="lbpmPostscript" varStatus="vStatus">
	<div class="muiLbpmPostscriptTableWrap">
		<table class="tb_noborder" width="100%">
			<c:if test="${lbpmPostscript.fdIsHide=='2'}">
				<tr>
					<td class="muiLbpmPostscriptContent muiLbpmPostscriptTop">
						<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
							<span class="lbpmPostscriptHeadBtns">
								<span class="muiLbpmserviceAuditExpandBtn" name="lbpmPostscriptExpandBtn" style="display: none;">
								</span>
								<span class="muiLbpmserviceAuditCollapseBtn" name="lbpmPostscriptCollapseBtn" style="display: inline-block;">
								</span>
							</span>
						</c:if>
						<span class="muiLbpmPostscriptHead">
							<kmss:showDate type="datetime" value="${lbpmPostscript.fdCreateTime}"/>
						</span>
						<span class="muiLbpmPostscriptHead" title='<c:out value="${lbpmPostscript.handlerName}" />'>
							<c:out value="${lbpmPostscript.handlerName}" escapeXml="false"/>
						</span>
						<span class="muiLbpmPostscriptHead">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.list.postscript" />
						</span>
					</td>
				</tr>
				<tr class="muiLbpmPostscriptContentTr">
					<td class="muiLbpmPostscriptContent">
						<kmss:showText value="${lbpmPostscript.fdPostscript}" />
					</td>
				</tr>
				<c:if test="${not empty mainForm.attachmentForms[lbpmPostscript.fdId] and not empty mainForm.attachmentForms[lbpmPostscript.fdId].attachments}">
					<tr class="muiLbpmPostscriptAttachTr">
						<td colspan="2" class="muiLbpmPostscriptAttachmentContent">
							<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
					          <c:param name="formBeanName" value="${formBeanName}"/>
					          <c:param name="fdKey" value="${lbpmPostscript.fdId}"/>
					          <c:param name="fdModelName" value="${lbpmPostscript.fdProcess.fdModelName}" />
							  <c:param name="fdModelId" value="${lbpmPostscript.fdProcess.fdModelId}" />
					          <c:param name="fdViewType" value="simple"/>
					        </c:import>
						</td>
					</tr>
				</c:if>
			</c:if>
			<c:if test="${lbpmPostscript.fdIsHide=='1'}" >
				<tr><td colspan="2" class="muiLbpmPostscriptContent"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font></td></tr>
			</c:if>
			<c:if test="${lbpmPostscript.fdIsHide=='3'}" >
				<tr><td colspan="2" class="muiLbpmPostscriptContent"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font></td></tr>
			</c:if>
			<c:if test="${not empty lbpmPostscript.fdPostscriptFrom}">
				<tr>
					<td colspan="2" align="right" style="color:#999;">
						<kmss:showText value="${lbpmPostscript.postscriptFrom}" />
					</td>
				</tr>
			</c:if>
		</table>
	</div>
</c:forEach>