<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingOperLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdCreator.name" title="${lfn:message('sys-modeling-base:modelingOperLog.fdCreator')}" escape="false">
            <c:out value="${modelingOperLog.fdCreator.fdName}" />
        </list:data-column>
        <list:data-column col="fdCreator.id" escape="false">
            <c:out value="${modelingOperLog.fdCreator.fdId}" />
        </list:data-column>
        <list:data-column property="fdIp" title="${lfn:message('sys-modeling-base:modelingOperLog.fdIp')}" />
        <list:data-column col="fdOperItemName" title="${lfn:message('sys-modeling-base:modelingOperLog.fdOperItemName')}" escape="false">
            <c:set var="fdOperItemName" value="${modelingOperLog.fdAppName}-${modelingOperLog.fdOperItemName}" />
            <span title="${modelingOperLog.fdOperItemName}">
                <c:out value="${fn:length(fdOperItemName) <= 28 ? fdOperItemName : fn:substring(fdOperItemName, 0, 28).concat('...')}"/>
            </span>
        </list:data-column>
        <list:data-column col="subject" title="${lfn:message('sys-modeling-base:modelingOperLog.subject')}" escape="false">
            <c:if test="${modelingOperLog.fdMethod != 'systemStopApp'}">
                <c:set var="subject" value="${modelingOperLog.fdModuleName}-${modelingOperLog.fdDataName}" />
                <span title="${modelingOperLog.fdDataName}">
                    <sunbor:enumsShow value="${modelingOperLog.fdMethod}" enumsType="modeling_oper_log_method" />-<c:out value="${fn:length(subject) <= 25 ? subject : fn:substring(subject, 0, 25).concat('...')}"/>
                </span>
            </c:if>
            <c:if test="${modelingOperLog.fdMethod == 'systemStopApp'}">
                <span title="${modelingOperLog.fdDataName}">
                    <sunbor:enumsShow value="${modelingOperLog.fdMethod}" enumsType="modeling_oper_log_method" />
                </span>
            </c:if>
        </list:data-column>
        <list:data-column col="fdCreateTime" title="${lfn:message('sys-modeling-base:modelingOperLog.fdCreateTime')}">
            <kmss:showDate value="${modelingOperLog.fdCreateTime}" type="datetime"/>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
