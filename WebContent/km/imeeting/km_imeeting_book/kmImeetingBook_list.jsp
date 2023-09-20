<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmImeetingBook" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdName" title="${ lfn:message('km-imeeting:kmImeetingBook.fdName') }" escape="false" style="text-align:left;min-width:150px;">
			<span class="com_subject"><c:out value="${kmImeetingBook.fdName}"/></span>
		</list:data-column>
      	<list:data-column col="fdPlace" title="${ lfn:message('km-imeeting:kmImeetingBook.fdPlace') }" escape="false">
		  	<c:out value="${kmImeetingBook.fdPlace.fdName}"/>
		</list:data-column>
		<list:data-column col="fdHoldDate" title="${ lfn:message('km-imeeting:kmImeetingBook.fdHoldDate') }">
		   <kmss:showDate value="${kmImeetingBook.fdHoldDate}" type="datetime" />
		</list:data-column>
		<list:data-column col="fdFinishDate" title="${ lfn:message('km-imeeting:kmImeetingBook.fdFinishDate') }">
		   <kmss:showDate value="${kmImeetingBook.fdFinishDate}" type="datetime" /> 
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${ lfn:message('km-imeeting:kmImeetingBook.docCreator') }" escape="false">
			<ui:person personId="${kmImeetingBook.docCreator.fdId}" personName="${kmImeetingBook.docCreator.fdName}"></ui:person>
		</list:data-column>
		<list:data-column col="status" title="${ lfn:message('km-imeeting:kmImeetingBook.exam.status') }">
			<c:choose>
				<c:when test="${kmImeetingBook.fdHasExam eq true}">
					<bean:message bundle="km-imeeting" key="kmImeetingBook.exam.status.yes" />
				</c:when>
				<c:when test="${kmImeetingBook.fdHasExam eq false}">
					<bean:message bundle="km-imeeting" key="kmImeetingBook.exam.status.no" />
				</c:when>
				<c:otherwise>
					<bean:message bundle="km-imeeting" key="kmImeetingCalendar.res.wait" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>