<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysModelingRelation" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
	<list:data-column property="fdName" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdName')}" />
        <list:data-column property="fdWidgetId" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdWidgetId')}" />
        <list:data-column property="fdWidgetName" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdWidgetName')}" />
        <list:data-column property="fdShowType" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdShowType')}" />
        <list:data-column property="fdReturn" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdReturn')}" />
        <list:data-column property="fdInWhere" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdInWhere')}" />
        <list:data-column property="fdOutSort" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdOutSort')}" />
        <list:data-column property="fdOutSelect" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdOutSelect')}" />
        <list:data-column property="fdOutSearch" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdOutSearch')}" />
        <list:data-column property="fdOutParam" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdOutParam')}" />
        <list:data-column col="fdIsThrough.name" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdIsThrough')}">
            <sunbor:enumsShow value="${sysModelingRelation.fdIsThrough}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsThrough">
            <c:out value="${sysModelingRelation.fdIsThrough}" />
        </list:data-column>
        <list:data-column property="fdThrough" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdThrough')}" />
        <list:data-column col="fdIsThroughList.name" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdIsThroughList')}">
            <sunbor:enumsShow value="${sysModelingRelation.fdIsThroughList}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsThroughList">
            <c:out value="${sysModelingRelation.fdIsThroughList}" />
        </list:data-column>
        <list:data-column property="fdThroughList" title="${lfn:message('sys-modeling-base:sysModelingRelation.fdThroughList')}" />
        <list:data-column col="modelMain.name" title="${lfn:message('sys-modeling-base:sysModelingRelation.modelMain')}" escape="false">
            <c:out value="${sysModelingRelation.modelMain.fdName}" />
        </list:data-column>
        <list:data-column col="modelMain.id" escape="false">
            <c:out value="${sysModelingRelation.modelMain.fdId}" />
        </list:data-column>
        <list:data-column col="modelPassive.name" title="${lfn:message('sys-modeling-base:sysModelingRelation.modelPassive')}" escape="false">
            <c:out value="${sysModelingRelation.modelPassive.fdName}" />
        </list:data-column>
        <list:data-column col="modelPassive.id" escape="false">
            <c:out value="${sysModelingRelation.modelPassive.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
