<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmNodeDefinition" list="${queryPage.list }">
		<list:data-column property="fdId" />
		
		<!--模板ID-->
		<list:data-column col="fdModelId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelId')}">
			<c:if test="${not empty modelIdMap[lbpmNodeDefinition.fdId]}">
	             ${modelIdMap[lbpmNodeDefinition.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--模板Name-->
		<list:data-column col="fdModelName" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelName')}">
			<c:if test="${not empty modelNameMap[lbpmNodeDefinition.fdId]}">
	             ${modelNameMap[lbpmNodeDefinition.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--模板名称-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-lbpmservice-support:lbpmTools.subject')}"
			escape="false" style="text-align:left;min-width:180px">
			<c:if test="${not empty subjectMap[lbpmNodeDefinition.fdId]}">
	             <span class="com_subject">${subjectMap[lbpmNodeDefinition.fdId]}</span>
	        </c:if>
		</list:data-column>
		
		<list:data-column property="fdFactId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdFactId')}" >
		</list:data-column>
		
		<list:data-column property="fdFactName" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdFactName')}" style="width:15%;">
		</list:data-column>
		
		<!--审批限时-->
		<list:data-column col="reviewTime" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.approvalTime')}">
			<c:if test="${not empty reviewTimeMap[lbpmNodeDefinition.fdId]}">
	             ${reviewTimeMap[lbpmNodeDefinition.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--审批人-->
		<list:data-column col="creator" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.approver')}">
			<c:if test="${not empty creatorNameMap[lbpmNodeDefinition.fdId]}">
	             ${creatorNameMap[lbpmNodeDefinition.fdId]}
	        </c:if>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>