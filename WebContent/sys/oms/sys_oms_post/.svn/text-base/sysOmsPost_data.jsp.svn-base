<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOmsPost" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('sys-oms:sysOmsPost.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-oms:sysOmsPost.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysOmsPost.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${sysOmsPost.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdNo" title="${lfn:message('sys-oms:sysOmsPost.fdNo')}" />
        <list:data-column property="fdKeyword" title="${lfn:message('sys-oms:sysOmsPost.fdKeyword')}" />
        <list:data-column property="fdImportinfo" title="${lfn:message('sys-oms:sysOmsPost.fdImportinfo')}" />
        <list:data-column property="fdHandleStatus" title="${lfn:message('sys-oms:sysOmsPost.fdHandleStatus')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
