<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingDtemplate" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingDtemplate.fdName')}" />
        <%-- <list:data-column property="fdProcessCode" title="${lfn:message('third-ding:thirdDingDtemplate.fdProcessCode')}" /> --%>
        <list:data-column property="fdCorpId" title="${lfn:message('third-ding:thirdDingDtemplate.fdCorpId')}" />
        <list:data-column property="fdAgentId" title="${lfn:message('third-ding:thirdDingDtemplate.fdAgentId')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-ding:thirdDingDtemplate.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdDingDtemplate.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdDingDtemplate.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('third-ding:thirdDingDtemplate.fdType')}">
            <sunbor:enumsShow value="${thirdDingDtemplate.fdType}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${thirdDingDtemplate.fdType}" />
        </list:data-column>
        <%-- <list:data-column col="fdFlow.name" title="${lfn:message('third-ding:thirdDingDtemplate.fdFlow')}">
            <sunbor:enumsShow value="${thirdDingDtemplate.fdFlow}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdFlow">
            <c:out value="${thirdDingDtemplate.fdFlow}" />
        </list:data-column>
        <list:data-column col="fdDisableFormEdit.name" title="${lfn:message('third-ding:thirdDingDtemplate.fdDisableFormEdit')}">
            <sunbor:enumsShow value="${thirdDingDtemplate.fdDisableFormEdit}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdDisableFormEdit">
            <c:out value="${thirdDingDtemplate.fdDisableFormEdit}" />
        </list:data-column> --%>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingDtemplate.docCreateTime')}">
            <kmss:showDate value="${thirdDingDtemplate.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
