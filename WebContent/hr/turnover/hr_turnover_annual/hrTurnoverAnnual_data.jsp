<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrTurnoverAnnual" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docCreator.name" title="${lfn:message('hr-turnover:hrTurnoverAnnual.docCreator')}" escape="false">
            <c:out value="${hrTurnoverAnnual.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${hrTurnoverAnnual.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-turnover:hrTurnoverAnnual.docCreateTime')}">
            <kmss:showDate value="${hrTurnoverAnnual.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdYear" title="${lfn:message('hr-turnover:hrTurnoverAnnual.fdYear')}" />
        <list:data-column property="fdRateO" title="${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateO')}" />
        <list:data-column property="fdDesc" title="${lfn:message('hr-turnover:hrTurnoverAnnual.fdDesc')}" />
        <list:data-column property="fdRateP" title="${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateP')}" />
        <list:data-column property="fdRateS" title="${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateS')}" />
        <list:data-column property="fdRateM" title="${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateM')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
