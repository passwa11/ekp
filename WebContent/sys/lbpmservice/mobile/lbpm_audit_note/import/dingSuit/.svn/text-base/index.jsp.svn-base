<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
//#79908 移除页面漏洞代码，增加移动端审批意见获取方法，避免移动端审批意见绕过主文档可阅读权限 作者 曹映辉 #日期 2019年8月14日
%>
<script>
	var LbpmAuditNoteList = ${auditNotes};
</script>
<c:if test="${not empty auditNotes}">
	<c:forEach items="${auditNotes}" var="auditNote" varStatus="vstatus">
		<%
			JSONObject auditNote = (JSONObject) pageContext
							.getAttribute("auditNote");
		%>
		<c:if test="${auditNote.fdActionKey=='_concurrent_branch' }">
			<%
				if (auditNote.getBoolean("firstBlank")) {
			%>
				<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditLabelItem"
					data-dojo-props="store:{fdFactNodeName:'并行分支',fdHandlerName:'启动节点'},formBeanName:'${ formBeanName}'">
				</div>
			<%
				}
			%>
			<div
				data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceAuditBranchItem"
				data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }']"></div>
		</c:if>
		<c:if test="${auditNote.fdActionKey!='_concurrent_branch' }">
			<%
				if (auditNote.getBoolean("showHead")) {
			%>
				<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditNode"
					data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }'],formBeanName:'${ formBeanName}'">
			<%
				}
			%>
			<c:if test="${'true' eq auditNote.hasOuterBorder }">
				<div class="muiDingAuditNoteItemWrap">
			</c:if>
			<div data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditNodeContainer"
				data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }'],formBeanName:'${ formBeanName}'">
				<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditLabelItem"
					data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }'],formBeanName:'${ formBeanName}'">
				</div>
				<div class="<c:if test="${auditNote.fdActionKey=='_pocess_end' }">auditLastNodeItem</c:if>"
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditNoteItem"
					data-dojo-props="store:LbpmAuditNoteList['${vstatus.index }'],formBeanName:'${ formBeanName}'">
					<c:if test="${auditNote.fdIsHide eq '2'}">
						<c:forEach items="${auditNote.auditNoteListsJsps4Mobile}"
							var="auditNoteListsJsp" varStatus="vstatus">
							
							<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
								<c:param name="auditNoteFdId" value="${auditNote.fdId}" />
								<c:param name="modelName"
									value="${auditNote.fdProcess.fdModelName}" />
								<c:param name="modelId" value="${auditNote.fdProcess.fdModelId}" />
								<c:param name="formName" value="${formBeanName}" />
							</c:import>
						</c:forEach>
						
						<c:import url="/sys/attachment/mobile/import/view.jsp"
							charEncoding="UTF-8">
							<c:param name="formName" value="${ formBeanName}"></c:param>
							<c:param name="fdKey" value="${auditNote.fdId}"></c:param>
							<c:param name="fdViewType" value="simple"></c:param>
						</c:import>
					</c:if>
				</div>
			</div>
			<c:if test="${'true' eq auditNote.hasOuterBorder }">
				</div>
			</c:if>
			<c:if test="${'true' eq auditNote.showHeadEnd }">
				<c:if test="${'30' ne auditNote.nodeStatus }">
					<div style="display:none;">
						<kmss:showWfPropertyValues idValue="${param.fdModelId}" var="fdCurrentHandlerId" propertyName="handlerId" extend="hidePersonal" />
						<kmss:showWfPropertyValues idValue="${param.fdModelId}" var="handlerName" propertyName="handlerName" extend="hidePersonal" />
						<person:dingHeadimage size="s" var="handlerImg" personId='${fdCurrentHandlerId}' contextPath='true'/>
					</div>
					<div class="muiDingAuditNoteItemWrap">
						<div data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditNodeContainer">
							<div
								data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingNextAuditLabelItem"
								data-dojo-props="handlerName:'${handlerName}',handlerImg:'${handlerImg}'">
							</div>
						</div>
					</div>
				</c:if>
				</div>
			</c:if>
		</c:if>
	</c:forEach>
	<c:if test="${param.docStatus=='20' || param.docStatus=='10' || param.docStatus=='11'}">
		<c:if test='${"true" eq showOuterNext}'>
			<div style="display:none;">
				<kmss:showWfPropertyValues idValue="${param.fdModelId}" var="fdCurrentHandlerId" propertyName="handlerId" extend="hidePersonal" />
				<kmss:showWfPropertyValues idValue="${param.fdModelId}" var="handlerName" propertyName="handlerName" extend="hidePersonal" />
				<person:dingHeadimage size="s" var="handlerImg" personId='${fdCurrentHandlerId}' contextPath='true'/>
			</div>
			<div data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingAuditNodeContainer">
				<div
					data-dojo-type="sys/lbpmservice/mobile/lbpm_audit_note/js/dingSuit/LbpmserviceDingNextAuditLabelItem"
					data-dojo-props="handlerName:'${handlerName}',handlerImg:'${handlerImg}'">
				</div>
			</div>
		</c:if>
	</c:if>
</c:if>