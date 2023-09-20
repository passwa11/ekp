<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrRatifyFire" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('hr-ratify:hrRatifyMain.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('hr-ratify:hrRatifyMain.docNumber')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('hr-ratify:hrRatifyMain.docCreator')}" escape="false">
            <c:out value="${hrRatifyFire.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${hrRatifyFire.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}">
            <kmss:showDate value="${hrRatifyFire.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docPublishTime" title="${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}">
            <kmss:showDate value="${hrRatifyFire.docPublishTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docStatus.name" title="${lfn:message('hr-ratify:hrRatifyMain.docStatus')}">
            <sunbor:enumsShow value="${hrRatifyFire.docStatus}" enumsType="hr_ratify_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${hrRatifyFire.docStatus}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('hr-ratify:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${hrRatifyFire.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('hr-ratify:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${hrRatifyFire.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
