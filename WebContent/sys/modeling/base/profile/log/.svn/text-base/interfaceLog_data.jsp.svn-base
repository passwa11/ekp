<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingInterfaceLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingInterfaceLog.docCreator')}" escape="false">
            <c:out value="${modelingInterfaceLog.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingInterfaceLog.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdConsumeTime" title="${lfn:message('sys-modeling-base:modelingInterfaceLog.fdConsumeTime')}" escape="false">
            <c:out value="${modelingInterfaceLog.fdConsumeTime}" />ms
        </list:data-column>
        <list:data-column col="fdName" title="${lfn:message('sys-modeling-base:modelingInterfaceLog.fdName')}" escape="false">
            <c:out value="${modelingInterfaceLog.fdName}" />
        </list:data-column>
        <list:data-column col="fdStatus" title="${lfn:message('sys-modeling-base:modelingInterfaceLog.fdStatus')}" escape="false">
            <div class="monitorLogStatus">
                <c:if test="${modelingInterfaceLog.fdStatus eq 1}">
                    <div class="success"></div>
                </c:if>
                <c:if test="${modelingInterfaceLog.fdStatus eq 2}">
                    <div class="fail"></div>
                </c:if>
                <c:if test="${modelingInterfaceLog.fdStatus eq 0}">
                    <div class="doing"></div>
                </c:if>
                <sunbor:enumsShow value="${modelingInterfaceLog.fdStatus}" enumsType="sys_modeling_interface_exec" />
            </div>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingInterfaceLog.docCreateTime')}">
            <kmss:showDate value="${modelingInterfaceLog.docCreateTime}" type="datetime"/>
        </list:data-column>
        <list:data-column col="oper" title="${lfn:message('list.operation')}" escape="false">
            <div class="monitorLogView">
                <a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/sys/modeling/base/interfaceLog.do?method=view&fdId=${modelingInterfaceLog.fdId }"  target="_blank">${lfn:message("button.view")}</a>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
