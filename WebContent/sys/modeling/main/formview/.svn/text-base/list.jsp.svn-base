<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling"%>
<%
	request.setAttribute("fdAppModelId", request.getParameter("fdAppModelId"));
%>
<list:data>
	<modeling:data-columns var="modelingAppModelMain" list="${queryPage.list }" templateId="${modelId }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject" title="${lfn:message('sys-modeling-main:main.docSubject') }" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${modelingAppModelMain.docSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdModel.fdName" title="${lfn:message('sys-modeling-main:main.fdTemplateName') }"  escape="false">
			<c:out value="${modelingAppModelMain.fdModel.fdName}" />
		</list:data-column>
		<list:data-column headerStyle="width:100px" col="docCreator.fdName" title="${lfn:message('sys-modeling-main:modeling.docCreator') }" escape="false">
		        <ui:person personId="${modelingAppModelMain.docCreator.fdId}" personName="${modelingAppModelMain.docCreator.fdName}"></ui:person>
		</list:data-column>
		<list:data-column headerClass="width40" col="docStatus" title="${lfn:message('sys-modeling-main:modelingAppBaseModel.docStatus') }">
	        <c:if test="${modelingAppModelMain.docStatus=='00'}">
				${ lfn:message('sys-modeling-base:status.discard')}
			</c:if>
			<c:if test="${modelingAppModelMain.docStatus=='10'}">
				${ lfn:message('sys-modeling-base:status.draft') } 
			</c:if>
			<c:if test="${modelingAppModelMain.docStatus=='11'}">
				${ lfn:message('sys-modeling-base:status.refuse')}
			</c:if>
			<c:if test="${modelingAppModelMain.docStatus=='20'}">
				${ lfn:message('sys-modeling-base:status.append') }
			</c:if>
			<c:if test="${modelingAppModelMain.docStatus=='30'}">
				${ lfn:message('sys-modeling-base:status.publish') }
			</c:if>
			<c:if test="${modelingAppModelMain.docStatus=='31'}">
				${ lfn:message('sys-modeling-base:status.feedback') }
			</c:if>
		</list:data-column>
		 <list:data-column headerStyle="width:100px" col="docCreateTime" title="${lfn:message('sys-modeling-main:modeling.docCreateTime') }">
		        <kmss:showDate value="${modelingAppModelMain.docCreateTime}" type="date"></kmss:showDate>
	      </list:data-column>
		<list:data-column headerClass="width100" col="nodeName" title="${lfn:message('sys-modeling-main:modeling.common.currentProgress') }" escape="false">
			<kmss:showWfPropertyValues  var="nodevalue" idValue="${modelingAppModelMain.fdId}" propertyName="nodeName" />
			    <div class="textEllipsis width100" title="${nodevalue}">
			        <c:out value="${nodevalue}"></c:out>
			    </div>
		</list:data-column>
		<list:data-column headerClass="width80" col="handlerName" title="${lfn:message('sys-modeling-main:modeling.common.currentHandler') }" escape="false">
		   <kmss:showWfPropertyValues  var="handlerValue" idValue="${modelingAppModelMain.fdId}" propertyName="handlerName" />
			    <div class="textEllipsis width80" style="font-weight:bold;" title="${handlerValue}">
			        <c:out value="${handlerValue}"></c:out>
			    </div>
		</list:data-column>
	</modeling:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>