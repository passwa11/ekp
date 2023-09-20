<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="fsscLoanMain" list="${queryPage.list }" custom="false">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject" headerClass="width250" styleClass="width250"  title="${ lfn:message('fssc-loan:fsscLoanMain.docSubject') }" escape="false" style="text-align:left;min-width:100px">
		 <a class="com_subject textEllipsis" title="${fsscLoanMain.docSubject}" href="${LUI_ContextPath}/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=view&fdId=${fsscLoanMain.fdId}" target="_blank">
		  [${fsscLoanMain.docTemplate.fdName}]&nbsp;&nbsp;<c:out value="${fsscLoanMain.docSubject}"/>
		 </a>
		</list:data-column>
		<list:data-column  col="docNumber"  title="${ lfn:message('fssc-loan:fsscLoanMain.docNumber') }">
		    ${fsscLoanMain.docNumber}
		</list:data-column>
		<list:data-column  col="docCreateTime"  title="${ lfn:message('fssc-loan:fsscLoanMain.docCreateTime') }">
		    <kmss:showDate value="${fsscLoanMain.docCreateTime}" type="date"/>
		</list:data-column>
		<list:data-column  col="fdMoney" headerClass="width120" styleClass="width120" title="${ lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney') }">
		    ${lfn:message('fssc-loan:fssc.loan.portlet.nopay.money') }：
		    <c:if test="${not empty noPayMap[fsscLoanMain.fdId]}">
		    	<kmss:showNumber value="${noPayMap[fsscLoanMain.fdId]}" pattern="##0.00"/>${ lfn:message('fssc-loan:portlet.loan.notExpense.money') }
		    </c:if>
		</list:data-column>
	</list:data-columns>
</list:data>
