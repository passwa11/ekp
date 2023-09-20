<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingOmsError" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdOms.name" title="${lfn:message('third-ding:thirdDingOmsError.fdOms')}" escape="false">
            <xform:radio property="fdOms" htmlElementProperties="id='fdOper'" value="${thirdDingOmsError.fdOms}">
                <xform:enumsDataSource enumsType="third_ding_oms" />
            </xform:radio>
        </list:data-column>
        <list:data-column col="fdOper.name" title="${lfn:message('third-ding:thirdDingOmsError.fdOper')}" escape="false">
            <xform:radio property="fdOper" htmlElementProperties="id='fdOper'" value="${thirdDingOmsError.fdOper}">
                <xform:enumsDataSource enumsType="third_ding_opertype" />
            </xform:radio>
        </list:data-column>
        <c:if test="${thirdDingOmsError.fdOms=='ekp'}">
        <list:data-column property="fdEkpName" title="${lfn:message('third-ding:thirdDingOmsError.fdEkpName')}" />
        <list:data-column col="fdEkpType.name" title="${lfn:message('third-ding:thirdDingOmsError.fdEkpType')}" escape="false">
            <xform:radio property="fdEkpType" htmlElementProperties="id='fdEkpType'" value="${thirdDingOmsError.fdEkpType}">
                <xform:enumsDataSource enumsType="third_ding_ekptype" />
            </xform:radio>
        </list:data-column>
        </c:if>
        <c:if test="${thirdDingOmsError.fdOms=='ding'}">
	        <list:data-column property="fdDingName" title="${lfn:message('third-ding:thirdDingOmsError.fdDingName')}" />
	        <list:data-column col="fdDingType.name" title="${lfn:message('third-ding:thirdDingOmsError.fdDingType')}" escape="false">
	            <xform:radio property="fdDingType" htmlElementProperties="id='fdDingType'" value="${thirdDingOmsError.fdDingType}">
	                <xform:enumsDataSource enumsType="third_ding_dingtype" />
	            </xform:radio>
	        </list:data-column>
        </c:if>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
