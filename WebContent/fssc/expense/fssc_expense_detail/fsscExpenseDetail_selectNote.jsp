<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="data" list="${queryPage.list}" varIndex="status">
        <list:data-column col="fdId" >
        		${data[0] }
        </list:data-column>
        <list:data-column col="fdExpenseItemId" >
        		${data[4] }
        </list:data-column>
        <list:data-column col="fdExpenseItemName" title="${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.fdExpenseItemName') }" >
        		${data[3] }
        </list:data-column>
        <list:data-column col="fdCostCenterId" >
        		${data[6] }
        </list:data-column>
        <list:data-column col="fdCostCenterName" title="${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.fdCostCenterName') }" >
        		${data[5] }
        </list:data-column>
        <list:data-column col="fdHappenDate" title="${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.fdHappenDate') }" >
        		<kmss:showDate value="${data[7] }" type="date"/>
        </list:data-column>
        <list:data-column col="fdEndPlace" title="${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.fdEndPlace') }" >
        		${data[2] }
        </list:data-column>
        <list:data-column col="fdMoney" title="${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.fdMoney') }" >
	        <c:if test="${not empty  data[8]}">
	        	<kmss:showNumber value="${data[8] }" pattern="0.00"/>
	        </c:if>
        </list:data-column>
        <list:data-column col="fdSubject" title="${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.fdSubject') }" >
        		${data[1] }
        </list:data-column>
        <list:data-column col="docCreatorId">
        		${data[10] }
        </list:data-column>
        <list:data-column col="docCreatorName">
        		${data[11] }
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
