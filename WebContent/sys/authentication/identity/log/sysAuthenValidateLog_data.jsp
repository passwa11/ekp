<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="elecAuthenVerilog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdRequesttime" title="${lfn:message('sys-authentication-identity:elecAuthenVerilog.fdRequesttime')}">
            <kmss:showDate value="${elecAuthenVerilog.fdRequesttime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdClientip" title="${lfn:message('sys-authentication-identity:elecAuthenVerilog.fdClientip')}" />
        <list:data-column property="fdUseragent" title="${lfn:message('sys-authentication-identity:elecAuthenVerilog.fdUseragent')}" />
        <list:data-column property="fdUserName" title="${lfn:message('sys-authentication-identity:elecAuthenVerilog.fdUserName')}" />
        <list:data-column property="fdPhone" title="${lfn:message('sys-authentication-identity:elecAuthenVerilog.fdPhone')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
