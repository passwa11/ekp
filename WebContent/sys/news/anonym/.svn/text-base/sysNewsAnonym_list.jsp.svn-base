<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<list:data>
    <list:data-columns var="sysAnonymCommon" list="${queryPage.list}">
        <list:data-column property="fdId">
        </list:data-column>
        <list:data-column property="fdAnonymId">
        </list:data-column>
        <list:data-column headerStyle="width:500px" col="fdName" title="${ lfn:message('sys-news:sysNewsMain.fdName') }" escape="false">
            <a href="<c:url value="/sys/anonym/sysAnonymData.do?method=dataView&fdId="/>${sysAnonymCommon.fdAnonymId}"
               target="_blank" title="<c:out value="${sysAnonymCommon.fdName}"/>"><c:out value="${sysAnonymCommon.fdName}"/></a>
        </list:data-column>
        <list:data-column property="docAuthorName" title="${ lfn:message('sys-news:sysNewsMain.fdAuthorId') }">
        </list:data-column>
        <list:data-column property="docPublishTime" title="${ lfn:message('sys-news:sysNewsMain.docPublishTime') }">
        </list:data-column>
        <list:data-column headerStyle="width:200px" property="fdSummary" title="${ lfn:message('sys-news:sysNewsMain.summary') }">
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>