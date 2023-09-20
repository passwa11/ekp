<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrStaffMoveRecord" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdStaffNumber" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdStaffNumber')}" />
        <list:data-column property="fdStaffName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdStaffName')}" />
        <list:data-column property="fdIsExplore" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdIsExplore')}" />
        <list:data-column col="fdInternStartDate" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdInternStartDate')}">
            <kmss:showDate value="${hrStaffMoveRecord.fdInternStartDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdInternEndDate" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdInternEndDate')}">
            <kmss:showDate value="${hrStaffMoveRecord.fdInternEndDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdMoveType.name" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdMoveType')}">
            <sunbor:enumsShow value="${hrStaffMoveRecord.fdMoveType}" enumsType="hr_staff_move_type" />
        </list:data-column>
        <list:data-column col="fdMoveType">
            <c:out value="${hrStaffMoveRecord.fdMoveType}" />
        </list:data-column>
        <list:data-column property="fdBeforeRank" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeRank')}" />
        <list:data-column col="fdBeforeLeader.name" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeLeader')}" escape="false">
            <c:out value="${hrStaffMoveRecord.fdBeforeLeader.fdName}" />
        </list:data-column>
        <list:data-column col="fdBeforeLeader.id" escape="false">
            <c:out value="${hrStaffMoveRecord.fdBeforeLeader.fdId}" />
        </list:data-column>
        <list:data-column col="fdBeforeDept.name" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeDept')}" escape="false">
            <c:out value="${hrStaffMoveRecord.fdBeforeDept.fdName}" />
        </list:data-column>
        <list:data-column col="fdBeforeDept.id" escape="false">
            <c:out value="${hrStaffMoveRecord.fdBeforeDept.fdId}" />
        </list:data-column>
        <list:data-column property="fdAfterRank" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterRank')}" />
        <list:data-column col="fdAfterDept.name" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterDept')}" escape="false">
            <c:out value="${hrStaffMoveRecord.fdAfterDept.fdName}" />
        </list:data-column>
        <list:data-column col="fdAfterDept.id" escape="false">
            <c:out value="${hrStaffMoveRecord.fdAfterDept.fdId}" />
        </list:data-column>
        <list:data-column col="fdAfterLeader.name" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterLeader')}" escape="false">
            <c:out value="${hrStaffMoveRecord.fdAfterLeader.fdName}" />
        </list:data-column>
        <list:data-column col="fdAfterLeader.id" escape="false">
            <c:out value="${hrStaffMoveRecord.fdAfterLeader.fdId}" />
        </list:data-column>
        <list:data-column property="fdBeforeFirstDeptName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeFirstDeptName')}" escape="false">
        </list:data-column>
        <list:data-column property="fdBeforeSecondDeptName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeSecondDeptName')}" escape="false">
        </list:data-column>
        <list:data-column property="fdBeforeThirdDeptName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforeThirdDeptName')}" escape="false">
        </list:data-column>
        <list:data-column property="fdAfterFirstDeptName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterFirstDeptName')}" escape="false">
        </list:data-column>
        <list:data-column property="fdAfterSecondDeptName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterSecondDeptName')}" escape="false">
        </list:data-column>
        <list:data-column property="fdAfterThirdDeptName" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterThirdDeptName')}" escape="false">
        </list:data-column>
        <list:data-column col="fdBeforePosts" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdBeforePosts')}" escape="false">
        <c:forEach items="${ hrStaffMoveRecord.fdBeforePosts }" varStatus="vstatus" var = "post">
				${post.fdName};
			</c:forEach>
        </list:data-column>
        <list:data-column col="fdAfterPosts" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdAfterPosts')}" escape="false">
        <c:forEach items="${ hrStaffMoveRecord.fdAfterPosts }" varStatus="vstatus" var = "post">
				${post.fdName};
			</c:forEach>
        </list:data-column>
        <list:data-column col="fdTransDept" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdTransDept')}" escape="false">
        <c:choose>
        <c:when test="${hrStaffMoveRecord.fdTransDept==0}">
            <c:out value="否" />
            </c:when>
            <c:otherwise>
					<c:out value="是" />
				</c:otherwise>
				</c:choose>
        </list:data-column>
        <list:data-column col="fdMoveDate" title="${lfn:message('hr-staff:hrStaffMoveRecord.fdMoveDate')}">
            <kmss:showDate value="${hrStaffMoveRecord.fdMoveDate}" type="date"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
