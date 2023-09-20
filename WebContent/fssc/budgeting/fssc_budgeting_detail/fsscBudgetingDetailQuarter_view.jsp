<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budgeting/budgeting.tld" prefix="budgeting" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<table class="tb_normal" width="100%">
  <tr>
      <budgeting:showBudgetingDetail type="title" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}"></budgeting:showBudgetingDetail>
      <td style="width:14%;text-align: center;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.item')}
      </td>
      <td style="width:14%;text-align: center;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}
      </td>
      <td style="width:14%;text-align: center;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}
      </td>
      <td style="width:14%;text-align: center;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}
      </td>
      <td style="width:14%;text-align: center;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}
      </td>
      <td style="width:14%;text-align: center;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}
      </td>
  </tr>
  <c:forEach items="${quarterList}" var="quarter" varStatus="vstatus">
	    <tr KMSS_IsContentRow="1">
	    	<c:set var="size" value="${fn:length(quarter)}"></c:set>
	    	<!-- 循环输出维度 -->
	    	<c:if test="${quarter[0]=='1'}">
	    		<c:set var="size" value="${fn:length(quarter)}"></c:set>
	    	</c:if>
	    	<c:if test="${quarter[0]=='0'}">
	    		<c:set var="size" value="${fn:length(quarter)+5}"></c:set>
	    	</c:if>
	    	<c:if test="${size>11}">
	    	<c:forEach begin="1" end="${size-11}" step="2" var="index">
	    	<td align="center">
	    		${quarter[index+1]}
	    		<xform:text property="fdDetailList_Form[${vstatus.index}].fdBudgetItemId" showStatus="noShow" value="${quarter[index]}"></xform:text>
	    	</td>
	    	</c:forEach>
	    	</c:if>
	        <td align="center">
             	<c:if test="${quarter[0]=='1'}">
             		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.child')}
             		<hr />
             		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
             	</c:if>
             </td>
	        <td align="center">
	            <%-- 全年--%>
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdTotal" _xform_type="text">
	            	<c:if test="${quarter[0]=='1'}">
		            	<kmss:showNumber value="${quarter[size-5]}" pattern="0.00"></kmss:showNumber><hr />
		            </c:if>
	                <kmss:showNumber value="${quarter[size-10]}" pattern="0.00"></kmss:showNumber>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 1期--%>
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodOne" _xform_type="text">
	            	<c:if test="${quarter[0]=='1'}">
		            	<kmss:showNumber value="${quarter[size-4]}" pattern="0.00"></kmss:showNumber><hr />
		            </c:if>
	                <kmss:showNumber value="${quarter[size-9]}" pattern="0.00"></kmss:showNumber>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 2期--%>
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwo" _xform_type="text">
	            	<c:if test="${quarter[0]=='1'}">
		            	<kmss:showNumber value="${quarter[size-3]}" pattern="0.00"></kmss:showNumber><hr />
		            </c:if>
	                <kmss:showNumber value="${quarter[size-8]}" pattern="0.00"></kmss:showNumber>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 3期--%>
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodThree" _xform_type="text">
	            	<c:if test="${quarter[0]=='1'}">
		            	<kmss:showNumber value="${quarter[size-2]}" pattern="0.00"></kmss:showNumber><hr />
		            </c:if>
	                <kmss:showNumber value="${quarter[size-7]}" pattern="0.00"></kmss:showNumber>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 4期--%>
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFour" _xform_type="text">
	            	<c:if test="${quarter[0]=='1'}">
		            	<kmss:showNumber value="${quarter[size-1]}" pattern="0.00"></kmss:showNumber><hr />
		            </c:if>
	                <kmss:showNumber value="${quarter[size-6]}" pattern="0.00"></kmss:showNumber>
	            </div>
	        </td>
	    </tr>
	</c:forEach>
</table>
