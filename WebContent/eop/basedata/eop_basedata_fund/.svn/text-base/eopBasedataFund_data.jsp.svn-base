<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataFund" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataFund.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataFund.fdCode')}" />
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataFund.fdType')}">
            <sunbor:enumsShow value="${eopBasedataFund.fdType}" enumsType="eop_basedata_fund_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${eopBasedataFund.fdType}" />
        </list:data-column>
        <list:data-column property="fdAccountingName" title="${lfn:message('eop-basedata:eopBasedataFund.fdAccountingName')}" />
        <list:data-column property="fdAccountingCode" title="${lfn:message('eop-basedata:eopBasedataFund.fdAccountingCode')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataFund.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataFund.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataFund.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataFund.docCreateTime')}">
            <kmss:showDate value="${eopBasedataFund.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataFund.docCreator')}" escape="false">
            <c:out value="${eopBasedataFund.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataFund.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
