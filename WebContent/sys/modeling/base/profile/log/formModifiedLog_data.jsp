<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingFormModifiedLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="docCreator.name"
                          title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.docCreator')}" escape="false">
            <c:out value="${modelingFormModifiedLog.docCreator.fdName}"/>
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingFormModifiedLog.docCreator.fdId}"/>
        </list:data-column>
        <list:data-column col="docCreateTime"
                          title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.docCreateTime')}">
            <kmss:showDate value="${modelingFormModifiedLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIp" title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.fdIp')}">
            <c:out value="${modelingFormModifiedLog.fdIp}"/>
        </list:data-column>
        <list:data-column col="fdAppName" title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.ApplicationName')}">
            <c:out value="${modelingFormModifiedLog.modelMain.fdApplication.fdAppName}"/>
        </list:data-column>
        <list:data-column col="modelMain.fdName" title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.modelMain')}">
            <c:out value="${modelingFormModifiedLog.modelMain.fdName}"/>
        </list:data-column>
        <list:data-column col="oper" title="${lfn:message('list.operation')}" escape="false">
            <div class="monitorLogView">
                <a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/sys/modeling/base/modelingFormModified.do?method=view&fdId=${modelingFormModifiedLog.fdId }"  target="_blank">${lfn:message("button.view")}</a>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}"/>
</list:data>
