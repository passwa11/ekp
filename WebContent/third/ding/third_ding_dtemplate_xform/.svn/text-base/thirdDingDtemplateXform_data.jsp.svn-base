<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingDtemplateXform" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingDtemplateXform.fdName')}" />
        <list:data-column property="fdCorpId" title="${lfn:message('third-ding:thirdDingDtemplateXform.fdCorpId')}" />
        <list:data-column property="fdAgentId" title="${lfn:message('third-ding:thirdDingDtemplateXform.fdAgentId')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-ding:thirdDingDtemplateXform.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdDingDtemplateXform.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdDingDtemplateXform.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingDtemplateXform.docCreateTime')}">
            <kmss:showDate value="${thirdDingDtemplateXform.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
