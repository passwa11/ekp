<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="fsscExpenseMain" list="${queryPage.list }" custom="false">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject" headerClass="width250" styleClass="width250"  title="${ lfn:message('fssc-expense:fsscExpenseMain.docSubject') }" escape="false" style="text-align:left;min-width:100px">
		  <a class="com_subject textEllipsis" title="${fsscExpenseMain.docSubject}" href="${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=view&fdId=${fsscExpenseMain.fdId}" target="_blank">
		  	[${fsscExpenseMain.docTemplate.fdName}]<c:out value="${fsscExpenseMain.docSubject}"/>
		  </a>
		</list:data-column>
		<list:data-column col="docNumber" headerClass="width250" styleClass="width250"  title="${ lfn:message('fssc-expense:fsscExpenseMain.docNumber') }" escape="false">
		  <c:out value="${fsscExpenseMain.docNumber}"/>
		</list:data-column>
		<list:data-column  col="docCreateTime"  title="${ lfn:message('fssc-expense:fsscExpenseMain.docCreateTime') }">
		    <kmss:showDate value="${fsscExpenseMain.docCreateTime}" type="date"/>
		</list:data-column>
		<list:data-column  col="fdMoney" headerClass="width120" styleClass="width120" title="${ lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney') }">
		    <kmss:showNumber value="${fsscExpenseMain.fdTotalApprovedMoney}" pattern="##0.00"/>${lfn:message('fssc-expense:portlet.expense.notExpense.money')}
		</list:data-column>
		<list:data-column  col="docStatus" headerClass="width120" styleClass="width120" title="${ lfn:message('fssc-fee:fsscFeeMain.docStatus') }">
		    <sunbor:enumsShow enumsType="common_status" value="${fsscExpenseMain.docStatus}"></sunbor:enumsShow>
		</list:data-column>
		 <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-expense:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscExpenseMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-expense:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscExpenseMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
	</list:data-columns>
</list:data>
