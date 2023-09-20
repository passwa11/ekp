<%@ page language="java" contentType="text/json; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmAuditNote" list="${queryPage.list }">
		<list:data-column headerStyle="width:15%" col="fdArrivalTime" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.arrivalTime') }">
			<c:if test='${not empty lbpmAuditNote.fdWorkitemId and not empty lbpmAuditNote.fdActionKey}'>
				<kmss:showDate value="${arrivalTimeMap[lbpmAuditNote.fdWorkitemId]}" type="time.sec"/>
			</c:if>
		</list:data-column>
		<list:data-column headerStyle="width:15%" col="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.processingTime') }">
			<kmss:showDate value="${lbpmAuditNote.fdCreateTime}" type="time.sec"/>
		</list:data-column>
		<list:data-column headerClass="width60" property="fdFactNodeName" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.fdFactNodeName') }" style="white-space:nowrap;">
		</list:data-column>
		<list:data-column headerClass="width60" col="handlerName" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.fdHandlerId') }">
			<c:out value="${lbpmAuditNote.handlerName}" />
			<c:if test="${lbpmAuditNote.fdActionKey == 'share_handler_pass' || lbpmAuditNote.fdActionKey == 'share_handler_refuse'}">
				(分享人)
			</c:if>
		</list:data-column>
		<list:data-column headerStyle="width:8%" property="fdActionInfo" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.fdActionKey') }" style="word-wrap: break-word;word-break: break-all;">
		</list:data-column>

		<list:data-column headerStyle="width:12%" property="fdActionName" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.fdActionInfo') }">
		</list:data-column>
		<list:data-column headerClass="width60" col="fdNotifyType" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.fdNotifyType') }" escape="false">
			<c:if test="${empty lbpmAuditNote.fdNotifyType}">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdNotifyType.default" />
			</c:if>
			<c:if test="${not empty lbpmAuditNote.fdNotifyType}">
				<kmss:showNotifyType value="${lbpmAuditNote.fdNotifyType}" />
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width60" property="fdCostTimeDisplayString" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNote.list.fdCostTime') }" style="word-wrap: break-word;word-break: break-all;">
		</list:data-column>
		<list:data-column headerClass="width60" col="fdAuditNote" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.opinion') }" escape="false">
			<c:if test="${not empty lbpmAuditNote.fdAuditNote}">
				<a style="color:#2574ad;" href="javascript:viewAuditNote('${lbpmAuditNote.fdId}');"><bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.opinion" /></a>
			</c:if>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
					  pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>