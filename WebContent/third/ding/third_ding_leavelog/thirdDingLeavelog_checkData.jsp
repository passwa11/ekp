<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingLeavelog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-ding:thirdDingLeavelog.docSubject')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('third-ding:third.ding.check.user')}" escape="false">
            <c:out value="${thirdDingLeavelog.fdEkpUsername}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdDingLeavelog.docCreator.fdId}" />
        </list:data-column>
        <list:data-column property="fdEkpUserid" title="${lfn:message('third-ding:thirdDingLeavelog.fdEkpUserid')}" />
        <list:data-column property="fdUserid" title="${lfn:message('third-ding:thirdDingLeavelog.fdUserid')}" />
        <list:data-column property="fdTagName" title="${lfn:message('third-ding:thirdDingLeavelog.fdSubType')}" />
        <list:data-column property="fdFromTime" title="${lfn:message('third-ding:thirdDingLeavelog.fdFromTime')}"/>
        <list:data-column property="fdToTime" title="${lfn:message('third-ding:thirdDingLeavelog.fdToTime')}"/>
        <list:data-column property="fdIstrue" title="${lfn:message('third-ding:thirdDingLeavelog.fdIstrue')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-ding:thirdDingLeavelog.docAlterTime')}">
            <kmss:showDate value="${thirdDingLeavelog.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
