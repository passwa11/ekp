<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:forEach var="fsscLoanMain" items="${queryPage.list}" varStatus="status">
<c:set var="loanId" value="${fsscLoanMain.fdId}"></c:set>
<li onclick="viewLoan('${fsscLoanMain.fdId}','${fsscLoanMain.docTemplate.fdId}');">
      <div class="ld-application-form-list-item-top">
      		<div style="width:60%;word-break: break-all;">
          <c:if test="${fn:length(fsscLoanMain.docSubject)>40 }">${fn:substring(fsscLoanMain.docSubject,0,37)}...</c:if>
          <c:if test="${fn:length(fsscLoanMain.docSubject)<=40 }">${fsscLoanMain.docSubject}</c:if>
          </div>
           <div style="width:40%;text-align:right;">
           	<c:if test="${not empty moreInfo[loanId]['standardMoney']}">
           		<kmss:showNumber value="${moreInfo[loanId]['standardMoney']}" pattern="##0.00"/>
           	</c:if>	
              <c:if test="${ empty moreInfo[loanId]['standardMoney']}">
              		0.00
              </c:if>
          </div>
      </div>
      <div class="ld-application-form-list-item-time">
      		<div style="width:40%;float:left;">
      			<kmss:showDate value="${fsscLoanMain.docCreateTime}" type="date"></kmss:showDate>
      		</div>
          <div style="width:40%;text-align:right;float:right;">
            	<span class="ld-list-status ld-list-status-status${fsscLoanMain.docStatus}"><sunbor:enumsShow value="${fsscLoanMain.docStatus}" enumsType="common_status" /></span>
          </div>
      </div>
  </li>
</c:forEach>
<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
<input name="totalrows" value="${queryPage.totalrows}" type="hidden"/>
