<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="lbpmProcess" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<%--状态--%>
		<list:data-column col="status" title="${ lfn:message('sys-lbpmperson:mui.lbpmperson.flow.docStatus') }" escape="false">
			<c:choose>
				<c:when test="${docStatusMap[lbpmProcess.fdId]=='00'}">
					<span class="muiProcessStatusBorder muiProcessDiscard">${ lfn:message('sys-lbpmperson:mui.lbpmperson.docAbandoned')}</span>
				</c:when>
				<c:when test="${docStatusMap[lbpmProcess.fdId]=='10'}">
					<span class="muiProcessStatusBorder muiProcessDraft">${ lfn:message('sys-lbpmperson:mui.lbpmperson.docDraft') } </span>
				</c:when>
				<c:when test="${docStatusMap[lbpmProcess.fdId]=='11'}">
					<span class="muiProcessStatusBorder muiProcessRefuse">${ lfn:message('sys-lbpmperson:mui.lbpmperson.docReject')}</span>
				</c:when>
				<c:when test="${docStatusMap[lbpmProcess.fdId]=='20'}">
					<span class="muiProcessStatusBorder muiProcessExamine">${ lfn:message('sys-lbpmperson:mui.lbpmperson.docPending') }</span>
				</c:when>
				<c:when test="${docStatusMap[lbpmProcess.fdId]=='30'}">
					<span class="muiProcessStatusBorder muiProcessPublish">${ lfn:message('sys-lbpmperson:mui.lbpmperson.docPublish') }</span>
				</c:when>
				<c:when test="${docStatusMap[lbpmProcess.fdId]=='31'}">
					<span class="muiProcessStatusBorder muiProcessPublish">${ lfn:message('sys-lbpmperson:mui.lbpmperson.docFeedback') }</span>
				</c:when>
			</c:choose>
		</list:data-column>
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('sys-lbpmperson:mui.lbpmperson.docSubject') }" escape="false">
		     <c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
		     	${subjectMap[lbpmProcess.fdId]}
	        </c:if>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('sys-lbpmperson:mui.lbpmperson.docCreator') }" >
		     <c:out value="${lbpmProcess.fdCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
			<person:headimageUrl personId="${lbpmProcess.fdCreator.fdId}" size="m" />
		</list:data-column>
		<%-- 创建者钉钉头像--%>
		<list:data-column col="dingIcon" escape="false">
			<person:dingHeadimage personId="${lbpmProcess.fdCreator.fdId}" size="m" returnIconUrl="true" />
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('sys-lbpmperson:mui.lbpmperson.person.creatorTime') }">
	        <kmss:showDate value="${lbpmProcess.fdCreateTime}" type="date"/>
      	</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			<c:if test="${not empty urltMap[lbpmProcess.fdId]}">
	             ${urltMap[lbpmProcess.fdId]}
	        </c:if>
		</list:data-column>
		 <%-- 流程进度--%>
	 	<list:data-column col="summary" title="${ lfn:message('sys-lbpmperson:mui.lbpmperson.flow.currentProcessor') }" escape="false">
	        <kmss:showWfPropertyValues idValue="${lbpmProcess.fdId}" propertyName="summary" />
      	</list:data-column>
      	<%-- 所属模块 --%>
      	<list:data-column col="module" title="${ lfn:message('sys-lbpmperson:mui.lbpmperson.docCreator') }" >
		      ${moduleMap[lbpmProcess.fdModelName]}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>