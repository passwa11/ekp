<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingCspace" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingCspace.fdName')}" />
        <list:data-column col="fdCreater.name" title="${lfn:message('third-ding:thirdDingCspace.fdCreater')}" escape="false">
            <c:out value="${thirdDingCspace.fdCreater.fdName}" />
        </list:data-column>
        <list:data-column col="fdCreater.id" escape="false">
            <c:out value="${thirdDingCspace.fdCreater.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingCspace.docCreateTime')}">
            <kmss:showDate value="${thirdDingCspace.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingCspace.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingCspace.fdStatus}" enumsType="third_ding_cspace_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingCspace.fdStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
