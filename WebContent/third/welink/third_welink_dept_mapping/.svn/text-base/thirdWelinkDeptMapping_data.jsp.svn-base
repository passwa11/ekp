<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWelinkDeptMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdWelinkId" title="${lfn:message('third-welink:thirdWelinkDeptMapping.fdWelinkId')}" />
        <list:data-column property="fdWelinkName" title="${lfn:message('third-welink:thirdWelinkDeptMapping.fdWelinkName')}" />
        <list:data-column col="fdEkpDept.name" title="${lfn:message('third-welink:thirdWelinkDeptMapping.fdEkpDept')}" escape="false">
            <c:out value="${thirdWelinkDeptMapping.fdEkpDept.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkpDept.id" escape="false">
            <c:out value="${thirdWelinkDeptMapping.fdEkpDept.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-welink:thirdWelinkDeptMapping.docAlterTime')}">
            <kmss:showDate value="${thirdWelinkDeptMapping.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
