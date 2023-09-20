<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		
		<!--模板ID-->
		<list:data-column col="fdModelId" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelId')}">
			<c:if test="${not empty modelIdMap[lbpmTemplate.fdId]}">
	             ${modelIdMap[lbpmTemplate.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--模板Name-->
		<list:data-column col="fdModelName" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.fdModelName')}">
			<c:if test="${not empty modelNameMap[lbpmTemplate.fdId]}">
	             ${modelNameMap[lbpmTemplate.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--模板名称-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-lbpmservice-support:lbpmTools.subject')}"
			escape="false" style="text-align:left;min-width:180px">
			<c:if test="${not empty subjectMap[lbpmTemplate.fdId]}">
	             <span class="com_subject">${subjectMap[lbpmTemplate.fdId]}</span>
	        </c:if>
		</list:data-column>
		
		<!--通知方式-->
		<list:data-column col="notifyType" escape="false" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.oldNotifyType')}">
			<c:if test="${not empty notifyTypeMap[lbpmTemplate.fdId]}">
	             <kmss:showNotifyType value="${notifyTypeMap[lbpmTemplate.fdId]}" />
	        </c:if>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>