<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseShareMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docSubject')}" headerClass="width140" />
        <list:data-column property="fdNumber" title="${lfn:message('fssc-expense:fsscExpenseShareMain.fdNumber')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docCreator')}" escape="false">
            <c:out value="${fsscExpenseShareMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscExpenseShareMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdOperator.name" title="${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}" escape="false">
            <c:out value="${fsscExpenseShareMain.fdOperator.fdName}" />
        </list:data-column>
        <list:data-column col="fdOperator.id" escape="false">
            <c:out value="${fsscExpenseShareMain.fdOperator.fdId}" />
        </list:data-column>
        <list:data-column property="fdModelName" title="${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName')}">
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-expense:fsscExpenseShareMain.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseShareMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdOperateDate" title="${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperateDate')}">
            <kmss:showDate value="${fsscExpenseShareMain.fdOperateDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-expense:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscExpenseShareMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-expense:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscExpenseShareMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
