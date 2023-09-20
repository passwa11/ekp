<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budgeting/budgeting.tld" prefix="budgeting" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<table class="tb_normal" width="100%">
  <tr>
      <budgeting:showBudgetingDetail type="title" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}"></budgeting:showBudgetingDetail>
      <td style="width:20%;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.item')}
      </td>
      <td style="width:20%;">
          ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}
      </td>
  </tr>
   <c:forEach items="${yearList}" var="year" varStatus="vstatus">
	    <tr KMSS_IsContentRow="1">
	    	<c:set var="size" value="${fn:length(year)}"></c:set>
	    	<!-- 循环输出维度 -->
	    	<c:if test="${year[0]=='1'}">
	    		<c:set var="size" value="${fn:length(year)}"></c:set>
	    	</c:if>
	    	<c:if test="${year[0]=='0'}">
	    		<c:set var="size" value="${fn:length(year)+1}"></c:set>
	    	</c:if>
	    	<c:if test="${size>3}">
	    	<c:forEach begin="1" end="${size-3}" step="2" var="index">
	    	<td align="center">
	    		${year[index+1]}
	    		<xform:text property="fdDetailList_Form[${vstatus.index}].fdBudgetItemId" showStatus="noShow" value="${year[index]}"></xform:text>
	    	</td>
	    	</c:forEach>
	    	</c:if>
	        <td align="center">
             	<c:if test="${year[0]=='1'}">
             		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.child')}
             		<hr />
             		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
             	</c:if>
             </td>
	        <td align="center">
	            <%-- 全年--%>
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdTotal" _xform_type="text">
	            	<c:if test="${year[0]=='1'}">
		            	<kmss:showNumber value="${year[size-1]}" pattern="0.00"></kmss:showNumber><hr />
		            </c:if>
	                <kmss:showNumber value="${year[size-2]}" pattern="0.00"></kmss:showNumber>
	            </div>
	        </td>
	    </tr>
	</c:forEach>
</table>
