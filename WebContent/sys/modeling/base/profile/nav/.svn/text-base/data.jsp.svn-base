<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingAppNav" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="docSubject"  title="${lfn:message('sys-modeling-base:modelingAppNav.docSubject')}">
            <c:out value="${modelingAppNav.docSubject}" />
        </list:data-column>

      <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingAppNav.docCreator')}" escape="false">
            <c:out value="${modelingAppNav.docCreator.fdName}" />
        </list:data-column>
        
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingAppNav.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingAppNav.docCreateTime')}">
            <kmss:showDate value="${modelingAppNav.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
