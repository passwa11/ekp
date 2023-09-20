<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdFeishuPersonMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdEkp.name" title="${lfn:message('third-feishu:thirdFeishuPersonMapping.fdEkp')}" escape="false">
            <c:out value="${thirdFeishuPersonMapping.fdEkp.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkp.id" escape="false">
            <c:out value="${thirdFeishuPersonMapping.fdEkp.fdId}" />
        </list:data-column>
        <list:data-column property="fdEmployeeId" title="${lfn:message('third-feishu:thirdFeishuPersonMapping.fdEmployeeId')}" />
        <list:data-column property="fdLoginName" title="${lfn:message('third-feishu:thirdFeishuPersonMapping.fdLoginName')}" />
        <list:data-column property="fdMobileNo" title="${lfn:message('third-feishu:thirdFeishuPersonMapping.fdMobileNo')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
