<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWpsConvert" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdTaskId" title="${lfn:message('third-wps:thirdWpsConvert.fdTaskId')}" />
        <list:data-column col="fdType.name" title="${lfn:message('third-wps:thirdWpsConvert.fdType')}">
            <sunbor:enumsShow value="${thirdWpsConvert.fdType}" enumsType="third_wps_convert_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${thirdWpsConvert.fdType}" />
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('third-wps:thirdWpsConvert.fdStatus')}">
            <sunbor:enumsShow value="${thirdWpsConvert.fdStatus}" enumsType="third_wps_convert_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdWpsConvert.fdStatus}" />
        </list:data-column>
        <list:data-column property="fdResult" title="${lfn:message('third-wps:thirdWpsConvert.fdResult')}" />
        <list:data-column property="fdDownloadUrl" title="${lfn:message('third-wps:thirdWpsConvert.fdDownloadUrl')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('third-wps:thirdWpsConvert.docCreator')}" escape="false">
            <c:out value="${thirdWpsConvert.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdWpsConvert.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
