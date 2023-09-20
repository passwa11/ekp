<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingMaintenanceLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('sys-modeling-base:modeling.model.fdName')}"/>
        <list:data-column property="fdIp" title="${lfn:message('sys-modeling-base:modelingOperLog.fdIp')}"/>
        <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingBusiness.docCreator')}" escape="false">
            <c:out value="${modelingMaintenanceLog.docCreator.fdName}"/>
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingMaintenanceLog.docCreator.fdId}"/>
        </list:data-column>
        <list:data-column col="fdStartTime" title="${lfn:message('sys-modeling-base:table.task.start.time')}">
            <kmss:showDate value="${modelingMaintenanceLog.fdStartTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdEndTime" title="${lfn:message('sys-modeling-base:table.task.end.time')}">
            <kmss:showDate value="${modelingMaintenanceLog.fdEndTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdTime" title="${lfn:message('sys-modeling-base:table.task.time.consuming')}">
            ${modelingMaintenanceLog.fdTime}
        </list:data-column>
        <list:data-column col="fdStatus" title="${lfn:message('sys-modeling-base:table.status')}">
            <c:if test="${modelingMaintenanceLog.fdStatus=='-2'}">
                "${lfn:message('sys-modeling-base:table.fail')}"
            </c:if>
            <c:if test="${modelingMaintenanceLog.fdStatus=='-1'}">
                "${lfn:message('sys-modeling-base:table.end')}"
            </c:if>
            <c:if test="${modelingMaintenanceLog.fdStatus=='0'||modelingMaintenanceLog.fdStatus=='1'||modelingMaintenanceLog.fdStatus=='2'}">
                "${lfn:message('sys-modeling-base:table.inProgress')}"
            </c:if>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.docCreateTime')}">
            <kmss:showDate value="${modelingMaintenanceLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <%--        <list:data-column col="fdType" title="操作" >--%>
        <%--        	1-2-3--%>
        <%--     	</list:data-column>--%>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}"/>
</list:data>
