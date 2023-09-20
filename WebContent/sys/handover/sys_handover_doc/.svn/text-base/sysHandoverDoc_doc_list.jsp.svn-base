<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="detailInfo" list="${page.list}"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdProcess.fdModelName">
		</list:data-column>
		<list:data-column col="index">
		     ${status+1}
		</list:data-column>
		<!--url-->
		<list:data-column col="url" escape="false">
			<c:if test="${not empty urlMap[detailInfo.fdProcess.fdId]}">
	            ${urlMap[detailInfo.fdProcess.fdId]}
	        </c:if>
		</list:data-column>
		<!--标题-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docSubject') }"
			escape="false" style="text-align:left;min-width:200px">
			<c:if test="${not empty subjectMap[detailInfo.fdProcess.fdId]}">
	             <span class="com_subject">${subjectMap[detailInfo.fdProcess.fdId]}</span>
	        </c:if>
		</list:data-column>
		<!--创建人-->
		<list:data-column headerStyle="width:8%" property="fdProcess.fdCreator.fdName"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docAuthor') }">
		</list:data-column>
		<%-- 特权人不需要节点名称 --%>
		<% if(!"privilegerIds".equals(request.getParameter("item"))) { %>
		<!--节点名称-->
		<list:data-column headerStyle="width:10%" property="fdFactId"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.fdFactId') }">
		</list:data-column>
		<% } %>
		<!--创建时间-->
		<list:data-column headerStyle="width:130px" property="fdProcess.fdCreateTime"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docAuthorTime') }">
		</list:data-column>
		<!--状态-->
		<c:if test="${param.method == 'getRecentHandle' || param.method == 'getInvalidHandler' || lbpmProcess.fdStatus!='21'}">
			<list:data-column headerStyle="width:8%" col="fdStatus"
				title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.status') }"
				escape="false">
				<c:if test="${detailInfo.fdProcess.fdStatus=='20'}">
					${ lfn:message('sys-handover-support-config-doc:status.activated') }
				</c:if>
				<c:if test="${detailInfo.fdProcess.fdStatus=='21'}">
					${ lfn:message('sys-handover-support-config-doc:status.error') }
				</c:if>
				<c:if test="${detailInfo.fdProcess.fdStatus=='30'}">
					${ lfn:message('sys-handover-support-config-doc:status.completed') }
				</c:if>
				<c:if test="${detailInfo.fdProcess.fdStatus=='00'}">
					${ lfn:message('sys-handover-support-config-doc:status.discard') }
				</c:if>
				<c:if test="${detailInfo.fdProcess.fdStatus=='40'}">
					${ lfn:message('sys-handover-support-config-doc:status.suspend') }
				</c:if>
			</list:data-column>
		</c:if>
		<!--当前处理（节点）-->
		<list:data-column headerStyle="width:130px" col="nodeName" title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.nodeName') }" escape="false">
			<kmss:showWfPropertyValues var="nodevalue" idValue="${detailInfo.fdProcess.fdId}" propertyName="nodeName" />
			    <div title='<c:out value="${nodevalue}"></c:out>'>
			        <c:out value="${nodevalue}"></c:out>
			    </div>
		</list:data-column>
		<!--当前处理人-->
		<list:data-column headerStyle="width:130px" col="handlerName" title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.currentHandler') }" escape="false">
		   <kmss:showWfPropertyValues var="handlerValue" idValue="${detailInfo.fdProcess.fdId}" propertyName="handlerName" />
			    <div style="font-weight:bold;" title='<c:out value="${handlerValue}"></c:out>'>
			        <c:out value="${handlerValue}"></c:out>
			    </div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${page.pageno }"
		pageSize="${page.rowsize }" totalSize="${page.totalrows }">
	</list:data-paging>
</list:data>