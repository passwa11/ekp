<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<c:forEach var="fsscFeeMain" items="${queryPage.list}" varStatus="status">
<c:set var="feeId" value="${fsscFeeMain.fdId}"></c:set>
<li onclick="viewFee('${fsscFeeMain.fdId}','${fsscFeeMain.docTemplate.fdId}');">
      <div class="ld-application-form-list-item-top">
      		<div style="width:60%;word-break: break-all;">
          <c:if test="${fn:length(fsscFeeMain.docSubject)>40 }">${fn:substring(fsscFeeMain.docSubject,0,37)}...</c:if>
          <c:if test="${fn:length(fsscFeeMain.docSubject)<=40 }">${fsscFeeMain.docSubject}</c:if>
          </div>
           <div style="width:40%;text-align:right;">
           	<c:if test="${not empty moreInfo[feeId]['standardMoney']}">
           		<kmss:showNumber value="${moreInfo[feeId]['standardMoney']}" pattern="##0.00"/>
           	</c:if>	
              <c:if test="${ empty moreInfo[feeId]['standardMoney']}">
              		0.00
              </c:if>
          </div>
      </div>
      <div class="ld-application-form-list-item-time">
      		<div style="width:40%;float:left;">
      			<kmss:showDate value="${fsscFeeMain.docCreateTime}" type="date"></kmss:showDate>
      		</div>
          <div style="width:40%;text-align:right;float:right;">
            	<span class="ld-list-status ld-list-status-status${fsscFeeMain.docStatus}"><sunbor:enumsShow value="${fsscFeeMain.docStatus}" enumsType="common_status" /></span>
          </div>
      </div>
  </li>
</c:forEach>
<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
<input name="totalrows" value="${queryPage.totalrows}" type="hidden"/>
