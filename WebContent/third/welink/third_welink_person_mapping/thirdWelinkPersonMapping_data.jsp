<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkPersonMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdWelinkId" title="${lfn:message('third-welink:thirdWelinkPersonMapping.fdWelinkId')}" />
        <list:data-column property="fdWelinkUserId" title="${lfn:message('third-welink:thirdWelinkPersonMapping.fdWelinkUserId')}" />
        <list:data-column property="fdLoginName" title="${lfn:message('third-welink:thirdWelinkPersonMapping.fdLoginName')}" />
        <list:data-column property="fdMobileNo" title="${lfn:message('third-welink:thirdWelinkPersonMapping.fdMobileNo')}" />
        <list:data-column col="fdEkpPerson.name" title="${lfn:message('third-welink:thirdWelinkPersonMapping.fdEkpPerson')}" escape="false">
            <c:out value="${thirdWelinkPersonMapping.fdEkpPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkpPerson.id" escape="false">
            <c:out value="${thirdWelinkPersonMapping.fdEkpPerson.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-welink:thirdWelinkPersonMapping.docAlterTime')}">
            <kmss:showDate value="${thirdWelinkPersonMapping.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
