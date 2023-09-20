<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="fsscMobileNote" list="${queryPage.list }" custom="false">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdSubject" headerClass="width250" styleClass="width250"  title="${ lfn:message('fssc-mobile:fsscMobileNote.fdSubject') }" escape="false" style="text-align:left;min-width:100px">
		  [${fsscMobileNote.fdExpenseItemName}]&nbsp;&nbsp;<c:out value="${fsscMobileNote.fdSubject}"/>
		</list:data-column>
		<list:data-column  col="docCreateTime"  title="${ lfn:message('fssc-mobile:fsscMobileNote.fdHappenDate') }">
		    ${lfn:message('fssc-mobile:fsscMobileNote.fdHappenDate')}：<kmss:showDate value="${fsscMobileNote.docCreateTime}" type="date"/>
		</list:data-column>
		<list:data-column  col="fdMoney" headerClass="width120" styleClass="width120" title="${ lfn:message('fssc-mobile:fsscMobileNote.fdMoney') }">
		    ${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}：<kmss:showNumber value="${fsscMobileNote.fdMoney}" pattern="##0.00"/>${lfn:message('fssc-mobile:portlet.note.notExpense.money')}
		</list:data-column>
		<list:data-column  col="fdExpenseDetailId" headerClass="width100" styleClass="width100" title="${ lfn:message('fssc-mobile:fsscMobileNote.fdExpenseDetailId') }">
			<c:set var="detailId" value="${fsscMobileNote.fdExpenseDetailId}"></c:set>
			<c:if test="${empty statusMap[detailId]}">
				${lfn:message('fssc-mobile:portlet.note.expense.status.not')}
			</c:if>
			<c:if test="${statusMap[detailId]=='20'}">
				${lfn:message('fssc-mobile:portlet.note.expense.status.ing')}
			</c:if>
			<c:if test="${statusMap[detailId]=='30'}">
				${lfn:message('fssc-mobile:portlet.note.expense.status.ed')}
			</c:if>
		</list:data-column>
	</list:data-columns>
</list:data>
