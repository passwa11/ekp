<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseDetail" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        
        <list:data-column col="docMain.fdId">
        	${fsscExpenseDetail.docMain.fdId}
        </list:data-column>
        
        <list:data-column col="docSubject" title="${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}" style="text-align:left;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;width:120px;" >
        	${fsscExpenseDetail.docMain.docSubject}
        </list:data-column>
        
        <list:data-column col="docNumber" title="${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}" headerClass="width140" >
        	${fsscExpenseDetail.docMain.docNumber}
        </list:data-column>

		<list:data-column col="fdCompany.fdName" title="${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany')}" headerClass="width140" >
        	${fsscExpenseDetail.fdCompany.fdName}
        </list:data-column>
        
        <list:data-column col="fdCostCenter.fdName" title="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" headerClass="width140" >
        	${fsscExpenseDetail.fdCostCenter.fdName}
        </list:data-column>
        
        <list:data-column col="fdProject.fdName" title="${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}" headerClass="width140" >
        	${fsscExpenseDetail.docMain.fdProject.fdName}
        </list:data-column>
        
        <list:data-column col="docTemplate.fdName" title="${lfn:message('fssc-expense:fsscExpenseMain.docTemplate')}" headerClass="width140" >
        	${fsscExpenseDetail.docMain.docTemplate.fdName}
        </list:data-column>
        
        <list:data-column col="fdProject.fdName" title="${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}" headerClass="width140" >
        	${fsscExpenseDetail.fdProject.fdName}
        </list:data-column>
        
        <list:data-column col="fdProappName" title="${lfn:message('fssc-expense:fsscExpenseMain.fdProappName')}" headerClass="width140" >
        	${fsscExpenseDetail.docMain.fdProappName}
        </list:data-column>
        
        <list:data-column col="fdExpenseItem.fdName" title="${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}" headerClass="width140" >
        	${fsscExpenseDetail.fdExpenseItem.fdName}
        </list:data-column>
        
        <list:data-column col="fdClaimant.name" title="${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}" escape="false">
            <c:out value="${fsscExpenseDetail.docMain.fdClaimant.fdName}" />
        </list:data-column>
        
        <list:data-column col="docStatus" title="${lfn:message('fssc-expense:fsscExpenseMain.docStatus')}" escape="false">
            <sunbor:enumsShow enumsType="common_status" value="${fsscExpenseDetail.docMain.docStatus}"/>
        </list:data-column>
        
        <list:data-column col="fdApprovedStandardMoney" title="${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedStandardMoney')}" escape="false">
            <kmss:showNumber value="${fsscExpenseDetail.fdApprovedStandardMoney }" pattern="#,##0.00"/>
        </list:data-column>
        
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-expense:fsscExpenseDetail.docCreateTime')}">
            <kmss:showDate value="${fsscExpenseDetail.docMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
