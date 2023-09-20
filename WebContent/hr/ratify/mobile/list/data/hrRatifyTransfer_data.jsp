<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="hrRatifyTransfer" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<%--状态--%>
		<list:data-column col="status" title="${ lfn:message('sys-news:sysNewsMain.docStatus') }" escape="false">
			<c:choose>
				<c:when test="${hrRatifyTransfer.docStatus=='00'}">
					<span class="muiProcessStatusBorder muiProcessDiscard">${ lfn:message('hr-ratify:status.discard')}</span>
				</c:when>
				<c:when test="${hrRatifyTransfer.docStatus=='10'}">
					<span class="muiProcessStatusBorder muiProcessDraft">${ lfn:message('hr-ratify:status.draft') } </span>
				</c:when>
				<c:when test="${hrRatifyTransfer.docStatus=='11'}">
					<span class="muiProcessStatusBorder muiProcessRefuse">${ lfn:message('hr-ratify:status.refuse')}</span>
				</c:when>
				<c:when test="${hrRatifyTransfer.docStatus=='20'}">
					<span class="muiProcessStatusBorder muiProcessExamine">${ lfn:message('hr-ratify:status.append') }</span>
				</c:when>
				<c:when test="${hrRatifyTransfer.docStatus=='30'}">
					<span class="muiProcessStatusBorder muiProcessPublish">${ lfn:message('hr-ratify:status.publish') }</span>
				</c:when>
				<c:when test="${hrRatifyTransfer.docStatus=='31'}">
					<span class="muiProcessStatusBorder muiProcessPublish">${ lfn:message('hr-ratify:status.feedback') }</span>
				</c:when>
			</c:choose>
		</list:data-column>
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false">
		         <c:out value="${hrRatifyTransfer.docSubject}"/>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('sys-news:sysNewsMain.docCreatorId') }" >
		         <c:out value="${hrRatifyTransfer.docCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
			    <person:headimageUrl personId="${hrRatifyTransfer.docCreator.fdId}" size="m" />
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('sys-news:sysNewsMain.docCreateTime') }">
	        <kmss:showDate value="${hrRatifyTransfer.docCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			<c:if test="${empty LandrayOffline }">
				/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=view&fdId=${hrRatifyTransfer.fdId}
			</c:if>
			<c:if test="${not empty LandrayOffline and LandrayOffline =='true' }">
				/hr/ratify/mobile/offline/view.jsp?fdId=${hrRatifyTransfer.fdId}
			</c:if>
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="summary" title="${ lfn:message('hr-ratify:sysWfNode.processingNode.currentProcessor') }" escape="false">
	        <kmss:showWfPropertyValues idValue="${hrRatifyTransfer.fdId}" propertyName="summary" />
      	</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>