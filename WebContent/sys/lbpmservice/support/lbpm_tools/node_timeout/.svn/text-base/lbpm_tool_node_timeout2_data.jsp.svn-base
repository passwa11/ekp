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
		
		<!--审批限时-->
		<list:data-column col="reviewTime" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.approvalTime')}">
			<c:if test="${not empty reviewTimeMap[lbpmTemplate.fdId]}">
	             ${reviewTimeMap[lbpmTemplate.fdId]}
	        </c:if>
		</list:data-column>
		
		<!--创建人-->
		<list:data-column property="fdCreator.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.creator')}">
		</list:data-column>
		
		<!--创建时间-->
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmTools.createTime')}">
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>