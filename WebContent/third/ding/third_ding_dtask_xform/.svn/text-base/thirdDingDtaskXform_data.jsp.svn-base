<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingDtaskXform" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingDtaskXform.fdName')}" />
        <list:data-column property="fdTaskId" title="${lfn:message('third-ding:thirdDingDtaskXform.fdTaskId')}" />
        <list:data-column property="fdDingUserId" title="${lfn:message('third-ding:thirdDingDtaskXform.fdDingUserId')}" />
        <list:data-column col="fdEkpUser.name" title="${lfn:message('third-ding:thirdDingDtaskXform.fdEkpUser')}" escape="false">
            <c:out value="${thirdDingDtaskXform.fdEkpUser.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkpUser.id" escape="false">
            <c:out value="${thirdDingDtaskXform.fdEkpUser.fdId}" />
        </list:data-column>
        <list:data-column col="fdInstance.name" title="${lfn:message('third-ding:thirdDingDtaskXform.fdInstance')}" escape="false">
            <c:out value="${thirdDingDtaskXform.fdInstance.fdName}" />
        </list:data-column>
        <list:data-column col="fdInstance.id" escape="false">
            <c:out value="${thirdDingDtaskXform.fdInstance.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingDtaskXform.docCreateTime')}">
            <kmss:showDate value="${thirdDingDtaskXform.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingDtaskXform.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingDtaskXform.fdStatus}" enumsType="third_ding_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingDtaskXform.fdStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
