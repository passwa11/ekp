<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}"  />
        <list:data-column col="docStatus" title="${lfn:message('fssc-expense:fsscExpenseMain.docStatus')}">
        	<sunbor:enumsShow enumsType="common_status" value="${fsscExpenseMain.docStatus }"/>
        </list:data-column>
        <list:data-column col="fdPaymentStatus" title="${lfn:message('fssc-expense:fsscExpenseMain.fdPaymentStatus')}">
        	<sunbor:enumsShow enumsType="eop_basedata_payment_status" value="${fsscExpenseMain.fdPaymentStatus }"/>
        </list:data-column>
        <list:data-column property="docNumber" title="${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}" />
        <list:data-column col="fdTotalApprovedMoney" title="${lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney')}" >
        	<kmss:showNumber value="${ fsscExpenseMain.fdTotalApprovedMoney}" pattern="#0.00"/>
        </list:data-column>
        <list:data-column col="fdTotalStandaryMoney" title="${lfn:message('fssc-expense:fsscExpenseMain.fdTotalStandaryMoney')}" >
        	<kmss:showNumber value="${ fsscExpenseMain.fdTotalStandaryMoney}" pattern="###,##0.00"/>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-expense:fsscExpenseMain.docCreator')}" escape="false">
            <c:out value="${fsscExpenseMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscExpenseMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-expense:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscExpenseMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-expense:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscExpenseMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
