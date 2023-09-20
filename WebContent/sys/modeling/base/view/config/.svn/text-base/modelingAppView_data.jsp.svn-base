<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingAppView" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('sys-modeling-base:modelingAppView.fdName')}" />
        <list:data-column col="fdModel.name" title="${lfn:message('sys-modeling-base:modelingAppView.fdModel')}" escape="false">
            <c:out value="${modelingAppView.fdModel.fdName}" />
        </list:data-column>
        <list:data-column col="fdModel.id" escape="false">
            <c:out value="${modelingAppView.fdModel.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingAppView.docCreateTime')}">
            <kmss:showDate value="${modelingAppView.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('sys-modeling-base:modelingAppView.docAlterTime')}">
            <kmss:showDate value="${modelingAppView.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-modeling-base:modelingAppView.fdIsAvailable')}">
            <sunbor:enumsShow value="${modelingAppView.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${modelingAppView.fdIsAvailable}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
