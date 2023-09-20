<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling"%>
<%
	request.setAttribute("fdAppModelId", request.getParameter("fdAppModelId"));
%>
<list:data>
	<modeling:data-columns var="modelingAppSimpleMain" list="${queryPage.list }" templateId="${modelId }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject" title="${lfn:message('sys-modeling-main:main.docSubject') }" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${modelingAppSimpleMain.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdModel.fdName" title="${lfn:message('sys-modeling-main:main.fdTemplateName') }" escape="false">
			<c:out value="${modelingAppSimpleMain.fdModel.fdName}" />
		</list:data-column>
		<list:data-column headerStyle="width:80px" col="docCreator.fdName" title="${lfn:message('sys-modeling-main:modeling.docCreator') }" escape="false">
		        <ui:person personId="${modelingAppSimpleMain.docCreator.fdId}" personName="${modelingAppSimpleMain.docCreator.fdName}"></ui:person>
		</list:data-column>
		 <!-- 创建时间-->
		 <list:data-column headerStyle="width:100px" col="docCreateTime" title="${lfn:message('sys-modeling-main:modeling.docCreateTime') }">
		        <kmss:showDate value="${modelingAppSimpleMain.docCreateTime}" type="date"></kmss:showDate>
	      </list:data-column>
	</modeling:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>