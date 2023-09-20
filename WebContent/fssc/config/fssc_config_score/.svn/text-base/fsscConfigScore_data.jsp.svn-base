<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscConfigScore" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdMonth.name" title="${lfn:message('fssc-config:fsscConfigScore.fdMonth')}">
            <sunbor:enumsShow value="${fsscConfigScore.fdMonth}" enumsType="fssc_config_enums_month" />
        </list:data-column>
        <list:data-column col="fdMonth">
            <c:out value="${fsscConfigScore.fdMonth}" />
        </list:data-column>
        <list:data-column col="fdYear.name" title="${lfn:message('fssc-config:fsscConfigScore.fdYear')}">
            <c:out value="${fsscConfigScore.fdYear}" />
        </list:data-column>
        <list:data-column col="fdPerson.name" title="${lfn:message('fssc-config:fsscConfigScore.fdPerson')}" escape="false">
            <c:out value="${fsscConfigScore.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${fsscConfigScore.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column property="fdScoreInit" title="${lfn:message('fssc-config:fsscConfigScore.fdScoreInit')}" />
        <list:data-column property="fdScoreRemain" title="${lfn:message('fssc-config:fsscConfigScore.fdScoreRemain')}" />
        <list:data-column property="fdScoreUse" title="${lfn:message('fssc-config:fsscConfigScore.fdScoreUse')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-config:fsscConfigScore.docCreator')}" escape="false">
            <c:out value="${fsscConfigScore.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscConfigScore.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-config:fsscConfigScore.docCreateTime')}">
            <kmss:showDate value="${fsscConfigScore.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
