<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdFeishuDeptMapping" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdEkp.name" title="${lfn:message('third-feishu:thirdFeishuDeptMapping.fdEkp')}" escape="false">
            <c:out value="${thirdFeishuDeptMapping.fdEkp.fdName}" />
        </list:data-column>
        <list:data-column col="fdEkp.id" escape="false">
            <c:out value="${thirdFeishuDeptMapping.fdEkp.fdId}" />
        </list:data-column>
        <list:data-column property="fdFeishuId" title="${lfn:message('third-feishu:thirdFeishuDeptMapping.fdFeishuId')}" />
        <list:data-column property="fdFeishuName" title="${lfn:message('third-feishu:thirdFeishuDeptMapping.fdFeishuName')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-feishu:thirdFeishuDeptMapping.docAlterTime')}">
            <kmss:showDate value="${thirdFeishuDeptMapping.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
