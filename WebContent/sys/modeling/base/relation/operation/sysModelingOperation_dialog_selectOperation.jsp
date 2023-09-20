<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysModelingOperation" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('sys-modeling-base:sysModelingOperation.fdName')}" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdType.name" title="${lfn:message('sys-modeling-base:sysModelingOperation.fdType')}">
            <sunbor:enumsShow value="${sysModelingOperation.fdType}" enumsType="sys_modeling_operation" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${sysModelingOperation.fdType}" />
        </list:data-column>
        <list:data-column property="fdDefType"/>
        <list:data-column property="fdOperationScenario"/>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
