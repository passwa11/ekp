<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWpsWebhook" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdAppId" title="${lfn:message('third-wps:thirdWpsWebhook.fdAppId')}" />
        <list:data-column property="fdVolumeId" title="${lfn:message('third-wps:thirdWpsWebhook.fdVolumeId')}" />
        <list:data-column col="fdExpireTime" title="${lfn:message('third-wps:thirdWpsWebhook.fdExpireTime')}">
            <kmss:showDate value="${thirdWpsWebhook.fdExpireTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdWebhookId" title="${lfn:message('third-wps:thirdWpsWebhook.fdWebhookId')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-wps:thirdWpsWebhook.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdWpsWebhook.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdWpsWebhook.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('third-wps:thirdWpsWebhook.docCreator')}" escape="false">
            <c:out value="${thirdWpsWebhook.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${thirdWpsWebhook.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
