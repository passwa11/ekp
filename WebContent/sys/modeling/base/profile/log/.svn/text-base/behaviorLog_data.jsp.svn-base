<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingBehaviorLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingBehaviorLog.docCreator')}" escape="false">
            <c:out value="${modelingBehaviorLog.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingBehaviorLog.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdStartTimeDate" title="${lfn:message('sys-modeling-base:modelingBehaviorLog.fdStartTime')}">
            <kmss:showDate value="${modelingBehaviorLog.fdStartTimeDate}" type="datetime"/>
        </list:data-column>
        <list:data-column col="fdEndTimeDate" title="${lfn:message('sys-modeling-base:modelingBehaviorLog.fdEndTime')}">
            <kmss:showDate value="${modelingBehaviorLog.fdEndTimeDate}" type="datetime"/>
        </list:data-column>
        <list:data-column col="fdName" title="${lfn:message('sys-modeling-base:modelingBehaviorLog.fdName')}" escape="false">
            <c:out value="${modelingBehaviorLog.fdName}" />
        </list:data-column>
        <list:data-column col="fdConsumeTime" title="${lfn:message('sys-modeling-base:modelingBehaviorLog.fdConsumeTime')}" escape="false">
            <c:if test="${not empty modelingBehaviorLog.fdConsumeTime && (modelingBehaviorLog.fdStatus eq 2 || modelingBehaviorLog.fdStatus eq 3 || modelingBehaviorLog.fdStatus eq 4)}">
            <c:out value="${modelingBehaviorLog.fdConsumeTime}" />ms
            </c:if>
        </list:data-column>
        <list:data-column col="fdStatus" title="${lfn:message('sys-modeling-base:modelingBehaviorLog.fdStatus')}" escape="false">
            <div class="monitorLogStatus">
                <c:if test="${modelingBehaviorLog.fdStatus eq 1 || modelingBehaviorLog.fdStatus eq 2}">
                    <div class="success"></div>
                </c:if>
                <c:if test="${modelingBehaviorLog.fdStatus eq 3}">
                    <div class="fail"></div>
                </c:if>
                <c:if test="${modelingBehaviorLog.fdStatus eq 0}">
                    <div class="doing"></div>
                </c:if>
                <c:if test="${modelingBehaviorLog.fdStatus eq 4 || modelingBehaviorLog.fdStatus eq 5}">
                    <div class="suspend"></div>
                </c:if>
                <sunbor:enumsShow value="${modelingBehaviorLog.fdStatus}" enumsType="sys_modeling_behavior_exec" />
            </div>
        </list:data-column>
        <list:data-column col="oper" title="${lfn:message('list.operation')}" escape="false">
            <div class="monitorLogView">
                <a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/sys/modeling/base/behaviorLog.do?method=view&fdId=${modelingBehaviorLog.fdId }"  target="_blank">${lfn:message("button.view")}</a>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
