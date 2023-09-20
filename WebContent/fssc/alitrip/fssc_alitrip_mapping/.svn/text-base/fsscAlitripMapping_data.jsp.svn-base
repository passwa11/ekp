<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAlitripMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="tripTitleText" title="${lfn:message('fssc-alitrip:fsscAlitripMapping.tripTitleText')}" />
        <list:data-column col="fdModelId.name" title="${lfn:message('fssc-alitrip:fsscAlitripMapping.fdModelId')}" escape="false">
            <c:out value="${fsscAlitripMapping.fdModelId.fdName}" />
        </list:data-column>
        <list:data-column col="fdModelId.id" escape="false">
            <c:out value="${fsscAlitripMapping.fdModelId.fdId}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-alitrip:fsscAlitripMapping.docCreator')}" escape="false">
            <c:out value="${fsscAlitripMapping.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscAlitripMapping.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-alitrip:fsscAlitripMapping.docCreateTime')}">
            <kmss:showDate value="${fsscAlitripMapping.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="applyIdText" title="${lfn:message('fssc-alitrip:fsscAlitripMapping.applyIdText')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
